/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.realCoded.CEC2008;

import java.util.StringTokenizer;
import utils.Fractal.FastFractal;

/**
 *
 * @author manso
 */
public class F7_Fast_Fractal extends RC_IndividualCEC2008 {

    public static double MIN = -1;
    public static double MAX = 1;
    public static FastFractal ff = null;

    public static void initFractalFunction(int numGenes) {
        try {
            ff = new FastFractal("utils.Fractal.DoubleDip", 3, 1, 1, numGenes);
        } catch (Exception e) {

            System.out.println("ERROR :" + e.getMessage());
        }
    }

    public F7_Fast_Fractal() {
        super(MIN, MAX);
        if (ff == null || ff.getDimensions() != numGenes) {
            initFractalFunction(numGenes);
        }
    }

    @Override
    public double fitness() {
        return ff.evaluate(genome);
    }
    static double[] opt = new double[1000];

    @Override
    public double[] getOptimum() {
        return opt;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

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
