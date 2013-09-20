package utils.Knapsack;

/*
 * Dynamic programming solution of the 0/1 knapsack.
 *
 * Author: Timothy Rolfe
 *
 * Instead of using the items one at a time across all weights, this algorithm
 * computes the complete solution vector for each weight. Consequently the
 * matrix has (maxWt+1) rows of (n+1) columns --- boolean
 * trial[maxWeight+1][n+1]. Then there is a vector bestVal[maxWeight] giving the
 * value for each weight.
 *
 * When computing a potential solution, search through items and only examine
 * ones that will not exceed the current weight limit. If the item does not
 * exceed the current limit, check to see whether this item is already being
 * used in the earlier partial solution:
 *
 * testWt = wt - weight[k]; if ( testWt >= 0 && ! trial[testWt][k] )
 *
 * The final check is to see if this would improve the best seen so far ---
 * bestVal[wt] <= value[k]+bestVal[testWt] In this case, retain bestK=k and
 * update bestVal[wt].
 *
 * Note that it is possible that there IS no bestK --- given the items, a
 * knapsack of this weight cannot be generated. In that case, just copy the
 * (wt-1) knapsack.
 */
public class KnapSack_DP {
// ? ?  Show EACH knapsack from 1 up to final solution ? ?

    public static boolean[] best = null;

    public static double getBestknapSack01Value(double maxWeight, double[] w, double[] v) {
        //  pack = new boolean[weight.length + 1];
        int[] weight = new int[w.length + 1];
        for (int i = 0; i < w.length; i++) {
            weight[i + 1] = (int) w[i];
        }
        double[] value = new double[v.length + 1];
        for (int i = 0; i < v.length; i++) {
            value[i + 1] = v[i];
        }
        boolean[] pack = new boolean[v.length + 1];
        CalculateknapSack01((int) maxWeight, pack, weight, value);
        return getBestKnapSackValue((int) maxWeight, pack, value);
    }

    static void CalculateknapSack01(int maxWeight, boolean[] pack,
            int[] weight, double[] value) {
        int n = pack.length - 1;
        boolean[][] trial = new boolean[maxWeight + 1][n + 1];
        double[] bestVal = new double[maxWeight + 1];
        int wt, k;

        for (wt = 1; wt <= maxWeight; wt++) {
            int bestK = 0, testWt;

            // Initial guess:  the knapsack for wt-1.
            bestVal[wt] = bestVal[wt - 1];
            for (k = 1; k <= n; k++) {
                testWt = wt - weight[k];
                if (testWt >= 0 && !trial[testWt][k]) {
                    if (bestVal[wt] < value[k] + bestVal[testWt]) {
                        bestK = k;
                        bestVal[wt] = value[k] + bestVal[testWt];
                    }
                }
            }
            if (bestK > 0) {
                testWt = wt - weight[bestK];
                System.arraycopy(trial[testWt], 0, trial[wt], 0, n + 1);
                trial[wt][bestK] = true;
            } else // Can't get here, so finish using the wt-1 solution
            {
                System.arraycopy(trial[wt - 1], 0, trial[wt], 0, n + 1);
            }
        }
        System.arraycopy(trial[maxWeight], 0, pack, 0, n + 1);
    }

    static double getBestKnapSackValue(int maxWeight, boolean[] pack, double[] value) {
        double sumValue = 0;
        int n = pack.length - 1;
        best = new boolean[n];
        for (int k = 1; k <= n; k++) {
            //first position of pack is always zero
            best[k - 1] = pack[k];
            if (pack[k]) {
                sumValue += value[k];
            }
        }
        return sumValue;
    }

    public static void main(String[] args) throws Exception {
        double weight[] = {
            94, 70, 90, 97, 54, 31, 82, 97, 1, 58, 96, 96, 87, 53, 62, 89, 68, 58, 81, 83, 67, 41, 50, 58, 61,
            45, 64, 55, 12, 87, 32, 53, 25, 59, 23, 77, 22, 18, 64, 85, 14, 23, 76, 81, 49, 47, 88, 19, 74, 31
        };
        double value[] = {
            3, 41, 22, 30, 45, 99, 75, 76, 79, 77, 41, 98, 31, 28, 58, 32, 99, 48, 20, 3, 81, 17, 3, 62, 39,
            76, 94, 75, 44, 63, 35, 11, 21, 45, 43, 46, 26, 2, 53, 37, 32, 78, 74, 66, 61, 51, 11, 85, 90, 40
        };
//        System.out.println(maxWeight + " BEST : " + getBestknapSack01Value(maxWeight, weight, value));
        for (int maxWeight = 1400; maxWeight < 1500; maxWeight++) {
            System.out.println(maxWeight + " BEST : " + getBestknapSack01Value(maxWeight, weight, value));
        }

    }
}
