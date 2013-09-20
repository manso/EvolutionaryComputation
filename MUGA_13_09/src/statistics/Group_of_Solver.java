/*
 * To change this solvers, choose Tools | Templates
 * and open the solvers in the editor.
 */
package statistics;

import GUI.PanelOfStatistics;
import genetic.Solver.SimpleSolver;
import genetic.population.HallOfFame;
import genetic.population.Population;
import genetic.population.UniquePopulation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import problem.Individual;
import statistics.elements.AbstractStatsElement;
import utils.Funcs;
import utils.StaticalLib.Chart.BoxChart;
import utils.StaticalLib.Chart.RankChart;
import utils.StaticalLib.Chart.RankChartMultiple;
import utils.StaticalLib.Distributions.T_Student;
import utils.StaticalLib.RankMeans;

/**
 *
 * @author manso
 */
public class Group_of_Solver implements Runnable {

    Thread autorun = null;
    //templates of solvers
    List<SimpleSolver> templateSolvers = new ArrayList<>();
    //group of solvers
    public ArrayList<Mean_Of_Solver> meanSolvers = new ArrayList<>();
    //simulation saved
    boolean simulationSaved = false;
    boolean active = false;
    String title; //title of simulation
    PanelOfStatistics statsPanel; // to save images of statistics

    public Group_of_Solver(String title, PanelOfStatistics statsPanel) {
        simulationSaved = false;
        active = false;
        this.title = title;
        this.statsPanel = statsPanel;
    }

    public void addSolver(List<SimpleSolver> solvers) {
        for (SimpleSolver s : solvers) {
            templateSolvers.add(s);
        }
    }

    public void setSolvers(SimpleSolver solver) {
        templateSolvers.add(solver);
    }

    private void makeMeanSolvers(int numberOfSolvers) {
        simulationSaved = false;
        //stopSolvers simulation if is running
        stopSolvers();
        //remove all the solvers from group
        meanSolvers.clear();
        for (SimpleSolver s : templateSolvers) {
            Mean_Of_Solver mean = new Mean_Of_Solver(s, numberOfSolvers);
            meanSolvers.add(mean);
        }
    }

    public void synchronizePopulations(int numberOfPopulations) {
        makeMeanSolvers(numberOfPopulations);
        Individual template = meanSolvers.get(0).template;
        int sizePop = meanSolvers.get(0).parents.size();
        synchronizePopulations(template, numberOfPopulations, sizePop);
    }

    public void randomizePopulations(int numberOfPopulations) {
        simulationSaved = false;
        //stopSolvers simulation if is running
        stopSolvers();
        //remove all the solvers from group
        meanSolvers.clear();
        for (SimpleSolver s : templateSolvers) {
            Mean_Of_Solver mean = new Mean_Of_Solver(s, numberOfPopulations);
            mean.setRandomPopulations(s, numberOfPopulations);
            meanSolvers.add(mean);
        }
    }

    private void synchronizePopulations(Individual template, int numberOfPopulations, int sizeOfPopulation) {
        //stopSolvers simulation if is running
        stopSolvers();

        simulationSaved = false;
        //create random populations with unique elements   
        List<Population> newPops = new ArrayList<>(sizeOfPopulation);
        //create populations to solvers
        for (int i = 0; i < numberOfPopulations; i++) {
            UniquePopulation pop = new UniquePopulation();
            pop.createRandomPopulation(sizeOfPopulation, template);
            pop.evaluate();
            newPops.add(pop);
        }
        //update populations of solvers
        for (Mean_Of_Solver meanSolver : meanSolvers) {
            meanSolver.setNewPopulations(newPops);
        }
    }

    public void startSolvers() {
        active = true;
        for (Mean_Of_Solver s : meanSolvers) {
            s.startMeanSolver();
        }
        autorun = new Thread(this);
        autorun.start();
    }

    public void stopSolvers() {
        active = false;
        for (Mean_Of_Solver sv : meanSolvers) {
            sv.stopMeanSolver();
            sv = null;
        }
        autorun = null;
        System.gc();
    }

    public boolean isRunning() {
        for (Mean_Of_Solver sv : meanSolvers) {
            if (!sv.isDone()) {
                return true;
            }
        }
        return false;
    }
    public static int NUM_OF_MINUTES_WAITING_TO_SAVE = 2;

