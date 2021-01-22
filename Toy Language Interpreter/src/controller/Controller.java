package controller;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.programState.ProgramState;
import model.values.IValue;
import model.values.ReferenceValue;
import repository.IRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {

    private IRepository repo;
    private ExecutorService executorService;

    public Controller( IRepository repo)
    {
        this.repo = repo;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public List<ProgramState> removeCompletedProgramStates(List<ProgramState> prgList)
    {
        return prgList.stream()
                .filter(prg -> prg.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAll(List<ProgramState> programStateList) throws MyException, InterruptedException {
        programStateList.forEach(prg -> {
            try {
                repo.logProgramStateExecution(prg);
                this.displayProgramState(prg);
            } catch (IOException e) {
                throw new MyException(e.getMessage());
            }
        });

        List<Callable<ProgramState>> callables = programStateList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (() -> {
                    return p.oneStep();
                }))
                .collect(Collectors.toList());

        List<ProgramState> newProgramList = this.executorService.invokeAll(callables).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (MyException | InterruptedException | ExecutionException e) {
                        throw new MyException(e.getMessage());
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        programStateList.addAll(newProgramList);

        programStateList.forEach(prg -> {
            try {
                this.repo.logProgramStateExecution(prg);
                this.displayProgramState(prg);
            } catch (IOException e) {
                throw new MyException(e.getMessage());
            }
        });

        this.repo.setProgramsList(programStateList);

    }
    public void allStep() throws InterruptedException {
        List<ProgramState> programStateList = this.removeCompletedProgramStates(this.repo.getProgramsList());
        while (programStateList.size() > 0)
        {
            programStateList.get(0).getHeap().setContent(this.GarbageCollector());
            this.oneStepForAll(programStateList);
            programStateList = this.removeCompletedProgramStates(repo.getProgramsList());
        }
        this.executorService.shutdownNow();
        this.repo.setProgramsList(programStateList);
    }

    public boolean oneStepForAllWrapper() throws InterruptedException, MyException{
        List<ProgramState> programStateList = this.removeCompletedProgramStates(this.repo.getProgramsList());
        if (programStateList.size() > 0)
        {
            programStateList.get(0).getHeap().setContent(this.GarbageCollector());
            this.oneStepForAll(programStateList);
            programStateList = this.removeCompletedProgramStates(repo.getProgramsList());
        }
        if (programStateList.size() == 0) {
            this.executorService.shutdownNow();
            return false;
        }
        this.repo.setProgramsList(programStateList);
        return true;
    }

    private Map<Integer, IValue> GarbageCollector()
    {
        ArrayList<Integer> addresses = new ArrayList<Integer>();
        IHeap<Integer, IValue> heap= this.repo.getProgramsList().get(0).getHeap();
        List<Integer> addressFromSymTable = this.getAddressesFromSymTable();
        for (var v : addressFromSymTable)
        {
            if (! addresses.contains(v) && v != 0) {
                addresses.add(v);
                IValue iValue = heap.getValue(v);
                while (iValue instanceof ReferenceValue) {
                    ReferenceValue refValue = (ReferenceValue) iValue;
                    int addr = refValue.getAddress();
                    if (addr != 0) {
                        if (!addresses.contains(addr))
                            addresses.add(addr);
                        iValue =heap.getValue(refValue.getAddress());
                    }
                    else
                        break;
                    }
            }
        }
        return heap.getAll().entrySet().stream()
                .filter(e-> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void displayProgramState(ProgramState prg)
    {
        String toDisplay = prg.toString();
        System.out.println(toDisplay);
    }

    private List<Integer> getAddressesFromSymTable()
    {
        List<ProgramState> programStates = this.repo.getProgramsList();
        List<Integer> toReturn = new ArrayList<Integer>();
        programStates.forEach(prg ->
        {
            IDictionary<String, IValue> symTable = prg.getSymbolsTable();
            for (String key : symTable.getKeys())
            {
                IValue v = symTable.getValue(key);
                if (v instanceof ReferenceValue)
                {
                    ReferenceValue referenceValue = (ReferenceValue)v;
                    if ( ! toReturn.contains(referenceValue.getAddress()))
                        toReturn.add(referenceValue.getAddress());
                }
            }
        });
        return toReturn;
    }

    public IRepository getRepo() {
        return repo;
    }
}
