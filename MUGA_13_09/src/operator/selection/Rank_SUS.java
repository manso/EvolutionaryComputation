/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.selection;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;
import problem.bitString.OnesMin;

/**
 *
 * @author manso
 */
public class Rank_SUS extends SUS {

    public void calculateValues(Population parents) {
        //inverse sort of population
        Iterator<Individual> it = parents.getSortedInverseIterable().iterator();
        double[] fitness = new double[parents.getNumGenotypes()];
        population = new Individual[parents.getNumGenotypes()];
        int index = 0;
        while (it.hasNext()) {
            Individual ind = it.next();
            fitness[index] = index + 1;
            population[index] = ind;
            //update index
            index++;
        }
        cumulativeFitness = new double[parents.getNumGenotypes()];
        //first element
        cumulativeFitness[0] = fitness[0] * population[0].getNumCopies();
        //accumulate
        for (int i = 1; i < cumulativeFitness.length; i++) {
            cumulativeFitness[i] = cumulativeFitness[i - 1] + fitness[i] * population[i].getNumCopies();
        }


//        for (int i = 0; i < cumulativeFitness.length; i++) {
//            System.out.print("\n " + population[i]);
//            System.out.print(" \tfitness " + fitness[i]);
//            System.out.print(" \tcumulative " + cumulativeFitness[i]);
//        }


    }

    @Override
    public String getInformation() {
        return "RANK " + getSize();

    }

    public static void main(String[] args) {
        Population pop = new MultiPopulation();
        Individual o1;


        o1 = new OnesMin("10001");
        pop.addGenotype(o1);
        o1 = new OnesMin("10001");
        pop.addGenotype(o1);
        o1 = new OnesMin("11010");
        pop.addGenotype(o1);
        o1 = new OnesMin("100011");
        pop.addGenotype(o1);
        o1 = new OnesMin("10111");
        pop.addGenotype(o1);
        o1 = new OnesMin("10000");
        pop.addGenotype(o1);
        o1 = new OnesMin("10000");
        pop.addGenotype(o1);


//        o1 = new OnesMax("10001");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("11111");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("10011");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("10111");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("00000");
//        pop.addGenotype(o1);




        pop.evaluate();
        System.out.println("\n" + pop);

//        RWS sel = new RWS();
//        RWS sel = new SUS();
        RWS sel = new Rank_SUS();
        Population selected = sel.execute(pop, 5000);
        System.out.println("\nSELECTED:" + selected);

    }
}
