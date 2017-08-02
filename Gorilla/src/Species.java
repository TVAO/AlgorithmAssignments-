/**
 * Holds information related to species, i.e. name and DNA profile.
 */
public class Species
{
    public String name;
    public int[] profile; // The profile represents a DNA sequence
    public double difference = 0.0;

    public Species(String name, int[] profile)
    {
        this.name = name;
        this.profile = profile;
    }
}