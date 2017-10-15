package pl.vrp.algorithm.GA.operators;

import pl.vrp.configuration.AlgorithmConfiguration;
import pl.vrp.enums.CrossoverOperator;
import pl.vrp.enums.ReplacementType;
import pl.vrp.enums.MutationOperator;
import pl.vrp.enums.SelectionType;

public class OperatorsAndParameters
{
    private int populationSize;
    
    private SelectionType selectionOperator;
    private float selectionPercentage;
    private int selectionParam;
    
    private CrossoverOperator crossoverOperator;
    private float crossoverProb;
    
    private MutationOperator mutationOperator;
    private float mutationProb;
    
    private ReplacementType generationOperator;
    private float replacementElitism;
    
    private float innerDepotPenalty;
    
    public OperatorsAndParameters() {
        this.populationSize = 1000;
        this.selectionOperator = SelectionType.TOURNAMENT;
        this.selectionPercentage = 1.0F;
        this.selectionParam = 2;
        this.crossoverOperator = CrossoverOperator.EDGE3;
        this.crossoverProb = 0.8F;
        this.mutationOperator = MutationOperator.SWAP_MUTATION;
        this.mutationProb = 0.01F;
        this.generationOperator = ReplacementType.REPLACEMENT;
        this.replacementElitism = 0.1F;
        this.innerDepotPenalty = 0.0F;
    }
    
    public OperatorsAndParameters(AlgorithmConfiguration config, Integer costMatrixSize){
    	this.populationSize = config.getPopulationSize();
        
    	this.selectionOperator = config.getSelectionOperator();
        this.selectionPercentage = 1.0F;
        this.selectionParam = 2;
        
        this.crossoverOperator = config.getCrossoverOperator();
        this.crossoverProb = config.getCrossoverProbability();
        this.mutationOperator = config.getMutationOperator();
        this.mutationProb = config.getMutationProbability() / costMatrixSize;
        this.generationOperator = config.getGenerationOperator();
        this.replacementElitism = config.getReplacementProbability();
        this.innerDepotPenalty = config.getDepotPenalty();
    }
    
    public int getPopulationSize(){
        return this.populationSize;
    }
    
    public void setPopulationSize(int populationSize)
    {
        this.populationSize = populationSize;
    }
    
    public SelectionType getSelectionOperator()
    {
        return this.selectionOperator;
    }
    
    public void setSelectionOperator(SelectionType selectionType)
    {
        this.selectionOperator = selectionType;
    }
    
    public int getSelectionParam()
    {
        return this.selectionParam;
    }
    
    public void setSelectionParam(int selectionParam)
    {
        this.selectionParam = selectionParam;
    }
    
    public float getSelectionPercentage()
    {
        return this.selectionPercentage;
    }
    
    public void setSelectionPercentage(float selectionPercentage)
    {
        this.selectionPercentage = selectionPercentage;
    }
    
    public CrossoverOperator getCrossoverOperator()
    {
        return this.crossoverOperator;
    }
    
    public void setCrossoverOperator(CrossoverOperator crossoverOperator)
    {
        this.crossoverOperator = crossoverOperator;
    }
    
    public float getCrossoverProb()
    {
        return this.crossoverProb;
    }
    
    public void setCrossoverProb(float crossoverProb)
    {
        this.crossoverProb = crossoverProb;
    }
    
    public MutationOperator getMutationOperator()
    {
        return this.mutationOperator;
    }
    
    public void setMutationOperator(MutationOperator mutationOperator)
    {
        this.mutationOperator = mutationOperator;
    }
    
    public float getMutationProb()
    {
        return this.mutationProb;
    }
    
    public void setMutationProb(float mutationProb)
    {
        this.mutationProb = mutationProb;
    }
    
    public ReplacementType getGenerationOperator()
    {
        return this.generationOperator;
    }
    
    public void setGenerationOperator(ReplacementType generationOperator)
    {
        this.generationOperator = generationOperator;
    }
    
    public float getReplacementElitism()
    {
        return this.replacementElitism;
    }
    
    public void setReplacementElitism(float replacementElitism)
    {
        this.replacementElitism = replacementElitism;
    }
    
    public float getInnerDepotPenalty()
    {
        return this.innerDepotPenalty;
    }
    
    public void setInnerDepotPenalty(float innerDepotPenalty)
    {
        this.innerDepotPenalty = innerDepotPenalty;
    }
}
