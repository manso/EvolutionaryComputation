/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem;

import genetic.gene.Gene;
import java.security.SecureRandom;
import java.util.Random;
import utils.Metrics;

/**
 *
 * @author manso
 */
public abstract class IndividualPermutation extends Individual {
    public static Random random = new SecureRandom();
    public IndividualPermutation(boolean maximize) {
        super(maximize);
    }
    /**
     * Every individual of permutation optimization<br>
     * must descend of this individual<br>
     * fill random the position of genes<br>
     *   [ 1 2 3 4 5 6 ] <br>
     * =>[ 3 1 4 2 6 5 ]<br>
     */
    public void fillRandom() {       
        int max = genome.getNumGenes();
        Gene aux;
        while (max > 1) {
         //   System.out.print(this.toStringPhenotype() + " SWAP");
            SwapGenes(random.nextInt(max), max-1);
          //  System.out.println(this.toStringGenotype());
            max--;
        }
    }




    /**
     * Swap two genes
     *   [ x 1 x 2 x x ] <br>
     * <br>
     *   [ x 2 x 1 x x ] <br>
     */
    public void SwapGenes(int index1, int index2) {
        Gene g1 = genome.getGene(index1);
        Gene g2 = genome.getGene(index2);
        genome.replaceGene(index1, g2);
        genome.replaceGene(index2, g1);
    }

    @Override
    public double distanceTo(Individual other) {
        int dist = 0;
       for (int i = 0; i < this.getNumGenes(); i++) {
            if(!this.getGene(i).equals(other.getGene(i)))
                    dist++;
        }
        return dist;
    }

    @Override
    public String toString() {
        return this.toStringPhenotype();//+ "\t" + toStringGenotype();
    }
}
