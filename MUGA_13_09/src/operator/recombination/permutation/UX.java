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
import operator.recombination.Recombination;
import problem.Individual;
import problem.PRM_Individual;
import utils.DynamicLoad;

/**
 *Improving Genetic Algorithms by Search Space Reductions
 * (with Applications to Flow Shop Scheduling)
 * 
 * Stephen Chen
 * Stephen F. Smith
 * gecco1999
 * ftp://ftp.cs.bham.ac.uk/pub/authors/W.B.Langdon/biblio/gecco1999/GA-829.pdf
 * 
 * @author ZULU
 */
public class UX extends CrossoverPRM {

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

    public void doCrossover(PRM_Individual i1, PRM_Individual i2) {
        int[] p1 = ((PRM_Individual) i1).getGeneValues();
        int[] p2 = ((PRM_Individual) i2).getGeneValues();
        //probability of parent 1
        double prob1 = i1.getNumCopies() / (double) (i1.getNumCopies() + i2.getNumCopies());
        //BuildMask
        boolean mask[] = new boolean[p1.length];
        for (int i = 0; i < mask.length; i++) {
            if (random.nextDouble() < prob1) {
                mask[i] = true;
            }
        }
        int[] c1 = UX_driver(p1, p2, mask, true);
        int[] c2 = UX_driver(p1, p2, mask, false);
        i1.setGeneValues(c1);
        i2.setGeneValues(c2);
        i1.setNumCopys(1);
        i2.setNumCopys(1);
    }

    //Improving Genetic Algorithms by Search Space Reductions
    // (with Applications to Flow Shop Scheduling)
    //http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.50.2308
    public static int removeFirst(int[] v) {
        for (int i = 0; i < v.length; i++) {
            if (v[i] != -1) {
                int aux = v[i];
                v[i] = -1;
                return aux;
            }
        }
        return -1;
    }

    public static void removeElement(int[] v, int elem) {
        for (int i = 0; i < v.length; i++) {
            if (v[i] == elem) {
                v[i] = -1;
                return;
            }
        }
    }

    public static int[] UX_driver(int[] p1, int[] p2, boolean[] mask, boolean first) {
        int[] c = new int[p1.length];
        //clone original parents
        int[] a = new int[p1.length];
        System.arraycopy(p1, 0, c, 0, p1.length);
        int[] b = new int[p1.length];
        System.arraycopy(p2, 0, b, 0, p2.length);
        //preserve genes
        for (int i = 0; i < mask.length; i++) {
            if (mask[i] && first) {
                removeElement(b, c[i]);
            } else {
                c[i] = -1;
            }
        }

        //replace genes
        for (int i = 0; i < mask.length; i++) {
            if (c[i] == -1) {
                c[i] = removeFirst(b);
            }
        }
        return c;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\n UX - UNIFORM ORDER-BASED CROSSOVER");
        return buf.toString();
    }

    public static void main(String[] args) {
        boolean T = true;
        boolean F = false;

        boolean mask[] = {F, T, T, F, F, T};
        int[] p1 = {0,3,2,4,1,5};
        int[] p2 = {1,2,3,4,5,0};

        System.out.println("P1 = " + Arrays.toString(p1));
        System.out.println("P2 = " + Arrays.toString(p2));
        int[] c = UX_driver(p1, p2, mask, true);
        System.out.println(Arrays.toString(c));
    }
}
