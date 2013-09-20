package utils.Fractal;
//
//  Step.java
//  FractalFunctions
//
//  Created by Cara MacNish on 21/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Base function consisting of a single step from zero to minus one (negative Heaviside function).
 * @see UnitFunction1D
 *
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class Step extends UnitFunction1D {
  
  public Step () {
    super();
  }
  
  public Step (double centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double point) {
    double halfWidth = scale/2.0;
    double distx = point - centre;
    double value = 0;
    if (distx < halfWidth && distx > 0) value = -halfWidth;
    return value;
  }
  
}
