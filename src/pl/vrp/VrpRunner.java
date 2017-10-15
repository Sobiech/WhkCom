package pl.vrp;

import javax.swing.JTextArea;

import pl.vrp.algorithm.GeneticAlgorithm;
import pl.vrp.algorithm.GA.Chromosome;
import pl.vrp.algorithm.GA.Population;
import pl.vrp.algorithm.GA.operators.OperatorsAndParameters;
import pl.vrp.algorithm.GA.operators.Replacement;
import pl.vrp.algorithm.GA.operators.Selection;
import pl.vrp.algorithm.GA.operators.mutation.SwapMutation;
import pl.vrp.algorithm.GA.phenotype.CapacitedTours;
import pl.vrp.enums.CrossoverOperator;
import pl.vrp.enums.MutationOperator;
import pl.vrp.enums.SelectionType;
import pl.vrp.executor.CallbackRunnable;
import pl.vrp.problem.CostMatrix;
import pl.vrp.swing.frame.DataFrame;
import pl.vrp.swing.graph.EvaluationGraph;
import pl.vrp.swing.listener.VrpExecutor;
import pl.vrp.swing.panel.CenterPanel;

public class VrpRunner implements CallbackRunnable {

	private Integer threadId;
	
    private static Population firstPopulation;
    private Population currentPopulation;
    
    private GeneticAlgorithm algorithm;
    private CostMatrix costMatrix;
    private Chromosome bestChromosome;
    
    private JTextArea loggerInfo;
    private EvaluationGraph evaluationGraph;
    private DataFrame dataFrame;
    private VrpExecutor startAction;
    
    private int currentGeneration = 0;
    private int bestChromosomeGeneration;
    private int numberOfUnimprovedGenerations;
    private int populationSize;
    
    private long acumulatedNrFitnessEvaluations = 0L;
    private long bestChromosomeNrFitnessEvaluations;
    
    private boolean tournamentSelection;
    private int selectionParam;

    private CrossoverOperator crossoverOperator;
    private float crossoverProbability;

    private MutationOperator mutationOperator;
    private float mutationProbability;

    private float elitism;
    
    
    public VrpRunner(GeneticAlgorithm geneticAlgorithm, CostMatrix costMatrix, Integer threadId, int stopValue, CenterPanel centerPanel) {
        this.threadId = threadId;
        
        this.loggerInfo = centerPanel.getLoggerInfo();
        this.evaluationGraph = centerPanel.getEvaluationGraph();
        this.startAction = centerPanel.getStartActionListener();
        
        OperatorsAndParameters operators = geneticAlgorithm.getOperatorsAndParameters();
        
        this.populationSize = operators.getPopulationSize();
        
        SelectionType selectionOperator = operators.getSelectionOperator();
        if (selectionOperator.equals(SelectionType.TOURNAMENT)) {
          this.tournamentSelection = true;
        }
        this.selectionParam = operators.getSelectionParam();
        
        crossoverOperator = operators.getCrossoverOperator();
        this.crossoverProbability = operators.getCrossoverProb();
        
        mutationOperator = operators.getMutationOperator();
        this.mutationProbability = operators.getMutationProb();
        
        this.elitism = operators.getReplacementElitism();
        
        this.algorithm = geneticAlgorithm;
        this.costMatrix = costMatrix;
        this.numberOfUnimprovedGenerations = stopValue;
    }
    
