/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import genetic.Solver.SimpleSolver;
import genetic.population.Population;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import utils.Funcs;



/**
 *
 * @author arm
 */
public abstract class DisplayPopulation extends JPanel {

    protected Population pop;
    protected SimpleSolver solver;

    public abstract void showFunc(Graphics gr);

    @Override
    public void paint(Graphics g) {
        //super.paint(g);
        if (getPop() != null ) {
            showFunc(g);           
        }

        DrawAuthor("MuGA (c)2013", g);
    }

    protected void DrawAuthor(String author, Graphics gr) {
        int px = this.getWidth() - 100;
        int py = this.getHeight() - 20;
        gr.setFont(new java.awt.Font("Courier", 0, 12));
        gr.setColor(Color.DARK_GRAY);
        gr.drawString(author, px + 1, py + 1);
        gr.setColor(Color.LIGHT_GRAY);
        gr.drawString(author, px, py);
        if (solver != null) {
            gr.setFont(new java.awt.Font("Courier", 0, 20));
            gr.setColor(new Color(0.1f, 0.1f, 0.1f, 0.50f));
            gr.fillRoundRect(20, py - 90, 200, 80, 30, 30);
            gr.setColor(Color.WHITE);
            if (solver.getStop() != null) {
                gr.drawString("Done " + Funcs.DoubleToString(solver.getStop().getProgress() * 100, 6) + " %", 40, py - 60);
            }
            if (solver.getHallOfFame() != null) {
                gr.drawString("Best " + Funcs.DoubleExponentialToString(solver.getBest().getBestFitness(), 11, "0.000E0"), 40, py - 30);
            }
        }

    }

    protected void DrawDouble(Graphics gr, double value, int px, int py) {
        gr.setFont(new java.awt.Font("Courier", 0, 10));
        gr.setColor(new Color(0.1f, 0.1f, 0.1f, 0.50f));
        gr.fillRoundRect(px, py, px + 80, py + 40, 10, 10);
        gr.setColor(Color.WHITE);
        gr.drawString(Funcs.DoubleToString(value, 6), px + 5, py + 5);
    }

    /**
     * @return the pop
     */
    public Population getPop() {
        return pop;
    }

    /**
     * @param pop the pop to set
     */
    public void setSolver(SimpleSolver s, Population p) {
        if (this.isVisible()) {
            this.solver = s;
            this.pop = p.getClone();
        }
    }
}
