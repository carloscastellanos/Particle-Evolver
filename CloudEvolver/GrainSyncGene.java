//
//  GrainSyncGene.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/26/05.
//  Copyright 2005. All rights reserved.
//

import org.jgap.impl.BooleanGene;

/**
 * A gene used to represent whether a cloud of grains is
 * synchronous or asynchronous
 */
public class GrainSyncGene extends BooleanGene
{
    
    public GrainSyncGene()
    {
        super();
    }


    /**
     * Compares this Gene with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object. The given object
     * must be a GrainSyncGene.
     *
     * @param   otherGrainSyncGene the Object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *              is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this Object.
     */
    public int compareTo(Object otherGrainSyncGene)
    {
        // If the other allele is null, we're bigger.
        // ------------------------------------------
        if(otherGrainSyncGene == null)
        {
            return 1;
        }

        // If our allele is null, then we're either the same as the given
        // GrainSyncGene if its allele is also null, or less than it if
        // its allele is not null.
        // -------------------------------------------------------------
        if(super.m_value == null)
        {
            if (((GrainSyncGene) otherGrainSyncGene).m_value == null)
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }

        // The Boolean class doesn't implement the Comparable interface, so
        // we have to do the comparison ourselves.
        // ----------------------------------------------------------------
        if(super.m_value.booleanValue() == false)
        {
            if(otherGrainSyncGene.m_value.booleanValue() == false)
            {
                // Both are false and therefore the same. Return zero.
                // ---------------------------------------------------
                return 0;
            }
            else
            {
                // This allele is false, but the other one is true. This
                // allele is the lesser.
                // -----------------------------------------------------
                return -1;
            }
        }
        else if(otherGrainSyncGene.m_value.booleanValue() == true)
        {
            // Both alleles are true and therefore the same. Return zero.
            // ----------------------------------------------------------
            return 0;
        }
        else
        {
            // This allele is true, but the other is false. This allele is
            // the greater.
            // -----------------------------------------------------------
            return 1;
        }
    }


    /**
     * Determines if this GrainSyncGene is equal to the given GrainSyncGene.
     *
     * @return true if this GrainSyncGene is equal to the given GrainSyncGene,
     *         false otherwise.
     */
    public boolean equals(Object otherGrainSizeGene)
    {
        return otherGrainSyncGene instanceof otherGrainSyncGene && 
               compareTo(otherGrainSyncGene) == 0;
    }
    
    
    /**
     * Calculates the hash-code for this GrainSyncGene.
     *
     * @return the hash-code of this GrainSyncGene
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
     * Retrieves a string representation of this GrainSyncGene's value that
     * may be useful for display purposes.
     *
     * @return a string representation of this GrainSyncGene's value.
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
