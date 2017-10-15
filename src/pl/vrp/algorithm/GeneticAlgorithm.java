package pl.vrp.algorithm;

import java.util.ArrayList;
import pl.vrp.algorithm.GA.Gene;
import pl.vrp.algorithm.GA.operators.OperatorsAndParameters;

public class GeneticAlgorithm
{
    private OperatorsAndParameters operatorsAndParameters;
    private Problem problem;
    
    public GeneticAlgorithm(Problem problem)
    {
        this.problem = problem;
    }
    
    public GeneticAlgorithm(OperatorsAndParameters operatorsAndParameters, Problem problem)
    {
        this.operatorsAndParameters = operatorsAndParameters;
        this.problem = problem;
    }
    
    public OperatorsAndParameters getOperatorsAndParameters()
    {
        return this.operatorsAndParameters;
    }
    
    public void setOperatorsAndParameters(OperatorsAndParameters op)
    {
        this.operatorsAndParameters = op;
    }
    
    public ArrayList<Gene> getGenes()
    {
        ArrayList<Gene> genes = new ArrayList<Gene>(this.problem.getCustomers().size() + this.problem.getVehicles().size());
        
        genes.addAll(this.problem.getCustomers());
        
        genes.addAll(this.problem.getVehicles());
        
        return genes;
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList<Gene> getClonedGenes()
    {
        return (ArrayList<Gene>) getGenes().clone();
    }
    
    public int getNrGenes()
    {
        return getNrCustomers() + getNrVehicles();
    }
    
    public int getNrCustomers()
    {
        return this.problem.getCustomers().size();
    }
    
    public int getNrVehicles()
    {
        return this.problem.getVehicles().size();
    }
    
    public int getNrDepots()
    {
        return this.problem.getDepots().size();
    }
    
    public Problem getProblem()
    {
        return this.problem;
    }
}
