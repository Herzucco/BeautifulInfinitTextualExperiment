package com.stratos.beautifulinfinittextualexperiment;

import com.api.SignUpService;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class SignUpActivity extends Activity {
	
	private EditText usernameField;
	private EditText passwordField;
	private Button sendButton;
    private Intent intent;
	private SignUpActivity context = this;
	private String username;
	private String password;
    private int color1, color2, red1, red2, blue1, blue2, green1, green2;
    View v;
    ObjectAnimator anim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		buttonClick();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        
        color1 = 0xffffffff;
        v = findViewById(R.id.textView1);
        
        anim = ObjectAnimator.ofInt(v, "backgroundColor", color1);
        anim.setEvaluator(new ArgbEvaluator());
        
        anim.setDuration(1000);
        
        new Thread() {
        	public void run(){
        		while(true){
        			try {
        				Thread.sleep(1000);
        			} catch (InterruptedException e) {
        				e.printStackTrace();
        			}
        			SignUpActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							red2 = (int)(Math.random() * 128 + 127);
                            green2 = (int)(Math.random() * 128 + 127);
                            blue2 = (int)(Math.random() * 128 + 127);
                            color2 = 0xff << 24 | (red2 << 16) |
                                    (green2 << 8) | blue2;

                            // Update the color values
                            anim.setIntValues(color1, color2);

                            anim.start();

                            // Order the colors
                            color1 = color2;
						}
					});
        		}
        	}
        }.start();
	}

	public void onBackPressed() {
	    super.onBackPressed();
		overridePendingTransition(R.anim.swipe_right_in, R.anim.swipe_right_out);
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
            intent = new Intent (context, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.swipe_right_in, R.anim.swipe_right_out);
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			SignUpService.signUp(username, password);
			return null;
		}
    	
    }
}
