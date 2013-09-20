/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population;

import genetic.population.initialization.BinaryUniformGenerator;
import genetic.population.initialization.InitUniformPop;
import genetic.population.initialization.RealUniformGenerator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import problem.Individual;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import problem.PRM_Individual;
import problem.RC_Individual;
import utils.DynamicLoad;
import utils.Funcs;

/**
 *
 * @author arm
 */
public abstract class Population implements Serializable {

    /**
     * random generator
     */
    protected Random random = new Random();
    private int evaluations = 0;

    public void resetStats() {
        evaluations = 0;
    }

    /**
     * Returns a copy of the population without individuals preserves the
     * atributes evaluations, generation, maxvalue and minValue
     *
     * @return
     */
    public Population getCleanCopie() {
        Population clone = (Population) DynamicLoad.makeObject(this);
        return clone;
    }

    /**
     * Clear the populations
     */
    public abstract void clear();

    public abstract int size();

    public abstract Iterator<Individual> getIterator();

    public abstract Iterable<Individual> getSortedIterable();

    public abstract Iterable<Individual> getSortedInverseIterable();

    public abstract int getNumIndividuals();

    public abstract int getNumGenotypes();

    public abstract void addIndividual(Individual ind, int copies);

    public abstract void addGenotype(Individual ind);

    public abstract Individual removeIndividual(int index);

    public abstract boolean removeIndividual(Individual ind);

    public abstract boolean removeGenotype(Individual ind);

    public abstract Individual removeGenotype(int index);

    public abstract int indexOfIndividual(Individual i);

    public abstract int indexOfGenotype(Individual i);

    public abstract Individual getIndividual(int index);

    public abstract Individual getGenotype(int index);

    public abstract Individual getGenotype(Individual genotype);

    public boolean isEmpty() {
        return getNumGenotypes() == 0;
    }

    /**
     * Clone of the population
     *
     * @return
     */
    public synchronized Population getClone() {
        Population clone = (Population) DynamicLoad.makeObject(this);
        Iterator<Individual> iter = this.getIterator();
        while (iter.hasNext()) {
            Individual i = iter.next().getClone();
            clone.addGenotype(i);
        }
        return clone;
    }

    /**
     * create random population
     *
     * @param sizePop number of individuals
     * @param template template for the random individuals
     */
    public void createRandomPopulation(int sizePop, Individual template) {
        InitUniformPop generator = null;
        if (template instanceof RC_Individual) {
            generator = new RealUniformGenerator();
            generator.initialize(template, sizePop);
        } else if (template instanceof PRM_Individual) {
            generator = null;
        } else {
            generator = new BinaryUniformGenerator();
            generator.initialize(template, sizePop);
        }
        clear();

        while (size() < sizePop) {
            if (generator == null) {
                addRandom(template);
            } else {
                Individual ind = generator.generateUniform();
                addGenotype(ind);
            }
        }
        evaluations = 0;

        evaluate();
    }

    public synchronized void restart() {
        createRandomPopulation(getNumGenotypes(), getGenotype(0));
    }

    /**
     * create random population basead in the previous the individual and the
     * number of genotypes is the previous
     */
    public void setRandomPopulation() {
        createRandomPopulation(size(), getIndividual(0));
    }

    /**
     * evaluate the indivudals not evaluated
     */
    public void evaluate() {
        Iterator<Individual> iter = getIterator();
        while (iter.hasNext()) {
            if (iter.next().evaluate()) {
                addEvaluations(1);
            }
        }
    }
//-------------------------------------------------------------------------  

    public void addIndividual(Individual ind) {
        addIndividual(ind, 1);
    }

    /**
     * add random invidual to population. (new in Multi)
     *
     * @param template template of new randomdom
     */
    public void addRandom(Individual template) {
//        //make new individual
//        Individual newInd = template.getClone();
        //make new individuals using default constructor
        Individual newInd = (Individual) DynamicLoad.makeObject(template);
        //randomize the genotype
        newInd.fillRandom();
        //add the individual
        addIndividual(newInd, 1);
    }
/////////////////////////////////////////////////////////////////////////////////
//adiciona no fim

