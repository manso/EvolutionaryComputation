/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.selection;

import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;

/**
 *
 * @author manso
 */
public class RWS extends Selection {

    double maxValue;
    double minValue;
    //cumulative fitness of population
    double[] cumulativeFitness;
    //array of population
    Individual[] population;

    @Override
    public Population execute(Population pop, int numChilds) {
        //make new population
        Population selected = pop.getCleanCopie();
        calculateValues(pop);
        for (int i = 0; i < numChilds; i++) {
           double fitness = random.nextDouble() * cumulativeFitness[cumulativeFitness.length - 1];
           selected.addIndividual( getIndividualInPosition(fitness));
            
        }
        return selected;
    }

    //play roulete
    protected Individual getIndividualInPosition(double fitness) {
        //normalize fitness
        while (fitness > cumulativeFitness[cumulativeFitness.length - 1]) {
            fitness -= cumulativeFitness[cumulativeFitness.length - 1];
        }
        for (int i = 0; i < cumulativeFitness.length; i++) {
           if( fitness < cumulativeFitness[i])
               return population[i].getClone();            
        }
        return null;
    }

    public void calculateValues(Population parents) {
        Iterator<Individual> it = parents.getIterator();
        maxValue = Double.NEGATIVE_INFINITY;
        minValue = Double.POSITIVE_INFINITY;
        double[] fitness = new double[parents.getNumGenotypes()];
        population = new Individual[parents.getNumGenotypes()];
        int index = 0;
        while (it.hasNext()) {
            Individual ind = it.next();

            fitness[index] = ind.getFitness();
            population[index] = ind;

            if (ind.typeOfOptimization == Individual.MINIMIZE) {
                fitness[index] *= -1;
            }

            if (maxValue < fitness[index]) {
                maxValue = fitness[index];
            }
            if (minValue > fitness[index]) {
                minValue = fitness[index];
            }

            //update index
            index++;
        }
        //normalize values
        double DIMENSION = maxValue - minValue;
//        System.out.println("MIN = " + minValue + " \t MAX = " + maxValue + " \t Dim " + DIMENSION);

        cumulativeFitness = new double[parents.getNumGenotypes()];
        //first element
        fitness[0] = DIMENSION - minValue + fitness[0];
        cumulativeFitness[0] = fitness[0] * population[0].getNumCopies();
        //accumulate
        for (int i = 1; i < cumulativeFitness.length; i++) {
            fitness[i] = DIMENSION - minValue + fitness[i];
            cumulativeFitness[i] = cumulativeFitness[i - 1] + fitness[i] * population[i].getNumCopies();
        }


//        for (int i = 0; i < cumulativeFitness.length; i++) {
//            System.out.print("\n " +population[i]);
//            System.out.print(" \tfitness " +fitness[i]);
//            System.out.print(" \tcumulative " +cumulativeFitness[i]);
//        }


    }

    public Individual getIndividualOfFitness(double fitRnd) {
        return null;
    }

    @Override
    public String getInformation() {
        return "Roulette " + getSize();

    }

//    public static void main(String[] args) {
//        Population pop = new MultiPopulation();
//        Individual o1;
//
//
//        o1 = new OnesMin("10001");
//        pop.addGenotype(o1);
//        o1 = new OnesMin("11010");
//        pop.addGenotype(o1);
//        o1 = new OnesMin("100011");
//        pop.addGenotype(o1);
//        o1 = new OnesMin("10111");
//        pop.addGenotype(o1);
//        o1 = new OnesMin("10000");
//        pop.addGenotype(o1);
//
//
////        o1 = new OnesMax("10001");
////        pop.addGenotype(o1);
////        o1 = new OnesMax("11111");
////        pop.addGenotype(o1);
////        o1 = new OnesMax("10011");
////        pop.addGenotype(o1);
////        o1 = new OnesMax("10111");
////        pop.addGenotype(o1);
////        o1 = new OnesMax("00000");
////        pop.addGenotype(o1);
//
//
//
//
//        pop.evaluate();
//        System.out.println(pop);
//
////        RWS sel = new RWS();
//        RWS sel = new SUS();
//        Population selected = sel.execute(pop, 5000);
//        System.out.println("SELECTED:" + selected);
//
//    }
}
