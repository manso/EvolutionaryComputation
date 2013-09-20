///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package genetic.Solver;

import genetic.population.Population;
import java.util.Random;
import problem.Individual;

/**
 *
 * @author ZULU
 */
public class SolverCrowding extends SimpleSolver {

    Random random = new Random();

    public Population evolve(Population original) {
        children = original.getClone();
        parents = original.getClone();
        Individual p1, p2, c1, c2;
        int size = parents.getNumGenotypes();
        for (int i = 0; i < original.size() / 2; i++) {
            //select parents from population
            p1 = parents.removeRandomGenotype();
            p2 = parents.removeRandomGenotype();

            c1 = p1.getClone();
            c2 = p2.getClone();
            //increment copys if is selected
            //and copy back to population
            p1.incrementCopys();
            p2.incrementCopys();

            //perform Crossover
            if (random.nextDouble() < recombine.getProbability()) {
                recombine.doCrossover(c1, c2);
            }
            //perform mutation
            mutate.doMutation(c1);
            mutate.doMutation(c2);
            //----------repair individuals -----
            c1 = reparation.repair(c1);
            c2 = reparation.repair(c2);
            //----------------------------------
            //evaluate            
            if (c1.evaluate()) {
                EVALUATIONS++;
            }
            if (c2.evaluate()) {
                EVALUATIONS++;
            }
            //calculate distances   
            double m1 = c1.distanceTo(p1) + c2.distanceTo(p2);
            double m2 = c1.distanceTo(p2) + c2.distanceTo(p1);
            //insert best individuals based on distances
            if (m1 < m2) {
                //p1 c1
                if (c1.compareTo(p1) >= 0) {
                    parents.addIndividual(c1);
                } else {
                    parents.addGenotype(p1);
                }
                //p2 c2
                if (c2.compareTo(p2) >= 0) {
                    parents.addIndividual(c2);
                } else {
                    parents.addGenotype(p2);
                }
            } else {
                //p2 c1
                if (c1.compareTo(p2) >= 0) {
                    parents.addIndividual(c1);
                } else {
                    parents.addGenotype(p2);
                }
                //p1 c2
                if (c2.compareTo(p1) >= 0) {
                    parents.addIndividual(c2);
                } else {
                    parents.addGenotype(p1);
                }
            }
            //------------------------------------------------
            //normalize  size of population
            //------------------------------------------------
            if (size != parents.getNumGenotypes()) {
                //sort parents
                if (p2.compareTo(p1) > 0) {
                    Individual aux = p1;
                    p1 = p2;
                    p2 = aux;
                }
                //------------ insert best parent
                if (!parents.contains(p1)) {
                    parents.addGenotype(p1);
                }
                //insert second parent
                if (size != parents.getNumGenotypes()) {
                    if (!parents.contains(p2)) {
                        parents.addGenotype(p2);
                    }
                }
            }
            //------------------------------------------------
        }
        parents = rescale.execute(parents);
        return parents;

    }

    public String getAlgorithm() {
        StringBuilder str = new StringBuilder();
        str.append("\n Crowding");
        str.append("\n 1 - create random POP");
        str.append("\n 2 - evaluate POP");
        str.append("\n 3 - until STOP criteria");
        str.append("\n    3.1 - Select two parents P1 and P2");
        str.append("\n    3.2 - C1,C2 = recombine(P1,P2)");
        str.append("\n    3.3 - C1,C2 = mutate(C1,C2)");
        str.append("\n    3.4 - Replace P1,P2 by C1,C2 if better");
        str.append("\n          and using distances between Px-Cx");
        return str.toString();
    }

    public String getInfo() {
        StringBuffer txt = new StringBuffer();
        txt.append("Crowding Solver " + getTitle().trim());
        txt.append("\nSolver type   " + this.getClass().getSimpleName());
        txt.append("\nStop Criteria " + this.getStop().toString().trim());
        txt.append("\nIndividual    " + this.getTemplate().getName());
        txt.append("\nPopulation    " + this.parents.getClass().getSimpleName().trim());
        txt.append("\nGenotypes     " + this.parents.getNumGenotypes());
        txt.append("\nIndividuals   " + this.parents.getNumIndividuals());
        txt.append("\nSelection      NOT USED");
        txt.append("\nRecombination " + this.recombine.toString().trim());
        txt.append("\nMutation      " + this.mutate.toString().trim());
        txt.append("\nReparation    " + this.reparation.toString().trim());
        txt.append("\nReplacement    NOT USED");
        txt.append("\nRescaling      NOT USED");
        txt.append("\nStatistics    " + this.stats.getInfo().trim());
        txt.append("\nEvaluations   " + EVALUATIONS);
        txt.append("\nGenerations   " + GENERATION);
        return txt.toString();
    }
}
