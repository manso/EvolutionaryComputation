package utils.StaticalLib.Distributions;

import java.util.Random;

/**
 * Wrapper of functions for the Exponential distribution.
 */
public class Laplace {
    static Random rnd = new Random();
    public static double random( double m, double b) {
        double U = rnd.nextDouble() - 0.5;
        if (U < 0) {
            return m + b*Math.log(1 + 2 * U);
        }
        return m - b*Math.log(1 - 2 * U);

    }
}
