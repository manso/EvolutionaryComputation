/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import java.awt.BorderLayout;
import java.awt.Component;
import jv.geom.PgElementSet;
import jv.object.PsMainFrame;
import jv.viewer.PvDisplay;
import problem.Individual;

/**
 *
 * @author arm
 */
public class Display2DFunc extends Display3DFunc {
    int numLines2;
    public Display2DFunc() {
        this.setLayout(new BorderLayout());
       numLines2 = 10 * numLines;
    }

    public void computeIndividuals() {
        for( Individual i: getPop().getSortedIterable()){
            disp.addGeometry(computeIndividual(i.getGeneValue(0),0,i.getFitness()));
        }
    }

    public void showFunc() {
        if (space == null || !this.getPop().getIndividual(0).getClass().equals(indSpace.getClass())) {
            computeSpace();
        }
        disp = new PvDisplay();
        disp.addGeometry(space);
        disp.selectGeometry(space);
        computeIndividuals();
          
        this.removeAll();
        this.add((Component) disp, BorderLayout.CENTER);
        this.revalidate();
        disp.fit();
    }

    public void computeSpace() {
        indSpace = getPop().getIndividual(0).getClone();
        PsMainFrame frame = new PsMainFrame("test");
//        PvDisplay disp = new PvDisplay();
        space = new PgElementSet(3);
        // Allocate space for vertices.
        space.setNumVertices(2 * numLines2);
        // Compute the vertices.
        int ind = 0;
        
        double xMin = indSpace.getMinValue();
        double xMax = indSpace.getmaxValue();
        double step = (xMax - xMin) / (numLines2 - 1);
        double x = 0, z = 0;
        computeNorms();
        // y = -step
        for (int nx = 0; nx < numLines2; nx++) {
            x = xMin + nx * step;
            //gene 0
            indSpace.setGeneValue(0,x);
            indSpace.evaluate();
            z = indSpace.getFitness();
            space.setVertex(ind++, x * normx , -step*15, z * normz);
        }
         // y = +step
        for (int nx = 0; nx < numLines2; nx++) {
            x = xMin + nx * step;
            //gene 0
            indSpace.setGeneValue(0,x);
            indSpace.evaluate();
            z = indSpace.getFitness();
            space.setVertex(ind++, x * normx, step*15, z * normz);
        }
        space.makeQuadrConn(2,numLines2);
        // Why not, just assign some colors.
        space.makeElementColorsFromZHue();
        space.showElementColors(true);
    }

    public void computeNorms() {
        double xMin = indSpace.getMinValue();
        double xMax = indSpace.getmaxValue();
        double stepX = (xMax - xMin) / (numLines2 - 1);
        double zMin = Double.MAX_VALUE;
        double zMax = Double.MIN_VALUE;
        double x = 0, y = 0, z = 0;
        for (int nx = 0; nx < numLines2; nx++) {
            x = xMin + nx * stepX;
            //gene o
            indSpace.setGeneValue(0,x);
            indSpace.evaluate();
            z = indSpace.getFitness();
            if (z < zMin) {
                zMin = z;
            }
            if (z > zMax) {
                zMax = z;
            }
        }
        double stepZ = (zMax - zMin) / (numLines2 - 1);
        double max = Math.max(stepX,stepZ);
        normx = max / stepZ;
        normz = max / stepZ;
        normi = stepX *2;

    }

    
}
