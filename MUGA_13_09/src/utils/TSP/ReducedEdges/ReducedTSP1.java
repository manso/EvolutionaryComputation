package utils.TSP.ReducedEdges;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import problem.permutation.TSP.AbstractTSP;
import utils.Delaunay.Delaunay;

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
public class ReducedTSP1 extends AbstractTSP {

    public ReducedTSP1(double[] x, double[] y, int[] opt) {
        super(x, y, opt);
//        distance = Delaunay.getDelaunay(x, y);
//         printEdges();
        calculateDistance2(x, y);

    }
    private static double REMOVED = 99;
    private static double TOLERANCE = 1E-15;

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
                printEdges();
            }
        }
        //fill above matriz        
        for (int i = 1; i < distance.length; i++) {
            for (int j = 0; j < i; j++) {
                distance[i][j] = distance[j][i];
            }
        }
        printEdges();
    }

    public static boolean collinear(int p1, int p2, int p3) {
        return Math.abs((_x[p2] - _x[p1]) * (_y[p3] - _y[p1]) - (_x[p3] - _x[p1]) * (_y[p2] - _y[p1])) < TOLERANCE;
    }

    public static void removeEdges(int start, int end) {

        //verify collinearity in the current line (start)
        for (int i = start+1; i < end; i++) {
            if (distance[start][i] != REMOVED && collinear(start, end, i)) {
                if (distance[start][i] > distance[start][end]) {
                    distance[start][i] = REMOVED;
                } else {
                    distance[start][end] = REMOVED;
                    return;
                }
            }
        }


        Line2D edge = new Line2D.Double(_x[start], _y[start], _x[end], _y[end]);
        //compare with calculated edges
        for (int i = 0; i < start; i++) {
            for (int j = i + 1; j < _x.length; j++) {
//                System.out.print("\n [" + start + "," + end + "]--x--[" + i + "," + j + "]");
                //the edge is always removed
                if (distance[i][j] == REMOVED) {
                    continue;
                }

                //adjacent edge
                if (i == start || i == end || j == start || j == end) {
                    continue;
                }
//                
//                Point2D inter = intersection(_x[start], _y[start], _x[end], _y[end], _x[i], _y[i], _x[i], _y[i]);
//                System.out.println("Intersection " + inter);
                Line2D test = new Line2D.Double(_x[i], _y[i], _x[j], _y[j]);
                //verify if i or j points are in the line between start o---o---o end 
                if (edge.ptSegDist(_x[i], _y[i]) < TOLERANCE
                        || edge.ptSegDist(_x[j], _y[j]) < TOLERANCE
                        || test.ptSegDist(_x[start], _y[start]) < TOLERANCE
                        || test.ptSegDist(_x[end], _y[end]) < TOLERANCE) {
                    //Point in the line
                    continue;
                    // System.out.println(" (0) ");
                } //intersection of lines                
                else
                    if (edge.intersectsLine(_x[j], _y[j], _x[i], _y[i])) {
                    //remove largest edge
                    if (distance[start][end] > distance[i][j]) {
                        distance[start][end] = REMOVED;

                    } else if (distance[i][j] > distance[start][end]) {
                        distance[i][j] = REMOVED;
                    } //remove both if the leght are equal
                    else {
                        distance[start][end] = REMOVED;
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
                if (distance[i][j] >= REMOVED) {
                    System.out.printf("   -    ", distance[i][j]);
                } else {
                    System.out.printf("%7.2f ", distance[i][j]);
                }
            }
        }

    }
//    public static void main(String[] args) {
//        RsimpleTSP r = new RsimpleTSP();
//    }
}
