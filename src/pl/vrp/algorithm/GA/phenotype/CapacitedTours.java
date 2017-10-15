package pl.vrp.algorithm.GA.phenotype;

import java.util.ArrayList;
import pl.vrp.algorithm.GA.phenotype.Tours;
import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Gene;
import pl.vrp.problem.vrp.CustomerGene;
import pl.vrp.problem.vrp.VehicleGene;


public class CapacitedTours extends Tours{
        
        public CapacitedTours(Chromosome chr){
            tourList = measureTours(chr);
        }
        
        private ArrayList<Tour> measureTours(Chromosome chromosome){

            ArrayList<Gene> currentCustomers = new ArrayList<Gene>();
            
            Gene currentGene = chromosome.getFirstVehicle();
            Gene nextGene = chromosome.getGeneAfter(currentGene);
            
            VehicleGene firstVehicle = (VehicleGene)chromosome.getFirstVehicle();
            VehicleGene currentVehicle = firstVehicle;
            
            do {
            	if (nextGene.getIsVehicle()){
		            ArrayList<Gene> repairedCustomerList = repairCustomerList(currentVehicle, currentCustomers);
		            tourList.add(this.new Tour(currentVehicle, repairedCustomerList));
		            // Assign the new currentVehicle and clean the currentCustomers
		            currentVehicle=(VehicleGene)nextGene;
		            currentCustomers.clear();
		        } else {
	                currentCustomers.add((CustomerGene)nextGene);
		        }
              currentGene = nextGene;
              nextGene = chromosome.getGeneAfter(currentGene);
                  
            } while (currentGene != firstVehicle);
            
            return tourList;
        }
        
        private ArrayList<Gene> repairCustomerList(VehicleGene currentVehicle, ArrayList<Gene> currentCustomers){
            float vehicleCapacity = currentVehicle.getCapacity();   
            float currentLoad = 0;                                           
            ArrayList<Gene> repairedCurrentCustomers = new ArrayList<Gene>(currentCustomers);  
            
            int j=0,i=0;

            for(Gene gene : currentCustomers){
              float currentDemand = gene.getSize();
              currentLoad += currentDemand;                                         
              if (currentLoad > vehicleCapacity){
                    repairedCurrentCustomers.add(i+j, (Gene)currentVehicle);
                    currentLoad = currentDemand;
                    j++;
              }
              i++;
            }
            
            return repairedCurrentCustomers;
        }
        
}
