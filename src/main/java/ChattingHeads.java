import java.util.Scanner;

public class ChattingHeads {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println(
            "Hello! I'm Chatting Heads.\n" +
            "You may find yourself\n" +
            "Living in a shotgun shack\n" +
            "What can I do for you?"
        );
        lineBreak();
        String input = scan.nextLine();

        while (!input.equals("bye")) {
            System.out.println(input);
            lineBreak();
            input = scan.nextLine();
        }

        System.out.println("Letting the days go bye");
    }

    public static void lineBreak() {
        System.out.println("------------------------------------------------------------");
    }
}