import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

// Tree node class definition
class TreeNode {
    Board board;
    boolean isTerminal;
    boolean isFullyExpanded;
    TreeNode parent;
    int visits;
    double score;
    Map<String, TreeNode> children;

    public TreeNode(Board board, TreeNode parent) {
        this.board = board;
        this.parent = parent;
        this.isTerminal = board.isWin() || board.isDraw();
        this.isFullyExpanded = this.isTerminal;
        this.visits = 0;
        this.score = 0;
        this.children = new HashMap<>();
    }
}

// MCTS class definition
class MCTS {
    private static final int ITERATIONS = 1000;
    private static final double EXPLORATION_CONSTANT = 2.0;

    // Search for the best move in the current position
    public TreeNode search(Board initialBoard) {
        TreeNode root = new TreeNode(initialBoard, null);

        for (int iteration = 0; iteration < ITERATIONS; iteration++) {
            TreeNode node = select(root);
            double score = rollout(node.board);
            backpropagate(node, score);
        }

        try {
            return getBestMove(root, 0);
        } catch (Exception e) {
            return null;
        }
    }

    // Select the most promising node
    private TreeNode select(TreeNode node) {
        while (!node.isTerminal) {
            if (node.isFullyExpanded) {
                node = getBestMove(node, EXPLORATION_CONSTANT);
            } else {
                return expand(node);
            }
        }
        return node;
    }

    // Expand node
    private TreeNode expand(TreeNode node) {
        List<Board> states = node.board.generateStates();
        Random random = new Random();

        for (Board state : states) {
            String stateString = state.toString();
            if (!node.children.containsKey(stateString)) {
                TreeNode newNode = new TreeNode(state, node);
                node.children.put(stateString, newNode);
                if (states.size() == node.children.size()) {
                    node.isFullyExpanded = true;
                }
                return newNode;
            }
        }
        return null; // Should not get here
    }

    // Simulate the game via making random moves until the end
// Simulate the game via making random moves until the end
    private double rollout(Board board) {
        while (!board.isWin()) {
            try {
                List<Board> states = board.generateStates();
                board = states.get(new Random().nextInt(states.size()));
            } catch (Exception e) {
                return 0;
            }
        }

        if (board.getCurrentPlayer() == 'x') {
            return 1;
        } else if (board.getCurrentPlayer() == 'o') {
            return -1;
        }
        return 0;
}

    // Backpropagate the number of visits and score up to the root node
    private void backpropagate(TreeNode node, double score) {
        while (node != null) {
            node.visits++;
            node.score += score;
            node = node.parent;
        }
    }

    // Select the best node based on UCB1 formula
    private TreeNode getBestMove(TreeNode node, double explorationConstant) {
        double bestScore = Double.NEGATIVE_INFINITY;
        List<TreeNode> bestMoves = new ArrayList<>();

        for (TreeNode childNode : node.children.values()) {
            double currentPlayer = (childNode.board.getCurrentPlayer() == 'x') ? 1 : -1;
            double moveScore = currentPlayer * childNode.score / childNode.visits
                    + explorationConstant * Math.sqrt(Math.log(node.visits) / childNode.visits);

            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestMoves.clear();
                bestMoves.add(childNode);
            } else if (moveScore == bestScore) {
                bestMoves.add(childNode);
            }
        }

        return bestMoves.get(new Random().nextInt(bestMoves.size()));
    }
}

// Other classes omitted for brevity
