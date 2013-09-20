/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.Knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author small
 */
public class KnapSack_G_Inverse {

    public static double MAX_PROBABILITY = 0.99;
    public static double MIN_PROBABILITY = 0.05;
    public static Random rnd = new Random();

    public static boolean[] greedyFillStatic(double[] profit, double[] weight, double size) {
        //sort
        insertionSort(profit, weight);
        //bits
        boolean bits[] = new boolean[weight.length];
        double prob;
        double maxSize = size;
        for (int i = 0; i < bits.length; i++) {
            if (size > weight[i]) {
                prob = MAX_PROBABILITY;
            } else {
                //negative size decrease probability
                prob = MIN_PROBABILITY ;
//                System.out.println(i + "  " + prob);
            }

            if (rnd.nextDouble() < prob) {
                bits[i] = true;
                size -= weight[i];
            }
        }
        return bits;
    }

    public static boolean[] greedyFillSigmoid(double[] profit, double[] weight, double sackSize) {
        //sort
        insertionSort(profit, weight);
        //bits
        boolean bits[] = new boolean[weight.length];
        double totalSize = 0;
        for (int i = 0; i < bits.length; i++) {
            totalSize += weight[i];
        }
        double prob;
        for (int i = 0; i < bits.length; i++) {
            //sigmoid Function
            prob = 1.0 / (1 + Math.exp(-Math.sqrt(weight.length) * (sackSize/totalSize )));
//            System.out.println(i + " Size " + sackSize +  " PROB " + prob);
            if (rnd.nextDouble() < prob) {
                bits[i] = true;
                sackSize -= weight[i];
            }
            totalSize -= weight[i];
        }
        return bits;
    }
    
    public static boolean[] greedyFillSigmoidSQRT(double[] profit, double[] weight, double sackSize) {
        //sort
        insertionSort(profit, weight);
        //bits
        boolean bits[] = new boolean[weight.length];
        double totalSize = 0;
        for (int i = 0; i < bits.length; i++) {
            totalSize += weight[i];
        }
        double prob;
        for (int i = 0; i < bits.length; i++) {
            //sigmoid Function
            prob = 1.0 / (1 + Math.exp(-Math.sqrt(weight.length) * (sackSize/totalSize )));
//            System.out.println(i + " Size " + sackSize +  " PROB " + prob);
            if (rnd.nextDouble() < prob) {
                bits[i] = true;
                sackSize -= weight[i];
            }
            totalSize -= weight[i];
        }
        return bits;
    }

    public static boolean[] greedyFillSigmoid2(double[] profit, double[] weight, double size) {
        //sort
        insertionSort(profit, weight);
        //bits
        boolean bits[] = new boolean[weight.length];
        double prob;
        double maxSize = size;
        for (int i = 0; i < bits.length; i++) {
            //sigmoid Function
            prob = 1.0 / (1 + Math.exp(-Math.pow(weight.length,1.0/3) * (size / maxSize)));
//            System.out.println(i + " Size " + size +  " PROB " + prob);
            if (rnd.nextDouble() < prob) {
                bits[i] = true;
                size -= weight[i];
            }
        }
        return bits;
    }
    
    public static boolean[] greedyFillSigmoidStatic(double[] profit, double[] weight, double size) {
       //sort
        insertionSort(profit, weight);
        //bits
        boolean bits[] = new boolean[weight.length];
        double currentSize = 0;
        for (int i = 0; i < bits.length; i++) {
            currentSize += weight[i];
        }
        double remainsSack = currentSize-size;
        double MAX =currentSize;
        double prob;
        for (int i = 0; i < bits.length; i++) {
            //sigmoid Function
            prob = 1.0 / (1 + Math.exp(- Math.sqrt(weight.length) * ((currentSize-remainsSack)/MAX)));
//            System.out.println(i + " Size " + size +  " PROB " + prob);
            if (rnd.nextDouble() < prob) {
                bits[i] = true;
                size -= weight[i];
            }
            currentSize -= weight[i];
        }
        return bits;
    }
    
     public static void swap(double[] profit, double[] weight, int i , int j) {
         double tmp = profit[i];
         profit[i]=profit[j];
         profit[j]=tmp;
         
         tmp = weight[i];
         weight[i]=weight[j];
         weight[j]=tmp;                  
     }
     
     public static void shuffle(double[] profit, double[] weight) {
         for (int i= weight.length-1; i > 0 ;i--) {
             swap(profit, weight, i,rnd.nextInt(i));
         }
     }
     

    public static void insertionSort(double[] profit, double[] weight) {
        shuffle(profit, weight);
        for (int i = 1; i < weight.length; i++) {
            //save pivot
            double key = profit[i] / weight[i];
            double w = weight[i];
            double p = profit[i];
            //element sorted
            if (profit[i - 1] / weight[i - 1] < key) {
                continue;
            }
            //index to backward in the array
            int index = i;
            //push array forward and find the position of tmp
            while (index >= 1 && key < profit[index - 1] / weight[index - 1]) {
                //copy element forward
                weight[index] = weight[index - 1];
                profit[index] = profit[index - 1];
                //decrease the index
                index--;
            }
            //put tmp in the right position
            weight[index] = w;
            profit[index] = p;
        }
    }

}
