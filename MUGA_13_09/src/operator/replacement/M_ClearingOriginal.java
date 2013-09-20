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
public class M_ClearingOriginal extends Replacement {

    @Override
    public Population execute(Population parents, Population childs) {

        int size = parents.getNumGenotypes();
        parents.appendPopulation(childs);
        //resize opulation using fitness sharing
        while (parents.size() > size) {
            //pivot
            Individual pivot = parents.removeRandomGenotype();
            int copies = pivot.getNumCopies();
            for (int i = 0; i < copies && parents.size() >= size; i++) {
                //remove similar    
                Individual similar = parents.removeMostSimilar(pivot);
                //change pivot
                if (similar.compareTo(pivot) >= 0) {
                    pivot = similar;
                }
            }//end of radius
            parents.addGenotype(pivot);
        }
        return parents;
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
