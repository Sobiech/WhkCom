package pl.vrp.problem.vrp;

import pl.vrp.algorithm.GA.Gene;

public class VehicleGene implements Gene, Cloneable {
    private String id;
    private int node;
    private float capacity;
    
    public VehicleGene(int depot) {
        this.node = depot;
        this.capacity = Float.MAX_VALUE;
    }
    
    public VehicleGene(int depot, float capacity) {
        this.node = depot;
        this.capacity = capacity;
    }
    
    public VehicleGene(String id, int depot, float capacity) {
        this.id = id;
        this.node = depot;
        this.capacity = capacity;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getNode() {
        return getDepot();
    }
    
    public int getDepot() {
        return this.node;
    }
    
    public boolean getIsVehicle() {
        return true;
    }
    
    public boolean getIsDepot() {
        return false;
    }
    
    public boolean getIsCustomer(){
        return false;
    }
    
    public float getCapacity(){
        return this.capacity;
    }
    
    public float getSize(){
        return getCapacity();
    }
    
    public String toString(){
        return Integer.toString(this.node);
    }
    
    public String print() {
        String geneString = "Depot: " + getDepot() + ", isVehicle:" + getIsVehicle() + ", capacity:" + getCapacity();
        return geneString;
    }
    
    public VehicleGene clone() {
        int clonedNode = this.node;
        float clonedCapacity = this.capacity;
        
        VehicleGene newVehicle = new VehicleGene(clonedNode, clonedCapacity);
        
        return newVehicle;
    }
}
