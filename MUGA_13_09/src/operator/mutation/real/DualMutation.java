/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.real;

import genetic.population.Population;
import java.util.ArrayList;
import java.util.StringTokenizer;
import operator.mutation.Mutation;
import problem.Individual;

public class DualMutation extends Mutation {
     public static double ALPHA = 0.1;
    public static double HIPERMUTATION = 1;
    
    public double getPerturbation(){
        return random.nextGaussian();
    }
    
      public ArrayList<Individual> mutate(Individual ind, double probability) {
        //mutants of the individual
        ArrayList<Individual> mut = new ArrayList<Individual>();
        //Dimension of the mutation
        double dimension = ind.getDimension() * ALPHA;
        //for all copies
        for (int numMI = 0; numMI < ind.getNumCopies(); numMI++) {
            //clone of multiindividual
            Individual mutant1 = ind.getClone();
            mutant1.setNumCopys(1);
            Individual mutant2 = ind.getClone();
            mutant2.setNumCopys(1);
            //Shriking factor
            double shrinkFactor = 1.0 / Math.pow(2, numMI);
            //for all genes
            for (int i = 0; i < mutant1.getNumGenes(); i++) {
                //probility of mutation increase with number of copies
                //if Hipermutation is > 0
                if (random.nextDouble() < probability
                        + probability * numMI * HIPERMUTATION) {
                    double perturbation = getPerturbation() * shrinkFactor * dimension;
                    //System.out.println(perturbation);
                    //mutate gene summing perurbation
                    mutant1.setGeneValue(i, ind.getGeneValue(i) + perturbation);
                    //mutate gene subtracting perurbation
                    mutant2.setGeneValue(i, ind.getGeneValue(i) - perturbation);
                }
            }
            //if the individual is mutated
            if (!mutant1.isEvaluated()) {
                //put mutants in the population   
                mut.add(mutant1);
                mut.add(mutant2);
            } //individual not mutated
            else {
                mut.add(mutant1);
            }

        }
        return mut;
    }
      @Override
    public Population execute(Population pop) {
        //----------------- AUTOMATIC Probability Mutation ---------------
        double mutProbability = getProbability();
        //----------------------------------------------------------------
        //make new population
        Population offspring = pop.getCleanCopie();
        //process all the individuals
        for (int i = 0; i < pop.getNumGenotypes(); i++) {
            //make a clone
            Individual ind = pop.getGenotype(i);
            //mutate the individual
            ArrayList<Individual> mut = mutate(ind, mutProbability);
            //add to the new population
            for (Individual mutInd : mut) {
                offspring.addIndividual(mutInd, 1);
            }
        }
        return offspring;
    }
      
      @Override
    public void setParameters(String str) {
        if (str == null || str.trim().length() == 0) {
            return;
        }
        StringTokenizer par = new StringTokenizer(str);
        double oldApha = ALPHA;
        double oldHiper = HIPERMUTATION;
        try {
            probability = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            probability = DEFAULT_PROBABILITY;
        }
        try {
            ALPHA = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            ALPHA = oldApha;
        }
        try {
            HIPERMUTATION = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            HIPERMUTATION = oldHiper;
        }
    }
      
       @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + probability + ">" + "<" + ALPHA + ">" + "<" + HIPERMUTATION+">";
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nMultiset Dual Mutation");
        buf.append("\nCreate to oposite mutants with Gaussian");
        buf.append("\nMutate each individual with mutation <PROBABILITY>");
        buf.append("\nIncrease for each copy with <HIPERMUTATION>");
        buf.append("\nand Shrinking the interval by <ALPHA>");
        buf.append("\n\nParameters: <Probability><ALPHA><HIPERMUTATION>");
        buf.append("\n<Probability> [0,1]- prob. to mut one gene");
        buf.append("\n<ALPHA> [0,1]- Interval Shrinking factor");
        buf.append("\n<HIPERMUTATION> [0,1]-increase probability ");
        buf.append("\n\n@ EPIA2011");
        return buf.toString();
    }
     @Override
    public Mutation getClone() {
        DualMutation clone = (DualMutation) super.getClone();
        clone.ALPHA = this.ALPHA;
        clone.HIPERMUTATION = this.HIPERMUTATION;
        return clone;
    }

}
