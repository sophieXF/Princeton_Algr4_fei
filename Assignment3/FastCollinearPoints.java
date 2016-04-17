import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.In; 
import edu.princeton.cs.algs4.StdDraw; 
import edu.princeton.cs.algs4.StdOut; 

public class FastCollinearPoints {

   private int numLineSegments;
   private ArrayList<LineSegment> segmentsLineList;

   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   {
   	numLineSegments = 0;
   	segmentsLineList = new ArrayList<LineSegment>(); 
   	LineSegment tempLineSegment; 
	
	Arrays.sort(points);
	for (int i=0; i < points.length-1; i++)
	{
		if(points[i] == points[i+1])
			throw new java.lang.IllegalArgumentException();
	}

   	for (int i = 0; i < points.length-3; i++)
   	{
   		Arrays.sort(points);
   	/*	 StdOut.println("after sort: "+points.length);
   		StdOut.println("after sort: ");

      for (Point p : points) {
        StdOut.print(p);
      }
      StdOut.println(i);*/


   	  Arrays.sort(points, i+1, points.length, points[i].slopeOrder());

   	 /* StdOut.println("after slope sort"+i);

      for (Point p : points) {
        StdOut.print(p);
      }
      StdOut.println("");*/


   		int j = 1; 
   		while (j < points.length - i -2)
   		{
   			int k = 0; 
   			while ((i+j+k+1) < points.length && points[i].slopeTo(points[i+j+k]) == points[i].slopeTo(points[i+j+k+1]))
   			{
   				k++;
   			}
   			if (k >= 2) {
   				tempLineSegment = new LineSegment(points[i], points[i+j+k]);
   				segmentsLineList.add(tempLineSegment); 
   				numLineSegments++;
   				  //   StdOut.println("in loop point i: "+points[i]);
                   //  StdOut.println("in loop point j: "+points[j]);
                   //  StdOut.println("in loop point k: "+points[k]);
                   //  StdOut.println("in loop point l: "+points[l]);
                   //  StdOut.println("in loop slope ij: "+points[i].slopeTo(points[j]));
                   //  StdOut.println("in loop slope ik: "+points[i].slopeTo(points[k]));
                   //  StdOut.println("in loop slope il: "+points[i].slopeTo(points[l]));

                  //   StdOut.println("in loop segment: "+tempLineSegment);
                   //  StdOut.println("in loop numLineSegments: "+numLineSegments);

   			}
   			j = j + k + 1; 
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
         	segmentsLineListused[i] = segmentsLineList.get(i);
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
  //  StdOut.println("start fast");
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    } 
}
}