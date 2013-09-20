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
public class Mean extends AbstractStatsElement{

    public Mean() {
    }
    public Mean(SimpleSolver s) {
        execute(s);
    }

    public double execute(SimpleSolver s) {
        double sum = 0;
        int num = 0;
        for (int i = 0; i < s.getParents().getNumGenotypes(); i++) {
            Individual ind = s.getParents().getGenotype(i);
            sum+= ind.getFitness()*ind.getNumCopies();
            num+=ind.getNumCopies();
        }
        setValue(sum / num);
        return getValue();
    }
    public String toString(){
        return "Mean " + getValue();
    }
    @Override
    public String getName() {
       return "Mean of Parents";
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
