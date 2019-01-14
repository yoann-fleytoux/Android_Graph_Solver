package disjkatra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.my_test.MainActivity;

import db.Data_base;
import db.Move;
import db.Move_sub;
import db.Vertex_hard;

public class Map_Disj {
	public static LinkedList<Vertex_hard> map_hard = new LinkedList<Vertex_hard>();
	public static LinkedList<Vertex> map = new LinkedList<Vertex>();

	@SuppressLint("UseSparseArrays")
	public static Map<Integer, Integer> vertex_id_to_real_vertex_id = new HashMap<Integer, Integer>();

	public void clear() {
		map_hard.clear();
		map.clear();
		vertex_id_to_real_vertex_id.clear();
	}

	public static void compute_possibilities() {
		// get vertex_hard with id_start
		// System.out.println(MainActivity.id_start);
		int map_i = 0;
		for (int i = 0; i < map_hard.size(); i++) {
			if (MainActivity.id_start == map_hard.get(i).Id_Vertex) {
				map_i = i;
				break;
			}
		}
		// System.out.println(map.get(map_i).Id_Vertex);
		// map.get(map_i).print_vertex();
		Dijkstra.computePaths(map_hard.get(map_i));
		for (int j = 0; j < map_hard.size(); j++) {
			// if(j!=map_i && map.get(j).minDistance!=
			// Double.POSITIVE_INFINITY){
			System.out.println("Distance from "
					+ map_hard.get(map_i).Vertex_name + " to "
					+ map_hard.get(j).Vertex_name + ": "
					+ map_hard.get(j).minDistance);
			List<Vertex_hard> path = Dijkstra
					.getShortestPathTo(map_hard.get(j));
			System.out.println("Path: ");
			for (int k = 0; k < path.size(); k++) {
				System.out.println(path.get(k).Vertex_name);
			}
			map_hard.get(j).minDistance = Double.POSITIVE_INFINITY;
			// }else{
			// System.out.println("nope");
			// }
		}
	}

	public static void compute_possibilities_bis() {
		// get vertex_hard with id_start
		// System.out.println(MainActivity.id_start);
		int map_i = vertex_id_to_real_vertex_id.get(MainActivity.id_start);
		// System.out.println(map.get(map_i).Id_Vertex);
		// map.get(map_i).print_vertex();
		int done = -1;
		Dijkstra.computePaths(map.get(map_i));
		for (int j = 0; j < map.size(); j++) {
			if (j != map_i
					&& map.get(j).minDistance != Double.POSITIVE_INFINITY) {
				done++;
				System.out.println("Distance from " + map.get(map_i).name
						+ " to " + map.get(j).name + ": "
						+ map.get(j).minDistance);
				MainActivity.moves.add(new Move(map.get(map_i).name,
						map.get(j).name));
				List<Vertex> path = Dijkstra.getShortestPathTo(map.get(j));
				// System.out.println("There s " + path.size() + "-1 edges");
				int curr = map_i;
				for (int k = 0; k < path.size(); k++) {
					// System.out.println(path.get(k).name);
					for (Edge e : map.get(curr).adjacencies) {
						// System.out.println("Test " + e.target.Id_Vertex+
						// " == " + path.get(k).Id_Vertex);
						if (e.target.Id_Vertex == path.get(k).Id_Vertex) {
							MainActivity.moves.get(done).move.add(new Move_sub(
									path.get(k).name, e.audio));
							// System.out.println(path.get(k).name + " "
							// +e.audio+ " added");
							curr = vertex_id_to_real_vertex_id
									.get(e.target.Id_Vertex);
						}
					}
				}
				map.get(j).minDistance = Double.POSITIVE_INFINITY;
			}
		}
	}
	public Map_Disj() {
		clear();
		Cursor c = Data_base.select_composed_map(MainActivity.id_map);
		if (c != null) {
			int count = c.getCount();
			c.moveToFirst();
			for (Integer j = 0; j < count; j++) {
				map_hard.add(new Vertex_hard(c.getInt(c
						.getColumnIndex("Id_Vertex"))));
				map.add(new Vertex(c.getInt(c.getColumnIndex("Id_Vertex"))));
				vertex_id_to_real_vertex_id.put(
						c.getInt(c.getColumnIndex("Id_Vertex")), j);
				// System.out.println("map :"+
				// c.getInt(c.getColumnIndex("Id_Vertex")) + " to " + j);
				// TODO map_hard.get(j).vertex_hard_edges();
				// map.get(j).vertex_edge();
				// map.get(j).print_vertex();
				c.moveToNext();
			}
			c.close();
			for (Integer j = 0; j < count; j++)
				map.get(j).vertex_edge();
		} else {
			System.out.println("cursor empty Map_Disj");
		}
		/*
		 * for(int k=0; k < map.size(); k++){
		 * System.out.println("Id "+map.get(k).Id_Vertex);
		 * System.out.println("name "+map.get(k).name);
		 * System.out.println("previous "+map.get(k).previous); for (Edge e :
		 * map.get(k).adjacencies){
		 * System.out.println("Target name "+e.target.name);
		 * System.out.println("weight "+e.weight); } }
		 */
	}

}
