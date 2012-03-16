/*
 * Moustafa Ismael
 * Mohammad Abdussalaam
 *Ayoub Benguedouar
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;

public class Cell2 extends JPanel {
    private boolean tf;
    int[] poss;
    private String sNum;
    public JPanel jPanel;
    private JLabel[] possibleSolutions;
    private JLabel actualSolution = null;
    private int[] nums = new int[9];
    public int[] nakedPairs = new int[2];
    
    public Cell2() {        
        jPanel = new JPanel();
        GridLayout layout = new GridLayout(3,3);        
        jPanel.setLayout(layout);
        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        jPanel.setBorder(raisedBevel);
        
        initialize();    
        Dimension d = new Dimension(50, 50);
        jPanel.setPreferredSize(d);
    }
    
    public Cell2(int num){
        sNum = Integer.toString(num);
        jPanel = new JPanel();
        GridLayout layout = new GridLayout(1,1);        
        jPanel.setLayout(layout);
        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        jPanel.setBorder(raisedBevel);
        initSolved();
    }
    
     public void initSolved() {
            actualSolution = new JLabel();
            actualSolution.setText(sNum);
            actualSolution.setFont(new java.awt.Font("Times New Roman", 0, 35));    
            
            actualSolution.setVerticalAlignment(actualSolution.CENTER);
            actualSolution.setHorizontalAlignment(actualSolution.CENTER);
            jPanel.add(actualSolution);            
    }
     
     public void initialize() {
        possibleSolutions = new JLabel[9];
        Integer count = 1;
        JLabel counter;     
        
        for (int index=0; index<9; index++) {
            counter = new JLabel();
            counter.setVerticalAlignment(actualSolution.CENTER);
            counter.setHorizontalAlignment(actualSolution.CENTER);
            counter.setText(count.toString());
            possibleSolutions[index] = counter;
            jPanel.add(counter);
            nums[index] = count;
            count++;
        }
    }
     
    public boolean checkCell(int row, int col, SudokuNine sud){
        tf = false;
        String numPoss;
        if(!isSolution()){
            for(int i = 0; i<9; i++){
                if((!sud.isValid(row, col, nums[i]))&&nums[i]!=0){
                    possibleSolutions[i].setForeground(Color.RED);
                    nums[i] = 0;
                    tf = true;
                }
            }
            
        }
        return tf;
    }

    public void cleanCell(){
        if(!isSolution())
            for(int i = 0; i<9; i++)
                if(nums[i] == 0)
                    possibleSolutions[i].setText("   ");
    }

    public void onlyToActual(){
        int count = 0;
        int index = 0;
        for(int i = 0; i<9; i++){
            if(nums[i]==0)
                count++;
            else
                index = i;
        }
        if(count == 8){
            sNum = Integer.toString(nums[index]);

            jPanel.setLayout(new GridLayout(1,1));
            jPanel.removeAll();
            initSolved();            
        }
    }
    
    public int getActualNum(){
        int num = 0;
        if(isSolution())
            num = Integer.parseInt(sNum);
        return num;
    }
    
    public JPanel getPanel() {
        return jPanel;
    }
    
    public JLabel getLabel() {
        return actualSolution;
    }
    
    public boolean isSolution() {
        if (actualSolution != null) {
            return true;
        }
        else {
            return false;
        }
    }
    
    
    
    ///////////heishman's 
       public boolean isPossibleInvalid(int labelNumber) {
      if(possibleSolutions[labelNumber].getForeground() == Color.RED)
         return true;
      else
         return false;
   }
   public int numOfPossibles() {
       return possibleSolutions.length;
   }
   public void setPossibleInvalid(int labelNumber) {
      possibleSolutions[labelNumber].setForeground(Color.RED);
   }
   
   ////////////// Ours Continued
   public boolean compareTo(Cell cellToCompare) {
       int thisCell = nakedPairs[0] + nakedPairs[1];
       int thatCell = cellToCompare.nakedPairs[0] + cellToCompare.nakedPairs[1];
       
       if (thisCell == thatCell)
           return true;
       else
           return false;
       
   }             
   
   public boolean checkForNudity(){
       int count = 0;
       int[] indexList = new int[2];
       indexList = null;
       int counter = 0;
        
        for(int i = 0; i<9; i++){
            if(nums[i]!=0) {
                try {
                    indexList[counter] = i;
                    counter++;
                } catch (ArrayIndexOutOfBoundsException e){
                    indexList = null;
                }
            }
        } 
        if (counter == 1) {
            nakedPairs = null;
            return false;
        }   
        else { 
            nakedPairs = indexList;
            return true;
        }
   }
       
   
   
   
}

