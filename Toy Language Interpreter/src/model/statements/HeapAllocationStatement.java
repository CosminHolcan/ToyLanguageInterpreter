package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.expressions.IExpression;
import model.programState.ProgramState;
import model.types.IType;
import model.types.ReferenceType;
import model.values.IValue;
import model.values.ReferenceValue;

public class HeapAllocationStatement implements IStatement{

    private String variableName;
    private IExpression expression;

    public HeapAllocationStatement(String variable, IExpression expr)
    {
        this.variableName = variable;
        this.expression = expr;
    }

    public String toString()
    {
        return "new ( "+this.variableName + ", "+this.expression.toString()+" )";
    }


    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IDictionary<String, IValue> symbolsTable = prg.getSymbolsTable();
        IHeap<Integer, IValue>heap = prg.getHeap();
        if (! symbolsTable.existKey(this.variableName))
            throw new MyException("Variable not declared !");
        IValue valueIVariable = symbolsTable.getValue(this.variableName);
        if (! (valueIVariable.getType() instanceof ReferenceType))
            throw new MyException("Variable is not a reference !");
        ReferenceValue referenceValue = (ReferenceValue)valueIVariable;
        IValue valueExpression = this.expression.evaluate(symbolsTable, heap);
        if (! valueExpression.getType().equals(referenceValue.getLocationType()))
            throw new MyException("Type of referenced value and of expression don't match !");
        int address = heap.getFirstFree();
        heap.add(address, valueExpression);
        ReferenceValue ref = new ReferenceValue(address, referenceValue.getLocationType());
        symbolsTable.update(this.variableName, ref);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        IType typeVar = dict.getValue(this.variableName);
        if (! (typeVar instanceof ReferenceType))
            throw new MyException("Variable is not a reference !");
        IType typeExpression = this.expression.typeCheck(dict);
        if (! typeVar.equals(new ReferenceType(typeExpression)))
            throw new MyException("Type of referenced value and of expression don't match !");
        return dict;
    }
}
