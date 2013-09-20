
/*  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 2.1 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
/*
 * Individual.java
 *
 * Created on 12 de Dezembro de 2004, 19:11
 */
package problem;

import genetic.Chromossom;
import genetic.gene.Gene;
import genetic.gene.GeneNumber;
import java.io.Serializable;
import utils.BitField;
import utils.DynamicLoad;
import utils.Funcs;

/**
 * Represents a individual in the population.<br> News problems could be solved
 * by GA <br> writen new individuals derivided by this Abstract individual<br>
 * to see HOW please open Simples.java<br>
 *
 * @author António Manuel Rodrigues Manso
 */
public abstract class Individual implements Comparable, Serializable {

    /**
     * Static constant to MAXIMIZE problems
     */
    public static boolean MAXIMIZE = true;
    /**
     * Static constant to MINIMIZE problems
     */
    public static boolean MINIMIZE = false;
    /**
     * type o the problem
     */
    public static boolean typeOfOptimization = MAXIMIZE;
    /**
     * best value of the individual CLASS
     */
    private static double best = Double.NaN;

    /**
     * best individual Know
     */
    // public static Individual bestIndividual = null;
    /**
     * Set the parameters to individual - to default do not do anithing
     *
     * @param params - string to the parametrs separated by spaces
     */
    public void setParameters(String params) {
    }

    public String getParameters() {
        return "";
    }

    /**
     * Sets the best value
     *
     * @param value
     */
    public static void setBest(double value) {
        best = value;
    }

    /**
     * gets the best value
     *
     * @return
     */
    public static double getBest() {
        return best;
    }

    /**
     * is the best individual
     *
     * @return
     */
    public boolean isBest() {
        return this.getFitness() == best;
    }

    /**
     * get fitness of individual
     *
     * @return fitness of individual
     */
    protected abstract double fitness();
    /**
     * number of copys of this individual in population
     */
    protected int numCopys = 1;
    /**
     * value of fitness of individual
     */
    protected double fitness = Double.NaN;
    /**
     * is evaluated
     */
    protected boolean isEvaluated = false;
    /**
     * Holds value of property genome.
     */
    protected Chromossom genome;

    /**
     * Creates a new individual
     *
     * @param typeOptimization ( MAXIMIZE / MINIMIZE )
     */
    public Individual(boolean typeOptimization) {
        //new Chromossom
        genome = new Chromossom();
        //number of copys
        numCopys = 1;
        //type of problem
        typeOfOptimization = typeOptimization;
        //not evaluated
        isEvaluated = false;
    }

    /**
     * Evaluate the individual
     */
    public boolean evaluate() {
        if (!isEvaluated()) {
            fitness = fitness();
            isEvaluated = true;
            return true;
        }
        return false;

    }

    //copia de um individuo numCopias =1
    /**
     * Sets the information to the individual
     *
     * @param other template of copy
     */
    public void setIndividual(final Individual other) {
        //create new chromossom
        genome = new Chromossom();
        //sets the number of copies
        numCopys = other.numCopys;
        //set the fitness
        setFitness(other.getFitness());
        //set if evaluated
        setIsEvaluated(other.isEvaluated());
        //set all genes
        for (int i = 0; i < other.genome.getNumGenes(); i++) {
            //clone of genes
            genome.addGene(other.genome.getGene(i).getClone());
        }
    }
    //copia integral de um individuo

    /**
     * Clone of the individula
     *
     * @return clone
     */
    public Individual getClone() {
        //make new individuals
        Individual ind = (Individual) DynamicLoad.makeObject(this);
        //set the same information of this
        ind.setIndividual(this);
        return ind;
    }

    /**
     * Getter for property numCopys.
     *
     * @return Value of property numCopys.
     */
    public int getNumCopies() {

        return this.numCopys;
    }

    /**
     * Setter for property numCopys.
     *
     * @param copys new number os copys
     */
    public void setNumCopys(int copys) {
        this.numCopys = copys;
    }

    /**
     * Increment number os copys of this individual by one
     */
    public void incrementCopys() {
        this.numCopys++;
    }

