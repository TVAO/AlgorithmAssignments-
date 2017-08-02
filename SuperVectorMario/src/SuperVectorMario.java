import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by JakeMullit on 29/04/15.
 */
public class SuperVectorMario
{


    //We go for a datastructor of size 2n where n is the amount places Mario could stand if all positions where non-lethal
    /*  S = start point
        F = finishPoint. Exit from the maze
        O = illegal area to stand on
        whitespace = legal area to stand on
     */

    int[][] nodes; // The outer array is used as index values for the graph, the inner list is x,y coordinates for the node
    int[][][] positions;
    //Example
    // position[x][y][0] = ascii value of either S,F,O or white space;
    // position[x][y][1] = index in graph;


    BufferedReader reader;
    int[] startCoordinate;
    int[] endCoordinate;
    Digraph graph;
    boolean hasPathBeenFound;
    BreadthFirstDirectedPaths search;

    int S = (int) 'S';
    int F = (int) 'F';
    int O = (int) 'O';
    int Space = (int) ' ';


    public static void main(String[] args)
    {
        SuperVectorMario mario = new SuperVectorMario();
        mario.printPath();


    }


    /**
     * width and height is the maps with and height
     */
    public SuperVectorMario()
    {
        hasPathBeenFound = false;
        loadData();
        createGraph();

    }

    public void createGraph()
    {
        graph = new Digraph(nodes.length);
        int x = startCoordinate[0];
        int y = startCoordinate[1];
        int startIndex = positions[x][y][1];
        createGraph(graph, x, y, startIndex, 0, 0);
    }

    /**
     * x and y is the current position
     */
    private void createGraph(Digraph graph, int x, int y, int previousIndex, int xVelocity, int yVerlocity)
    {
        if (x < 0 || y < 0 || x > nodes.length || y > nodes[x].length)
        {
            return;
        }
        if (hasPathBeenFound)
            return;

        if (positions[x][y][0] == O)
        {
            return;
        } else if (positions[x][y][0] == Space || positions[x][y][0] == S)
        {
            graph.addEdge(previousIndex, positions[x][y][1]);

            createGraph(graph, x + xVelocity, y + yVerlocity, positions[x][y][1], xVelocity, yVerlocity);
            createGraph(graph, x + xVelocity + 1, y + yVerlocity, positions[x][y][1], xVelocity + 1, yVerlocity);
            createGraph(graph, x + xVelocity - 1, y + yVerlocity, positions[x][y][1], xVelocity - 1, yVerlocity);
            createGraph(graph, x + xVelocity, y + yVerlocity + 1, positions[x][y][1], xVelocity, yVerlocity + 1);
            createGraph(graph, x + xVelocity, y + yVerlocity - 1, positions[x][y][1], xVelocity, yVerlocity - 1);
            createGraph(graph, x + xVelocity + 1, y + yVerlocity + 1, positions[x][y][1], xVelocity + 1, yVerlocity + 1);
            createGraph(graph, x + xVelocity - 1, y + yVerlocity - 1, positions[x][y][1], xVelocity - 1, yVerlocity - 1);

        } else
        {
            graph.addEdge(previousIndex, positions[x][y][1]);
            endCoordinate = new int[]{x, y};
            hasPathBeenFound = true;
        }
    }

    public void loadData()
    {
        try
        {
            reader = new BufferedReader(new FileReader("marioMap.text"));

            int width = Integer.parseInt(reader.readLine());
            int height = Integer.parseInt(reader.readLine());

            nodes = new int[width * height][2]; //width*height = total amount of nodes. 2 -> (x,y) Each node has a coordinate
            positions = new int[width][height][2];

            String currentLine = reader.readLine();

            int x = 0;
            int y = 0;
            int index = 0;

            while (currentLine != null)
            {
                char[] areas = currentLine.toCharArray();

                for (char area : areas)
                {
                    nodes[index][0] = x % width;
                    nodes[index][1] = y % height;
                    positions[x % width][y % height][0] = (int) area;
                    positions[x % width][y % height][1] = index;

                    //Store the start coordinate.
                    if (area == 'S')
                    {
                        startCoordinate = new int[]{x % width, y % height};
                    }

                    x++;
                    index++;
                }
                y++;
                currentLine = reader.readLine();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void printPath()
    {
        int x1 = startCoordinate[0];
        int y1 = startCoordinate[1];

        int x2 = endCoordinate[0];
        int y2 = endCoordinate[1];
        search = new BreadthFirstDirectedPaths(graph, positions[x1][y1][1]);

        for (Integer i : search.pathTo(positions[x2][y2][1]))
        {
            System.out.println(i);
        }
    }
}


