/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package operator;

import genetic.Solver.SimpleSolver;
import java.io.Serializable;
import java.util.Random;
import utils.DynamicLoad;

/**
 *
 * @author manso
 */
public abstract class GeneticOperator implements Serializable {

    protected static java.util.Random random = new Random();
    //solver where the operator do the operations
    public SimpleSolver solver = null;

    /**
     * Set the solver where the operator executes operations
     *
     * @param solver
     */
    public void setSolver(SimpleSolver s) {
        solver = s;
    }

    public void setRandomGenerator(Random r) {
        random = r;
    }

    /**
     * deep cloning
     *
     * @return Genetic operator
     */
    public GeneticOperator getClone() {
        GeneticOperator clone = (GeneticOperator) DynamicLoad.makeObject(this);
        clone.random = this.random;
        clone.solver = this.solver;
        return clone;
    }

    public void setParameters(String str) {
    }

    public String getParameters() {
        return "";
    }

    /**
     * Information about genetic operator
     *
     * @return
     */
    public String getInformation() {
        return toString();
    }
}
