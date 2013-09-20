/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package statistics.elements;

import genetic.Solver.SimpleSolver;

/**
 *
 * @author arm
 */
public class NumberOfIndividuals extends AbstractStatsElement {

    @Override
    public double execute(SimpleSolver s) {
        setValue(s.getParents().getNumIndividuals());
        return getValue();
    }

    @Override
    public String getName() {
        return "# Individuals";
    }

}
