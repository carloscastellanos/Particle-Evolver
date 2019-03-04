//
//  ParticleDragGene.java
//  SCD
//
//  Created by Carlos Castellanos on 2/28/05.
//  Copyright 2005. All rights reserved.
//

package ga;

import org.jgap.impl.IntegerGene;


/**
 * A gene used to represent the drag on a particle.  Indicates the percentage
 * of each particle's velocity that is lost in each simulation step. Has a range
 * of 0 (no velocity lost) to 100 (all velocity lost and the particle stops moving).
 * An optional minimum and maximum may be provided.
 *
 * Since these values represent percentages, any value greater than 100 will be
 * interpreted as 100 and any value less than 0 will be interpreted as 0 by the
 * constructors.
 *
 */
public class ParticleDragGene extends IntegerGene {

    /**
     * Constructs a new ParticleDragGene with default minimum (0) and
     * maximum (100) values for the drag.
     */
    public ParticleDragGene()
    {
        super(0, 100);
    }


    /**
     * Constructs a new ParticleDragGene with a constraint on the
     * minimum and maximum values.
     *
     * @param min The lowest possible life that this ParticleDragGene
     *                can represent.
     *
     * @param max The highest possible life that this ParticleDragGene
     *                can represent.
     *
     * @throws IllegalArgumentException if min is greater than max
     *          
     */
    public ParticleDragGene(int min, int max)
    {
            
        // Make sure that min isn't greater than max
        if(min > max)
            throw new IllegalArgumentException
                    ("Value for minimum speed cannot be more than the value for maximum speed!");
                    
        if(min < 0)
            min = 0;
        if(max < 0)
            max = 0;
        
        if(min > 100)
            min = 100;
        if(max > 100)
            max = 100;
        
        super(min, max);
    }
    
    
    /**
     * Constructs a new ParticleDragGene with the specified minimum value
     * and a default maximum value (100) for drag.
     *
     * @param min The lowest possible drag that this ParticleDragGene
     *                can represent.
     *
     */
    public ParticleDragGene(int min)
    {
        if(min < 0)
            min = 0;
        if(min > 100)
            min = 100;

        super(min, 100);
    }


    /**
     * Constructs a new ParticleDragGene with the specified maximum value
     * and a default minimum value (0) for drag.
     *
     * @param max The highest possible drag that this ParticleDragGene
     *                can represent.
     *
     */
    public ParticleDragGene(int max)
    {
        if(max < 0)
            max = 0;
        if(max > 100)
            max = 100;

        super(0, max);
    }
    
    
    /**
     * Compares this Gene with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object. The given object
     * must be a ParticleDragGene.
     *
     * @param   otherParticleDragGene the Object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *              is less than, equal to, or greater than the specified object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this Object.
     */
    public int compareTo(Object otherParticleDragGene)
    {
        // If the other allele is null, we're bigger.
        // ------------------------------------------
        if(otherParticleDragGene == null)
        {
            return 1;
        }

        // If our allele is null, then we're either the same as the given
        // ParticleDragGene if its allele is also null, or less than it if
        // its allele is not null.
        // -------------------------------------------------------------
        if(super.m_value == null)
        {
            if (((ParticleDragGene) otherParticleDragGene).m_value == null)
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
            return super.m_value.compareTo(((ParticleDragGene) otherParticleDragGene).m_value);
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * Determines if this ParticleDragGene is equal to the given ParticleDragGene.
     *
     * @return true if this ParticleDragGene is equal to the given ParticleDragGene,
     *         false otherwise.
     */
    public boolean equals(Object otherParticleDragGene)
    {
        return otherParticleDragGene instanceof otherParticleDragGene && 
               compareTo(otherParticleDragGene) == 0;
    }
    
    
    /**
     * Calculates the hash-code for this ParticleDragGene.
     *
     * @return the hash-code of this ParticleDragGene
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
     * Retrieves a string representation of this ParticleDragGene's value that
     * may be useful for display purposes.
     *
     * @return a string representation of this ParticleDragGene's value.
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
