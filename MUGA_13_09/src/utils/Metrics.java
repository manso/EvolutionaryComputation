/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import problem.Individual;

/**
 *
 * @author manso
 */
public class Metrics {

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

    
   
}
