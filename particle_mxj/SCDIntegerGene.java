/*Using the supergenes with JGap

For better understanding of this chapter, we recommend to read about the MinimizeMakeChange example first.

In JGap, the supergene support is implemented in package org.jgap.supergenes. The simpliest way to create a supergene is to derive it from the abstractSupergene class, overriding isValid() method. For example, if we need a supergene, holding the two IntegerGenes (nickels and pennies) and ensuring condition nickels mod 2 = pennies mod 2:
*/

package ga;

import org.jgap.impl.IntegerGene;

import org.jgap.supergenes.abstractSupergene;

import org.jgap.Gene;

 

/** Supergene to hold quarters and nickels. Valid if the number of

 * nickels and pennies is either both odd or both even. */

 

public class NickelsPenniesSupergene extends abstractSupergene {

    /* It is important to provide these two constructors: */

    public NickelsPenniesSupergene() {}

    public NickelsPenniesSupergene( Gene[] a_genes )

     {

         super(a_genes);

     }

 

    /* Now just check nickels mod 2 = pennies mod 2: */

    public boolean isValid(Gene [] genes, Supergene s)

    {

         IntegerGene nickels = (IntegerGene)  genes[0];

         IntegerGene pennies  = (IntegerGene) genes[1];

         boolean valid = nickels.intValue() % 2 == pennies.intValue() % 2;

         return valid;

    }

}

/*Now, the sample gene array, consisting of one supergene and two ordinary genes for quarters and dimes, is created in the following way:*/

        Gene[] sampleGenes = new Gene[3];

        sampleGenes[DIMES]    = getDimesGene ();

        sampleGenes[QUARTERS] = getQuartersGene ();

 

        sampleGenes[2] = new NickelsPenniesSupergene(

            new Gene[] {

            getNickelsGene (),

            getPenniesGene (),

  }

//Compare this with the non-supergene version:

        Gene[] sampleGenes = new Gene[4];

        sampleGenes[DIMES] = getDimesGene(); 

        sampleGenes[NICKELS] = getNickelsGene(); 

        sampleGenes[QUARTERS] = getQuartersGene(); 

        sampleGenes[PENNIES] = getPenniesGene(); 

/*It is trivial to create a chromosome from the gene array and to get a solution of the task (see example class org.jgap.supergenes.SupergeneSampleApplicationTest included with the tests in the JGAP distribution).*/