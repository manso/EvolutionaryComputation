/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import genetic.Solver.SimpleSolver;
import java.io.BufferedWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;
import org.jfree.data.xy.XYSeries;
import statistics.elements.AbstractStatsElement;
import statistics.elements.SucessRate;
import statistics.elements.BestFoundEvaluations;
import statistics.elements.BestValueFound;
import statistics.elements.NumberOfBestInHallOfFame;
import utils.Funcs;

/**
 *
 * @author manso
 */
public class SolverStatistic implements Serializable {
    //value of statistic elements

    public Vector<StatisticElements> statsElem = new Vector<StatisticElements>();
    //dataset to graphics
    public Vector<XYSeries> dataSet = new Vector<XYSeries>();
    protected String title = "No Name";
    protected StatisticElements template = new StatisticElements();
    // to inherance
    //--------------------------------------------------------------------
    //--------------       S T A T I C              ----------------------
    //--------------       M E M B E R S            ----------------------
    //--------------------------------------------------------------------
    //EPIA 
    public static int sliding = 1500;
    //model to statistics
    public static StatisticElements model = new StatisticElements();

    static {

        model.addStatistic(new SucessRate());
        model.addStatistic(new BestFoundEvaluations());
//        model.addStatistic(new NumberOfCopiesOfBest());
//        model.addStatistic(new RunningTime());
//        model.addStatistic(new NumberOfEvaluations());
//        model.addStatistic(new GeneticDiversity());
//        model.addStatistic(new GeneticDiversityMinDistance());
//        model.addStatistic(new CeilingFactor());
//        model.addStatistic(new NumberOfIndividuals());
//        model.addStatistic(new GeneticDiversityRealCoded());
//        model.addStatistic(new GeneticDiversityInertia());
//        model.addStatistic(new RelativeStandardDeviation());       
        model.addStatistic(new BestValueFound());
        model.addStatistic(new NumberOfBestInHallOfFame());
    }
    //EPIA

    public String getInfo() {
        StringBuilder str = new StringBuilder();
        for (AbstractStatsElement stat : model.statsElem) {
            str.append("[").append(stat.getName()).append("] ");
        }
        return str.toString();
    }

    public String getLastValues() {
        if (statsElem.isEmpty()) {
            return "EMPTY";
        }

        StringBuilder str = new StringBuilder();
        StatisticElements last = statsElem.get(statsElem.size() - 1);
        for (AbstractStatsElement stat : last.statsElem) {
            str.append("\n").append(Funcs.SetStringSize(stat.getName(), 20));
            str.append(" ");
            str.append(Funcs.DoubleToString(stat.getValue(), 20));
        }
        return str.toString();
    }

    public String getLastValues_CSV() {
        if (statsElem.isEmpty()) {
            return "EMPTY";
        }

        StringBuilder str = new StringBuilder();
        StatisticElements last = statsElem.get(statsElem.size() - 1);
        for (AbstractStatsElement stat : last.statsElem) {
            str.append(stat.getValue());
            str.append(" ,");
        }
        return str.toString();
    }

    public String getLastSTD_CSV() {
        if (statsElem.isEmpty()) {
            return "EMPTY";
        }

        StringBuilder str = new StringBuilder();
        StatisticElements last = statsElem.get(statsElem.size() - 1);
        for (AbstractStatsElement stat : last.statsElem) {
            str.append(stat.getStdDev());
            str.append(" ,");
        }
        return str.toString();
    }

    public String getHeaderCSV() {
        if (statsElem.isEmpty()) {
            return "EMPTY";
        }

        StringBuilder str = new StringBuilder();
        StatisticElements last = statsElem.get(statsElem.size() - 1);
        for (AbstractStatsElement stat : last.statsElem) {
            str.append(Funcs.SetStringSize(stat.getName(), 30));
            str.append(" ,");
        }
        return str.toString();
    }

    protected SolverStatistic() {
    }

    public SolverStatistic(String label) {
        title = label;

        for (AbstractStatsElement stat : model.statsElem) {
            template.addStatistic(stat.getClone());
        }

        for (int i = 0; i < template.statsElem.size(); i++) {
            //serie de dados

            dataSet.add(new XYSeries(label));
            //label
            dataSet.get(i).setDescription(template.statsElem.get(i).getName());
            ////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
            if (sliding > 0) {
                dataSet.get(i).setMaximumItemCount(sliding);
            }
            ////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////
        }
    }

    public void restart() {
        for (StatisticElements elem : statsElem) {
            elem.clear();
        }
    }

    public void addSolverToStats(SimpleSolver s, double generation) {
        StatisticElements genStat = template.makeStats(s, generation);
        statsElem.add(genStat);

        for (int i = 0; i < template.statsElem.size(); i++) {
            dataSet.get(i).add(generation, genStat.getValue(i));
        }
    }
    ReentrantLock lock = new ReentrantLock();

