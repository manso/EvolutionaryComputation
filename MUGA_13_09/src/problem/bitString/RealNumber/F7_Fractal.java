/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.bitString.RealNumber;

import problem.realCoded.CEC2008.*;
import java.util.StringTokenizer;
import utils.Fractal.FastFractal;

/**
 *
 * @author manso
 */
public class F7_Fractal extends RealBitString  {

    public static double min = -1;
    public static double max = 1;
    public static FastFractal ff = null;

    public static void initFractalFunction(int numGenes) {
        try {
            ff = new FastFractal("utils.Fractal.DoubleDip", 3, 1, 1, numGenes);
        } catch (Exception e) {

            System.out.println("ERROR :" + e.getMessage());
        }
    }

    public F7_Fractal() {
         super(MINIMIZE,min, max);
        if (ff == null || ff.getDimensions() != getNumGenes()) {
            initFractalFunction(getNumGenes());
        }
    }

    @Override
    public double fitness() { 
        double[] x = getGeneValues();
        return ff.evaluate(x);
    }

}
