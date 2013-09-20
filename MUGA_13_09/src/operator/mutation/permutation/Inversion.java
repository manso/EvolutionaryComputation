/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.permutation;

import genetic.population.Population;
import operator.mutation.Mutation;
import problem.PRM_Individual;

/**
 *
 * @author manso
 */
public class Inversion extends MutationPRM {

    @Override
    public Population execute(Population pop) {
        double mutProb = probability;
        if (probability == 0) {
            mutProb = 1.0 / pop.getGenotype(0).getNumGenes();
        }

        //----------------------------------------------------------------
        //make new population
        Population offspring = pop.getCleanCopie();
        //process all the individuals
        for (int i = 0; i < pop.getNumIndividuals(); i++) {
            PRM_Individual ind = (PRM_Individual) pop.getIndividual(i).getClone();
            mutate(ind, mutProb);
            offspring.addGenotype(ind);

        }
        return offspring;
    }

    /**
     * mutate one individual
     *
     * @param ind individual to mutate
     */
    public void mutate(PRM_Individual ind, double mutProb) {
        int[] genes = ind.getGeneValues();
        for (int index1 = 0; index1 < genes.length; index1++) {
            if (random.nextDouble() < mutProb) {
                int index2 = random.nextInt(genes.length);
                int min = Math.min(index1, index2);
                int max = Math.max(index1, index2);
                while (min < max) {
                    int aux = genes[min];
                    genes[min] = genes[max];
                    genes[max] = aux;
                    min++;
                    max--;
                }
                int aux = genes[index1];
                genes[index1] = genes[index2];
                genes[index2] = aux;

            }
        }
        ind.setGeneValues(genes);
    }

    /**
     * information of mutation
     *
     * @return information
     */
    public String toString() {
        StringBuffer str = new StringBuffer(super.toString());
        str.append("\nSwap Genes Mutation:\n");
        str.append("Swaps two genes\n");
        str.append(" x x G1 x G2 x \n");
        str.append(" x x G2 x G1 x \n");
        return str.toString();
    }
}
