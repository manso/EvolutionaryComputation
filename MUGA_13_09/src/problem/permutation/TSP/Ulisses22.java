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
package problem.permutation.TSP;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author ZULU
 */
public class Ulisses22 extends AbstractTSP {

    static int[] best = {0, 13, 12, 11, 6, 5, 14, 4, 10, 8, 9, 18, 19, 20, 15, 2, 1, 16, 3, 17, 21, 7};
    //{0, 15, 20, 19, 18, 9, 8, 10, 4, 14, 5, 6, 11, 12, 13, 7, 21, 17, 3, 16, 1, 2};
    static double[] x = {38.24, 39.57, 40.56, 36.26, 33.48, 37.56, 38.42, 37.52, 41.23, 41.17, 36.08, 38.47, 38.15, 37.51, 35.49, 39.36, 38.09, 36.09, 40.44, 40.33, 40.37, 37.57};
    static double[] y = {20.42, 26.15, 25.32, 23.12, 10.54, 12.19, 13.11, 20.44, 9.10, 13.05, -5.21, 15.13, 15.35, 15.17, 14.32, 19.56, 24.36, 23.00, 13.57, 14.15, 14.23, 22.56};

    public Ulisses22() {
        super(x, y, best);
    }
}
