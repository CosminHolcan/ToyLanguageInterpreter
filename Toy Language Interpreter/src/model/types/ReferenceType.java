package model.types;

import model.values.IValue;
import model.values.ReferenceValue;

public class ReferenceType implements IType{

    private IType innerType;
    public ReferenceType(IType inner)
    {
        this.innerType = inner;
    }

    public IType getInnerType() {
        return innerType;
    }

    public boolean equals(Object another)
    {
        if (another instanceof ReferenceType)
            return this.innerType.equals(((ReferenceType) another).getInnerType());
        else
            return false;
    }

    public String toString()
    {
        return "ref ( "+this.innerType.toString()+" )";
    }

    @Override
    public IValue defaultValue() {
        return new ReferenceValue(0, this.innerType);
    }

    @Override
    public IType clone() {
        return new ReferenceType(this.innerType.clone());
    }
}
