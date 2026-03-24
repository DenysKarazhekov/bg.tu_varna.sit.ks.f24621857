package Project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CommandProcessor processor = new CommandProcessor();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (!processor.process(input)) {
                break;
            }
        }
    }
}