package utils.Fractal;
//
//  QuickStep.java
//  FractalFunctions
//
//  Created by Cara MacNish on 31/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * A base fuction that steps down to -1 for one quarter of the unit width, then back up to zero.
 * @see UnitFunction1D
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class QuickStep extends UnitFunction1D {
  
  public QuickStep () {
    super();
  }
  
  public QuickStep (double centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double point) {
    if (point > centre && point-centre < scale/4.0) return -scale;
    else return 0;
  }

  public double twist (double x, double y) {
    double dx = 0;
    double mody = y%1;
    if (mody>0.5 && mody<=0.8) dx=-0.3;
    else if (mody<=0.5 && mody>0.3) dx=0.4;
    return dx;
  }
}
