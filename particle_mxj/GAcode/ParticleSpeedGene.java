//
//  ParticleSpeedGene.java
//  SCD
//
//  Created by Carlos Castellanos on 2/28/05.
//  Copyright 2005. All rights reserved.
//

package ga;

import org.jgap.impl.DoubleGene;


/**
 * A gene used to represent the speed of a particle.  An optional
 * minimum and maximum may be provided
 */
 
public class ParticleSpeedGene extends DoubleGene {

    /**
     * Constructs a new ParticleSpeedGene with default minimum (1.0) and
     * maximum (10000.0) values for speed.
     */
    public ParticleSpeedGene()
    {
        super(1.0, 10000.0);
    }


    /**
     * Constructs a new ParticleSpeedGene with a constraint on the
     * minimum and maximum values.
     *
     * @param min The lowest possible speed that this ParticleSpeedGene
     *                can represent.
     *
     * @param max The highest possible speed that this ParticleSpeedGene
     *                can represent.
     *
     * @throws IllegalArgumentException if either of the given values is
     *          negative or if min is greater than max
     */
    public ParticleSpeedGene(double min, double max)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(min < 0 || max < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
            
        // Make sure that min isn't greater than max
        if(min > max)
            throw new IllegalArgumentException
                    ("Value for minimum speed cannot be more than the value for maximum speed!");
                    
        super(min, max);
    }
    
    
    /**
     * Constructs a new ParticleSpeedGene with the specified minimum value
     * and a default maximum value (10000.0) for the speed.
     *
     * @param min The lowest possible speed that this ParticleSpeedGene
     *                can represent.
     *
     * @throws IllegalArgumentException if the given value is negative or
     *          if it is greater than 10000 (the default max value)
     */
    public ParticleSpeedGene(double min)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(min < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
            
        // Make sure that min isn't greater than 10000
        if(min > 10000)
            throw new IllegalArgumentException
                    ("Value for minimum speed cannot be more than the default value for maximum speed!");
        
        super(min, 10000.0);
    }


    /**
     * Constructs a new ParticleSpeedGene with the specified maximum value
     * and a default minimum value (1.0) for the speed.
     *
     * @param max The highest possible speed that this ParticleSpeedGene
     *                can represent.
     *
     * @throws IllegalArgumentException if the given value is less than 1 (the
     *           default min value)
     */
    public ParticleSpeedGene(double max)
    {
        // Make sure the given value isn't less than 1.
        // --------------------------------------------
        if(max < 1)
            throw new IllegalArgumentException
                    ("Value for maximum speed cannot be less than the default value for minimum speed!");

        super(1.0, max);
    }
    
    
    /**
     * Compares this Gene with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object. The given object
     * must be a ParticleSpeedGene.
     *
     * @param   otherParticleSpeedGene the Object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *              is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this Object.
     */
    public int compareTo(Object otherParticleSpeedGene)
    {
        // If the other allele is null, we're bigger.
        // ------------------------------------------
        if(otherParticleSpeedGene == null)
        {
            return 1;
        }

        // If our allele is null, then we're either the same as the given
        // ParticleSpeedGene if its allele is also null, or less than it if
        // its allele is not null.
        // -------------------------------------------------------------
        if(super.m_value == null)
        {
            if (((ParticleSpeedGene) otherParticleSpeedGene).m_value == null)
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }

        // Otherwise, we just take advantage of our super class's compareTo()
        // method.
        // ------------------------------------------------------------
        try
        {
            return super.m_value.compareTo(((ParticleSpeedGene) otherParticleSpeedGene).m_value);
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Determines if this ParticleSpeedGene is equal to the given ParticleSpeedGene.
     *
     * @return true if this ParticleSpeedGene is equal to the given ParticleSpeedGene,
     *         false otherwise.
     */
    public boolean equals(Object otherParticleSpeedGene)
    {
        return otherParticleSpeedGene instanceof otherParticleSpeedGene && 
               compareTo(otherParticleSpeedGene) == 0;
    }
    
    
    /**
     * Calculates the hash-code for this ParticleSpeedGene.
     *
     * @return the hash-code of this ParticleSpeedGene
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
     * Retrieves a string representation of this ParticleSpeedGene's value that
     * may be useful for display purposes.
     *
     * @return a string representation of this ParticleSpeedGene's value.
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
