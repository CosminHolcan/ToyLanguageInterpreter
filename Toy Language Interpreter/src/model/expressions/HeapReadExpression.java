package model.expressions;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.types.IType;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapReadExpression implements IExpression{

    private IExpression expression;

    public HeapReadExpression(IExpression expr)
    {
        this.expression = expr;
    }

    public String toString()
    {
        return "read heap ( "+this.expression.toString() + " )";
    }


    @Override
    public IValue evaluate(IDictionary<String, IValue> dict, IHeap<Integer, IValue> heap) throws MyException {
        IValue valueExpression = this.expression.evaluate(dict, heap);
        if (! (valueExpression.getType() instanceof ReferenceType))
            throw new MyException("Expression is not a reference !");
        ReferenceValue referenceValue = (ReferenceValue) valueExpression;
        int address = referenceValue.getAddress();
        try {
            return heap.getValue(address);
        }
        catch (MyException e)
        {
            throw new MyException(e.getMessage());
        }
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> dict) throws MyException {
        IType typeToCheck = this.expression.typeCheck(dict);
        if (typeToCheck instanceof ReferenceType)
        {
            ReferenceType refType = (ReferenceType) typeToCheck;
            return refType.getInnerType();
        }
        else
            throw new MyException("The expression to read from heap is not a reference type !");
    }
}
