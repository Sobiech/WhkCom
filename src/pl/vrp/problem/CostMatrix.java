package pl.vrp.problem;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import pl.vrp.problem.mdss.Customer;
import pl.vrp.problem.mdss.Depot;

public class CostMatrix {
	
    private float[][] costMatrix;
    
    /** kordynaty dostepne tylko dla 
     *  danych zawierajce lokalizacje 
    */
    
    private float[][] coordinates;
    private boolean isCoordinateDataAvailable = false;
    
    /** CostMatrix
     * Konstruktor uzywany gdy kordynaty generowane sa losowo
     * @param depot - obiekt zawierajacy lokalizacje klientw i magazynu
     */
    
    public CostMatrix(Depot depot){
    	float[][] costMatrix = getVRPCostMatrix(depot);
    	verifyCostMatrixSize(costMatrix);
    	this.costMatrix = costMatrix;
    	isCoordinateDataAvailable = true;
    }
    
    /** CostMatrix
     * Konstruktor uzywany gdy dane pobierane sa z pliku
     * @param fileName - nazwa pliku 
     * @param fileWithPositionData - sprawdzamy czy plik zawiera informacje o 
     * kordynatach czy dzielacy dystans dane node'y
     */
    
    public CostMatrix(String fileName, boolean fileWithPositionData){
        
    	if (fileWithPositionData) {
          this.costMatrix = getVRPCostMatrix(fileName);
          isCoordinateDataAvailable = true;
        } else {
          this.costMatrix = getGvSIGCostMatrix(fileName);
        }
        
        verifyCostMatrixSize(this.costMatrix);
    }
    
    private int verifyCostMatrixSize(float[][] dm) {
        
    	int length = dm.length;
        
    	if (length != dm[0].length) {
          throw new AssertionError("ERROR: The cost matrix is not a square matrix!");
        }
    	
        return length;
    }
    
    /* FILE_TYPE RANDOMIZE */
    /** Tworzymy tabele dwu-wymiarowa typu float na podstawie danych z obiektu depot **/
    
