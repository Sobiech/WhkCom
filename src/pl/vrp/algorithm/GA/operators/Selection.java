package pl.vrp.algorithm.GA.operators;

import java.util.Arrays;

import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Population;
import pl.vrp.algorithm.GA.support.ProbabilityMathHelper;

public class Selection
{
    public static Chromosome tournamentSelection(int n, Population pop) {
        Chromosome[] tournamentPool = new Chromosome[n];
        Chromosome[] chromosomes = pop.getChromosomes();
        
        for (int i = 0; i < n; i++) {
          tournamentPool[i] = chromosomes[ProbabilityMathHelper.randomInt(chromosomes.length)];
        }
        Arrays.sort(tournamentPool, pop);
        
        try {
          return (Chromosome)tournamentPool[0].clone();
        } catch (CloneNotSupportedException ex) {
          System.out.println("[Error] Could't clone the best element of the Tournament Pool:");
          ex.printStackTrace();
        }
        return null;
    }
}
