/*
 * Created on Apr 17, 2007
 */
package utils.StaticalLib.Distributions.rng;

import java.util.Random;

import utils.StaticalLib.Distributions.StdUniformRng;


public class Rand implements StdUniformRng {

  Random random;
  
  public Rand() {
    random = new Random();
  }
  
  public void fixupSeeds() {
    ; // do nothing since seeds are managed
  }

  public double random() {
    return random.nextDouble();
  }

}
