import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.BinarySearchST;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Queue;
import java.lang.String;

public class SAP {
   private Digraph sapDigraph; 
   private int minLength; 
   private int commancestor;
   private int numV;
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
      if (G == null)
            throw new java.lang.NullPointerException();
      sapDigraph = new Digraph(G);
      numV = sapDigraph.V();
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
      if (v < 0 || v > numV - 1 || w < 0 || w > numV - 1)
         throw new java.lang.IndexOutOfBoundsException();
      if (v == w) return 0;
      else {
         searchAncestor(v, w);
         return minLength;
      }
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
      if (v < 0 || v > numV - 1 || w < 0 || w > numV - 1)
         throw new java.lang.IndexOutOfBoundsException();
      if (v == w) return v;
      else {
         searchAncestor(v,w);
         return commancestor;
      }
   }

   private void searchAncestor(int v, int w) {
      if (v < 0 || v > numV - 1 || w < 0 || w > numV - 1)
         throw new java.lang.IndexOutOfBoundsException();
      boolean[] vmarked = new boolean[numV];
      boolean[] wmarked = new boolean[numV];
      BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(sapDigraph, v);
      BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(sapDigraph, w);
      Queue<Integer> vq = new Queue<Integer>();
      Queue<Integer> wq = new Queue<Integer>();
      vq.enqueue(v); 
      wq.enqueue(w);
      vmarked[v] = true;
      wmarked[w] = true;
      int vDis = 0;
      int wDis = 0;
      int flag = 0; 
      minLength = 2*numV;
      while (!vq.isEmpty() && vDis < minLength && flag == 0)
      {
         int a = vq.dequeue();
         vDis = vBFS.distTo(a);
         if (wBFS.hasPathTo(a)) {
            int tempLength = wBFS.distTo(a) + vDis;
            if (tempLength < minLength) {
               minLength = tempLength;
               commancestor = a;
            }
         }
         for (int b : sapDigraph.adj(a))
         {
            if(!vmarked[b])
            {
               vq.enqueue(b);
               vmarked[b] = true;
            }
         }
         flag = 1; 
         while (!wq.isEmpty() && wDis < minLength && flag == 1)
         {
            a = wq.dequeue();
            wDis = wBFS.distTo(a);
            if (vBFS.hasPathTo(a)) {
               int tempLength = vBFS.distTo(a) + wDis;
               if (tempLength < minLength) {
                  minLength = tempLength;
                  commancestor = a;
               }
            }
            for (int b : sapDigraph.adj(a))
            {
               if(!wmarked[b])
               {
                  wq.enqueue(b);
                  wmarked[b] = true;
               }
            }
            flag = 1; 
         }
      } 
      if (minLength == 2*numV)
      {
         commancestor = -1;
         minLength = -1;
      }
   }

 private void searchAncestorMulti(Iterable<Integer> v, Iterable<Integer> w){
      boolean[] vmarked = new boolean[numV];
      boolean[] wmarked = new boolean[numV];
      BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(sapDigraph, v);
      BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(sapDigraph, w);
      Queue<Integer> vq = new Queue<Integer>();
      Queue<Integer> wq = new Queue<Integer>();
      for (int i : v)
      {
         vq.enqueue(i);
         vmarked[i] = true;
      }
      for (int j : w)
      {
         wq.enqueue(j);
         wmarked[j] = true;
      } 
      int vDis = 0;
      int wDis = 0;
      int flag = 0; 
      minLength = 2*numV;
      while (!vq.isEmpty() && vDis < minLength && flag == 0)
      {
         int a = vq.dequeue();
         vDis = vBFS.distTo(a);
         if (wBFS.hasPathTo(a)) {
            int tempLength = wBFS.distTo(a) + vDis;
            if (tempLength < minLength) {
               minLength = tempLength;
               commancestor = a;
            }
         }
         for (int b : sapDigraph.adj(a))
         {
            if(!vmarked[b])
            {
               vq.enqueue(b);
               vmarked[b] = true;
            }
         }
         flag = 1; 
         while (!wq.isEmpty() && wDis < minLength && flag == 1)
         {
            a = wq.dequeue();
            wDis = wBFS.distTo(a);
            if (vBFS.hasPathTo(a)) {
               int tempLength = vBFS.distTo(a) + wDis;
               if (tempLength < minLength) {
                  minLength = tempLength;
                  commancestor = a;
               }
            }
            for (int b : sapDigraph.adj(a))
            {
               if(!wmarked[b])
               {
                  wq.enqueue(b);
                  wmarked[b] = true;
               }
            }
            flag = 1; 
         }
      } 
      if (minLength == 2*numV)
      {
         commancestor = -1;
         minLength = -1;
      }
   }


   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
      for ( int i : v)
      {
         if (i < 0 || i > numV - 1)
            throw new java.lang.IndexOutOfBoundsException();
      }
      for ( int i : w)
      {
         if (i < 0 || i > numV - 1)
            throw new java.lang.IndexOutOfBoundsException();
      }
      searchAncestorMulti(v,w);
      return minLength;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
      searchAncestorMulti(v,w);
      return commancestor;
   }

   // do unit testing of this class
   public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
   }
}
