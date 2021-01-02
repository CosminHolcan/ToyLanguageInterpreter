package repository;

import model.programState.ProgramState;
import model.values.StringValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyRepository implements IRepository{

    private ArrayList<ProgramState> programStates;
    private String pathToFile;

    public MyRepository (ProgramState prg, String path)
    {
        this.programStates = new ArrayList<ProgramState>();
        this.programStates.add(prg);
        this.pathToFile = path;
    }


    @Override
    public List<ProgramState> getProgramsList() {
        return this.programStates;
    }

    @Override
    public void logProgramStateExecution(ProgramState prg) throws IOException {
        PrintWriter logFile= new PrintWriter(new BufferedWriter(new FileWriter(this.pathToFile, true)));
        logFile.println(prg.toFile());
        logFile.println("\n\n");
        logFile.close();
    }

    @Override
    public void setProgramsList(List<ProgramState> prgState) {
        this.programStates = new ArrayList<ProgramState>(prgState);
    }


}
