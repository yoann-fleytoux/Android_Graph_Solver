package com.example.my_test;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import db.Data_base;
import db.Map_tmp;
import disjkatra.Map_Disj;

public class Activity_load_map extends Activity {

	private static final String LOG_TAG = "AudioPlayTest";
	private PlayButton mPlayButton = null;
	private MediaPlayer mPlayer = null;

	private static String mFileName = null;
	private int moves_done = 0;
	private int move_id = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MainActivity.clear();
		super.onCreate(savedInstanceState);
		setContentView_end(R.layout.activity_load_map);
	}

	void setContentView_end(int in) {
		setContentView(in);
		TableLayout tableLayout = (TableLayout) findViewById(R.id.Table_Layout_load_map);
		tableLayout.setVerticalScrollBarEnabled(true);
		TableRow tableRow;
		TextView textView, textView1;

		Button test;

		tableRow = new TableRow(getApplicationContext());
		textView = new TextView(getApplicationContext());
		textView.setText("Nom Carte");
		textView.setTextColor(Color.RED);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setPadding(20, 20, 20, 20);
		tableRow.addView(textView);
		tableLayout.addView(tableRow);
		Cursor c = Data_base.select_map_all();

		if (c != null) {
			int count = c.getCount();
			// System.out.println(count);
			c.moveToFirst();
			for (Integer j = 0; j < count; j++) {
				MainActivity.maps.add(new Map_tmp(c.getInt(c
						.getColumnIndex("Id_Map")), c.getString(c
						.getColumnIndex("Map_name"))));
				tableRow = new TableRow(getApplicationContext());
				textView1 = new TextView(getApplicationContext());
				textView1.setText(MainActivity.maps.get(j).Map_name);
				textView1.setPadding(20, 20, 20, 20);

				test = new Button(getApplicationContext());
				test.setText("OK");
				test.setId(MainActivity.maps.get(j).Id_map);
				test.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						show_choose_road(view);
					}
				});
				tableRow.addView(textView1);
				tableRow.addView(test);
				tableLayout.addView(tableRow);
				c.moveToNext();
			}
			c.close();
		} else
			System.out.println("cursor empty load");
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

	public void show_accueil(View view) {
		MainActivity.clear();
		finish();
	}

	public void show_choose_road(View view) {
		MainActivity.id_map = view.getId();
		show_vertex_start(view);
	}

	public void show_vertex_start(View view) {
		new Map_Disj();
		// Map_Disj.compute_possibilities();
		TableLayout tableLayout = new TableLayout(getApplicationContext());
		tableLayout.setVerticalScrollBarEnabled(true);
		TableRow tableRow;
		TextView textView, textView1;

		Button test;

		tableRow = new TableRow(getApplicationContext());
		textView = new TextView(getApplicationContext());
		textView.setText("Nom Départ");
		textView.setTextColor(Color.RED);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setPadding(20, 20, 20, 20);
		tableRow.addView(textView);
		tableLayout.addView(tableRow);

		for (Integer j = 0; j < Map_Disj.map_hard.size(); j++) {
			tableRow = new TableRow(getApplicationContext());
			textView1 = new TextView(getApplicationContext());
			textView1.setText(Map_Disj.map_hard.get(j).Vertex_name);
			textView1.setPadding(20, 20, 20, 20);

			test = new Button(getApplicationContext());
			test.setText("OK");
			test.setId(Map_Disj.map_hard.get(j).Id_Vertex);
			test.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					show_vertex_end(view);
				}
			});
			tableRow.addView(textView1);
			tableRow.addView(test);
			tableLayout.addView(tableRow);
		}
		setContentView(tableLayout);

	}

	public void show_vertex_end(View view) {
		MainActivity.id_start = view.getId();
		Map_Disj.compute_possibilities_bis();
		TableLayout tableLayout = new TableLayout(getApplicationContext());
		tableLayout.setVerticalScrollBarEnabled(true);
		TableRow tableRow;
		TextView textView, textView1;

		Button test;

		tableRow = new TableRow(getApplicationContext());
		textView = new TextView(getApplicationContext());
		textView.setText("Nom Arrivé");
		textView.setTextColor(Color.RED);
		textView.setTypeface(null, Typeface.BOLD);
		textView.setPadding(20, 20, 20, 20);
		tableRow.addView(textView);
		tableLayout.addView(tableRow);
		for (Integer j = 0; j < MainActivity.moves.size(); j++) {
			tableRow = new TableRow(getApplicationContext());
			textView1 = new TextView(getApplicationContext());
			textView1.setText(MainActivity.moves.get(j).name_end);
			textView1.setPadding(20, 20, 20, 20);

			test = new Button(getApplicationContext());
			test.setText("OK");
			test.setId(j);
			test.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					setContentView(R.layout.activity_move);
					// setVolumeControlStream(AudioManager.STREAM_MUSIC);
					TableLayout curr_layout = (TableLayout) findViewById(R.id.TableLayout_move);
					move_id = view.getId();
					// System.out.println("TAILLE MOVES ="+
					// MainActivity.moves.get(move_id).move.size());
					mFileName = MainActivity.moves.get(move_id).move
							.get(moves_done).audio;
					mPlayButton = new PlayButton(getApplicationContext());
					mPlayButton.setLayoutParams(findViewById(
							R.id.button_next_move).getLayoutParams());
					curr_layout.addView(mPlayButton);
				}
			});
			tableRow.addView(textView1);
			tableRow.addView(test);
			tableLayout.addView(tableRow);
		}
		setContentView(tableLayout);

	}

	class PlayButton extends Button {
		boolean mStartPlaying = true;

		OnClickListener clicker = new OnClickListener() {
			@Override
			public void onClick(View v) {
				onPlay(mStartPlaying);
				if (mStartPlaying) {
					setText("Arreter\nl'écoute");
				} else {
					setText("Commencer\nl'écoute");
				}
				mStartPlaying = !mStartPlaying;
			}
		};

		public PlayButton(Context ctx) {
			super(ctx);
			setText("Commencer\nl'écoute");
			setOnClickListener(clicker);
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			// System.out.println(mFileName);
			mPlayer.setDataSource(mFileName);
			// mPlayer.setDataSource(getApplicationContext(),
			// Uri.parse(mFileName));
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	public void next_move(View view) {
		moves_done++;
		if (moves_done == MainActivity.moves.get(move_id).move.size())
			show_accueil(view);
		else {
			mFileName = MainActivity.moves.get(move_id).move.get(moves_done).audio;
			mPlayButton.setText("Commencer\nl'écoute");
		}
	}
}
