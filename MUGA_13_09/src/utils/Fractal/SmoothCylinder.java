package utils.Fractal;
//
//  SmoothCylinder.java
//  RecursiveFunctions
//
//  Created by Cara MacNish on 12/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Function consisting of a cylinder with smoothed edges (not a base function for fractals). 
 * For composing with other base functions that are truncated from larger functions. 
 * This smooths them to zero by the edge of the unit square, thereby preserving their smooth
 * (continuously differentiable) nature.
 *
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class SmoothCylinder extends UnitFunction2D {

  private double dropPercent=10;
  
  public SmoothCylinder () {
    super();
  }
  
  /**
   * Extra constructor for manually setting the rate at which the sides drop off.
   * @param dropPercent the percentage of the radius over which the smoothed edges drop to zero
   * (default is 10)
   */
  public SmoothCylinder (double dropPercent) {
    super();
    this.dropPercent = dropPercent;
  }
  
  public SmoothCylinder (double[] centre, double scale) {
    super(centre, scale);
  }

  /**
   * Extra constructor for manually setting the rate at which the sides drop off.
   * @param dropPercent the percentage of the radius over which the smoothed edges drop to zero
   * (default is 10)
   */
  public SmoothCylinder (double[] centre, double scale, double dropPercent) {
    super(centre, scale);
    this.dropPercent = dropPercent;
  }
  
  public double getValue (double[] point) {
    double outerRadius = scale/2.0;
    double innerRadius = (100-dropPercent)/100*outerRadius;
    double distx = StrictMath.abs(point[0] - centre[0]);
    double disty = StrictMath.abs(point[1] - centre[1]);
    double dist = StrictMath.sqrt(distx*distx+disty*disty);
    double value;
    if (dist >= outerRadius) value = 0;
    else if (dist <= innerRadius) value = -1;
    else value = -(0.5 +0.5*StrictMath.cos((dist-innerRadius)/(outerRadius-innerRadius)*StrictMath.PI));
    return value;
  }
  
}
