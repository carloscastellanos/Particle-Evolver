//
//  GrainWindowGene.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/27/05.
//  Copyright 2005. All rights reserved.
//

import org.jgap.Gene;
import org.jgap.Configuration;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.RandomGenerator;

/**
 * A gene used to represent grain window or amplitude envelope
 * the available options are: Gaussian, Quasi-Gaussian, Traingle,
 * Expodec and Reexpodec.
 */
public class GrainWindowGene implements Gene, java.io.Serializable
{

    private static final String TOKEN_SEPARATOR = ":";
    
    
    public GrainWindowGene()
    {
    
    }
    
    
    public static double gaussian(double d1, double d2, double d3)
    {
        return Math.exp((-1*(d1-d3)*(d1-d3))/(2*d2*d2))/(d2*Math.sqrt(2*Math.PI));
    }
    

    public static double quasiGaussian(double d1, double d2, double d3)
    {
        double max = 0.5;
        double d = Math.min(d1, max);
        return Math.exp((-1*(d-d3)*(d-d3))/(2*d2*d2))/(d2*Math.sqrt(2*Math.PI));
    }
    
    
    public static double expodec(double d1 double d2)
    {
       return Math.exp(-1.0 * Math.abs(d1) /d2) / (2.0 * d2);
       //d * (d2 * 2.0)
    }
    
    
    public static double reexpodec()
    {
    
    }
    
    
    public static double triangle(double d1 double d2)
    {
        return (d1 + d2) * 0.5;
    }
}
