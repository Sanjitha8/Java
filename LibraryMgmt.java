// LibraryManagementApp.java

import java.util.*;

abstract class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public abstract String getRole();
}

class Member extends User {
    List<Book> borrowedBooks = new ArrayList<>();

    public Member(String username, String password) {
        super(username, password);
    }

    public void borrowBook(Book b) {
        borrowedBooks.add(b);
    }

    @Override
    public String getRole() {
        return "Member";
    }
}

class Librarian extends User {
    public Librarian(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Librarian";
    }
}

class Book {
    String id;
    String title;
    String author;
    boolean isBorrowed;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Map<String, User> userMap = new HashMap<>();
    static List<Book> bookList = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Register 2. Login 3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) register();
            else if (choice == 2) login();
            else break;
        }
    }

    static void register() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        if (userMap.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }
        System.out.print("Enter password: ");
        String password = sc.nextLine();
        System.out.print("Are you a Member or Librarian? ");
        String role = sc.nextLine();

        if (role.equalsIgnoreCase("Member")) {
            userMap.put(username, new Member(username, password));
            System.out.println("Member registered successfully.");
        } else if (role.equalsIgnoreCase("Librarian")) {
            userMap.put(username, new Librarian(username, password));
            System.out.println("Librarian registered successfully.");
        } else {
            System.out.println("Invalid role!");
        }
    }

    static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User u = userMap.get(username);
        if (u == null || !u.password.equals(password)) {
            System.out.println("Invalid login.");
            return;
        }

        if (u instanceof Member) memberMenu((Member) u);
        else if (u instanceof Librarian) librarianMenu((Librarian) u);
    }

    static void memberMenu(Member m) {
        while (true) {
            System.out.println("\n1. View Books 2. Borrow Book 3. View My Books 4. Logout");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                for (Book b : bookList) {
                    if (!b.isBorrowed)
                        System.out.println(b.id + " - " + b.title + " by " + b.author);
                }
            } else if (choice == 2) {
                System.out.print("Enter book ID to borrow: ");
                String id = sc.nextLine();
                for (Book b : bookList) {
                    if (b.id.equals(id) && !b.isBorrowed) {
                        b.isBorrowed = true;
                        m.borrowBook(b);
                        System.out.println("Book borrowed successfully.");
                        break;
                    }
                }
            } else if (choice == 3) {
                for (Book b : m.borrowedBooks) {
                    System.out.println(b.id + " - " + b.title);
                }
            } else return;
        }
    }

    static void librarianMenu(Librarian l) {
        while (true) {
            System.out.println("\n1. Add Book 2. View All Books 3. Logout");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                System.out.print("Enter book ID: ");
                String id = sc.nextLine();
                System.out.print("Enter title: ");
                String title = sc.nextLine();
                System.out.print("Enter author: ");
                String author = sc.nextLine();
                Book b = new Book(id, title, author);
                bookList.add(b);
                System.out.println("Book added successfully.");
            } else if (choice == 2) {
                for (Book b : bookList) {
                    System.out.println(b.id + " - " + b.title + " - " + (b.isBorrowed ? "Borrowed" : "Available"));
                }
            } else return;
        }
    }
}
