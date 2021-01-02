package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.expressions.IExpression;
import model.programState.ProgramState;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement{

    private IExpression expression;

    public CloseRFileStatement(IExpression exp)
    {
        this.expression = exp;
    }

    public String toString()
    {
        return "close ( "+this.expression.toString()+ " )";
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IDictionary<String, IValue> symbolsTable = prg.getSymbolsTable();
        IHeap<Integer, IValue> heap = prg.getHeap();
        IValue v = this.expression.evaluate(symbolsTable, heap);
        if (! v.getType().equals(new StringType()))
            throw new MyException("Type of file name is not a string !");
        StringValue val = (StringValue) v;
        IDictionary<StringValue, BufferedReader> fileTable = prg.getFileTable();
        if (! fileTable.existKey(val))
            throw new MyException("File not opened");
        try {
            BufferedReader bf = fileTable.getValue(val);
            bf.close();
            fileTable.remove(val);
            return null;
        } catch (IOException e1) {
            throw new MyException(e1.getMessage());
        }
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        if (! this.expression.typeCheck(dict).equals(new StringType()))
            throw new MyException("Type of file name is not a string !");
        return dict;
    }
}
