/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.bitString;

import genetic.population.Population;
import problem.Individual;
import utils.BitField;

/**
 *
 * @author arm
 */
public class M_XHarem extends Crossover {
     @Override
    public void doCrossover(Individual i1, Individual i2) {
        //build mask        
        BitField b1 = i1.getBits();
        BitField b2 = i2.getBits();
        //--------------------------------------------------------
//        Number of cuts is the mean of the copies
        int cuts = (i1.getNumCopies() + i2.getNumCopies()) / 2;
        if (cuts > NUM_CUTS) {
            cuts = 1+ cuts % NUM_CUTS;
        }
//        int cuts =NUM_CUTS;
        //-------------------------------------------------------
        BitField mask = buildMask(b1.getNumberOfBits(), cuts);
        for (int i = 0; i < mask.getNumberOfBits(); i++) {
            //one in mask
            if (mask.getBit(i) && b1.getBit(i) != b2.getBit(i)) {
                //swap bits
                boolean aux = b1.getBit(i);
                b1.setBit(i, b2.getBit(i));
                b2.setBit(i, aux);
            }
        }
        i1.setBits(b1);
        i2.setBits(b2);
    }

    @Override
    public Population execute(Population pop) {
        Population childs = pop.getCleanCopie();
        Individual child1, child2, male;
        //fazer os crossovers
        while (pop.getNumGenotypes() > 1) {
            //remove the male individual
            male = pop.removeRandomGenotype();
            //probability to not make crossover
            if (random.nextDouble() > getProbability()) {
                childs.addGenotype(male);
                continue;
            }
            //total of individuals
            int numXs = male.getNumCopies();
            //perform many X
            while (numXs > 0) {
                //are individuals to crossover
                if (pop.getNumGenotypes() > 0) {
                    child1 = male.getClone();
                    child2 = pop.removeRandomIndividual();
                    //update number of copies
                    child1.setNumCopys(numXs+1);
                    child2.setNumCopys(1);
                    //performs crossover
                    doCrossover(child1, child2);
                    // add childrens to new population
                    childs.addIndividual(child1, 1);
                    childs.addIndividual(child2, 1);
                    numXs--;
                } ////not have more individuals
                else {
                    childs.addIndividual(male, male.getNumCopies() - numXs);
                    break;
                }
            }
        }
        //insert remain individuals
        childs.appendPopulation(pop);
        return childs;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\nMultiset Crossover");
        buf.append("\nSelect a male individual ");
        buf.append("\n for each copy of individual");
        buf.append("\n   select a female");
        buf.append("\n   crossover with increased #cuts");
        buf.append("\nafter MAX CUTS restart number of cuts");
        buf.append("\n\nParameters: <PROB CROSSOVER><MAX CUTS> ");
        buf.append("\n<PROB CROSSOVER> - probability of crossover [0,1]");
        buf.append("\n<MAX CUTS> - Maximum of cuts");
        return buf.toString();
    }
}
