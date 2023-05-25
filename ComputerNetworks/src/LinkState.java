import java.util.*;
import java.io.File;

public class LinkState {
    private static final int NO_PARENT = -1;

    // Function that implements Dijkstra's
    // single source shortest path
    // algorithm for a graph represented
    // using adjacency matrix
    // representation
    private static void dijkstra(int[][] adjacencyMatrix,
                                 int startVertex) {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        int[] shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++) {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++) {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <
                                shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }

        printSolution(startVertex, shortestDistances, parents);
    }

    // A utility function to print
    // the constructed distances
    // array and shortest paths
    private static void printSolution(int startVertex,
                                      int[] distances,
                                      int[] parents) {
        System.out.print("Vertex\t Distance\tPath");

        int vertexIndex = 0;

            if (vertexIndex != startVertex)
            {
                System.out.print("\n" + (startVertex+1) + " -> ");
                System.out.print((vertexIndex+1) + " \t\t ");
                System.out.print(distances[vertexIndex] + "\t\t");
                printPath(vertexIndex, parents);
            }
    }

    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    private static void printPath(int currentVertex, int[] parents)
    {

        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT)
        {
            return;
        }
        printPath(parents[currentVertex], parents);
        System.out.print((currentVertex+1) + " ");
    }

    // Driver Code
    public static void main(String[] args) throws Exception
    {
        File topoFile = new File("C:\\Users\\oumta\\Desktop\\2022 Spring\\2022 fall\\Computer Networks\\topofile.txt");
        File changesFile = new File("C:\\Users\\oumta\\Desktop\\2022 Spring\\2022 fall\\Computer Networks\\changesfile.txt");
        File messageFile = new File("C:\\Users\\oumta\\Desktop\\2022 Spring\\2022 fall\\Computer Networks\\messagefile.txt");
        Scanner sc = new Scanner(topoFile);
        Scanner sc2 = new Scanner(changesFile);
        List<Integer> count = new ArrayList<Integer>();

        while (sc.hasNextLine())
        {
            int num = sc.nextInt();
            if(!count.contains(num))
            {
                count.add(num);
            }
            num = sc.nextInt();
            if(!count.contains(num))
            {
                count.add(num);
            }
            sc.nextInt();
        }

        int n = count.size();   // number of nodes
        int[][] adjacencyMatrix = new int[n][n];

        Scanner sc1 = new Scanner(topoFile);
        while (sc1.hasNextLine())
        {
            int i = sc1.nextInt() - 1;
            int j = sc1.nextInt() - 1;
            int k = sc1.nextInt();
            adjacencyMatrix[i][j] = k;
            adjacencyMatrix[j][i] = k;
        }

        dijkstra(adjacencyMatrix, 1);
    }
}