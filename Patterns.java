// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n=4;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(j==n-1-i){
                    System.out.print("* ");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}


