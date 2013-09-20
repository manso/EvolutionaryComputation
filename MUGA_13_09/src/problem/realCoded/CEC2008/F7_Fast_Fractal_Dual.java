/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.realCoded.CEC2008;

import problem.RC_Individual;

/**
 *
 * @author manso
 */
public class F7_Fast_Fractal_Dual extends F7_Fast_Fractal {

    RC_Individual other;

    public F7_Fast_Fractal_Dual() {
        super();
        other = new F7_Fast_Fractal();
        other.fillRandom();
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

    /**
     * Clone of the individual
     *
     * @return clone
     */
    public RC_Individual getClone() {
        F7_Fast_Fractal_Dual ind = (F7_Fast_Fractal_Dual) super.getClone();
        ind.other = other.getClone();
        return ind;
    }
}
