import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Vectors are represented as an array of characters (character vectors).
 */
public class Gorilla {

    private BufferedReader reader;
    private BufferedReader inputData;
    private String currentLine;

    // k is the substring length
    private int k = 2;
    // d is the size of the hash array
    private int d = 100;

    // Hash function
    public int getHashValue(String string)
    {
        int hashValue = string.hashCode() % d; // Modulo ensures that hashcode lies within array boundary
        return Math.abs(hashValue); // Avoid negative values 
    }

    // Explain....
    public int[] getProfile(String sequence)
    {
        int[] profile = new int[d];
        for (int i = 0; i < sequence.length() - k; i++) // n - k
        {
            // A k-gram is a substring from the original DNA string sequence with precicely k characters
            String gram = sequence.substring(i, i+k);
            profile[getHashValue(gram)]++; // Increment by one for each iteration
        }
        return profile;
    }

    // Calculates the length of a vector
    public double getLength(int[] charVector)
    {
        double powerValue = 0.0;
        for (int i = 0; i < charVector.length; i++)
        {
            powerValue += Math.pow(charVector[i],2);
        }
        double length = Math.sqrt(powerValue);
        return length;
    }

    // Calculates the dot product of two vectors
    public double getDotProduct(int[] firstCharVector, int[] secondCharVector)
    {
        double dotProduct = 0.0;
            for(int i = 0; i < firstCharVector.length && i < secondCharVector.length; i++)
            {
                dotProduct += firstCharVector[i] * secondCharVector[i];
            }
        return dotProduct;
    }

    // Calculates the angle between two vectors
    public double getAngle(int[] firstCharVector, int[] secondCharVector)
    {
        double dotProduct = getDotProduct(firstCharVector, secondCharVector);
        double firstLength = getLength(firstCharVector);
        double secondLength = getLength(secondCharVector);
        double angle = dotProduct / (firstLength * secondLength);
        return angle;
    }

    public static void main(String[] args)
    {
        Gorilla gorilla = new Gorilla();

        /*
        //gorilla.reader();
        int[] array = new int[2];
        array[0] = 2;
        array[1] = 2;
        int[] secondArray = new int[2];
        secondArray[0] = 2;
        secondArray[1] = 2;
        double length = gorilla.getLength(array);
        double dot = gorilla.getDotProduct(array,secondArray);
        double angle = gorilla.getAngle(array, secondArray);
        System.out.println("Length is " + length);
        System.out.println("Dot is " + dot);
        System.out.println("Angle is " + angle);
        */

        // Why these specific elements?
        int[] firstDNA = new int[]{1,5};
        int[] secondDNA = new int[]{10,4};
        double firstDNALength = gorilla.getLength(firstDNA);
        double dotProduct = gorilla.getDotProduct(firstDNA, secondDNA);
        double angle = gorilla.getAngle(firstDNA, secondDNA);
        StdOut.println("Length of vector is " + firstDNALength);
        StdOut.println("Dot product of vectors is " + dotProduct);
        StdOut.println("Angle between vectors is " + angle);
        StdOut.println("-----------------------------------------");

        ArrayList<Species> speciesList = new ArrayList<Species>();

        while(!StdIn.isEmpty())
        {
            // First line in data declares species type with additional irrelevant info
           String textName = StdIn.readLine();
            // Sequence consists of three consecutive lines in data
            String DNASequence = StdIn.readLine() + StdIn.readLine() + StdIn.readLine();
            // Filter out irrelevant info from first line, only want name of species
            String speciesName = textName.substring(1, textName.indexOf(" ")); // Filter out '>' and stop at space

            Species species = new Species(speciesName, gorilla.getProfile(DNASequence));
            speciesList.add(species);
        }

        // Convert ArrayList to Array and create one spot for registered species
        Species[] speciesArray = speciesList.toArray(new Species[0]);

        // Find difference in DNA between human and other species
        // Similarity is based upon the angle between the two vectors representing the DNA sequences for each species
        // A low angle degree thus implies a high correlation between the two species
        double DNADifference = 0.0;
        for (int i = 0; i < speciesArray.length; i++)
        {
            DNADifference = gorilla.getAngle(speciesArray[i].profile, speciesArray[0].profile); // Angle between given species and Human
            speciesArray[i].difference = DNADifference;
            {
                StdOut.println("Human DNA is set to " + DNADifference);
            }
            StdOut.println("Difference to Human proteine:  " + speciesArray[i].name + " is " + DNADifference);
        }

        // Find species closest to Human
        String closestSpecies = null;
        double closeDiff = 0.0;
        // i starts at 1 to skip Human species 
        for(int i = 1; i < speciesArray.length; i++)
        {
        	double newDifference = gorilla.getAngle(speciesArray[i].profile, speciesArray[0].profile); // Explain position...
      		// If difference is closer to 1 (equal to Human)
        	if(newDifference > closeDiff)
        	{
        		closeDiff = newDifference;
        		closestSpecies = speciesArray[i].name;
        	}
        }
        StdOut.println("The species closes to the Human species is " + closestSpecies + " with the difference " + closeDiff);
        
        // Find species farthest related to Human species 
        String farestSpecies = null;
        double farDiff = 1.0; // Starts at 1, equal to Human, and iterates down to lowest found value
        for (int i = 1; i < speciesArray.length; i++)
        {
        	double newDifference = gorilla.getAngle(speciesArray[i].profile, speciesArray[0].profile); // Explain position
        	// If DNA is less related to Human
        	if(newDifference < farDiff) 
        	{ 
        		farDiff = newDifference;
        		farestSpecies = speciesArray[i].name;
        	}
        }
        StdOut.println("The species farthest away from Human is " + farestSpecies + " with a difference " + farDiff);
        StdOut.println();

        /*
        StdOut.println("Test for angle: ");
        int[] testArrayP = new int[]{0,1};
        int[] testArrayQ = new int[]{1,0};
        double testAngle = gorilla.getAngle(testArrayP, testArrayQ);
        StdOut.println("Angle is: " + testAngle);

        StdOut.println("Test for multiangle: ");
        int[] testMultiArrayP = new int[1000];
        int[] testMultiArrayQ = new int[1000];
        for(int i = 0; i < 1000; i++)
        {
            if(i == 1000-1)
            {
                testMultiArrayP[i] = 1;
            }
            else testMultiArrayP[i] = 0; 
            if (i == 0)
            {
                testMultiArrayQ[i] = 1; 
            }
            else testMultiArrayQ[i] = 0;
        }
        double testMultiAngle = gorilla.getAngle(testMultiArrayP, testMultiArrayQ);
        StdOut.println("Angle for multi is: " + testMultiAngle);
        */ 

    }

    private void reader()
    {
        try
        {
            reader = new BufferedReader(new FileReader("data.txt"));
            currentLine = reader.readLine();

            while (currentLine != null)
            {
                System.out.println(currentLine);
                currentLine = reader.readLine();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }





}
