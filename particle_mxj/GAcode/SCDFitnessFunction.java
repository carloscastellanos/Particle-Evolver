/*

 */
 
package ga;

import org.jgap.*;
import java.util.*;


public class SCDFitnessFunction extends FitnessFunction
{

    public static final int MAX_BOUND = 1000;

    public SCDFitnessFunction(Map targets)
    {
  /*

      if( a_targetAmount < 1 || a_targetAmount >= MAX_BOUND )

      {

          throw new IllegalArgumentException(

              "Change amount must be between 1 and "+MAX_BOUND+" cents." );

      }



      m_targetAmount = a_targetAmount;
*/
    }


    public double evaluate(Chromosome a_subject) {
        return 0.0;
    }


}





