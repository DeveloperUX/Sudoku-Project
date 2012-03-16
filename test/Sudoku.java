
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * Mohammad Abdussalaam
 *Moustafa Ismael
 *Ayoub Benguedouar
 */




public class Sudoku {
   
   private int[][] row;
   private int[][] col;
   private int[][] block;  
   private Cell[][] cellRow;
   //
   private int[][] puzzleSolution;
   private Random random;
   private ArrayList<int[][]> solutions;
   private ArrayList<Point> removalList;
   private ArrayList<Point> randomPoints;
   private boolean isUnique = true;
   private int numberOfHints;
   private static final int PUZZLE_SIZE = 9;
   
   // Default constructor
   public Sudoku() {
       random = new Random();
       initialize();
    }
    public Sudoku(String puzzle){
        onPuzzle(puzzle);
    }
    
    public void initialize(){
        solutions = new ArrayList<int [][]>();
        removalList = new ArrayList<Point>();
        randomPoints = new ArrayList<Point>();
        numberOfHints = 0;
        //
        col = new int [9][9];
        row = new int [9][9];
        block = new int [9][9];
        cellRow = new Cell[9][9];
        for (int r=0; r<9; r++) {
            for (int c=0; c<9; c++) {
                
                setElement(r, c, 0);
            }     
        }
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
        if(element!=0)
             this.cellRow[row][col] = new Cell(element);        
        else
             this.cellRow[row][col] = new Cell();
    }
    
   public boolean isValid(int row, int col, int element) {

      for(int i=0; i<9; i++) {
         if((this.row[row][i] == element) ||
	    (this.col[col][i] == element) ||
 	    (this.block[row/3*3+col/3][i] == element))
               return false;
      }
      return true;
   }
    
    public Cell[][] getCells(){
        return cellRow;
    }
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
    @Override
    public String toString() {
        String mainString = "";
        int[][][] rowColBlock = {row, row, col, block}; 
        
        mainString += " ----GRID---   row:       ";
        mainString += "col:       ";
        mainString += "block:   \n";
        
        for (int r=0; r<9; r++) {
            
            for (int i=0; i<4; i++) { 
                
                if ((r==3 || r==6) && i == 0) {
                    mainString += " -----------\n";
                }
                
                for (int c=0; c<9; c++) {
                    
                    if (c%3==0 && i==0) {
                        mainString += "|";
                    }
                    mainString += rowColBlock[i][r][c];
                    if (c==8 && i==0) {
                        mainString += "|";
                    }
                    
                }                
                mainString += "  ";
                
            }
            mainString += "\n";
            if (r==8) {
                mainString += " -----------\n";                
            }
        }
        for (int emptyLine=0; emptyLine<8; emptyLine++) {
            System.out.println();
        }
        return mainString;
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

}
