package model.expressions;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.types.IType;
import model.values.IValue;

public interface IExpression {

    IValue evaluate(IDictionary<String, IValue> dict, IHeap<Integer, IValue> heap) throws MyException;
    IType typeCheck (IDictionary<String, IType> dict) throws MyException;
}
