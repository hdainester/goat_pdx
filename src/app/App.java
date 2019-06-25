package app;

import java.util.Random;
import java.util.Scanner;

public class App {
    private static final Random RNG = new Random();

    public static void main(String[] args) throws Exception {
        int simCount = -1;
        boolean isSim = false;

        if(args.length > 0) try {
            simCount = Integer.parseInt(args[0]);
            isSim = true;
        } catch(NumberFormatException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        Gates gates = new Gates();

        int games = 0;
        int wins = 0;

        while(running) {
            gates.shuffle();
            System.out.println(gates);

            readGate(gates, sc, isSim);
            gates.reveal();
            System.out.println(gates);
            readGate(gates, sc, isSim);

            boolean win = gates.open();
            System.out.println(gates);
            
            if(win) {
                System.out.println("Congratulations! You won an 'O'.");
                ++wins;
            } else System.out.println("You lost. Get your math right!");
            ++games;

            if(!isSim) {
                String answer = sc.nextLine().toLowerCase();
                while(!answer.equals("y") && !answer.equals("n")) {
                    System.out.print("Another game (y/n)? ");
                    answer = sc.nextLine().toLowerCase();
                }

                running = answer.equals("y");
            } else running = (--simCount) > 0;
        }

        System.out.printf("%d of %d %s won (%.2f%%).\n",
            wins, games, games > 1 ? "games were" : "game was", 100f*wins/games);
    }

    private static void readGate(Gates gates, Scanner sc, boolean isSim) {
        while(true) try {
            System.out.print("Select a gate: ");
            int g = isSim ? RNG.nextInt(3) : sc.nextInt(); // not a very smart ai but whatever..
            gates.select(g);
            System.out.println("Selected " + g);
            return;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            if(!isSim) sc.nextLine();
        }
    }
}