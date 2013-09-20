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
 *
 *
 *
 * @author ZULU
 */
public class Bipolar extends Individual {
//size of deceptive block     

    public static int NUMBER_OF_BLOCKS = 1; //numbe
    //lenght of gene
    public static int LENGHT = 5;
    //location  where  the  unitation search  space  of  the  function  is  divided
    public static int MIN_POSITION = 1;
    public static double MIN_VALUE = LENGHT;
    public static double MAX_VALUE = LENGHT - 1;
    static Random random = new SecureRandom();

    public Bipolar() {
        super(MAXIMIZE);

        setBest(NUMBER_OF_BLOCKS * MIN_VALUE);
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            addGene(new GeneBinary(LENGHT * 2));
        }
    }

    public Bipolar(String bits) {
        super(MAXIMIZE);

        setBest(NUMBER_OF_BLOCKS * MAX_VALUE);
        for (int i = 0; i < NUMBER_OF_BLOCKS; i++) {
            addGene(new GeneBinary(bits));
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
        StringBuilder buf = new StringBuilder();
        buf.append("Bipolar Deceptive Problem <").append(NUMBER_OF_BLOCKS).append("> <").append(LENGHT).append(">");
        buf.append("\n\nBipolar Ackley Trap Function :\n");
        buf.append("\nPosition of valey (z) :" + MIN_POSITION);
        buf.append("\nLocal Optima Value (a) :" + MAX_VALUE);
        buf.append("\nGlobal Optima Value(b) :" + MIN_VALUE);

        buf.append("\n\nParameters    <BLOCKS> <L>");
        buf.append("\n     <BLOCKS> number of Genes");
        buf.append("\n     <l> Size of genome");

        return buf.toString();

    }

    @Override
    protected double fitness() {
        double total = 0;
        //for each block
        for (int i = 0; i < getNumGenes(); i++) {
            total += getBlockValue(i);
        }
        return total;
    }
    //--------------------------------------------------------------------------

    public double getBlockValue(int numGene) {
        int u = getGene(numGene).getAlels().getNumberOfOnes();
        //simetry
//        if (u > LENGHT) {
//            u = LENGHT * 2 - u;
//        }
        u = Math.abs(LENGHT - u);
        if (u < MIN_POSITION) {
            return ((MAX_VALUE / (double) MIN_POSITION) * (MIN_POSITION - u));
        }
        return MIN_VALUE / (double) (LENGHT - MIN_POSITION) * (u - MIN_POSITION);
    }
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

    @Override
    public String getParameters() {
        return NUMBER_OF_BLOCKS + " " + LENGHT*2 ;
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
                LENGHT /=2;
                if (LENGHT <= 0) {
                    LENGHT = 8;
                }
            } catch (Exception e) {
                LENGHT = 8;
            }
        }
        MIN_VALUE = LENGHT;
        MAX_VALUE = LENGHT - 1;
        //----------------------------------------------
        // Set new Genotype to this individual
       this.restart();
        //----------------------------------------------


    }

    public static void main(String[] args) {

        StringBuilder bits = new StringBuilder("0000000000");
        Bipolar b = new Bipolar(bits.toString());
        b.evaluate();
        System.out.println(0 + " " + b);

        for (int i = 0; i < bits.length(); i++) {
            bits.setCharAt(i, '1');
            b = new Bipolar(bits.toString());
            b.evaluate();
            System.out.println((i + 1) + " " + b);

        }

    }
}
