package utils.Fractal;
//
//  UnitRastrigin2D.java
//  RecursiveFunctions
//
//  Created by Cara MacNish on 12/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * 2-dimensional Rastrigin base function.
 *<p>
 * Since Rastrigin is proposed as a smooth (continuously differentiable) function we preserve this. We
 * therefore require it fades smoothly to zero by the edges of its unit square.
 * (Simply truncating it would introduce "cliffs" to the fractal function.)
 *<p>
 * Rastrigin is normally defined from -5.12 to 5.12, with a minimum of 0 and a maximum of approx 80.7029.
 * We firstly scale the whole function to within a unit square, that is from -0.5 to 0.5.
 *<p>
 * The low frequency behaviour is a bowl (quadratic), suggesting it is better to use a circular
 * rather than square cut-off to avoid unneccessarily high "walls" at the sides. 
 * Using circular bounds ("cutting with a cylinder") gives a maximum of approx 66.0377 or a scaled maximum
 * of approx 6.4239. We therefore subtract this first to give a function that drops down from the surface. 
 * <p>
 * To preserve the smooth nature of the function, we wish to smooth the edges. 
 * We therefore compose with a smoothed cylinder ({@link SmoothCylinder}). 
 * The higher frequence behaviour has a period of 1, which scaled to a unit width function is 1/(10.14)=0.0977
 * or approx 0.1. Our smoothing function smooths over a half period (of cosine), thus to maintain the high
 * frequency nature it should smooth over approx 0.05, or 10% of the radius.
 *
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class Rastrigin2D extends UnitFunction2D {
    
    public Rastrigin2D () {
      super();
    }
    
    public Rastrigin2D (double[] centre, double scale) {
      super(centre, scale);
    }
    
    public double getValue (double[] point) {
      double halfWidth = scale/2.0;
      double distx = Math.abs(point[0] - centre[0]);
      double disty = Math.abs(point[1] - centre[1]);
      
      double[] scaledPoint = {distx/halfWidth*5.12, disty/halfWidth*5.12};

      double rast = (-6.4239 + (OriginalRastrigin.getValue(scaledPoint)/10.24))*scale;
      double atten = (new SmoothCylinder(centre, scale, 10)).getValue(point);
      return (-atten)*rast;
    }
    
}

