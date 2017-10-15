package pl.vrp.algorithm.GA.operators.crossover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Gene;
import pl.vrp.algorithm.GA.support.ProbabilityMathHelper;

public class EdgeCrossover {
	
    public static Chromosome[] getChildChromosomes(Chromosome[] parents, float probability) {
        
    	if ((probability < 0.0F) || (probability > 1.0F)) {
          System.out.println("[WARNING] Probability of Edge crossover is " + probability + 
            ", but should be between 0 and 1.");
        }
        
    	if (parents.length > 2) {
          System.out.println("[WARNING] Tried to crossover more than two parents with Edge3.");
          System.out.println("[WARNING] Only the first two chromosomes will be used.");
        }
    	
        if (!ProbabilityMathHelper.checkProbability(probability)) {
          return parents;
        }
        
        HashMap<Gene, ArrayList<Gene>> edgeTable1 = buildEdgeTable(parents[0], parents[1]);
        HashMap<Gene, ArrayList<Gene>> edgeTable2 = buildEdgeTable(parents[1], parents[0]);
        
        int size = parents[0].getLenght();
        
        Gene[] child1 = new Gene[size];
        Gene[] child2 = new Gene[size];
        
        Gene currentElement1 = parents[0].getGene(ProbabilityMathHelper.randomInt(size));
        Gene currentElement2 = parents[1].getGene(ProbabilityMathHelper.randomInt(size));

        child1[0] = currentElement1;
        child2[0] = currentElement2;
        
        for (int i = 1; i < size; i++) {
          ArrayList<Gene> values1 = getRemoveElement(edgeTable1, currentElement1);
          ArrayList<Gene> values2 = getRemoveElement(edgeTable2, currentElement2);
          
          currentElement1 = getNextElement(edgeTable1, values1);
          currentElement2 = getNextElement(edgeTable2, values2);
          
          child1[i] = currentElement1;
          child2[i] = currentElement2;
        }
        Chromosome[] childs = new Chromosome[2];
        childs[0] = new Chromosome(child1, parents[0].getCostMatrix(), parents[0].getGeneticAlgorithm());
        childs[1] = new Chromosome(child2, parents[0].getCostMatrix(), parents[0].getGeneticAlgorithm());
        
        childs[0].verifyGenes();
        childs[1].verifyGenes();
        
        return childs;
    }
    
    public static HashMap<Gene, ArrayList<Gene>> buildEdgeTable(Chromosome P1, Chromosome P2)
    {
        if (P1.getLenght() != P2.getLenght()) {
          throw new AssertionError("ERROR: The Chromosomes have different lenght!");
        }
        
        HashMap<Gene, ArrayList<Gene>> edgeTable = 
        		new HashMap<Gene, ArrayList<Gene>>(P1.getLenght() * P1.getLenght());
        
        for (int i = 0; i < P1.getLenght(); i++) {
          ArrayList<Gene> list = new ArrayList<Gene>();
          list.add(P1.getGeneAfter(i));
          list.add(P1.getGeneBefore(i));
          edgeTable.put(P1.getGene(i), list);
        }
        
        for (int i = 0; i < P2.getLenght(); i++) {
          ArrayList<Gene> list = edgeTable.get(P2.getGene(i));
          list.add(P2.getGeneAfter(i));
          list.add(P2.getGeneBefore(i));
        }
        return edgeTable;
    }
    
    public static ArrayList<Gene> getRemoveElement(HashMap<Gene, ArrayList<Gene>> edgeTable, Gene currentElement) {
        ArrayList<Gene> currentValues = edgeTable.get(currentElement);
        
        edgeTable.remove(currentElement);
        for (Gene gene : currentValues) {
          ArrayList<Gene> values = edgeTable.get(gene);
          values.remove(currentElement);
        }
        
        return currentValues;
    }
    
    public static Gene getNextElement(HashMap<Gene, ArrayList<Gene>> edgeTable, ArrayList<Gene> values) {
        if (values.isEmpty()) {
          return randomEntry(edgeTable);
        }
        
        Gene nextGene = getCommonEdge(values);
        if (nextGene != null) {
          return nextGene;
        }
        
        return getShortest(edgeTable, values);
    }
    
    public static Gene getCommonEdge(ArrayList<Gene> values){
        HashSet<Gene> valuesSet = new HashSet<Gene>(values.size(), 1.0F);
        for(Gene gene : values){
        	if(!valuesSet.add(gene)){
        		return gene;
        	}
        }
        return null;
    }
    
    public static Gene getShortest(HashMap<Gene, ArrayList<Gene>> edgeTable, ArrayList<Gene> values) {
        Gene smallestList = null;
        int smallestSize = Integer.MAX_VALUE;
        
        for(Gene gene : values){
          int size = edgeTable.get(gene).size();
          if (size < smallestSize){
            smallestSize = size;
            smallestList = gene;
          }
        }
        return smallestList;
    }
    
    public static Gene randomEntry(HashMap<Gene, ArrayList<Gene>> edgeTable) {
        Gene[] entrySet = (Gene[])edgeTable.keySet().toArray(new Gene[0]);
        int randomIndex = ProbabilityMathHelper.randomInt(entrySet.length);
        return entrySet[randomIndex];
    }
}
