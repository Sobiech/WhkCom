package pl.vrp.algorithm.GA.fitnessFunctions;

import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Gene;

public class SimpleVRP {
    
	public static float measureCost(Chromosome chr) {
        
		int nVehicles = chr.getNrVehicles();
        float[] vCosts = new float[nVehicles];
        int indexCurrentVehicle = 0;
        
        Gene firstVehicle = chr.getFirstVehicle();
        Gene currentVehicle = firstVehicle;
        
        Gene currentGene = firstVehicle;
        Gene nextGene = chr.getGeneAfter(firstVehicle);
        
        do {
          if (nextGene.getIsVehicle()){
            if (currentGene.getIsVehicle()) {
                vCosts[indexCurrentVehicle] = 0.0F;
            } else {
                vCosts[indexCurrentVehicle] += chr.getCost(currentGene, currentVehicle);
            }
            indexCurrentVehicle++;
            currentVehicle = nextGene;
          } else {
            vCosts[indexCurrentVehicle] += chr.getCost(currentGene, nextGene);
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
        
        return globalCost;
    }
}
