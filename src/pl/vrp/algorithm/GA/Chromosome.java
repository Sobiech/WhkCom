package pl.vrp.algorithm.GA;

import java.util.ArrayList;
import java.util.Arrays;

import pl.vrp.algorithm.GeneticAlgorithm;
import pl.vrp.algorithm.GA.fitnessFunctions.CapacitedVRP;
import pl.vrp.algorithm.GA.operators.OperatorsAndParameters;
import pl.vrp.algorithm.GA.support.ProbabilityMathHelper;
import pl.vrp.problem.CostMatrix;

public class Chromosome
    implements Cloneable, Comparable<Chromosome>
{
    private CostMatrix costMatrix;
    private Gene[] genes;
    private GeneticAlgorithm geneticAlgorithm;
    private float fitness;
    private boolean isFitnessOutdated = true;
    private int nrFitnessEvaluations = 0;
    private float[] vehiclesCosts;
    
    public Chromosome(CostMatrix dm, GeneticAlgorithm ga)
    {
        this.costMatrix = dm;
        this.geneticAlgorithm = ga;
        this.genes = generateRandomChromosome(ga);
        verifyGenes();
    }
    
    public Chromosome(Gene[] genes, CostMatrix costMatrix, float fitness, float[] vehiclesFitness, GeneticAlgorithm ga)
    {
        this.geneticAlgorithm = ga;
        this.costMatrix = costMatrix;
        this.genes = genes;
        this.fitness = fitness;
        this.vehiclesCosts = vehiclesFitness;
    }
    
    public Chromosome(Gene[] genes, CostMatrix dm, GeneticAlgorithm ga)
    {
        this.geneticAlgorithm = ga;
        this.costMatrix = dm;
        this.genes = genes;
        verifyGenes();
    }
    
    public final void verifyGenes()
    {
        ArrayList<Gene> genesList = new ArrayList<Gene>(Arrays.asList(this.genes));
        for (Gene gene : this.geneticAlgorithm.getGenes())
        {
          if (!genesList.contains(gene)) {
            throw new AssertionError("There's a gene missing!");
          }
          genesList.remove(gene);
        }
        if (!genesList.isEmpty()) {
          throw new AssertionError("There are duplicated genes!");
        }
        if (this.costMatrix.getSize() + this.geneticAlgorithm.getNrVehicles() - this.geneticAlgorithm.getNrDepots() != this.genes.length) {
          throw new AssertionError("Some genes are missing in the chromosome!");
        }
    }
    
    private Gene[] generateRandomChromosome(GeneticAlgorithm ga)
    {
        ArrayList<Gene> temp_chr = ga.getClonedGenes();
        
        int size = ga.getNrGenes();
        
        ArrayList<Gene> random_chr = new ArrayList<Gene>(size);
        for (int i = 0; i < size; i++) {
          random_chr.add((Gene)temp_chr.remove(ProbabilityMathHelper.randomInt(size - i)));
        }
        Gene[] chromossome = new Gene[size];
        return (Gene[])random_chr.toArray(chromossome);
    }
    
    public int countVehicles()
    {
        int count = 0;
        for (int i = 0; i < this.genes.length; i++) {
          if (this.genes[i].getIsVehicle()) {
            count++;
          }
        }
        return count;
    }
    
    private float measureFitness()
    {
        float cvrpCost = CapacitedVRP.measureCost(this, this.geneticAlgorithm.getOperatorsAndParameters().getInnerDepotPenalty());
        return cvrpCost;
    }
    
    public float getCost(Gene geneA, Gene geneB)
    {
        int nodeA = geneA.getNode();
        int nodeB = geneB.getNode();
        return getCost(nodeA, nodeB);
    }
    
    public float getCost(int nodeA, int nodeB){
        return this.costMatrix.getCost(nodeA, nodeB);
    }
    
    public void updateFitness(){
        if (this.isFitnessOutdated)
        {
          this.fitness = measureFitness();
          this.isFitnessOutdated = false;
        }
    }
    
    public void resetNrFitnessEvaluations() {
        this.nrFitnessEvaluations = 0;
    }
    
    public int incrementFitnessEvaluations() {
        this.nrFitnessEvaluations += 1;
        return this.nrFitnessEvaluations;
    }
    
    public void swapGenes(int a, int b) {
        if ((a < 0) || (b < 0) || (a > this.genes.length) || (b > this.genes.length)) {
          throw new AssertionError("[ERROR] Cannot swap genes because one of them is out of the chromosome!");
        }
        Gene gene1 = this.genes[a];
        Gene gene2 = this.genes[b];
        setGene(gene2, a);
        setGene(gene1, b);
    }
    
    public void swapGenes(Gene a, Gene b){
        int indexA = indexOf(a);
        int indexB = indexOf(b);
        if (indexA == -1) {
          throw new AssertionError("[ERROR] The gene " + a.toString() + " doesn't exist on the chromosome " + toString());
        }
        if (indexB == -1) {
          throw new AssertionError("[ERROR] The gene " + b.toString() + " doesn't exist on the chromosome " + toString());
        }
        setGene(b, indexA);
        setGene(a, indexB);
    }
    
    public void swapWithNextGene(Gene gene){
        int indexGene = indexOf(gene);
        if (indexGene == -1) {
          throw new AssertionError("[ERROR] The gene " + gene.toString() + " doesn't exist on the chromosome " + toString());
        }
        if (indexGene == getLenght() - 1) {
          swapGenes(indexGene, 0);
        } else {
          swapGenes(indexGene, indexGene + 1);
        }
    }
    
    public int indexOf(Gene g) {
        int index = -1;
        for (int i = 0; i < getLenght(); i++) {
          if (this.genes[i].equals(g)) {
            return index = i;
          }
        }
        return index;
    }
    
    public int indexFirstVehicle() {
        for (int i = 0; i < this.genes.length; i++) {
          if (this.genes[i].getIsVehicle()) {
            return i;
          }
        }
        throw new AssertionError("ERROR: The array of genes doesn't have any vehicles!");
    }
    
    public Gene getFirstVehicle(){
        return getGene(indexFirstVehicle());
    }
    
    public boolean hasGene(Gene g) {
        for (int i = 0; i < this.genes.length; i++) {
          if (this.genes[i] != null)
          {
            if (this.genes[i] == g) {
                return true;
            }
          }
          else if ((this.genes[i] == null) && (g == null)) {
            return true;
          }
        }
        return false;
    }
    
    public String print() {
        String gene = "";
        for (int i = 0; i < this.genes.length; i++) {
          gene = gene + this.genes[i].toString() + " ";
        }
        String text = "Genes: [ " + gene + "]";
        text = text + "\n Fitness: " + Float.toString(getFitness());
        String vFitness = "";
        for (int i = 0; i < this.vehiclesCosts.length; i++) {
          vFitness = vFitness + Float.toString(this.vehiclesCosts[i]) + " ";
        }
        text = text + "\n Vehicles fitness: " + vFitness;
        System.out.println(text);
        return text;
    }
    
    public String toString() {
        String chrPrint = "[";
        for (int i = 0; i < this.genes.length; i++) {
          chrPrint = chrPrint + this.genes[i].getNode() + "|";
        }
        chrPrint = chrPrint + "]";
        return chrPrint;
    }
    
    public int compareTo(Chromosome chr) {
        return Float.compare(getFitness(), chr.getFitness());
    }
    
    public Object clone()
        throws CloneNotSupportedException {
    	
        Gene[] newGenes = new Gene[this.genes.length];
        System.arraycopy(this.genes, 0, newGenes, 0, this.genes.length);
        Chromosome newChromosome = new Chromosome(newGenes, this.costMatrix, this.fitness, this.vehiclesCosts, this.geneticAlgorithm);
        
        return newChromosome;
    }
    
    public Gene getGeneBefore(Gene g)  {
        int index = indexOf(g);
        if (index == 0) {
          return getGene(getLenght() - 1);
        }
        return getGene(index - 1);
    }
    
    public Gene getGeneBefore(int index) {
        if (index == 0) {
          return getGene(getLenght() - 1);
        }
        return getGene(index - 1);
    }
    
    public Gene getGeneAfter(Gene g) {
        int index = indexOf(g);
        if (index >= getLenght() - 1) {
          return getGene(0);
        }
        return getGene(index + 1);
    }
    
    public Gene getGeneAfter(int index)  {
        if (index >= getLenght() - 1) {
          return getGene(0);
        }
        return getGene(index + 1);
    }
    
    public float getFitness() {
        updateFitness();
        return this.fitness;
    }
    
    public Gene[] getGenes(){
        return this.genes;
    }
    
    public Gene getGene(int i) {
        return this.genes[i];
    }
    
    public void setGene(Gene g, int i)
    {
        this.genes[i] = g;
        
        this.isFitnessOutdated = true;
    }
    
    public int getLenght()
    {
        return this.genes.length;
    }
    
    public int getNrVehicles()
    {
        return this.geneticAlgorithm.getNrVehicles();
    }
    
    public int getNrCustomers()
    {
        return this.geneticAlgorithm.getNrCustomers();
    }
    
    public int getNrDepots()
    {
        return this.geneticAlgorithm.getNrDepots();
    }
    
    public float[] getVehiclesFitness()
    {
        updateFitness();
        return this.vehiclesCosts;
    }
    
    public void setVehiclesCosts(float[] vehiclesCosts)
    {
        this.vehiclesCosts = vehiclesCosts;
    }
    
    public CostMatrix getCostMatrix()
    {
        return this.costMatrix;
    }
    
    public int getNrFitnessEvaluations()
    {
        return this.nrFitnessEvaluations;
    }
    
    public OperatorsAndParameters getOperators()
    {
        return this.geneticAlgorithm.getOperatorsAndParameters();
    }
    
    public GeneticAlgorithm getGeneticAlgorithm()
    {
        return this.geneticAlgorithm;
    }
}
