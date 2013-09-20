/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.replacement;

import genetic.population.Population;
import operator.GeneticOperator;

/**
 *
 * @author arm
 */
public abstract class Replacement extends GeneticOperator {
   public abstract Population execute( Population pop, Population childs);
   public Population evaluteAndReplace( Population pop, Population childs){
       if( pop.getNumIndividuals() == 0 )
           return childs;
//       pop.evaluate();
       childs.evaluate();
       return execute(pop, childs);
   }
    @Override
   public void setParameters(String str) {
   }
    @Override
   public String toString(){
        return getClass().getSimpleName() ;
    }
   
    @Override
     public Replacement getClone() {
        return (Replacement) super.getClone();
    }
}
