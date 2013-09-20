/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.bitString;

import problem.Individual;
import utils.BitField;

/**
 *
 * @author arm
 */
public class M_XCoupleGene extends M_XCouple {

    @Override
    public void doCrossover(Individual i1, Individual i2) {
        boolean changed = false;
        //for all Genes
        for (int i = 0; i < i1.getNumGenes(); i++) {
            BitField b1 = i1.getGene(i).getAlels();
            BitField b2 = i2.getGene(i).getAlels();
            //--------------------------------------------------------
            //Number of cuts is the mean of the copies
            int cuts = (i1.getNumCopies() + i2.getNumCopies()) / 2;
            if (cuts > NUM_CUTS) {
                cuts = 1 + cuts % NUM_CUTS;
            }
            //-------------------------------------------------------
            BitField mask = buildMask(b1.getNumberOfBits(), cuts);
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
}
