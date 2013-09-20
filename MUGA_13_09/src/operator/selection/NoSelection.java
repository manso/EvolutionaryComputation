/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package operator.selection;

import genetic.population.Population;

/**
 *
 * @author arm
 */
public class NoSelection extends Selection{

    @Override
    public Population execute(Population pop, int numChilds) {
        return pop.getClone();

    }
    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
    @Override
     public String getInformation(){
        return "All Population Selection" +
               "\nReturn a clone of the population\n";
    }

}
