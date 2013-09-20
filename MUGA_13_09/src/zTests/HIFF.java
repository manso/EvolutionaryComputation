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
package zTests;

import utils.BitField;

/**
 *
 * @author ZULU
 */
public class HIFF {

    public static double HIFF_Value(String bits, int level) {
//        System.out.println(level + "\t BITS = " + bits);
        //top of the tree
        if (bits.length() == 1) {
            if (bits.charAt(0) == '-') {
                return 0;
            }
            return Math.pow(2, level);
        }
        //new bit level
        double sum = 0;
        StringBuilder newLevel = new StringBuilder(bits.length() / 2);
        for (int i = 0; i < bits.length(); i += 2) {
            //false
            if (bits.charAt(i) == '0' && bits.charAt(i + 1) == '0') {
                newLevel.append("0");
                sum += Math.pow(2, level);
            } //true
            else if (bits.charAt(i) == '1' && bits.charAt(i + 1) == '1') {
                newLevel.append("1");
                sum += Math.pow(2, level);
            } else //error
            {
                newLevel.append("-");
            }
        }
        return sum + HIFF_Value(newLevel.toString(), level + 1);
    }

    public static double evaluateHIFF(BitField bits) {
        return bits.getNumberOfBits() + HIFF_Value(bits.toString(), 1);
    }

    static int BEST(int size) {
        int total = 0;
        int dim = size;
        while (size > 1) {
            total += dim;
            size /= 2;
        }
        return total;
    }

    public static void main(String[] args) {
        BitField b = new BitField("00001101");
        System.out.println(evaluateHIFF(b));
        BitField b1 = new BitField("00100011");
        System.out.println(evaluateHIFF(b1));
        int n = 2;
        for (int i = 1; i < 16; i++) {
            BitField x = new BitField(n);
            System.out.println(n + " BEST " + BEST(n) + " EVAL " + evaluateHIFF(x));
            n *= 2;

        }

    }
}
