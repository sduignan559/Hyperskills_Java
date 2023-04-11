package animals;

import java.time.LocalTime;
import java.util.List;

public class Communication {

    public static void printGreeting() {
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isAfter(LocalTime.parse("04:59:59")) && currentTime.isBefore(LocalTime.parse("12:00:00"))) {
            System.out.println("Good morning!");
        } else if (currentTime.isAfter(LocalTime.parse("11:59:59")) && currentTime.isBefore(LocalTime.parse("18:00:00"))) {
            System.out.println("Good afternoon!");
        } else if (currentTime.isAfter(LocalTime.parse("17:59:59")) && currentTime.isBefore(LocalTime.parse("23:00:00"))) {
            System.out.println("Good evening!");
        } else if (currentTime.isAfter(LocalTime.parse("22:59:59")) && currentTime.isBefore(LocalTime.parse("05:00:00"))) {
            System.out.println("Hi, Night Owl!");
        }
    }

    public static List<String> positiveInput = List.of("y", "yes", "yes.", "yeah", "yep", "sure", "right", "affirmative",
            "correct", "indeed", "you bet", "exactly", "you said it");

    public static List<String> negativeInput = List.of("n", "no", "no way", "nah", "nope", "negative",
            "i don't think so", "i dont think so", "yeah no");

    public static List<String> clarification = List.of("I'm not sure I caught you: was it yes or no?",
            "Funny, I still don't understand, is it yes or no?",
            "Oh, it's too complicated for me: just tell me yes or no.",
            "Could you please simply say yes or no?",
            "Oh, no, don't try to confuse me: say yes or no.");

    public static List<String> goodbye = List.of("Bye!", "Have a nice day!", "See you soon!");

    public static String getArticle(String animal) {
        if (animal.contains(" ")) {
            return animal.split(" ")[0];
        } else {
            if (animal.charAt(0) == 'a' || animal.charAt(0) == 'e' || animal.charAt(0) == 'i'
                    || animal.charAt(0) == 'o' || animal.charAt(0) == 'u')
                return "an";
            else {
                return "a";
            }
        }
    }

    public static String getAnimal(String animal) {

        if (animal.contains(" ")) {
            return animal.split(" ")[1];
        } else {
            return animal;
        }
    }

    public static String convertStatment(String gameQuestion) {
        if (gameQuestion.startsWith("it can ")) {
            return "- Can it " + gameQuestion.replaceFirst("it can ", "") + "?";
        } else if (gameQuestion.startsWith("it has ")) {
            return "- Does it have " + gameQuestion.replaceFirst("it has ", "") + "?";
        } else if (gameQuestion.startsWith("it is ")) {
            return "- Is it " + gameQuestion.replaceFirst("it is ", "") + "?";
        }
        return gameQuestion;
    }

    public static String convertQuestion(String gameStatment, boolean response) {
        gameStatment = gameStatment.replaceFirst("\\?", ".");

        if (gameStatment.contains("Can it ") && response == true) {
            return gameStatment.replaceFirst("Can it ", "It can ");
        } else if (gameStatment.contains("Can it ") && response == false) {
            return gameStatment.replaceFirst("Can it ", "It can't ");
        } else if (gameStatment.contains("Is it ") && response == true) {
            return gameStatment.replaceFirst("Is it ", "It is ");
        } else if (gameStatment.contains("Is it ") && response == false) {
            return gameStatment.replaceFirst("Is it ", "It isn't ");
        } else if (gameStatment.contains("Does it ") && response == true) {
            return gameStatment.replaceFirst("Does it ", "It does ");
        } else if (gameStatment.contains("Does it ") && response == false) {
            return gameStatment.replaceFirst("Does it ", "It doesn't ");
        } else {
            return gameStatment;
        }
    }
}