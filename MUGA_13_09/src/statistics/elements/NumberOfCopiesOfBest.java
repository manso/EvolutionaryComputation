/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.elements;

import genetic.Solver.SimpleSolver;
import java.util.Iterator;
import problem.Individual;

/**
 *
 * @author manso
 */
public class NumberOfCopiesOfBest extends AbstractStatsElement {

    public NumberOfCopiesOfBest() {
    }

    public double execute(SimpleSolver s) {
       int copies = s.getParents().getBestGenotype().getNumCopies();       
        setValue(copies);
        return getValue();
    }

    public String toString() {
        return "Copies of Best Genotype";
    }

    @Override
    public String getName() {
        return toString();
    }
}
