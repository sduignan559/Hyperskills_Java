package animals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static animals.Main.root;

public class GuessingGame {
    static Scanner scanner = new Scanner(System.in);
    public static void guessAnimal() throws IOException {

        Node current = null;
        String animal1 = root.getValue();
        String article1 = Communication.getArticle(root.getValue());
        String firstFact = "";
        String firstQuestionResponse = "";
        boolean keepPlaying = true;

            //if file exista read the root node as the first questio

            System.out.println("Let's play a game!");
            System.out.println("Press enter when you're ready.");
            System.out.println("You think of an animal, and I guess it.");
            scanner.nextLine();

            System.out.println("Is it " + article1 + " " + animal1 + "?");
            String firstGuess;

            while (true) {
                firstGuess = scanner.nextLine().toLowerCase().strip();
                String firstGuessVerified = verifyYesNo(firstGuess);
                if (firstGuessVerified != null) break;
            }

            System.out.println("I give up. What animal do you have in mind?");
            String animal2 = scanner.nextLine().toLowerCase();
            String article2 = Communication.getArticle(animal2);
            animal2 = Communication.getAnimal(animal2);


            while (true) {
                System.out.println("Specify a fact that distinguishes " + article1 + " " + animal1 + " from " + article2 + " " + animal2 + ".");
                System.out.println("The sentence should satisfy one of the following templates:\n" +
                        "- It can ...\n" +
                        "- It has ...\n" +
                        "- It is a/an ...");

                if (!verifyQuestion(firstFact).equals("next")) {
                    firstFact = scanner.nextLine().toLowerCase();
                } else {
                    break;
                }
            }

            System.out.println("Is the statement correct for " + article2 + " " + animal2 + "?");

            while (true) {
                firstQuestionResponse = scanner.nextLine().toLowerCase();
                firstQuestionResponse = verifyYesNo(firstQuestionResponse);
                if (firstQuestionResponse != null) break;
            }

            if(firstQuestionResponse.equals("yes")) {
                root = getNode(animal2, animal1, firstFact, firstQuestionResponse);
            } else{
                root = getNode(animal1, animal2, firstFact, firstQuestionResponse);
            }

            System.out.println("\n" + "Would you like to play again?");
            String again;
            while (true) {
                again = verifyYesNo(scanner.nextLine().toLowerCase());
                if (again != null) break;
            }
            if (again.equals("no")) {
                keepPlaying = false;
                System.out.println("\n" + getRandomListElement(Communication.goodbye));
            }

        //oonce root node has been established begin the gueesing game
        while (keepPlaying == true) {
            current = root;
            System.out.println("You think of an animal, and I guess it.");
            System.out.println("press enter");
            scanner.nextLine();

            //transverse list
            while(true) {
                //if leaf node make guesss
                if (current.getRight() == null && current.getLeft() == null) {
                    System.out.println("Is it " +Communication.getArticle(current.getValue())+" " + current.getValue() +"?");
                    System.out.println("I win!1");

                    String animalGuess;
                    while (true) {
                        animalGuess = verifyYesNo(scanner.nextLine().toLowerCase());
                        if (animalGuess != null) break;
                    }

                    if (animalGuess.equals("yes")) {
                        System.out.println("I win!");
                        break;
                    }
                    if (animalGuess.equals("no")){
                        System.out.println("I give up. What animal do you have in mind?");
                        String animal3 = Communication.getAnimal(scanner.nextLine());

                        String article3 = Communication.getArticle(animal3);
                        animal3 = Communication.getAnimal(animal3);
                        String animal4 = Communication.getAnimal(current.getValue());
                        String article4 = Communication.getArticle(animal4);

                        System.out.println("Specify a fact that distinguishes " + article3 + " " + animal3 + " from " + article4 + " " + animal4 + ".");
                        System.out.println("The sentence should satisfy one of the following templates:\n" +
                                "- It can ...\n" +
                                "- It has ...\n" +
                                "- It is a/an ...");

                        String gameFact;
                        while (true) {
                            gameFact = scanner.nextLine().toLowerCase();
                            if (!verifyQuestion(gameFact).equals("next")) {
                            } else {
                                break;
                            }
                        }

                        System.out.println("Is the statement correct for " + article3 + " " + animal3 + "?");
                        String response2;
                        while (true) {
                            response2 = scanner.nextLine().toLowerCase();
                            response2 = verifyYesNo(response2);
                            if (response2 != null) break;
                        }
                        String gameQuestion = Communication.convertStatment(gameFact);                   //set nodes
                        if(response2.equals("yes")){
                            current.setValue(gameQuestion);
                            current.setLeft(new Node(animal4));
                            current.setRight(new Node(animal3));
                        } else{
                            current.setValue(gameQuestion);
                            current.setLeft(new Node(animal3));
                            current.setRight(new Node(animal4));
                        }
                        break;
                    }

                } else {
                    System.out.println(current.getValue());
                    String factAnswer;
                    while (true) {
                        factAnswer = scanner.nextLine().toLowerCase();
                        factAnswer = verifyYesNo(factAnswer);
                        if (factAnswer != null) break;
                    }

                    if(factAnswer.equals("yes")) {
                        current = current.getRight();
                    } else {
                        current = current.getLeft();
                    }
                }
            }
            System.out.println("would you like to play again");
            String responseww5 = scanner.nextLine().toLowerCase();

            if (responseww5.equals("no")) {
                keepPlaying = false;
            }
        }
    }


