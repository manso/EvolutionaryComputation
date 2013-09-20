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
import operator.recombination.Recombination;
import problem.Individual;

/**
 * Self-Adaptive Simulated Binary Crossover for Real-Parameter Optimization
 * Kalyanmoy Deb , S. Karthik , Tatsuya Okabe
 *
 * @author manso
 *
 */
public class SimulatedBinary extends Recombination {

    static double DEFAULT_N = 2;
    double N = DEFAULT_N;

    protected double beta(double n) {
        double u = random.nextDouble();
        if (u < 0.5) {
            return Math.pow(2 * u, 1.0 / (n + 1));
        }
        return Math.pow(1 / (2 * (1 - u)), 1.0 / (n + 1));
    }

    /**
     * Blend (BLX) Crossover
     *
     * @param i0 first parent
     * @param i1 second parent
     * @param alpha
     * @param numChildren
     * @return
     */
    public Individual[] execute(Individual i0, Individual i1) {
        int n1 = i0.getNumCopies();
        int n2 = i0.getNumCopies();
        Individual[] children = new Individual[n1 + n2];

        //calculate new genes
        double p1[] = i0.getValues();
        double p2[] = i1.getValues();
        for (int k = 0; k < (n1 + n2) / 2; k++) {
            children[2 * k] = i0.getClone();
            children[2 * k + 1] = i1.getClone();
            double c1[] = children[2 * k].getValues();
            double c2[] = children[2 * k + 1].getValues();
            for (int i = 0; i < i0.getNumGenes(); i++) {
                double b = beta(k+1);
                c1[i] = 0.5 * (p1[i] + p2[i]) + 0.5 * b * (p1[i] - p2[i]);
                c2[i] = 0.5 * (p1[i] + p2[i]) + 0.5 * b * (p2[i] - p1[i]);
            }

            children[2 * k].setValues(c1);
            children[2 * k + 1].setValues(c2);
        }
        return children;
    }

    @Override
    public Population execute(Population pop) {
        //make new population
        Population offspring = pop.getCleanCopie();
        //calculate the number of individuals selected to crossover
        int numCrossovers = (int) (pop.getNumIndividuals() * getProbability() / 2);
        //make crossovers
        int countCrossover = 0;
        while (countCrossover < numCrossovers && pop.getNumGenotypes() > 1) {
            //increment crossovers
            countCrossover++;
            //select two individuals at random
            Individual p1 = pop.removeRandomGenotype();
            Individual p2 = pop.removeRandomGenotype();
            //make the combinations
            Individual[] children = execute(p2, p1);
            //put individuals in population
            for (Individual chld : children) {
                offspring.addIndividual(chld);
            }
        }
        //append the individuals wich not do recombination
        offspring.appendPopulation(pop);
        return offspring;

    }

    @Override
    public String toString() {
        return "SBX <" + probability + "> N<" + N + "> ";
    }

    @Override
    public String getInformation() {

        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nSimulated Binary Crossover ");

        buf.append("\n\nParameters: <Probability> <N>");
        buf.append("\n<probability> - probability of recombination");
        buf.append("\n<N> - Expansion of exponential (default 3");
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
            N = Double.parseDouble(par[1]);
        } catch (Exception e) {
            N = DEFAULT_N;
        }
    }
}