/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.realCoded.CEC2008;

import problem.Individual;
import problem.IndividualCEC;

/**
 *
 * @author manso
 */
public abstract class IndividualCEC2008 extends IndividualCEC {

    public static int numGenes = 2;
    

    public IndividualCEC2008() {
        super();
    }
  
    @Override
    public void setParameters(String params) {
        String[] par = params.split(" ");
        if (par[0].isEmpty()) {
            return;
        }
        numGenes = Integer.parseInt(par[0]);
        if (numGenes < 2) {
            numGenes = 2;
        } else if (numGenes > 1000) {
            numGenes = 1000;
        }        
    }
      
    //double[] f_bias = {-450.0, -450.0, 390.0, -330.0, -180.0, -140.0};
    double[] f_bias = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
}
