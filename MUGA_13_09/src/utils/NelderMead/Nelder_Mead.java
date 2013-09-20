/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.NelderMead;

import genetic.population.MultiPopulation;
import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;

/**
 *
 * @author manso
 */
public class Nelder_Mead {

    public boolean DEBUG = false;
    public double factorReflexection = 1.0;
    public double factorExpansion = 2.0;
    public double factorContration = 0.5;
    public double factorReduction = 0.5;
    protected Individual B = null; //best
    protected Individual W = null; //worst
    protected Individual G = null; //second worst
    protected Individual M = null; // center o mass
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------

    public void simplexSearch(Population p, int maxFailure, int maxItera) {
        int numFailure = 0;
        int iteration = 0;
        double best = p.getBestValue();
        while (numFailure <= maxFailure && iteration < maxItera) {

            if (simplexIteration(p)) { //improve the best of the simplex
                numFailure = 0;
                if (DEBUG) {
                    System.out.println(iteration + " Improve " + (best - p.getBestValue()) + "  --------------- " + p.getBestGenotype());
                }
                best = p.getBestValue();
            } else {
                numFailure++;
            }
            iteration++;
        }
    }
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------

    protected void calculateSimplexIndividuals(Population pop) {
        //center of Mass
        M = pop.getGenotype(0).getClone();
        double m[] = new double[M.getNumGenes()];
        //sorted iterator of population
        Iterator<Individual> it = pop.getSortedIterable().iterator();
        //to store the first individual
        B = null;
        while (it.hasNext()) {
            Individual ind = it.next();
            //best individual
            if (B == null) {
                B = ind;
            }
            //worst individual
            //last individual don't contribute to center of mass
            if (!it.hasNext()) {
                W = ind;
                break;
            } //compute sum of genes to center of mass
            else {
                //second worst
                G = ind;
                for (int i = 0; i < M.getNumGenes(); i++) {
                    m[i] += ind.getGene(i).getValue();
                }
            }
        }//iterator
        //calculate center of mass
        double dim = pop.getNumGenotypes() - 1;
        for (int i = 0; i < M.getNumGenes(); i++) {
            M.setGeneValue(i, m[i] / dim);
        }
    }
    //------------------------------------------------------------------------
    //-----------------------------------------------------------------------

