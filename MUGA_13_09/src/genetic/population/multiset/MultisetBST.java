/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population.multiset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author manso
 */
public class MultisetBST<T> {

    ArrayList<T> data = new ArrayList<T>();
    ArrayList<Integer> copies = new ArrayList<Integer>();
    Comparator<T> comparator;

    public MultisetBST() {
        comparator = null;
    }

    public MultisetBST(Comparator<T> comparator) {
        this.comparator = comparator;
    }
//-----------------------------------------------------------------
//-----------------------------------------------------------------
//-----------------------------------------------------------------
//-----------------------------------------------------------------
//-----------------------------------------------------------------
//-----------------------------------------------------------------
    /**
     * Iterator to support set
     * @return support set iterator
     */
    public Iterator<T> iteratorSupportSet() {
        return data.iterator();
    }

    public Iterator<Integer> iteratorCardinalitySet() {
        return copies.iterator();
    }

    public Iterator<Pair<T>> iteratorMultiSet() {
        return new multiSetIterator();
    }

     ////////////////////////////////////////////////////////////////

    private class multiSetIterator implements Iterator< Pair<T>> {
        int index = 0;
        @Override
        public boolean hasNext() {
            return index < getSupportSize();
        }

        @Override
        public Pair<T> next() {
            //increment
            index++;
            //return the previous
            return new Pair(data.get(index-1), copies.get(index-1));
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////


//-----------------------------------------------------------------
//-----------------------------------------------------------------
//-----------------------------------------------------------------
//-----------------------------------------------------------------
//-----------------------------------------------------------------

    public void update(T elem, int numCopies) {
        int index = data.indexOf(elem);
        copies.set(index, numCopies);
    }

    public void clear() {
        data.clear();
        copies.clear();
    }

    public int getSupportSize() {
        return data.size();
    }

    public int getCardinalitySize() {
        int size = 0;
        for (int i : copies) {
            size += i;
        }
        return size;
    }

    public boolean contains(T elem) {
        return data.contains(elem);
    }

    public void add(T elem, int cop) {
     int index = data.indexOf(elem);
     if( index < 0){
        data.add(elem);
        copies.add(cop);
        }
     else{
         copies.set(index, copies.get(index) + cop);
     }
    }
    //---------------------------------------------

    public Pair<T> removePairIndex(int index) {
        Pair<T> p = getPairIndex(index);
        data.remove(index);
        copies.remove(index);
        return p;
    }

    public Pair<T> removeAll(T elem) {
        int index = data.indexOf(elem);
        return removePairIndex(index);
    }
    //---------------------------------------------------
    //---------------------------------------------------

    private int getDataIndex(int copiesIndex) {
        int i = 0;
        for (int j = 0; j < copies.size(); j++) {
            i += copies.get(j);
            if (copiesIndex < i) {
                return j;
            }
        }
        return -1;
    }

    private int getCopiesIndex(int dataIndex) {
        if( dataIndex < 0) return dataIndex;
        int num = 0;
        for (int j = 0; j < dataIndex; j++) {
            num += copies.get(j);
        }
        return num;
    }
    //---------------------------------------------------
    //---------------------------------------------------
    //---------------------------------------------------

    public T remove(T elem, int numCopies) {
        int index = data.indexOf(elem);
        int newcopies = copies.get(index) - numCopies;
        if (newcopies > 0) {
            copies.set(index, newcopies);
        } else {
            removePairIndex(index);
        }
        return elem;
    }

    public T remove(T elem) {
        return remove(elem, 1);
    }

    public T removeIndex(int index) {
        return remove(data.get(getDataIndex(index)));
    }

    //--------------------------------------------
    public T getIndex(int index) {
        return data.get(getDataIndex(index));
    }

    public Pair<T> getPairIndex(int index) {
        return new Pair<T>(data.get(index), copies.get(index));
    }

    public int indexOfPair(T elem) {
        return data.indexOf(elem);
    }

    public int indexOf(T elem) {
        return getCopiesIndex( data.indexOf(elem));
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            str.append("<").append(data.get(i)).append(" , ").append(copies.get(i)).append( ">");
        }
        return str.toString();
    }
}
