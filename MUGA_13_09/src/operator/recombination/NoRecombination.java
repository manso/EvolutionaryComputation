/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator.recombination;

import genetic.population.Population;

/**
 *
 * @author arm
 */
public class NoRecombination extends Recombination {

    @Override
    public Population execute(Population pop) {
        return pop;
    }
    @Override
    public String toString(){
        return  "No Recombination";
    }
 

}
