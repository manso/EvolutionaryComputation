package genetic.population.multiset;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import problem.Individual;
import utils.BitField;

/**
 *
 * @author manso
 */
public class RealComparator extends IndividualComparator {

    @Override
    public int compare(Individual i1, Individual i2) {

//        BitField b1 = i1.getBits();
//        BitField b2 = i2.getBits();
//        boolean l1 = b1.getBit(0);
//        boolean l2 = b2.getBit(0);
//        for (int i = 1; i < b1.getNumberOfBits(); i++) {
//            if (l1 != l2) {
//                if (l1) {
//                    return -1;
//                } else {
//                    return 1;
//                }
//            }
//            l1 ^= b1.getBit(i);
//            l2 ^= b2.getBit(i);
//        }
//
//        if (l1 != l2) {
//            if (l1) {
//                return -1;
//            } else {
//                return 1;
//            }
//        }
//        return 0;

//        double distance1 = 0;
//        double distance2 = 0;
//        double v1,v2;
//        int equal = 0;
//        //compute distances
//        for (int i = 0; i < i1.getNumGenes(); i++) {
//            v1 = i1.getGene(i).getValue();
//            v2 = i2.getGene(i).getValue();
//            distance1 += Math.pow(v1, 2);
//            distance2 += Math.pow(v2, 2);
//            //if have a diferent gene
//            if( equal ==0 && v1 != v2){
//                if( v1 > v2 ) equal = 1;
//                else
//                    equal =-1;
//            }
//        }
//               //verify if is diferents
//        if( distance1 > distance2 ) return 1;
//        if( distance2 < distance1) return -1;
//        // 0 if no different genes ou a value
//        return equal;



//        //compute distances
        for (int i = 0; i < i1.getNumGenes(); i++) {
            double v1 = i1.getGene(i).getValue();
            double v2 = i2.getGene(i).getValue();
            //if have a diferent gene
            if(  v1 != v2){
                if( v1 > v2 ) return 1;
                return -1;
            }
        }
        return 0;

    }
}
