package ga;

//import org.w3c.dom.*;
//import org.jgap.xml.*;
//import org.jgap.data.*;
//import java.io.*;
import org.jgap.*;
import org.jgap.impl.*;
import java.util.HashMap;
import java.util.ArrayList;
import com.cycling74.max.*;

/**
 * This class provides an implementation of a Particle system evolver
 *
 */

public class ParticleEvolver extends MaxObject
{
    // targets
    private HashMap targets;    
    
    private Genotype population;            // population
    private Configuration conf;             // configuration
    private FitnessFunction myFunc;         // fitness function
    private Gene[] particleGenes;           // genes
    private Chromosome particleChromosome;  // chromosome
    private int popSize = 10;                   // population size
    
    // The total number of times we'll let the population evolve.
    private static final int MAX_ALLOWED_EVOLUTIONS = 10;
    private int evoCount;

    // contructor required for MXJ
    public ParticleEvolver(Atom[] args)
    {
        declareInlets(new int[]{DataTypes.ALL});
        declareOutlets(new int[]{DataTypes.ALL});
        declareIO(1, 3);
        createInfoOutlet(false);
        setInletAssist(0, "(list) list of targets");
        setOutletAssist(0, "(list) list of alleles of current chromosome, after every evolution");
        setOutletAssist(1, "(list) list of alleles of current chromosome, every 10 evolutions");
        setOutletAssist(2, "bang when done");
        
        configure();
    }

    /**
     * Configures a template chromosome
     */
    private void configure()
    {
        particleGenes = new Gene[12];
        
        particleGenes[0] = new IntegerGene(100, 5000); // Life
        particleGenes[1] = new DoubleGene(1.0, 10000.0); // Speed
        particleGenes[2] = new IntegerGene(0, 100); // Drag
        particleGenes[3] = new DoubleGene(0.0, 0.99); // Path strength
        
        // these are composite genes
        // --- gravity ---
        CompositeGene gravityGene = new CompositeGene();
        DoubleGene gravityXGene = new DoubleGene(-100.0, 100.0); // x coord of gravity vector
        DoubleGene gravityYGene = new DoubleGene(-100.0, 100.0); // y coord of gravity vector
        DoubleGene gravityZGene = new DoubleGene(-100.0, 100.0); // z coord of gravity vector
        gravityGene.addGene(gravityXGene);
        gravityGene.addGene(gravityYGene);
        gravityGene.addGene(gravityZGene);
        particleGenes[4] = gravityGene;
        
        // --- path ---
        CompositeGene pathGene = new CompositeGene();
        DoubleGene pathAXGene = new DoubleGene(-100.0, 100.0); // x coord of path vector A
        DoubleGene pathAYGene = new DoubleGene(-100.0, 100.0); // y coord of gravity vector A
        DoubleGene pathAZGene = new DoubleGene(-100.0, 100.0); // z coord of gravity vector A
        DoubleGene pathBXGene = new DoubleGene(-100.0, 100.0); // x coord of path vector B
        DoubleGene pathBYGene = new DoubleGene(-100.0, 100.0); // y coord of gravity vector B
        DoubleGene pathBZGene = new DoubleGene(-100.0, 100.0); // z coord of gravity vector B
        DoubleGene pathCXGene = new DoubleGene(-100.0, 100.0); // x coord of path vector C
        DoubleGene pathCYGene = new DoubleGene(-100.0, 100.0); // y coord of gravity vector C
        DoubleGene pathCZGene = new DoubleGene(-100.0, 100.0); // z coord of gravity vector C
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
        DoubleGene dirXGene = new DoubleGene(-100.0, 100.0); // x coord of direction vector
        DoubleGene dirYGene = new DoubleGene(-100.0, 100.0); // y coord of direction vector
        DoubleGene dirZGene = new DoubleGene(-100.0, 100.0); // z coord of direction vector
        dirGene.addGene(dirXGene);
        dirGene.addGene(dirYGene);
        dirGene.addGene(dirZGene);
        particleGenes[6] = dirGene;
             
        // --- size range ---
        CompositeGene sizeRangeGene = new CompositeGene();
        DoubleGene sizeRangeStartGene = new DoubleGene(0.0, 10.0); // size  range start
        DoubleGene sizeRangeEndGene = new DoubleGene(0.0, 10.0);   // size  range end
        sizeRangeGene.addGene(sizeRangeStartGene);
        sizeRangeGene.addGene(sizeRangeEndGene);
        particleGenes[7] = sizeRangeGene;
        
        // --- color range ---
        CompositeGene colorRangeGene = new CompositeGene();
        IntegerGene colorRangeStartRedGene = new IntegerGene(0, 255);   // color range start (red)
        IntegerGene colorRangeStartGreenGene = new IntegerGene(0, 255); // color range start (green)
        IntegerGene colorRangeStartBlueGene = new IntegerGene(0, 255);  // color range start (blue)
        IntegerGene colorRangeEndRedGene = new IntegerGene(0, 255);     // color range end (red)
        IntegerGene colorRangeEndGreenGene = new IntegerGene(0, 255);   // color range end (green)
        IntegerGene colorRangeEndBlueGene = new IntegerGene(0, 255);    // color range end (blue)
        colorRangeGene.addGene(colorRangeStartRedGene);
        colorRangeGene.addGene(colorRangeStartGreenGene);
        colorRangeGene.addGene(colorRangeStartBlueGene);
        colorRangeGene.addGene(colorRangeEndRedGene);
        colorRangeGene.addGene(colorRangeEndGreenGene);
        colorRangeGene.addGene(colorRangeEndBlueGene);
        particleGenes[8] = colorRangeGene;
        
        // --- blend range ---
        CompositeGene blendRangeGene = new CompositeGene();
        IntegerGene blendRangeStartGene = new IntegerGene(0, 100); // blend  range start
        IntegerGene blendRangeEndGene = new IntegerGene(0, 100);   // blend  range end
        blendRangeGene.addGene(blendRangeStartGene);
        blendRangeGene.addGene(blendRangeEndGene);
        particleGenes[9] = blendRangeGene;
        
        particleGenes[10] = new DoubleGene(0.0, 180.0); // angle
        particleGenes[11] = new IntegerGene(1, 1000); // number of particles
        
        
        // set up the chromosome
        particleChromosome = new Chromosome(particleGenes);
    }


