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
package operator.replacement;

import genetic.population.Population;
import java.util.StringTokenizer;
import problem.Individual;

/**
 * Fecundity and Selectivity in Evolutionary Computation Lee Spector, Thomas
 * Helmuth, Kyle Harrington. In Natalio Krasnogor, Pier Luca Lanzi, editors,
 * 13th Annual Genetic and Evolutionary Computation Conference, GECCO 2011,
 * Companion Material Proceedings, Dublin, Ireland, July 12-16, 2011. pages
 * 129-130, ACM, 2011. [doi]
 *
 * @author arm
 */
public class M_Decimation extends Replacement {

    @Override
    public Population execute(Population parents, Population children) {
        int sizeNewPop = parents.getNumGenotypes();
        //Join populations
        parents.appendPopulation(children);
        //while not selectet the number of genotypes
        while (parents.getNumGenotypes() > sizeNewPop) {
            //-------------------------------------------------------------
            //------------ get parents ------------------------------------
            //-------------------------------------------------------------
            Individual pivot = parents.getRandomGenotype();
            int copy = pivot.getNumCopies();
            for (int i = 0; i < copy; i++) {
                Individual ind = parents.getRandomGenotype();
                if (ind.compareTo(pivot) < 0) {
                    pivot = ind;
                }
            }
            parents.removeGenotype(pivot);
        }
        return parents;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\nMultiset Decimation Replacement ");
        buf.append("\nJoin parents and children");
         buf.append("\n\nSelect a pivot");
        buf.append("\n\nMake tornaments with size pivot.copies");
        buf.append("\n\nremove the worst individual");

        return buf.toString();
    }

   
}
