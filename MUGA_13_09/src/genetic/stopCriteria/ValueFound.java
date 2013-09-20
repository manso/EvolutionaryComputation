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
public class ValueFound extends StopCriteria {
    
    double precision;

    public ValueFound(double maxValue) {
        super(maxValue);
    }

    public ValueFound() {
        this(0);
    }

    @Override
    public void updateValue(SimpleSolver s) {
        //set current value
        setCurrentValue( s.getBest().getBestFitness());
    }

    @Override
    public boolean isDone(SimpleSolver s) {
       if( s.getHallOfFame().getGood().isEmpty())
           return false;
        updateValue(s);
        
       if( s.getTemplate().typeOfOptimization == s.getTemplate().MAXIMIZE){
           if( getCurrentValue() + precision > getMaxValue())
               return true;
           return false;
       }
          if( getCurrentValue() -  precision < getMaxValue())
               return true;
           return false;
        
    }
    /**
     * Set parameters to Object
     *
     * @param str parameters to set
     */
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            super.setParameters(iter.nextToken());
        }
        if (iter.hasMoreTokens()) {
            precision = Double.valueOf(iter.nextToken());
        }

    }
    /**
     * Set parameters to Object
     *
     * @param str parameters to set
     */
    @Override
    public String getParameters() {
        return super.getParameters() + " " + precision;
    }
        /**
     * information about Stop criteria
     *
     * @return information
     */
    @Override
    public String getInformation() {
        StringBuilder txt = new StringBuilder();
        txt.append(toString());
        txt.append("\n Parameters : <VALUE><PRECISION>");
        txt.append("\n  <VALUE> target value ");
        txt.append("\n  <PRECISION> precision of real values");
        return txt.toString();

    }
    @Override
    public ValueFound getClone() {
        ValueFound stop = (ValueFound) DynamicLoad.makeObject(this);
        stop.setCurrentValue(getCurrentValue());
        stop.setMaxValue(getMaxValue());
        stop.precision = this.precision;
        return stop;
    }
}
