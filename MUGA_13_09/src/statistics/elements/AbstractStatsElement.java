/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package statistics.elements;

import genetic.Solver.SimpleSolver;
import java.io.Serializable;
import utils.DynamicLoad;


/**
 *
 * @author manso
 */
public abstract class AbstractStatsElement implements Serializable{
    double value =0.0;
    //to calculate standard deviation in means
    double stdDev = 0.0;
    //maximum
    private double max = 0.0;
    //minimum
    private double min = 0.0;

    public AbstractStatsElement() {
       value = 0.0;
    }

//    public  double execute( Solver pop){
//        return execute(pop.getParents());
//    }
    public abstract double execute( SimpleSolver s);
    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @return the value
     */
    public double getStdDev() {
        return stdDev;
    }

    public abstract String getName();
    public String toString(){
        return getName() + " " + getValue();
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @param value the value to set
     */
    public void setStdDev(double value) {
        this.stdDev = value;
    }

    public AbstractStatsElement getClone(){
        AbstractStatsElement  elem = (AbstractStatsElement)DynamicLoad.makeObject(this);
        elem.value = this.value;
        elem.stdDev = this.stdDev;
        elem.setMax(this.getMax());
        elem.setMin(this.getMin());
        return elem;
        
    }

    /**
     * @return the max
     */
    public double getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * @return the min
     */
    public double getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(double min) {
        this.min = min;
    }
    /**
     * specify if the higher value is better
     * used in mean comparison from statistics
     * @return true if higher value is better
     */
    public boolean isMaximumBetter(){
        return true;
    }

    

}
