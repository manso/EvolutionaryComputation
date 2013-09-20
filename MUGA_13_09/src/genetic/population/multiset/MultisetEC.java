/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genetic.population.multiset;

import problem.Individual;

/**
 * Multiset Evolutionary Computation
 * @author manso
 */
public class MultisetEC extends MiTree<Individual>{
    IndividualComparator comparator;;
    public MultisetEC(IndividualComparator comp) {
        super(comp);
        comparator = comp;
    }

//    public Individual getSimilar( Individual i){
//        double dist = Double.MAX_VALUE;
//        MNode best=null;
//        MNode tmp = root;
//        while( tmp!=NIL){
//            //calculate comparate
//             double comp = i.distanceTo((Individual)tmp.data);
//            if (comp == 0) {
//                best = tmp;
//                break;
//            }
//             //calculate similarity
//             double d = comparator.compare(i, (Individual) tmp.data);
//            if( best ==null || d < dist){
//                dist = d;
//                best = tmp;
//            }
//             //navigate in the tree
//            if (comp < 0) {
//                tmp = tmp.left;
//            } else {
//                tmp = tmp.right;
//            }
//        }
//        Individual bestInd = (Individual)best.data;
//        bestInd.setNumCopys( best.copies);
//        return bestInd;
//    }

//    public Individual getMostSimilar( Individual i){
//        double dist = Double.MAX_VALUE;
//        MNode best=null;
//        NodeIterator iterator = new NodeIterator();
//        while( iterator.hasNext()){
//          MNode tmp = iterator.next();
//          double d = comparator.compare(i, (Individual) tmp.data);
//            if( best == null || d < dist){
//                dist = d;
//                best = tmp;
//            }
//        }
//        Individual bestInd = (Individual)best.data;
//        bestInd.setNumCopys( best.copies);
//        return bestInd;
//    }




}
