/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TestRecombination.java
 *
 * Created on 6/Nov/2010, 10:41:15
 */
package GUI;

import genetic.gene.GeneNumber;
import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import operator.mutation.Default;
import operator.mutation.Mutation;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import problem.Individual;
import utils.Individuals.SimpleRC;
import utils.DynamicLoad;
import utils.GAJarFile;

/**
 *
 * @author manso
 */
public class TestMutations extends javax.swing.JFrame {

    Color[] color = { Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA};
    int N_POINTS = 2000;
    JFreeChart chart;
    ChartPanel chartPanel;
    Individual ind;
    XYSeriesCollection dataset;
    XYPlot plot;
    Mutation mut = new Default();

    /** Creates new form TestRecombination */
    public TestMutations() {
        initComponents();
        loadJar("operator.mutation.real", lstMutation);

        chartPanel = creatChart(new SimpleRC());
        panel.add(chartPanel);
        panel.setSize(new java.awt.Dimension(500, 500));
        panel.revalidate();
        panel.repaint();
    }

    private void loadJar(String model, JList lst) {
        ArrayList<String> rec = GAJarFile.getClassName(model);
        DefaultListModel modelL = new DefaultListModel();
        //select the default
        int indexOfDefault = 0;
        for (int i = 0; i < rec.size(); i++) {
            modelL.add(i, rec.get(i));
            if (rec.get(i).equalsIgnoreCase("default")) {
                indexOfDefault = i;
            }
        }
        lst.setModel(modelL);
        //set the default selected
        lst.setSelectedIndex(indexOfDefault);
        lst.repaint();
    }

    private ChartPanel creatChart(Individual p) {

        Population pop = new MultiPopulation();
        Population tmp = new MultiPopulation();
        if (mut.getClass().getSimpleName().startsWith("Simplex")) {
            tmp.createRandomPopulation(3, p);
        } else {
            tmp.createRandomPopulation(1, p);
        }

        for (int pi = 0; pi < tmp.getNumGenotypes(); pi++) {
            Individual i = tmp.getGenotype(pi);
            i.setNumCopys(pi + 2);
//            //shrink genes
            for (int j = 0; j < i.getNumGenes(); j++) {
                i.setGeneValue(j, i.getGeneValue(j) / 2.0);
            }
            pop.addGenotype(i);
        }



        dataset = createDataset(pop);
        // create a scatter chart...
        chart = ChartFactory.createScatterPlot(
                mut.getClass().getSimpleName(), "X", "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        plot = chart.getXYPlot();
        XYDotRenderer render = new XYDotRenderer();
        render.setDotHeight(1);
        render.setDotWidth(1);
        //cor dos pontos
        for (int i = 0; i < 5; i++) {
            render.setSeriesPaint(i, color[i]);
        }
        plot.setDomainGridlinePaint(Color.DARK_GRAY);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);
        plot.setRenderer(render);
        plot.setBackgroundPaint(Color.WHITE);


        for (int i = 0; i < pop.getNumGenotypes(); i++) {
            Individual p1 = pop.getGenotype(i);
//            Individual p2 = pop.getGenotype((i + 1) % pop.getNumGenotypes() );
            double x1 = p1.getGene(0).getValue();
            double y1 = p1.getGene(1).getValue();

            Shape e2 = new Ellipse2D.Double(x1, y1, 1, 1);
            plot.addAnnotation(new XYShapeAnnotation(e2,
                    new BasicStroke(5.0f * p1.getNumCopies()),
                    Color.black));

        }
        chartPanel = new ChartPanel(chart);
        return chartPanel;
    }

    private XYSeriesCollection createDataset(Population pop) {

        mut.setProbability(1.0);
        Population child = mut.execute(pop.getClone());


        XYSeries[] series = new XYSeries[child.getNumGenotypes()];
        for (int i = 0; i < series.length; i++) {
            series[i] = new XYSeries(pop.getIndividual(0).getClass().getSimpleName() + "[" + (i+1) + "]");
        }
        //put one point in each the corner
        GeneNumber g1 = (GeneNumber) pop.getIndividual(0).getGene(0);
        GeneNumber g2 = (GeneNumber) pop.getIndividual(0).getGene(1);
        double d1 = (g1.getMaxValue() - g1.getMinValue()) * 0.25;
        double d2 = (g2.getMaxValue() - g2.getMinValue()) * 0.25;
        series[0].add(g1.getMaxValue() + d1, g2.getMaxValue() + d2);
        series[0].add(g1.getMinValue() - d1, g2.getMaxValue() + d2);
        series[0].add(g1.getMaxValue() + d1, g2.getMinValue() - d2);
        series[0].add(g1.getMinValue() - d1, g2.getMinValue() - d2);

        for (int i = 0; i < N_POINTS; i++) {
            Population p = pop.getClone();
            child = mut.execute(p);
            System.out.println("CHILDS " + child.getNumGenotypes());
            for (int j = 0; j < child.getNumGenotypes(); j++) {
                series[j].add(child.getGenotype(j).getGene(0).getValue(), child.getGenotype(j).getGene(1).getValue());
            }
        }


        XYSeriesCollection data = new XYSeriesCollection();
        for (int i = 0; i < series.length; i++) {
            data.addSeries(series[i]);
        }
        return data;

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstMutation = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sldCurrent = new javax.swing.JSlider();
        txtCurrentValue = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel.setPreferredSize(new java.awt.Dimension(900, 900));
        panel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panel, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(200, 800));
        jPanel4.setLayout(new java.awt.BorderLayout());

        lstMutation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstMutationMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstMutation);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jButton1.setText("New Mutation");
        jButton1.setPreferredSize(new java.awt.Dimension(119, 100));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, java.awt.BorderLayout.PAGE_START);

        jLabel1.setText("Generation");

        sldCurrent.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldCurrentStateChanged(evt);
            }
        });

        txtCurrentValue.setText("50");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sldCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCurrentValue, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCurrentValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sldCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel4, java.awt.BorderLayout.LINE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        chartPanel = creatChart(new SimpleRC());
        panel.removeAll();
        panel.add(chartPanel);
        panel.setSize(new java.awt.Dimension(500, 500));
        panel.revalidate();
        panel.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lstMutationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstMutationMouseClicked
        String cross = "operator.mutation.real." + lstMutation.getSelectedValue().toString();
        mut = (Mutation) DynamicLoad.makeObject(cross);
        jButton1ActionPerformed(null);
    }//GEN-LAST:event_lstMutationMouseClicked

    private void sldCurrentStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldCurrentStateChanged
        txtCurrentValue.setText(sldCurrent.getValue() + "");
    }//GEN-LAST:event_sldCurrentStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TestMutations().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstMutation;
    private javax.swing.JPanel panel;
    private javax.swing.JSlider sldCurrent;
    private javax.swing.JTextField txtCurrentValue;
    // End of variables declaration//GEN-END:variables
}