    /**
     * calculate the new simplex of the positions of the individuals
     * int the population
     * @param pop currente simplex (individuals)
     * @return true if the new simplex improve the better
     */
    protected boolean simplexIteration(Population pop) {
        //calculare (B)est (W)orst  (G)second worst and Center of (M)ass
        calculateSimplexIndividuals(pop);
        //-----------------------NEW NEW NEW NEW ---------------------
        M.evaluate();
        pop.addEvaluations(1);
        //improve de in the middle of simplex
        if (M.compareTo(G) < 0) {
            //replace the Best to Center of MASS
            G.setIndividual(M);
            System.out.println("CENTER Of MASS");
            //improve the simplex
            return false;
        }
        //----------------------------------------------------------
        //------------------------ REFLECTION -----------------------
        //----------------------------------------------------------
        //reflex
        Individual R = W.getClone();
        //calculate reflex
        for (int i = 0; i < R.getNumGenes(); i++) {
            R.setGeneValue(i, M.getGene(i).getValue() + factorReflexection
                    * (M.getGene(i).getValue() - W.getGene(i).getValue()));
        }
        R.evaluate();
        pop.addEvaluations(1);
        //improve de in the middle of simplex
        if (R.compareTo(B) < 0 && R.compareTo(G) > 0) {
            //replace the worst to second worst
            W.setIndividual(G);
            //replace second worst to reflexion
            G.setIndividual(R);
            if (DEBUG) {
                System.out.println("G REFLECTION");
            }
            //not improve the simplex
            return false;
        } //----------------------------------------------------------
        //---------------------------------------- EXPANSION --------
        //----------------------------------------------------------
        //R better than the best
        else if (R.compareTo(B) > 0) {
            //Replace B to R
            B.setIndividual(R);
            //expand more
            Individual E = W.getClone();
            double alpha = factorExpansion;
            while (true) {
                //continue expansion (W->M) with step alpha
                for (int i = 0; i < R.getNumGenes(); i++) {
                    E.setGeneValue(i, M.getGene(i).getValue()
                            + alpha * (M.getGene(i).getValue() - W.getGene(i).getValue()));
                }
                //increase alpha
                alpha += factorExpansion / 2.0;
                //evaluate Expasion
                E.evaluate();
                pop.addEvaluations(1);
                //is better than the Best
                if (E.compareTo(B) > 0) {
                    B.setIndividual(E);
                    //improve solution
                    if (DEBUG) {
                        System.out.println("B EXPANSION " + alpha);
                    }
                    //contine
                } else {
                    break;
                    //stop
                }
            }
            return true;
        } //M better than G
        else { // M Worst than G
            //----------------------------------------------------------
            //----------------------------- CONTRACTION ----------------
            //----------------------------------------------------------
            //Contraction of direction
            Individual C = W.getClone();
            for (int i = 0; i < R.getNumGenes(); i++) {
                C.setGeneValue(i, W.getGene(i).getValue() + factorContration
                        * (M.getGene(i).getValue() - W.getGene(i).getValue()));
            }
            //evaluate
            C.evaluate();
            pop.addEvaluations(1);
            //contraction better than the best
            if (C.compareTo(B) > 0) {
                B.setIndividual(C);
                if (DEBUG) {
                    System.out.println("B CONTRATION");
                }
                //improve best
                return true;
            } else //contraction better than the worst
            if (C.compareTo(W) > 0) {
                if (DEBUG) {
                    System.out.println("W CONTRATION");
                }
                W.setIndividual(C);
                //not improve the best
                return false;
            } //contraction
            else {
                //----------------------------------------------------------
                //------------------------- REDUCTION ----------------------
                //----------------------------------------------------------
                //Reduction
                Individual S = W.getClone();
                for (int i = 0; i < W.getNumGenes(); i++) {
                    S.setGeneValue(i, factorReduction
                            * (W.getGene(i).getValue() + B.getGene(i).getValue()));
                }
                S.evaluate();
                //Reduction better than the best
                if (S.compareTo(B) > 0) {
                    if (DEBUG) {
                        System.out.println(" B REDUCTION");
                    }
                    B.setIndividual(S);
                    //improve best
                    return true;
                } //Reducao do ultimo
                else {
                    if (DEBUG) {
                        System.out.println(" W REDUCTION");
                    }
                    W.setIndividual(S);
                    //not improve the best
                    return false;
                }
            }//Recuction
        }//Contraction
    } //simplex

    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    //------------------------------------------------------------------------
    /**
     * Ceter of mass of the individuals
     * @param parents  points
     * @return individual in the center of mass
     */
    public Individual getCenter(Population pop) {
        Individual center = pop.getIndividual(0).getClone();
        int numGenes = center.getNumGenes();
        double[] mid = new double[numGenes];
        for (int i = 0; i < pop.getNumGenotypes(); i++) {
            for (int j = 0; j < numGenes; j++) {
                mid[j] += pop.getIndividual(i).getGene(j).getValue();
            }
        }
        int size = pop.getNumGenotypes();
        for (int i = 0; i < numGenes; i++) {
            center.setGeneValue(i, mid[i] / size);
        }
        return center;
    }

    /**
     * Expand the simplex with the number of copies
     * @param simplex population
     * @param EXPANSION factor of expansion
     * @return
     */
    public MultiPopulation expandSimplex(MultiPopulation simplex, double EXPANSION) {
        Population expanded = simplex.getCleanCopie();
        Individual center = getCenter(simplex);
        for (int i = 0; i < simplex.getNumGenotypes(); i++) {
            Individual ind = simplex.getIndividual(i).getClone();
            for (int gene = 0; gene < ind.getNumGenes(); gene++) {
                double dist = ind.getGene(gene).getValue() - center.getGene(gene).getValue();
                double value = ind.getGene(gene).getValue() + dist * EXPANSION * (ind.getNumCopies() - 1);
                ind.setGeneValue(gene, value);
            }
            ind.setIsEvaluated(false);
            expanded.addGenotype(ind);
        }
        return simplex;
    }
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//************************************************************************
//-------                                                           ------
//-------                                                           ------
//-------        functions to crossover operator                    ------
//-------                                                           ------
//-------                                                           ------
//************************************************************************

    /**
     * Get the center of the simplex
     * @param i0 individual
     * @param i1 individual
     * @param i2 individual
     * @return  center individual
     */
    public static Individual getCenter(Individual i0, Individual i1, Individual i2) {
        Individual center = i0.getClone();
        for (int i = 0; i < center.getNumGenes(); i++) {
            center.setGeneValue(i, (i0.getGene(i).getValue()
                    + i1.getGene(i).getValue()
                    + i2.getGene(i).getValue())
                    / 3.0);
        }
        return center;
    }

    /**
     * Expand the individual in the line center---> ind  by the factor
     * @param center
     * @param ind
     * @param EXPANSION
     */
    public static void expandIndividual(Individual center, Individual ind, double EXPANSION) {
        for (int gene = 0; gene < ind.getNumGenes(); gene++) {
            double dist = ind.getGene(gene).getValue() - center.getGene(gene).getValue();
            ind.setGeneValue(gene, ind.getGene(gene).getValue() + dist * EXPANSION);
        }
    }

