///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package problem.permutation;

import problem.PRM_Individual;

/**
 *
 * @author ZULU
 */
public class SimplePermutation extends PRM_Individual{

    public SimplePermutation() {
        super(MINIMIZE, 3);
    }
    public SimplePermutation(int numGenes) {
        super(MINIMIZE, numGenes);
    }
    public SimplePermutation(int []g ) {
        super(MINIMIZE, g.length);
        genome = g;
    }
    
    @Override
    protected double fitness() {
        return 0;
    }

}
