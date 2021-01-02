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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement{

    private IExpression expression;

    public OpenRFileStatement(IExpression exp)
    {
        this.expression = exp;
    }

    public String toString()
    {
        return "open (" +this.expression.toString() + " )";
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
        if (fileTable.existKey(val))
            throw new MyException("File already open");
        try {
            BufferedReader br = new BufferedReader(new FileReader(val.getValue()));
            fileTable.add(val, br);
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
