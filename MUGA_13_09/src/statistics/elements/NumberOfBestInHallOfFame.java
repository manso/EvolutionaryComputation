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
public class NumberOfBestInHallOfFame extends AbstractStatsElement{

    public NumberOfBestInHallOfFame() {
    }
   
    @Override
    public double execute(SimpleSolver s) {
        setValue(s.getHallOfFame().getNumberOFBestIndividuals());
        return getValue();
    }
    public String toString(){
        return "# Best in Hall of Fame";
    }

    @Override
    public String getName() {
       return toString();
    }
}
