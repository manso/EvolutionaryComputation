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
package problem.bitString.Hierarchical;

/**
 *
 * @author ZULU
 */
public class HIFF_II extends HIFF_K {

    public HIFF_II() {
    }

    public HIFF_II(String bits) {
        super(bits);
    }

    public String getLeaf(String bits, int start) {
        StringBuilder txt = new StringBuilder(K_ARY);
        int step = bits.length() / K_ARY;
        for (int i = 0; i < K_ARY; i++) {
            txt.append(bits.charAt(i * step + start));
        }
        return txt.toString();
    }

    @Override
    public double HIFF_Value(String bits, int level) {
        //top of the tree
        if (bits.length() == 1) {
            return 0;
        }
        //new bit level
        double sum = 0;
        StringBuilder newLevel = new StringBuilder(bits.length() / K_ARY);
        //---------------------------------------------------------
        int numLeafs = bits.length() / K_ARY;
        for (int i = 0; i < numLeafs; i++) {
            //------------------- get leaf ---------------
            String leaf = getLeaf(bits, i);
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

    public String HIFF(String bits) {
        //top of the tree
        if (bits.length() == 1) {
            return "";
        }
        StringBuilder newLevel = new StringBuilder(bits.length() / K_ARY);
        //---------------------------------------------------------
        int numLeafs = bits.length() / K_ARY;
        for (int i = 0; i < numLeafs; i++) {
            //------------------- get leaf ---------------
            String leaf = getLeaf(bits, i);
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
        HIFF_II h = new HIFF_II("10111011");
        h.evaluate();
        System.out.println(h.toStringGenotype());
        System.out.println(h.toStringPhenotype());
    }
}
