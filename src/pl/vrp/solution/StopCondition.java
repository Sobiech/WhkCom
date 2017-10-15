package pl.vrp.solution;

public class StopCondition
{
    private int maxNumGenerations;
    private int maxNumGenerationsWtImprovement;
    
    public StopCondition() {}
    
    public StopCondition(int maxNumGenerations)
    {
        this.maxNumGenerations = maxNumGenerations;
    }
    
    public int getMaxNumGenerations()
    {
        return this.maxNumGenerations;
    }
    
    public void setMaxNumGenerations(int maxNumGenerations)
    {
        this.maxNumGenerations = maxNumGenerations;
    }
    
    public int getMaxNumGenerationsWtImprovement()
    {
        return this.maxNumGenerationsWtImprovement;
    }
    
    public void setMaxNumGenerationsWtImprovement(int maxNumGenerationsWtImprovement)
    {
        this.maxNumGenerationsWtImprovement = maxNumGenerationsWtImprovement;
    }
}
