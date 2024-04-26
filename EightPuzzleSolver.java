import java.util.*;

class PuzzleNode {
    //current state
    int[][] state;
    int cost = 0;
    int heuristic;
    PuzzleNode parent;
    String moves; // to store movement in String format

    public PuzzleNode(int[][] state, int cost, PuzzleNode parent, String moves) {
        this.state = state;
        this.cost = cost;
        this.parent = parent;
        this.moves = moves;
        //calculate heuristic for node
        this.heuristic = calculateHeuristic();
    }

    // Calculate menhatten distance for heuristic
    public int calculateHeuristic() {
        int heuristic = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                int value = state[i][j];
                if (value != 0) {
                    //calculate target distance
                    int targetRow = (value - 1) / state[0].length;
                    int targetCol = (value - 1) % state[0].length;
                    //basic calc. for calculate distance
                    heuristic += Math.abs(targetRow - i) + Math.abs(targetCol - j);
                    
                }
            }
        }
        return heuristic;
    }
}

public class EightPuzzleSolver {

    public static PuzzleNode solvePuzzle(int[][] startState, int[][] goalState) {
        //openSet is using for store the possible configration to select the best one based on hueristic and coast
        PriorityQueue<PuzzleNode> openSet = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost + a.heuristic));
        //visited for store the configrations which are selected in every initial state for find the solution way.
        List<String> visited = new ArrayList<>();
        openSet.add(new PuzzleNode(startState, 0, null, ""));

        while (!openSet.isEmpty()) {    //cont is there any conf. in the open set
            //select the min f(x) value to select right confg. from openset
            PuzzleNode current = openSet.poll();    
            if (Arrays.deepEquals(current.state, goalState)) {  //verify ist it goal state or not
                return current; 
            }

            visited.add(Arrays.deepToString(current.state));    //every current which are selected by f(x) function added to the visited array
            
            // Create possible movement of empty cell
            int[] dr = {0, 0, 1, -1};
            int[] dc = {1, -1, 0, 0};
            String[] moves = {"R", "L", "D", "U"}; // Define movement types and store it
            //Create all possible movements of empty cell
            for (int i = 0; i < 4; i++) {
                int[][] nextState = makeMove(current.state, dr[i], dc[i]);
                //the next possible state is not not or not already visited add states in to the openSet for handling
                if (nextState != null && !visited.contains(Arrays.deepToString(nextState))) {
                    String newMoves = moves[i];
                    openSet.add(new PuzzleNode(nextState, current.cost + 1, current, newMoves));
                }
                
            }
           
        }
        return null;
    }

    //Try to make movement 
    public static int[][] makeMove(int[][] state, int dr, int dc) {
        int emptyRow = -1, emptyCol = -1;
        //Define where is empty cell
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
        }

        int newRow = emptyRow + dr;
        int newCol = emptyCol + dc;

        //Try to understand boarder lines of the board and understand iteration of empty cell with lines
        if (newRow < 0 || newRow >= state.length || newCol < 0 || newCol >= state[0].length) {
            return null;
        }

        //update state 
        int[][] newState = new int[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                newState[i][j] = state[i][j];
            }
        }

        newState[emptyRow][emptyCol] = state[newRow][newCol];
        newState[newRow][newCol] = 0;

        return newState;
    }
   
    //print solution path
    public static List<String> printSolutionPath(PuzzleNode solution) {
    List<int[][]> path = new ArrayList<>();
    List<String> movesList = new ArrayList<>();
    while (solution != null) {
        if (!solution.moves.isEmpty()) { // Skip adding empty moves
            path.add(0, solution.state);    //store solution states
            movesList.add(0, solution.moves); // add movement based on solution moves
            //System.out.println("Cost: " + solution.cost + ", Heuristic: " + solution.heuristic );   //for developer to see it's work correctly
        }
        solution = solution.parent;
    }
    System.out.println("Solution Path --> "+movesList);       //for developer to see it's work correctly
    return movesList;

    
}
   

}



