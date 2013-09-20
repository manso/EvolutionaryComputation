/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.real;

import genetic.population.Population;
import java.util.Random;
import operator.mutation.Mutation;
import problem.Individual;
import problem.RC_Individual;

/**
 * Multi-parent Recombination with Simplex Crossover(99)
 * @author manso
 */
public class RealMutation extends Mutation {

    static Random rnd = new Random();

    static double calculateAlpha(double k, double prob) {
        double sum = 0;
        for (int i = 0; i < k; i++) {
            if (rnd.nextDouble() < prob) {
                sum += Math.pow(2, -i);
            }
        }
        return sum;
    }

    public static Individual execute(RC_Individual ind, double prob) {
        double v[] = ind.getValues();
        double range = ind.getDimension();
        for (int i = 0; i < ind.getNumGenes(); i++) {
            if (rnd.nextDouble() < prob) {
                 //Analysis and Design of Intelligent Systems Using Soft Computing Techniques
                double alpha = calculateAlpha(20, prob);
                v[i]  = rnd.nextBoolean() ? v[i] + range * alpha : v[i] - range * alpha;
            }
        }
        ind.setValues(v);
        return ind;
    }

    @Override
    public Population execute(Population pop) {
        //make new population
        Population childs = pop.getCleanCopie();
        for (int i = 0; i < pop.getNumIndividuals(); i++) {
            Individual ind = pop.getIndividual(i).getClone();
            ind.setNumCopys(1);
            ind = execute((RC_Individual)ind, 1.0 / ind.getNumGenes());
            childs.addIndividual(ind, 1);
        }

        return childs;
    }

}
