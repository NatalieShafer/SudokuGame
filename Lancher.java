import java.util.Scanner;

public class Lancher {
    private Sudoku inputSudoku;
    private SudokuSolver solver;

    public Lancher() {  this.inputSudoku = new Sudoku(9);  }

    private void createSolver() {  this.solver = new SudokuSolver(9);  }

    private void scanDigits() {
        int cellsToScan = 81;
        int y = 0;
        int x = 0;
        

        try (Scanner scanner = new Scanner(System.in)) {
          while (cellsToScan > 0) {
            if (!scanner.hasNextInt()) { break; }
            
            int cellValue = scanner.nextInt();
            inputSudoku.set(x++, y, cellValue);
            
            if (x == 9) 
            {
              x = 0;
              ++y;
            }
            --cellsToScan;
          }
        }
    }

    private void solve() {  Sudoku solution = solver.solve(inputSudoku);  }

    public static void main(String[] args) {
            javax.swing.SwingUtilities.invokeLater(() -> { 
                new SudokuFrame(); 
            });
    }
}