import java.util.Scanner;
import manager.LibraryManager;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryManager manager = new LibraryManager();

        while (true) {
            System.out.println("\n========= Library Management Menu =========");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. View All Books");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Delete Member");
            System.out.println("8. Exit");
            System.out.println("9. Search Book by Title");
            System.out.print("Enter your choice (1-8): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    manager.addBook(title, author, isbn, category);
                    break;

                case 2:
                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();
                    manager.registerMember(name);
                    break;

                case 3:
                    manager.viewAllBooks();
                    break;

                case 4:
                    System.out.print("Enter member ID: ");
                    int issueMemberId = scanner.nextInt();
                    System.out.print("Enter book ID: ");
                    int issueBookId = scanner.nextInt();
                    manager.issueBook(issueMemberId, issueBookId);
                    break;

                case 5:
                    System.out.print("Enter member ID: ");
                    int returnMemberId = scanner.nextInt();
                    System.out.print("Enter book ID: ");
                    int returnBookId = scanner.nextInt();
                    manager.returnBook(returnMemberId, returnBookId);
                    break;

                case 6:
                    System.out.print("Enter book ID to delete: ");
                    int delBookId = scanner.nextInt();
                    manager.deleteBook(delBookId);
                    break;

                case 7:
                    System.out.print("Enter member ID to delete: ");
                    int delMemberId = scanner.nextInt();
                    manager.deleteMember(delMemberId);
                    break;

                case 8:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    System.exit(0);

                case 9:
                    System.out.print("Enter book title or keyword: ");
                    String keyword = scanner.nextLine();
                    manager.searchBookByTitle(keyword);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
