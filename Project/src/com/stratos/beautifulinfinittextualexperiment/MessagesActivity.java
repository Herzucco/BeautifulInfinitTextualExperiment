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
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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
	private CustomArrayAdapter listAdapter;
	private EditText sendMessage;
	private TextView welcomeText;
	private Activity context;
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate (savedInstanceState);
		
		NotificationManager mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancelAll();
		
		setContentView(R.layout.activity_messages);
		mainListView = (ListView) findViewById(R.id.listView1);
		sendMessage = (EditText) findViewById(R.id.sendMessage);
		welcomeText = (TextView) findViewById(R.id.welcomeText);
		
		welcomeText.setText(AppUser.getInstance().getName());
		
		String hex = CustomArrayAdapter.toHex(AppUser.getInstance().getName());
        hex = hex.substring(hex.length()-6);
        
        int color = CustomArrayAdapter.stringToColor("#"+hex);
        
        context = this;
        
        welcomeText.setBackgroundColor(color-4000000);
		listAdapter = new CustomArrayAdapter(getApplicationContext(), R.id.tvItemTitle, MessageService.messageList);
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
		String text;
		Message msg;
		@Override
		protected void onPreExecute() {
			//todo : add spinning
			text = sendMessage.getText().toString();
			sendMessage.setText("");
			msg = new Message(AppUser.getInstance().getName(), text);
			MessageService.messageList.add(msg);
			mainListView.setSelection(listAdapter.getCount() -1);
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			//Message msg = new Message(AppUser.getInstance().getName(), text);
			Log.i("[Message sending]", msg.getFormatedContent());
		    msg.send();
		    return true;
		}
    	
		@Override
		protected void onPostExecute(Boolean result){
			
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
		int baseSize;
		@Override
		protected void onPreExecute() {
			//todo : add spinning
			baseSize = listAdapter.getCount();
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			MessageService.getList();
			return true;
		}
    	
		@Override
		protected void onPostExecute(Boolean result){
			MessageService.parseString(MessageService.getContentStr());
			Log.i("[oejfoejf]", String.valueOf(baseSize)+ " --- "+String.valueOf(listAdapter.getCount()));
			if(baseSize < listAdapter.getCount()){
				Message last = listAdapter.getItem(listAdapter.getCount() -1);
				mainListView.setSelection(listAdapter.getCount() -1);
				
				if(!AppUser.getInstance().getName().equals(last.getAuthor())){
					sendNotification(last.getAuthor(), last.getContent());
				}
			}
			for(int i = 0; i < listAdapter.getCount(); i++){
				Message msg = listAdapter.getItem(i);
				if(msg.edition != null){
					msg.edit();
				}
			}
		}
		
		private void sendNotification(String author, String content){
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(context)
			        .setSmallIcon(R.drawable.ic_notif)
			        .setContentTitle(author+" sent a message :")
			        .setLights(0xffff69b4, 300, 100)
			        .setAutoCancel(true)
			        .setContentText(content);
			
			Intent resultIntent = new Intent(context, MessagesActivity.class);
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(MessagesActivity.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent =
			        stackBuilder.getPendingIntent(
			            0,
			            PendingIntent.FLAG_UPDATE_CURRENT
			        );
			mBuilder.setContentIntent(resultPendingIntent);
			NotificationManager mNotificationManager =
				    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(0, mBuilder.build());
		}
    }  
	
	private class GetMessages extends TimerTask{
		public void run() {
			GetMessagesTask task = new GetMessagesTask();
			task.execute();
		}
	}
}
