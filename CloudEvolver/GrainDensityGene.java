//
//  GrainDensityGene.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/24/05.
//  Copyright 2005. All rights reserved.
//

import org.jgap.impl.IntegerGene;


/**
 * A gene used to represent grain density, measured in grains per second
 * an optional minimum and maximum number of grains may be provided
 */
public class GrainDensityGene extends IntegerGene
{
    
    /**
     * Constructs a new GrainDensityGene with default minimum (1) and
     * maximum (1000) number of grains.
     * Naturally you're limited by memory an processor restraints as
     * to the number of grains you can actually play per second.
     */
    public GrainDensityGene()
    {
        super(1, 1000);
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
     */
    public GrainDensityGene(int minGrains, int maxGrains)
    {
        super(minGrains, maxGrains);
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
        
        super(minGrains, 1000);
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
        
        
        super(1, maxGrains);
    }
    
    
    /**
     * Compares this Gene with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object. The given object
     * must be a GrainDensityGene.
     *
     * @param   otherGrainDensityGene the Object to be compared.
     *
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
        if(super.m_value == null)
        {
            if (((GrainDensityGene) otherGrainDensityGene).m_value == null)
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
            return super.m_value.compareTo(((GrainDensityGene) otherGrainDensityGene).m_value);
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
            throw e;
        }
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
     * Retrieves a string representation of this GrainDensityGene's value that
     * may be useful for display purposes.
     *
     * @return a string representation of this GrainDensityGene's value.
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

