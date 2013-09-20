/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.replacement.permutation;

import operator.replacement.*;
import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;
import problem.permutation.TSP.AbstractTSP;

/**
 * Replace the parents to childrens and complete with the parents
 *
 * @author arm
 */
public class Truncation_2_OPT extends Replacement {

    @Override
    public Population execute(Population pop, Population children) {
        //clean copies in the main population
//       cleanCopies.setFactor(1.25);
//       cleanCopies.execute(pop);
//       
        int sizeNewPop = pop.getNumGenotypes();
        // System.out.print("POP " + sizeNewPop + " Children" + children.getNumGenotypes());
           
       Iterator<Individual> it = children.getIterator();
       while( it.hasNext()){
          AbstractTSP ind = (AbstractTSP) it.next();
          LS_2_opt(ind.getGeneValues());
          ind.evaluate();
          pop.addIndividual(ind);
       }
//        //sort all
        Iterator<Individual> iter = pop.getSortedIterable().iterator();
//          //make new population
        Population selected = pop.getCleanCopie();
        while (selected.getNumGenotypes() < sizeNewPop && iter.hasNext()) {
            selected.addGenotype(iter.next());
        }
        return selected;

    }

    public static void LS_2_opt(int[] genome) {
        boolean done = false;
        while (!done) {
            done = true;
            for (int p1 = 0; p1 < genome.length; p1++) {
                for (int p2 = p1 + 2; p2 < genome.length; p2++) {
                    double d1 = getLenght(genome, p1, p1 + 1);
                    double d2 = getLenght(genome, p2, p2 + 1);
                    double d3 = getLenght(genome, p1, p2);
                    double d4 = getLenght(genome, p1 + 1, p2 + 1);
                    if (d1 + d2 > d3 + d4) {
                        reverse2(genome, p1 + 1, p2);
                        done = false;
                    }
                }
            }
        }
    }

    private static double getLenght(int[] genome, int y, int x) {
        return AbstractTSP.getTSPDistance(genome[y % genome.length], genome[x % genome.length]);
    }

    public static void reverse2(int[] cities, int startIndex, int stopIndex) {
        if (startIndex >= stopIndex || startIndex >= cities.length || stopIndex < 0) {
            return;
        }
        while (startIndex < stopIndex) {
            int tmp = cities[startIndex];
            cities[startIndex] = cities[stopIndex];
            cities[stopIndex] = tmp;
            startIndex++;
            stopIndex--;
        }

    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(toString());
        buf.append("\nTruncation Replacement:");
        buf.append("\nperform 2-opt Local Search in childrens");
        buf.append("\nJoin Parents and children");
        buf.append("\nChoose the bests Individuals");
        return buf.toString();
    }
}
