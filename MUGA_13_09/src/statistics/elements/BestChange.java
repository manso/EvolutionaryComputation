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
public class BestChange extends AbstractStatsElement{
    double notChange = 0;
    double best = 0;
    @Override

    public double execute(SimpleSolver s) {
        //get last Element
         BestChange last =  (BestChange)s.getStats().getLastElement(this);
         best = s.getParents().getBestValue();
        if( last == null || last.best != best){
            notChange =0;
          //  System.out.println("BEST " + best);
        }else{
            notChange = last.notChange +1;
        }
        
        setValue(notChange);
        return getValue();
    }
    public String toString(){
        return getName() + " " + getValue();
    }

    @Override
    public String getName() {
       return "Change Best Value";
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
