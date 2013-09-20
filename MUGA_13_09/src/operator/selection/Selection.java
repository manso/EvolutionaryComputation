/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator.selection;

import genetic.population.Population;
import operator.GeneticOperator;

/**
 *
 * @author manso
 */
public abstract class Selection extends GeneticOperator {

    //by default select the same number of individuals as defines in simple Solver
    protected int SIZE_OF_SELECT = 0;

    public abstract Population execute(Population pop, int numChilds);

    /**
     * Execute the selection of the <size> individuals
     *
     * @param pop populations of parents
     * @return selected population
     */
    public Population execute(Population pop) {
        if (SIZE_OF_SELECT == 0) {
            return execute(pop, pop.getNumGenotypes());
        } else {
            return execute(pop, SIZE_OF_SELECT);
        }

    }

    @Override
    public void setParameters(String str) {
        if (str == null || str.trim().length() == 0) {
            return;
        }
        try {
            SIZE_OF_SELECT = Integer.parseInt(str);
        } catch (Exception e) {
            SIZE_OF_SELECT = 0;
        }
    }

    @Override
    public String getParameters() {
        return SIZE_OF_SELECT + "";
    }

    /**
     * @return the SIZE_OF_SELECT
     */
    public int getSize() {
        if (SIZE_OF_SELECT == 0 && solver != null) {
            return solver.getParents().getNumGenotypes();
        }
        return SIZE_OF_SELECT;
    }

    /**
     * @param SIZE_OF_SELECT the SIZE_OF_SELECT to set
     */
    public void setSize(int size) {
        this.SIZE_OF_SELECT = size;
    }

    @Override
    public String toString() {
        if (SIZE_OF_SELECT == 0) {
            return this.getClass().getSimpleName() + " <AUTO>";
        } else {
            return this.getClass().getSimpleName() + " <" + SIZE_OF_SELECT + ">";
        }
    }

    public String getInformation() {
        return getClass().getSimpleName() + " Size: " + SIZE_OF_SELECT;
    }

    @Override
    public Selection getClone() {
        Selection clone = (Selection) super.getClone();
        clone.SIZE_OF_SELECT = this.SIZE_OF_SELECT;
        return clone;
    }
}
