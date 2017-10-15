package pl.vrp.swing.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartPanel;

import pl.vrp.configuration.AlgorithmConfiguration;
import pl.vrp.enums.CrossoverOperator;
import pl.vrp.enums.FileType;
import pl.vrp.enums.MutationOperator;
import pl.vrp.enums.ReplacementType;
import pl.vrp.enums.SelectionType;
import pl.vrp.helper.FileTypeHelper;
import pl.vrp.swing.combo.VrpComboBox;
import pl.vrp.swing.frame.MainFrame;
import pl.vrp.swing.graph.EvaluationGraph;
import pl.vrp.swing.listener.VrpExecutor;
import pl.vrp.swing.slider.FloatSlider;
import pl.vrp.swing.textfield.TextField;

public class CenterPanel extends FormPanel{
    
	private static final long serialVersionUID = -7643173817903166672L;
    
	private FloatSlider mutationProbSlider;
	private FloatSlider crossOverProbSlider;
	private FloatSlider replacementElitismSlider;
    private FloatSlider innerDepotPenalty;
	
    private TextField numberOfDepotsField;
    private TextField numberOfVehiclesField;
    private TextField vehicleCapacityField;
    private TextField populationSizeField;
    private TextField stopConditionField;
    private TextField fileNameField;
    private TextField customersCountField;
    
    private EvaluationGraph evaluationGraph;
    
    private JTextArea loggerInfo;
    private FileTypeHelper fileHelper;
    
    private JButton startButton;
    private JButton fileButton;
    private JButton refreshButton;
    
    private VrpComboBox<SelectionType> selectionCb;
    private VrpComboBox<MutationOperator> mutationCb;
    private VrpComboBox<CrossoverOperator> crossOverCb;
    private VrpComboBox<ReplacementType> generationCb;
    private VrpComboBox<FileType> fileTypeCb;
    
    private JLabel fileNameLabel;
    
    private VrpExecutor startActionListener;
    
    private boolean isRandomized = false;
    
    private MainFrame frame;
    
    public CenterPanel(MainFrame frame){
        super("10px, pref:grow, 10px, 170px, 10px, 170px,10px", "10px, 25px, 20px, 27px, 15px, 27px, 15px, 27px, 30px, 25px, 20px, 27px, 15px, 27px, 15px, 27px, 15px, 27px, 15px, 27px, 20px, 25px, 10px, 20px, 10px, 20px, 10px, 20px, pref:grow, 10px");
        this.frame = frame;
        initFileChooser();
        initButtons();
        initTextFields();
        initComboBox();
        initPanels();
        setFileTypeComponentsVisible(this.isRandomized);
    }
    
    private void initFileChooser(){
        this.fileHelper = new FileTypeHelper(this);
        this.fileNameLabel = this.fileHelper.getFileNameLabel();
    }
    
    private void initButtons(){
        this.startActionListener = new VrpExecutor(this);
        this.startButton = createButton("Uruchom algorytm");
        this.refreshButton = createButton("Wyczyść dane");
        
        this.fileButton = this.fileHelper.getFileButton();
    }
    
    private void initTextFields(){
        this.fileNameField = new TextField(4, 4, "Nazwa pliku", "Wprowadź nazwę pliku by zapisać dane.", this);
        
        this.numberOfVehiclesField = new TextField(4, 6, "Ilość cięzarówek", "Podaj ilość ciężarówek.", this);
        this.vehicleCapacityField = new TextField(6, 6, "Ładownosć ciężarówki", "Podaj Ładowność ciężarówki.", this);

        this.numberOfDepotsField = new TextField(4, 8, "Ilość magazynów", "Wprowadź ilość magazynów.", this);
        this.customersCountField = new TextField(6, 8, "Ilość klientów", "Wprowadź ilość klientów", this);

        this.populationSizeField = new TextField(6, 12, "Wielkość populacji", "Podaj wielkość populacji GA.", this);
        this.crossOverProbSlider = new FloatSlider(6,14," Prawdopodobieństwo krzyżowania");
        this.mutationProbSlider = new FloatSlider(6, 16, " Prawdopodobieństwo mutacji");
        this.replacementElitismSlider = new FloatSlider(6,18," Prawdopodobieństwo zastępowania");
        this.innerDepotPenalty = new FloatSlider(6, 20, " Wspolczynnik kary");
        this.stopConditionField = new TextField(4, 20, "Warunek stopu", "Podaj warunek stopu GA.", this);
    }
    