    /**
     * Join two populations
     *
     * add to this population the individuals of the other
     *
     * @param pop popoluation to be added
     */
    public void appendPopulation(Population newPop) {
        Iterator<Individual> iter = newPop.getIterator();
        while (iter.hasNext()) {
            Individual ind = iter.next();
            addGenotype(ind.getClone());
        }
    }

    /**
     * Remove the Genotypes of the population
     *
     * @param pop population to remove
     */
    public void removePopulation(Population pop) {
        Iterator<Individual> iter = pop.getIterator();
        while (iter.hasNext()) {
            Individual ind = iter.next();
            removeGenotype(ind);
        }
    }

    /**
     * sets a clone of the population to this
     *
     * @param newPop population to be cloned
     */
    public void setPopulation(Population newPop) {
        clear();
        appendPopulation(newPop);
    }
//--------------------------------------------------------------------------

    /**
     * calculate the best individuals of the population
     *
     * @return hall of fame of the population
     */
    public HallOfFame getGoods() {
        HallOfFame bpop = new HallOfFame();
        Iterator<Individual> it = this.getIterator();
        while (it.hasNext()) {
            bpop.add(it.next());
        }
        return bpop;
    }

    @Override
    public String toString() {
        return toStringGenotype();
    }
//--------------------------------------------------------------------------

    public String toStringGenotype() {
        if (size() == 0) {
            return "EMPTY";
        }
        Iterator<Individual> it = getIterator();
        StringBuffer txt = new StringBuffer();
        txt.append("Problem : " + getGenotype(0).getClass().getSimpleName() + "\t");
        txt.append("#Genotypes : " + getNumGenotypes());
        txt.append("\t#Individuals : " + getNumIndividuals());

//       for( Individual i: getSortedIterable()){
//            txt.append("\n" + i.toStringGenotype());
//        }
        Iterator<Individual> itr = getSortedIterable().iterator();
        while (itr.hasNext()) {
            Individual i = itr.next();
            String ind_str = i.toStringGenotype();
            if (i.isBest()) {
                ind_str = ind_str + "\t [OPTIMUM]";
            }
            txt.append("\n" + ind_str);
        }

        return txt.toString();
    }

    //--------------------------------------------------------------------------
    public String toSortedStringGenotype() {
        if (size() == 0) {
            return "EMPTY";
        }
        StringBuilder txt = new StringBuilder();
        txt.append("Problem : " + getGenotype(0).getClass().getSimpleName() + "\t");
        txt.append("#Genotypes : " + getNumGenotypes());
        txt.append("\t#Individuals : ").append(getNumIndividuals());
        txt.append("\tType : ").append(this.getClass().getSimpleName());

        if (getIndividual(0) instanceof RC_Individual) {
            //header
            txt.append("\n").append(Funcs.SetStringSize("COPIES", 9));
            txt.append(Funcs.SetStringSize("FITNESS", 24));
            for (int i = 0; i < getGenotype(0).getNumGenes(); i++) {
                txt.append(Funcs.SetStringSize("GENE " + i, 24));
            }
        }
        for (Individual ind : getSortedIterable()) {
            String ind_str = ind.toStringGenotype();
            if (ind.isBest()) {
                ind_str = ind_str + "\t [OPTIMUM]";
            }
            txt.append("\n" + ind_str);
        }
        return txt.toString();
    }

