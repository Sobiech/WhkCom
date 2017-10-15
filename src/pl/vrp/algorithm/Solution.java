package pl.vrp.algorithm;

import pl.vrp.solution.Statistics;
import pl.vrp.solution.StopCondition;

public class Solution
{
    private StopCondition stopCondition;
    private Statistics statistics;
    
    public Solution() {}
    
    public Solution(StopCondition stopCondition, Statistics statistics)
    {
        this.stopCondition = stopCondition;
        this.statistics = statistics;
    }
    
    public Statistics getStatistics()
    {
        return this.statistics;
    }
    
    public void setStatistics(Statistics statistics)
    {
        this.statistics = statistics;
    }
    
    public StopCondition getStopCondition()
    {
        return this.stopCondition;
    }
    
    public void setStopCondition(StopCondition stopCondition)
    {
        this.stopCondition = stopCondition;
    }
}
