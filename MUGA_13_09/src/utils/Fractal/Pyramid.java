package utils.Fractal;
//
//  Pyramid.java
//  RecursiveFunctions
//
//  Created by Cara MacNish on 10/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Inverted Pyramid base function.
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class Pyramid extends UnitFunction2D {
  
  public Pyramid () {
    super();
  }
  
  public Pyramid (double[] centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double[] point) {
    double halfWidth = scale/2.0;
    double distx = Math.abs(point[0] - centre[0]);
    double disty = Math.abs(point[1] - centre[1]);
    double value = 0;
    double maxDist = Math.max(distx, disty);
    if (maxDist < halfWidth) value = -halfWidth + maxDist; 
    return value;
  }
  
}
