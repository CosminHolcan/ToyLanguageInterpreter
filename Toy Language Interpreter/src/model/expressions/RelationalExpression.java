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

public class RelationalExpression implements IExpression{

    private IExpression firstExpression;
    private IExpression secondExpression;
    private int operand;

    public RelationalExpression(IExpression first, IExpression second, int operand)
    {
        this.firstExpression = first;
        this.secondExpression = second;
        this.operand = operand;
    }

    public String toString()
    {
        if (this.operand == 1)
            return this.firstExpression.toString() + " < "+this.secondExpression.toString();
        else if (this.operand == 2)
            return this.firstExpression.toString() + " <= "+this.secondExpression.toString();
        else if (this.operand == 3)
            return this.firstExpression.toString() + " == "+this.secondExpression.toString();
        else if (this.operand == 4)
            return this.firstExpression.toString() + " != "+this.secondExpression.toString();
        else if (this.operand == 5)
            return this.firstExpression.toString() + " >= "+this.secondExpression.toString();
        else if (this.operand == 6)
            return this.firstExpression.toString() + " > "+this.secondExpression.toString();
        else
            return null;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> dict, IHeap<Integer, IValue> heap) throws MyException {
        IValue val1, val2;
        val1 = this.firstExpression.evaluate(dict, heap);
        val2 = this.secondExpression.evaluate(dict, heap);
        if (! val1.getType().equals(new IntegerType()))
            throw new MyException("First operand is not an integer");
        if(! val2.getType().equals(new IntegerType()))
            throw new MyException("Second operand is not an integer");
        IntegerValue int1 = (IntegerValue)val1;
        IntegerValue int2 = (IntegerValue)val2;
        int number1, number2;
        number1 = int1.getValue();
        number2 = int2.getValue();
        if (this.operand == 1)
            return new BoolValue(number1 < number2);
        else if (this.operand == 2)
            return new BoolValue(number1 <= number2);
        else if (this.operand == 3)
            return new BoolValue(number1 == number2);
        else if (this.operand == 4)
            return new BoolValue(number1 != number2);
        else if (this.operand == 5)
            return new BoolValue(number1 >= number2);
        else if (this.operand == 6)
            return new BoolValue(number1 > number2);
        else throw new MyException("Invalid operand");
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> dict) throws MyException {
        IType type1, type2;
        type1 = this.firstExpression.typeCheck(dict);
        type2 = this.secondExpression.typeCheck(dict);
        if (! type1.equals(new IntegerType()))
            throw new MyException("First expression is not an integer !");
        if (! type2.equals(new IntegerType()))
            throw new MyException("Second operator is not an integer !");
        return new BoolType();
    }
}
