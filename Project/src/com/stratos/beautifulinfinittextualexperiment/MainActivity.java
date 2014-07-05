package com.stratos.beautifulinfinittextualexperiment;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String TAG = MainActivity.class.getSimpleName();
	private EditText usernameField;
	private EditText passwordField;
	private TextView myTextView;
	private Button sendButton;
	private Button eraseButton;
	private Intent intent;
	private MainActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	buttonClick();
    }
    
    public void buttonClick()
    {
    	usernameField = (EditText) findViewById(R.id.editText1);
    	passwordField = (EditText) findViewById(R.id.editText2);
    	myTextView = (TextView) findViewById(R.id.textView2);
    	eraseButton = (Button) findViewById(R.id.button1);
    	sendButton = (Button) findViewById(R.id.button2);

    	
    	eraseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				usernameField.setText("");
				passwordField.setText("");
			}
		});    

    	sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "TOAST AU BEURRE!", Toast.LENGTH_SHORT).show();
				TestTask task = new TestTask();
				task.execute();
			}
		});    
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	Log.i(TAG, "Bails Pause");
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	Log.i(TAG, "Bails Resume");
    	super.onResume();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onRestoreInstanceState(savedInstanceState);
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    }

    public class TestTask extends android.os.AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPostExecute(Boolean result) {
			intent = new Intent (context, ListActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.activity_switch, R.anim.activity_out);
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }  
    
}