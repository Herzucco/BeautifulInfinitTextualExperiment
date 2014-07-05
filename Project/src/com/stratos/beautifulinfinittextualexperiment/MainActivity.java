package com.stratos.beautifulinfinittextualexperiment;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.api.ConnectionService;
import com.api.SignUpService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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
	private Button signUpButton;
	private Button eraseButton;
	private Intent intent;
	private MainActivity context = this;
	private String username;
	private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	buttonClick();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    
	public void onBackPressed() {
	    super.onBackPressed();
		overridePendingTransition(R.anim.swipe_left_in, R.anim.swipe_left_out);
	}
    
    public void buttonClick()
    {
    	usernameField = (EditText) findViewById(R.id.editText1);
    	passwordField = (EditText) findViewById(R.id.editText2);
    	myTextView = (TextView) findViewById(R.id.textView2);
    	eraseButton = (Button) findViewById(R.id.button1);
    	sendButton = (Button) findViewById(R.id.button2);
    	signUpButton = (Button) findViewById(R.id.button3);

    	
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
				username = usernameField.getText().toString();
				password = passwordField.getText().toString();
				SignInTask task = new SignInTask();
				task.execute();
			}
		});
    	
    	signUpButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent (context, SignUpActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.swipe_left_in, R.anim.swipe_left_out);
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

    public class SignInTask extends android.os.AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPostExecute(Boolean result) {
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			System.out.println(ConnectionService.connect(username, password));
			intent = new Intent (context, ListActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.swipe_left_in, R.anim.swipe_left_out);
			// TODO Auto-generated method stub
			return null;
		}
    	
    }  
    
}