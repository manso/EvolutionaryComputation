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
public class HXDual extends CrossoverPRM {
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
        int[][] offspring = executeHX(g1, g2, 2);
        i1.setGeneValues(offspring[0]);
        i2.setGeneValues(offspring[1]);
    }
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    static Random random = new Random();

    /**
     * execute execute crossover between two parents
     *
     * @param tmp1 parent
     * @param tmp2 parent
     */
    public static int[][] executeHX(int[] parent1, int[] parent2, int numChildrens) {

        int lenght = parent1.length;
        int[][] offspring = new int[numChildrens][lenght];
        for (int i = 0; i < offspring.length; i++) {
            //alias to bidimensional array      
            offspring[i] = HX(parent1, parent2);
        }
        return offspring;
    }

    private static int[] HX(int[] parent1, int[] parent2) {
        int length = parent1.length;
        ArrayList<Integer> children = new ArrayList<>();
        //--------------------------------------------
        //1.  Randomly  select  a  city  to be the  current  city  c  of the  offspring
        children.add(random.nextInt(length));

        while (children.size() < length) {
            int first = selectNextCity(parent1, parent2, children, children.get(0));
            //repair first edge
            first = first == -1 ? selectRandom(children, length) : first;
            double distFistr = AbstractTSP.getTSPDistance(children.get(0), first);
            int last = selectNextCity(parent1, parent2, children, children.get(children.size() - 1));
            //repair last edge
            last = last == -1 ? selectRandom(children, length) : last;
            double distLast = AbstractTSP.getTSPDistance(children.get(children.size() - 1), last);
            if (distFistr < distLast) {
                children.add(0, first);
            } else {
                children.add(last);
            }
        }
        //-------------------------- Return array ----
        int[] genes = new int[parent1.length];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = children.get(i);
        }
        return genes;
    }

    private static int selectRandom(ArrayList<Integer> children, int length) {
        int rnd;
        do {
            rnd = random.nextInt(length);
        } while (children.contains(rnd));
        return rnd;
    }

    private static int selectNextCity(int[] parent1, int[] parent2, ArrayList<Integer> children, int city) {
        //next parent 1
        int best = -1;
        double min = Double.MAX_VALUE;
        //previous parent 1
        int edge = getPreviousCity(parent1, city);
        double cost = AbstractTSP.getTSPDistance(city, edge);
        if (!children.contains(edge) && min > cost) {
            min = cost;
            best = edge;
        }
        //next parent 1
        edge = getNextCity(parent1, city);
        cost = AbstractTSP.getTSPDistance(city, edge);
        if (!children.contains(edge) && min > cost) {
            min = cost;
            best = edge;
        }
        //next parent 2
        edge = getNextCity(parent2, city);
        cost = AbstractTSP.getTSPDistance(city, edge);
        if (!children.contains(edge) && min > cost) {
            min = cost;
            best = edge;
        }
        //previous parent 2
        edge = getPreviousCity(parent2, city);
        cost = AbstractTSP.getTSPDistance(city, edge);
        if (!children.contains(edge) && min > cost) {
            min = cost;
            best = edge;
        }
        return best;
    }

    /**
     * Get next city of the permutation
     *
     * @param p permutation
     * @param city actual city
     * @return next city
     */
    private static int getNextCity(int[] p, int city) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == city) {
                return p[(i + 1) % p.length];
            }
        }
        return 0;
    }

    /**
     * Get previous city of the permutation
     *
     * @param p permutation
     * @param city actual city
     * @return previous city
     */
    private static int getPreviousCity(int[] p, int city) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] == city) {
                return p[(i + p.length - 1) % p.length];
            }
        }
        return 0;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\n HXDual - Heuristic Crossover");
        buf.append("\n append edges in two extremes");
        return buf.toString();
    }

    public static void main(String[] args) {
        AbstractTSP i1 = new Ulisses16();
        i1.evaluate();
        AbstractTSP i2 = new Ulisses16();
        i2.evaluate();

        System.out.println("P1 :" + i1);
        System.out.println("P1 :" + i2);

        int[][] o = executeHX(i1.getGeneValues(), i2.getGeneValues(), 2);

        for (int k = 0; k < o.length; k++) {
            i1.setGeneValues(o[k]);
            i1.evaluate();
            System.out.print("\nC  :" + i1);
        }


    }
}
