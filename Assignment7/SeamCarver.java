import edu.princeton.cs.algs4.Picture; 
import edu.princeton.cs.algs4.StdOut; 
// import java.lang.Object; 
import java.awt.Color;
// import java.lang.IndexOutOfBoundsException;
// import java.lang.NullPointerException;
// import java.lang.IllegalArgumentException;
// import java.lang.IllegalArgumentException;

public class SeamCarver {
	private int[][] colorTable; 
	private double[][] energyTable;
	private int picH; 
	private int picW; 
	private int[] seamlist; 

	// create a seam carver object based on the given picture
	public SeamCarver(Picture picture) {
		if (picture == null)
		{
			throw new java.lang.NullPointerException();
		}
		picH = picture.height();
		picW = picture.width();
		colorTable = new int[picH][picW];
		energyTable = new double[picH][picW];
		 // StdOut.println("picH: "+ picH + ";picW: "+ picW);
		for (int i = 0; i < picH; i++)
			for (int j = 0; j < picW; j++)
			{
				// StdOut.println("i = "+i+"; j = "+j);
				colorTable[i][j] = picture.get(j, i).getRGB();
				// StdOut.println("i = "+i+"; j = "+j+ "; color = " + colorTable[i][j].getBlue());
			}
		for (int i = 0; i < picH; i++)
			for (int j = 0; j < picW; j++)
			{
				energyTable[i][j] = energy(j, i);
				// StdOut.println("i = "+i+"; j = "+j+ "; energy = " + energyTable[i][j]);
			}
	}

	// current picture
	public Picture picture() {
		Picture curPicture = new Picture(picW, picH);
		Color tempColor; 
		for (int i = 0; i < picH; i++)
			for (int j = 0; j < picW; j++)
			{
				tempColor = new Color(colorTable[i][j]);
				curPicture.set(j, i, tempColor);
			}
		return curPicture;
	}                     

	// width of current picture
	public int width() {
		return picW;
	} 

	// height of current picture
	public int height() {
		return picH; 
	}

