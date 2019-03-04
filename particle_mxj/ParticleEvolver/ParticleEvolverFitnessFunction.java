/*

 */
 
package ga;

import org.jgap.*;
import org.jgap.impl.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Vector;


public class ParticleEvolverFitnessFunction extends FitnessFunction
{
    private HashMap targets;

    public ParticleEvolverFitnessFunction(HashMap targets)
    {
        this.targets = targets;
    }


  /**
   * Determine the fitness of the given Chromosome instance. The higher the
   * return value, the more fit the instance. This method should always
   * return the same fitness value for two equivalent Chromosome instances.
   *
   * @param subject The Chromosome instance to evaluate.
   *
   * @return A positive double value reflecting the fitness rating of the given
   *         Chromosome.
   */
    public double evaluate(Chromosome subject)
    {
        double totalFitnessVal = 0.0;
		
        double lifeFitness = evaluateLife(subject);
        totalFitnessVal += lifeFitness;
        
        double speedFitness = evaluateSpeed(subject);
        totalFitnessVal += speedFitness;
        
        double dragFitness = evaluateDrag(subject);
        totalFitnessVal += dragFitness;
        
        double pathStrengthFitness = evaluatePathStrength(subject);
        totalFitnessVal += pathStrengthFitness;
        
        double gravityFitness = evaluateGravity(subject);
        totalFitnessVal += gravityFitness;
        
        double pathFitness = evaluatePath(subject);
        totalFitnessVal += pathFitness;
        
        double directionFitness = evaluateDirection(subject);
        totalFitnessVal += directionFitness;
        
        double sizeFitness = evaluateSizeRange(subject);
        totalFitnessVal += sizeFitness;
        
        double colorFitness = evaluateColorRange(subject);
        totalFitnessVal += colorFitness;
        
        double blendFitness = evaluateBlendRange(subject);
        totalFitnessVal += blendFitness;
        
        double angleFitness = evaluateAngle(subject);
        totalFitnessVal += angleFitness;
        
        double particleFitness = evaluateNumParticles(subject);
        totalFitnessVal += particleFitness;
        

        // Make sure fitness value is always positive.
        // -------------------------------------------
        return Math.max(1.0, totalFitnessVal);

    }


    // evaluate life
    private double evaluateLife(Chromosome subject)
    {
        int lifeUpper = 5000; // upper bounds
        
        // target life
        int targetLife = ((Integer) targets.get("life")).intValue();
        // actual life
        int life = ((IntegerGene) subject.getGene(0)).intValue();
    
        // fitness value
        Integer fitnessVal = new Integer(lifeUpper - (Math.abs(life - targetLife)));
        
        // return fitness value as a double
        return fitnessVal.doubleValue();
    }							


