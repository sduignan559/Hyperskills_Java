package analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class FileLoader implements Callable<Void> {
     public FileLoader(File file, String patternFile) throws FileNotFoundException {


         File file2 = new File(patternFile);
         Scanner scanner2 = new Scanner(file2);

         while (scanner2.hasNext()) {
             String[] patternStrings = scanner2.nextLine().split(";");
             String patterns = patternStrings[1].replace("\"","").replace("\\ ","\\");
             String outputString = patternStrings[2].replace("\"","").replace("\\ ","\\");

             System.out.println(patterns);
             System.out.println(outputString);


             Scanner scanner = null;
             try {
                 scanner = new Scanner(file);
             } catch (FileNotFoundException e) {
                 System.out.println("Unknown file type");
                 System.exit(0);
             }
             String output = "";

             while (scanner.hasNext()) {
                 output = output + scanner.next();
             }
             scanner.close();

             System.out.println(output);
             // if (algorithm.equals("--KMP")) {

             if (kmp(output, patterns) == true) {
                 System.out.println(file.getName() + ": " + outputString);
             } else {
                 System.out.println(file.getName() + ": " + "Unknown file type");
             }
         }
         scanner2.close();
     }
    private static boolean naive(String patternString, String output) {
        {
            int l1 = patternString.length();
            int l2 = output.length();
            int i = 0, j = l2 - 1;

            for (i = 0, j = l2 - 1; j < l1; ) {
                if (output.equals(patternString.substring(i, j + 1))) {
                    return true;
                }
                i++;
                j++;
            }
        }
        return false;
    }

    public static boolean kmp(String text, String pattern) {
        int[] prefixFunction = computePrefixFunction(pattern);
        int i = 0, j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                if (j == pattern.length() - 1) {
                    return true;
                }
                i++;
                j++;
            } else if (j > 0) {
                j = prefixFunction[j - 1];
            } else {
                i++;
            }
        }
        return false;
    }

    private static int[] computePrefixFunction(String pattern) {
        int[] prefixFunction = new int[pattern.length()];
        int i = 1, j = 0;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(j)) {
                prefixFunction[i] = j + 1;
                i++;
                j++;
            } else if (j > 0) {
                j = prefixFunction[j - 1];
            } else {
                prefixFunction[i] = 0;
                i++;
            }
        }
        return prefixFunction;
    }


    @Override
    public Void call() throws Exception {
        return null;
    }
}
