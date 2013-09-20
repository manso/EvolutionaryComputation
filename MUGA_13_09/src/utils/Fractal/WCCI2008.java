package utils.Fractal;
//
//  WCCI2008.java
//  FractalFunctions
//
//  Created by Cara MacNish on 2/11/07.
//  Copyright 2007 CSSE, UWA. All rights reserved.
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

import java.io.*;
import java.util.*;

/**
 * Front-end class for entering the WCCI2008 Moutains or Molehills Competition.
 * Algorithms submitted for the competition are required to interface only with this class.
 *<p>
 *Your entry must simply do the following:<br>
 *&nbsp; 1. Create a WCCI2008 object, with some text information to identify your entry.<br>
 *&nbsp; 2. Use a loop to call nextProblem NUM_PROBLEMS times.<br>
 *&nbsp; 3. For each problem, use your alloted NUM_EVALUATIONS evalutations as you wish by calling the evaluate methods.
 *
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class WCCI2008 {

  /**
   * The number of benchmark problems algorithms must solve (currently set to 30).
   */
  public final static int NUM_PROBLEMS = 30;

  /**
   * The number of evaluations permitted for each problem (currently set to 1000).
   */
  public final static int NUM_EVALUATIONS = 1000;

  // the only thing that will change in the comp is this number
  private final static int START_NUMBER = 1;         
  
  private boolean outputOn = false;
  private boolean outputToFile = false;
  private final static String dir = ".";
  private final static String file = "results.txt";
  private PrintStream out;
  
  private final static int FRACTAL_DEPTH=48;
  private final static Object[] PROBLEM1 = {"Sphere", new Integer(3)};
  private final static Object[] PROBLEM2 = {"Volcano", new Integer(3)};
  private final static Object[] PROBLEM3 = {"Cube", new Integer(16)};
  private final static Object[][] PROBLEMS = {PROBLEM1, PROBLEM2, PROBLEM3};
  
  private ArrayList problemSet = new ArrayList(NUM_PROBLEMS);
  private ListIterator problemIterator;
  private FractalFunction2D currentProblem;
  private int problemNumber;
  private Random ran = new Random(START_NUMBER);
  private double[] origin = new double[2];
  private double zero;
  private double scaleFactor;
  
  private double result, bestResult;
  private int count;

  /**
   * Create an entry for a new competitor.
   * @param name the name of the competitor
   * @param affiliation the affiliation of the competitor
   * @param email the contact email address of the competitor
   * @param algorithmName the name of your algorithm
   */
  public WCCI2008 (String name, String affiliation, String email, String algorithmName) throws Exception {
    File target = new File(dir,file);
    target.createNewFile();
    if (outputToFile) out = new PrintStream(new FileOutputStream(target,true), true);
    else out = System.out;
    if (outputOn) out.print("\n"+name+"\t"+affiliation+"\t"+email+"\t"+algorithmName);
    List problems = Arrays.asList(PROBLEMS);
    while (problemSet.size() < NUM_PROBLEMS) problemSet.addAll(problems);
    Collections.shuffle(problemSet, new Random(START_NUMBER));
    problemIterator = problemSet.listIterator();
    problemNumber = 0;
  }
  
  /**
   * Start the next problem.
   */
  public void nextProblem () throws Exception {
    if (problemNumber >= NUM_PROBLEMS) throw new Exception("All problems finished.");
    else {
      problemNumber++;
      int seed = START_NUMBER*599+problemNumber*199;   // scatter using primes
      if (outputOn) out.print("\t"+problemNumber+"\t");
      Object[] nextProblem = (Object[]) problemIterator.next();
      currentProblem = new FractalFunction2D ((String) nextProblem[0], FRACTAL_DEPTH, 
                                              ((Integer) nextProblem[1]).intValue(), seed);
      ran = new Random(seed);
      ran.nextDouble();
      origin[0] = ran.nextDouble();
      origin[1] = ran.nextDouble();
      scaleFactor = Math.pow(2,ran.nextDouble()*(FRACTAL_DEPTH-8));
      zero = currentProblem.evaluate(origin);
      count = 0;
      bestResult = Double.MAX_VALUE;
    }
  }
  
  /**
   * Evaluate a point in the parameter space this problem (get its fitness).
   * @param point the two dimensional (x,y) co-ordinates of the point
   * @return the value or fitness (or zero if all permitted evaluations have been used)
   */
  public double evaluate (double[] point) {
    count++;
    if (count <= NUM_EVALUATIONS) {
      double[] vector = {point[0]/scaleFactor, point[1]/scaleFactor};
      double[] realPoint = {origin[0]+vector[0], origin[1]+vector[1]};
      result = (currentProblem.evaluate(realPoint)-zero) * scaleFactor;
      if (result < bestResult) bestResult=result;
      if (count == NUM_EVALUATIONS && outputOn) {
        out.print(bestResult);
      }
    }
    else result=0;
    return result;
  }

  /**
   * Evaluate a set (array) of points for this problem.
   * @param points the points to evaluate - this must be an <i>n</i>x2 array in which second argument
   * contains the two dimensional (x,y) co-ordinates for each of the <i>n</i> points
   * @return an array containing the result for each point evaluated
   */
  public double[] evaluate (double[][] points) {
    double[] results = new double[points.length];
    for (int i=0; i<points.length; i++) results[i] = evaluate(points[i]);
    return results;
  }
  

  /**
   * Evaluate a List of points for this problem.
   * @param points the points to evaluate - this must be a List, each element of which is a double array
   * of length 2 containing the two dimensional (x,y) co-ordinates of a point to evaluate
   * @return a List of Doubles containing the evaluations of each point 
   */
  public List evaluate (List points) {
    List results = new ArrayList(points.size());
    ListIterator li = points.listIterator();
    while (li.hasNext()) results.add(new Double(evaluate((double[]) li.next()))); 
    return results;
  }
  

  /**
   * Check which problem number you are solving (convenience method only).
   * @return the problem number, starting from number 1
   */
  public int getProblemNumber () {
    return problemNumber;
  }
  
  /**
   * Check how many evaluations you've done on this problem so far (convenience method only).
   * @return the number of evaluations used
   */   
   public int getCount () {
    return count;
  }
  
  /**
   * Turn output on or off. Produces a tab-separated output of the 30 trials and the score for each.
   * @param outputOn true for on, false for off
   */
  public void turnOutputOn (boolean outputOn) {
    this.outputOn = outputOn;
  }

  /**
   * Set output (if on) to file or stdout (usually terminal). The file is results.txt in the 
   * current directory (for a different file use stdout and redirect to a file).
   * @param outputToFile true for file, false for stdout (default)
   */
  public void setOutputToFile (boolean outputToFile) {
    this.outputToFile = outputToFile;
  }
  
  
}
