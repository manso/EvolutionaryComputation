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
public class NumBestFound extends StopCriteria {

    public NumBestFound(int maxBests) {
        super(maxBests);
    }

    public NumBestFound() {
        this(1);
    }

    @Override
    public void updateValue(SimpleSolver s) {
        //set current value
        setCurrentValue(s.getHallOfFame().getNumberOFBestIndividuals());
    }

    @Override
    public boolean isDone(SimpleSolver s) {
        updateValue(s);
        //System.out.println("# Best Found " + getMaxValue() + " <= " + getCurrentValue() + " = " + (getMaxValue() <= getCurrentValue()));

        return getCurrentValue() >= getMaxValue();
    }
}
