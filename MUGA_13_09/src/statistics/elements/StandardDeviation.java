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
public class StandardDeviation extends AbstractStatsElement{

    public StandardDeviation() {
    }

    @Override
    public double execute(SimpleSolver s) {
        double var = (new Variance(s)).getValue();
        setValue(Math.sqrt(var));
        return getValue();
    }

    public String toString(){
        return "STD " + getValue();
    }

    @Override
    public String getName() {
       return "Standard Deviation";
    }


}
