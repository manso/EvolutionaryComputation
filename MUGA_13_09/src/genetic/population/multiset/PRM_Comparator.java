package genetic.population.multiset;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import problem.Individual;
import problem.PRM_Individual;

/**
 *
 * @author manso
 */
public class PRM_Comparator extends IndividualComparator {

    @Override
    public int compare(Individual i1, Individual i2) {
        int [] v1 = ((PRM_Individual)i1).getGeneValues();
        int [] v2 = ((PRM_Individual)i2).getGeneValues();
        for (int i = 0; i < v1.length; i++) {
           if(v1[i] > v2[i]) return 1;
           if(v1[i] < v2[i]) return -1;
        }
        return 0;

    }
}
