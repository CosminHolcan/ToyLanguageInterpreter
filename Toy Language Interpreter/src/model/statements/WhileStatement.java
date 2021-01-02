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

public class WhileStatement implements IStatement{

    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expr, IStatement stmt)
    {
        this.expression = expr;
        this.statement = stmt;
    }

    public String toString()
    {
        return "while ("+this.expression.toString() + " ) { "+this.statement.toString() + " }";
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IStack<IStatement> stack = prg.getExecStack();
        IHeap<Integer, IValue> heap = prg.getHeap();
        IDictionary<String, IValue> symbolsTable = prg.getSymbolsTable();
        IValue condition = this.expression.evaluate(symbolsTable, heap);
        if (! condition.getType().equals(new BoolType()))
            throw new MyException("Condition can't be evaluated to bool !");
        BoolValue bool = (BoolValue) condition;
        if (bool.getValue())
        {
            stack.push(this);
            stack.push(this.statement);
        }
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        IType typeExpression = this.expression.typeCheck(dict);
        if (! typeExpression.equals(new BoolType()))
            throw new MyException("The condition doesn't have the boolean type !");
        this.statement.typeCheck(dict.clone());
        return dict;
    }
}
