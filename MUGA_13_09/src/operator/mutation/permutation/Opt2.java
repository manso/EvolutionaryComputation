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
package operator.mutation.permutation;

import genetic.population.Population;
import java.util.Iterator;
import operator.mutation.Mutation;
import problem.Individual;
import problem.PRM_Individual;
import problem.permutation.TSP.AbstractTSP;

/**
 *
 * @author ZULU
 */
public class Opt2 extends MutationPRM {

    @Override
    public Population execute(Population pop) {
        //----------------------------------------------------------------
        //make new population
        Population offspring = pop.getCleanCopie();
        //process all the individuals
        Iterator<Individual> it = pop.getIterator();
        while (it.hasNext()) {
            AbstractTSP ind = (AbstractTSP) it.next();
            mutate(ind, probability);
            offspring.addGenotype(ind);
        }
        return offspring;
    }

    public void mutate(PRM_Individual ind, double mutProb) {
        int[] genome = ind.getGeneValues();
        LS_2_opt(genome);
        ind.setGeneValues(genome);
    }

    /**
     * information of mutation
     *
     * @return information
     */
    public String toString() {
        StringBuffer str = new StringBuffer(super.toString());
//        str.append("\nSwap Genes Mutation:\n");
//        str.append("Swaps two genes\n");
//        str.append(" x x G1 x G2 x \n");
//        str.append(" x x G2 x G1 x \n");
        return str.toString();
    }

    public static void LS_2_opt(int[] genome) {
        boolean done = false;
        while (!done) {
            done = true;
            for (int p1 = 0; p1 < genome.length; p1++) {
                for (int p2 = p1 + 2; p2 < genome.length; p2++) {
                    double d1 = getLenght(genome, p1, p1 + 1);
                    double d2 = getLenght(genome, p2, p2 + 1);
                    double d3 = getLenght(genome, p1, p2);
                    double d4 = getLenght(genome, p1 + 1, p2 + 1);
                    if (d1 + d2 > d3 + d4) {
                        reverse(genome, p1 + 1, p2);
                        done = false;
                    }
                }
            }
        }
    }

    private static double getLenght(int[] genome, int y, int x) {
        return AbstractTSP.getTSPDistance(genome[y % genome.length], genome[x % genome.length]);
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