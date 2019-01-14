package com.example.my_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Activity_hm_veretx extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hm_vertex);
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
	
	public void show_create_vertex(View view)
	{
		Intent intent =new Intent(this, Activity_create_vertex.class);	
		EditText hm_veretx = (EditText) findViewById(R.id.Edit_nb_vertex);
		boolean valide = true;
		if(!hm_veretx.getText().toString().equals(getResources().getString(R.string.string_hm_vertex))){
			String int_hm_vertex = hm_veretx.getText().toString();
			intent.putExtra(MainActivity.EXTRA_HM_VERTEX, int_hm_vertex);
		}else{
			valide = false;
		}
		if(valide){
			//System.out.println("hm vertex ; Map name = "+intent.getStringExtra((MainActivity.EXTRA_MAP_NAME)));
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
