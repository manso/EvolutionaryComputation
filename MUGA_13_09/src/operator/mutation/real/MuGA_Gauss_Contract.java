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
public class MuGA_Gauss_Contract extends Mutation {

    double EXPAND = 0.1;
    int DYNAMIC = 1;

    public double getMutation() {
        return random.nextGaussian();
    }

    public Individual mutate(Individual ind, int copyNumber, double probability) {
        //automatic probability
        if (probability == 0) {
            probability = 0.1 / ind.getNumGenes();
        }
        if (DYNAMIC > 0) {
            probability *=  Math.pow(2,copyNumber-1);
        }
        //number of copie shrink interval
        double amplitud = (ind.getDimension() * EXPAND) / Math.pow(2,copyNumber-1);
        //for all genes
        for (int i = 0; i < ind.getNumGenes(); i++) {
            //probility of mutation increase with number of copies dynamicaly
            if (random.nextDouble() < probability) {
                //mutate gene 
                double mutationValue = getMutation() * amplitud;
                ind.setGeneValue(i, ind.getGeneValue(i) + mutationValue);
            }
        }
        return ind;
    }

    @Override
    public Population execute(Population pop) {
        double mutProbability = getProbability();
        //----------------------------------------------------------------
        //make new population
        Population offspring = pop.getCleanCopie();
        //process all the individuals
        for (int i = 0; i < pop.getNumGenotypes(); i++) {
            //process all copies
            Individual ind = pop.getGenotype(i);
            for (int k = 0; k < ind.getNumCopies(); k++) {
                //mutate single individual
                //making a clone of the individual
                offspring.addIndividual(mutate(ind.getClone(), k + 1, mutProbability), 1);
            }
        }
        return offspring;
    }
    static String logic[] = {"Static", "Dynamic"};

    @Override
    public String toString() {
        return super.toString() + "<" + EXPAND + "><" + logic[DYNAMIC] + ">";
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(toString());
        buf.append("\nExpand MultiIndividuals into Single Individuals");
        buf.append("\nMutation Using Gaussian( 0, 1) distribution");
        buf.append("\nShrink Factor (" + EXPAND + ") Probability (" + logic[DYNAMIC] + ")");
        buf.append("\nShrink the range of mutation and dynamic ");
        buf.append("\nincrease probability by the #copies\n");
        buf.append("\nParameters <PROBABILITY><Dim><TYPE>");
        buf.append("\n<PROBABILITY> to mutate one gene[0,1]");
        buf.append("\n<DIM> dimension of mutation [0,1]");
        buf.append("\n<TYPE> type of probability [0-Static 1-Dynamic]");
        return buf.toString();
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
        try {
            DYNAMIC = Integer.parseInt(par.nextToken());
            DYNAMIC = DYNAMIC > 0 ? 1 : 0;
        } catch (Exception e) {
            DYNAMIC = 0;
        }
    }

    @Override
    public MuGA_Gauss_Contract getClone() {
        MuGA_Gauss_Contract clone = (MuGA_Gauss_Contract) super.getClone();
        clone.probability = this.probability;
        clone.EXPAND = this.EXPAND;
        clone.DYNAMIC = this.DYNAMIC;
        return clone;
    }
}