/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.selection;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.util.Iterator;
import java.util.StringTokenizer;
import problem.Individual;
import problem.bitString.OnesMin;

/**
 * The GENITOR Algorithm and Selection Pressure: Why Rank-Based Allocation of
 * Reproductive Trials is Best (1989) by Darrell Whitley Proceedings of the
 * Third International Conference on Genetic Algorithm
 * http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.18.8195
 *
 * @author arm
 */
public class Rank_Whitley extends Selection {

    protected double C = 2;

    /**
     * Tournament selection
     *
     * @param pop population of parents
     * @param numChilds number of children to select
     * @return population selected
     */
    @Override
    public Population execute(final Population pop, int numChilds) {
        int N = pop.getNumIndividuals();
        //make new population
        Population selected = pop.getCleanCopie();
        //---------- rank individuals -------------------------------
        Individual[] rank = new Individual[N];
        Iterator<Individual> it = pop.getSortedIterable().iterator();
        int index = 0;
        while (it.hasNext()) {
            Individual ind = it.next();
            for (int i = 0; i < ind.getNumCopies(); i++) {
                rank[index++] = ind;
            }
        }
        //----------------- select individuals
        for (int i = 0; i < numChilds; i++) {
            index = (int) Math.floor(
                    (N / (2 * (C - 1)))
                    * (C - Math.sqrt(C * C - 4 * (C - 1) * random.nextDouble())));
            selected.addIndividual(rank[index].getClone());

        }
        return selected;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder(toString());
        buf.append("\nRank Selection by Witley formula");
        buf.append("\n\nParameters: <SIZE> <C>");
        buf.append("\n    <SIZE>  - # of individuals to be selected ");
        buf.append("\n             0 =  AUTO (size of population)");
        buf.append("\n    <C> - Selective pressure [1,2]");
        buf.append("\n          <1> - uniform selection");
        buf.append("\n          <2> - p(best) = 2 * p(median)");
        buf.append("\n\nThe GENITOR Algorithm and Selection Pressure");
        buf.append("\nWhy Rank-Based Allocation of Reproductive Trials is Best (1989)");
        buf.append("\nby Darrell Whitley");
        buf.append("\nThird International Conference on Genetic Algorithm");

        return buf.toString();
    }

    @Override
    public void setParameters(String param) {
        StringTokenizer iter = new StringTokenizer(param);
        if (iter.hasMoreTokens()) {
            super.setParameters(iter.nextToken());
        }
        if (iter.hasMoreTokens()) {
            //number of itens
            try {
                C = Double.parseDouble(iter.nextToken());
                if (C <= 1) {
                    C = 1.001;
                }
            } catch (Exception e) {
                C = 2;
            }
        }
    }

    @Override
    public String getParameters() {
        return super.getParameters() + " " + C;
    }

    @Override
    public String toString() {
        return super.toString() + " <" + C + ">";
    }

    @Override
    public Rank_Whitley getClone() {
        Rank_Whitley clone = (Rank_Whitley) super.getClone();
        clone.C = this.C;
        return clone;
    }

    public static void main(String[] args) {
        Population pop = new MultiPopulation();
        Individual o1;


        o1 = new OnesMin("10001");
        pop.addGenotype(o1);
        o1 = new OnesMin("10001");
        pop.addGenotype(o1);
        o1 = new OnesMin("11010");
        pop.addGenotype(o1);
        o1 = new OnesMin("100011");
        pop.addGenotype(o1);
        o1 = new OnesMin("10111");
        pop.addGenotype(o1);
        o1 = new OnesMin("10000");
        pop.addGenotype(o1);
        o1 = new OnesMin("10000");
        pop.addGenotype(o1);


//        o1 = new OnesMax("10001");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("11111");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("10011");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("10111");
//        pop.addGenotype(o1);
//        o1 = new OnesMax("00000");
//        pop.addGenotype(o1);




        pop.evaluate();
        System.out.println("\n" + pop);

//        RWS sel = new RWS();
//        RWS sel = new SUS();
        Rank_Whitley sel = new Rank_Whitley();
        Population selected = sel.execute(pop, 5000);
        System.out.println("\nSELECTED:" + selected);

    }
}
