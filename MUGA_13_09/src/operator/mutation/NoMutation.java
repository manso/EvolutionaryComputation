/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation;

import problem.Individual;
import genetic.population.Population;
import utils.BitField;
import utils.DynamicLoad;

/**
 *
 * @author arm
 */
public class NoMutation extends Mutation {

    @Override
    public Population execute(Population pop) {        
        return pop;
    }

    public String getInformation() {
        return "\nNo Mutation";
    }
   



   
}
