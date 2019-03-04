//
//  GrainPitchGene.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/28/05.
//  Copyright 2005 __MyCompanyName__. All rights reserved.
//

import org.jgap.impl.IntegerGene;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * A gene used to represent grain pitch/frequency, measured in Hz (20-20k)
 */
public class GrainPitchGene extends IntegerGene
{
    
    // to randomize the grain panning
    private boolean randomize;
    
    private static final String TOKEN_SEPARATOR = ":";
    
    /**
     * Constructs a new GrainPitchGene with default minimum (20) and
     * maximum (20000) values for grain pan.
     */
    public GrainPitchGene()
    {
        super(20, 20000);
        randomize = false;
    }


    /**
     * Constructs a new GrainPitchGene with a constraint on the
     * lowest and highest frequency.
     *
     * @param minFreq The lowest frequency that this GrainPitchGene can represent          
     *
     * @param maxFreq The highest frequency that this GrainPitchGene can represent  
     * 
     */
    public GrainPitchGene(int minFreq, int maxFreq)
    {
        super(minFreq, maxFreq);
        randomize = false;
    }


    /**
     * Constructs a new GrainPitchGene with the specified value for the
     * low frequency and default value (20000) for the high frequency.
     *
     * @param minFreq The lowest frequency that this GrainPitchGene can represent 
     *
     * @throws IllegalArgumentException if the given value is negative
     */
    public GrainPitchGene(int minFreq)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(minFreq < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
        
        super(minFreq, 20000);
        randomize = false;
    }


    /**
     * Constructs a new GrainPitchGene with the specified value for the
     * high frequency and default value (20) for the low frequency.
     *
     * @param maxFreq The highest frequency that this GrainPitchGene can represent 
     *
     * @throws IllegalArgumentException if the given value is negative
     */
    public GrainPitchGene(int maxFreq)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(maxFreq < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");

        super(20, maxFreq);
        randomize = false;
    }
    
    
    /**
     * Constructs a new GrainPitchGene with the option to set randomize to true
     * so that it will return random values for frequency
     *
     * @param b turns randomization on or off
     */
    public GrainPitchGene(boolean b)
    {
        super(20, 20000);
        randomize = b;
    }
    
    /**
     * Sets the frequency to random, so whenever this gene is accessed
     * the frequency will be a random number between 20 and 20000.
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
        // We construct the new GrainPitchGene with the same maximum and
        // number of grains that this Gene was constructed with, or with
        // randomization, if the randomize value is true
        // -------------------------------------------------------------
        if(randomize) {
            new GrainPitchGene(randomize);
        } else {
            return new GrainPitchGene(super.m_lowerBounds, super.m_upperBounds);
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
            return new Integer(new Random().nextInt(19980)+20);
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
        if(otherGrainPitchGene == null)
        {
            return 1;
        }

        // If our allele is null, then we're either the same as the given
        // GrainSizeGene if its allele is also null, or less than it if
        // its allele is not null.
        // -------------------------------------------------------------
        if(super.m_value == null)
        {
            if (((GrainPitchGene) otherGrainPitchGene).m_value == null)
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
            return super.m_value.compareTo(((GrainPitchGene) otherGrainPitchGene).m_value);
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Determines if this GrainPitchGene is equal to the given GrainPitchGene.
     *
     * @return true if this GrainPitchGene is equal to the given GrainPitchGene,
     *         false otherwise.
     */
    public boolean equals(Object otherGrainPitchGene)
    {
        return otherGrainPitchGene instanceof otherGrainPitchGene && 
               compareTo(otherGrainPitchGene) == 0;
    }
    
    
    /**
     * Calculates the hash-code for this GrainPitchGene.
     *
     * @return the hash-code of this GrainPitchGene
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
     * Retrieves a string representation of this GrainPitchGene's value that
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
