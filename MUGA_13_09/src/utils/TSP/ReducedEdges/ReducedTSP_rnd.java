package utils.TSP.ReducedEdges;

///****************************************************************************/
import java.util.Random;

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
public class ReducedTSP_rnd extends ReducedTSP {

    static int DIM = 50;
    static int[] optimum = {};
    static double[] xx;
    static double[] yy;

    public static boolean contains(double x, double y) {
        for (int i = 0; i < xx.length; i++) {
            if (xx[i] == x && yy[i] == y) {
                return true;
            }
        }
        return false;
    }

    static {
        Random rnd = new Random();
        xx = new double[DIM];
        yy = new double[DIM];
        double x, y;
        for (int i = 0; i < xx.length; i++) {
            do {
                x = (int) (rnd.nextDouble() * DIM /2);
                y = (int) (rnd.nextDouble() * DIM /2);
            } while (contains(x, y));
            xx[i] = x;
            yy[i] = y;
        }
    }

    public ReducedTSP_rnd() {
        super(xx, yy, optimum);
    }
}