    public void run() {
        long start = System.currentTimeMillis();
        this.loggerInfo.append("Magazyn nr." + this.threadId + ": rozpoczêto znajdywanie tras.\n");
        
        this.currentPopulation = new Population(this.populationSize, this.costMatrix, this.algorithm);
        this.acumulatedNrFitnessEvaluations += this.currentPopulation.getNrFitnessEvaluations();
        
        try {
          firstPopulation = (Population)this.currentPopulation.clone();
        } catch (CloneNotSupportedException ex) {
          ex.printStackTrace();
        }
        
        this.bestChromosome = this.currentPopulation.getBestChromosome();
        
        float startBest = this.currentPopulation.getBestFitness();
        this.dataFrame = new DataFrame(String.valueOf(this.threadId), startBest, this.costMatrix.getSize()-1);
        this.dataFrame.setVisible(true);
        
        Population newPop = this.currentPopulation;
        long startIn = System.currentTimeMillis();
        
        do {
          printPopulationStatistics(this.currentPopulation, this.currentGeneration, System.currentTimeMillis() - startIn, this.acumulatedNrFitnessEvaluations);
          
          this.currentPopulation.resetNrFitnessEvaluations();
          this.currentGeneration += 1;
          
          startIn = System.currentTimeMillis();
          
          try {
            newPop = (Population)this.currentPopulation.clone();
          } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
          }
          
          Chromosome[] matingPool = new Chromosome[this.populationSize];
          
          for (int i = 0; i < this.populationSize; i += 2) {
            if (i + 2 > this.populationSize) {
                Chromosome parent = Selection.tournamentSelection(this.selectionParam, newPop);
                Chromosome child = SwapMutation.mutateChromosome(this.mutationProbability, parent);
                matingPool[i] = child;
            } else {
                Chromosome[] parents = new Chromosome[2];
                parents[0] = Selection.tournamentSelection(this.selectionParam, newPop);
                parents[1] = Selection.tournamentSelection(this.selectionParam, newPop);
                
                Chromosome[] childs = crossoverOperator.getChildChromosomes(parents, this.crossoverProbability);

                childs[0] = mutationOperator.mutateChromosome(this.mutationProbability, childs[0]);
                childs[1] = mutationOperator.mutateChromosome(this.mutationProbability, childs[1]);
                
                matingPool[i] = childs[0];
                matingPool[(i + 1)] = childs[1];
            }
          }
          
          newPop = Replacement.populationReplacement(matingPool, this.currentPopulation, this.elitism, this.costMatrix);
          
          keepBestChromosome(newPop, matingPool, this.currentGeneration);
          
          this.acumulatedNrFitnessEvaluations += newPop.getNrFitnessEvaluations();
          
          this.currentPopulation = newPop;
        } while ((nrUnimprovedGenerations(this.currentGeneration) < this.numberOfUnimprovedGenerations) && (this.acumulatedNrFitnessEvaluations < this.currentPopulation.getPopSize() * 50000));
        printFinalPopulationStats(this.currentPopulation, this.currentGeneration, start, this.acumulatedNrFitnessEvaluations);
    }
    
    
    public void printFinalPopulationStats(Population pop, int generation, long time, long evaluations){
        this.loggerInfo.append("\nMagazyn nr." + this.threadId + ": wyznaczanie tras zosta³o zakoñczone. Znaleziony dystans:"+pop.getTop(1)[0].getFitness()+".");
        
        this.dataFrame.getFinalInfoArea().append("Czas wykonania:" + (System.currentTimeMillis() - time) + " [ms] dla Magazynu nr:" + this.threadId + ".\n");
        this.dataFrame.getFinalInfoArea().append("Brak poprawy od pokolenia:" + this.bestChromosomeGeneration + "\n");
        this.dataFrame.getFinalInfoArea().append("Przebyty dystans:" + pop.getTop(1)[0].getFitness() + "\n");
        
        CapacitedTours tours = new CapacitedTours(this.bestChromosome);
        this.dataFrame.getFinalInfoArea().append("Znalezione marszruty:\n");
        this.dataFrame.getFinalInfoArea().append(tours.getVehicleTours());
        
        
        if(costMatrix.isCoordinateDataAvailable()){
	        dataFrame.showVehiclesTours(tours.getTours(), costMatrix, threadId);
    	}
        
        printPopulationStatistics(pop, generation, System.currentTimeMillis() - time, evaluations);
    }
    
    public void printPopulationStatistics(Population pop, int generation, long time, long evaluations) {
        float bestVal = pop.getBestFitness();
        float avgVal = pop.getAverageFitness();
        float worstVal = pop.getWorstFitness();
        
        this.dataFrame.setLabelValue(bestVal, avgVal, worstVal);
        this.dataFrame.setGeneration(generation);
        this.dataFrame.setTimeSpent(time);
        this.dataFrame.setNumberEvaluations(evaluations);
        if (generation % 50 == 0) {
          this.evaluationGraph.addSeriesPoints(pop.getBestFitness(), generation, this.threadId.intValue());
        }
    }
    
    private void keepBestChromosome(Population newPop, Chromosome[] matingPool, int generation) {
        if (newPop.getBestFitness() < this.bestChromosome.getFitness()) {
          this.bestChromosome = newPop.getBestChromosome();
          this.bestChromosomeGeneration = generation;
          this.bestChromosomeNrFitnessEvaluations = this.acumulatedNrFitnessEvaluations;
        }
    }
    
    public int nrUnimprovedGenerations(int generation)
    {
        return generation - this.bestChromosomeGeneration;
    }
    
    public Population getPopulation()
    {
        return this.currentPopulation;
    }
    
    public Population getFirstPopulation()
    {
        return firstPopulation;
    }
    
    public int getGeneration()
    {
        return this.currentGeneration;
    }
    
    public boolean isTournamentSelection()
    {
        return this.tournamentSelection;
    }
    
    public void setTournamentSelection(boolean tournamentSelection)
    {
        this.tournamentSelection = tournamentSelection;
    }
    
    public long getBestChromosomeNrFitnessEvaluations()
    {
        return this.bestChromosomeNrFitnessEvaluations;
    }
    
    public void setBestChromosomeNrFitnessEvaluations(long bestChromosomeNrFitnessEvaluations)
    {
        this.bestChromosomeNrFitnessEvaluations = bestChromosomeNrFitnessEvaluations;
    }
    
    public void callback()
    {
        this.startAction.enableItems(this.threadId.intValue());
    }
}
