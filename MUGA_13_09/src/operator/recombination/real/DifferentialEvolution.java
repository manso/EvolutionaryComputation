/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.recombination.real;

import genetic.population.Population;
import java.util.Iterator;
import java.util.StringTokenizer;
import operator.recombination.Recombination;
import operator.selection.MTournament;
import operator.selection.Selection;
import problem.Individual;

/**
 *
 * @author manso
 */
public class DifferentialEvolution extends Recombination {

    static String[] strategies = {"RANDOM", "DEBest1Bin", "DEBest1Exp",
        "DEBest2Bin", "DEBest2Exp", "DECurrent2Rand",
        "DERand1Bin", "DERand1Exp", "DERandToBest1Bin",
        "DERandToBest1Exp"};
    public int STRATEGY = 1;
    public double F = 0.5;
    public double CR = 0.9;
    Selection selRandom = new MTournament();

    protected void applyStrategy(double Ffactor, double CRfactor, Individual x, Population pop) {
        int dim = x.getNumGenes();
        int strat = STRATEGY;
        double[] best = solver.getParents().getBestGenotype().getValues();;
        double[] p0 = pop.removeRandomGenotype().getValues();
        double[] p1 = pop.removeRandomGenotype().getValues();
        double[] p2 = pop.removeRandomGenotype().getValues();
        double[] p3 = pop.removeRandomGenotype().getValues();

        //choose random strategie
        if (strat <= 0 || strat >= strategies.length) {
            strat = 1 + random.nextInt(strategies.length - 1);
        }
        switch (strat) {
            case 1:
                DEBest1Bin(Ffactor, CRfactor, dim, x.getValues(), best, p0, p1);
                break;
            case 2:
                DEBest1Exp(Ffactor, CRfactor, dim, x.getValues(), best, p0, p1);
                break;
            case 3:
                DEBest2Bin(Ffactor, CRfactor, dim, x.getValues(), best, p0, p1, p2, p3);
                break;
            case 4:
                DEBest2Exp(Ffactor, CRfactor, dim, x.getValues(), best, p0, p1, p2, p3);
                break;
            case 5:
                DECurrent2Rand(Ffactor, CRfactor, dim, x.getValues(), p0, p1, p2);
            case 6:
                DERand1Bin(Ffactor, CRfactor, dim, x.getValues(), p0, p1, p2);
                break;
            case 7:
                DERand1Exp(Ffactor, CRfactor, dim, x.getValues(), p0, p1, p2);
                break;
            case 8:
                DERandToBest1Bin(Ffactor, CRfactor, dim, x.getValues(), best, p0, p1);
                break;
            case 9:
                DERandToBest1Exp(Ffactor, CRfactor, dim, x.getValues(), best, p0, p1);
                break;
            default:
                System.out.println("ERROR in STRATEGY");
        }


    }

    public Individual executeDE(Individual x, Population randomPop) {
        Individual mut = x.getClone();
        //double F = RandomVariable.uniform(0.5, 1.0);
        // double CR = RandomVariable.uniform(0.2, 0.9);
        applyStrategy(F, CR, mut, randomPop);
        mut.setIsEvaluated(false);
        mut.evaluate();
        solver.addEvaluation();
        if (mut.compareTo(x) >= 0) {
            return mut;
        } else {
            return x;
        }
    }
    
    @Override
    public Population execute(Population pop) {
        if( pop.getNumGenotypes() < 5)
            return pop;
        //make new population
        Population offspring = pop.getCleanCopie();
        Iterator<Individual> it = pop.getIterator();
        Individual selected;
        while (it.hasNext()) {
            selected = it.next();
            //select three individuals at random
            Population popDE = selRandom.execute(solver.getParents(), 4);
            //make the combinations
            offspring.addGenotype(executeDE(selected, popDE));
        }
        return offspring;
    }
    //////////////////////////////////////////////////////////////////////////    
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////  
    public void DEBest1Bin(double F, double Cr, int dim, double[] x,
            double[] best, double[] p0, double[] p1) {
        int i = random.nextInt(dim);
        int counter = 0;
        while (counter++ < dim) {
            if ((random.nextDouble() < Cr) || (counter == dim)) {
                x[i] = best[i] + F * (p0[i] - p1[i]);
            }
            i = ++i % dim;
        }
    }
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////  

    public void DEBest1Exp(double F, double Cr, int dim, double[] x,
            double[] best, double[] p0, double[] p1) {
        int i = random.nextInt(dim);
        int counter = 0;
        do {
            x[i] = best[i] + F * (p0[i] - p1[i]);
            i = ++i % dim;
        } while ((random.nextDouble() < Cr) && (++counter < dim));
    }
    //////////////////////////////////////////////////////////////////////////  

