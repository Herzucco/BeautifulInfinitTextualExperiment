package com.stratos.beautifulinfinittextualexperiment;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.os.Build;

import com.stratos.beautifulinfinittextualexperiment.CustomAdapter;

public class ListActivity extends Activity {
	
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate (savedInstanceState);
		setContentView(R.layout.activity_messages);
		mainListView = (ListView) findViewById(R.id.listView1);
		String[] bails = new String[] { "Bails", "Jeej", "Suus", "Squalala", "( ͡° ͜ʖ ͡°)", "Loulou"};
		ArrayList<String> bailsList = new ArrayList<String>();
		bailsList.addAll(Arrays.asList(bails));
		
		listAdapter = new CustomAdapter(this, R.layout.simplerow, bailsList);
		listAdapter.add("uidfg");
		listAdapter.add("sdfgf");
		listAdapter.add("azesw");
		listAdapter.add("ntyyhnb");
		listAdapter.add("dsfgnbhn");
		listAdapter.add("dsfvd");
		listAdapter.add("cvbgtrf");
		listAdapter.add("sfbxbf");
		listAdapter.add("sgfbgr");
		listAdapter.add("rgbffgbv");
		listAdapter.add("fgbhthbn");
		listAdapter.add("erfdv");
		listAdapter.add("sdfvfre");
		listAdapter.add("fdbgfb");
		listAdapter.add("xcvdcbs");
		listAdapter.add("rzbngf");
		listAdapter.add("sdfdfb");
		mainListView.setAdapter(listAdapter);
		
		mainListView.setSelection(listAdapter.getCount() -1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}
	
	public void onBackPressed() {
	    super.onBackPressed();
		overridePendingTransition(R.anim.swipe_left_in, R.anim.swipe_left_out);
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

}
