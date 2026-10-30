
package com.cavazos;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class App {

    public static void main(String[] args) {

        ArrayList<String> commands = new ArrayList<>();
        Random random = new Random();

        // Stacks for undo and redo
        Stack<String> undoStack = new Stack<>();
        Stack<String> redoStack = new Stack<>();

        // Load military commands from JSON file
        try (InputStream input = App.class.getClassLoader()
                .getResourceAsStream("commands.json")) {

            if (input == null) {
                System.out.println("Error: commands.json not found.");
                return;
            }

            String json = new String(
                    input.readAllBytes(),
                    StandardCharsets.UTF_8);

            // Extract command strings from the JSON array
            int start = json.indexOf('[');
            int end = json.lastIndexOf(']');

            if (start == -1 || end <= start) {
                System.out.println("Invalid commands JSON file.");
                return;
            }

            String content = json.substring(start + 1, end);

            // Match quoted strings, including commas inside commands
            java.util.regex.Matcher matcher =
                    java.util.regex.Pattern.compile(
                            "\"((?:\\\\.|[^\"\\\\])*)\"")
                            .matcher(content);

            while (matcher.find()) {
                commands.add(matcher.group(1));
            }

            if (commands.isEmpty()) {
                System.out.println("No military commands found.");
                return;
            }

        } catch (IOException e) {
            System.out.println(
                    "Error reading commands: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Main application menu
        while (running) {

            System.out.println();
            System.out.println("================================");
            System.out.println("   GENERAL CAVAZOS COMMANDER");
            System.out.println("================================");
            System.out.println("i - Issue a random command");
            System.out.println("l - List all commands");
            System.out.println("u - Undo last command");
            System.out.println("r - Redo last command");
            System.out.println("q - Quit");
            System.out.println("================================");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine()
                    .trim().toLowerCase();

            switch (choice) {

                // Issue a random military command
                case "i":
                    int index = random.nextInt(commands.size());
                    String command = commands.get(index);

                    undoStack.push(command);
                    redoStack.clear();

                    System.out.println(
                            "Command issued: " + command);
                    break;

                // List all military commands
                case "l":
                    System.out.println();
                    System.out.println("All Military Commands:");

                    for (int i = 0; i < commands.size(); i++) {
                        System.out.println(
                                (i + 1) + ". " + commands.get(i));
                    }
                    break;

                // Undo the most recent command
                case "u":
                    if (undoStack.isEmpty()) {
                        System.out.println(
                                "No commands to undo.");
                    } else {
                        String undone = undoStack.pop();
                        redoStack.push(undone);

                        System.out.println(
                                "Command undone: " + undone);
                    }
                    break;

                // Redo the most recently undone command
                case "r":
                    if (redoStack.isEmpty()) {
                        System.out.println(
                                "No commands to redo.");
                    } else {
                        String redone = redoStack.pop();
                        undoStack.push(redone);

                        System.out.println(
                                "Command redone: " + redone);
                    }
                    break;

                // Quit the application
                case "q":
                    running = false;
                    System.out.println(
                            "Goodbye, General!");
                    break;

                // Handle invalid input
                default:
                    System.out.println(
                            "Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
