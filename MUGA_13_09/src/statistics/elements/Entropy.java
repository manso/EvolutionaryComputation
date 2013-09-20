/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.elements;

import genetic.Solver.SimpleSolver;
import genetic.population.Population;
import utils.Diversity.Shannon;

/**
 *
 * @author manso
 */
public class Entropy extends AbstractStatsElement {

    Shannon entropy = new Shannon();

    public Entropy() {
    }

    public Entropy(Population pop) {
    }

    @Override
    public double execute(SimpleSolver s) {
        setValue(entropy.calculateEntropy(s.getParents()));
        return getValue();

    }

    @Override
    public String getName() {
        return "Shannon Entropy";
    }
}
