import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class test {
	// public static double callnN (int n) {
	// 	if (n == 1) return 0;
	// 	return Math.log(n) + callnN(n - 1);
	// }
	public static int rank (int key, int[] a) {
		return rank(key, a, 0, a.length);
	}

	private static int rank (int key, int[] a, int lo, int hi) {
		StdOut.printf("%10d%10d\n", lo, hi);
		if (hi < lo) return -1; 
		int mid = (lo + hi) / 2; 
		if (key > mid) return rank(key, a, mid, hi); 
		else if (key < mid) return rank(key, a, lo, mid);
		else return mid; 
	}

	public static void main (String[] args) {
		Scanner input = new Scanner(System.in);
		// Scanner input = new Scanner(new File("data.txt"));
		int[] a = new int [10];
		int count = 0;
		while (input.hasNextInt() && input.nextInt() != 999) {
			a[count] = input.nextInt();
			count++;
		}
		// int[] a = {1,2,3,4,5,6,7,8};
		// int x = rank(3, a); 
		// StdOut.println("rank: " + x);
		// int count = 0;
		// int n = StdIn.readInt();
		// String[] names = new String[n]; 
		// int[] dataOne = new int[n];
		// int[] dataTwo = new int[n];
		// double[] dataThree = new double[n];
		// while (!StdIn.isEmpty())
		// {
		// 	names[count] = StdIn.readString();
		// 	dataOne[count] = StdIn.readInt();
		// 	dataTwo[count] = StdIn.readInt();
		// 	dataThree[count] = (double) dataOne[count]/dataTwo[count];
		// 	count++;
		// //StdOut.println(Arrays.toString(names));
		// //StdOut.println(Arrays.toString(dataOne));
		// //StdOut.println(Arrays.toString(dataTwo));

		// }
		// StdOut.println(Arrays.toString(names));
		// for (int i = 0; i < n; i++)
		// {
		// 	// StdOut.printf("%10s", names[i]);
		// 	// StdOut.printf("%10d", dataOne[i]); 
		// 	// StdOut.printf("%10d", dataTwo[i]);
		// 	// StdOut.printf("%10.5f\n", dataThree[i]);
		// 	StdOut.printf("%10s-  %-10d\n", names[i], dataOne[i]);
		// }
		// double x = callnN(2);
		// StdOut.println("x = "+ x);
		// int N = Integer.parseInt(args[0]);
		// int[] f = new int[N];
		// f[0] = 0; 
		// f[1] = 1;
		// for (int i = 0; i < N; i++)
		// {
		// 	if (i == 0) f[i] = 0; 
		// 	else if (i == 1) f[i] = 1;
		// 	else f[i] = f[i - 1] + f[i - 2];
		// }
		// StdOut.println(Arrays.toString(f));
	 	//StdOut.println(3 + 4 + "");
	 	//int a = Integer.parseInt(args[0]);
	 	//String s = Integer.toBinaryString(a);
	 	//StdOut.println("s = " + s);
	 	//String s = "";
	 	//for (int i = a; i > 0; i /= 2)
	 	//{
	 //		s = i % 2 + s;
	 //	}
	 	//StdOut.println("s = " + s);
	 	//StdOut.println("a = "+ a);
	 	//int x = StdIn.readInt();
	 	//int y = StdIn.readInt();
	 	//int z = StdIn.readInt();
	 	//if (x == y && y == z)
	 	//	StdOut.println("equal");
	 	//else 
	 	//	StdOut.println("not equal");
	}
}