package pl.vrp.algorithm.GA.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import pl.vrp.algorithm.GA.Chromosome;

public class ProbabilityMathHelper
{
    static final Random randomGenerator = new Random();
    
    public static boolean checkProbability(float probability)
    {
        float randomFloat = randomGenerator.nextFloat();
        
        return randomFloat < probability;
    }
    
    public static int randomInt(int max)
    {
        return randomGenerator.nextInt(max);
    }
    
    public static float randomFloat()
    {
        return randomGenerator.nextFloat();
    }
    
    public static int[] randomCrossoverPoints(Chromosome chr, int n)
    {
        ArrayList<Integer> crossoverPoints = new ArrayList<Integer>();
        int[] crossoverPointsArray = new int[n];
        for (int i = 0; i < n; i++)
        {
          int rand;
          do
          {
            rand = randomInt(chr.getLenght());
          } while (crossoverPoints.contains(Integer.valueOf(rand)));
          crossoverPoints.add(Integer.valueOf(rand));
        }
        for (int i = 0; i < crossoverPoints.size(); i++) {
          crossoverPointsArray[i] = ((Integer)crossoverPoints.get(i)).intValue();
        }
        Arrays.sort(crossoverPointsArray);
        
        return crossoverPointsArray;
    }
}
