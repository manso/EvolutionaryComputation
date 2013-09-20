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
package problem.permutation.TSP;

import java.awt.geom.Point2D;

/**
 *
 * @author ZULU
 */
public class SimpleTSP2 extends AbstractTSP {

    static int[] optimum = {0, 1, 2, 3, 4};
    static double[] x = {0,1,3,3,2};
    static double[] y = {2,1,1,2,4};

    public SimpleTSP2() {
        super(x, y, optimum);
    }
    public SimpleTSP2(int []g ) {
        super(x, y, optimum);
        genome = g;
    }
    
    public String toTSPString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i <x.length; i++) {            
            str.append("\t").append(genome[i]).append( "\t");
        }
        str.append("\n");
        for (int i = 0; i <x.length; i++) {
            Point2D p = getPoint(genome[i]);
            str.append("(").append(p.getX()).append(",").append(p.getY()).append( ")\t ");
        }
        return str.toString();
    }
}
