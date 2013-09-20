package utils.Fractal;
//
//  Sphere.java
//  RecursiveFunctions
//
//  Created by Cara MacNish on 10/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * Spherical base function. This is the original function used in:
 *<p> 
 * MacNish, C., Towards Unbiased Benchmarking of Evolutionary and Hybrid Algorithms for Real-valued 
 * Optimisation</a>, <i><a href="http://www.tandf.co.uk/journals/titles/09540091.asp">Connection Science</a></i>,
 * Vol. 19, No. 4, December 2007.
 *<p> 
 * MacNish, C., <a href="seal06.pdf">Benchmarking Evolutionary and Hybrid
 * Algorithms using Randomized Self-Similar Landscapes</a>, <i>Proc. 6th
 * International Conference on Simulated Evolution and Learning
 * (SEAL'06)</i>, LNCS 4247, pp. 361-368, Springer, 2006.
 * (<a href="COSPrereviewLowRes.pdf">Pre-review draft.</a>)
 *<p>
 * and the CEC2006 <a href="http://ai.csse.uwa.edu.au/cgi-bin/WebObjects/huygensWS">Huygens Probe</a> 
 * competition.
 *<p>
 * Note that this is a true sphere, unlike the quadratic from De Jong's suite that is traditionally
 * mis-named "Sphere".
 *
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
 public class Sphere extends UnitFunction2D {
  
  public Sphere () {
    super();
  }

  public Sphere (double[] centre, double scale) {
    super(centre, scale);
  }
  
  public double getValue (double[] point) {
    double radius = scale/2.0;
    double radsq = radius*radius;
    double distx = Math.abs(point[0] - centre[0]);
    double disty = Math.abs(point[1] - centre[1]);
    double distsq = distx*distx + disty*disty;
    double value = 0;
    if (distsq < radsq) value = -Math.sqrt(radsq-distsq);
    return value;
  }
  
}
