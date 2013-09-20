/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.realCoded.CEC2008;

import java.util.StringTokenizer;

/**
 *
 * @author manso
 */
public class F6_Shifted_Schaffer extends RC_IndividualCEC2008 {

    public static double MIN = -10;
    public static double MAX = 10;

    public F6_Shifted_Schaffer() {
        super(MIN, MAX, 2);
    }

    @Override
    public double fitness() {
        double[] values = genome;
        double x = values[0] - schaffer[0];
        double y = values[1] - schaffer[1];
        return 0.5 + (Math.pow(Math.sin(Math.sqrt(x * x + y * y)), 2) - 0.5)
                / Math.pow(1.0 + 0.001 * (x * x + y * y), 2);
    }

    @Override
    public double[] getOptimum() {
        return schaffer;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
    //-------------------------------------------------------------------------
    static double[] schaffer = {
        -1.432, 1.0234
    };

    @Override
    public void setParameters(String params) {
        StringTokenizer par = new StringTokenizer(params);
        try {
            numGenes = Integer.parseInt(par.nextToken());
        } catch (Exception e) {
            numGenes = 2;
        }
        if (numGenes < 2) {
            numGenes = 2;
        }
        try {
            MIN = Double.parseDouble(par.nextToken());
            MAX = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
        }
        if (MIN >= MAX) {
            MAX = MIN;
            MIN = -MAX;
        }
        createGenes(numGenes, MIN, MAX);
    }

    @Override
    public String getInfo() {
        return getClass().getSimpleName() + "(" + numGenes + ") interval["
                + MIN + "," + MAX + "]";
    }

    @Override
    public String getGenomeInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append("\n\nParameters:<Size><Min><Max>");
        buf.append("\n\t<Size> Number of genes");
        buf.append("\n\t<Min>  Minumum of the interval");
        buf.append("\n\t<Max>  Maximum of the interval");
        buf.append("\n\nOptimum:");
        double[] opt = getOptimum();
        for (int i = 0; i < numGenes; i++) {
            buf.append("\n\t" + i + "\t" + opt[i]);
        }
        return buf.toString();
    }
}
