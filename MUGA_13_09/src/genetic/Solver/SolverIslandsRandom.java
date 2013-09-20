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
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;
import problem.Individual;

/**
 *
 * @author ZULU
 */
public class SolverIslandsRandom extends SolverIslands {

    @Override
    public Population evolve(Population original) {
        //----------------------------------------------------------------------
        //---------------   M A K E   N E W   I S L A N D S --------------------
        //----------------------------------------------------------------------
        if (islands == null) {
            makeNewIslands(original);
        }
        //----------------------------------------------------------------------
        //---------------  E V O L V E   I S L A N D S      --------------------
        //----------------------------------------------------------------------
        for (int i = 0; i < islands.length; i++) {
            SimpleSolver solver = islands[i];
            //execute migration
            executeMigration(i);
            //iterate solver
            solver.iterate();
        }
        //----------------------------------------------------------------------
        //-------- B U I L D   P A R E N T S   P O P U L  A T I O N      -------
        //----------------------------------------------------------------------
        int size = original.getNumGenotypes();
        //join all individuals in children population
        children = original.getCleanCopie();
        children.appendPopulation(migrants);
        //sum evaluations
        EVALUATIONS = 0;
        for (int i = 0; i < islands.length; i++) {
            children.appendPopulation(islands[i].parents);
            //sum evaluations
            EVALUATIONS += islands[i].EVALUATIONS;
        }
        //select best individuals to parents
        parents = original.getCleanCopie();
        Iterator<Individual> it = children.getSortedIterable().iterator();
        while (it.hasNext() && parents.size() < size) {
            parents.addGenotype(it.next());
        }
        return parents;
    }

    protected void executeMigration(int index) {
        int sizePop = islands[index].parents.size();
        Individual out = islands[index].parents.getRandomGenotype();
        //get random individual from migrants
        Individual in = migrants.removeRandomGenotype();
        //introduce new individual        
        islands[index].parents.addIndividual(in);
        //execute migration
        migrants.addIndividual(out.getClone());
        //normalize size of population
        if (islands[index].parents.size() > sizePop) {
            islands[index].parents.removeGenotype(out);
        }
//        System.out.println(islands[index].getClass().getSimpleName() + " MIGRATION <" + index + "> =" + islands[index].stopCriteria.getProgress() * 100 + " %");
//        System.out.println("MIGRATION <"+ index+">\n in"+ in + "\n out " + out);
    }

    @Override
    public String getAlgorithm() {
        StringBuilder str = new StringBuilder();
        str.append("\nIsland Solver <" + solvers[SOLVER_TYPE] + " " + NUM_ISLANDS + " " + MIGRATION + ">");

        str.append("\nParameters <SOLVER_TYPE><NUM_ISLANDS><MIGRATION>");
        str.append("\n   <SOLVER_TYPE> ");
        for (int i = 0; i < solvers.length; i++) {
            str.append(" [" + i + "]-" + solvers[i]);
        }
        str.append("\n   <NUM_ISLANDS> number of islands");
        str.append("\n   <MIGRATION> Migration TIME [0,1]");
        str.append("\n  [0]no migration [1]migration every generation");
        return str.toString();
    }

    @Override
    public void setParameters(String params) {
        StringTokenizer par = new StringTokenizer(params);
        if (par.hasMoreElements()) {
            try {
                SOLVER_TYPE = Integer.parseInt(par.nextToken());
            } catch (Exception ex) {
                SOLVER_TYPE = 1;
            }
        }
        if (par.hasMoreElements()) {
            try {
                NUM_ISLANDS = Integer.parseInt(par.nextToken());
            } catch (Exception ex) {
                NUM_ISLANDS = 2;
            }
        }
        if (par.hasMoreElements()) {
            try {
                MIGRATION = Double.parseDouble(par.nextToken());
            } catch (Exception ex) {
                MIGRATION = 0.1;
            }
        }
    }

    @Override
    public String getParameters() {
        return SOLVER_TYPE + " " + NUM_ISLANDS + " " + MIGRATION;
    }

    @Override
    public SimpleSolver getClone() {
        //make a copy of the class
        SolverIslandsRandom solver = (SolverIslandsRandom) super.getClone();
        solver.MIGRATION = this.MIGRATION;
        solver.NUM_ISLANDS = this.NUM_ISLANDS;
        return solver;
    }
}
