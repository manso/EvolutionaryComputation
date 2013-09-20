/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Iterator;
import jv.geom.PgElementSet;
import jv.vecmath.PdVector;
import jv.viewer.PvDisplay;
import problem.Individual;

/**
 *
 * @author arm
 */
public class Display3DFunc extends DisplayPopulation {

    protected PgElementSet space = null;
    protected PvDisplay disp = null;
    protected Individual indSpace = null;
    protected int numLines = 64;
    protected double normx = 1;
    protected double normy = 1;
    protected double normz = 1;
    protected double normi = 1;
    protected double MIN_VALUE = 0;

    public Display3DFunc() {
        this.setLayout(new BorderLayout());
    }

    @Override
    public void showFunc(Graphics gr) {
        if (disp == null || !this.getPop().getIndividual(0).getClass().equals(indSpace.getClass())) {
            computeSpace();
            disp = new PvDisplay();
            this.removeAll();
            this.add((Component) disp, BorderLayout.CENTER);
            this.revalidate();
        }

        disp.removeGeometries();
        disp.addGeometry(space);
        disp.selectGeometry(space);
        computeIndividuals();
        disp.repaint();
    }

    public void computeIndividuals() {
        Iterator<Individual> iter = getPop().getIterator();
        while (iter.hasNext()) {
            Individual i = iter.next();
            PgElementSet ind = computeIndividual(i.getGeneValue(0), i.getGeneValue(1), i.getFitness());
            disp.addGeometry(ind);
        }
    }

    public void computeSpace() {
        int DIM = 5;
        indSpace = getPop().getIndividual(0).getClone();
        space = new PgElementSet(3);
        // Allocate space for vertices.
        space.setNumVertices((numLines + DIM*2) * (numLines + DIM*2));
        // Compute the vertices.
        int ind = 0;
        double xMin = indSpace.getMinValue();
        double xMax = indSpace.getmaxValue();
        double yMin = indSpace.getMinValue();
        double yMax = indSpace.getmaxValue();
        double stepX = (xMax - xMin) / (numLines -1);
        double stepy = (yMax - yMin) / (numLines -1);
        double step = Math.max(stepX, stepy);
        normx = step / stepX;
        normy = step / stepy;
        double x = 0, y = 0, z = 0;
        computeNorms();
        
       
        for (int ny = -DIM; ny < numLines + DIM; ny++) {
            for (int nx = -DIM; nx < numLines + DIM ; nx++) {
                x = xMin + nx * stepX;
                y = yMin + ny * stepy;
                if (ny >= 0 && ny < numLines && nx >= 0 && nx < numLines) {
                    indSpace.setGeneValue(0, x);
                    indSpace.setGeneValue(1, y);
                    indSpace.evaluate();
                    z = indSpace.getFitness();
                    space.setVertex(ind++, x * normx, y * normy, z * normz);
//                    space.setVertex(ind++, (x+stepX) * normx, (y+stepy) * normy, z * normz);
                } //borders
                else {
                    space.setVertex(ind++, x * normx, y * normy, MIN_VALUE * normz);
                }
            }
        }
        space.makeQuadrConn(numLines+DIM*2, numLines+DIM*2);

        space.makeElementColorsFromZHue();
        space.showElementColors(true);
        space.showEdges(true);

    }

    public void computeNorms() {

        double xMin = indSpace.getMinValue();
        double xMax = indSpace.getmaxValue();
        double yMin = indSpace.getMinValue();
        double yMax = indSpace.getmaxValue();
        double stepX = (xMax - xMin) / (numLines - 1);
        double stepy = (yMax - yMin) / (numLines - 1);
        double zMin = Double.MAX_VALUE;
        double zMax = -Double.MAX_VALUE;
        double x = 0, y = 0, z = 0;
        for (int ny = 0; ny < numLines; ny++) {
            for (int nx = 0; nx < numLines; nx++) {
                x = xMin + nx * stepX;
                y = yMin + ny * stepy;
//                xx.setValue(x);
//                yy.setValue(y);
                indSpace.setGeneValue(0, x);
                indSpace.setGeneValue(1, y);
                indSpace.evaluate();
                z = indSpace.getFitness();
                if (z < zMin) {
                    zMin = z;
                }
                if (z > zMax) {
                    zMax = z;
                }
            }
        }
        double step = Math.max(stepX, stepy);
        normx = step / stepX;
        normy = step / stepy;
        double stepZ = (zMax - zMin) / (numLines - 1);
        normz = step / (stepZ * 2);
        normi = step / 2;
        MIN_VALUE = zMin;

    }

    public PgElementSet computeIndividual(double x, double y, double z) {
        PgElementSet ind = new PgElementSet(3);
        ind.computeSphere(6, 6, normi * 2);
        PdVector v = new PdVector(x * normx, y * normy, z * normz);
        ind.translate(v);
//        ind.setGlobalEdgeColor(Color.black);
//        ind.setGlobalBndColor(Color.BLACK);
        ind.makeElementColorsFromZ();


        ind.showElementColors(true);
        ind.showEdges(true);

        return ind;
    }
}
