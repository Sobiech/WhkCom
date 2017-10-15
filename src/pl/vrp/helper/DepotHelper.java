package pl.vrp.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.vrp.configuration.AlgorithmConfiguration;
import pl.vrp.problem.mdss.Customer;
import pl.vrp.problem.mdss.Depot;

public class DepotHelper
{
    private List<Depot> depotList;
    private List<Customer> customerList;
    private Random rand;
    private String fileName;
    private Integer MAX_CLIENTS;
    private Integer MAX_DEPOTS;
    
    public DepotHelper(AlgorithmConfiguration agConfiguration){
    	this.MAX_CLIENTS = agConfiguration.getCustomerCount();
    	this.MAX_DEPOTS = agConfiguration.getDepotCount();
    	this.fileName = agConfiguration.getFileName();
    	
        this.depotList = new ArrayList<Depot>();
        this.customerList = new ArrayList<Customer>();
        
        this.rand = new Random();
        assignClientsToDepot();
        createFileWithLocations();
    }
    
    private void assignClientsToDepot() {
        for (int id = 0; id < this.MAX_DEPOTS.intValue(); id++) {
          this.depotList.add(generateDepot(id));
        }
        
        for (int id = 0; id < this.MAX_CLIENTS.intValue(); id++){
          Customer customer = generateCustomer(id);
          for (Depot depot : this.depotList) {
            customer.addDistance(depot);
          }
          this.customerList.add(customer);
        }
        
        for(Customer customer : customerList){
        	for(Depot depot : depotList){
        		if(customer.isThisCustomerAssignedToThisDepot(depot)){
        			depot.addCustomer(customer);
        		}
        	}
        }
    }
    
    private void createFileWithLocations() {
    	
        String firstContent = "NODE_COORD_TYPE: TWOD_COORDS \nNODE_COORD_SECTION\n";
        String middleContent = "DEMAND_SECTION\n";
        
        for (Depot depot : this.depotList)
        {
          String fName = this.fileName + "_" + depot.getId() + ".txt";
          String lastContent = "DEPOT_SECTION\n" + depot.getX() + " " + depot.getY() + "\n\n-1\nEOF";
          File file = new File(fName);
          try
          {
            FileOutputStream fos = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos.write(firstContent.getBytes());
            int i = 1;
            for (Customer customer : depot.getDepotCustomers())
            {
                fos.write(customer.toString(i).getBytes());
                i++;
            }
            fos.write(middleContent.getBytes());
            for (int j = 1; j < i; j++) {
                fos.write((j + " 10\n").getBytes());
            }
            fos.write(lastContent.getBytes());
            fos.flush();
            fos.close();
          }
          catch (IOException e)
          {
            e.printStackTrace();
          }
        }
    }
    
    private Customer generateCustomer(int id)
    {
    	double x  = this.rand.nextGaussian() * 100.0,
     		   y   = this.rand.nextGaussian() * 100.0;
    	
        return new Customer(x,y, id);
    }
    
    private Depot generateDepot(int id)
    {
    	double x  = this.rand.nextGaussian() * 100.0,
    		   y   = this.rand.nextGaussian() * 100.0;
        return new Depot(x,y,id);
    }
    
    public List<Depot> getDepotList()
    {
        return this.depotList;
    }
    
    public void setDepotList(List<Depot> depotList)
    {
        this.depotList = depotList;
    }
    
    public List<Customer> getCustomerList()
    {
        return this.customerList;
    }
    
    public void setCustomerList(List<Customer> customerList)
    {
        this.customerList = customerList;
    }
}
