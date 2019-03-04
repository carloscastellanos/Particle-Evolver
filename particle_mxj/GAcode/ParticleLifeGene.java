//
//  ParticleLifeGene.java
//  SCD
//
//  Created by Carlos Castellanos on 2/28/05.
//  Copyright 2005. All rights reserved.
//

package ga;

import org.jgap.impl.IntegerGene;


/**
 * A gene used to represent the life of a particle.  An optional
 * minimum and maximum may be provided
 */
public class ParticleLifeGene extends IntegerGene {

    /**
     * Constructs a new ParticleLifeGene with default minimum (100) and
     * maximum (5000) values for life.
     */
    public ParticleLifeGene()
    {
        super(100, 5000);
    }


    /**
     * Constructs a new ParticleLifeGene with a constraint on the
     * minimum and maximum values.
     *
     * @param min The lowest possible life that this ParticleLifeGene
     *                can represent.
     *
     * @param max The highest possible life that this ParticleLifeGene
     *                can represent.
     *
     * @throws IllegalArgumentException if either of the given values is
     *          negative or if min is greater than max
     */
    public ParticleLifeGene(int min, int max)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(min < 0 || max < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
            
        // Make sure that min isn't greater than max
        if(min > max)
            throw new IllegalArgumentException
                    ("Value for minimum life cannot be more than the value for maximum life!");
            
        super(min, max);
    }
    
    
    /**
     * Constructs a new ParticleLifeGene with the specified minimum value
     * and a default maximum value (5000) for the life.
     *
     * @param min The lowest possible life that this ParticleLifeGene
     *                can represent.
     *
     * @throws IllegalArgumentException if the given value is negative or
     *          if it is greater than 5000 (the default max value)
     */
    public ParticleLifeGene(int min)
    {
        // Make sure the given value is non-negative.
        // --------------------------------------------
        if(min < 0)
            throw new IllegalArgumentException("Negative numbers not allowed!");
            
        // Make sure that min isn't greater than 5000
        if(min > 5000)
            throw new IllegalArgumentException
                    ("Value for minimum life cannot be more than the default value for maximum life!");
        
        super(min, 5000);
    }


    /**
     * Constructs a new ParticleLifeGene with the specified maximum value
     * and a default minimum value (100) for the life.
     *
     * @param max The highest possible life that this ParticleLifeGene
     *                can represent.
     *
     * @throws IllegalArgumentException if the given value is less than 100
     *           (the default min value)
     */
    public ParticleLifeGene(int max)
    {
        // Make sure the given value isn't less than 100.
        // --------------------------------------------
        if(max < 100)
            throw new IllegalArgumentException
                    ("Value for maximum life cannot be less than the default value for minimum life!");

        super(100, max);
    }
    
    
    /**
     * Compares this Gene with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object. The given object
     * must be a ParticleLifeGene.
     *
     * @param   otherParticleLifeGene the Object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *              is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this Object.
     */
    public int compareTo(Object otherParticleLifeGene)
    {
        // If the other allele is null, we're bigger.
        // ------------------------------------------
        if(otherParticleLifeGene == null)
        {
            return 1;
        }

        // If our allele is null, then we're either the same as the given
        // ParticleLifeGene if its allele is also null, or less than it if
        // its allele is not null.
        // -------------------------------------------------------------
        if(super.m_value == null)
        {
            if (((ParticleLifeGene) otherParticleLifeGene).m_value == null)
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
            return super.m_value.compareTo(((ParticleLifeGene) otherParticleLifeGene).m_value);
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Determines if this ParticleLifeGene is equal to the given ParticleLifeGene.
     *
     * @return true if this ParticleLifeGene is equal to the given ParticleLifeGene,
     *         false otherwise.
     */
    public boolean equals(Object otherParticleLifeGene)
    {
        return otherParticleLifeGene instanceof otherParticleLifeGene && 
               compareTo(otherParticleLifeGene) == 0;
    }
    
    
    /**
     * Calculates the hash-code for this ParticleLifeGene.
     *
     * @return the hash-code of this ParticleLifeGene
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
     * Retrieves a string representation of this ParticleLifeGene's value that
     * may be useful for display purposes.
     *
     * @return a string representation of this ParticleLifeGene's value.
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
