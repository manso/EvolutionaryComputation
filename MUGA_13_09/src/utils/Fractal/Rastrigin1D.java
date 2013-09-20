package utils.Fractal;
//
//  Rastrigin1D.java
//  FractalFunctions
//
//  Created by Cara MacNish on 24/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * A base function consisting of a 1-d slice of {@link OriginalRastrigin}, shifted and truncated
 * at (to a close approximation) the outer maxima, resulting in an (approximately) smooth fractal
 * function.
 * @see UnitFunction1D
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class Rastrigin1D extends UnitFunction1D {
    
    public Rastrigin1D () {
      super();
    }
    
    public Rastrigin1D (double centre, double scale) {
      super(centre, scale);
    }
    
    public double getValue (double point) {
      double value = 0;
      double distx = Math.abs(point - centre);
      double halfWidth = scale/2.0;
      if (distx < halfWidth) {
        double[] scaledPoint = {distx/halfWidth*4.522994688};
        value = (-3.940750995492086 + (OriginalRastrigin.getValue(scaledPoint)/10.24))*scale;
      }
      return value; 
    }
  
  public double twist (double x, double y) {
    double dx = 0;
    y = y%1;
    double ys = y*y;
    dx = 2*(32*ys*ys*ys - 96*ys*ys*y + 105*ys*ys - 50*ys*y + 9*ys);       // twisting sextic/
    return dx;
  }
  
}
