/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package statistics.elements;


import genetic.Solver.SimpleSolver;
import problem.Individual;

/**
 *
 * @author manso
 */
public class BestValue extends AbstractStatsElement{

    public double execute(SimpleSolver s) {
        setValue(s.getParents().getBestValue());
        //setValue(s.getHallOfFame().getBestFitness());
        return getValue();
    }
    public String toString(){
        return getName() + " " + getValue();
    }

    @Override
    public String getName() {
       return "Best Value";
    }
    
    /**
     * specify if the higher value is better
     * used in mean comparison from statistics
     * @return true if higher value is better
     */
    public boolean isMaximumBetter(){
        return Individual.typeOfOptimization;
    }
}
