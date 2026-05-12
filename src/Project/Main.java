package Project;

import java.util.Scanner;

public class Main {

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        CommandProcessor processor = new CommandProcessor();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.isBlank()) {
                clearConsole();
                if (processor.isJsonMode()) {
                    processor.printJsonHelp();
                } else {
                    processor.printCommands();
                }
                continue;
            }

            boolean running = processor.process(input);
            if (!running) {
                break;
            }
        }
    }
}