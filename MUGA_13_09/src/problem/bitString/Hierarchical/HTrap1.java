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
 * Scalable Optimization via Probabilistic Modeling: From Algorithms to
 * Applications. Martin Pelikan, Kumara Sastry, and Erick Cantu-Paz (eds.), pp.
 * 78
 * http://books.google.pt/books?id=znzGDXPh6NAC&pg=PA78&lpg=PA78&dq=Htrap+functions&source=bl&ots=9NA_MD_uLN&sig=sYjeqAzJcm97awqnFh4uLV3DSkA&hl=pt-PT&sa=X&ei=U8n7UO-yGISmhAfDwYG4CA&ved=0CDQQ6AEwAQ#v=onepage&q=Htrap%20functions&f=false
 *
 *
 * Escaping Hierarchical Traps with Competent Genetic Algorithms bDavid E.
 * Goldberg , David E. Goldberg 2001
 *http://medal-lab.org/files/2001003.pdf
 *
 * @author ZULU
 */
public class HTrap1 extends Individual {

    public static int LEVEL = 5;
    public static int SIZE = (int) Math.pow(3, LEVEL);

    public HTrap1() {
        super(MAXIMIZE);
        setBest(Double.POSITIVE_INFINITY);
        addGene(new GeneBinary(SIZE));
    }

    public boolean isBest() {
        return getBits().getNumberOfOnes() == SIZE;
    }

    @Override
    protected double fitness() {
        BitField bits = getBits();
        return bits.getNumberOfBits() + HTrap_Value(bits.toString(), 1, bits.getNumberOfBits());
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append("HIFF Trap <").append(SIZE).append(">");
        buf.append("Hierarchical-if-and-only-if with 3 leafs Trap ");
        buf.append("\nBest Value :" + getBest());
        buf.append("\n\nParameters <LEVEL>");
        buf.append("\n     <LEVEL>  Level o Tree = 3^LEVEL bits ");
        buf.append("\n     <1=3> <2=9> <3=27> <4=81> <5=243> <6 =729>");
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
                if (LEVEL <= 0) {
                    LEVEL = 4;
                }
            } catch (Exception e) {
                LEVEL = 4;
            }
            SIZE = (int) Math.pow(3, LEVEL);
            //----------------------------------------------
            // Set new Genotype to this individual
            restart();
            //----------------------------------------------
        }
    }

    public static double trap3(double fhigh, double flow, char b1, char b2, char b3) {
        //fhigh = 1
        //flow = 1+ 0.1/lenght of chromossom
        int u = (b1 == '1' ? 1 : 0) + (b2 == '1' ? 1 : 0) + (b3 == '1' ? 1 : 0);
        if (u == 3) {
            return fhigh;
        }
        return flow - (u * (flow / 2));
    }

    public static double HTrap_Value(String bits, int level, int l) {
//        System.out.println(level + "\t BITS = " + bits);
        //top level
        if (bits.length() == 3) {
            if (bits.charAt(0) == '-' || bits.charAt(1) == '-' || bits.charAt(2) == '-') {
                return 0;
            }
            return trap3(1.0, 0.9, bits.charAt(0), bits.charAt(1), bits.charAt(2));
        }
        //new bit level
        double sum = 0;
        StringBuilder newLevel = new StringBuilder(bits.length() / 3);
        for (int i = 0; i < bits.length(); i += 3) {
            //false
            if (bits.charAt(i) == '0' && bits.charAt(i + 1) == '0' && bits.charAt(i + 2) == '0') {
                newLevel.append("0");
                sum += trap3(1.0, 1 + 0.1 / l, bits.charAt(0), bits.charAt(1), bits.charAt(2));
            } //true
            else if (bits.charAt(i) == '1' && bits.charAt(i + 1) == '1' && bits.charAt(i + 2) == '1') {
                newLevel.append("1");
                sum += trap3(1.0, 1 + 0.1 / l, bits.charAt(0), bits.charAt(1), bits.charAt(2));
            } else //error
            {
                newLevel.append("-");
            }
        }
        return sum * Math.pow(3, level) + HTrap_Value(newLevel.toString(), level + 1, l);
    }

    @Override
    public String toStringPhenotype() {
        if (isBest()) {
            return HTrap3(getBits().toString()) + " [OPTIMUM]";
        } else {
            return HTrap3(getBits().toString());
        }

    }

    public static String HTrap3(String bits) {
        //top of the tree
        if (bits.length() == 1) {
            return "";
        }
        //new bit level
        StringBuilder newLevel = new StringBuilder(bits.length() / 3);
        for (int i = 0; i < bits.length(); i += 3) {
            //false
            if (bits.charAt(i) == '0' && bits.charAt(i + 1) == '0' && bits.charAt(i + 2) == '0') {
                newLevel.append("0");
            } //true
            else if (bits.charAt(i) == '1' && bits.charAt(i + 1) == '1' && bits.charAt(i + 2) == '1') {
                newLevel.append("1");
            } else //error
            {
                newLevel.append("-");
            }
        }
        return " " + newLevel + HTrap3(newLevel.toString());
    }
}