    /**
     * Sets up genes and chromosomes for a particle system
     */
    private void setupParticleSystem(HashMap targets) throws Exception
    {
        // Start with a DefaultConfiguration, which comes setup with the
        // most common settings.
        // -------------------------------------------------------------

        conf = new ParticleConfig();

        conf.setPreservFittestIndividual(true);

        // Set the fitness function we want to use, which is our
        // ParticleEvolverFitnessFunction. We construct it with the HashMap
        // of targets passed into this method.
        // ---------------------------------------------------------

        myFunc = new ParticleEvolverFitnessFunction(targets);

        conf.setFitnessFunction(myFunc);

        // Now we need to tell the Configuration object how we want our
        // Chromosomes to be setup. We do that by actually creating a
        // sample Chromosome and then setting it on the Configuration
        // object. Our Chromosomes will have 9 genes, (5 of which are
        // CompositeGenes) representing various parametrs of the particle
        // system.
        // --------------------------------------------------------------
        
        conf.setSampleChromosome(particleChromosome);

        // Finally, we need to tell the Configuration object how many
        // Chromosomes we want in our population. The more Chromosomes,
        // the larger number of potential solutions (which is good for
        // finding the answer), but the longer it will take to evolve
        // the population (which could be seen as bad). We'll just set
        // the population size to 10 here.
        // ------------------------------------------------------------

        conf.setPopulationSize(popSize);

        // Create random initial population of Chromosomes.
        // ------------------------------------------------

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

        // Evolve the population. Since we don't know what the best answer
        // is going to be, we just evolve the max number of times.
        // ---------------------------------------------------------------
        
        /*
        for(int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++)
        {
            population.evolve();
            
            // for director
            outputFittest(population, 0);
        }
        */
        evoCount = 0;
        population.evolve(1);
        evoCount++;
        // for director
        outputFittest(population, 0);

        // Save progress to file. A new run of this example will then be able to
        // resume where it stopped before!

        // represent Genotype as tree with elements Chromomes and Genes
        /*
        DataTreeBuilder builder = DataTreeBuilder.getInstance();
        IDataCreators doc2 = builder.representGenotypeAsDocument(population);

        // create XML document from generated tree
        XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
        Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
        XMLManager.writeFile(xmlDoc, new File("particle_JGAP.xml"));
        */
        // Display the best solution we found.
        // -----------------------------------
        //Chromosome bestSolutionSoFar = population.getFittestChromosome();
    }
    

