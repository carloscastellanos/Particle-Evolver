//
//  CloudEvolverFitnessFunction.java
//  CloudEvolver
//
//  Created by Carlos Castellanos on 1/24/05.
//  Copyright 2005. All rights reserved.
//

import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import java.util.HashMap;

public class CloudEvolverFitnessFunction extends FitnessFunction
{

    private final HashMap targets;
    
    // Constructor takes a HashMap of target values
    public CloudEvolverFitnessFunction(HashMap targets)
    {
        this.targets = new HashMap(targets);
    }

    public int assignRank(int rank) {
    
    }
    
    public int evaluate(Chromosome chrom)
    {
    
    }

}


