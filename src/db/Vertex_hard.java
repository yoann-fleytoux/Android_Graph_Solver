package db;

import java.util.LinkedList;

import android.database.Cursor;

public class Vertex_hard implements Comparable<Vertex_hard>{
	
	public int Id_Vertex;
	public String Vertex_name;
	public LinkedList<Edge_tmp> adjacencies;;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex_hard previous=null;
	
	public Vertex_hard(int Id_Vertex){
		this.Id_Vertex = Id_Vertex;
		Cursor c= Data_base.select_vertex(Id_Vertex);
		if( c != null){
			 c.moveToFirst();
			this.Vertex_name = c.getString(c.getColumnIndex("Vertex_name"));
			c.close();
		}
			Cursor c2= Data_base.select_edge_vertex(Id_Vertex);
		    int count= c2.getCount();
		    
		    c2.moveToFirst();
		    adjacencies = new LinkedList<Edge_tmp>();
		    for (Integer j = 0; j < count; j++){
		    	//System.out.println("lol");
		    	adjacencies.add(new Edge_tmp(Id_Vertex, c2.getInt(c2.getColumnIndex("Id_Vertex")), c2.getInt(c2.getColumnIndex("Edge_weight")), c2.getString(c2.getColumnIndex("Edge_description")), c2.getString(c2.getColumnIndex("Edge_audio"))));
		    	c2.moveToNext();
		    }
		    //print_vertex();
		    c2.close();
	}

	public void print_vertex(){
		System.out.println(Id_Vertex);
		System.out.println(Vertex_name);
		System.out.println("adjacencies: ");
		for(int i=0; i < adjacencies.size(); i++){
			System.out.println(adjacencies.get(i).ASSOCIATED_VERTEX_ID);
			System.out.println(adjacencies.get(i).edge_lenght);
		}
		System.out.println("minDistance "+minDistance);
		if(previous != null)
			System.out.println("Previous "+previous.Id_Vertex);
		
	}

	@Override
	public int compareTo(Vertex_hard other) {
		double d2 = other.minDistance;
		double d1 =minDistance;
		 return Double.compare(d1,d2);
	}
}
