package genetic.population.multiset;

/*****************************************************************************/
/****     M U G A -  M U L T I S E T   G E N E TI C   A L G OR I T H M    ****/
/****                           ver 2.0 (Sept/2009)                       ****/
/****                                                                     ****/
/****     authors:                                                        ****/
/****              António Manso                                          ****/
/****              URL   : http://orion.ipt.pt/~manso/                    ****/
/****              e-mail: manso@ipt.pt                                   ****/
/****                                                                     ****/
/****              Luís Correia                                          ****/
/****              URL   : http://www.di.fc.ul.pt/~lcorreia/              ****/
/****              e-mail: Luis.Correia@di.fc.ul.pt                       ****/
/****                                                                     ****/
/*****************************************************************************/
/*****************************************************************************/
/*  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */


/**
 *
 * @author manso
 */
enum RBColor {
    RED, BLACK
}

class MNode<T> {

    MNode<T> left = null;
    MNode<T> right = null;
    MNode<T> parent = null;
    T data = null;
    int copies = 0;
    int cardinality = 0;
    int support = 0;
    long key = 0;
    RBColor color = RBColor.BLACK;
    // NIL Element
    static MNode NIL;

    static {
        NIL = new MNode();
    }

    /**
     * private constructo to NIL
     */
    private MNode() {
    }

    public MNode(T value, int copies, RBColor nodeColor, MNode<T> left, MNode<T> right) {
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

    public long key() {
        return key;
    }

    @Override
    public String toString() {
        return "[" + data + "]";//," + copies + "|" + cardinality + " | " + support + "]";
    }

    public void calculateCardinality() {
        cardinality = left.cardinality + right.cardinality + copies;
    }
    public void calculateIndexes() {
        support = left.support + right.support + 1;
        cardinality = left.cardinality + right.cardinality + copies;
    }

    public MNode<T> grandparent() {
        return parent.parent;
    }

    public MNode<T> sibling() {
        if (this == parent.left) {
            return parent.right;
        } else {
            return parent.left;
        }
    }

    public MNode<T> uncle() {
        return parent.sibling();
    }

    public void propagateIndexes() {
        propagateIndexes(this);
    }

    private void propagateIndexes(MNode<T> node) {
        if (node != NIL) {
            node.calculateIndexes();
            propagateIndexes(node.parent);
        }
    }

    public void propagateCardinality() {
        propagateCardinality(this);
    }

    private void propagateCardinality(MNode<T> node) {
        if (node != NIL) {
            node.calculateCardinality();
            propagateCardinality(node.parent);
        }
    }
}
