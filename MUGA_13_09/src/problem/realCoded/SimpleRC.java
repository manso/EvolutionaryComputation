///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso                             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****************************************************************************/
///****************************************************************************/
///****     This software was built with the purpose of investigating      ****/
///****     and learning. Its use is free and is not provided any          ****/
///****     guarantee or support.                                          ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/

package problem.realCoded;

import problem.RC_Individual;

/**
 *
 * @author Administrator
 */
public class SimpleRC extends RC_Individual {

    public SimpleRC() {
        super(MAXIMIZE,0,1,2);
    }
    public SimpleRC(int size) {
        super(MAXIMIZE,0,1,size);
    }
    
    public SimpleRC(double min, double max,int size) {
        super(MAXIMIZE,min,max,size);
    }

    @Override
    protected double fitness() {
        double []v= getValues();
        double f = 0;
        for (int i = 0; i < v.length; i++) {
            f+= v[i]*v[i];
        }
        return f;
    }
    
    

}
