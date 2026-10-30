
package com.cavazos;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ArrayList<String> commands = new ArrayList<>();
        Random random = new Random();

        // Load commands from the JSON file
        try (InputStream input = App.class.getClassLoader()
                .getResourceAsStream("commands.json")) {

            if (input == null) {
                System.out.println("Error: commands.json not found.");
                return;
            }

            String json = new String(input.readAllBytes(),
                    StandardCharsets.UTF_8);

            // Read the command strings from the JSON array
            String[] items = json.substring(
                    json.indexOf('[') + 1,
                    json.lastIndexOf(']')).split(",");

            for (String item : items) {
                commands.add(item.trim().replace("\"", ""));
            }

        } catch (IOException e) {
            System.out.println("Error reading commands: " + e.getMessage());
            return;
        }

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
                    int index = random.nextInt(commands.size());
                    String command = commands.get(index);
                    System.out.println("Command issued: " + command);
                    break;

                case "l":
                    System.out.println("\nAll Military Commands:");
                    for (int i = 0; i < commands.size(); i++) {
                        System.out.println((i + 1) + ". " + commands.get(i));
                    }
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
