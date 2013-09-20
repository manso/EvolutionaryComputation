/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.selection;

import genetic.population.Population;
import java.util.StringTokenizer;
import problem.Individual;

/**
 * Tournament selection
 *
 * @author arm
 */
public class Tournament extends Selection {

    /**
     * Size of tournament 1- uniform 2- linear 3- quadratic (DJONG pp 58)
     */
    protected int SIZE_OF_TOURNAMENT = 2;

    /**
     * Tournament selection
     *
     * @param pop population of parents
     * @param numChilds number of children to select
     * @return population selected
     */
    @Override
    public Population execute(final Population pop, int numChilds) {

        //make new population
        Population selected = pop.getCleanCopie();
        //select NumChilds Individuals
        for (int count = 0; count < numChilds; count++) {
            //get one individual
            Individual best = pop.getRandomIndividual();
            //get other individuals
            for (int i = 0; i < SIZE_OF_TOURNAMENT - 1; i++) {
                Individual ind = pop.getRandomIndividual();
                //select the best of the tournament
                if (ind.compareTo(best) > 0) {
                    best = ind;
                }
            }
            //append one copie of the best
            selected.addIndividual(best.getClone(), 1);
        }
        return selected;
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder(toString());
        buf.append("\nSelect the best of the tournament");
        buf.append("\n\nParameters: <SIZE> <TOURN>");
        buf.append("\n    <SIZE>  - # of individuals to be selected ");
        buf.append("\n             0 =  AUTO (size of population)");
        buf.append("\n    <TOURN> - size of tournament ( >= 1)");
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
                SIZE_OF_TOURNAMENT = Integer.parseInt(iter.nextToken());
                if (SIZE_OF_TOURNAMENT < 1) {
                    SIZE_OF_TOURNAMENT = 1;
                }
            } catch (Exception e) {
                SIZE_OF_TOURNAMENT = 2;
            }
        }
    }

    @Override
    public String getParameters() {
        return super.getParameters() + " " + SIZE_OF_TOURNAMENT;
    }

    @Override
    public String toString() {
        return super.toString() + " <" + SIZE_OF_TOURNAMENT + ">";
    }

    @Override
    public Tournament getClone() {
        Tournament clone = (Tournament) super.getClone();
        clone.SIZE_OF_TOURNAMENT = this.SIZE_OF_TOURNAMENT;
        return clone;
    }
}
