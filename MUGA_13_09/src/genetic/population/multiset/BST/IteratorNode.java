/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population.multiset.BST;

import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author ZULU
 */
public class IteratorNode<T> implements Iterator<MNode<T>> {

    MNode<T> actual = null;
    MNode<T> root;
    Stack<MNode<T>> nodes = new Stack<>();

    public IteratorNode(MNode root) {
        this.root = root;
        nodes = new Stack<>();
        if (root != null) {
            actual = Minimum(root);
        }
    }

    public boolean hasNext() {
        return actual != null;
    }

    public MNode<T> next() {
        MNode<T> tmp = actual;
        actual = Successor(actual);
        return tmp;
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Minimum MNode of tree started in x
     *
     * @param x root of the subtree
     * @return MNode with minimum value
     */
    protected MNode<T> Minimum(MNode x) {
        //got to the left
        while (x.left != null) {
            nodes.add(x);
            x = x.left;
        }
        return x;
    }

    /**
     * Sucessor of the node in-ordem
     *
     * @param x current node
     * @return sucessor of x in-ordem
     */
    protected MNode<T> Successor(MNode<T> x) {
        //get minimum of the right
        if (x.right != null) {
            return Minimum(x.right);
        }
        //y is always the parent of x        
        MNode<T> y = nodes.isEmpty() ? null : nodes.pop();
        // get the ancestor of the x
        while (!nodes.isEmpty() && x == y.right) {
            x = y;
            y = nodes.pop();
        }
        return y;
    }
}