///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Ant�nio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Polit�cnico de Tomar                                 ****/
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
public class Ulisses16 extends AbstractTSP {

    static int[] best = {0, 15, 11, 12, 13, 5, 6, 9, 8, 10, 4, 14, 7, 3, 1, 2};
    static double[] x = {38.24, 39.57, 40.56, 36.26, 33.48, 37.56, 38.42, 37.52,
        41.23, 41.17, 36.08, 38.47, 38.15, 37.51, 35.49, 39.36};
    static double[] y = {20.42, 26.15, 25.32, 23.12, 10.54, 12.19, 13.11, 20.44,
        9.10, 13.05, -5.21, 15.13, 15.35, 15.17, 14.32, 19.56};

    public Ulisses16() {
        super(x, y, best);
    }
}
