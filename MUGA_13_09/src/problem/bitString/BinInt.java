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
package problem.bitString;

import genetic.gene.Gene;
import genetic.gene.GeneNumberBits;
import java.math.BigDecimal;
import java.util.Random;
import java.util.StringTokenizer;
import problem.Individual;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public class BinInt extends Individual {

    public static int SIZE_OF_GENE = 100;
    public static int NUMBER_OF_GENES = 1;
    static Random rnd = new Random();

    public BinInt() {
        super(MAXIMIZE);
        setBest(NUMBER_OF_GENES * (Math.pow(2, SIZE_OF_GENE) - 1));
        for (int i = 0; i < NUMBER_OF_GENES; i++) {
            addGene(new GeneNumberBits(0, 1, SIZE_OF_GENE));
//            addGene(new GeneNumberBits(0, 1, SIZE_OF_GENE));
//            addGene(new GeneBinary(SIZE_OF_GENE));
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
            txt.append(Funcs.SetStringSize(getBigFitness().toString(), 30));
        } else {
            txt.append(Funcs.SetStringSize("????", 30));
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
            txt.append(Funcs.SetStringSize(getBigFitness().toString(), 30));
        } else {
            txt.append(Funcs.SetStringSize("????", 30));
        }
        txt.append(" = ");

        for (int i = 0; i < getNumGenes(); i++) {
            txt.append(getGene(i).getAlels().getBigDecimal() + " ");
        }

        return txt.toString();

    }

    @Override
    public String getGenomeInformation() {
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("Bin Int Problem <").append(NUMBER_OF_GENES).append("> <").append(SIZE_OF_GENE).append(">");

        buf.append("\n\nParameters <#GENES> <#BITS>");
        buf.append("\n     <#GENES> number of Genes");
        buf.append("\n     <#BITS>  numbor of bits in gene ");
        return buf.toString();

    }

    @Override
    protected double fitness() {
        double total = 0;
        //for each block
        for (int i = 0; i < getNumGenes(); i++) {
            total += getValue(i);
        }
        return total;
    }
    //--------------------------------------------------------------------------

    public double getValue(int numGene) {
        Gene gene = getGene(numGene);
        return gene.getAlels().getBigDecimal().doubleValue();
    }

    protected BigDecimal getBigFitness() {
        BigDecimal total = new BigDecimal("0");
        //for each block
        for (int i = 0; i < getNumGenes(); i++) {
            total = total.add(getGene(i).getAlels().getBigDecimal());
        }
        return total;
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public String getParameters() {
        return NUMBER_OF_GENES + " " + SIZE_OF_GENE;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                NUMBER_OF_GENES = Integer.parseInt(iter.nextToken());
                if (NUMBER_OF_GENES <= 0) {
                    NUMBER_OF_GENES = 4;
                }
            } catch (Exception e) {
                NUMBER_OF_GENES = 4;
            }
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                SIZE_OF_GENE = Integer.parseInt(iter.nextToken());
                if (SIZE_OF_GENE <= 0) {
                    SIZE_OF_GENE = 8;
                }
            } catch (Exception e) {
                SIZE_OF_GENE = 8;
            }
        }
        //----------------------------------------------
        // Set new Genotype to this individual
        this.restart();
        //----------------------------------------------
    }

    public boolean isBest() {
        for (int i = 0; i < getNumGenes(); i++) {
            if (getGene(i).getAlels().getNumberOfOnes() != getGene(i).getAlels().getNumberOfBits()) {
                return false;
            }
        }
        return true;
    }

    /**
     * fitness comparation
     *
     * @param other x
     * @return x
     */
    @Override
    public int compareTo(Object other) {

        if (!(other instanceof BinInt)) {
            return super.compareTo(other);
        }

        BinInt tmp = (BinInt) other;
        if (!tmp.isEvaluated) {
            if (this.isEvaluated) {
                return 1;
            }
            return -1;
        }
        int comp = this.getBigFitness().compareTo(tmp.getBigFitness());
        if (typeOfOptimization == MAXIMIZE) {
            return comp;
        } else {
            return -comp;
        }
    }
    //--------------------------------------------------------------------------

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            BinInt b = new BinInt();
            b.evaluate();
            System.out.println(b);

        }
    }
}