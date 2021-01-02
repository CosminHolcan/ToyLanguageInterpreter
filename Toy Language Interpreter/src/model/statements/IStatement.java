package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.programState.ProgramState;
import model.types.IType;

public interface IStatement {

    ProgramState execute(ProgramState prg) throws MyException;
    IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException;
}
