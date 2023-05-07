package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    private String[] shapes;
    private String userName;
    private int userScore;

    public void processGame() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Enter your name:");
        userName = scanner.nextLine();

        greetAndInitializeUser(userName);

        String shapesString = scanner.nextLine();
        initializeShapes(shapesString);
        System.out.println("Okay, let's start");

        String userChoice;
        String computerChoice;

        while (true) {
            userChoice = scanner.nextLine();
            if ("!exit".equals(userChoice)) {
                System.out.println("Bye!");
                break;
            } else if ("!rating".equals(userChoice)) {
                System.out.printf("Your rating: %d%n", userScore);
            } else if (!isInListOfShapes(userChoice)) {
                System.out.println("Invalid input");
            } else {
                computerChoice = shapes[random.nextInt(shapes.length)];
                findWinner(userChoice, computerChoice);
            }
        }
    }

    private void findWinner(String userChoice, String computerChoice) {

        if (computerChoice.equals(userChoice)) {
            System.out.printf("There is a draw (%s)%n", computerChoice);
            this.userScore += 50;
        } else if (isComputerWins(userChoice, computerChoice)) {
            System.out.printf("Sorry, but the computer chose %s%n", computerChoice);
        } else {
            System.out.printf("Well done. The computer chose %s and failed%n", computerChoice);
            this.userScore += 100;
        }
    }
    private void greetAndInitializeUser(String userName) {
        System.out.printf("Hello, %s%n", userName);

        File file = new File("rating.txt");

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNext()) {
                if (userName.equals(fileScanner.next())) {
                    this.userScore = fileScanner.nextInt();
                    break;
                } else {
                    this.userScore = 0;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: rating.txt");
        }
    }

    private void initializeShapes(String shapesString) {

        if (shapesString.isEmpty()) {
            this.shapes = new String[]{"paper", "scissors", "rock"};
        } else {
            this.shapes = shapesString.split(",");
        }
    }

    private boolean isInListOfShapes(String userChoice) {
        boolean isInListOfShapes = false;
        for (int i = 0; i < shapes.length; i++) {
            if (shapes[i].equals(userChoice)) {
                isInListOfShapes = true;
                break;
            }
        }
        return isInListOfShapes;
    }

    private boolean isComputerWins(String userChoice, String computerChoice) {
        int index = 0;
        for (int i = 0; i < shapes.length; i++) {
            if (userChoice.equals(shapes[i])) {
                index = i;
                break;
            }
        }

        boolean isComputerWins = false;
        for (int i = 0; i < (shapes.length - 1) / 2; i++) {
            if (computerChoice.equals(shapes[(i + index + 1) % shapes.length])) {
                isComputerWins = true;
                break;
            }
        }
        return isComputerWins;
    }
}
