package maze;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        String options = "";
        Scanner scanner = new Scanner(System.in);
        String fileName = "";
        int[][] tempMaze = null;
        int[][] savedMaze = null;

        while (true) {

            System.out.println("=== Menu ===");
            System.out.println("1.Generate a new maze.");
            System.out.println("2.Load a maze.");

            if(!fileName.equals("")) {
                System.out.println("3.Save the maze.");
                System.out.println("4.Display the maze.");
                System.out.println("5.Solve the maze.");
            }

            System.out.println("0. Exit");

            options = scanner.nextLine();

            if(options.equals("1")) {
                MazeGenerator mazeGenerator = new MazeGenerator();
                tempMaze = mazeGenerator.generateMaze();
                MazePrinter mazePrinter = new MazePrinter();
                mazePrinter.printMaze(tempMaze);

            } else if (options.equals("2")) {
                MazeLoader mazeLoader = new MazeLoader();
                System.out.println("enter file path");
                fileName = scanner.nextLine();
                savedMaze = mazeLoader.loadMaze(fileName);

            } else if (options.equals("3")) {
                savedMaze = tempMaze.clone();
                System.out.println("Enter file name");
                fileName = scanner.nextLine();
                MazeWriter writer = new MazeWriter();
                writer.saveMazeToCSV(savedMaze , fileName);

            } else if (options.equals("4")) {
                if(savedMaze != null) {
                    MazePrinter mazePrinter = new MazePrinter();
                    mazePrinter.printMaze(savedMaze);
                } else {
                    System.out.println("No maze has been generated or loaded yet.");
                }

            } else if (options.equals("5")) {
                if(savedMaze != null) {
                    MazeSolver mazeSolver = new MazeSolver();
                    int[][] solvedMaze = new int[savedMaze.length][];
                    solvedMaze = mazeSolver.solveMaze(savedMaze);
                    MazePrinter mazePrinter = new MazePrinter();
                    mazePrinter.printMaze(solvedMaze);
                } else {
                    System.out.println("No maze has been generated or loaded yet.");
                }

            } else if (options.equals("0")) {
                System.out.println("Bye");
                System.exit(0);
            }
        }
    }
}