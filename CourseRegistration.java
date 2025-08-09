import java.util.*;

// Abstract base class for all user types
abstract class User {
    protected String username;
    protected String password;

    // Constructor to initialize common user data
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Abstract method each subclass must implement
    public abstract String getRole();

    // Authenticate provided password
    public boolean authenticate(String pwd) {
        return this.password.equals(pwd);
    }

    public String getUsername() {
        return username;
    }
}

// Student subclass
class Student extends User {
    // List of enrollments for this student
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(String username, String password) {
        super(username, password); // Calls User constructor
    }

    @Override
    public String getRole() {
        return "Student";
    }

    // Enroll in a course
    public void enroll(Course course) {
        Enrollment e = new Enrollment(UUID.randomUUID().toString(), this, course, new Date());
        enrollments.add(e);
        System.out.println("Enrolled in: " + course.getTitle());
    }


    // View my courses
    public void viewMyCourses() {
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments yet.");
        } else {
            System.out.println("My Courses:");
            for (Enrollment e : enrollments) {
                System.out.println("- " + e.getCourse().getTitle());
            }
        }
    }
}

// Instructor subclass
class Instructor extends User {
    private List<Course> myCourses = new ArrayList<>();

    public Instructor(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Instructor";
    }

    // Create a new course
    public Course createCourse(String title, String description) {
        Course c = new Course(UUID.randomUUID().toString(), title, description, this);
        myCourses.add(c);
        System.out.println("Course created: " + title);
        return c;
    }

    private static void createCourse(Instructor ins) {
        System.out.print("Course title: ");
        String title = Main.sc.nextLine();
        System.out.print("Description: ");
        String desc = Main.sc.nextLine();
        Course c = ins.createCourse(title, desc);
        Main.courses.add(c);
        System.out.println("Course created successfully!");
        System.out.println("Course ID: " + c.getId() + " - Title: " + c.getTitle());
    }

    // View courses I teach
    public void viewMyCourses() {
        if (myCourses.isEmpty()) {
            System.out.println("No courses created yet.");
        } else {
            System.out.println("My Courses:");
            for (Course c : myCourses) {
                System.out.println("- " + c.getTitle());
            }
        }
    }
}

// Course entity
class Course {
    private String id;
    private String title;
    private String description;
    private Instructor instructor;

    public Course(String id, String title, String description, Instructor instructor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.instructor = instructor;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Instructor getInstructor() { return instructor; }
}

// Enrollment entity linking students and courses
class Enrollment {
    private String id;
    private Student student;
    private Course course;
    private Date date;

    public Enrollment(String id, Student student, Course course, Date date) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.date = date;
    }

    public Course getCourse() { return course; }
}

// Main application class
public class Main {
    public static Scanner sc = new Scanner(System.in);
    private static Map<String, User> users = new HashMap<>();
    static List<Course> courses = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Register  2. Login  3. Exit");
            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> register();
                case "2" -> login();
                default -> {
                    System.out.println("Goodbye!");
                    return;
                }
            }
        }
    }

    private static void register() {
        System.out.print("Username: ");
        String uname = sc.nextLine();
        if (users.containsKey(uname)) {
            System.out.println("Username exists.");
            return;
        }
        System.out.print("Password: ");
        String pwd = sc.nextLine();
        System.out.print("Role (Student/Instructor): ");
        String role = sc.nextLine();

        if (role.equalsIgnoreCase("Student")) {
            users.put(uname, new Student(uname, pwd));
        } else if (role.equalsIgnoreCase("Instructor")) {
            users.put(uname, new Instructor(uname, pwd));
        } else {
            System.out.println("Invalid role.");
            return;
        }
        System.out.println("Registered as " + role + ".");
    }

    private static void login() {
        System.out.print("Username: ");
        String uname = sc.nextLine();
        System.out.print("Password: ");
        String pwd = sc.nextLine();

        User u = users.get(uname);
        if (u == null || !u.authenticate(pwd)) {
            System.out.println("Login failed.");
            return;
        }
        System.out.println("Welcome, " + u.getRole() + " " + uname + "!\n");

        if (u instanceof Student) studentMenu((Student) u);
        else if (u instanceof Instructor) instructorMenu((Instructor) u);
    }


    private static void studentMenu(Student s) {
        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. View All Courses  2. Enroll in Course  3. My Courses  4. Logout");
            String c = sc.nextLine();
            switch (c) {
                case "1" -> viewAllCourses();
                case "2" -> enrollCourse(s);
                case "3" -> s.viewMyCourses();
                default -> { return; }
            }
        }
    }

    private static void instructorMenu(Instructor ins) {
        while (true) {
            System.out.println("\n--- Instructor Menu ---");
            System.out.println("1. Create Course  2. My Courses  3. Logout");
            String c = sc.nextLine();
            switch (c) {
                case "1" -> createCourse(ins);
                case "2" -> ins.viewMyCourses();
                default -> { return; }
            }
        }
    }

    private static void viewAllCourses() {
        if (courses.isEmpty()) System.out.println("No courses available.");
        else {
            System.out.println("Available Courses:");
            for (Course c : courses) {
                System.out.println(c.getId() + " - " + c.getTitle() + " (by " + c.getInstructor().getUsername() + ")");
            }
        }
    }

    private static void enrollCourse(Student s) {
        System.out.print("Enter course ID to enroll: ");
        String id = sc.nextLine();
        for (Course c : courses) {
            if (c.getId().equals(id)) {
                s.enroll(c);
                return;
            }
        }
        System.out.println("Course not found.");
    }

    private static void createCourse(Instructor ins) {
        System.out.print("Course title: ");
        String title = sc.nextLine();
        System.out.print("Description: ");
        String desc = sc.nextLine();
        Course c = ins.createCourse(title, desc);
        courses.add(c);
    }
}
