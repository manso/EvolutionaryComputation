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
package utils.TSP.ReducedEdges;

import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import problem.permutation.TSP.AbstractTSP;
import problem.permutation.TSP.Ulisses16;

/**
 *
 * @author ZULU
 */
public class RemoveEdges {

    static TSP_Graphics graphics = new TSP_Graphics();

    public static double[][] execute(AbstractTSP tsp) {
        double distance[][] = AbstractTSP.distance;//new double [tsp._x.length][tsp._y.length];

        //clean distances
        for (int i = 0; i < distance.length; i++) {
            for (int j = 0; j < distance.length; j++) {
                distance[i][j] = AbstractTSP.REMOVED_EDGE;
            }
        }
        //insert points 
        for (int i = 1; i < distance.length; i++) {
//            System.out.println("\nInserting " + i);
            insertEdge(i, tsp);
//            graphics.getImage(tsp, 2000, 1000);
//            printEdges(tsp);
//            System.out.println("");
        }

        for (int i = 0; i < distance.length; i++) {
            for (int j = 0; j < i; j++) {
                distance[i][j] = distance[j][i];
            }

        }
//        printEdges(tsp);
//        graphics.getImage(tsp, 1000, 1000, "_RemovedEdeges.jpg", null);
        return distance;
    }

    public static void insertEdge(int point, AbstractTSP tsp) {
        //verify conflict with the previous edges introduced
        for (int y = 0; y < point; y++) {
            tsp.distance[y][point] = getDistance(y, point, tsp);
            deleteConflicts(point, y, tsp);
//            graphics.getImage(tsp, 1000, 1000);
//            System.out.print("");
        }
//        printEdges(tsp);

    }

    public static double getDistance(int p1, int p2, AbstractTSP tsp) {
        double dx = tsp._x[p1] - tsp._x[p2];
        double dy = tsp._y[p1] - tsp._y[p2];
        return Math.sqrt(dx * dx + dy * dy);
    }
    static double TOLERANCE = 1e-15;

    public static void deleteConflicts(int start, int end, AbstractTSP tsp) {
//        System.out.println("\nVERIFY EDGE " + start + " - " + end);
        Line2D edge = new Line2D.Double(tsp._x[start], tsp._y[start], tsp._x[end], tsp._y[end]);
        //for all edges
        for (int p1 = 0; p1 <= start; p1++) {
            for (int p2 = start; p2 > p1; p2--) {
//                System.out.print("[ " + p1 + " - " + p2 + " ]");
                if (p1 == end && p2 == start) {
                    continue;
                }
                removeIfIntersection(edge, start, end, p1, p2, tsp);
            }
        }

    }

    private static void removeIfIntersection(Line2D edge, int start, int end, int p1, int p2, AbstractTSP tsp) {
//        System.out.print("[" + p1 + "," + p2 + "]");
        //if edge deleted continue to next
        if (AbstractTSP.distance[p1][p2] == AbstractTSP.REMOVED_EDGE) {
            return;
        }
        Line2D test = new Line2D.Double(AbstractTSP._x[p1], AbstractTSP._y[p1],
                AbstractTSP._x[p2], AbstractTSP._y[p2]);

        //test line contais start and end => remove test line
        if (test.ptSegDist(AbstractTSP._x[start], AbstractTSP._y[start]) < TOLERANCE &&
            test.ptSegDist(AbstractTSP._x[end], AbstractTSP._y[end]) < TOLERANCE ){    
            AbstractTSP.distance[p1][p2] = AbstractTSP.REMOVED_EDGE;
            return;
        }
        // edge line contais p1 and p2 => remove edge
        if (edge.ptSegDist(AbstractTSP._x[p1], AbstractTSP._y[p1]) < TOLERANCE
                && edge.ptSegDist(AbstractTSP._x[p2], AbstractTSP._y[p2]) < TOLERANCE) {
            AbstractTSP.distance[end][start] = AbstractTSP.REMOVED_EDGE;
            return;
        }

        //P1 is common of two lines
        if (p1 == start || p1 == end) {
//            //p2 is inside the line
//            if (edge.ptSegDist(AbstractTSP._x[p2], AbstractTSP._y[p2]) < TOLERANCE) {
//                tsp.distance[end][start] = AbstractTSP.REMOVED_EDGE;
//            }
            return;
        }
        //P2 is common of two lines
        if (p2 == start || p2 == end) {
//            //p2 is inside the line
//            if (edge.ptSegDist(AbstractTSP._x[p1], AbstractTSP._y[p1]) < TOLERANCE) {
//                tsp.distance[end][start] = AbstractTSP.REMOVED_EDGE;
//            }
            return;
        }


        //verify if i or j points are in the line between start o---(i,j)---o end 
        if (edge.ptSegDist(AbstractTSP._x[p1], AbstractTSP._y[p1]) < TOLERANCE
                || edge.ptSegDist(AbstractTSP._x[p2], AbstractTSP._y[p2]) < TOLERANCE
                || test.ptSegDist(AbstractTSP._x[start], AbstractTSP._y[start]) < TOLERANCE
                || test.ptSegDist(AbstractTSP._x[end], AbstractTSP._y[end]) < TOLERANCE) {
            //Point in the line
            return;
            // System.out.println(" (0) ");
        } //intersection of lines 
        if (edge.intersectsLine(tsp._x[p1], tsp._y[p1], tsp._x[p2], tsp._y[p2])) {
            //remove largest edge
            if (tsp.distance[end][start] > tsp.distance[p1][p2]) {
                tsp.distance[end][start] = AbstractTSP.REMOVED_EDGE;
                return;
            } else if (tsp.distance[p1][p2] > tsp.distance[end][start]) {
                tsp.distance[p1][p2] = AbstractTSP.REMOVED_EDGE;
            } else {
                tsp.distance[end][start] = AbstractTSP.REMOVED_EDGE;
                tsp.distance[p1][p2] = AbstractTSP.REMOVED_EDGE;
            }
        }
    }

    public static void printEdges(AbstractTSP tsp) {
        int edges = 0;

        System.out.print("\n\n-----------------------------\n           ");

        for (int i = 0; i < tsp.distance.length; i++) {
            //calculate distances
            System.out.printf("[%7d]", i);
        }
        for (int i = 0; i < tsp.distance.length; i++) {
            //calculate distances
            System.out.printf("\n[%7d]  ", i);
            for (int j = 0; j < tsp.distance.length; j++) {
                if (tsp.distance[i][j] == AbstractTSP.REMOVED_EDGE) {
                    System.out.printf("    -    ");
                } else {
                    edges++;
                    System.out.printf(" %7.2f ", tsp.distance[i][j]);
                }
            }
        }
        System.out.println("\nEdges = " + edges);
    }

    public static void main(String[] args) {
        AbstractTSP tsp = new ReducedTSP_A6();
        execute(tsp);
    }
}
