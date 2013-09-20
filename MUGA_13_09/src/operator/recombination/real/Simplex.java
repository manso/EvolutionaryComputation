///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package operator.recombination.real;

import genetic.population.Population;
import java.util.ArrayList;
import operator.recombination.Recombination;
import problem.Individual;

/**
 *
 * @author ZULU
 */
public class Simplex extends Recombination {

    protected static int DEFAULT_SIZE = 3;
    protected static double DEFAULT_EXPANSION = 0.5;
    protected int SIMPLEX_SIZE = DEFAULT_SIZE;
    protected double EXPANSION = DEFAULT_EXPANSION;

    /**
     * Execute Simplex Crossover
     *
     * @param simplex parents of the crossover
     * @return
     */
    public Population executeSimplex(Population simplex) {
        //expand Simplex  
        simplex = utils.SimplexUtils.expandSimplex(simplex, 1.0 + EXPANSION);

        Population children = simplex.getCleanCopie();
        int size = simplex.getNumGenotypes();

        for (int ind = 0; ind < simplex.getNumIndividuals(); ind++) {

            int index0 = random.nextInt(size);
            int index1, index2;
            //next random individual
            do {
                index1 = random.nextInt(size);
            } while (index1 == index0);
            //next random individual
            do {
                index2 = random.nextInt(size);
            } while (index2 == index0 || index2 == index1);
            //get random individual in triangle
            Individual rnd = utils.SimplexUtils.getUniformRandom(
                    simplex.getGenotype(index0),
                    simplex.getGenotype(index1),
                    simplex.getGenotype(index2));

            children.addIndividual(rnd, 1);
        }
        return children;
    }

    @Override
    public Population execute(Population pop) {
        //make new population
        Population offspring = pop.getCleanCopie();
    
        while (pop.getNumGenotypes() >= SIMPLEX_SIZE) {
            if( random.nextDouble() > getProbability()){
                offspring.addGenotype( pop.removeRandomGenotype());
                continue;
            }

            Population selected = pop.getCleanCopie();
            for (int i = 0; i < SIMPLEX_SIZE; i++) {
                selected.addGenotype(pop.removeRandomGenotype());
            }
            //make the combinations
            Population children = executeSimplex(selected);
            offspring.appendPopulation(children);

        }
        //append the individuals wich not do recombination
        offspring.appendPopulation(pop);
        return offspring;
    }
     @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + probability + "><" + SIMPLEX_SIZE+ "><" + EXPANSION+">";
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder();
        buf.append(this.toString());
        buf.append("\nSimplex Crossover:");
        buf.append("\nExtends By ").append(EXPANSION).append(" factor");
        buf.append("\n\nParameters: <Probability> <SIZE> <EXPANSION> ");
        buf.append("\n<probability> - probability of recombination");
        buf.append("\n<SIZE> - number of individuals in simplex (default 3)");
        buf.append("\n<EXPANSION> - Expansion Factor ( >=0.0  default 0.5)");
        return buf.toString();
    }

    @Override
    public void setParameters(String str) {
        if (str == null || str.trim().length() == 0) {
            return;
        }
        String[] par = str.split(" ");
        try {
            probability = Double.parseDouble(par[0]);
        } catch (Exception e) {
            probability = DEFAULT_PROBABILITY;
        }
        try {
            SIMPLEX_SIZE = Integer.parseInt(par[1]);
        } catch (Exception e) {
            SIMPLEX_SIZE = DEFAULT_SIZE;
        }
        try {
            EXPANSION = Double.parseDouble(par[2]);
        } catch (Exception e) {
            EXPANSION = DEFAULT_EXPANSION;
        }
    }
}