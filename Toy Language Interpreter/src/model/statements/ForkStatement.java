package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.adts.IStack;
import model.adts.MyStack;
import model.programState.ProgramState;
import model.types.IType;
import model.values.IValue;

public class ForkStatement implements IStatement{

    private IStatement statement;

    public ForkStatement(IStatement stmt)
    {
        this.statement = stmt;
    }

    public String toString()
    {
        return "fork ("+this.statement.toString()+" )";
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IDictionary<String, IValue> symbolsTable = prg.getSymbolsTable();
        IDictionary<String, IValue> symbolsTableClone = symbolsTable.clone();
        IStack<IStatement> newStack = new MyStack<IStatement>();
        newStack.push(this.statement);
        return new ProgramState(newStack, symbolsTableClone, prg.getOutput(), prg.getFileTable(), prg.getHeap());
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        this.statement.typeCheck(dict.clone());
        return dict;
    }
}
