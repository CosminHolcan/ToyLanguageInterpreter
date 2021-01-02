package model.expressions;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.types.IType;
import model.values.IValue;

public class ValueExpression implements  IExpression{

    private IValue value;
    public ValueExpression(IValue v)
    {
        this.value = v;
    }

    public String toString()
    {
        return this.value.toString();
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> dict, IHeap<Integer, IValue> heap) throws MyException {
        return this.value;
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> dict) throws MyException {
        return this.value.getType();
    }
}
