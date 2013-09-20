package genetic.population.multiset;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Comparator;
import problem.Individual;

/**
 *
 * @author manso
 */
public class IndividualComparator implements Comparator<Individual> {

//    static  BitsComparator bits = new BitsComparator();
//    static RealComparator real = new RealComparator();
//    static PRM_Comparator perm = new PRM_Comparator();
//    static RC_Comparator rc = new RC_Comparator();

    @Override
    public int compare(Individual i1, Individual i2) {
        return i1.compareGenotype(i2);
//        if( i1 instanceof RC_Individual )
//            return rc.compare(i1, i2);
//        if( i1 instanceof PRM_Individual)
//            return perm.compare(i1, i2);
//        if( i1.getGene(0) instanceof GeneNumber)
//          return real.compare(i1, i2);
//        return bits.compare(i1, i2);
    }
}
