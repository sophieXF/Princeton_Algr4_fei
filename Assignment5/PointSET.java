import java.util.TreeSet; 
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV; 
import edu.princeton.cs.algs4.StdDraw;
import java.util.Deque;
import java.util.ArrayDeque;
import java.lang.NullPointerException;

public class PointSET {
	private TreeSet<Point2D> pset; 
	// construct an empty set of points
	public PointSET()                                
	{
		pset = new TreeSet<Point2D>(); 
	}
   // is the set empty?
	public boolean isEmpty() {
		return pset.isEmpty();
	} 
	// number of points in the set
	public int size() {
		return pset.size();
	}
	// add the point to the set (if it is not already in the set)         
	public void insert(Point2D p)
	{
		if (p == null)
			throw new java.lang.NullPointerException();
		if (!contains(p))
			pset.add(p);
	}
	// does the set contain point p?             
	public boolean contains(Point2D p)
	{
		if (p == null)
			throw new java.lang.NullPointerException();
		if (isEmpty()) return false;
		return pset.contains(p);
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
		if (rect == null)
			throw new java.lang.NullPointerException();
		Deque<Point2D> pointsIn = new ArrayDeque<Point2D>();
		for (Point2D p2d : pset)
		{
			if (p2d.x() >= rect.xmin() && p2d.x() <= rect.xmax() && p2d.y() >= rect.ymin() && p2d.y() <= rect.ymax())
			{
				pointsIn.push(p2d); 
			}
		}
		return pointsIn; 
	} 
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p)  
	{
		if (p == null)
			throw new java.lang.NullPointerException();
		if (isEmpty())
			return null;
		Point2D temp = pset.first();
		double tempDis;
		double minDis = p.distanceTo(temp); 
		for (Point2D p2d : pset)
		{
			tempDis = p.distanceTo(p2d);
			if (tempDis <= minDis) {
				temp = p2d; 
				minDis = tempDis;
			}
		}
		return temp;
	}           
// unit testing of the methods (optional)
	//public static void main(String[] args)                   
}