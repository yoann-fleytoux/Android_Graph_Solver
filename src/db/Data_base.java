package db;

import java.sql.Blob;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Data_base {

	/*
	 * MLD:
	 * 
	 * Map:{Id_Map, Map_name}
	 * 
	 * Composed:{#Id_map,#Id_Vertex}
	 * 
	 * Vertex:{Id_Vertex, Vertex_name}
	 * 
	 * Move:{#Id_Vertex,#Id_Edge}
	 * 
	 * Edge:{Id_Edge, Edge_weight, Edge_description, Edge_audio, #Id_Vertex}
	 */

	public static SQLiteDatabase db;

	public static void init() {
		create_map();
		create_vertex();
		create_edge();
		create_composed();
		create_move();
	}

	public static void drop_all() {
		drop_move();
		drop_composed();
		drop_edge();
		drop_map();
		drop_vertex();
	}

	/*
	 * #------------------------------------------------------------ # Table:
	 * Edge #------------------------------------------------------------
	 */

	public static final String create_edge = "CREATE TABLE IF NOT EXISTS Edge(Id_Edge INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , Edge_weight FLOAT NOT NULL , Edge_description TEXT, Edge_audio TEXT NOT NULL , Id_Vertex NOT NULL, FOREIGN KEY (Id_Vertex) REFERENCES Vertex (Id_Vertex) ON DELETE CASCADE ON UPDATE CASCADE)";

	public static final String drop_edge = "DROP table Edge";

	public static long insert_edge(int curr_Edge_weight,
			String curr_Edge_description, String curr_Edge_audio,
			int asso_Id_Vertex) {
		// String curr = "INSERT INTO Map VALUES('"+curr_Map_name+"');";
		// System.out.println(curr);
		// db.execSQL(curr);
		ContentValues values = new ContentValues();
		values.put("Edge_weight", curr_Edge_weight);
		values.put("Edge_description", curr_Edge_description);
		values.put("Edge_audio", curr_Edge_audio);
		values.put("Id_Vertex", asso_Id_Vertex);
		//System.out.println("INSERT AUDIO = " + curr_Edge_audio);
		return db.insert("Edge", null, values);
	}

	public static void delete_edge(int curr_Id_Edge) {
		String curr = "DELETE from Edge where Id_Edge='" + curr_Id_Edge + "');";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static void drop_edge() {
		// System.out.println(drop_edge);
		db.execSQL(drop_edge);
	}

	public static Cursor select_edge(int curr_Id_Edge) {
		String curr = "SELECT * from Edge where Id_Edge='" + curr_Id_Edge + "'";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}

	public static Cursor select_edge_vertex(int curr_Id_Vertex) {
		String curr = "SELECT * from Edge where Id_Vertex='" + curr_Id_Vertex
				+ "'";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}

	public static Cursor select_edge_all() {
		String curr = "SELECT * from Edge";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}

	public static void update_edge(int curr_Edge_weight,
			String curr_Edge_description, Blob curr_Edge_audio) {
		String curr = "UPDATE Edge SET Edge_weight='" + curr_Edge_weight
				+ "', Edge_description='" + curr_Edge_description
				+ "', Edge_audio='" + curr_Edge_audio + "';";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static void create_edge() {
		// System.out.println(create_edge);
		db.execSQL(create_edge);
	}

	/*
	 * #------------------------------------------------------------ # Table:
	 * Map #------------------------------------------------------------
	 */

	public static final String create_map = "CREATE TABLE IF NOT EXISTS Map (Id_Map INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,Map_name TEXT NOT NULL);";

	public static final String drop_map = "DROP table Map";

	public static long insert_map(String curr_Map_name) {
		// String curr = "INSERT INTO Map VALUES('"+curr_Map_name+"');";
		// System.out.println(curr);
		// db.execSQL(curr);
		ContentValues values = new ContentValues();
		values.put("Map_name", curr_Map_name);
		return db.insert("Map", null, values);
	}

	public static void delete_map(int curr_Id_Map) {
		String curr = "DELETE from Map where Id_Map='" + curr_Id_Map + "');";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static Cursor select_map_id(int curr_Id_Map) {
		String curr = "SELECT * from Map where Id_Map='" + curr_Id_Map + "');";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}

	public static Cursor select_map_all() {
		String curr = "SELECT * from Map;";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}

	public static void update_map(String curr_Map_name) {
		String curr = "UPDATE Map SET Map_name='" + curr_Map_name + "';";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static void drop_map() {
		// System.out.println(drop_map);
		db.execSQL(drop_map);
	}

	public static void create_map() {
		// System.out.println(create_map);
		db.execSQL(create_map);
	}

	/*
	 * #------------------------------------------------------------ # Table:
	 * Vertex #------------------------------------------------------------
	 */

	public static final String create_vertex = "CREATE TABLE IF NOT EXISTS Vertex( Id_Vertex INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , Vertex_name TEXT NOT NULL);";

	public static final String drop_vertex = "DROP table Vertex";

	public static long insert_vertex(String curr_Vertex_name) {
		// String curr = "INSERT INTO Vertex VALUES('"+curr_Vertex_name+"');";
		// System.out.println(curr);
		// db.execSQL(curr);
		ContentValues values = new ContentValues();
		values.put("Vertex_name", curr_Vertex_name);
		return db.insert("Vertex", null, values);
	}

	public static void delete_vertex(int curr_Id_Vertex) {
		String curr = "DELETE from Vertex where Id_Vertex='" + curr_Id_Vertex
				+ "';";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static Cursor select_vertex(int curr_Id_Vertex) {
		String curr = "SELECT * from Vertex where Id_Vertex='" + curr_Id_Vertex
				+ "';";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}

	public static void update_vertex(String curr_Vertex_namet) {
		String curr = "UPDATE Vertex SET Vertex_name='" + curr_Vertex_namet
				+ "';";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static void drop_vertex() {
		// System.out.println(drop_vertex);
		db.execSQL(drop_vertex);
	}

	public static void create_vertex() {
		// System.out.println(create_vertex);
		db.execSQL(create_vertex);
	}

	/*
	 * #------------------------------------------------------------ # Table:
	 * Composed #------------------------------------------------------------
	 */

	public static final String create_composed = "CREATE TABLE IF NOT EXISTS Composed (Id_Map INTEGER NOT NULL ,Id_Vertex INTEGER NOT NULL, PRIMARY KEY (Id_Map , Id_Vertex), FOREIGN KEY(Id_Map) REFERENCES Map(Id_Map), FOREIGN KEY(Id_Vertex) REFERENCES Vertex(Id_Vertex));";;

	public static final String drop_composed = "DROP table Composed";

	public static void create_composed() {
		// System.out.println(create_composed);
		db.execSQL(create_composed);
	}

	public static void drop_composed() {
		// System.out.println(drop_composed);
		db.execSQL(drop_composed);
	}

	public static void insert_composed(long id_map, long id_vertex) {
		String curr = "INSERT INTO Composed VALUES('" + id_map + "','"
				+ id_vertex + "');";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static void drop_composed_map(int curr_Id_Map) {
		String curr = "DELETE from Composed where Id_Map='" + curr_Id_Map
				+ "';";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static void drop_composed_vertex(int curr_Id_Vertex) {
		String curr = "DELETE from Composed where Id_Vertex='" + curr_Id_Vertex
				+ "';";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static Cursor select_composed_map(int curr_Id_Map) {
		String curr = "SELECT * from Composed where Id_Map='" + curr_Id_Map
				+ "';";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}

	public static Cursor select_composed_vertex(int curr_Id_Vertex) {
		String curr = "SELECT * from Composed where Id_Vertex='"
				+ curr_Id_Vertex + "';";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}

	/*
	 * #------------------------------------------------------------ # Table:
	 * Move #------------------------------------------------------------
	 */

	public static final String create_move = "CREATE TABLE IF NOT EXISTS Move (Id_Vertex INTEGER NOT NULL ,Id_Edge INTEGER NOT NULL, PRIMARY KEY (Id_Vertex , Id_Edge), FOREIGN KEY(Id_Vertex) REFERENCES Vertex(Id_Vertex), FOREIGN KEY(Id_Edge) REFERENCES Edge(Id_Edge));";;

	public static final String drop_move = "DROP table Move";

	public static void create_move() {
		// System.out.println(create_move);
		db.execSQL(create_move);
	}

	public static void drop_move() {
		// System.out.println(drop_move);
		db.execSQL(drop_move);
	}

	public static void insert_move(long id_vertex, long id_edge) {
		String curr = "INSERT INTO Move VALUES('" + id_vertex + "','" + id_edge
				+ "');";
		System.out.println(curr);
		db.execSQL(curr);
	}

	public static void drop_move_edge(int curr_Id_Edge) {
		String curr = "DELETE from Move where Id_Edge='" + curr_Id_Edge + "';";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static void drop_move_vertex(int curr_Id_Vertex) {
		String curr = "DELETE from Move where Id_Vertex='" + curr_Id_Vertex
				+ "';";
		// System.out.println(curr);
		db.execSQL(curr);
	}

	public static Cursor select_move_edge(int curr_Id_Edge) {
		String curr = "SELECT * from Move where Id_Edge='" + curr_Id_Edge
				+ "';";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}

	public static Cursor select_move_vertex(int curr_Id_Vertex) {
		String curr = "SELECT * from Move where Id_Vertex='" + curr_Id_Vertex
				+ "';";
		// System.out.println(curr);
		return db.rawQuery(curr, null);
	}
}
