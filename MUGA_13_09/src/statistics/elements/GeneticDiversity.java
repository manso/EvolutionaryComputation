/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.elements;

import genetic.Solver.SimpleSolver;
import genetic.population.Population;
import problem.PRM_Individual;
import problem.RC_Individual;
import utils.BitField;
import utils.Diversity.Shannon;

/**
 *A. Manso, L. Correia
 * Genetic Algorithms using Populations based on Multisets
 * EPIA'2009 - Fourteenth Portuguese Conference on Artificial Intelligence
 * Aveiro , Portugal, Outubro 2009
 * @author manso
 */
public class GeneticDiversity extends AbstractStatsElement {

    protected double hist[];

    public GeneticDiversity() {
    }

    public GeneticDiversity(Population pop) {
    }

    @Override
    public double execute(SimpleSolver s) {
        //real coded problem
        if(s.getTemplate() instanceof RC_Individual ){
           setValue(Shannon.calculateEntropy(s.getParents() ));
           return getValue();
        }
        if(s.getTemplate() instanceof PRM_Individual ){
           setValue(Shannon.calculateEntropy(s.getParents() ));
           return getValue();
        }
        int dim = s.getParents().getIndividual(0).getGenome().getBinString().getNumberOfBits();
        BitField bits;
        hist = new double[dim];
        //inicializar
        for (int i = 0; i < hist.length; i++) {
            hist[i] = 0.0;
        }
        //calcular o histograma
        for (int i = 0; i < s.getParents().getNumGenotypes(); i++) {
            bits = s.getParents().getGenotype(i).getGenome().getBinString();
            for (int j = 0; j < bits.getNumberOfBits(); j++) {
                if (bits.getBit(j)) //add number of copies
                {
                    hist[j] += s.getParents().getGenotype(i).getNumCopies();
                }
            }
        }
        //calcular a estatistica
//        System.out.println("-------------------------------");
        double val = 0.0;
        //number of individuals
        double numPop = s.getParents().getNumIndividuals();
        for (int i = 0; i < hist.length; i++) {
            double ones = hist[i];
            double zeros = (numPop - hist[i]);
//            System.out.print("<" + (ones * zeros) + "> ");
            val += ones * zeros;
        }
        val = (val * 4) / (dim * numPop * numPop);
//        System.out.println("\n------------------------------- Value = " + value);
        setValue(val);
        return getValue();

    }

    @Override
    public String getName() {
        return "Genetic Diversity";
    }
}
