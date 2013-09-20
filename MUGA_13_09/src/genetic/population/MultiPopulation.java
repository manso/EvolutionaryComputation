/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.population;

import genetic.gene.GeneNumber;
import genetic.population.initialization.BinaryUniformGenerator;
import genetic.population.initialization.InitUniformPop;
import genetic.population.initialization.RealUniformGenerator;
import genetic.population.multiset.IndividualComparator;
import genetic.population.multiset.MiTree;
import genetic.population.multiset.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import problem.Individual;
import problem.PRM_Individual;
import problem.RC_Individual;
import utils.DynamicLoad;
import utils.FastMergeSort;

/**
 *
 * @author arm
 */
public class MultiPopulation extends Population {
//

//    MiTree<Individual> pop = new MiTree<>();
    MiTree<Individual> pop = new MiTree<>(new IndividualComparator());

//    UnsortedVector<Individual> pop = new UnsortedVector<Individual>();
    public MultiPopulation() {
    }

    public MultiPopulation(IndividualComparator comparator) {
        // pop = new MiTree<Individual>(comparator);
//        pop = new UnsortedVector<Individual>(comparator);
    }

    /**
     * create random population
     *
     * @param size number of individuals
     * @param template template for the random individuals
     */
    @Override
    public void createRandomPopulation(int size, Individual template) {
//        createGridRandomPopulation(size, template);
        InitUniformPop generator = null;
        if (template instanceof RC_Individual) {
            generator = new RealUniformGenerator();
            generator.initialize(template, size);
        } else if (template instanceof PRM_Individual) {
            generator = null;
        } else {
            generator = new BinaryUniformGenerator();
            generator.initialize(template, size);
        }
        clear();
        int tryNumber = size * 10;
        while (tryNumber > 0 && size() < size) {
            if (generator == null) {
                addRandom(template);
            } else {
                Individual ind = generator.generateUniform();
                addGenotype(ind);
            }
            tryNumber--;
        }
        //clean number of individuals
        Iterator<Individual> elems = pop.iteratorSupportSet();
        while (elems.hasNext()) {
            //element of population
            Individual elem = elems.next();
            //sets number of copies to one
            pop.update(elem, 1);
        }

    }

    /**
     * create random population
     *
     * @param size number of individuals
     * @param template template for the random individuals
     */
    public void createGridRandomPopulation(int size, Individual template) {
        // 2 genes - root(2)
        // 3 genes - root(3)
        this.GRID_MINSIZE = (int) Math.ceil(Math.pow(size, 1.0 / template.getNumGenes()));

        clear();
        while (size() < size) {
            //make new individual
            Individual newInd = template.getClone();
            //randomize the genotype
            newInd.fillRandom();
            //discretize individuals
            newInd.discretize(GRID_MINSIZE);
            //add the individual
            if (!isGridOcuppied(newInd, GRID_MINSIZE)) {
                addIndividual(newInd, 1);
                //System.out.println(pop.getSupportSize() + " - " + newInd);
            }
        }
        //clean number of individuals
        Iterator<Individual> elems = pop.iteratorSupportSet();
        while (elems.hasNext()) {
            //element of population
            Individual elem = elems.next();
            //sets number of copies to one
            pop.update(elem, 1);
        }

    }

    @Override
    public void clear() {
        pop.clear();
    }

    @Override
    public int size() {
        return pop.getSupportSize();
    }

    @Override
    public Iterator<Individual> getIterator() {
        return new MultisetIterator();
    }

    @Override
    public Iterable<Individual> getSortedIterable() {
        ArrayList<Individual> sort = new ArrayList<Individual>(size());
        Iterator<Individual> iter = getIterator();
        while (iter.hasNext()) {
            sort.add(iter.next());
        }
        FastMergeSort.descendingSort(sort);
//        Collections.sort(sort);
//        Collections.reverse(sort);
        return sort;
    }

