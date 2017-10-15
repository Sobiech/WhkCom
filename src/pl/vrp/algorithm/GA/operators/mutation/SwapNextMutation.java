package pl.vrp.algorithm.GA.operators.mutation;

import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Gene;
import pl.vrp.algorithm.GA.support.ProbabilityMathHelper;

public class SwapNextMutation
{
    public static Chromosome mutateChromosome(float probability, Chromosome genes)
    {
        Gene[] arrayOfGene;
        int j = (arrayOfGene = genes.getGenes()).length;
        for (int i = 0; i < j; i++)
        {
          Gene gene = arrayOfGene[i];
          if (ProbabilityMathHelper.checkProbability(probability)) {
            genes.swapWithNextGene(gene);
          }
        }
        return genes;
    }
}