    /**
     * This is where we get the list of targets from Max
     *
     * @param atomArray The list of targets from Max
     *
     */
    public void list(Atom[] atomArray)
    {
        String[] atoms;
        // convert a Max message containing the targets to a HashMap
        atoms = Atom.toString(atomArray);
        
        /*
        for(int i = 0; i < atoms.length; i++)
            System.out.println(atoms[i]);
        */
            
        // put the targets String array into a HashMap
        targets = setupTargets(atoms);
               
        try {
            setupParticleSystem(targets);
        } catch(Exception e) {
            System.out.println("Error setting up particle system. " + e.toString());
            e.printStackTrace();
        }
    }


    private HashMap setupTargets(String[] targetsArray)
    {
        HashMap targs = new HashMap(targetsArray.length);
        
        // --- life, speed, drag, pathStrength ---
        targs.put("life", new Integer(Integer.parseInt(targetsArray[0])));
        targs.put("speed", new Double(Double.parseDouble(targetsArray[1])));
        targs.put("drag", new Integer(Integer.parseInt(targetsArray[2])));
        targs.put("pathStrength", new Double(Double.parseDouble(targetsArray[3])));
    
        // -- gravity ---
        ArrayList gravityList = new ArrayList(3);
        gravityList.add(new Double(Double.parseDouble(targetsArray[4])));
        gravityList.add(new Double(Double.parseDouble(targetsArray[5])));
        gravityList.add(new Double(Double.parseDouble(targetsArray[6])));
        targs.put("gravity",gravityList);

        // --- path ---
        ArrayList pathList = new ArrayList(9);
        pathList.add(new Double(Double.parseDouble(targetsArray[7])));
        pathList.add(new Double(Double.parseDouble(targetsArray[8])));
        pathList.add(new Double(Double.parseDouble(targetsArray[9])));
        pathList.add(new Double(Double.parseDouble(targetsArray[10])));
        pathList.add(new Double(Double.parseDouble(targetsArray[11])));
        pathList.add(new Double(Double.parseDouble(targetsArray[12])));
        pathList.add(new Double(Double.parseDouble(targetsArray[13])));
        pathList.add(new Double(Double.parseDouble(targetsArray[14])));
        pathList.add(new Double(Double.parseDouble(targetsArray[15])));
        targs.put("path", pathList);
        
        // --- direction ---
        ArrayList dirList = new ArrayList(3);
        dirList.add(new Double(Double.parseDouble(targetsArray[16])));
        dirList.add(new Double(Double.parseDouble(targetsArray[17])));
        dirList.add(new Double(Double.parseDouble(targetsArray[18])));
        targs.put("direction", dirList);
        
        // --- size range ---
        ArrayList sizeRangeList = new ArrayList(2);
        sizeRangeList.add(new Double(Double.parseDouble(targetsArray[19])));
        sizeRangeList.add(new Double(Double.parseDouble(targetsArray[20])));
        targs.put("sizeRange", sizeRangeList);
        
        // --- color range ---
        ArrayList colorRangeList = new ArrayList(6);
        colorRangeList.add(new Integer(Integer.parseInt(targetsArray[21])));
        colorRangeList.add(new Integer(Integer.parseInt(targetsArray[22])));
        colorRangeList.add(new Integer(Integer.parseInt(targetsArray[23])));
        colorRangeList.add(new Integer(Integer.parseInt(targetsArray[24])));
        colorRangeList.add(new Integer(Integer.parseInt(targetsArray[25])));
        colorRangeList.add(new Integer(Integer.parseInt(targetsArray[26])));
        targs.put("colorRange", colorRangeList);
        
        // --- blend range ---
        ArrayList blendRangeList = new ArrayList(2);
        blendRangeList.add(new Integer(Integer.parseInt(targetsArray[27])));
        blendRangeList.add(new Integer(Integer.parseInt(targetsArray[28])));
        targs.put("blendRange", blendRangeList);
        
        // angle
        targs.put("angle", new Double(Double.parseDouble(targetsArray[29])));
        
        // number of particles
        targs.put("numP", new Integer(Integer.parseInt(targetsArray[30])));
        
        
        return targs;
    }


