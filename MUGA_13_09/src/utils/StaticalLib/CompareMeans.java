package utils.StaticalLib;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import utils.StaticalLib.Distributions.T_Student;

/**
 *
 * @author manso
 */
public class CompareMeans {

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    /**
     * Test if means are equals with two samples not paired
     * @param v1 values of first sample
     * @param v2 values of second sample
     * @param signif level of significance ( 0.95 = 95% )
     * @return  true if means are equal
     */
    public static boolean MeansAreEqualsNotPaired(double[] v1, double[] v2, double signif) {
        double t_obs = P_value_np(v1, v2, signif);
        double tc = T_Student.twoTailed(signif, v1.length + v2.length - 2);
        if (t_obs > tc) {
            return false;
        }
        return true;
    }
    /**
     * Test if means are equals with two paired samples
     * @param v1 values of first sample
     * @param v2 values of second sample
     * @param signif level of significance ( 0.95 = 95% )
     * @return  true if means are equal
     */
    public static boolean MeansAreEqualsPaired(double[] v1, double[] v2, double signif) {
        double t_obs = P_value_p(v1, v2, signif);
        double tc = T_Student.twoTailed(signif, v1.length - 1);
        if (Math.abs(t_obs) > tc) {
            return false;
        }
        return true;
    }
    /**
     * Test if means are equals with two samples where variance are differents
     * @param v1 values of first sample
     * @param v2 values of second sample
     * @param signif level of significance ( 0.95 = 95% )
     * @return  true if means are equal
     */
    public static boolean MeansAreEqualsDifVariance(double[] v1, double[] v2, double signif) {
        //medias dos valores
        double x1 = mean(v1);
        double x2 = mean(v2);
        //variancia
        double s1 = variance(v1);
        double s2 = variance(v2);
        //size
        int n1 = v1.length;
        int n2 = v2.length;
        double t_obs = (x2 - x1) / (Math.sqrt(s1 / n1 + s2 / n2));
        double g =
                Math.pow(s1 / n1 + s2 / n2, 2)
                / (Math.pow(s1 / n1, 2) / (n1 - 1)
                + Math.pow(s2 / n2, 2) / (n2 - 1));

        double tc = T_Student.twoTailed(signif, g);
        if (Math.abs(t_obs) > tc) {
            return false;
        }
        return true;
    }
    //--------------------------------------------------------------------------
    public static double mean(double[] v) {
        double sum = 0.0;
        for (int i = 0; i < v.length; i++) {
            sum += v[i];
        }
        return sum / v.length;
    }
    //--------------------------------------------------------------------------
    /**
     * P-value of not paired samples
     *
     * @param v1 first sample
     * @param v2 second sample
     * @param signif significance (0.5 - 1.0)
     * @return p-value
     */
    public static double P_value_np(double[] v1, double[] v2, double signif) {
        //variancia ponderada
        double S = varianceWeighted(v1, v2);
        //medias dos valores
        double x1 = mean(v1);
        double x2 = mean(v2);
        //tamanho da amostra
        int n1 = v1.length;
        int n2 = v2.length;
        //t observado
        return (x2 - x1) / Math.sqrt(S * (1.0 / n1 + 1.0 / n2));
    }
    //--------------------------------------------------------------------------

    /**
     * P-value of paired samples
     *
     * @param v1 first sample
     * @param v2 second sample
     * @param signif significance (0.5 - 1.0)
     * @return p-value
     */
    public static double P_value_p(double[] v1, double[] v2, double signif) {
        //diferencas
        double[] d = difs(v1, v2);
        //media das diferencas
        double md = mean(d);
        //variancia das diferencas
        double s = variance(d);
        if(s==0)
            return 0;
        //T observado
        return md / Math.sqrt(s / d.length);
    }
    //--------------------------------------------------------------------------

    /**
     * P-value of paired samples
     *
     * @param v1 first sample
     * @param v2 second sample
     * @param signif significance (0.5 - 1.0)
     * @return p-value
     */
    public static double P_value_sDif(double[] v1, double[] v2, double signif) {
        //medias dos valores
        double x1 = mean(v1);
        double x2 = mean(v2);
        //variancia
        double s1 = variance(v1);
        double s2 = variance(v2);
        return (x2 - x1) / (Math.sqrt(s1 / v1.length + s2 / v2.length));
    }
    //--------------------------------------------------------------------------
    //calculo da variancia 
    private static double variance(double[] v) {
        double x = 0;
        double x2 = 0.0;
        int n = v.length;
        for (int i = 0; i < n; i++) {
            x2 += v[i] * v[i];
            x += v[i];
        }
        return (x2 - (x * x) / n) / (n - 1);
    }
    //--------------------------------------------------------------------------    
    //variancia ponderada
    private static double varianceWeighted(double[] v1, double[] v2) {
        double S2_1 = variance(v1);
        double S2_2 = variance(v2);
        int n1 = v1.length;
        int n2 = v2.length;

        return ((n1 - 1) * S2_1 + (n2 - 1) * S2_2) / (n1 + n2 - 2);
    }
    //--------------------------------------------------------------------------
    private static double[] difs(double[] v1, double[] v2) {
        double[] d = new double[v1.length];
        for (int i = 0; i < d.length; i++) {
            d[i] = v2[i] - v1[i];
        }
        return d;
    }
    //--------------------------------------------------------------------------
    public static void main(String[] args) {
        //--------------------------------------------------------------------------
        //http://bioestatistica.wikispaces.com/file/view/Aula_teste_t.pdf
        //--------------------------------------------------------------------------
        double[] x1 = {12, 8, 15, 13, 10, 12, 14, 11, 12, 13};
        double[] x2 = {15, 19, 15, 12, 13, 16, 15};

        System.out.println("Not Paired =" + MeansAreEqualsNotPaired(x1, x2, 0.95));

        double[] d1 = {77, 62, 61, 80, 90, 72, 86, 59, 88};
        double[] d2 = {80, 58, 61, 76, 79, 69, 90, 51, 81};
        System.out.println("Paired =" + MeansAreEqualsPaired(d1, d1, 0.95));

        //medias dos valores
        double k1 = 49.29;
        double k2 = 48.54;
        //variancia
        double s1 = 5.76;
        double s2 = 6.30;
        //size
        int n1 = 1442;
        int n2 = 1361;
        double t_obs = (k2 - k1) / (Math.sqrt(s1 / n1 + s2 / n2));
        double g =
                Math.pow(s1 / n1 + s2 / n2, 2)
                / (Math.pow(s1 / n1, 2) / (n1 - 1)
                + Math.pow(s2 / n2, 2) / (n2 - 1));


        double tc = T_Student.twoTailed(0.95, g);
        System.out.println("Tc " + tc + " \tTobs " + t_obs);

    }
}
