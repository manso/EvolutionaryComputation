/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import genetic.population.Population;
import java.util.ArrayList;
import problem.Individual;
import utils.geometry.GeometryVector;

/**
 *
 * @author programmer
 */
public class SimplexUtils {

    /**
     * return a random individual in the triangle i0-i1-i2
     *
     * @param i0 point of the triangle
     * @param i1 point of the triangle
     * @param i2 point of the triangle
     * @return randonm individual in the triangle
     */
    public static Individual getUniformRandom(Individual i0, Individual i1, Individual i2) {
        Individual rndInd = i0.getClone();
        double[] genes = GeometryVector.uniformRandomPointInTriangle(i0.getValues(), i1.getValues(), i2.getValues());
        rndInd.setValues(genes);
        return rndInd;
    }

    /**
     * Ceter of mass of the individuals
     *
     * @param parents points
     * @return individual in the center of mass
     */
    public static double[] getCenter(Population parents) {
        int numGenes = parents.getIndividual(0).getNumGenes();
        double[] center = new double[numGenes];
        for (int i = 0; i < parents.getNumGenotypes(); i++) {
            double v[] = parents.getGenotype(i).getValues();
            for (int j = 0; j < numGenes; j++) {
                center[j] += v[j];
            }
        }
        for (int i = 0; i < numGenes; i++) {
            center[i] /= parents.size();
        }
        return center;
    }

    /**
     * Expand the simplex by the factor EXPANSION
     *
     * @param parent points
     * @param EXPANSION factor of EXPANSION
     * @return new points (parents expanded)
     */
    public static Population expandSimplex(Population parent, double EXPANSION) {
        Population simplex = parent.getCleanCopie();
        double [] center = getCenter(parent);
        for (int i = 0; i < parent.getNumGenotypes(); i++) {
            Individual ind = parent.getGenotype(i).getClone();
            double[] v = ind.getValues();
            for (int gene = 0; gene < ind.getNumGenes(); gene++) {
                double dist = v[gene] - center[gene];
                v[gene] += dist * EXPANSION * ind.getNumCopies();
            }
            ind.setValues(v);
            simplex.addGenotype(ind);
        }
        return simplex;
    }
}
