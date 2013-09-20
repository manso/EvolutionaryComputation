/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import genetic.gene.Gene;
import java.awt.Color;
import java.awt.Graphics;
import problem.Individual;
import problem.bitString.Deceptive.Deceptive;
import utils.BitField;

/**
 *
 * @author arm
 */
public class DisplayDeceptive extends DisplayPopulation {

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
            displayIndividual(gr, ind, (int) (dimY * i++), (int) dimY);
        }

    }

    protected void displayIndividual(Graphics gr, Individual ind, int py, int sizey) {
        Deceptive dec = (Deceptive) ind;
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
            boolean[] pattern = dec.PATTERN[i];
            //print separator between genes
            px += separatorSize / 2;
            for (int j = 0; j < gene.getNumBits(); j++) {
                gr.fillRect(px, py, (int) (dx - gap), sizey);
                
                if (pattern[j] != gene.getAlels().getBit(j)) {
                    gr.setColor(new Color(0,255,0));
                } else {
                   gr.setColor(new Color(255,0,0));
                }
                gr.fill3DRect(px, py, (int) (dx - gap), sizey, true);

                //increase px
                px += (int) dx;
            }
            //print another half of separator
            px += (separatorSize + 1) / 2;
        }
    }

}
