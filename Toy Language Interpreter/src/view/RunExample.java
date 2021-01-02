package view;

import controller.Controller;
import model.MyException;
import model.adts.*;
import model.programState.ProgramState;
import model.statements.IStatement;
import model.types.IType;
import model.values.IValue;
import model.values.StringValue;
import repository.IRepository;
import repository.MyRepository;

import java.io.BufferedReader;

public class RunExample extends Command{

    private Controller controller;
    private IStatement statement;
    private String path;
    public RunExample(String key, String description, IStatement stmt, String path){
        super(key, description);
        this.statement = stmt;
        this.path = path;
    }

    private void createController()
    {
        IStack<IStatement> stack = new MyStack<IStatement>();
        IDictionary<String , IValue> symbolsTable = new MyDictionary<String, IValue>();
        IList<IValue> output = new MyList<IValue>();
        IDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer, IValue> heap = new MyHeap<Integer, IValue>();
        stack.push(this.statement);
        ProgramState prg = new ProgramState(stack, symbolsTable, output, fileTable, heap);
        IRepository repo = new MyRepository(prg, this.path);
        this.controller = new Controller(repo);
    }

    @Override
    public void execute() {
        try
        {
            IDictionary<String, IType> dict = new MyDictionary<>();
            this.statement.typeCheck(dict);
        }
        catch (MyException e)
        {
            System.out.println("Type checker error : "+e.getMessage());
            return;
        }
        try{
            this.createController();
            controller.allStep(); }
        catch (MyException e)
        {
            System.out.println("Error : "+e.getMessage());
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
