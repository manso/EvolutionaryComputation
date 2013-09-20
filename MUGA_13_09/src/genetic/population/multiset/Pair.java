/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population.multiset;

/**
 *
 * @author manso
 */
public class Pair<T> {

    public T elem;
    public int copies;

    public Pair(T elem, int copies) {
        this.elem = elem;
        this.copies = copies;
    }

    @Override
    public String toString() {
        return "< " + elem + "," + copies + " >";
    }
}
