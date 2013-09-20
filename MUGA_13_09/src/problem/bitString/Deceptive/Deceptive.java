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
import genetic.gene.GeneBinary;
import java.util.Random;
import java.util.StringTokenizer;
import problem.Individual;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public class Deceptive extends Individual {
//size of deceptive block     

    public static boolean[][] PATTERN = null; //pattern of deceptive
    public static int SIZE_OF_BLOCK = 8;
    public static int NUMBER_OF_BLOCKS = 10;
    static Random rnd = new Random();

    public Deceptive() {
        super(MAXIMIZE);
        //initialize pattern
        if (PATTERN == null || PATTERN.length != NUMBER_OF_BLOCKS || PATTERN[0].length != SIZE_OF_BLOCK) {
            initializePattern();
        }

        setBest(NUMBER_OF_BLOCKS * (SIZE_OF_BLOCK + 1));
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
//            addGene(new GeneNumberBits(0, 1, SIZE_OF_BLOCK));
//            addGene(new GeneNumberGray(0, 1, SIZE_OF_BLOCK));
            addGene(new GeneBinary(SIZE_OF_BLOCK));
        }

    }

    @Override
    public String toString() {
        return toStringGenotype();
    }

    @Override
    public String toStringGenotype() {
        StringBuilder txt = new StringBuilder();
        //number of copies
        String elem = "";
        if (this.getNumCopies() > 1) {
            elem = "[" + Funcs.IntegerToString(getNumCopies(), 4) + "]";
        }
        txt.append(Funcs.SetStringSize(elem, 8));

        if (this.isEvaluated()) {
            txt.append(Funcs.DoubleToString(fitness, 10));
        } else {
            txt.append(Funcs.SetStringSize("????", 10));
        }
        txt.append(" = ");
        for (int i = 0; i < getNumGenes(); i++) {
            txt.append(getGene(i).toBinString() + " ");
        }
        return txt.toString();
    }

    @Override
    public String toStringPhenotype() {
        StringBuilder txt = new StringBuilder();
        //number of copies
        String elem = "";
        if (this.getNumCopies() > 1) {
            elem = "[" + Funcs.IntegerToString(getNumCopies(), 4) + "]";
        }
        txt.append(Funcs.SetStringSize(elem, 8));

        if (this.isEvaluated()) {
            txt.append(Funcs.DoubleToString(fitness, 10));
        } else {
            txt.append(Funcs.SetStringSize("????", 10));
        }
        txt.append(" = ");

        for (int i = 0; i < getNumGenes(); i++) {
            txt.append(getBlockValue(i) + " ");
        }

        return txt.toString();

    }

    @Override
    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("Deceptive Problem <").append(NUMBER_OF_BLOCKS).append("> <").append(SIZE_OF_BLOCK).append(">");
        buf.append("\nDeceptive Pattern:\n");
        for (int i = 0; i < PATTERN.length; i++) {
            for (int j = 0; j < PATTERN[i].length; j++) {
                if (PATTERN[i][j]) {
                    buf.append("1");
                } else {
                    buf.append("0");
                }
            }
            buf.append(" ");
        }
        buf.append("\n\nParameters <BLOCKS> <SIZE>");
        buf.append("\n     <BLOCKS> number of blocks");
        buf.append("\n     <SIZE>  size of block ");
        return buf.toString();

    }

    @Override
    protected double fitness() {
        int total = 0;
        //for each block
        for (int i = 0; i < getNumGenes(); i++) {
            total += getBlockValue(i);
        }
        return total;
    }
    //--------------------------------------------------------------------------

    public int getBlockValue(int numGene) {
        Gene gene = getGene(numGene);
        int TOTAL = 0;
        //for each bit in block
        for (int i = 0; i < gene.getNumBits(); i++) {
            //compute FALSE bits
            if (gene.getAlels().getBit(i) == PATTERN[numGene][i]) {
                TOTAL++;
            }//bits  
        }
        if (TOTAL > 0) {
            return TOTAL;
        } else {
            return SIZE_OF_BLOCK + 1;
        }
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    @Override
    public String getParameters() {
        return NUMBER_OF_BLOCKS + " " + SIZE_OF_BLOCK;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                NUMBER_OF_BLOCKS = Integer.parseInt(iter.nextToken());
                if (NUMBER_OF_BLOCKS <= 0) {
                    NUMBER_OF_BLOCKS = 4;
                }
            } catch (Exception e) {
                NUMBER_OF_BLOCKS = 4;
            }
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                SIZE_OF_BLOCK = Integer.parseInt(iter.nextToken());
                if (SIZE_OF_BLOCK <= 0) {
                    SIZE_OF_BLOCK = 8;
                }
            } catch (Exception e) {
                SIZE_OF_BLOCK = 8;
            }
        }
        initializePattern();
        this.restart();
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    protected void initializePattern() {
        //pattern - initialize all bits to False
        PATTERN = new boolean[NUMBER_OF_BLOCKS][SIZE_OF_BLOCK];
        //all genes
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            for (int j = 0; j < SIZE_OF_BLOCK; j++) {
                PATTERN[i][j] = true;
            }
        }//all blocks
    }//shuffle
    //--------------------------------------------------------------------------
}
