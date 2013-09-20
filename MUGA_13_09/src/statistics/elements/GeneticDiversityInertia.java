package statistics.elements;

import genetic.Solver.SimpleSolver;
import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;

/**
 * An Adaptive Genetic Algorithm Based on Population Diversity Strategy(2009)
 * @author manso
 */
public class GeneticDiversityInertia extends AbstractStatsElement {

    double[] centerMass;

    public GeneticDiversityInertia() {
    }

    /**
     * calculate de mean of fitness
     * @param pop population
     * @return mean of the fitness population
     */
    private double[] centerMass(Population pop) {
        int nGenes = pop.getIndividual(0).getNumGenes();
        int nInd = pop.getNumGenotypes();
        double[] center = new double[nGenes];
        //sum of all genes
        Iterator<Individual> it = pop.getIterator();
        while (it.hasNext()) {
            Individual ind = it.next();
            for (int i = 0; i < center.length; i++) {
                center[i] += ind.getGeneValue(i);
            }
        }
        //divide by number of individuals
        for (int i = 0; i < center.length; i++) {
            center[i] /= nInd;
        }

        return center;
    }

    @Override
    public double execute(SimpleSolver s) {
        double sum = 0.0;
        double[] center = centerMass(s.getParents());
        Iterator<Individual> it = s.getParents().getIterator();
        //calculate de diferrence of the individual and the center of mass
        while (it.hasNext()) {
            Individual ind = it.next();
            for (int i = 0; i < center.length; i++) {
                sum += Math.pow( center[i] - ind.getGeneValue(i) , 2);
            }
        }
        setValue(sum);
        return getValue();

    }

    @Override
    public String getName() {
        return "Inertia";
    }
}
