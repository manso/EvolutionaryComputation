/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.rescaling;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;
import utils.DynamicLoad;

/**
 *
 * @author manso
 */
public class NoCopys extends Rescaling {

    public NoCopys() {
        setFactor(0.1);
    }

    @Override
    public Population execute(Population pop) {
        //if is multipopulation
        if (pop instanceof MultiPopulation && pop.getNumIndividuals() > pop.getNumGenotypes()) {
            //convert to multipopulation
            MultiPopulation mpop = (MultiPopulation) pop;
            //create new populatioh
            Population rescaled = pop.getCleanCopie();
            //clean copies of the worst individuals
            Iterator<Individual> it = pop.getIterator();
            while (it.hasNext()) {
                rescaled.addIndividual(it.next(), 1);
            }
            return rescaled;
        }//mpop
        return pop;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public String getInformation() {
        return getClass().getSimpleName()
                + "\n\nClean the number of copies of Multipopulation";
    }

    @Override
    public Rescaling getClone() {
        NoCopys clone = (NoCopys) DynamicLoad.makeObject(this);
        return clone;
    }
}
