package model.statements;

import model.MyException;
import model.adts.IDictionary;
import model.adts.IHeap;
import model.expressions.IExpression;
import model.programState.ProgramState;
import model.types.IType;
import model.values.IValue;

public class AssignmentStatement implements IStatement{

    private String id;
    private IExpression expression;

    public AssignmentStatement(String id, IExpression expr)
    {
        this.id = id;
        this.expression = expr;
    }

    public String toString(){
        return this.id+" = "+ this.expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState prg) throws MyException {
        IDictionary<String, IValue> symbolsTable = prg.getSymbolsTable();
        IHeap<Integer, IValue> heap = prg.getHeap();
        if (symbolsTable.existKey(id))
        {
            IValue value = this.expression.evaluate(symbolsTable, heap);
            IType typeObj = symbolsTable.getValue(this.id).getType();
            IType typeExpr = value.getType();
            if (typeObj.equals(typeExpr))
            {
                symbolsTable.update(id, value);
            }
            else
                throw new MyException("Type of variable "+this.id+" and type of expression are different !");
        }
        else
            throw new MyException("The variable "+this.id+" not declared !");
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> dict) throws MyException {
        IType typeID = dict.getValue(this.id);
        IType typeExpression = this.expression.typeCheck(dict);
        if (typeID.equals(typeExpression))
            return dict;
        else
            throw new MyException("Impossible assignment : variable and expression have different types !");
    }
}
