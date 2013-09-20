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
public class HTrap1 {

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
        return sum + HTrap_Value(newLevel.toString(), level + 1, l);
    }

    public static double evaluateTrap3HIFF(BitField bits) {
        return bits.getNumberOfBits() + HTrap_Value(bits.toString(), 1, bits.getNumberOfBits());
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
        BitField b = new BitField("000000000");
        System.out.println(b.toString() + " = " + evaluateTrap3HIFF(b));
        b = new BitField("111111111");
        System.out.println(b.toString() + " = " + evaluateTrap3HIFF(b));
        b = new BitField("111111000");
        System.out.println(b.toString() + " = " + evaluateTrap3HIFF(b));
        for (int i = 0; i < 1000; i++) {
            b= new BitField(9);
            b.fillRandom();           
            System.out.println(b.toString() + " = " + evaluateTrap3HIFF(b));
        }
    }
}
