package genetic.population.multiset.BST;

//  BinarySearchTree class

import genetic.population.multiset.Pair;
import java.util.Iterator;

// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate
/**
 * Implements an unbalanced binary search tree. Note that all "matching" is
 * based on the compareTo method.
 *
 * @author Mark Allen Weiss
 */
public class MultiSetBinaryTree<T extends Comparable<? super T>> {
    
    public T get(int index){
        return getIndex(index).element;
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
        return new IteratorSupportSet(root);
    }

    /**
     * Iterator to cardinality set
     * @return cardinality set iterator
     */
    public Iterator<Integer> iteratorCardinalitySet() {
        return new IteratorCardinalitySet(root);
    }

     /**
     * Iterator to cardinality set
     * @return cardinality set iterator
     */
    public Iterator<Pair<T>> iteratorMultiSet() {
        return new IteratorMultiset(root);
    }
     //:::::::::::::::::::::::::::::::::::::::::::::::
    //---------------------------------------------------
    
    

    /**
     * The tree root.
     */
    private MNode<T> root;

    /**
     * Construct the tree.
     */
    public MultiSetBinaryTree() {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */
    public void insert(T x) {
        insert(x, 1);
    }

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param x the item to insert.
     */
    public void insert(T x, int copies) {
        root = insert(x, root, copies);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     *
     * @param x the item to remove.
     */
    public void remove(T x) {
        remove(x, 1);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     *
     * @param x the item to remove.
     */
    public void remove(T x, int copies) {
        root = remove(x, root, copies);
    }

    /**
     * Find the smallest item in the tree.
     *
     * @return smallest item or null if empty.
     */
    public T findMin() {
        if (isEmpty()) {
            throw new RuntimeException("Empty Tree");
        }
        return findMin(root).element;
    }

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item of null if empty.
     */
    public T findMax() {
        if (isEmpty()) {
            throw new RuntimeException("Empty Tree");
        }
        return findMax(root).element;
    }

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains(T x) {
        return search(x, root)!=null;
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
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
    private MNode<T> getIndex(int index) {
        //index not valid
        if (index < 0 || index >= root.cardinality) {
            throw new IndexOutOfBoundsException("Index not valid :" + index);
        }
        MNode<T> node = root;
        //find index
        while (node != null) {
            // if the index is found
            int leftHeight = node.left == null ? 0 : node.left.cardinality;
            int rightHeight = node.right == null ? 0 : node.right.cardinality;
            
            if (index >= leftHeight && index < node.cardinality - rightHeight) {
                return node;
            }
            // go to the left if index is less than the left cardinality
            if (index < leftHeight) {
                node = node.left;
            } // else got to the right and decrease de index value
            else {
                // decrease the index
                index -= leftHeight + node.copies;
                node = node.right;
            }
        }
        //never happen
        throw new RuntimeException("Index not found : " + index);
    }

    /**
     * index in the plain set of the element
     *
     * @param data
     * @return index
     */
    public int indexOf(T elem) {
        MNode<T> node = root;
        //find index
        int index = 1;
        while (node != null) {
            long comp = node.element.compareTo(elem);
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
                index += node.left.cardinality + node.cardinality;
                node = node.right;
            }
        }
        //never happen
        throw new RuntimeException("Element not found");
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private MNode<T> insert(T x, MNode<T> t, int copies) {
        if (t == null) {
            return new MNode<>(x, copies, null, null);
        }
        int compareResult = x.compareTo(t.element);
        //increase the height of the tree
        t.cardinality += copies;
        if (compareResult < 0) {
            t.left = insert(x, t.left, copies);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right, copies);
        } else {
            t.copies += copies; // increase copies
        }
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     *
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private MNode<T> remove(T x, MNode<T> t, int copies) {
        if (t == null) {
            throw new RuntimeException("Element not found");
        }
        int compareResult = x.compareTo(t.element);
        //decrease the height of the tree
        t.cardinality -= copies;

        if (compareResult < 0) {
            t.left = remove(x, t.left, copies);
        } else if (compareResult > 0) {
            t.right = remove(x, t.right, copies);
        } //verify the number of copies
        else if (t.copies < copies) {
            throw new RuntimeException("wrong number of copies: expected " + copies + " found " + t.copies);
        } //delete copies
        else if (t.copies > copies) {
            t.copies -= copies;
        } //delete node
        else if (t.left != null && t.right
                != null) // Two children
        {
            //replace info of the node
            MNode<T> min = findMin(t.right);
            t.element = min.element;
            t.copies = min.copies;
            //remove the node min
            t.right = remove(min.element, t.right, min.copies);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private MNode<T> findMin(MNode<T> t) {
        if (t == null) {
            return null;
        } else if (t.left == null) {
            return t;
        }
        return findMin(t.left);
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private MNode<T> findMax(MNode<T> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private MNode<T> search(T x, MNode<T> t) {
        if (t == null) {
            return null;
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            return search(x, t.left);
        } else if (compareResult > 0) {
            return search(x, t.right);
        } else {
            return t;    // Match
        }
    }

   ////////////////////////////////////////////////////////////////
   static int INDENT_STEP = 12;
    public void print() {
        System.out.println("\n-----------------------------------SIZE : " + root.cardinality);
        printHelper(root, 0, true);
    }
    /**
     * Print tree in the console
     * @param n node
     * @param level level of the node
     * @param direction left or right
     */
    private static void printHelper(MNode<?> n, int level, boolean direction) {
        if (n == null) {
            System.out.print("<empty tree>");
            return;
        }
        if (n.left != null) {
            printHelper(n.left, level + 1, false);
        }
        System.out.println("");
        for (int i = 0; i < level* INDENT_STEP; i++) {
            System.out.print(" ");
        }
        if (direction) {
            System.out.print("\\");
        } else {
            System.out.print("/");
        }
        System.out.print(n.element + " (" + n.copies + "-" + n.cardinality + ")");
//        if (n.left != n.right) {
//            System.out.print("-");
//        }
        if (n.right != null) {
            printHelper(n.right, level+1 , true);
        }
    }

    /**
     * @return the size
     */
    public int getSize() {
        return root != null ? root.cardinality : 0;
    }
    
    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < getSize(); i++) {
                   str.append( this.getIndex(i).element + " ,");
        }
        return str.toString();
    }



    // Test program
    public static void main(String[] args) {
        MultiSetBinaryTree<String> t = new MultiSetBinaryTree<>();

        t.insert("B", 2);
        t.insert("A", 1);
        t.insert("D", 4);
        t.insert("C", 3);
        t.insert("F",1);
        t.insert("E", 3);
        t.insert("G", 1);
        System.out.println("\n Elements :"+t.toString());

        t.print();

        t.remove("E");
        t.print();
        System.out.println("\n Elements(-E) :"+t.toString());
//        t.print();
        
        t.remove("D",4);
         System.out.println("\n Elements(-4D) :"+t.toString());
        t.print();
        
        
        Iterator<String> its = t.iteratorSupportSet();
        Iterator<Integer> itc = t.iteratorCardinalitySet();
        Iterator<Pair<String>> itm = t.iteratorMultiSet();
        
        while( itm.hasNext()){
            System.out.print("\nElem " + its.next());
            System.out.print("  cardinality " + itc.next());
            System.out.print("  Multiset " + itm.next());
        }
    }
}
