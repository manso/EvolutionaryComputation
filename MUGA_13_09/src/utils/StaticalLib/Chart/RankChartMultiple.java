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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import utils.Funcs;

/**
 *
 * @author ZULU
 */
public class RankChartMultiple {

    public static JPanel getRank(int[][] v, String[] label, String[] title, String simulation) {
        DefaultCategoryDataset data = createDataSet(v, label, title);
        JFreeChart chart = createChart(data, simulation);
        JPanel pan = new JPanel();
        pan.setLayout(new java.awt.BorderLayout());
        ChartPanel CP = new ChartPanel(chart);
        pan.add(CP, BorderLayout.CENTER);
        pan.validate();
        return pan;
    }

    private static DefaultCategoryDataset createDataSet(int[][] v, String[] label, String title[]) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int k = 0; k < title.length; k++) {
            for (int i = 0; i < v[k].length; i++) {
                dataset.addValue(v[k][i], label[i] + "   ", title[k]);
            }
        }
        return dataset;

    }

    private static JFreeChart createChart(DefaultCategoryDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createBarChart(
                title, // chart title
                "", // domain axis label
                "Rank", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips?
                false // URLs?
                );

        final CategoryPlot plot = chart.getCategoryPlot();
        final CategoryAxis axis = plot.getDomainAxis();


        BarRenderer renderer = (BarRenderer) plot.getRenderer();
//        renderer.setDrawBarOutline(true);
        //espaço entre as barras
        renderer.setItemMargin(0.05);




        // change the margin at the top of the range axis...
        final ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setLowerMargin(0);
        rangeAxis.setUpperMargin(0.05);
         
        CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);




//        LegendTitle legend = chart.getLegend();
//        legend.setPosition(RectangleEdge.TOP);
//        rangeAxis.setInverted(true);

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.WHITE);


        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        plot.setDomainGridlinePaint(Color.DARK_GRAY);
//        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);


        DecimalFormat formt = new DecimalFormat("##");
        //show rank numbers
        renderer.setItemLabelsVisible(true);
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", formt));



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
    
    public static void saveRankChart(int[][] v, String[] label, String []algorithms,String title, String path) {
        try {
            DefaultCategoryDataset data = createDataSet(v, label,algorithms);
            JFreeChart chart = createChart(data, title);
            //make path
            File dir = new File(path+"rank/");
            dir.mkdirs();
            String fileName = Funcs.getNormalizedFileName(chart.getTitle().getText());
            File image = new File(dir, fileName + ".png");
            ChartUtilities.saveChartAsPNG(image, chart, 800, 800);
        } catch (IOException ex) {
            Logger.getLogger(RankChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        int[][] r = {
            {1, 2, 3, 6, 5, 4},
            {1, 2, 3, 3, 3, 4},
            {1, 2, 2, 6, 1, 4}
        };

        String[] l = {" A1", "B2", "C3", "G", "h", "J"};
        String[] t = {" Stat1", "stat2", "stat3",};
        JFrame window = new JFrame("Test");
        JPanel pan = getRank(r, l, t, "simulation");

        window.setContentPane(pan);

        window.setSize(800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.validate();

        window.setVisible(true);

    }
}
