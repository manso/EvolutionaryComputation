/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.bitString;

import genetic.population.Population;
import operator.recombination.Recombination;
import problem.Individual;
import utils.BitField;
import utils.DynamicLoad;

/**
 *
 * @author arm
 */
public class M_UniformCrossover extends UniformCrossover {

    @Override
    public Population execute(Population pop) {
        Population childs = (Population) DynamicLoad.makeObject(pop);
        int numCrossovers;
        numCrossovers = (int) ((pop.getNumGenotypes() * getProbability()) / 2.0);
        Individual i1, i2;
        //fazer os crossovers
        for (int i = 0; i < numCrossovers; i++) {
            i1 = pop.removeRandomGenotype();
            i2 = pop.removeRandomGenotype();
            //number of crossovers
            int numX = (i1.getNumCopies() + i2.getNumCopies() + 1) / 2;
            for (int copy = 0; copy < numX; copy++) {
                Individual child1 = i1.getClone();
                Individual child2 = i2.getClone();

                child1.setNumCopys(1);
                child2.setNumCopys(copy + 1); //change mask probabilities
                PROB_OF_ONE = 1.0 / (copy + 2);
                doCrossover(child1, child2);
                childs.addIndividual(child1, 1);
                childs.addIndividual(child2, 1);
            }
            //even individuals 
            if ((i1.getNumCopies() + i2.getNumCopies()) % 2 != 0) {
                //append individual with more copies
                if (i1.getNumCopies() > i2.getNumCopies()) {
                    childs.addIndividual(i1.getClone(), 1);
                } else {
                    childs.addIndividual(i2.getClone(), 1);
                }
            }
        }
        //insert remain individuals
        childs.appendPopulation(pop);
        return childs;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nMultiset Uniform Crossover in the genotype");
        buf.append("\nSelect two MI (P1 and P2)and execute many crossovers");
        buf.append("\nThe probability of \"1\" in mask change belongs crossovers");
        buf.append("\n1/2 - 1/3 - 1/4 and so on");
        buf.append("\n\nParameters: <PROB CROSSOVER> ");
        buf.append("\n<PROB CROSSOVER> - probability of crossover [0,1]");
        return buf.toString();
    }
}
