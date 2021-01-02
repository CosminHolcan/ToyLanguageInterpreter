package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.programState.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.types.IntegerType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntegerValue;

public class DeclarativeStatement implements  IStatement{

    private String name;
    private IType type;

    public DeclarativeStatement(String name, IType type)
    {
        this.name = name;
        this.type = type;
    }

    public String toString()
    {
        return this.type.toString()+" "+this.name;
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IDictionary<String, IValue> symbolsTable = prg.getSymbolsTable();
        if (symbolsTable.existKey(this.name))
            throw new MyException("This value is already declared !");
        symbolsTable.add(this.name, this.type.defaultValue());
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        dict.add(this.name, this.type);
        return dict;
    }
}
