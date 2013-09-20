///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package utils.Delaunay;

import java.util.Iterator;
import problem.permutation.TSP.AbstractTSP;

/**
 *
 * @author ZULU
 */
public class Delaunay {

    static double[] xx;
    static double[] yy;

    static int getIndex(double vx, double vy) {
        for (int i = 0; i < xx.length; i++) {
            if (xx[i] == vx && yy[i] == vy) {
                return i;
            }

        }
        return -1;
    }

    static double getDistance(int i, int j) {
        double dx = xx[j] - xx[i];
        double dy = yy[j] - yy[i];
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double[][] getDelaunay(double[] x, double[] y) {
        xx = x;
        yy = y;
        double[][] m = new double[x.length][x.length];
        Delaunay_Triangulation dt = new Delaunay_Triangulation();

        for (int i = m.length - 1; i >= 0; i--) {
            dt.insertPoint(new Point_dt(x[i], y[i]));

        }
        Iterator<Triangle_dt> iterator = dt.trianglesIterator();

        
        
        
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                m[i][j] = AbstractTSP.REMOVED_EDGE;
            }

        }
        
        
        while (iterator.hasNext()) {
            Triangle_dt curr = iterator.next();
            if (!curr.isHalfplane()) {
                int i1 = getIndex(curr.p1().x, curr.p1().y);
                int i2 = getIndex(curr.p2().x, curr.p2().y);
                int i3 = getIndex(curr.p3().x, curr.p3().y);

                m[i1][i2] = getDistance(i1, i2);
                m[i2][i1] = m[i1][i2];

                m[i2][i3] = getDistance(i2, i3);;
                m[i3][i2] = m[i2][i3];

                m[i3][i1] = getDistance(i3, i1);;
                m[i1][i3] = m[i3][i1];
            }
        }
        return m;
    }
}
