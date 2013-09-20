package utils.Fractal;
//
//  Circle.java
//  FractalFunctions
//
//  Created by Cara MacNish on 24/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Half circle base function.
 * @see UnitFunction1D
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class Circle extends UnitFunction1D {
  
  public Circle() {
    super();
  }
  
  public Circle (double centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double point) {
    double radius = scale/2.0;
    double radsq = radius*radius;
    double distx = Math.abs(point - centre);
    double distxSq = distx*distx;
    double value = 0;
    if (distxSq < radsq) value = -Math.sqrt(radsq-distxSq);
    return value;
  }
  
  

}
