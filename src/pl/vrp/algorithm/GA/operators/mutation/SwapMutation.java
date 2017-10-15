package pl.vrp.algorithm.GA.operators.mutation;

import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Gene;
import pl.vrp.algorithm.GA.support.ProbabilityMathHelper;

public class SwapMutation
{
    public static Chromosome mutateChromosome(float probability, Chromosome genes)
    {
        Gene[] arrayOfGene;
        int j = (arrayOfGene = genes.getGenes()).length;
        for (int i = 0; i < j; i++)
        {
          Gene geneA = arrayOfGene[i];
          if (ProbabilityMathHelper.checkProbability(probability))
          {
            Gene geneB = null;
            do
            {
                int randomIndex = ProbabilityMathHelper.randomInt(genes.getLenght());
                geneB = genes.getGene(randomIndex);
            } while (geneA == geneB);
            genes.swapGenes(geneA, geneB);
          }
        }
        return genes;
    }
}
