import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.In; 
import edu.princeton.cs.algs4.StdDraw; 
import edu.princeton.cs.algs4.StdOut; 

public class BruteCollinearPoints {
    private LineSegment[] segmentsLineList;
    private int numLineSegments;

   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
  //  StdOut.println("strat");
   	segmentsLineList = new LineSegment[points.length];
      if (points == null) {
   		throw new java.lang.NullPointerException();
   	}

   	LineSegment tempLineSegment; 
      numLineSegments = 0;
    //  StdOut.println("before sort: "+points.length);
   	Arrays.sort(points);
    //  StdOut.println("after sort: "+points.length);
    //  for (Point p : points) {
    //    StdOut.println("after sort points: " + p);
    //  }
      
   	for (int i = 0; i < points.length - 3; i++)
   		for (int j = i+1; j < points.length - 2; j++)
   			for (int k = j+1; k < points.length -1; k++)
   				for (int l = k+1; l < points.length; l++)
   				{
   					if (points[i] == null || points[j] == null 
                        || points[k] == null || points[l] == null)
   					{
   						throw new java.lang.NullPointerException();
   					}
   					if (points[i] == points[j] || points[i] == points[k] 
                        || points[i] == points[l])
   					{
   						throw new java.lang.IllegalArgumentException();
   					}
   					if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) 
                        && points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]))
   					{
   						tempLineSegment = new LineSegment(points[i], points[l]);
   						segmentsLineList[numLineSegments] = tempLineSegment; 
                     numLineSegments++;
                   //  StdOut.println("in loop point i: "+points[i]);
                   //  StdOut.println("in loop point j: "+points[j]);
                   //  StdOut.println("in loop point k: "+points[k]);
                   //  StdOut.println("in loop point l: "+points[l]);
                   //  StdOut.println("in loop slope ij: "+points[i].slopeTo(points[j]));
                   //  StdOut.println("in loop slope ik: "+points[i].slopeTo(points[k]));
                   //  StdOut.println("in loop slope il: "+points[i].slopeTo(points[l]));

                    // StdOut.println("in loop segment: "+tempLineSegment);
                    // StdOut.println("in loop numLineSegments: "+numLineSegments);
   					}

   				}

   }
   public int numberOfSegments()        // the number of line segments
   {
   	return numLineSegments; 
   }
   public LineSegment[] segments()                // the line segments
   {
   	LineSegment[] segmentsLineListused = new LineSegment[numLineSegments];
      for (int i = 0; i < numLineSegments; i++)
      {
         segmentsLineListused[i] = segmentsLineList[i];
      }
      return segmentsLineListused;
   }

   public static void main(String[] args) {

/*Point[] points = new Point[6]; 

      points[0] = new Point(10000,10000);
      points[1] = new Point(10000,20000);
      points[2] = new Point(20000,20000);
      points[3] = new Point(30000,30000);
      points[4] = new Point(30000,5000);
      points[5] = new Point(500,500);


      BruteCollinearPoints brutetest = new BruteCollinearPoints(points);
      StdOut.println("numberOfSegments: " + brutetest.numberOfSegments());
      StdDraw.show(0);
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      StdOut.println(p);
        p.draw();
    }
    StdDraw.show();
   for (LineSegment segment : brutetest.segments()) {
        StdOut.println("my print"+segment);
        segment.draw();
     }*/

    // read the N points from a file
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    for (int i = 0; i < N; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.show(0);
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    StdOut.println("start brute");
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    } 
}
}