    public Iterable<Individual> getSortedInverseIterable() {
        ArrayList<Individual> sort = new ArrayList<Individual>(size());
        Iterator<Individual> iter = getIterator();
        while (iter.hasNext()) {
            sort.add(iter.next());
        }
        FastMergeSort.sort(sort);
//        Collections.sort(sort);
        // Collections.reverse(sort);
        return sort;
    }

    @Override
    public int getNumIndividuals() {
        return pop.getCardinalitySize();
    }

    @Override
    public int getNumGenotypes() {
        return pop.getSupportSize();
    }

    @Override
    public void addIndividual(Individual ind, int copies) {
        if (copies <= 0) {
            throw new RuntimeException(copies + " Number of copies of individual i <= 0");
        }
        pop.add(ind, copies);
    }

    @Override
    public void addGenotype(Individual ind) {
        if (ind.getNumCopies() <= 0) {
            throw new RuntimeException(" Number of copies of individual i <= 0");
        }
        pop.add(ind, ind.getNumCopies());
    }

    @Override
    public Individual removeIndividual(int index) {
        Individual ind = pop.removeIndex(index).getClone();
        ind.setNumCopys(1);
        return ind;
    }

    @Override
    public boolean removeIndividual(Individual ind) {
        return pop.remove(ind) != null;
    }

    @Override
    public boolean removeGenotype(Individual ind) {
        if (!pop.contains(ind)) {
            return false;
        }
        pop.removeAll(ind);
        return true;
    }

    @Override
    public Individual removeGenotype(int index) {
        Pair<Individual> p = pop.removePairIndex(index);
        p.elem.setNumCopys(p.copies);
        return p.elem;
    }

    @Override
    public int indexOfIndividual(Individual i) {
        return pop.indexOf(i);
    }

