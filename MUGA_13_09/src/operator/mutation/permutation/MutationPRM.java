/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.mutation.permutation;

import operator.mutation.Mutation;
import problem.PRM_Individual;

/**
 *
 * @author ZULU
 */
public abstract class MutationPRM extends Mutation{
    public abstract  void mutate(PRM_Individual ind, double mutProb);      
}
