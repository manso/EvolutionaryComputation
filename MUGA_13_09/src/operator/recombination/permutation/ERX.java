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

/**
 *
 * @author ZULU
 */
public class ERX extends CrossoverPRM {

    @Override
    public Population execute(Population pop) {
        Population childs = pop.getCleanCopie();
        PRM_Individual i1, i2;
        //fazer os crossovers
        while (pop.getNumGenotypes() > 1) {
            i1 = (PRM_Individual) pop.removeRandomGenotype();
            //clonning individuals
            if (random.nextDouble() > getProbability()) {
                childs.addGenotype(i1);
                continue;
            }
            //select second individual
            i2 = (PRM_Individual) pop.removeRandomGenotype().getClone();
            //execute X
            doCrossover(i1, i2);
            childs.addGenotype(i1);
            childs.addGenotype(i2);
        }
        //append the rest of original population
        childs.appendPopulation(pop);
        return childs;
    }

    public  void  doCrossover(PRM_Individual i1, PRM_Individual i2) {
        int[] g1 = i1.getGeneValues();
        int[] g2 = i2.getGeneValues();
        //execute crossover
        int[][] offspring = executeERX(g1, g2, i1.getNumCopies() + i2.getNumCopies());
         i1.setGeneValues(offspring[0]);
        i2.setGeneValues(offspring[1]);        
    }
    //--------------------------------------------------------------
    //--------------------------------------------------------------

    /**
     * execute execute crossover between two parents
     *
     * @param parent1 parent
     * @param parent2 parent
     */
    public int[][] executeERX(int[] parent1, int[] parent2, int numOfX) {
        int[][] offspring = new int[numOfX][parent1.length];
        for (int i = 0; i < offspring.length; i++) {
            ERX_Edges erx = new ERX_Edges();
            erx.addEdges(parent1);
            erx.addEdges(parent2);
            offspring[i] = erx.executeERX();
        }
        return offspring;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\n ERX- Edge Recombination Crossover");
        return buf.toString();
    }
}
