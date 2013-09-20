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

import problem.permutation.TSP.AbstractTSP;

/**
 *
 * @author ZULU
 */
public class TspLocalSearch {

    public static void opt_2(int[] genome) {
        execute_2_opt(genome, 0, genome.length);
    }

    public static void execute_2_opt(int[] genome, int begin, int end) {
        //normalize start and stop
        if (end < begin) {
            int aux = end;
            end = begin;
            begin = aux;
        }
        boolean done = false;
        while (!done) {
            done = true;
            for (int p1 = begin; p1 < end; p1++) {
                for (int p2 = p1 + 2; p2 < end - 1; p2++) {
                    //verify 2-opt
                    done = done || opt_2(genome, p1, p2);
                }
            }
        }
    }

    private static boolean opt_2(int[] genome, int p1, int p2) {
        double d1 = getLenght(genome, p1, p1 + 1);
        double d2 = getLenght(genome, p2, p2 + 1);
        double d3 = getLenght(genome, p1, p2);
        double d4 = getLenght(genome, p1 + 1, p2 + 1);
        //verify 2-opt
        if (d1 + d2 > d3 + d4) {
            reverse(genome, p1 + 1, p2);
            return true;
        }
        return false;
    }

    private static double getLenght(int[] genome, int y, int x) {
        return AbstractTSP.getTSPDistance(genome[y], genome[x]);
    }

    public static void reverse(int[] cities, int begin, int end) {
        if (begin >= end || begin >= cities.length || end < 0) {
            return;
        }
        while (begin < end) {
            int tmp = cities[begin];
            cities[begin] = cities[end];
            cities[end] = tmp;
            begin++;
            end--;
        }

    }
}