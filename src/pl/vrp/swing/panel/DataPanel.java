package pl.vrp.swing.panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class DataPanel extends FormPanel {
    
	private static final long serialVersionUID = 5462256977144797041L;
	
    private String bestCost = "Najlepszy koszt przejazdu:";
    private String averageCost = "Œredni koszt przejazdu:";
    private String worstCorst = "Najgorszy koszt przejazdu:";
    private String timeSpent = "Czas wykonania:";
    private String generation = "Pokolenie:";
    private String evaluations = "Iloœæ ewaluacji:";
    private String maxDistance = "Pocz¹tkowy koszt przejazdu:";
    private String customers = "Iloœæ klientów:";
    
    private JLabel distanceL;
    private JLabel customerL;
    private JLabel bestCostL;
    private JLabel averageCostL;
    private JLabel worstCostL;
    private JLabel timeSpentL;
    private JLabel generationL;
    private JLabel evaluationsL;
    
    private JTextArea finalInfoArea;
    private JScrollPane scrollPane;
    
    private JButton showRouteButton;
    
    private int popSize;
    private float firstBest;
    
    public DataPanel(float firstBest, int popSize){
        super(
	"10px,100px,10px,150px,10px,150px,10px", 
	"10px,25px,10px,25px,10px,25px,10px,25px,10px,25px,10px,25px,10px,25px,10px,200px,10px"
		);
        
        this.popSize = popSize;
        this.firstBest = firstBest;
        
        this.showRouteButton = new JButton("Poka¿ trasy");
        showRouteButton.setEnabled(false);
        
        initalizeLabels();
        initScrollPane();
        addComponentsToPanel();
    }
    
    private void initalizeLabels(){
        this.bestCostL = new JLabel(this.bestCost + "0");
        this.averageCostL = new JLabel(this.averageCost + "0");
        this.worstCostL = new JLabel(this.worstCorst+ "0");
        this.timeSpentL = new JLabel(this.timeSpent + "0");
        this.generationL = new JLabel(this.generation + "0");
        this.evaluationsL = new JLabel(this.evaluations + "0");
        this.distanceL = new JLabel(this.maxDistance + this.firstBest);
        this.customerL = new JLabel(this.customers + this.popSize);
    }
    
    private void initScrollPane(){
        this.finalInfoArea = new JTextArea(5, 20);
        this.finalInfoArea.setEditable(false);
        this.scrollPane = new JScrollPane(this.finalInfoArea);
    }
    
    private void addComponentsToPanel(){
        addComponent(new JSeparator(0), 2, 1, Integer.valueOf(5));
        addComponent(this.distanceL, 2, 2, Integer.valueOf(3));
        addComponent(this.customerL, 6, 2, Integer.valueOf(1));
        addComponent(new JSeparator(0), 2, 3, Integer.valueOf(5));
        
        addComponent(this.bestCostL, 2, 4, Integer.valueOf(4));
        addComponent(this.averageCostL, 2, 6, Integer.valueOf(4));
        addComponent(this.worstCostL, 2, 8, Integer.valueOf(4));
        addComponent(new JSeparator(0), 2, 9, Integer.valueOf(5));
        
        addComponent(this.timeSpentL, 2, 10, Integer.valueOf(3));
        addComponent(this.generationL, 2, 12, Integer.valueOf(3));
        addComponent(this.evaluationsL, 2, 14, Integer.valueOf(3));
        addComponent(this.showRouteButton, 5,12, Integer.valueOf(2));
        addComponent(new JSeparator(0), 2, 15, Integer.valueOf(5));
        
        addComponent(this.scrollPane, 2, 16, Integer.valueOf(5));
    }
    
    public void setLabelValue(float bestVal, float avgVal, float worstVal){
        this.bestCostL.setText(this.bestCost + Float.valueOf(bestVal));
        this.averageCostL.setText(this.averageCost + Float.valueOf(avgVal));
        this.worstCostL.setText(this.worstCorst + Float.valueOf(worstVal));
    }
    
    public void setGeneration(int generation){
        this.generationL.setText(this.generation + Integer.valueOf(generation));
    }
    
    public void setTimeSpent(long time){
        this.timeSpentL.setText(this.timeSpent + Long.valueOf(time) + "ms");
    }
    
    public void setNumberEvaluations(long number){
        this.evaluationsL.setText(this.evaluations + Long.valueOf(number));
    }
    
    public JButton getShowRouteButton(){
    	return this.showRouteButton;
    }
    
    public JTextArea getFinalInfoArea(){
        return this.finalInfoArea;
    }
}
