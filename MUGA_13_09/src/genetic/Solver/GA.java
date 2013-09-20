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
///**** or support.                                                    ****/
///**** \   If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package genetic.Solver;

import genetic.population.Population;

/**
 *
 * @author ZULU
 */
public class GA extends SimpleSolver {

    @Override
    public Population evolve(Population original) {
//        System.out.print(" inicial "+EVALUATIONS);
        selected = select.execute(original);
//        System.out.print("SELECTED :" + selected.getNumIndividuals());
        children = recombine.execute(selected.getClone());
//        System.out.print("\t Recombine :" + children.getNumIndividuals());
        children = mutate.execute(children);
//        System.out.print("\t mutate :" + children.getNumIndividuals());
        children = reparation.execute(children);
//        System.out.print("\t reparation :" + children.getNumIndividuals());
        children.evaluate();
        parents = replace.execute(original, children);          
        parents = rescale.execute(parents);        
//        System.out.println("\t replacement :" + children.getNumIndividuals());
        EVALUATIONS += children.getEvaluations();
//        System.out.println(" final "+EVALUATIONS);
        children =  selected;
        return parents;
    }

    @Override
    public String getAlgorithm() {
        StringBuilder str = new StringBuilder();
        str.append("\n Simple Genetic Algorithm Solver");
        str.append("\n 1 - create POP");
        str.append("\n 2 - evaluate POP");
        str.append("\n 3 - until STOP criteria");
        str.append("\n    3.1 - MATE = selection(POP)");
        str.append("\n    3.2 - MATE = recombination(MATE)");
        str.append("\n    3.3 - MATE = mutation(MATE)");
        str.append("\n    3.4 - MATE = reparation(MATE)");
        str.append("\n    3.5 - evaluate MATE");
        str.append("\n    3.6 - POP  = replacement(POP,MATE)");
        str.append("\n    3.7 - POP  = rescaling(POP)");
        return str.toString();
    }
}