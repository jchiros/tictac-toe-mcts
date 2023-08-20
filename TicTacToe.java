import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

// Tic Tac Toe board class
class Board {
    // Define constants for players and empty squares
    private final char PLAYER_1 = 'x';
    private final char PLAYER_2 = 'o';
    private final char EMPTY_SQUARE = '.';

    private char currentPlayer = PLAYER_1;
    
    private char[][] position;
    private int[] lastMoveMade;

    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    // Constructor
    public Board() {
        position = new char[3][3];
        lastMoveMade = new int[2];
        initBoard();
    }
    
    // Initialize the board
    private void initBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                position[row][col] = EMPTY_SQUARE;
            }
        }
    }

     // Method to switch players
    private void switchPlayers() {
        if (currentPlayer == PLAYER_1) {
            currentPlayer = PLAYER_2;
        } else {
            currentPlayer = PLAYER_1;
        }
    }
    
    // Make a move and return a new board instance
    public Board makeMove(int row, int col) {
        Board newBoard = new Board();
        for (int r = 0; r < 3; r++) {
            System.arraycopy(position[r], 0, newBoard.position[r], 0, 3);
        }
        
        newBoard.position[row][col] = PLAYER_1;
        newBoard.lastMoveMade[0] = row;
        newBoard.lastMoveMade[1] = col;
        
        switchPlayers();

        return newBoard;
    }
    
    // Check if the game is a draw
    public boolean isDraw() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (position[row][col] == EMPTY_SQUARE) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // Check if the game is won
    public boolean isWin() {
        // Check rows, columns, and diagonals for a win
        // Implementation left to you
        
        return false;
    }
    
    // Generate legal moves
    public List<Board> generateStates() {
        List<Board> actions = new ArrayList<>();
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (position[row][col] == EMPTY_SQUARE) {
                    actions.add(makeMove(row, col));
                }
            }
        }
        
        return actions;
    }
    
    // Main game loop
    public void gameLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n  Tic Tac Toe by Code Monkey King\n");
        System.out.println("  Type \"exit\" to quit the game");
        System.out.println("  Move format [x,y]: 1,2 where 1 is row and 2 is column");
        
        System.out.println(this);
        
        // Game loop
        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine();
            
            if (userInput.equals("exit")) {
                break;
            }
            
            // Parse user input
            try {
                String[] inputParts = userInput.split(",");
                int row = Integer.parseInt(inputParts[0].trim()) - 1;
                int col = Integer.parseInt(inputParts[1].trim()) - 1;
                
                if (position[row][col] != EMPTY_SQUARE) {
                    System.out.println(" Illegal move!");
                    continue;
                }
                
                this.position = makeMove(row, col).position;
                System.out.println(this);
                
                // Search for the best move (AI move)
                // Implementation left to you
                
                System.out.println(this);
                
                if (isWin()) {
                    System.out.printf("Player \"%c\" has won the game!\n", PLAYER_2);
                    break;
                } else if (isDraw()) {
                    System.out.println("Game is drawn!\n");
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
                System.out.println("  Illegal command!");
                System.out.println("  Move format [x,y]: 1,2 where 1 is row and 2 is column");
            }
        }
        
        scanner.close();
    }
    
    // Override toString to print board state
    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardString.append(" ").append(position[row][col]);
            }
            boardString.append("\n");
        }
        
        if (currentPlayer == 'x') {
            boardString.insert(0, "\n--------------\n \"x\" to move:\n--------------\n\n");
        } else if (currentPlayer == 'o') {
            boardString.insert(0, "\n--------------\n \"o\" to move:\n--------------\n\n");
        }

        
        return boardString.toString();
    }
    
    // Other methods left to you
    
}

public class TicTacToe {
    public static void main(String[] args) {
        Board board = new Board();
        board.gameLoop();
    }
}
