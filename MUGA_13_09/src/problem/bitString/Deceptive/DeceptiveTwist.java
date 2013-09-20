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
package problem.bitString.Deceptive;

import genetic.gene.Gene;
import utils.BitField;

/**
 *
 * @author ZULU
 */
public class DeceptiveTwist extends Deceptive {

    public DeceptiveTwist() {
        super();
        setBest(NUMBER_OF_BLOCKS * (SIZE_OF_BLOCK + 2));
    }

    public DeceptiveTwist(String bits) {
        super();
        BitField b = new BitField(SIZE_OF_BLOCK);
        for (int i = 0; i < bits.length(); i++) {
            if (bits.charAt(bits.length()-i-1) == '1') {
                b.setBitTrue(SIZE_OF_BLOCK-i-1);
            }
        }
        genome.setBinString(b);
        setBest(NUMBER_OF_BLOCKS * (SIZE_OF_BLOCK + 2));
    }

    @Override
    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("Deceptive Twist Problem <").append(NUMBER_OF_BLOCKS).append("> <").append(SIZE_OF_BLOCK).append(">");
        buf.append("\nTwo deceptive functions (A and B ) twisted in the same block ");
        buf.append("\n\t A - Deceptive Ones\n\t B - Deceptive Zeros");
        buf.append("\nDeceptive Pattern:\n");
        for (int i = 0; i < PATTERN.length; i++) {
            for (int j = 0; j < PATTERN[i].length; j++) {
                if (PATTERN[i][j]) {
                    buf.append("B");
                } else {
                    buf.append("A");
                }
            }
            buf.append(" ");
        }
        buf.append("\n\nParameters <#BLOCKS> <SIZE>");
        buf.append("\n     <#BLOCKS> number of blocks");
        buf.append("\n     <SIZE>  size of block ");
        return buf.toString();

    }

    //--------------------------------------------------------------------------
    @Override
    public int getBlockValue(int numGene) {
        Gene gene = getGene(numGene);
        int A = 0;
        int B = 0;
        int total = 0;
        //pattern of block
        boolean[] pattern = PATTERN[numGene];
        //for each bit in block
        for (int j = 0; j < gene.getNumBits(); j++) {
            //compute BLOCK TRUE
            if (pattern[j] == false && gene.getAlels().getBit(j) == false) {
                A++;
            } //compute BLOCK FALSE
            else if (pattern[j] == true && gene.getAlels().getBit(j) == true) {
                B++;
            }
        }//bits  
//            System.out.println("GENE " + gene + " A = " + A + " B = " + B);
        //sum FALSE block   
        if (B > 0) {
            total += B;
        } else {
            total += (SIZE_OF_BLOCK / 2) + 1;
        }
        //sum TRUE block
        if (A > 0) {
            total += A;
        } else {
            total += (SIZE_OF_BLOCK / 2) + 1;
        }
        return total;
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    @Override
    protected void initializePattern() {
        //pattern - initialize all bits to False
        PATTERN = new boolean[NUMBER_OF_BLOCKS][SIZE_OF_BLOCK];
        //all genes
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            //first part of gene is set to true the other is initalized to false
            // TTTT FFFFF
            for (int j = 0; j < SIZE_OF_BLOCK / 2; j++) {
                PATTERN[i][j] = true;
            }
            //shuffle bits
            for (int j = SIZE_OF_BLOCK - 1; j > 0; j--) {
                //random position
                int index = rnd.nextInt(j);
                //switch with the last position
                boolean aux = PATTERN[i][j];
                PATTERN[i][j] = PATTERN[i][index];
                PATTERN[i][index] = aux;
            }
        }//all blocks
    }//shuffle

    public static void main(String[] args) {
        Deceptive.NUMBER_OF_BLOCKS = 1;
        Deceptive.SIZE_OF_BLOCK = 10;

        for (int i = 0; i < 256*4; i++) {
            String bits = Integer.toBinaryString(i);
            DeceptiveTwist d = new DeceptiveTwist(bits);
            d.evaluate();
            System.out.println(i +"\t" + bits +  "\t" + d);
        }


    }
}
