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
import problem.Individual;

/**
 *
 * @author ZULU
 */
public class SolverES extends SimpleSolver {

    @Override
    public Population evolve(Population original) {
        children = original.getCleanCopie();

        //select best individuals
        selected = select.execute(original);
        //elitism of all individuals selected
        children.appendPopulation(selected);
        //size of original population
        int sizePop = original.getNumGenotypes();
        //reproduce selected by mutation Operator
        int index = 0;
        //complete population
        while (children.getNumGenotypes() < sizePop) {
            //select one individual sequencially
            index = (index + 1) % selected.size();
            Individual mutant = selected.getGenotype(index).getClone();
            for (int i = 0; i < mutant.getNumCopies(); i++) {
                Individual ind = mutant.getClone();
                ind.setNumCopys(i + 1);
//            mutant.setNumCopys(1);
                //apply mutation
                mutate.doMutation(ind);
                if (mutant.evaluate()) {
                    EVALUATIONS++;
                }
                ind.setNumCopys(1);
                //introduce individual in the new population
                children.addIndividual(ind);
                //stop mutation
                if(children.getNumGenotypes() >= sizePop)
                    break;
                
            }
        }
        //rescale number of copies
        parents = rescale.execute(children);
        //return new population
        return parents;
    }

    @Override
    public String getAlgorithm() {
        StringBuilder str = new StringBuilder();
        str.append("\n Evolution by Mutation");
        str.append("\n 1 - create random POP");
        str.append("\n 2 - evaluate POP");
        str.append("\n 3 - until STOP criteria");
        str.append("\n    3.1 - ELIT = selection(POP)");
        str.append("\n    3.3 - MATE = mutation(ELIT)");
        str.append("\n    3.4 - MATE = reparation(MATE)");
        str.append("\n    3.5 - POP  = ELIT + MATE");
        str.append("\n\n  Selection operator may select an elit Population ");
        str.append("\n  individuals selected < population dimension");
        return str.toString();
    }

    public String getInfo() {
        StringBuffer txt = new StringBuffer();
        txt.append("GeneticSolver " + getTitle().trim());
        txt.append("\nSolver type   " + this.getClass().getSimpleName());
        txt.append("\nStop Criteria " + this.getStop().toString().trim());
        txt.append("\nIndividual    " + this.getTemplate().getName());
        txt.append("\nPopulation    " + this.parents.getClass().getSimpleName().trim());
        txt.append("\nGenotypes     " + this.parents.getNumGenotypes());
        txt.append("\nIndividuals   " + this.parents.getNumIndividuals());
        txt.append("\nSelection     " + this.getSelect().toString().trim());
        txt.append("\nRecombination " + "NOT USED");
        txt.append("\nMutation      " + this.mutate.toString().trim());
        txt.append("\nReparation    " + this.reparation.toString().trim());
        txt.append("\nReplacement   " + "NOT USED");
        txt.append("\nRescaling     " + this.rescale.toString().trim());
        txt.append("\nStatistics    " + this.stats.getInfo().trim());
        txt.append("\nEvaluations   " + EVALUATIONS);
        txt.append("\nGenerations   " + GENERATION);
        return txt.toString();
    }
}
