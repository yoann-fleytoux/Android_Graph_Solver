package disjkatra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import db.Vertex_hard;


public class Dijkstra
{
    public static void computePaths(Vertex_hard source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex_hard> Vertex_hardQueue = new PriorityQueue<Vertex_hard>();
      	Vertex_hardQueue.add(source);
      	while (!Vertex_hardQueue.isEmpty()) {
      		Vertex_hard u = Vertex_hardQueue.poll();
            // Visit each edge exiting u
      		//u.print_vertex();
            for (int i=0;i < u.adjacencies.size();i++ ){
                Vertex_hard v = new Vertex_hard(u.adjacencies.get(i).ASSOCIATED_VERTEX_ID);
                double weight = u.adjacencies.get(i).edge_lenght;
                //System.out.println(u.adjacencies[i].edge_lenght+" to double "+ (double) u.adjacencies[i].edge_lenght);
                double distanceThroughU = u.minDistance + weight;
                //System.out.println(distanceThroughU+" < "+v.minDistance);
				if (distanceThroughU < v.minDistance) {
				    Vertex_hardQueue.remove(v);
				    v.minDistance = distanceThroughU ;
				    if(v.Id_Vertex != u.Id_Vertex){
				    	v.previous = u;
				    	Vertex_hardQueue.add(v);
				    }
				}
            }
        }
    }
    
    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
		}
            }
        }
    }

    public static List<Vertex_hard> getShortestPathTo(Vertex_hard target)
    {
        List<Vertex_hard> path = new ArrayList<Vertex_hard>();
        for (Vertex_hard Vertex_hard = target; Vertex_hard != null; Vertex_hard = Vertex_hard.previous)
            path.add(Vertex_hard);
        Collections.reverse(path);
        return path;
    }
    
    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }
    
}

