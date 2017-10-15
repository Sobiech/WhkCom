package pl.vrp.algorithm.GA;

public abstract interface Gene
{
    public abstract String getId();
    
    public abstract int getNode();
    
    public abstract boolean getIsVehicle();
    
    public abstract boolean getIsCustomer();
    
    public abstract boolean getIsDepot();
    
    public abstract float getSize();
    
    public abstract String toString();
    
    public abstract String print();
    
    public abstract Object clone();
}
