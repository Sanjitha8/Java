//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;

// Abstract base class for any type of user (Trainer or RegularUser)
abstract class User {
    protected String username;
    protected String password;

    // Constructor to initialize username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Check password for login
    public boolean authenticate(String pwd) {
        return this.password.equals(pwd);
    }

    public String getUsername() {
        return username;
    }

    // Each subclass must define its role
    public abstract String getRole();
}

// Trainer class can create and assign workout plans to users
class Trainer extends User {
    private List<WorkoutPlan> createdPlans = new ArrayList<>();

    public Trainer(String username, String password) {
        super(username, password); // Call constructor of User
    }

    // Trainer assigns a workout plan to a user
    public void assignPlan(RegularUser user, WorkoutPlan plan) {
        user.receivePlan(plan);
        System.out.println("Assigned plan to " + user.getUsername());
    }

    // Add a new plan to their created plans list
    public void createPlan(WorkoutPlan plan) {
        createdPlans.add(plan);
        System.out.println("Workout plan created successfully.");
    }

    public String getRole() {
        return "Trainer";
    }
}

// Regular user who logs workouts and receives plans
class RegularUser extends User {
    private List<Activity> activities = new ArrayList<>();
    private List<WorkoutPlan> assignedPlans = new ArrayList<>();
    private List<Goal> goals = new ArrayList<>();

    public RegularUser(String username, String password) {
        super(username, password);
    }

    // Log a new activity
    public void logActivity(Activity a) {
        activities.add(a);
        System.out.println("Activity logged!");
        updateGoals(a);
    }

    // Add a new goal
    public void addGoal(Goal g) {
        goals.add(g);
        System.out.println("Goal added!");
    }

    // Show goal status
    public void showGoals() {
        if (goals.isEmpty()) {
            System.out.println("No goals set.");
        } else {
            for (Goal g : goals) {
                System.out.println(g);
            }
        }
    }

    // Display all assigned workout plans
    public void viewPlans() {
        if (assignedPlans.isEmpty()) {
            System.out.println("No plans assigned.");
        } else {
            for (WorkoutPlan p : assignedPlans) {
                System.out.println(p);
            }
        }
    }

    // Receive plan from trainer
    public void receivePlan(WorkoutPlan p) {
        assignedPlans.add(p);
    }

    // Update progress of all goals based on new activity
    private void updateGoals(Activity a) {
        for (Goal g : goals) {
            g.updateProgress(a);
        }
    }

    public String getRole() {
        return "User";
    }
}

// Represents an individual workout or exercise
class Activity {
    private String type;
    private double duration; // in minutes
    private double calories;

    public Activity(String type, double duration, double calories) {
        this.type = type;
        this.duration = duration;
        this.calories = calories;
    }

    public String getType() {
        return type;
    }

    public double getDuration() {
        return duration;
    }

    public double getCalories() {
        return calories;
    }

    public String toString() {
        return type + " - " + duration + " mins - " + calories + " kcal";
    }
}

// Represents a fitness goal (e.g., burn 1000 kcal)
class Goal {
    private String description;
    private String type; // "calories" or "duration"
    private double target;
    private double progress;

    public Goal(String description, String type, double target) {
        this.description = description;
        this.type = type;
        this.target = target;
        this.progress = 0;
    }

    // Update progress based on activity
    public void updateProgress(Activity a) {
        if (type.equalsIgnoreCase("calories")) {
            progress += a.getCalories();
        } else if (type.equalsIgnoreCase("duration")) {
            progress += a.getDuration();
        }
    }

    public String toString() {
        return description + " | Progress: " + progress + "/" + target + " " + type +
                " | " + (progress >= target ? "Achieved ✅" : "In Progress ⏳");
    }
}

// Represents a workout plan assigned by trainer
class WorkoutPlan {
    private String name;
    private String description;

