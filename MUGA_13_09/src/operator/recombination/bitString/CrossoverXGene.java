/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.bitString;

import java.util.Arrays;
import problem.Individual;
import utils.BitField;

/**
 *
 * @author arm
 */
public class CrossoverXGene extends Crossover {

    @Override
    public void doCrossover(Individual i1, Individual i2) {
        boolean changed = false;
        //for all Genes
        for (int i = 0; i < i1.getNumGenes(); i++) {
            BitField b1 = i1.getGene(i).getAlels();
            BitField b2 = i2.getGene(i).getAlels();
            //build Mask
             BitField mask = buildMask(b1.getNumberOfBits() , NUM_CUTS);
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
        if( changed){
            i1.setIsEvaluated(false);
            i2.setIsEvaluated(false);
        }

    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\nCrossover with cuts in Genes");
        buf.append("\nPerforms #cuts in each gene ");
        buf.append("\n\nParameters: <PROB CROSSOVER> <#CUTS>");
        buf.append("\n<PROB CROSSOVER> - probability of crossover [0,1]");
        buf.append("\n<#CUTS> - number of cuts (>=1) ");
        return buf.toString();
    }
}
