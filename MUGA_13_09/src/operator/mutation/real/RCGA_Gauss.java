/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.real;

import genetic.population.Population;
import java.util.StringTokenizer;
import operator.mutation.Mutation;
import problem.Individual;

/**
 *
 * @author manso
 */
public class RCGA_Gauss extends Mutation {

    public double EXPAND = 0.1;

    public double getMutation() {
        return random.nextGaussian();
    }

    public Individual mutate(Individual ind, double probability) {

        double dimension = ind.getDimension();
       
        Individual mutant = ind;
        //for all genes
        for (int i = 0; i < mutant.getNumGenes(); i++) {
            //probility of mutation increase with number of copies
            if (random.nextDouble() < probability) {
                double mutVal = getMutation() * dimension * EXPAND;
                mutant.setGeneValue(i, ind.getGeneValue(i) + mutVal);
            }
        }
        return mutant;
    }

    @Override
    public Population execute(Population pop) {
        double mutProbability = getProbability();
        //automatic probability
        if (probability == 0) {
            probability = 0.1 / pop.getIndividual(0).getNumGenes();
        }
        //----------------------------------------------------------------
        //make new population
        Population offspring = pop.getCleanCopie();
        //process all the individuals
        for (int i = 0; i < pop.getNumIndividuals(); i++) {
            //make a clone
            Individual ind = pop.getIndividual(i);
            //mutate the individual
            Individual mut = mutate(ind.getClone(), mutProbability);
            //add to the new population
            offspring.addIndividual(mut, 1);
        }
        return offspring;
    }

    @Override
    public String toString() {
        return super.toString() + "<" + EXPAND + ">";
    }

    @Override
    public void setParameters(String str) {
        StringTokenizer par = new StringTokenizer(str);
        double oldProb = probability;
        double old = EXPAND;
        try {
            probability = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            probability = oldProb;
        }
        try {
            EXPAND = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            EXPAND = old;
        }
    }

    @Override
    public RCGA_Gauss getClone() {
        RCGA_Gauss clone = (RCGA_Gauss) super.getClone();
        clone.probability = this.probability;
        clone.EXPAND = this.EXPAND;
        return clone;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(toString());
        buf.append("\nMutation Using Gaussian( 0, 1) distribution");
        buf.append("\nFactor of mutation <" + EXPAND + ">");
        buf.append("\nParameters <Probability><DIM>");
        buf.append("\n<Probability> to mutate one gene");
        buf.append("\n<DIM> dimension of mutation [0,1]");
        return buf.toString();
    }
}