    public WorkoutPlan(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String toString() {
        return "Workout Plan: " + name + " - " + description;
    }
}

// Main application class that handles menus and user interaction
public class FitnessApp {
    static Scanner sc = new Scanner(System.in);
    static Map<String, User> userMap = new HashMap<>();
    static User currentUser = null;

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Fitness Tracker ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Perform Action");
            System.out.println("4. Logout");
            System.out.println("5. Exit");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> performAction();
                case 4 -> currentUser = null;
                case 5 -> {
                    running = false;
                    System.out.println("Thank you! Exiting...");
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    // Register user (choose role)
    private static void register() {
        System.out.print("Username: ");
        String uname = sc.nextLine();
        if (userMap.containsKey(uname)) {
            System.out.println("Username already exists.");
            return;
        }

        System.out.print("Password: ");
        String pwd = sc.nextLine();
        System.out.print("Role (User/Trainer): ");
        String role = sc.nextLine();

        if (role.equalsIgnoreCase("User")) {
            userMap.put(uname, new RegularUser(uname, pwd));
        } else if (role.equalsIgnoreCase("Trainer")) {
            userMap.put(uname, new Trainer(uname, pwd));
        } else {
            System.out.println("Invalid role.");
            return;
        }

        System.out.println("Registration successful.");
    }

    // Login and authenticate
    private static void login() {
        System.out.print("Username: ");
        String uname = sc.nextLine();
        System.out.print("Password: ");
        String pwd = sc.nextLine();

        User user = userMap.get(uname);
        if (user != null && user.authenticate(pwd)) {
            currentUser = user;
            System.out.println("Welcome, " + uname + "!");
        } else {
            System.out.println("Login failed.");
        }
    }

    // After login, based on role show appropriate actions
    private static void performAction() {
        if (currentUser == null) {
            System.out.println("Login first.");
            return;
        }

        if (currentUser instanceof Trainer trainer) {
            System.out.println("\n--- Trainer Menu ---");
            System.out.println("1. Create Workout Plan");
            System.out.println("2. Assign Plan to User");
            int c = Integer.parseInt(sc.nextLine());

            switch (c) {
                case 1 -> {
                    System.out.print("Plan name: ");
                    String pname = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    WorkoutPlan plan = new WorkoutPlan(pname, desc);
                    trainer.createPlan(plan);
                }
                case 2 -> {
                    System.out.print("Enter username of user: ");
                    String uname = sc.nextLine();
                    User u = userMap.get(uname);
                    if (u instanceof RegularUser ru) {
                        System.out.print("Plan name: ");
                        String pname = sc.nextLine();
                        System.out.print("Description: ");
                        String desc = sc.nextLine();
                        WorkoutPlan plan = new WorkoutPlan(pname, desc);
                        trainer.assignPlan(ru, plan);
                    } else {
                        System.out.println("User not found or not a RegularUser.");
                    }
                }
            }

        } else if (currentUser instanceof RegularUser user) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Log Activity");
            System.out.println("2. Set Goal");
            System.out.println("3. View Goals");
            System.out.println("4. View Assigned Plans");
            int c = Integer.parseInt(sc.nextLine());

            switch (c) {
                case 1 -> {
                    System.out.print("Type of activity: ");
                    String type = sc.nextLine();
                    System.out.print("Duration (min): ");
                    double dur = Double.parseDouble(sc.nextLine());
                    System.out.print("Calories: ");
                    double cal = Double.parseDouble(sc.nextLine());
                    user.logActivity(new Activity(type, dur, cal));
                }
                case 2 -> {
                    System.out.print("Goal description: ");
                    String desc = sc.nextLine();
                    System.out.print("Type (calories/duration): ");
                    String type = sc.nextLine();
                    System.out.print("Target: ");
                    double target = Double.parseDouble(sc.nextLine());
                    user.addGoal(new Goal(desc, type, target));
                }
                case 3 -> user.showGoals();
                case 4 -> user.viewPlans();
            }
        }
    }
}
