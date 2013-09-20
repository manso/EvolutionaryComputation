package genetic.population.multiset;

/*
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation, Inc.,
 * 675 Mass Ave, Cambridge, MA 02139, USA.
 */
//************************** RED BLACK TREE Copyright *************************
/*
 * Copyright (c) 2010 the authors listed at the following URL, and/or the
 * authors of referenced articles or incorporated external code:
 * http://en.literateprograms.org/Red-black_tree_(Java)?action=history&offset=20100112141306
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Retrieved from:
 * http://en.literateprograms.org/Red-black_tree_(Java)?oldid=16622
 *
 */
import java.util.*;

/**
 *
 * @author manso
 */
public class MiTree<T> {

    private static boolean DEBUG = false;
    /**
     * NIL element from Tree node All leafs in the tree link to this element the
     * parent of root is NIL
     */
    static final MNode NIL = MNode.NIL;
    /**
     * root of the tree
     */
    protected MNode<T> root = NIL;
    protected final Comparator<T> comparator;

    /**
     * Contructor to Mitree by default elements are compared by hascode()
     * function
     */
    public MiTree() {
        comparator = null;
    }

    /**
     * Contructor with comparator
     *
     * @param comparator comparator to elements
     */
    public MiTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public int getNumberOfElements() {
        if (root != null) {
            return root.cardinality;
        }
        return 0;
    }

    public int getNumberOfDistinctElements() {
        if (root != null) {
            return root.support;
        }
        return 0;
    }

    /**
     * compare the elements of two nodes
     *
     * @param node1 first element
     * @param node2 second element
     * @return <0 if node1 < node2 0 if are equal and >0 otherwise
     */
    protected long compareNode(MNode<T> node1, MNode<T> node2) {
        //use comparator if exists
        if (comparator != null) {
            return comparator.compare(node1.data, node2.data);
        }
        return node1.key - node2.key;
    }

    /**
     * clear multiset
     */
    public void clear() {
        root = NIL;
    }

    /**
     * number of distinct elements in the support set
     *
     * @return number of distinct elements
     */
    public int getSupportSize() {
        return root.support;
    }

    /**
     * get the number of elements in the collection ( data * copies)
     *
     * @return number of elements
     */
    public int getCardinalitySize() {
        return root.cardinality;
    }

    /**
     * get the number of copies of the element
     *
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
     * Search element in the data structure
     *
     * @param data element to found
     * @return MNode of element or NIL if data not exists
     */
    protected MNode<T> search(T elem) {
        //new node
        MNode<T> element = new MNode<T>(elem, 0, RBColor.BLACK, null, null);
        //made a pointer node
        MNode<T> tmp = root;
        //search key
        while (tmp != NIL) {
            long comp = compareNode(tmp, element);
            if (comp == 0) {
                return tmp;
            }
            if (comp < 0) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }
        return tmp;
    }
    
