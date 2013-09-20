///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
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
public class Clearing extends Replacement {

    @Override
    public Population execute(Population pop, Population childs) {
        int size = pop.getNumGenotypes();
        Population allPop = pop.getClone();
        allPop.appendPopulation(childs);
        Population newPop = pop.getCleanCopie();
        //resize opulation using fitness sharing
        while (allPop.size() + newPop.size() > size) {
            //random individual
            Individual randomInd = allPop.removeBestGenotype();
            //remove most similar
            allPop.removeMostSimilar(randomInd);
            newPop.addGenotype(randomInd);
        }//while resize
        newPop.appendPopulation(allPop);
        return newPop;
    }
}
