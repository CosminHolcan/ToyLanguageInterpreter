package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue{

    private String value;

    public StringValue(String s)
    {
        this.value = s;
    }

    public String getValue() {
        return value;
    }

    public String toString()
    {
        return this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue clone() {
        return new StringValue(this.value);
    }

    public boolean equals(Object another)
    {
        if (another instanceof StringValue)
        {
            StringValue anotherString = (StringValue) another;
            if (this.value == anotherString.getValue())
                return true;
            else
                return false;
        }
        else
            return false;
    }
}
