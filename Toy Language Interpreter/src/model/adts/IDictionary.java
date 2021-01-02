package model.adts;

import model.MyException;

import java.util.ArrayList;
import java.util.Set;

public interface IDictionary<K, V> {

    void add(K key, V value) throws MyException;
    V getValue(K key) throws MyException;
    void remove(K key) throws MyException;
    Set<K> getKeys();
    void update(K key, V value) throws MyException;
    boolean existKey(K key);
    void clear();
    String toFileWithValues();
    String toFileJustKeys();
    ArrayList<V> getValues();
    IDictionary<K, V> clone();
}
