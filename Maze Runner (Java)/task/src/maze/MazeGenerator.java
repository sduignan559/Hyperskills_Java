package maze;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MazeGenerator {
    private int width;
    private int height;
    private int wall = 0;
    private int gap = 1;

    public int[][] generateMaze() {
        getWidthHeight();

        int[][] maze = new int[height][width];
        boolean[][] visited = new boolean[height][width];
        initializeMaze(maze, visited);

        maze[1][0] = 1;

        // Create visited matrix
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                visited[0][j] = true;
                visited[i][0] = true;
                visited[i][width - 1] = true;
                visited[height - 1][j] = true;
            }
        }

        // Create a list of walls
        List<int[]> walls = new ArrayList<>();

        // Current cell
        int x = 1;
        int y = 1;
        maze[x][y] = 1;
        visited[x][y] = true;
        walls.add(new int[]{x, y});

        // End cell
        maze[height - 2][width - 1] = 2;

        String[] directions = {"down", "left", "right", "up"};
        int k = 1;

        // Visit each direction and mark the first one not already visited as a gap
        while (maze[x][y] != 2) {
            boolean moved = false;
            shuffleArray(directions);

            for (int i = 0; i < directions.length; i++) {
                String direction = directions[i];
                int newX = x;
                int newY = y;

                if (direction.equals("left") && y - 1 >= 0 && !visited[x][y - 1]) {
                    visited[x][y - 1] = true;
                    newY = y - 1;
                    moved = true;
                } else if (direction.equals("right") && y + 1 < width && !visited[x][y + 1]) {
                    newY = y + 1;
                    visited[x + 1][y] = true;
                    moved = true;
                } else if (direction.equals("down") && x + 1 < height && !visited[x + 1][y]) {
                    newX = x + 1;
                    visited[x - 1][y] = true;
                    moved = true;
                } else if (direction.equals("up") && x - 1 >= 0 && !visited[x - 1][y]) {
                    visited[x - 1][y] = true;
                    newX = x - 1;
                    moved = true;
                }

                if (moved) {
                    maze[newX][newY] = gap;
                    x = newX;
                    y = newY;
                    break;
                }
            }

            if (!moved) {
                // Move back
                if (walls.isEmpty()) {
                    break; // No more walls to backtrack, maze generation completed
                }

                int[] wall = walls.remove(walls.size() - 1);
                x = wall[0];
                y = wall[1];
            } else {
                walls.add(new int[]{x, y});
            }

            k = k + 1;
        }

        return maze;
    }

    private void getWidthHeight() {
        System.out.println("Please, enter the size of a maze");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        height = Integer.parseInt(input);
        width = height;
    }

    private void initializeMaze(int[][] maze, boolean[][] visited) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = wall;
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                visited[i][j] = false;
            }
        }
    }

    private void shuffleArray(String[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            String temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
