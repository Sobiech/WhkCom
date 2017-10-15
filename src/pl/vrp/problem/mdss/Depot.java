package pl.vrp.problem.mdss;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Depot extends DepotSystem {
	
    private List<Customer> depotCustomers;
    
    public Depot(double x, double y, int id) {
        this.x = x;
        this.y = y;
        
        this.id = Integer.valueOf(id);
        this.depotCustomers = new ArrayList<Customer>();
    }
    
    public double getDistanceToDepot(Customer customer){
        return Point2D.distance(getX(), getY(), customer.getX(), customer.getY());
    }
    
    public void addCustomer(Customer customer){
        this.depotCustomers.add(customer);
    }
    
    public void printCustomersInfromation(){
        System.out.println("DEPOT ID:" + getId() + "   Customer count:" + this.depotCustomers.size());
    }
    
    public void setLocation(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
    public Integer getId(){
        return this.id;
    }
    
    public List<Customer> getDepotCustomers() {
        return this.depotCustomers;
    }
    
    public void setDepotCustomers(List<Customer> depotCustomers) {
        this.depotCustomers = depotCustomers;
    }
}
