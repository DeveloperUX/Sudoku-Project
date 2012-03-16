//LAB 9

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class SudokuNine {
   
   // Attributes
   private int[][] row, col, block, puzzleSolution;
   private Random random;
   private ArrayList<int[][]> solutions;
   private ArrayList<Point> removalList;
   private ArrayList<Point> randomPoints;
   private boolean isUnique = true;
   private int numberOfHints;
   private static final int PUZZLE_SIZE = 9;
   private Cell[][] cellRow;
   
   private ArrayList<Cell> nakedCells = new ArrayList<Cell>();
   
   // Default constructor
   public SudokuNine() {
      random = new Random();
      initialize();
   }
   
   public SudokuNine(String puzzle){
       onPuzzle(puzzle);
       toCells();
   }
    
   
   // Accessors
   public int getRowElement(int row, int col) {
      return this.row[row][col];
   }
   
   public int getColElement(int row, int col) {
      return this.col[row][col];
   }
   
   public int getBlockElement(int row, int col) {
      return this.block[row][col];
   }
   
   public int[][] getRow() {
      return row;
   }
   
   public int getNumberOfHints() {
      return numberOfHints;
   }
   
   // Mutator

      
   // Public Interface Behaviors
   
   
   public void generateSolution() {
      boolean completed = false;
      
      while(!completed) {
         initialize();
         completed = isGenerated();
      }
   }
   
   public void generatePuzzle(int limit) {
      puzzleSolution = makeDeepCopy(row); 
      generateRandomPoints();
      removeCells(limit);
      numberOfHints = countSolutionCells();
   }
   
   //public void checkForNa
   
   public String toString() {
      String[] perspectiveRow = new String[9];
      
      for(int i=0; i<PUZZLE_SIZE; i++) {
	 perspectiveRow[i] = new String();
         for (int j=0; j<29; j++) {
            if(j == 9 || j == 19 )
	       perspectiveRow[i] += " ";
	    else if(j < 9)
	       perspectiveRow[i] += row[i][j];      
            else if(j < 19)
	       perspectiveRow[i] += col[i][j-10];  
            else if(j < 29)
	       perspectiveRow[i] += block[i][j-20];  
         }
      }
      String temp = " -----------     Row:      Col:      Block: \n";
      int offset = 0; 
      
      for(int i=0; i<PUZZLE_SIZE; i++) {
	 for (int j=0; j<PUZZLE_SIZE; j++) {
	    if(j == 0 || j == 3 | j == 6)
	       temp += "|";
            temp += row[i][j];
	 }
	 if(offset < 9) 
	    temp += "|    " + perspectiveRow[offset++] + "\n";
    	 else temp += "|\n";
	 if(i == 2 || i == 5) {
            temp += "|---+---+---|    " + perspectiveRow[offset++] + "\n";
	 }
      }
      temp += " -----------\n";
      
      return temp;
   }
   
   public String toStringInOneLine() {
      String temp = "";
      for(int i=0; i<PUZZLE_SIZE; i++) {
	 for (int j=0; j<PUZZLE_SIZE; j++) {
            temp += row[i][j];
         }
      }
      return temp;
   }
   
   // Private Helper Behaviors
   
   //CREATES A BLANK SUDOKU WITH 0 FOR ALL CELLS
   private void initialize() {
      solutions = new ArrayList<int [][]>();
      removalList = new ArrayList<Point>();
      randomPoints = new ArrayList<Point>();
      //nakedCells = new ArrayList<Cell>();
      numberOfHints = 0;
      row = new int[PUZZLE_SIZE][PUZZLE_SIZE];
      col = new int[PUZZLE_SIZE][PUZZLE_SIZE];
      block = new int[PUZZLE_SIZE][PUZZLE_SIZE];
      
      for(int i=0; i<PUZZLE_SIZE; i++) {
         for (int j=0; j<PUZZLE_SIZE; j++) {
            row[i][j] = col[i][j] = block[i][j] = 0;
         }
      }
   }
   
   private boolean isGenerated() {
      int number = 0, counter = 1;
      boolean invalid = true;

      for(int i=0; i<PUZZLE_SIZE; i++) {
         for (int j=0; j<PUZZLE_SIZE; j++) {
            counter = 1;
            invalid = true;
            while(invalid) {
               number = random.nextInt(9) + 1;
               if(isValid(i, j, number)) {
		  setElement(i, j, number);
                  invalid = false;
	       }
               counter++;
               if(counter > 50) {
                  return false;
               }
            }     
         }
      }
      return true;
   }   
   
   public boolean isValid(int row, int col, int element) {

      for(int i=0; i<PUZZLE_SIZE; i++) {
         if((this.row[row][i] == element) ||
	    (this.col[col][i] == element) ||
 	    (this.block[row/3*3+col/3][i] == element))
               return false;
      }
      return true;
   }
   
   
   private void generateRandomPoints() {
      ArrayList<Point> temp = new ArrayList<Point>();
      Point[][] pointArray = new Point[9][9];
      int x, y;
      for(int i=0; i<PUZZLE_SIZE; i++) {
         for(int j=0; j<PUZZLE_SIZE; j++) {
             pointArray[i][j] = new Point(i, j);
         }
      }
      while(randomPoints.size() < 81){
         x = random.nextInt(9);
         y = random.nextInt(9);
         if(pointArray[x][y] != null) {
            randomPoints.add(pointArray[x][y]);
            pointArray[x][y] = null;
         }
         if(pointArray[x][y] != null) {
            randomPoints.add(pointArray[y][x]);
            pointArray[y][x] = null;
         }
      }
   }

   private void removeCells(int numberOfCellsToRemove){
      Point cellToRemove = null;
      int originalCellValue = 0;
      
      while(numberOfCellsToRemove > 0) {
         solutions.clear();  
         solutions.add(puzzleSolution);
         
         if(randomPoints.size() != 0) {
            cellToRemove = randomPoints.remove(0); 
            setElement(cellToRemove.x, cellToRemove.y, 0);  
            findSolutions(0,0);
            
            if(isUnique) {
               isUnique = false;
               numberOfCellsToRemove--;
            }
            else {  //more than 2 solutions
               originalCellValue = 
                     puzzleSolution[cellToRemove.x][cellToRemove.y];
               setElement(cellToRemove.x,cellToRemove.y, originalCellValue);
            }
         } 
         else {
            numberOfCellsToRemove = 0;
         }          
      }
   }
   
   private void findSolutions(int rowIndex, int colIndex) {

      // Base Case
      if (rowIndex == 8 && colIndex == 8)
         storeIfUniqueSolution(row);  
             
      // Recursive Case
      else {
         
         if(rowIndex == 9) {
            rowIndex = 0;
            colIndex++;
         }
         
         if(row[rowIndex][colIndex] != 0)
            findSolutions(rowIndex+1,colIndex);
     
         else {
            for (int tryThisNumber=1; tryThisNumber<=PUZZLE_SIZE; ++tryThisNumber) {
               if (isValid(rowIndex, colIndex, tryThisNumber)) {  
                  setElement(rowIndex, colIndex, tryThisNumber);
                  findSolutions(rowIndex+1, colIndex);
                  setElement(rowIndex, colIndex, 0);
               }
            }
         }
	 
      }
      
   }
   
   private int[][] makeDeepCopy(int[][] original) {
      int[][] copy = new int[PUZZLE_SIZE][PUZZLE_SIZE];
      for(int i=0; i<PUZZLE_SIZE; i++){
         for(int j=0; j<PUZZLE_SIZE; j++){
             copy[i][j] = original[i][j];
         }
      }
      return copy;
   }
   
   private void storeIfUniqueSolution(int[][] solution){
      boolean addSolution = true; 
      for(int i = 0; i < solutions.size(); i++) {
         if(equals(solutions.get(i), solution))
            addSolution = false;
      }
      if(addSolution)    
            solutions.add(makeDeepCopy(solution));
      if(solutions.size() > 1)
         isUnique = false;
      else
         isUnique = true;
   }
    
   private boolean equals(int[][] target, int[][] temp) {
      for(int i=0; i<PUZZLE_SIZE; i++){
         for(int j=0; j<PUZZLE_SIZE; j++){
             if (temp[i][j] != target[i][j])
                 return false;
         }
      }
      return true;
   }

   
   
   
   //////////////////////////////////CHANGED
   private int countSolutionCells() {
      int count = 0;
      for(int i=0; i<PUZZLE_SIZE; i++) {
	 for (int j=0; j<PUZZLE_SIZE; j++) {
            if(row[i][j] != 0)
               count++;
         }
      }
      return count;
   }
   
   public void onPuzzle(String puzzle) {  
    col = new int [9][9];
    row = new int [9][9];
    block = new int [9][9];
    cellRow = new Cell[9][9];
    int count = 0;
    StringBuffer x = new StringBuffer(puzzle);
    int num;

    for (int r=0; r<9; r++) {
        for (int c=0; c<9; c++) {

            num = Integer.parseInt(x.substring(count, count + 1));

            count++;
            setElement(r, c, num);

        }     
    }
    
    } 
    public void setElement(int row, int col, int element) {

    this.row[row][col] = element;
    this.col[col][row] = element;
    this.block[row/3*3 + col/3][row%3*3 + col%3] = element;
    
    }
    
    public Cell[][] getCells(){
        return cellRow;
    } 
    
    public void toCells(){
        int element;
        for(int r = 0; r<PUZZLE_SIZE; r++) {
            for (int c=0; c<PUZZLE_SIZE; c++) {
                element = row[r][c];
                if(element!=0)
                 this.cellRow[r][c] = new Cell(element);        
            else
                 this.cellRow[r][c] = new Cell();
            }
        }
    }
    
    //COMPARABLE
    public void compareCells() {
       boolean isPair;
       
       for (int r=0; r<9; r++) {
           for (int c=0; c<9; c++) {
               
               isPair = cellRow[r][c].checkForPair();
               if (isPair)
                   nakedCells.add(cellRow[r][c]);
                   
           }
       }
       sortCells();
    }
    
    public void sortCells() {
        boolean bool;
        int pos;
        ArrayList<Cell> commonCells = new ArrayList();
        
        System.out.println (nakedCells.toString());
        for (int i=0; i<nakedCells.size(); i++) {
            
            pos = nakedCells.get(i).getX()+1;
            
            for (int j=pos; j<nakedCells.size(); j++) {
                
                bool = nakedCells.get(i).compareTo(nakedCells.get(j));
                commonCells.add(nakedCells.get(i));
                commonCells.add(nakedCells.get(j));   
            }
        }
        nakedCells = commonCells;
        for(Cell cell : nakedCells){
            cell.changeCellColorToBlue();
        }
    }   
    
    public ArrayList<Cell> getNakedCells() {
        return nakedCells;
    }
}
