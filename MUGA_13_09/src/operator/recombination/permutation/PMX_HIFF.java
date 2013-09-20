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
public class PMX_HIFF extends CrossoverPRM {

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
        int[][] offspring;
        if (i1.compareGenotype(i2) > 0) {
            offspring = executePMX(g1, g2);
        } else {
            offspring = executePMX(g2, g1);
        }
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
        int[][] offspring = new int[2][parent1.length];
        int pos = random.nextInt(parent1.length);
        int UNDEFINED = 0xFFFF;
        for (int i = 0; i < parent1.length; i++) {
            if (Math.abs(parent1[i]) >= Math.abs(parent1[pos])) {
                offspring[0][i] = parent1[i];
                offspring[1][i] = UNDEFINED;
            } else {
                offspring[0][i] = UNDEFINED;
                offspring[1][i] = parent2[i];
            }
        }

        for (int i = 0; i < parent1.length; i++) {
            //complete first
            if (offspring[0][i] == UNDEFINED) {
                for (int j = 0; j < parent2.length; j++) {
                    if (!contains(offspring[0], parent2[j])) {
                        offspring[0][i] = parent2[j];
                        break;
                    }
                }
            }
            //complete second
            if (offspring[1][i] == UNDEFINED) {
                for (int j = 0; j < parent2.length; j++) {
                    if (!contains(offspring[1], parent1[j])) {
                        offspring[1][i] = parent1[j];
                        break;
                    }
                }
            }

        }
//        offspring[0] = HIFF_perm.repair(offspring[0]);
//        offspring[1] = HIFF_perm.repair(offspring[1]);
        return offspring;
    }

    public boolean contains(int[] lst, int v) {
        v = Math.abs(v);
        for (Integer i : lst) {
            if (Math.abs(i) == v) {
                return true;
            }
        }
        return false;
    }
}
