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
import java.security.SecureRandom;
import java.util.Random;
import java.util.StringTokenizer;
import problem.Individual;
import utils.Funcs;

/**
 * Multimodal Deceptive Functions - Complex Systems
 * http://www.complex-systems.com/pdf/07-2-2.pdf
 *
 *
 *
 *
 * @author ZULU
 */
public class Bit_Trap extends Individual {
//size of deceptive block     

    public static int NUMBER_OF_BLOCKS = 8; //numbe
    //lenght of gene
    public static int LENGHT = 8;
    //location  where  the  unitation search  space  of  the  function  is  divided
    public static int MIN_POSITION = 7;
    public static double MIN_VALUE = 0.6;
    public static double MAX_VALUE = 1.0;
    static Random rnd = new SecureRandom();

    public Bit_Trap() {
        super(MAXIMIZE);

        setBest(NUMBER_OF_BLOCKS * MAX_VALUE);
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            addGene(new GeneBinary(LENGHT));
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
//            public static int NUMBER_OF_BLOCKS = 8; //numbe
//    //lenght of gene
//    public static int LENGHT = 8;
//    //location  where  the  unitation search  space  of  the  function  is  divided
//    public static int MIN_POSITION  = 7;
//    public static double MIN_VALUE = 0.6;
//    public static double MAX_VALUE = 1.0;
        // public String getInfo() {
        StringBuilder buf = new StringBuilder();
        buf.append("Deceptive Problem <").append(NUMBER_OF_BLOCKS).append("> <").append(LENGHT).append(">");
        buf.append("\n\nAckley Trap Function :\n");
        buf.append("\nLenght of Gene     (L) :" + MIN_VALUE);
        buf.append("\nPoisition of valey (z) :" + MIN_POSITION);
        buf.append("\nLocal Optima Value (a) :" + MIN_VALUE);
        buf.append("\nGlobal Optima Value(b) :" + MAX_VALUE);

        buf.append("\n\nParameters    <BLOCKS> <L><z><a><b>");
        buf.append("\n     <BLOCKS> number of Genes");
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

    public double getBlockValue(int numGene) {
        int u = getGene(numGene).getAlels().getNumberOfOnes();
        if (u < MIN_POSITION) {
            return ((MAX_VALUE / MIN_POSITION) * (MIN_POSITION - u));
        }
        return MAX_VALUE / (LENGHT - MIN_POSITION) * (u - MIN_POSITION);
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    @Override
    public String getParameters() {
        return NUMBER_OF_BLOCKS + " " + LENGHT + " " + MIN_POSITION + " " + MIN_VALUE + " " + MAX_VALUE;
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
                LENGHT = Integer.parseInt(iter.nextToken());
                if (LENGHT <= 0) {
                    LENGHT = 8;
                }
            } catch (Exception e) {
                LENGHT = 8;
            }
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                MIN_POSITION = Integer.parseInt(iter.nextToken());
                if (MIN_POSITION <= 0 || MIN_POSITION > LENGHT) {
                    MIN_POSITION = LENGHT - 1;
                }
            } catch (Exception e) {
                LENGHT = LENGHT - 1;
            }
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                MIN_VALUE = Double.parseDouble(iter.nextToken());
            } catch (Exception e) {
                MIN_VALUE = 0.6;
            }
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                MAX_VALUE = Double.parseDouble(iter.nextToken());
            } catch (Exception e) {
                MAX_VALUE = 1.0;
            }
        }
        this.restart();

    }
}
