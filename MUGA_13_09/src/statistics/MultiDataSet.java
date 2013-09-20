/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import genetic.Solver.SimpleSolver;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import utils.Funcs;

/**
 * DataSet to display one statistic to multiple solvers
 *
 * @author manso
 */
public class MultiDataSet {

    static float width = 3.0f;
    static float[][] pattern = {{10.0f}, {10.0f, 10.0f}, {5.0f, 5.0f}, {2.0f, 2.0f}};
    static Stroke[] linePattern = new Stroke[]{
        new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 15.0f), // solid line
    //            new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, pattern[1], 0.0f),
    //            new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, pattern[2], 0.0f),
    //            new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, pattern[3], 0.0f),
    };
    public static Color[] color = {
        new Color(255, 50, 50),
        new Color(75, 75, 255),
        new Color(0, 200, 0),
        Color.MAGENTA,
        new Color(0, 200, 200),
        Color.ORANGE, Color.YELLOW, Color.PINK};
    //series to displaying graph
    private XYSeries[] data;
    //chart to diplay series
    JFreeChart chart = null;
    //series collection
    private XYSeriesCollection dataset = new XYSeriesCollection();
    //Panel of charts
    private JPanel panel = null;
    double MAX = 0;

    public MultiDataSet(List ga, int index) {
        if (ga != null && !ga.isEmpty()) {
            //alocate array
            data = new XYSeries[ga.size()];

            SimpleSolver s = (SimpleSolver) ga.get(0);
            //create chart and panel            
            chart = createChart(s.getTemplate().getName(),
                    s.getStop().getName());
            panel = new ChartPanel(chart);
            //link the datasets
            setDataSets(ga, index);
        }
    }

    private void formatChart(String yAxisLabel, int size) {
        XYPlot plot = (XYPlot) chart.getXYPlot();
        plot.getDomainAxis().setAutoRange(true);
        plot.getRangeAxis().setAutoRange(true);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setAutoRange(true);
        //label of the y axis
        yAxis.setLabel(yAxisLabel);
        //zeros
//            yAxis.setAutoRangeIncludesZero(false);
        // set the plot's axes to display integers
        TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setStandardTickUnits(ticks);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        for (int i = 0; i < size; i++) {
            renderer.setSeriesStroke(i, linePattern[i % linePattern.length]);
            renderer.setSeriesPaint(i, color[i % color.length]);
            renderer.setSeriesVisible(i, Boolean.TRUE, true);
        }
        renderer.setDrawSeriesLineAsPath(true);
        plot.setRenderer(renderer);
    }

    public void fireDataChangedEvent() {
        for (XYSeries serie : data) {
            serie.fireSeriesChanged();
        }
    }

    /**
     * link the datasets to statistics of the GA Solvers
     *
     * @param ga - Vector of solvers
     * @param index - index of statistic
     */
    public void setDataSets(List ga, int index) {
        dataset.removeAllSeries();

        for (int i = 0; i < ga.size(); i++) {
            try {
                //attrib the dataset of the solver
//                data[i] = (XYSeries) ((Solver) ga.get(i)).getStats().getDataSet(index).clone();
                data[i] = ((SimpleSolver) ga.get(i)).getStats().getDataSet(index);
                data[i].setDescription(((SimpleSolver) ga.get(i)).getTitle());
                //label for the dataSet
                dataset.addSeries(data[i]);
                dataset.getSeries(i).setNotify(true);
                //update                
            } catch (Exception ex) {
                Logger.getLogger(MultiDataSet.class.getName()).log(Level.SEVERE, null, ex);
            }
            //add data

        }
        dataset.setAutoWidth(true);

        //title of y Axis
        formatChart(((SimpleSolver) ga.get(0)).getStats().getTemplate().getName(index), ga.size());
    }

    private JFreeChart createChart(String title, String xAxisTitle) {
        chart = ChartFactory.createXYLineChart(title, xAxisTitle, title, dataset,
                PlotOrientation.VERTICAL, true, false, false);
        XYPlot plot = (XYPlot) chart.getXYPlot();
        plot.getDomainAxis().setAutoRange(true);
        plot.getRangeAxis().setAutoRange(true);
        plot.setBackgroundPaint(new Color(220, 220, 220));
        plot.setDomainGridlinePaint(Color.DARK_GRAY);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);
        plot.configureDomainAxes();

        plot.setRangeCrosshairVisible(false);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setAutoRange(true);
        // yAxis.setAutoRangeIncludesZero(true);
        yAxis.setAutoRange(true);


        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        // xAxis.setAutoRangeIncludesZero(true);
        xAxis.setAutoRange(true);

        TextTitle source = new TextTitle(
                "             MuGA (c) 2013");
        source.setFont(new Font("SansSerif", Font.PLAIN, 10));
        source.setPaint(Color.DARK_GRAY);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.LEFT);
        chart.addSubtitle(source);
        //formatChart(title);
        return chart;
    }

    /**
     * @return the panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Sets the panel to display the chart
     *
     * @param pan - panel to display chart
     */
    public void setPanel(JPanel pan) {
        panel = pan;
        //remove All
        panel.removeAll();
        //add the chart
        panel.setLayout(new BorderLayout());
        panel.add(new ChartPanel(chart), BorderLayout.CENTER);
    }

    public String getTitle() {
        return chart.getTitle().getText();
    }

    public void saveImage(String path) {
        try {
            //make path
            File dir = new File(path);
            dir.mkdirs();
            //make clone of chart
            JFreeChart saveChart = (JFreeChart) chart.clone();
            XYPlot plot = (XYPlot) saveChart.getXYPlot();

            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
            //range of axis ( 0 - 110%)
            rangeAxis.setRange(getMinimum() * 0.09, getMaximum() * 1.01);
            rangeAxis.setAutoRange(true);
            domainAxis.setAutoRange(true);
            rangeAxis.setAutoRangeIncludesZero(false);
            domainAxis.setAutoRangeIncludesZero(false);
            plot.setRangeAxis(rangeAxis);
            plot.setDomainAxis(domainAxis);



            String fileName = Funcs.getNormalizedFileName(saveChart.getTitle().getText()
                    + "_" + rangeAxis.getLabel());

            File image = new File(dir, fileName + ".png");
            int imgSize = Math.min(panel.getWidth(), panel.getHeight());
            int trysToSave = 10;
            // try many times - problems with threads concurrency
            while (trysToSave-- > 0) {
                try {

                    //            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                     //------------------------------------- SAVE ImgSize ------------
                    //write image
                    ChartUtilities.saveChartAsPNG(image, saveChart, imgSize, imgSize);
                    //------------------------------------- SAVE 500 x 300 ------------
                    dir = new File(path + "/normal/");
                    dir.mkdirs();
                    image = new File(dir, fileName + ".png");
                    ChartUtilities.saveChartAsPNG(image, saveChart, 500, 500);
                    //------------------------------------- SAVE 300 x 300 ------------
                    dir = new File(path + "/low/");
                    dir.mkdirs();
                    image = new File(dir, fileName + ".png");
                    ChartUtilities.saveChartAsPNG(image, saveChart, 300, 300);
                    //------------------------------------- SAVE 1000 x 1000 ------------
                    dir = new File(path + "/high/");
                    dir.mkdirs();
                    image = new File(dir, fileName + ".png");
                    ChartUtilities.saveChartAsPNG(image, saveChart, 1000, 1000);
                    //sucess
                    return;
                } catch (IOException ex) {
                    System.out.println("ERROR in Saving " + getTitle() + " Image!!, retry");
                }
                System.out.println("cannot save " + getTitle() + " Image, :-(");
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(MultiDataSet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double getMinimum() {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < data.length; i++) {
            for (int k = 0; k < data[i].getItemCount(); k++) {
                if (min > data[i].getDataItem(k).getYValue()) {
                    min = data[i].getDataItem(k).getYValue();
                }
            }
        }
        return min;
    }

    private double getMaximum() {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < data.length; i++) {
            for (int k = 0; k < data[i].getItemCount(); k++) {
                if (max < data[i].getDataItem(k).getYValue()) {
                    max = data[i].getDataItem(k).getYValue();
                }
            }
        }
        return max;
    }
}
