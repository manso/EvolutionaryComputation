package utils.Fractal;
//
//  Ran0.java
//  Random deviate generator for the Fractal Benchmark Functions
//
//  This source is distributed under GPL3.0. See ../index.html
//  for important information on modifying and distributing.

/**
 * This class implements a (repeatable) portable multiplicative congruential pseudo-random 
 * generator with modulus 2<sup>31</sup>-1, using the algorithm provided by:
 * W. H. Press et al, "Numerical Recipes in C: The Art of Scientific Computing", 
 * Cambridge University Press, 2nd Ed., 1992.
 * The class provides both uniform and Gaussian deviates.
 *<p>
 * The class is intended for creating a sequence of generator instances (and hence benchmark functions)
 * from a sequence of indices.
 *<p>
 * This is the original generator used in:
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
 * It proved too slow for the very high dimensional functions and has been replaced by {@link RanQD1}.
 * @author {@link <a href="http://www.csse.uwa.edu.au/~cara/">Cara MacNish</a>}, University of Western Australia
 * @version 1.0RC1, 7th Nov 2007
 * <br>For the latest version and additional information see the
 * {@link <a href="http://www.cs.bham.ac.uk/research/projects/ecb/">Birmingham Repository</a>}
 */
public class Ran0 {
  private final static long IA_DEFAULT_BENCHMARKING = 69621l;  // Benchmarking multiplicand
  private final static long IA_DEFAULT_TRAINING = 16807l;      // Training multiplicand
  private final static long IA_ALTERNATIVE = 48271;            // Third possibility from Numerical Recipes
  private final static long IM = 2147483647;                   // modulus, m = 2^31 - 1
  private double AM = 1.0/IM;                                  // do division once
  
  //IQ = 30845;                                                // not required if using
  //IR = 23902;                                                // 64-bit double precision
  
  private long IA;
  private long idum;
 
  private boolean gaussAvail = false;                           // used for
  private double gaussVal = 0;                                  // Gaussian deviates
  
  /**
   * Create a random generator using the default multiplicand for benchmarking.
   * @param seed the sequence value for the generator (from which the generator is seeded)
   **/
  public Ran0 (long seed) {
    if (seed <= 0 || seed >= IM) 
      throw new RuntimeException("Ran0 Exception: seed must be in range 1..2^31-2"); 
    IA = IA_DEFAULT_BENCHMARKING;
    idum = seed * IA_ALTERNATIVE % IM;    // spreads indices while guaranteeing using all values    
  }
  
  /**
   * Create a random generator using a multiplicand passed by the user.
   * The value 16807 is recommended as an alternative value for training purposes.
   * No other values should be used (see Numerical Recipies).
   * @param IA the multiplicand to be used
   * @param seed the sequence value for the generator (from which the generator is seeded)
   **/
  public Ran0 (long IA, long seed) {
    if (seed <= 0 || seed >= IM) 
      throw new RuntimeException("Ran0 Exception: seed must be in range 1..2^31-2. Yours was: " + seed); 
    this.IA = IA;
    idum = seed * IA_ALTERNATIVE % IM;    // spreads indices while guaranteeing using all values    
  }
  
  /**
   * Reset the seed (quicker than obtaining a new instance).
   */
  public void setSeed (long seed) {
    if (seed <= 0 || seed >= IM) 
      throw new RuntimeException("Ran0 Exception: seed must be in range 1..2^31-2. Yours was: " + seed); 
    idum = seed * IA_ALTERNATIVE % IM;    // spreads indices while guaranteeing using all values    
  }
  
  
/* For scattering seed. No longer used.
  private void initialise (long seed) {
    idum = seed * 48271 % IM;        
    if (idum==0) throw new RuntimeException("Ran0 Exception: Zero seed");
    
    //first clean seed - must be in range [1..2^31-2]
    seed = Math.abs(seed);                          // ensure positive
    if (seed == 0) System.out.println("Warning: zero seed moved"); //[for debugging only]
    seed = ( (seed-1) % (IM-1) ) +1;                // map into range [1..2^31-2]

    // next scatter seed
    idum = (int)((IA*seed) % IM);                   // one multiple by 'a'
    StringBuffer s = new StringBuffer(Integer.toHexString(idum));     // convert to hex
    s.reverse();                                    // flip so least sig becomes most sig
    while (s.length() < 8) s.append('0');           // pad out to 8 digits
    long temp = Long.parseLong(s.toString(),16);    // convert back to decimal
    idum = (int) ((temp % (IM-1)) + 1);             // may be larger than IM - map into [1..2^31-1]
  }
 */   
  
  /**
   * Obtain the next random number from a uniform distribution between 0 and 1.
   * @return the uniform deviate
   */
  public double uniform () {          
    idum = (IA * idum) % IM; 
//    while (idum >= IM) idum = idum - IM;  // this seems much faster than using mod
    return AM * idum;                     // needs further testing
  }
  
  /**
   * Obtain the next random number from a uniform distribution between user defined bounds.
   * @param lower the lower bound
   * @param upper the upper bound
   * @return the uniform deviate
   */
  public double uniform (double lower, double upper) {
    return lower + uniform() * (upper - lower);
  }
  
  /**
   * Obtain the next random long
   */
  public long nextLong () {
    idum = ((IA * idum) % IM);
    return idum;
  }
  
  /**
   * Obtain the next random number from a Gaussian distribution with mean and standard deviation 1.
   * @return the Guassian deviate
   */
  public double gauss () {                          // get Gaussian deviate
    double gdev=0;
    if (!gaussAvail) {                              // if no stored deviate available
      while (!gaussAvail) {
        double v1 = 2*uniform()-1;                  // generate two at once for efficiency
        double v2 = 2*uniform()-1;
        double rsq = v1*v1 + v2*v2;
        if (rsq < 1.0 && rsq != 0) {                // accept if in range
          double fac = Math.sqrt(-2*Math.log(rsq)/rsq);
          gaussVal = v1 * fac;                      // store one value
          gaussAvail = true;
          gdev = v2 * fac;                          // second value gets returned
        }
      }
    }
    else {                                          // else if already available use stored deviate
      gaussAvail = false;
      gdev = gaussVal;
    }
    return gdev;
  }
  
  /**
   * Obtain the next random number from a Gaussian distribution with user defined mean and standard deviation.
   * @param mean the mean of the Gaussian distribution
   * @param stdev the standard deviation of the Gaussian distribution
   */
  public double gauss (double mean, double stdev) { // get shifted and scaled Gaussian deviate
    return mean + stdev * gauss();
  }
  
}
