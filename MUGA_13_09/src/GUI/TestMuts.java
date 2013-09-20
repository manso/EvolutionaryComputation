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

import genetic.Solver.SimpleSolver;
import genetic.Solver.GA;
import genetic.population.MultiPopulation;
import genetic.population.Population;
import genetic.population.SimplePopulation;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import operator.mutation.Mutation;
import operator.recombination.Default;
import operator.recombination.Recombination;
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
import problem.RC_Individual;
import utils.Individuals.SimpleRC;
import utils.DynamicLoad;
import utils.GAJarFile;

/**
 *
 * @author manso
 */
public class TestMuts extends javax.swing.JFrame {

    Color[] color = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.MAGENTA};
    int N_POINTS = 4000;
    int MAXCOPIES = 5;
    JFreeChart chart;
    ChartPanel chartPanel;
    Individual ind;
    XYSeriesCollection dataset;
    XYPlot plot;
    Mutation mutation = new Mutation() {

        @Override
        public Population execute(Population pop) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    SimpleSolver solver = new GA();

    /**
     * Creates new form TestRecombination
     */
    public TestMuts() {
        initComponents();

        MultiPopulation pop = new MultiPopulation();
        pop.createRandomPopulation(2, new SimpleRC());
        solver.setParents(pop);

        loadJar("operator.mutation.real", lstCrossover);
        mutation = (Mutation) DynamicLoad.makeObject("operator.mutation.real." + lstCrossover.getSelectedValue().toString());
        mutation.setSolver(solver);

        chartPanel = creatChart(new SimpleRC());
        panel.add(chartPanel);
        panel.setSize(new java.awt.Dimension(500, 500));
        panel.revalidate();
        panel.repaint();

    }

    private ChartPanel creatChart(Individual p) {
//        Random rnd = new Random();
        Population pop = solver.getParents();
//        Population tmp = new MultiPopulation();
//        solver.setParents(pop);
//        mutation.solver = solver;
//
//        tmp.createRandomPopulation(2, p);
//
//
//        tmp.evaluate();
//
//        for (int pi = 0; pi < tmp.getNumGenotypes(); pi++) {
//            Individual i = tmp.getGenotype(pi);
//            i.setNumCopys(rnd.nextInt(MAXCOPIES) + 1);
//            //shrink genes
////            for (int j = 0; j < i.getNumGenes(); j++) {
////                i.setGeneValue(j, i.getGeneValue(j) / 2.0);
////            }
//            pop.addGenotype(i);
//        }



        dataset = createDataset(pop);
        // create a scatter chart...
        chart = ChartFactory.createScatterPlot(
                mutation.getClass().getSimpleName(), "X", "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        plot = chart.getXYPlot();
        XYDotRenderer render = new XYDotRenderer();
        render.setDotHeight(2);
        render.setDotWidth(2);
        //cor dos pontos
//        for (int i = 0; i < 5; i++) {
        render.setSeriesPaint(0, Color.BLACK);
//        }
        plot.setDomainGridlinePaint(Color.DARK_GRAY);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);
        plot.setRenderer(render);
        plot.setBackgroundPaint(Color.WHITE);


        for (int i = 0; i < pop.getNumGenotypes(); i++) {
            Individual p1 = pop.getGenotype(i);
//            Individual p2 = pop.getGenotype((i + 1) % pop.getNumGenotypes() );
            double x1 = p1.getGeneValue(0);
            double y1 = p1.getGeneValue(1);
//            Ellipse2D expansion = new Ellipse2D.Double(x1 - DIM, y1 - DIM, DIM * 2, DIM * 2);
//            plot.addAnnotation(new XYShapeAnnotation(expansion, new BasicStroke(8.0f * p1.getNumCopies()), Color.BLACK));
//            Shape e2 = new Ellipse2D.Double(x1, y1, 0.1*p1.getNumCopies(), 0.1*p1.getNumCopies());
            Shape e2 = new Rectangle2D.Double(x1, y1, 0.1, 0.1);

            plot.addAnnotation(new XYShapeAnnotation(e2,
                    new BasicStroke(10.0f), color[((i) % color.length)]));
        }

        // add the chart to a panel...
        chartPanel = new ChartPanel(chart);

        return chartPanel;
    }

    private XYSeriesCollection createDataset(Population pop) {

        mutation.setProbability(1.0);
        Population child = mutation.execute(pop.getClone());


        XYSeries[] series = new XYSeries[pop.getNumGenotypes() + 1];
        series[0] = new XYSeries("offspring");
        for (int i = 0; i < pop.getNumGenotypes(); i++) {
            series[i + 1] = new XYSeries("p(" + pop.getGenotype(i).getNumCopies() + ")");
        }
        //put one point in each the corner
        double min = ((RC_Individual) pop.getIndividual(0)).getMinValue();
        double max = ((RC_Individual) pop.getIndividual(0)).getmaxValue();


        series[0].add(min, min);
        series[0].add(min, max);
        series[0].add(max, max);
        series[0].add(max, min);

        for (int i = 0; i < N_POINTS; i++) {
            Population p = pop.getClone();
            p.evaluate();
            child = mutation.execute(p);
            for (int j = 0; j < child.getNumGenotypes(); j++) {
                series[0].add(child.getGenotype(j).getGeneValue(0), child.getGenotype(j).getGeneValue(1));
            }



        }


        XYSeriesCollection data = new XYSeriesCollection();
        for (int i = 0; i < series.length; i++) {
            data.addSeries(series[i]);
        }
        return data;

    }

    private void loadJar(String model, JList lst) {
        ArrayList<String> rec = GAJarFile.getClassName(model);
        DefaultListModel modelL = new DefaultListModel();
        //select the default
        int indexOfDefault = 0;
        for (int i = 0; i < rec.size(); i++) {
            modelL.add(i, rec.get(i));
//            if (rec.get(i).equalsIgnoreCase("default")) {
//                indexOfDefault = i;
//            }
        }
        lst.setModel(modelL);
        //set the default selected
        lst.setSelectedIndex(indexOfDefault);
        lst.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstCrossover = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sldCurrent = new javax.swing.JSlider();
        txtCurrentValue = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btAddOne = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel.setPreferredSize(new java.awt.Dimension(900, 900));
        panel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(panel, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(200, 800));
        jPanel4.setLayout(new java.awt.BorderLayout());

        lstCrossover.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Multiset_AX", "Multiset_AX_mean", "Multiset_AX_mean2", "RCGA_AX", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstCrossover.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstCrossoverMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstCrossover);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sldCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
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

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("SimpleiPop");
        jButton1.setPreferredSize(new java.awt.Dimension(119, 100));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("MultiPop");
        jButton2.setPreferredSize(new java.awt.Dimension(119, 100));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btAddOne.setText("Add One");
        btAddOne.setPreferredSize(new java.awt.Dimension(119, 100));
        btAddOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddOneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addComponent(btAddOne, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btAddOne, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel4, java.awt.BorderLayout.LINE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        solver.setParents(new SimplePopulation());
        btAddOneActionPerformed(evt);
        lstCrossoverMouseClicked(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lstCrossoverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstCrossoverMouseClicked
        String cross = "operator.mutation.real." + lstCrossover.getSelectedValue().toString();
        mutation = (Mutation) DynamicLoad.makeObject(cross);
        mutation.setSolver(solver);

        chartPanel = creatChart(new SimpleRC());
        panel.removeAll();
        panel.add(chartPanel);
        panel.setSize(new java.awt.Dimension(500, 500));
        panel.revalidate();
        panel.repaint();
    }//GEN-LAST:event_lstCrossoverMouseClicked

    private void sldCurrentStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldCurrentStateChanged
        txtCurrentValue.setText(sldCurrent.getValue() + "");
    }//GEN-LAST:event_sldCurrentStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        solver.setParents(new MultiPopulation());
        btAddOneActionPerformed(evt);
        lstCrossoverMouseClicked(null);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btAddOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddOneActionPerformed
        Random rnd = new Random();
        Individual ind = new SimpleRC();
        ind.setNumCopys(rnd.nextInt(MAXCOPIES) + 1);
        ind.evaluate();
        solver.getParents().addGenotype(ind);
        lstCrossoverMouseClicked(null);
    }//GEN-LAST:event_btAddOneActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new TestMuts().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddOne;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstCrossover;
    private javax.swing.JPanel panel;
    private javax.swing.JSlider sldCurrent;
    private javax.swing.JTextField txtCurrentValue;
    // End of variables declaration//GEN-END:variables
}
