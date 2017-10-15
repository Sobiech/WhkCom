package pl.vrp.algorithm;

import java.util.ArrayList;
import pl.vrp.problem.CostMatrix;
import pl.vrp.problem.vrp.CustomerGene;
import pl.vrp.problem.vrp.DepotGene;
import pl.vrp.problem.vrp.VehicleGene;

public class Problem
{
    private CostMatrix costMatrix;
    private ArrayList<CustomerGene> customers;
    private ArrayList<VehicleGene> vehicles;
    private ArrayList<DepotGene> depots;
    
    public Problem()
    {
        this.customers = new ArrayList<CustomerGene>();
        this.vehicles = new ArrayList<VehicleGene>();
        this.depots = new ArrayList<DepotGene>();
    }
    
    public Problem(CostMatrix costMatrix, ArrayList<CustomerGene> customers, ArrayList<VehicleGene> vehicles, ArrayList<DepotGene> depots)
    {
        this.costMatrix = costMatrix;
        this.customers = customers;
        this.vehicles = vehicles;
        this.depots = depots;
    }
    
    public void addCustomer(CustomerGene customer)
    {
        this.customers.add(customer);
    }
    
    public void addVehicle(VehicleGene vehicle)
    {
        this.vehicles.add(vehicle);
    }
    
    public void addDepot(DepotGene depot)
    {
        this.depots.add(depot);
    }
    
    public CostMatrix getCostMatrix()
    {
        return this.costMatrix;
    }
    
    public void setCostMatrix(CostMatrix costMatrix)
    {
        this.costMatrix = costMatrix;
    }
    
    public ArrayList<CustomerGene> getCustomers()
    {
        return this.customers;
    }
    
    public void setCustomers(ArrayList<CustomerGene> customers)
    {
        this.customers = customers;
    }
    
    public ArrayList<DepotGene> getDepots()
    {
        return this.depots;
    }
    
    public void setDepots(ArrayList<DepotGene> depots)
    {
        this.depots = depots;
    }
    
    public ArrayList<VehicleGene> getVehicles()
    {
        return this.vehicles;
    }
    
    public void setVehicles(ArrayList<VehicleGene> vehicles)
    {
        this.vehicles = vehicles;
    }
}
