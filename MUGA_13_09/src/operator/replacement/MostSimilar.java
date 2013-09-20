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
public class MostSimilar extends Replacement {

    protected double ELITISM = 0.1;

    @Override
    public Population execute(Population parents, Population children) {

//        int elit = (int) (ELITISM * parents.getNumGenotypes());
//        //replace worst parents by worst childrens
        Iterator<Individual> itSorted = children.getSortedIterable().iterator();
//        for (int i = 0; i < elit && itSorted.hasNext(); i++) {
//            Individual ch = itSorted.next();
//            parents.addGenotype(ch);
//        }
        //

        while (itSorted.hasNext()) {
            Individual ch = itSorted.next();
            Individual sim = parents.getMostSimilar(ch);
            int compare = ch.compareTo(sim);
//            if (compare == 0) {
//                if (sim.equals(ch)) {
//                     parents.addGenotype(ch);
//                } else {
//                    parents.removeGenotype(sim);
//                    parents.addGenotype(ch);
//                }
//            } //replace if is better
//            else 
            if (ch.compareTo(sim) >= 0) {

                if (sim.equals(ch)) {
                    ch.setNumCopys(1 + sim.getNumCopies());
                } else {
                    parents.removeGenotype(sim);
                }
                parents.addGenotype(ch);
            }
        }
        return parents;
    }

    @Override
    public String getInformation() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nReplace most similars individuals in the population");
        buf.append("\n\nParameters: <ELITISM>");
        buf.append("\n<ELITISM>percentage of Elitism");

        return buf.toString();
    }

    @Override
    public void setParameters(String str) {
        StringTokenizer par = new StringTokenizer(str);
        try {
            ELITISM = Double.parseDouble(par.nextToken());
            ELITISM = ELITISM > 1 ? 0.99 : ELITISM < 0 ? 0.01 : ELITISM;
        } catch (Exception e) {
            ELITISM = 0.1;
        }

    }

    @Override
    public String toString() {
        return super.toString() + " <" + ELITISM + ">";
    }

    @Override
    public Replacement getClone() {
        MostSimilar clone = (MostSimilar) super.getClone();
        clone.ELITISM = this.ELITISM;
        return clone;
    }
}