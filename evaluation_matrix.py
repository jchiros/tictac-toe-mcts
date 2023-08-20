
def traverse_tree(current_node, evaluation_matrix):
    if current_node.children:
        for child in current_node.children.values():
            row, col = child.board.last_move_made  
            evaluation_matrix[col][row] = child.visits  
            traverse_tree(child, evaluation_matrix)

def create_evaluation_matrix(node):
    size = 3 # board size
    evaluation_matrix = [[0] * size for _ in range(size)]
    traverse_tree(node, evaluation_matrix)
    return evaluation_matrix

def print_evaluation_matrix(matrix):
    for row in matrix:
        print("[", end="")
        for value in row:
            print(value, end=", ")
        print("]")