/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.stopCriteria;

import genetic.Solver.SimpleSolver;
import java.util.StringTokenizer;
import utils.DynamicLoad;

/**
 *
 * @author manso
 */
public abstract class StopCriteria {

    //precision
    private double currentValue = 0;
    private double maxValue = 0;

    public StopCriteria(double max) {
        this.maxValue = max;
    }

    public double getProgress() {
        return currentValue / maxValue;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    /**
     * verify if the solver is done the job
     *
     * @param s solver
     * @return if done
     */
    public abstract void updateValue(SimpleSolver s);

    public abstract boolean isDone(SimpleSolver s);

    public StopCriteria getClone() {
        StopCriteria stop = (StopCriteria) DynamicLoad.makeObject(this);
        stop.setCurrentValue(getCurrentValue());
        stop.setMaxValue(getMaxValue());
        return stop;
    }

    /**
     * Set parameters to Object
     *
     * @param str parameters to set
     */
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            maxValue = Double.valueOf(iter.nextToken());
        }

    }
    /**
     * Set parameters to Object
     *
     * @param str parameters to set
     */
    public String getParameters() {
        return maxValue +"";
    }

    /**
     * information about Stop criteria
     *
     * @return information
     */
    public String getInformation() {
        StringBuffer txt = new StringBuffer();
        txt.append(toString());
        txt.append("\n Parameters : <VALUE>");
        txt.append("\n     <VALUE> target value");
        return txt.toString();

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " <" + getMaxValue() + ">";
    }

    /**
     * @return the currentValue
     */
    public double getCurrentValue() {
        return currentValue;
    }
    
     /**
     * @return the currentValue
     */
    public double getAxisGraphValue() {
        return currentValue;
    }

    /**
     * @param currentValue the currentValue to set
     */
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * @return the maxValue
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
    
    public void reset(){
        currentValue = 0;
    }
}