	// energy of pixel at column x and row y                    
	public double energy(int x, int y) {
		// StdOut.println("x: " + x + "; y: "+y);
		// StdOut.println("picW " + picW + "; picH: "+picH);
		if (x < 0 || y < 0 || x >= picW || y >= picH)
		{
			throw new java.lang.IndexOutOfBoundsException();
		}
		// StdOut.println("step2");
		if (x == 0 || y == 0 || x == picW - 1 || y == picH - 1)
		{
			return 1000;
		}
		int rx = (colorTable[y][x - 1] & 0xFF) - (colorTable[y][x + 1] & 0xFF); 
		int gx = ((colorTable[y][x - 1] & 0xFF00) >> 8) - ((colorTable[y][x + 1] & 0xFF00) >> 8);
		int bx = ((colorTable[y][x - 1] & 0xFF0000) >> 16) - ((colorTable[y][x + 1] & 0xFF0000) >> 16);
		int ry = (colorTable[y - 1][x] & 0xFF) - (colorTable[y + 1][x] & 0xFF); 
		int gy = ((colorTable[y - 1][x] & 0xFF00) >> 8) - ((colorTable[y + 1][x] & 0xFF00) >> 8);
		int by = ((colorTable[y - 1][x] & 0xFF0000) >> 16) - ((colorTable[y + 1][x] & 0xFF0000) >> 16);

		double vEnergy = Math.sqrt(rx*rx + gx*gx + bx*bx + ry*ry + gy*gy + by*by);
		return vEnergy; 
	}
	// sequence of indices for horizontal seam            
	public int[] findVerticalSeam() { 
		double minWeight = Double.POSITIVE_INFINITY;
		int[][] prePixelY = new int[picH][picW]; 
		double[][] totalWeightTill = new double[picH][picW]; 
		seamlist = new int[picH];

		// int minY = 0;

		for (int i = 0; i < picH; i++)
		{
			for (int j = 0; j < picW; j++)
			{
				totalWeightTill[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		for (int j = 0; j < picW; j++)
		{
			totalWeightTill[0][j] = 1000;
		}
		// StdOut.println("picH: "+ picH + ";picW: "+ picW);

		for (int i = 0; i < picH; i++)
		{
			for (int j = 0; j < picW; j++)
			{
				if (i == 0) {
					totalWeightTill[i][j] = 1000;
				}
				else {
					double curEnergy = energyTable[i][j];
					for (int k = j - 1; k <= j + 1; k++)
					{
						if (k <= 0 || k >= picW) continue;
						if (totalWeightTill[i][j] > totalWeightTill[i-1][k] + curEnergy)
						{
							totalWeightTill[i][j] = totalWeightTill[i-1][k] + curEnergy; 
							prePixelY[i][j] = k; 
						}
						if (i == picH - 1)
						{
							if (totalWeightTill[i][j] < minWeight) {
								seamlist[picH - 1] = j;
								minWeight = totalWeightTill[i][j];
							}
						}
					}
				}
			}
		}
		// StdOut.println("picH seam: " + picH + "seam length: "+ seamlist.length);
		// seamlist[picH - 1] = minY; 
		for (int i = picH - 2; i >= 0; i--)
		{
			seamlist[i] = prePixelY[i + 1][seamlist[i + 1]];
		}
		return seamlist;
	}

	private void transpose() {
		int temp; 
		int[][] trancolorTable = new int[picW][picH];
		double[][] tranenergyTable = new double[picW][picH]; 
		for (int i = 0; i < picW; i++)
			for (int j = 0; j < picH; j++)
			{
				trancolorTable[i][j] = colorTable[j][i];
				tranenergyTable[i][j] = energyTable[j][i];
			}
		temp = picW;
		picW = picH; 
		picH = temp; 
		colorTable = trancolorTable;
		energyTable = tranenergyTable;
	}
    
	// sequence of indices for vertical seam 
	public int[] findHorizontalSeam()   
 	{
 		transpose();
 		findVerticalSeam();
 		transpose();
 		return seamlist;
	}

	// remove horizontal seam from current picture
	public void removeHorizontalSeam(int[] seam) {
		if (seam == null)
		{
			throw new java.lang.NullPointerException();
		}
		if (picH <= 1)
		{
			throw new java.lang.IllegalArgumentException();
		}
		transpose();
		removeVerticalSeam(seam);
		transpose();
	}
	// remove vertical seam from current picture
	public void removeVerticalSeam(int[] seam) {
		if (seam == null)
		{
			throw new java.lang.NullPointerException();
		}
		if (seam.length != picH)
		{
			throw new java.lang.IllegalArgumentException();
		}
		if (picW <= 1)
		{
			throw new java.lang.IllegalArgumentException();
		}
		int temp;
		for (int i = 0; i < seam.length; i++)
		{
			temp = seam[i];
			if (temp < 0 || temp >= picW) 
			{
				throw new java.lang.IllegalArgumentException();
			} 
			if (i != (seam.length - 1) && Math.abs(temp - seam[i + 1]) > 1)
			{
				throw new java.lang.IllegalArgumentException();
			}
		}

		for (int i = 0; i < seam.length; i++)
		{
			temp = seam[i];
			for (int j = 0; j <= picW - 2 - temp; j++)
			{
				colorTable[i][temp + j] = colorTable[i][temp + j + 1];
				energyTable[i][temp + j] = energyTable[i][temp + j + 1];
			}			
		}
		picW = picW - 1;
		for (int i = 0; i < seam.length; i++)
		{
			temp = seam[i];
			// StdOut.println("i = "+ i + "; temp: "+ temp);

			if (temp < picW) 
				energyTable[i][temp] = energy(temp, i);
			if (temp != 0)
				energyTable[i][temp - 1] = energy(temp - 1, i);
			if (temp < picW && i != 0) 
				energyTable[i - 1][temp] = energy(temp, i - 1);
			if (temp < picW && i != picH - 1)
				 // StdOut.println("i = "+ i + "; temp: "+ temp);
				 // StdOut.println("picW: " + picW + "; picH" + picH);
				 // double x = energy(temp, i + 1);
				 // StdOut.println("x: "+ x);
				 // energyTable[i + 1][temp] = x; }
				 energyTable[i + 1][temp] = energy(temp, i + 1);	
		} 
	}
}


