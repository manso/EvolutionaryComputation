/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.permutation;

import operator.recombination.Recombination;
import problem.PRM_Individual;

/**
 *
 * @author ZULU
 */
public abstract class CrossoverPRM extends Recombination{
    
    public abstract void doCrossover(PRM_Individual i1, PRM_Individual i2);
    
}
