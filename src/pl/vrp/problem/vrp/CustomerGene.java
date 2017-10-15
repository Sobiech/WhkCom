package pl.vrp.problem.vrp;

import pl.vrp.algorithm.GA.Gene;

public class CustomerGene implements Gene, Cloneable {
    private String id;
    private int node;
    private float demand;
    
    public CustomerGene(int node) {
        this.node = node;
        this.demand = 0.0F;
    }
    
    public CustomerGene(int node, float demand) {
        this.node = node;
        this.demand = demand;
    }
    
    public CustomerGene(String id, int node, float demand) {
        this.id = id;
        this.node = node;
        this.demand = demand;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getNode(){
        return this.node;
    }
    
    public boolean getIsVehicle(){
        return false;
    }
    
    public boolean getIsDepot() {
        return false;
    }
    
    public boolean getIsCustomer(){
        return true;
    }
    
    public float getDemand(){
        return this.demand;
    }
    
    public void setDemand(float demand) {
        this.demand = demand;
    }
    
    public void setSize(float size) {
        setDemand(size);
    }
    
    public float getSize(){
        return getDemand();
    }
    
    public String toString(){
        return Integer.toString(this.node);
    }
    
    public String print() {
        String geneString = "Customer: " + getNode() + ", isVehicle: " + getIsVehicle() + ", capacity:" + getSize();
        return geneString;
    }
    
    public CustomerGene clone() {
        int clonedNode = this.node;
        float clonedSize = this.demand;
        
        CustomerGene newCustomer = new CustomerGene(clonedNode, clonedSize);
        
        return newCustomer;
    }
}
