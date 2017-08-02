import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * WordLadders builds a digraph by reading a given words-x.txt file, 
 * where x represents a number of strings of five letters.
 * Then it takes input in the terminal of two words to search for. 
 * The application uses BFS to find the shortest path between the words and prints it out. 
 * 
 * The running time highly depends on the size of the list that again depends on the amount of words in our input file.
 * By way of example, the running time is assumed to be constant when checking lists of length 2-10.
 * On the contrary, when running this application on words-5757.txt we check all lists containing all nodes.
 * Thus, it checks n pairs and the running time is quadratic, O(n^2). 
 * The quadratic time is derived from the possibility that one runs through all other nodes for each node
 *  and make edges for nodes meeting the connection rule.
 *
 * Our build preprocesses the data to achieve linear time. 
 * In this way, it only takes constant time to find all nodes that meet the connection rule.
 * We use a Hash Table mapping combinations of 4 letters to bags of nodes that contain those 4 letters. 
 * The combinations represent unique substrings extracted from the origin one.
 * For each word in the input file, the program looks up the last four letters in the Hash Table and make edges to the associated nodes.
 * 
 * All combinations of the samme letters are mapped to the same key (original string) (EXPLAIN HOW).
 *
 */

public class WordLadder 

{

	// Fields
	BreadthFirstDirectedPaths BFS;
	Digraph digraph;
	ResizingArrayBag<String> substrings;
	// Used to map 4-5 letter combinations
	// Note: Separate Chaining handles key collisions 
	// Words with repeated characters should still have unique key for same character with different location
	// Example: the word 'bede' should have a key mapped to the first 'e' and a different key to the second 'e'
	SeparateChainingHashST<String, ResizingArrayBag<String>> wordCombination;  
	SeparateChainingHashST<String, Integer> wordsInteger; // Used for lookup 
	ArrayList<String> words; 
	int counter; 

	public WordLadder() 
	{
		wordCombination = new SeparateChainingHashST<>();
		wordsInteger = new SeparateChainingHashST<>();
		words = new ArrayList<>();
		counter = 0;
	}

	private void createSortedCombination(String input)

	{

		for(int i = 0; i < input.length(); i++)
		{

			// Build substring 4-combination 
			StringBuilder stringBuilder = new StringBuilder(input).deleteCharAt(i); 
			char[] sequence = stringBuilder.toString().toCharArray();

			// Sort combination
			Arrays.sort(sequence);
			String sortedWord = new String(sequence);

			// Add 4-combinations as keys and original word as value in bag 
			if(wordCombination.contains(sortedWord))
			{
				ResizingArrayBag bag = wordCombination.get(sortedWord);
				bag.add(input);
			}
			// Else insert sorted 4-combination and add original string to bag 
			else 
			{
				wordCombination.put(sortedWord, new ResizingArrayBag<String>());
				wordCombination.get(sortedWord).add(input); 
			}

		}

		wordsInteger.put(input, counter); // Holds given string and tracks when it was added 
		words.add(input);
		counter++;

	}

	public void createDigraph()
	{
		int size = wordsInteger.size();
		digraph = new Digraph(size);

		for(String word : wordsInteger.keys())
		{
			// Remove first char 
			StringBuilder stringBuilder = new StringBuilder(word).deleteCharAt(0);
			char[] sequence = stringBuilder.toString().toCharArray();

			// Sort 4-combination
			Arrays.sort(sequence);
			String sortedWord = new String(sequence);

			// Compare words and add edge 
			if(wordCombination.contains(sortedWord))
			{
				Iterator bagIterator = wordCombination.get(sortedWord).iterator();

				while(bagIterator.hasNext())
				{
					String bagItem = (String) bagIterator.next();
					int startVertex = wordsInteger.get(word);
					int endVertex = wordsInteger.get(bagItem);

					digraph.addEdge(startVertex, endVertex);
				}
			}
		}
	}

	public void findShortestPath(String firstWord, String secondWord)
	{
		int start = wordsInteger.get(firstWord);
		int end = wordsInteger.get(secondWord);

		BFS = new BreadthFirstDirectedPaths(digraph, start);

		if(BFS.hasPathTo(end))
		{
			StdOut.println();
			StdOut.println("Shortest path from " + firstWord + " to " + secondWord);
			StdOut.println("Distance calculated: " + BFS.distTo(end));
			StdOut.println("Shortest path: ");

			for(int steps : BFS.pathTo(end))
			{
				StdOut.println(words.get(steps));
			}
		}
		else StdOut.println("No path found between the two given words...");
	}

	public static void main(String[] args)
	{
		WordLadder wordLadder = new WordLadder();

		// Scan input and construct combinations

		StdOut.println("Loading input");
		while (StdIn.hasNextLine())
		{
			wordLadder.createSortedCombination(StdIn.readString());
		}
		StdOut.println("Data Loaded");
		StdOut.println("Creating directed graph");
		wordLadder.createDigraph();
		StdOut.println("Graph created");
		StdOut.println("Please Enter name of start Node");




		StdOut.println("WordLadder constructs a digraph from the givens words in the file words-5757.txt");
		// StdOut.println("Please type in the path of the file that should be compared with words-5757.txt");
		/*
		try
		{
			scanner = new Scanner(new BufferedReader(new FileReader("input.txt")));
			
			while (scanner.hasNext())
			{
				wordLadder.findShortestPath(scanner.next(), scanner.next());
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if (scanner != null)
			{
				scanner.close();
			}
		}
		StdOut.println("");

		*/
		

	}



}