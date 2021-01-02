package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.programState.ProgramState;
import model.types.IType;

public class NOPStatement implements IStatement{

    public NOPStatement(){}

    public String toString()
    {
        return "NOP";
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        return dict;
    }
}
