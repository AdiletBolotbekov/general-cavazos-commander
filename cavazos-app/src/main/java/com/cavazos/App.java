
package com.cavazos;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== General Cavazos Commander ===");
            System.out.println("i - Issue a random command");
            System.out.println("l - List all commands");
            System.out.println("u - Undo last command");
            System.out.println("r - Redo last command");
            System.out.println("q - Quit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "i":
                    System.out.println("Issue command coming soon.");
                    break;
                case "l":
                    System.out.println("List commands coming soon.");
                    break;
                case "u":
                    System.out.println("Undo coming soon.");
                    break;
                case "r":
                    System.out.println("Redo coming soon.");
                    break;
                case "q":
                    running = false;
                    System.out.println("Goodbye, General!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}










