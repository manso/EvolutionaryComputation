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

import utils.Delaunay.Delaunay;
import utils.TSP.GreadyVertexInsertion;

/**
 *
 * @author ZULU
 */
public class A66_REduced extends AbstractTSP {

    static int[] optimum = {0, 12, 13, 14, 15, 36, 35, 32, 33, 34, 41, 40, 37, 38, 39, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 28, 27, 26, 29, 30, 25, 24, 31, 16, 23, 22, 17, 18, 21, 20, 19, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    static double[] xx = {236, 228, 228, 236, 236, 228, 228, 236, 252, 260, 260, 260, 260, 260, 260, 260, 276, 276, 276, 276, 284, 284, 284, 284, 284, 284, 284, 288, 280, 276, 276, 276, 268, 260, 252, 260, 260, 236, 228, 228, 236, 236, 228, 228, 228, 228, 220, 212, 204, 196, 188, 180, 180, 180, 180, 180, 196, 204, 212, 220, 228, 236, 246, 252, 260, 280};
    static double[] yy = {45, 45, 37, 37, 29, 29, 21, 21, 21, 29, 37, 45, 53, 61, 69, 77, 77, 69, 61, 53, 53, 61, 69, 77, 85, 93, 101, 109, 109, 101, 93, 85, 97, 109, 101, 93, 85, 85, 85, 93, 93, 101, 101, 109, 117, 125, 125, 117, 109, 101, 93, 93, 101, 109, 117, 125, 145, 145, 145, 145, 145, 145, 141, 125, 129, 133};
    static boolean first = true;
        
    
    public A66_REduced() {
        super(xx, yy, optimum);
        distance = Delaunay.getDelaunay(xx, yy);

    }
     @Override
    public void fillRandom() {
        genome = GreadyVertexInsertion.calculate(distance);
        repair(genome);
    }
}
