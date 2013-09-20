/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.selection;

import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;

/**
 * Tournament selection
 *
 * @author arm
 */
public class Truncation extends Selection {

    /**
     * Tournament selection
     *
     * @param pop population of parents
     * @param numChilds number of children to select
     * @return population selected
     */
    @Override
    public Population execute(final Population pop, int numChilds) {
      
        if ( pop.getNumGenotypes()<= numChilds) {
            return pop.getClone();
        }
        //make new population
        Population selected = pop.getCleanCopie();
        Iterator<Individual> it = pop.getSortedIterable().iterator();
        //select NumChilds Individuals
        for (int count = 0; count < numChilds; count++) {
            //append one copie of the best
            selected.addGenotype(it.next().getClone());
        }
        return selected;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer(toString());
        buf.append("\nSelect the best individuals of population");
        buf.append("\n\nParameters: <SIZE>");
        buf.append("\n    <SIZE>  - # of genotypes to be selected ");
        return buf.toString();
    }
}
