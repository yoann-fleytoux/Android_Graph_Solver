package com.example.my_test;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import db.Edge_tmp;

public class Activity_create_edge extends Activity {

	public final static String EXTRA_RECORD_FILE = "com.example.my_test.EXTRA_RECORD_FILE";
	public final static String EXTRA_LENGHT_EDGE = "com.example.my_test.EXTRA_LENGHT_EDGE";
	public final static String EXTRA_DESCRIPTION_EDGE = "com.example.my_test.EXTRA_DESCRIPTION_EDGE";
	public final static String EXTRA_CURR_ASSOCIATED_VERTEX_ID = "com.example.my_test.EXTRA_CURR_ASSOCIATED_VERTEX_ID";

	// TODO DELETE UNSAVED AUDIO FILES

	private static final String LOG_TAG = "AudioRecordTest";
	private static String mFileName = null;

	private RecordButton mRecordButton = null;
	private MediaRecorder mRecorder = null;

	private PlayButton mPlayButton = null;
	private MediaPlayer mPlayer = null;

	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	private void startRecording() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}

		mRecorder.start();
	}

	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	class RecordButton extends Button {
		boolean mStartRecording = true;

		OnClickListener clicker = new OnClickListener() {
			@Override
			public void onClick(View v) {
				onRecord(mStartRecording);
				if (mStartRecording) {
					setText("Arreter\nEnregistrement");
				} else {
					setText("Commencer\nEnregistrement");
				}
				mStartRecording = !mStartRecording;
			}
		};

		public RecordButton(Context ctx) {
			super(ctx);
			setText("Commencer\nEnregistrement");
			setOnClickListener(clicker);
		}
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

	@Override
	public void onPause() {
		super.onPause();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_edge);
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

	// TODO NOT SHOW EDGE ALREADY DONE
	public void show_list_after_create_edge(View view) {
		Intent intent = getIntent();
		EditText edge_lenght = (EditText) findViewById(R.id.Edit_edge_lenght);
		EditText edge_description = (EditText) findViewById(R.id.EditText_vertex_name);
		String str_edge_lenght = edge_lenght.getText().toString();
		String str_edge_description = edge_description.getText().toString();
		if (!edge_lenght.getText().toString()
				.equals(getResources().getString(R.string.string_edge_lenght))) {
			intent.putExtra(EXTRA_LENGHT_EDGE, str_edge_lenght);
		}
		if (!edge_description
				.getText()
				.toString()
				.equals(getResources().getString(
						R.string.string_enter_edge_description))) {
			intent.putExtra(EXTRA_DESCRIPTION_EDGE, str_edge_description);
		}
		TableLayout tableLayout = new TableLayout(getApplicationContext());
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
		/*
		 * int t[MainActivity.edges.size()][2]; for (Integer i = 0; i <
		 * MainActivity.edges.size(); i++){
		 * 
		 * }
		 */
		for (Integer j = 0; j < MainActivity.vertex_names.size(); j++) {
			if (j != Integer.parseInt(intent
					.getStringExtra(MainActivity.EXTRA_CURR_VERTEX_ID))) {
				tableRow = new TableRow(getApplicationContext());
				textView1 = new TextView(getApplicationContext());
				textView1.setText(MainActivity.vertex_names.get(j));
				textView1.setPadding(20, 20, 20, 20);

				test = new Button(getApplicationContext());
				test.setText("Asoccier");
				test.setId(j);
				test.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = getIntent();
						String int_curr_id = Integer.toString(view.getId());
						intent.putExtra(EXTRA_CURR_ASSOCIATED_VERTEX_ID,
								int_curr_id);
						setContentView(R.layout.activity_create_edge);

						EditText edge_lenght = (EditText) findViewById(R.id.Edit_edge_lenght);
						EditText edge_description = (EditText) findViewById(R.id.EditText_vertex_name);
						Button button_wich_vertex = (Button) findViewById(R.id.button_wich_vertex);

						if (!intent.getStringExtra(EXTRA_LENGHT_EDGE).equals(
								"com.example.my_test.EXTRA_LENGHT_EDGE")) {
							edge_lenght.setText(intent
									.getStringExtra(EXTRA_LENGHT_EDGE));
						}
						if (!intent
								.getStringExtra(EXTRA_DESCRIPTION_EDGE)
								.equals("com.example.my_test.EXTRA_DESCRIPTION_EDGE")) {
							edge_description.setText(intent
									.getStringExtra(EXTRA_DESCRIPTION_EDGE));
						}
						if (!intent
								.getStringExtra(EXTRA_CURR_ASSOCIATED_VERTEX_ID)
								.equals("com.example.my_test.EXTRA_CURR_ASSOCIATED_VERTEX_ID")) {
							button_wich_vertex.setText(MainActivity.vertex_names.get(Integer.parseInt(intent
									.getStringExtra(EXTRA_CURR_ASSOCIATED_VERTEX_ID))));
						}
					}
				});
				tableRow.addView(textView1);
				tableRow.addView(test);
				tableLayout.addView(tableRow);
			}
		}
		setContentView(tableLayout);
	}

	public void cancel(View view) {
		finish();
	}

	public void record_audio(View view) {
		Intent intent = getIntent();
		EditText edge_lenght = (EditText) findViewById(R.id.Edit_edge_lenght);
		EditText edge_description = (EditText) findViewById(R.id.EditText_vertex_name);
		String str_edge_lenght = edge_lenght.getText().toString();
		String str_edge_description = edge_description.getText().toString();
		if (!edge_lenght.getText().toString()
				.equals(getResources().getString(R.string.string_edge_lenght))) {
			intent.putExtra(EXTRA_LENGHT_EDGE, str_edge_lenght);
		}
		if (!edge_description
				.getText()
				.toString()
				.equals(getResources().getString(
						R.string.string_enter_edge_description))) {
			intent.putExtra(EXTRA_DESCRIPTION_EDGE, str_edge_description);
		}

		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		// mFileName +=
		// "/"+MainActivity.vertex_names.get(Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_CURR_VERTEX_ID)))+"_"+MainActivity.vertex_names.get(Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_CURR_ASSOCIATED_VERTEX_ID)))+"_"+RandomStringUtils.randomAlphanumeric(8)+".3gp";
		String ext = "3gp";

		String name = String.format("%s.%s",
				RandomStringUtils.randomAlphanumeric(8), ext);
		mFileName += "/" + name;

		LinearLayout ll = new LinearLayout(this);
		mRecordButton = new RecordButton(this);
		ll.addView(mRecordButton, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		mPlayButton = new PlayButton(this);
		ll.addView(mPlayButton, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		Button test = new Button(getApplicationContext());
		test.setText("Fin");
		test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = getIntent();
				intent.putExtra(EXTRA_RECORD_FILE, mFileName);
				setContentView(R.layout.activity_create_edge);

				EditText edge_lenght = (EditText) findViewById(R.id.Edit_edge_lenght);
				EditText edge_description = (EditText) findViewById(R.id.EditText_vertex_name);
				Button button_wich_vertex = (Button) findViewById(R.id.button_wich_vertex);

				if (!intent.getStringExtra(EXTRA_LENGHT_EDGE).equals(
						"com.example.my_test.EXTRA_LENGHT_EDGE")) {
					edge_lenght.setText(intent
							.getStringExtra(EXTRA_LENGHT_EDGE));
				}
				if (!intent.getStringExtra(EXTRA_DESCRIPTION_EDGE).equals(
						"com.example.my_test.EXTRA_DESCRIPTION_EDGE")) {
					edge_description.setText(intent
							.getStringExtra(EXTRA_DESCRIPTION_EDGE));
				}
				if (!intent
						.getStringExtra(EXTRA_CURR_ASSOCIATED_VERTEX_ID)
						.equals("com.example.my_test.EXTRA_CURR_ASSOCIATED_VERTEX_ID")) {
					button_wich_vertex.setText(MainActivity.vertex_names.get(Integer.parseInt(intent
							.getStringExtra(EXTRA_CURR_ASSOCIATED_VERTEX_ID))));
				}

			}
		});
		ll.addView(test, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		setContentView(ll);
	}

	public void show_accueil(View view) {
		finish();
	}

	/*
	 * enregistrer les edit text en EXTRA
	 */
	public void end_creation_edge(View view) {
		boolean valide = true;
		Intent intent = getIntent();

		int lenght = -1;
		int curr_vertex = -1;
		String description = "empty";
		String audio = "empty";

		EditText edge_lenght = (EditText) findViewById(R.id.Edit_edge_lenght);
		EditText edge_description = (EditText) findViewById(R.id.EditText_vertex_name);
		String str_edge_lenght = edge_lenght.getText().toString();
		String str_edge_description = edge_description.getText().toString();
		if (!edge_lenght.getText().toString()
				.equals(getResources().getString(R.string.string_edge_lenght))) {
			intent.putExtra(EXTRA_LENGHT_EDGE, str_edge_lenght);
		}
		if (!edge_description
				.getText()
				.toString()
				.equals(getResources().getString(
						R.string.string_enter_edge_description))) {
			intent.putExtra(EXTRA_DESCRIPTION_EDGE, str_edge_description);
		}

		if (!intent.getStringExtra(EXTRA_LENGHT_EDGE).equals(
				"com.example.my_test.EXTRA_LENGHT_EDGE")) {
			lenght = Integer.parseInt(intent.getStringExtra(EXTRA_LENGHT_EDGE));
		} else {
			valide = false;
		}
		if (!intent.getStringExtra(EXTRA_DESCRIPTION_EDGE).equals(
				"com.example.my_test.EXTRA_DESCRIPTION_EDGE")) {
			description = intent.getStringExtra(EXTRA_DESCRIPTION_EDGE);
		}
		if (!intent.getStringExtra(EXTRA_CURR_ASSOCIATED_VERTEX_ID).equals(
				"com.example.my_test.EXTRA_CURR_ASSOCIATED_VERTEX_ID")) {
			curr_vertex = Integer.parseInt(intent
					.getStringExtra(EXTRA_CURR_ASSOCIATED_VERTEX_ID));
		} else {
			valide = false;
		}
		if (!intent.getStringExtra(EXTRA_RECORD_FILE).equals(
				"com.example.my_test.EXTRA_RECORD_FILE")) {
			audio = intent.getStringExtra(EXTRA_RECORD_FILE);
		} else {
			valide = false;
		}
		// System.out.println("Id curr = "+Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_CURR_VERTEX_ID))+" Id asso = "+curr_vertex+" Edge lenght "+lenght+" Edge description: "+description+" audio: "+audio);

		if (valide) {
			MainActivity.edges.add(new Edge_tmp(Integer.parseInt(intent
					.getStringExtra(MainActivity.EXTRA_CURR_VERTEX_ID)),
					curr_vertex, lenght, description, audio));
			finish();
		}// TODO show promt error try again
	}

}
