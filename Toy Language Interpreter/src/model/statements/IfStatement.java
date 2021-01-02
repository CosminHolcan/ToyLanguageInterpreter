package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.adts.IStack;
import model.expressions.IExpression;
import model.programState.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStatement implements IStatement{

    private IExpression expression;
    private IStatement ifStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression exp, IStatement st1, IStatement st2)
    {
        this.expression = exp;
        this.ifStatement = st1;
        this.elseStatement = st2;
    }

    public String toString()
    {
        return "If ( "+this.expression.toString()+" ) Then "+this.ifStatement.toString()+" Else "+this.elseStatement.toString();
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IStack<IStatement> stack = prg.getExecStack();
        IHeap<Integer, IValue> heap = prg.getHeap();
        IDictionary<String, IValue> symbolsTable = prg.getSymbolsTable();
        IValue condition = this.expression.evaluate(symbolsTable, heap);
        if (! condition.getType().equals( new BoolType()))
            throw new MyException("Condition can't be evaluated to bool !");
        BoolValue b = (BoolValue) condition;
        if (b.getValue())
            stack.push(this.ifStatement);
        else
            stack.push(this.elseStatement);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        IType typeExpression = this.expression.typeCheck(dict);
        if (! typeExpression.equals(new BoolType()))
            throw new MyException("The condition doesn't have the boolean type !");
        this.ifStatement.typeCheck(dict.clone());
        this.elseStatement.typeCheck(dict.clone());
        return dict;
    }
}

