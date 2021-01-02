package model.adts;

import model.MyException;
import model.statements.CompoundStatement;
import model.statements.IStatement;

import java.io.OutputStream;
import java.util.Stack;

public class MyStack<T> implements IStack<T> {

    private Stack<T> stack;
    public MyStack() {
        this.stack = new Stack<T>();
    }


    @Override
    public T top() throws MyException{
        if(this.stack.isEmpty())
            throw new MyException("Stack is empty !");
        return this.stack.peek();
    }

    @Override
    public T pop() throws MyException {
        if (this.stack.isEmpty())
            throw new MyException("Stack is empty !");
        return this.stack.pop();
    }

    @Override
    public void push(T elem) {
        this.stack.push(elem);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public void clear() {
        this.stack.clear();
    }

    public String toString()
    {
        String toReturn = "[ ";
        boolean first = true;
        Stack<T> aux = new Stack<T>();
        while (!this.stack.isEmpty())
        {
            if (first)
            {
                toReturn += this.stack.peek().toString();
                first = false;
            }
            else {
                toReturn +=" | "+ this.stack.peek().toString();
            }
            aux.push(this.stack.pop());
        }
        toReturn += " ]";
        while (!aux.isEmpty())
        {
            this.stack.push(aux.pop());
        }
        return toReturn;
    }

    @Override
    public String toFile()
    {
        String toReturn = "";
        Stack<T> aux = new Stack<T>();
        while (!this.stack.isEmpty())
        {
            toReturn += inOrder((IStatement)this.stack.peek());
            aux.push(this.stack.pop());
        }
        while (!aux.isEmpty())
        {
            this.stack.push(aux.pop());
        }
        return toReturn;

    }

    private String inOrder(IStatement statement)
    {
        if (! (statement instanceof CompoundStatement)) {
            return statement.toString() + "\n";
        }
        else {
            CompoundStatement compoundStatement = (CompoundStatement) statement;
            String returnLeft = inOrder(compoundStatement.getFirstStmt());
            String returnRight = inOrder(compoundStatement.getSecondStmt());
            return returnLeft + returnRight;
        }
    }
}
