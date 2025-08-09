// HouseRentalApp.java

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

class Tenant extends User {
    public Tenant(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Tenant";
    }
}

class Owner extends User {
    List<Property> myProperties = new ArrayList<>();

    public Owner(String username, String password) {
        super(username, password);
    }

    public void addProperty(Property p) {
        myProperties.add(p);
    }

    @Override
    public String getRole() {
        return "Owner";
    }
}

class Property {
    String id;
    String location;
    double rent;
    boolean isRented;

    public Property(String id, String location, double rent) {
        this.id = id;
        this.location = location;
        this.rent = rent;
        this.isRented = false;
    }
}

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Map<String, User> userMap = new HashMap<>();
    static List<Property> propertyList = new ArrayList<>();

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
        System.out.print("Are you a Tenant or Owner? ");
        String role = sc.nextLine();

        if (role.equalsIgnoreCase("Tenant")) {
            userMap.put(username, new Tenant(username, password));
            System.out.println("Tenant registered successfully.");
        } else if (role.equalsIgnoreCase("Owner")) {
            userMap.put(username, new Owner(username, password));
            System.out.println("Owner registered successfully.");
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

        if (u instanceof Tenant) tenantMenu((Tenant) u);
        else if (u instanceof Owner) ownerMenu((Owner) u);
    }

    static void tenantMenu(Tenant t) {
        while (true) {
            System.out.println("\n1. View Properties 2. Rent Property 3. Logout");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                for (Property p : propertyList) {
                    if (!p.isRented)
                        System.out.println(p.id + " - " + p.location + " - Rs." + p.rent);
                }
            } else if (choice == 2) {
                System.out.print("Enter property ID to rent: ");
                String id = sc.nextLine();
                for (Property p : propertyList) {
                    if (p.id.equals(id) && !p.isRented) {
                        p.isRented = true;
                        System.out.println("Property rented successfully.");
                        return;
                    }
                }
                System.out.println("Property not available.");
            } else return;
        }
    }

    static void ownerMenu(Owner o) {
        while (true) {
            System.out.println("\n1. Add Property 2. View My Properties 3. Logout");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                System.out.print("Enter property ID: ");
                String id = sc.nextLine();
                System.out.print("Enter location: ");
                String loc = sc.nextLine();
                System.out.print("Enter rent amount: ");
                double rent = sc.nextDouble();
                sc.nextLine();
                Property p = new Property(id, loc, rent);
                o.addProperty(p);
                propertyList.add(p);
                System.out.println("Property added successfully.");
            } else if (choice == 2) {
                for (Property p : o.myProperties) {
                    System.out.println(p.id + " - " + p.location + " - Rs." + p.rent + " - " + (p.isRented ? "Rented" : "Available"));
                }
            } else return;
        }
    }
}
