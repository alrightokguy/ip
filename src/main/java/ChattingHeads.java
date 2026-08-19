import java.util.Scanner;
import java.util.ArrayList;

public class ChattingHeads {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<String> texts = new ArrayList<>();

        System.out.println(
            "Hello! I'm Chatting Heads.\n" +
            "You may find yourself\n" +
            "Living in a shotgun shack\n" +
            "What can I do for you?"
        );
        lineBreak();
        String input = scan.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                for (int i = 0; i < texts.size(); i++) {
                    System.out.println(String.format("%d. %s", i+1, texts.get(i)));
                }
            } else {
                texts.add(input);
                System.out.println("added: " + input);
            }
            lineBreak();
            input = scan.nextLine();
        }
        System.out.println("Letting the days go bye");
    }

    public static void lineBreak() {
        System.out.println("------------------------------------------------------------");
    }
}