package model.adts;

import model.MyException;

import java.util.Iterator;

public interface IList<T> {

    void add(T elem);
    T getOnPosition(int pos) throws MyException;
    void remove(int pos) throws MyException;
    int getSize();
    void clear();
    String toFile();
}
