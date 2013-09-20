/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package problem;



/**
 *
 * @author manso
 */
public abstract class IndividualCEC  extends Individual{   
    public IndividualCEC() {
        super(MINIMIZE);
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

}
