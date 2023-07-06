package maze;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
public class MazeWriter {

    public void saveMazeToCSV(int[][] maze, String fileName) {
        try {
            BufferedWriter writer = null;
            writer = new BufferedWriter(new FileWriter(new File(fileName)));
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    writer.write(Integer.toString(maze[i][j]));
                    if (j != maze[i].length - 1) {
                        writer.write(',');
                    }
                }
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the maze to a file.");
            e.printStackTrace();
        }
    }
}
