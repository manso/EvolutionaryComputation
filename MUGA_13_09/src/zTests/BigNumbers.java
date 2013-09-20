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
package zTests;

import java.math.BigDecimal;

/**
 *
 * @author ZULU
 */
public class BigNumbers {
    
    public static void main(String[] args) {
        BigDecimal exp = new BigDecimal("1");
        BigDecimal two = new BigDecimal("2");
        for (int i = 0; i < 100; i++) {
            exp = exp.multiply(two);
            System.out.println(i + " \t" + exp);
        }
    }

}
