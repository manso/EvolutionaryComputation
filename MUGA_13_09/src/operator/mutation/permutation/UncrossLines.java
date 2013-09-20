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
import java.awt.geom.Line2D;
import operator.mutation.Mutation;
import problem.permutation.TSP.AbstractTSP;
import problem.permutation.TSP.SimpleTSP;
import utils.geometry.GeometryLine;

/**
 *
 * @author ZULU
 */
public class UncrossLines extends Mutation {

    AbstractTSP problem;

    @Override
    public Population execute(Population pop) {
        problem = (AbstractTSP) pop.getIndividual(0);
        double mutProb = probability;
        if (probability == 0) {
            mutProb = 1.0 / pop.getGenotype(0).getNumGenes();
        }

        //----------------------------------------------------------------
        //make new population
        Population offspring = pop.getCleanCopie();
        //process all the individuals
        for (int i = 0; i < pop.getNumIndividuals(); i++) {
            AbstractTSP ind = (AbstractTSP) pop.getIndividual(i).getClone();
            unCrossLine(ind, mutProb);
            offspring.addGenotype(ind);

        }
        return offspring;
    }

    /**
     * mutate one individual
     *
     * @param ind individual to mutate
     */
    public void unCrossLine(AbstractTSP ind, double mutProb) {
        int[] genes = ind.getGeneValues();
        for (int index1 = 0; index1 < genes.length; index1++) {
            if (random.nextDouble() < mutProb) {
                unCrossThisEdge(index1, ind);
            }
//            ind.setGeneValues(genes);
//            System.out.println("IND : " + ind);
        }
        ind.setGeneValues(genes);
    }

    private boolean unCrossThisEdge(int position, AbstractTSP ind) {
        int[] genes = ind.getGeneValues();
       boolean cross = false;
        //actual edge
        Line2D l1 = ind.getEdge(position);

        for (int i = 2; i < genes.length - 1; i++) {
            //edge to test
            Line2D l2 = ind.getEdge(position + i);
            //intersect edge
            if (GeometryLine.intersects(l1, l2)) {
                int p1 = (position + 1) % AbstractTSP.getNumberOfCities();
                int p2 = (i + position) % AbstractTSP.getNumberOfCities();

                int min = Math.min(p1, p2);
                int max = Math.max(p1, p2);
                //System.out.println("[" + min + "-----" + max + "]");
                while (min < max) {
                    int aux = genes[min];
                    genes[min] = genes[max];
                    genes[max] = aux;
                    min++;
                    max--;
                }
                cross = true;
            }
        }
        return cross;
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

    public static void main(String[] args) {
        SimpleTSP t = new SimpleTSP();
        int v[] = {0, 2, 1, 3, 4, 5};
//        int v[] = {0,3,4,1,2,5};
        t.setGeneValues(v);
        UncrossLines mut = new UncrossLines();
        System.out.println(t.toTSPString());
        mut.unCrossLine(t, 1);
        System.out.println(t);

    }
}