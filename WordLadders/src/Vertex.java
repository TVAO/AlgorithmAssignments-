import java.util.Arrays;

/**
 * Created by JakeMullit on 13/04/15.
 */
public class Vertex {

    //Stores neighbor edges
    private Bag<Vertex> outgoingEdges;
    private boolean visited;
    private String name;
    private String sortedName;





    public Vertex(String name)
    {
        this.name = name;
        outgoingEdges = new Bag<>();
        visited = false;
        sortedName = sortString(name);

    }

    public String getName()
    {return name;}

    public String getSortedLastFourDigitsOfName()
    {
        //Get the last four characters in Vertex name
        String lastCharacters = name.substring(1,5);

        return sortString(lastCharacters);
    }

    private String sortString(String input)
    {
        System.out.println(input);
        char[] sortedChars  = input.toCharArray();
        Arrays.sort(sortedChars);
        return String.valueOf(sortedChars);
    }

    public String getSortedName()
    {
        return sortedName;
    }



    public void insertOutgoingEdges(Bag<Vertex> vertices)
    {
        outgoingEdges = vertices;
    }



}
