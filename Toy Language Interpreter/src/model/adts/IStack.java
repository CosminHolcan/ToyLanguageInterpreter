package model.adts;

import model.MyException;

public interface IStack<T> {

    T top() throws MyException;
    T pop() throws MyException;
    void push(T elem) ;
    boolean isEmpty();
    void clear();
    String toFile();
}