    // evaluate speed
    private double evaluateSpeed(Chromosome subject)
    {
        double speedUpper = 10000.0; // upper bounds
        
        // target speed
        double targetSpeed = ((Double) targets.get("speed")).doubleValue();
        // actual speed
        double speed = ((DoubleGene) subject.getGene(1)).doubleValue();
        
        // return fitness value
        return speedUpper - (Math.abs(speed - targetSpeed));
    }
    
    
    // evaluate drag
    private double evaluateDrag(Chromosome subject)
    {
        int upperDrag = 100; // upper bounds
        
        // target drag
        int targetDrag = ((Integer) targets.get("drag")).intValue();
        // actual drag
        int drag = ((IntegerGene) subject.getGene(2)).intValue();
    
        // fitness value
        Integer fitnessVal = new Integer(upperDrag - (Math.abs(drag - targetDrag)));
        
        // return fitness value as a double
        return fitnessVal.doubleValue();
    }
    
    
    // evaluate pathStrength
    private double evaluatePathStrength(Chromosome subject)
    {
        double upperPathStrengthBounds = 0.99; // upper bounds
        
        // target strength
        double targetStrength = ((Double) targets.get("pathStrength")).doubleValue();
        // actual strength
        double strength = ((DoubleGene) subject.getGene(3)).doubleValue();
        
        // return fitness value
        return upperPathStrengthBounds - (Math.abs(strength - targetStrength));
    }
    
    
    // evaluate gravity
    private double evaluateGravity(Chromosome subject)
    {
        double gravityUpper = 100.0; // upper bounds
        
        // target vectors
        ArrayList gravList = (ArrayList) targets.get("gravity");
        double targetVectorX = ((Double) gravList.get(0)).doubleValue();
        double targetVectorY = ((Double) gravList.get(1)).doubleValue();
        double targetVectorZ = ((Double) gravList.get(2)).doubleValue();
        
        // actual vectors in this chromosome
        Vector geneList = (Vector) (subject.getGene(4).getAllele());
        double vectorX = ((Double) geneList.get(0)).doubleValue();
        double vectorY = ((Double) geneList.get(1)).doubleValue();
        double vectorZ = ((Double) geneList.get(2)).doubleValue();
        
        // fitness values
        double fitnessVal1 = gravityUpper - (Math.abs(vectorX - targetVectorX));
        double fitnessVal2 = gravityUpper - (Math.abs(vectorY - targetVectorY));
        double fitnessVal3 = gravityUpper - (Math.abs(vectorZ - targetVectorZ));
        
        // return overall fitness
        return (fitnessVal1 + fitnessVal2 + fitnessVal3) / 3.0;
    }
    
    
    // evaluate path
    private double evaluatePath(Chromosome subject)
    {
        double pathUpper = 100.0; // upper bounds
        
        // ArrayList containing 3 sets of x/y/z path vectors
        ArrayList pathList = (ArrayList) targets.get("path");

        // now the target vectors
        double targetVector1X = ((Double) pathList.get(0)).doubleValue();
        double targetVector1Y = ((Double) pathList.get(1)).doubleValue();
        double targetVector1Z = ((Double) pathList.get(2)).doubleValue();
        double targetVector2X = ((Double) pathList.get(3)).doubleValue();
        double targetVector2Y = ((Double) pathList.get(4)).doubleValue();
        double targetVector2Z = ((Double) pathList.get(5)).doubleValue();
        double targetVector3X = ((Double) pathList.get(6)).doubleValue();
        double targetVector3Y = ((Double) pathList.get(7)).doubleValue();
        double targetVector3Z = ((Double) pathList.get(8)).doubleValue();
        
        // actual vectors in this chromosome
        Vector geneList = (Vector) (subject.getGene(5).getAllele());
        double vector1X = ((Double) geneList.get(0)).doubleValue();
        double vector1Y = ((Double) geneList.get(1)).doubleValue();
        double vector1Z = ((Double) geneList.get(2)).doubleValue();
        double vector2X = ((Double) geneList.get(3)).doubleValue();
        double vector2Y = ((Double) geneList.get(4)).doubleValue();
        double vector2Z = ((Double) geneList.get(5)).doubleValue();
        double vector3X = ((Double) geneList.get(6)).doubleValue();
        double vector3Y = ((Double) geneList.get(7)).doubleValue();
        double vector3Z = ((Double) geneList.get(8)).doubleValue();
        
        // fitness values
        double fitnessVal1 = pathUpper - (Math.abs(vector1X - targetVector1X));
        double fitnessVal2 = pathUpper - (Math.abs(vector1Y - targetVector1Y));
        double fitnessVal3 = pathUpper - (Math.abs(vector1Z - targetVector1Z));
        double fitnessVal4 = pathUpper - (Math.abs(vector2X - targetVector2X));
        double fitnessVal5 = pathUpper - (Math.abs(vector2Y - targetVector2Y));
        double fitnessVal6 = pathUpper - (Math.abs(vector2Z - targetVector2Z));
        double fitnessVal7 = pathUpper - (Math.abs(vector3X - targetVector3X));
        double fitnessVal8 = pathUpper - (Math.abs(vector3Y - targetVector3Y));
        double fitnessVal9 = pathUpper - (Math.abs(vector3Z - targetVector3Z));
        
        // return overall fitness
        return (fitnessVal1 + fitnessVal2 + fitnessVal3 + fitnessVal4 + fitnessVal5 + fitnessVal6 +
                fitnessVal7 + fitnessVal8 + fitnessVal9) / 9.0;
    }
    
    
    // evaluate direction
    private double evaluateDirection(Chromosome subject)
    {
        double directionUpper = 100.0; // upper bounds
        
        // target vectors
        ArrayList dirList = (ArrayList) targets.get("direction");
        double targetVectorX = ((Double) dirList.get(0)).doubleValue();
        double targetVectorY = ((Double) dirList.get(1)).doubleValue();
        double targetVectorZ = ((Double) dirList.get(2)).doubleValue();
        
        // actual vectors in this chromosome
        Vector geneList = (Vector) (subject.getGene(6).getAllele());
        double vectorX = ((Double) geneList.get(0)).doubleValue();
        double vectorY = ((Double) geneList.get(1)).doubleValue();
        double vectorZ = ((Double) geneList.get(2)).doubleValue();
        
        // fitness values
        double fitnessVal1 = directionUpper - (Math.abs(vectorX - targetVectorX));
        double fitnessVal2 = directionUpper - (Math.abs(vectorY - targetVectorY));
        double fitnessVal3 = directionUpper - (Math.abs(vectorZ - targetVectorZ));
        
        // return overall fitness
        return (fitnessVal1 + fitnessVal2 + fitnessVal3) / 3.0;
    }
    
    
    // evaluate size range
    private double evaluateSizeRange(Chromosome subject)
    {
        double sizeUpper = 10.0; // upper bounds
        
        // target sizes
        ArrayList sizeList = (ArrayList) targets.get("sizeRange");
        double targetBeginSize = ((Double) sizeList.get(0)).doubleValue();
        double targetEndSize = ((Double) sizeList.get(1)).doubleValue();
        
        // actual sizes in this chromosome
        Vector geneList = (Vector) (subject.getGene(7).getAllele());
        double beginSize = ((Double) geneList.get(0)).doubleValue();
        double endSize = ((Double) geneList.get(1)).doubleValue();
        
        // fitness values
        double fitnessVal1 = sizeUpper - (Math.abs(beginSize - targetBeginSize));
        double fitnessVal2 = sizeUpper - (Math.abs(endSize - targetEndSize));
        
        // return overall fitness
        return (fitnessVal1 + fitnessVal2) / 2.0;
    }
    
    
    // evaluate color range
    private double evaluateColorRange(Chromosome subject)
    {
        int colorUpper = 255; // upper bounds
        
        // ArrayList containing 2 sets of 3 color values (rgb)
        ArrayList colorRangeList = (ArrayList) targets.get("colorRange");

        // now the target colors
        int targetBeginRed = ((Integer) colorRangeList.get(0)).intValue();
        int targetBeginGreen = ((Integer) colorRangeList.get(1)).intValue();
        int targetBeginBlue = ((Integer) colorRangeList.get(2)).intValue();
        int targetEndRed = ((Integer) colorRangeList.get(3)).intValue();
        int targetEndGreen = ((Integer) colorRangeList.get(4)).intValue();
        int targetEndBlue = ((Integer) colorRangeList.get(5)).intValue();
        
        // actual colors in this chromosome
        Vector geneList = (Vector) (subject.getGene(8).getAllele());
        int beginRed = ((Integer) geneList.get(0)).intValue();
        int beginGreen = ((Integer) geneList.get(1)).intValue();
        int beginBlue = ((Integer) geneList.get(2)).intValue();
        int endRed = ((Integer) geneList.get(3)).intValue();
        int endGreen = ((Integer) geneList.get(4)).intValue();
        int endBlue = ((Integer) geneList.get(5)).intValue();
        
        // fitness values
        int fitnessVal1 = colorUpper - (Math.abs(beginRed - targetBeginRed));
        int fitnessVal2 = colorUpper - (Math.abs(beginGreen - targetBeginGreen));
        int fitnessVal3 = colorUpper - (Math.abs(beginBlue - targetBeginBlue));
        int fitnessVal4 = colorUpper - (Math.abs(endRed - targetEndRed));
        int fitnessVal5 = colorUpper - (Math.abs(endGreen - targetEndGreen));
        int fitnessVal6 = colorUpper - (Math.abs(endBlue - targetEndBlue));
        
        double overallFitness = (fitnessVal1 + fitnessVal2 + fitnessVal3 + 
                fitnessVal4 + fitnessVal5 + fitnessVal6) / 6.0;
        
        // return overall fitness
        return overallFitness;
    }
    
    
    // evaluate blend range
    private double evaluateBlendRange(Chromosome subject)
    {
        int blendUpper = 100; // upper bounds
        
        // target blends
        ArrayList blendList = (ArrayList) targets.get("blendRange");
        int targetBeginBlend = ((Integer) blendList.get(0)).intValue();
        int targetEndBlend = ((Integer) blendList.get(1)).intValue();
        
        // actual blends in this chromosome
        Vector geneList = (Vector) (subject.getGene(9).getAllele());
        int beginBlend = ((Integer) geneList.get(0)).intValue();
        int endBlend = ((Integer) geneList.get(1)).intValue();
        
        // fitness values
        int fitnessVal1 = blendUpper - (Math.abs(beginBlend - targetBeginBlend));
        int fitnessVal2 = blendUpper - (Math.abs(endBlend - targetEndBlend));
        
        double overallFitness = (fitnessVal1 + fitnessVal2) / 2.0;
        
        // return overall fitness
        return overallFitness;
    }
    
    
    // evaluate angle
    private double evaluateAngle(Chromosome subject)
    {
        double angleUpper = 180.0; // upper bounds
        
        // target angle
        double targetAngle = ((Double) targets.get("angle")).doubleValue();
        // actual angle
        double angle = ((DoubleGene) subject.getGene(10)).doubleValue();
        
        // return fitness value
        return angleUpper - (Math.abs(angle - targetAngle));
    }
    
    
    // evaluate number of particles
    private double evaluateNumParticles(Chromosome subject)
    {
        int particlesUpper = 1000; // upper bounds
        
        // target num particles
        int targetParticles = ((Integer) targets.get("numP")).intValue();
        // actual num particles
        int particles = ((IntegerGene) subject.getGene(11)).intValue();
    
        // fitness value
        Integer fitnessVal = new Integer(particlesUpper - (Math.abs(particles - targetParticles)));
        
        // return fitness value as a double
        return fitnessVal.doubleValue();
    }	
    
}