    public float[][] getVRPCostMatrix(Depot depot) {
    	
        int size = depot.getDepotCustomers().size() + 1;
        
        float[][] costMatrix = new float[size][size];
        float[][] coordinates = new float[size][2];
        
        int node = 1;
        
        for (Customer customer : depot.getDepotCustomers()) {
          coordinates[node][0] = ((float)customer.getX());
          coordinates[node][1] = ((float)customer.getY());
          node++;
        }
        
        coordinates[0][0] = ((float)depot.getX());
        coordinates[0][1] = ((float)depot.getY());
        
        for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
            double x1 = coordinates[i][0];
            double y1 = coordinates[i][1];
            double x2 = coordinates[j][0];
            double y2 = coordinates[j][1];
            costMatrix[i][j] = ((float)Point2D.distance(x1, y1, x2, y2));
          }
        }
        
        this.coordinates = coordinates;
        return costMatrix;
    }
    
    /*  FILE_TYPE POSITION */
    /** Dystans obliczany za pomoca wyciagnietej pozycji z pliku w sekcji CORD  **/
    /** Wartosci 1= NODE_ID , 2 i 3 to kordynaty 								**/
    
    public float[][] getVRPCostMatrix(String fileName) {
    	
        int costMatrixSize = measureVRPCostMatrixSize(fileName);
        float[][] costMatrix = new float[costMatrixSize + 1][costMatrixSize + 1];
        float[][] coordinates = new float[costMatrixSize + 1][2];
        
        try {
          FileInputStream fileInputStream = new FileInputStream(fileName);
          DataInputStream dataInputStream = new DataInputStream(fileInputStream);
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
          
          String readedLine = null;
          String[] splitedLine = null;
          
          while (!bufferedReader.readLine().contains("NODE_COORD_SECTION")) {}
          readedLine = bufferedReader.readLine();
          
          while (!readedLine.contains("DEMAND_SECTION")) {
            splitedLine = readedLine.split("\\s+");
            
            if (!splitedLine[0].equals("")) {
                int node = Integer.parseInt(splitedLine[0]);
                coordinates[node][0] = Float.parseFloat(splitedLine[1]);
                coordinates[node][1] = Float.parseFloat(splitedLine[2]);
            }
            readedLine = bufferedReader.readLine();
          } while (!bufferedReader.readLine().contains("DEPOT_SECTION")) {}
          
          do {
            readedLine = bufferedReader.readLine();
            splitedLine = readedLine.split("\\s+");
          } while (splitedLine[0].equals(""));
          
          coordinates[0][0] = Float.parseFloat(splitedLine[0]);
          coordinates[0][1] = Float.parseFloat(splitedLine[1]);
          
          for (int i = 0; i <= costMatrixSize; i++) {
            for (int j = 0; j <= costMatrixSize; j++) {
                double x1 = coordinates[i][0];
                double y1 = coordinates[i][1];
                double x2 = coordinates[j][0];
                double y2 = coordinates[j][1];
                costMatrix[i][j] = ((float)Point2D.distance(x1, y1, x2, y2));
            }
          }
          bufferedReader.close();
          
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        this.coordinates = coordinates;
        return costMatrix;
    }
    
    public int measureVRPCostMatrixSize(String fileName) {
        int maxNode = 0;
        int numberOfNodes = 0;
        
        try {
          FileInputStream fileInputStream = new FileInputStream(fileName);
          DataInputStream dataInputStream = new DataInputStream(fileInputStream);
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
          
          String readedLine = bufferedReader.readLine();
          String[] splitedLine = null;
          
          while (!bufferedReader.readLine().contains("NODE_COORD_SECTION")) {}
          readedLine = bufferedReader.readLine();
          
          while (!readedLine.contains("DEMAND_SECTION")) {
            splitedLine = readedLine.split("\\s+");
            
            if (!splitedLine[0].equals("")){
                int node = Integer.parseInt(splitedLine[0]);
                if (node > maxNode) {
                  maxNode = node;
                }
                numberOfNodes++;
            }
            readedLine = bufferedReader.readLine();
          }
          bufferedReader.close();
        
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        if (maxNode != numberOfNodes) {
          throw new AssertionError("ERROR: The file contains a number of nodes different than the numbers given to the nodes!");
        }
        
        return numberOfNodes;
    }
    
    
    /*  FILE_TYPE DISTANCE */
    /** Odleglosc jaka dzieli klienta ,z innymi klientami zapisana bezposrednio w pliku **/
    
    public float[][] getGvSIGCostMatrix(String fileName) {
        int size = measureGvSIGCostMatrixSize(fileName);
        float[][] costMatrix = new float[size][size];
        
        try {
          FileInputStream fileInputStream = new FileInputStream(fileName);
          DataInputStream dataInputStream = new DataInputStream(fileInputStream);
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
          
          String readedLine = null;
          String[] splitedLine = null;
          
          readedLine = bufferedReader.readLine();
          while (readedLine != null){
        	  
            splitedLine = readedLine.split("\t");
            int x = Integer.parseInt(splitedLine[0]);
            int y = Integer.parseInt(splitedLine[1]);
            float cost = Float.parseFloat(splitedLine[3]);
            costMatrix[x][y] = cost;
            readedLine = bufferedReader.readLine();
            
          }
          bufferedReader.close();
          
        } catch (Exception e) {
          e.printStackTrace();
        } 
        
        return costMatrix;
    }
    
    public int measureGvSIGCostMatrixSize(String fileName) {
        
    	int maxX = 0;
        int maxY = 0;
        
        try {
          FileInputStream fileInputStream = new FileInputStream(fileName);
          DataInputStream dataInputStream = new DataInputStream(fileInputStream);
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
          
          String readedLine = bufferedReader.readLine();
          String[] splitedLine = null;
          
          while (readedLine != null) {
            splitedLine = readedLine.split("\t");
            int cordinateX = Integer.parseInt(splitedLine[0]);
            int cordinateY = Integer.parseInt(splitedLine[1]);
            
            if (cordinateX > maxX) {
                maxX = cordinateX;
            }
            
            if (cordinateY > maxY) {
                maxY = cordinateY;
            }
            readedLine = bufferedReader.readLine();
          }
          bufferedReader.close();
          
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        if (maxX != maxY) {
          throw new AssertionError("ERROR: The file represents a cost matrix that isn't square!");
        }
        
        return maxX + 1;
    }
    
    public float getCost(int origin, int destination) {
        return this.costMatrix[origin][destination];
    }
    
    public int getSize() {
        return this.costMatrix.length;
    }
    
    public float[] getCoordinates(int node){
    	return coordinates[node];
    }
    
	public boolean isCoordinateDataAvailable() {
		return isCoordinateDataAvailable;
	}

}
