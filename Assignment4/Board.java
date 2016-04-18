import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Stack;
import java.lang.Math;

public class Board {
    private int N; 
    private int[][] blkArray; 
    private int indexZeroi; 
    private int indexZeroj;

    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    {
        N = blocks.length; 
        blkArray = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                blkArray[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) 
                {
                    indexZeroi = i; 
                    indexZeroj = j;
                }
            }
    }
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension N
    {
        return N; 
    }

    public int hamming()                   // number of blocks out of place
    {
        int numOutofPlace = 0; 
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if(blkArray[i][j] != 0 && blkArray[i][j] != i * N + j + 1)
                    numOutofPlace++;
            }

        }
        return numOutofPlace;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manhattanSum = 0; 
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                if (blkArray[i][j] != 0)
                {
                    if(blkArray[i][j] % N == 0)
                    {
                        manhattanSum = manhattanSum + Math.abs(N - 1 - j) + Math.abs(blkArray[i][j]/ N - 1 -i); 
                    }
                    else 
                    manhattanSum = manhattanSum + Math.abs(blkArray[i][j] % N - 1 - j) + Math.abs(( blkArray[i][j] - blkArray[i][j] % N )/ N - i); 
                }
                

            }
        }
        return manhattanSum; 
    }

    public boolean isGoal()                // is this board the goal board?
    {
        int flag = 0; 
        int i = 0; 
        while(i < N && flag == 0)
        {
            int j = 0; 
            while (j < N && flag == 0)
            {
                if (blkArray[i][j] != 0 && blkArray[i][j]!= i * N + j + 1) flag = 1;
                j++;  
            }
            i++;
        }

        return flag == 0; 
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int r1 = 0; 
        int r2 = 0; 
        int i1 = 0; 
        int j1 = 0; 
        int i2 = 0; 
        int j2 = 0;
        int temp;

        Board arrayTwin = new Board(blkArray);

        while ( r2 == r1 || arrayTwin.blkArray[i1][j1] == 0 || arrayTwin.blkArray[i2][j2] == 0)
        {
            r1 = StdRandom.uniform(N * N); 
            r2 = StdRandom.uniform(N * N);
            i1 = (r1 - r1 % N) / N;
            j1 = r1 % N;
            i2 = (r2 - r2 % N) / N;
            j2 = r2 % N;
        }
        temp = arrayTwin.blkArray[i1][j1]; 
        arrayTwin.blkArray[i1][j1] = arrayTwin.blkArray[i2][j2];
        arrayTwin.blkArray[i2][j2] = temp;
        return arrayTwin;
    }

    public boolean equals(Object y)        // does this board equal y?
    {
        if ( y == this) return true; 
        if ( y == null) return false; 
        if (y.getClass() != this.getClass()) return false; 
        Board that = (Board) y; 
        if(this.dimension() != that.dimension()) return false;
        int flag = 0; 
        int i = 0; 
        while(i < N && flag == 0)
        {
            int j = 0; 
            while (j < N && flag == 0)
            {
                if (blkArray[i][j]!= that.blkArray[i][j]) flag = 1;
                j++;  
            }
            i++;
        }
        return flag == 0; 
    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        int[][] blkArraycopy1 = new int[N][N];
        int[][] blkArraycopy2 = new int[N][N];
        int[][] blkArraycopy3 = new int[N][N];
        int[][] blkArraycopy4 = new int[N][N];
        Stack<Board> allneighbors = new Stack<Board>();
        Board tempneighbor; 

        for (int i = 0; i < N; i++ )
            for (int j = 0; j < N; j++)
            {
                blkArraycopy1[i][j] = blkArray[i][j];
                blkArraycopy2[i][j] = blkArray[i][j];
                blkArraycopy3[i][j] = blkArray[i][j];
                blkArraycopy4[i][j] = blkArray[i][j];
            }

        if (indexZeroj > 0)
        {
            blkArraycopy1[indexZeroi][indexZeroj] = blkArray[indexZeroi][indexZeroj - 1];
            blkArraycopy1[indexZeroi][indexZeroj - 1] = 0; 
            tempneighbor = new Board(blkArraycopy1);
            allneighbors.push(tempneighbor);
        }
        if (indexZeroj < N-1)
        {
            blkArraycopy2[indexZeroi][indexZeroj] = blkArray[indexZeroi][indexZeroj + 1];
            blkArraycopy2[indexZeroi][indexZeroj + 1] = 0;
            tempneighbor = new Board(blkArraycopy2);
            allneighbors.push(tempneighbor);
        }
        if (indexZeroi > 0)
        {
            blkArraycopy3[indexZeroi][indexZeroj] = blkArray[indexZeroi -1 ][indexZeroj];
            blkArraycopy3[indexZeroi - 1][indexZeroj] = 0; 
            tempneighbor = new Board(blkArraycopy3);
            allneighbors.push(tempneighbor);
        }
        if (indexZeroi < N - 1)
        {
            blkArraycopy4[indexZeroi][indexZeroj] = blkArray[indexZeroi + 1][indexZeroj];
            blkArraycopy4[indexZeroi + 1][indexZeroj] = 0; 
            tempneighbor = new Board(blkArraycopy4);
            allneighbors.push(tempneighbor);
        }


        // StdOut.println("step0");
        // Stack<Board> allneighbors = new Stack<Board>();
        // Board tempneighbor1 = new Board(blkArray);
        // Board tempneighbor2 = new Board(blkArray);
        // Board tempneighbor3 = new Board(blkArray);
        // Board tempneighbor4 = new Board(blkArray); 
        // StdOut.println("indexj " + indexZeroj);
        // StdOut.println("indexi " + indexZeroi);
        // if(indexZeroj > 0) 
        //     {
        //         StdOut.println("next" + blkArray[indexZeroi][indexZeroj - 1]);
        //         tempneighbor1.blkArray[indexZeroi][indexZeroj] = blkArray[indexZeroi][indexZeroj - 1]; 
        //         StdOut.println("interstep1");
        //         tempneighbor1.blkArray[indexZeroi][indexZeroj - 1] = 0; 
        //         StdOut.println("interstep2");
        //         tempneighbor1.indexZeroj = indexZeroj - 1;  
        //         allneighbors.push(tempneighbor1); 
        //         //tempneighbor = new Board(blkArray);
        //     }
        //     StdOut.println("step1");
        // if(indexZeroj < N-1)
        //     {
        //         tempneighbor2.blkArray[indexZeroi][indexZeroj] = blkArray[indexZeroi][indexZeroj + 1]; 
        //         tempneighbor2.blkArray[indexZeroi][indexZeroj + 1] = 0; 
        //         tempneighbor2.indexZeroj = indexZeroj + 1;
        //         allneighbors.push(tempneighbor2);
        //        // tempneighbor = new Board(blkArray);

        //     }
        //      StdOut.println("step2");
        // if(indexZeroi > 0)
        //     {
        //         tempneighbor3.blkArray[indexZeroi][indexZeroj] = blkArray[indexZeroi - 1][indexZeroj]; 
        //         tempneighbor3.blkArray[indexZeroi - 1][indexZeroj] = 0; 
        //         tempneighbor3.indexZeroi = indexZeroi - 1;
        //         allneighbors.push(tempneighbor3); 
        //         //tempneighbor = new Board(blkArray);
        //     }
            
        // StdOut.println("step3");
        // if(indexZeroi < N-1)
        //     {
        //         tempneighbor4.blkArray[indexZeroi][indexZeroj] = blkArray[indexZeroi + 1][indexZeroj]; 
        //         tempneighbor4.blkArray[indexZeroi + 1][indexZeroj] = 0; 
        //         tempneighbor4.indexZeroi = indexZeroi - 1;
        //         allneighbors.push(tempneighbor4); 
        //         //tempneighbor = new Board(blkArray);
        //     }
        return allneighbors; 

    }

    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blkArray[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {}
}