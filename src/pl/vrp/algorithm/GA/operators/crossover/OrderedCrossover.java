package pl.vrp.algorithm.GA.operators.crossover;

import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Gene;
import pl.vrp.algorithm.GA.support.ProbabilityMathHelper;

public class OrderedCrossover
{
    public static Chromosome[] getChildChromosomes(Chromosome[] parents, float probability)
    {
        if (!ProbabilityMathHelper.checkProbability(probability)) {
          return parents;
        }
        if ((probability < 0.0F) || (probability > 1.0F)) {
          System.out.println("[WARNING] Probability of Order1 crossover is " + probability + 
            ", but should be between 0 and 1.");
        }
        if (parents.length > 2)
        {
          System.out.println("[WARNING] Tried to crossover more than two parents with Order1.");
          System.out.println("[WARNING] Only the first two chromosomes will be used.");
        }
        Chromosome[] childs = (Chromosome[])parents.clone();
        try
        {
          for (int i = 0; i < parents.length; i++) {
            childs[i] = ((Chromosome)parents[i].clone());
          }
        }
        catch (Exception e)
        {
          throw new AssertionError("[ERROR] Error cloning parents on Order1 crossover.");
        }
        Chromosome parent1 = parents[0];
        Chromosome parent2 = parents[1];
        Chromosome child1 = childs[0];
        Chromosome child2 = childs[1];
        int lenght = parent1.getLenght();
        for (int i = 0; i < lenght; i++)
        {
          child1.setGene(null, i);
          child2.setGene(null, i);
        }
        int[] crossoverPoints = ProbabilityMathHelper.randomCrossoverPoints(parent1, 2);
        int firstPoint = crossoverPoints[0];
        int secondPoint = crossoverPoints[1];
        for (int i = firstPoint; i <= secondPoint; i++)
        {
          Gene gene = parent1.getGene(i);
          child1.setGene(gene, i);
          gene = parent2.getGene(i);
          child2.setGene(gene, i);
        }
        int indexChild1 = secondPoint + 1;
        int indexChild2 = secondPoint + 1;
        for (int i = secondPoint + 1; i < lenght; i++)
        {
          Gene gene = parent2.getGene(i);
          if (!child1.hasGene(gene))
          {
            if (indexChild1 >= lenght) {
                indexChild1 = 0;
            }
            child1.setGene(gene, indexChild1);
            indexChild1++;
          }
          gene = parent1.getGene(i);
          if (!child2.hasGene(gene))
          {
            if (indexChild2 >= lenght) {
                indexChild2 = 0;
            }
            child2.setGene(gene, indexChild2);
            indexChild2++;
          }
        }
        for (int i = 0; i <= secondPoint; i++)
        {
          Gene gene = parent2.getGene(i);
          if (!child1.hasGene(gene))
          {
            if (indexChild1 >= lenght) {
                indexChild1 = 0;
            }
            child1.setGene(gene, indexChild1);
            indexChild1++;
          }
          gene = parent1.getGene(i);
          if (!child2.hasGene(gene))
          {
            if (indexChild2 >= lenght) {
                indexChild2 = 0;
            }
            child2.setGene(gene, indexChild2);
            indexChild2++;
          }
        }
        return childs;
    }
}
