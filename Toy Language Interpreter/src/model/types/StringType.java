package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{

    public boolean equals(Object another)
    {
        if (another instanceof StringType)
            return true;
        else
            return false;
    }

    public String toString()
    {
        return "string";
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public IType clone() {
        return new StringType();
    }
}
