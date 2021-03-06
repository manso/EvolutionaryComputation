/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package statistics.elements;


import genetic.Solver.SimpleSolver;

/**
 *
 * @author manso
 */
public class NumberOfEvaluations extends AbstractStatsElement{

    public NumberOfEvaluations() {        
    }
    

    @Override
    public double execute(SimpleSolver s) {
        //setValue(s.getParents().getEvaluations());
        setValue(s.EVALUATIONS);
        return getValue();
    }
    @Override
    public String toString(){
        return getName() + " " + getValue();
    }

    @Override
    public String getName() {
       return "# Evaluations";
    }
}