    /**
     * Increment number os copys of this individual by value param
     *
     * @param value nnumber of copyes to add
     */
    public void incrementCopys(int value) {
        this.numCopys += value;
    }

    /**
     * Decrement number os copys of this individual by one
     */
    public void decrementCopys() {
        numCopys--;
    }

    /**
     * Decrement number os copys of this individual by one
     */
    public void decrementCopys(int value) {
        numCopys -= value;
    }

    /**
     * Getter for property fitness.
     *
     * @return Value of property fitness.
     */
    public double getFitness() {
        if (!isEvaluated) {
            return Double.NaN;
        }
        return this.fitness;
    }

    /**
     * Setter for property fitness.
     *
     * @param fitness New value of property fitness.
     */
    public void setFitness(double fitness) {
        setIsEvaluated(true);
        this.fitness = fitness;
    }

    /**
     * Getter for property genome.
     *
     * @return Value of property genome.
     */
    public Chromossom getGenome() {
        return this.genome;
    }

//    /**
//     * Setter for property genome.
//     * @param genome New value of property genome.
//     */
//    public void setGenome(Chromossom genome) {
//        this.genome = genome;
//        fitness = 0;
//        setIsEvaluated(false);
//    }
    /**
     * add a clone of the new gene to individual
     *
     * @param newGene template of new gene
     */
    public void addGene(Gene newGene) {
        genome.addGene(newGene);
        //not evaluated
        isEvaluated = false;
    }

    /**
     * gets number of genes
     *
     * @return numbe of genes in genome
     */
    public int getNumGenes() {
        return genome.getNumGenes();
    }

    /**
     * gets the gene at position index
     *
     * @param index position in genome
     * @return gene in the position
     */
    public Gene getGene(int index) {
        return genome.getGene(index);
    }

    /**
     * gets the gene at position index
     *
     * @param index position in genome
     * @return gene in the position
     */
    public double getGeneValue(int index) {
        return genome.getGene(index).getValue();
    }

    /**
     * dimension of the search space
     *
     * @return max - min value of the gene
     */
    public double getDimension() {
        try {
            return ((GeneNumber) getGene(0)).getDimension();
        } catch (Exception e) {
            //gene binary
            return 1;
        }
    }

    public double getMinValue() {
        try {
            return ((GeneNumber) getGene(0)).getMinValue();
        } catch (Exception e) {
            //gene binary
            return 0;
        }
    }

    public double getmaxValue() {
        try {
            return ((GeneNumber) getGene(0)).getMaxValue();
        } catch (Exception e) {
            //gene binary
            return 1;
        }
    }

    /**
     * gets the gene at position index
     *
     * @param index position in genome
     * @return gene in the position
     */
    public void setGene(int index, Gene g) {
        genome.setGene(index, g);
        isEvaluated = false;
    }

    /**
     * gets the gene at position index
     *
     * @param index position in genome
     * @return gene in the position
     */
    public void setGeneValue(int index, double value) {
        genome.getGene(index).setValue(value);
        isEvaluated = false;
    }

    public void addValueToGene(int index, double value) {
        setGeneValue(index, getGene(index).getValue() + value);
    }

    /**
     * gets the gene at position index
     *
     * @param index position in genome
     * @return gene in the position
     */
    public void setGeneDiscreteValue(int index, double value, long intervals) {
        ((GeneNumber) genome.getGene(index)).setValue(value, intervals);
        isEvaluated = false;
    }

    /**
     * replace one gene in genome
     *
     * @param index index o gene to be replaced
     * @param newGene template of new gene
     */
    synchronized public void replaceGene(int index, Gene newGene) {
        genome.replaceGene(index, newGene);
        isEvaluated = false;
    }

    /**
     * remove one gene from genome
     *
     * @param newGene gene to be removed
     */
    synchronized public void removeGene(Gene newGene) {
        genome.removeGene(newGene);
        isEvaluated = false;
    }

