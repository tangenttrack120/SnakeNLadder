import java.util.Scanner;
import java.util.Random;

// The new Player class to track individual stats
class Player {
    private String name; // name and position can only be accessed in this class
    private int position;

    public Player(String name) {
        this.name = name;
        this.position = 1; // Everyone starts at position 1
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

public class Main {

    // Made static so it can be called from public static void main
    public static int DiceRoller(String dice) {
        Random randomGenerator = new Random();
        int rollResult;

        // Switched to .equals() for string value comparison
        if (dice.equals("1")) {
            rollResult = randomGenerator.nextInt(6) + 1;
        } else if (dice.equals("2")) {
            rollResult = randomGenerator.nextInt(12) + 1;
        } else {
            rollResult = 0;
        }
        return rollResult;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to SnakeNLadders!");
        System.out.println("This game has 100 tiles that you have to climb.");
        System.out.println("You will roll a dice to climb to the 100th tile and finish.");
        System.out.println("If you get on to a ladder you will get boosted to an upper tile.");
        System.out.println("The snake is the opposite, you will be kicked to a lower tile if you land on it.");
        System.out.println("If your roll result is more than what you need to finish, you will back up according to the excess result.\n");

        int playerCount = 0;
        while (playerCount < 1 || playerCount > 4) {
            System.out.print("Are you ready? Specify how many players will play (Max. 4): ");
            try {
                playerCount = Integer.parseInt(scanner.nextLine());
                if (playerCount < 1 || playerCount > 4) {
                    System.out.println("Please enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        System.out.println("You will be playing with " + playerCount + " players.\n");

        // Initialize the array of Player objects
        Player[] players = new Player[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player("Player " + (i + 1));
        }

        System.out.print("How many dice will you use (Max. 2): ");
        String dice = scanner.nextLine();
        System.out.println("You will be playing with " + dice + " dice.\n");

        // checking a player's starting position
        System.out.println("Game Setup:");
        for (Player p : players) {
            System.out.println(p.getName() + " is currently at tile " + p.getPosition());
        }

        System.out.println("\nLet the game begin!");

        boolean gameWon = false;
        Player winner = null;

        // The main game loop
        while (!gameWon) {
            for (Player p : players) {
                System.out.println("\nStatus: ");
                System.out.println("It is " + p.getName() + "'s turn!");
                System.out.println("Current position: Tile " + p.getPosition());

                // "Press any key" equivalent
                System.out.print("Press Enter to roll the dice...");
                scanner.nextLine();

                int roll = DiceRoller(dice);
                System.out.println(p.getName() + " rolled a " + roll + "!");

                // Calculate the new position
                int newPosition = p.getPosition() + roll;

                // The bounce-back mechanic for overshooting 100
                if (newPosition > 100) {
                    int excess = newPosition - 100;
                    newPosition = 100 - excess;
                    System.out.println("Whoops! You overshot tile 100 by " + excess + ".");
                    System.out.println("Bouncing back to tile " + newPosition + ".");
                } else {
                    System.out.println("Moving to tile " + newPosition + ".");
                }

                p.setPosition(newPosition);

                // Check for the win condition
                if (p.getPosition() == 100) {
                    gameWon = true;
                    winner = p;
                    break; // Break out of the player turn loop because the game is over
                }

                // Pause before the next player's turn
                System.out.print("Press Enter to pass the turn to the next player...");
                scanner.nextLine();
            }
        }

        System.out.println("\n🎉 Congratulations! " + winner.getName() + " has reached exactly 100 and won the game! 🎉");

        scanner.close();
    }
}