    @Override
    public int indexOfGenotype(Individual i) {
        try {
            return pop.indexOfPair(i);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public Individual getIndividual(int index) {
        Individual ind = pop.getIndex(index);
        ind.setNumCopys(1);
        return ind;
    }

    @Override
    public Individual getGenotype(int index) {
        Pair<Individual> p = pop.getPairIndex(index);
        p.elem.setNumCopys(p.copies);
        return p.elem;
    }

    @Override
    public Individual getGenotype(Individual genotype) {
        return pop.get(genotype);
    }

    public void reScaleCopys(double factor) {
        Iterator<Individual> elems = pop.iteratorSupportSet();
        Iterator<Integer> card = pop.iteratorCardinalitySet();
        //minimum 1 individual maximum 5%
        int max = 1 + (int) (getNumGenotypes() * 0.05);
        while (elems.hasNext()) {
            //element of population
            Individual elem = elems.next();
            //new number of copies
            double ncopies = Math.min(Math.ceil(card.next().intValue() / factor), max);
            if (ncopies == 0) {
                System.out.println(" ERROR " + this.getClass().getCanonicalName());
                return;
            }
            //sets number of copies
            pop.update(elem, (int) ncopies);
        }
    }

    public void cleanNumberOfCopies() {
        Iterator<Individual> elems = pop.iteratorSupportSet();
        while (elems.hasNext()) {
            //element of population
            Individual elem = elems.next();
            pop.update(elem, 1);
        }
    }
//
//    public void reScaleCopys(double factor) {
//        Iterator<Individual> elems = getSortedInverseIterable().iterator();
//        int max = getNumGenotypes() * 2;
//        int numIndividuals = getNumIndividuals();
//        int maxCopies = getNumGenotypes() / 10;
//        while (elems.hasNext()) {
//            //element of population
//            Individual elem = elems.next();
//            //new number of copies
//            if (numIndividuals > max || elem.getNumCopies() > maxCopies) {
//                double ncopies = Math.min(Math.ceil(elem.getNumCopies() / factor), maxCopies);
//                if (ncopies == 0) {
//                    System.out.println(" ERROR " + this.getClass().getCanonicalName());
//                    return;
//                }
//                //sets number of copies
//                pop.update(elem, (int) ncopies);
//            }
//        }
//    }

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    public class MultisetIterator implements Iterator<Individual> {

        Iterator<Pair<Individual>> it = pop.iteratorMultiSet();

        public boolean hasNext() {
            return it.hasNext();
        }

        public Individual next() {
            Pair<Individual> pair = it.next();
            pair.elem.setNumCopys(pair.copies);
            return pair.elem;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    ////////////////////////////////////////////////////////////////
//===================================================================================
//===================================================================================
//===================================================================================
//-----------------------------------------------------------------------------------
//                        D I S C R E T I Z A T I O N
//-----------------------------------------------------------------------------------
    //minimum of intervals
    public long GRID_MINSIZE = 10;

    public long getGridMinSize() {
        return GRID_MINSIZE;
    }

    //-----------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------
    /**
     * return the most similar individual in the population using grid
     *
     * @param newIndividual template to serach the similar
     * @return most similar in grid
     */
    public boolean isGridOcuppied(Individual newIndividual, long intervals) {
        Iterator<Individual> it = getIterator();
        while (it.hasNext()) {
            //next individual
            Individual parent = it.next();
            //distance to new individual
            if (getGridDistance(newIndividual, parent, intervals) == 0.0) {
                return true;
            }
        }
        return false;
    }
    //-----------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------

    /**
     * calculate the distance between two individuals in the grid if the
     * individuals are in the same grid the distance is zero
     *
     * @param i1 first individual
     * @param i2 second individual
     * @param intervals number of intervals in the gene space
     * @return
     */
    public double getGridDistance(Individual i1, Individual i2, long intervals) {
        boolean is_in_grid = true;
        for (int i = 0; i < i1.getNumGenes(); i++) {
            long g1 = getGridNumber((GeneNumber) i1.getGene(i), intervals);
            long g2 = getGridNumber((GeneNumber) i2.getGene(i), intervals);
            if (g1 != g2) {
                is_in_grid = false;
                break;
            }
        }
        if (is_in_grid) {
            return 0;
        } else {
            return i1.distanceTo(i2);
        }
    }
//-----------------------------------------------------------------------------------

    public long getGridNumber(GeneNumber g, long interval) {
        //dimension of interval
        double dim = g.getMaxValue() - g.getMinValue();
        // proportion of value
        double proportion = (g.getValue() - g.getMinValue()) / dim;
        //convert to integer
        return (long) (interval * proportion);
    }
//-----------------------------------------------------------------------------------
//===================================================================================

    /**
     * // * return the most similar individual in the population using grid // *
     * //
     *
     * @param newIndividual template to serach the similar //
     * @return most similar in grid //
     */
//    public Individual getSimilar(Individual newIndividual) {
//        Iterator<Individual> it = getIterator();
//        Individual similar = null;
//        double distance = Double.MAX_VALUE;
//        while (it.hasNext()) {
//            //next individual
//            Individual parent = it.next();
//            //distance to new individual
//            double distanceToInd = newIndividual.distanceTo(parent);
//            //is the same
//            if (distanceToInd == 0) {
//                return parent;
//            }
//            //----------------------------------
//            if (similar == null || distanceToInd < distance) {
//                similar = parent;
//                distance = distanceToInd;
//            }
//        }
//        return similar;
//    }
//===================================================================================
//===================================================================================
    /**
     * Clone of the population
     *
     * @return
     */
    public Population getClone() {
        MultiPopulation clone = (MultiPopulation) super.getClone();
        clone.GRID_MINSIZE = this.GRID_MINSIZE;
        return clone;
    }

    /**
     * Returns a copy of the population without individuals preserves the
     * atributes evaluations, generation, maxvalue and minValue
     *
     * @return
     */
    @Override
    public Population getCleanCopie() {
        MultiPopulation clone = (MultiPopulation) DynamicLoad.makeObject(this);
        clone.GRID_MINSIZE = this.GRID_MINSIZE;
        return clone;
    }
}
