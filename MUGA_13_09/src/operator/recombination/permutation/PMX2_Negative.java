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
import problem.PRM_Individual;
import utils.DynamicLoad;

/**
 * Genetic Algorithm Solution of the TSP Avoiding Special Crossover and Mutation
 * http://www.ceng.metu.edu.tr/~ucoluk/research/publications/tspnew.pdf
 * http://www.ceng.metu.edu.tr/~ucoluk/research/publications/tsp.pdf
 *
 * @author ZULU
 */
public class PMX2_Negative extends CrossoverPRM {

    @Override
    public Population execute(Population pop) {
        //automatic probability
        double probToX = probability <= 0 ? 0.75 : probability;
        Population childs = (Population) DynamicLoad.makeObject(pop);
        PRM_Individual i1, i2;
        //fazer os crossovers
        while (!pop.isEmpty()) {
            //Clone
            if (random.nextDouble() > probToX || pop.getNumGenotypes() == 1) {
                childs.addGenotype(pop.removeRandomGenotype().getClone());
            } //crossover
            else {
                i1 = (PRM_Individual) pop.removeRandomGenotype().getClone();
                i2 = (PRM_Individual) pop.removeRandomGenotype().getClone();
                doCrossover(i1, i2);
                childs.addGenotype(i1);
                childs.addGenotype(i2);
            }
        }
        return childs;
    }

    @Override
    public void doCrossover(PRM_Individual i1, PRM_Individual i2) {
        int[] g1 = i1.getGeneValues();
        int[] g2 = i1.getGeneValues();
        int[][] offspring = executePMX(g1, g2);
        i1.setGeneValues(offspring[0]);
        i2.setGeneValues(offspring[1]);
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\n PMX -  Partially Mapped Crossover");
        return buf.toString();
    }
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------
    //--------------------------------------------------------------

    /**
     * doCrossover doCrossover crossover between two parents
     *
     * @param tmp1 parent
     * @param tmp2 parent
     */
    public int[][] executePMX(int[] parent1, int[] parent2) {
        int UNDEFINED = -9999999;
        int[][] offspring = new int[2][parent1.length];
        //alias to bidimensional array
        int[] child1 = offspring[0];
        int[] child2 = offspring[1];
        //make copys of parents and clean genes
        for (int i = 0; i < parent2.length; i++) {
            //clean genes
            child1[i] = UNDEFINED;
            child2[i] = UNDEFINED;
        }

        //select two points of crossover
        int cut1 = 3, cut2 = 6;
//        //------------------------------------------------
        do {
            cut1 = random.nextInt(child1.length - 1) + 1;
            cut2 = random.nextInt(child1.length - 1) + 1;
        } while (cut2 <= cut1);
        //-----------------------------------------------
//        System.out.println("[" + cut1 + " , " + cut2 + "]");
        //copy genes between cut points
        for (int j = cut1; j < cut2; ++j) {
            child1[j] = parent1[j];
            child2[j] = parent2[j];
        }
//        System.out.println("C1 =" + Arrays.toString(child1));
        //fill the left side of the 1st cut point
        for (int j = 0; j < cut1; ++j) {
            child1[j] = getMappedGene(parent2[j], child1, parent2, cut1, cut2);
            child2[j] = getMappedGene(parent1[j], child2, parent1, cut1, cut2);
//            System.out.println("C1 =" + Arrays.toString(child1));

        }
        // fill the right side of the 2st cut point
        for (int j = cut2; j < child1.length; ++j) {
            child1[j] = getMappedGene(parent2[j], child1, parent2, cut1, cut2);
            child2[j] = getMappedGene(parent1[j], child2, parent1, cut1, cut2);
//            System.out.println("C1 =" + Arrays.toString(child1));
        }
//        for (int i = 0; i < child2.length / 2; i++) {
//            int aux = child2[i];
//            child2[i] = child2[child2.length - i - 1];
//            child2[child2.length - i - 1] = aux;
//        }
        return offspring;
    }

    /**
     * get maped gene of doCrossover crossover
     *
     * @param gene original gene
     * @param destination individual to put the gene
     * @param origin individual to get the gene
     * @param cut1 first cut point
     * @param cut2 second cut point
     * @return value of mapped gene
     */
    protected int getMappedGene(int gene, int destination[], int origin[], int cut1, int cut2) {
        //if destination not contais the gene
        if (!contains(gene, destination)) {
            return gene;
        } //get mapped value
        else {
            //index of mapping
            int index = cut1;
            //get mapped value
            while (index < cut2 && Math.abs(gene) != Math.abs(destination[index])) {
                index++;
            }
            //destination not contains the value
            if (!contains(origin[index], destination)) {
                return origin[index];
            } else {
                //calculate new mapping value
                return getMappedGene(origin[index], destination, origin, cut1, cut2);
            }
        }
    }

    /**
     * verify if the value is present in vector
     *
     * @param value value to search
     * @param vector array of values
     * @return
     */
    protected boolean contains(int value, int[] vector) {
        value = Math.abs(value);
        for (int i = 0; i < vector.length; i++) {
            if (Math.abs(vector[i]) == value) {
                return true;
            }
        }
        return false;
    }
//    public static void main(String[] args) {
//        int[] p1 = {-6, -2, -3, -4, -1, -7, -5};
//        int[] p2 = {-5, 2, 4, -1, 3, 7, 6};
//
//        int[][] x = PMX2_Negative.executePMX(p1, p2);
//        for (int i = 0; i < x.length; i++) {
//            System.out.println("");
//            for (int j = 0; j < x[i].length; j++) {
//                System.out.print("\t" + x[i][j]);
//            }
//
//        }
//    }
}
