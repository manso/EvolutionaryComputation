///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package utils.StaticalLib.Chart;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import statistics.MultiDataSet;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public class BoxChart {

    static Color[] color = {Color.RED, Color.BLUE, Color.GREEN, Color.BLACK, Color.MAGENTA, Color.YELLOW, Color.DARK_GRAY, Color.ORANGE, Color.CYAN};

    public static JPanel getRank(double[][] values, String[] algorithms, String statistic) {
        BoxAndWhiskerCategoryDataset data = createDataSet(values, algorithms, statistic);
        JFreeChart chart = createChart(data, statistic);
        JPanel pan = new JPanel();
        pan.setLayout(new java.awt.BorderLayout());
        ChartPanel CP = new ChartPanel(chart);
        pan.add(CP, BorderLayout.CENTER);
        pan.validate();
        return pan;
    }

    private static BoxAndWhiskerCategoryDataset createDataSet(double[][] value, String[] algorithm, String stat) {
        final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        for (int i = 0; i < algorithm.length; i++) {
            final List list = new ArrayList();
            for (int k = 0; k < value[i].length; k++) {
                list.add(new Double(value[i][k]));
            }
            dataset.add(list, algorithm[i] + "    ", "");
        }
        return dataset;

    }

    private static JFreeChart createChart(BoxAndWhiskerCategoryDataset dataset, String title) {
        final CategoryAxis xAxis = new CategoryAxis("Simulations");
        final NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(false);

        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(true);
        renderer.setMedianVisible(true);
        renderer.setMeanVisible(true);
//        renderer.setArtifactPaint(new Color(128, 128, 128));

        renderer.setMaximumBarWidth(0.05);
        renderer.setItemMargin(0.5);
        renderer.setBaseOutlineStroke(new BasicStroke(1));
        renderer.setBaseOutlinePaint(Color.BLACK);
        renderer.setUseOutlinePaintForWhiskers(true);



        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart(
                title,
                new Font("SansSerif", Font.BOLD, 16),
                plot,
                true);

//        // set the background color for the chart...
        chart.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(new Color(220, 220, 220));
        plot.setDomainGridlinePaint(Color.DARK_GRAY);
        chart.setAntiAlias(true);



        TextTitle source = new TextTitle(
                "  MuGA (c) 2013");
        source.setFont(new Font("SansSerif", Font.PLAIN, 10));
        source.setPaint(Color.DARK_GRAY);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.LEFT);
        chart.addSubtitle(source);
        //set colors 
        for (int i = 0; i < dataset.getRowCount(); i++) {
            renderer.setSeriesPaint(i, MultiDataSet.color[i % MultiDataSet.color.length]);
        }
        return chart;
    }

    public static void saveChart(double[][] values, String[] algorithms, String statistic, String path) {
        saveChart(values, algorithms, statistic, path, 1000);
        saveChart(values, algorithms, statistic, path, 500);
        saveChart(values, algorithms, statistic, path, 300);
    }

    public static double [][]normalizeValuesLog10(double[][] values){
        double [][] log10V= new double[values.length] [values[0].length];
        for (int i = 0; i < log10V.length; i++) {
            for (int j = 0; j < log10V[i].length; j++) {
               log10V[i][j] = Math.log10(values[i][j]);                
            }            
        }
        return log10V;
    }
    
    public static void saveChartLog10(double[][] values, String[] algorithms, String statistic, String path, int size) {
        try {
            values = normalizeValuesLog10(values);
            BoxAndWhiskerCategoryDataset data = createDataSet(values, algorithms, statistic);
            JFreeChart chart = createChart(data, statistic);
           //make path
            File dir = new File(path + "boxPlot/"+size+"/log10");
            dir.mkdirs();
            String fileName = Funcs.getNormalizedFileName("BoxPlot_" + chart.getTitle().getText() + "_" + size);
            File image = new File(dir, fileName + ".png");
            ChartUtilities.saveChartAsPNG(image, chart, size, size);
        } catch (IOException ex) {
            Logger.getLogger(BoxChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void saveChart(double[][] values, String[] algorithms, String statistic, String path, int size) {
        try {
            BoxAndWhiskerCategoryDataset data = createDataSet(values, algorithms, statistic);
            JFreeChart chart = createChart(data, statistic);
            //make path
            File dir = new File(path + "boxPlot/"+size+"/");
            dir.mkdirs();
            String fileName = Funcs.getNormalizedFileName("BoxPlot_" + chart.getTitle().getText() + "_" + size);
            File image = new File(dir, fileName + ".png");
            ChartUtilities.saveChartAsPNG(image, chart, size, size);
        } catch (IOException ex) {
            Logger.getLogger(BoxChart.class.getName()).log(Level.SEVERE, null, ex);
        }
        saveChartLog10(values, algorithms, statistic, path, size);
    }

    public static void main(String[] args) {
        double[][] v = {
            {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
            {3, 4, 5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7},
            {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
            {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
            {3, 4, 5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7},
            {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
            {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
            {3, 4, 5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7},
            {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
            {5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7}
        };
        String[] l = {" X1", "X2", " X5", "X4", " X5", "X6", " X7", " X8", " X9", " X10"};


        JFrame window = new JFrame("Test");

        JPanel pan = getRank(v, l, "OLA");
        window.setContentPane(pan);
        window.setSize(500, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.validate();
        window.setVisible(true);

    }
}
