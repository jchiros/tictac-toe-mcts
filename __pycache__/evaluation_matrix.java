import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class TreeNode {
    // ... other fields ...

    // Method to traverse the tree and populate the evaluation matrix
    public void traverseTree(int[][] evaluationMatrix) {
        if (!children.isEmpty()) {
            for (TreeNode child : children.values()) {
                int[] lastMoveMade = child.board.getLastMoveMade();
                int row = lastMoveMade[0];
                int col = lastMoveMade[1];
                evaluationMatrix[row][col] = child.visits;
                child.traverseTree(evaluationMatrix);
            }
        }
    }

    // Method to create the evaluation matrix
    public int[][] createEvaluationMatrix() {
        int size = 3; // board size
        int[][] evaluationMatrix = new int[size][size];
        traverseTree(evaluationMatrix);
        return evaluationMatrix;
    }

    // ... other methods ...
}

public class TicTacToe {
    // ... other fields and methods ...

    // Main game loop
    public void gameLoop() {
        // ... your existing code ...

        if (isWin()) {
            System.out.printf("Player \"%c\" has won the game!\n", PLAYER_2);
            printEvaluationMatrix(bestMove.createEvaluationMatrix()); // Print evaluation matrix
            break;
        } else if (isDraw()) {
            System.out.println("Game is drawn!\n");
            printEvaluationMatrix(bestMove.createEvaluationMatrix()); // Print evaluation matrix
            break;
        }

        // ... your existing code ...
    }

    // ... other methods ...
    
    // Method to print the evaluation matrix
    private void printEvaluationMatrix(int[][] matrix) {
        System.out.println("Evaluation Matrix:");
        for (int[] row : matrix) {
            System.out.print("[");
            for (int value : row) {
                System.out.print(value + ", ");
            }
            System.out.println("]");
        }
    }
}

public class MCTS {
    // ... other fields and methods ...

    public static void main(String[] args) {
        Board board = new Board();
        MCTS mcts = new MCTS();
        TreeNode bestMove = mcts.search(board);

        // ... your existing code ...

        int[][] evaluationMatrix = bestMove.createEvaluationMatrix();
        printEvaluationMatrix(evaluationMatrix);
    }
}
