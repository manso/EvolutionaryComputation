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
import java.util.Random;
import operator.recombination.Recombination;
import problem.Individual;
import problem.PRM_Individual;
import utils.DynamicLoad;

/**
 *
 * @author ZULU
 */
public class OX extends CrossoverPRM  {

    @Override
    public Population execute(Population pop) {
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
        int[][] offspring = executeOX(g1, g2);
        i1.setGeneValues(offspring[0]);
        i2.setGeneValues(offspring[1]);
    }
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    private static Random rnd = new Random();
    //--------------------------------------------------------------

    /**
     * execute execute crossover between two parents
     *
     * @param tmp1 parent
     * @param tmp2 parent
     */
    public static int[][] executeOX(int[] parent1, int[] parent2) {

        int lenght = parent1.length;
        int[][] offspring = new int[2][lenght];
        //alias to bidimensional array      
        int[] child1 = offspring[0];
        int[] child2 = offspring[1];
        //make copys of parents and clean genes
        for (int i = 0; i < lenght; i++) {
            //clean genes
            child1[i] = -1;
            child2[i] = -1;
        }

        //select two points of crossover
        int cut1 = rnd.nextInt(lenght - 3);
        int cut2 = cut1 + rnd.nextInt(lenght - 1 - cut1);

        //copy genes between cut points
        for (int j = cut1; j < cut2; ++j) {
            child1[j] = parent1[j];
            child2[j] = parent2[j];
        }
        int pointerC;
        int pointerP = cut2;
        //build first children
        for (int i = 0; i < lenght - (cut2 - cut1); i++) {
            //increase Children Pointer
            pointerC = (cut2 + i) % child1.length;
            //find next gene in order
            while (contains(child1, parent2[pointerP])) {
                pointerP = (pointerP + 1) % lenght;
            }
            //set gene
            child1[pointerC] = parent2[pointerP];

        }

        pointerP = cut2;
        //build first children
        for (int i = 0; i < lenght - (cut2 - cut1); i++) {
            //increase Children Pointer
            pointerC = (cut2 + i) % child1.length;
            //find next gene in order
            while (contains(child2, parent1[pointerP])) {
                pointerP = (pointerP + 1) % lenght;
            }
            //set gene
            child2[pointerC] = parent1[pointerP];

        }

        return offspring;
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
        buf.append("\n OX - Order Crossover");
        return buf.toString();
    }

    public static void main(String[] args) {
        int[] p1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] p2 = {4, 5, 2, 1, 8, 7, 6, 9, 3};
        int[][] o = executeOX(p1, p2);
        System.out.print("\n o1 = ");
        for (int i = 0; i < p1.length; i++) {
            System.out.print(" " + o[0][i]);
        }
        System.out.print("\n o2 = ");
        for (int i = 0; i < p1.length; i++) {
            System.out.print(" " + o[1][i]);
        }

    }
}
