package pl.vrp.swing.listener;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import pl.vrp.VrpRunner;
import pl.vrp.algorithm.GeneticAlgorithm;
import pl.vrp.algorithm.Problem;
import pl.vrp.algorithm.Solution;
import pl.vrp.algorithm.GA.operators.OperatorsAndParameters;
import pl.vrp.configuration.AlgorithmConfiguration;
import pl.vrp.configuration.ConfigurationHelper;
import pl.vrp.enums.ExecuteType;
import pl.vrp.exception.NoDataException;
import pl.vrp.exception.SliderValueException;
import pl.vrp.executor.CallbackExecutor;
import pl.vrp.executor.ExecuteValidator;
import pl.vrp.helper.DepotHelper;
import pl.vrp.helper.PopupHelper;
import pl.vrp.problem.CostMatrix;
import pl.vrp.problem.vrp.CustomerGene;
import pl.vrp.problem.vrp.DepotGene;
import pl.vrp.problem.vrp.VehicleGene;
import pl.vrp.solution.StopCondition;
import pl.vrp.swing.panel.AboutPanel;
import pl.vrp.swing.panel.CenterPanel;

public class VrpExecutor extends AbstractAction {

	private static final long serialVersionUID = -718233094274646297L;
	
	private CenterPanel centerPanel;
	private AlgorithmConfiguration agConfig;
	
    private int threadTick = 0;
     
    public VrpExecutor(CenterPanel centerPanel){
         this.centerPanel = centerPanel;
    }
     
    public void actionPerformed(ActionEvent e) {
    	Object eventSource = e.getSource();
    	try {
	    	if (eventSource.equals(this.centerPanel.getStartButton())){
	    		startAlgorithmEvent();
		    } else if ((eventSource instanceof JMenuItem)){
				menuItemEvent(eventSource);
		    } else if (eventSource.equals(this.centerPanel.getRefreshButton())){
		    	this.centerPanel.clearAllFields();
		    }
    	} catch (NoDataException | SliderValueException e1) {
    		PopupHelper.showError(this.centerPanel, e1.getMessage());
		} catch (ClassNotFoundException | IOException e1) {
			PopupHelper.showError(this.centerPanel, "Wystąpił problem w czasie przetwarzania pliku konfiguracyjnego.");
		}
	}
     
    private void startAlgorithmEvent() throws NoDataException, SliderValueException{
    		initializeAlgorithmConfiguration();
    		runAlgorithm();
    }
    
    private void menuItemEvent(Object eventSource) throws NoDataException, SliderValueException, ClassNotFoundException, IOException{
    	JMenuItem jMenuItem = (JMenuItem)eventSource;
    	String itemName = jMenuItem.getName();
        if(itemName.equals("SaveCFG")){
        	saveConfiguration();
        } else if(itemName.equals("LoadCFG")){
        	loadConfiguration();
        } else if(itemName.equals("AboutProgram")){
        	new AboutPanel().setVisible(true);
        } 
    }
    
    private void initializeAlgorithmConfiguration() throws NoDataException, SliderValueException {
    	agConfig = new AlgorithmConfiguration();
    	agConfig = ExecuteValidator.validateFileConfiguration(agConfig,centerPanel);
     	agConfig = ExecuteValidator.validateMDVRPConfiguration(agConfig,centerPanel);
        agConfig = ExecuteValidator.validateAGConfiguration(agConfig,centerPanel);
    }
    
    private void runAlgorithm() {
    	ExecuteType executeType = ConfigurationHelper.getExecuteType(agConfig.getFileType());
    	
    	if (executeType.equals(ExecuteType.RANDOMIZE)){
    		executeRandomizeOption();
        } else if (executeType.equals(ExecuteType.DIR_EXECUTE) ) {
        	executeDirOption();
    	} else {
    		executeFileOption();
        }
    	
    	this.centerPanel.enableExecutionButtons(false);
    }
     	
