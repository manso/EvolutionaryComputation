/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.replacement.real;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.util.Iterator;
import operator.replacement.Replacement;
import problem.Individual;
import utils.NelderMead.NelderMead;

/**
 *
 * @author programmer
 */
public class NM_Gecco extends Replacement {

    NelderMead simplex = new NelderMead();

    public Population selectSimplex(Population parents, Population children) {
        Population popSimplex = new MultiPopulation();
        //best parent
        Individual C1 = children.getBestGenotype();
        popSimplex.addGenotype(C1);
        children.removeGenotype(C1);
        //similar children
        Individual P1 = parents.getMostSimilar(C1);
        popSimplex.addGenotype(P1);
        parents.removeGenotype(P1);
        //Random Parent
        Individual P2 = parents.getRandomGenotype();
        popSimplex.addGenotype(P2);
        parents.removeGenotype(P2);
        //Similar child
        Individual C2 = children.getMostSimilar(P2);
        popSimplex.addGenotype(C2);
        children.removeGenotype(C2);
        //Random child
        Individual C3 = children.getRandomGenotype();
        popSimplex.addGenotype(C3);
        children.removeGenotype(C3);
        //Similar parent
        Individual P3 = parents.getMostSimilar(C3);
        popSimplex.addGenotype(P3);
        parents.removeGenotype(P3);
        return popSimplex;
    }

    @Override
    public Population execute(Population parents, Population children) {
        int sizeNewPop = parents.getNumGenotypes();
        //make new population
        Population selected = children.getCleanCopie();
         //elitism
        selected.addGenotype( parents.getBestGenotype());

        while (selected.getNumGenotypes() < sizeNewPop
                && children.getNumGenotypes() > sizeNewPop / 2
                && parents.getNumGenotypes() > sizeNewPop / 2) {

            //-------------------------------------------------------------
            //------------ get population   ------------------------
            //-------------------------------------------------------------
            Population popSimplex = selectSimplex(parents, children);
            //-------------------------------------------------------
            //-------------  SEARCH ---------------------------------
            //-------------------------------------------------------------
            simplex.searchG(popSimplex, popSimplex.getNumGenotypes());
            //-------------------------------------------------------
            //-------------  REPLACE --------------------------------
            //-------------------------------------------------------
            Iterator<Individual> it = popSimplex.getIterator();
            while (selected.getNumGenotypes() < sizeNewPop && it.hasNext()) {
                selected.addIndividual(it.next().getClone(), 1);
            }
            //add evaluations
            solver.addEvaluations(popSimplex.getEvaluations());
        }
        //------------------- Complete population with random individuals -----------
        parents.appendPopulation(children);
        //add random genotypes to complete population
        while (!parents.isEmpty() && selected.getNumGenotypes() < sizeNewPop) {
            System.out.println("PARENTS " + parents.getNumGenotypes());
            selected.addGenotype(parents.removeRandomGenotype());
        }
        //---------------------------------------------------------------------------
        return selected;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nSelect and remove 6 individuals");
        buf.append("\n3 from de children and 3 from parents");
        buf.append("\nC1 - Best Child");
        buf.append("\nP1 - Parent most similar to C1");
        buf.append("\nC2 - Random Children");
        buf.append("\nP2 - Parent most similar to C2");
        buf.append("\nP3 - Random Parent");
        buf.append("\nC3 - children most similar to P3");
        buf.append("\nExecute Nelder-Mead search in the simplex");
        buf.append("\nInsert the individuals in the new population");
        return buf.toString();
    }
 
}
