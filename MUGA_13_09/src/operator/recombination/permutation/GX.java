///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package operator.recombination.permutation;

import genetic.population.Population;
import genetic.population.SimplePopulation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import operator.recombination.Recombination;
import problem.PRM_Individual;
import problem.permutation.TSP.AbstractTSP;
import problem.permutation.TSP.Ulisses16;
import utils.DynamicLoad;

/**
 *
 * @author ZULU
 */
public class GX extends CrossoverPRM {
    //individual to access the cost matrix

    AbstractTSP tsp;

    @Override
    public Population execute(Population pop) {
        //update matrix cost
        tsp = (AbstractTSP) pop.getGenotype(0);

        Population childs = (Population) DynamicLoad.makeObject(pop);
        PRM_Individual i1, i2;
        //fazer os crossovers
        while (pop.getNumGenotypes() > 1) {
            i1 = (PRM_Individual) pop.removeRandomGenotype().getClone();
            //clonning individuals
            if (random.nextDouble() > getProbability()) {
                childs.addGenotype(i1);
                continue;
            }
            //execute X
            i2 = (PRM_Individual) pop.removeRandomGenotype().getClone();
            doCrossover(i1, i2);
            childs.addGenotype(i1);
            childs.addGenotype(i2);
        }
        //append the rest of original population
        childs.appendPopulation(pop);
        return childs;
    }

    @Override
    public void doCrossover(PRM_Individual i1, PRM_Individual i2) {
        int[] g1 = i1.getGeneValues();
        int[] g2 = i2.getGeneValues();
        //execute crossover
        int[][] offspring = executeGX(g1, g2, 2);
        i1.setGeneValues(offspring[0]);
        i2.setGeneValues(offspring[1]);
    }

    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    /**
     * execute execute crossover between two parents
     *
     * @param tmp1 parent
     * @param tmp2 parent
     */
    public static int[][] executeGX(int[] parent1, int[] parent2, int numChildrens) {

        int lenght = parent1.length;
        int[][] offspring = new int[numChildrens][lenght];
        for (int i = 0; i < offspring.length; i++) {
            //alias to bidimensional array      
            offspring[i] = crossover(parent1, parent2);
        }
        return offspring;
    }

    /**
     * In short, algorithm takes first city from parent1 and looks for the way
     * from this first city in both, parent1 and parent2. then it uses the
     * better next city
     *
     * @see org.jgap.impl.GreedyCrossover
     * @param chromosome1 - first chromosome
     * @param chromosome2 - second chromosome
     * @return newly ordered array of cities (=child =new chromosome)
     */
    public static int[] crossover(int[] chromosome1, int[] chromosome2) {
        Random rnd = new Random();
        int[] c1 = chromosome1;
        int[] c2 = chromosome2;

        int lenght = c1.length;
        //first parent
        LinkedList<Integer> p1 = new LinkedList<>();
        //second parent
        LinkedList<Integer> p2 = new LinkedList<>();
        //children
        LinkedList<Integer> children = new LinkedList<>();
        //copy cities to parents
        for (int j = 0; j < lenght; j++) { // g[0] picked
            p1.add(c1[j]);
            p2.add(c2[j]);
        }
        //include first random city in the children
        children.add(p1.get(rnd.nextInt(c1.length)));

        //insert cheapest city from the last one
        while (p1.size() > 1) {
            //select last city
            int pivot = children.getLast();
            //select next city of parents
            int city1 = findNext(p1, pivot);
            int city2 = findNext(p2, pivot);
            //select cheapes edge
            if (AbstractTSP.getTSPDistance(pivot, city1) < AbstractTSP.getTSPDistance(pivot, city2)) {
                children.add(city1);
            } else {
                children.add(city2);
            }
            //remove pivot from parents
            p1.removeFirstOccurrence(pivot);
            p2.removeFirstOccurrence(pivot);
        }


        int[] rets = new int[c1.length];
        for (int i = 0; i < rets.length; i++) {
            rets[i] = children.get(i);
        }
        return rets;
    }

    /**
     * Helper for GreedyCrossover getChild() algorithm. It finds the next city
     * after city "x" in the chromosome "cities"
     *
     * @param cities - array in which to find the next city after city "x"
     * @param x - city for which we are looking for the next path
     * @return next city to go from the chromosome
     */
    protected static int findNext(LinkedList<Integer> cities, int x) {
        Iterator<Integer> it = cities.iterator();
        while (it.hasNext()) {
            if (it.next() == x && it.hasNext()) {
                return it.next();
            }
        }
        //from the last city we go to the first one
        return cities.getFirst();
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\n GX - Gready Crossover");
        return buf.toString();
    }

    public static void main(String[] args) {
        AbstractTSP i1 = new Ulisses16();
        i1.evaluate();
        AbstractTSP i2 = new Ulisses16();
        i2.evaluate();

        System.out.println("P1 :" + i1);
        System.out.println("P1 :" + i2);

        int[][] o = executeGX(i1.getGeneValues(), i2.getGeneValues(), 20);

        for (int k = 0; k < o.length; k++) {
            i1.setGeneValues(o[k]);
            i1.evaluate();
            System.out.print("\nC  :" + i1);
        }


    }
}
