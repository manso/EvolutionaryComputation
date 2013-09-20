/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic.population.multiset;

/**
 *
 * @author manso
 */
public class Node<T> {
    static int RED =0;
    static int BLACK=1;

    Node<T> left = null;
    Node<T> right = null ;
    Node<T> parent = null;
    T data = null;
    int copies = 0;
    int cardinality = 0;
    int support = 0;
    int key = 0;
    int color = BLACK;

    // NIL Element
    static Node NIL;
    static {NIL = new Node(); }

    /**
     * private constructo to NIL
     */
    private Node() {}

    public Node(T value, int copies, int nodeColor, Node<T> left, Node<T> right) {
        this.key = value.hashCode();
        this.data = value;
        this.copies = copies;
        this.color = nodeColor;
        this.left = left;
        this.right = right;
        this.parent = NIL;
        this.cardinality = copies;
        this.support = 1;
    }
    public T getElem(){
        return data;
    }

    public int getCopies(){
        return copies;
    }

    public int key() {
        return key;
    }

    @Override
    public String toString() {
        return "[" + data + "," + copies + "|" + cardinality + " | " + support + "]";
    }

    public void calculateIndexes() {
        cardinality = left.cardinality + right.cardinality + copies;
        support = left.support + right.support + 1;
    }


    public Node<T> grandparent() {
        return parent.parent;
    }

    public Node<T> sibling() {
        if (this == parent.left) {
            return parent.right;
        } else {
            return parent.left;
        }
    }

    public Node<T> uncle() {
        return parent.sibling();
    }

    public void propagateIndexUp() {
        propagateIndexUp(this);
    }

    private void propagateIndexUp(Node<T> node) {
        if (node != NIL) {
            node.calculateIndexes();
            propagateIndexUp(node.parent);
        }
    }
}