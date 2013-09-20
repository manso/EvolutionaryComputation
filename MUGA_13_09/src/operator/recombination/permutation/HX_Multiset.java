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
package operator.recombination.permutation;

import genetic.population.Population;
import genetic.population.SimplePopulation;
import java.util.ArrayList;
import java.util.Random;
import operator.recombination.Recombination;
import problem.PRM_Individual;
import problem.permutation.TSP.AbstractTSP;
import problem.permutation.TSP.Ulisses16;
import utils.DynamicLoad;

/**
 *
 * @author ZULU
 */
public class HX_Multiset extends HX {
    //individual to access the cost matrix

    AbstractTSP tsp;

    @Override
    public Population execute(Population pop) {
        //update matrix cost
        tsp = (AbstractTSP) pop.getGenotype(0);

        Population childs = (Population) DynamicLoad.makeObject(pop);
        PRM_Individual i1, i2;
        //fazer os crossovers
        while (pop.getNumGenotypes() > 1) {
            i1 = (PRM_Individual) pop.removeRandomGenotype().getClone();
            //clonning individuals
            if (random.nextDouble() > getProbability()) {
                childs.addGenotype(i1);
                continue;
            }
            //execute X
            i2 = (PRM_Individual) pop.removeRandomGenotype().getClone();
            int numCrossovers = (i1.getNumCopies() + i2.getNumCopies()) / 2;
            for (int i = 0; i < numCrossovers; i++) {                
                PRM_Individual c1 = i1.getClone();
                PRM_Individual c2 = i1.getClone();
                doCrossover(c1, c2);
                childs.addGenotype(c1);
                childs.addGenotype(c2);
            }

        }
        //append the rest of original population
        childs.appendPopulation(pop);
        return childs;
    }


    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\n HX - Multiset Heuristic Crossover");
        buf.append("\n performs many crossovers between parents");
        return buf.toString();
    }

}
