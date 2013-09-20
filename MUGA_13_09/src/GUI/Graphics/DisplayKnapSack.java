/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import problem.Individual;
import problem.bitString.KSPenalty.K50;
import problem.bitString.KSPenalty.Knapsack;


import utils.BitField;

/**
 *
 * @author arm
 */
public class DisplayKnapSack extends DisplayPopulation {

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
        BitField bits = ind.getBits();

        if (ind.isBest()) {
            gr.setColor(new Color(0.0f, 0.99f, 0.5f, 0.5f));
        } else if (!ind.isViable()) {
            gr.setColor(new Color(0.99f, 0.25f, 0.25f, 0.5f));
        } else {
            gr.setColor(new Color(0.0f, 0.5f, 0.99f, 0.5f));
        }
        double h = Knapsack.heightSack(bits) - minC;
        double hx = (h * dimX) / (maxC - minC);
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
        int minimumC = Integer.MAX_VALUE;
        int maximumC = Integer.MIN_VALUE;
        while (it.hasNext()) {
            ind = (Knapsack) it.next();
            capacity = (int) Knapsack.heightSack(ind.getBits());
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
        //}



    }

    public static int getLimitMin(int value, int step) {
        String num = value + "";
        int digit = num.charAt(0) - '0';

        if (digit - step <= 0) {
            digit = 10 + digit - step;
            return digit * (int) Math.pow(10, num.length() - 2);
        }
        digit -= step;
        return digit * (int) Math.pow(10, num.length() - 1);
    }

    public static int getLimitMin(int min, int max, int step) {
        String smin = min + "";
        String smax = max + "";
        StringBuilder num = new StringBuilder();
        int index = 0;
        //copy equal numbers
        for (index = 0; index < smin.length(); index++) {
            if (smin.charAt(index) == smax.charAt(index)) {
                num.append(smin.charAt(index));
            } else {
                break;
            }
        }
        int digit = (smin.charAt(index) - '0');
        if (digit >= step) {
            digit -= step;
        } else {
            digit = 0;
        }
        num.append(digit);
        for (; index < smin.length() - 1; index++) {
            num.append('0');
        }
        return Integer.parseInt(num.toString());
    }

    public static int getLimitMax(int min, int max, int step) {
        String smin = getLimitMin(min, max, step)+"";        
        String smax = max + "";
        int index = smin.length()-1;
         while(index > 0 && smin.charAt(index) == '0') {
                index--;
        }
        int value = Integer.parseInt( smax.substring(0, index+1)) +1;
        return value * (int)Math.pow(10, smax.length()-index-1);
        
        
    }

    public static int getLimitMax(int value, int step) {
        String num = value + "";
        int digit = num.charAt(0) - '0';
        digit += step +1;
        return digit * (int) Math.pow(10, num.length() - 1);
    }

   
}
