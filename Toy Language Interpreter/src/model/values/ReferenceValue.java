package model.values;

import model.types.IType;
import model.types.ReferenceType;

public class ReferenceValue implements IValue{

    private int address;
    private IType locationType;

    public ReferenceValue(int address, IType location)
    {
        this.address = address;
        this.locationType = location;
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return this.locationType;
    }

    public String toString()
    {
        return "( "+this.address+", "+this.locationType.toString()+" )";
    }

    @Override
    public IType getType() {
        return new ReferenceType(this.locationType);
    }

    @Override
    public IValue clone() {
        return new ReferenceValue(this.address, this.getLocationType());
    }


    public boolean equals(Object another)
    {
        if (another instanceof ReferenceValue)
        {
            ReferenceValue anotherReference = (ReferenceValue) another;
            return this.address == anotherReference.getAddress() && this.locationType.equals(anotherReference.getLocationType());
        }
        else
            return false;
    }
}
