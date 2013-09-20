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

import genetic.gene.GeneBinary;
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
public class F6Bipolar extends Individual {

    static double values[] = {
        0.9, 0.8, 0, 1};
    public static int NUMBER_OF_BLOCKS = 10;
    static int GENE_SIZE = 3;

    public F6Bipolar() {
        super(MAXIMIZE);
        setBest(NUMBER_OF_BLOCKS * values[GENE_SIZE]);
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            addGene(new GeneBinary(GENE_SIZE*2));
        }
    }

    @Override
    protected double fitness() {
        double fit = 0;
        for (int i = 0; i < getGenome().getNumGenes(); i++) {
            fit += getGeneDeceptiveValue(i);
        }
        return fit;
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append("Bipolar Deceptive Problem <").append(NUMBER_OF_BLOCKS).append(">");
        buf.append("\n\n f3 deceptive problem");
        buf.append("\n0.9 if u = 0");
        buf.append("\n0.8 if u = 1");
        buf.append("\n0   if u = 2");
        buf.append("\n1   if u = 3");
        buf.append("\nf6 Bipolar = f3 ( | 3- u)");

        buf.append("\n\nParameters <BLOCKS>");
        buf.append("\n     <#BLOCKS>  number of deceptive block ");

        buf.append("\n\nMartin Pelikan, David E. Goldberg, and Erick Cant´u-Paz");
        buf.append("\nGBOA: The Bayesian Optimization Algorithm");


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

    protected double getGeneDeceptiveValue(int gene) {
        return values[Math.abs(3 - genome.getGene(gene).getAlels().getNumberOfOnes())];
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
            txt.append("[" + getGeneDeceptiveValue(i) + " ]");
        }
        return txt.toString();
    }

    @Override
    public String toString() {
        return toStringGenotype();
    }
}
