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
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public class BoxChartMultiple {

    public static JPanel getBoxChart(double[][][] values, String[] algorithms, String[] statistic, String title) {
        BoxAndWhiskerCategoryDataset data = createDataSet(values, algorithms, statistic);
        JFreeChart chart = createChart(data, title);
        JPanel pan = new JPanel();
        pan.setLayout(new java.awt.BorderLayout());
        ChartPanel CP = new ChartPanel(chart);
        pan.add(CP, BorderLayout.CENTER);
        pan.validate();
        return pan;
    }

    private static BoxAndWhiskerCategoryDataset createDataSet(double[][][] value, String[] algorithm, String[] stat) {
        final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        for (int s = 0; s < value.length; s++) {
            for (int i = 0; i < algorithm.length; i++) {
                final List list = new ArrayList();
                for (int k = 0; k < value[s][i].length; k++) {
                    list.add(new Double(value[s][i][k]));
                }
                dataset.add(list, algorithm[i] + "    ", stat[s]);
            }
        }
        return dataset;

    }

    private static JFreeChart createChart(BoxAndWhiskerCategoryDataset dataset, String title) {
        final CategoryAxis xAxis = new CategoryAxis("");
        final NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(false);
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(true);
        renderer.setMedianVisible(true);
        renderer.setMeanVisible(true);

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
        plot.setBackgroundPaint(new Color(225, 225, 225));
        plot.setDomainGridlinePaint(Color.DARK_GRAY);

        TextTitle source = new TextTitle(
                "  MuGA(c)2013");
        source.setFont(new Font("SansSerif", Font.PLAIN, 10));
        source.setPaint(Color.DARK_GRAY);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.LEFT);
        chart.addSubtitle(source);

        //formatChart(title);
        return chart;
    }

    public static void saveChart(double[][][] values, String[] algorithms, String[] statistic, String title, String path) {
        try {
            BoxAndWhiskerCategoryDataset data = createDataSet(values, algorithms, statistic);
            JFreeChart chart = createChart(data, title);
            //make path
            File dir = new File(path);
            dir.mkdirs();
            String fileName = Funcs.getNormalizedFileName("BoxChart_" +chart.getTitle().getText());
            File image = new File(dir, fileName + ".png");
            ChartUtilities.saveChartAsPNG(image, chart, 300 * statistic.length,500);
        } catch (IOException ex) {
            Logger.getLogger(BoxChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        double[][][] v = {
            {
                {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
                {3, 4, 5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7},
                {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
                {5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7}
            },
            {
                {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
                {3, 4, 5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7},
                {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
                {5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7}
            },
            {
                {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
                {3, 4, 5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7},
                {1, 2, 3, 5, 5, 4, 3, 4, 5, 3, 2, 3, 4, 5, 6},
                {5, 6, 7, 8, 7, 8, 9, 7, 6, 5, 6, 7, 8, 7}
            }
        };

        String[] l = {" X1", "X2", " X5", "X4"};
        String[] s = {" S1", "S2", " S3"};


        JFrame window = new JFrame("Test");

        JPanel pan = getBoxChart(v, l, s, "OLA");
        window.setContentPane(pan);
        window.setSize(800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.validate();
        window.setVisible(true);

    }
}
