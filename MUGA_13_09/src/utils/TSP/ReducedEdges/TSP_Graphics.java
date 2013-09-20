/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.TSP.ReducedEdges;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import problem.permutation.TSP.AbstractTSP;
import utils.Funcs;

/**
 *
 * @author manso
 */
public class TSP_Graphics {

    static double INFINITY = 99;
    Point2D ini, fin;
    double width;
    double height;
    int GAP = 20;
    static int MAX_LINE_SIZE = 10; // max size o line
    Rectangle window = null;
    AbstractTSP tsp = null;

    private void drawCities(Color color, Graphics g, int size) {
//        if (size > MAX_LINE_SIZE) {
//            size = MAX_LINE_SIZE;
//        }
        Graphics2D gr = (Graphics2D) g;
        color = new Color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, 0.75f);        
        gr.setStroke(new BasicStroke(size));
        gr.setFont(new java.awt.Font("Courier new", 1, GAP/2+4));
        // color = new Color( color.getRed()/256.0f , color.getGreen()/256.0f , color.getBlue()/256.0f , 0.5f);
        for (int i = 0; i < AbstractTSP.getX().length; i++) {
            Point pi = getCoords(i);
            gr.setColor(color);
            gr.fillOval(pi.x - GAP, pi.y - GAP, GAP * 2, GAP * 2);
            gr.setColor(Color.BLACK);
            gr.drawOval(pi.x - GAP, pi.y - GAP, GAP * 2, GAP * 2);
            gr.drawString("" + i, pi.x - GAP / 2, pi.y + GAP / 3);
        }
        gr.setStroke(new BasicStroke(1));
    }

    private void drawEdges(Color color, Graphics g, int size) {
        Graphics2D gr = (Graphics2D) g;
        gr.setColor(color);
        gr.setStroke(new BasicStroke(size));
        for (int i = 0; i < AbstractTSP.distance.length; i++) {
            for (int j = 0; j < AbstractTSP.distance.length; j++) {
                if (AbstractTSP.distance[i][j] > 0 && AbstractTSP.distance[i][j] < INFINITY) {
                    Point pi = getCoords(i);
                    Point pf = getCoords(j);
                    gr.drawLine(pi.x, pi.y, pf.x, pf.y);
                }

            }

        }
        gr.setStroke(new BasicStroke(1));
    }

    private void drawOptimum(Color color, Graphics gr, int size) {
        color = new Color(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, 0.5f); 
        drawPath(color, gr, size, tsp.getOptimumPath());
    }

    private void drawPath(Color color, Graphics gr, int size, int[] path) {

        for (int i = 0; i < path.length; i++) {
            drawPath(path[i], path[(i + 1) % path.length], color, (Graphics2D) gr, size);
        }
    }

    private void drawPath(int ini, int fin, Color color, Graphics2D gr, float size) {
        Point pi = getCoords(ini);
        Point pf = getCoords(fin);
        gr.setColor(color);
        gr.setStroke(new BasicStroke(size));
        gr.drawLine(pi.x, pi.y, pf.x, pf.y);
        gr.setStroke(new BasicStroke(1));
    }

    private Point getCoords(int city) {
        double x = window.x + ((AbstractTSP.getX()[city] - AbstractTSP.getBounds().getMinX()) / width) * (window.getWidth() - 2 * GAP);
        double y = window.height - ((AbstractTSP.getY()[city] - AbstractTSP.getBounds().getMinY()) / height) * (window.getHeight() - 2 * GAP);
        return new Point(GAP + (int) x, GAP + (int) y);
    }

    public void setTSP(ReducedTSP tsp) {
        this.tsp = tsp;
        ini = new Point2D.Double(AbstractTSP.getBounds().getMinX(), AbstractTSP.getBounds().getMinY());
        fin = new Point2D.Double(AbstractTSP.getBounds().getMaxX(), AbstractTSP.getBounds().getMaxY());
        width = fin.getX() - ini.getX();
        height = fin.getY() - ini.getY();
    }

    public BufferedImage getImage(AbstractTSP tsp, int w, int h, String filename, int[] path) {
        try {
            BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            this.tsp = tsp;
            ini = new Point2D.Double(AbstractTSP.getBounds().getMinX(), AbstractTSP.getBounds().getMinY());
            fin = new Point2D.Double(AbstractTSP.getBounds().getMaxX(), AbstractTSP.getBounds().getMaxY());
            width = fin.getX() - ini.getX();
            height = fin.getY() - ini.getY();

            window = new Rectangle(40, 40, w - 80, h - 80);
            Graphics gr = img.createGraphics();
            gr.setColor(Color.BLACK);
            gr.fillRect(0, 0, w, h);

            
            drawEdges(Color.RED, gr, 3);
            drawOptimum(Color.YELLOW, gr, 20);
            drawCities(Color.WHITE, gr, 3);
            if (path != null) {
                drawPath(Color.GREEN, gr, 5, path);
                gr.setFont(new java.awt.Font("Arial", 0, 20));
                gr.setColor(new Color(0.1f, 0.1f, 0.1f, 0.5f));
                gr.fillRoundRect(window.x + 10, window.height - 30, 200, 80, 30, 30);
                double cost = AbstractTSP.calculateLenght(path);
                gr.setColor(Color.WHITE);
                gr.drawString("Lenght " + Funcs.DoubleToString(cost, 10), 80, window.height);

            }
            
            ImageIO.write(img, "jpg", new File(filename));
            return img;
        } catch (Exception ex) {
            Logger.getLogger(TSP_Graphics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public BufferedImage getImage(AbstractTSP tsp, int w, int h) {
        return getImage(tsp, w, h, tsp.getClass().getCanonicalName() + ".jpg", null);
    }

    public BufferedImage getImage(AbstractTSP tsp, int w, int h, int[] path) {
        return getImage(tsp, w, h, tsp.getClass().getCanonicalName() + ".jpg", path);
    }

    public static void main(String[] args) {
        //mostrar o fractal numa janela
//        JFrame window = new JFrame("Fractal Image ");
//        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        window.setBounds(100, 100, 1500, 900);
//        window.setVisible(true);

        TSP_Graphics gr = new TSP_Graphics();
        ReducedTSP p = new ReducedTSP_A6();
//        ReducedTSP p = new ReducedTSP_A66();
//        ReducedTSP p = new ReducedTSP_rnd();
//        ReducedTSP p = new ReducedPCB_3038();
//        ReducedTSP p = new Reduced_A280();
//        p.calculateDistance2();
        gr.getImage(p, 1400, 1400);
//        window.dispose();
//        window.getGraphics().drawImage(gr.getImage(p, window.getWidth(),window.getHeight()), 0, 0, null);




    }
}
