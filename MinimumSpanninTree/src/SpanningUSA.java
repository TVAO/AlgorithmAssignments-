import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JakeMullit on 21/04/15.
 */
public class SpanningUSA {

    private ArrayList<String> nodes;    // All nodes. Sorted list. The graph reference them by their index number
    private String regex;   // Regex used to interpret edge creation instructions
    private Pattern patern; // Regex pattern used to compile the regex statement
    private Matcher matcher;
    private int amountOfNodes;  //Amount of nodes the graph neeeds to contain
    private PrimMST prim;  //Algorithm used to find minimum spanning tree
    private EdgeWeightedGraph edgeWeightedGraph;    //Graph storing all edges
    private String currentLine; //Current line of the input reader

    public static void main(String[] args)
    {
        SpanningUSA spanningUSA = new SpanningUSA();
    }


    public SpanningUSA()
    {
        initializeComponents();
        loadNodes();
        Collections.sort(nodes); //Sort nodes so we can use binary search
        initializeGraph();
        insertEdges();
        findMinimumSpanningTreeOrForest();
        printMinimumSpanningTree();
        printMinimumSpanningTreeLength();

    }

    public void printMinimumSpanningTreeLength()
    {
        System.out.println("Spanning trees length " + prim.weight());
    }

    private void printMinimumSpanningTree()
    {
        System.out.println("Minimum spanning tree");

        for(Edge edge: prim.edges())
        {
            // Find edges outgoing from edge.either()
            System.out.println("Go from "+nodes.get(edge.either())+ " to "+ nodes.get(edge.other(edge.either())));
        }
    }

    private void findMinimumSpanningTreeOrForest()
    {
        prim = new PrimMST(edgeWeightedGraph);
    }

    private void loadNodes()
    {
        currentLine = StdIn.readLine();
        //Node lines does not contain "--".
        while(currentLine!= null && !currentLine.contains("--"))
        {
            //Store node
            nodes.add(currentLine);
            amountOfNodes++;
            currentLine = StdIn.readLine();
        }
    }

    private void initializeGraph()
    {
        edgeWeightedGraph = new EdgeWeightedGraph(amountOfNodes);
    }


    private void insertEdges()
    {
        //We use regex to interpret edge construction. Edge goes from startNode to EndNode with given weight
        while(currentLine!= null)
        {
            matcher = patern.matcher(currentLine);
            if(matcher.find())
            {
                String startNode = matcher.group(1);
                int startNodeIndex = getIndex(startNode,nodes); //Binary search for node's index number

                String endNode = matcher.group(2);
                int endNodeIndex = getIndex(endNode,nodes); //Binary search for node's index number

                double weight = Integer.parseInt(matcher.group(3));
                //Create PrinstonClasses.Edge based on index's og startNode and endNode
                edgeWeightedGraph.addEdge(new Edge(startNodeIndex,endNodeIndex, weight));
            }
            else
            {
                System.out.println("No match at "+ currentLine);
            }
            currentLine = StdIn.readLine();
        }
    }


    private void initializeComponents()
    {
        nodes = new ArrayList<>();
        String firstVertex = "(.*)--";
        String secondVertex = "(.*)\\s";
        String weight = "\\[(.*)\\]";
        regex = firstVertex + secondVertex + weight; //"(.*)--(.*)\\s\\[(.*)\\]";
        patern = Pattern.compile(regex);
        amountOfNodes = 0;

    }

    /**Binary search for index of given string in ArrayList nodes
     * Returns -1 if no result is found.
     */
    private int getIndex(String key, ArrayList<String> a)
    {
        int lo = 0;
        int hi = a.size() - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key.compareTo(a.get(mid))<0) hi = mid - 1;
            else if (key.compareTo(a.get(mid))>0) lo = mid + 1;
            else return mid;
        }
        return -1;
    }


}
