///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package problem.bitString;

import genetic.gene.Gene;
import genetic.gene.GeneNumberBits;
import java.util.Random;
import java.util.StringTokenizer;
import problem.Individual;
import utils.Funcs;

/**
 * Mitchell, M., Forrest, S., and Holland, J. H. (1992) The royal road for
 * genetic algorithms: Fitness landscapes and GA performance In Proceedings of
 * the First European Conference on Artificial Life. Cambridge, MA: MIT
 * Press/Bradford Books.
 *
 * @author ZULU
 */
public class RoyalRoad extends Individual {

    public static int SIZE_OF_BLOCK = 8;
    public static int NUMBER_OF_BLOCKS = 8;
    static Random rnd = new Random();

    public RoyalRoad() {
        super(MAXIMIZE);
        setBest(NUMBER_OF_BLOCKS * SIZE_OF_BLOCK);
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
//            addGene(new GeneNumberGray(0, 1, SIZE_OF_BLOCK));
            addGene(new GeneNumberBits(0, 1, SIZE_OF_BLOCK));
//            addGene(new GeneBinary(SIZE_OF_BLOCK));
        }
    }

    public RoyalRoad(int sizeOfBlocks, int numBlocks) {
        super(MAXIMIZE);
        NUMBER_OF_BLOCKS = numBlocks;
        SIZE_OF_BLOCK = sizeOfBlocks;
        setBest(NUMBER_OF_BLOCKS * SIZE_OF_BLOCK);
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
//            addGene(new GeneNumberGray(0, 1, SIZE_OF_BLOCK));
            addGene(new GeneNumberBits(0, 1, SIZE_OF_BLOCK));
//            addGene(new GeneBinary(SIZE_OF_BLOCK));
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
        buf.append("Royal Road Problem <").append(NUMBER_OF_BLOCKS).append("> <").append(SIZE_OF_BLOCK).append(">");

        buf.append("\n\nParameters <#BLOCKS> <SIZE>");
        buf.append("\n     <#BLOCKS> number of blocks");
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
            //compute True bits
            if (gene.getAlels().getBit(i)) {
                TOTAL++;
            }//bits  
        }
        if (TOTAL < gene.getNumBits()) {
            return 0;
        }
        return TOTAL;
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
        //----------------------------------------------
        // Set new Genotype to this individual
        restart();
        //----------------------------------------------
    }
    //--------------------------------------------------------------------------
}