    /**
     * Expand Simplex by the number of copies
     * @param i0 individual in simplex
     * @param i1 individual in simplex
     * @param i2 individual in simplex
     * @param factor factor of expansion
     */
    public static void expandSimplex(Individual i0, Individual i1, Individual i2, double factor) {
        Individual center = getCenter(i0, i1, i2);
        expandIndividual(center, i0, (i0.getNumCopies() -1) * factor);
        expandIndividual(center, i1, (i1.getNumCopies() -1) * factor);
        expandIndividual(center, i2, (i2.getNumCopies() -1) * factor);
        i0.evaluate();
        i1.evaluate();
        i2.evaluate();
    }
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------

    /**
     * Return the best individual in the simplex
     * @param i0 individual
     * @param i1 individual
     * @param i2 individual
     * @return  Best individual
     */
    public static Individual getB(Individual i0, Individual i1, Individual i2) {
        if (i0.compareTo(i1) >= 0 && i0.compareTo(i2) >= 0) {
            return i0;
        }
        if (i1.compareTo(i0) >= 0 && i1.compareTo(i2) >= 0) {
            return i1;
        }
        return i2;
    }

    /**
     * Return the Worst individual in the simplex
     * @param i0 individual
     * @param i1 individual
     * @param i2 individual
     * @return  Worst individual
     */
    public static Individual getW(Individual i0, Individual i1, Individual i2) {
        if (i0.compareTo(i1) <= 0 && i0.compareTo(i2) <= 0) {
            return i0;
        }
        if (i1.compareTo(i0) <= 0 && i1.compareTo(i2) <= 0) {
            return i1;
        }
        return i2;
    }

    /**
     * Return the individual not best and not worst individual in the simplex
     * @param i0 individual
     * @param i1 individual
     * @param i2 individual
     * @return  middle individual
     */
    public static Individual getG(Individual i0, Individual i1, Individual i2) {
        if (i0.compareTo(i1) <= 0 && i0.compareTo(i2) >= 0
                || i0.compareTo(i1) >= 0 && i0.compareTo(i2) <= 0) {
            return i0;
        }
        if (i1.compareTo(i0) <= 0 && i1.compareTo(i2) >= 0
                || i1.compareTo(i0) >= 0 && i1.compareTo(i2) <= 0) {
            return i1;
        }
        return i2;
    }

    /**
     * Return the middle Point in the Line B - G
     * @param B Best individual
     * @param G Genereral individual
     * @return  middle Point individual
     */
    public static Individual getM(Individual B, Individual G) {
        Individual M = B.getClone();
        for (int i = 0; i < M.getNumGenes(); i++) {
            M.setGeneValue(i, (G.getGene(i).getValue() + B.getGene(i).getValue()) / 2.0);
        }
        M.evaluate();
        return M;
    }

    /**
     * Return the R Point
     * @param B Best individual
     * @param G Genereral individual
     * @return  R Point
     */
    public static Individual getR(Individual W, Individual M) {
        Individual R = M.getClone();
        for (int i = 0; i < R.getNumGenes(); i++) {
            R.setGeneValue(i,
                    M.getGene(i).getValue()
                    + (M.getGene(i).getValue() - W.getGene(i).getValue()));
        }
        R.evaluate();
        return R;
    }

    /**
     * Return the E Point
     * @param W Worst individual
     * @param M Mean individual

     * @return  E Point
     */
    public static Individual getE(Individual W, Individual M) {
        Individual E = M.getClone();
        for (int i = 0; i < E.getNumGenes(); i++) {
            E.setGeneValue(i,
                    M.getGene(i).getValue()
                    + 2 * (M.getGene(i).getValue() - W.getGene(i).getValue()));
        }
        E.evaluate();
        return E;
    }

    /**
     * Return the C Point
     * @param W Worst individual
     * @param M Mean individual
     * @return  C Point
     */
    public static Individual getC(Individual W, Individual M) {
        Individual C = M.getClone();
        for (int i = 0; i < C.getNumGenes(); i++) {
            C.setGeneValue(i,
                    W.getGene(i).getValue()
                    + 0.5 * (M.getGene(i).getValue() - W.getGene(i).getValue()));
        }
        C.evaluate();
        return C;
    }

    /**
     * Return the S Point
     * @param W Worst individual
     * @param B Best individual
     * @return  S Point
     */
    public static Individual getS(Individual W, Individual B) {
        Individual S = B.getClone();
        for (int i = 0; i < S.getNumGenes(); i++) {
            S.setGeneValue(i,
                    (W.getGene(i).getValue() + B.getGene(i).getValue()) / 2.0);
        }
        S.evaluate();
        return S;
    }
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
//------------------------------------------------------------------------
}
