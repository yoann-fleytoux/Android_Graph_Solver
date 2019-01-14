package com.example.my_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Activity_create_map extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MainActivity.clear();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_map);
	
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
	
	//click hm_vertex
	public void show_hm_vertex(View view)
	{
		Intent intent =new Intent(this, Activity_hm_veretx.class);
		EditText map_name = (EditText) findViewById(R.id.EditText_vertex_name);
		boolean valide = true;
		if(!map_name.getText().toString().equals(getResources().getString(R.string.string_enter_map_name))){
			String str_map_name = map_name.getText().toString();
			intent.putExtra(MainActivity.EXTRA_MAP_NAME, str_map_name);
			//System.out.println("create map ; Map name = "+intent.getStringExtra((MainActivity.EXTRA_MAP_NAME)));
			MainActivity.map_name=intent.getStringExtra((MainActivity.EXTRA_MAP_NAME));
		}else{
			valide = false;
		}
		
		if(valide){
			startActivity(intent);
			finish();
		}
		
	}
	
	public void show_accueil(View view)
	{
		MainActivity.vertex_names.clear();
		MainActivity.edges.clear();
		finish();
	}
	
}
