/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package problem;

import utils.Funcs;



/**
 *
 * @author manso
 */
public abstract class RC_IndividualCEC  extends RC_Individual{   
    public RC_IndividualCEC(double min, double max, int genes) {
        super(MINIMIZE,min,max,genes);
    }
    
    @Override
    public abstract double fitness();
    @Override
    public abstract String getInfo();
    @Override
    public abstract void setParameters(String params);
    public abstract double [] getOptimum();

    @Override
    public String getName() {
        return getClass().getSimpleName() + " ["+ getNumGenes() + "]" ;
    }

    public Individual getOptimumIndividual(){
        Individual ind = this.getClone();
        double [] opt = getOptimum();
        for(int i=0 ; i < ind.getNumGenes() ; i++)
            ind.setGeneValue(i,opt[i]);

        ind.setFitness( getBest());
        return ind;
    }
    
    @Override
    public String toStringPhenotype() {
        final StringBuffer result = new StringBuffer();
        if (this.numCopys > 1) {
            result.append("[").append(Funcs.IntegerToString(numCopys, 3)).append("] ");
        } else {
            result.append("      ");
        }
        if (isEvaluated) {
            result.append(Funcs.DoubleToString(fitness, 24));
        } else {
            result.append(Funcs.SetStringSize("NOT_EVAL.!", 24));
        }
        result.append(" = ");
        double [] opt = getOptimum();
        for (int i = 0; i < genome.length; i++) {
            result.append(Funcs.DoubleExponentialToString(genome[i] -opt[i], 24));
        }
        return result.toString();
    }

}
