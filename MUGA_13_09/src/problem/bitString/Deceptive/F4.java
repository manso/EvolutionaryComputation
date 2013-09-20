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

import genetic.gene.GeneNumberBits;
import java.util.StringTokenizer;
import problem.Individual;
import utils.BitField;
import utils.Funcs;

/**
 * from paper: Whitley L.D., “Fundamental Principles of Deception in Genetic
 * Search,” Foundations of Genetic Algorithms, Volume 1, Rowlins G.J.E. (ed).
 * Morgan Kaufmann, ISBN 1-55860-170- 8. 1991, pp 221-241.
 * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.18.6847&rep=rep1&type=pdf
 *
 *
 * * The Island Model Genetic Algorithm: On Separability, Population Size and
 * Convergence (1998) by Darrell Whitley , Soraya Rana , Robert B. Heckendorn
 * Journal of Computing and Information Technology
 * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.36.7225&rep=rep1&type=pdf
 *
 *
 *
 * @author ZULU
 */
public class F4 extends Individual {

    static int values[] = {
        28, 26, 24, 18,
        22, 16, 14, 0,
        20, 12, 10, 2,
        8, 4, 6, 30
    };
    static int GENE_SIZE = 4;
    public static int NUMBER_OF_BLOCKS = 10;

    public F4() {
        super(MAXIMIZE);
        setBest(NUMBER_OF_BLOCKS * values[15]);
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            addGene(new GeneNumberBits(0, 1, GENE_SIZE));
        }
    }

    @Override
    protected double fitness() {
        double fit = 0;
        for (int i = 0; i < getGenome().getNumGenes(); i++) {
            int g = (int) getGene(i).getAlels().getInteger();
            fit += values[g];
        }
        return fit;
    }
    
     protected BitField getGeneBits(BitField bits, int gene) {
        BitField num = new BitField(GENE_SIZE);
        for (int i = 0; i < GENE_SIZE; i++) {
            num.setBit(i, bits.getBit(gene * GENE_SIZE+ i));
        }
        return num;
    }
    protected int getGeneValue(BitField bits, int gene) {
        return (int) getGeneBits(bits, gene).getInteger();
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append("Fully Deceptive Problem <").append(NUMBER_OF_BLOCKS).append(">");
        buf.append("\n\n 4-bit fully deceptive problem");
        buf.append("\nf(1111) = 30 f(0000) = 28 f(0111) = 0  f(1011) = 2");
        buf.append("\nf(1101) = 4  f(1110) = 6  f(1100) = 8  f(1010) = 10");
        buf.append("\nf(1001) = 12 f(0110) = 14 f(0101) = 16 f(0011) = 18");
        buf.append("\nf(1000) = 20 f(0100) = 22 f(0010) = 24 f(0001) = 26");
        buf.append("\n\nParameters <BLOCKS>");
        buf.append("\n     <#BLOCKS>  number of deceptive block ");

        buf.append("\n\nL.  D.  Whitley Whitley");
        buf.append("\nFundamental  Principles  of Deception  in  Genetic  Search");
        buf.append("\nFoundations  of Genetic Algorithms, 1991");

        return buf.toString();

    }

    @Override
    public String getParameters() {
        return "" + NUMBER_OF_BLOCKS;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                NUMBER_OF_BLOCKS = Integer.parseInt(iter.nextToken());
                if (NUMBER_OF_BLOCKS <= 0) {
                    NUMBER_OF_BLOCKS = 1;
                }
            } catch (Exception e) {
                NUMBER_OF_BLOCKS = 10;
            }
        }
        this.restart();
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
        BitField bits = getBits();
        for (int i = 0; i < getNumGenes(); i++) {
            txt.append("["+ values[getGeneValue(bits, i)]+ "("+getGeneBits(bits, i) +")]");
        }
        return txt.toString();
    }
    @Override
    public String toString() {
        return toStringGenotype();
    }
//    
//    public static void main(String[] args) {
//        FullyDeceptive4 f = new FullyDeceptive4();
//        System.out.println(f.toStringGenotype());
//        System.out.println(f.toStringPhenotype());
//    }
}
