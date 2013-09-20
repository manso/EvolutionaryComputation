/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

/**
 *
 * @author manso
 */
public class MeanSolverStatistic extends SolverStatistic {

    public MeanSolverStatistic(String label) {
        super(label);
        for (int i = 0; i < template.statsElem.size(); i++) {
            dataSet.get(i).setMaximumItemCount(1000000);
        }
    }
}
