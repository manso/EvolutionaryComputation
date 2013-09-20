/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.bitString;

import java.util.Arrays;
import java.util.StringTokenizer;
import operator.recombination.Recombination;
import problem.Individual;
import utils.BitField;

/**
 *
 * @author arm
 */
public class Crossover extends UniformCrossover {

    static final int DEFAULT_CUTS = 2;
    protected int NUM_CUTS = DEFAULT_CUTS;

   /**
     * Build a Mask with cuts
     * uniform crossover do not use cuts
     * @param size size of the mas 
     * @param cuts number of cuts
     * @return 
     */
    public BitField buildMask(int size, int cuts) {
        //initialaize all bits to zero 
        BitField mask = new BitField(size);
        //generate random cuts
        int point[] = new int[cuts + 2]; //last is the size ofindividual
        for (int i = 1; i < point.length - 1; i++) {
            point[i] = random.nextInt(size-1)+1;
        }
        //add the end of individual
        point[cuts] = size;
        //sort Points
        Arrays.sort(point);
//        System.out.println(Arrays.toString(point));
        //performs cuts in mask
        boolean bit = random.nextBoolean();
        for (int cut = 1; cut < point.length; cut++) {
            for (int i = point[cut - 1]; i < point[cut]; i++) {
                mask.setBit(i, bit);
            }
//            //if poinst are differents
            if (point[cut - 1] != point[cut]) //change bit
            {
                bit = !bit;
            }
        }
        return mask;
    }
    @Override
    public void doCrossover(Individual i1, Individual i2) {
        //build mask        
        BitField b1 = i1.getBits();
        BitField b2 = i2.getBits();
        //uniform crossover do not use cuts
        BitField mask = buildMask(b1.getNumberOfBits() , NUM_CUTS);
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
    public String toString() {
        return this.getClass().getSimpleName() + "<" + probability + ">" + "<" + NUM_CUTS + ">";
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                probability = Double.parseDouble(iter.nextToken());
                if (probability > 1 || probability <0 ) {
                    probability = 0;
                }
            } catch (Exception e) {
                probability = DEFAULT_PROBABILITY;
            }
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                NUM_CUTS = Integer.parseInt(iter.nextToken());
                if (NUM_CUTS < 1) {
                    NUM_CUTS = DEFAULT_CUTS;
                }
            } catch (Exception e) {
                NUM_CUTS = DEFAULT_CUTS;
            }
        }
              
    }

    @Override
    public String getParameters() {
        return probability + " " + NUM_CUTS;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nCrossover with cuts in the genotype");
        buf.append("\n\nParameters: <PROB CROSSOVER> <#CUTS>");
        buf.append("\n<PROB CROSSOVER> - probability of crossover [0,1]");
        buf.append("\n<#CUTS> - number of cuts (>=1) ");
        return buf.toString();
    }
    
    @Override
    public Recombination getClone() {
        Crossover mut = (Crossover) super.getClone();
        mut.NUM_CUTS = this.NUM_CUTS;
        return mut;
    }
}
