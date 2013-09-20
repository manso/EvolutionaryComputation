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
public class Variance extends AbstractStatsElement{

    public Variance() {        
    }

     public Variance(SimpleSolver s) {
         execute(s);
    }

    public double execute(SimpleSolver s) {
        double mean = (new Mean(s)).getValue();
        double sum = 0;
        int num = 0;
        for (int i = 0; i < s.getParents().getNumGenotypes(); i++) {
            Individual ind = s.getParents().getGenotype(i);
            sum+= Math.pow( ind.getFitness() - mean , 2.0)*ind.getNumCopies();
            num+=ind.getNumCopies();
        }
        setValue((1.0 / (num)) * sum);
        return getValue();
    }
    public String toString(){
        return "Var " + getValue();
    }
    @Override
    public String getName() {
       return "Variance";
    }
    /**
     * specify if the higher value is better
     * used in mean comparison from statistics
     * @return true if higher value is better
     */
    public boolean isMaximumBetter(){
        return false;
    }

}
