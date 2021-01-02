package model.types;

import model.values.IValue;
import model.values.IntegerValue;

public class IntegerType implements IType{

    public boolean equals(Object another)
    {
        if(another instanceof IntegerType)
            return true;
        else
            return false;
    }

    public String toString()
    {
        return "integer";
    }

    @Override
    public IValue defaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public IType clone() {
        return new IntegerType();
    }
}
