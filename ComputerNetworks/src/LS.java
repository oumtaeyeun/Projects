/*
    Taeyeun Oum
    11/20/2022
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LS
{
    private static final int NO_PARENT = -1;

    private static void dijkstra(int[][] adjacencyMatrix, int startVertex, int dest)
    {
        int nVertices = adjacencyMatrix[0].length;

        int[] shortestDistances = new int[nVertices];

        boolean[] added = new boolean[nVertices];

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        shortestDistances[startVertex] = 0;

        int[] parents = new int[nVertices];

        parents[startVertex] = NO_PARENT;

        for (int i = 1; i < nVertices; i++)
        {

            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
            {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            added[nearestVertex] = true;

            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
            {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }

        int path_cost = shortestDistances[dest];
        System.out.print(" cost " + path_cost + " hops ");
        printPath(dest, parents);

    }

    private static void printPath(int currentVertex, int[] parents)
    {
        if (currentVertex == NO_PARENT)
        {
            return;
        }
        printPath(parents[currentVertex], parents);
        System.out.print((currentVertex + 1) + " ");
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
        int change = 0;
        int change2 = 0;
        int change3 = 0;
        int changed = 0;
        int changeCount = 1;
        boolean isContained = false;
        boolean next = true;
        while (next)
        {
            if(!sc2.hasNext())
                next = false;
            Scanner topo = new Scanner(topoFile);
            while (topo.hasNextLine())
            {
                int temp1 = topo.nextInt();
                int temp2 = topo.nextInt();
                int temp3 = topo.nextInt();
                if(changeCount > 1 && temp1 == change && temp2 == change2)
                {
                    if(change == 2)
                        changed = change3 + temp3;
                    else
                        changed = changed + change3;
                    System.out.println(temp1 + " " + temp2 + " " + changed);
                    isContained = true;
                }
                else
                {
                    System.out.println(temp1 + " " + temp2 + " " + temp3);
                }
            }
            if(!isContained && change3 > 0)
            {
                System.out.println(change + " " + change2 + " " + change3);
            }
            Scanner sc4 = new Scanner(messageFile);
            while (sc4.hasNextLine())
            {
                int src = sc4.nextInt() - 1;
                int dest = sc4.nextInt() - 1;

                System.out.print("from " + (src+1) + " to " + (dest+1));
                dijkstra(adjacencyMatrix, src, dest);
                System.out.println("message" + sc4.nextLine());
            }
            if(sc2.hasNextInt())
                change = sc2.nextInt();
            if(sc2.hasNextInt())
                change2 = sc2.nextInt();
            if(sc2.hasNextInt())
                change3 = sc2.nextInt();
            if(sc2.hasNext())
                System.out.println("----------At this point, change #" + changeCount + " is applied: "
                                + change + " " + change2 + " " + change3);
            changeCount++;
            int i = change - 1;
            int j = change2 - 1;
            int k = change3;
            if(adjacencyMatrix[i][j] + k > 0 && adjacencyMatrix[j][i] + k > 0)
            {
                adjacencyMatrix[i][j] += k;
                adjacencyMatrix[j][i] += k;
            }
            else
            {
                adjacencyMatrix[i][j] = 0;
                adjacencyMatrix[j][i] = 0;
            }
        }
    }
}