    /**
     * tenta gravar a simulaç?o quando acabar
     */
    @Override
    public void run() {
        try {
            //if user dont stop this and solvers are running
            while (active && isRunning()) {
                Thread.currentThread().sleep(1000);
            }
            //wait 4 seconds to threads done the work
            Thread.currentThread().sleep(4000);

        } catch (InterruptedException ex) {
            Logger.getLogger(Group_of_Solver.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Saving simulation " + this.title);
        saveToFile();

    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------

    public String getHeadrGroup() {
        StringBuilder str = new StringBuilder();
        str.append("SOLVERS,");
        for (Mean_Of_Solver ms : meanSolvers) {
            str.append(ms.title + ",");
        }
        return str.toString();
    }

    public void saveToFile() {
        String path = "./stats/" + Funcs.getNow("yyyy-MM-dd") + "/" + title + "/";
        Funcs.createPath(path);
        try {
            
            String file = Funcs.getNormalizedFileName("RESUME_" + title);
            
            System.out.println("Saving resume " + path + file); 
            
            BufferedWriter out = new BufferedWriter(new FileWriter(path + file + ".csv"));
            saveResume(out);
            saveResumeMeans(out);
            saveResumeStds(out);
            saveHallOfFame(out);
            saveSolversInfo(out);
            saveAllDetails(out);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Group_of_Solver.class.getName()).log(Level.SEVERE, null, ex);
        }
        //save mean solvers
        for (Mean_Of_Solver ms : meanSolvers) {
            System.out.println("Saving Solver Result" + path + ms.title); 
            ms.saveToFile(path + "solvers/");
        }
        //save Ranks
        try {
            System.out.println("Saving Statistics 95% " + path ); 
            saveStatistics(0.95);
        } catch (IOException ex) {
            Logger.getLogger(Group_of_Solver.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Saving Box-plots charts " + path ); 
        saveBoxCharts(path);
        System.out.println("Saving charts " + path ); 
        statsPanel.saveStatisticsImages(path + "charts/");
        System.out.println("Done !");



    }

    private void saveResume(BufferedWriter out) throws IOException {
        StatisticElements statsModel = SolverStatistic.model;
        out.write("\n----------- Resume of simulation --------------------\n");
        out.write("\nSolvers,");
        for (Mean_Of_Solver ms : meanSolvers) {
            out.write(ms.title + ",,");
        }
        out.write("\nStatistics,");
        for (Mean_Of_Solver ms : meanSolvers) {
            out.write("Mean ," + "Std ,");
        }
        for (AbstractStatsElement st : statsModel.statsElem) {
            out.write("\n" + st.getName() + ",");
            for (int gr = 0; gr < meanSolvers.size(); gr++) {
                double val = meanSolvers.get(gr).getStats().getLastElement(st).getValue();
                out.write(val + ",");
                val = meanSolvers.get(gr).getStats().getLastElement(st).getStdDev();
                out.write(val + ",");
            }

        }
    }

    private void saveResumeMeans(BufferedWriter out) throws IOException {
        StatisticElements statsModel = SolverStatistic.model;
        out.write("\n\n------------- Mean --------------------\n");
        out.write("\nSolvers,");
        for (Mean_Of_Solver ms : meanSolvers) {
            out.write(ms.title + ",");
        }
        for (AbstractStatsElement st : statsModel.statsElem) {
            out.write("\n" + st.getName() + ",");
            for (int gr = 0; gr < meanSolvers.size(); gr++) {
                double val = meanSolvers.get(gr).getStats().getLastElement(st).getValue();
                out.write(val + ",");
            }

        }
    }

    private void saveResumeStds(BufferedWriter out) throws IOException {
        StatisticElements statsModel = SolverStatistic.model;
        out.write("\n\n------------- Standard Deviation --------------------\n");
        out.write("\nSolvers,");
        for (Mean_Of_Solver ms : meanSolvers) {
            out.write(ms.title + ",");
        }
        for (AbstractStatsElement st : statsModel.statsElem) {
            out.write("\n" + st.getName() + ",");
            for (int gr = 0; gr < meanSolvers.size(); gr++) {
                double val = meanSolvers.get(gr).getStats().getLastElement(st).getStdDev();
                out.write(val + ",");
            }

        }
    }

    private void saveHallOfFame(BufferedWriter out) throws IOException {
        out.write("\n\n------------- Hall of Fame --------------------\n");
        HallOfFame fame = new HallOfFame();
        for (Mean_Of_Solver ms : meanSolvers) {
            fame.add(ms.hallOfFame);
        }
        Population pop = fame.getGoodPopulation();
        Iterator<Individual> it = pop.getIterator();
        out.write("Is Best,Genotype,FitnessPhenotype\n");
        while (it.hasNext()) {
            Individual i = it.next();
            out.write(i.isBest() + ",");
            out.write("[" + i.getGenome().toString() + "],");
            out.write(i.getFitness() + ",");
            out.write(i.toStringPhenotype() + "\n");
        }
    }

    private void saveSolversInfo(BufferedWriter out) throws IOException {
        out.write("\n\n------------- Solvers Info --------------------\n");
        out.write("Title,Problem,Population Type,Size of Population,Selection,Recombination,Mutation,Reparation,Replacement,Rescaling,Stop Criteria\n");

        for (Mean_Of_Solver ms : meanSolvers) {
            out.write(ms.getTitle().trim() + ",");
            out.write(ms.getTemplate().getName() + ",");
            out.write(ms.solversVector.get(0).getParents().getClass().getSimpleName().trim() + ",");
            out.write(ms.solversVector.get(0).getParents().getNumGenotypes() + ",");
            out.write(ms.getSelect().toString().trim() + ",");
            out.write(ms.getRecombine().toString().trim() + ",");
            out.write(ms.getMutate().toString().trim() + ",");
            out.write(ms.getReparation().toString().trim() + ",");
            out.write(ms.getReplace().toString().trim() + ",");
            out.write(ms.getRescaling().toString().trim() + ",");
            out.write(ms.getStop().toString().trim() + ",");
            out.write("\n");
        }
    }

    private void saveAllDetails(BufferedWriter out) throws IOException {
        StatisticElements statsModel = SolverStatistic.model;
        int numSimuls = meanSolvers.get(0).NUMBER_OF_POPULATIONS;
        out.write("\n------------------------------------- -----------------");
        out.write("\n------------- Details of Simulations-------------------");
        out.write("\n------------Last values of statistics -----------------");
        out.write("\n------------------------------------- -----------------");
        for (AbstractStatsElement st : statsModel.statsElem) {
            out.write("\n-------------------------------------------------------");
            out.write("\n\t\t\t" + st.getName() + "\n");
            out.write("\n-------------------------------------------------------\n");
            out.write(getHeadrGroup() + "\n");
            for (int i = 0; i < numSimuls; i++) {
                out.write("Simulation " + i + ",");
                for (int gr = 0; gr < meanSolvers.size(); gr++) {
                    double val = meanSolvers.get(gr).solversVector.get(i).getStats().getLastStatValue(st);
                    out.write(val + ",");
                }
                out.write("\n");
            }
            out.write("\nMean,");
            for (int gr = 0; gr < meanSolvers.size(); gr++) {
                double val = meanSolvers.get(gr).getStats().getLastElement(st).getValue();
                out.write(val + ",");
            }
            out.write("\nStd,");
            for (int gr = 0; gr < meanSolvers.size(); gr++) {
                double val = meanSolvers.get(gr).getStats().getLastElement(st).getStdDev();
                out.write(val + ",");
            }
            out.write("\n-------------------------------------------------------");
        }
    }
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    private void saveStatistics(double significance) throws IOException {
        String path = "./stats/" + Funcs.getNow("yyyy-MM-dd") + "/" + title + "/";
        Funcs.createPath(path);
        String file = Funcs.getNormalizedFileName("Statistics_" + title + "_" + (significance * 100));
        try (BufferedWriter out = new BufferedWriter(new FileWriter(path + file + ".csv"))) {
            saveCompareMeans(out, path, significance);
        }
    }

    private String algorithmsHead(String[] alg) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < alg.length; i++) {
            str.append(alg[i] + ",");
        }
        return str.toString();
    }

    private void saveCompareMeans(BufferedWriter out, String path, double significance) throws IOException {
        StatisticElements statsModel = SolverStatistic.model;
        int numSimuls = meanSolvers.get(0).NUMBER_OF_POPULATIONS;

        //Label of statistics
        int sizeOfStatistics = statsModel.statsElem.size();
        String statsLabel[] = new String[sizeOfStatistics];
        for (int i = 0; i < statsLabel.length; i++) {
            statsLabel[i] = statsModel.statsElem.get(i).getName();

        }
        //label of algorithms
        int sizeOfAlgorithms = meanSolvers.size();
        String algoLabel[] = new String[sizeOfAlgorithms];
        for (int i = 0; i < sizeOfAlgorithms; i++) {
            algoLabel[i] = meanSolvers.get(i).getTitle();
        }
        //data of simulations
        double[][] data = new double[sizeOfAlgorithms][numSimuls];
        //ranks of simulations
        int[][] rank = new int[sizeOfStatistics][sizeOfAlgorithms];
        //Problem
        Individual problem = meanSolvers.get(0).getTemplate();

        out.write("\n-------------------------------------------------------");
        out.write("\nProblem ," + problem.getName() + " ," + problem.getParameters());
        out.write("\nSignificance ," + (significance * 100) + " %");
        out.write("\np_value ," + T_Student.twoTailed(significance, numSimuls - 1));
        out.write("\n-------------------------------------------------------");

        String algHead = algorithmsHead(algoLabel);

        for (int k = 0; k < sizeOfStatistics; k++) {
            AbstractStatsElement st = statsModel.statsElem.get(k);
            out.write("\n\n-------------------------------------------------------");
            //calculate data
            out.write("\nStatistic," + st.getName() + "\nDATA,");
            for (int i = 0; i < numSimuls; i++) {
                out.write((i + 1) + ",");
            }
            for (int gr = 0; gr < meanSolvers.size(); gr++) {
                out.write("\n" + algoLabel[gr] + ",");
                for (int i = 0; i < numSimuls; i++) {
                    double val = meanSolvers.get(gr).solversVector.get(i).getStats().getLastStatValue(st);
                    data[gr][i] = val;
                    out.write(data[gr][i] + ",");
                }
            }
            //calculate comparations
            int[][] mCompare = RankMeans.Compare(data, significance, st.isMaximumBetter());
            //calculate Rank
            rank[k] = RankMeans.rank(data, significance, st.isMaximumBetter());
            //calculate p-values
            double[][] p_values = RankMeans.p_values(data, significance, st.isMaximumBetter());
            //-------------------------------------------------------------------------
            //----------------------  Write information -----------------------
            //-------------------------------------------------------------------------
            out.write("\n\nComparison of means\n");
            //separated by 3 columns
            out.write(" ," + algHead + ",,," + algHead + ",,," + algHead);
            for (int i = 0; i < sizeOfAlgorithms; i++) {

                out.write("\n" + algoLabel[i] + ",");
                for (int j = 0; j < sizeOfAlgorithms; j++) {
                    out.write(mCompare[i][j] + ",");
                }
                //separated by 3 columns
                out.write(",," + algoLabel[i] + ",");
                for (int j = 0; j < sizeOfAlgorithms; j++) {
                    if (mCompare[i][j] < 0) {
                        out.write("-,");
                    } else if (mCompare[i][j] > 0) {
                        out.write("+,");
                    } else {
                        out.write("=,");
                    }
                }
                //separated by 3 columns
                out.write(",," + algoLabel[i] + ",");
                for (int j = 0; j < sizeOfAlgorithms; j++) {
                    out.write(p_values[i][j] + ",");
                }
            }
            //-------------------------------------------------------------------------
            //save ranks
            out.write("\nRANK,");
            for (int j = 0; j < sizeOfAlgorithms; j++) {
                out.write(rank[k][j] + ",");
            }

            String fileName = "Rank " + problem.getName() + " " + st.getName() + " (" + (significance * 100) + "%)";
            RankChart.saveRankChart(rank[k], algoLabel, fileName, path);
        }
        //-------------------------------------------------------------------------
        //save all Ranks
        //-------------------------------------------------------------------------
        out.write("\n\n\nResume of Ranks");
        out.write("\n," + algHead);
        for (int i = 0; i < rank.length; i++) {
            out.write("\n" + statsLabel[i] + ",");
            for (int j = 0; j < rank[i].length; j++) {
                out.write(rank[i][j] + ",");
            }
        }
        String fileName = "Rank " + problem.getName() + " (" + (significance * 100) + "%)";
        RankChartMultiple.saveRankChart(rank, algoLabel, statsLabel, fileName, path);
    }
        
    //---------------------------------------------------------------------------------------------------------------

    private void saveBoxCharts(String path) {
        StatisticElements statsModel = SolverStatistic.model;
        int numSimuls = meanSolvers.get(0).NUMBER_OF_POPULATIONS;

        //Label of statistics
        int sizeOfStatistics = statsModel.statsElem.size();
        String statsLabel[] = new String[sizeOfStatistics];
        for (int i = 0; i < statsLabel.length; i++) {
            statsLabel[i] = statsModel.statsElem.get(i).getName();

        }
        //label of algorithms
        int sizeOfAlgorithms = meanSolvers.size();
        String algoLabel[] = new String[sizeOfAlgorithms];
        for (int i = 0; i < sizeOfAlgorithms; i++) {
            algoLabel[i] = meanSolvers.get(i).getTitle();
        }
        //data of simulations
        double[][][] data = new double[sizeOfStatistics][sizeOfAlgorithms][numSimuls];

        Individual problem = meanSolvers.get(0).getTemplate();


        for (int k = 0; k < sizeOfStatistics; k++) {
            AbstractStatsElement st = statsModel.statsElem.get(k);
            for (int gr = 0; gr < meanSolvers.size(); gr++) {
                for (int i = 0; i < numSimuls; i++) {
                    double val = meanSolvers.get(gr).solversVector.get(i).getStats().getLastStatValue(st);
                    data[k][gr][i] = val;
                }
            }
            //calculate comparations         
            String fileName = problem.getName() + "_" + st.getName();
            BoxChart.saveChart(data[k], algoLabel, fileName, path);
        }

    }
}
