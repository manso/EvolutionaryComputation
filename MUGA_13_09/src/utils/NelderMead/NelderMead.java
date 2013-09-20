package utils.NelderMead;
//http://www.scholarpedia.org/article/Nelder-Mead_algorithm
//http://geogebra.uni.lu/svn/branches/ggbLocusLine/geogebra/org/apache/commons/math/optimization/direct/NelderMead.java
//http://math.fullerton.edu/mathews/n2003/neldermead/NelderMeadMod/Links/NelderMeadMod_lnk_3.html    

import genetic.population.Population;
import java.util.Iterator;
import problem.Individual;

public class NelderMead {

    protected double REFLECTION = 1;
    protected double EXPANSION = 2.0;
    protected double CONTRACTION = 0.5;
    protected double REDUCTION = 0.5;
    protected double SHRINK = 0.5;
    public Individual B = null; // best Individual
    public Individual G = null; // Median Individual
    public Individual W = null; // Worst Individual
    protected Individual M = null;
    protected Individual R = null;
    protected Individual E = null;
    protected Individual C = null;
    protected Individual S = null;

    public NelderMead() {
        ;
    }

    public NelderMead(final double reflection, final double expansion,
            final double contraction, final double reduction) {
        this.REFLECTION = reflection;
        this.EXPANSION = expansion;
        this.CONTRACTION = contraction;
        this.REDUCTION = reduction;
    }

    //return an evaluated individual
    protected Individual getIndividual(Population pop, double[] values) {
        Individual ind = pop.getIndividual(0).getClone();
        ind.setValues(values);
        ind.evaluate();
        pop.addEvaluations(1);
        return ind;
    }

    protected void calculateBGWM(Population pop) {
        //center of Mass
        M = pop.getGenotype(0).getClone();
        double m[] = new double[M.getNumGenes()];
        //sorted iterator of population
        Iterator<Individual> it = pop.getSortedIterable().iterator();
        //to store the first individual
        B = null;
        G = null;
        int median = pop.getNumGenotypes() / 2;
        while (it.hasNext()) {
            Individual ind = it.next();
            //best individual
            if (B == null) {
                B = ind;
            } //second best
            //calculate median
            if (median == 0) {
                G = ind;
            }
            median--;
            //worst individual
            //last individual dont contribute to center of mass
            if (!it.hasNext()) {
                W = ind;
                break;
            } //compute sum of genes to center of mass
            else {
                final double[] genes = ind.getValues();
                for (int i = 0; i < M.getNumGenes(); i++) {
                    m[i] += genes[i];
                }
            }
        }//iterator
        //calculate center of mass
        double dim = pop.getNumGenotypes() - 1;
        for (int i = 0; i < M.getNumGenes(); i++) {
            M.setGeneValue(i, m[i] / dim);
        }
    }

    protected void calculateR(Population pop) {
        final double[] xR = new double[B.getNumGenes()];
        final double[] xM = M.getValues();
        final double[] xW = W.getValues();
        for (int j = 0; j < xR.length; ++j) {
            xR[j] = xM[j] + REFLECTION * (xM[j] - xW[j]);
        }
        //calculate R
        R = getIndividual(pop, xR);
    }

    protected void calculateE(Population pop) {
        final double[] xE = new double[B.getNumGenes()];
        final double[] xR = R.getValues();
        final double[] xM = M.getValues();
        for (int j = 0; j < xE.length; ++j) {
            xE[j] = xM[j] + EXPANSION * (xR[j] - xM[j]);
        }
        //calculate E
        E = getIndividual(pop, xE);
    }

    protected void calculateC_in_out(Population pop) {
        final double[] xC = new double[B.getNumGenes()];
        final double[] xR = R.getValues();
        final double[] xM = M.getValues();
        final double[] xB = B.getValues();
        final double[] xW = W.getValues();
        //R is better than W
        if (R.compareTo(W) > 0) {
            //----------------- CONTRACTION OUTSIDE ------------------
            for (int j = 0; j < xC.length; ++j) {
                xC[j] = xM[j] + CONTRACTION * (xR[j] - xM[j]); // original
            }
        } //R is worst than W
        else {
            //----------------- REDUCTION - CONTRACTION INSIDE ------------
            for (int j = 0; j < xC.length; ++j) {
                xC[j] = xM[j] - REDUCTION * (xM[j] - xW[j]); //original
            }
        }
        //calculate C
        C = getIndividual(pop, xC);
    }

