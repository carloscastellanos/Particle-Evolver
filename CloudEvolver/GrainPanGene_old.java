//
//  GrainPanGene.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/27/05.
//  Copyright 2005. All rights reserved.
//

import org.jgap.Gene;
import org.jgap.Configuration;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.RandomGenerator;

import java.util.StringTokenizer;
import java.util.Random;

/**
 * A gene used to represent grain panning, measured in integers from 0 (hard left)
 * to 127 (hard right).  This is not so much panning of individual grains but the
 * overall stero image of the cloud
 */
public class GrainPanGene implements Gene, java.io.Serializable
{

    private static final String TOKEN_SEPARATOR = ":";
    
    private int maxGrainPan, minGrainPan;
    private Integer grainPan = null;
    
    // to randomize the grain panning
    private boolean randomize;
    
    /**
     * Constructs a new GrainPanGene with default minimum (0) and
     * maximum (127) values for grain pan.  So the default allows
     * for hard left and hard right panning.
     */
    public GrainPanGene()
    {
        minGrainPan = 0;
        maxGrainPan = 127;
        
        randomize = false;
    }


    /**
     * Constructs a new GrainPanGene with a constraint on the
     * amount of panning.
     *
     * @param minPan The farthest left panning that this GrainPanGene can represent
     *               
     *
     * @param maxPan The farthest right panning that this GrainPanGene can represent
     *         
     *
     * @throws IllegalArgumentException if either of the given values is negative
     *         or if minPan > maxPan
     */
    public GrainPanGene(int minPan, int maxPan)
    {
        // Make sure the given values are non-negative.
        // --------------------------------------------
        if(minPan < 0 || maxPan < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
            
        // Make sure that minPan isn't more than maxPan.
        // ---------------------------------------------------
        if(minPan > maxPan)
            throw new IllegalArgumentException
                    ("Value for minimum grain pan cannot be more than the value for maximum grain pan!");
        
        minGrainPan = minPan;        
        maxGrainPan = maxPan;
        
        randomize = false;
    }


    /**
     * Constructs a new GrainPanGene with the specified minimum value
     * and a default maximum value (127) for the grain pan.
     *
     * @param minPan The farthest left panning that this GrainPanGene
     *                can represent.
     *
     * @throws IllegalArgumentException if the given value is negative
     */
    public GrainPanGene(int minPan)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(minPan < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
        
        minGrainPan = minPan;        
        maxGrainPan = 127;
        
        randomize = false;
    }


    /**
     * Constructs a new GrainPanGene with the specified maximum value
     * and a default minimum value (0) for the grain pan.
     *
     * @param maxPan The farthest right panning that this GrainPanGene
     *                can represent.
     *
     * @throws IllegalArgumentException if the given value is negative
     */
    public GrainPanGene(int maxPan)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(maxPan < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
        
        minGrainPan = 0;        
        maxGrainPan = maxPan;
        
        randomize = false;
    }
    
    
    /**
     * Constructs a new GrainPanGene with the option to set randomize to true
     * so that it will return random values for grainPan
     *
     * @param b turns randomization on or off
     */
    public GrainPanGene(boolean b)
    {
        randomize = b;
        
        minGrainPan = 0;
        maxGrainPan = 127;
    }
    
    /**
     * Sets the panning to random, so whenever this gene is accessed
     * the panning will be a random number between 0 and 127.
     * This method is incvluded as a courtesy in case you called the
     * wrong constructor or in you case you change your mind ;-)
     *
     * @param value turns randomization on or off
     *
     */
    public void setRandom(boolean value)
    {
        randomize = value;
    }
    
    
    /**
     * Returns a boolean value indicating whether randomization is on or not
     *
     * @return a boolean value indicating whether randomization is on or not
     */
    public boolean getRandom()
    {
        return randomize;
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
        // We construct the new GrainPanGene with the same maximum and
        // number of grains that this Gene was constructed with, or with
        // randomization, if the randomize value is true
        // -------------------------------------------------------------
        if(randomize) {
            new GrainPanGene(randomize);
        } else {
            return new GrainPanGene(minGrainPan, maxGrainPan);
        }
    }


    /**
     * Sets the value of this Gene to the new given value. The actual
     * type of the value is implementation-dependent.
     *
     * note - This method is effectively rendered useless if
     * randomization is true
     *
     * @param newValue the new value of this Gene instance.
     */
    public void setAllele(Object newValue)
    {
        grainPan = (Integer) newValue;
    }


    /**
     * Retrieves the value represented by this Gene. The actual type
     * of the value is implementation-dependent.
     *
     * @return the value of this Gene.
     */
    public Object getAllele()
    {
        // if randomize is true this method will return random values
        if(randomize) {
            return new Integer(new Random().nextInt(127));
        } else {
            return grainPan;
        }
    }
    
    
    /**
     * Sets the value of this Gene to a random legal value for the
     * implementation. This method exists for the benefit of mutation and other
     * operations that simply desire to randomize the value of a gene.
     *
     * note - This method is effectively rendered useless if
     * randomization is true
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
        grainPan = new Integer(numberGenerator.nextInt(maxGrainPan));
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
        // ----------------------------------------------------------------
        return new Integer(minGrainPan).toString() + TOKEN_SEPARATOR + new Integer(maxGrainPan).toString()
            + TOKEN_SEPARATOR + grainPan.toString() + TOKEN_SEPARATOR + new Boolean(randomize).toString();

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
        if(tokenizer.countTokens() != 4)
            throw new UnsupportedRepresentationException(
                "Unknown representation format: Four tokens expected.");

        try
        {
            // Parse the four tokens.
            // ---------------------------------
            minGrainPan = Integer.parseInt(tokenizer.nextToken());
            maxGrainPan = Integer.parseInt(tokenizer.nextToken());
            grainPan = new Integer(tokenizer.nextToken());
            randomize = Boolean.getBoolean(tokenizer.nextToken());
        }
        catch(NumberFormatException e)
        {
            throw new UnsupportedRepresentationException(
                "Unknown representation format: Expecting 3 integer values and one boolean value.");
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
     * must be a GrainPanGene.
     *
     * @param   otherGrainSizeGene the Object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *              is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this Object.
     */
    public int compareTo(Object otherGrainPanGene)
    {
        // If the other allele is null, we're bigger.
        // ------------------------------------------
        if(otherGrainPanGene == null)
        {
            return 1;
        }

        // If our allele is null, then we're either the same as the given
        // GrainSizeGene if its allele is also null, or less than it if
        // its allele is not null.
        // -------------------------------------------------------------
        if(grainPan == null)
        {
            if (((GrainPanGene) otherGrainPanGene).grainPan == null)
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
        return grainPan.compareTo(((GrainPanGene) otherGrainPanGene).grainPan);
    }


    /**
     * Determines if this GrainPanGene is equal to the given GrainPanGene.
     *
     * @return true if this GrainPanGene is equal to the given GrainPanGene,
     *         false otherwise.
     */
    public boolean equals(Object otherGrainPanGene)
    {
        return otherGrainPanGene instanceof otherGrainPanGene && 
               compareTo(otherGrainPanGene) == 0;
    }
    
    
    /**
     * Calculates the hash-code for this GrainPanGene.
     *
     * @return the hash-code of this GrainPanGene
     */
    public int hashCode()
    {
        return grainPan;
    }


}