    /**
     * remove a gene placede in position index
     *
     * @param index position of gene
     */
    synchronized public Gene removeGeneAt(int index) {
        isEvaluated = false;
        return genome.removeGeneAt(index);

    }

    /**
     * remove a gene placede in position index
     *
     * @param index position of gene
     */
    synchronized public void insertGeneAt(int index, Gene newGene) {
        isEvaluated = false;
        genome.addGeneAt(index, newGene);
    }

    /**
     * fill random all bits of every gene in genome
     */
    public void fillRandom() {
        genome.fillRandom();
        isEvaluated = false;
        fitness = Double.NaN;
    }

    /**
     * string
     *
     * @return string
     */
    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        //number of copies
        String elem = "";

        if (isEvaluated) {
            result.append(Funcs.DoubleToString(fitness, 24));
        } else {
            result.append(Funcs.SetStringSize(" NOT EVALUETED ! ", 24));
        }
        result.append(" [" + this.getNumCopies() + "] ");
        result.append(genome.toString());
        return result.toString();

    }

    /**
     * string
     *
     * @return string
     */
    public String toStringCSV() {
        final StringBuilder txt = new StringBuilder();
        txt.append("Copies, ").append(getNumCopies()).append(", Fitness,");
        if (isEvaluated) {
            txt.append(fitness);
        } else {
            txt.append("NOT_EVALUETED");
        }
        txt.append(", Genome, ").append(genome.toStringCSV());
        return txt.toString();

    }

    public String toStringGenotype() {
        final StringBuilder result = new StringBuilder();
        //number of copies
        String elem = "";

        if (isEvaluated) {
            result.append(Funcs.DoubleToString(fitness, 24));
        } else {
            result.append(Funcs.SetStringSize(" NOT EVALUATED ! ", 24));
        }
        result.append(" = ");

        if (this.numCopys > 1) {
            elem = "[" + Funcs.IntegerToString(numCopys, 4) + "]";
        }
        result.append(Funcs.SetStringSize(elem, 8));
        //genotype
        for (int i = 0; i < genome.getNumGenes(); i++) {
            result.append(genome.getGene(i).toBinString() + " ");
        }
        if (isBest()) {
            result.append(" \t[OPTIMUM]");
        }
        return result.toString();
    }

    public String toStringGenotypeCSV() {
        final StringBuilder result = new StringBuilder();
        //number of copies
        String elem = "";

        if (isEvaluated) {
            result.append(fitness + ",");
        } else {
            result.append("NOT EVALUATED,");
        }

        result.append(" " + numCopys + ", ");
        //genotype
        for (int i = 0; i < genome.getNumGenes(); i++) {
            result.append(genome.getGene(i).toBinString() + ", ");
        }
        if (isBest()) {
            result.append(" \t[OPTIMUM]");
        }
        return result.toString();
    }

    public String toStringPhenotype() {
        return toStringGenotype();
    }

    /**
     * getDistance genomes of individuals
     *
     * @param obj individual
     * @return value of comparition
     */
    public boolean equals(final Object obj) {
        if (obj instanceof Individual) {
            final Individual other = (Individual) obj;
            return this.getClass().equals(other.getClass())
                    && other.genome.equals(this.genome);
        } else {
            return super.equals(obj);
        }
    }

    /**
     * fitness comparation
     *
     * @param other x
     * @return x
     */
    @Override
    public int compareTo(Object other) {

        Individual tmp = (Individual) other;
        if (!tmp.isEvaluated) {
            if (this.isEvaluated) {
                return 1;
            }
            return -1;
        }
        if (typeOfOptimization == MAXIMIZE) {
            //ordenar por ordem decrescente
            if (this.getFitness() > tmp.getFitness()) {
                return 1;
            }
            if (this.getFitness() < tmp.getFitness()) {
                return -1;
            }
        } else {
            //ordenar por ordem crescente
            if (this.getFitness() < tmp.getFitness()) {
                return 1;
            }
            if (this.getFitness() > tmp.getFitness()) {
                return -1;
            }
        }
        return 0;
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
        str.append("\nBest Value   : " + this.best);
        int numGenes = this.genome.getNumGenes();
        str.append("\nGENES        : " + numGenes);
        for (int index = 0; index < numGenes; index++) {
            Gene g = genome.getGene(index);
            str.append("\n\t");
            str.append(g.getClass().getName());
            str.append(" - ");
            str.append(g.getAlels().getNumberOfBits());
            str.append(" bits");
        }
        return str.toString();
    }

    /**
     * @return the isEvaluated
     */
    public boolean isEvaluated() {
        return isEvaluated;
    }

    /**
     * @param isEvaluated the isEvaluated to set
     */
    public void setIsEvaluated(boolean isEvaluated) {
        this.isEvaluated = isEvaluated;
    }
    //---------------------------------------------------------------------
    //---------------------------------------------------------------------

    public boolean isFunc3D() {
        if (this.genome.getNumGenes() == 2
                && genome.getGene(0) instanceof GeneNumber
                && genome.getGene(1) instanceof GeneNumber) {
            return true;
        }
        return false;
    }
    //---------------------------------------------------------------------

    public boolean isFunc2D() {
        if (this.genome.getNumGenes() == 1
                && genome.getGene(0) instanceof GeneNumber) {
            return true;
        }
        return false;
    }

    synchronized public BitField getBits() {
        return genome.getBinString();
    }

    synchronized public void setBits(BitField bits) {
        genome.setBinString(bits);
        isEvaluated = false;
    }

    public String getName() {
        return this.getClass().getSimpleName() + " " + getParameters();
    }

    @Override
    public int hashCode() {
        return genome.hashCode();
    }

    public String getInfo() {
        return getClass().getSimpleName();
    }

    public double distanceTo(Individual other) {
        double dist = 0.0;
        //Genes of numbers
        // Euclidean Distance
        if (getGene(0) instanceof GeneNumber) {
            for (int nGene = 0; nGene < this.getNumGenes(); nGene++) {
                double v1 = this.getGene(nGene).getValue();
                double v2 = other.getGene(nGene).getValue();
                dist += Math.pow(v1 - v2, 2);
            }
            return Math.sqrt(dist);
        }
        //Hamming
        for (int nGene = 0; nGene < this.getNumGenes(); nGene++) {
            dist += this.getGene(nGene).getAlels().getHammingDistance(other.getGene(nGene).getAlels());
        }
        return dist;
    }

    public double[] getValues() {
        double[] value = new double[this.getNumGenes()];
        for (int i = 0; i < value.length; i++) {
            value[i] = getGene(i).getValue();
        }
        return value;
    }

    public void setValues(double[] v) {
        double[] value = new double[this.getNumGenes()];
        for (int i = 0; i < value.length; i++) {
            setGeneValue(i, v[i]);
        }
        isEvaluated = false;
    }
