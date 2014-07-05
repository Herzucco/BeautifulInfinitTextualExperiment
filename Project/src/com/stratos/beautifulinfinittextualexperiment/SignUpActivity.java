package com.stratos.beautifulinfinittextualexperiment;

import com.api.SignUpService;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class SignUpActivity extends Activity {
	
	private EditText usernameField;
	private EditText passwordField;
	private Button sendButton;
	private SignUpActivity context = this;
	private String username;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		buttonClick();
	}

    public void buttonClick()
    {
    	usernameField = (EditText) findViewById(R.id.editText1);
    	passwordField = (EditText) findViewById(R.id.editText2);
    	sendButton = (Button) findViewById(R.id.button2);
    	
    	sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				username = usernameField.getText().toString();
				password = passwordField.getText().toString();
				SignUpTask task = new SignUpTask();
				task.execute();
			}
		});

    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
	
    public class SignUpTask extends android.os.AsyncTask<String, Void, Boolean> {
		@Override
		protected void onPostExecute(Boolean result) {
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			SignUpService.signUp(username, password);
			return null;
		}
    	
    }
}
