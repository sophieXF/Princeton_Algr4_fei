import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Subset 
{
	public static void main(String[] args) 
	{
		RandomizedQueue<String> strTest = new RandomizedQueue<String>();

   		int k = Integer.parseInt(args[0]);

  		while (!StdIn.isEmpty()) {
  			strTest.enqueue(StdIn.readString());
   		}

   		for (int i = 0; i < k; i++)
   		{
   			StdOut.println(strTest.dequeue());
   		}
  	}
}