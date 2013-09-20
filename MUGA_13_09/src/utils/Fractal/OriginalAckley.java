package utils.Fractal;
//
//  OriginalAckley.java
//  FractalFunctions
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Ackley's function, as found at the 
 * <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>.
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class OriginalAckley {
  
  public static double getValue (double[] point) {
    int dim = point.length;
    double firstSum = 0.0;
    for (int i=0; i< dim; i++) firstSum = firstSum + point[i]*point[i];
    double secondSum = 0.0;
    for (int i=0; i< dim; i++) secondSum = secondSum + Math.cos(2*Math.PI*point[i]);
    return -20*Math.exp(-0.2*Math.sqrt(1.0/dim*firstSum)) - Math.exp(1.0/dim*secondSum) + 20 + Math.E;
  }
  
}

