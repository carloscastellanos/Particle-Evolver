//
//  GrainSizeGene.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/25/05.
//  Copyright 2005. All rights reserved.
//

import org.jgap.impl.IntegerGene;

/**
 * A gene used to represent grain size (duration), measured in milliseconds
 * an optional minimum and maximum number for grain size may be provided
 */
public class GrainSizeGene extends IntegerGene
{

    /**
     * Constructs a new GrainSizeGene with default minimum (1) and
     * maximum (100) values for grain size (in ms).
     */
    public GrainSizeGene()
    {
        super(1, 100);
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
     */
    public GrainSizeGene(int minSize, int maxSize)
    {
        super(minSize, maxSize);
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
        
        super(minSize, 100);
    }


    /**
     * Constructs a new GrainSizeGene with the specified maximum value
     * and a default minimum value (1) for the grain size.
     *
     * @param maxSize The smallest possible grain size (in ms) that this GrainSizeGene
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

        super(1, maxSize);
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
        if(super.m_value == null)
        {
            if (((GrainSizeGene) otherGrainSizeGene).m_value == null)
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
            return super.m_value.compareTo(((GrainSizeGene) otherGrainSizeGene).m_value);
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
            throw e;
        }
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
     * Retrieves a string representation of this GrainSizeGene's value that
     * may be useful for display purposes.
     *
     * @return a string representation of this GrainSizeGene's value.
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
