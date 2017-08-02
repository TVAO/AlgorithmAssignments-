/**
 * Created by JakeMullit on 13/04/15.
 */
public class main {


    public static void main(String[] args)
    {

        int i = Integer.MIN_VALUE;
        System.out.println("Minimum interger: "+i);
        System.out.println("Abselute value of minimum value "+Math.abs(i));
        System.out.println("Abselute value of minimum value -1"+Math.abs(i-1));
        /*
        BufferedReader reader = FileLoader.getReader();
        DirectedGraph graph  = new DirectedGraph();



        try
        {
            String currentLine = reader.readLine();
            while (currentLine!= null)
            {
                graph.insert(currentLine);
                currentLine =reader.readLine();
            }
            graph.ConnectedNodes();
            System.out.println(graph);

        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        */
    }



}
