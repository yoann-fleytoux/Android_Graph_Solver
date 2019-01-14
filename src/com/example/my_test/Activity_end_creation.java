package com.example.my_test;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import db.Data_base;

public class Activity_end_creation extends Activity {

	@SuppressLint("UseSparseArrays")
	public static Map<Integer, Integer> vertex_id_to_real_vertex_id = new HashMap<Integer, Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView_end(R.layout.activity_end_creation);
	}

	void setContentView_end(int in) {
		setContentView(in);
		TableLayout tableLayout = (TableLayout) findViewById(R.id.Table_Layout_show_vertex);
		tableLayout.setVerticalScrollBarEnabled(true);
		TableRow tableRow;
		TextView textView, textView1;

		Button test;

		tableRow = new TableRow(getApplicationContext());
		textView = new TextView(getApplicationContext());
		textView.setText("Nom Destination");
		textView.setTextColor(Color.RED);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setPadding(20, 20, 20, 20);
		tableRow.addView(textView);
		tableLayout.addView(tableRow);
		for (Integer j = 0; j < MainActivity.vertex_names.size(); j++) {
			tableRow = new TableRow(getApplicationContext());
			textView1 = new TextView(getApplicationContext());
			textView1.setText(MainActivity.vertex_names.get(j));
			textView1.setPadding(20, 20, 20, 20);

			test = new Button(getApplicationContext());
			test.setText("Créer le chemin");
			test.setId(j);
			test.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					show_create_edge(view);
				}
			});
			tableRow.addView(textView1);
			tableRow.addView(test);
			tableLayout.addView(tableRow);
		}

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

	// TODO
	public void SAVE_BACK_ACCEUIL(View view) {
		// Intent intent = getIntent();

		/*
		 * System.out.println("Map name = "+intent.getStringExtra((MainActivity.
		 * EXTRA_MAP_NAME)));
		 * System.out.println("Curr = "+intent.getStringExtra(
		 * (MainActivity.EXTRA_CURR_VERTEX_ID)));
		 * System.out.println("hm = "+intent
		 * .getStringExtra((MainActivity.EXTRA_HM_VERTEX)));
		 */
		// System.out.println("Map name = "+MainActivity.map_name);

		long id_map = Data_base.insert_map(MainActivity.map_name);

		// TODO RECUPERER LES ID DES VERETX CREER ET REMPLACER CURR VERTEX ID
		// and asso!!!
		for (int i = 0; i < MainActivity.vertex_names.size(); i++) {
			long id_vertex = Data_base.insert_vertex(MainActivity.vertex_names
					.get(i));
			vertex_id_to_real_vertex_id.put(i, (int) id_vertex);
			Data_base.insert_composed(id_map, id_vertex);
		}

		for (int j = 0; j < MainActivity.edges.size(); j++) {
			long id_edge = Data_base
					.insert_edge(MainActivity.edges.get(j).edge_lenght,
							MainActivity.edges.get(j).description,
							MainActivity.edges.get(j).audio,
							vertex_id_to_real_vertex_id.get(MainActivity.edges
									.get(j).ASSOCIATED_VERTEX_ID));
			Data_base.insert_move(vertex_id_to_real_vertex_id
					.get(MainActivity.edges.get(j).CURR_VERTEX_ID), id_edge);
		}
		// clear at the end
		MainActivity.edges.clear();
		MainActivity.vertex_names.clear();
		finish();
	}

	public void show_accueil(View view) {
		MainActivity.edges.clear();
		MainActivity.vertex_names.clear();
		finish();
	}

	public void show_create_edge(View view) {
		Intent intent = new Intent(this, Activity_create_edge.class);
		String int_curr_id = Integer.toString(view.getId());
		intent.putExtra(MainActivity.EXTRA_CURR_VERTEX_ID, int_curr_id);
		startActivity(intent);
	}

}
