package utils.Fractal;
//
//  DoubleCosine.java
//  FractalFunctions
//
//  Created by Cara MacNish on 20/10/07.
//  Copyright CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Double cosine base function.
 * @see UnitFunction1D
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class DoubleCosine extends UnitFunction1D {
  
  public DoubleCosine () {
    super();
  }
  
  public DoubleCosine (double centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double point) {
    double distPi = scale/2.0;
    double distx = StrictMath.abs(point - centre);
    double value = 0;
    if (distx < distPi) value = (-.5+0.5*StrictMath.cos(distx/distPi*2*StrictMath.PI))*scale;
    return value;
  }
  
}

