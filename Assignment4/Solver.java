
import java.util.Deque;
import java.util.ArrayDeque;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;

public class Solver {

	private Deque<Board> solutionBoard = new ArrayDeque<Board>();  
	private int numMoves; 
	private Board initialBoard; 
	private boolean solvableflag; 

    private class Node implements Comparable<Node>
    {
    	public Board board; 
    	public int move; 
    	public Node preNode; 

    	public Node (Board bd, int mv, Node pre)
    	{
    		board = bd; 
    		move = mv; 
    		preNode = pre;  
    	} 

    	public int compareTo (Node that)
    	{
    		int prthis;
    		int prthat;  
    		prthis = this.move + this.board.manhattan(); 
    		prthat = that.move + that.board.manhattan();
    		if (prthis < prthat) return -1; 
    		else if (prthis > prthat) return +1; 
    		else return 0; 
    	}
    }

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
    	boolean solvableflagTwin; 
    	initialBoard = initial;
    	MinPQ<Node> pqNode = new MinPQ<Node>();
    	MinPQ<Node> pqNodeTwin = new MinPQ<Node>();
    	Node minNode = new Node (initial, 0, null);
    	Node minNodeTwin = new Node (initialBoard.twin(), 0, null);
    	pqNode.insert(minNode);
    	pqNodeTwin.insert(minNodeTwin);	
    	solvableflag = minNode.board.isGoal();
    	solvableflagTwin = minNodeTwin.board.isGoal();
    	while (!solvableflag && !solvableflagTwin)
    	{
    		//StdOut.println("pqlength: " + pqNode.size());
    		minNode = pqNode.delMin(); 
    		solvableflag = minNode.board.isGoal();
    		
    		minNodeTwin = pqNodeTwin.delMin();
    		solvableflagTwin = minNodeTwin.board.isGoal();
    		numMoves = minNode.move; 

    		if (solvableflag == true || solvableflagTwin == true) { break;}
    		//StdOut.println("move: "+ numMoves); 
    		//StdOut.println("pqlength: " + pqNode.size());
    		StdOut.println("min:"+minNode.board);
  
    		for (Board tempBoard : minNode.board.neighbors())
    		{	
    			if (minNode.preNode == null)
    			{
    				Node tempNode = new Node (tempBoard, minNode.move + 1, minNode); 
    				pqNode.insert(tempNode);
    				StdOut.println("neighbors1: "+tempBoard);
    			}
    			else if (!tempBoard.equals(minNode.preNode.board))
    			{
    				Node tempNode = new Node (tempBoard, minNode.move + 1, minNode); 
    				pqNode.insert(tempNode);
    				StdOut.println("neighbors: "+tempBoard);
    			}
    		}
    		for (Board tempBoardTwin : minNodeTwin.board.neighbors())
    		{	
    			if (minNodeTwin.preNode == null)
    			{
    				Node tempNodeTwin = new Node (tempBoardTwin, minNodeTwin.move + 1, minNodeTwin); 
    				pqNodeTwin.insert(tempNodeTwin);
    			}
    			else if (!tempBoardTwin.equals(minNodeTwin.preNode.board))
    			{
    				Node tempNodeTwin = new Node (tempBoardTwin, minNodeTwin.move + 1, minNodeTwin); 
    				pqNodeTwin.insert(tempNodeTwin);
    			}
    		}
    	}
    	

    	while (minNode.preNode != null) 
    	{
    		solutionBoard.push(minNode.board); 
    		minNode = minNode.preNode; 
    	}
    	solutionBoard.push(minNode.board);
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
    	return solvableflag;

    }
    public int moves()                     // min number of moves to solve initial board; -1 if 
    {
    	if (!isSolvable()) return -1; 
    	else return numMoves; 
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
    	if (!isSolvable()) return null; 
    	else return solutionBoard; 
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
    	// create initial board from file
    	In in = new In(args[0]);
    	int N = in.readInt();
    	int[][] blocks = new int[N][N];
    	for (int i = 0; i < N; i++)
    	    for (int j = 0; j < N; j++)
    	        blocks[i][j] = in.readInt();
    	Board initial = new Board(blocks);

    	//StdOut.println(initial);
    	//boolean x = initial.isGoal();
    	//StdOut.println("initial isGoal: " + x);


    	// solve the puzzle
    	Solver solver = new Solver(initial);

    	//boolean y = solver.isSolvable();
    	//StdOut.println("isSolvable: " + y);

    	// print solution to standard output
    	if (!solver.isSolvable())
    	    StdOut.println("No solution possible");
    	else {
    	    StdOut.println("Minimum number of moves = " + solver.moves());
    	    for (Board board : solver.solution())
    	       StdOut.println(board);
    	}
    }
}