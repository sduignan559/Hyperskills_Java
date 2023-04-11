
package animals;



import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static String fileName = "placeholder";
    public static Node root;

    public static void main(String[] args) throws IOException {

        root = null;
        String fileName = "placeholder";
        boolean fileLoaded = false;
        String animal1 = "";
        String article1 = "";
        File knowledgeBaseFile = new File(fileName);

        String fileType = Storage.getStorageType(args);
        root = Storage.readAnimalData(fileType);

        if(root == null) {
            System.out.println("I want to learn about animals.");
            System.out.println("Which animal do you like most?");
            animal1 = scanner.nextLine().toLowerCase();;
            animal1 = Communication.getAnimal(animal1);
            root = new Node(animal1);
        }

        while (true) {
            System.out.println();
            System.out.println("Welcome to the animal expert system");
            System.out.println();
            System.out.println("1. Play the guessing game");
            System.out.println("2. List of all animals");
            System.out.println("3. Search for an animal");
            System.out.println("4. Calculate statistics");
            System.out.println("5. Print the Knowledge Tree");
            System.out.println("0. Exit");

            int menuOption = scanner.nextInt();

            if (menuOption == 1) {
                GuessingGame.guessAnimal();
                Storage.writeAnimalData(fileType, root);
            } else if (menuOption == 2) {
                System.out.println("Here are the animals I know:");
                ArrayList <String> animalList = Tree.listAnimals(root);
                for(String leafNodes : animalList) System.out.println(leafNodes);
            } else if (menuOption == 3) {
                System.out.println("Enter the animal:");
                scanner.nextLine();
                String animalSearch = scanner.nextLine().toLowerCase();
                Tree.printPath(root, animalSearch);
            } else if (menuOption == 4) {
                Tree.statistics(root);
            } else if (menuOption == 5) {
                Tree.printTree(root);
            } else {
                System.out.println("Thank you and goodbye!");
                System.exit(0);
            }
        }
    }
}