    private void initChart(){
        this.evaluationGraph = new EvaluationGraph();
        addComponent(this.evaluationGraph.getChartPanel(), 2, 2, Integer.valueOf(1), Integer.valueOf(21));

    }
    
	private void initComboBox(){
        this.selectionCb = new VrpComboBox<SelectionType>(SelectionType.values(),"typ selekcji");
        this.mutationCb = new VrpComboBox<MutationOperator>(MutationOperator.values(),"typ mutacji");
        this.crossOverCb = new VrpComboBox<CrossoverOperator>(CrossoverOperator.values(),"typ krzyżowania");
        this.generationCb = new VrpComboBox<ReplacementType>(ReplacementType.values(),"typ generacji");
        this.fileTypeCb = new VrpComboBox<FileType>(FileType.values(),"typ pliku");
        
        this.fileTypeCb.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e)
          {
            if (e.getStateChange() == 1)
            {
                Object item = e.getItem();
                boolean componentVisible = ((item instanceof FileType)) && (item.equals(FileType.RANDOMIZE));
                CenterPanel.this.setFileTypeComponentsVisible(componentVisible);
            }
          }
        });
        addComponent(this.fileTypeCb, 6, 4, Integer.valueOf(1));
        addComponent(this.selectionCb, 4, 12, Integer.valueOf(1));
        addComponent(this.crossOverCb, 4, 14, Integer.valueOf(1));
        addComponent(this.mutationCb, 4, 16, Integer.valueOf(1));
        addComponent(this.generationCb, 4, 18, Integer.valueOf(1));
    }
    
    private void initPanels(){
        addComponent(new TitlePanel("Dane MDVRP:"), 4, 2, Integer.valueOf(3));
        addComponent(this.fileButton, 4, 4, Integer.valueOf(1));
        
        addComponent(new TitlePanel("Konfiguracja AG:"), 4, 10, Integer.valueOf(3));
        addTextFields();
        initChart();
        addComponent(getInfoAreaScrollPane(), 2, 24, Integer.valueOf(1), Integer.valueOf(6));
        
        addComponent(new TitlePanel("Runtime:"), 4, 22, Integer.valueOf(3));
        addComponent(this.fileNameLabel, 4, 24, Integer.valueOf(2));
        addComponent(this.refreshButton, 4, 26, Integer.valueOf(3));
        addComponent(this.startButton, 4, 28, Integer.valueOf(3));
        
        setBackground(Color.WHITE);
    }
    
    private JScrollPane getInfoAreaScrollPane(){
        this.loggerInfo = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(this.loggerInfo);
        this.loggerInfo.setEditable(false);
        
        return scrollPane;
    }
    
    private void addTextFields(){
        addComponent(this.numberOfDepotsField, this.numberOfDepotsField.getPosX(), this.numberOfDepotsField.getPosY(), Integer.valueOf(1));
        addComponent(this.numberOfVehiclesField, this.numberOfVehiclesField.getPosX(), this.numberOfVehiclesField.getPosY(), Integer.valueOf(1));
        addComponent(this.vehicleCapacityField, this.vehicleCapacityField.getPosX(), this.vehicleCapacityField.getPosY(), Integer.valueOf(1));
        addComponent(this.stopConditionField, this.stopConditionField.getPosX(), this.stopConditionField.getPosY(), Integer.valueOf(1));
        addComponent(this.populationSizeField, this.populationSizeField.getPosX(), this.populationSizeField.getPosY(), Integer.valueOf(1));
        
        addComponent(this.crossOverProbSlider, this.crossOverProbSlider.getPosX(), this.crossOverProbSlider.getPosY(), Integer.valueOf(1));
        addComponent(this.mutationProbSlider, this.mutationProbSlider.getPosX(), this.mutationProbSlider.getPosY(),Integer.valueOf(1));
        
        addComponent(this.replacementElitismSlider, this.replacementElitismSlider.getPosX(), this.replacementElitismSlider.getPosY(), Integer.valueOf(1));
        addComponent(this.innerDepotPenalty, this.innerDepotPenalty.getPosX(), this.innerDepotPenalty.getPosY(), Integer.valueOf(1));
        addComponent(this.fileNameField, this.fileNameField.getPosX(), this.fileNameField.getPosY(), Integer.valueOf(1));
        addComponent(this.customersCountField, this.customersCountField.getPosX(), this.customersCountField.getPosY(), Integer.valueOf(1));
    }
    
    private void setFileTypeComponentsVisible(boolean bool){
        this.fileButton.setVisible(!bool);
        this.fileNameField.setVisible(bool);
        this.fileNameLabel.setVisible(!bool);
        this.numberOfDepotsField.setVisible(bool);
        this.customersCountField.setVisible(bool);
        setRandomized(bool);
    }
    
    private JButton createButton(String name){
        JButton jb = new JButton(name);
        jb.addActionListener(this.startActionListener);
        return jb;
    }
    
    public void enableExecutionButtons(boolean bool){
        getStartButton().setEnabled(bool);
        getRefreshButton().setEnabled(bool);
    }
    
    public void clearAllFields(){
        Component[] arrayOfComponent;
        int j = (arrayOfComponent = getComponents()).length;
        for (int i = 0; i < j; i++)
        {
          Component cmp = arrayOfComponent[i];
          if ((cmp instanceof VrpComboBox)) {
            ((VrpComboBox<?>)cmp).setSelectedIndex(0);
          } else if ((cmp instanceof TextField)) {
            ((TextField)cmp).setText("");
          } else if ((cmp instanceof ChartPanel)) {
        	  this.remove(this.evaluationGraph.getChartPanel());
        	  initChart();
        	  this.repaint();
        	  this.frame.getContentPane().repaint();
        	  this.frame.revalidate();
        	  this.frame.repaint();
          }
        }
    }
    
    public void loadConfiguration(AlgorithmConfiguration agConfig){
    	
    	 FileType choosenFileType = agConfig.getFileType();
		 fileTypeCb.setSelectedItem(choosenFileType);
    	 
    	 if(choosenFileType.equals(FileType.RANDOMIZE)){
    		 fileNameField.setText(agConfig.fileName);
    		 numberOfDepotsField.setText(agConfig.getDepotCount().toString());
    		 customersCountField.setText(agConfig.getCustomerCount().toString());
    	 }
    	 
		 numberOfVehiclesField.setText(agConfig.getVehicleCount().toString());
		 vehicleCapacityField.setText(agConfig.getVehicleCapacity().toString());
		 populationSizeField.setText(agConfig.getPopulationSize().toString());
		 
		 crossOverProbSlider.setValue((int)(agConfig.getCrossoverProbability()*100));
		 mutationProbSlider.setValue((int)(agConfig.getMutationProbability()*100));
		 replacementElitismSlider.setValue((int)(agConfig.getReplacementProbability()*100));
		 
		 innerDepotPenalty.setValue((int)(agConfig.getDepotPenalty()*100));
		 stopConditionField.setText(agConfig.getStopValueCondition().toString());
		 
		 crossOverCb.setSelectedItem(agConfig.getCrossoverOperator());
		 mutationCb.setSelectedItem(agConfig.getMutationOperator());
		 generationCb.setSelectedItem(agConfig.getGenerationOperator());
		 selectionCb.setSelectedItem(agConfig.getSelectionOperator());
		 
    }
    
    public TextField getNumberOfDepotsField(){
        return this.numberOfDepotsField;
    }
    
    public void setNumberOfDepotsField(TextField numberOfDepotsField){
        this.numberOfDepotsField = numberOfDepotsField;
    }
    
    public TextField getNumberOfVehiclesField(){
        return this.numberOfVehiclesField;
    }
    
    public void setNumberOfVehiclesField(TextField numberOfVehiclesField){
        this.numberOfVehiclesField = numberOfVehiclesField;
    }
    
    public TextField getVehicleCapacityField(){
        return this.vehicleCapacityField;
    }
    
    public void setVehicleCapacityField(TextField vehicleCapacityField){
        this.vehicleCapacityField = vehicleCapacityField;
    }
    
    public TextField getPopulationSizeField(){
        return this.populationSizeField;
    }
    
    public void setPopulationSizeField(TextField populationSizeField){
        this.populationSizeField = populationSizeField;
    }
    
    
    public TextField getStopConditionField(){
        return this.stopConditionField;
    }
    
    public void setStopConditionField(TextField stopConditionField){
        this.stopConditionField = stopConditionField;
    }
    
    public EvaluationGraph getEvaluationGraph(){
        return this.evaluationGraph;
    }
    
    public void setEvaluationChart(EvaluationGraph evaluationGraph){
        this.evaluationGraph = evaluationGraph;
    }
    
    public FileTypeHelper getFileHelper(){
        return this.fileHelper;
    }
    
    public void setFileHelper(FileTypeHelper fileHelper){
        this.fileHelper = fileHelper;
    }
    
    public JButton getStartButton(){
        return this.startButton;
    }
    
    public void setStartButton(JButton startButton){
        this.startButton = startButton;
    }
    
    public VrpComboBox<SelectionType> getSelectionCb(){
        return this.selectionCb;
    }
    
    public void setSelectionCb(VrpComboBox<SelectionType> selectionCb){
        this.selectionCb = selectionCb;
    }
    
    public VrpComboBox<MutationOperator> getMutationCb(){
        return this.mutationCb;
    }
    
    public void setMutationCb(VrpComboBox<MutationOperator> mutationCb){
        this.mutationCb = mutationCb;
    }
    
    public VrpComboBox<CrossoverOperator> getCrossOverCb(){
        return this.crossOverCb;
    }
    
    public void setCrossOverCb(VrpComboBox<CrossoverOperator> crossOverCb){
        this.crossOverCb = crossOverCb;
    }
    
    public VrpComboBox<ReplacementType> getGenerationCb(){
        return this.generationCb;
    }
    
    public void setGenerationCb(VrpComboBox<ReplacementType> generationCb){
        this.generationCb = generationCb;
    }
    
    public VrpComboBox<FileType> getFileTypeCb(){
        return this.fileTypeCb;
    }
    
    public void setFileTypeCb(VrpComboBox<FileType> fileTypeCb){
        this.fileTypeCb = fileTypeCb;
    }
    
    public void setLoggerText(String str){
        this.loggerInfo.setText(str);
    }
    
    public TextField getFileNameField(){
        return this.fileNameField;
    }
    
    public void setFileNameField(TextField fileNameField){
        this.fileNameField = fileNameField;
    }
    
    public JButton getFileButton(){
        return this.fileButton;
    }
    
    public void setFileButton(JButton fileButton){
        this.fileButton = fileButton;
    }
    
    public JButton getRefreshButton(){
        return this.refreshButton;
    }
    
    public void setRefreshButton(JButton refreshButton){
        this.refreshButton = refreshButton;
    }
    
    public JLabel getFileNameLabel(){
        return this.fileNameLabel;
    }
    
    public void setFileNameLabel(JLabel fileNameLabel){
        this.fileNameLabel = fileNameLabel;
    }
    
    public boolean isRandomized(){
        return this.isRandomized;
    }
    
    public void setRandomized(boolean isRandomized){
        this.isRandomized = isRandomized;
    }
    
    public FloatSlider getInnerDepotPenalty() {
		return innerDepotPenalty;
	}

	public void setInnerDepotPenalty(FloatSlider innerDepotPenalty) {
		this.innerDepotPenalty = innerDepotPenalty;
	}

	public TextField getCustomersCountField(){
        return this.customersCountField;
    }
    
    public void setCustomersCountField(TextField customersCountField){
        this.customersCountField = customersCountField;
    }
    
    public JTextArea getLoggerInfo(){
        return this.loggerInfo;
    }
    
    public void setLoggerInfo(JTextArea loggerInfo){
        this.loggerInfo = loggerInfo;
    }
    
    public VrpExecutor getStartActionListener(){
        return this.startActionListener;
    }
    
    public void setStartActionListener(VrpExecutor startActionListener){
        this.startActionListener = startActionListener;
    }

	public FloatSlider getMutationProbSlider() {
		return mutationProbSlider;
	}

	public void setMutationProbSlider(FloatSlider mutationProbSlider) {
		this.mutationProbSlider = mutationProbSlider;
	}

	public FloatSlider getCrossOverProbSlider() {
		return crossOverProbSlider;
	}

	public void setCrossOverProbSlider(FloatSlider crossOverProbSlider) {
		this.crossOverProbSlider = crossOverProbSlider;
	}

	public FloatSlider getReplacementElitismSlider() {
		return replacementElitismSlider;
	}

	public void setReplacementElitismSlider(FloatSlider replacementElitismSlider) {
		this.replacementElitismSlider = replacementElitismSlider;
	}
}
