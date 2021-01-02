package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue{

    private boolean value;
    public BoolValue(boolean v)
    {
        this.value = v;
    }

    public boolean getValue()
    {
        return this.value;
    }

    public String toString()
    {
        return this.value+" ";
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue clone() {
        return new BoolValue(this.value);
    }

    public boolean equals(Object another)
    {
        if (another instanceof BoolValue)
        {
            BoolValue anotherBool = (BoolValue) another;
            if (this.value == anotherBool.getValue())
                return true;
            else
                return false;
        }
        else
            return false;

    }
}
