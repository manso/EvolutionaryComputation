/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.bitString;

import genetic.gene.Gene;
import problem.Individual;
import genetic.population.Population;
import operator.mutation.Mutation;

/**
 *
 * @author arm
 */
public class FlipBitsInGene extends Mutation {

    @Override
    public  void doMutation(Individual ind) {
        //for all genes
        for (int i = 0; i < ind.getNumGenes(); i++) {
            Gene g = ind.getGene(i);
            //automatic probability
            double prob = probability == 0.0 ? 1.0 / g.getNumBits() : probability;
            //for all bits
            for (int j = 0; j < g.getNumBits(); j++) {
                if (random.nextDouble() < prob) {
                    ind.setIsEvaluated(false);
                    g.getAlels().invertBit(j);
                }
            }
        }
    }

    @Override
    public Population execute(Population pop) {
        Population childs = pop.getCleanCopie();
        //all individuals
        for (int i = 0; i < pop.getNumIndividuals(); i++) {
            //clone of original
            Individual ind = pop.getIndividual(i).getClone();
            ind.setNumCopys(1);
            //add clone mutated
            doMutation(ind);
            childs.addIndividual(ind);
        }

        return childs;
    }
     @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\nFlip on bit in Genes probabilisticaly");
        buf.append("\nThe probaility is applyed to gene bits");
        buf.append("\n\nParameters: <PROB> ");
        buf.append("\n    <PROB>  - Probability to mutate one bit");
        buf.append("\n             0 =  AUTO (1 / genome.lenght)");
        return buf.toString();
    }
}
