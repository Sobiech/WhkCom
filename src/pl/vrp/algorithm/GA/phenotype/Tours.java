package pl.vrp.algorithm.GA.phenotype;

import java.util.ArrayList;
import pl.vrp.algorithm.GA.Gene;
import pl.vrp.problem.vrp.CustomerGene;
import pl.vrp.problem.vrp.VehicleGene;


public class Tours {
        
        protected ArrayList<Tour> tourList = new ArrayList<Tour>();
        
        public Tours(){}
        
        // Constructor
        public Tours(ArrayList<Tour> tours){
            this.tourList = tours;
        }
        
            
        // Get the list of tours
        public ArrayList<Tour> getTours(){
            return tourList;
        }
        
        // Get a given tour, by vehicle
        public Tour getTour(VehicleGene vehicle){
            for (Tour tour : tourList){
                  if (tour.getVehicle()==vehicle){
                        return tour;
                  }
            }
            return null;
        }
        
        public String getVehicleTours(){
          String str = "";
          int i = 1;
          for (Tour tour : this.tourList){
            str = str + "Pojazd nr." + i + "  :  " + tour.toString() + "\n";
            i++;
          }
          return str;
        }
        
        // Get a given tour, by index. First tour is index 0.
        public Tour getTour(int index){
            return tourList.get(index);
        }
        
        // Get the number of tours
        public Integer numberOfTours(){
            return tourList.size();
        }
        
        // Prints a human friendly representation of the tours
        public void print(){
            for (Tour tour:tourList){
                  System.out.println(tour.toString());
            }
        }
        
        // Returns a String with a human friendly representation of the tours
        @Override
        public String toString(){
            String toursString = new String();
            for (Tour tour:tourList){
                  toursString += tour.toString() + "\n";
            }
            return toursString;
        }
        
      
        public class Tour {
            
            private VehicleGene vehicle;
            private ArrayList<Gene> customers;

            
            public Tour (VehicleGene vehicle, ArrayList<Gene> customers){
                  this.customers = customers;
                  this.vehicle = vehicle;
            }
            
            /*
             * This constructor take care of the capacities of the vehicles
             */
            public Tour (VehicleGene vehicle, ArrayList<CustomerGene> customers, float capacity){
                  this.vehicle = vehicle;
                  
            }
            
            // Human friendly string, representing this tour
            @Override
            public String toString(){
                  String stringTour = ": [ 0";
                  for(Gene customer:customers){
                        stringTour += " "+customer.toString();
                  }
                  return stringTour + " ]";
            }
            
            /*
             * Getters and Setters
             */

            public ArrayList<Gene> getCustomers() {
                  return customers;
            }

            public void setCustomers(ArrayList<Gene> customers) {
                  this.customers = customers;
            }

            public VehicleGene getVehicle() {
                  return vehicle;
            }

            public void setVehicle(VehicleGene vehicle) {
                  this.vehicle = vehicle;
            }
        }
}