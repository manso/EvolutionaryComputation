/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.selection;

import genetic.population.Population;

/**
 *
 * @author manso
 */
public class SUS extends RWS {

    @Override
    public Population execute(Population pop, int numChilds) {
        //make new population
        Population selected = pop.getCleanCopie();
        calculateValues(pop);
        double fitness = random.nextDouble() * cumulativeFitness[cumulativeFitness.length - 1];
        double step =  cumulativeFitness[cumulativeFitness.length - 1] /numChilds;
        for (int i = 0; i < numChilds; i++) {            
            selected.addIndividual(getIndividualInPosition(fitness));
            fitness +=step;

        }
        return selected;
    }

    @Override
    public String getInformation() {
        return "Stochastic Universal Sampling: size = " + getSize()
                + "\nselecting potentially useful solutions for recombination"
                + "\nBaker, James E. (1987)"
                + "\nReducing Bias and Inefficiency in the Selection Algorithm"
                + "\nAdaptation to negative values and to minimization (c) Ma@nso 09"
                + "\n Parents population is sorted"
                + "\n\nParameters: <size>\n";

    }
}
