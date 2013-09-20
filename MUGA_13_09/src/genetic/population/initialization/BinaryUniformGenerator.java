///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package genetic.population.initialization;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import genetic.population.SimplePopulation;
import genetic.population.UniquePopulation;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import problem.Individual;
import problem.bitString.OnesMax;
import utils.BitField;

/**
 * this class generate random population with equal number of ones and zeros
 *
 * @author Administrator
 */
public class BinaryUniformGenerator extends InitUniformPop {

    ArrayList<Boolean>[] bits;
    Random random = new Random();
    Individual template;
 
    /**
     * initialize the vector of probabilities
     *
     * @param size template individual
     * @param size size of population
     */
    public void initialize(Individual ind, int size) {
        //population with individuals       
        template = ind;
        bits = new ArrayList[ind.getBits().getNumberOfBits()];
        for (int i = 0; i < bits.length; i++) {
            bits[i] = new ArrayList<>();
            //insert ones
            for (int j = 0; j < size / 2; j++) {
                bits[i].add(true);

            }
            //insert zeros
            for (int j = size / 2; j < size; j++) {
                bits[i].add(false);

            }
            //randomize
            Collections.shuffle(bits[i]);
        }
    }

    /**
     * generate a random bitsring
     *
     * @return bitstring
     */
    @Override
    public Individual generateUniform() {
        BitField bit = new BitField(bits.length);
        for (int i = 0; i < bits.length; i++) {
//            due the multiset not suport repeated genotypes
            if (bits[i].isEmpty()) {
                bit.setBit(i, random.nextBoolean());
            } else {
                bit.setBit(i, bits[i].remove(random.nextInt(bits[i].size())));
            }
        }
        Individual newInd = template.getClone();
        newInd.setBits(bit);
        return newInd;
    }
    public static void verify( Population pop){
        int []ones = new int[pop.getGenotype(0).getBits().getNumberOfBits()];
        Iterator<Individual> it = pop.getIterator();
        while(it.hasNext()){
            BitField bits = it.next().getBits();
            for (int k = 0; k < ones.length; k++) {
                if( bits.getBit(k))
                    ones[k]++;            
            }
        }
        System.out.println("\nOnes:");
        for (int i = 0; i < ones.length; i++) {
            System.out.print(" " + ones[i]);
            
        }
        
    }

    public static void main(String[] args) {
        int SIZE_POP = 50;
        int SIZE_IND =80;
        OnesMax ind = new OnesMax(SIZE_IND);
        BinaryUniformGenerator bin = new BinaryUniformGenerator();
        bin.initialize(ind, SIZE_POP);
        Population pop = new MultiPopulation();
        for (int i = 0; i < SIZE_POP; i++) {
            Individual ind1 = bin.generateUniform();
//            System.out.println(ind1);
            pop.addGenotype(ind1);
        }
        System.out.println(pop);
        bin.verify(pop);
    }
}
