package utils.MetricsA;

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
import problem.Individual;
import problem.permutation.SimplePermutation;
import problem.permutation.TSP.SimpleTSP;
import problem.permutation.TSP.Ulisses16;

/**
 *
 * @author ZULU
 */
public class Distances {

    private static int minimum(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    public static int computeLevenshteinDistance(CharSequence str1,
            CharSequence str2) {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= str2.length(); j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1]
                        + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0
                        : 1));
            }
        }

        return distance[str1.length()][str2.length()];
    }

    public static int computeLevenshteinDistance(int[] v1, int[] v2) {
        int[][] distance = new int[v1.length + 1][v2.length + 1];
        for (int i = 0; i <= v1.length; i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= v2.length; j++) {
            distance[0][j] = j;
        }
        for (int i = 1; i <= v1.length; i++) {
            for (int j = 1; j <= v2.length; j++) {
                distance[i][j] = minimum(
                        distance[i - 1][j] + 1,
                        distance[i][j - 1] + 1,
                        distance[i - 1][j - 1]
                        + ((v1[i - 1] == v2[j - 1]) ? 0
                        : 1));
            }
        }
        return distance[v1.length][v2.length];
    }

    public static int editDistance(Individual ind1, Individual ind2) {
        int[][] dp = new int[ind1.getNumGenes() + 1][ind2.getNumGenes() + 1];

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = i == 0 ? j : j == 0 ? i : 0;
                if (i > 0 && j > 0) {
                    if (ind1.getGene(i - 1).equals(ind2.getGene(j - 1))) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = Math.min(dp[i][j - 1] + 1, Math.min(
                                dp[i - 1][j - 1] + 1, dp[i - 1][j] + 1));
                    }

                }
            }
        }
        return dp[ind1.getNumGenes()][ind2.getNumGenes()];
    }

    public static int editDistance(int[] ind1, int[] ind2) {
        int[][] dp = new int[ind1.length + 1][ind2.length + 1];

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = i == 0 ? j : j == 0 ? i : 0;
                if (i > 0 && j > 0) {
                    if (ind1[i - 1] == ind2[j - 1]) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = Math.min(dp[i][j - 1] + 1, Math.min(
                                dp[i - 1][j - 1] + 1, dp[i - 1][j] + 1));
                    }
                }
            }
        }
        return dp[ind1.length][ind2.length];
    }

    public static int distanceTSP(int a[], int b[]) {
        int count = 0;
        int size = a.length;
        //for all aa´s
        for (int i = 0; i < a.length; i++) {
            int j = 0;
            //find b = a
            while (b[j] != a[i] && j < b.length) {
                j++;
            }
            //verify foward of b is the forward of a
            if (a[(i + 1) % size] == b[(j + 1) % size]
                    //verify backward
                    || a[(i + 1) % size] == b[(j + size - 1) % size]) {
                // System.out.println("" + a[i]);
                count++;
            }
        }

        return size - count;
    }

//    public static void main(String[] args) {
//        SimplePermutation t1 = new SimplePermutation(20);
//        for (int i = 0; i < 10; i++) {            
//            SimplePermutation t2 = new SimplePermutation(20);
//            System.out.println("\n"+ t1.toString() + "\n" + t2.toString() + " DISTANCE :"
//                    + computeLevenshteinDistance(t1.getGeneValues(), t2.getGeneValues())
//                    + " EDIT: " + editDistance(t1.getGeneValues(), t2.getGeneValues()));
//          }         
//        }
    public static void main(String[] args) {

        int[] i1 = {1, 2, 3, 4, 5, 6};
        int[] i2 = {3, 2, 5, 4, 1, 6};
        int[] i3 = {4, 1, 2, 6, 3, 5};
        System.out.println("Distance 1-2 = " + distanceTSP(i1, i2));
        System.out.println("Distance 1-3 = " + distanceTSP(i1, i3));
        System.out.println("Distance 3-2 = " + distanceTSP(i3, i2));
        System.out.println("Distance 1-1 = " + distanceTSP(i1, i1));

        Ulisses16 u1 = new Ulisses16();
        Ulisses16 u2 = new Ulisses16();
        System.out.println(u1);
        System.out.println(u2);
        System.out.println("distance = " + u1.distanceTo(u2));
    }
}