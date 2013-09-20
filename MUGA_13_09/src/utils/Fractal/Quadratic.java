package utils.Fractal;
//
//  Quadratic.java
//  FractalFunctions
//
//  Created by Cara MacNish on 24/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Quadratic base function. 1-Dimensional version of a bowl function, or what is traditionally 
 * mis-named after De Jong's suite as "Sphere". 
 * For a real 1-d sphere see {@link Circle}.
 * @see UnitFunction1D
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
 public class Quadratic extends UnitFunction1D {
  
  public Quadratic () {
    super();
  }
  
  public Quadratic (double centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double point) {
    double depth = 0;
    double radius = scale/2.0;
    double distx = Math.abs(point - centre);
    if (distx < radius) {
      double ratio = distx/radius;
      depth = ((ratio*ratio) -1)*scale;
    }
    return depth;
  }

}
