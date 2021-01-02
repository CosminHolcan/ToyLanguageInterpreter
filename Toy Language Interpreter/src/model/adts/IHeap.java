package model.adts;

import model.MyException;
import model.values.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IHeap<K, V> {

    void add(K address, V value) throws MyException;
    V getValue(K address) throws MyException;
    void remove(K address) throws MyException;
    void update(K address, V newValue) throws MyException;
    void clear();
    String toFile();
    Set<K> getUsedAddresses();
    int getFirstFree();
    boolean existAddress(K address);
    void setContent(Map<K, V> newContent);
    Map<K, V> getAll();
}
