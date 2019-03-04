//
//  ParticleConfig.java
//  ParticleEvolver
//
//  Created by Carlos Castellanos on 3/12/05.
//  Copyright 2005. All rights reserved.
//

package ga;

import org.jgap.*;
import org.jgap.event.*;
import org.jgap.impl.*;

/**
 * The ParticleConfig class provides values for many of the
 * configuration settings. The following values must still be provided:
 * the sample Chromosome, population size, and desired fitness function.
 * All other settings may also be changed in the normal fashion for
 * those who wish to specify other custom values.
 */
public class ParticleConfig extends Configuration {


  /**
   * Constructs a new ParticleConfig instance with a number of
   * Configuration settings set to default values. It is still necessary
   * to set the sample Chromosome, population size, and desired fitness
   * function. Other settings may optionally be altered as desired.
   */
  public ParticleConfig() {
    super();
    try {
        // Weighted Roulette Wheel
      WeightedRouletteSelector weightedRoulette = new WeightedRouletteSelector();
      weightedRoulette.setDoubletteChromosomesAllowed(false);
      addNaturalSelector(weightedRoulette, true);
      setRandomGenerator(new StockRandomGenerator());
      setMinimumPopSizePercent(0);
      setEventManager(new EventManager());
      setFitnessEvaluator(new DefaultFitnessEvaluator());
      setChromosomePool(new ChromosomePool());
      addGeneticOperator(new AveragingCrossoverOperator());
      addGeneticOperator(new MutationOperator(10));
    }
    catch (InvalidConfigurationException e) {
      throw new RuntimeException(
          "Fatal error: ParticleConfig class could not use its own configuration values.");
    }
  }
}
