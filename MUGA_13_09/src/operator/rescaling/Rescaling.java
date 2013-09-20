/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator.rescaling;

import genetic.population.Population;
import operator.GeneticOperator;
import utils.DynamicLoad;

/**
 *
 * @author manso
 */
public abstract class Rescaling extends GeneticOperator  {
    static double DEFAULT_FACTOR = 2.0;
    protected double factor = DEFAULT_FACTOR;

    public abstract Population execute( Population pop);

    @Override
    public void setParameters( String str){
        if (str.isEmpty()) {
            return;
        }
      try{
            setFactor(Double.parseDouble(str));
      }catch( Exception e){
            setFactor(DEFAULT_FACTOR);
      }
    }
    @Override
    public String toString(){
        return  getClass().getSimpleName() + " <" + getFactor() + ">";
    }
    public String getInformation(){
        return getClass().getSimpleName() + " Scale Factor: " + getFactor()+
                "\n Parameters : <Scale Factor> "+
                "\n\n Rescale the number of copys by the factor";
    }

    /**
     * @return the factor
     */
    public double getFactor() {
        return factor;
    }

    /**
     * @param factor the factor to set
     */
    public void setFactor(double factor) {
        this.factor = factor;
    }

    @Override
     public Rescaling getClone() {
        Rescaling res = (Rescaling)DynamicLoad.makeObject(this);
        res.factor = this.factor;
        return res;
    }

}
