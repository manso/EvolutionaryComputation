/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.replacement;

import genetic.population.Population;
import java.util.Iterator;
import java.util.StringTokenizer;
import problem.Individual;

/**
 *
 * @author manso
 */
public class Gerational extends Replacement {
    
    protected double ELITISM = 0;

    @Override
    public Population execute(Population parents, Population children) {
        int sizeNewPop = parents.getNumGenotypes();
        int ELITISM = (int) (Math.ceil(this.ELITISM * sizeNewPop));        
                
//        //error in percentage (1%)
        if( ELITISM <0 || ELITISM >= sizeNewPop){
          ELITISM = (int)Math.ceil( sizeNewPop*0.01);   
        }        
        //make new population
        Population selected = children.getCleanCopie();
        Iterator<Individual> bestParent = parents.getSortedIterable().iterator();
        //elitism of parents
        for (int i = 0; i < ELITISM; i++) {
            selected.addIndividual(bestParent.next(),1);
        }
        //select best children (possibily all)
        Iterator<Individual> bestChildren = children.getSortedIterable().iterator();
        while (bestChildren.hasNext() && selected.size() < sizeNewPop) {
            selected.addGenotype(bestChildren.next());
        }
        //complete population with parents (if necessary)        
        while (selected.size() < sizeNewPop && bestParent.hasNext()) {
                selected.addGenotype(bestParent.next());
        }
        return selected;

    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nSelect best Childrens to next population");
        buf.append("\nIf necessary complete population");
        buf.append("\nwith best parents");
        buf.append("\n\nParameters: <ELITISM>");
        buf.append("\n<ELITISM>percentage of Elitism [0,1]");

        return buf.toString();
    }

    @Override
    public void setParameters(String str) {
        StringTokenizer par = new StringTokenizer(str);
        try {
            ELITISM = Double.parseDouble(par.nextToken());
            ELITISM = ELITISM > 1 ? 0.99 : ELITISM < 0 ? 0.01 : ELITISM;
        } catch (Exception e) {
            ELITISM = 0;
        }

    }
    @Override
    public String getParameters() {
           return  ELITISM +"";

    }

    @Override
    public String toString() {
        return super.toString() + " <" + ELITISM + ">";
    }

    @Override
    public Replacement getClone() {
        Gerational clone = (Gerational) super.getClone();
        clone.ELITISM = this.ELITISM;
        return clone;
    }
}