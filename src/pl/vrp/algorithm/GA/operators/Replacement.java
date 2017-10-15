package pl.vrp.algorithm.GA.operators;

import java.util.Arrays;
import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Population;
import pl.vrp.problem.CostMatrix;

public class Replacement {
    public static Population populationReplacement
    	(Chromosome[] matingPool, Population pop, float elitism, CostMatrix costMatrix) {
    	
        int nrEliteChromosomes = (int)(pop.getPopSize() * elitism);
        if (matingPool.length < pop.getPopSize() - nrEliteChromosomes) {
          throw new AssertionError("[ERROR] The number of generated chromossomes is insufficient to create a new population!");
        }
        Chromosome[] elitistChromosomes = pop.getTop(nrEliteChromosomes);
        
        Chromosome[] newChromosomes = (Chromosome[]) 
        		Arrays.copyOfRange(matingPool, 0, pop.getPopSize() - nrEliteChromosomes);
        
        Population newPopulation = new Population(newChromosomes, elitistChromosomes, costMatrix, pop.getGeneticAlgorithm());
        
        return newPopulation;
    }
}
