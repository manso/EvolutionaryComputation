package utils.Fractal;
//
//  PlotUtilities.java
//  RecursiveFunctions
//
//  Created by Cara MacNish on 12/10/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

import java.io.*;

/**
 * This class contains a number of utilities to help plot, and hence visualise, the functions.
 * The methods generate data files which can be read in as matrices and plotted by a program 
 * such as Matlab.
 *<p>
 * For many functions corresponding Matlab programs are available for producing the plots.
 *<p>
 * By default, these methods and the Matlab functions assume that in the same directory
 * that houses FractalFunctions there are two additional directories called 
 * FractalData and FractalImages. The Java methods write to FractalData, and the
 * Matlab functions read from FractalData and write to FractalImages.
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class PlotUtilities {
  
  File dir;
  PrintWriter out;

  /**
   * Create a plot generating object on a given data directory.
   * @param dataDir the directory to save the data for plots
   */
  public PlotUtilities (String dataDir) {
    dir = new File(dataDir);
    System.out.println("Plot utility created for directory: "+dir.getPath());
  }
  
  /**
   * Create a plot generating object on the default directory (where you are running Java from).
   */
  public PlotUtilities () {
    dir = new File(".");
    System.out.println("Writing to "+dir.getPath());
  }
  
  private void setPrintWriter (String filename) throws IOException {
    File target = new File(dir,filename);
    target.createNewFile();
    out = new PrintWriter(new BufferedWriter(new FileWriter(target)));
  }
 
  /**
   * Produce a file (matrix) of values for plotting a 1D unit function.
   * @param unitFunctionName the name of the unit function
   * @param intervals the number of intervals to sample (1000 works well for Matlab)
   */
  public void plotUnitFunction1D (String unitFunctionName, int intervals) throws Exception {
    plotUnitFunction1D (unitFunctionName, 1, intervals);
  }
  
  /**
   * This method is only used directly for double checking that a 1D unit function is indeed scaling correctly.
   * @param unitFunctionName the name of the unit function
   * @param magnification the magnification, usually 10
   * @param intervals the number of intervals to sample (1000 works well for Matlab)
   */
  public void plotUnitFunction1D (String unitFunctionName, int magnification, int intervals) throws Exception {
    UnitFunction1D unitFunction = (UnitFunction1D) Class.forName(unitFunctionName).newInstance();
    double scale = 1.0/magnification;
    double centre = 0.5*scale;
    unitFunction.setCentre(centre);
    unitFunction.setScale(scale);
    int totalProbes=intervals+1;            // function wraps so first and last point are the same
    String filename = unitFunction.getName()+"-" + totalProbes + ".txt";
    setPrintWriter(filename);
    System.out.println("Writing "+filename);
    double step = 1.0*scale/intervals;
    for (int x = 0; x <= intervals; x++) {
      double pos = x*step;
      double depth = unitFunction.getValue(pos);
      out.print(depth+" ");
    }
    out.println();
    out.close();
  }

  
  /**
   * Produce a file (matrix) of values for plotting a 2D unit function.
   * @param unitFunctionName the name of the unit function
   * @param intervals the number of intervals to sample in each co-ordinate direction 
   * (200 works well for Matlab)
   */
  public void plotUnitFunction2D (String unitFunctionName, int intervals) throws Exception {
    plotUnitFunction2D (unitFunctionName, 1, intervals);
  }
  
  /**
   * This method is only used directly for double checking that a unit function is indeed scaling correctly.
   * @param unitFunctionName the name of the unit function
   * @param magnification the magnification, usually set to 10
   * @param intervals the number of intervals to sample in each co-ordinate direction 
   * (200 works well for Matlab)
   */
  public void plotUnitFunction2D (String unitFunctionName, int magnification, int intervals) throws Exception {
    UnitFunction2D unitFunction = (UnitFunction2D) Class.forName(unitFunctionName).newInstance();
    double scale = 1.0/magnification;
    double[] centre = {0.5*scale, 0.5*scale};
    unitFunction.setCentre(centre);
    unitFunction.setScale(scale);
    int totalProbes=intervals+1;            // function wraps so first and last point are the same
    String filename = unitFunction.getName()+"-" + totalProbes +"x"+ totalProbes + ".txt";
    setPrintWriter(filename);
    System.out.println("Writing "+filename);
    double step = 1.0*scale/intervals;
    for (int y = 0; y <= intervals; y++) {
      for (int x = 0; x <= intervals; x++) {
        double[] pos = {x*step, y*step};
        double depth = unitFunction.getValue(pos);
        out.print(depth+" ");
      }
      out.println();
    }
    out.close();
  }
  
  
  
  /**
   * Generate plot data for a sequence of 1-dimensional fractal functions
   * at standard magnification and origin.
   * @param unitFunctionName the name of the base function, must match the
   * class name of a subclass of {@link UnitFunction1D}.
   * @param fractalDepth the fractal depth, see {@link FastFractal}
   * @param density the density, see {@link FastFractal}
   * @param first index the first in the sequence
   * @param last index the last in the sequence (same as first for a single plot)
   * @param intervals the number of intervals to divide the surface in each co-ordinate direction (1000 works well with Matlab)
   */
  public void plotFractal1D (String unitFunctionName, int fractalDepth, int density, long first, long last, int intervals) 
    throws Exception {
      for (long index = first; index <= last; index++) 
        plotFractal1D(unitFunctionName, fractalDepth, density, index, 1, 0.0, intervals);
    }

  /**
   * Generate plot data for a 1-dimensional fractal function
   * @param unitFunctionName the name of the base function, must match the
   * class name of a subclass of {@link UnitFunction1D}.
   * @param fractalDepth the fractal depth, see {@link FastFractal}
   * @param density the density, see {@link FastFractal}
   * @param index the sequence number
   * @param magnification the magnification
   * @param xOffset offset from the origin
   * @param intervals the number of intervals to divide the surface in each co-ordinate direction (1000 works well with Matlab)
   */
  public void plotFractal1D (String unitFunctionName, int fractalDepth, int density, long index, int magnification, double xOffset, int intervals) 
    throws Exception {
      UnitFunction1D unitFunction = (UnitFunction1D) Class.forName(unitFunctionName).newInstance();
      int totalProbes=intervals+1;            // function wraps so first and last point are the same
      FractalFunction1D ff = new FractalFunction1D (unitFunction, fractalDepth, density, index);
      String name = "Fractal_"+unitFunction.getName()+"_F"+fractalDepth+"D"+density+"N"+index;
      String filename = name+"x"+magnification+"-"+totalProbes + ".txt";
      setPrintWriter(filename);
      System.out.print("Writing "+filename+" ");
      double step = 1.0/magnification/intervals;
      for (int x = 0; x <= intervals; x++) {
        double pos = xOffset+x*step;
        double depth = ff.evaluate(pos);
        out.print(depth+" ");
      }
      out.println();
      out.close();
      System.out.println("done.");
    }
  
  /**
   * Generate plot data for a sequence of 2-dimensional fractal functions
   * at standard magnification and origin.
   * @param unitFunctionName the name of the base function, must match the
   * class name of a subclass of {@link UnitFunction1D}.
   * @param fractalDepth the fractal depth, see {@link FastFractal}
   * @param density the density, see {@link FastFractal}
   * @param first index the first in the sequence
   * @param last index the last in the sequence (same as first for a single plot)
   * @param intervals the number of intervals to divide the surface in each co-ordinate direction (200 works well with Matlab)
   */
  public void plotFractal2D (String unitFunctionName, int fractalDepth, int density, long first, long last, int intervals) 
    throws Exception {
      for (long index = first; index <= last; index++) 
        plotFractal2D(unitFunctionName, fractalDepth, density, index, 1, 0.0, 0.0, intervals);
    }

  /**
   * Generate plot data for a 2-dimensional fractal function
   * @param unitFunctionName the name of the base function, must match the
   * class name of a subclass of {@link UnitFunction1D}.
   * @param fractalDepth the fractal depth, see {@link FastFractal}
   * @param density the density, see {@link FastFractal}
   * @param index the sequence number
   * @param magnification the magnification
   * @param xOffset offset from the origin
   * @param yOffset offset from the origin
   * @param intervals the number of intervals to divide the surface in each co-ordinate direction (200 works well with Matlab)
   */
  public void plotFractal2D (String unitFunctionName, int fractalDepth, int density, long index, int magnification, double xOffset, double yOffset, int intervals)
    throws Exception {
      UnitFunction2D unitFunction = (UnitFunction2D) Class.forName(unitFunctionName).newInstance();
      int totalProbes=intervals+1;            // function wraps so first and last point are the same
      int bips = Math.round(intervals/10);
      FractalFunction2D ff = new FractalFunction2D (unitFunction, fractalDepth, density, index);
      String name = "Fractal_"+unitFunction.getName()+"_F"+fractalDepth+"D"+density+"N"+index;
      String filename = name+"x"+magnification+"-"+totalProbes +"x"+ totalProbes + ".txt";
      setPrintWriter(filename);
      System.out.print("Writing "+filename+" ");
      double step = 1.0/magnification/intervals;
      for (int y = 0; y <= intervals; y++) {
        for (int x = 0; x <= intervals; x++) {
          double[] pos = {xOffset+x*step, yOffset+y*step};
          double depth = ff.evaluate(pos);
          out.print(depth+" ");
        }
        out.println();
        if (bips>0 && y%bips==0) System.out.print(".");
      }
      out.close();
      System.out.println("done.");
    }
    
  
  /**
   * Generate plot data (for the first two dimensions) of a sequence of multi-dimensional FastFractals
   * at standard magnification and origin.
   * @param unitFunctionName the name of the base function, must match the
   * class name of a subclass of {@link UnitFunction1D}.
   * @param fractalDepth the fractal depth, see {@link FastFractal}
   * @param density the density, see {@link FastFractal}
   * @param first index the first in the sequence
   * @param last index the last in the sequence (same as first for a single plot)
   * @param intervals the number of intervals to divide the surface in each co-ordinate direction (200 works well with Matlab)
   */
   public void plotFastFractal (String unitFunctionName, int fractalDepth, int density, long first, long last, int intervals) 
   throws Exception {
     for (long index = first; index <= last; index++) 
       plotFastFractal(unitFunctionName, fractalDepth, density, index, 1, 0.0, 0.0, intervals);
   }
  
  /**
   * Generate plot data for the first two dimensions of a multi-dimensional FastFractal
   * @param unitFunctionName the name of the base function, must match the
   * class name of a subclass of {@link UnitFunction1D}.
   * @param fractalDepth the fractal depth, see {@link FastFractal}
   * @param density the density, see {@link FastFractal}
   * @param index the sequence number
   * @param magnification the magnification
   * @param xOffset offset from the origin
   * @param yOffset offset from the origin
   * @param intervals the number of intervals to divide the surface in each co-ordinate direction (200 works well with Matlab)
   */
   public void plotFastFractal (String unitFunctionName, int fractalDepth, int density, long index, int magnification, double xOffset, double yOffset, int intervals) 
   throws Exception {
     int dimension = 2;
     int totalProbes=intervals+1;            // function wraps so first and last point are the same
     FastFractal ff = new FastFractal(unitFunctionName, fractalDepth, density, index, dimension);
     String name = "FastFractal_"+unitFunctionName+"_F"+fractalDepth+"D"+density+"N"+index;
     String filename = name+"x"+magnification+"-"+totalProbes +"x"+ totalProbes + ".txt";
     setPrintWriter(filename);
     System.out.print("Writing "+filename+" ");
     double step = 1.0/magnification/intervals;
     for (int y = 0; y <= intervals; y++) {
       for (int x = 0; x <= intervals; x++) {
         double[] pos = {xOffset+x*step, yOffset+y*step};
         double depth = ff.evaluate(pos);
         out.print(depth+" ");
       }
       out.println();
     }
     out.close();
     System.out.println("done.");
   }
  

  /**
   * Produce a file (vector) of values for generating plot of a 1D slide through a fractal function.
   * @param unitFunctionName the name of the unit function that underlies the fractal function
   * @param density the density of the fractal function
   * @param index the index of the function
   * @param intervals the number of intervals at which to sample function in each co-ordinate direction (200 works well with Matlab)
   * @param xStart the x co-ordinate of the lower end of the line in parameter space
   * @param yStart the y co-ordinate of the lower end of the line in parameter space
   * @param xStop the x co-ordinate of the upper end of the line in parameter space
   * @param yStop the y co-ordinate of the upper end of the line in parameter space
   */
/*
  public void slicePlot (String unitFunctionName, int density, int index, int intervals, 
                         double xStart, double yStart, double xStop, double yStop) throws Exception {
    UnitFunction2D unitFunction = (UnitFunction2D) Class.forName(unitFunctionName).newInstance();
    FractalFunction2D rf = new FractalFunction2D (unitFunction, density, index);
    String filename = unitFunction.getName()+"_"+density+"_"+index+"-("+xStart+","+yStart+")-("+xStop+","+yStop+").txt";
    setPrintWriter(filename);
    System.out.println("Writing "+filename);
    double xstep = (xStop - xStart)/intervals;
    double ystep = (yStop - yStart)/intervals;
    for (int count = 0; count <= intervals; count++) {
      double[] pos = {xStart+count*xstep, yStart+count*ystep};
      double depth = rf.evaluate(pos);
      out.print(depth+" ");
    }
    out.println();
    out.close();
    System.out.println(filename+" done.");
  }
*/    
  
}
