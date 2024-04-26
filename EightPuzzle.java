import java.awt.Color;
import java.awt.Font;
import java.util.List;

public class EightPuzzle {
    public static void main(String[] args) {
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(0, 4);
        StdDraw.setYscale(0, 4);
        StdDraw.enableDoubleBuffering();

        while (true) {

            // Crate a new board object, create inital and goal state.
            Board board = new Board();
            int[][] startState = board.twoDimArray;
            int[][] goalState = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        
            // Call drawStartScreen methode
            drawStartScreen();
            // Wait For user click if its click cont. program
            waitForStart();

            
            
            //Call numberOfInversion class to calculate it.
            int numberOfInversion = CountInversions.countInversions(startState);

            //Based on numberOfInversion if number even = solvabe if odd = unsolvable
            //if its add then its print unsolvable on screen
            if (numberOfInversion % 2 != 0){
                StdDraw.clear();
                drawUnsolvableScreen();
                StdDraw.pause(1000);
            }
            //If its even then contune with start game and animate solution
            if (numberOfInversion % 2 == 0) {
                StdDraw.clear();
                PuzzleNode solution = solveAndDraw(board, startState, goalState);
                if (solution != null) {
                    //when it's done draw succesfully done screen
                    drawSolvableScreen();
                    
            
            } 
        }
    }
    }

    //create Start screen for GUI
    public static void drawStartScreen() {
        StdDraw.clear(Color.WHITE);
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(2, 2.5, "START");
        StdDraw.text(2, 2, "Press START to begin the puzzle");
        StdDraw.show();
    }

    //create mousepressed for understand user click on the board.
    public static void waitForStart() {
        while (true) {
            //give limitation for clicked area to understand its click start
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                if (x >= 1.5 && x <= 2.5 && y >= 2 && y <= 3) {
                    break;
                }
            }
        }
    }

    //based on solution animate the call abimate the puzzle and write the movement on the board func.
    public static PuzzleNode solveAndDraw(Board board, int[][] startState, int[][] goalState) {
        PuzzleNode solution = EightPuzzleSolver.solvePuzzle(startState, goalState);
        if (solution != null) {
            List <String> movementList = EightPuzzleSolver.printSolutionPath(solution);
            for (int i = 0; i < movementList.size(); i++) {
                board.draw();
                StdDraw.show();
                StdDraw.pause(200);

                if (movementList.get(i).equals("R")) {
                    board.moveRight();
                    printMoveOnBord("Move: Right", 3.5, 0.2, board);
                }
                if (movementList.get(i).equals("L")) {
                    board.moveLeft();
                    printMoveOnBord("Move: Left", 3.5, 0.2, board);
                }
                if (movementList.get(i).equals("U")) {
                    board.moveUp();
                    printMoveOnBord("Move: Up", 3.5, 0.2, board);
                }
                if (movementList.get(i).equals("D")) {
                    board.moveDown();
                    printMoveOnBord("Move: Down", 3.5, 0.2, board);
                }
            }
            System.out.println("Number of move: "+ movementList.size());

        }

        return solution;
    }
    
    //methode for print movement on the board
    public static void printMoveOnBord(String move, double x, double y, Board board) {
        Color backgroundColor = new Color(145, 234, 255);                       //Create color for movements same as bg.
        // Clear the area below the board
        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledRectangle(x, y, 1.2, 0.2);
        // Print the current move
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.textRight(x, y, move);
        StdDraw.show();
        // Make the moving tile gray
        board.draw();
        StdDraw.setPenColor(Color.GRAY);
        StdDraw.pause(500); // Wait for the animation to complete
        board.draw(); // Redraw to reset the color
        StdDraw.show();
    }

    //for Solvable Screen Frame
    public static void drawSolvableScreen() {
        StdDraw.pause(500);
        StdDraw.clear(Color.GREEN);
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(2, 2, "PUZZLE SUCCESSFULLY SOLVED!");
        StdDraw.show();
        StdDraw.pause(5000); // 5 sn bekleyin
    }

    //for Unsolvable Screen Frame
    public static void drawUnsolvableScreen() {
        StdDraw.clear(Color.RED);
        StdDraw.setPenColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(2, 2, "PUZZLE UNSOLVABLE");
        StdDraw.show();
    }

    
}