//===================================================================================
//===================================================================================
    /**
     * Copare genotypes of two in dividuals
     *
     * @param i2
     * @return [-1](this < i2) [0](this==i2) [1](this > i2)
     */
    public int compareGenotype(Individual i2) {
        //for all genes
        for (int nGene = 0; nGene < this.getNumGenes(); nGene++) {
            //alelels of the genes
            BitField b1 = genome.getGene(nGene).getAlels();
            BitField b2 = i2.genome.getGene(nGene).getAlels();
            for (int i = 0; i < b1.getNumberOfBits(); i++) {
                //bits are differents
                if (b1.getBit(i) != b2.getBit(i)) {
                    if (b1.getBit(i)) {
                        return -1;
                    }
                    return 1;
                }//bits difs
            }//for bits
        }
        return 0;
    }
    
    public void restart(){
      //make new individuals
        Individual ind = (Individual) DynamicLoad.makeObject(this);
        //set the information of New Individual
        setIndividual(ind);
    }
//===================================================================================
//===================================================================================
//-----------------------------------------------------------------------------------
//                        D I S C R E T I Z A T I O N
//-----------------------------------------------------------------------------------

    public void discretize(long intervals) {
        genome.discretize(intervals);
    }
//===================================================================================
//===================================================================================
//===================================================================================
}
