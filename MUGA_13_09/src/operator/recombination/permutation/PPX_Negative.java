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
import problem.PRM_Individual;
import utils.DynamicLoad;

/**
 *
 * @author ZULU
 */
public class PPX_Negative extends CrossoverPRM {

    public static int REMOVED = 0xFFFF;

    @Override
    public Population execute(Population pop) {
        Population childs = (Population) DynamicLoad.makeObject(pop);
        int numCrossovers = (int) ((pop.getNumGenotypes() * getProbability()) / 2.0);
        PRM_Individual i1, i2;
        //fazer os crossovers
        for (int i = 0; i < numCrossovers; i++) {
            i1 = (PRM_Individual) pop.removeRandomGenotype().getClone();
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
        int[] c1 = PPX_driver(p1, p2, mask);

        for (int i = 0; i < mask.length; i++) {
            if (random.nextDouble() < prob1) {
                mask[i] = true;
            }
        }
        int[] c2 = PPX_driver(p2, p1, mask);
        ((PRM_Individual) i1).setGeneValues(c1);
        ((PRM_Individual) i2).setGeneValues(c2);
        i1.setNumCopys(1);
        i2.setNumCopys(1);
    }

    //Improving Genetic Algorithms by Search Space Reductions
    // (with Applications to Flow Shop Scheduling)
    //http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.50.2308
    public static int getNext(int[] v, int elem) {
//        System.out.print("Search " + elem + " in " + Arrays.toString(v));
        int index = 0;
        elem = Math.abs(elem);
        while (Math.abs(v[index]) != elem) {
            index++;
        }
//        System.out.println(" found " + index);

        for (int i = 1; i <= v.length; i++) {
            if (v[(i + index) % v.length] != REMOVED) {
                return v[(i + index) % v.length];
            }
        }
        return REMOVED;
    }

    public static void removeElement(int[] v, int elem) {
        for (int i = 0; i < v.length; i++) {
            if (Math.abs(v[i]) == Math.abs(elem)) {
                v[i] = REMOVED;
                return;
            }
        }
    }

    public static int[] PPX_driver(int[] p1, int[] p2, boolean[] mask) {
        int[] c = new int[p1.length];
        //clone original parents
        int[] a = new int[p1.length];
        System.arraycopy(p1, 0, a, 0, p1.length);
        int[] b = new int[p1.length];
        System.arraycopy(p2, 0, b, 0, p2.length);
        if (mask[0]) {
            c[0] = b[0];
        } else {
            c[0] = a[0];
        }
        //build children
        for (int i = 1; i < mask.length; i++) {
            if (mask[i]) {
                //get  gene from first parent
                c[i] = getNext(b, c[i - 1]);
            } else {
                //get  gene from second parent
                c[i] = getNext(a, c[i - 1]);
            }
            removeElement(a, c[i - 1]);
            removeElement(b, c[i - 1]);
//            System.out.println("\nA = " + Arrays.toString(a));
//            System.out.println("B = " + Arrays.toString(b));
//            System.out.println("C = " + Arrays.toString(c));

        }
        return c;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\n PPX - Precedence Preserving Crossover");
        return buf.toString();
    }

    public static void main(String[] args) {
        boolean T = true;
        boolean F = false;

        boolean mask[] = {F, T, F, T, T};
        int[] p1 = {-1, -2, -3, -4, -5};
        int[] p2 = {2, 3, 1, 5, 4};

        System.out.println("P1 = " + Arrays.toString(p1));
        System.out.println("P2 = " + Arrays.toString(p2));
        int[] c = PPX_driver(p1, p2, mask);
        System.out.println(Arrays.toString(c));
    }
}
