package pl.vrp.problem.vrp;

import pl.vrp.algorithm.GA.Gene;

public class DepotGene implements Gene, Cloneable {
    private String id;
    private int node;
    
    public DepotGene(int node) {
        this.node = node;
    }
    
    public DepotGene(String id, int node) {
        this.id = id;
        this.node = node;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getNode() {
    	return this.node;
    }
    
    public boolean getIsVehicle(){
        return false;
    }
    
    public boolean getIsDepot(){
        return true;
    }
    
    public boolean getIsCustomer() {
        return false;
    }
    
    public float getSize(){
        throw new AssertionError("[Error] You tried to get the size of a depot. You have some logical error in your program");
    }
    
    public String toString(){
        return Integer.toString(this.node);
    }
    
    public String print(){
        String geneString = "Customer: " + getNode() + ", isVehicle: " + getIsVehicle() + ", capacity:" + getSize();
        return geneString;
    }
    
    public CustomerGene clone(){
        int clonedNode = this.node;
        
        CustomerGene newCustomer = new CustomerGene(clonedNode);
        
        return newCustomer;
    }

}
