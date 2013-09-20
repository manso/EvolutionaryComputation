/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import genetic.population.Population;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import problem.Individual;
import problem.permutation.TSP.AbstractTSP;
import utils.Funcs;

/**
 *
 * @author manso
 */
public class TSP_Graphics {
    int dimCity = 10;
    Point2D ini, fin;
    double width;
    double height;
    int GAP = 10;
    static int MAX_LINE_SIZE = 10; // max size o line
    Rectangle window = null;

    private void init() {
        ini = new Point2D.Double(AbstractTSP.getBounds().getMinX(), AbstractTSP.getBounds().getMinY());
        fin = new Point2D.Double(AbstractTSP.getBounds().getMaxX(), AbstractTSP.getBounds().getMaxY());
        width = fin.getX() - ini.getX();
        height = fin.getY() - ini.getY();
        GAP = 4 + (int) (Math.min(window.getWidth(), window.getHeight()) / (AbstractTSP.getX().length * 5));
    }

    public void showPopulation(Graphics gr, int x1, int y1, int x2, int y2, Population pop) {
        AbstractTSP tsp = ((AbstractTSP) pop.getIndividual(0));
        //not have coordinates to display
        if (AbstractTSP.getX() == null || AbstractTSP.getY() == null) {
            return;
        }
        window = new Rectangle(x1, y1, x2 - x1, y2 - y1);

        init();
        gr.setColor(Color.LIGHT_GRAY);
        //limpar o anterior
        gr.fill3DRect(window.x, window.y, window.width, window.height, true);
        drawPath(tsp, AbstractTSP.getOptimum(), Color.WHITE, (Graphics2D) gr, GAP);

        drawPopulation(tsp, gr, pop);
        drawCities(Color.yellow, gr, 2);
    }

    public void showTSP(AbstractTSP tsp, Graphics gr, int x1, int y1, int x2, int y2) {
        showTSP(tsp, Color.BLUE, gr, x1, y1, x2, y2);
    }

    public void showTSP(AbstractTSP tsp, Color color, Graphics gr, int x1, int y1, int x2, int y2) {
        //not have coordinates to display
        if (AbstractTSP.getX() == null || AbstractTSP.getY() == null) {
            return;
        }
        //limpar o anterior
        gr.setColor(Color.GRAY);
        gr.fillRect(x1, y1, x2 - x1, y2 - y1);
        gr.setColor(Color.LIGHT_GRAY);
        gr.fill3DRect(x1 + 4, y1 + 4, x2 - x1 - 8, y2 - y1 - 8, true);

        int borderX = (x2 - x1) / 20;
        int borderY = (y2 - y1) / 20;
        window = new Rectangle(x1 + borderX, y1 + borderY, x2 - x1 - borderX * 2, y2 - y1 - borderY * 2);
        init();
        int gp = (GAP / 3) - 1;
        drawCities(Color.GRAY, gr, 1);
        drawPath(tsp, AbstractTSP.getOptimum(), Color.GRAY, (Graphics2D) gr, GAP);
        drawPath(tsp, AbstractTSP.getOptimum(), Color.LIGHT_GRAY, (Graphics2D) gr, GAP - gp);
        drawPath(tsp, AbstractTSP.getOptimum(), Color.WHITE, (Graphics2D) gr, GAP - gp * 2);
        //       System.out.println("BEST : " + pop.getBestIndividual());


        int[] ind = tsp.getGeneValues();
        drawPath(tsp, ind, color, gr, GAP / 3 + 1);

        drawDouble(gr, tsp.getFitness(), x1, y1);
    }

    private void drawPopulation(AbstractTSP tsp, Graphics gr, Population pop) {
        Graphics2D g2 = (Graphics2D) gr;
        Iterator<Individual> iter = pop.getIterator();
        while (iter.hasNext()) {
            int[] ind = ((AbstractTSP) iter.next()).getGeneValues();
            drawPath(tsp, ind, Color.RED, gr, 2);

        }

    }

    private void drawPath(AbstractTSP tsp, int[] path, Color color, Graphics gr, int size) {
        int MAX = path.length;
        for (int i = 0; i < MAX; i++) {
            drawPath(path[i], path[(i + 1) % MAX], color, (Graphics2D) gr, size);
        }
    }

    private void drawCities(Color color, Graphics g, int size) {
//        if (size > MAX_LINE_SIZE) {
//            size = MAX_LINE_SIZE;
//        }
        Graphics2D gr = (Graphics2D) g;
        gr.setStroke(new BasicStroke(size));
        for (int i = 0; i < AbstractTSP.getX().length; i++) {
            Point pi = getCoords(i);
            gr.setColor(color);
            gr.fillOval(pi.x - GAP, pi.y - GAP, GAP * 2, GAP * 2);
            gr.setColor(Color.BLACK);
            gr.drawOval(pi.x - GAP, pi.y - GAP, GAP * 2, GAP * 2);
        }
        gr.setStroke(new BasicStroke(1));
    }

    private void drawPath( int ini, int fin, Color color, Graphics2D gr, float size) {
//        if (size > MAX_LINE_SIZE) {
//            size = MAX_LINE_SIZE;
//        }
        Point pi = getCoords(ini);
        Point pf = getCoords(fin);

        gr.setColor(color);
        gr.setStroke(new BasicStroke(size));
        gr.drawLine(pi.x, pi.y, pf.x, pf.y);
        gr.setStroke(new BasicStroke(1));
    }

    private Point getCoords(int city) {
        double x = window.x + ((AbstractTSP.getX()[city] - AbstractTSP.getBounds().getMinX()) / width) * (window.getWidth() - 2 * GAP);
        double y = window.y + ((AbstractTSP.getY()[city] - AbstractTSP.getBounds().getMinY()) / height) * (window.getHeight() - 2 * GAP);
        return new Point(GAP + (int) x, GAP + (int) y);
    }

    protected void drawDouble(Graphics gr, double value, int px, int py) {
        gr.setFont(new java.awt.Font("Courier", 0, 18));
        gr.setColor(new Color(0.f, 0.f, 0.25f, 0.50f));
        gr.fillRoundRect(px + 10, py + 10, 120, 30, 20, 20);
        gr.setColor(Color.WHITE);
        gr.drawString(Funcs.DoubleToString(value, 10), px + 15, py + 30);
    }
}
