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
 * D. E. Goldberg, Genetic Algorithms and Walsh Functions: Part I, a Gentle
 * Introduction. Complex Systems, Vol. 3, 1989, pp. 129-152
 *
 * Rapid, Accurate Optimization of Difficult Problems Using Fast Messy Genetic
 * Algorithms (1993) by David E. Goldberg , David E. Goldberg , Kalyanmoy Deb ,
 * Kalyanmoy Deb , Hillol Kargupta , Hillol Kargupta , Georges Harik , Georges
 * Harik
 *
 *
 * from paeper: The Island Model Genetic Algorithm: On Separability, Population
 * Size and Convergence http://neo.lcc.uma.es/Articles/WRH98.pdf Original
 * published in: D. Goldberg, B. Korb, and K. Deb. Messy Genetic Algorithms:
 * Motivation, Analysis, and First Results. Complex Systems, 4:415{444, 1989
 *
 *
 * @author ZULU
 */
public class F3 extends Individual {

    static int values[] = {
        28, 26, 22, 0,
        14, 0, 0, 30};
    public static int NUMBER_OF_BLOCKS = 10;
    static int GENE_SIZE = 3;

    public F3() {
        super(MAXIMIZE);
        setBest(NUMBER_OF_BLOCKS * values[7]);
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

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append("Fully Deceptive Problem <").append(NUMBER_OF_BLOCKS).append(">");
        buf.append("\n\n 3-bit fully deceptive problem");
        buf.append("\nf(000) = 28,  f(001) = 26,  f(010) = 22, f(011)  =  0");
        buf.append("\nf(100) = 14,  f(101) = 0,   f(110) =  0, f(111) = 30");

        buf.append("\n\nParameters <BLOCKS>");
        buf.append("\n     <#BLOCKS>  number of deceptive block ");

        buf.append("\n\nD. E. Goldberg");
        buf.append("\nGenetic Algorithms and Walsh Functions: Part I, a Gentle Introduction");
        buf.append("\nComplex Systems, Vol. 3, 1989");

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

    protected BitField getGeneBits(BitField bits, int gene) {
        BitField num = new BitField(GENE_SIZE);
        for (int i = 0; i < GENE_SIZE; i++) {
            num.setBit(i, bits.getBit(gene * GENE_SIZE + i));
        }
        return num;
    }

    protected int getGeneValue(BitField bits, int gene) {
        return (int) getGeneBits(bits, gene).getInteger();
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
            txt.append("[" + values[getGeneValue(bits, i)] + "(" + getGeneBits(bits, i) + ")]");
        }
        return txt.toString();
    }

    @Override
    public String toString() {
        return toStringGenotype();
    }
}
