import java.util.*;
import java.util.Scanner;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//1 Reversing an array
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n =sc.nextInt();
        int[] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        int left=0;
        int right=n-1;
        while(left<right){
            int temp=arr[left];
            arr[left]=arr[right];
            arr[right]=temp;
            left++;
            right--;
        }
        for(int i=0;i<n;i++){
            System.out.print(arr[i]+" ");
        }


//2 max char & fav char given(prev zoho ques)
import java.util.*;


        // ---- Input ----
        int maxCharacters = 10;
        char favoriteCharacter = 'o';
        String inputWords = "Zoho,Eating,Watching,Pogo,Loving,Mango";

        // Split words
        String[] words = inputWords.split(",");

        List<String> lines = new ArrayList<>();
        List<Integer> counts = new ArrayList<>();

        String currentLine = "";
        int currentCount = 0;

        for (String word : words) {
            // Get the actual count of this word ignoring favorite character
            int wordCount = getWordCount(word, favoriteCharacter);

            // Check if this word can be added
            if (currentCount + wordCount <= maxCharacters) {
                // Add to current line
                if (!currentLine.isEmpty()) {
                    currentLine += " ";
                }
                currentLine += word;
                currentCount += wordCount;
            } else {
                // Push the existing line and reset
                lines.add(currentLine);
                counts.add(currentCount);

                // Start a new line
                currentLine = word;
                currentCount = wordCount;
            }
        }

        // Push the final line
        if (!currentLine.isEmpty()) {
            lines.add(currentLine);
            counts.add(currentCount);
        }

        // ---- Output ----
        for (int i = 0; i < lines.size(); i++) {
            System.out.println(lines.get(i) + " (" + counts.get(i) + ")");
        }
    }

    // Method to count word characters excluding favorite character
    private static int getWordCount(String word, char favorite) {
        int count = 0;
        for (char c : word.toCharArray()) {
            if (Character.toLowerCase(c) != Character.toLowerCase(favorite)) {
                count++;
            }
        }
        return count;


//3 Largest & Second Largest

        // Read array
        System.out.println("Enter the number of elements:");
        int n = sc.nextInt();
        int[] arr = new int[n];
        System.out.println("Enter the elements:");
        for(int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // Initialize
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        // Find largest and second largest
        for(int num : arr) {
            if(num > largest) {
                secondLargest = largest;
                largest = num;
            } else if(num > secondLargest && num != largest) {
                secondLargest = num;
            }
        }

        // Output results
        if(secondLargest == Integer.MIN_VALUE) {
            System.out.println("There is no distinct second largest element.");
        } else {
            System.out.println("Largest: " + largest);
            System.out.println("Second Largest: " + secondLargest);
        }




    / /4 Left rotate by k positions
        public static void leftRotate(int[] arr, int k) {
            int n = arr.length;
            k = k % n;
            reverse(arr, 0, k-1);
            reverse(arr, k, n-1);
            reverse(arr, 0, n-1);
        }

        private static void reverse(int[] arr, int left, int right) {
            while (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }


//5 Check Duplicate in Array
    public static boolean hasDuplicate(int[] arr){
            HashSet<Integer> set = new HashSet<>();
            for (int num : arr) {
                if (!set.add(num)) {
                    return true; // Duplicate found
                }
            }
            return false;
        }


//move zeroes to end of array
        public static void moveZeros(int[] arr) {
            int index = 0;
            for (int num : arr) {
                if (num != 0) {
                    arr[index++] = num;
                }
            }
            while (index < arr.length) {
                arr[index++] = 0;
            }
        }

// Usage example
// int[] arr = {0, 1, 0, 3, 12};
// moveZeros(arr);
// Result: [1, 3, 12, 0, 0]


//6