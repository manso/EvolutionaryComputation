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
import operator.mutation.bitString.FlipBits;
import operator.recombination.bitString.UniformCrossover;
import operator.replacement.Replacement;
import problem.Individual;

/**
 *
 * @author ZULU
 */
public class CrowdingBits extends Replacement {

    double pCross = 0.75;
    UniformCrossover cross = new UniformCrossover();
    FlipBits mut = new FlipBits();

    @Override
    public Population execute(Population pop, Population childs) {
//        cross = (UniformCrossover) solver.getRecombine();
//        mut = (FlipBits) solver.getMutate();
        cross.setProbability(0.75);
        // 1.0 / #bits
        mut.setProbability(1.0 / pop.getGenotype(0).getBits().getNumberOfBits());
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
            if (random.nextDouble() < pCross) {
                cross.doCrossover(c1, c2);
            }
            //perform mutation
            mut.doMutation(c1);
            mut.doMutation(c2);
            //evaluate            
            if (c1.evaluate()) {
                solver.addEvaluation();
            }
            if (c2.evaluate()) {
                solver.addEvaluation();
            }
            // crowding for c1
            double m1 = c1.getBits().getHammingDistance(p1.getBits())
                    + c2.getBits().getHammingDistance(p2.getBits());
            double m2 = c1.getBits().getHammingDistance(p2.getBits())
                    + c2.getBits().getHammingDistance(p1.getBits());
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
        buf.append("\nCrowding to Bits Problems");
        buf.append("\nCrossover :" + cross.toString());
        buf.append("\nMutation  :" + mut.toString());
        return buf.toString();
    }
//    public Population execute(Population pop, Population childs) {
//        //update probabilities of crossover and mutation
//        cross.setProbability(0.75);
//        // 1.0 / #bits
//        mut.setProbability(1.0 / pop.getGenotype(0).getBits().getNumberOfBits());
//
////        
////        
////        //get genetic operators
////        Recombination cross = solver.getRecombine();
////        Mutation mut = solver.getMutate();
//        //number of genotypes
//        int size = pop.getNumGenotypes();
//        //new population
//        Population newPop = new SimplePopulation();
//        Individual p1, p2, c1, c2;
//        while (newPop.getNumGenotypes() < size && pop.getNumGenotypes() > 1) {
//            //select parents from old population
//            p1 = pop.removeRandomGenotype();
//            p2 = pop.removeRandomGenotype();
//            executeCrowding(p1.getClone(), p2.getClone(), cross, mut);
//            newPop.addGenotype(p1);
//            newPop.addGenotype(p2);
//        }
//        //insert the remain individual (if any)
//        newPop.appendPopulation(pop);
//        return newPop;
//    }

   
}
