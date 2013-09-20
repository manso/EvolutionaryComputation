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
public class SucessRate extends AbstractStatsElement{

    public SucessRate() {
    }
   
    @Override
    public double execute(SimpleSolver s) {
        int val = s.getHallOfFame().getNumberOFBestIndividuals();
        setValue( val > 0 ? 100.0 : 0.0);
        return getValue();
    }
    @Override
    public String toString(){
        return "Sucess Rate %";
    }

    @Override
    public String getName() {
       return toString();
    }
}
