package pl.vrp.enums;

import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.operators.mutation.InsertMutation;
import pl.vrp.algorithm.GA.operators.mutation.InversionMutation;
import pl.vrp.algorithm.GA.operators.mutation.SwapMutation;
import pl.vrp.algorithm.GA.operators.mutation.SwapNextMutation;

public enum MutationOperator {
	
    TYP_MUTACJI("Typ mutacji"),  
    SWAP_NEXT_MUTATION("Swap next mutation"),  
    SWAP_MUTATION("Swap mutation"),  
    INSERT_MUTATION("Insert mutation"),  
    INVERSION_MUTATION("Inversion mutation");
    
    private String name;
    
    private MutationOperator(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Chromosome mutateChromosome(float probability, Chromosome genes){
    	Chromosome chromosome = null;
    	
    	if(this.equals(INSERT_MUTATION)){
    		chromosome = InsertMutation.mutateChromosome(probability, genes);
    	} else if(this.equals(INVERSION_MUTATION)){
    		chromosome = InversionMutation.mutateChromosome(probability, genes);
    	} else if(this.equals(SWAP_MUTATION)){
    		chromosome = SwapMutation.mutateChromosome(probability, genes);
    	} else if(this.equals(SWAP_NEXT_MUTATION)){
    		chromosome = SwapNextMutation.mutateChromosome(probability, genes);
    	}
    	
    	return chromosome;
    }
    
}
