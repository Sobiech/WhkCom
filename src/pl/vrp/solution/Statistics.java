package pl.vrp.solution;

import java.io.File;

public class Statistics
{
    private File statsFile;
    
    public Statistics() {}
    
    public Statistics(File statsFile)
    {
        this.statsFile = statsFile;
    }
    
    public File getStatsFile()
    {
        return this.statsFile;
    }
    
    public void setStatsFile(File statsFile)
    {
        this.statsFile = statsFile;
    }
}
