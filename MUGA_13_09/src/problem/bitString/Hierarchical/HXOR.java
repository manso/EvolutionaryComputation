///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Ant?nio Manuel Rodrigues Manso                                 ****/
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
package problem.bitString.Hierarchical;

import genetic.gene.GeneBinary;
import java.util.StringTokenizer;
import problem.Individual;
import utils.BitField;

/**
 * The Hierarchical-if-and-only-if (H-IFF)
 *
 * defined in: Modeling Building-Block Interdependency (1998) by Richard Watson
 * , Gregory S. Hornby , Jordan B. Pollack Parallel Problem Solving from Nature
 * - PPSN V
 * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.48.6392&rep=rep1&type=pdf
 *
 *
 * Solved By: algoritmo - A RECOMBINATIVE HILL-CLIMBER Richard A. Watson -
 * Analysis of recombinative algorithms on a non-separable building-block
 * problem 2000
 *
 * Escaping Hierarchical Traps with Competent Genetic Algorithms bDavid E.
 * Goldberg , David E. Goldberg 2001
 *
 * Natural Intelligence for Scheduling, Planning and Packing Problems Studies in
 * Computational Intelligence Volume 250, 2009, pp 111-143 Solving
 * Hierarchically Decomposable Problems with the Evolutionary Transition
 * Algorithm
 *
 *
 *
 * @author ZULU
 */
public class HXOR extends Individual {

    public static int LEVEL = 8; // height of tree

    public HXOR() {
        super(MAXIMIZE);
        setBest((LEVEL + 1) * (1 << LEVEL));
        addGene(new GeneBinary(1 << LEVEL));
    }

    public HXOR(String bits) {
        super(MAXIMIZE);
        setBest(Double.POSITIVE_INFINITY);
        addGene(new GeneBinary(bits));
    }

    @Override
    protected double fitness() {
        BitField bits = getBits();
        return bits.getNumberOfBits() + HXor_Value(bits.toString(), 1);
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append("Hierarchical XOR  <").append(LEVEL).append(">");
        buf.append("\nNumber of bits :" + (1 << LEVEL) + " \t Best Value : " + (int) getBest());
        buf.append("\n\nParameters <LEVEL>");
        buf.append("\n     <LEVEL>  Height of Tree ( bits = 2^LEVEL) ");

        buf.append("\n\n Maping Function:");
        buf.append("\n  \"01\" -> \"0\"\t\"10\" -> \"1\"\t  otherwise -> \"-\"");
        buf.append("\n\n Contribution Function:");
        buf.append("\n  \"0\" = 2^LEVEL\t \"1\" = 2^LEVEL\t \"-\" = 0");
        buf.append("\n\n Hierarchically Consistent Test Problems for Genetic Algorithms ");
        buf.append("\n Richard A. Watson Jordan B. Pollack");
        return buf.toString();

    }

    @Override
    public String getParameters() {
        return "" + LEVEL;
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                LEVEL = Integer.parseInt(iter.nextToken());
                if (LEVEL <= 2) {
                    LEVEL = 2;
                }
            } catch (Exception e) {
                LEVEL = 8;
            }

            //----------------------------------------------
            // Set new Genotype to this individual
            restart();
            //----------------------------------------------
        }
    }

    public static double HXor_Value(String bits, int level) {
        //top of the tree
        //top of the tree
        if (bits.length() == 1) {
            return 0;
        }
        //new bit level
        double sum = 0;
        StringBuilder newLevel = new StringBuilder(bits.length() / 2);
        for (int i = 0; i < bits.length(); i += 2) {
            //false
            if (bits.charAt(i) == '0' && bits.charAt(i + 1) == '1') {
                newLevel.append("0");
                sum += Math.pow(2, level);
            } //true
            else if (bits.charAt(i) == '1' && bits.charAt(i + 1) == '0') {
                newLevel.append("1");
                sum += Math.pow(2, level);
            } else //error
            {
                newLevel.append("-");
            }
        }
        return sum + HXor_Value(newLevel.toString(), level + 1);
    }

    @Override
    public String toStringPhenotype() {
        if (isBest()) {
            return HXor(getBits().toString()) + " [OPTIMUM]";
        }
        return HXor(getBits().toString());
    }

    public static String HXor(String bits) {
        //top of the tree
        //top of the tree
        if (bits.length() == 1) {
            return "";
        }
        //new bit level
        StringBuilder newLevel = new StringBuilder(bits.length() / 2);
        for (int i = 0; i < bits.length(); i += 2) {
            //false
            if (bits.charAt(i) == '0' && bits.charAt(i + 1) == '1') {
                newLevel.append("0");
            } //true
            else if (bits.charAt(i) == '1' && bits.charAt(i + 1) == '0') {
                newLevel.append("1");
            } else //error
            {
                newLevel.append("-");
            }
        }
        return " " + newLevel + HXor(newLevel.toString());
    }

    public static void main(String[] args) {
        HXOR x = new HXOR("10010110011010010110100101101001");
        x.setParameters("5");
        x.evaluate();
        System.out.println(x.toStringPhenotype() + " Best ? " + x.isBest());
    }
}
