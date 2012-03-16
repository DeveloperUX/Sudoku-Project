
/**
 * Ayoub Benguedouar
 * Moustafa Ismael
 * Mohammad Abdussalaam
 */
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

public class AppFrame extends JFrame{
    private int switches = 0;
    private boolean tf;
    private int count = 0;
    private JButton next = new JButton("NEXT");
    private GridLayout puzzleLayout = new GridLayout(9,9);
    private Cell[][] cells = new Cell[9][9];
    private SudokuNine sud;
    private Scanner scan = null;
    private String toSolve;
    
    public AppFrame(){
        super("Sudoku");
        setResizable(false);
        
    }
    
    public void addComponentsToPane(final Container pane) {
        
        final JPanel puzzleGrid = new JPanel();
        puzzleGrid.setLayout(puzzleLayout);
        
        
        try {
            scan = new Scanner(new File("sudoku.dat"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        //toSolve = "007050300081403590000206000705030408008040200204010607000307000059104870006080100";
        //toSolve = scan.nextLine();

        sud = new SudokuNine();         
        sud.generateSolution();        
        sud.generatePuzzle(53);
        toSolve = sud.toStringInOneLine();
        sud = new SudokuNine(toSolve);
        cells = sud.getCells();
        for(int r = 0; r<9; r++){
            for(int c = 0; c<9; c++){
                puzzleGrid.add(cells[r][c].getPanel());
            }
        }
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout());
        controls.add(next);
        next.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switch(count%3){
                    case 0:
                        tf = false;
                        for(int r = 0; r<9; r++){
                            for(int c = 0; c<9; c++){
                                
                                if(cells[r][c].checkCell(r, c, sud))
                                    switches = 1;
                            }
                        }
                        System.out.print(switches);
                        if((switches == 0)&&(!isComplete())) {
                            System.out.print("DONE!!! FINISH THE PROJECT YOU FOOLS!! FORGET HAND STANDS THIS IS DUE TODAY!!");
                            
                            sud.compareCells();
                            //sud.sortCells();
                            
                            
                        }
                        switches = 0;
                        
                        break;
                    case 1:
                        for(int r = 0; r<9; r++){
                            for(int c = 0; c<9; c++){
                                cells[r][c].cleanCell();
                                
                            }
                        }
                        break;
                    case 2:
                        for(int r = 0; r<9; r++){
                            for(int c = 0; c<9; c++){
                                cells[r][c].onlyToActual();
                                if(cells[r][c].isSolution())
                                    sud.setElement(r, c, cells[r][c].getActualNum());
                                    
                            }
                        }
                        
                        break;
                    }
                    count++;
                    restart(puzzleGrid);
                    validate();
            }
            
        });
        pane.add(puzzleGrid, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);
        pane.add(controls, BorderLayout.SOUTH);
    } 
    
    private void initialize() {
         sud = new SudokuNine();         
         sud.generateSolution();        
         sud.generatePuzzle(53);
         System.out.println(sud);
   }

    public boolean isComplete() {
        
        for(int r = 0; r<9; r++){
            for(int c = 0; c<9; c++){
                if(!cells[r][c].isSolution())
                     return false;
            }
        }
        return true;
    }
    public void restart(JPanel puzzleGrid){
        if(isComplete()&&scan.hasNext()&&count%3==1){
                        toSolve = scan.nextLine();

                        sud = new SudokuNine(toSolve);
                        cells = sud.getCells();
                        puzzleGrid.removeAll();
                        for(int r = 0; r<9; r++){
                            for(int c = 0; c<9; c++){
                                puzzleGrid.add(cells[r][c].getPanel());
                            }
                        }
                        count = 0;
        }
        else if(isComplete()&&!scan.hasNext()&&count%3==1){
            System.exit(0);
        }
    }
}
