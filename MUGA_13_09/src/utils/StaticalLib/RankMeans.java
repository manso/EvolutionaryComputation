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
package utils.StaticalLib;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class RankMeans {
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    public static double[][] p_values(double[][] v, double sigificance, boolean right) {
        //number of tests
        int size = v.length;
        //matrtix of comparisons
        double[][] res = new double[size][size];
        //for all tests
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //calculate test i-j
                res[i][j] = CompareMeans.P_value_p(v[i], v[j], sigificance);
            }
        }
        return res;
    }
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    public static int[][] Compare(double[][] v, double sigificance, boolean right) {
        //number of tests
        int size = v.length;
        //matrtix of comparisons
        int[][] res = new int[size][size];
        //for all tests
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //calculate test i-j
                boolean test = CompareMeans.MeansAreEqualsPaired(v[i], v[j], sigificance);
                //mean are equal
                if (test) {
                    res[i][j] = 0;
                } //means are differents
                //compare means
                else if (CompareMeans.mean(v[i]) > CompareMeans.mean(v[j])) {
                    if (right) {
                        res[i][j] = 1;
                    } else {
                        res[i][j] = -1;
                    }
                } else {
                    if (right) {
                        res[i][j] = -1;
                    } else {
                        res[i][j] = 1;
                    }
                }
            }
        }
        return res;
    }

    public static int[] rank(double[][] v, double sigificance, boolean right) {
        //matrtix of comparisons
        int[][] res = Compare(v, sigificance, right);
        int[] sum = new int[res.length];
        int[] index = new int[res.length];
        //compute sums
        for (int i = 0; i < sum.length; i++) {
            sum[i] = 0;
            index[i] = i;
            for (int j = 0; j < sum.length; j++) {
                sum[i] += res[i][j];
            }
        }
        return  getRank(sum);
     }
    
    private static int[] getRank(int [] sum){
        int[] index = new int[sum.length];
        //compute indexes
        for (int i = 0; i < sum.length; i++) {
            index[i] = i;
        }
        //sort sums
        int aux;
        for (int j = 0; j < index.length; j++) {
            for (int i = 0; i < index.length-1; i++) {                
                if (sum[i] < sum[i + 1]) {
                    //sort sums
                    aux = sum[i + 1];
                    sum[i + 1] = sum[i];
                    sum[i] = aux;
                    //change index
                    aux = index[i + 1];
                    index[i + 1] = index[i];
                    index[i] = aux;
                }
            }
        }//sort
//        System.out.println("SORT " + Arrays.toString(sum));
        int [] rank = new int[sum.length];
        int [] rnumbers = normalizeRanks(sum);
        for (int i = 0; i < rnumbers.length; i++) {
            rank[ index[i] ] = rnumbers[i];          
        }
        return rank;
    }
    private static int [] normalizeRanks(int [] v){
        int [] r = new int[v.length];
        r[0]=1;
        for (int i = 1; i < r.length; i++) {
            if(v[i]== v[i-1])
                r[i]= r[i-1];
            else
                r[i] = i+1;
        }
        return r;
    }

    public static String toStringCSV(int[][] res) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                str.append(res[i][j] + ",\t");
            }
            str.append("\n");
        }
        return str.toString().trim();

    }

    public static void main(String[] args) {
        double[][] v = {
            {77, 62, 61, 80, 97, 72, 86, 59, 88},
            {8, 9, 9, 9, 9, 9, 19, 7, 12},
            {177, 162, 161, 180, 197, 172, 186, 159, 188},
            {80, 95, 91, 96, 99, 99, 99, 71, 91},
            {78, 62, 61, 84, 97, 72, 88, 59, 84},        
        };

        int[][] m = Compare(v, 0.95, true);
        System.out.println(toStringCSV(m));
        int [] r = rank(v, 0.95, true);
        System.out.println(Arrays.toString(r));
        
        
        int [] sums= {0,20,10,90,9,40,400,10,9,20,1};
        int []rnk = getRank(sums);
        System.out.println("RANK :" + Arrays.toString(rnk));

    }
}
