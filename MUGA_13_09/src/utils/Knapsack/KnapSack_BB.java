package utils.Knapsack;

/**
 * 0/1 knapsack problem: Based on item weights and values, find the combination
 * of items to include in the knapsack that will maximize the value, subject to
 * a weight limitation.
 *
 * This uses backtracking, and applies a simple bounding function: if, ignoring
 * weight constraints, ALL remaining items were included in the knapsack, would
 * the value exceed the current best? If not, backtrack immediately.
 *
 * For a dataset which, in the pure backtracking implementation, uses 129
 * invocations of the recursive method, this implementation uses 46 invocations.
 *
 * Author: Timothy Rolfe
 *
 * This version sorts the data into non-increasing by val/wt
 */

public class KnapSack_BB {//If DEBUG is turned on, show each new solution as discovered

    final static boolean DEBUG = false;
// valRemain[j] holds the sum of all values from value[j] on up.
    static double[] valRemain;
    static int nCalls;

    /**
     * Public method will call the recursive backtracking method
     *
     * The recursive method needs to receive and update information on the
     * current state and the optimal solution. This is handled through passing
     * arrays.
     *
     * current[] will hold [0] current weight, [1] current value, and [2]
     * current MAXIMUM value;
     *
     * pack[] will be the boolean vector corresponding to that current MAXIMUM
     * value. It is received as a parameter.
     *
     * used[] is the scratch boolean vector used to generate the possible
     * knapsacks.
     */
    public static void knapSack(double maxWt, boolean[] pack,
            double[] weight, double[] value) {
        double[] current = {0, 0, Double.NEGATIVE_INFINITY};
        boolean[] used = new boolean[pack.length];

        // Generate the bounding vector:  the value of ALL items from
        // the current position up to the top.
        valRemain = new double[value.length];
        for (int j = 0; j < value.length; j++) {
            for (int k = j; k >= 0; k--) {
                valRemain[k] += value[j];
            }
        }
        knapSack(maxWt, used, 0, weight, value, pack, current);
    }

    /**
     * Integer knapsack through backtracking --- implementation to retain
     * information about the knapsack contents.
     *
     * Parameters: maxWt --- carrying capacity of the knapsack used[] ---
     * current knapsack contents item --- position being considered. weight[]
     * --- individual weights value[] --- individual values pack[] --- retain
     * the OPTIMAL boolean vector current[] --- [0] current weight, [1] current
     * value, and [2] current MAXIMUM value
     */
    static void knapSack(double maxWt, boolean[] used, int item,
            double[] weight, double[] value,
            boolean[] pack, double[] current) {
        nCalls++;    // Tally number of method invocations        

        // Check whether all positions have been considered
        if (item == pack.length) {
            // Do we have a winner?
            if (current[1] > current[2]) {
                if (DEBUG) {
                    System.out.println(current[1] + " replaces "
                            + current[2]);
                }
                // Save the state for this winner
                current[2] = current[1];
                System.arraycopy(used, 0, pack, 0, used.length);
            } else if (DEBUG) {
                System.out.println(current[1] + " fails.");
            }
        } else {
            // Are we ABLE to use one of these items?  If so, try that option
            if (current[0] + weight[item] <= maxWt) {
                // Bound based on value:  if we used EVERYTHING from here to the
                // end, would we get something more than the current best?
                if (current[1] + valRemain[item] > current[2]) {
                    // Set the state of using this item
                    used[item] = true;
                    current[0] += weight[item];
                    current[1] += value[item];
                    knapSack(maxWt, used, item + 1, weight, value, pack, current);
                    // Then reset to not using the item
                    used[item] = false;
                    current[0] -= weight[item];
                    current[1] -= value[item];
                } else {
                    if (DEBUG) {
                        System.out.println("Trim value-" + current[1]
                                + " tree at " + item);
                    }
                    return;
                }
            }
            // Whether we used one or not, go forward WITHOUT using one.
            knapSack(maxWt, used, item + 1, weight, value, pack, current);
        }
    }

    static void swap(int p, int q, double[] x) {
        double tmp = x[p];
        x[p] = x[q];
        x[q] = tmp;
    }

    static void sort(double[] wt, double[] val) {
        for (int lim = wt.length - 1; lim > 0; lim--) {
            int max = 0;

            for (int k = 1; k <= lim; k++) {
                if ((double) val[k] / wt[k] < (double) val[max] / wt[max]) {
                    max = k;
                }
            }
            swap(max, lim, wt);
            swap(max, lim, val);
        }
    }

    public static void main(String[] args) throws Exception {
//
//        for (int size = 10; size < 100; size+=5) {
//
//            StronglyCorrelated.createProblem(size, 0.5);
//
//            boolean[] pack;
//            double[] weight = StronglyCorrelated.w;
//            double[] value = StronglyCorrelated.p;
//            int n = weight.length;
//            double maxWeight = StronglyCorrelated.capacity;
//
//            double sumValue = 0, sumWeight = 0;
//
//            pack = new boolean[n];
//            long start = System.currentTimeMillis();
//            sort(weight, value);
//            knapSack(maxWeight, pack, weight, value);
//            long time = System.currentTimeMillis() - start;
//            DateFormat df = new SimpleDateFormat("mm:ss.SSS");
//            System.out.println(size + " time :" + time + " = " + df.format(new Date(time)));
//
//        }
//        System.out.println("Optimal knapsack:");
//        for (int k = 0; k < n; k++) {
//            if (pack[k]) {
//                sumWeight += weight[k];
//                sumValue += value[k];
//                System.out.println("(" + weight[k] + ", " + value[k] + ")");
//            }
//        }
//        System.out.println("Total weight:  " + sumWeight);
//        System.out.println("Total value:   " + sumValue);
//        System.out.println("Total method invocations:  " + nCalls);
    }
}




/* Specimen run

Reading data from file BandP.txt
50 replaces -2147483648
Trim value-44 tree at 6
Trim value-43 tree at 6
47 fails.
Trim value-37 tree at 6
Trim value-38 tree at 6
Trim value-32 tree at 5
Trim value-25 tree at 4
57 replaces 50
58 replaces 57
52 fails.
51 fails.
45 fails.
Trim value-0 tree at 2
Optimal knapsack:
(11, 45)
(2, 7)
(2, 6)
Total weight:  15
Total value:   58
Total method invocations:  46
*/