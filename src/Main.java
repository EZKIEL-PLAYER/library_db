import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CrudOperation operation = new CrudOperation();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("====== Library Menu ======");
            System.out.println("1. Add Book");
            System.out.println("2. Find Book by Title");
            System.out.println("3. Update Book by ID");
            System.out.println("4. Delete Book by ID");
            System.out.println("5. List All Books");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        operation.createBooks();
                        break;
                    case "2":
                        operation.findBookByTitle();
                        break;
                    case "3":
                        operation.updateBookById();
                        break;
                    case "4":
                        operation.deleteBookById();
                        break;
                    case "5":
                        operation.listAllBooks();
                        break;
                    case "6":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please choose 1-6.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace(); // optional for debugging
            }
        }
    }
}
