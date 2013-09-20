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
package problem.permutation;

import java.util.Random;
import java.util.StringTokenizer;
import operator.mutation.permutation.MWM_perm;
import problem.PRM_Individual;

/**
 *
 * @author ZULU
 */
public class HIFF_perm extends PRM_Individual {

    public static int LEVEL = 5; // height of tree
    public static int K_ARY = 2; // arity of leafs
    public static int SIZE = (int) Math.pow(K_ARY, LEVEL); //number of bits = K_ARY ^ LEVEL
    static Random rnd = new Random();
    public static int[] bitOrder;

    static {
        shuffleOrder();
    }

    /**
     * randomize the bit order of HIFF_K function
     */
    public static void shuffleOrder() {
        bitOrder = new int[SIZE];
        for (int i = 0; i < bitOrder.length; i++) {
            bitOrder[i] = i;
        }
        //shuffle order
        for (int i = bitOrder.length - 1; i > 0; i--) {
            //random index
            int index = rnd.nextInt(i);
            int aux = bitOrder[i];
            bitOrder[i] = bitOrder[index];
            bitOrder[index] = aux;
        }
        bitOrder = repair(bitOrder);
//        Console.println("ORDER : " + Arrays.toString(bitOrder));
    }

    public static int[] repair(int[] genome) {
        int[] ordered = new int[genome.length];
        int min = Math.abs(genome[0]);
        int index = 0;
        for (int i = 1; i < genome.length; i++) {
            if (Math.abs(genome[i]) < min) {
                min = Math.abs(genome[i]);
                index = i;
            }
        }
        for (int i = 0; i < ordered.length; i++) {
            ordered[i] = genome[ (index + i) % ordered.length];
        }
        return ordered;
    }

    public HIFF_perm() {
        super(MAXIMIZE, SIZE);
    }

    @Override
    public void fillRandom() {
        //add values
        for (int i = 0; i < genome.length; i++) {
            genome[i] = i + 1;
        }
        //shufle
        int index = genome.length - 1;
        while (index > 0) {
            int i = random.nextInt(index);
            int aux = genome[i];

            genome[i] = Math.abs(genome[index]);
            if (rnd.nextBoolean()) {
                genome[index] = aux;
            } else {
                genome[index] = -aux;
            }
            index--;
        }

//        Console.println("GENOME \t" + Arrays.toString(genome));

    }

    public String getHIFF() {
        StringBuilder bits = new StringBuilder(getBitsGenome());
        StringBuilder order = new StringBuilder(bits.length());
        for (int i = 0; i < bitOrder.length; i++) {
            order.append(bits.charAt(bitOrder[i]));
        }
        return order.toString();
    }

    public String getBitsGenome() {
        StringBuilder bits = new StringBuilder(genome.length);
        //add ones
        for (int i = 0; i < genome.length; i++) {
            bits.append("1");
        }
        //replaces ones to zeros
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] < 0) {
                bits.setCharAt(-genome[i] - 1, '0');
            }
        }
        return bits.toString();
    }

    @Override
    protected double fitness() {
        return genome.length + HIFF_Value(getHIFF(), 1);
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
        shuffleOrder();
        //----------------------------------------------
        // Set new Genotype to this individual
        restart();
//        shuffleOrder();
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
        String hiff = getHIFF();
        return getBitsGenome() + " = " + hiff + " " + HIFF_decode(hiff) + " " + toString();
    }

    public String HIFF_decode(String bits) {
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
        return " " + newLevel + HIFF_decode(newLevel.toString());
    }

    /**
     * Clone of the individula
     *
     * @return clone
     */
    @Override
    public HIFF_perm getClone() {
        //make new individuals
        HIFF_perm ind = new HIFF_perm();
        //set the same information of this
        ind.setIndividual(this);
        return ind;
    }

    public static void main(String[] args) {
        HIFF_perm h = new HIFF_perm();
        h.evaluate();
        MWM_perm mut = new MWM_perm();

        for (int i = 1; i < 10; i++) {
            HIFF_perm h1 = (HIFF_perm) h.getClone();
            h1.setNumCopys(i);
            System.out.println("\nH = " + h1.toStringPhenotype());
            mut.doMutation(h1);
            h1.evaluate();
            System.out.println("M = " + h1.toStringPhenotype());


        }
    }
}