    public void DEBest2Bin(double F, double Cr, int dim, double[] x,
            double[] best, double[] p0, double[] p1, double[] p2, double[] p3) {
        int i = random.nextInt(dim);
        int counter = 0;
        while (counter++ < dim) {
            if ((random.nextDouble() < Cr) || (counter == dim)) {
                x[i] = best[i] + F * (p0[i] + p1[i] - p2[i] - p3[i]);
            }
            i = ++i % dim;
        }
    }
    //////////////////////////////////////////////////////////////////////////  

    public void DEBest2Exp(double F, double Cr, int dim, double[] x,
            double[] best, double[] p0, double[] p1, double[] p2, double[] p3) {
        int i = random.nextInt(dim);
        int counter = 0;
        do {
            x[i] = best[i] + F * (p0[i] + p1[i] - p2[i] - p3[i]);
            i = ++i % dim;
        } while ((random.nextDouble() < Cr) && (++counter < dim));
    }
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////  

    public void DECurrent2Rand(double F, double Cr, int dim, double[] x,
            double[] p0, double[] p1, double[] p2) {
        int i = random.nextInt(dim);
        for (i = 0; i < dim; i++) {
            x[i] = x[i] + Cr * (p0[i] - x[i]) + F * (p1[i] - p2[i]);
        }
    }
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////   

    public void DERand1Bin(double F, double Cr, int dim, double[] x,
            double[] p0, double[] p1, double[] p2) {
        int i = random.nextInt(dim);
        int counter = 0;
        while (counter++ < dim) {
            if ((random.nextDouble() < Cr) || (counter == dim)) {
                x[i] = p0[i] + F * (p1[i] - p2[i]);
            }
            i = ++i % dim;
        }
    }
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////            

    public void DERand1Exp(double F, double Cr, int dim, double[] x,
            double[] p0, double[] p1, double[] p2) {
        int i = random.nextInt(dim);
        int counter = 0;
        do {
            x[i] = p0[i] + F * (p1[i] - p2[i]);
            i = ++i % dim;
        } while ((random.nextDouble() < Cr) && (++counter < dim));
    }
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public void DERandToBest1Bin(double F, double Cr, int dim, double[] x,
            double[] best, double[] p0, double[] p1) {
        int i = random.nextInt(dim);
        int counter = 0;
        while (counter++ < dim) {
            if ((random.nextDouble() < Cr) || (counter == dim)) {
                x[i] += F * ((best[i] - x[i]) + (p0[i] - p1[i]));
            }
            i = ++i % dim;
        }
    }
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    public void DERandToBest1Exp(double F, double Cr, int dim, double[] x,
            double[] best, double[] p0, double[] p1) {
        int i = random.nextInt(dim);
        int counter = 0;
        do {
            x[i] += F * ((best[i] - x[i]) + (p0[i] - p1[i]));
            i = ++i % dim;
        } while ((random.nextDouble() < Cr) && (++counter < dim));
    }

    @Override
    public void setParameters(String str) {
        if (str == null || str.trim().length() == 0) {
            return;
        }
        StringTokenizer par = new StringTokenizer(str);
        try {
            F = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            F = 0.5;
        }
        try {
            CR = Double.parseDouble(par.nextToken());
        } catch (Exception e) {
            CR = 0.9;
        }
        try {
            STRATEGY = Integer.parseInt(par.nextToken());
        } catch (Exception e) {
            STRATEGY = 0;
        }
        if (STRATEGY < 0 || STRATEGY >= strategies.length) {
            STRATEGY = 0;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "<" + F + ">" + "<" + CR + ">" + "<" + strategies[STRATEGY] + ">";
    }

    @Override
    public String getInformation() {
        StringBuilder buf = new StringBuilder(toString());
        buf.append("\nDifferential Evolution");
        buf.append("\n\nParameters:<F><CR><Strategie> ");
        buf.append("\n         <F>:Mutation Factor");
        buf.append("\n        <CR>: Cossover Factor");
        buf.append("\n <Strategie>: number o DE strategie");
        buf.append("\n\nStrategies:");
        for (int i = 0; i < strategies.length; i++) {
            buf.append("\n").append(i).append(" - ").append(strategies[i]);
        }
        return buf.toString();
    }
     @Override
    public Recombination getClone() {
        DifferentialEvolution clone = (DifferentialEvolution)super.getClone();
        clone.CR = this.CR;
        clone.F = this.F;
        clone.STRATEGY = this.STRATEGY;
        return clone;
    }
}
