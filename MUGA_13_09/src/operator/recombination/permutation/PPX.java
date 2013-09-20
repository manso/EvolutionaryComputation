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
 *
 * @author ZULU
 */
public class PPX extends CrossoverPRM {

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

    @Override
    public void doCrossover(PRM_Individual i1, PRM_Individual i2) {
        int[] p1 = i1.getGeneValues();
        int[] p2 = i2.getGeneValues();
        //probability of parent 1
        double prob1 = i1.getNumCopies() / (double) (i1.getNumCopies() + i2.getNumCopies());
        //BuildMask
        boolean mask[] = new boolean[p1.length];
        for (int i = 0; i < mask.length; i++) {
            if (random.nextDouble() < prob1) {
                mask[i] = true;
            }
        }
        int[] c1 = PPX_driver(p1, p2, mask, true);
        int[] c2 = PPX_driver(p1, p2, mask, false);
        ((PRM_Individual) i1).setGeneValues(c1);
        ((PRM_Individual) i2).setGeneValues(c2);
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

    public static int[] PPX_driver(int[] p1, int[] p2, boolean[] mask, boolean firstParent) {
        int[] c = new int[p1.length];
        //clone original parents
        int[] a = new int[p1.length];
        System.arraycopy(p1, 0, a, 0, p1.length);
        int[] b = new int[p1.length];
        System.arraycopy(p2, 0, b, 0, p2.length);
        //build children
        for (int i = 0; i < mask.length; i++) {
            if (mask[i] && firstParent) {
                //get and remove first gene from first parent
                c[i] = removeFirst(a);
                //remove the same element in second parent
                removeElement(b, c[i]);
            } else {
                //get and remove first gene from second parent
                c[i] = removeFirst(b);
                //remove the same element in second parent
                removeElement(a, c[i]);
            }
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
}
