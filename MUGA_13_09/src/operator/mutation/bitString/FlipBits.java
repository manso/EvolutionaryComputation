/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.bitString;

import problem.Individual;
import genetic.population.Population;
import operator.mutation.Mutation;
import utils.BitField;
import utils.DynamicLoad;

/**
 *
 * @author arm
 */
public class FlipBits extends Mutation {

    @Override
    public void doMutation(Individual ind) {
        BitField bits = ind.getBits();
        //automatic probability
        double prob = probability == 0.0 ? 1.0 / bits.getNumberOfBits() : probability;
        boolean mut = false;
        for (int j = 0; j < bits.getNumberOfBits(); j++) {
            if (random.nextDouble() < prob) {
                mut = true;
                bits.invertBit(j);
            }
        }
        if (mut) {
            ind.setBits(bits);
        }
    }

    @Override
    public Population execute(Population pop) {
        Population childs = pop.getCleanCopie();
        for (int i = 0; i < pop.getNumIndividuals(); i++) {
            Individual ind = pop.getIndividual(i).getClone();
            ind.setNumCopys(1);
            doMutation(ind);
            childs.addIndividual(ind);
        }

        return childs;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\nFlip on bit in the genotype probabilisticaly");
        buf.append("\n\nParameters: <PROB> ");
        buf.append("\n    <PROB>  - Probability to mutate one bit");
        buf.append("\n             0 =  AUTO (1 / genome.lenght)");
        return buf.toString();
    }
}
