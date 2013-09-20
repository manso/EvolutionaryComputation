package utils.Fractal;
//
//  TimingUtilities.java
//  FractalFunctions
//
//  Created by Cara MacNish on 31/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

import java.util.*;

/**
 * This class contains utilities to produce timing information for the functions on your machine.
 *
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class TimingUtilities {

  long start, stop;
  
  public void timeFractal1D (String unitFunctionName, int fractalDepth, int density, int index) throws Exception {
    UnitFunction1D unitFunction = (UnitFunction1D) Class.forName(unitFunctionName).newInstance();
    FractalFunction1D ff = new FractalFunction1D (unitFunction, fractalDepth, density, index);
    int intervals = 1000000;
    System.out.println("Timing "+unitFunctionName+"_F"+fractalDepth+"D"+density+"N"+index+": " +intervals+" evalutations.");
    double step = 1.0/intervals;
    start = System.currentTimeMillis();
    for (int x = 0; x < intervals; x++) {
      double pos = x*step;
      double depth = ff.evaluate(pos);
    }
    stop = System.currentTimeMillis();
    System.out.println("Time per evaluation: "+ 1.0*(stop-start)/intervals + " milliseconds.\n");
  }
  
  
  public void timeFractal2D (String unitFunctionName, int fractalDepth, int density, int index) throws Exception {
    UnitFunction2D unitFunction = (UnitFunction2D) Class.forName(unitFunctionName).newInstance();
    FractalFunction2D ff = new FractalFunction2D (unitFunction, fractalDepth, density, index);
    int intervals = 100;
    int totalEvals = intervals*intervals;
    System.out.println("Timing "+unitFunctionName+"_F"+fractalDepth+"D"+density+"N"+index+": " +totalEvals+" evalutations.");
    double step = 1.0/intervals;
    start = System.currentTimeMillis();
    for (int y = 0; y < intervals; y++) {
      for (int x = 0; x < intervals; x++) {
        double[] pos = {x*step, y*step};
        double depth = ff.evaluate(pos);
      }
    }
    stop = System.currentTimeMillis();
    System.out.println("Time per evaluation: "+ 1.0*(stop-start)/totalEvals + " milliseconds.\n");
  }
  

  public void timeFastFractal (String unitFunctionName, int fractalDepth, int density, long index, int dimensions) throws Exception {
    FastFractal ff = new FastFractal (unitFunctionName, fractalDepth, density, index, dimensions);
    int intervals = 1000;
    double step = 1.0/intervals;
    double[][] points = new double[intervals][dimensions];
    for (int i=0; i<intervals; i++) {
      Arrays.fill(points[i], i*step);   // setup points across the diagonal
    }
    System.out.println("Timing "+unitFunctionName+"_F"+fractalDepth+"D"+density+"N"+index+": " + dimensions + " dimensions, " + intervals +" evalutations.");
    start = System.currentTimeMillis();
    for (int x = 0; x < intervals; x++) {
      double depth = ff.evaluate(points[x]);
    }
    stop = System.currentTimeMillis();
    System.out.println("Time per evaluation: "+ 1.0*(stop-start)/intervals + " milliseconds.\n");
  }
  
}
