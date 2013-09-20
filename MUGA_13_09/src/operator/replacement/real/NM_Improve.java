/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.replacement.real;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.util.Iterator;
import java.util.StringTokenizer;
import operator.replacement.Replacement;
import problem.Individual;
import utils.NelderMead.NelderMeadOriginal;

/**
 *
 * @author programmer
 */
public class NM_Improve extends Replacement {

    static String[] strategieName = {"Best", "Worst", "Random"};
    protected int strategie = 0;
    protected int simplexSize = 4;
    protected double probability = 0.25;
    protected NelderMeadOriginal simplex = null;

    public NM_Improve() {
        simplex = new NelderMeadOriginal();
    }

    public Population selectSimplex(Population parents, Population children) {
        Population popSimplex = new MultiPopulation();
        //select one individual
        if (!parents.isEmpty()) {
            Individual ind = null;
            switch (strategie) {
                case 0:
                    ind = parents.getBestGenotype();
                    break;
                case 1:
                    ind = parents.getWorstIndividual();
                    break;
                default:
                    ind = parents.getRandomIndividual();
            }
            parents.removeIndividual(ind);
            popSimplex.addIndividual(ind);
        }
        //select the rest of population
        while (popSimplex.getNumGenotypes() < simplexSize) {
            if (!children.isEmpty()) {
                popSimplex.addIndividual(children.removeRandomIndividual(), 1);
            }
            if (!parents.isEmpty() && popSimplex.getNumGenotypes() < simplexSize) {
                popSimplex.addIndividual(parents.removeRandomIndividual(), 1);
            }
            if (parents.isEmpty() && children.isEmpty()) {
                return popSimplex;
            }
        }
        return popSimplex;
    }

    @Override
    public Population execute(Population parents, Population children) {
        int sizeNewPop = parents.getNumGenotypes();
        //make new population
        Population selected = children.getCleanCopie();
        //elitism
        selected.addGenotype(parents.getBestGenotype());

        while (selected.getNumGenotypes() < sizeNewPop
                && children.getNumGenotypes() > 0) {

            //-------------------------------------------------------------
            //------------ get population   ------------------------
            //-------------------------------------------------------------
            Population popSimplex = selectSimplex(parents, children);
            //-------------------------------------------------------
            //-------------  SEARCH ---------------------------------
            //-------------------------------------------------------------
            if (random.nextDouble() < probability && popSimplex.getNumGenotypes() >= 3) {
                //perform search
                simplex.search(popSimplex);
                //add evaluations
                solver.addEvaluations(popSimplex.getEvaluations());
            }
            //-------------------------------------------------------
            //--------select individuals from next generation -----------
            //-------------------------------------------------------
            Iterator<Individual> it = popSimplex.getIterator();
            while (selected.getNumGenotypes() < sizeNewPop && it.hasNext()) {
                selected.addIndividual(it.next(), 1);
            }
        }
        //------------------- Complete population with best individuals -----------
        parents.appendPopulation(children);
        //add random genotypes to complete population
        while (!parents.isEmpty()
                && selected.getNumGenotypes() < sizeNewPop) {
            selected.addGenotype(parents.removeBestGenotype());
        }
        //add random genotypes to complete population
        while (selected.getNumGenotypes() < sizeNewPop) {
            Individual ind = solver.getTemplate().getClone();
            ind.fillRandom();
            ind.evaluate();
            solver.addEvaluation();
            selected.addGenotype(ind);
        }
        //---------------------------------------------------------------------------

        return selected;
    }

    @Override
    public void setParameters(String str) {
        if (str == null || str.trim().length() == 0) {
            return;
        }
        StringTokenizer par = new StringTokenizer(str);
        try {
            probability = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
        }
        try {
            simplexSize = Integer.parseInt(par.nextToken());
        } catch (Exception e) {
        }
        try {
            strategie = Integer.parseInt(par.nextToken());
        } catch (Exception e) {
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + probability + ">"
                + "<" + simplexSize + ">" + "<" + strategieName[strategie] + ">";
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nImprove individuals in populations");
        buf.append("\nUsing simplex Nelder Mead Algorithm");
        buf.append("\nSelect " + strategieName[strategie] + " parent");
        buf.append("\nSelect half random individuals from offspring");
        buf.append("\nSelect half random individuals from parents");
        buf.append("\n\nParameters:<PROBABILITY><SIMPLEX SIZE><STATEGIE> ");
        buf.append("\n      <PROBABILITY>:to perform local search");
        buf.append("\n      <SIMPLEX SIZE>:size of simplex");
        buf.append("\n      <STRATEGIE>: 0-Best 1-Worst 2-Random");
        return buf.toString();
    }

    @Override
    public NM_Improve getClone() {
        NM_Improve clone = (NM_Improve) super.getClone();
        clone.simplexSize = this.simplexSize;
        clone.probability = this.probability;
        clone.strategie = this.strategie;
        return clone;
    }
}
