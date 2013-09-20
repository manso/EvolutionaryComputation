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
import java.util.StringTokenizer;
import problem.Individual;

/**
 *
 * @author manso
 */
public class RTR extends Replacement {
    
    protected double WINDOW = 0.5;

    public Individual getMostSimilar(Population pop, Individual ind, int numTrials) {
        Individual similar = pop.getRandomGenotype();
        double dist = ind.distanceTo(similar);

        for (int i = 0; i < numTrials; i++) {
            Individual trial = pop.getRandomGenotype();
            if (trial.distanceTo(ind) < dist) {
                similar = trial;
            }
        }
        return similar;
    }

    @Override
    public Population execute(Population parents, Population children) {

        Iterator<Individual> itSorted = children.getIterator();
        int window = (int)(parents.getNumGenotypes() * WINDOW);

        while (itSorted.hasNext()) {
            Individual offspring = itSorted.next();

            Individual sim = getMostSimilar(parents, offspring, window);

            if (offspring.compareTo(sim) >= 0  && random.nextDouble() < 0.99) {
                if (!parents.contains(offspring)) {
                    parents.removeGenotype(sim);
                }               
                parents.addGenotype(offspring);
            }
        }
        return parents;
    }
    
     @Override
    public void setParameters(String str) {
        StringTokenizer par = new StringTokenizer(str);
        try {
            WINDOW = Double.parseDouble(par.nextToken());
            WINDOW = WINDOW > 1 ? 0.99 : WINDOW < 0 ? 0.01 : WINDOW;
        } catch (Exception e) {
            WINDOW = 0;
        }

    }
    @Override
    public String getParameters() {
           return  WINDOW +"";

    }

    @Override
    public String toString() {
        return super.toString() + " <" + WINDOW + ">";
    }

    @Override
    public Replacement getClone() {
        RTR clone = (RTR) super.getClone();
        clone.WINDOW = this.WINDOW;
        return clone;
    }
}