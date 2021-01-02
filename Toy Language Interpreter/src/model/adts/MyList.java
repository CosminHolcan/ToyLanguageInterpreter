package model.adts;

import model.MyException;

import java.util.ArrayList;

public class MyList<T> implements IList<T>{

    private ArrayList<T> elements;

    public MyList() {
        this.elements = new ArrayList<T>();
    }

    @Override
    public void add(T elem) {
        this.elements.add(elem);
    }


    @Override
    public T getOnPosition(int pos) throws MyException {
        if (pos > this.elements.size())
            throw new MyException("No element on this position");
        return this.elements.get(pos);
    }

    @Override
    public void remove(int pos) throws MyException {
        if (pos < this.elements.size())
            throw new MyException("No element on this position");
        this.elements.remove(pos);
    }

    @Override
    public int getSize() {
        return this.elements.size();
    }

    @Override
    public void clear() {
        this.elements.clear();
    }

    public String toString()
    {
        return this.elements.toString();
    }

    @Override
    public String toFile()
    {
        String toReturn = "";
        for (var elem : this.elements)
            toReturn += elem.toString() + "\n";
        return toReturn;
    }

}

