# Overview
This Java program solves a variant of the Peg Solitaire game. It recursively searches for the optimal set of moves that minimizes the number of balls ("o") remaining on a fixed 9×5 'board' (2D Array). Among equally effective solutions (in terms of remaining balls), it favors the one requiring the fewest moves.

# Logic

  The board is passed as a 2D String[][], representing pegs ("o"), empty cells ("."), and blocks ("#").
  
  The algorithm scans the board for pegs, attempting jumps in all four directions (right, down, left, up), defined by rowChange and columnChange.
  
  A move is valid if:
    There's a peg to jump ("o"),
    There's another peg adjacent in the move direction,
    The landing cell two steps away is empty (".") and within bounds.
  When a move is possible:
    The current board state (String[][]) and move counters (int[]) are pushed to stackBoard and stackCounters.
    The move is made, counters are updated, and solveGame is called recursively.
    After recursion, state is backtracked by popping from the stacks.
    If no moves are possible (movesPossible == false), the current configuration is evaluated.
    A solution is better if fewer pegs remain, or the same number with fewer moves.

  # Technicality: State Management
