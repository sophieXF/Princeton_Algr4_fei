import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinarySearchST;
import java.lang.NullPointerException;
import java.lang.String;
import java.util.ArrayList;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {
    private BinarySearchST<String, ArrayList<Integer>> nounTable;
    private BinarySearchST<Integer, String> idTable;
    private Digraph hyperDigraph; 
    private int numSynsets; 
    private int minlength;
    private SAP hyperSAP;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null)
            throw new java.lang.NullPointerException();
        if (hypernyms == null)
            throw new java.lang.NullPointerException();
        In input = new In(synsets);
        nounTable = new BinarySearchST<String, ArrayList<Integer>>();
        idTable = new BinarySearchST<Integer, String>();
        String stringInput; 
        numSynsets = 0;
        while (input.hasNextLine())
        {
            numSynsets++;
            stringInput = input.readLine();
            String[] parts = stringInput.split(",");
            idTable.put(Integer.parseInt(parts[0]), parts[1]);
            String[] nounParts = parts[1].split(" ");
            for (int i = 0; i < nounParts.length; i++)
            {
                ArrayList<Integer> tempID = new ArrayList<Integer>();
                if (!nounTable.isEmpty() && nounTable.contains(nounParts[i]))
                {   
                    tempID = nounTable.get(nounParts[i]);
                    tempID.add(Integer.parseInt(parts[0]));
                } else {
                    tempID.add(Integer.parseInt(parts[0])); 
                }
                nounTable.put(nounParts[i], tempID);
            }
        }
        // numSynsets = nounTable.size();
        hyperDigraph = new Digraph(numSynsets);
        input = new In(hypernyms);

        while (input.hasNextLine())
        {
            stringInput = input.readLine();
            String[] parts = stringInput.split(",");
            for (int i = 1; i < parts.length; i++)
            {
                hyperDigraph.addEdge(Integer.parseInt(parts[0]), Integer.parseInt(parts[i]));
            }
        }

        int isRooted = 0; 
        int i = 0;
        while (i < numSynsets && isRooted < 2)
        {
            if (hyperDigraph.outdegree(i) == 0 && hyperDigraph.indegree(i) != 0)
                isRooted++;
            i++;
        } 
        if (isRooted == 2) 
            throw new java.lang.IllegalArgumentException();
        DirectedCycle iscycle = new DirectedCycle(hyperDigraph);
        if (iscycle.hasCycle())
            throw new java.lang.IllegalArgumentException();
         hyperSAP = new SAP(hyperDigraph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounTable.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new java.lang.NullPointerException();
        return nounTable.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null)
            throw new java.lang.NullPointerException();
        if (nounB == null)
            throw new java.lang.NullPointerException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();
            // StdOut.println("stepD1");
        sap(nounA, nounB);
        return minlength;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null)
            throw new java.lang.NullPointerException();
        if (nounB == null)
            throw new java.lang.NullPointerException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new java.lang.IllegalArgumentException();
        ArrayList<Integer> nounAID;
        ArrayList<Integer> nounBID; 
        nounAID = nounTable.get(nounA);
        nounBID = nounTable.get(nounB);
          //  StdOut.println("nounAID: "+ nounAID);
            // StdOut.println("nounBID: "+ nounBID);
        
        minlength = hyperSAP.length(nounAID, nounBID);
            // StdOut.println("minlength: "+ minlength);

        int commonAncestorID = hyperSAP.ancestor(nounAID, nounBID);
        return idTable.get(commonAncestorID);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        
    }
}