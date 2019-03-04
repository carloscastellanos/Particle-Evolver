//
//  CloudEvolver.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/24/05.
//  Copyright (c) 2005. All rights reserved.
//

import org.jgap.Gene;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Genotype;
import org.jgap.FitnessFunction;
import org.jgap.impl.DefaultConfiguration;


public class CloudEvolver
{

    private Genotype population;
    
    /**
     * Initializes the cloud population
     *
     * @param atargetAmount
     *
     *
     * @throws Exception
     */
    public static void initCloudPopulation(int popSize)
    {
        // Start with a DefaultConfiguration, which comes setup with the
        // most common settings.
        // -------------------------------------------------------------
        Configuration conf = new DefaultConfiguration();
        
        // Set the fitness function we want to use, which is the
        // CloudEvolverFitnessFunction. We construct it with
        // the target grain parameters passed in to this method.
        // ---------------------------------------------------------
        FitnessFunction myFunc = new CloudEvolverFitnessFunction(targets);

        conf.setFitnessFunction(myFunc);
        
        // Now we need to set up our chromosome and pass it to our
        // Configuration object.  The chromosomes will have 5 genes
        // for five different grain parametrs (grain size, grain density,
        // synchronous/asynchronous, grain spatiality and grain frequency/pitch)
        // Weve created customized genes to represent these genes and their
        // values(alleles)
        Gene[] cloudGenes = new Gene[5];
        cloudGenes[0] = new GrainDensityGene();
        cloudGenes[1] = new GrainSizeGene();
        cloudGenes[2] = new GrainSyncGene();
        cloudGenes[3] = new GrainPanGene();
        cloudGenes[4] = new GrainPitchGene();
        
        Chromosome cloudChromosome = new Chromosome(cloudGenes);
        
        conf.setSampleChromosome(cloudChromosome);
        
        // Finally, we need to tell the Configuration object how many
        // Chromosomes we want in our population. The more Chromosomes,
        // the larger number of potential solutions (which is good for
        // finding the target), but the longer it will take to evolve
        // the population (which you might want).  I suggest keeping the
        // number small.
        // ------------------------------------------------------------
        conf.setPopulationSize(popSize);
        
        // Create random initial population of Chromosomes.
        // ------------------------------------------------
        population = Genotype.randomInitialGenotype(conf);
    }
    
    public evolveCloudPopulation(int rating)
    {
    
    }
}