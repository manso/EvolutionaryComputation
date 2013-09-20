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

import java.util.ArrayList;
import java.util.Random;
import problem.permutation.TSP.AbstractTSP;
import utils.TSP.GreadyVertexInsertion;
import utils.TSP.NearestNeighbor;

/**
 *
 * @author ZULU
 */
public class BuildPath {

    TSP_Graphics graphics = new TSP_Graphics();
    public static double[][] distance = null;
    AbstractTSP tsp;
    Random rnd = new Random();
    Graph graph;
    ArrayList<Integer> path;
    //-----------------------------------------------------

    int[] convertTointArray(ArrayList<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public int[] execute(AbstractTSP tsp) {

//        AbstractTSP.distance = Delaunay.getDelaunay(tsp._x, tsp._y);
        AbstractTSP.distance = RemoveEdges.execute(tsp);
//        this.tsp = tsp;
//        int[] best = GreadyVertexInsertion.calculate(AbstractTSP.distance);
//        graphics.getImage(tsp, 2000, 1000, "_GreadyVertexInsertion.jpg", best);
//        best = NearestNeighbor.calculate(tsp.distance);
//        graphics.getImage(tsp, 2000, 1000, "_NearestNeighbor.jpg", best);


//        AbstractTSP.distance = Delaunay.getDelaunay(tsp._x, tsp._y);
        AbstractTSP.distance = RemoveEdges.execute(tsp);
        BuildPath.distance = tsp.distance;
//        build graph
        graph = new Graph(distance);
//        System.out.println("GRaph= \n" + graph);
        path = graph.getBestTriangle(rnd.nextInt(distance.length));
        graphics.getImage(tsp, 3000, 2000, "_original.jpg", AbstractTSP._optimum);
//        System.out.println("PATH =" + path);
        while (path.size() < distance.length) {
            graph.increasePath(path);
            graphics.getImage(tsp, 3000,3000, "_IncreasePath.jpg", convertTointArray(path));
            System.out.println("PATH =" + path + " SIZE = " + path.size());
            System.out.println("");
        }
        graphics.getImage(tsp, 2000, 1000, "_IncreasePath.jpg", convertTointArray(path));

        return null;
    }

    //------------------------------------------------------
    ArrayList<Integer>[] buildGraph() {
        ArrayList<Integer>[] gr = new ArrayList[distance.length];
        for (int i = 0; i < gr.length; i++) {
            gr[i] = new ArrayList();
        }
        for (int i = 1; i < distance.length; i++) {
            for (int j = i + 1; j < distance.length; j++) {
                if (distance[i][j] != AbstractTSP.REMOVED_EDGE) {
                    gr[i].add(j);
                    gr[j].add(i);
                }
            }
        }


        return gr;
    }

    public static void main(String[] args) {
        TSP_Graphics gr = new TSP_Graphics();
        ReducedTSP p = new ReducedTSP_A66();

        BuildPath b = new BuildPath();
        int[] path = b.execute(p);

    }
}
