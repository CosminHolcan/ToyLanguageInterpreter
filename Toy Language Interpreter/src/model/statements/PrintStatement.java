package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.adts.IList;
import model.adts.IStack;
import model.expressions.IExpression;
import model.programState.ProgramState;
import model.types.IType;
import model.values.IValue;

public class PrintStatement implements IStatement{

    private IExpression expression;

    public PrintStatement(IExpression expr)
    {
        this.expression = expr;
    }

    public String toString(){
        return "print(" +this.expression.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IList<IValue> output = prg.getOutput();
        IDictionary<String, IValue> dict = prg.getSymbolsTable();
        IHeap<Integer, IValue> heap = prg.getHeap();
        output.add(this.expression.evaluate(dict, heap));
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        this.expression.typeCheck(dict);
        return dict;
    }
}
