package model.adts;

import model.MyException;
import model.types.IType;
import model.values.IValue;
import model.values.StringValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MyDictionary<K, V> implements IDictionary<K, V>{

    private HashMap<K,V> dictionary;
    public MyDictionary() {
        this.dictionary = new HashMap<K,V>();
    }

    @Override
    public void add(K key, V value) throws MyException{
        if (this.dictionary.containsKey(key))
            throw new MyException("Key already in dictionary");
        this.dictionary.put(key, value);
    }

    @Override
    public V getValue(K key) throws MyException{
        if (!this.dictionary.containsKey(key))
            throw new MyException("Key not found");
        return this.dictionary.get(key);
    }

    @Override
    public void remove(K key) throws MyException{
        if (!this.dictionary.containsKey(key))
            throw new MyException("Key not found");
        this.dictionary.remove(key);
    }

    @Override
    public Set<K> getKeys() {
        return this.dictionary.keySet();
    }

    @Override
    public void update(K key, V value) throws MyException {
        if (!this.dictionary.containsKey(key))
            throw new MyException("Key not found");
        this.dictionary.replace(key, value);
    }

    @Override
    public boolean existKey(K key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public void clear() {
        this.dictionary.clear();
    }

    public String toString()
    {
        return this.dictionary.toString();
    }

    @Override
    public String toFileWithValues()
    {
        String toReturn = "";
        for (var key : this.dictionary.keySet())
        {
            toReturn += key.toString() + " --> " + this.dictionary.get(key) + "\n";
        }
        return toReturn;
    }

    @Override
    public String toFileJustKeys() {
        String toReturn = "";
        Set<K> s = this.dictionary.keySet();
        for (var fl : s)
            toReturn += fl.toString();
        return toReturn;
    }

    @Override
    public ArrayList<V> getValues() {
        ArrayList<V> toReturn = new ArrayList<V>();
        for (var key : this.dictionary.keySet())
            toReturn.add(this.dictionary.get(key));
        return toReturn;
    }

    @Override
    public IDictionary<K, V> clone() {
        IDictionary<K, V> toReturn = new MyDictionary<K, V>();
        for (var k : this.dictionary.keySet())
        {
            V v = this.dictionary.get(k);
            if (v instanceof IValue) {
                IValue clone = ((IValue) v).clone();
                String s = (String) k;
                String newKey = new String(s);
                toReturn.add((K) newKey, (V) clone);
            }
            else if (v instanceof IType)
            {
                IType clone = ((IType) v).clone();
                String s = (String) k;
                String newKey = new String(s);
                toReturn.add((K) newKey, (V) clone);
            }
        }
        return toReturn;
    }
}
