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
package genetic.population;

import problem.Individual;

/**
 *
 * @author ZULU
 */
public class UniquePopulation extends MultiPopulation {

    @Override
    public void addGenotype(Individual ind) {  
        if (!pop.contains(ind)) {
            ind.setNumCopys(1);
            super.addGenotype(ind);
        }
    }
    @Override
    public void addIndividual(Individual ind, int copies) {            
        addGenotype(ind);
    }
}
