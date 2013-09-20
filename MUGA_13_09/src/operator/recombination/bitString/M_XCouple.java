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
public class M_XCouple extends Crossover {
    @Override
    public void doCrossover(Individual i1, Individual i2) {
        //build mask        
        BitField b1 = i1.getBits();
        BitField b2 = i2.getBits();
        //--------------------------------------------------------
        //Number of cuts is the mean of the copies
        int cuts = (i1.getNumCopies() + i2.getNumCopies()) / 2;
        if (cuts > NUM_CUTS) {
            cuts = 1+ cuts % NUM_CUTS;
        }
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
        int numCrossovers;
        numCrossovers = (int) ((pop.getNumGenotypes() * getProbability()) / 2.0);
        Individual i1, i2;
        //fazer os crossovers
        for (int i = 0; i < numCrossovers; i++) {
            i1 = pop.removeRandomGenotype();
            i2 = pop.removeRandomGenotype();
            //number of crossovers
            int numX = (i1.getNumCopies() + i2.getNumCopies()) / 2;
            for (int j = 0; j < numX; j++) {
                Individual child1 = i1.getClone();
                Individual child2 = i2.getClone();
                //update number of copies
                child1.setNumCopys(j + 1);
                child2.setNumCopys(j + 1);
                //performs crossover
                doCrossover(child1, child2);
                // add childrens to new population
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
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\nMultiset Crossover with Cuts in the genotype");
        buf.append("\nSelect two MI (P1 and P2)and execute many crossovers");
        buf.append("\n#crossovers = (P1.Copies + P2.copies) / 2");
        buf.append("\nNumber of cuts increase with the number of copies");
        buf.append("\nCrossover 1 <=> 1 cut");
        buf.append("\nCrossover 2 <=> 2 cut\n and so on.");
        buf.append("\nafter MAX CUTS restart number of cuts");
        buf.append("\n\nParameters: <PROB CROSSOVER><MAX CUTS> ");
        buf.append("\n<PROB CROSSOVER> - probability of crossover [0,1]");
        buf.append("\n<MAX CUTS> - Maximum of cuts");
        return buf.toString();
    }
    
    
}
