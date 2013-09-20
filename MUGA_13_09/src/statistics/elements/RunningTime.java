/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.elements;

import genetic.Solver.SimpleSolver;
import java.util.Date;
import problem.Individual;

/**
 *
 * @author manso
 */
public class RunningTime extends AbstractStatsElement {

    long time = 0;

    public double execute(SimpleSolver s) {
        RunningTime first = (RunningTime) s.getStats().getFirstElement(this);
        time = System.currentTimeMillis();
        double value = 0.0;
        if (first != null ) {     
            value = (System.currentTimeMillis() - first.time)/1000.0 ;
        }
//        System.out.println("TIME " + value + " " + time);
        setValue(value);
        return getValue();
    }

    public String toString() {
        return getName() + " " + getValue();
    }

    @Override
    public String getName() {
        return "Running Time";
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
