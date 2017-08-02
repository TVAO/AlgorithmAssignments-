import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by JakeMullit on 26/04/15.
 */
public class newWordLadder {

    ArrayList<String> nodes;
    ArrayList<String> nodeKeys;
    Digraph graph;
    SeparateChainingHashST<String,Bag<Integer>> table;
    BreadthFirstDirectedPaths search;
    BufferedReader reader;




    public static void main(String[] args) {

        newWordLadder wordLadder = new newWordLadder();

        StdOut.println("Calculate shortestPath");
        StdOut.println("Input start node");
        String startNode = StdIn.readLine();
        StdOut.println("Input end node");
        String endNode = StdIn.readLine();
        StdOut.println("Calculating");
        wordLadder.findShortestPath(startNode,endNode);

    }

    public newWordLadder()
    {

        try{reader = new BufferedReader(new FileReader("input.txt"));}
        catch (java.io.FileNotFoundException e ){e.printStackTrace();}

        nodes = new ArrayList<String>();
        nodeKeys = new ArrayList<String>();
        table = new SeparateChainingHashST<>();
        createSubstringTable();
        createDegraph();
    }


    public void findShortestPath(String node1, String node2)
    {
        int node1Index = nodes.indexOf(node1);
        int node2Index = nodes.indexOf(node2);

        search = new BreadthFirstDirectedPaths(graph,node1Index);

        if(search.hasPathTo(node2Index))
        {

            for(Integer i : search.pathTo(node2Index))
            {
                System.out.println("Move to "+nodes.get(i));
            }
        }
        else
        {
            System.out.println("No path is connecting " + node1 + " and "+node2);
        }


    }
    public void createDegraph()
    {
        graph = new Digraph(nodes.size());
        String nodekey;
        Bag<Integer> bag;
        for(int i =0; i<nodes.size(); i++)
        {
            nodekey = nodeKeys.get(i);
            bag = table.get(new String(nodekey));
            Iterator edges = bag.iterator();

            while (edges.hasNext())
            {
                graph.addEdge(i,(int)edges.next());
            }
        }
    }

    public void createSubstringTable()
    {
        int index = 0;
        String[] currentSubstrings;
        String currentLine = StdIn.readLine();



            while (currentLine!= null)
            {
                String[] wordsOnLine = currentLine.split("\\s");
                for(String currentNode : wordsOnLine)
                {
                    nodes.add(index, currentNode);
                    currentSubstrings = createSubstringList(currentNode);
                    insertSubstringsInTable(currentSubstrings, index);
                    index++;
                    currentLine = StdIn.readLine();
                }
            }


        /*
        while (!StdIn.isEmpty())
        {
            currentNode = StdIn.readString();
            nodes.add(index, currentNode);
            currentSubstrings = createSubstringList(currentNode);
            insertSubstringsInTable(currentSubstrings,index);
            index++;
        }
        */



    }

    public String[] createSubstringList(String string)
    {
        String substring1 = string.substring(0, 4);
        String substring2 = string.substring(0, 1) + string.substring(2, 5);
        String substring3 = string.substring(0, 2) + string.substring(3, 5);
        String substring4 = string.substring(0, 3) + string.substring(4, 5);
        String substring5 = string.substring(1,5);

        char[] sortedString1 =substring1.toCharArray();
        char[] sortedString2 =substring2.toCharArray();
        char[] sortedString3 =substring3.toCharArray();
        char[] sortedString4 =substring4.toCharArray();
        char[] sortedString5 =substring5.toCharArray();

        Arrays.sort(sortedString1);
        Arrays.sort(sortedString2);
        Arrays.sort(sortedString3);
        Arrays.sort(sortedString4);
        Arrays.sort(sortedString5);

        String[] sortedSubstrings = new String[5];
        sortedSubstrings[0] = new String(sortedString1);
        sortedSubstrings[1] = new String(sortedString2);
        sortedSubstrings[2] = new String(sortedString3);
        sortedSubstrings[3] = new String(sortedString4);
        sortedSubstrings[4] = new String(sortedString5);

        return sortedSubstrings;
        }

        public void insertSubstringsInTable(String[] substrings, int index)
        {
            for (int i = 0; i<substrings.length; i++)
            {
                if(table.get(substrings[i])==null)
                {
                    Bag<Integer> bag= new Bag<>();
                    bag.add(index);
                    table.put(substrings[i],bag);
                }
                else table.get(substrings[i]).add(index);
            }
            //Store the nodes key for later use
            nodeKeys.add(index,substrings[4]);
        }

}
