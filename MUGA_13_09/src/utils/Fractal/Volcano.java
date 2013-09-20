package utils.Fractal;
//
//  Volcano.java
//  FractalFunctions
//
//  Created by Cara MacNish on 6/11/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Base function that resembles a little (but not much!) a large volcanic crater with a small, hollow
 * volcanic mound in the centre.
 *
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class Volcano extends UnitFunction2D {
  
  public Volcano () {
    super();
  }
  
  public Volcano (double[] centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double[] point) {
    double depth=0;
    double halfWidth = scale/2.0;
    double rs = 3*4*((point[0]-centre[0])*(point[0]-centre[0]) + (point[1]-centre[1])*(point[1]-centre[1]))/(scale*scale);
    if (rs > -1 && rs < 1)  depth = (-96*rs*rs*rs + 193*rs*rs - 98*rs + 1) * scale /10; 
    double atten = (new SmoothCylinder(centre, scale, 20)).getValue(point);
    return 0.5*(atten)*scale-0.3*depth;
  }
  
}

