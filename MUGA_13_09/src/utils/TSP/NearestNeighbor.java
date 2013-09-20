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
import utils.TSP.ReducedEdges.ReducedTSP;
import utils.TSP.ReducedEdges.ReducedTSP_A66;
import utils.TSP.ReducedEdges.TSP_Graphics;

/**
 * The Traveling Salesman Problem:A Case Study in Local Optimization David S.
 * Johnson Lyle A. McGeoch 1995 2.2. Four Important Tour Construction Algorithms
 * -Nearest Neighbor
 *
 * @author ZULU
 */
public class NearestNeighbor {

    static Random random = new Random();
    static double[][] _cost;
    static int[] permut;
    static boolean[] processed;

    public static int[] calculate(double[][] cost) {
        ArrayList<Integer> lst = new ArrayList<>();
        _cost = cost;
        permut = new int[cost[0].length];
        processed = new boolean[permut.length];
        //first city - Random
        lst.add(random.nextInt(permut.length));
        processed[lst.get(0)] = true;
        int last = lst.get(0);
        for (int i = 0; i < permut.length - 1; i++) {
            //calculate nearest city in the end            
            int right = getNearest(last);
            lst.add(right);
            last = right;
            processed[right] = true;
        }
        for (int i = 0; i < permut.length; i++) {
            permut[i] = lst.get(i);
        }
        return permut;
    }

    private static int getNearest(int i) {
        int index = -1;
        double value = Double.MAX_VALUE;
        for (int j = 0; j < _cost.length; j++) {
            if (!processed[j] && _cost[i][j] < value) {
                value = _cost[i][j];
                index = j;
            }
        }
        return index;
    }
    
    public static void main(String[] args) {
        TSP_Graphics graphics = new TSP_Graphics();
        ReducedTSP p = new ReducedTSP_A66();
        int[] path = calculate(p.distance);
        graphics.getImage(p, 2000, 1000, "_NearestNeighbour.jpg", path);
    }
}
