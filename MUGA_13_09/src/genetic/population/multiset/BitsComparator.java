package genetic.population.multiset;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import problem.Individual;
import utils.BitField;

/**
 *
 * @author manso
 */
public class BitsComparator extends IndividualComparator {

    @Override
    public int compare(Individual i1, Individual i2) {
        //for all genes
        for (int nGene = 0; nGene < i1.getNumGenes(); nGene++) {
            //alelels of the genes
            BitField b1 = i1.getGene(nGene).getAlels();
            BitField b2 = i2.getGene(nGene).getAlels();
            for (int i = 0; i < b1.getNumberOfBits(); i++) {
                //bits are differents
                if (b1.getBit(i) != b2.getBit(i)) {
                    if (b1.getBit(i)) {
                        return -1;
                    }
                    return 1;
                }//bits difs
            }//for bits
        }
        return 0;
    }
}
