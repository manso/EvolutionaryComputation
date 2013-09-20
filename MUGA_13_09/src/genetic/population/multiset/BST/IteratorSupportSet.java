/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population.multiset.BST;

import java.util.Iterator;

/**
 *
 * @author ZULU
 */
public class IteratorSupportSet<T> implements Iterator<T> {
    IteratorNode<T> iterator;
    public IteratorSupportSet(MNode root) {
        iterator = new IteratorNode<>(root);
    }
    

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T next() {
        return iterator.next().element;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
