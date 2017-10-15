package pl.vrp.problem.mdss;

import java.util.HashMap;
import java.util.Map;

public class Customer extends DepotSystem {
	private Map<Depot, Double> distanceToDepot;
    
	private float demandSize;
	private int node;
	
    public Customer(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = Integer.valueOf(id);
        this.node = id;
        this.demandSize = 1.0f;
        this.distanceToDepot = new HashMap<Depot, Double>();
    }
    
    public void addDistance(Depot depot) {
        this.distanceToDepot.put(depot, Double.valueOf(depot.getDistanceToDepot(this)));
    }
    
    public boolean isThisCustomerAssignedToThisDepot(Depot depot) {
        Map.Entry<Depot, Double> min = null;
        for (Map.Entry<Depot, Double> entry : this.distanceToDepot.entrySet()) {
          if ((min == null) || (((Double)min.getValue()).doubleValue() > ((Double)entry.getValue()).doubleValue())) {
            min = entry;
          }
        }
        if (((Depot)min.getKey()).equals(depot)) {
          return true;
        }
        return false;
    }
    
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id){
    	this.id = id;
    }
    
    public float getDemandSize() {
		return demandSize;
	}

	public void setDemandSize(float demandSize) {
		this.demandSize = demandSize;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public String toString(int i) {
        return i + " " + getX() + " " + getY() +"\n";
    }

	@Override
	public String toString() {
		return "Customer [demandSize=" + demandSize + ", id=" + id + ", x=" + x + ", y=" + y + "]";
	}
	
}
