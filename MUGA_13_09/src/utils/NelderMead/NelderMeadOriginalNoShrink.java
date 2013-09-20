package utils.NelderMead;
//http://www.scholarpedia.org/article/Nelder-Mead_algorithm
//http://geogebra.uni.lu/svn/branches/ggbLocusLine/geogebra/org/apache/commons/math/optimization/direct/NelderMead.java
//http://math.fullerton.edu/mathews/n2003/neldermead/NelderMeadMod/Links/NelderMeadMod_lnk_3.html    

import genetic.population.Population;

public class NelderMeadOriginalNoShrink  extends NelderMeadOriginal{

    @Override
    protected void Shrink(Population pop) {        
    }

}
