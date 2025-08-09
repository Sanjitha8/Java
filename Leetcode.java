import java.util.*;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] arr=new int[]{16,17,4,3,5,2};
        ArrayList<Integer> updated = new ArrayList<>();
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            if (i != n - 1) {
                int maxRight = Integer.MIN_VALUE;
                for (int j = i + 1; j < n; j++) {
                    maxRight = Math.max(maxRight, arr[j]);
                }
                updated.add(maxRight);
            } else {
                updated.add(-1);
            }
        }
        for(int num: updated){ System.out.print(num+" ");}
    }
}