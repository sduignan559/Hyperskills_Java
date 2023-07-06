package maze;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MazeLoader {

    static int[][] loadMaze(String filePath) {

        List<List<Integer>> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                List<Integer> row = new ArrayList<>();

                for (String value : values) {
                    row.add(Integer.parseInt(value));
                }

                rows.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numRows = rows.size();
        int numCols = rows.get(0).size();

        int[][] array = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            List<Integer> row = rows.get(i);

            for (int j = 0; j < numCols; j++) {
                array[i][j] = row.get(j);
            }
        }

        return array;
    }
}