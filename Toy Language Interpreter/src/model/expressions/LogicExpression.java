package model.expressions;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntegerType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntegerValue;

public class LogicExpression implements IExpression{

    private IExpression firstExpression;
    private IExpression secondExpression;
    private int operand;

    public LogicExpression(int operand, IExpression e1, IExpression e2 )
    {
        this.firstExpression = e1;
        this.secondExpression = e2;
        this.operand = operand;
    }

    public String toString()
    {
        if (this.operand == 1)
            return this.firstExpression.toString() + " AND "+this.secondExpression.toString();
        else if (this.operand == 2)
            return this.firstExpression.toString() + " OR "+this.secondExpression.toString();
        else
            return null;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> dict, IHeap<Integer, IValue> heap) throws MyException {
        IValue val1, val2;
        val1 = this.firstExpression.evaluate(dict, heap);
        val2 = this.secondExpression.evaluate(dict, heap);
        if (! val1.getType().equals(new BoolType()))
            throw new MyException("First operand is not a boolean");
        if(! val2.getType().equals(new BoolType()))
            throw new MyException("Second operand is not a boolean");
        BoolValue bv1 = (BoolValue)val1;
        BoolValue bv2 = (BoolValue) val2;
        boolean b1, b2;
        b1 = bv1.getValue();
        b2 = bv2.getValue();
        if (this.operand == 1)
            return new BoolValue(b1 &&b2);
        else if (this.operand == 2)
            return new BoolValue(b1 || b2);
        else throw new MyException("Invalid operand");
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> dict) throws MyException {
        IType type1, type2;
        type1 = this.firstExpression.typeCheck(dict);
        type2 = this.secondExpression.typeCheck(dict);
        if (! type1.equals(new BoolType()))
            throw new MyException("First expression is not boolean !");
        if (! type2.equals(new BoolType()))
            throw new MyException("Second expression is not boolean !");
        return new BoolType();
    }
}
