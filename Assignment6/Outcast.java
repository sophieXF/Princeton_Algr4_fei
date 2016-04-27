
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;



public class Outcast {
	private WordNet net;
    // constructor takes a WordNet object
   public Outcast(WordNet wordnet) {
   	net = wordnet;
   }
   // given an array of WordNet nouns, return an outcast
   public String outcast(String[] nouns) {
   	int[][] dis = new int[nouns.length][nouns.length];
   	for (int i = 0; i < nouns.length; i++)
   	{	
   		//StdOut.println("i: "+i);
   		for (int j = i; j < nouns.length; j++)
   		{
   			dis[i][j] = net.distance(nouns[i], nouns[j]);
   			dis[j][i] = dis[i][j];

   		}			
   	}
   	int maxDis = 0;
   	int maxID = 0;
   	int sum;
 
   	for (int i = 0; i < nouns.length; i++)
   	{
   		sum = 0;
   		for (int j = 0; j < nouns.length; j++)
   		{
   			sum = sum + dis[i][j];
   		} 
   		if(sum > maxDis)
   		{
   			maxDis = sum;
   			maxID = i;
   		}
   	}
   	return nouns[maxID];
   }
   // see test client below
   public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
 
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
        In in = new In(args[t]);
        String[] nouns = in.readAllStrings();
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
   }
}
