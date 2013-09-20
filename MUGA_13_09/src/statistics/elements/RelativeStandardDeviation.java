/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics.elements;

import genetic.Solver.SimpleSolver;
import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;

/**
 *
 * @author manso
 */
public class RelativeStandardDeviation extends AbstractStatsElement {

    public RelativeStandardDeviation() {
    }

    public RelativeStandardDeviation(SimpleSolver s) {
        execute(s);
    }

    private double[] mean(Population pop) {
        //dimension is the number of genes
        double[] value = new double[pop.getIndividual(0).getNumGenes()];
        //for all individuals
        Iterator<Individual> it = pop.getIterator();
        while (it.hasNext()) {
            Individual ind = it.next();
            //sum the value of the gene
            for (int i = 0; i < value.length; i++) {
                value[i] += ind.getGeneValue(i);
            }
        }
        //divide by the number of individuals
        double factor = 1.0 / pop.getNumGenotypes();
        for (int i = 0; i < value.length; i++) {
            value[i] *=factor;
        }
        return value;
    }

    private double[] std(Population pop) {
        //dimension is the number of genes
        double[] value = new double[pop.getIndividual(0).getNumGenes()];
        double [] m = mean(pop);
        //for all individuals
        Iterator<Individual> it = pop.getIterator();
        while (it.hasNext()) {
            Individual ind = it.next();
            //sum the value of the gene
            for (int i = 0; i < value.length; i++) {
                value[i] += Math.pow( ind.getGeneValue(i) - m[i],2);
            }
        }
        //divide by the number of individuals - 1
        for (int i = 0; i < value.length; i++) {
            value[i] = Math.sqrt( value[i] /(pop.getNumGenotypes()-1));
        }
        return value;
    }

    private double rsd(Population pop) {
        //dimension is the number of genes
        double[] s = std(pop);
        double [] m = mean(pop);
        Individual ind = pop.getIndividual(0);
        double v = 0.0;
        for (int i = 0; i < s.length; i++) {
           if( m[i] != 0)
               v += s[i] /ind.getDimension();
        }
        return (100 * v) / s.length;
    }



    public double execute(SimpleSolver s) {
        setValue(rsd(s.getParents()));
        return getValue();
    }

    public String toString() {
        return "RSD " + getValue();
    }

    @Override
    public String getName() {
        return "Relative SD";
    }
    /**
     * specify if the higher value is better
     * used in mean comparison from statistics
     * @return true if higher value is better
     */
    public boolean isMaximumBetter(){
        return false;
    }
}
