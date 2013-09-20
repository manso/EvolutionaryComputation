package utils.Fractal;
//
//  FractalFunction2D.java
//  FractalFunctions
//
//  Created by Cara MacNish on 15/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.


/** 
 * This is the top-level class for generating Fractal Functions on 2 parameters.
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class FractalFunction2D {
  
  final static long MASK = 0xffffffffl;    // lower order 32 bits of long
  final static long SCATTER = 16807;       // Park & Miller's multiplicand for Ran0 (7^5)
                                           // Numerical Recipes. Note < 2^15
  private int fractalDepth = 48;           // maximum recommended for IEEE 64-bit
  private int density = 4;
  private long index = 1;
  private UnitFunction2D unitFunction;
  private RanQD1 ran;
   
  double[] pos1 = new double[2];           // really local variables but a little faster to   
  long[] square1 = new long[2];            // declare outside loop
  int[] offset = new int[2];
  
  // Constructors
    
 /**
   * Create a new 2D fractal function generator.
   * @param unitFunction the base function for this generator
   * @param fractalDepth recursive depth of fractal - each increment adds detail at half the scale
   * (double the resolution).
   * Must be between 1 and 64, although in practice the maximum supported by IEEE 64-bit 
   * floating point is less. A maximum of 48 is recommended.
   * @param density average number of base functions per unit area at each resolution
   * @param index the sequence number of this surface (for the given fractal depth and density)
   */
  public FractalFunction2D (UnitFunction2D unitFunction, int fractalDepth, int density, long index) {
    this.unitFunction = unitFunction;
    this.fractalDepth = fractalDepth;
    this.density = density;
    this.index = (index * SCATTER) & MASK;
    ran = new RanQD1(index);
  }

 /**
   * Create a new 2D fractal function generator.
   * @param unitFunctionName the name of the base function for this generator 
   * (must match the class name of a subclass of {@link UnitFunction2D})
   * @param fractalDepth recursive depth of fractal - each increment adds detail at half the scale
   * (double the resolution).
   * Must be between 1 and 64, although in practice the maximum supported by IEEE 64-bit 
   * floating point is less. A maximum of 48 is recommended.
   * @param density average number of base functions per unit area at each resolution
   * @param index the sequence number of this surface (for the given fractal depth and density)
   */
  public FractalFunction2D (String unitFunctionName, int fractalDepth, int density, long index) throws Exception {
    this.unitFunction = (UnitFunction2D) Class.forName(unitFunctionName).newInstance();
    this.fractalDepth = fractalDepth;
    this.density = density;
    this.index = (index * SCATTER) & MASK;
    ran = new RanQD1(index);
  }

 /**
   * Create a new 2D fractal function generator using the default (maximum recommended) fractal
   * depth of 48.
   * @param unitFunction the base function for this generator
   * @param density average number of base functions per unit area at each resolution
   * @param index the sequence number of this surface (for the given fractal depth and density)
   */
  public FractalFunction2D (UnitFunction2D unitFunction, int density, long index) {
    this.unitFunction = unitFunction;
    this.density = density;
    this.index = (index * SCATTER) & MASK;
    ran = new RanQD1(index);
  }

 /**
   * Create a new 2D fractal function generator using the default (maximum recommended) fractal
   * depth of 48.
   * @param unitFunctionName the name of the base function for this generator 
   * (must match the class name of a subclass of {@link UnitFunction2D})
   * @param density average number of base functions per unit area at each resolution
   * @param index the sequence number of this surface (for the given fractal depth and density)
   */
  public FractalFunction2D (String unitFunctionName, int density, long index) throws Exception {
    this.unitFunction = (UnitFunction2D) Class.forName(unitFunctionName).newInstance();
    this.density = density;
    this.index = (index * SCATTER) & MASK;
    ran = new RanQD1(index);
  }

  
  /**
   * Evaluate the function at the given point.
   * @param point the two dimensional (x,y) co-ordinates of the point to evaluate
   * @return the value (or fitness) at those co-ordinates
   */
  public double evaluate (double[] point) {
    point[0] = point[0] % 1;                 // first map pos into (0,1], (0,1]
    point[1] = point[1] % 1;                 // note in Java -4.3%1 is -0.3 not 0.7 ie Matlab 'rem' function not 'mod'
    if (point[0] <= 0) point[0] = point[0]+1;  //0 must move to 1, or will be in wrong square
    if (point[1] <= 0) point[1] = point[1]+1;
    return getDepthLocal (point, 1, index, 1);
  }
  
  /**
   * Evaluate the function at the given point (convenience method).
   * @param x the x-value of the point to evaluate
   * @param y the y-value of the point to evaluate
   * @return the value (or fitness) at those co-ordinates
   */
  public double evaluate (double x, double y) {
    double[] point = {x, y};
    return evaluate(point);
  }
  
  
  private double getDepthLocal (double[] pos, int recDepth, long seed, long span) {
    double depth = 0;
    double scale = 1.0/span;
    long[] square = {(long) Math.ceil(pos[0]*span), (long) Math.ceil(pos[1]*span)};
    long newSeed;
    // get contribution from each of the 9 relevant squares...
    for (int i = -1; i<2; i++) {
      for (int j = -1; j<2; j++) {
        for (int k = 0; k<2; k++) {        // faster than arraycopy
          pos1[k] = pos[k];
          square1[k] = square[k];
        }
        offset[0] = i; offset[1] = j; 
        translate(pos1, square1, offset, span);
        depth = depth + getDepthWRTSquare(pos1, square1, recDepth, seed, span, scale);
      }
    }      
    // now fire recursion to next level down...
    if (recDepth < fractalDepth) {
      // Seeds up to depth 16 can be gererated uniquely for each square at each depth. 
      // After that, the number of squares becomes too great.
      // Total number of squares < 2^(2*recDepth)-1
      if (recDepth < 16) 
        // Generate unique seeds up to and including depth 16 (remember this is generating seed for next depth).
        // MASK is needed since user may use a high index. Quicker than mod.
        newSeed = (seed + span*span) & MASK;
      else   
        // We're generating the seed for depth 17 or greater. Cannot ensure unique, so no need use power.
        newSeed = (seed * SCATTER) & MASK;
      long newSpan = span << 1;                                      // newSpan = 2^(recDepth-1), bit shift faster
      depth = depth + getDepthLocal(pos,recDepth+1,newSeed,newSpan); // recur to next level
    }
    return depth;
  }
  
  
  private void translate (double[] pos, long[] square, int[] offset, long span) {
    for (int i=0; i<=1; i++) {
      square[i] = square[i] + offset[i];
      if (square[i] == 0) {
        square[i] = span;
        pos[i] = pos[i]+1;
      }
      else if (square[i] > span) {
        square[i] = 1;
        pos[i] = pos[i]-1;
      }
    }
  }
  
  
  private double getDepthWRTSquare (double[] pos, long[] square, int recDepth, long seed, long span, double scale) {
    double depth = 0;
    long squareSeed;
    if (recDepth <= 16)                // squares have unique seeds, see above
      squareSeed = (square[0]-1)*span + (square[1]-1);
    else 
      // Not unique, just scatter. Ensure product doesn't overflow long. Max fractal depth is 
      // approx 48, so square <= 2^47, and SCATTER < 2^15, so OK.
      squareSeed = ((square[0]-1)*SCATTER + (square[1]-1)) & MASK; 
    long localSeed = seed + squareSeed;
    ran.setSeed(localSeed);
    int numUnits = ran.nextInt(0, 2*density);
    for (int i=1; i<=numUnits; i++) {
      double diameter = 1/(2 - ran.nextDouble()) * scale;    // get diameter from skewed distribution
      double[] centre = {(square[0]-ran.nextDouble())*scale, (square[1]-ran.nextDouble())*scale};
      if ( Math.abs(pos[0]-centre[0]) < diameter/2 && Math.abs(pos[1]-centre[1]) < diameter/2) {
        unitFunction.setScale(diameter);
        unitFunction.setCentre(centre);
        depth = depth+unitFunction.getValue(pos);
      }
    }
    return depth;
  }
  
}

