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
package zTests.neuralNetwork;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ZULU
 */
public class Perceptron {

    static Random random = new Random();
    //last element of input is the bias
    private double[] w;
    private double bias;
    private double y;

    public Perceptron(int size) {
        w = new double[size];

        for (int i = 0; i < size; i++) {
            w[i] = random.nextDouble() - 0.5;
        }
        bias = random.nextDouble() - 0.5;
    }

    public void activate(double[] input) {
        double value = bias;
        for (int i = 0; i < input.length; i++) {
            value += input[i] * w[i];
        }
        y = step(value);
    }

    public void learn(double[] input, double expected, double alpha) {
        activate(input);
        double error = expected - y;
        double[] w1 = new double[input.length];
        for (int i = 0; i < w1.length; i++) {

            w1[i] = w[i] + error * alpha * input[i];
        }
        w = w1;
        bias = bias + error * alpha * 1;
    }

    public void learn(double[][] input, double[] expected, double alpha) {
        for (int k = 0; k < input.length; k++) {
            activate(input[k]);
            double error = expected[k] - y;
            double[] w1 = new double[input[k].length];
            for (int i = 0; i < w1.length; i++) {
                w1[i] = w[i] + error * alpha * input[k][i];
            }
            w = w1;
            bias = bias + error * alpha * 1;
        }
    }

    public void learnBinary(int[] input, int expected, double alpha) {
        double[] realInput = new double[input.length];
        for (int i = 0; i < realInput.length; i++) {
            if (input[i] > 0) {
                realInput[i] = 1;
            } else {
                realInput[i] = -1;
            }
        }
        learn(realInput, expected > 0 ? 1 : -1, alpha);
    }

    public int getBinary(double[] input) {
        activate(input);
        return y >= 0 ? 1 : 0;
    }

    public double step(double p) {
        return p > 0 ? 1.0 : -1.0;
    }

    public String toString() {
        StringBuilder txt = new StringBuilder();
        txt.append("bi = " + bias);
        for (int i = 0; i < w.length; i++) {
            txt.append("\nw" + i + " = " + w[i]);
        }
        return txt.toString();
    }

    public static void main(String[] args) {
        double[][] space = {
            {-1, -1},
            {-1, 1},
            {1, -1},
            {1, 1}
        };
        double[] func = {-1, -1, -1, 1};

        Perceptron p = new Perceptron(2);
        for (int itera = 0; itera < 100; itera++) {
            p.learn(space, func, 0.01);
        }
        System.out.println("neuron = \n" + p.toString());
        for (int j = 0; j < func.length; j++) {
            System.out.println("" + p.getBinary(space[j]));
        }
    }
}
