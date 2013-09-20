package utils.Fractal;
//
//  Cylinder.java
//  RecursiveFunctions
//
//  Created by Cara MacNish on 15/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Cylindrical function (not for fractal generation).
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class Cylinder extends UnitFunction2D {
  
  public Cylinder () {
    super();
  }
  
  public Cylinder (double[] centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double[] point) {
    double radius = scale/2.0;
    double radiusSq = radius*radius;
    double distx = StrictMath.abs(point[0] - centre[0]);
    double disty = StrictMath.abs(point[1] - centre[1]);
    double distSq = distx*distx+disty*disty;
    double value = 0;
    if (distSq < radiusSq) value = -1;
    return value;
  }
  
}
