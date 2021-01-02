package model.expressions;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.types.IType;
import model.values.IValue;

public class VariableExpression implements IExpression{

    private String id;

    public VariableExpression(String id)
    {
        this.id = id;
    }

    public String toString()
    {
        return this.id;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> dict, IHeap<Integer, IValue> heap) throws MyException {
        try
        {
            return dict.getValue(this.id);
        }
        catch (MyException e)
        {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> dict) throws MyException {
        return dict.getValue(this.id);
    }
}
