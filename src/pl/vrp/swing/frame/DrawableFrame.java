package pl.vrp.swing.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class DrawableFrame extends JFrame{
	
	private static final long serialVersionUID = 5356673476074384446L;
	
	private DrawablePanel drawablePanel;
	private Color[] colors;
	
	public DrawableFrame(String title, List<float[][]> routesList , float[] depotPosition){
		super(title);
		setSize(400,400);
		
		addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					DrawableFrame.this.dispose();
				}
			}
		);
		
		initPanel();
		colors = new Color[routesList.size()];
		createColors();
		
		drawablePanel.setPositionData(routesList , depotPosition, colors);
		setVisible(true);
		
	}
	
	private void initPanel(){
		drawablePanel = new DrawablePanel();
		drawablePanel.setPreferredSize(new Dimension(2500,2500));
		final JScrollPane scrollPane = new JScrollPane(drawablePanel);
		setLayout(new BorderLayout());
        scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        add(scrollPane, BorderLayout.CENTER);
	}
	
	private void createColors(){
		Random random = new Random();
		for(int i = 0 ; i < colors.length ; i ++ ){
			colors[i] = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		}
	}
	
	class DrawablePanel extends JPanel{
		
		private static final long serialVersionUID = -2743140562105211866L;
		
		private List<float[][]> routesList;
		private float[] depotPosition;
		private Color[] colors;
		
		public void paintComponent(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			
			if(routesList != null && routesList.size() > 0){
				drawCustomer(g2);
			}
			
			if(depotPosition != null && depotPosition.length > 0){
				drawDepot(g2);
			}
		}
		
		private void drawDepot(Graphics2D g2){
			g2.setColor(Color.BLUE);
			
			int[] pos = optimizeValues(depotPosition[0], depotPosition[1]);
			g2.fillRoundRect(pos[0], pos[1], 10, 10, 10, 10);
		}
		
		private void drawCustomer(Graphics2D g2){
			int colorId = 0;
			int[] dPos = optimizeValues(depotPosition[0], depotPosition[1]); 
			
			int incData = 5;
			boolean isFromDepot = true;
			
			for(float[][] data : routesList){
				g2.setColor(colors[colorId]);
				int[] pos2 = dPos;
				for(float[] position : data){
					int[] pos = optimizeValues(position[0], position[1]);
					g2.fillRect(pos[0], pos[1], 6, 6);
					g2.drawLine(pos2[0]+incData, pos2[1]+incData, pos[0]+3, pos[1]+3);
					pos2 = pos;
					
					if(isFromDepot){
						incData = 3;
						isFromDepot = !isFromDepot;
					}
				}
				g2.drawLine(pos2[0]+3, pos2[1]+3, dPos[0]+5, dPos[1]+5);
				colorId++;
			}
		}
		
		public int[] optimizeValues(float fx , float fy){
			int x = Math.round(fx);
			int y = Math.round(fy);
			
			y = (y + ((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2))*2;
			x = (x + ((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2))*2;
			
			return new int[]{ x , y };
		}
		
		
		public void setPositionData(List<float[][]> routesList , float[] depotPosition, Color[] colors){
			this.routesList = routesList;
			this.depotPosition = depotPosition;
			this.colors = colors;
		}
		
	}
	
}
