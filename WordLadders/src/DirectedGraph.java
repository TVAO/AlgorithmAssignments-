

public class DirectedGraph {


    //HashTable table = new HashTable(1000);
    Vertex[] vertices;
    int amountOfNodes = 100;
    int amountOfElementsInserted =0;

    public DirectedGraph()
    {
        vertices = new Vertex[amountOfNodes];
    }

    public void insert(String vertexName)
    {
        if(amountOfElementsInserted+1<vertices.length)
        {
            vertices[amountOfElementsInserted++] = new Vertex(vertexName);
           // table.insert(new Vertex(vertexName));
        }
        else// Expand array and insert new element
        {
            expandVeticesArray();
            insert(vertexName);
        }
    }

    public void ConnectedNodes()
    {
        Vertex currentVertex;

        for(int i= 0; i< amountOfElementsInserted; i++)
        {
            currentVertex = vertices[i];
            //currentVertex.insertOutgoingEdges(table.getAdjacentNodes(currentVertex));
        }

    }

    public void expandVeticesArray()
    {
        //Double the size
        Vertex[] newArray = new Vertex[vertices.length*2];

        for(int i = 0; i< vertices.length; i++)
        {
            newArray[i] = vertices[i];
        }

        vertices = newArray;
    }

    public Vertex getVertex(int index)
    {
        Vertex result = null;
        if (index<amountOfElementsInserted)
            result = vertices[index];
        return result;

    }



}
