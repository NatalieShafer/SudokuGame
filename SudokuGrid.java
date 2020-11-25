import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JSeparator;
import javax.swing.*;

final class SudokuGrid extends JPanel {

    private static final Font FONT = new Font("Verdana", Font.CENTER_BASELINE, 20);
    private final JTextField[][] grid;
    private final Map<JTextField, Point> mapFieldToCoordinates = new HashMap<>();
    private final JPanel gridPanel;
    private final JPanel mainbuttonPanel;
    private final JPanel rightbuttonPanel;
    private final JPanel leftbuttonPanel;
    private final JButton solveButton;
    private final JButton clearButton;
    private final JButton newGameButton;
    private final JButton checkButton;
    private final JPanel[][] minisquarePanels;
    public static int currentPuzzel=0;
    public static int[] solvingArray;
    public static String[] origPuzzle;

    SudokuGrid() {
        this.grid = new JTextField[9][9];

        for (int y = 0; y < 9; ++y) {
            for (int x = 0; x < 9; ++x) {
                JTextField field = new JTextField();
                field.addKeyListener(new SudokuCellKeyListener(this));
                mapFieldToCoordinates.put(field, new Point(x, y));
                grid[y][x] = field;
            }
        }

        this.gridPanel   = new JPanel();
        this.mainbuttonPanel = new JPanel();
        this.leftbuttonPanel = new JPanel();
        this.rightbuttonPanel = new JPanel();

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Dimension fieldDimension = new Dimension(30, 30);

        for (int y = 0; y < 9; ++y) {
            for (int x = 0; x < 9; ++x) {
                JTextField field = grid[y][x];
                field.setBorder(border);
                field.setFont(FONT);
                field.setPreferredSize(fieldDimension);
            }
        }
       
        this.gridPanel.setLayout(new GridLayout(3, 3));
        this.minisquarePanels = new JPanel[3][3];
        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(3, 3));
                panel.setBorder(minisquareBorder);
                minisquarePanels[y][x] = panel;
                gridPanel.add(panel);
            }
        }

        for (int y = 0; y < 9; ++y) {
            for (int x = 0; x < 9; ++x) {
                int minisquareX = x / 3;
                int minisquareY = y / 3;
                minisquarePanels[minisquareY][minisquareX].add(grid[y][x]);
            }
        }

        this.gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.clearButton = new JButton("Clear");
        this.solveButton = new JButton("Solve");
        this.newGameButton = new JButton("New Game");
        this.checkButton = new JButton("Check");
        
        this.mainbuttonPanel.setLayout(new BorderLayout());
        this.leftbuttonPanel.setLayout(new BorderLayout());
        this.rightbuttonPanel.setLayout(new BorderLayout());
        
        this.leftbuttonPanel.add(clearButton, BorderLayout.WEST);
        this.leftbuttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        this.leftbuttonPanel.add(newGameButton, BorderLayout.EAST);
        this.rightbuttonPanel.add(solveButton, BorderLayout.EAST);
        this.rightbuttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        this.rightbuttonPanel.add(checkButton, BorderLayout.WEST);
        
        this.mainbuttonPanel.add(leftbuttonPanel, BorderLayout.WEST);
        this.mainbuttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        this.mainbuttonPanel.add(rightbuttonPanel, BorderLayout.EAST);
        
        
        this.setLayout(new BorderLayout());
        this.add(gridPanel,   BorderLayout.CENTER);
        this.add(mainbuttonPanel, BorderLayout.SOUTH);  

        clearButton.addActionListener((ActionEvent e) -> {
            clear();
        });

        solveButton.addActionListener((ActionEvent e) -> {
            solve();
        });
        
        checkButton.addActionListener((ActionEvent e) -> {
            check();
        });
        
        newGameButton.addActionListener((ActionEvent e) -> {
          newGame();
        });
        
        newGame();
    }
    
    public void openFile(File file) {
      origPuzzle = new String[]{" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "};
      try {
        Scanner scanner = new Scanner(file);
        clearAll();
        
        String text = scanner.nextLine();
        char[] ch = new char[text.length()];
        
        //insted of spliting by " " use the chars of the string and only use 81 
        // so you dont go over the arrays
        //use wordinout.java as reference
        //String splitinput[] = text.split("");
        int length = text.length();
        int count =0;

        
        for (int i = 0; i < length; i++) {ch[i] = text.charAt(i); }
        
        for (int y = 0; y < 9; ++y) {
          for (int x = 0; x < 9; ++x) {
        
            if(count<length)
            {
              if(ch[count] == '1' || ch[count] == '2' || ch[count] == '3' || ch[count] == '4' || ch[count] == '5' || ch[count] == '6' || ch[count] == '7' || ch[count] == '8' || ch[count] == '9' ){
                (grid[y][x]).setForeground(Color.BLACK);
                grid[y][x].setText(" " + ch[count]);
                
                origPuzzle[count] = (origPuzzle[count] + ch[count]);
                grid[y][x].setEditable(false);
              }
              else
              {
                grid[y][x].setText(" ");
                (grid[y][x]).setForeground(Color.BLUE);
              }    
              count++;
            }
            else
            {
              grid[y][x].setText(" ");
            (grid[y][x]).setForeground(Color.BLUE);
            }
          }
        }
        
      } catch (FileNotFoundException ex) {}
    }
    
    private void addSpace(JTextField field) {
        if (field.getText().isEmpty()) {
            field.setText(" ");
        }
    }

    void moveCursor(JTextField field, char c) {
        Point coordinates = mapFieldToCoordinates.get(field);
        field.setBackground(Color.WHITE);

        switch (c) {
            case 'w':
            case 'W':

                if (coordinates.y > 0) {
                    grid[coordinates.y - 1][coordinates.x].requestFocus();
                    addSpace(grid[coordinates.y - 1][coordinates.x]);
                }

                break;

            case 'd':
            case 'D':

                if (coordinates.x < 8) {
                    grid[coordinates.y][coordinates.x + 1].requestFocus();
                    addSpace(grid[coordinates.y][coordinates.x + 1]);
                }

                break;

            case 's':
            case 'S':

                if (coordinates.y < 8) {
                    grid[coordinates.y + 1][coordinates.x].requestFocus();
                    addSpace(grid[coordinates.y + 1][coordinates.x]);
                }

                break;

            case 'a':
            case 'A':

                if (coordinates.x > 0) {
                    grid[coordinates.y][coordinates.x - 1].requestFocus();
                    addSpace(grid[coordinates.y][coordinates.x - 1]);
                }

                break;
        }
    }
     
    
    void moveCursor(JTextField field, String c) {
        Point coordinates = mapFieldToCoordinates.get(field);
        field.setBackground(Color.WHITE);

        switch (c) {
            case "up":
                if (coordinates.y > 0) {
                    grid[coordinates.y - 1][coordinates.x].requestFocus();
                    addSpace(grid[coordinates.y - 1][coordinates.x]);
                }

                break;

            case "right":

                if (coordinates.x < 8) {
                    grid[coordinates.y][coordinates.x + 1].requestFocus();
                    addSpace(grid[coordinates.y][coordinates.x + 1]);
                }

                break;

            case "down":

                if (coordinates.y < 8) {
                    grid[coordinates.y + 1][coordinates.x].requestFocus();
                    addSpace(grid[coordinates.y + 1][coordinates.x]);
                }

                break;

            case "left":

                if (coordinates.x > 0) {
                    grid[coordinates.y][coordinates.x - 1].requestFocus();
                    addSpace(grid[coordinates.y][coordinates.x - 1]);
                }

                break;
        }
    }
    
    
    void clearAll() {
      for (JTextField[] row : grid) {
        for (JTextField field : row) {
          field.setText("");
          field.setEditable(true);
        }
      }
    }
    
    void clear() {
      clearAll();
      int count = 0;
      for (int y = 0; y < 9; ++y) {
        for (int x = 0; x < 9; ++x) {
          grid[y][x].setText(origPuzzle[count]);
          if(origPuzzle[count] == " ")
          (grid[y][x]).setForeground(Color.BLUE);
          
          
          if(origPuzzle[count] != " " )
          {
           grid[y][x].setEditable(false);
          (grid[y][x]).setForeground(Color.BLACK);
          }
          count++;
        }
      }
      
    }
    
    
    
    void newGame() {
      for (int y = 0; y < 9; ++y) {
        for (int x = 0; x < 9; ++x) {
          grid[y][x].setEditable(true);
        }
      }
      
      origPuzzle = new String[]{" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," "};
      
      origPuzzle = getPuzzle();
      int count=0;       
      
      for (int y = 0; y < 9; ++y) {
        for (int x = 0; x < 9; ++x) {
          grid[y][x].setText(origPuzzle[count]);
          (grid[y][x]).setForeground(Color.BLUE);
          if(origPuzzle[count] != " ")
          {
            (grid[y][x]).setForeground(Color.BLACK);
            grid[y][x].setEditable(false);
          }
          count++;
        }
      }
    }
    
    void solve() {
      
      solvingArray = new int[81];
      Sudoku sudoku = new Sudoku(9);
      int count=0;  
      for (int y = 0; y < 9; ++y) {
        for (int x = 0; x < 9; ++x) {
          String usertext = grid[y][x].getText();
          String origtext = origPuzzle[count];
          
          int usernumber = -1;
          int orignumber = -1;
          
          try { orignumber = Integer.parseInt(origtext.trim());
          } catch (NumberFormatException ex) {}
          
          try {usernumber = Integer.parseInt(usertext.trim());
          }catch (NumberFormatException ex) {}
          
          solvingArray[count] = usernumber;
          sudoku.set(x, y, orignumber);
          count++;
        }
      }
      
      try {
        Sudoku solution = new SudokuSolver(9).solve(sudoku);
        String skip = 9 < 10 ? " " : "";
        int count2=0;  
        
        count2=0; 
        
        for (int y = 0; y < 9; ++y) {
          for (int x = 0; x < 9; ++x) {
            
            if(origPuzzle[count2] == " ")
            {                    
              grid[y][x].setText(skip + solution.get(x, y));
              (grid[y][x]).setForeground(Color.BLUE); 
            }
            else if(solvingArray[count2] != solution.get(x, y))
            {
              grid[y][x].setText(skip + solution.get(x, y));
              (grid[y][x]).setForeground(Color.RED); 
            }
            else
            {
              grid[y][x].setText(skip + solution.get(x, y));
              (grid[y][x]).setForeground(Color.BLACK); 
            }
            count2++;
          }
        }
        
        if (!solution.isValid()) { throw new RuntimeException("Something gone wrong."); }  
        
      } catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);}

    }

    void check() {
      Sudoku sudoku = new Sudoku(9);
      
      for (int y = 0; y < 9; ++y) {
        for (int x = 0; x < 9; ++x) {
          String text = grid[y][x].getText();
          
          int number = -1;
          
          try {
            number = Integer.parseInt(text.trim());
          } catch (NumberFormatException ex) {          }
          
          sudoku.set(x, y, number);
        }
      }
      try {
        Sudoku solution = new SudokuSolver(9).solve(sudoku);
        String skip = 9 < 10 ? " " : "";
        
        if (!solution.isValid()) {          throw new RuntimeException("Something gone wrong.");        }
      } catch (Exception ex) {        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);      }

      solvingArray = new int[81];

      sudoku = new Sudoku(9);
      int count=0;  
      for (int y = 0; y < 9; ++y) {
        for (int x = 0; x < 9; ++x) {
          String usertext = grid[y][x].getText();
          String origtext = origPuzzle[count];
          
          int usernumber = -1;
          int orignumber = -1;
          
          try { orignumber = Integer.parseInt(origtext.trim());
          } catch (NumberFormatException ex) {}
          
          try {usernumber = Integer.parseInt(usertext.trim());
          }catch (NumberFormatException ex) {}
          
          solvingArray[count] = usernumber;
          sudoku.set(x, y, orignumber);
          count++;
        }
      }
      
      count=0;
      
      try {
        Sudoku solution = new SudokuChecker(9).solve(sudoku);
        
        //if(nospaces)
        //{
        String skip = 9 < 10 ? " " : "";
        int count2=0;  
        
        count2=0; 
        
        for (int y = 0; y < 9; ++y) {
          for (int x = 0; x < 9; ++x) {
            
            if(solvingArray[count2] == -1 )
            {
            (grid[y][x]).setForeground(Color.BLUE); 
            }
            
            if(solvingArray[count2] != solution.get(x, y) && solvingArray[count2] != -1 )
            {
              grid[y][x].setText(" " + String.valueOf(solvingArray[count2]));
              (grid[y][x]).setForeground(Color.RED); 
            }
            if(solvingArray[count2] == solution.get(x, y) && origPuzzle[count2] == " ")
            {                    
              grid[y][x].setText(skip + solution.get(x, y));
              (grid[y][x]).setForeground(Color.BLUE); 
            }
           
            count2++;
          }
        }
        
       // }
        
        
        if (!solution.isValid()) { throw new RuntimeException("Something gone wrong."); }  
        
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static String[] getPuzzle()   {
      
        
     int num = (int)(Math.random()*10);   
        
     while(currentPuzzel == num)
     {
       num = (int)(Math.random()*10);
     
     }
       currentPuzzel = num; 
       
        
      String[][] arrays = new String[][]{
      {" 3"," 5"," "," "," "," 7"," "," 4"," "," "," 4"," 6"," 1"," "," "," "," "," 5"," "," "," 2"," "," "," 5"," 3"," "," 6"," "," 3"," "," 9"," "," 6"," "," "," "," 4"," 2"," "," "," 1"," "," "," 9"," 3"," "," "," "," 3"," "," 2"," "," 8"," "," 5"," "," 7"," 8"," "," "," 9"," "," "," 8"," "," "," "," "," 1"," 5"," 6"," "," "," 6"," "," 5"," "," "," "," 7"," 8"},
        {" "," "," "," 9"," "," 3"," "," 2"," 1"," 8"," "," 3"," 5"," "," "," "," 6"," 9"," "," 4"," 9"," "," "," "," 5"," "," "," 9"," "," "," 3"," "," "," "," "," 2"," "," 5"," "," 6"," 9"," 7"," "," 1"," "," 7"," "," "," "," "," 8"," "," "," 5"," "," "," 2"," "," "," "," 1"," 5"," "," 6"," 1"," "," "," "," 2"," 8"," "," 7"," 4"," 9"," "," 7"," "," 5"," "," "," "},
          {" "," 8"," 3"," 7"," "," "," 9"," "," "," 1"," 5"," "," "," 4"," 8"," 3"," "," "," "," "," "," "," "," 9"," "," 6"," 5"," 4"," "," 1"," 3"," 8"," "," 2"," "," "," 3"," "," "," "," "," "," "," "," 8"," "," "," 8"," "," 1"," 7"," 4"," "," 3"," 8"," 4"," "," 9"," "," "," "," "," "," "," "," 6"," 8"," 2"," "," "," 5"," 9"," "," "," 2"," "," "," 3"," 7"," 8"," "},
            {" "," 6"," "," "," 5"," 2"," 1"," "," "," 2"," 8"," "," "," "," 1"," 7"," 6"," "," "," "," 5"," 8"," 6"," "," "," "," 3"," "," "," 3"," 7"," "," "," 4"," "," 8"," "," "," "," 4"," "," 9"," "," "," "," 1"," "," 4"," "," "," 5"," 3"," "," "," 6"," "," "," "," 7"," 4"," 8"," "," "," "," 3"," 1"," 5"," "," "," "," 4"," 7"," "," "," 8"," 6"," 9"," "," "," 5"," "},   
              {" 4"," "," "," "," "," 6"," 1"," 7"," "," "," "," "," 4"," "," "," "," 8"," "," 5"," "," "," 2"," "," 8"," 6"," "," "," "," "," "," "," 3"," 1"," 4"," 2"," "," "," 3"," "," "," 8"," "," "," 6"," "," "," 7"," 1"," 5"," 2"," "," "," "," "," "," "," 6"," 1"," "," 2"," "," "," 3"," "," 5"," "," "," "," 9"," "," "," "," "," 4"," 9"," 7"," "," "," "," "," 8"},
                {" "," "," 1"," 7"," 4"," 3"," "," 2"," "," "," 5"," 7"," "," "," "," "," "," 4"," "," "," 3"," "," "," 5"," "," 9"," "," "," 4"," 5"," "," "," 6"," "," "," "," "," 6"," "," 3"," 1"," 2"," "," 5"," "," "," "," "," 4"," "," "," 1"," 8"," "," "," 1"," "," 2"," "," "," 7"," "," "," 9"," "," "," "," "," "," 8"," 6"," "," "," 7"," "," 6"," 8"," 1"," 9"," "," "},
                  {" 9"," "," 4"," "," "," 8"," "," "," 3"," 1"," "," "," "," 9"," "," "," 4"," "," "," "," "," 1"," 5"," "," "," "," 9"," "," 7"," "," 5"," "," "," 8"," 2"," 1"," "," "," "," 4"," 1"," 6"," "," "," "," 5"," 3"," 1"," "," "," 7"," "," 6"," "," 3"," "," "," "," 8"," 2"," "," "," "," "," 2"," "," "," 4"," "," "," "," 7"," 7"," "," "," 9"," "," "," 3"," "," 2"},
                    {" "," 3"," "," "," "," 9"," "," "," "," "," 8"," "," "," 1"," 7"," 3"," "," 9"," "," "," 1"," 5"," "," "," "," 6"," 8"," 1"," "," "," "," 2"," 4"," 5"," "," "," "," 2"," "," "," 9"," "," "," 7"," "," "," "," 9"," 8"," 3"," "," "," "," 1"," 9"," 4"," "," "," "," 1"," 2"," "," "," 8"," "," 5"," 4"," 6"," "," "," 3"," "," "," "," "," 9"," "," "," "," 1"," "},
                      {" "," "," 1"," 2"," 6"," "," 7"," 5"," "," "," 7"," "," "," "," 9"," 1"," "," "," "," 6"," "," 5"," "," "," "," 8"," "," "," 8"," "," 4"," 2"," "," "," "," 3"," 1"," "," "," "," 9"," "," "," "," 8"," 2"," "," "," "," 3"," 6"," "," 9"," "," "," 3"," "," "," "," 2"," "," 4"," "," "," "," 4"," 6"," "," "," "," 1"," "," "," 1"," 6"," "," 8"," 4"," 2"," "," "},
                        {" 4"," "," "," "," "," 6"," "," "," "," 2"," "," 1"," 4"," "," "," "," 3"," 7"," "," 5"," 6"," 7"," "," 2"," "," "," 1"," "," "," 3"," "," 2"," "," "," 7"," "," "," 1"," "," 8"," "," 4"," "," 2"," "," "," 2"," "," "," 6"," "," 9"," "," "," 1"," "," "," 6"," "," 7"," 2"," 5"," "," 9"," 6"," "," "," "," 5"," 4"," "," 3"," "," "," "," 9"," "," "," "," "," 8"},                        
      };
      

      String holder[] = new String [81]; 
      
      for(int i=0; i<81; i++)
      {        holder[i] = arrays[currentPuzzel][i];      }
     // for(int i=0; i<81; i++)
      //{        System.out.print(holder[i]+" ");      }
      return holder;

    }
    
    
}

