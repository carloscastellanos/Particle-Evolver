//
//  GrainPanGene.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/27/05.
//  Copyright 2005. All rights reserved.
//

import org.jgap.impl.IntegerGene;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * A gene used to represent grain panning, measured in integers from 0 (hard left)
 * to 127 (hard right).  This is not so much panning of individual grains but the
 * overall stero image of the cloud
 */
public class GrainPanGene extends IntegerGene
{
    
    // to randomize the grain panning
    private boolean randomize;
    
    private static final String TOKEN_SEPARATOR = ":";
    
    /**
     * Constructs a new GrainPanGene with default minimum (0) and
     * maximum (127) values for grain pan.  So the default allows
     * for hard left and hard right panning.
     */
    public GrainPanGene()
    {
        super(0, 127);
        randomize = false;
    }


    /**
     * Constructs a new GrainPanGene with a constraint on the
     * amount of panning.
     *
     * @param minPan The farthest left panning that this GrainPanGene can represent          
     *
     * @param maxPan The farthest right panning that this GrainPanGene can represent
     * 
     */
    public GrainPanGene(int minPan, int maxPan)
    {
        super(minPan, maxPan);
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
        
        super(minPan, 127);
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

        super(0, maxPan);
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
        super(0, 127);
        randomize = b;
    }
    
    /**
     * Sets the panning to random, so whenever this gene is accessed
     * the panning will be a random number between 0 and 127.
     * This method is incvluded as a courtesy in case you called the
     * wrong constructor or in you case you change your mind ;-)
     *
     * @param b turns randomization on or off
     *
     */
    public void setRandom(boolean b)
    {
        randomize = b;
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
            return new GrainPanGene(super.m_lowerBounds, super.m_upperBounds);
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
        if(!randomize)
            super.setAllele(newValue);
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
            return super.m_value;
        }
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
        return new Integer(super.m_lowerBounds).toString() + TOKEN_SEPARATOR +
                new Integer(super.m_upperBounds).toString() + TOKEN_SEPARATOR +
                super.m_value.toString() + TOKEN_SEPARATOR + new Boolean(randomize).toString();
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
            super.m_lowerBounds = Integer.parseInt(tokenizer.nextToken());
            super.m_upperBounds = Integer.parseInt(tokenizer.nextToken());
            super.m_value = new Integer(tokenizer.nextToken());
            randomize = Boolean.getBoolean(tokenizer.nextToken());
        }
        catch(NumberFormatException e)
        {
            throw new UnsupportedRepresentationException(
                "Unknown representation format: Expecting 3 integer values and one boolean value.");
        }
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
        if(super.m_value == null)
        {
            if (((GrainPanGene) otherGrainPanGene).m_value == null)
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
        try
        {
            return super.m_value.compareTo(((GrainPanGene) otherGrainPanGene).m_value);
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
            throw e;
        }
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
        if(super.m_value == null)
        {
            return 0;
        }
        else
        {
            return super.m_value.hashCode();
        }
    }


    /**
     * Retrieves a string representation of this GrainPanGene's value that
     * may be useful for display purposes.
     *
     * @return a string representation of this GrainPanGene's value.
     */
    public String toString()
    {
        if(super.m_value == null)
        {
            return "null";
        }
        else
        {
            return super.m_value.toString();
        }
    }


}
