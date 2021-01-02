package model.values;

import model.types.IType;
import model.types.IntegerType;

public class IntegerValue implements IValue{

    private int value;
    public IntegerValue(int v)
    {
        this.value = v;
    }

    public int getValue()
    {
        return this.value;
    }

    public String toString()
    {
        return this.value+" ";
    }


    @Override
    public IType getType() {
        return new IntegerType();
    }

    @Override
    public IValue clone() {
        return new IntegerValue(this.value);
    }

    public boolean equals(Object another)
    {
        if (another instanceof IntegerValue)
        {
            IntegerValue anotherInteger = (IntegerValue) another;
            if (this.value == anotherInteger.getValue())
                return true;
            else
                return false;
        }
        else
            return false;
    }
}
