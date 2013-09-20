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
public class BestValueFound extends AbstractStatsElement{

    public double execute(SimpleSolver s) {
        setValue(s.getHallOfFame().getBestFitness());
        //setValue(s.getHallOfFame().getBestFitness());
        return getValue();
    }
    public String toString(){
        return getName() + " " + getValue();
    }

    @Override
    public String getName() {
       return "Best Value Found";
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
