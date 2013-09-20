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

/**
 *
 * @author ZULU
 */
public class NeuralNetwork {

    ArrayList<Perceptron> nn;
    double learningRate = 0.1;

    public NeuralNetwork(int size) {
        for (int i = 0; i < size; i++) {
            Perceptron p = new Perceptron(size);
            nn.add(p);
        }
    }

   
    public void learn(double[][] input, double[][] value) {
        for (int i = 0; i < value.length; i++) {
            nn.get(i).learn(input, value[i], learningRate);
        }
    }
    

    public static void main(String[] args) {
        double[][] space = {
            {-1, -1},
            {-1, 1},
            {1, -1},
            {1, 1}
        };
        double[][] func = {
            {-1, -1, -1, 1},
            {1, 1, 1, -1}
        };
        
        NeuralNetwork nn = new NeuralNetwork(2);
        
    }
}
