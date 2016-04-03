import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
  
  private double[] exp;
  private int Tnum;
  
  public PercolationStats (int N, int T) //T experiement 
  {
    if (N<=0 || T<=0)
      {
        throw new java.lang.IllegalArgumentException("out of bounds"); 
      }
    int count;
    int i;
    int j;
    Percolation perco;
    
    exp = new double[T];
    Tnum=T;
    
    for (int k=0; k<T; k++)
    {
      perco = new Percolation(N);
      count = 0;   
      while (!perco.percolates() && count<N*N)
      {
        i = StdRandom.uniform(N);
        j = StdRandom.uniform(N);

        if (!perco.isOpen(i+1,j+1)) 
        {
          perco.open(i+1, j+1);
          count++;
        }       
      }
      exp[k]=(double) count/N/N;
    }
  }
    public double mean()
    {
      return StdStats.mean(exp);
    }
    public double stddev()
    {
      return StdStats.stddev(exp);
    }
    public double confidenceLo()
    {
      return StdStats.mean(exp)-1.96*StdStats.stddev(exp)/Math.sqrt(Tnum);
    }
    public double confidenceHi()
    {
      return StdStats.mean(exp)+1.96*StdStats.stddev(exp)/Math.sqrt(Tnum);
    }
    
    public static void main(String[] args)
    { 
      Stopwatch stopw = new Stopwatch();
      PercolationStats percosta = new PercolationStats(2,10);
      StdOut.println("mean ="+ percosta.mean());
      StdOut.println("stddev ="+ percosta.stddev());
      StdOut.println("95% confidence interval ="+ percosta.confidenceLo()+","+percosta.confidenceHi());
      double time = stopw.elapsedTime();
      StdOut.println("elasped time ="+ time);
    
    }
    

}