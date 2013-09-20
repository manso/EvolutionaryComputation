/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import statistics.Group_of_Solver;
import genetic.Solver.SimpleSolver;
import genetic.Solver.GA;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import utils.SolverContainer;

/**
 *
 * @author ZULU
 */
public class RunMultipleSolvers extends javax.swing.JFrame {
    
    Thread autorun = null;
    boolean running = false;
    SolverContainer frmSolver;
    List<SimpleSolver> solvers = new ArrayList<>();
    //Stats
    PanelOfStatistics displayStats;
    SetupSolver setup;
    Group_of_Solver groupSolvers;
    //main meno of this frame
    JFrame mainMenu;

    /**
     * Creates new form RunMultipleSolvers
     */
    public RunMultipleSolvers(JFrame mainMenu) {
        initComponents();
        this.mainMenu = mainMenu;
        solvers.add(new GA());
        updateList();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayStats = new PanelOfStatistics(solvers.get(0));
        displayStats.setBounds(this.getWidth(), 0, dim.width - this.getWidth(), dim.height - 50);
        displayStats.setVisible(true);
        
    }
    
    private void updateList() {
        int index = lstSolvers.getSelectedIndex();
        if (index < 0) {
            index = 0;
        } else if (index >= solvers.size()) {
            index = solvers.size() - 1;
        }
        
        DefaultListModel modelL = new DefaultListModel();
        for (SimpleSolver solver : solvers) {
            modelL.addElement(solver.title);
        }
        lstSolvers.setModel(modelL);
        lstSolvers.setSelectedIndex(index);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgRand_sync = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSolverInfo = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstSolvers = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btSetIndividual = new javax.swing.JButton();
        btSetStopCriteria = new javax.swing.JButton();
        btSelection = new javax.swing.JButton();
        btSetCrossover = new javax.swing.JButton();
        btSetMutation = new javax.swing.JButton();
        btSetRepl = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtNumSolvers = new javax.swing.JTextField();
        sldNumSolvers = new javax.swing.JSlider();
        jPanel4 = new javax.swing.JPanel();
        sbRandom = new javax.swing.JRadioButton();
        rbSynchronized = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        btStartStop = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSimulationName = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btMainMenu = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Explore Genetic Solvers");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        txtSolverInfo.setColumns(20);
        txtSolverInfo.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        txtSolverInfo.setRows(5);
        jScrollPane2.setViewportView(txtSolverInfo);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lstSolvers.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstSolvers.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstSolversValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstSolvers);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.GridLayout(3, 0, 0, 3));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/setup_small_icon.png"))); // NOI18N
        jButton1.setText("Setup");
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Copy_24x24.png"))); // NOI18N
        jButton2.setText("Clone");
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        btDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/delete_24.png"))); // NOI18N
        btDelete.setText("delete");
        btDelete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });
        jPanel3.add(btDelete);

        jLabel2.setText("From Selected Simulation set");

        jPanel11.setLayout(new java.awt.GridLayout(3, 2, 1, 1));

        btSetIndividual.setText("Population");
        btSetIndividual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetIndividualActionPerformed(evt);
            }
        });
        jPanel11.add(btSetIndividual);

        btSetStopCriteria.setText("Stop Crit.");
        btSetStopCriteria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetStopCriteriaActionPerformed(evt);
            }
        });
        jPanel11.add(btSetStopCriteria);

        btSelection.setText("Selection");
        btSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSelectionActionPerformed(evt);
            }
        });
        jPanel11.add(btSelection);

        btSetCrossover.setText("Crossover");
        btSetCrossover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetCrossoverActionPerformed(evt);
            }
        });
        jPanel11.add(btSetCrossover);

        btSetMutation.setText("Mutation");
        btSetMutation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetMutationActionPerformed(evt);
            }
        });
        jPanel11.add(btSetMutation);

        btSetRepl.setText("Replacement");
        btSetRepl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSetReplActionPerformed(evt);
            }
        });
        jPanel11.add(btSetRepl);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.GridLayout(2, 0));

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel22.setText("# of Populations");
        jPanel6.add(jLabel22);

        txtNumSolvers.setText("50");
        jPanel6.add(txtNumSolvers);

        jPanel2.add(jPanel6);

        sldNumSolvers.setMinimum(1);
        sldNumSolvers.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldNumSolversStateChanged(evt);
            }
        });
        jPanel2.add(sldNumSolvers);

        jPanel5.add(jPanel2);

        jPanel4.setLayout(new java.awt.GridLayout(2, 0, 0, 2));

        btgRand_sync.add(sbRandom);
        sbRandom.setText("Random");
        jPanel4.add(sbRandom);

        btgRand_sync.add(rbSynchronized);
        rbSynchronized.setSelected(true);
        rbSynchronized.setText("Synchronized");
        jPanel4.add(rbSynchronized);

        jPanel5.add(jPanel4);

        btStartStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Evolution_64.png"))); // NOI18N
        btStartStop.setText("Start Simulation");
        btStartStop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btStartStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartStopActionPerformed(evt);
            }
        });

        jLabel1.setText("Simulation Name");

        txtSimulationName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtSimulationName.setForeground(new java.awt.Color(0, 51, 255));
        txtSimulationName.setText("Simulation1");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/save_medium_icon.png"))); // NOI18N
        jButton4.setText("save");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSimulationName, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSimulationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btStartStop, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btStartStop)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setLayout(new java.awt.GridLayout(1, 2, 20, 20));

        btMainMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/home_64.png"))); // NOI18N
        btMainMenu.setText("Main Menu");
        btMainMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMainMenuActionPerformed(evt);
            }
        });
        jPanel9.add(btMainMenu);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/icons/Exit_64.png"))); // NOI18N
        jButton5.setText("Exit");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0) {
            SimpleSolver s = solvers.get(index).getClone();
            s.title = solvers.get(index).title + "_copy";
            solvers.add(s);
            updateList();
        }
        lstSolvers.setSelectedIndex(solvers.size() - 1);
        lstSolvers.requestFocus();
        
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0 && solvers.size() > 1) {
            solvers.remove(index);
            updateList();
        }
        lstSolvers.requestFocus();
    }//GEN-LAST:event_btDeleteActionPerformed
    
    private void lstSolversValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstSolversValueChanged
        if (lstSolvers.getSelectedIndex() >= 0) {
            txtSolverInfo.setText(solvers.get(lstSolvers.getSelectedIndex()).getInfo());
        }
    }//GEN-LAST:event_lstSolversValueChanged
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0) {
            //--------------solver container --------------        
            frmSolver = new SolverContainer(solvers.get(index), this);
            //---------------------------------------------        
            setup = new SetupSolver(frmSolver);
            setup.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0 && frmSolver != null) {
            //extracted solver from container
            solvers.set(index, frmSolver.solver);
            //delete frmSolver
            frmSolver = null;
            updateList();
        }
        //
        if (setup != null) {
            setup.dispose();
            setup = null;
        }
        
    }//GEN-LAST:event_formWindowGainedFocus
    
    private void sldNumSolversStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldNumSolversStateChanged
        txtNumSolvers.setText(sldNumSolvers.getValue() + "");
    }//GEN-LAST:event_sldNumSolversStateChanged
    
    private void btMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMainMenuActionPerformed
        if (groupSolvers != null) {
            groupSolvers.stopSolvers();
        }
        mainMenu.setVisible(true);
        this.dispose();
        displayStats.dispose();
    }//GEN-LAST:event_btMainMenuActionPerformed
    
    private void btStartStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartStopActionPerformed
        if (running == false) {
            int numSolvers = Integer.parseInt(txtNumSolvers.getText());
            //stop others solvers
            if (groupSolvers != null) {
                groupSolvers.stopSolvers();
            }
            groupSolvers = new Group_of_Solver(txtSimulationName.getText(), displayStats);
            groupSolvers.addSolver(solvers);
            //create populations of solvers
            if (rbSynchronized.isSelected()) {
                groupSolvers.synchronizePopulations(numSolvers);
            } else {
                groupSolvers.randomizePopulations(numSolvers);
            }
            //---------------------------------------------------
            groupSolvers.startSolvers();
            displayStats.setSolver(groupSolvers.meanSolvers);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            displayStats.setBounds(this.getWidth(), 0, dim.width - this.getWidth(), dim.height - 50);
            displayStats.setVisible(true);
            
            btStartStop.setText("Stop Evolution");
            running = true;
        } else {
            btStartStop.setText("Start Evolution");
            groupSolvers.stopSolvers();
            running = false;
        }
    }//GEN-LAST:event_btStartStopActionPerformed
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (groupSolvers != null) {
            groupSolvers.stopSolvers();
        }
        if( setup != null){
            setup.dispose();
        }
        mainMenu.dispose();
        this.dispose();
        displayStats.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        groupSolvers.saveToFile();
    }//GEN-LAST:event_jButton4ActionPerformed
    
    private void btSetIndividualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetIndividualActionPerformed
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0) {
            SimpleSolver s1 = solvers.get(index);
            for (int i = 0; i < solvers.size(); i++) {
                solvers.get(i).setParents(s1.getParents().getClone());
            }
        }
        lstSolvers.requestFocus();
    }//GEN-LAST:event_btSetIndividualActionPerformed
    
    private void btSetStopCriteriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetStopCriteriaActionPerformed
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0) {
            SimpleSolver s1 = solvers.get(index);
            for (int i = 0; i < solvers.size(); i++) {
                solvers.get(i).setStop(s1.getStop().getClone());
            }
        }
        lstSolvers.requestFocus();
    }//GEN-LAST:event_btSetStopCriteriaActionPerformed
    
    private void btSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSelectionActionPerformed
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0) {
            SimpleSolver s1 = solvers.get(index);
            for (int i = 0; i < solvers.size(); i++) {
                solvers.get(i).setSelect(s1.getSelect().getClone());
            }
        }
        lstSolvers.requestFocus();
    }//GEN-LAST:event_btSelectionActionPerformed
    
    private void btSetCrossoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetCrossoverActionPerformed
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0) {
            SimpleSolver s1 = solvers.get(index);
            for (int i = 0; i < solvers.size(); i++) {
                solvers.get(i).setRecombine(s1.getRecombine().getClone());
            }
        }
        lstSolvers.requestFocus();
    }//GEN-LAST:event_btSetCrossoverActionPerformed
    
    private void btSetMutationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetMutationActionPerformed
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0) {
            SimpleSolver s1 = solvers.get(index);
            for (int i = 0; i < solvers.size(); i++) {
                solvers.get(i).setMutate(s1.getMutate().getClone());
            }
        }
        lstSolvers.requestFocus();
    }//GEN-LAST:event_btSetMutationActionPerformed
    
    private void btSetReplActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSetReplActionPerformed
        int index = lstSolvers.getSelectedIndex();
        if (index >= 0) {
            SimpleSolver s1 = solvers.get(index);
            for (int i = 0; i < solvers.size(); i++) {
                solvers.get(i).setReplace(s1.getReplace().getClone());
            }
        }
        lstSolvers.requestFocus();
    }//GEN-LAST:event_btSetReplActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btMainMenu;
    private javax.swing.JButton btSelection;
    private javax.swing.JButton btSetCrossover;
    private javax.swing.JButton btSetIndividual;
    private javax.swing.JButton btSetMutation;
    private javax.swing.JButton btSetRepl;
    private javax.swing.JButton btSetStopCriteria;
    private javax.swing.JButton btStartStop;
    private javax.swing.ButtonGroup btgRand_sync;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList lstSolvers;
    private javax.swing.JRadioButton rbSynchronized;
    private javax.swing.JRadioButton sbRandom;
    private javax.swing.JSlider sldNumSolvers;
    private javax.swing.JTextField txtNumSolvers;
    private javax.swing.JTextField txtSimulationName;
    private javax.swing.JTextArea txtSolverInfo;
    // End of variables declaration//GEN-END:variables
}