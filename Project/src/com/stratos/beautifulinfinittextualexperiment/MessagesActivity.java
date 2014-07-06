package com.stratos.beautifulinfinittextualexperiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import com.api.AppUser;
import com.api.ConnectionService;
import com.api.Message;
import com.api.MessageService;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.os.Build;

public class MessagesActivity extends Activity {
	
	private ListView mainListView;
	private ArrayAdapter<String> listAdapter;
	private EditText sendMessage;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate (savedInstanceState);
		setContentView(R.layout.activity_messages);
		mainListView = (ListView) findViewById(R.id.listView1);
		sendMessage = (EditText) findViewById(R.id.sendMessage);
		
		listAdapter = MessageService.displayableList;
		mainListView.setAdapter(listAdapter);
		
		mainListView.setSelection(listAdapter.getCount() -1);
		
		sendMessage.setOnEditorActionListener(new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        boolean handled = false;
		        if (actionId == EditorInfo.IME_ACTION_SEND) {
		        	SendMessageTask sender = new SendMessageTask();
		        	sender.execute();
		        }
		        return handled;
		    }

		});
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new GetMessages(), 0L, 2000L);
	}
	
	public class SendMessageTask extends android.os.AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			//todo : add spinning
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			Message msg = new Message(AppUser.getInstance().getName(), sendMessage.getText().toString());
			Log.i("[Message sending]", msg.getFormatedContent());
		    msg.send();
		    return true;
		}
    	
		@Override
		protected void onPostExecute(Boolean result){
			sendMessage.setText("");
			sendMessage.setCursorVisible(false);
		}
    }  
	
	public void onBackPressed() {
	    super.onBackPressed();
		overridePendingTransition(R.anim.swipe_left_in, R.anim.swipe_left_out);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
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
	
	public class GetMessagesTask extends android.os.AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			//todo : add spinning
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			MessageService.getList();
			return true;
		}
    	
		@Override
		protected void onPostExecute(Boolean result){
			int baseSize = listAdapter.getCount();
			for(int i = listAdapter.getCount(); i < MessageService.messageList.size(); i++){
				Message msg = MessageService.messageList.get(i);
				if(msg.isFromBITE() && msg.isNotDisplayed()){
					listAdapter.add(msg.getFormatedContent());
					msg.setNotDisplayed(false);
				}
			}
			if(baseSize < listAdapter.getCount()){
				mainListView.setSelection(listAdapter.getCount() -1);
			}
		}
    }  
	
	private class GetMessages extends TimerTask{
		public void run() {
			GetMessagesTask task = new GetMessagesTask();
			task.execute();
		}
	}
}
