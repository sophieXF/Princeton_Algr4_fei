import java.util.TreeSet; 
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV; 
import edu.princeton.cs.algs4.StdDraw;
import java.util.Deque;
import java.lang.NullPointerException;

public class KdTree {
	
	private Node root;
	private int numNode;  
	private class Node
	{
		public double x;
		public double y; 
		public int count; 
		public Node left; 
		public Node right;
		
		public Node(double xval, double yval, int countval) 
		{
			x = xval; 
			y = yval;
			count = countval;
		}
	} 
	// construct an empty set of points
	public KdTree()                                
	{
		root - null; 
	}
   // is the set empty?
	public boolean isEmpty() {
		return root == null; 
	} 
	// number of points in the set
	public int size() {
		return numNode; 
	}
	// add the point to the set (if it is not already in the set)         
	public void insert(Point2D p)
	{
		if ( p == null)
			throw new java.lang.NullPointerException();
		root = insert(root, p);	
	}

	private Node insert( Node nd, Point2D p)
	{
		if (nd == null) 
			{
				if (isEmpty())
					return new Node (p.x, p.y, 0);
				else 
					return new Node (p.x, p.y, nd.count + 1);
			}
		int cmp = compare (p, nd);
		if (cmp < 0)
			nd.left = insert(nd.left, p);
		else if (cmp > 0)
			nd.right = insert(nd.right, p); 
		else {
				nd.x = p.x();
				nd.y = p.y(); 
			} 
		return nd; 
	}

	private int compare( Point2D p, Node nd)
	{
		if (nd.count % 2 == 0 )
		{
			if (p.x() < nd.x) return -1; 
			else if (p.x() > nd.x) return +1; 
			else return 0;
		}
		else 
		{
			if(p.y() < nd.y) return -1; 
			else if (p.y() >nd.y) return +1; 
			else return 0;
		}
	}
	// does the set contain point p?             
	public boolean contains(Point2D p)
	{
		if ( p == null)
			throw new java.lang.NullPointerException();
		return contains (root, p);
	}

	private boolean contains (Node root, Point2D p)
	{
		if (root == null) 
			return false;
		else {
			int cmp = compare (p, root);
			if (cmp < 0)
				contains(nd.left, p);
			else if (cmp > 0)
				contains(nd.right, p); 
			else {
					return true;
				} 
		}
	}

	// draw all points to standard draw
	public void draw()
	{
		for (Point2D p2d : pset)
			StdDraw.point(p2d.x(), p2d.y());
	} 
	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect)             
	{
		if ( rect == null)
			throw new java.lang.NullPointerException();
		Deque<Point2D> pointsIn = new ArrayDeque<Point2D>();
		for (Point2D p2d : pset)
		{
			if (p2d.x() >= rect.xmin() && p2d.x() <= rect.xmax() && p2d.y() >= rect.ymin() && p2d.y() <= rect.ymax())
			{
				pointIn.push = p2d; 
			}
		}
		return pointIn; 
	} 
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p)  
	{
		if ( p == null)
			throw new java.lang.NullPointerException();

		Point2D temp = pset.first(); 
		if(isEmpty())
			return null; 

		for (Point2D p2d : pset)
		{
			p.distanceTo(p2d) <= p.distanceTo(temp);
			temp = p2d; 
		}
		return temp;
	}           

	public static void main(String[] args)                  // unit testing of the methods (optional) 
}