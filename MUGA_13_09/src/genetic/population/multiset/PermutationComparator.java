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
public class PermutationComparator extends IndividualComparator {

    @Override
    public int compare(Individual i1, Individual i2) {
       int  g1 =2,g2=2; // odd values
       boolean isEven = true;

        for (int i = 0; i < i1.getNumGenes(); i++) {
            //parity of last Gene
            if( g1%2 == 0) isEven = true;
            else isEven = false;
            //get Genes
            g1 = (int)i1.getGene(i).getValue();
            g2 = (int)i2.getGene(i).getValue();
            //if are equals continues to next gene
            if( g1== g2) continue;
            //pares
            if(isEven){
               if( g1 > g2) return -1;
               if( g1 < g2) return 1;
            }
            //impares
            else{
               if( g1 < g2) return -1;
               if( g1 > g2) return 1;
            }
        }
        return 0;

    }
}
