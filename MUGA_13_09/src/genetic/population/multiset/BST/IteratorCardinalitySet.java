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
public class IteratorCardinalitySet implements Iterator<Integer> {

    IteratorNode iterator;

    public IteratorCardinalitySet(MNode root) {
        iterator = new IteratorNode(root);
    }
    

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Integer next() {
        return iterator.next().copies;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
