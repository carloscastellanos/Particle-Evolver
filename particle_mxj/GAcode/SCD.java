package ga;

import java.io.*;
import org.jgap.*;
import org.jgap.data.*;
import org.jgap.impl.*;
import org.jgap.xml.*;
import org.w3c.dom.*;
import java.util.*;
import com.cycling74.max.*;

/**
 * This class provides an implementation of a Particle system evolver
 *
 */

public class SCD extends MaxObject
{

    // The total number of times we'll let the population evolve.
    private static final int MAX_ALLOWED_EVOLUTIONS = 50;


    // contructor required for MXJ
    public SCD(Atom[] args)
    {
        declareInlets(new int[]{DataTypes.ALL});
        declareOutlets(new int[]{DataTypes.ALL});
        declareIO(1, 2);
        createInfoOutlet(false);
        setInletAssist(0, "(list) list of targets");
        setOutletAssist(0, "(list) list of alleles of current chromosome");
        setOutletAssist(1, "bang when done");
    }
 
    /**
     * Sets up genes and chromosomes for a particle system
     */
    public void setupParticleSystem(Map targets) throws Exception
    {
        // Start with a DefaultConfiguration, which comes setup with the
        // most common settings.
        // -------------------------------------------------------------

        Configuration conf = new DefaultConfiguration();

        conf.setPreservFittestIndividual(true);

        // Set the fitness function we want to use, which is our
        // SCDFitnessFunction. We construct it with the HashMap
        // of targets passed into this method.
        // ---------------------------------------------------------

        FitnessFunction myFunc = new SCDFitnessFunction(targets);

        conf.setFitnessFunction(myFunc);

        // Now we need to tell the Configuration object how we want our
        // Chromosomes to be setup. We do that by actually creating a
        // sample Chromosome and then setting it on the Configuration
        // object. Our Chromosomes will have 9 genes, (5 of which are
        // CompositeGenes) representing various parametrs of the particle
        // system.
        // --------------------------------------------------------------

        Gene[] particleGenes = new Gene[9];
        // easy stuff
        particleGenes[0] = new IntegerGene(100, 5000); // Life
        particleGenes[1] = new DoubleGene(1.0, 10000.0); // Speed
        particleGenes[2] = new IntegerGene(0, 100); // Drag
        particleGenes[3] = new DoubleGene(0.0, 0.99); // Path strength
        
        // now the composite genes
        // --- gravity ---
        CompositeGene gravityGene = new CompositeGene();
        Gene gravityXGene = new DoubleGene(-360.0, 360.0); // x coord of gravity vector
        Gene gravityYGene = new DoubleGene(-360.0, 360.0); // y coord of gravity vector
        Gene gravityZGene = new DoubleGene(-360.0, 360.0); // z coord of gravity vector
        gravityGene.addGene(gravityXGene);
        gravityGene.addGene(gravityYGene);
        gravityGene.addGene(gravityZGene);
        particleGenes[4] = gravityGene;
        
        // --- path ---
        CompositeGene pathGene = new CompositeGene();
        Gene pathAXGene = new DoubleGene(-360.0, 360.0); // x coord of path vector A
        Gene pathAYGene = new DoubleGene(-360.0, 360.0); // y coord of gravity vector A
        Gene pathAZGene = new DoubleGene(-360.0, 360.0); // z coord of gravity vector A
        Gene pathBXGene = new DoubleGene(-360.0, 360.0); // x coord of path vector B
        Gene pathBYGene = new DoubleGene(-360.0, 360.0); // y coord of gravity vector B
        Gene pathBZGene = new DoubleGene(-360.0, 360.0); // z coord of gravity vector B
        Gene pathCXGene = new DoubleGene(-360.0, 360.0); // x coord of path vector C
        Gene pathCYGene = new DoubleGene(-360.0, 360.0); // y coord of gravity vector C
        Gene pathCZGene = new DoubleGene(-360.0, 360.0); // z coord of gravity vector C
        pathGene.addGene(pathAXGene);
        pathGene.addGene(pathAYGene);
        pathGene.addGene(pathAZGene);
        pathGene.addGene(pathBXGene);
        pathGene.addGene(pathBYGene);
        pathGene.addGene(pathBZGene);
        pathGene.addGene(pathCXGene);
        pathGene.addGene(pathCYGene);
        pathGene.addGene(pathCZGene);
        particleGenes[5] = pathGene;
        
        // --- direction ---
        CompositeGene dirGene = new CompositeGene();
        Gene dirXGene = new DoubleGene(-360.0, 360.0); // x coord of direction vector
        Gene dirYGene = new DoubleGene(-360.0, 360.0); // y coord of direction vector
        Gene dirZGene = new DoubleGene(-360.0, 360.0); // z coord of direction vector
        dirGene.addGene(dirXGene);
        dirGene.addGene(dirYGene);
        dirGene.addGene(dirZGene);
        particleGenes[6] = dirGene;
             
        // --- size range ---
        CompositeGene sizeRangeGene = new CompositeGene();
        Gene sizeRangeStartGene = new DoubleGene(0.0, 10.0); // size  range start
        Gene sizeRangeEndGene = new DoubleGene(0.0, 10.0);   // size  range end
        sizeRangeGene.addGene(sizeRangeStartGene);
        sizeRangeGene.addGene(sizeRangeEndGene);
        particleGenes[7] = sizeRangeGene;
        
        // --- color range ---
        Gene colorRangeGene = new CompositeGene();
        Gene colorRangeStartRedGene = new IntegerGene(0, 255);   // color range start (red)
        Gene colorRangeStartGreenGene = new IntegerGene(0, 255); // color range start (green)
        Gene colorRangeStartBlueGene = new IntegerGene(0, 255);  // color range start (blue)
        Gene colorRangeEndRedGene = new IntegerGene(0, 255);     // color range end (red)
        Gene colorRangeEndGreenGene = new IntegerGene(0, 255);   // color range end (green)
        Gene colorRangeEndBlueGene = new IntegerGene(0, 255);    // color range end (blue)
        particleGenes[8] = colorRangeGene;
        
        
        // set up the chromosome
        Chromosome particleChromosome = new Chromosome(particleGenes);

        conf.setSampleChromosome(particleChromosome);

        // Finally, we need to tell the Configuration object how many
        // Chromosomes we want in our population. The more Chromosomes,
        // the larger number of potential solutions (which is good for
        // finding the answer), but the longer it will take to evolve
        // the population (which could be seen as bad). We'll just set
        // the population size to 500 here.
        // ------------------------------------------------------------

        conf.setPopulationSize(500);

        // Create random initial population of Chromosomes.
        // ------------------------------------------------

        Genotype population;
        population = Genotype.randomInitialGenotype(conf);

/*
        try {
        Document doc = XMLManager.readFile(new File("SCD_JGAP.xml"));
        population = XMLManager.getGenotypeFromDocument(conf, doc);
        }

        catch (FileNotFoundException fex) {
        population = Genotype.randomInitialGenotype(conf);
        }
*/

        // rank the members of the population?
        


        // Evolve the population. Since we don't know what the best answer
        // is going to be, we just evolve the max number of times.
        // ---------------------------------------------------------------
        
        population.evolve(MAX_ALLOWED_EVOLUTIONS);
        //population.evolve(1);


        // Save progress to file. A new run of this example will then be able to
        // resume where it stopped before!

        // represent Genotype as tree with elements Chromomes and Genes

        DataTreeBuilder builder = DataTreeBuilder.getInstance();

        IDataCreators doc2 = builder.representGenotypeAsDocument(population);

        // create XML document from generated tree

        XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();

        Document xmlDoc = (Document) docbuilder.buildDocument(doc2);

        XMLManager.writeFile(xmlDoc, new File("SCD_JGAP.xml"));
        

        // Display the best solution we found.
        // -----------------------------------
        Chromosome bestSolutionSoFar = population.getFittestChromosome();
    
    /*
        System.out.println("The best solution has a fitness value of " + bestSolutionSoFar.getFitnessValue());

        System.out.println("It contained the following: ");

        System.out.println("\t" + 
		SCDFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 0) +
		" quarters.");

        System.out.println("\t" + SCDFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 1) +
		" dimes.");

        System.out.println("\t" + 
		SCDFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 2) + 
		" nickels.");

        System.out.println("\t" + 
		SCDFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 3) + 
		" pennies.");

        System.out.println("For a total of " + 
		SCDFitnessFunction.amountOfChange(bestSolutionSoFar) + 
		" cents in " + 
		SCDFitnessFunction.getTotalNumberOfCoins(bestSolutionSoFar) + " coins.");

	outlet(0,"quarters",  new Atom[]{Atom.newAtom(SCDFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 0))});
	outlet(0,"dimes",  new Atom[]{Atom.newAtom(SCDFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 1))});
	outlet(0,"nickels", new Atom[]{Atom.newAtom(SCDFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 2))});
	outlet(0,"pennies",  new Atom[]{Atom.newAtom(SCDFitnessFunction.getNumberOfCoinsAtGene(bestSolutionSoFar, 3))});
	outlet(0,"amountOfChange",  new Atom[]{Atom.newAtom(SCDFitnessFunction.amountOfChange(bestSolutionSoFar))});
	outlet(0,"numberOfCoins",  new Atom[]{Atom.newAtom(SCDFitnessFunction.getTotalNumberOfCoins(bestSolutionSoFar))});
	outlet(0,"fitnessValue",  new Atom[]{Atom.newAtom(bestSolutionSoFar.getFitnessValue())});
*/
    }
    
    
    /*
    Map targets = new HashMap();
    targets.put("life", new Integer(1000));
    targets.put("speed", new Double(10.0));
    targets.put("drag", new Integer(1));
    targets.put("pathStrength", new Double(0.99));
    
    List gravityList = new ArrayList(3);
    gravityList.add(new Double(1.0));
    gravityList.add(new Double(1.0));
    gravityList.add(new Double(1.0));
    targets.put("gravity",gravityList);
    
    List pathList1 = new ArrayList(3);
    pathList1.add(new Double(1.0));
    pathList1.add(new Double(1.0));
    pathList1.add(new Double(1.0));
    List pathList2 = new ArrayList(3);
    pathList1.add(new Double(1.0));
    pathList1.add(new Double(1.0));
    pathList1.add(new Double(1.0));
    List pathList3 = new ArrayList(3);
    pathList1.add(new Double(1.0));
    pathList1.add(new Double(1.0));
    pathList1.add(new Double(1.0));
    List fullPath = new ArrayList(3);
    fullPath.add(pathList1);
    fullPath.add(pathList2);
    fullPath.add(pathList3);
    targets.put("path",fullPath);
    
    
    */

    /**
     * This is where we get the list of targets from Max
     *
     * @param atomArray The list of targets from Max
     *
     */

    public void list(Atom[] atomArray)
    {
        // convert a Max message containing the targets to a HashMap
        String atoms = atomArray.toString();
        System.out.println(atoms);
    
    }


}



