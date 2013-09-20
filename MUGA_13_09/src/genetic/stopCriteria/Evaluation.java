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
public class Evaluation extends StopCriteria {

    public Evaluation(int maxEvaliations) {
        super(maxEvaliations);
    }

    public Evaluation() {
        this(50000);
    }

    @Override
    public void updateValue(SimpleSolver s) {
        //set current value
        setCurrentValue(s.EVALUATIONS);
    }

    @Override
    public boolean isDone(SimpleSolver s) {
        updateValue(s);
        // System.out.println("EVALUATIONS " + getMaxValue() + " <= " + getCurrentValue() + " = " + (getMaxValue() <= getCurrentValue()));
        return getMaxValue() <= getCurrentValue();
    }
    
    @Override
    public String getName() {
        return "Evaluations";
    }
}
