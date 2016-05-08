import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Test {
    public static void main(String[] args) {
        File file = new File("data.txt");
        Scanner console = new Scanner(file);
        int i = console.nextInt();
        System.out.println(i);
        console.close();
        // try {

        //     Scanner console = new Scanner(file);
        //     int i = console.nextInt();
        //     System.out.println(i);
        //     console.close();
        //     // Scanner sc = new Scanner(file);

        //     // while (sc.hasNextLine()) {
        //     //     int i = sc.nextInt();
        //     //     System.out.println(i);
        //     // }
        //     // sc.close();
        // }
        // catch (FileNotFoundException e) {
        //     e.printStackTrace();
        // }


    }
}