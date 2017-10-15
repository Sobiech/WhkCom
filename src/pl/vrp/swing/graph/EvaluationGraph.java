package pl.vrp.swing.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class EvaluationGraph {
	
    private ChartPanel chartPanel;
    
    private XYSeries worstSeries;
    private XYSeries middleSeries;
    private XYSeries bestSeries;
    
    private JFreeChart evaluationChart;
    private int seriesNumber = 0;
    private List<XYSeries> series;
    
    public EvaluationGraph() {
        this.series = new ArrayList<XYSeries>();
        this.evaluationChart = createChart(createDataSet());
        this.chartPanel = new ChartPanel(this.evaluationChart);
    }
    
    private JFreeChart createChart(XYDataset dataSet){
        JFreeChart chart = ChartFactory.createXYLineChart(
          "Optymalizacja kosztów transportu", 
          "Pokolenie", 
          "Koszt przejazdu", 
          dataSet, 
          PlotOrientation.VERTICAL, 
          true, 
          true, 
          false);
        
        chart.setBackgroundPaint(Color.WHITE);
        
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.BLACK);
        
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, false);
        plot.setRenderer(renderer);
        
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        
        return chart;
    }
    
    private XYSeriesCollection createDataSet(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        return dataset;
    }
    
    public void resetDataSet(){
        this.evaluationChart = createChart(createDataSet());
        this.chartPanel.repaint();
    }
    
    public void createSeries(int depotCount){
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i = 0; i < depotCount; i++){
          XYSeries xySeries = new XYSeries("Magazyn (nr: " + i + " )");
          
          this.series.add(xySeries);
          dataset.addSeries(xySeries);
          
          XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
          renderer.setSeriesShapesVisible(i, false);
          renderer.setSeriesPaint(i, getRandomColor());
          renderer.setSeriesStroke(i, 
            new BasicStroke(
            2.0F, 1, 1, 
            1.0F, new float[] { 10.0F, 6.0F }, 0.0F));
        }
        this.evaluationChart.getXYPlot().setDataset(this.seriesNumber, dataset);
    }
    
    public void addSeriesPoints(double best, int generation, int seriesNr){
        ((XYSeries)this.series.get(seriesNr)).add(generation, best);
    }
    
    public Color getRandomColor(){
        Random numGen = new Random();
        return new Color(numGen.nextInt(256), numGen.nextInt(256), numGen.nextInt(256));
    }
    
    public ChartPanel getChartPanel(){
        return this.chartPanel;
    }
    
    public void setChartPanel(ChartPanel chartPanel){
        this.chartPanel = chartPanel;
    }
    
    public XYSeries getWorstSeries(){
        return this.worstSeries;
    }
    
    public XYSeries getMiddleSeries(){
        return this.middleSeries;
    }
    
    public XYSeries getBestSeries(){
        return this.bestSeries;
    }
    
    public void setWorstSeries(XYSeries worstSeries){
        this.worstSeries = worstSeries;
    }
    
    public void setMiddleSeries(XYSeries middleSeries){
        this.middleSeries = middleSeries;
    }
    
    public void setBestSeries(XYSeries bestSeries){
        this.bestSeries = bestSeries;
    }
}
