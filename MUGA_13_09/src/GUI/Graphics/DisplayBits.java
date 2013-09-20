/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import genetic.gene.Gene;
import java.awt.Color;
import java.awt.Graphics;
import problem.Individual;
import problem.PRM_Individual;
import problem.RC_Individual;
import utils.BitField;

/**
 *
 * @author arm
 */
public class DisplayBits extends DisplayPopulation {

    int x0, y0, x1, y1;

    @Override
    public void showFunc(Graphics gr) {
        if (getPop() == null) {
            return;
        }
        x0 = 0;
        y0 = x0;
        x1 = this.getWidth();
        y1 = this.getHeight();
        gr.setColor(new Color(120, 120, 120));
        //gr.clearRect(0, 0, this.getWidth(), this.getHeight());
        gr.fill3DRect(x0, y0, x1, y1, true);
        //----------------------------------------------------------------
        //dimensions of Line of bits
        double dimY = (double) this.getHeight() / (getPop().size());
        int i = 0;
        for (Individual ind : getPop().getSortedIterable()) {
            if (ind instanceof RC_Individual) {
                displayRealCodedIndividual(gr, (RC_Individual) ind, (int) (dimY * i++), (int) dimY);
            }else if (ind instanceof PRM_Individual) {
                displayPermutationIndividual(gr, (PRM_Individual) ind, (int) (dimY * i++), (int) dimY);
            } else {
                displayBinaryIndividual(gr, ind, (int) (dimY * i++), (int) dimY);
            }
        }

    }

    protected void displayBinaryIndividual(Graphics gr, Individual ind, int py, int sizey) {
        BitField bits = ind.getBits();
        int BLOCKS = ind.getNumGenes();
        double dx = (double) this.getWidth() / (bits.getNumberOfBits() + BLOCKS);
        int separatorSize = (int) ((this.getWidth() - bits.getNumberOfBits() * (int) dx) / BLOCKS);
        int gap = 2;
        if (dx < 2) {
            gap = -1;
        }
        if (sizey > 4) {
            sizey = (sizey * 8) / 10;
        }
        int px = 0;
        for (int i = 0; i < ind.getNumGenes(); i++) {
            Gene gene = ind.getGene(i);
            //print separator between genes
            px += separatorSize / 2;
            for (int j = 0; j < gene.getNumBits(); j++) {
                //draw alel
                gr.setColor(getColor(gene.getAlels().getBit(j)));
                gr.fill3DRect(px, py, (int) (dx - gap), sizey, true);
                //increase px
                px += (int) dx;
            }
            //print another half of separator
            px += (separatorSize + 1) / 2;
        }
    }

    protected void displayRealCodedIndividual(Graphics gr, RC_Individual ind, int py, int sizey) {
        double[] v = ind.getValues();
        double min = ind.getMinValue();
        double max = ind.getmaxValue();
        double dim = max - min;

        double dx = (double) this.getWidth() / (v.length);
        int gap = 2;
        if (dx < 2) {
            gap = -1;
        }
        if (sizey > 4) {
            sizey = (sizey * 8) / 10;
        }
        double px = 0;
        for (int j = 0; j < v.length; j++) {
            //draw alel
            gr.setColor(getColor((v[j] - min) / dim));
            gr.fill3DRect((int)px, py, (int) (dx - gap), sizey, true);
            //increase px
            px +=  dx;
        }
    }
    
    protected void displayPermutationIndividual(Graphics gr, PRM_Individual ind, int py, int sizey) {
        int[] v = ind.getGeneValues();
        double stepColor = 255.0/v.length;
        double dx = (double) this.getWidth() / (v.length);
        int gap = 2;
        if (dx < 2) {
            gap = -1;
        }
        if (sizey > 4) {
            sizey = (sizey * 8) / 10;
        }
        double px = 0;
        for (int j = 0; j < v.length; j++) {
            //draw alel
            gr.setColor(getColor((int)(v[j]*stepColor)));
            gr.fill3DRect((int)px, py, (int) (dx - gap), sizey, true);
            //increase px
            px +=  dx;
        }
    }

    private Color getColor(boolean cValue) {
        if (cValue) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    private Color getColor(double val) {

        return Color.getHSBColor((float)(val), 1.0f, 1.0f);
    }
    
    private Color getColor(int val) {
        int c =  val%255;
        if (val < 128) {

            return new Color(255, c, c);
        }
        return new Color(c, 255, c);

    }
}
