//
//  GrainSizeGene.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/25/05.
//  Copyright 2005. All rights reserved.
//

import org.jgap.Gene;
import org.jgap.Configuration;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.RandomGenerator;

import java.util.StringTokenizer;

/**
 * A gene used to represent grain size (duration), measured in milliseconds
 * an optional minimum and maximum number for grain size may be provided
 */
public class GrainSizeGene implements Gene, java.io.Serializable
{

    private static final String TOKEN_SEPARATOR = ":";
    
    private int maxGrainSize, minGrainSize;
    private Integer grainSize = null;

    /**
     * Constructs a new GrainSizeGene with default minimum (1) and
     * maximum (100) values for grain size (in ms).
     */
    public GrainDensityGene()
    {
        minGrainSize = 1;
        maxGrainSize = 100;
    }


    /**
     * Constructs a new GrainSizeGene with a constraint on the
     * minimum and maximum number for the grain size.
     *
     * @param minSize The smallest possible grain size (in ms) that this GrainSizeGene
     *                can represent.
     *
     * @param maxSize The largest possible grain size (in ms) that this GrainSizeGene
     *                can represent.
     *
     * @throws IllegalArgumentException if either of the given values is negative
     *         or if minSize > maxSize
     */
    public GrainSizeGene(int minSize, int maxSize)
    {
        // Make sure the given values are non-negative.
        // --------------------------------------------
        if(minSize < 0 || maxSize < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
            
        // Make sure that minGrains isn't more than maxGrains.
        // ---------------------------------------------------
        if(minSize > maxSize)
            throw new IllegalArgumentException
                    ("Value for minimum grain size cannot be more than the value for maximum grain size!");
        
        minGrainSize = minSize;        
        maxGrainSize = maxSize;
    }
    
        
    /**
     * Constructs a new GrainSizeGene with the specified minimum value
     * and a default maximum value (100) for the grain size.
     *
     * @param minSize The smallest possible grain size (in ms) that this GrainSizeGene
     *                can represent.
     *
     * @throws IllegalArgumentException if the given value is negative
     */
    public GrainSizeGene(int minSize)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(minSize < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
        
        minGrainSize = minSize;        
        maxGrainSize = 100;
    }


    /**
     * Constructs a new GrainSizeGene with the specified maximum value
     * and a default minimum value (1) for the grain size.
     *
     * @param minSize The smallest possible grain size (in ms) that this GrainSizeGene
     *                can represent.
     *
     * @throws IllegalArgumentException if the given value is negative
     */
    public GrainSizeGene(int maxSize)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(maxSize < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
        
        minGrainSize = 1;        
        maxGrainSize = maxSize;
    }


    /**
     * Provides an implementation-independent means for creating new Gene
     * instances. The new instance that is created and returned should be
     * setup with any implementation-dependent configuration that this Gene
     * instance is setup with (aside from the actual value, of course).
     *
     * @param activeConfig The current active configuration.
     * @return A new Gene instance of the same type and with the same
     *         setup as this concrete Gene.
     */
    public Gene newGene(Configuration activeConfig)
    {
        // We construct the new GrainSizeGene with the same maximum and
        // number of grains that this Gene was constructed with.
        // -------------------------------------------------------------
        return new GrainSizeGene(minGrainSize, maxGrainSize);
    }


    /**
     * Sets the value of this Gene to the new given value. The actual
     * type of the value is implementation-dependent.
     *
     * @param newValue the new value of this Gene instance.
     */
    public void setAllele(Object newValue)
    {
        grainSize = (Integer) newValue;
    }


    /**
     * Retrieves the value represented by this Gene. The actual type
     * of the value is implementation-dependent.
     *
     * @return the value of this Gene.
     */
    public Object getAllele()
    {
        return grainSize;
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
    public void setToRandomValue(RandomGenerator numberGenerator)
    {
        // Pick a random number between 0 and the maximum number
        // of grains we're allowed to represent.
        // -----------------------------------------------------
        grainSize = new Integer(numberGenerator.nextInt(maxGrainSize));
    }

    
    /**
     * Retrieves a string representation of the value of this Gene instance
     * that includes any information required to reconstruct it at a later
     * time, such as its value and internal state. This string will be used to
     * represent this Gene instance in XML persistence.
     *
     * @return A string representation of this Gene's current state.
     * @throws UnsupportedOperationException to indicate that no implementation
     *         is provided for this method.
     */
    public String getPersistentRepresentation()
                  throws UnsupportedOperationException
    {
        // We want to represent both the minimum and maximum grain size
        // that can be represented by this Gene and its actual current value.
        // We'll separate each with colons.
        // ---------------------------------------------------------------
        return new Integer(minGrainSize).toString() + TOKEN_SEPARATOR + new Integer(maxGrainSize).toString()
            + TOKEN_SEPARATOR + grainSize.toString();
    }


    /**
     * Sets the value and internal state of this Gene from the string
     * representation returned by a previous invocation of the
     * getPersistentRepresentation() method.
     *
     * @param representation the string representation retrieved from a
     *                         prior call to the getPersistentRepresentation()
     *                         method.
     *
     * @throws UnsupportedOperationException to indicate that no implementation
     *         is provided for this method.
     * @throws UnsupportedRepresentationException if this Gene implementation
     *         does not support the given string representation.
     */
    public void setValueFromPersistentRepresentation(String representation)
                throws UnsupportedOperationException, UnsupportedRepresentationException
    {
        // We're expecting to find the minimum and maximum grain size that this
        // Gene can represent, with a colon after each value, followed by the
        // actual grain size currently represented.
        // -----------------------------------------------------------------
        StringTokenizer tokenizer = new StringTokenizer(representation, TOKEN_SEPARATOR);
        // Make sure there are exactly three tokens.
        // ---------------------------------------
        if(tokenizer.countTokens() != 3)
            throw new UnsupportedRepresentationException(
                "Unknown representation format: Three tokens expected.");

        try
        {
            // Parse the three tokens as integers.
            // ---------------------------------
            minGrainSize = Integer.parseInt(tokenizer.nextToken());
            maxGrainSize = Integer.parseInt(tokenizer.nextToken());
            grainSize = new Integer(tokenizer.nextToken());
        }
        catch(NumberFormatException e)
        {
            throw new UnsupportedRepresentationException(
                "Unknown representation format: Expecting integer values." );
        }
    }
    
    
    /**
     * Executed by the genetic engine when this Gene instance is no
     * longer needed and should perform any necessary resource cleanup.
     */
    public void cleanup()
    {
        // There's no cleanup necessary for this implementation (I think).
        // ---------------------------------------------------------------
    }


    /**
     * Compares this Gene with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object. The given object
     * must be a GrainSizeGene.
     *
     * @param   otherGrainSizeGene the Object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *              is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this Object.
     */
    public int compareTo(Object otherGrainSizeGene)
    {
        // If the other allele is null, we're bigger.
        // ------------------------------------------
        if(otherGrainSizeGene == null)
        {
            return 1;
        }

        // If our allele is null, then we're either the same as the given
        // GrainSizeGene if its allele is also null, or less than it if
        // its allele is not null.
        // -------------------------------------------------------------
        if(grainSize == null)
        {
            if (((GrainSizeGene) otherGrainSizeGene).grainSize == null)
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }

        // Otherwise, we just take advantage of the Integer.compareTo()
        // method.
        // ------------------------------------------------------------
        return grainSize.compareTo(((GrainSizeGene) otherGrainSizeGene).grainSize);
    }


    /**
     * Determines if this GrainSizeGene is equal to the given GrainSizeGene.
     *
     * @return true if this GrainSizeGene is equal to the given GrainSizeGene,
     *         false otherwise.
     */
    public boolean equals(Object otherGrainSizeGene)
    {
        return otherGrainSizeGene instanceof otherGrainSizeGene && 
               compareTo(otherGrainSizeGene) == 0;
    }
    
    
    /**
     * Calculates the hash-code for this GrainSizeGene.
     *
     * @return the hash-code of this GrainSizeGene
     */
    public int hashCode()
    {
        return grainSize;
    }

    
}
