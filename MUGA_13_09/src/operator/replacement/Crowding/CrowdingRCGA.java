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
package operator.replacement.Crowding;

import genetic.population.Population;
import operator.mutation.real.RCGA_Gauss;
import operator.recombination.real.RCGA_AX;
import operator.replacement.Replacement;
import problem.Individual;

/**
 *
 * @author ZULU
 */
public class CrowdingRCGA extends Replacement {

    RCGA_AX cross = new RCGA_AX();
    RCGA_Gauss mut = new RCGA_Gauss();

    @Override
    public Population execute(Population pop, Population childs) {

        cross.setProbability(solver.getRecombine().getProbability());
        mut.setProbability(solver.getMutate().getProbability());

        Population oldPop = solver.getParents().getClone();
        Population newPop = solver.getParents().getCleanCopie();
        Individual p1, p2, c1, c2;
        while (oldPop.getNumGenotypes() > 1) {
            //select parents from old population
            p1 = oldPop.removeRandomGenotype();
            p2 = oldPop.removeRandomGenotype();
            c1 = p1.getClone();
            c2 = p2.getClone();
            //perform Crossover
            if (random.nextDouble() < cross.getProbability()) {
                Individual[] young = cross.execute(c1, c2);
                c1 = young[0];
                c2 = young[1];
            }
            //perform mutation
            c1 = mut.mutate(c1, solver.getMutate().getProbability());
            c2 = mut.mutate(c2, solver.getMutate().getProbability());
            //evaluate            
            if (c1.evaluate()) {
                solver.addEvaluation();
            }
            if (c2.evaluate()) {
                solver.addEvaluation();
            }
            // crowding for c1
            double m1 = c1.distanceTo(p1) + c2.distanceTo(p2);
            double m2 = c1.distanceTo(p2) + c2.distanceTo(p1);
            if (m1 < m2) {
                //p1 c1
                if (c1.compareTo(p1) >= 0) {
                    newPop.addIndividual(c1, 1);
                } else {
                    newPop.addIndividual(p1, 1);
                }
                //p2 c2
                if (c2.compareTo(p2) >= 0) {
                    newPop.addIndividual(c2, 1);
                } else {
                    newPop.addIndividual(p2, 1);
                }
            } else {
                //p2 c1
                if (c1.compareTo(p2) >= 0) {
                    newPop.addIndividual(c1, 1);
                } else {
                    newPop.addIndividual(p2, 1);
                }
                //p1 c2
                if (c2.compareTo(p1) >= 0) {
                    newPop.addIndividual(c2, 1);
                } else {
                    newPop.addIndividual(p1, 1);
                }
            }
        }
//        //complete multipopulation
        int size = pop.getNumGenotypes();
        while (newPop.getNumGenotypes() < size) {
            newPop.addIndividual(pop.removeGenotype(0), 1);
        }
        return newPop;
    }
    
    @Override
    public String toString() {
        return super.toString();  
    }
    
    
    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nCrowding to Real Coded Problems");
        buf.append("\nCrossover :" + cross.toString());
        buf.append("\nMutation  :" + mut.toString());
        return buf.toString();
    }
    
}
