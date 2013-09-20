/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation;

import genetic.population.Population;
import operator.GeneticOperator;
import problem.Individual;

/**
 *
 * @author arm
 */
public abstract class Mutation extends GeneticOperator {

    protected static final double DEFAULT_PROBABILITY = 0;
    protected double probability = DEFAULT_PROBABILITY;

    public abstract Population execute(Population pop);

    /**
     * method to be overwrited in the subclasses to mutate only one individual
     * originaly is to be used in coevolution 
     * @param ind 
     */
    public void doMutation(Individual ind) {
    }

    /**
     * @return the probability
     */
    public double getProbability() {
        return probability;
    }

    /**
     * @return the probability
     */
    public double getProbability(Population pop) {
        if (probability > 0) {
            return probability;
        }
        return 0.05 / pop.getGenotype(0).getNumGenes();
    }

    /**
     * @param probability the probability to set
     */
    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        if (probability <= 0) {
            return getClass().getSimpleName() + "<AUTO>";
        } else {
            return getClass().getSimpleName() + "<" + this.probability + ">";
        }
    }

    @Override
    public String getInformation() {
       StringBuilder buf = new StringBuilder(toString());
        buf.append("\n\nParameters: <PROB> ");
        buf.append("\n    <PROB>  - Probability to mutate one bit");
        buf.append("\n             0 =  AUTO (1 / genome.lenght)");
        return buf.toString();
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
    }
     @Override
    public String getParameters() {
         return probability + "";
    }
  

    @Override
    public Mutation getClone() {
        Mutation clone = (Mutation) super.getClone();
        clone.probability = this.probability;
        return clone;
    }
    
}
