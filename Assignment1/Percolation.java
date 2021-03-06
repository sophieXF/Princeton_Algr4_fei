import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
  private int[][] open_status;
  private WeightedQuickUnionUF WUF;
  private int Num;
  
  public Percolation(int N) //create NN grid all block
  {
    if (N <= 0)
    {
      throw new java.lang.IllegalArgumentException("index out of bounds"); 
  } 
  Num = N;
  WUF = new WeightedQuickUnionUF(Num*Num + 1);
  open_status = new int[Num][Num];
    // blah
  for (int i = 0; i < Num; i++)
  {
      for(int j = 0; j < Num; j++)
      {
        open_status[i][j] = 1;
    }
}

int virtual_top = Num*Num;
for (int j=0; j<Num; j++)
{
  WUF.union(j, virtual_top);
}   


}
public void open (int i, int j) // open ij if not open already, and connect to neighbor 
{
    if (i <= 0 || i > Num || j <= 0 || j > Num)
    {
      throw new java.lang.IndexOutOfBoundsException("index out of bounds"); 
  }
  i = i-1;
  j = j-1;
  if(open_status[i][j] == 1) {
      open_status[i][j] = 0;
      if (i != 0 && open_status[i-1][j] == 0) WUF.union(i*Num + j, (i - 1)*Num+j);
      if (i != Num-1 && open_status[i+1][j] == 0) WUF.union(i*Num + j, (i + 1)*Num+j);
      if (j != 0 && open_status[i][j-1] == 0) WUF.union(i*Num + j, i*Num + j- 1);
      if (j != Num-1 && open_status[i][j+1] == 0) WUF.union(i*Num + j, i*Num + j + 1);     
  }
}

public boolean isOpen(int i, int j) //is ij open
{
    if (i <= 0 || i > Num || j <= 0 || j > Num)
    {
      throw new java.lang.IndexOutOfBoundsException("index out of bounds"); 
  }
  i=i-1;
  j=j-1;
  return open_status[i][j]==0;
}
public boolean isFull(int i, int j)//is full 
{
    if (i<=0 || i>Num || j<=0 || j>Num)
    {
      throw new java.lang.IndexOutOfBoundsException("index out of bounds"); 
  }
  i=i-1;
  j=j-1;
  if (i == 0) {
      return open_status[i][j] == 0;
  }
  else {
      return WUF.connected(i*Num+j, Num*Num);
  }
}
public boolean percolates()
{
    if (Num == 1)
    {
      return open_status[0][0]==0;
  }
  else {
        //union the top row to virtual top site   
      
      boolean botconnect = false; 
      int j = 0;
      while (j < Num && botconnect == false)
      {
        botconnect = WUF.connected((Num-1)*Num+j, Num*Num);
        j++;
    }
    return botconnect;
}
}
public static void main(String[] args)
{
    Percolation perco_test;
    perco_test =new Percolation(5);
    StdOut.println("percolates:"+perco_test.percolates());
    perco_test.open(1, 0);
    //  perco_test.open(1,0);
    
    StdOut.println("open:"+perco_test.isOpen(0,0));
    StdOut.println("open:"+perco_test.isOpen(1,0));
    StdOut.println("open:"+perco_test.isOpen(2,0));
    StdOut.println("open:"+perco_test.isOpen(0,1));
    StdOut.println("open:"+perco_test.isOpen(0,2));
    StdOut.println("open:"+perco_test.isOpen(1,1));
    perco_test.open(2,0);
    StdOut.println("percolates:"+perco_test.percolates());
}

}
