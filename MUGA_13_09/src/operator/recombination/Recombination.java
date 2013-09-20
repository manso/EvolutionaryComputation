/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination;

import genetic.population.Population;
import operator.GeneticOperator;
import problem.Individual;

/**
 *
 * @author arm
 */
public abstract class Recombination extends GeneticOperator {

    static protected final double DEFAULT_PROBABILITY = 0.75;
    protected double probability = DEFAULT_PROBABILITY;

    public abstract Population execute(Population pop);
    
    //--------------------------------------------------------
    // METER IR ABSCTRACTO
    //--------------------------------------------------------
    public  void doCrossover(Individual i1, Individual i2){}

    /**
     * @return the probability
     */
    public double getProbability() {
        if (probability <= 0) {
            return DEFAULT_PROBABILITY;
        }
        return probability;
    }

    /**
     * @param probability the probability to set
     */
    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + probability + ">";
    }

    public String getInformation() {
        return toString();
    }

    @Override
    public void setParameters(String str) {
        if (str == null || str.trim().length() == 0) {
            return;
        }
        try {
            probability = Double.parseDouble(str);
        } catch (Exception e) {
            probability = DEFAULT_PROBABILITY;
        }
        probability = probability < 0 || probability > 1 ? 0.75 : probability;
    }
    
     @Override
    public String getParameters() {
         return probability + "";
    }

    @Override
    public Recombination getClone() {
        Recombination mut = (Recombination) super.getClone();
        mut.probability = this.probability;
        return mut;
    }
}
