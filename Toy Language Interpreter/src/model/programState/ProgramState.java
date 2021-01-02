package model.programState;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.adts.IList;
import model.adts.IStack;
import model.statements.IStatement;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;

public class ProgramState {

    private IStack<IStatement> execStack;
    private IDictionary<String, IValue> symbolsTable;
    private IList<IValue> output;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, IValue> heap;
    private static int globalID = 0;
    private final int myID;

    public ProgramState(IStack<IStatement> stack, IDictionary<String, IValue> dict, IList<IValue> otp,
                        IDictionary<StringValue, BufferedReader> ft, IHeap<Integer, IValue> heap)
    {
        this.execStack = stack;
        this.symbolsTable = dict;
        this.output = otp;
        this.fileTable = ft;
        this.heap = heap;
        this.myID = getNextID();
    }

    public IDictionary<String, IValue> getSymbolsTable() {
        return symbolsTable;
    }

    public IList<IValue> getOutput() {
        return output;
    }

    public IStack<IStatement> getExecStack() {
        return execStack;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap<Integer, IValue> getHeap() {
        return heap;
    }

    public synchronized static int getNextID()
    {
        return globalID++;
    }


    public String toString()
    {
        String toReturn = "ID : "+this.myID +"\n";
        toReturn += "Stack : "+this.execStack.toString() +"\n";
        toReturn += "Symbols table : "+this.symbolsTable.toString()+"\n";
        toReturn += "Output : "+this.output+"\n";
        toReturn += "File table : "+this.fileTable.toString()+"\n";
        toReturn += "Heap table : "+this.heap.toString()+"\n";
        return toReturn;
    }

    public String toFile()
    {
        String toReturn = "ID : \n\n"+this.myID +"\n\n";
        toReturn += "Exe stack : \n\n";
        toReturn += this.execStack.toFile()+"\n\n";
        toReturn +="Symbols table : \n\n";
        toReturn += this.symbolsTable.toFileWithValues()+"\n\n";
        toReturn += "Output : \n\n";
        toReturn += this.output.toFile()+"\n\n";
        toReturn += "File table : \n\n";
        toReturn += this.fileTable.toFileJustKeys()+"\n\n";
        toReturn += "Heap table : \n\n";
        toReturn += this.heap.toFile() + "\n\n";
        return toReturn;
    }


    public Boolean isNotCompleted()
    {
        return !this.execStack.isEmpty();
    }

    public ProgramState oneStep() throws MyException
    {
        IStack<IStatement> stack = this.getExecStack();
        if (stack.isEmpty())
            throw new MyException("Stack is empty !");
        IStatement statement = stack.pop();
        return statement.execute(this);
    }
}
