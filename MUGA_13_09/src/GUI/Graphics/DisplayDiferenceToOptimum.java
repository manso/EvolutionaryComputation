/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import problem.Individual;
import problem.IndividualCEC;
import problem.RC_Individual;
import problem.RC_IndividualCEC;

/**
 *
 * @author arm
 */
public class DisplayDiferenceToOptimum extends DisplayPopulation {

    static Color BEST_BLACK = new Color(0, 255, 0);
    static Color BEST_WHITE = new Color(255, 128, 128);
    int x0, y0, x1, y1;
    double [] opt = {0};

    @Override
    public void showFunc(Graphics gr) {
        if (getPop() == null || getPop().getNumGenotypes() == 0) {
            return;
        }
        Individual ind = getPop().getIndividual(0);
        if (ind instanceof RC_Individual) {
            opt = ((RC_Individual) ind).getOptimum();
        }
        
        gr.setColor(Color.LIGHT_GRAY);
        x0 = 0;
        y0 = x0;
        x1 = this.getWidth();
        y1 = this.getHeight();
        gr.setColor(Color.GRAY);
        //gr.clearRect(0, 0, this.getWidth(), this.getHeight());
        gr.fill3DRect(x0, y0, x1, y1, true);
        int max = getPop().getNumGenotypes() > 1000 ? 1000 : getPop().getNumGenotypes();
        double dimY = ((double) this.getHeight()) / max;
        //  for (Individual ind : pop.getSortedIterable())
        Iterator<Individual> iter = getPop().getSortedIterable().iterator();
        int j = -1;
        while (iter.hasNext()) {
            {
                ind = iter.next();
                displayIndividual(gr, ind, (int) Math.floor(dimY * ++j), (int) Math.floor(dimY));
            }

        }
        
    }

    protected void displayIndividual(Graphics gr, Individual ind, int py, int sizey) {
        double[] gene = getGenome(ind);
        double dx = (double) this.getWidth() / (gene.length);
//        int gap = 1;
//        if (sizey > 2) {
//            sizey--;
//        }
        //     boolean best = ind.isBest();
        for (int i = 0; i < gene.length; i++) {
            gr.setColor(getLogColor(gene[i]));
//            gr.fill3DRect((int)(i * dx), py,(int)( dx - gap), sizey, true);
//            gr.fillRect((int) (i * dx), py, (int) (dx + 1), sizey);
            gr.fill3DRect((int) (i * dx), py, (int) (dx + 1), sizey,true);
            //gr.setColor(Color.BLACK);
            //int l = getLog(gene[i]);
            // gr.drawString(Funcs.DoubleToString(l, 6), (int) (i * dx), py + sizey/2);
        }
    }

    private double[] getGenome(Individual i) {
        double[] vi = i.getValues();
        double[] genes = new double[vi.length];
        for (int j = 0; j < genes.length; j++) {
            genes[j] = opt[j%opt.length] - vi[j];
        }
        return genes;
    }

    public static Color getLogColor(double value) {
        if (value > 0) {
            int l = (int) (128 - Math.log(value) * 10);
            if (l > 255) {
                l = 255;
            }
            if (l < 0) {
                l = 0;
            }
            return new Color(255, l, l);
        } else {
            int l = (int) (128 - Math.log(-value) * 10);
            if (l > 255) {
                l = 255;
            }
            if (l < 0) {
                l = 0;
            }
            return new Color(l, l, 255);
        }
    }

    public static int getLog(double value) {
        if (value > 0) {
            int l = (int) Math.log10(value);
            return l;
        } else {
            int l = (int) Math.log10(-value);
            return l;
        }
    }
}
