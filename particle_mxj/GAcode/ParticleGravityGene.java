//
//  ParticleGravityGene.java
//  SCD
//
//  Created by Carlos Castellanos on 2/28/05.
//  Copyright 2005. All rights reserved.
//

package ga;

import org.jgap.Gene;
import org.jgap.impl.CompositeGene;
import org.jgap.RandomGenerator;
import org.jgap.UnsupportedRepresentationException;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * A gene used to represent the gravity of a particle.  Takes
 * three double values (given in an array) which correspond
 * to the x, y and z of a vector.
 */
public class ParticleGravityGene extends CompositeGene {

    // the gravity vector
    private Double[] gravityVector = null;
    // list of vector values
    private ArrayList vectors;

    private double upperBounds, lowerBounds;
    
    /**
     * Constructs a new ParticleGravityGene with default
     * minimum (-100.0) and maximum (100.0) values for each
     * of the x, y and z values of the vector.
     */
    public ParticleGravityGene()
    {
        lowerBounds = -100.0;
        upperBounds = 100.0;
    }
    
    
    /**
     * Constructs a new ParticleGravityGene with the specified
     * minimum and maximum values for the x, y and z of the vector
     *
     * @param min The lowest possible gravity vector that this ParticleGravityGene
     *              can represent
     *
     * @param max The highest possible gravity vector that this ParticleGravityGene
     *              can represent
     *
     * @throws IllegalArgumentException if min is greater than max
     */
    public ParticleGravityGene(double min, double max)
    {
        // Make sure that min isn't greater than max
        if(min > max)
            throw new IllegalArgumentException
                    ("Value for minimum gravity cannot be more than the value for maximum gravity!");

        lowerBounds = min;
        upperBounds = max;
    }
    
    
    /**
     * Constructs a new ParticleGravityGene with the specified
     * minimum value and a default maximum value for the x, y
     * and z of the vector
     *
     * @param min The lowest possible gravity vector that this ParticleGravityGene
     *              can represent
     *
     * @throws IllegalArgumentException if min is greater than 100 (the default max value)
     */
    public ParticleGravityGene(double min)
    {
        // Make sure that min isn't greater than 100
        if(min > 100)
            throw new IllegalArgumentException
                    ("Value for minimum gravity cannot be more than the default value for maximum gravity!");

        lowerBounds = min;
        upperBounds = 100;
    }
    
    
    /**
     * Constructs a new ParticleGravityGene with the specified
     * minimum value and a default maximum value for the x, y
     * and z of the vector
     *
     * @param max The highest possible gravity vector that this ParticleGravityGene
     *              can represent
     *
     * @throws IllegalArgumentException if max is less than -100 (the default min value)
     */
    public ParticleGravityGene(double max)
    {
        // Make sure that min isn't greater than 100
        if(max < -100)
            throw new IllegalArgumentException
                    ("Value for maximum gravity cannot be less than the default value for minimum gravity!");

        lowerBounds = -100.0;
        upperBounds = max;
    }


    /**
     * Provides an implementation-independent means for creating new Gene
     * instances. The new instance that is created and returned should be
     * setup with any implementation-dependent configuration that this Gene
     * instance is setup with (aside from the actual value, of course).
     *
     * @return A new Gene instance of the same type and with the same
     *         setup as this concrete Gene.
     */
    public Gene newGene()
    {
        // We construct the new ParticleGravityGene with the same minimum
        // and maximum values for each of the x, y and z values of the vector
        // that this Gene was constructed with.
        // ----------------------------------------------------------------
        return new ParticleGravityGene(lowerBounds, upperBounds);
    }


    /**
     * Sets the value of this ParticleGravityGene to the new given value. The actual
     * type of the value is implementation-dependent.
     *
     * @param newValue the new value of this Gene instance.
     */
    public void setAllele(Object newValue)
    {
        vectors = (ArrayList) newValue;
        gravityVector = (Double[]) vectors.toArray();
        mapValueToWithinBounds();
    }


    /**
     * Retrieves the value (allele) represented by this ParticleGravityGene.
     *
     * @return the value of this ParticleGravityGene.
     */
    protected Object getInternalValue()
    {
        return gravityVector;
    }
    
    
    /**
     * Sets the value of this Gene to a random legal value for the
     * implementation. This method exists for the benefit of mutation and other
     * operations that simply desire to randomize the value of a gene.
     *
     * @param numberGenerator The random number generator that should be
     *                        used to create any random values. It's important
     *                        to use this generator to maintain the user's
     *                        flexibility to configure the genetic engine
     *                        to use the random number generator of their
     *                        choice.
     */
    public void setToRandomValue(RandomGenerator numGenerator)
    {
        // Pick a random number between the minimum and maximum number
        // allowed for a gravity vector.
        // -----------------------------------------------------
        for(int i = 0; i < gravityVector.length; i++)
        {
            gravityVector[i] = new Double((upperBounds - lowerBounds)
                        * numGenerator.nextDouble() + lowerBounds);
        }
    }

}
