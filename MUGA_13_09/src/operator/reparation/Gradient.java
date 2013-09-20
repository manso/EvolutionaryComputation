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
package operator.reparation;

import genetic.population.Population;
import problem.Individual;
import problem.RC_Individual;

/**
 *
 * @author ZULU
 */
public class Gradient extends Reparation {

    @Override
    public Population execute(Population pop) {
        Population rep = pop.getCleanCopie();
        pop.evaluate();
        while (pop.size() > 1) {
            RC_Individual best = (RC_Individual) pop.removeBestGenotype();
            RC_Individual other = (RC_Individual) pop.removeMostSimilar(best);
            repair(best, other);
            rep.addGenotype(best);
            if (other.compareTo(best) < 0) {
                other.fillRandom();
            }
            rep.addGenotype(other);
        }
        rep.appendPopulation(pop);
        return rep;

    }

    @Override
    public Individual repair(Individual ind) {
        return ind;
    }

    public void repair(RC_Individual best, RC_Individual other) {
        RC_Individual improved = (RC_Individual) best.getClone();
        double[] x1 = ((RC_Individual) best).getValues();
        double[] x2 = ((RC_Individual) other).getValues();
        double[] newValues = contract(x1, x2);
        improved.setValues(newValues);
        improved.evaluate();
        solver.addEvaluation();
        if (improved.compareTo(best) > 0) {
            other.setIndividual(best);
            best.setIndividual(improved);
            repair(best, other);
        } else {
            newValues = expand(x1, x2);
            improved.setValues(newValues);
            improved.evaluate();
            solver.addEvaluation();
            if (improved.compareTo(best) > 0) {
                best.setIndividual(improved);
                repair(best, other);
            }
        }
    }

    private double[] contract(double[] x1, double[] x2) {
        double[] x3 = new double[x1.length];
        for (int i = 0; i < x3.length; i++) {
            x3[i] = (x1[i] + x2[i]) / 2.0;
        }
        return x3;
    }

    private double[] expand(double[] x1, double[] x2) {
        double[] x3 = new double[x1.length];
        for (int i = 0; i < x3.length; i++) {
            x3[i] = x1[i] + (x1[i] - x2[i]);
        }
        return x3;
    }
}
