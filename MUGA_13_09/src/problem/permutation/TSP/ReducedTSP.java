package problem.permutation.TSP;


import java.awt.geom.Line2D;

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
public class ReducedTSP extends AbstractTSP {

    public ReducedTSP(double[] x, double[] y, int[] opt) {
        super(x, y, opt);
        calculateDistance2(x, y);

    }
    private static double REMOVED = 1E99;
    private static double TOLERANCE = 1E-5;

    public static void calculateDistance2(double[] xx, double[] yy) {
        distance = new double[_x.length][_x.length];
       // printEdges();

        for (int i = 0; i < distance.length; i++) {
            //calculate distances
            for (int j = i + 1; j < distance.length; j++) {
                double dx = _x[j] - _x[i];
                double dy = _y[j] - _y[i];

                distance[i][j] = Math.sqrt(dx * dx + dy * dy);
                //System.out.println("\nVerify edge [" + i + "," + j + "]");
                removeEdges(i, j);
               // printEdges();
            }
        }
        //fill above matriz        
        for (int i = 0; i < distance.length; i++) {
            for (int j = i + 1; j < distance.length; j++) {
                distance[j][i] = distance[i][j];
            }
        }
        printEdges();
    }
    

    public static void removeEdges(int start, int end) {
        Line2D l = new Line2D.Double(_x[start], _y[start], _x[end], _y[end]);
        //compare with calculated edges
        for (int i = 0; i < start; i++) {            
            for (int j = i + 1; j < _x.length; j++) {
                //System.out.print("[" + start + "," + end + "]--x--[" + y + "," + x + "]");
                //the edge is always removed
                if( distance[i][j] == REMOVED) continue;
                //verify if i or j points are in the liene between start o---o---o end 
                if (l.ptSegDist(_x[i], _y[i]) < TOLERANCE
                        || l.ptSegDist(_x[j], _y[j]) < TOLERANCE) {
                   //Point in the line
                    continue;
                    // System.out.println(" (0) ");
                } //intersection of lines                
                else if (l.intersectsLine(_x[j], _y[j], _x[i], _y[i])) {
                    if (distance[start][end] > distance[i][j]) {
                        distance[start][end] = REMOVED;
                    } else if (distance[i][j] > distance[start][end]) {
                        distance[i][j] = REMOVED;
                    }

                }
            }

        }
    }

    public static void printEdges() {

        System.out.println("\n\n-----------------------------");
        for (int i = 0; i < distance.length; i++) {
            //calculate distances
            System.out.println("");
            for (int j = 0; j < distance.length; j++) {
                System.out.printf("%7.2f ", distance[i][j]);
            }
        }

    }

//    public static void main(String[] args) {
//        RsimpleTSP r = new RsimpleTSP();
//    }
}