    public static Node getNode(String animal1, String animal2, String question, String respons) {
        System.out.println("I learned the following facts about animals:");

        String leftanimal = "";
        String rightAnimal = "";
        if (question.equals("yes")) {
            if (question.startsWith("it can ")) {
                leftanimal = animal1;
                rightAnimal = animal2;
                System.out.println("- The " + animal1 + " can't " + question.replaceFirst("it can ", "") + ".");
                System.out.println("- The " + animal2 + " can " + question.replaceFirst("it can ", "") + ".");
            } else if (question.startsWith("it has ")) {
                leftanimal = animal1;
                rightAnimal = animal2;
                System.out.println("- The " + animal1 + " doesn't have " + question.replaceFirst("it has ", "") + ".");
                System.out.println("- The " + animal2 + " has " + question.replaceFirst("it has ", "") + ".");
            } else if (question.startsWith("it is ")) {
                leftanimal = animal1;
                rightAnimal = animal2;
                System.out.println("- The " + animal1 + " isn't " + question.replaceFirst("it is ", "") + ".");
                System.out.println("- The " + animal2 + " is " + question.replaceFirst("it is ", "") + ".");
            }
        }
        if (respons.equals("no")) {
            if (question.startsWith("it can ")) {
                leftanimal = animal2;
                rightAnimal = animal1;
                System.out.println("- The " + animal2 + " can't " + question.replaceFirst("it can ", "") + ".");
                System.out.println("- The " + animal1 + " can " + question.replaceFirst("it can ", "") + ".");
            } else if (question.startsWith("it has ")) {
                leftanimal = animal2;
                rightAnimal = animal1;
                System.out.println("- The " + animal2 + " doesn't have " + question.replaceFirst("it has ", "") + ".");
                System.out.println("- The " + animal1 + " has " + question.replaceFirst("it has ", "") + ".");
            } else if (question.startsWith("it is ")) {
                leftanimal = animal2;
                rightAnimal = animal1;
                System.out.println("- The " + animal2 + " isn't " + question.replaceFirst("it is ", "") + ".");
                System.out.println("- The " + animal1 + " is " + question.replaceFirst("it is ", "") + ".");
            }
        }

        System.out.println("I can distinguish these animals by asking the question:");
        String animalQuestion = "";
        if (question.startsWith("it can ")) {
            animalQuestion = "- Can it " + question.replaceFirst("it can ", "") + "?";
            System.out.println(animalQuestion);
        } else if (question.startsWith("it has ")) {
            animalQuestion = "- Does it have " + question.replaceFirst("it has ", "") + "?";
            System.out.println(animalQuestion);
        } else if (question.startsWith("it is ")) {
            animalQuestion = "- Is it " + question.replaceFirst("it is ", "") + "?";
            System.out.println(animalQuestion);
        }

        Node node = new Node(animalQuestion);
        node.setLeft(new Node(leftanimal));
        node.setRight(new Node(rightAnimal));
        return node;
    }

    public static String verifyYesNo(String response) {
        Pattern pattern = Pattern.compile("[.!?]");
        Matcher matcher = pattern.matcher(response);
        response  = matcher.replaceFirst("");

        if (Communication.positiveInput.contains(response)) {
            response = "yes";
        } else if (Communication.negativeInput.contains(response)) {
            response = "no";
        } else {
            System.out.println("yes or no");
            response = null;
        }
        return response;
    }

    public static String  verifyQuestion(String question) {
        if (question.startsWith("it has") || question.startsWith("it can") || question.startsWith("it is")) {
            return "next";
        } else if (question.startsWith("has it ")) {
            return "examples of a statement\n" +
                    "- It can fly\n" +
                    " - It has " + question.replaceFirst("it has", "") + "\n" +
                    " - It is a mamal";
        } else if (question.startsWith("is is ")) {
            return "examples of a statement\n"+
                    "- It can fly\n" +
                    " - It has horn\n" +
                    " - It is a " + question.replaceFirst("is it", "") + "\n";
        } else if (question.startsWith("can it ")) {
            return "examples of a statement\n"+
                    "- It can " + question.replaceFirst("is it", "") + "\n" +
                    " - It has horn\n" +
                    " - It is a mamal\n";
        } else {
            return  "examples of a statement\n"+
                    "- It can\n" +
                    " - It has horn\n" +
                    " - It is a mamal\n";
        }
    }


    public static String getRandomListElement(List<String> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}

