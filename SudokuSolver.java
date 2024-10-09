import java.util.Scanner;

public class SudokuSolver {

    public static void main(String args[]) {
        int sud[][] = new int[9][9];

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Sudoku board (9 rows, each with 1-9 integers):");
        System.out.println("Use '0' for empty cells.");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sud[i][j] = sc.nextInt();
                if (sud[i][j] < 0 || sud[i][j] > 9) {
                    System.out.println("Invalid input at position (" + i + "," + j + "). Please enter a number between 0 and 9.");
                    return;
                }
            }
        }

        // Validate initial Sudoku setup
        if (isValidSudoku(sud)) {
            System.out.println("Validated Sudoku Entered : ");
            display(sud); // Print initial board
            System.out.println("Solving the Sudoku:");
            if (solveSudoku(sud, 0, 0)) {
                display(sud); // Print solved board
            } else {
                System.out.println("No solution exists!");
            }
        } else {
            System.out.println("Invalid Sudoku!!!!");
        }
    }

    // Method to validate the Sudoku
    public static boolean isValidSudoku(int[][] board) {
        for (int i = 0; i < 9; i++) {
            boolean[] rowCheck = new boolean[10];
            boolean[] colCheck = new boolean[10];
            boolean[] gridCheck = new boolean[10];

            for (int j = 0; j < 9; j++) {
                // Check the row
                if (board[i][j] != 0) {
                    if (rowCheck[board[i][j]]) return false;
                    rowCheck[board[i][j]] = true;
                }

                // Check the column
                if (board[j][i] != 0) {
                    if (colCheck[board[j][i]]) return false;
                    colCheck[board[j][i]] = true;
                }

                // Check the 3x3 subgrid
                int gridRow = 3 * (i / 3) + j / 3;
                int gridCol = 3 * (i % 3) + j % 3;
                if (board[gridRow][gridCol] != 0) {
                    if (gridCheck[board[gridRow][gridCol]]) return false;
                    gridCheck[board[gridRow][gridCol]] = true;
                }
            }
        }
        return true;
    }

    // Backtracking method to solve the Sudoku
    public static boolean solveSudoku(int[][] board, int row, int col) {
        // If we reach the end of the board
        if (row == 9) return true;

        // Move to the next row if we finish this row
        if (col == 9) return solveSudoku(board, row + 1, 0);

        // If the cell is already filled, move to the next cell
        if (board[row][col] != 0) return solveSudoku(board, row, col + 1);

        // Try placing numbers 1 to 9 in the current empty cell
        for (int num = 1; num <= 9; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num; // Place number

                // Continue with the next cell
                if (solveSudoku(board, row, col + 1)) return true;

                board[row][col] = 0; // Backtrack
            }
        }
        return false; // Trigger backtracking if no number fits
    }

    // Method to check if a number can be placed in a specific cell
    public static boolean isSafe(int[][] board, int row, int col, int num) {
        // Check row and column
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
        }

        // Check 3x3 subgrid
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i + startRow][j + startCol] == num) return false;
            }
        }

        return true;
    }

    // Method to display the Sudoku board
    public static void display(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
