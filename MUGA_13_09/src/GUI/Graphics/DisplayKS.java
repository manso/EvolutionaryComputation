/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import problem.Individual;
import problem.bitString.KSPenalty.Knapsack;




import utils.BitField;

/**
 *
 * @author arm
 */
public class DisplayKS extends DisplayPopulation {

    int x0, y0, dimX, dimY;
    double maxCapacity;
    double maxValue;
    double minC = Integer.MAX_VALUE, maxC = Integer.MIN_VALUE;
//    double minV, maxV;

    @Override
    public void showFunc(Graphics gr) {
        calculateValues();

        gr.setColor(Color.WHITE);
        x0 = 8;
        y0 = x0;
        dimX = this.getWidth() - 2 * x0;
        dimY = this.getHeight() - 3 * y0;
        gr.setColor(Color.DARK_GRAY);
        gr.fillRect(0, 0, this.getWidth(), this.getHeight());
        gr.setColor(Color.WHITE);
        gr.fill3DRect(x0, y0, dimX, dimY, true);
        //draw capacity
        displaySack(gr);
        double stepY = ((double) dimY / getPop().getNumGenotypes());
        int i = 0;
        Iterator<Individual> iter = getPop().getSortedIterable().iterator();
        while (iter.hasNext()) {
            Individual ind = iter.next();
            displayString(gr, (Knapsack) ind, y0 + (int) (stepY * i), (int) stepY);
            i++;
        }

    }

    private void displayString(Graphics gr, Knapsack ind, int py, int sizey) {
        int SHADE = 2;
        BitField bits = ind.getBits();
        double h = ind.heightSack() - minC;
        double hx = (h * dimX) / (maxC - minC);
        gr.setColor(Color.DARK_GRAY);
        gr.fillRect(x0, py+SHADE, (int) hx+SHADE, (int)(sizey+SHADE));
        if (ind.isBest()) {
            gr.setColor(new Color(0.0f, 0.99f, 0.5f, 0.5f));
        } else if (!ind.isViable()) {
            gr.setColor(new Color(0.99f, 0.25f, 0.25f, 0.5f));
        } else {
            gr.setColor(new Color(0.0f, 0.5f, 0.99f, 0.5f));
        }

        gr.fillRect(x0, py, (int) hx, (int) (sizey));
    }

    private void displaySack(Graphics gr) {
        gr.setColor(new Color(0.85f, 0.85f, 0.85f, 1f));
        double h = Knapsack.getCapacity() - minC;
        double hx = (h * dimX) / (maxC - minC);
        gr.fill3DRect(x0, y0, (int) hx, dimY, true);
        gr.setColor(Color.WHITE);
        // gr.setXORMode(Color.WHITE);
        int py = y0 + dimY + 12;
        gr.drawString("" + minC, x0, py);
        gr.drawString("" + maxC, x0 + dimX - 40, py);
        gr.drawString("" + Knapsack.getCapacity(), (int) hx - 20, py);
    }

    private void calculateValues() {
        Iterator<Individual> it = getPop().getIterator();
        Knapsack ind;
        int capacity = (int) Knapsack.calcTotalCapacity();
        double minimumC = Double.POSITIVE_INFINITY;
        double maximumC = Double.NEGATIVE_INFINITY;
        while (it.hasNext()) {
            ind = (Knapsack) it.next();
            capacity = (int) ind.heightSack();
            if (capacity < minimumC) {
                minimumC = capacity;
            }
            if (capacity > maximumC) {
                maximumC = capacity;
            }
        }
        //if (minC > minimumC || maxC < maximumC) {
        minC = getLimitMin(minimumC, 0);
        maxC = getLimitMax(maximumC, 0);
    }

    public static int getLimitMin(double value, int step) {
        int div = (int) Math.pow(10, (int) Math.log10(value) - 1);
        if (div <= 0) {
            div = 1;
        }
        int dec = (int) value;
        return (dec / div) * div;
    }

    public static int getLimitMax(double value, int step) {
        int div = (int) Math.pow(10, (int) Math.log10(value) - 1);
        if (div <= 0) {
            div = 1;
        }
        int dec = ((int) value) / div;
        return (dec + 1) * div;
    }
//    public static void main(String[] args) {
////        for (int i = 0; i < 100; i++) {
////            int n = (int) (10000 * Math.random());
////            System.out.println("\t [" + getLimitMin(n, 2) + " ]\t" + n + "\t[" + getLimitMax(n, 2) + "]");
////
////        }
//        int min = 3;
//        int max = 11179;
//        System.out.println(getLimitMin(min, 0));
//        System.out.println(getLimitMax(max, 0));
//
//
//        System.out.println("");
//
//    }
}
