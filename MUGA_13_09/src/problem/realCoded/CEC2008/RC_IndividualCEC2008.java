/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem.realCoded.CEC2008;

import problem.RC_IndividualCEC;

/**
 *
 * @author manso
 */
public abstract class RC_IndividualCEC2008 extends RC_IndividualCEC {

    public static int numGenes = 100;
    

    public RC_IndividualCEC2008(double min, double max) {
        super(min, max,numGenes);
    }
     public RC_IndividualCEC2008(double min, double max,int genes) {
        super(min, max,genes);
        numGenes = genes;
        
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
        } else if (numGenes > 100) {
            numGenes = 100;
        }        
    }
      
    //double[] f_bias = {-450.0, -450.0, 390.0, -330.0, -180.0, -140.0};
    double[] f_bias = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
}
