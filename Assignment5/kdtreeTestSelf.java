import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class kdtreeTestSelf {
	public static void main (String[] args) {
		String filename = args[0];
		In in = new In(filename);
		KdTree kdtree = new KdTree();
		while (!in.isEmpty())
		{
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D (x, y);
			kdtree.insert(p);
		}
		kdtree.draw();
		StdOut.println("size: "+ kdtree.size());
		Point2D q = new Point2D (0.206107, 0.095492);
		//Point2D x = kdtree.nearest(q);
		//StdOut.println("nearest x, y: "+ x.x()+", "+x.y());
		boolean x = kdtree.contains(q); 
		StdOut.println("contains: " + x);
		Point2D q1 = new Point2D (0.206107, 0.904508);
		boolean x1 = kdtree.contains(q1);
		StdOut.println("contains: " + x1);
	}
}