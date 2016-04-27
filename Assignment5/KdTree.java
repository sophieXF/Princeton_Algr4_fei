import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV; 
import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.StdOut;
import java.util.Deque;
import java.util.ArrayDeque;
import java.lang.NullPointerException;
import java.lang.Math;

public class KdTree {
	
	private Node root;
	private int numNode = 0;  
	private class Node
	{
		private double x;
		private double y; 
		private int count; 
		private Node left; 
		private Node right;
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
		root = null; 
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
		root = insert(root, p, -1);	
	}

	private Node insert( Node nd, Point2D p, int ct)
	{
		//StdOut.println("3");
		if (nd == null) 
			{
//				StdOut.println("4");
				numNode++;
				return new Node (p.x(), p.y(), ct + 1);
			}
	//	StdOut.println("rooty"+root.y);
//		StdOut.println("rootx"+root.x);
		if (nd.x == p.x() && nd.y == p.y()) return nd;
		int cmp = compare (p, nd);
		if (cmp < 0)
			nd.left = insert(nd.left, p, nd.count);
		else if (cmp >= 0)
			nd.right = insert(nd.right, p, nd.count); 
		return nd; 
	}

	private int compare( Point2D p, Node nd)
	{
		if (p.x() == nd.x && p.y() == nd.y)
			return 0;
		if (nd.count % 2 == 0 )
		{
			if (p.x() < nd.x) return -1; 
			else return +1; 
				
		}
		else 
		{
			if(p.y() < nd.y) return -1; 
			else return +1; 
		}
	}
	// does the set contain point p?             
	public boolean contains(Point2D p)
	{
		if ( p == null)
			throw new java.lang.NullPointerException();
		boolean flag; 
		flag = contains(root, p);
		return flag;
	}

	private boolean contains (Node nd, Point2D p)
	{
		boolean flag = false;
		if (nd == null) {
			return false;
		}
		else {
			int cmp = compare (p, nd);
			if (cmp < 0)
				flag = contains(nd.left, p);
			else if (cmp > 0)
				flag = contains(nd.right, p); 
			else {
					return true;
				} 
		}
		return flag;
	}

	// draw all points to standard draw
	public void draw()
	{	
		draw(root, 0, 0, 1, 1);
	} 

	private void draw(Node nd, double xmin, double ymin, double xmax, double ymax)
	{
		if (nd == null) return;
		if (nd.count % 2 == 0) {
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.01);
			StdDraw.point(nd.x, nd.y);
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();
			StdDraw.line(nd.x, ymin, nd.x, ymax);
			draw(nd.left, xmin, ymin, nd.x, ymax);
			draw(nd.right, nd.x, ymin, xmax, ymax);
		}
		if (nd.count % 2 != 0){
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(0.01);
			StdDraw.point(nd.x, nd.y);
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();
			StdDraw.line(xmin, nd.y, xmax, nd.y);
			draw(nd.left, xmin, ymin, xmax, nd.y);
			draw(nd.right, xmin, nd.y, xmax, ymax);
		}
	}
	// all points that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect)             
	{
		if ( rect == null)
			throw new java.lang.NullPointerException();

		Deque<Point2D> pointsIn = new ArrayDeque<Point2D>();
	//	StdOut.println("rooty"+root.y);
//		StdOut.println("rootx"+root.x);
		range(root, rect, pointsIn);	
		return pointsIn; 
	} 

	private void range(Node nd, RectHV rect, Deque<Point2D> pointIn)
	{ 
	//	StdOut.println("ndy1"+nd.y);
	//	StdOut.println("ndx1"+nd.x);
		if(nd == null) return;
		if (nd!=null && nd.count % 2 ==0){
			if (rect.xmax() < nd.x)
				range (nd.left, rect, pointIn);
			else if (rect.xmin() > nd.x) 
				range (nd.right, rect, pointIn); 
			else
			{
			//	StdOut.println("in");
		//		StdOut.println("ndy"+nd.y);
				if (nd.y <= rect.ymax() && nd.y >=rect.ymin())
					pointIn.push (new Point2D(nd.x, nd.y));
		//		StdOut.println("length: "+pointIn.size());
				range (nd.left, rect, pointIn);
				range (nd.right, rect, pointIn);
			}  
		}
		if (nd!=null && nd.count % 2 != 0){
			if (rect.ymax() < nd.y)
				range (nd.left, rect, pointIn);
			else if (rect.ymin() > nd.y)
				range (nd.right, rect, pointIn);
			else 
			{
				if (nd.x <= rect.xmax() && nd.x >= rect.xmin())
					pointIn.push (new Point2D(nd.x, nd.y));
				range (nd.left, rect, pointIn);
				range (nd.right, rect, pointIn);
			}
		}
		return;
	}
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p)  
	{
		if ( p == null)
			throw new java.lang.NullPointerException();
		Node nearNode; 
		//StdOut.println("rooty"+root.y);
		//StdOut.println("rootx"+root.x);
		if (isEmpty()) return null;
		nearNode = nearest(root, p, root);
		Point2D nearP = new Point2D (nearNode.x, nearNode.y);
		return nearP;
	}

	private Node nearest(Node nd, Point2D p, Node minNode)
	{
		Point2D tempP;
		double minDis;
		double tempDis;
		int cmp;
		Point2D tempminP = new Point2D(minNode.x, minNode.y);
		minDis = p.distanceTo(tempminP);
		if(nd == null)
			return minNode;
		else {
			tempP = new Point2D(nd.x, nd.y);
			tempDis = p.distanceTo(tempP);
			if (minDis > tempDis)
			{
				minNode = nd;
				//minDis = tempDis;
			}
			if (tempDis == 0) return nd;
			cmp = compare(p, nd);
			if (cmp < 0)
			{	
				minNode = nearest(nd.left, p, minNode);
				double qual = 0; 
				if(nd.count % 2 == 0 ) qual = Math.abs(p.x() - nd.x);
				else if (nd.count % 2 !=0 ) qual = Math.abs(p.y() - nd.y);
				if (minDis <= qual)
					return minNode; 
				else minNode = nearest(nd.right, p, minNode);
			}
			else if (cmp >= 0 )
			{	
				minNode = nearest(nd.right, p, minNode);
				double qual = 0; 
				if(nd.count % 2 == 0 ) qual = Math.abs(p.x() - nd.x);
				else if (nd.count % 2 !=0 ) qual = Math.abs(p.y() - nd.y);
				if (minDis <= qual)
					return minNode; 
				else minNode = nearest(nd.left, p, minNode);
			}
		}
		return minNode;
	}           
// unit testing of the methods (optional) 
	//public static void main(String[] args)                  
}