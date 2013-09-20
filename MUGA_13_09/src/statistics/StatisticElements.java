/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import genetic.Solver.SimpleSolver;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
import statistics.elements.AbstractStatsElement;
import utils.DynamicLoad;
import utils.Funcs;


/**
 *
 * @author manso
 */
public class StatisticElements implements Serializable {

    public Vector<AbstractStatsElement> statsElem = new Vector<AbstractStatsElement>();
    double iterationValue = 0;

    public void addStatistic(AbstractStatsElement elem) {
        getStatsElem().add(elem);
    }

    public StatisticElements makeStats(SimpleSolver s, double generation) {
        StatisticElements newStat = new StatisticElements();
        newStat.iterationValue = generation;
        for (AbstractStatsElement stat : statsElem) {
            //clone of the template
            AbstractStatsElement elem = stat.getClone();
            //calculate element
            elem.execute(s);

            newStat.getStatsElem().add(elem);
        }
        return newStat;
    }

    /**
     * Calculate the average of the elements of the vector
     *
     * @param vSolver vector of solvers
     * @return statistic element with average of solver
     */
    public StatisticElements makeAverageStats(ArrayList<SimpleSolver> vSolver, double evolutionValue) {
        // no solvers
        if (vSolver.isEmpty()) {
            return null;
        }
        //new average element
        StatisticElements averageStat = new StatisticElements();
        //----------------------------------------------------------------------       
        averageStat.iterationValue = evolutionValue;
        //----------------------------------------------------------------------
        // for each statistics
        for (AbstractStatsElement stat : statsElem) {
            //make the statistics element
            AbstractStatsElement elem = stat.getClone();
            //---------------------------  calculate average -------------------
            double sum = 0.0;
            double min = Double.POSITIVE_INFINITY;
            double max = Double.NEGATIVE_INFINITY;
            double value;
            //for each solver
            for (SimpleSolver solver : vSolver) {
                value = elem.execute(solver);
                sum += value;
                if (value > max) {
                    max = value;
                }
                if (value < min) {
                    min = value;
                }
            }
            double avg = sum / vSolver.size();

            //----------------------------   calculate Standard deviation  -----
            //calculate average
            sum = 0.0;
            //for each solver
            for (SimpleSolver solver : vSolver) {
                sum += Math.pow(elem.execute(solver) - avg, 2);
            }
            double std = Math.sqrt((1.0 / (vSolver.size())) * sum);

            //---- put values in element ------
            elem.setValue(avg);
            elem.setStdDev(std);
            elem.setMin(min);
            elem.setMax(max);            
            //------------------------------------------------------------------

            averageStat.getStatsElem().add(elem);
        }
        return averageStat;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[" + Funcs.DoubleToString(iterationValue, 4) + "]\t");
        for (AbstractStatsElement stat : getStatsElem()) {
            buf.append(stat.getName() + " : " + Funcs.DoubleToString(stat.getValue(), 10) + " \t");
        }
        return buf.toString();
    }

    /**
     * @return the statsElem
     */
    public Vector<AbstractStatsElement> getStatsElem() {
        return statsElem;
    }

    public double getValue(int index) {
        return statsElem.get(index).getValue();
    }

    public AbstractStatsElement getLast() {
        return statsElem.get(statsElem.size() - 1);
    }

    public double getStdDev(int index) {
        return statsElem.get(index).getStdDev();
    }
    public double getMin(int index) {
        return statsElem.get(index).getMin();
    }
    public double getMax(int index) {
        return statsElem.get(index).getMax();
    }

    public String getName(int index) {
        return statsElem.get(index).getName();
    }

    //EPIA
    public boolean contains(String elem) {
        for (AbstractStatsElement stat : statsElem) {
            if (elem.equalsIgnoreCase(stat.getClass().getName())) {
                return true;
            }
        }
        return false;
    }
    //----------------------------------------------------------------------

    public void clear() {
        statsElem.clear();
    }
//----------------------------------------------------------------------

    public void add(String stat) {
        AbstractStatsElement elem = (AbstractStatsElement) DynamicLoad.makeObject("statistics.elements." + stat);
        statsElem.add(elem);
    }
//----------------------------------------------------------------------
    //EPIA
}
