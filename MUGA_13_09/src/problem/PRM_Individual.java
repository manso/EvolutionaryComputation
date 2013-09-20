/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problem;

import genetic.Chromossom;
import genetic.gene.Gene;
import java.util.ArrayList;
import java.util.Random;
import utils.BitField;
import utils.DynamicLoad;
import utils.Funcs;
import utils.MetricsA.Distances;

/**
 *
 * @author manso
 */
public abstract class PRM_Individual extends Individual {

    protected Random random = new Random();
    protected int[] genome = null;

    public PRM_Individual(boolean optimization, int size) {
        super(optimization);
        genome = new int[size];
        for (int i = 0; i < genome.length; i++) {
            genome[i] = i;
        }
        fillRandom();
        isEvaluated = false;
    }

    @Override
    public void fillRandom() {
        int index = genome.length - 1;
        while (index > 0) {
            int i = random.nextInt(index);
            int aux = genome[i];
            genome[i] = genome[index];
            genome[index] = aux;
            index--;
        }
    }

    /**
     * Clone of the individula
     *
     * @return clone
     */
    @Override
    public PRM_Individual getClone() {
        //make new individuals
        PRM_Individual ind = (PRM_Individual) DynamicLoad.makeObject(this);
        //set the same information of this
        ind.setIndividual(this);
        return ind;
    }
    //copia de um individuo numCopias =1

    /**
     * Sets the information to the individual
     *
     * @param other template of copy
     */
    @Override
    public void setIndividual(final Individual x) {
        //create new chromossom
        PRM_Individual other = (PRM_Individual) x;
        genome = new int[other.genome.length];
        System.arraycopy(other.genome, 0, genome, 0, genome.length);
        //sets the number of copies
        numCopys = other.numCopys;
        //set the fitness
        fitness = other.fitness;
        //set if evaluated
        isEvaluated = other.isEvaluated;
    }

    public int[] getGeneValues() {
        return genome;
        //return getCloneValues();
    }

    public int get(int index) {
        return genome[index];
    }

    public int getNext(int index) {
        return genome[(index + 1) % genome.length];
    }

    public int getPrevious(int index) {
        return genome[(index - 1 + genome.length) % genome.length];
    }

    public int indexOf(int elem) {
        for (int i = 0; i < genome.length; i++) {
            if (elem == i) {
                return i;
            }
        }
        return -1;
    }

    private void swap(int i, int j) {
        int aux = genome[i];
        genome[i] = genome[j];
        genome[j] = aux;
    }

    public void invert(int begin, int end) {
        //inversion <-<-E------B<-<-<-
        if (begin > end) {
            int itera = (genome.length - begin + end) / 2;
            while (itera-- > 0) {
                swap(begin, end);
                end = (genome.length + end - 1) % genome.length;
                begin = (begin + 1) % genome.length;
            }
        } else {
            //inversion b ---B<-<-<-E---
            while (begin < end) {
                swap(begin, end);
                begin++;
                end--;
            }
        }
        this.setIsEvaluated(false);
    }

    /*
     * gets the gene at position index @param index position in genome @return
     * gene in the position
     */
    @Override
    public void setGeneValue(int index, double value) {

        if (genome[index] != value) {
            genome[index] = (int) value;
            isEvaluated = false;
        }
    }

    public void setGeneValues(int[] values) {
        genome = values;
        isEvaluated = false;
    }

    @Override
    public double distanceTo(Individual other) {
        return Distances.computeLevenshteinDistance(genome, ((PRM_Individual) other).genome);
    }

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    /**
     * gets number of genes
     *
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
            result.append(Funcs.SetStringSize("NOT_EVAL!", 10));
        }
        result.append(" = ");
        for (int i = 0; i < genome.length; i++) {
            result.append(Funcs.IntegerToString(genome[i], 5));
        }

        return result.toString();
    }

    /**
     * string of main features of individual
     *
     * @return string
     */
    public String getGenomeInformation() {
        StringBuffer str = new StringBuffer();
        str.append("PROBLEM      : ");
        str.append(getClass().getName());
        str.append("\nOPTIMIZATION : ");
        str.append(typeOfOptimization ? "MAXIMIZE" : "MINIMIZE");
        str.append("\nBest Value   : " + getBest());
        int numGenes = this.genome.length;
        str.append("\nGENES        : " + numGenes);
        return str.toString();
    }

    @Override
    public String toStringPhenotype() {
        return toString();
    }

    /**
     * getDistance genomes of individuals
     *
     * @param obj individual
     * @return value of comparition
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Individual) {
            final PRM_Individual other = (PRM_Individual) obj;
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
        ArrayList lst = new ArrayList();
        for (int d : genome) {
            lst.add(d);
        }
        return lst.hashCode();
//	return value;
    }
    //---------------------------------------------------------------------  
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
        PRM_Individual i2 = (PRM_Individual)other;    
        for (int i = 0; i < genome.length; i++) {
           if(genome[i] > i2.genome[i]) return 1;
           if(genome[i] < i2.genome[i]) return -1;
        }
        return 0;
    }
//===================================================================================
//===================================================================================
}
