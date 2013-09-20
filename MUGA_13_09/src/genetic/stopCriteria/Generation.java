/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.stopCriteria;

import genetic.Solver.SimpleSolver;

/**
 *
 * @author manso
 */
public class Generation extends StopCriteria {

    public Generation(int maxGeneration) {
        super(maxGeneration);
    }

    public Generation() {
        this(1000);
    }

    @Override
    public void updateValue(SimpleSolver s) {
        //set current value
        setCurrentValue(s.GENERATION);
    }

    @Override
    public boolean isDone(SimpleSolver s) {
        updateValue(s);
       // System.out.println("GENERATION " + getMaxValue() + " <= " + getCurrentValue() + " = " + (getMaxValue() <= getCurrentValue()));
        return getMaxValue() <= getCurrentValue();
    }
}
