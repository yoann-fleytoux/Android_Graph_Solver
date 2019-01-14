package com.example.my_test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Activity_create_vertex extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_vertex);
		Intent intent = getIntent();
		TextView hm_veretx_left= (TextView) findViewById(R.id.hm_vertex_left);
		hm_veretx_left.setText(intent.getStringExtra(MainActivity.EXTRA_HM_VERTEX));
		hm_veretx_left.setTextColor(Color.RED);
		hm_veretx_left.setTypeface(null, Typeface.BOLD);
		hm_veretx_left.setPadding(20, 20, 20, 20);
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
	
	public void show_accueil(View view)
	{
		MainActivity.vertex_names.clear();
		MainActivity.edges.clear();
		finish();
	}
	
	//click hm_vertex
		public void deal_vertex(View view)
		{
			Intent intent = getIntent();
			EditText name_vertex = (EditText) findViewById(R.id.EditText_vertex_name);
			boolean valide = true;
			if(!name_vertex.getText().toString().equals(getResources().getString(R.string.string_enter_vertex_name))){
				MainActivity.vertex_names.add(name_vertex.getText().toString());
			}else{
				valide = false;
			}
			if(valide){
				String int_hm_vertex= intent.getStringExtra(MainActivity.EXTRA_HM_VERTEX);
				int_hm_vertex= Integer.toString(Integer.parseInt(int_hm_vertex)-1);
				intent.putExtra(MainActivity.EXTRA_HM_VERTEX, int_hm_vertex);
				
				//if every vertex were created show end creation
				if(Integer.parseInt(intent.getStringExtra(MainActivity.EXTRA_HM_VERTEX)) == 0){
					intent =new Intent(this, Activity_end_creation.class);
					//System.out.println("create vertex  ; Map name = "+intent.getStringExtra((MainActivity.EXTRA_MAP_NAME)));
					startActivity(intent);
					finish();
				}else{
					//else show create vertex, reduce count vertex
					setContentView(R.layout.activity_create_vertex);
					TextView hm_veretx_left= (TextView) findViewById(R.id.hm_vertex_left);
					hm_veretx_left.setText(intent.getStringExtra(MainActivity.EXTRA_HM_VERTEX));
					hm_veretx_left.setTextColor(Color.RED);
					hm_veretx_left.setTypeface(null, Typeface.BOLD);
					hm_veretx_left.setPadding(20, 20, 20, 20);
				}
			}
		}
}
