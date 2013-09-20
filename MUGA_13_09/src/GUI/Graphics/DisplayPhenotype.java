/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import genetic.Solver.SimpleSolver;
import genetic.population.Population;
import java.awt.BorderLayout;
import java.awt.Graphics;
import problem.Individual;
import problem.RC_Individual;
import problem.bitString.Deceptive.Deceptive;
import problem.bitString.KSPenalty.Knapsack;
import problem.permutation.TSP.AbstractTSP;

/**
 *
 * @author arm
 */
public class DisplayPhenotype extends DisplayPopulation {

    DisplayPopulation func = null;
    Individual individual = null;

    public DisplayPhenotype() {
        this.setLayout(new BorderLayout());
    }

    /**
     * @param pop the pop to set
     */
    public void calculateFunc(SimpleSolver s, Population p) {
        super.setSolver(s,p);
        if (getPop() == null || getPop().getNumGenotypes() == 0) {
            return;
        }
        Individual i = getPop().getGenotype(0);
        //se os individuos da populaç?o forem diferentes
        //criar nova funcao
        if (individual == null || !individual.getClass().equals(i.getClass())) {

            if (i instanceof AbstractTSP) {
                if (!(func instanceof DisplayTSP)) {
                    func = new DisplayTSP();
                    this.removeAll();
                    this.add(func, BorderLayout.CENTER);
                }
                func.setSolver(s,p);                               
            } else if (i instanceof Knapsack) {
                if (!(func instanceof DisplayKS)) {
                    func = new DisplayKS();
                    this.removeAll();
                    this.add(func, BorderLayout.CENTER);
                }
                func.setSolver(s,p); 
            }else if (i.isFunc3D()) {
                if (!(func instanceof Display3DFunc)) {
                    func = new Display3DFunc();
                    this.removeAll();
                    this.add(func, BorderLayout.CENTER);
                }
                func.setSolver(s,p);                 
            } else if (i.isFunc2D()) {
                if (!(func instanceof Display2DFunc)) {
                    func = new Display2DFunc();
                    this.removeAll();
                    this.add(func, BorderLayout.CENTER);
                }
                func.setSolver(s,p);  
            } else if ( i instanceof RC_Individual) {
                if (!(func instanceof DisplayDiferenceToOptimum)) {
                    func = new DisplayDiferenceToOptimum();
                    this.removeAll();
                    this.add(func, BorderLayout.CENTER);
                }
                func.setSolver(s,p);                  
            } 
            else if (i instanceof Deceptive) {
                if (!(func instanceof DisplayDeceptive)) {
                    func = new DisplayDeceptive();
                    this.removeAll();
                    this.add(func, BorderLayout.CENTER);
                }
                func.setSolver(s,p);                  
            }
            else {
                if (!(func instanceof DisplayBits)) {
                    func = new DisplayBits();
                    this.removeAll();
                    this.add(func, BorderLayout.CENTER);
                }
                func.setSolver(s,p);  
            }
            this.revalidate();
        }

    }
   
   /**
     * @param pop the pop to set
     */
    @Override
    public synchronized void setSolver(SimpleSolver s, Population p) {
       super.setSolver(s, p);
       calculateFunc(s, p);
    }
    @Override
    public void showFunc(Graphics gr){
        if (func != null) {
            func.showFunc(gr);
        }
    }
}
