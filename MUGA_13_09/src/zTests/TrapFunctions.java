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
package zTests;

/**
 *
 * @author ZULU
 */
public class TrapFunctions {
    
    public static int Unitation(String str){
        int ONES = 0;
        for (int i = 0; i < str.length(); i++) {
            if( str.charAt(i)=='1') ONES++;            
        }
        return ONES;
    }
    public static double Trap(int L, int val, double a, double b, double z){
        String s = Integer.toBinaryString(val);
        int u = Unitation(s);
        if( u < z)
            return (a/z)*(z-u);
        return (b/(L-z))*(u-z);
    }
    
    public static void main(String[] args) {
        double a = 0.95, b=1.0, z = 3;
        for (int i = 0; i < 16; i++) {
            System.out.println( i +" = " + Trap(4, i, a, b, z));
            
        }
        
    }

}
