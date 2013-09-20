/*
 * Introduction to Algorithms - 2º edition
 * Cormen , Leiserson , Rivest , Stein
 * MIT Press
 */
package genetic.population.multiset;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author manso
 */
public class MultisetRedBlackTree<T>  {

    /**
     * NIL element from Tree node
     * All leafs in the tree link to this element
     * the parent of root is NIL
     */
    static final Node NIL = Node.NIL;
    /**
     * root of the tree
     */
    Node<T> root = NIL;

    /**
     * clear multiset
     */
    public void clear() {
        root = NIL;
    }

    /**
     * number of distinct elements
     * @return number of distinct elements
     */
    public int getSupportSize() {
        return root.support;
    }

    /**
     * get the number of elements in the collection ( data * copies)
     * @return number of elements
     */
    public int getCardinalitySize(){
        return root.cardinality;
    }

    /**
     * get the number of copies of the element
     * @param data element to search
     * @return number of copies
     */
    public int getCardinality(T elem) {
        return search(elem).copies;
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //----------------------------------------------
    // S E A R C H  E L E M E N T S
    //------------------------------------------------
    //:::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * Search element in the data stgructure
     * @param data element to found
     * @return Node of element or NIL if data not exists
     */
    private Node<T> search(T elem) {
        //calculate the key
        int key = elem.hashCode();
        //made a pointer node
        Node<T> tmp = root;
        //search key
        while (tmp != NIL && tmp.key() != key) {
            if (tmp.key() > key) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }
        return tmp;
    }

    /**
     * Minimum Node of tree started in x
     * @param x root of the subtree
     * @return Node with minimum value
     */
    private Node<T> Minimum(Node x) {
        //got to the left
        while (x.left != NIL) {
            x = x.left;
        }
        return x;
    }

    /**
     * Sucessor of the node in-ordem
     * @param x current node
     * @return  sucessor of x in-ordem
     */
    private Node<T> Successor(Node<T> x) {
        //get minimum of the right
        if (x.right != NIL) {
            return Minimum(x.right);
        }
        //y is always the parent of x
        Node<T> y = x.parent;
        // get the ancestor of the x
        while (y != NIL && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    /**
     * verify if the elemet is present in support set
     * @param data element to find
     * @return data exists
     */
    public boolean contains(T elem) {
        return search(elem) != NIL;
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //----------------------------------------------
    // A D D  E L E M E N T S
    //------------------------------------------------
    //:::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * add element with one copie
     * @param data element to add
     */
    public void add(T element) {
        add(element, 1);
    }

    /**
     * add copies of element
     * @param data element do add
     * @param copies number of copies
     */
    public void add(T element, int copies) {
        insert(element, copies);
    }

    /**
     * add the collection to multiset
     * @param c
     */
    public void addAll(Collection<T> c) {
        for (T elem : c) {
            add(elem, 1);
        }
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //----------------------------------------------
    // U P D A T E   E L E M E N T S
    //------------------------------------------------
    //:::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * update the value associated to the key
     * @param element
     * @param value
     */
    public void update(T element, Integer value) {

        Node<T> node = search(element);
        // assert( node.data.equa/ls(key));
        if (node == NIL) {
            throw new NoSuchElementException("Element not found :" + element);
        }
        if (value <= 0) {
            throw new NoSuchElementException("Value must be greather than zero :" + value);
        }
        node.copies = value;
        //
        node.propagateIndexUp();

//        verifyProperties();



    }
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //------------------------------------------------
    // R E M O V E   E L E M E N T S
    //------------------------------------------------
    //:::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * remove one copie of the element
     * @param data element to remove
     * @return the element removed or null if dont exist
     */
    public T remove(T elem) {
        return remove(elem, 1);
    }

    /**
     * remove the number of copies of the element
     *  if the number decays to zero the element is removed
     * @param data element to remove
     * @param copies number of copies to remove
     * @return return the element removed or null if dont exist
     */
    public T remove(T elem, int copies) {
        delete(elem, copies);
        return elem;
    }

    /**
     * remove the element from support and cardinality set
     * @param data element to remove
     */
    public void removeAll(T elem) {
        Node<T> node = search(elem);
        remove(node.data, node.copies);
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::
    //---------------------------------------------------
    // plain Set
    // multi Set:  <One,1> <TWO,2> <TREE,3>
    // Plain SeT:  <ONE> <TWO> <TWO> <THREE> <THREE> <THREE>
    //     INDEX:    0     1     2     3        4       5
    //---------------------------------------------------
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * get the element at the index in the plain set
     * @param index index of the plain set
     * @return element at index
     */
    private Node<T> cardinalityIndex(int index) {
        //index not valid
        if (index < 0 || index >= root.cardinality) {
            throw new IndexOutOfBoundsException("Index not valid :" + index);
        }
        Node<T> node = root;
        //find index
        while (node != NIL) {
            // if the index is found
            if (index >= node.left.cardinality && index < node.cardinality - node.right.cardinality) {
                return node;
            }
            // go to the left if index is less than the left cardinality
            if (index < node.left.cardinality) {
                node = node.left;
            } // else got to the right and decrease de index value
            else {
                // decrease the index
                index -= node.left.cardinality + node.copies;
                node = node.right;
            }
        }
        //never happen
        throw new RuntimeException("Index not found");
    }

    public boolean verifyTree() {
        return verifyTree(root);
    }

    public boolean verifyTree(Node<T> node) {
        if (node == NIL) {
            return true;
        }
        return node.cardinality == node.left.cardinality + node.right.cardinality + node.copies
                && verifyTree(node.left) && verifyTree(node.right)
                && node.support == node.left.support + node.right.support + 1
                && verifyTree(node.left) && verifyTree(node.right);

    }

    /**
     * index in the plain set of the element
     * @param data
     * @return index
     */
    public int indexOf(T elem) {
        Node<T> node = root;
        int key = elem.hashCode();
        //find index
        int index = 1;
        while (node != NIL) {
            // index is found
            if (node.key == key) {
                return node.left.cardinality + index - 1;
            }
            // go to the left
            if (node.key > key) {
                node = node.left;
            } // else go to the right
            else {
                //sum the height of the left tree and the
                index += node.left.cardinality + node.copies;
                node = node.right;
            }
        }
        //never happen
        throw new NoSuchElementException("Element not found");
    }

    /**
     * remove a element at index in the plain set
     * @param index index of the plain set
     * @return element removed
     */
    public T removeIndex(int index) {

        Node<T> node = cardinalityIndex(index);
        if (node == NIL) {
            throw new IndexOutOfBoundsException("index not valid :  " + index);
        }
        T element = node.data;
        //remove 1 element
        remove(node.data, 1);
        return element;
    }

    /**
     * returns the element in the cardinality set of the index
     * @param index index of element
     * @return element corresponding to index
     */
    public T getIndex(int index) {
        return cardinalityIndex(index).data;
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::
    //---------------------------------------------------
    // Support Set
    // multi Set :  <One,1> <TWO,2> <TREE,3>
    // Pair INDEX:    0       1        2
    //---------------------------------------------------
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * get the element at the index in the plain set
     * @param index index of the plain set
     * @return element at index
     */
    private Node<T> supportIndex(int index) {
        //index not valid
        if (index < 0 || index >= root.support) {
            throw new IndexOutOfBoundsException("Index not valid :" + index);
        }
        Node<T> node = root;
        //find index
        while (node != NIL) {
            // if the index is found (support of the left tree)
            if (index == node.left.support) {
                return node;
            }
            // go to the left if index is less than the left support
            if (index < node.left.support) {
                node = node.left;
            } // else go to the right and decrease de index value
            else {
                // decrease the index
                index -= node.left.support + 1;
                node = node.right;
            }
        }
        //never happen
        throw new NoSuchElementException("Index not found");
    }

    /**
     * index in the plain set of the element
     * @param data
     * @return index
     */
    public int indexOfPair(T elem) {
        Node<T> node = root;
        int key = elem.hashCode();
        //find index
        int count = 1;
        while (node != NIL) {

            // if the index is found
            if (node.key == key) {
                return count - 1;
            }
            // go to the left
            if (node.key > key) {
                count = node.support;
                node = node.left;
            } // else got to the right
            else {
                count += node.left.support + 1;
                node = node.right;
            }
        }
        //never happen
        throw new NoSuchElementException("Element not found");
    }

    /**
     * remove a element at index in the plain set
     * @param index index of the plain set
     * @return element removed
     */
    public Pair removePairIndex(int index) {
        Node<T> node = supportIndex(index);
        if (node == NIL) {
            throw new IndexOutOfBoundsException("Element not valid : NIL " + index);
        }
        Pair aux = new Pair(node.data, node.copies);
        remove(node.data, node.copies);
        return aux;
    }

    /**
     * returns the element in the cardinality set of the index
     * @param index index of element
     * @return element corresponding to index
     */
    public Pair<T> getPairIndex(int index) {
        Node<T> node = supportIndex(index);
        if (node == NIL) {
            throw new IndexOutOfBoundsException("Element not valid : NIL " + index);
        }
        return new Pair(node.data, node.copies);
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::

    private String InOrdem(Node<T> node) {
        if (node == NIL) {
            return "";
        }
        return InOrdem(node.left) + node.toString() + InOrdem(node.right);
    }

    /**
     * to string
     * @return string
     */
    @Override
    public String toString() {
        return "SIZE: " + getSupportSize() + " Cardinality: " + getCardinalitySize() + " Data :" + InOrdem(root);
    }
public void print(String msg) {
    System.out.println(":::::::::::::::::::::::: " + msg + " ::::::::::::::::::::::::::::::");
    print();

}
    public void print() {
        printHelper(root, 0);
    }

    private static void printHelper(Node<?> n, int indent) {
        if (n == NIL) {
            System.out.print("<empty tree>");
            return;
        }
        if (n.right != NIL) {
            printHelper(n.right, indent + 10);
        }
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        if (n.color == Node.BLACK) {
            System.out.println(n.toString());
        } else {
            System.out.println("#" + n.toString() + "#");
        }
        if (n.left != NIL) {
            printHelper(n.left, indent + 10);
        }
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //---------------------------------------------------
    // I T E R A T O R S
    //---------------------------------------------------
    //:::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * Iterator to support set
     * @return support set iterator
     */
    public Iterator<T> iteratorSupportSet() {
        return new SupportSetIterator();
    }

    /**
     * Iterator to cardinality set
     * @return cardinality set iterator
     */
    public Iterator<Integer> iteratorCardinalitySet() {
        return new CardinalitySetIterator();
    }

    /**
     * Iterator to cardinality set
     * @return cardinality set iterator
     */
    public Iterator<Node<T>> iteratorMultiSet() {
        return new NodeIterator();
    }

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    private class NodeIterator implements Iterator<Node<T>> {

        Node<T> actual = NIL;

        public NodeIterator() {
            if (root != NIL) {
                actual = Minimum(root);
            }
        }

        public boolean hasNext() {
            return actual != NIL;
        }

        public Node<T> next() {
            Node<T> tmp = actual;
            actual = Successor(actual);
            return tmp;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    ////////////////////////////////////////////////////////////////

    private class SupportSetIterator implements Iterator<T> {

        NodeIterator iterator = new NodeIterator();

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public T next() {
            return iterator.next().data;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    ////////////////////////////////////////////////////////////////
    private class CardinalitySetIterator implements Iterator<Integer> {
        NodeIterator iterator = new NodeIterator();
        public boolean hasNext() {
            return iterator.hasNext();
        }
        public Integer next() {
            return iterator.next().copies;
        }
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////

    public void verifyProperties() {
        if (true) {
            verifyHeight(root);
            verifyProperty1(root);
            verifyProperty2(root);
            // Property 3 is implicit
            verifyProperty4(root);
            verifyProperty5(root);
        }
    }

    private void verifyHeight(Node<?> n) {
        if (n != NIL) {
            assert (n.support > 0);
            assert (n.cardinality > 0);
            assert (n.cardinality == n.copies + n.left.cardinality + n.right.cardinality);
            assert (n.support == 1 + n.left.support + n.right.support);
            verifyHeight(n.left);
            verifyHeight(n.right);
        }
    }

    private static void verifyProperty1(Node<?> n) {
        if (n == NIL) {
            return;
        }
        verifyProperty1(n.left);
        verifyProperty1(n.right);
    }

    private static void verifyProperty2(Node<?> root) {
        assert nodeColor(root) == Node.BLACK;
    }


    private static int nodeColor(Node<?> n) {
        return n == NIL ? Node.BLACK : n.color;
    }

    private static void verifyProperty4(Node<?> n) {
        if (nodeColor(n) == Node.RED) {
            assert nodeColor(n.left) == Node.BLACK;
            assert nodeColor(n.right) == Node.BLACK;
            assert nodeColor(n.parent) == Node.BLACK;
        }
        if (n == NIL) {
            return;
        }
        verifyProperty4(n.left);
        verifyProperty4(n.right);
    }

    private static void verifyProperty5(Node<?> root) {
        verifyProperty5Helper(root, 0, -1);
    }

    private static int verifyProperty5Helper(Node<?> n, int blackCount, int pathBlackCount) {
        if (nodeColor(n) == Node.BLACK) {
            blackCount++;
        }
        if (n == NIL) {
            if (pathBlackCount == -1) {
                pathBlackCount = blackCount;
            } else {
                assert blackCount == pathBlackCount;
            }
            return pathBlackCount;
        }
        pathBlackCount = verifyProperty5Helper(n.left, blackCount, pathBlackCount);
        pathBlackCount = verifyProperty5Helper(n.right, blackCount, pathBlackCount);
        return pathBlackCount;
    }
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private void rotateLeft(Node<T> n) {
      // print("Before Rotate Left " + n.toString());
        Node<T> r = n.right;
        replaceNode(n, r);
        n.right = r.left;
        if (r.left != NIL) {
            r.left.parent = n;
        }
        r.left = n;
        n.parent = r;

        //propagate heights for the left
        //n.propagateIndextDown();
        n.calculateIndexes();
        //calculate the cardinality of r
        r.calculateIndexes();
       // print("After Rotate Left " + n.toString());
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    private void rotateRight(Node<T> n) {
        Node<T> l = n.left;
        replaceNode(n, l);
        n.left = l.right;
        if (l.right != NIL) {
            l.right.parent = n;
        }
        l.right = n;
        n.parent = l;

        //propagate heights for the right
        //n.propagateIndextDown();
        n.calculateIndexes();
        //calculate the cardinality of r
        l.calculateIndexes();
       // print("Rotate right " + n.toString());
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    private void replaceNode(Node<T> oldn, Node<T> newn) {
        if (oldn.parent == NIL) {
            root = newn;
        } else {
            if (oldn == oldn.parent.left) {
                oldn.parent.left = newn;
            } else {
                oldn.parent.right = newn;
            }
        }
        if (newn != NIL) {
            newn.parent = oldn.parent;
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public void insert(T value, int copies) {
        Node<T> insertedNode = new Node<T>(value, copies, Node.RED, NIL, NIL);
        if (root == NIL) {
            root = insertedNode;
        } else {
            Node<T> n = root;
            while (true) {
                if (insertedNode.key == n.key) {
                    //increase copies
                    n.copies += copies;
                    //propagate the heights up
                    n.propagateIndexUp();
                    return;
                } else if (insertedNode.key < n.key) {
                    if (n.left == NIL) {
                        n.left = insertedNode;
                        break;
                    } else {
                        n = n.left;
                    }
                } else {
                    if (n.right == NIL) {
                        n.right = insertedNode;
                        break;
                    } else {
                        n = n.right;
                    }
                }
            }
            insertedNode.parent = n;
        }
        //propagate the heights up
        insertedNode.propagateIndexUp();
        //balance the tree
        insertCase1(insertedNode);

//        verifyProperties();
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    private void insertCase1(Node<T> n) {
        if (n.parent == NIL) {
            n.color = Node.BLACK;
        } else {
            insertCase2(n);
        }
    }

    private void insertCase2(Node<T> n) {
        if (nodeColor(n.parent) == Node.BLACK) {
            return; // Tree is still valid
        } else {
            insertCase3(n);
        }
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    void insertCase3(Node<T> n) {
        if (nodeColor(n.uncle()) == Node.RED) {
            n.parent.color = Node.BLACK;
            n.uncle().color = Node.BLACK;
            n.grandparent().color = Node.RED;
            insertCase1(n.grandparent());
        } else {
            insertCase4(n);
        }
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    void insertCase4(Node<T> n) {
        if (n == n.parent.right && n.parent == n.grandparent().left) {
            rotateLeft(n.parent);
            n = n.left;
        } else if (n == n.parent.left && n.parent == n.grandparent().right) {
            rotateRight(n.parent);
            n = n.right;
        }
        insertCase5(n);
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    void insertCase5(Node<T> n) {
        n.parent.color = Node.BLACK;
        n.grandparent().color = Node.RED;
        if (n == n.parent.left && n.parent == n.grandparent().left) {
            rotateRight(n.grandparent());
        } else {
            assert n == n.parent.right && n.parent == n.grandparent().right;
            rotateLeft(n.grandparent());
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    public void delete(T elem, int copies) {
        Node<T> n = search(elem);
        if (n == NIL) {
           throw new NullPointerException("Element Not Found");
        }
        //delete copies
        if (n.copies > copies) {
            n.copies -= copies;
            n.propagateIndexUp();
//            verifyProperties();
            return;
        }

        if (n.left != NIL && n.right != NIL) {
            // Copy data from predecessor and then delete it instead
            Node<T> pred = maximumNode(n.left);
            n.key = pred.key;
            n.data = pred.data;
            n.copies = pred.copies;
            n = pred;
        }

        Node<T> child = (n.right == NIL) ? n.left : n.right;
        if (nodeColor(n) == Node.BLACK) {
            n.color = nodeColor(child);
            deleteCase1(n);
        }
        replaceNode(n, child);

        if (nodeColor(root) == Node.RED) {
            root.color = Node.BLACK;
        }
        n.propagateIndexUp();
        //verify
//        verifyProperties();
    }

    private Node<T> maximumNode(Node<T> n) {
        while (n.right != NIL) {
            n = n.right;
        }
        return n;
    }

    private void deleteCase1(Node<T> n) {
        if (n.parent == NIL) {
            return;
        } else {
            deleteCase2(n);
        }
    }

    private void deleteCase2(Node<T> n) {
        if (nodeColor(n.sibling()) == Node.RED) {
            n.parent.color = Node.RED;
            n.sibling().color = Node.BLACK;
            if (n == n.parent.left) {
                rotateLeft(n.parent);
            } else {
                rotateRight(n.parent);
            }
        }
        deleteCase3(n);
    }

    private void deleteCase3(Node<T> n) {
        if (nodeColor(n.parent) == Node.BLACK
                && nodeColor(n.sibling()) == Node.BLACK
                && nodeColor(n.sibling().left) == Node.BLACK
                && nodeColor(n.sibling().right) == Node.BLACK) {
            n.sibling().color = Node.RED;
            deleteCase1(n.parent);
        } else {
            deleteCase4(n);
        }
    }

    private void deleteCase4(Node<T> n) {
        if (nodeColor(n.parent) == Node.RED
                && nodeColor(n.sibling()) == Node.BLACK
                && nodeColor(n.sibling().left) == Node.BLACK
                && nodeColor(n.sibling().right) == Node.BLACK) {
            n.sibling().color = Node.RED;
            n.parent.color = Node.BLACK;
        } else {
            deleteCase5(n);
        }
    }

    private void deleteCase5(Node<T> n) {
        if (n == n.parent.left
                && nodeColor(n.sibling()) == Node.BLACK
                && nodeColor(n.sibling().left) == Node.RED
                && nodeColor(n.sibling().right) == Node.BLACK) {
            n.sibling().color = Node.RED;
            n.sibling().left.color = Node.BLACK;
            rotateRight(n.sibling());
        } else if (n == n.parent.right
                && nodeColor(n.sibling()) == Node.BLACK
                && nodeColor(n.sibling().right) == Node.RED
                && nodeColor(n.sibling().left) == Node.BLACK) {
            n.sibling().color = Node.RED;
            n.sibling().right.color = Node.BLACK;
            rotateLeft(n.sibling());
        }
        deleteCase6(n);
    }

    private void deleteCase6(Node<T> n) {
        n.sibling().color = nodeColor(n.parent);
        n.parent.color = Node.BLACK;
        if (n == n.parent.left) {
            n.sibling().right.color = Node.BLACK;
            rotateLeft(n.parent);
        } else {
            n.sibling().left.color = Node.BLACK;
            rotateRight(n.parent);
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
}


 