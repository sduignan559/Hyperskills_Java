package maze;

public class MazePrinter {

    public void printMaze(int[][] maze) {
        int height = maze.length;
        int width = maze[0].length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze[i][j] == 0) {
                    System.out.print("\u2588\u2588");
                }
                if (maze[i][j] == 1 || maze[i][j] == 2) {
                    System.out.print("  ");
                }

                if (maze[i][j] == 3) {
                    System.out.print("//");
                }

                if (j == width - 1) {
                    System.out.println();
                }
            }
        }
    }
}




