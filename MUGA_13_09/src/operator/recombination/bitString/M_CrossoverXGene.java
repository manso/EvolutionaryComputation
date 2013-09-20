/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.bitString;

import genetic.gene.Gene;
import genetic.gene.GeneBinary;
import genetic.population.Population;
import operator.recombination.Recombination;
import problem.Individual;
import problem.bitString.KSPenalty.K50;
import utils.BitField;

/**
 *
 * @author arm
 */
public class M_CrossoverXGene extends M_XCouple {

    @Override
    public void doCrossover(Individual i1, Individual i2) {
        boolean changed = false;
        //for all Genes
        for (int i = 0; i < i1.getNumGenes(); i++) {
            BitField b1 = i1.getGene(i).getAlels();
            BitField b2 = i2.getGene(i).getAlels();
            //build Mask
            BitField mask = buildMask(b1.getNumberOfBits(), NUM_CUTS);
            for (int k = 0; k < mask.getNumberOfBits(); k++) {
                //one in mask
                if (mask.getBit(k) && b1.getBit(k) != b2.getBit(k)) {
                    changed = true;
                    //change bits
                    b1.setBit(k, !b1.getBit(k));
                    b2.setBit(k, !b2.getBit(k));
                }
            }//change bits
        }
        //bits are changed
        if (changed) {
            i1.setIsEvaluated(false);
            i2.setIsEvaluated(false);
        }

    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nMultiset Crossover with Cuts in Genes");
        buf.append("\nSelect two MI (P1 and P2)and execute many crossovers");
        buf.append("\n#crossovers = (P1.Copies + P2.copies) / 2");
        buf.append("\nNumber of cuts increase with the number of copies");
        buf.append("\nCrossover 1 <=> 1 cut in each gene");
        buf.append("\nCrossover 2 <=> 2 cut in each gene");
        buf.append("\nand so on.");
        buf.append("\n\nParameters: <PROB CROSSOVER> ");
        buf.append("\n<PROB CROSSOVER> - probability of crossover [0,1]");

        return buf.toString();
    }
}
