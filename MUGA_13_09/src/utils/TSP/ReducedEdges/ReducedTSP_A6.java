package utils.TSP.ReducedEdges;

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
/**
 *
 * @author ZULU
 */
public class ReducedTSP_A6 extends ReducedTSP {

    static int[] optimum = {};
    static double[] xx = {1,2,1,0,0,2,0, 0.5};
    static double[] yy = {2,2,1,1,2,0,0,  1 };
//    static double[] xx = {0,0,0,0,1};
//    static double[] yy = {0,1,2,3,3};

//    static double[] xx = {1, 2, 1,   0, 0, 2, 0 , 1.5};
//    static double[] yy = {2, 2, 1.5, 1, 2, 0, 0 , 1.5 };
    public ReducedTSP_A6() {
        super(xx, yy, optimum);
    }
}
