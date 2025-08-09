import java.util.*;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
                // INPUT
                int[] arr = {35, 145, 67, 1004, 88, 456, 2034};
                int windowSize = 3;
                int favoriteNumber = 8;

                List<String> valid = new ArrayList<>();
                List<String> invalid = new ArrayList<>();

                // SLIDING WINDOW
                for (int i = 0; i <= arr.length - windowSize; i++) {
                    int[] window = Arrays.copyOfRange(arr, i, i + windowSize);
                    boolean containsFav = contains(window, favoriteNumber);
                    int derivedNumber = buildNumber(window);
                    String windowStr = Arrays.toString(window).replace("[", "{").replace("]", "}");

                    if (containsFav) {
                        valid.add(windowStr + " : " + derivedNumber);
                    } else {
                        invalid.add(windowStr + " : " + derivedNumber);
                    }
                }

                // OUTPUT
                System.out.println("\nValid Subarrays:");
                valid.forEach(System.out::println);
                System.out.println("\nInvalid Subarrays:");
                invalid.forEach(System.out::println);
            }

            // Check if any number contains the favorite digit
            private static boolean contains(int[] window, int favorite) {
                String fav = String.valueOf(favorite);
                for (int num : window) {
                    if (String.valueOf(num).contains(fav))
                        return true;
                }
                return false;
            }

            // Build the final number by extracting max digits position-wise
            private static int buildNumber(int[] window) {
                String[] strNums = new String[window.length];
                for (int i = 0; i < window.length; i++) {
                    strNums[i] = new StringBuilder(String.valueOf(window[i])).reverse().toString();
                }

                int maxLen = Arrays.stream(strNums).mapToInt(String::length).max().orElse(0);
                StringBuilder result = new StringBuilder();
                for (int pos = 0; pos < maxLen; pos++) {
                    int maxDigit = 0;
                    for (String s : strNums) {
                        if (pos < s.length()) {
                            maxDigit = Math.max(maxDigit, s.charAt(pos) - '0');
                        }
                    }
                    result.insert(0, maxDigit);
                }
                return Integer.parseInt(result.toString());
            }
        }
        