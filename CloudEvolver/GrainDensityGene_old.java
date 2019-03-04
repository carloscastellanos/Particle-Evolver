//
//  GrainDensityGene.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/24/05.
//  Copyright 2005. All rights reserved.
//

import org.jgap.Gene;
import org.jgap.Configuration;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.RandomGenerator;

import java.util.StringTokenizer;

/**
 * A gene used to represent grain density, measured in grains per second
 * an optional minimum and maximum number of grains may be provided
 */
public class GrainDensityGene implements Gene, java.io.Serializable
{

    private static final String TOKEN_SEPARATOR = ":";
    
    private int maxNumGrains, minNumGrains;
    private Integer numGrains = null;
    
    /**
     * Constructs a new GrainDensityGene with default minimum (1) and
     * maximum (1000) number of grains.
     * Naturally you're limited by memory an processor restraints as
     * to the number of grains you can actually play per second.
     */
    public GrainDensityGene()
    {
        minNumGrains = 1;
        maxNumGrains = 1000;
    }
    
    
    /**
     * Constructs a new GrainDensityGene with a constraint on the
     * minimum and maximum number of grains that can be represented
     *
     * @param minGrains The minimum number of grains that this GrainDensityGene
     *                  can represent.
     *
     * @param maxGrains The maximum number of grains that this GrainDensityGene
     *                  can represent.
     *
     * @throws IllegalArgumentException if either of the given values is negative
     *         or if minGrains > maxGrains
     */
    public GrainDensityGene(int minGrains, int maxGrains)
    {
        // Make sure the given values are non-negative.
        // --------------------------------------------
        if(minGrains < 0 || maxGrains < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
            
        // Make sure that minGrains isn't more than maxGrains.
        // ---------------------------------------------------
        if(minGrains > maxGrains)
            throw new IllegalArgumentException
                    ("Value for minimum grains cannot be more than the value for maximum grains!");
        
        minNumGrains = minGrains;        
        maxNumGrains = maxGrains;
    }
    
    
    /**
     * Constructs a new GrainDensityGene with the specified minimum value
     * and a default maximum value (1000) for the number of grains.
     *
     * @param minGrains The minimum number of grains that this GrainDensityGene
     *                  can represent.
     *
     * @throws IllegalArgumentException if the given value is negative
     */
    public GrainDensityGene(int minGrains)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(minGrains < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
        
        minNumGrains = minGrains;        
        maxNumGrains = 1000;
    }
    
    
    /**
     * Constructs a new GrainDensityGene with the specified maximum value
     * and a default minimum value (1) for the number of grains.
     *
     * @param maxGrains The maximum number of grains that this GrainDensityGene
     *                  can represent.
     *
     * @throws IllegalArgumentException if the given value is negative
     */
    public GrainDensityGene(int maxGrains)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(maxGrains < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
        
        minNumGrains = 1;        
        maxNumGrains = maxGrains;
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
        // We construct the new GrainDensityGene with the same maximum and
        // number of grains that this Gene was constructed with.
        // -------------------------------------------------------------
        return new GrainDensityGene(minNumGrains, maxNumGrains);
    }
    
    
    /**
     * Sets the value of this Gene to the new given value. The actual
     * type of the value is implementation-dependent.
     *
     * @param newValue the new value of this Gene instance.
     */
    public void setAllele(Object newValue)
    {
        numGrains = (Integer) newValue;
    }
    
    
    /**
     * Retrieves the value represented by this Gene. The actual type
     * of the value is implementation-dependent.
     *
     * @return the value of this Gene.
     */
    public Object getAllele()
    {
        return numGrains;
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
        numGrains = new Integer(numberGenerator.nextInt(maxNumGrains));
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
        // We want to represent both the minimum and maximum number of grains
        // that can be represented by this Gene and its actual current value.
        // We'll separate each with colons.
        // ---------------------------------------------------------------
        return new Integer(minNumGrains).toString() + TOKEN_SEPARATOR + new Integer(maxNumGrains).toString()
            + TOKEN_SEPARATOR + numGrains.toString();
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
        // We're expecting to find the minimum and maximum number of grains that
        // this Gene can represent, with a colon after each value, followed by the
        // actual number of grains currently represented.
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
            minNumGrains = Integer.parseInt(tokenizer.nextToken());
            maxNumGrains = Integer.parseInt(tokenizer.nextToken());
            numGrains = new Integer(tokenizer.nextToken());
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
     * must be a GrainDensityGene.
     *
     * @param   otherGrainDensityGene the Object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *              is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this Object.
     */
    public int compareTo(Object otherGrainDensityGene)
    {
        // If the other allele is null, we're bigger.
        // ------------------------------------------
        if(otherGrainDensityGene == null)
        {
            return 1;
        }

        // If our allele is null, then we're either the same as the given
        // GrainDensityGene if its allele is also null, or less than it if
        // its allele is not null.
        // -------------------------------------------------------------
        if(numGrains == null)
        {
            if (((GrainDensityGene) otherGrainDensityGene).numGrains == null)
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
        return numGrains.compareTo(((GrainDensityGene) otherGrainDensityGene).numGrains);
    }
    
    
    /**
     * Determines if this GrainDensityGene is equal to the given GrainDensityGene.
     *
     * @return true if this GrainDensityGene is equal to the given GrainDensityGene,
     *         false otherwise.
     */
    public boolean equals(Object otherGrainDensityGene)
    {
        return otherGrainDensityGene instanceof otherGrainDensityGene && 
               compareTo(otherGrainDensityGene) == 0;
    }
    
    
    /**
     * Calculates the hash-code for this GrainDensityGene.
     *
     * @return the hash-code of this GrainDensityGene
     */
    public int hashCode()
    {
        return numGrains;
    }


}

