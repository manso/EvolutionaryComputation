package utils.Fractal;
//
//  Rastrigin.java
//  RecursiveFunctions
//
//  Created by Cara MacNish on 12/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Rastrigin function, as found at the 
 * <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>.
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class OriginalRastrigin {
  
  public static double getValue (double[] point) {
    int dimensions = point.length;
    double value = 0.0;
    for (int i=0; i<dimensions; i++) {
      value += (point[i]*point[i]) 
              -(10*StrictMath.cos(2*StrictMath.PI*point[i]))
              +10;
    }
    return value;
  }

}

