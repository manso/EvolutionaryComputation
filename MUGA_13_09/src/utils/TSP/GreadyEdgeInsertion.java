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
package utils.TSP;

import java.util.ArrayList;
import java.util.Random;
import problem.permutation.TSP.AbstractTSP;
import utils.Delaunay.Delaunay;
import utils.TSP.ReducedEdges.*;

/**
 * The Traveling Salesman Problem:A Case Study in Local Optimization David S.
 * Johnson Lyle A. McGeoch 1995 2.2. Four Important Tour Construction Algorithms
 * -Nearest Neighbor
 *
 * @author ZULU
 */
public class GreadyEdgeInsertion {

    /**
     * build a TSP tour using greedy insertion of citys starting from a rondom
     * city insert the remains minimizing the subtour lenght
     *
     * @param distance matrix of costs
     * @return tour of TSP
     */
    public static int[] calculate( AbstractTSP tsp) {
         AbstractTSP.distance = Delaunay.getDelaunay(tsp._x, tsp._y);
//        tsp.distance = RemoveEdges.execute(tsp);
        TSP_Graphics graphics = new TSP_Graphics();
        //array of permutation
        int[] permut = new int[AbstractTSP.distance.length];
        //citys selected to tour
        ArrayList<Integer> tour = new ArrayList<>(permut.length);
        Graph graph = new Graph(AbstractTSP.distance);

        ArrayList<Integer> notSelected = new ArrayList<>(permut.length);
        for (int i = 0; i < permut.length; i++) {
            notSelected.add(i);
        }
        int begin = (new Random()).nextInt(permut.length);
        int end = graph.getBestEdge(begin,0);
        tour.add((Integer) begin);
        tour.add((Integer) end);
        graph.removeEdge(end, begin);

        while (tour.size() < AbstractTSP.distance.length) {
            int edge1 = graph.getBestEdge(begin,end);
            int edge2 = graph.getBestEdge(end,begin);
            if (AbstractTSP.distance[edge1][begin] < AbstractTSP.distance[edge2][end]) {
                tour.add(0, edge1);
                graph.removeEdge(edge1, begin);
                graph.removeVertex(begin);
                begin = edge1;
            } else {
                tour.add((Integer) edge2);
                graph.removeEdge(edge2, end);
                graph.removeVertex(end);
                end = edge2;
            }


            //transfer ArrayList to permutation
            int [] path = new int[tour.size()];
            for (int i = 0; i < tour.size(); i++) {
                path[i] = tour.get(i);
            }
            graphics.getImage(tsp, 1000, 1000, "_GeredyEdges.jpg", path);
            System.out.println("" + tour);
            System.out.println("");
        }

        //transfer ArrayList to permutation
        for (int i = 0; i < permut.length; i++) {
            permut[i] = tour.get(i);
        }
        return permut;
    }

    public static void main(String[] args) {
        TSP_Graphics graphics = new TSP_Graphics();
        ReducedTSP p = new ReducedTSP_A66();
        int[] path = calculate(p);
        graphics.getImage(p, 2000, 1000, "_GeredyEdges.jpg", path);
    }
}
