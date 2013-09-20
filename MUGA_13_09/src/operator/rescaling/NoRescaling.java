/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.rescaling;

import genetic.population.Population;

/**
 *
 * @author manso
 */
public class NoRescaling extends Rescaling {

    @Override
    public Population execute(Population pop) {
        return pop;
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public String getInformation() {
        return toString();
    }
}
