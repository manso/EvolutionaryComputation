/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.selection;

import genetic.population.Population;
import problem.Individual;

/**
 *
 * @author arm
 */
public class MTournament extends Tournament {
    


/**
     * Multi Tournament selection - Select individuals ignoring the number of copies
     * @param pop population of parents
     * @param numChilds number of children to select
     * @return population selected
     */
    @Override
    public Population execute(final Population pop, int numChilds) {
        //if there not population
        if (pop == null || pop.getNumGenotypes() == 0) {
            return pop;
        }
        //if the number o selctions is greather than the size of population
        if( numChilds >= pop.getNumGenotypes())
            return pop.getClone();

       //make new population
        Population selected = pop.getCleanCopie();
        //select NumChilds Individuals
       while( selected.getNumGenotypes() < numChilds)
        {
            //get one individual
            Individual best = pop.getRandomGenotype();
            //get other individuals
            for (int i = 0; i < SIZE_OF_TOURNAMENT - 1; i++) {
                Individual ind = pop.getRandomGenotype();
                //select the best of the tournament
                if (ind.compareTo(best) > 0) {
                    best = ind;
                }
            }
            //add best to population
           // if( !selected.contains(best))
              selected.addIndividual(best.getClone());
        }
        return selected;
    }

    @Override
     public MTournament getClone() {
        MTournament res = new MTournament();
        res.setSize(this.getSize());
        res.SIZE_OF_TOURNAMENT = this.SIZE_OF_TOURNAMENT;
        return res;
    }
    @Override
    public String getInformation() {

        StringBuffer buf = new StringBuffer(toString());
        buf.append("\nGarantie of <SIZE> different Individuals ");
        buf.append("\nSelect individuals with tournament ");
        buf.append("\n\nParameters: <SIZE> <TOURN>");
        buf.append("\n    <SIZE>  - # of individuals to be selected ");
        buf.append("\n             0 =  AUTO (size of population)");
        buf.append("\n    <TOURN> - size of tournament ( >= 1)");
        return buf.toString();
    }
}
