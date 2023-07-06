package analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ExecutorService pool = Executors.newFixedThreadPool(50);

        String path = "";
        String patternFile = "";
        String resultString = "";
        String algorithm = "";

        //algorithm = args[0];
        path = args[0];
        patternFile = args[1];
        //resultString = args[2];

        File file = new File(path);

        if (args.length != 2) {
            System.exit(1);
        }

        for (File f : file.listFiles()) {
            Callable<Void> task = new FileLoader(f, patternFile);
            pool.submit(task);
        }
    }
}