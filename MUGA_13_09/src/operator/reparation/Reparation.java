/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.reparation;

import operator.mutation.*;
import genetic.population.Population;
import operator.GeneticOperator;
import problem.Individual;
import utils.DynamicLoad;

/**
 *
 * @author arm
 */
public abstract class Reparation extends GeneticOperator {

    public abstract Individual repair(Individual ind);
    public abstract Population execute(Population pop);
     @Override
    public String toString() {       
            return getClass().getSimpleName();
     }
     
      /**
     * deep cloning
     *
     * @return Genetic operator
     */
    @Override
    public Reparation getClone() {
        Reparation clone = (Reparation) DynamicLoad.makeObject(this);
        return clone;
    }
}
