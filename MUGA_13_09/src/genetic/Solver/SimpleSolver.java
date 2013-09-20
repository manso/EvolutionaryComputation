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
package genetic.Solver;

import genetic.population.HallOfFame;
import genetic.population.MultiPopulation;
import genetic.population.Population;
import genetic.stopCriteria.StopCriteria;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import operator.mutation.Mutation;
import operator.recombination.Recombination;
import operator.reparation.Reparation;
import operator.replacement.Replacement;
import operator.rescaling.Rescaling;
import operator.selection.Selection;
import problem.Default;
import problem.Individual;
import statistics.SolverStatistic;
import utils.DynamicLoad;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public abstract class SimpleSolver implements Serializable{

    public static int DEFAULT_POPULATION_SIZE = 128;
    //------------------------------------------------------
    //------------------------------------------------------

    public abstract Population evolve(Population original);

    public abstract String getAlgorithm();
    //------------------------------------------------------
    //------------------------------------------------------    
    protected static Random rnd = new Random();
    public int EVALUATIONS = 0;
    public int GENERATION = 0;
    public String title;
    public Individual template;
    public Population parents; //main population
    public Population selected;  // mate population
    public Population children;//offspring population
    public Selection select;
    public Recombination recombine;
    public Mutation mutate;
    public Replacement replace;
    public Reparation reparation;
    public Rescaling rescale;
    public StopCriteria stopCriteria;
    public SolverStatistic stats;
    public HallOfFame hallOfFame;

    public SimpleSolver(SimpleSolver solver) {
        setAll(solver);
    }

    public final void setAll(SimpleSolver solver) {
        this.GENERATION = solver.GENERATION;
        this.EVALUATIONS = solver.EVALUATIONS;
        title = solver.title + "";
        template = solver.template.getClone();
        parents = solver.parents.getClone();
        children = solver.children.getClone();
        selected = solver.selected.getClone();
        setSelect(solver.getSelect().getClone());
        setRecombine(solver.getRecombine().getClone());
        setMutate((Mutation)solver.getMutate().getClone());
        setReparation(solver.getReparation().getClone());
        setReplace(solver.getReplace().getClone());
        setRescaling(solver.getRescaling().getClone());
        setStop(solver.getStop().getClone());
        setStats(solver.getStats().getClone());
        hallOfFame = solver.hallOfFame.getClone();
    }

    public SimpleSolver() {
        title = this.getClass().getSimpleName();
        template = new Default();

        //------------- default population -------------
        Population pop = new MultiPopulation();
        pop.createRandomPopulation(DEFAULT_POPULATION_SIZE, template);
        pop.evaluate();
        EVALUATIONS = pop.getEvaluations();
        GENERATION = 0;
        setPopulation(pop);
        //---------------------------------------------
        setSelect(new operator.selection.Default());
        setRecombine(new operator.recombination.Default());
        setMutate(new operator.mutation.Default());
        setReparation(new operator.reparation.Default());
        setReplace(new operator.replacement.Default());
        setRescaling(new operator.rescaling.Default());
        setStop(new genetic.stopCriteria.Default());
        hallOfFame = new HallOfFame();
        hallOfFame.add(parents.getGoods());
        setStats(new SolverStatistic(title));
        stats.addSolverToStats(this, GENERATION);

    }

    public void iterate() {
        parents = evolve(parents);
        updateSolverStats();
        GENERATION++;
    }

    public boolean isDone() {
        return stopCriteria.isDone(this);
    }

    public String getEvolutionInfo() {
        StringBuilder txt = new StringBuilder();
        txt.append("GENERATION  ").append(GENERATION);
        txt.append("\nEVALUATIONS ").append(EVALUATIONS);
        Individual best = hallOfFame.getBestIndividal();
        txt.append("\nBest Value  ").append(best.getFitness());
        txt.append("\nOptimum     ").append(Individual.getBest());

        txt.append("\n-------- Statistics ----------").append(stats.getLastValues());
        return txt.toString();
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
        txt.append("\nRecombination " + this.recombine.toString().trim());
        txt.append("\nMutation      " + this.mutate.toString().trim());
        txt.append("\nReparation    " + this.reparation.toString().trim());
        txt.append("\nReplacement   " + this.replace.toString().trim());
        txt.append("\nRescaling     " + this.rescale.toString().trim());
        txt.append("\nStatistics    " + this.stats.getInfo().trim());
        txt.append("\nEvaluations   " + EVALUATIONS);
        txt.append("\nGenerations   " + GENERATION);
        return txt.toString();
    }

    public void resetStats() {
        parents.evaluate();
        GENERATION = 0;
        EVALUATIONS = 0;
        this.stats = new SolverStatistic(title);       
        //----------------------------------------------
        hallOfFame = parents.getGoods();
        this.stats.addSolverToStats(this, GENERATION);
        stopCriteria.reset();
    }

    public void addToStatistics() {
        getStats().addSolverToStats(this, stopCriteria.getCurrentValue());
    }

    public void restartSolver() {
        this.parents.restart();
        this.children = parents.getCleanCopie();
        this.selected = parents.getCleanCopie();
        resetStats();
    }

    public void setIndividualsToPopulation(Population pop) {
        parents.clear();
        //add clone of individuals
        for (int i = 0; i < pop.size(); i++) {
            parents.addGenotype(pop.getIndividual(i).getClone());
        }
        this.children = parents.getCleanCopie();
        this.selected = parents.getCleanCopie();
        this.GENERATION = 0;
        this.EVALUATIONS = parents.getEvaluations();
    }

    public void setPopulation(Population pop) {
        //----- evaluate population if necessary ----
        //store old evals
        int numEvals = pop.getEvaluations();
        pop.evaluate();
        //update EVALUATIONS
        this.EVALUATIONS = pop.getEvaluations() - numEvals;
        //update populations
        this.parents = pop;
        this.children = parents.getCleanCopie();
        this.selected = parents.getCleanCopie();
    }

    public void setParents(Population pop) {
        parents = pop;
        if (parents != null && parents.size() > 0) {
            resetStats();
            template = pop.getGenotype(0);
            template.setNumCopys(1);
        }
    }

    public void setChildren(Population pop) {
        children = pop;
    }

    /**
     * @return the parents
     */
    public Population getParents() {
        return parents;
    }

    /**
     * @return the children
     */
    public Population getChildren() {
        return children;
    }

    public void setParameters(String params) {
        //do nothing
    }

    public String getParameters() {
        //do nothing
        return "";
    }

    public void addEvaluation() {
        EVALUATIONS++;
    }

    public void addEvaluations(int evals) {
        EVALUATIONS += evals;
    }

    /**
     * @return the template
     */
    public Individual getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(Individual template) {
        this.template = template;
    }

    /**
     * @return the children
     */
    public Population getChilds() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChilds(Population childs) {
        this.children = childs;
    }

    /**
     * @return the selected
     */
    public Population getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(Population selected) {
        this.selected = selected;
    }

    /**
     * @return the select
     */
    public Selection getSelect() {
        return select;
    }

    /**
     * @param s the select to set
     */
    public void setSelect(Selection s) {
        s.setSolver(this);
        this.select = s;
    }

    /**
     * @return the recombine
     */
    public Recombination getRecombine() {
        return recombine;
    }

    /**
     * @param r the recombine to set
     */
    public void setRecombine(Recombination r) {
        r.setSolver(this);
        this.recombine = r;
    }

    /**
     * @return the mutate
     */
    public Mutation getMutate() {
        return mutate;
    }

    /**
     * @param m the mutate to set
     */
    public void setMutate(Mutation m) {
        m.setSolver(this);
        this.mutate = m;
    }

    /**
     * @return the replace
     */
    public Replacement getReplace() {
        return replace;
    }

    /**
     * @param r the replace to set
     */
    public void setReparation(Reparation r) {
        r.setSolver(this);
        this.reparation = r;
    }

    /**
     * @return the replace
     */
    public Reparation getReparation() {
        return reparation;
    }

    /**
     * @param r the replace to set
     */
    public void setReplace(Replacement r) {
        r.setSolver(this);
        this.replace = r;
    }

    /**
     * @return the rescaling
     */
    public Rescaling getRescaling() {
        return rescale;
    }

    /**
     * @param replace the replace to set
     */
    public void setRescaling(Rescaling r) {
        r.setSolver(this);
        this.rescale = r;

    }

    /**
     * @return the stats
     */
    public SolverStatistic getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(SolverStatistic stats) {
        this.stats = stats;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the hallOfFame
     */
    public HallOfFame getHallOfFame() {
        return hallOfFame;
    }

    public void saveNow() {
        String path = "./stats/" + Funcs.getNow("yyyy-MM-dd") + "/";
        Funcs.createPath(path);
        String file = Funcs.getNormalizedFileName(Funcs.getNow("HH-mm-ss-SSS") + title);
        System.out.println("Saving file :" + file);
        saveToFile(path + file + ".txt");
    }

    public void saveToFile(String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(this.getInfo());
            stats.saveToFile(out);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(SimpleSolver.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * @return the stop
     */
    public StopCriteria getStop() {
        return stopCriteria;
    }

    /**
     * @param stop the stop to set
     */
    public void setStop(StopCriteria stop) {
        this.stopCriteria = stop;
    }

    public double getCurrentEvolutionValue() {
        return stopCriteria.getCurrentValue();
    }

    public double getMaxEvolutionValue() {
        return stopCriteria.getMaxValue();
    }

    public HallOfFame getBest() {
        return getHallOfFame();
    }

    public SimpleSolver getClone() {
        //make a copy of the class
        SimpleSolver solver = (SimpleSolver) DynamicLoad.makeObject(this);
        //set atributs of THIS
        solver.setAll(this);
        return solver;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + title;
    }

    public void updateSolverStats() {
        int oldEvals = parents.getEvaluations();
        parents.evaluate();
        EVALUATIONS += parents.getEvaluations() - oldEvals;
        //update Hall of Fame
        hallOfFame.add(parents.getGoods());
        //update stats
        stats.addSolverToStats(this, (int) stopCriteria.getCurrentValue());
        //update stop criteria
        stopCriteria.updateValue(this);
    }
}