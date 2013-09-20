/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import genetic.Solver.SimpleSolver;
import java.util.List;
import javax.swing.JTabbedPane;

/**
 *
 * @author manso
 */
public class MultiPanelStatistics {

    MultiDataSet[] datasets = null;
    /**
     * TabedPane to all statistics
     */
    JTabbedPane tabs = null;

    public MultiPanelStatistics() {
    }

    public JTabbedPane getTabs() {
        return tabs;
    }

    public void setSolvers(List solver) {
        //no solvers
        if (solver == null || solver.isEmpty()) {
            return;
        }
        //tabed pane
        tabs = new JTabbedPane();
        //elements of statistics
        StatisticElements template = ((SimpleSolver) solver.get(0)).getStats().getTemplate();
        //data Sets
        datasets = new MultiDataSet[template.statsElem.size()];
        for (int i = 0; i < template.statsElem.size(); i++) {
            //create dataset
            datasets[i] = new MultiDataSet(solver, i);
            tabs.add(datasets[i].getPanel(), template.statsElem.get(i).getName());
        }

        tabs.revalidate();
    }
    
    
    public void saveStatisticsImages(String path){
        for( MultiDataSet stat:datasets){
            stat.saveImage(path);
        }
    }

   

}
