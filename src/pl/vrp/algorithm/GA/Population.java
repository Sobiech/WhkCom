package pl.vrp.algorithm.GA;

import java.util.Arrays;
import java.util.Comparator;

import pl.vrp.algorithm.GeneticAlgorithm;
import pl.vrp.problem.CostMatrix;

public class Population
    implements Cloneable, Comparator<Chromosome>
{
    private Chromosome[] chromosomes;             // The effective population: an array of chromosomes
         private CostMatrix costMatrix;                  //The (very important) cost matrix
         private GeneticAlgorithm geneticAlgorithm;   // The chosen GA's operators and parameters

         // Creates a randomly generated population, given some population size (number
         // of chromosomes), some number of vehicles and the cost matrix.
         // All vehicles start (and end) from the first node of the cost matrix.
         public Population(int popSize, CostMatrix costMatrix, GeneticAlgorithm ga) {
             //Randomly generate the population
             this.chromosomes = generateChromosomes(popSize, costMatrix, ga); 
             this.costMatrix = costMatrix;
             this.geneticAlgorithm = ga;
             fitnessSort();  //Sort the population
         }
         
         // The population is given as an array of chromosomes. The rest 
         // (until popSize) is randomly generated.
         // All vehicles start (and end) from the first node of the cost matrix
         public Population(Chromosome[] chromosomes, int popSize, CostMatrix costMatrix, GeneticAlgorithm ga) {
             this.chromosomes = generateMissingChromosomes(chromosomes, popSize, costMatrix, ga);
             this.costMatrix = costMatrix;
             this.geneticAlgorithm = ga;
             verifyNrVehicles(chromosomes[0]);
             fitnessSort(); //Sort the population
         }
         
         // Not enought? Yet another constructor
         // This time the population is given as two arrays of chromosomes that are merged. 
         // All vehicles start (and end) from the first node of the cost matrix.
         public Population(Chromosome[] newChromosomes, Chromosome[] elitistChromosomes, CostMatrix costMatrix, GeneticAlgorithm ga) {
             Chromosome[] concatChromosomes = mergeChromosomes(newChromosomes, elitistChromosomes);
             this.chromosomes = concatChromosomes;
             this.costMatrix = costMatrix;
             this.geneticAlgorithm = ga;
             fitnessSort(); //Sort the population
         }
         
         
         // Create a population of random chromosomes
         private Chromosome[] generateChromosomes(int popSize, CostMatrix costMatrix, GeneticAlgorithm ga){
             chromosomes = new Chromosome[popSize];
             // Construct each chromosome
             for (int i=0; i < popSize; i++) {
                   chromosomes[i] = new Chromosome(costMatrix, ga); // Call the constructor
             }
             return chromosomes;
         }
         
         // Create the missing chromosomes of some array
         private Chromosome[] generateMissingChromosomes(Chromosome[] chromosomes, int popSize, CostMatrix costMatrix, GeneticAlgorithm ga) {
             Chromosome[] newChromosomes;    //A new array of chromosomes to create a new population

             if (chromosomes.length<popSize) { // In this case we have to generate the rest of the chromosomes
                   newChromosomes = Arrays.copyOf(chromosomes, popSize);   //Copy the given chromosomes
                   for (int i=chromosomes.length; i < popSize; i++) {    // Randomly generate the rest
                         newChromosomes[i] = new Chromosome(costMatrix, ga); // Call the constructor
                   }
             } else if (chromosomes.length == popSize) {
                   newChromosomes=chromosomes;
             } else { // chromosomes.length>popSize
                   //Just in case of some mistake on the popSize value
                   System.out.println("[ERROR] Trying to create a population with a smaller size "
                             + "than the array of chromosomes. Using the size of the array."); 
                   newChromosomes=chromosomes;
             }
             return newChromosomes;
         }
         
         // Merge chromosomes from two arrays
         private Chromosome[] mergeChromosomes(Chromosome[] newChromosomes, Chromosome[] elitistChromosomes) {
             // A new array of chromosomes to create a new population
             // Initialy made by copying the first array of chromosomes
             Chromosome[] concatChromosomes = Arrays.copyOf(newChromosomes, newChromosomes.length + elitistChromosomes.length);
             // Next copy all the chromosomes from the second array
             for (int i=newChromosomes.length, j=0; i < newChromosomes.length + elitistChromosomes.length; i++, j++) {
                   concatChromosomes[i] = elitistChromosomes[j]; // Copy the chromosome
             }
             return concatChromosomes;
         }

         
         // Sort the population's chromosomes by cost ascending (the best fitness is the least cost value)
         private boolean fitnessSort() {
             // This class implements a comparator
             Arrays.sort(chromosomes, this);
             return true;    // Now the chromosomes are sorted
         }

         // Get the best n elements of the population
         public Chromosome[] getTop(int n) {
             Chromosome[] best;
             fitnessSort();   // Sort the population.

             // Now copy the best n elements to 'best'
             best = Arrays.copyOfRange(this.chromosomes, 0, n);
             return (best);
         }
         
         // Get the best element of the population
         public Chromosome getBestChromosome(){
             return getTop(1)[0];
         }
         
         
         /*
          * Returns the number of times that the Fitness evaluation function was called
          * on this population.
          */
         public long getNrFitnessEvaluations(){
             long totalFitnessEvaluations=0;
             for (Chromosome chr:chromosomes){
                   totalFitnessEvaluations += chr.getNrFitnessEvaluations();
             }
             return totalFitnessEvaluations;
         }
         
         /*
          * Resets the number of times that the Fitness evaluation function was called
          * on this population.
          */
         public void resetNrFitnessEvaluations(){
             for (Chromosome chr:chromosomes){
                   chr.resetNrFitnessEvaluations();
             }
         }
         
         /*
          * Based on the GA definition is the number of vehicles of the chromosome correct?
          */
         public final void verifyNrVehicles(Chromosome chr){
             if (chr.getNrVehicles()!=chr.countVehicles()){
                   throw new AssertionError("[ERROR] The number of vehicles of the "
                         + "chromosome is different from the GeneList.");
             }
         }
         
         // We need to implement this method for sorting
         @Override
         public int compare(Chromosome chr1, Chromosome chr2) {
             return chr1.compareTo(chr2);
         }
         
         // Clone method
         @Override
         public Object clone() throws CloneNotSupportedException {

             Chromosome[] newChromosomes = new Chromosome[popSize()];
             for (int i=0;i<popSize();i++){
                   newChromosomes[i]=(Chromosome)chromosomes[i].clone();
             }
             Population newPop = new Population(newChromosomes, popSize(), costMatrix, geneticAlgorithm);
             
             return newPop;
         }
         
         public int popSize(){
             return chromosomes.length;
         }
         
         public Chromosome[] getChromosomes() {
             return chromosomes;
         }
         
         public Chromosome[] getSortedChromosomes() {
             fitnessSort();
             return chromosomes;
         }
         
         /*
          * Returns the lenght of the chromosomes
          */
         public int getChromosomeLenght(){
             return this.chromosomes[0].getLenght();
         }
         
         public float getBestFitness() {
             fitnessSort();
             // The best chromosome is the first
             return this.chromosomes[0].getFitness();
         }
         
         public float getAverageFitness() {
             fitnessSort();
             // Average fitness value
             double totalFitness=0;
             for (int i=0;i<chromosomes.length;i++){
                   totalFitness+=chromosomes[i].getFitness();
             }
             return (float) totalFitness/chromosomes.length; // The average fitness
         }
         
         public float getMedianFitness(){
             float medianFitness;
             // Median fitness value
             //If the number of chromosomes in the population is odd, just return the 
             //fitness value of the middle chromosome. If it is even, return the average 
             //of the two middle values.
             if (chromosomes.length%2!=0){ // Its odd
                   //Return the middle chromosome's fitness value
                   medianFitness = chromosomes[chromosomes.length/2].getFitness();
             }
             else {  // Its even
                   int topMiddle=(chromosomes.length/2)-1;
                   int bottomMiddle=chromosomes.length/2;
                   medianFitness = (chromosomes[topMiddle].getFitness()+chromosomes[bottomMiddle].getFitness())/2;
             }
             return medianFitness;
         }

         public float getWorstFitness() {
             fitnessSort();
             // The worst chromosome is the last
             return this.chromosomes[chromosomes.length-1].getFitness();  
         }

         // The size (number of chromossomes) of the population 
         public int getPopSize() {
             return chromosomes.length;
         }

         public GeneticAlgorithm getGeneticAlgorithm() {
             return geneticAlgorithm;
         }
}
