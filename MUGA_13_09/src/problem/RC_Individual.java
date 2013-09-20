/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem;

import genetic.Chromossom;
import genetic.gene.Gene;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import utils.BitField;
import utils.DynamicLoad;
import utils.Funcs;

/**
 *
 * @author manso
 */
public abstract class RC_Individual extends Individual {

    protected Random random = new SecureRandom();
    protected double[] genome = null;
    double MIN_VALUE;
    double MAX_VALUE;
    double dimension;

    public RC_Individual(boolean optimization, double min, double max, int size) {
        super(optimization);
        createGenes(size, min, max);
    }

    protected void createGenes(int size, double min, double max) {
        genome = new double[size];
        MIN_VALUE = min;
        MAX_VALUE = max;
        dimension = MAX_VALUE - MIN_VALUE;
        fillRandom();
        isEvaluated = false;
    }

    public double getMinValue() {
        return MIN_VALUE;
    }

    public double getmaxValue() {
        return MAX_VALUE;
    }

    public double getDimension() {
        return dimension;
    }

    /**
     * fill random all bits of every gene in genome
     */
    public void fillRandom() {
        for (int i = 0; i < genome.length; i++) {
            genome[i] = MIN_VALUE + random.nextDouble() * dimension;
        }
        isEvaluated = false;
    }

    /**
     * Clone of the individual
     * @return clone
     */
    public RC_Individual getClone() {
        //make new individuals
        RC_Individual ind = (RC_Individual) DynamicLoad.makeObject(this);
        //set the same information of this
        ind.setIndividual(this);
        return ind;
    }
    //copia de um individuo numCopias =1

    /**
     * Sets the information to the individual
     * @param other template of copy
     */
    @Override
    public void setIndividual(final Individual x) {
        //create new chromossom
        RC_Individual other = (RC_Individual) x;
        genome = new double[other.genome.length];
        System.arraycopy(other.genome, 0, genome, 0, genome.length);
        //sets the number of copies
        numCopys = other.numCopys;
        //set the fitness
        fitness = other.fitness;
        //set if evaluated
        isEvaluated = other.isEvaluated;
    }

    public double getGeneValue(int index) {
        return genome[index];
    }

    @Override
    public double[] getValues() {
        return genome;
        //return getCloneValues();
    }

    public double[] getCloneValues() {
        double[] tmp = new double[genome.length];
        System.arraycopy(genome, 0, tmp, 0, tmp.length);
        return tmp;
    }
    /* gets the gene at position index
     * @param index position in genome
     * @return gene in the position
     */

    @Override
    public void setGeneValue(int index, double value) {
        if (value > MAX_VALUE) {
//            double dif = value - MAX_VALUE;
//            setGeneValue(index, MAX_VALUE - dif / 10.0);
//            double dim = MAX_VALUE - MIN_VALUE;
//            while (value > MAX_VALUE) {
//                value -= dim;
//            }
//            System.out.println("");
            //TRUNC
            value = MAX_VALUE;
            
            //MIRROR
//            value = MAX_VALUE - random.nextDouble() * (value - MAX_VALUE) ;
            
            //ROTATE
//            value = MIN_VALUE +  random.nextDouble() * (value - MAX_VALUE);
        }
        if (value < MIN_VALUE) {
//           double dif = MIN_VALUE-value;
//            setGeneValue(index,MIN_VALUE + dif/10.0);
//            double dim = MAX_VALUE - MIN_VALUE;
//            while (value < MIN_VALUE) {
//                value += dim;
//            }
            value = MIN_VALUE;
           
            //Mirror
//            value = MIN_VALUE + random.nextDouble() * (MIN_VALUE - value) ;
            //ROTATE
//            value = MAX_VALUE - random.nextDouble() * (MIN_VALUE - value);
        }
        if (value > MAX_VALUE || value < MIN_VALUE) {
            setGeneValue(index, value);
        }
        if (genome[index] != value) {
            genome[index] = value;
            isEvaluated = false;
        }
    }
    @Override
    public void addValueToGene(int index, double value) {
        setGeneValue(index,genome[index] + value );
    }

    @Override
    public void setValues(double[] v) {
        for (int i = 0; i < v.length; i++) {
            setGeneValue(i, v[i]);
        }
        isEvaluated = false;
    }

