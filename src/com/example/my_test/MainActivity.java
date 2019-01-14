package com.example.my_test;

import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import db.Data_base;
import db.Edge_tmp;
import db.Map_tmp;
import db.Move;
import db.Vertex_tmp;

public class MainActivity extends Activity {

	public final static String EXTRA_MAP_NAME = "com.example.my_test.EXTRA_MAP_NAME";
	public final static String EXTRA_CURR_VERTEX_ID = "com.example.my_test.EXTRA_CURR_VERTEX_ID";
	public final static String EXTRA_HM_VERTEX = "com.example.my_test.EXTRA_HM_VERTEX";
	public static LinkedList<String> vertex_names = new LinkedList<String>();// TODO
																				// CLEAR
																				// IT
	public static LinkedList<Edge_tmp> edges = new LinkedList<Edge_tmp>();
	public static LinkedList<Map_tmp> maps = new LinkedList<Map_tmp>();
	public static LinkedList<Vertex_tmp> vertexs = new LinkedList<Vertex_tmp>();

	public static LinkedList<Move> moves = new LinkedList<Move>();

	public static String map_name = null;
	public static int id_map = -1;
	public static int id_start = -1;
	public static int id_end = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accueil);
		Data_base.db = openOrCreateDatabase("MyDB1", MODE_PRIVATE, null);
		Data_base.init();
		// Data_base.drop_all();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// click create -> map
	public void show_create(View view) {
		Intent intent = new Intent(this, Activity_create_map.class);
		startActivity(intent);
	}

	// click load
	public void show_load(View view) {
		Intent intent = new Intent(this, Activity_load_map.class);
		startActivity(intent);
	}

	// click delete
	public void show_delete(View view) {
		// TODO SHOW ARE YOU SURE
		Data_base.drop_all();
		Data_base.init();
	}

	// record audio for edge
	public void record_audio(View view) {

	}
	// record audio for edge
	public void close(View view) {
		Data_base.db.close();
		System.exit(0);
	}

	public static void clear() {
		// TODO Auto-generated method stub
		vertex_names = new LinkedList<String>();// TODO CLEAR IT
		edges = new LinkedList<Edge_tmp>();
		maps = new LinkedList<Map_tmp>();
		vertexs = new LinkedList<Vertex_tmp>();

		moves = new LinkedList<Move>();

		map_name = null;
		id_map = -1;
		id_start = -1;
		id_end = -1;
	}

	// TODO prevempt creation with empty fields
}
