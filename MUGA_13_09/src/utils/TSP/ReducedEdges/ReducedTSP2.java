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
public class ReducedTSP2 extends AbstractTSP {

    static TSP_Graphics picture = new TSP_Graphics();

    public ReducedTSP2(double[] x, double[] y, int[] opt) {
        super(x, y, opt);
//        distance = Delaunay.getDelaunay(x, y);
//         printEdges();


    }
    private static double REMOVED = 99;
    private static double TOLERANCE = 1E-15;

    public void calculateDistance2() {
        distance = new double[_x.length][_x.length];
        // printEdges();

        for (int i = 0; i < distance.length; i++) {
            //calculate distances
            for (int j = i + 1; j < distance.length; j++) {
                double dx = _x[j] - _x[i];
                double dy = _y[j] - _y[i];

                distance[i][j] = Math.sqrt(dx * dx + dy * dy);
                distance[j][i] = distance[i][j];
//                picture.getImage(this, 1000, 1000);
                //System.out.println("\nVerify edge [" + i + "," + j + "]");
                removeEdges(i, j);
//                printEdges();
            }
        }
//        //fill above matriz        
//        for (int i = 1; i < distance.length; i++) {
//            for (int j = 0; j < i; j++) {
//                distance[i][j] = distance[j][i];
//            }
//        }
        printEdges();
    }

    public static boolean collinear(int p1, int p2, int p3) {
        //only two points
        if (p1 == p2 || p1 == p3 || p2 == p3) {
            return false;
        }
        return Math.abs((_x[p2] - _x[p1]) * (_y[p3] - _y[p1]) - (_x[p3] - _x[p1]) * (_y[p2] - _y[p1])) < TOLERANCE;
    }

    public void removeEdges(int start, int end) {
        Line2D edge = new Line2D.Double(_x[start], _y[start], _x[end], _y[end]);
        //compare with calculated edges
        for (int i = 0; i <= start; i++) {
            if (collinear(start, end, i)) {
//                System.out.println("----------Colinear J" + "[" + start + "," + end + "]--x--[" + i + "]");
//                if (edge.ptSegDist(_x[i], _y[i]) < TOLERANCE) {
//                    distance[start][end] = REMOVED;
//                    distance[end][start] = REMOVED;
////                    picture.getImage(this, 1000, 1000);
//                    return;
//                }
            }

            for (int j = i + 1; j < _x.length; j++) {
                //the edge is always removed
                if (distance[i][j] == REMOVED || distance[i][j] == 0) {
                    continue;
                }

////                System.out.print("\n [" + start + "," + end + "]--x--[" + i + "," + j + "]");
//                //adjacent edge
//                if (i == start || i == end || j == start || j == end) {
//                    continue;
//                }
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
                else if (edge.intersectsLine(_x[j], _y[j], _x[i], _y[i])) {
                    //remove largest edge
                    if (distance[start][end] > distance[i][j]) {
                        distance[start][end] = REMOVED;
                        distance[end][start] = REMOVED;
//                        picture.getImage(this, 1000, 1000);
                        return;

                    } else if (distance[i][j] > distance[start][end]) {
                        distance[i][j] = REMOVED;
                        distance[j][i] = REMOVED;
//                        picture.getImage(this, 1000, 1000);
                    } //remove both if the leght are equal
                    else {
                        distance[i][j] = REMOVED;
                        distance[j][i] = REMOVED;
                        distance[start][end] = REMOVED;
                        distance[end][start] = REMOVED;
//                        picture.getImage(this, 1000, 1000);
                        return;
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
                if (distance[i][j] >= REMOVED || distance[i][j] == 0) {
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