    public double distanceTo(Individual other) {
        double dist = 0.0;
        //Genes of numbers
        // Euclidean Distance
        RC_Individual x = (RC_Individual) other;
        for (int nGene = 0; nGene < this.getNumGenes(); nGene++) {
            dist += Math.pow(genome[nGene] - x.genome[nGene], 2);
        }
        return Math.sqrt(dist);
    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    /**
     * gets number of genes
     * @return numbe of genes in genome
     */
    public int getNumGenes() {
        return genome.length;
    }
    @Override
    public String toString() {
        return toStringGenotype();
    }
    
    @Override
    public String toStringCSV() {
      final StringBuilder txt = new StringBuilder(super.toStringCSV());
        for (int i = 0; i < genome.length; i++) {
            txt.append(" , ").append(genome[i]);
        }        
        return txt.toString();
    }

    @Override
    public String toStringGenotype() {
        final StringBuilder result = new StringBuilder();
        if (this.numCopys > 1) {
            result.append("[").append(Funcs.IntegerToString(numCopys, 3)).append("] ");
        } else {
            result.append("      ");
        }

        if (isEvaluated) {
            result.append(Funcs.DoubleToString(fitness, 24));
        } else {
            result.append(Funcs.SetStringSize("NOT_EVAL.!", 10));
        }
        result.append(" = ");
        for (int i = 0; i < genome.length; i++) {
            result.append(Funcs.DoubleToString(genome[i], 24));
        }

        return result.toString();
    }

    /**
     * string of main features of individual
     * @return string
     */
    public String getGenomeInformation() {
        StringBuffer str = new StringBuffer();
        str.append("PROBLEM      : ");
        str.append(getClass().getName());
        str.append("\nOPTIMIZATION : ");
        str.append(typeOfOptimization ? "MAXIMIZE" : "MINIMIZE");
        str.append("\nBest Value   : " + this.getBest());
        int numGenes = this.genome.length;
        str.append("\nGENES        : " + numGenes);
        str.append("\nMinimum      : " + MIN_VALUE);
        str.append("\nMaximum      : " + MAX_VALUE);
        return str.toString();
    }

    @Override
    public String toStringPhenotype() {
        final StringBuffer result = new StringBuffer();
        if (this.numCopys > 1) {
            result.append("[").append(Funcs.IntegerToString(numCopys, 3)).append("] ");
        } else {
            result.append("      ");
        }

        if (isEvaluated) {
            result.append(Funcs.DoubleToString(fitness, 24));
        } else {
            result.append(Funcs.SetStringSize("NOT_EVAL.!", 10));
        }
        result.append(" = ");
        for (int i = 0; i < genome.length; i++) {
            result.append(Funcs.DoubleExponentialToString(genome[i], 12));
        }
        return result.toString();
    }

    /**
     * getDistance genomes of individuals
     * @param obj individual
     * @return value of comparition
     */
    public boolean equals(final Object obj) {
        if (obj instanceof Individual) {
            final RC_Individual other = (RC_Individual) obj;
            for (int i = 0; i < genome.length; i++) {
                if (genome[i] != other.genome[i]) {
                    return false;
                }
            }
            return true;
        } else {
            return super.equals(obj);
        }
    }
    
    @Override
    public int hashCode() {
        ArrayList lst = new ArrayList ();
        for (double d : genome) {
            lst.add(d);
        }
        return lst.hashCode();
        
//       int value = 1;
//	for(Double d : genome){
//	    value = 31*value + d.hashCode();
//	}
//	return value;
    }
    //---------------------------------------------------------------------

    public boolean isFunc3D() {
        return genome.length == 2;
    }
    //---------------------------------------------------------------------

    public boolean isFunc2D() {
        return genome.length == 1;
    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    public Chromossom getGenome() {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    public void addGene(Gene newGene) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    public Gene getGene(int index) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    public void setGene(int index, Gene g) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    

    public void setGeneDiscreteValue(int index, double value, long intervals) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    synchronized public void replaceGene(int index, Gene newGene) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    synchronized public void removeGene(Gene newGene) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    synchronized public Gene removeGeneAt(int index) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    synchronized public void insertGeneAt(int index, Gene newGene) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    synchronized public BitField getBits() {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    synchronized public void setBits(BitField bits) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }

    public void discretize(long intervals) {
        throw new UnsupportedOperationException("Not supported in real coded individuals");
    }
    public double[] getOptimum(){
        double [] opt = { (MIN_VALUE + MAX_VALUE)/2.0 };
        return opt;
    }
     //===================================================================================
//===================================================================================
    /**
     * Copare genotypes of two in dividuals
     *
     * @param i2
     * @return [-1](this < i2) [0](this==i2) [1](this > i2)
     */
    @Override
    public int compareGenotype(Individual other) {
        RC_Individual i2 = (RC_Individual)other;    
        for (int i = 0; i < genome.length; i++) {
           if(genome[i] > i2.genome[i]) return 1;
           if(genome[i] < i2.genome[i]) return -1;
        }
        return 0;
    }
//===================================================================================
//===================================================================================
}
