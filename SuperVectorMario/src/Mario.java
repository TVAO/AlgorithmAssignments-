import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * This class is used to find paths between given words among the five-letter words of English.
 * The class constructs a grid of dimension 40x40 holding positions with different attributes.
 * A position is either makred "S" (start), "F" (finish), "O" (offroad) or " " (whitespace).
 * The class determines the shortest path from S to F by evaluating all moving options on the way.
 * A boolean method is used to evaluate what options are legal, e.g. "O" is illegal and " " is legal.
 */
public class Mario {

    // Inner array holds (x,y) coordinates and outer array holds index values for each node in graph
    private int[][] nodes;
    private int [][][] positions;
    private HashMap<Integer, PointWithAcceleration> visitedLocations; // Keeps track of previous moves

    /*
    Example
    position[x][y][0] = ascii value of either S,F,O or white space;
    position[x][y][1] = index in graph;
     */

    private BufferedReader reader;
    private int[] startCoordinate;
    private int[] endCoordinate;
    private Digraph graph;
    private boolean hasPathBeenFound;
    private BreadthFirstDirectedPaths BFS;
    private MinPQ<ComparableLinkedList> queue;

    // Positions determined by ascii value
    private int S = (int) 'S';
    private int F = (int) 'F';
    private int O = (int) 'O';
    private int Space = (int) ' ';

    public Mario()
    {
        queue = new MinPQ<>();
        loadData();
        visitedLocations = new HashMap<>();

    }

    public static void main(String[] args)
    {
        Mario mario = new Mario();
        mario.createGraph();
    }

    /**
     * Creates a graph by evaluating all moving positions on the way and adding the best moves to a queue.
     * Checks if moves are legal for all directions for each position and the current acceleration.
     * @return list with shortest move set (path)
     */
    public ComparableLinkedList createGraph()
    {
        ComparableLinkedList start = new ComparableLinkedList();
        ComparableLinkedList newPath;
        start.add(startCoordinate);
        start.setAcceleration(0,0);
        queue.insert(start);
        int x;
        int y;
        int[] coordinate;

        ComparableLinkedList currentList = null;
        while(!hasPathBeenFound)
        {
            // Store shortest list and delete other


            try {
                currentList = (ComparableLinkedList) queue.min().clone(); queue.delMin();
            }
            catch (NoSuchElementException e)
            {
                hasPathBeenFound = true;
                System.out.println("Path not found from start to finish point.");
            }

                coordinate = currentList.getLast();
                x = coordinate[0];
                y = coordinate[1];

            if(positions[x][y][0]==F)
            {
                hasPathBeenFound=true;
                System.out.println("Path has been found");
                System.out.println(currentList.size());
                return currentList;

            }

            if(isMoveLegal(currentList,1,0))
            {
                newPath = (ComparableLinkedList) currentList.clone();
                newPath.xAccelerate();
                newPath.insertNextCoordinate();
                if(isUnknownScenario(newPath))
                    queue.insert(newPath);
            }

            if(isMoveLegal(currentList,0,1))
            {
                newPath = (ComparableLinkedList) currentList.clone();
                newPath.yAccelerate();
                newPath.insertNextCoordinate();
                if(isUnknownScenario(newPath))
                    queue.insert(newPath);
            }

            if(isMoveLegal(currentList,1,1))
            {
                newPath = (ComparableLinkedList) currentList.clone();
                newPath.xAccelerate();
                newPath.yAccelerate();
                newPath.insertNextCoordinate();
                if(isUnknownScenario(newPath))
                    queue.insert(newPath);
            }

            if(isMoveLegal(currentList,-1,0))
            {
                newPath = (ComparableLinkedList) currentList.clone();
                newPath.DeXAccelerate();
                newPath.insertNextCoordinate();
                if(isUnknownScenario(newPath))
                    queue.insert(newPath);
            }

            if(isMoveLegal(currentList,0,-1))
            {
                newPath = (ComparableLinkedList) currentList.clone();
                newPath.DeYAccelerate();
                newPath.insertNextCoordinate();
                if(isUnknownScenario(newPath))
                    queue.insert(newPath);
            }

            if(isMoveLegal(currentList,-1,-1))
            {
                newPath = (ComparableLinkedList) currentList.clone();
                newPath.DeYAccelerate();
                newPath.DeXAccelerate();
                newPath.insertNextCoordinate();
                if(isUnknownScenario(newPath))
                    queue.insert(newPath);
            }

            if(isMoveLegal(currentList,+1,-1))
            {
                newPath = (ComparableLinkedList) currentList.clone();
                newPath.DeYAccelerate();
                newPath.xAccelerate();
                newPath.insertNextCoordinate();
                if(isUnknownScenario(newPath))
                    queue.insert(newPath);
            }

            if(isMoveLegal(currentList,-1,+1))
            {
                newPath = (ComparableLinkedList) currentList.clone();
                newPath.DeXAccelerate();
                newPath.yAccelerate();
                newPath.insertNextCoordinate();
                if(isUnknownScenario(newPath))
                    queue.insert(newPath);
            }

            if(isMoveLegal(currentList,0,0))
            {
                newPath = (ComparableLinkedList) currentList.clone();
                newPath.insertNextCoordinate();
                if(isUnknownScenario(newPath))
                    queue.insert(newPath);
            }
        }
        return null;

    }

