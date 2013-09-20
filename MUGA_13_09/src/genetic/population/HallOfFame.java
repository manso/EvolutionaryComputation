/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population;

import java.util.HashSet;
import java.util.Iterator;
import problem.Individual;

/**
 *
 * @author arm
 */
public class HallOfFame {

    int MAXSIZE = 128;
    /**
     * best individuals - not repeated
     */
    private HashSet<Individual> hall_of_fame;
    private Individual goodIndividual = null;

    public HallOfFame() {
        hall_of_fame = new HashSet<>();
        goodIndividual = null;
    }

    public HallOfFame getClone() {
        HallOfFame tmp = new HallOfFame();
        if (goodIndividual != null) {
            tmp.goodIndividual = goodIndividual.getClone();
            for (Individual i : hall_of_fame) {
                tmp.hall_of_fame.add(i.getClone());
            }
        }
        return tmp;
    }

    /**
     * join hall of fame
     *
     * @param h
     */
    public void add(HallOfFame h) {
        for (Individual i : h.hall_of_fame) {
            add(i);
        }
    }

    /**
     * Add new individuals to Hall of Fame if the new individuals are best the
     * old ones are replaced
     *
     * @param nbest
     */
    public void add(Individual ind) {
        //verify if the individual is evaluated
        if (!ind.isEvaluated()) {
            throw new RuntimeException("Individual not Evaluated");
        }
        //if empty add individual
        if (goodIndividual == null || hall_of_fame.isEmpty()) {
            hall_of_fame.add(ind);
            goodIndividual = ind;
            return;
        }
        int comp = goodIndividual.compareTo(ind);
        if (comp < 0) {//replace old ones
            hall_of_fame.clear();
            hall_of_fame.add(ind);
            goodIndividual = ind;
        } else // if are equal append individuals
        if (comp == 0 && hall_of_fame.size() < MAXSIZE) {
            hall_of_fame.add(ind);
            goodIndividual = ind;
        }
    }

    /**
     * get the hall of fame
     *
     * @return hall of fame
     */
    public HashSet<Individual> getGood() {
        return hall_of_fame;
    }

    /**
     * convert all of fame to population
     *
     * @return population of hall of fame
     */
    public Population getGoodPopulation() {
        Population pop = new UniquePopulation();
        for (Individual i : hall_of_fame) {
            pop.addIndividual(i);
        }
        return pop;
    }

    /**
     * @return number of best individuals
     */
    public int getNumberOFBestIndividuals() {
        if (goodIndividual == null || !goodIndividual.isBest()) {
            return 0;
        }
        return hall_of_fame.size();
    }

    @Override
    public String toString() {
        Iterator<Individual> it = hall_of_fame.iterator();
        StringBuilder txt = new StringBuilder();
        txt.append("# Best ,").append(hall_of_fame.size());
        while (it.hasNext()) {
            txt.append("\n" + it.next().toStringPhenotype());
        }
        return txt.toString();
    }

    /**
     * return best fitness in hall of fame
     *
     * @return best fitness
     */
    public double getBestFitness() {
        return goodIndividual.getFitness();
    }

    /**
     * Best individual
     *
     * @return best individual
     */
    public Individual getBestIndividal() {
        return goodIndividual;
    }
}
