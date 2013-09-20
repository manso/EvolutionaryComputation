/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import genetic.Solver.SimpleSolver;
import genetic.population.HallOfFame;
import genetic.population.Population;
import genetic.population.UniquePopulation;
import genetic.stopCriteria.StopCriteria;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Funcs;

/**
 *
 * @author manso
 */
public class Mean_Of_Solver extends SimpleSolver implements Runnable {

    public int NUMBER_OF_POPULATIONS = 1;
    //number of statical elements saved in file
    public static int NUMBER_OF_STATS_SAVED = 100;
    //thread running
    public boolean running = false;
    //fast iterations
    public boolean fastIteration = false;

    @Override
    public String getAlgorithm() {
        StringBuilder str = new StringBuilder(getClass().getSimpleName() + "<" + NUMBER_OF_POPULATIONS + ">");
        str.append("\n Mean of Solver:\n");
        return str.toString();
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                NUMBER_OF_POPULATIONS = Integer.parseInt(iter.nextToken());
                if (NUMBER_OF_POPULATIONS <= 0) {
                    NUMBER_OF_POPULATIONS = 1;
                }
            } catch (Exception e) {
                NUMBER_OF_POPULATIONS = 1;
            }
        }
        setRandomPopulations(this, NUMBER_OF_POPULATIONS);
    }

    public String getParameters() {
        return NUMBER_OF_POPULATIONS + "";
    }
    //vector of solvers to make the average
    public ArrayList<SimpleSolver> solversVector;

    public Mean_Of_Solver() {
        super();
//        setRandomPopulations(this, NUMBER_OF_POPULATIONS);
//        stats.addAverageSolversToStats(solversVector, 0);
    }

    public Mean_Of_Solver(SimpleSolver template, int solvers) {
        super(template);
        template.restartSolver();
        //create Solvers
        solversVector = new ArrayList<>(solvers);
        template.restartSolver();
        for (int i = 0; i < solvers; i++) {
            solversVector.add(template.getClone());
        }
        //add random populations
        setRandomPopulations(template, solvers);
        stats.addAverageSolversToStats(solversVector, 0);
    }

    /**
     * Create a solversVector that constains the average of many solvers
     *
     * @param s - template solversVector
     * @param numberOfSolvers - numbers of solvers to average
     */
    public void setRandomPopulations(SimpleSolver template, int numberOfSolvers) {
        //create random populations with unique elements   
        List<Population> newPops = new ArrayList<>(numberOfSolvers);
        //create populations to solvers
        for (int i = 0; i < numberOfSolvers; i++) {
            parents.restart();
            newPops.add(parents.getClone());
        }
        setNewPopulations(newPops);
    }

    /**
     * apply the stop Criteriaparameter to allsolvers
     *
     * @param stop Stop criteria
     */
    public void setStopCriteria(StopCriteria stop) {
        for (SimpleSolver s : solversVector) {
            s.setStop(stop.getClone());
        }
    }

    @Override
    public Population evolve(Population original) {
        //mean stop criteria of solver
        double progress = 0;
        int numEvls = 0;
        int numGens = 0;
        parents = new UniquePopulation();
        for (int i = 0; i < solversVector.size(); i++) {
            SimpleSolver solver = solversVector.get(i);
            if (!solver.stopCriteria.isDone(solver)) {
                solver.iterate();
            }
            progress += solver.stopCriteria.getCurrentValue();
            numEvls += solver.EVALUATIONS;
            numGens += solver.GENERATION;
            //append best individuals
            this.parents.appendPopulation(solver.hallOfFame.getGoodPopulation());
        }
        EVALUATIONS = (int) (numEvls / solversVector.size());
        GENERATION = (int) (numGens / solversVector.size());
        stopCriteria.setCurrentValue(progress / solversVector.size());
        hallOfFame.add(parents.getGoods());
        stats.addAverageSolversToStats(solversVector, stopCriteria.getCurrentValue());
        return parents;
    }

    @Override
    public void iterate() {
        parents = evolve(parents);
    }
    //-------------------------------------------------------------------------

    /**
     * Reset solvers with new populations
     *
     * @param pops
     */
    public void setNewPopulations(List<Population> pops) {
        //create new solvers
        NUMBER_OF_POPULATIONS = pops.size();
//        solversVector = new ArrayList<>();
        //create new statistics
        stats = new MeanSolverStatistic(title);
        //add solvers to array
        for (int index = 0; index < NUMBER_OF_POPULATIONS; index++) {
            SimpleSolver solver = solversVector.get(index);
            //set population
            solver.setIndividualsToPopulation(pops.get(index).getClone());
            solver.resetStats();
        }
        //reset stop criteria
        stopCriteria.reset();
        //update stats
        stats.addAverageSolversToStats(solversVector, stopCriteria.getCurrentValue());
    }

    //-------------------------------------------------------------------------
    public void saveToFile() {
        String path = "./stats/" + Funcs.getNow("yyyy-MM-dd") + "/" + title + "/";
        saveToFile(path);
    }

    public void saveToFile(String path) {
        try {
            Funcs.createPath(path);
            String file = Funcs.getNormalizedFileName(title + "_" + template.getName());
            BufferedWriter out = new BufferedWriter(new FileWriter(path + file + ".csv"));
            saveDataToFile(out);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(SolverStatistic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveDataToFile(BufferedWriter out) {
        double STEP = getStop().getMaxValue() / NUMBER_OF_STATS_SAVED;
        try {
            out.write("\n-------------------------------------------------------\n");
            out.write(title);
            out.write("\n-------------------------------------------------------\n");
            //write values os simulation
            out.write("Solver/Statitic \t,");
            out.write(stats.getHeaderCSV());
            for (int i = 0; i < solversVector.size(); i++) {
                out.write("\n Simulation " + i + "\t,");
                out.write(solversVector.get(i).stats.getLastValues_CSV());
            }
            out.write("\n Mean ," + getStats().getLastValues_CSV());
            out.write("\n Std ," + getStats().getLastSTD_CSV());
            out.write("\n-------------------------------------------------------\n");
            StatisticElements template = this.getStats().getTemplate();
            for (int index = 0; index < template.statsElem.size(); index++) {
                out.write("\n\n" + getStats().toMeanString(stopCriteria.getName(), index, NUMBER_OF_STATS_SAVED));
            }
            HallOfFame best = new HallOfFame();
            for (int solver = 0; solver < solversVector.size(); solver++) {
                best.add(solversVector.get(solver).hallOfFame);
            }
            out.write("\n\n*********************************************************\n");
            out.write("-----------------HALL OF FAME --------------------------\n");
            out.write(best.toString());
            out.write("\n-----------------HALL OF FAME --------------------------\n");
            out.write("\nSize of Populations :" + solversVector.get(0).getParents().getNumGenotypes());
            out.write("\n" + this.getInfo());
        } catch (IOException ex) {
            Logger.getLogger(SolverStatistic.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void startMeanSolver() {
        Thread thr = new Thread(this);
        thr.start();
    }

    public void stopMeanSolver() {
        running = false;
    }

    @Override
    public boolean isDone() {
        for (int i = 0; i < solversVector.size(); i++) {
            if (!solversVector.get(i).isDone()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() {
        running = true;
        while (!isDone() && running) {
            iterate();
        }
        stopMeanSolver();
        running = false;
    }

    @Override
    public SimpleSolver getClone() {
        //make a copy of the class
        Mean_Of_Solver solver = new Mean_Of_Solver(this, NUMBER_OF_POPULATIONS);
        return solver;
    }
}
