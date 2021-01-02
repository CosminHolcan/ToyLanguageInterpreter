package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.expressions.IExpression;
import model.programState.ProgramState;
import model.types.IType;
import model.types.IntegerType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntegerValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{

    private IExpression expression;
    private String variable_name;

    public ReadFileStatement(IExpression exp, String name)
    {
        this.expression = exp;
        this.variable_name = name;
    }

    public String toString()
    {
        return "read ( "+this.expression.toString() + ", "+this.variable_name + " )";
    }


    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IDictionary<String, IValue> symbolsTable = prg.getSymbolsTable();
        IHeap<Integer, IValue> heap = prg.getHeap();
        IValue v = this.expression.evaluate(symbolsTable, heap);
        StringValue val = (StringValue) v;
        if (! (symbolsTable.existKey(this.variable_name)))
            throw new MyException("Nonexistent variable !");
        if (! symbolsTable.getValue(this.variable_name).getType().equals(new IntegerType()))
            throw new MyException("Variable type not integer !");
        IDictionary<StringValue, BufferedReader> fileTable = prg.getFileTable();
        if (! v.getType().equals(new StringType()))
            throw new MyException("Type of file name is not a string !");
        if (! fileTable.existKey(val))
            throw new MyException("File not opened");
        BufferedReader br = fileTable.getValue(val);
        try {
            String number_s = br.readLine();
            IntegerValue integer;
            if (number_s == null)
                integer = new IntegerValue(0);
            else
                integer = new IntegerValue(Integer.parseInt(number_s));
            symbolsTable.update(this.variable_name, integer);
            return null;
        } catch (IOException | NumberFormatException e) {
            throw new MyException(e.getMessage());
        }

    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        if (! this.expression.typeCheck(dict).equals(new StringType()))
            throw new MyException("Type of file name is not a string !");
        IType typeVar = dict.getValue(this.variable_name);
        if (! typeVar.equals(new IntegerType()))
            throw new MyException("Variable type not integer !");
        return dict;
    }
}
