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
import genetic.population.SimplePopulation;
import operator.recombination.Recombination;
import problem.Individual;
import utils.geometry.GeometryVector;

/**
 * Ono, I., Kita, H. and Kobayashi, S. (1999). A Robust Real-Coded Genetic
 * Algorithm using Uni- modal Normal Distribution Crossover Augmented by Uniform
 * Crossover: Effects of Self- Adaptation of Crossover Probabilities. In
 * Banzhaf,W. et al., editors, Proceedings of the Genetic and Evolutionary
 * Computation Conference ’99, pages 496–503, Morgan Kaufmann, San Francisco,
 * California.
 *
 * @author manso
 */
public class UNDX extends Recombination {

    static double DEFAULT_sigmaE = 0.5;
    static double DEFAULT_sigmaN = 0.35;
    protected double sigmaE = DEFAULT_sigmaE;
    protected double sigmaN = DEFAULT_sigmaN;

    @Override
    public Population execute(Population pop) {
        //make new population
        Population offspring = pop.getCleanCopie();

        while (pop.getNumGenotypes() > 2) {
            //clones - not recombinations
            if (random.nextDouble() > getProbability()) {
                offspring.addIndividual(pop.removeRandomGenotype());
                continue;
            }
            //select pivot
            Individual p3 = pop.removeRandomGenotype();
            //select two individuals at random
            Individual p1 = pop.removeRandomGenotype();
            Individual p2 = pop.removeRandomGenotype();
            offspring.appendPopulation(UNDXcrossover(p1, p2, p3));
        }
        //append the individuals wich not do recombination
        offspring.appendPopulation(pop);
        return offspring;
    }

    protected double[] midPoint(Individual p1, Individual p2) {
        //intersection point
        double[] mid = new double[p1.getNumGenes()];
        double v1[] = p1.getValues();
        double v2[] = p2.getValues();
        for (int i = 0; i < mid.length; i++) {
            mid[i] = (v1[i] * p1.getNumCopies() + v2[i] * p2.getNumCopies()) / (p1.getNumCopies() + p2.getNumCopies());
        }
        return mid;
    }

    protected double[] diffPoint(Individual p1, Individual p2) {
        //intersection point
        //intersection point
        double[] dif = new double[p1.getNumGenes()];
        double v1[] = p1.getValues();
        double v2[] = p2.getValues();
        for (int i = 0; i < dif.length; i++) {
            dif[i] = v1[i] * p1.getNumCopies() - v2[i] * p2.getNumCopies();
        }
        return dif;
    }

    protected double distance(Individual p1, Individual p2, Individual p3) {
        return GeometryVector.distanceTo(p1.getValues(), p2.getValues(), p3.getValues());
    }

    public Population UNDXcrossover(Individual p1, Individual p2, Individual p3) {
        Population pop = new SimplePopulation();
        int N = p1.getNumCopies() + p2.getNumCopies() + p3.getNumCopies();
        int numGenes = p1.getNumGenes();
        double sE = sigmaE;
        double sN = sigmaN / Math.sqrt(numGenes);

        p1.setNumCopys(1);
        p2.setNumCopys(1);
        p3.setNumCopys(1);

        double[] xp = midPoint(p1, p2);
        double[] d = diffPoint(p1, p2);
        double D = distance(p1, p2, p3);
        double[] orthonormal = GeometryVector.orthonormalize(p1.getValues(), p2.getValues());
        for (int k = 0; k < N; k++) {
            Individual children = p1.getClone();
            double values[] = children.getValues();
            for (int i = 0; i < values.length; i++) {
                double t1 = xp[i];
                double t2 = random.nextGaussian() * sE * d[i];

                double sum = 0;
                for (int j = 0; j < numGenes - 1; j++) {
                    sum += random.nextGaussian() * sN * orthonormal[i];
                }
                double t3 = D * sum;
                values[i] = t1 + t2 + t3;
            }

            children.setValues(values);
            pop.addIndividual(children);
        }
        return pop;
    }

    @Override
    public String toString() {
        return "UNDX <" + probability + "> sigmaE<" + sigmaE + "> sigmaN <" + sigmaN + ">";
    }

    @Override
    public String getInformation() {

        StringBuffer buf = new StringBuffer();
        buf.append(this.toString());
        buf.append("\nUnimodal Normal Distribution Crossover");
        buf.append("\ndistance factor (sigmaE = " + sigmaE + ")");
        buf.append("\nOrthonormal factor (sigmaN = " + sigmaN + ")");

        buf.append("\n\nParameters: <Probability> <sigmaE> <sigmaN>");
        buf.append("\n<probability> - probability of recombination");
        buf.append("\n<sigmaE> - Distance Factor");
        buf.append("\n<sigmaN> - Orthonormal Factor");
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
            sigmaE = Double.parseDouble(par[1]);
        } catch (Exception e) {
            sigmaE = DEFAULT_sigmaE;
        }
        try {
            sigmaN = Double.parseDouble(par[2]);
        } catch (Exception e) {
            sigmaN = DEFAULT_sigmaE;
        }


    }
}