import java.util.Scanner; // for getting user input
import java.util.Random; // for dice randomizer

public class Main {
    public int DiceRoller(String dice) {
        // create instance of random class
        Random randomGenerator = new Random();
        int rollResult;

        // can be replaced by switch later
        if  (dice == "1") {
            rollResult = randomGenerator.nextInt(6) + 1;
        } else if (dice == "2") {
            rollResult = randomGenerator.nextInt(12) + 1;
        } else {
            rollResult = 0;
        }
        return rollResult;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to SnakeNLadders!");
        System.out.println("This game have 100 tiles that you have to climb.");
        System.out.println("You will roll dice to climb to get into 100th tile and finish");
        System.out.println("If you get on to ladder you will get boosted to an upper tile");
        System.out.println("The snake is the opposite, you will kicked to an lower tile if you get it");
        System.out.println("If you roll result is more than what you need to finish, you will need to back up according to the exces result\n");

        System.out.print("Are you ready? Specify how much player will play (Max.4): ");
        int player = Integer.parseInt(scanner.nextLine()); // use try catch for invalid integer and more than 4
        System.out.println("You will be playing with " + player + ".");

        System.out.println("How much dice will you use (Max.2): ");
        String dice = scanner.nextLine();
        System.out.println("You will be playing with " + dice + ".");
    }
}