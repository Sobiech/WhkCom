package pl.vrp.swing.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import pl.vrp.algorithm.GA.Gene;
import pl.vrp.algorithm.GA.phenotype.Tours.Tour;
import pl.vrp.problem.CostMatrix;
import pl.vrp.swing.panel.DataPanel;

public class DataFrame extends Frame {
	
    private static final long serialVersionUID = 5841849099352402132L;
    
    private DataPanel dataPanel;
    
    private float firstBest;
    private int popSize;
    
    public DataFrame(String title, float firstBest, int popSize){
        super(450, 500, "CVRP | Magazyn nr:" + title, false);
        this.firstBest = firstBest;
        this.popSize = popSize;
        initalizeFrame();
    }
    
    private void initalizeFrame(){
        setResizable(false);
        this.dataPanel = new DataPanel(this.firstBest, this.popSize);
        this.contentPane.setLayout(new BorderLayout());
        this.contentPane.add(this.dataPanel);
    }
    
    
    public void showVehiclesTours(ArrayList<Tour> arrayTours, CostMatrix costMatrix, int threadId){
    	this.dataPanel.getShowRouteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<float[][]> vehicleTours = new ArrayList<float[][]>();
				
				for(Tour tour : arrayTours){
					if(tour.getCustomers().size() > 0){
						float[][] customerCoordinates = new float[tour.getCustomers().size()][2];
						int i = 0;
						for(Gene customer : tour.getCustomers()){
							customerCoordinates[i] = costMatrix.getCoordinates(customer.getNode());
							i++;
						}
						vehicleTours.add(customerCoordinates);
					}
				}
				new DrawableFrame("Magazyn nr." + threadId + ": marszruty pojazdów", vehicleTours, costMatrix.getCoordinates(0));
			}
		});
    	this.dataPanel.getShowRouteButton().setEnabled(true);
    }
    
    
    public void setLabelValue(float bestVal, float avgVal, float worstVal){
        this.dataPanel.setLabelValue(bestVal, avgVal, worstVal);
    }
    
    public void setGeneration(int generation){
        this.dataPanel.setGeneration(generation);
    }
    
    public void setTimeSpent(long time){
        this.dataPanel.setTimeSpent(time);
    }
    
    public void setNumberEvaluations(long number){
        this.dataPanel.setNumberEvaluations(number);
    }
    
    public JTextArea getFinalInfoArea(){
        return this.dataPanel.getFinalInfoArea();
    }
}
