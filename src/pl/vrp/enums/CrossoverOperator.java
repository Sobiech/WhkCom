package pl.vrp.enums;

import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.operators.crossover.EdgeCrossover;
import pl.vrp.algorithm.GA.operators.crossover.OrderedCrossover;
import pl.vrp.algorithm.GA.operators.crossover.PartiallyMappedCrossover;

public enum CrossoverOperator
{
    TYP_KRZYZOWANIA("Typ krzyzowania"),  
    ORDER1("Order1"),  
    PMX("PMX.PMX"),  
    EDGE3("Edge3.Edge3");
    
    private String name;
    
    private CrossoverOperator(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Chromosome[] getChildChromosomes(Chromosome[] parents, float probability){
    	
    	Chromosome[] chromosomes = null;
    	if(this.equals(EDGE3)){
    		chromosomes = EdgeCrossover.getChildChromosomes(parents, probability);
    	}else if(this.equals(ORDER1)){
    		chromosomes = OrderedCrossover.getChildChromosomes(parents, probability);
    	} else if(this.equals(PMX)){
    		chromosomes = PartiallyMappedCrossover.getChildChromosomes(parents, probability);
    	}
    	
    	return chromosomes;
    }
    
}
