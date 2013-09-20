/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Graphics;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Iterator;
import problem.Individual;
import problem.permutation.TSP.AbstractTSP;

/**
 *
 * @author manso
 */
public class DisplayTSP1 extends DisplayPopulation {

    TSP_Graphics tspDisplay = new TSP_Graphics();

    @Override
    public void showFunc(Graphics gr) {
        AbstractTSP tsp = (AbstractTSP)pop.getIndividual(0);
        int dx = getWidth() / 4;
        int dy = getHeight()/3;
        Iterator<Individual> it = pop.getSortedIterable().iterator();
        //best
        tspDisplay.showTSP((AbstractTSP)it.next(), gr, 0, 0, getWidth()-dx, getHeight() );
        //next 3
        for (int i = 0; i < 3; i++) {
            tspDisplay.showTSP((AbstractTSP)it.next() , gr,dx*3 ,dy *i , getWidth(), dy * (i+1) );            
        }
        
        

    }

    
}
