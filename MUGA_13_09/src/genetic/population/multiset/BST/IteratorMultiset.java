/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population.multiset.BST;

import genetic.population.multiset.Pair;
import java.util.Iterator;

/**
 *
 * @author ZULU
 */
public class IteratorMultiset<T> implements Iterator< Pair<T>> {

    IteratorNode<T> iterator;

    public IteratorMultiset(MNode root) {
        iterator = new IteratorNode<>(root);
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Pair<T> next() {
        MNode<T> actual = iterator.next();
        return new Pair(actual.element, actual.copies);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}