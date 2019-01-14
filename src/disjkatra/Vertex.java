package disjkatra;

import android.database.Cursor;
import db.Data_base;

public class Vertex implements Comparable<Vertex> {

	public String name;
	public Edge[] adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;
	public int Id_Vertex;

	public Vertex(String argName) {
		name = argName;
	}

	@Override
	public String toString() {
		return name;
	}

	public Vertex(int Id_Vertex) {
		this.Id_Vertex = Id_Vertex;
		Cursor c = Data_base.select_vertex(Id_Vertex);
		if (c != null) {
			c.moveToFirst();
			this.name = c.getString(c.getColumnIndex("Vertex_name"));
			c.close();
		}
	}
	public void vertex_edge() {
		Cursor c = Data_base.select_move_vertex(Id_Vertex);
		int count = c.getCount();
		// System.out.println("Nb edge with vertix ID "+Id_Vertex+" = "+count);
		this.adjacencies = new Edge[count];
		c.moveToFirst();
		// for nb edges of vertex
		for (Integer j = 0; j < count; j++) {
			// System.out.println("lol");
			// get the i of the vertex associated with this edge

			// System.out.println("map_i "+map_i);
			// add the adjacencie tardgetr = associated vertex, weight
			Cursor c2 = Data_base.select_edge(c.getInt(c
					.getColumnIndex("Id_Edge")));
			c2.moveToFirst();
			// get the map_id of the vertex with Id_Veretx asso ==
			// System.out.println(c2.getInt(c2.getColumnIndex("Id_Vertex")));
			// System.out.println(Map_Disj.vertex_id_to_real_vertex_id.get(c2.getInt(c2.getColumnIndex("Id_Vertex"))));
			adjacencies[j] = (new Edge(
					Map_Disj.map.get(Map_Disj.vertex_id_to_real_vertex_id
							.get(c2.getInt(c2.getColumnIndex("Id_Vertex")))),
					c2.getInt(c2.getColumnIndex("Edge_weight")),
					c2.getString(c2.getColumnIndex("Edge_audio"))));
			c2.close();
			c.moveToNext();
			// print_vertex();
		}
		c.close();
		// print_veretx();
	}

	public void print_veretx() {
		System.out.println("Id " + Id_Vertex);
		System.out.println("name " + name);
		System.out.println("previous " + previous);
		for (Edge e : adjacencies) {
			System.out.println("Target name " + e.target.name);
			System.out.println("weight " + e.weight);
		}
	}
	@Override
	public int compareTo(Vertex other) {
		return Double.compare(minDistance, other.minDistance);
		// the value 0 if d1 is numerically equal to d2;
		// a value less than 0 if d1 is numerically less than d2;
		// and a value greater than 0 if d1 is numerically greater than d2.
	}
}