    /**
     * Reads an input file using a BufferedReader and builds the grid to be played on.
     */
    public void loadData()
    {
        try
        {
            reader = new BufferedReader(new FileReader("eighty-in.txt"));

            int width = Integer.parseInt(reader.readLine());
            int height = Integer.parseInt(reader.readLine());

            // Grid dimension is equal to width * height
            positions = new int[width][height][2];

            // Each node holds two coordinates representing a position
            nodes= new int[width*height][2];

            String currentLine = reader.readLine();

            int x = 0;
            int y = 0;
            int index = 0;

            while (currentLine!= null)
            {
                char[] areas = currentLine.toCharArray();

                for(char area: areas)
                {
                    nodes[index][0] = x % width;
                    nodes[index][1] = y % height;
                    // Stores char ascii value positions[x][y][0]
                    positions[x%width][y % height][0] = (int) area;
                    // Stores index of node representing coordinate pair for a given position in the grid
                    positions[x%width][y % height][1] = index;

                    //Store start coordinate
                    if(area == 'S')
                        startCoordinate = new int[]{x % width,y % height};

                    x++;
                    index++;
                }

                y++;
                currentLine= reader.readLine();

            }

        }
        catch (IOException e){e.printStackTrace();}
    }

    /**
     * Prints the path between two coordinates.
     */
    public void printPath()
    {
        int x1 = startCoordinate[0];
        int y1 = startCoordinate[1];

        int x2 = endCoordinate[0];
        int y2 = endCoordinate[1];
        BFS = new BreadthFirstDirectedPaths(graph,positions[x1][y1][1]);

        for(Integer i: BFS.pathTo(positions[x2][y2][1]))
        {
            System.out.println(i);
        }
    }

    /**
     * Checks whether a given move is legal based on what positions have previously been marked
     * and what the current acceleration is set to.
     * @param list holding current move set history
     * @param x coordinate
     * @param y coordinate
     * @return true if move is legal
     */
    public boolean isMoveLegal(ComparableLinkedList list, int x, int y)
    {
        x+= list.getLast()[0]+ list.getxAcceleration();
        y+= list.getLast()[1]+ list.getyAcceleration();

        if(x < 0 || x >= positions.length ||
           y < 0 || y >= positions[x].length)
        {
            return false;
        }
        else if(positions[x][y][0] == O)
        {
            return false;
        }
        
        else return true;
    }

    /**
     * Checks if a position has already been visited with a given acceleration.
     * @param list - holding coordinates in the path.
     * @return true if position has not been visited already with given acceleration.
     */
    private boolean isUnknownScenario(ComparableLinkedList list)
    {
        if(!visitedLocations.containsKey(list.getPoint().hashCode()))
        {
            visitedLocations.put(list.getPoint().hashCode(),list.getPoint());
            return true;
        }
        else
            return false;
    }


}



