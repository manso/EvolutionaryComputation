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
public class Geometry {

    public static double[] Intersection(double[] p1, double[] p2, double[] p3) {
        double[] pi = new double[p1.length];
        double[] delta = new double[p1.length];
        double delta2 = 0;
        double u = 0;
        for (int i = 0; i < pi.length; i++) {
            delta[i] = p2[i] - p1[i];
            u += (p3[i] - p1[i]) * delta[i];
            delta2 += delta[i] * delta[i];
        }
        u /= delta2;
        for (int i = 0; i < pi.length; i++) {
            pi[i] = p1[i] + u * delta[i];
        }
        return pi;
    }

    public static Individual Intersection(Individual i1, Individual i2, Individual i3) {
       Individual intersect = i3.getClone();
       double []p1 = i1.getValues();
       double []p2 = i2.getValues();
       double [] p3 = i3.getValues();
       i3.setValues(  Intersection(p1, p2, p3));
       return intersect;
    }


    

}
