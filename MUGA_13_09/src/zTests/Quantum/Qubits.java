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
package zTests.Quantum;

import java.util.Random;
import problem.bitString.KSPenalty.K50;

/**
 *
 * @author ZULU
 */
public class Qubits {

    Random rnd = new Random();
    public double[] alpha;

    double evaluate(String x) {
        double val = 0;
        double height = 0;
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == '1') {
                val += K50.p5[i];
                height += K50.w5[i];
            }
        }
        return val - K50.penaltyLinear(height);
    }

    
    public void update(String x, String b) {
    }

    public Qubits(int size) {
        alpha = new double[size];
        double R2_2 = Math.sqrt(2.0) / 2.0;
        for (int i = 0; i < size; i++) {
            alpha[i] = R2_2; //Math.sqrt(0.5);
        }
    }

    public String make() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < alpha.length; i++) {
            if (rnd.nextDouble() < alpha[i] * alpha[i]) {
                str.append("1");
            } else {
                str.append("0");
            }
        }
        return str.toString();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < alpha.length; i++) {
            str.append(utils.Funcs.DoubleToString(alpha[i], 10) + " ");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        Qubits q = new Qubits(10);
        System.out.println(q);
        for (int i = 0; i < 100; i++) {
            System.out.println(q.make());

        }

    }
}
