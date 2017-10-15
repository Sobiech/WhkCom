package pl.vrp.problem.mdss;

public abstract class DepotSystem {
	
    protected Integer id;
    protected double x;
    protected double y;
    
    abstract void setLocation(double paramDouble1, double paramDouble2);
    
    abstract double getX();
    
    abstract double getY();
    
    abstract Integer getId();
}
