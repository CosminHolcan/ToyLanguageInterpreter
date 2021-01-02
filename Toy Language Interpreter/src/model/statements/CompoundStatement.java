package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IStack;
import model.adts.MyStack;
import model.programState.ProgramState;
import model.types.IType;

public class CompoundStatement implements IStatement{

    private IStatement firstStmt;
    private IStatement secondStmt;

    public CompoundStatement(IStatement st1, IStatement st2)
    {
        this.firstStmt = st1;
        this.secondStmt = st2;
    }

    public String toString() {
        return this.firstStmt.toString() + "; " + this.secondStmt.toString();
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IStack<IStatement> stack = prg.getExecStack();
        stack.push(this.secondStmt);
        stack.push(this.firstStmt);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        IDictionary<String, IType> dict1 = this.firstStmt.typeCheck(dict);
        IDictionary<String, IType> dict2 = this.secondStmt.typeCheck(dict1);
        return dict2;
    }

    public IStatement getFirstStmt() {
        return firstStmt;
    }

    public IStatement getSecondStmt() {
        return secondStmt;
    }
}
