import java.util.Scanner;
import java.util.Random;

// The Player class is to track individual stats
class Player {
    private String name;
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

    public static void displayBoard(Player[] players) {
        System.out.println("\n--- Current Board ---");

        // A standard Snakes and Ladders board is 10x10
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {

                // Calculate the base number for the current row (Row 0 is the 90s, Row 9 is the 0s)
                int base = (9 - row) * 10;
                int tileNumber;

                // Even rows (0, 2, 4, 6, 8) go right-to-left
                if (row % 2 == 0) {
                    tileNumber = base + 10 - col;
                }
                // Odd rows (1, 3, 5, 7, 9) go left-to-right
                else {
                    tileNumber = base + col + 1;
                }

                // Check if any player is on this tile
                String tileDisplay = String.format("%3d", tileNumber); // Default tile number formatted to 3 spaces
                for (int pIndex = 0; pIndex < players.length; pIndex++) {
                    if (players[pIndex].getPosition() == tileNumber) {
                        // If a player is here, replace the number with their marker (e.g., P1, P2)
                        tileDisplay = " P" + (pIndex + 1);
                        break; // Only show one player per tile to keep the grid aligned for now
                    }
                }

                // Print the tile with a border
                System.out.print("[" + tileDisplay + "]");
            }
            // Move to the next row
            System.out.println();
        }
        System.out.println("---------------------\n");
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

        System.out.println("\n--- Let the game begin! ---");

        boolean gameWon = false;
        Player winner = null;

        // Show the initial board before any rolls happen
        displayBoard(players);

        // The main game loop
        while (!gameWon) {
            for (Player p : players) {
                System.out.println("==================================");
                System.out.println("It is " + p.getName() + "'s turn!");

                System.out.print("Press Enter to roll the dice...");
                scanner.nextLine();

                int roll = DiceRoller(dice);
                System.out.println(p.getName() + " rolled a " + roll + "!");

                int newPosition = p.getPosition() + roll;

                // The bounce-back mechanic
                if (newPosition > 100) {
                    int excess = newPosition - 100;
                    newPosition = 100 - excess;
                    System.out.println("Whoops! You overshot tile 100 by " + excess + ".");
                    System.out.println("Bouncing back to tile " + newPosition + ".");
                } else {
                    System.out.println("Moving to tile " + newPosition + ".");
                }

                p.setPosition(newPosition);

                // --- DRAW THE UPDATED BOARD HERE ---
                displayBoard(players);

                if (p.getPosition() == 100) {
                    gameWon = true;
                    winner = p;
                    break;
                }

                System.out.print("Press Enter to pass the turn to the next player...");
                scanner.nextLine();
            }
        }

        System.out.println("\n🎉 Congratulations! " + winner.getName() + " has reached exactly 100 and won the game! 🎉");

        scanner.close();
    }
}