	private void saveConfiguration() throws NoDataException, SliderValueException, IOException{
    	initializeAlgorithmConfiguration();
    	FileOutputStream fos = null;
    	ObjectOutputStream oos = null;
    	
    	try {
    		JFileChooser fileChooser = new JFileChooser();
            int resultValue = fileChooser.showSaveDialog(centerPanel);
            if (resultValue == 0) {
            	fos = new FileOutputStream(fileChooser.getSelectedFile());
            	oos = new ObjectOutputStream(fos);
            	oos.writeObject(agConfig);
    			PopupHelper.showInfo(centerPanel, "Plik kofiguracyjny został zapisany." );
            }
		} finally {
			if(oos != null){
				oos.close();
			}
		}
    }
     
    private void loadConfiguration() throws IOException, ClassNotFoundException{
    	FileInputStream fis = null;
    	ObjectInputStream ois = null;

    	try {
    		JFileChooser fileChooser = new JFileChooser();

            int resultValue = fileChooser.showOpenDialog(centerPanel);
            if (resultValue == 0) {
            	fis = new FileInputStream(fileChooser.getSelectedFile());
    			ois = new ObjectInputStream(fis);
    			PopupHelper.showInfo(centerPanel, "Plik kofiguracyjny został wczytany." );
    			centerPanel.loadConfiguration((AlgorithmConfiguration) ois.readObject());
            }
		} finally {
			if(ois != null){
				ois.close();
			}
		}
    }
     
    public synchronized void enableItems(int threadId){
        this.threadTick += 1;
        if (this.threadTick > agConfig.getDepotCount() - 1) {
        	this.centerPanel.enableExecutionButtons(true);
        	this.threadTick = 0;
        }
    }
     
    private VrpRunner runMeasures(CostMatrix costMatrix, Integer nrRun, Integer depotCount){
    	agConfig.setDepotCount(depotCount);
    	return runMeasures(costMatrix, nrRun);
    }
     
    private VrpRunner runMeasures(CostMatrix costMatrix, Integer nrRun){
    	Problem problem = new Problem();
        GeneticAlgorithm ga = new GeneticAlgorithm(problem);
        Solution solution = new Solution();
        problem.setCostMatrix(costMatrix);
         
        float customerDemand = 1.0f;
        int nrCustomers = costMatrix.getSize()-1;
        for (int i = 1; i <= nrCustomers; i++) {
        	problem.addCustomer(new CustomerGene(i, customerDemand));
        }

        problem.addDepot(new DepotGene(0));
        int depotIndex = ((DepotGene)problem.getDepots().get(0)).getNode();
        
        for (int i = 1; i <= agConfig.getVehicleCount().intValue(); i++) {
        	problem.addVehicle(new VehicleGene(depotIndex, agConfig.getVehicleCapacity()));
        }
         
        OperatorsAndParameters operators = new OperatorsAndParameters(agConfig, costMatrix.getSize());
        ga.setOperatorsAndParameters(operators);
         
        StopCondition stop = new StopCondition();
        stop.setMaxNumGenerationsWtImprovement(agConfig.getStopValueCondition());
        solution.setStopCondition(stop);
         
        return new VrpRunner(ga, costMatrix, nrRun, agConfig.getStopValueCondition(), this.centerPanel);
    }
    
    private void executeRandomizeOption(){
    	DepotHelper depotHelper = new DepotHelper(agConfig);
    	this.centerPanel.getEvaluationGraph().createSeries(agConfig.getDepotCount());
     	
    	depotHelper.getDepotList().forEach(depot -> {
    		new CallbackExecutor().execute(runMeasures(new CostMatrix(depot), depot.getId()));
    	});
    }
     
    private void executeDirOption(){
    	String[] filePaths = this.centerPanel.getFileHelper().getFileAbsolutePaths();
    	this.centerPanel.getEvaluationGraph().createSeries(filePaths.length);
     	
    	for (int i = 0; i < filePaths.length; i++){
    		CostMatrix costMatrix = new CostMatrix(filePaths[i], agConfig.getFileType().measureByPosition());
     		new CallbackExecutor().execute(runMeasures(costMatrix, i, filePaths.length));
    	}
    }
     
    private void executeFileOption(){
    	String filePath = this.centerPanel.getFileHelper().getFileAbsolutePath();
    	this.centerPanel.getEvaluationGraph().createSeries(1);
     	
    	CostMatrix costMatrix = new CostMatrix(filePath, agConfig.getFileType().measureByPosition());
 		new CallbackExecutor().execute(runMeasures(costMatrix, 0));
    }
}
