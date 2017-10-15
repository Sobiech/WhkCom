package pl.vrp.algorithm.GA.fitnessFunctions;

import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Gene;

public class CapacitedVRP {
    float innerDepotPenalty = 0.0F;
    
    public CapacitedVRP(float innerDepotPenalty) {
        this.innerDepotPenalty = innerDepotPenalty;
    }
    
    public float measureCost(Chromosome chr) {
        return measureCost(chr, this.innerDepotPenalty);
    }
    
    public static float measureCost(Chromosome chr, float innerDepotPenalty) {
        int nVehicles = chr.getNrVehicles();
        float[] vCosts = new float[nVehicles];
        int indexCurrentVehicle = 0;
        
        Gene firstVehicle = chr.getFirstVehicle();
        Gene currentVehicle = firstVehicle;
        float currentVehicleCapacity = currentVehicle.getSize();
        float currentVehicleLoad = 0.0F;
        
        Gene currentGene = firstVehicle;
        Gene nextGene = chr.getGeneAfter(firstVehicle);
        
        int nrDepotsInsideTour = 0;
        
        do {
          if (nextGene.getIsVehicle()) {
            if (currentGene.getIsVehicle()) {
                vCosts[indexCurrentVehicle] = 0.0F;
            } else {
                vCosts[indexCurrentVehicle] += chr.getCost(currentGene, currentVehicle);
            }
            indexCurrentVehicle++;
            currentVehicle = nextGene;
            currentVehicleCapacity = currentVehicle.getSize();
            currentVehicleLoad = 0.0F;
          } else {
        	  
            float futureLoad = currentVehicleLoad + nextGene.getSize();
            if (futureLoad > currentVehicleCapacity) {
                
            	vCosts[indexCurrentVehicle] += chr.getCost(currentGene, currentVehicle);
                vCosts[indexCurrentVehicle] += chr.getCost(currentVehicle, nextGene);
                
                currentVehicleLoad = nextGene.getSize();
                nrDepotsInsideTour++;
            } else {
                vCosts[indexCurrentVehicle] += chr.getCost(currentGene, nextGene);
                currentVehicleLoad = futureLoad;
            }
          }
          currentGene = nextGene;
          nextGene = chr.getGeneAfter(currentGene);
          
        } while (currentGene != firstVehicle);
        
        chr.setVehiclesCosts(vCosts);
        float globalCost = 0.0F;
        for (int i = 0; i < vCosts.length; i++) {
          globalCost += vCosts[i];
        }
        chr.incrementFitnessEvaluations();
        
        globalCost *= (1.0F + nrDepotsInsideTour * innerDepotPenalty);
        
        return globalCost;
    }
}
