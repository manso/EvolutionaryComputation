package utils.Fractal;

//
//  Ackley1D.java
//  FractalFunctions
//
//  Created by Cara MacNish on 29/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Base function consisting of 1-D Ackley's Function, smoothed to zero at the edges.
 * @see UnitFunction1D
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class Ackley1D extends UnitFunction1D {
  
  public Ackley1D () {
    super();
  }
  
  public Ackley1D (double centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double point) {
    double value = 0;
    double distx = Math.abs(point - centre);
    double halfWidth = scale/2.0;
    if (distx < halfWidth) {
      double[] scaledPoint = {distx/halfWidth*32};
      double ack = (-0.348651221003070 + OriginalAckley.getValue(scaledPoint)/64)*scale;
      double atten = (new SmoothRectangle(centre, scale, 40)).getValue(point);
      value = (-atten)*ack;
    }
    return value; 
  }
  
}
