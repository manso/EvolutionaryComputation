
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import problem.Individual;
import utils.FastMergeSort;

/**
 *
 * @author manso
 */
public class SimplePopulation extends Population {

    /**
     * vector of population
     */
    protected ArrayList<Individual> pop = new ArrayList<Individual>();

    public SimplePopulation() {
    }

    public SimplePopulation(Iterator<Individual> it) {
        while(it.hasNext())
            pop.add(it.next());
    }
    

    @Override
    public void clear() {
        pop.clear();
    }

    @Override
    public int size() {
        return pop.size();
    }

    @Override
    public Iterator<Individual> getIterator() {
        return pop.iterator();
    }

    @Override
    public int getNumIndividuals() {
        return pop.size();
    }

    @Override
    public int getNumGenotypes() {
        return pop.size();
    }

    @Override
    public void addIndividual(Individual ind, int copies) {
         if( copies <=0 ){
            throw  new RuntimeException(" Number of copies of individual i <= 0");
        }
       for (int i = 0; i < copies; i++) {
            addGenotype(ind);
        }
    }

    @Override
    public void addGenotype(Individual ind) {        
        ind.setNumCopys(1);
        pop.add(ind.getClone());
    }

    @Override
    public Individual removeIndividual(int index) {
        return pop.remove(index);
    }

    @Override
    public boolean removeIndividual(Individual ind) {
        return pop.remove(ind);
    }

    @Override
    public boolean removeGenotype(Individual ind) {
        return removeIndividual(ind);
    }

    @Override
    public Individual removeGenotype(int index) {
        return removeIndividual(index);
    }

    @Override
    public int indexOfIndividual(Individual i) {
        return pop.indexOf(i);
    }

    @Override
    public int indexOfGenotype(Individual i) {
        return indexOfIndividual(i);
    }

    @Override
    public Individual getIndividual(int index) {
        return pop.get(index);
    }

    @Override
    public Individual getGenotype(int index) {
        return getIndividual(index);
    }
    public Individual getGenotype(Individual genotype){
        int index = pop.indexOf(genotype);
        return pop.get(index);
    }

    @Override
    public Iterable<Individual> getSortedIterable() {
        // Collections.sort(pop);
        FastMergeSort.sort(pop);
        Collections.reverse(pop);
        return pop;
    }
    @Override
    public Iterable<Individual> getSortedInverseIterable() {
//        Collections.sort(pop);
         FastMergeSort.sort(pop);
        return pop;
    }
}