    public synchronized void addAverageSolversToStats(ArrayList<SimpleSolver> solver, double evolutionValue) {
        // no solvers or populations empty
        if (solver.isEmpty() || solver.get(0).getParents().getNumGenotypes() == 0) {
            return;
        }
        //make the average stats of solver
        StatisticElements avgStat = template.makeAverageStats(solver, evolutionValue);

        statsElem.add(avgStat);
        for (int i = 0; i < template.statsElem.size(); i++) {
            dataSet.get(i).add(avgStat.iterationValue, avgStat.getValue(i));
        }
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (int statElemnt = 0; statElemnt < template.statsElem.size(); statElemnt++) {

            //serie de dados
            XYSeries stat = dataSet.get(statElemnt);
            //series name
            buf.append("\n" + utils.Funcs.SetStringSize(template.statsElem.get(statElemnt).getName() + " Mean ", 2 * SIZETOFILE) + " \t");
            //series data
            for (int generation = 0; generation < stat.getItemCount(); generation++) {
                StatisticElements vstats = statsElem.get(generation);
                //buf.append(utils.Funcs.DoubleToString(stat.getDataItem(i).getYValue(), SIZETOFILE) + " \t");
                buf.append(utils.Funcs.DoubleExponentialToString(vstats.getValue(statElemnt), SIZETOFILE) + " \t");
            }
            //series name
            buf.append("\n" + utils.Funcs.SetStringSize(template.statsElem.get(statElemnt).getName() + " StDv", 2 * SIZETOFILE) + " \t");
            //series data
            for (int generation = 0; generation < stat.getItemCount(); generation++) {
                StatisticElements vstats = statsElem.get(generation);
                //buf.append(utils.Funcs.DoubleToString(stat.getDataItem(i).getYValue(), SIZETOFILE) + " \t");
                buf.append(utils.Funcs.DoubleExponentialToString(vstats.getStdDev(statElemnt), SIZETOFILE) + " \t");
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    public String toStringValue(int statElemnt) {
        StringBuilder buf = new StringBuilder();
        //series name
        buf.append(utils.Funcs.SetStringSize(template.statsElem.get(statElemnt).getName(), 2 * SIZETOFILE) + ",");
        //series data
        for (int generation = 0; generation < statsElem.size(); generation++) {
            StatisticElements vstats = statsElem.get(generation);
            buf.append(utils.Funcs.DoubleExponentialToString(vstats.getValue(statElemnt), SIZETOFILE) + ",");
        }

        return buf.toString();
    }

    public String toMeanString(String criteria, int elem, int numSamples) {
        StringBuilder txt = new StringBuilder();
        txt.append(toStringHeader(criteria, numSamples));
        txt.append("\n" + toStringValue(elem, numSamples));
        txt.append("\n" + toStringStdDev(elem, numSamples));
        txt.append("\n" + toStringMin(elem, numSamples));
        txt.append("\n" + toStringMax(elem, numSamples));
        return txt.toString();

    }

    public String toStringHeader(String criteria, int numOfSamples) {
        int step = (int) Math.ceil(statsElem.size() / (double) numOfSamples);
        StringBuilder buf = new StringBuilder();
        //series name
        buf.append(utils.Funcs.SetStringSize(criteria, 2 * SIZETOFILE) + ",");
        //series data
        for (int generation = 0; generation < statsElem.size(); generation += step) {
            buf.append(utils.Funcs.Double_or_INT_ToString(statsElem.get(generation).iterationValue, SIZETOFILE) + ",");
        }

        return buf.toString();
    }

    public String toStringValue(int statElemnt, int numOfSamples) {
        int step = (int) Math.ceil(statsElem.size() / (double) numOfSamples);
        StringBuilder buf = new StringBuilder();
        //series name
        buf.append(utils.Funcs.SetStringSize("Avg " + template.statsElem.get(statElemnt).getName(), 2 * SIZETOFILE) + ",");
        //series data
        for (int generation = 0; generation < statsElem.size(); generation += step) {
            StatisticElements vstats = statsElem.get(generation);
            buf.append(utils.Funcs.DoubleExponentialToString(vstats.getValue(statElemnt), SIZETOFILE) + ",");
        }

        return buf.toString();
    }

    public String toStringStdDev(int statElemnt, int numOfSamples) {
        int step = (int) Math.ceil(statsElem.size() / (double) numOfSamples);
        StringBuilder buf = new StringBuilder();
        //series name
        buf.append(utils.Funcs.SetStringSize("Std " + template.statsElem.get(statElemnt).getName(), 2 * SIZETOFILE) + ",");
        //series data
        for (int generation = 0; generation < statsElem.size(); generation += step) {
            StatisticElements vstats = statsElem.get(generation);
            buf.append(utils.Funcs.DoubleExponentialToString(vstats.getStdDev(statElemnt), SIZETOFILE) + ",");
        }
        return buf.toString();
    }

    public String toStringMin(int statElemnt, int numOfSamples) {
        int step = (int) Math.ceil(statsElem.size() / (double) numOfSamples);
        StringBuilder buf = new StringBuilder();
        //series name
        buf.append(utils.Funcs.SetStringSize("Min " + template.statsElem.get(statElemnt).getName(), 2 * SIZETOFILE) + ",");
        //series data
        for (int generation = 0; generation < statsElem.size(); generation += step) {
            StatisticElements vstats = statsElem.get(generation);
            buf.append(utils.Funcs.DoubleExponentialToString(vstats.getMin(statElemnt), SIZETOFILE) + ",");
        }
        return buf.toString();
    }

    public String toStringMax(int statElemnt, int numOfSamples) {
        int step = (int) Math.ceil(statsElem.size() / (double) numOfSamples);
        StringBuilder buf = new StringBuilder();
        //series name
        buf.append(utils.Funcs.SetStringSize("Max " + template.statsElem.get(statElemnt).getName(), 2 * SIZETOFILE) + ",");
        //series data
        for (int generation = 0; generation < statsElem.size(); generation += step) {
            StatisticElements vstats = statsElem.get(generation);
            buf.append(utils.Funcs.DoubleExponentialToString(vstats.getMax(statElemnt), SIZETOFILE) + ",");
        }
        return buf.toString();
    }

    public String toStringStdDev(int statElemnt) {
        StringBuffer buf = new StringBuffer();
        //serie de dados
        XYSeries stat = dataSet.get(statElemnt);
        //series name
        buf.append(utils.Funcs.SetStringSize("Standard Deviation", 2 * SIZETOFILE) + " \t");
        //series data
        for (int generation = 0; generation < stat.getItemCount(); generation++) {
            StatisticElements vstats = statsElem.get(generation);
            //buf.append(utils.Funcs.DoubleToString(stat.getDataItem(i).getYValue(), SIZETOFILE) + " \t");
            buf.append(utils.Funcs.DoubleExponentialToString(vstats.getStdDev(statElemnt), SIZETOFILE) + " \t");
        }
        return buf.toString();
    }
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    //---------------------------------------------------------------------------
    public static int SIZETOFILE = 20;

    public void appendToFileStat(BufferedWriter out) throws IOException {
        out.write(toString());
        out.write("\n");
    }

    public void appendToFileHeader(BufferedWriter out) throws IOException {
        //out.write(Funcs.SetStringSize(title, SIZETOFILE));
        out.write(Funcs.SetStringSize("\nIteration", 2 * SIZETOFILE) + " \t");
        //series data
        for (int i = 0; i < dataSet.get(0).getItemCount(); i++) {
            out.write(utils.Funcs.IntegerToString((int) dataSet.get(0).getDataItem(i).getXValue(), SIZETOFILE) + " \t");
        }
    }

    public void saveToFile(BufferedWriter out) throws IOException {
        appendToFileHeader(out);
        appendToFileStat(out);
    }

    public void saveToFile(String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            saveToFile(out);
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(SolverStatistic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StatisticElements getTemplate() {
        // template = new StatisticElements();
        return template;
    }

    public XYSeries getDataSet(int index) {
        return dataSet.get(index);
    }

    public SolverStatistic getClone() {
        SolverStatistic clone = new SolverStatistic("XXX");
        clone.dataSet.clear();
        for (int i = 0; i < dataSet.size(); i++) {
            try {
                clone.dataSet.add((XYSeries) this.getDataSet(i).clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(SolverStatistic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return clone;
    }

    public double getStatValue(int index, AbstractStatsElement type) {
        for (int i = 0; i < template.statsElem.size(); i++) {
            if (type.getClass().isInstance(template.statsElem.get(i))) {
                if (dataSet.get(i).getItemCount() <= index) {
                    return dataSet.get(i).getDataItem(index).getYValue();
                }
            }
        }
        return 0;
    }

    public double getLastStatValue(AbstractStatsElement type) {
        for (int i = 0; i < template.statsElem.size(); i++) {
            if (type.getClass().isInstance(template.statsElem.get(i))) {
                if (dataSet.get(i).getItemCount() > 0) {
                    return dataSet.get(i).getDataItem(dataSet.get(i).getItemCount() - 1).getYValue();
                }
            }
        }
        return 0;
    }

    //last element of stratistics
    public AbstractStatsElement getLastElement(int index) {
        return statsElem.get(statsElem.size() - 1).statsElem.get(index);
    }

    public AbstractStatsElement getLastElement(AbstractStatsElement type) {
        if (statsElem.size() == 0) {
            return null;
        }
        for (int i = 0; i < template.statsElem.size(); i++) {
            if (type.getClass().isInstance(template.statsElem.get(i))) {
                return statsElem.get(statsElem.size() - 1).statsElem.get(i);
            }
        }
        return null;
    }

    public AbstractStatsElement getFirstElement(AbstractStatsElement type) {
        if (statsElem.size() == 0) {
            return null;
        }
        for (int i = 0; i < template.statsElem.size(); i++) {
            if (type.getClass().isInstance(template.statsElem.get(i))) {
                return statsElem.get(0).statsElem.get(i);
            }
        }
        return null;
    }
}
