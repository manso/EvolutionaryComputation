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
package utils.Diversity;

import genetic.gene.Gene;
import genetic.gene.GeneNumber;
import genetic.population.Population;
import java.util.Arrays;
import java.util.Iterator;
import problem.Individual;
import problem.PRM_Individual;
import problem.RC_Individual;

/**
 *
 * @author ZULU
 */
public class Shannon {

    public static double calculateEntropy(Population pop) {
        Individual template = pop.getIndividual(0);
        //Real coded individuals 
        if (template instanceof RC_Individual) {
            RC_Individual r = (RC_Individual) template;
            return getEntropyRealCoded(pop, r.getMinValue(), r.getmaxValue());
        } else if (template instanceof PRM_Individual) {
           return getEntropyPermutation(pop);
        } else {
            //gene double individuals
            Gene g = template.getGene(0);
            if (g instanceof GeneNumber) {
                GeneNumber gd = (GeneNumber) g;
                return getEntropyRealCoded(pop, gd.getMinValue(), gd.getMaxValue());
            }
        }
        //not real coded
        return 0;
    }

    public static double getEntropyRealCoded(Population pop, double min, double max) {
        int numInd = pop.getNumGenotypes();
        int numGenes = pop.getIndividual(0).getNumGenes();
        //matrix are inverted
        //every row of the matrix represents the column of values in the population         
        // necessary to sort the data of the gene
        //first row - Minimum of the gene
        //last row - maximum of the gene
        double[][] gene = new double[numGenes][numInd + 2];
        Iterator<Individual> it = pop.getIterator();
        //maximum and minimum of interval
        for (int i = 0; i < gene.length; i++) {
            gene[i][0] = min;
            gene[i][gene[i].length - 1] = max;
        }
        //copy individuals
        int index = 1;
        while (it.hasNext()) {
            //value of the genes
            double v[] = it.next().getValues();
            //transpose values of the genes
            for (int i = 0; i < v.length; i++) {
                gene[i][index] = v[i];
            }
            index++;
        }

        double entropyValue = 0;
        for (double[] g : gene) {
            entropyValue += getIntervalEntropy(g);
        }
        return entropyValue / gene.length;
    }

    public static double getIntervalEntropy(double[] gene) {
        double sum = 0;
        double DIM = gene[gene.length - 1] - gene[0];
        Arrays.sort(gene);
        for (int i = 2; i < gene.length - 1; i++) {
            double p = (gene[i] - gene[i - 1]) / DIM;
            //prevent the logarithm of zero
            if (p != 0.0) {
                sum += -p * logarithm(p, gene.length - 3);
            }
        }
        return sum;
    }
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    //--------------------------------------------- PERMUTATION ---------------------
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------

    public static double getEntropyPermutation(Population pop) {
        PRM_Individual template = (PRM_Individual) pop.getIndividual(0);
        int genes = template.getNumGenes();
        int num = pop.getNumGenotypes();
        double[][] matrix = new double[genes][genes];
        Iterator<Individual> it = pop.getIterator();
        //count genes
        while (it.hasNext()) {
            int[] g = ((PRM_Individual) it.next()).getGeneValues();
            for (int i = 0; i < g.length; i++) {
                matrix[g[i]][i] += 1.0;
            }
        }
        //calculate probability
        for (int y = 0; y < matrix.length; y++) {
//            System.out.println("");
            for (int x = 0; x < matrix.length; x++) {
                matrix[y][x] /= num;
//                System.out.print(matrix[y][x] + "  \t");
            }
        }
//        System.out.println("\nEntropia");
        //calculate total entropy
        double entropy = 0;
        for (int x = 0; x < matrix.length; x++) {
            double total = 0;
            for (int y = 0; y < matrix.length; y++) {
                if(matrix[y][x]!= 0.0)
                    total += -matrix[y][x] * logarithm(matrix[y][x], genes);
            }
//            System.out.print(total  + "  \t");
            entropy += total;
        }
        //return mean
        return entropy / genes;
    }

    public static double logarithm(double num, double base) {
        return Math.log10(num) / Math.log10(base);
    }

    public static void main(String[] args) {
//        double[] v1 = {1, 1, 2, 3, 4, 5, 6, 7, 8, 9,9};
//        System.out.println("Entr " + getEntropy(v1));
//
//
//        double[] v2 = {1, 5,5,5,5,5,5,5,5,5,5,9};
//        System.out.println("Entr " + getEntropy(v2));
//        
//        double[] v3 = {1,4, 5,5,5,5,5,5,5,5,5,9};
//        System.out.println("Entr " + getEntropy(v3));
//        
//        double[] v4 = {1,2, 3,3,3,3,3,3,3,3,3,9};
//        System.out.println("Entr " + getEntropy(v4));
//        
//        double[] v5 = {1,5, 5.1,5.2,5.3,5.4,5.6,5.8,5.9,6,9};
//        System.out.println("Entr " + getEntropy(v5));
//        
//         double[] v6 = {1,5, 6,6,6,6,6,6,6,6,9};
//        System.out.println("Entr " + getEntropy(v6));
//
//

//        double[] v2 = {-5, -5, -5, 0, 0, 0, 0, 5, 5, 5, 5};
//        System.out.println("Entr " + getEntropy(v2, 10));
//
//        Population p = new SimplePopulation();
//        SimpleRC r1 = new SimpleRC(3);
//        double v1[] = {1, 2, 3};
//        r1.setValues(v1);
//        p.addIndividual(r1);
//        SimpleRC r2 = new SimpleRC(3);
//        double v2[] = {2, 3, 4};
//        r2.setValues(v2);
//        p.addIndividual(r2);
//
//        SimpleRC r3 = new SimpleRC(3);
//        double v3[] = {5, 6, 7};
//        r3.setValues(v3);
//        p.addIndividual(r3);
//
//        System.out.println("Sh" + Shannon.calculateEntropy(p));


//        System.out.println("Pop\n" + p);
//        System.out.println("Entrophy" + calculateEntropy(p));
//        F1_Shifted_Sphere f = new F1_Shifted_Sphere();
//        f.setParameters("10");
//        f = new F1_Shifted_Sphere();
//        p.createRandomPopulation(40, f);
//        System.out.println("Pop\n" + p);
//        System.out.println("Entrophy" + calculateEntropy(p));
//          int g[][] = {
//              {0,0,0,0},
//              {1,1,1,1},
//              {2,2,2,2},
//              {3,3,3,3},              
//          };
        
//        
//         int g[][] = {
//              {0,1,2,3,4},
//              {1,0,2,3,4},
//              {2,1,0,3,4},
//              {3,2,1,0,4},
//              {4,2,1,0,4},
//          };
//          SimplePopulation p = new SimplePopulation();
//          for (int i = 0; i < g.length; i++) {
//            p.addGenotype( new SimplePermutation(g[i]) );
//        }
//          System.out.println(p);
//          System.out.println("Entrophy" + calculateEntropy(p));


    }
}
