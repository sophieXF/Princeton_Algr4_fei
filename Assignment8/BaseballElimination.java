import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;


public class BaseballElimination {
    private int numTeam; 
    private ST<String, Integer> teamName; 
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g; 
    private String[] nameString;
    private int teamIndex;
    private ArrayList<String> subset; 
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In input = new In(filename);
        String line = input.readLine();
        numTeam = Integer.parseInt(line);
        w = new int[numTeam];
        l = new int[numTeam];
        r = new int[numTeam];
        g = new int[numTeam][numTeam];
        nameString = new String[numTeam];
        int i = 0;
        teamName = new ST<String, Integer>();
        while (input.hasNextLine()) {
            line = input.readLine().trim();
            String[] parts = line.split(" +"); 
            teamName.put(parts[0], i);
            nameString[i] = parts[0];
            w[i] = Integer.parseInt(parts[1]);
            l[i] = Integer.parseInt(parts[2]);
            r[i] = Integer.parseInt(parts[3]);
            for (int j = 0; j < numTeam; j++)
                g[i][j] = Integer.parseInt(parts[4+j]);
            i++;
        }
        teamIndex = - 1;

    }
    // number of teams
    public int numberOfTeams() {
        return numTeam;
    }
    // all teams
    public Iterable<String> teams() {
        return teamName.keys();
    }
    // number of wins for given team
    public int wins(String team) {
        if (!teamName.contains(team))
            throw new IllegalArgumentException();
        int i = teamName.get(team);
        return w[i];
    }
    // number of losses for given team
    public int losses(String team) {
        if (!teamName.contains(team))
            throw new IllegalArgumentException();
        int i = teamName.get(team);
        return l[i];
    }
    // number of remaining games for given team
    public int remaining(String team) {
        if (!teamName.contains(team))
            throw new IllegalArgumentException();
        int i = teamName.get(team);
        return r[i];
    }
    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!teamName.contains(team1) || !teamName.contains(team2))
            throw new IllegalArgumentException();
        int i = teamName.get(team1);
        int j = teamName.get(team2);
        return g[i][j];
    }
    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!teamName.contains(team))
            throw new IllegalArgumentException();
        teamIndex = teamName.get(team);
        int maxwin = w[teamIndex] + r[teamIndex];
        subset = new ArrayList<String>(); 
        for (int i = 0; i < numTeam; i++)
        {
            if (maxwin < w[i]) {
                subset.add(nameString[i]);
                return true;
            }
        }
        int numV = numTeam * (numTeam - 1) / 2 + numTeam  + 2; 
        double totalcapS = 0;
        FlowEdge edge;
        FlowNetwork baseballFlow = new FlowNetwork(numV);
        int count = numTeam; 
        for (int i = 0; i < numTeam; i++)
        {   
            if (i != teamIndex) {
                for (int j = i + 1; j < numTeam; j++) {
                    // StdOut.println("i = " + i + "j = " + j);
                    if (j != teamIndex) {
                        totalcapS = totalcapS + (double) g[i][j];
                        edge = new FlowEdge(numV - 2, count, (double) g[i][j]);
                        baseballFlow.addEdge(edge);
                        edge = new FlowEdge(count, i, Double.POSITIVE_INFINITY);
                        baseballFlow.addEdge(edge);
                        edge = new FlowEdge(count, j, Double.POSITIVE_INFINITY);
                        baseballFlow.addEdge(edge);
                        count++; 
                    }
                }
            }
        }
        for (int k = 0; k < numTeam; k++)
        {
            if(k != teamIndex)
            {   
                double cap = w[teamIndex] + r[teamIndex] - w[k];
                edge = new FlowEdge(k, numV - 1, cap);
                baseballFlow.addEdge(edge);
            }
        }
        FordFulkerson baseballSolution = new FordFulkerson(baseballFlow, numV - 2, numV - 1);

        if (baseballSolution.value() < totalcapS) {
            for (int i = 0; i < numTeam; i++) {
                if (i != teamIndex) {
                    if (baseballSolution.inCut(i) == true)
                        subset.add(nameString[i]);
                }
            }            
            return true;
        }
        else {
            subset = null;
           return false; 
        }
    }
    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!teamName.contains(team))
            throw new IllegalArgumentException();
        int thisteamIndex = teamName.get(team);
        if (thisteamIndex != teamIndex)
            isEliminated(team);
        return subset;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}