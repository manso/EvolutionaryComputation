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
import static problem.Individual.MAXIMIZE;
import static problem.Individual.setBest;
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
public class HIFF_K extends Individual {

    public static int LEVEL = 6; // height of tree
    public static int K_ARY = 2; // arity of leafs
    public static int SIZE = (int) Math.pow(K_ARY, LEVEL); //number of bits = K_ARY ^ LEVEL

    public HIFF_K() {
        super(MAXIMIZE);
        setBest((LEVEL + 1) * (1 << LEVEL));
        addGene(new GeneBinary(SIZE));
    }

    public HIFF_K(String bits) {
        super(MAXIMIZE);
        setBest(Double.POSITIVE_INFINITY);
        addGene(new GeneBinary(bits));
    }

    @Override
    protected double fitness() {
        BitField bits = getBits();
        //(bits in level 0) + Value of upper Tree
        return bits.getNumberOfBits() + HIFF_Value(bits.toString(), 1);
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append("Hierarchical-if-and-only-if (HIFF)  <").append(LEVEL).append("><").append(K_ARY).append(">");
        buf.append("\nNumber of bits :" + SIZE + " \t Best Value : " + (int) getBest());
        buf.append("\n\nParameters <LEVEL>");
        buf.append("\n     <LEVEL>  Height of Tree ( bits = K_ARY^LEVEL) ");
        buf.append("\n     <K_ARY>  Arity of leafs ");

        buf.append("\n\n Maping Function:");
        buf.append("\n  \"0(0)0\" -> \"0\"\t\"1(1)1\" -> \"1\"\t  otherwise -> \"-\"");
        buf.append("\n  {K_ARY}           \t{K_ARY}");
        buf.append("\n\n Contribution Function:");
        buf.append("\n  \"0\" = K_ARY^LEVEL\t \"1\" = K_ARY^LEVEL\t \"-\" = 0");
        buf.append("\n\n Modeling Building-Block Interdependency (1998) ");
        buf.append("\n Richard Watson, Gregory S. Hornby , Jordan B. Pollack ");


        return buf.toString();

    }

    @Override
    public String getParameters() {
        return "" + LEVEL + " " + K_ARY;
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
                LEVEL = 5;
            }
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {

                K_ARY = Integer.parseInt(iter.nextToken());
                if (K_ARY <= 2) {
                    K_ARY = 2;
                }
            } catch (Exception e) {
                K_ARY = 2;
            }
        }
        SIZE = (int) Math.pow(K_ARY, LEVEL);
        //----------------------------------------------
        // Set new Genotype to this individual
        restart();
        //----------------------------------------------
    }

    protected int unitation(String s) {
        int ones = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                ones++;
            }
        }
        return ones;
    }

    public double HIFF_Value(String bits, int level) {
        //top of the tree
        if (bits.length() == 1) {
            return 0;
        }
        //new bit level
        double sum = 0;
        StringBuilder newLevel = new StringBuilder(bits.length() / K_ARY);
        for (int i = 0; i < bits.length(); i += K_ARY) {
            String leaf = bits.substring(i, i + K_ARY);
            //propagate - symbol
            if (leaf.contains("-")) {
                newLevel.append("-");
            } //calculate new symbol
            else {
                int u = unitation(leaf);
                //false
                if (u == 0) {
                    newLevel.append("0");
                    sum += Math.pow(K_ARY, level);
                } //true
                else if (u == K_ARY) {
                    newLevel.append("1");
                    sum += Math.pow(K_ARY, level);
                } else //error
                {
                    newLevel.append("-");
                }
            }
        }
        return sum + HIFF_Value(newLevel.toString(), level + 1);
    }

    @Override
    public String toStringPhenotype() {
        if (isBest()) {
            return HIFF(getBits().toString()) + " [OPTIMUM]";
        } else {
            return HIFF(getBits().toString());
        }

    }

    public String HIFF(String bits) {
        //top of the tree
        if (bits.length() == 1) {
            return "";
        }
        StringBuilder newLevel = new StringBuilder(bits.length() / K_ARY);
        for (int i = 0; i < bits.length(); i += K_ARY) {
            String leaf = bits.substring(i, i + K_ARY);
            //propagate - symbol
            if (leaf.contains("-")) {
                newLevel.append("-");
            } //calculate new symbol
            else {
                int u = unitation(leaf);
                //false
                if (u == 0) {
                    newLevel.append("0");
                } //true
                else if (u == K_ARY) {
                    newLevel.append("1");
                } else //error
                {
                    newLevel.append("-");
                }
            }
        }
        return " " + newLevel + HIFF(newLevel.toString());
    }

    public static void main(String[] args) {
        //HIFFX h = new HIFFX("1111");
        HIFF_K h = new HIFF_K("011111111111111111111111110");
        h.K_ARY = 3;
        h.evaluate();
        System.out.println(" h = " + h);
    }
}
