/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population.multiset.BST;

/**
 *
 * @author ZULU
 */
public class MNode<AnyType> {
        MNode(AnyType theElement, int copies, MNode<AnyType> lt, MNode<AnyType> rt) {
            this.cardinality = copies;
            this.copies = copies;
            this.element = theElement;
            this.left = lt;
            this.right = rt;
        }
        int copies = 0; // number of node copies
        int cardinality; //height of node
        AnyType element;            // The data in the node
        MNode<AnyType> left;   // Left child
        MNode<AnyType> right;  // Right child
    }