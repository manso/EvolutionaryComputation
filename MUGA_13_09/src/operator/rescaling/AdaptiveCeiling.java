/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.rescaling;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import utils.DynamicLoad;

/**
 *
 * @author manso
 */
public class AdaptiveCeiling extends Rescaling {

    static double DEFAULT_MAX = 2.0;
    private double MAX = DEFAULT_MAX;

    public AdaptiveCeiling() {
        setFactor(DEFAULT_MAX);
    }

    @Override
    public Population execute(Population pop) {
        //if is multipopulation
        if (pop instanceof MultiPopulation) {
            //convert to multipopulation
            MultiPopulation mpop = (MultiPopulation) pop;
            
            double dif = mpop.getNumIndividuals() - MAX * mpop.getNumGenotypes();
            setFactor(getFactor() + dif / (mpop.getNumGenotypes() * MAX));
            if (getFactor() <= 1.0) {
                setFactor(1.0);
                return pop;
            }            
            mpop.reScaleCopys(getFactor());
        }//mpop
        return pop;  
    }

    @Override
    public void setParameters(String str) {
        try {
            MAX = Double.parseDouble(str);
        } catch (Exception e) {
            MAX = DEFAULT_MAX;
        }
    }
    @Override
    public String getParameters() {
        return  MAX + "";
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "<" + MAX + ">]";

    }

    @Override
    public String getInformation() {
        return getClass().getSimpleName() + " MAX Factor : " + MAX
                + "\n Parameters : <MAX Factor>"
                + "\n\n Rescale the number of copys to target #Genotypes * MAX Factor";
    }

    @Override
    public Rescaling getClone() {
        AdaptiveCeiling clone = (AdaptiveCeiling) DynamicLoad.makeObject(this);
        clone.MAX = this.MAX;
        return clone;
    }
}
