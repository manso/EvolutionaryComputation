/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.bitString.KSPenalty;

import genetic.gene.GeneBinary;
import utils.Knapsack.KnapSack_DP;

/**
 *
 * @author manso
 */
public class K_x extends Knapsack {
   
    public K_x() {
        super();    
        setBest(KBEST);
        addGene(new GeneBinary(w.length));
    }


    @Override
    public double fitness() {
        double val = valueSack(this.getBits());
        double height = heightSack(this.getBits());
        double pen = penaltyLinear(height);
        return val - pen;
    }
  
};
