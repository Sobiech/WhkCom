package pl.vrp.algorithm.GA;

import pl.vrp.algorithm.GA.operators.OperatorsAndParameters;

public class Generation
{
    private Population oldPopulation;
    private Population newPopulation;
    private int generationNumber;
    private float bestFitness;
    private float worstFitness;
    private float averageFitness;
    private float medianFitness;
    
    public Generation(Population oldPop, int genNumber, OperatorsAndParameters operators)
    {
        this.oldPopulation = oldPop;
        this.generationNumber = genNumber;
    }
    
    public Population getOldPopulation()
    {
        return this.oldPopulation;
    }
    
    public void setOldPopulation(Population oldPopulation)
    {
        this.oldPopulation = oldPopulation;
    }
    
    public Population getNewPopulation()
    {
        return this.newPopulation;
    }
    
    public void setNewPopulation(Population newPopulation)
    {
        this.newPopulation = newPopulation;
    }
    
    public int getGenerationNumber()
    {
        return this.generationNumber;
    }
    
    public void setGenerationNumber(int generationNumber)
    {
        this.generationNumber = generationNumber;
    }
    
    public float getBestFitness()
    {
        return this.bestFitness;
    }
    
    public void setBestFitness(float bestFitness)
    {
        this.bestFitness = bestFitness;
    }
    
    public float getWorstFitness()
    {
        return this.worstFitness;
    }
    
    public void setWorstFitness(float worstFitness)
    {
        this.worstFitness = worstFitness;
    }
    
    public float getAverageFitness()
    {
        return this.averageFitness;
    }
    
    public void setAverageFitness(float averageFitness)
    {
        this.averageFitness = averageFitness;
    }
    
    public float getMedianFitness()
    {
        return this.medianFitness;
    }
    
    public void setMedianFitness(float medianFitness)
    {
        this.medianFitness = medianFitness;
    }
}