    /**
     * Outputs the values of the fittes chromosome
     *
     */
    private void outputFittest(Genotype pop, int whichOut)
    {
        // Display the best solution we found.
        // -----------------------------------
        Chromosome bestChrom = pop.getFittestChromosome();
        Gene[] bestGenes = bestChrom.getGenes();
        
        /*
        * gene values (alleles)
        */
        int life = ((IntegerGene) bestGenes[0]).intValue();              // life
        double speed = ((DoubleGene) bestGenes[1]).doubleValue();        // speed
        int drag = ((IntegerGene) bestGenes[2]).intValue();              // drag
        double pathStrength = ((DoubleGene) bestGenes[3]).doubleValue(); // pathStrength
        
        // --- composite genes --- //
        CompositeGene cgGrav = (CompositeGene) bestGenes[4]; // // // // //
        DoubleGene dgx = (DoubleGene) cgGrav.geneAt(0);      // // // // //
        double gravityX = dgx.doubleValue();                 // // // // //
        DoubleGene dgy = (DoubleGene) cgGrav.geneAt(1);      // gravity  //
        double gravityY = dgy.doubleValue();                 // // // // //
        DoubleGene dgz = (DoubleGene) cgGrav.geneAt(2);      // // // // //
        double gravityZ = dgz.doubleValue();                 // // // // //
        double[] gravity = {gravityX, gravityY, gravityZ};
		
        CompositeGene cgPath = (CompositeGene) bestGenes[5]; // // // // //
        DoubleGene dgpax = (DoubleGene) cgPath.geneAt(0);    // // // // //
        double pathAX = dgpax.doubleValue();                 // // // // //
        DoubleGene dgpay = (DoubleGene) cgPath.geneAt(1);    // // // // //
        double pathAY = dgpay.doubleValue();                 // // // // //
        DoubleGene dgpaz = (DoubleGene) cgPath.geneAt(2);    // // // // //
        double pathAZ = dgpaz.doubleValue();                 // // // // //
        DoubleGene dgpbx = (DoubleGene) cgPath.geneAt(3);    // // // // //
        double pathBX = dgpbx.doubleValue();                 // // // // //
        DoubleGene dgpby = (DoubleGene) cgPath.geneAt(4);    //   path	 //
        double pathBY = dgpby.doubleValue();                 // // // // //
        DoubleGene dgpbz = (DoubleGene) cgPath.geneAt(5);    // // // // //
        double pathBZ = dgpbz.doubleValue();                 // // // // //
        DoubleGene dgpcx = (DoubleGene) cgPath.geneAt(6);    // // // // //
        double pathCX = dgpcx.doubleValue();                 // // // // //
        DoubleGene dgpcy = (DoubleGene) cgPath.geneAt(7);    // // // // //
        double pathCY = dgpcy.doubleValue();                 // // // // //
        DoubleGene dgpcz = (DoubleGene) cgPath.geneAt(8);    // // // // //
        double pathCZ = dgpcz.doubleValue();                 // // // // //
        double[] pathA = {pathAX, pathAY, pathAZ};
        double[] pathB = {pathBX, pathBY, pathBZ};
        double[] pathC = {pathCX, pathCY, pathCZ};
        
        CompositeGene cgDir = (CompositeGene) bestGenes[6]; // // // // //
        DoubleGene dgdx = (DoubleGene) cgDir.geneAt(0);     // // // // //
        double dirX = dgdx.doubleValue();                   // // // // //
        DoubleGene dgdy = (DoubleGene) cgDir.geneAt(1);     // direction //
        double dirY = dgdy.doubleValue();                   // // // // //
        DoubleGene dgdz = (DoubleGene) cgDir.geneAt(2);     // // // // //
        double dirZ = dgdz.doubleValue();                   // // // // //
        double[] direction = {dirX, dirY, dirZ};
        
        CompositeGene cgSize = (CompositeGene) bestGenes[7]; // // // // //
        DoubleGene dgsb = (DoubleGene) cgSize.geneAt(0);     // // // // //
        double sizeBegin = dgsb.doubleValue();               // sizeRange //
        DoubleGene dgse = (DoubleGene) cgSize.geneAt(1);     // // // // //
        double sizeEnd = dgse.doubleValue();                 // // // // //
        double[] sizeRange = {sizeBegin, sizeEnd};
        
        CompositeGene cgColor = (CompositeGene) bestGenes[8]; // // // // // //
        IntegerGene igrb = (IntegerGene) cgColor.geneAt(0);   // // // // // //
        int redBegin = igrb.intValue();                       // // // // // //
        IntegerGene iggb = (IntegerGene) cgColor.geneAt(1);   // // // // // //
        int greenBegin = iggb.intValue();                     // // // // // //
        IntegerGene igbb = (IntegerGene) cgColor.geneAt(2);   // // // // // //
        int blueBegin = igbb.intValue();                      // colorRange  //
        IntegerGene igre = (IntegerGene) cgColor.geneAt(3);   // // // // // //
        int redEnd = igre.intValue();                         // // // // // //
        IntegerGene igge = (IntegerGene) cgColor.geneAt(4);   // // // // // //
        int greenEnd = igge.intValue();                       // // // // // //
        IntegerGene igbe = (IntegerGene) cgColor.geneAt(5);   // // // // // //
        int blueEnd = igbe.intValue();                        // // // // // //
        int[] colorRangeBegin = {redBegin, greenBegin, blueBegin};
        int[] colorRangeEnd = {redEnd, greenEnd, blueEnd};

        CompositeGene cgBlend = (CompositeGene) bestGenes[9]; // // // // // //
        IntegerGene dgbb = (IntegerGene) cgBlend.geneAt(0);     // // // // // //
        int blendBegin = dgbb.intValue();               //  blendRange  //
        IntegerGene dgbe = (IntegerGene) cgBlend.geneAt(1);      // // // // // //
        int blendEnd = dgbe.intValue();                 // // // // // //
        int[] blendRange = {blendBegin, blendEnd};
  
        double angle = ((DoubleGene) bestGenes[10]).doubleValue(); // angle
        int numP = ((IntegerGene) bestGenes[11]).intValue();       // number of particles
        
        // send it out!
        outlet(whichOut, new Atom[]{ Atom.newAtom(life), Atom.newAtom(speed), Atom.newAtom(drag),
                Atom.newAtom(pathStrength), Atom.newAtom(gravity[0]), Atom.newAtom(gravity[1]), 
                Atom.newAtom(gravity[2]), Atom.newAtom(pathA[0]), Atom.newAtom(pathA[1]), Atom.newAtom(pathA[2]), 
                Atom.newAtom(pathB[0]), Atom.newAtom(pathB[1]), Atom.newAtom(pathB[2]), Atom.newAtom(pathC[0]),
                Atom.newAtom(pathC[1]), Atom.newAtom(pathC[2]), Atom.newAtom(direction[0]), Atom.newAtom(direction[1]), 
                Atom.newAtom(direction[2]), Atom.newAtom(sizeRange[0]), Atom.newAtom(sizeRange[1]), 
                Atom.newAtom(colorRangeBegin[0]), Atom.newAtom(colorRangeBegin[1]), Atom.newAtom(colorRangeBegin[2]),
                Atom.newAtom(colorRangeEnd[0]), Atom.newAtom(colorRangeEnd[1]), Atom.newAtom(colorRangeEnd[2]), 
                Atom.newAtom(blendRange[0]), Atom.newAtom(blendRange[1]), Atom.newAtom(angle), Atom.newAtom(numP) });
    }


    /**
     */
    public void evolveAgain()
    {
        if(population != null)
        {
            population.evolve(1);
            // for director
            outputFittest(population, 0);
            evoCount++;
            
            if (evoCount >= MAX_ALLOWED_EVOLUTIONS)
            {
                evoCount = 0;
                // for everyone else
                outputFittest(population, 1);
            }
            
            // bang when done
            outletBang(2);
            
        } else {
            post("*** Error!  You must first initialize a population! ***");
        }
        
    }

    
    public void setPopulationSize(int size)
    {
        popSize = size;
    }

    public void postPopulationSize()
    {
        post(new Integer(popSize).toString());
        //return popSize;
    }
}