    protected void Shrink(Population pop) {
        //sorted population
        Iterator<Individual> it = pop.getSortedIterable().iterator();
        //best individual
        final double[] xS = it.next().getValues();
        while (it.hasNext()) {
            final double[] x = it.next().getValues();
            for (int j = 0; j < xS.length; ++j) {
                x[j] = xS[j] + SHRINK * (x[j] - xS[j]);
            }
        }
        pop.evaluate();
    }

    protected boolean iterateSimplex(Population pop) {
        //compute center  and the interesting individuals
        calculateBGWM(pop);
        // ---------------  REFLECTION -----------------------------
        calculateR(pop);
        //---------------------------------------------------------
        //R in the middle B--G
        if ((B.compareTo(R) >= 0) && (R.compareTo(G) > 0)) {
            W.setIndividual(R);
            return false;
        } //R is better than the best
        else if (R.compareTo(B) > 0) {
            // ---------------------------EXPANSION -----------------
            calculateE(pop);
            //-------------------------------------------------------
            //better Expanded is better than the reflected
            if (E.compareTo(B) > 0) {
                W.setIndividual(E);
                //System.out.print("E\t");
            } else {
                W.setIndividual(R);
                //System.out.print("Ro\t");
            }
            return true;
        } // R is worst than G
        else {
            //----------------- CONTRACTION OUTSIDE and INSIDE ------------------
            calculateC_in_out(pop);
            //--------------------------------------------------------------
            if (C.compareTo(B) > 0) {
                W.setIndividual(C);
                return true;
            }
            if (C.compareTo(W) > 0) {
                W.setIndividual(C);
                return false;
            }
        }
        // SHRINK not implemented
        return false;
    }

    protected boolean iterateSimplex_G(Population pop) {
        //compute center  and the interesting individuals
        calculateBGWM(pop);
        double oldMedian = G.getFitness();
        //-------------  REFLECTION -----------------------------
        calculateR(pop);
        //---------------------------------------------------------
        //R in the middle B--G
        if ((B.compareTo(R) >= 0) && (R.compareTo(G) > 0)) {
            W.setIndividual(G);
            G.setIndividual(R);
        } //R is better than the best
        else if (R.compareTo(B) > 0) {
            // ---------------------------EXPANSION -----------------
            calculateE(pop);
            //-------------------------------------------------------
            if (E.compareTo(R) > 0) {
                W.setIndividual(G);
                G.setIndividual(E);
            } else {
                W.setIndividual(G);
                G.setIndividual(R);
            }
        } else {
            calculateC_in_out(pop);
            //C greather than G
            if (C.compareTo(G) > 0) {
                W.setIndividual(G);
                G.setIndividual(C);
            } //C between W and G
            else if (C.compareTo(W) > 0) {
                W.setIndividual(C);
            }
        }
        if (G.getFitness() < oldMedian) {
//          if (G.compareTo(B) > 0) {
            return true;
        }
        return false;
    }

    protected boolean iterateSimplex_B(Population pop) {
        //compute center  and the interesting individuals
        calculateBGWM(pop);
        double oldbest = B.getFitness();
        //-------------  REFLECTION -----------------------------
        calculateR(pop);
        //---------------------------------------------------------
        //R in the middle B--G
        if ((B.compareTo(R) >= 0) && (R.compareTo(G) > 0)) {
            W.setIndividual(G);
            G.setIndividual(R);
        } //R is better than the best
        else if (R.compareTo(B) > 0) {
            // ---------------------------EXPANSION -----------------
            calculateE(pop);
            //-------------------------------------------------------
            if (E.compareTo(R) > 0) {
                W.setIndividual(G);
                G.setIndividual(E);
            } else {
                W.setIndividual(G);
                G.setIndividual(R);
            }
        } else {
            calculateC_in_out(pop);
            //C greather than G
            if (C.compareTo(G) > 0) {
                W.setIndividual(G);
                G.setIndividual(C);
            } //C between W and G
            else if (C.compareTo(W) > 0) {
                W.setIndividual(C);
            }
        }
        if (B.getFitness() < oldbest) {
            return true;
        }
        return false;
    }

    public void searchG(Population pop, int maxIterations) {
        //do maxItera iterations
        for (int i = 0; i < maxIterations; i++) {
            if (!iterateSimplex_G(pop)) {
                break;
            }
        }
    }

    public void searchG(Population pop) {
        //while improve
        while (!iterateSimplex_G(pop))
            ;
    }

    public void searchB(Population pop, int maxIterations) {
        //do maxItera iterations
        for (int i = 0; i < maxIterations; i++) {
            if (!iterateSimplex_B(pop)) {
                break;
            }
        }
    }

    public void searchB(Population pop) {
        //while improve
        while (iterateSimplex_B(pop)) 
            ;
    }

    public void search(Population pop) {
        while (iterateSimplex(pop));
    }
}
