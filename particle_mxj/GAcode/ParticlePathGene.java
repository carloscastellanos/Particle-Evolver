//
//  BaseArrayGene.java
//  SCD
//
//  Created by Carlos Castellanos on 3/1/05.
//  Copyright 2005. All rights reserved.
//

package ga;

import org.jgap.Gene;
import org.jgap.BaseGene;
import org.jgap.IGeneConstraintChecker;
import java.util.Collection;

/**
 * Sometimes a single allele needs to be a collection of related values
 * (e.g. - the x, y, z coords of a vector).  This class accomodates that
 */
public class ParticlePathGene extends CompositeGene {

    /**
     * References the internal value (allele) of this Gene
     * E.g., for DoubleGene this is of type Double
     */
    protected Collection m_value = null;
    
    /**
     * Optional helper class for checking if a given allele value to be set
     * is valid. If not the allele value may not be set for the gene!
     */
    private IGeneConstraintChecker m_geneAlleleChecker;
    
    /**
     * Compares to objects by first casting them into their expected type
     * (e.g. Integer for IntegerGene) and then calling the compareTo-method
     * of the casted type.
     * @param o1 first object to be compared, always is not null
     * @param o2 second object to be compared, always is not null
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the object provided for comparison.
     *
     */
    protected abstract int compareToNative(Object o1, Object o2);
    
    public void setAllele(Collection a_newValue)
    {
        if (m_geneAlleleChecker != null) {
            if (!m_geneAlleleChecker.verify(this, a_newValue)) {
                return;
            }
        }
        
        m_value = a_newValue;
        
        // If the value isn't between the upper and lower bounds of this
        // Gene, map it to a value within those bounds.
        // -------------------------------------------------------------
        mapValueToWithinBounds();
    }

    /**
     * Maps the value of this ArrayGene to within the bounds specified by
     * the m_upperBounds and m_lowerBounds instance variables. The value's
     * relative position within the integer range will be preserved within the
     * bounds range (in other words, if the value is about halfway between the
     * integer max and min, then the resulting value will be about halfway
     * between the upper bounds and lower bounds). If the value is null or
     * is already within the bounds, it will be left unchanged.
     *
     */
    protected abstract void mapValueToWithinBounds();
    
    protected Collection getInternalValue()
    {
        return m_value;
    }
    
    
    /**
     * Sets the constraint checker to be used for this gene whenever method
     * setAllele(Object a_newValue) is called
     *
     * @param a_constraintChecker the constraint checker to be set
     */
    public void setConstraintChecker(IGeneConstraintChecker a_constraintChecker)
    {
        m_geneAlleleChecker = a_constraintChecker;
    }
  
  
    /**
     * @return IGeneConstraintChecker the constraint checker to be used whenever
     * method setAllele(Object a_newValue) is called
     *
     */
    public IGeneConstraintChecker getConstraintChecker()
    {
        return m_geneAlleleChecker;
    }


}
