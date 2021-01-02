package model.adts;


import model.MyException;
import model.values.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<K, V> implements IHeap<K, V>{

    private HashMap<K, V> elements;
    private int nextFree;

    public MyHeap()
    {
        this.elements = new HashMap<K, V>();
        this.nextFree = 1;
    }

    @Override
    public void add(K address, V value) throws MyException
    {
        this.elements.put(address, value);
        Set<K> keys = this.elements.keySet();
        int newFree = this.nextFree + 1;
        while (keys.contains(newFree))
            newFree ++;
        this.nextFree = newFree;
    }

    @Override
    public V getValue(K address) throws MyException {
        if (!this.elements.containsKey(address))
            throw new MyException("Address not used in heap !");
        return this.elements.get(address);
    }

    @Override
    public void remove(K address) throws MyException {
        if (!this.elements.containsKey(address))
            throw new MyException("Address not used in heap !");
        if ((int)address < this.nextFree)
            this.nextFree = (int)address;
    }


    @Override
    public void update(K address, V newValue) throws MyException{
        if (!this.elements.containsKey(address))
            throw new MyException("Address not used in heap !");
        this.elements.replace(address, newValue);
    }

    @Override
    public void clear()
    {
        this.elements.clear();
    }

    public String toString()
    {
        String toReturn = "{";
        boolean first = true;
        for (var key : this.elements.keySet())
        {
            if (first)
            {
                first = false;
                toReturn += key.toString() + " --> " + this.elements.get(key);
            }
            else
                toReturn += ", "+key.toString() + " --> " + this.elements.get(key);
        }
        toReturn += " }";
        return toReturn;
    }

    @Override
    public String toFile()
    {
        String toReturn = "";
        for (var key : this.elements.keySet())
        {
            toReturn += key.toString() + " --> " + this.elements.get(key) + "\n";
        }
        return toReturn;
    }

    @Override
    public Set<K> getUsedAddresses() {
        return this.elements.keySet();
    }

    @Override
    public int getFirstFree() {
        return this.nextFree;
    }

    @Override
    public boolean existAddress(K address) {
        return this.elements.containsKey(address);
    }

    @Override
    public void setContent(Map<K, V> newContent) {
        this.elements = new HashMap<K, V>(newContent);
    }

    @Override
    public Map<K, V> getAll() {
        return this.elements;
    }
}
