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
import problem.permutation.TSP.AbstractTSP;

/**
 *
 * @author ZULU
 */
public class Graph {

    // Adjacency vertex
    //   [0] ->  [ 1 , 2 , 4 ]
    //   [1] ->  [ 0 , 2 , 5 ]
    //   [2] ->  [ 0 , 1 , 3 , 4 , 5 ]
    //   [3] ->  [ 2 , 4 , 5 ]
    //   [4] ->  [ 0 , 2 , 3 ]
    //   [5] ->  [ 1 , 2 , 5 ]
    ArrayList<Integer>[] graph;
    double[][] distance;

    public Graph(double[][] distance) {
        this.distance = distance;
        //build graph

        //create Arrays of adjecency
        graph = new ArrayList[distance.length];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList();
        }
        // create edges
        for (int i = 0; i < distance.length; i++) {
            for (int j = i + 1; j < distance.length; j++) {
                if( i != j){
//                if (distance[i][j] != AbstractTSP.REMOVED_EDGE) {
                    graph[i].add(j);
                    graph[j].add(i);
                }
            }
        }
    }

    ArrayList<Integer> getBestTriangle(int vertex1) {
        //Triangle not found
        // vertice has no edges
        if (graph[vertex1].isEmpty()) {
            return null;
        }

        //best triangle with the point index
        ArrayList<Integer> best = null;
        double Max_LENGHT = Double.NEGATIVE_INFINITY;
        //second point
        for (int index2 = 0; index2 < graph[vertex1].size(); index2++) {
            int vertex2 = graph[vertex1].get(index2);
            //third point 
            for (int index3 = 0; index3 < graph[vertex2].size(); index3++) {
                //verify if the third point has connection to the first
                if (graph[ graph[vertex2].get(index3)].contains(vertex1)) {
                    //build a triangle
                    int vertex3 = graph[vertex2].get(index3);
                    ArrayList<Integer> tri = new ArrayList<>();
                    tri.add(vertex1);
                    tri.add(vertex2);
                    tri.add(vertex3);
                    //calculate lenght
                    double lenght =
                            distance[tri.get(0)][tri.get(1)]
                            + distance[tri.get(1)][tri.get(2)]
                            + distance[tri.get(2)][tri.get(0)];

//                    System.out.println("Triange " + tri + " lenght = " + lenght);
                    if (lenght > Max_LENGHT) {
                        best = tri;
                        Max_LENGHT = lenght;
                    }//if
                }//contains

            }//index3

        }//index1
        return best;
    }

    int getTriangle(int index1, int index2) {
        for (int i = 0; i < graph[index2].size(); i++) {
            if (graph[ graph[index2].get(i)].contains(index1)) {
                return graph[index2].get(i);
            }
        }
        return -1;
    }

    ArrayList<Integer> increasePath(ArrayList<Integer> path) {
        //next best point
        int bestPoint = -1;
        int insertionPosition = 0;
        //area to increase
        double MIN_LENGHT = Double.POSITIVE_INFINITY;
        //verify all the edges 
        for (int i = 0; i < path.size(); i++) {

            //get the triangle
            int point = getNewTriangle(path.get(i), path.get((i + 1) % path.size()), path);
            //triangle founf
            if (point >= 0) {

                //verify area
                double lenght = distance[path.get((i + 1) % path.size())][point]
                        + distance[point][path.get(i)]
                        - distance[path.get(i)][path.get((i + 1) % path.size())];
                //store best point
                if (lenght < MIN_LENGHT) {
                    bestPoint = point;
                    insertionPosition = i;
                    MIN_LENGHT = lenght;

                }
            }
        }

        //get the closest point to the path
        if (bestPoint == -1) {
            MIN_LENGHT = Double.POSITIVE_INFINITY;
            for (int i = 0; i < path.size(); i++) {
                int point = path.get(i);
                for (int j = 0; j < graph[point].size(); j++) {
                    if (!path.contains(graph[point].get(j))) {
                        if( distance[point][ graph[point].get(j)]  < MIN_LENGHT){
                            MIN_LENGHT =distance[point][ graph[point].get(j)] ;
                            bestPoint = graph[point].get(j);
                            insertionPosition = i;
                        }
                    }
                }
            }
            
//            System.out.println("");
            
        }
        //remove edge from path
//        int nextPosition= (insertionPosition + 1) % path.size();
//        int previous = path.get(insertionPosition);
//        graph[next].remove((Integer) previous);
//        graph[previous].remove((Integer) next);
//        
//        removeEdge(next, previous);


//        //
//        distance[next][previous] = AbstractTSP.REMOVED_EDGE;
//        distance[previous][next] = AbstractTSP.REMOVED_EDGE;



//        System.out.println("PATH :" + path + " Inserting " + bestPoint + " after " + path.get(insertionPosition) );
        addEdgeToPath(path, insertionPosition, bestPoint);
        return path;
    }

    public void addEdgeToPath(ArrayList<Integer> path, int insertionPosition, int bestPoint) {
        int nextPosition = (insertionPosition + 1) % path.size();
        // remove internal edges
        for (int j = 0; j < path.size(); j++) {
            if (j != insertionPosition && j != nextPosition) {
//                System.out.println("REMOVE [" + path.get(j) + " - " + bestPoint + "]");
                removeEdge(path.get(j), bestPoint);
            }

        }
        removeEdge(path.get(nextPosition), path.get(insertionPosition));
        path.add(insertionPosition + 1, bestPoint);
    }

    public void removeEdge(int p1, int p2) {
        graph[p1].remove((Integer) p2);
        graph[p2].remove((Integer) p1);
        distance[p1][p2] = AbstractTSP.REMOVED_EDGE;
        distance[p2][p1] = AbstractTSP.REMOVED_EDGE;
    }
    public void removeVertex(int p1) {
//        //clean edges to vertex
//        for (int i = 0; i < graph.length; i++) {
//            graph[i].remove((Integer)p1); 
//            distance[i][p1] = AbstractTSP.REMOVED_EDGE;
//            distance[p1][i] = AbstractTSP.REMOVED_EDGE;
//        }
       //clean edges of vertex
        for (int i = graph[p1].size()-1; i >=0; i--) {
            int k = graph[p1].remove(i);
            distance[p1][k]= AbstractTSP.REMOVED_EDGE;;
            distance[k][p1]= AbstractTSP.REMOVED_EDGE;;            
        }
       
        
    }

    ArrayList<Integer> increasePathGood(ArrayList<Integer> path) {
        //next best point
        int bestPoint = -1;
        int insertionPosition = 0;
        //area to increase
        double MIN_LENGHT = Double.POSITIVE_INFINITY;
        //verify all the edges
        for (int i = 0; i < path.size(); i++) {
            //get the triangle
            int point = getNewTriangle(path.get(i), path.get((i + 1) % path.size()), path);
            //triangle founf
            if (point >= 0) {
                //verify area
                double lenght = distance[path.get((i + 1) % path.size())][point]
                        + distance[point][path.get(i)]
                        - distance[path.get(i)][path.get((i + 1) % path.size())];
                //store best point
                if (lenght < MIN_LENGHT) {
                    bestPoint = point;
                    insertionPosition = i;
                    MIN_LENGHT = lenght;
                }
            }
        }
        //remove edge from path
        int next = path.get((insertionPosition + 1) % path.size());
        int previous = path.get(insertionPosition);
//        graph[next].remove((Integer)previous);
//        graph[previous].remove((Integer)next);
//        
//        //
//        distance[next][previous] = AbstractTSP.REMOVED_EDGE;
//        distance[previous][next] = AbstractTSP.REMOVED_EDGE;

        path.add(insertionPosition + 1, bestPoint);
        return path;
    }

    //tabu list - nor permited vertex
    int getNewTriangle(int index1, int index2, ArrayList<Integer> tabu) {
//        for (int i = 0; i < graph[index2].size(); i++) {
//            if (graph[ graph[index2].get(i)].contains(index1) && !tabu.contains(graph[index2].get(i))) {
//                return graph[index2].get(i);
//            }
//        }
//        return -1;

        double MIN_LENGHT = Double.POSITIVE_INFINITY;
        int point = -1;
        for (int i = 0; i < graph[index2].size(); i++) {
            int index3 = graph[index2].get(i);
            if (graph[ index3].contains(index1) && !tabu.contains(index3)) {

                double lenght = distance[index1][index3]
                        + distance[index2][index3] - distance[index1][index2];

                if (lenght < MIN_LENGHT) {
                    MIN_LENGHT = lenght;
                    point = index3;
                }

            }
        }
        return point;

    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < graph.length; i++) {
            str.append("[" + i + "] = " + graph[i] + "\n");
        }
        return str.toString();

    }

    private double getAreaTriangleHeron(double a, double b, double c) {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
    
    
    public int getBestEdge(int start, int tabu){
        int index = -1;
        double MIN = Double.POSITIVE_INFINITY;
        for (int i = 0; i < graph[start].size(); i++) {
            if(graph[start].get(i)!= tabu && distance[start][graph[start].get(i)] < MIN){
                MIN = distance[start][graph[start].get(i)];
                index=graph[start].get(i);
            }            
        }
        return index;
        
    }
}
