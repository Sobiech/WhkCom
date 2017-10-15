package pl.vrp.executor;

import pl.vrp.configuration.AlgorithmConfiguration;
import pl.vrp.enums.CrossoverOperator;
import pl.vrp.enums.FileType;
import pl.vrp.enums.ReplacementType;
import pl.vrp.enums.MutationOperator;
import pl.vrp.enums.SelectionType;
import pl.vrp.exception.NoDataException;
import pl.vrp.exception.SliderValueException;
import pl.vrp.swing.combo.VrpComboBox;
import pl.vrp.swing.panel.CenterPanel;

public class ExecuteValidator {
	
	public static AlgorithmConfiguration validateAGConfiguration(AlgorithmConfiguration agConfig, CenterPanel centerPanel) 
			throws NoDataException, SliderValueException{ 
		//AG Configuration
    	VrpComboBox<SelectionType> selectionCombo = centerPanel.getSelectionCb();
        VrpComboBox<MutationOperator> mutationCombo = centerPanel.getMutationCb();
        VrpComboBox<CrossoverOperator> crossoverCombo = centerPanel.getCrossOverCb();
        VrpComboBox<ReplacementType> generationTypeCombo = centerPanel.getGenerationCb();
    	
    	agConfig.setSelectionOperator(selectionCombo.getSelectedNode());
        agConfig.setPopulationSize(centerPanel.getPopulationSizeField().getInteger());
        
     	agConfig.setCrossoverOperator(crossoverCombo.getSelectedNode());
        agConfig.setCrossoverProbability(centerPanel.getCrossOverProbSlider().getFloatValue());

     	agConfig.setMutationOperator(mutationCombo.getSelectedNode());
        agConfig.setMutationProbability(centerPanel.getMutationProbSlider().getFloatValue());

     	agConfig.setGenerationOperator(generationTypeCombo.getSelectedNode());
        agConfig.setReplacementProbability(centerPanel.getReplacementElitismSlider().getFloatValue());
            
        agConfig.setStopValueCondition(centerPanel.getStopConditionField().getInteger());
        agConfig.setDepotPenalty(centerPanel.getInnerDepotPenalty().getFloatValue());
	
        return agConfig;
	}
    
	public static AlgorithmConfiguration validateMDVRPConfiguration (AlgorithmConfiguration agConfig, CenterPanel centerPanel) 
			throws NoDataException, SliderValueException {
		agConfig.setVehicleCount(centerPanel.getNumberOfVehiclesField().getInteger());
        agConfig.setVehicleCapacity(centerPanel.getVehicleCapacityField().getInteger());
     	
        if (agConfig.getFileType().equals(FileType.RANDOMIZE)){
        	agConfig.setDepotCount(centerPanel.getNumberOfDepotsField().getInteger());
        	agConfig.setCustomerCount(centerPanel.getCustomersCountField().getInteger());
        }
        
        return agConfig;
	}
	
    public static AlgorithmConfiguration validateFileConfiguration(AlgorithmConfiguration agConfig, CenterPanel centerPanel) 
    		throws NoDataException {
    	
    	VrpComboBox<FileType> fileTypeCombo = centerPanel.getFileTypeCb();	
        
 		agConfig.setFileType(fileTypeCombo.getSelectedNode());
     	agConfig.setFileName(centerPanel.getFileNameField().getText());

     	if ((agConfig.getFileType().equals(FileType.RANDOMIZE)) && (agConfig.getFileName().equals(""))) {
        	throw new NoDataException("WprowadŸ nazwê pliku w celu zapisu !");
        }
        
     	if ((!centerPanel.getFileHelper().isFileSelected()) && (agConfig.getFileType().isFileNeeded())) {
        	throw new NoDataException("Nie wybrano pliku, dodaj plik i zatwierdz zmiany.");
      	}
     	
     	return agConfig;
    }
}
