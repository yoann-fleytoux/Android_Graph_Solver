package disjkatra;

public class Edge
{
    public final Vertex target;
    public final double weight;
    public String audio;
    
    public Edge(Vertex argTarget, double argWeight, String argAudio){
    	target = argTarget; 
    	weight = argWeight; 
    	audio = argAudio;
    }
}