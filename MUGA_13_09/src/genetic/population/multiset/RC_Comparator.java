package genetic.population.multiset;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import problem.Individual;

/**
 *
 * @author manso
 */
public class RC_Comparator extends IndividualComparator {

    @Override
    public int compare(Individual i1, Individual i2) {
        double [] v1 = i1.getValues();
        double [] v2 = i2.getValues();        
        for (int i = 0; i < v1.length; i++) {
           if(v1[i] > v2[i]) return 1;
           if(v1[i] < v2[i]) return -1;
        }
        return 0;

    }
}