     /**
     * Search element in the data structure
     *
     * @param data element to found
     * @return MNode of element or NIL if data not exists
     */
    public T get(T elem) {
        //new node
        MNode<T> tmp =search(elem);
        return tmp.data;
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::
    //----------------------------------------------
    // S E A R C H  E L E M E N T S
    //------------------------------------------------
    //:::::::::::::::::::::::::::::::::::::::::::::::
    /**
     * Minimum MNode of tree started in x
     *
     * @param x root of the subtree
     * @return MNode with minimum value
     */
    protected MNode<T> Minimum(MNode x) {
        //got to the left
        while (x.left != NIL) {
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
        if (x.right != NIL) {
            return Minimum(x.right);
        }
        //y is always the parent of x
        MNode<T> y = x.parent;
        // get the ancestor of the x
        while (y != NIL && x == y.right) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    /**
     * verify if the elemet is present in support set
     *
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
     *
     * @param data element to add
     */
    public void add(T element) {
        add(element, 1);
    }

    /**
     * add copies of element
     *
     * @param data element do add
     * @param copies number of copies
     */
    public void add(T element, int copies) {
        insert(element, copies);
    }

    /**
     * add the collection to multiset
     *
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
     *
     * @param element
     * @param value
     */
    public void update(T element, Integer value) {
        //search to the node
        MNode<T> node = search(element);

        if (node == NIL) {
            System.out.println(" Fail Serach:element");
            System.out.println(toString());
            print("\nTree:\n");
//            update(element, value);
            throw new NoSuchElementException("Element not found :" + element);

        }
        if (value <= 0) {
            throw new NoSuchElementException("Value must be greather than zero :" + value);
        }
        //update the number of copies
        node.copies = value;
        //actualize indexes
        node.propagateIndexes();
        verifyProperties();

    }

    /**
     * update the value associated to the key
     *
     * @param element
     * @param value
     */
    public void addCopies(T element, Integer value) {
        //search to the node
        MNode<T> node = search(element);

        if (node == NIL) {
            throw new NoSuchElementException("Element not found :" + element);
        }
        if (value <= 0) {
            throw new NoSuchElementException("Value must be greather than zero :" + value);
        }
        //update the number of copies
        node.copies += value;
        //actualize indexes
        node.propagateIndexes();
        //verifyProperties();

    }
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //------------------------------------------------
    // R E M O V E   E L E M E N T S
    //------------------------------------------------
    //:::::::::::::::::::::::::::::::::::::::::::::::

    /**
     * remove one copie of the element
     *
     * @param data element to remove
     * @return the element removed or null if dont exist
     */
    public T remove(T elem) {
        return remove(elem, 1);
    }

    /**
     * remove the number of copies of the element if the number decays to zero
     * the element is removed
     *
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
     *
     * @param data element to remove
     */
    public void removeAll(T elem) {
        MNode<T> node = search(elem);
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
     *
     * @param index index of the plain set
     * @return element at index
     */
    private MNode<T> cardinalityIndex(int index) {
        //index not valid
        if (index < 0 || index >= root.cardinality) {
            throw new IndexOutOfBoundsException("Index not valid :" + index);
        }
        MNode<T> node = root;
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

    private boolean verifyTree(MNode<T> node) {
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
     *
     * @param data
     * @return index
     */
    public int indexOf(T elem) {
        MNode<T> node = root;
        MNode<T> element = new MNode<T>(elem, 0, RBColor.BLACK, null, null);
        //find in0dex
        int index = 1;
        while (node != NIL) {
            long comp = compareNode(node, element);
            // index is found
            if (comp == 0) {
                return node.left.cardinality + index - 1;
            }
            // go to the left
            if (comp < 0) {
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
     *
     * @param index index of the plain set
     * @return element removed
     */
    public T removeIndex(int index) {

        MNode<T> node = cardinalityIndex(index);
        if (node == NIL) {
            throw new IndexOutOfBoundsException("index not valid :  " + index);
        }
        T element = node.data;

        //remove 1 element
        //  remove(node.data, 1);
        delete(node, 1);
        return element;
    }

    /**
     * returns the element in the cardinality set of the index
     *
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
     *
     * @param index index of the plain set
     * @return element at index
     */
    private MNode<T> supportIndex(int index) {
        //index not valid
        if (index < 0 || index >= root.support) {
            throw new IndexOutOfBoundsException("Index not valid :" + index);
        }
        MNode<T> node = root;
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
     *
     * @param data
     * @return index
     */
    public int indexOfPair(T elem) {
        MNode<T> node = root;
        MNode<T> element = new MNode<T>(elem, 0, RBColor.BLACK, null, null);
        //find index
        int count = 1;
        while (node != NIL) {
            long comp = compareNode(node, element);
            // if the index is found
            if (comp == 0) {
                return count - 1;
            }
            // go to the left
            if (comp < 0) {
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
     *
     * @param index index of the plain set
     * @return element removed
     */
    public Pair<T> removePairIndex(int index) {
        MNode<T> node = supportIndex(index);
        if (node == NIL) {
            throw new IndexOutOfBoundsException("Element not valid : NIL " + index);
        }
        Pair aux = new Pair(node.data, node.copies);
        remove(node.data, node.copies);
        return aux;
    }

    /**
     * returns the element in the cardinality set of the index
     *
     * @param index index of element
     * @return element corresponding to index
     */
    public Pair<T> getPairIndex(int index) {
        MNode<T> node = supportIndex(index);
        if (node == NIL) {
            throw new IndexOutOfBoundsException("Element not valid : NIL " + index);
        }
        return new Pair(node.data, node.copies);
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::::::::::::::::::::::::::::::

    private String InOrdem(MNode<T> node) {
        if (node == NIL) {
            return "";
        }
        return InOrdem(node.left) + node.toString() + "\n" + InOrdem(node.right);
    }

    /**
     * to string
     *
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

    private static void printHelper(MNode<?> n, int indent) {
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
        if (n.color == RBColor.BLACK) {
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
     *
     * @return support set iterator
     */
    public Iterator<T> iteratorSupportSet() {
        return new SupportSetIterator();
    }

    /**
     * Iterator to cardinality set
     *
     * @return cardinality set iterator
     */
    public Iterator<Integer> iteratorCardinalitySet() {
        return new CardinalitySetIterator();
    }

    /**
     * Iterator to cardinality set
     *
     * @return cardinality set iterator
     */
    public Iterator<Pair<T>> iteratorMultiSet() {
        return new MultiSetIterator();
    }

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    protected class NodeIterator implements Iterator<MNode<T>> {

        MNode<T> actual = NIL;

        public NodeIterator() {
            if (root != NIL) {
                actual = Minimum(root);
            }
        }

        public boolean hasNext() {
            return actual != NIL;
        }

        public MNode<T> next() {
            MNode<T> tmp = actual;
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

    ////////////////////////////////////////////////////////////////
    private class MultiSetIterator implements Iterator< Pair<T>> {

        NodeIterator iterator = new NodeIterator();

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public Pair<T> next() {
            MNode<T> actual = iterator.next();
            return new Pair(actual.data, actual.copies);
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

        if (DEBUG) {
            verifyOrder(root);
            verifyHeight(root);
            verifyProperty1(root);
            verifyProperty2(root);
            // Property 3 is implicit
            verifyProperty4(root);
            verifyProperty5(root);
        }
    }

    private void verifyOrder(MNode<T> n) {
        if (n != NIL) {
            if (n.left != NIL) {
                assert (compareNode(n, n.left) < 0);
            }
            if (n.right != NIL) {
                assert (compareNode(n, n.right) > 0);
            }
            verifyOrder(n.left);
            verifyOrder(n.right);
        }

    }

    private void verifyHeight(MNode<?> n) {
        if (n != NIL) {
            assert (n.support > 0);
            assert (n.cardinality > 0);
            assert (n.cardinality == n.copies + n.left.cardinality + n.right.cardinality);
            assert (n.support == 1 + n.left.support + n.right.support);
            verifyHeight(n.left);
            verifyHeight(n.right);
        }
    }

    private static void verifyProperty1(MNode<?> n) {
        assert nodeColor(n) == RBColor.RED || nodeColor(n) == RBColor.BLACK;
        if (n == NIL) {
            return;
        }
        verifyProperty1(n.left);
        verifyProperty1(n.right);
    }

    private static void verifyProperty2(MNode<?> root) {
        assert nodeColor(root) == RBColor.BLACK;
    }

    private static RBColor nodeColor(MNode<?> n) {
        return n == NIL ? RBColor.BLACK : n.color;
    }

    private static void verifyProperty4(MNode<?> n) {
        if (nodeColor(n) == RBColor.RED) {
            assert nodeColor(n.left) == RBColor.BLACK;
            assert nodeColor(n.right) == RBColor.BLACK;
            assert nodeColor(n.parent) == RBColor.BLACK;
        }
        if (n == NIL) {
            return;
        }
        verifyProperty4(n.left);
        verifyProperty4(n.right);
    }

    private static void verifyProperty5(MNode<?> root) {
        verifyProperty5Helper(root, 0, -1);
    }

    private static int verifyProperty5Helper(MNode<?> n, int blackCount, int pathBlackCount) {
        if (nodeColor(n) == RBColor.BLACK) {
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

    private void rotateLeft(MNode<T> n) {
        //print("Before Rotate Left " + n.toString());
        MNode<T> r = n.right;
        replaceNode(n, r);
        n.right = r.left;
        if (r.left != NIL) {
            r.left.parent = n;
        }
        r.left = n;
        n.parent = r;

        // recalculate indexes
        n.calculateIndexes();
        r.calculateIndexes();
        // print("After Rotate Left " + n.toString());
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    private void rotateRight(MNode<T> n) {
        // print("Before Rotate Right " + n.toString());
        MNode<T> l = n.left;
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

    private void replaceNode(MNode<T> oldn, MNode<T> newn) {
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
        MNode<T> insertedNode = new MNode<T>(value, copies, RBColor.RED, NIL, NIL);
        if (root == NIL) {
            root = insertedNode;
        } else {
            MNode<T> n = root;
            while (true) {
                long comp = compareNode(n, insertedNode);
                if (comp == 0) {
                    //increase copies
                    n.copies += copies;
                    //propagate the heights up
                    n.propagateCardinality();
                    return;
                } else if (comp < 0) {
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
        insertedNode.propagateIndexes();
        //balance the tree
        insertCase1(insertedNode);

        verifyProperties();
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    private void insertCase1(MNode<T> n) {
        if (n.parent == NIL) {
            n.color = RBColor.BLACK;
        } else {
            insertCase2(n);
        }
    }

    private void insertCase2(MNode<T> n) {
        if (nodeColor(n.parent) == RBColor.BLACK) {
            return; // Tree is still valid
        } else {
            insertCase3(n);
        }
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    void insertCase3(MNode<T> n) {
        if (nodeColor(n.uncle()) == RBColor.RED) {
            n.parent.color = RBColor.BLACK;
            n.uncle().color = RBColor.BLACK;
            n.grandparent().color = RBColor.RED;
            insertCase1(n.grandparent());
        } else {
            insertCase4(n);
        }
    }
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    void insertCase4(MNode<T> n) {
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

    void insertCase5(MNode<T> n) {
        n.parent.color = RBColor.BLACK;
        n.grandparent().color = RBColor.RED;
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
        MNode<T> n = search(elem);
        delete(n, copies);
    }

    private void delete(MNode<T> n, int copies) {
        if (n == NIL) {
            throw new NullPointerException("Element Not Found");
        }
        //delete copies
        if (n.copies > copies) {
            n.copies -= copies;
            n.propagateIndexes();
            verifyProperties();
            return;
        }

        if (n.left != NIL && n.right != NIL) {
            // Copy data from predecessor and then delete it instead
            MNode<T> pred = maximumNode(n.left);
            n.key = pred.key;
            n.data = pred.data;
            n.copies = pred.copies;
            n = pred;
        }

        MNode<T> child = (n.right == NIL) ? n.left : n.right;
        if (nodeColor(n) == RBColor.BLACK) {
            n.color = nodeColor(child);
            deleteCase1(n);
        }
        replaceNode(n, child);

        if (nodeColor(root) == RBColor.RED) {
            root.color = RBColor.BLACK;
        }
        n.propagateIndexes();
        //verify
        verifyProperties();
    }

    private MNode<T> maximumNode(MNode<T> n) {
        while (n.right != NIL) {
            n = n.right;
        }
        return n;
    }

    private void deleteCase1(MNode<T> n) {
        if (n.parent == NIL) {
            return;
        } else {
            deleteCase2(n);
        }
    }

    private void deleteCase2(MNode<T> n) {
        if (nodeColor(n.sibling()) == RBColor.RED) {
            n.parent.color = RBColor.RED;
            n.sibling().color = RBColor.BLACK;
            if (n == n.parent.left) {
                rotateLeft(n.parent);
            } else {
                rotateRight(n.parent);
            }
        }
        deleteCase3(n);
    }

    private void deleteCase3(MNode<T> n) {
        if (nodeColor(n.parent) == RBColor.BLACK
                && nodeColor(n.sibling()) == RBColor.BLACK
                && nodeColor(n.sibling().left) == RBColor.BLACK
                && nodeColor(n.sibling().right) == RBColor.BLACK) {
            n.sibling().color = RBColor.RED;
            deleteCase1(n.parent);
        } else {
            deleteCase4(n);
        }
    }

    private void deleteCase4(MNode<T> n) {
        if (nodeColor(n.parent) == RBColor.RED
                && nodeColor(n.sibling()) == RBColor.BLACK
                && nodeColor(n.sibling().left) == RBColor.BLACK
                && nodeColor(n.sibling().right) == RBColor.BLACK) {
            n.sibling().color = RBColor.RED;
            n.parent.color = RBColor.BLACK;
        } else {
            deleteCase5(n);
        }
    }

    private void deleteCase5(MNode<T> n) {
        if (n == n.parent.left
                && nodeColor(n.sibling()) == RBColor.BLACK
                && nodeColor(n.sibling().left) == RBColor.RED
                && nodeColor(n.sibling().right) == RBColor.BLACK) {
            n.sibling().color = RBColor.RED;
            n.sibling().left.color = RBColor.BLACK;
            rotateRight(n.sibling());
        } else if (n == n.parent.right
                && nodeColor(n.sibling()) == RBColor.BLACK
                && nodeColor(n.sibling().right) == RBColor.RED
                && nodeColor(n.sibling().left) == RBColor.BLACK) {
            n.sibling().color = RBColor.RED;
            n.sibling().right.color = RBColor.BLACK;
            rotateLeft(n.sibling());
        }
        deleteCase6(n);
    }

    private void deleteCase6(MNode<T> n) {
        n.sibling().color = nodeColor(n.parent);
        n.parent.color = RBColor.BLACK;
        if (n == n.parent.left) {
            n.sibling().right.color = RBColor.BLACK;
            rotateLeft(n.parent);
        } else {
            n.sibling().left.color = RBColor.BLACK;
            rotateRight(n.parent);
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
}