    //--------------------------------------------------------------------------
    public String toSortedStringGenotypeCSV() {
        if (size() == 0) {
            return "EMPTY";
        }
        StringBuilder txt = new StringBuilder();
        txt.append("Problem, " + getGenotype(0).getClass().getSimpleName() + "\n");

        if (getIndividual(0) instanceof RC_Individual) {
            //header
            txt.append("\n").append("COPIES,");
            txt.append(" FITNESS,");
            for (int i = 0; i < getGenotype(0).getNumGenes(); i++) {
                txt.append("GENE " + i + ",");
            }
        }
        for (Individual i : getSortedIterable()) {
            txt.append("\n").append(i.toStringGenotypeCSV());
            if (i.isBest()) {
                txt.append("\t [OPTIMUM]");
            }
        }
        return txt.toString();
    }
    //--------------------------------------------------------------------------

    public String toSortedStringPhenotype() {
        if (size() == 0) {
            return "EMPTY";
        }
        StringBuilder txt = new StringBuilder();
        txt.append("Problem : ").append(getGenotype(0).getClass().getSimpleName()).append("\t");
        txt.append("#Genotypes : ").append(getNumGenotypes());
        txt.append("\t#Individuals : ").append(getNumIndividuals());
        txt.append("\tType : ").append(this.getClass().getSimpleName());
        if (getIndividual(0) instanceof RC_Individual) {
            //header
            txt.append("\n").append(Funcs.SetStringSize("COPIES", 9));
            txt.append(Funcs.SetStringSize("FITNESS", 24));
            for (int i = 0; i < getGenotype(0).getNumGenes(); i++) {
                txt.append(Funcs.SetStringSize("GENE " + i, 24));
            }
        }
        for (Individual i : getSortedIterable()) {
            txt.append("\n").append(i.toStringPhenotype());
        }

        return txt.toString();
    }

//--------------------------------------------------------------------------
    public String toStringPhenotype() {
        if (size() == 0) {
            return "EMPTY";
        }
        Iterator<Individual> it = getIterator();
        StringBuilder txt = new StringBuilder();
        txt.append("Problem : ").append(getGenotype(0).getClass().getSimpleName()).append("\t");
        txt.append("#Genotypes : " + getNumGenotypes());
        txt.append("\t#Individuals : ").append(getNumIndividuals());

        Iterator<Individual> itr = getIterator();
        while (itr.hasNext()) {
            txt.append("\n").append(itr.next().toStringPhenotype());
        }
        return txt.toString();
    }
//--------------------------------------------------------------------------
//---------------------------------------------------------------------
//---------------------------------------------------------------------

