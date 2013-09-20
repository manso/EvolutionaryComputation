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
import java.util.Iterator;
import problem.Individual;

/**
 * Replace the parents to childrens and complete with the parents
 *
 * @author arm
 */
public class Truncation extends Replacement {

    @Override
    public Population execute(Population pop, Population children) {
        //clean copies in the main population
//       cleanCopies.setFactor(1.25);
//       cleanCopies.execute(pop);
//       
        int sizeNewPop = pop.getNumGenotypes();
        // System.out.print("POP " + sizeNewPop + " Children" + children.getNumGenotypes());

        //join populations
        pop.appendPopulation(children);

//        //sort all
        Iterator<Individual> iter = pop.getSortedIterable().iterator();
//          //make new population
        Population selected = pop.getCleanCopie();
        while (selected.getNumGenotypes() < sizeNewPop && iter.hasNext()) {
            selected.addGenotype(iter.next());
        }
        return selected;

    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(toString());
        buf.append("\nTruncation Replacement:");
        buf.append("\nJoin Parents and children");
        buf.append("\nChoose the bests Individuals");
        return buf.toString();
    }
}
