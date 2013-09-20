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
import problem.Individual;

/**
 *
 * @author ZULU
 */
public class M_ClearingBest extends Replacement {

    @Override
    public Population execute(Population pop, Population childs) {
        int size = pop.getNumGenotypes();
        Population allPop = pop.getClone();
        allPop.appendPopulation(childs);
        Population newPop = pop.getCleanCopie();
        //resize opulation using fitness sharing
        while (allPop.size() + newPop.size() > size) {
            //random individual
            Individual best = allPop.removeBestGenotype();
            int copies = best.getNumCopies();
            while (copies > 0 && allPop.size() + newPop.size() >= size) {
                //remove most similar
                allPop.removeMostSimilar(best);
                copies--;
            }
            newPop.addGenotype(best);
        }//while resize
        newPop.appendPopulation(allPop);
        return newPop;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nMultiset Clearing Replacement");
        buf.append("\nClear individuals in the windows");
        buf.append("\nwith size to number of copies");
        buf.append("\nProcedure starts with a selection");
        buf.append("\nand continue to most similar");
        buf.append("\nKilling the most weakers");
        return buf.toString();
    }
}