    /**
     * write
     *
     * @param file stream
     */
    public void Write(String filename) {
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(filename);
            ObjectOutputStream objStream = new ObjectOutputStream(outStream);
            objStream.writeObject(this);


        } catch (Exception ex) {
            Logger.getLogger(Population.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outStream.close();


            } catch (IOException ex) {
                Logger.getLogger(Population.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
//------------------------------------------------------------------------------

    /**
     * read
     *
     * @param file stream
     */
    public static Population read(String filename) {
        FileInputStream inStream;
        Population pop = null;
        try {
            inStream = new FileInputStream(filename);
            ObjectInputStream objStream = new ObjectInputStream(inStream);
            pop = (Population) objStream.readObject();
            objStream.close();


        } catch (Exception ex) {
            Logger.getLogger(Population.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pop;
    }
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

    public Individual getRandomIndividual() {
        if (this.getNumGenotypes() <= 0) {
            System.out.println("ERROR getRandomIndividual() " + this.getClass().getCanonicalName());
        }
        return getIndividual(random.nextInt(this.getNumIndividuals()));
    }

    public Individual getRandomGenotype() {
        if (this.getNumGenotypes() <= 0) {
            System.out.println("ERROR getRandomIndividual() " + this.getClass().getCanonicalName());
        }
        return getGenotype(random.nextInt(this.getNumGenotypes()));
    }

    public Individual removeRandomGenotype() {
        return removeGenotype(random.nextInt(this.getNumGenotypes()));
    }

    public Individual removeBestGenotype() {
        Individual best = getBestGenotype();
        removeGenotype(best);
        return best;
    }

    public Individual removeWorstGenotype() {
        Individual worst = getWorstIndividual();
        removeGenotype(worst);
        return worst;
    }

    public Individual removeBestIndividual() {
        Individual best = getBestGenotype().getClone();
        best.setNumCopys(1);
        removeIndividual(best);
        return best;
    }

    public Individual removeRandomIndividual() {
        return removeIndividual(random.nextInt(this.getNumIndividuals()));
    }

    public Individual removeMostSimilar(Individual ind) {
        Individual sim = getMostSimilar(ind);
        removeGenotype(sim);
        return sim;
    }

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
    /**
     * @return the evaluations
     */
    public int getEvaluations() {
        return evaluations;
    }

    /**
     * @param evaluations the evaluations to set
     */
    public void setEvaluations(int evaluations) {
        this.evaluations = evaluations;
    }

    /**
     * @param evaluations the evaluations to set
     */
    public void addEvaluations(int evaluations) {
        this.evaluations += evaluations;
    }

    public double getBestValue() {

        Individual best = getBestGenotype();
        return best.getFitness();

    }

    public  Individual getBestGenotype() {
        Iterator<Individual> it = getIterator();
        Individual ind = it.next();
        while (it.hasNext()) {
            Individual i = it.next();
            if (i.compareTo(ind) > 0) {
                ind = i;
            }
        }
        return ind;
    }

    public Population getBestGenotypes(int size) {
        //normalize size
        size = size > getNumGenotypes() ? getNumGenotypes() : size;

        Iterator<Individual> it = getSortedIterable().iterator();
        Population best = getCleanCopie();
        for (int i = 0; i < size; i++) {
            best.addIndividual(it.next().getClone());
        }
        return best;
    }

    public synchronized Individual getWorstIndividual() {
        Iterator<Individual> it = getIterator();
        Individual ind = it.next();
        while (it.hasNext()) {
            Individual i = it.next();
            if (i.compareTo(ind) < 0) {
                ind = i;
            }
        }
        return ind;
    }

    public boolean contains(Individual ind) {
        return indexOfGenotype(ind) >= 0;
    }

//    public Individual getMostSimilar(Individual ind) {
//        int NUM_TRYS = 100;
//        if (contains(ind)) {
//            return ind;
//        }
//        Individual best = getRandomGenotype();
//        double distance = best.distanceTo(ind);
//        for (int i = 0; i < NUM_TRYS; i++) {
//            Individual ind2 = getRandomGenotype();
//            double d = ind2.distanceTo(ind);
//            if (d < distance) {
//                distance = d;
//                best = ind2;
//            }
//        }
//        return best;
//    }
    public Individual getMostSimilar(Individual ind) {
        Iterator<Individual> it = getIterator();
        //first individual
        Individual similar = it.next();
        double distance = similar.distanceTo(ind);
        while (it.hasNext()) {
            Individual ind2 = it.next();

            double d = ind2.distanceTo(ind);
            //distance zero - same individuals
            if (d == 0) {
                return ind2;
            }

            if (d < distance) {
                distance = d;
                similar = ind2;
            }
        }
        return similar;
    }

    /**
     * Return the most similar, but not equal individual
     *
     * @param ind individual to compare
     * @return similar but not equal
     */
    public Individual getSimilar(Individual ind) {
        Iterator<Individual> it = getIterator();
        Individual best = null;
        double distance = Double.MAX_VALUE;
        while (it.hasNext()) {
            Individual indPop = it.next();

            //first individual
            if (best == null) {
                best = indPop;
                distance = best.distanceTo(ind);
                continue;
            }
            //calculate the distance
            double d = indPop.distanceTo(ind);
            //compare if are equal
            if (d == 0) {
                continue;
            }
            if (d < distance) {
                distance = d;
                best = indPop;
            }
        }
        return best;
    }
}
