package statistics.elements;

import genetic.Solver.SimpleSolver;
import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;

/**
 * An Adaptive Genetic Algorithm Based on Population Diversity Strategy(2009)
 * @author manso
 */
public class GeneticDiversityASD extends AbstractStatsElement {

    public GeneticDiversityASD() {
    }
    /**
     * calculate de mean of fitness
     * @param pop population
     * @return mean of the fitness population
     */
    private double meanFitness(Population pop) {
        double sum = 0;
        Iterator<Individual> it = pop.getIterator();
        while (it.hasNext()) {
            sum += it.next().getFitness();
        }
        return sum / pop.getNumGenotypes();
    }

    @Override
    public double execute(SimpleSolver s) {
        double sum = 0.0;
        double fi, fm = meanFitness(s.getParents());
        Iterator<Individual> it = s.getParents().getIterator();
        //calculate de diferrence of the individual and the mean fitness
        while (it.hasNext()) {
            fi = it.next().getFitness();
            sum += Math.pow(fi - fm, 2);
        }

        setValue( Math.sqrt(sum) /s.getParents().getNumGenotypes() );
        return getValue();

    }

    @Override
    public String getName() {
        return "Average Square Deviation";
    }
}
