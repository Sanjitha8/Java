import java.util.Scanner;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
                Scanner sc = new Scanner(System.in);

                // 1️⃣ Read dimensions
                System.out.println("Enter number of rows:");
                int rows = sc.nextInt();
                System.out.println("Enter number of columns:");
                int cols = sc.nextInt();

                // 2️⃣ Read matrix
                int[][] matrix = new int[rows][cols];
                System.out.println("Enter the matrix elements:");
                for(int i = 0; i < rows; i++) {
                    for(int j = 0; j < cols; j++) {
                        matrix[i][j] = sc.nextInt();
                    }
                }
                sc.nextLine();

                // Input in the form "{4,0}"
                System.out.println("Enter starting position (e.g., {4,0}):");
                String input = sc.nextLine().trim();

                // 1️⃣ Remove braces
                input = input.replace("{", "").replace("}", "");
                // Now input = "4,0"

                // 2️⃣ Split by comma
                String[] parts = input.split(",");
                int startRow = Integer.parseInt(parts[0].trim());
                int startCol = Integer.parseInt(parts[1].trim());


                // Decide direction based on starting position
                boolean goingUp = (startRow == rows - 1);

                // Traverse columns starting from 'startCol' to end
                for (int col = startCol; col < cols; col++) {
                    if (goingUp) {
                        // Print from bottom to top
                        for (int row = rows - 1; row >= 0; row--) {
                            System.out.print(matrix[row][col] + " ");
                        }
                    } else {
                        // Print from top to bottom
                        for (int row = 0; row < rows; row++) {
                            System.out.print(matrix[row][col] + " ");
                        }
                    }
                    goingUp = !goingUp;
                }
            }
        }
