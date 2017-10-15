package pl.vrp.configuration;

import java.io.Serializable;

import pl.vrp.enums.CrossoverOperator;
import pl.vrp.enums.FileType;
import pl.vrp.enums.ReplacementType;
import pl.vrp.enums.MutationOperator;
import pl.vrp.enums.SelectionType;

public class AlgorithmConfiguration implements Serializable{
	private static final long serialVersionUID = -6513487053449720945L;

	public FileType fileType;
	public SelectionType selectionOperator; 
	public CrossoverOperator crossoverOperator; 
	public MutationOperator mutationOperator;
	public ReplacementType generationOperator;

	public String fileName;
	public String filePath;
	
	public Integer vehicleCount;
	public Integer vehicleCapacity;
	public Integer depotCount;
	public Integer populationSize;
	public Integer stopValueCondition;
	public Integer customerCount;
	
	public Float crossoverProbability;
	public Float mutationProbability;
	public Float replacementProbability;
	public Float depotPenalty;
	
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	public SelectionType getSelectionOperator() {
		return selectionOperator;
	}
	public void setSelectionOperator(SelectionType selectionOperator) {
		this.selectionOperator = selectionOperator;
	}
	public CrossoverOperator getCrossoverOperator() {
		return crossoverOperator;
	}
	public void setCrossoverOperator(CrossoverOperator crossoverOperator) {
		this.crossoverOperator = crossoverOperator;
	}
	public MutationOperator getMutationOperator() {
		return mutationOperator;
	}
	public void setMutationOperator(MutationOperator mutationOperator) {
		this.mutationOperator = mutationOperator;
	}
	public ReplacementType getGenerationOperator() {
		return generationOperator;
	}
	public void setGenerationOperator(ReplacementType generationOperator) {
		this.generationOperator = generationOperator;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getVehicleCount() {
		return vehicleCount;
	}
	public void setVehicleCount(Integer vehicleCount) {
		this.vehicleCount = vehicleCount;
	}
	public Integer getVehicleCapacity() {
		return vehicleCapacity;
	}
	public void setVehicleCapacity(Integer vehicleCapacity) {
		this.vehicleCapacity = vehicleCapacity;
	}
	public Integer getDepotCount() {
		return depotCount;
	}
	public void setDepotCount(Integer depotCount) {
		this.depotCount = depotCount;
	}
	public Integer getCustomerCount() {
		return customerCount;
	}
	public void setCustomerCount(Integer customerCount) {
		this.customerCount = customerCount;
	}
	public Integer getPopulationSize() {
		return populationSize;
	}
	public void setPopulationSize(Integer populationSize) {
		this.populationSize = populationSize;
	}
	public Integer getStopValueCondition() {
		return stopValueCondition;
	}
	public void setStopValueCondition(Integer stopValueCondition) {
		this.stopValueCondition = stopValueCondition;
	}
	public Float getCrossoverProbability() {
		return crossoverProbability;
	}
	public void setCrossoverProbability(Float crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}
	public Float getMutationProbability() {
		return mutationProbability;
	}
	public void setMutationProbability(Float mutationProbability) {
		this.mutationProbability = mutationProbability;
	}
	public Float getReplacementProbability() {
		return replacementProbability;
	}
	public void setReplacementProbability(Float replacementProbability) {
		this.replacementProbability = replacementProbability;
	}
	public Float getDepotPenalty() {
		return depotPenalty;
	}
	public void setDepotPenalty(Float depotPenalty) {
		this.depotPenalty = depotPenalty;
	}
	@Override
	public String toString() {
		return "AlgorithmConfiguration [fileType=" + fileType + ", selectionOperator=" + selectionOperator
				+ ", crossoverOperator=" + crossoverOperator + ", mutationOperator=" + mutationOperator
				+ ", generationOperator=" + generationOperator + ", fileName=" + fileName + ", vehicleCount="
				+ vehicleCount + ", vehicleCapacity=" + vehicleCapacity + ", depotCount=" + depotCount
				+ ", populationSize=" + populationSize + ", stopValueCondition=" + stopValueCondition
				+ ", customerCount=" + customerCount + ", crossoverProbability=" + crossoverProbability
				+ ", mutationProbability=" + mutationProbability + ", replacementProbability=" + replacementProbability
				+ ", depotPenalty=" + depotPenalty + "]";
	}
}
