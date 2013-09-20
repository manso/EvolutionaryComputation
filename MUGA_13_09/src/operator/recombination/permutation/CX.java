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
import java.util.Arrays;
import java.util.Random;
import problem.PRM_Individual;
import problem.permutation.TSP.Ulisses16;

/**
 *
 * @author ZULU
 */
public class CX extends CrossoverPRM {

    @Override
    public Population execute(Population pop) {
        Population childs = pop.getCleanCopie();
        PRM_Individual i1, i2;

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
        int[][] offspring = executeCX(g1, g2);
        i1.setGeneValues(offspring[0]);
        i2.setGeneValues(offspring[1]);
    }
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    private static Random rnd = new Random();
    private static int EMPTY = -1;
    //--------------------------------------------------------------

    /**
     * execute execute crossover between two parents
     *
     * @param tmp1 parent
     * @param tmp2 parent
     */
    public static int[][] executeCX(int[] parent1, int[] parent2) {

        int lenght = parent1.length;
        int[][] offspring = new int[2][lenght];
        //alias to bidimensional array      
        int[] child1 = offspring[0];
        int[] child2 = offspring[1];
        //make copys of parents and clean genes
        for (int i = 0; i < lenght; i++) {
            //clean genes
            child1[i] = EMPTY;
            child2[i] = EMPTY;
        }
        buildCycle(parent1, parent2, child1);
        buildCycle(parent2, parent1, child2);
//        System.out.println("\nC1" + Arrays.toString(child1));
//        System.out.println("C2" + Arrays.toString(child2));
        return offspring;
    }

    private static void buildCycle(int[] parent1, int[] parent2, int[] child) {
        int pt = rnd.nextInt(parent1.length);
        while (!contains(child, parent1[pt])) {
//             System.out.print("\n"+pt );
            child[pt] = parent1[pt];
            pt = parent2[pt];
//            System.out.println(" ->"+pt + " => " +Arrays.toString(child));
        }
        pt = 0;
        for (int i = 0; i < child.length; i++) {
            //ocupied position
            if (child[i] != EMPTY) {
                continue;
            }
            //find next 
            while (contains(child, parent2[pt])) {
                pt++;
            }
            child[i] = parent2[pt];
//            System.out.println(Arrays.toString(child));
        }
    }

    private static boolean contains(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\n CX - Cycle Crossover");
        return buf.toString();
    }

    public static void main(String[] args) {
        int[] p1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
        int[] p2 = {4, 1, 2, 8, 7, 6, 9, 3, 5, 0};
        System.out.print("\n p1 = " + Arrays.toString(p1));
        System.out.print("\n p2 = " + Arrays.toString(p2));
        int[][] o = executeCX(p1, p2);
        System.out.print("\n o1 = " + Arrays.toString(o[0]));
        System.out.print("\n o2 = " + Arrays.toString(o[1]));
        Ulisses16 u1 = new Ulisses16();
        Ulisses16 u2 = new Ulisses16();
        CX cx = new CX();
        u1.evaluate();
        u2.evaluate();
        Ulisses16 c1 = (Ulisses16) u1.getClone();
        Ulisses16 c2 = (Ulisses16) u2.getClone();

        cx.doCrossover(c1, c2);
        c1.evaluate();
        c2.evaluate();

        System.out.println("Parents");
        System.out.println("u1 = " + u1);
        System.out.println("u2 = " + u2);
        System.out.println("Childrens");
        System.out.println("c1 = " + c1);
        System.out.println("c2 = " + c2);

    }
}
