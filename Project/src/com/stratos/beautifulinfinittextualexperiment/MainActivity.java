package com.stratos.beautifulinfinittextualexperiment;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.api.AppUser;
import com.api.ConnectionService;
import com.api.Message;
import com.api.MessageService;
import com.api.SignUpService;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private final String TAG = MainActivity.class.getSimpleName();
    private EditText usernameField;
    private EditText passwordField;
    private TextView myTextView;
    private Button sendButton;
    private Button signUpButton;
//  private Button eraseButton;
    private Intent intent;
    private MainActivity context = this;
    private String username;
    private String password;
    private SharedPreferences preferences;
    private ProgressBar progressBar;
    private int color1, color2, red1, red2, blue1, blue2, green1, green2;
    View v;
    ObjectAnimator anim;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonClick();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);
        
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
        			MainActivity.this.runOnUiThread(new Runnable() {
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
        overridePendingTransition(R.anim.swipe_left_in, R.anim.swipe_left_out);
    }
    
    public void buttonClick()
    {
        usernameField = (EditText) findViewById(R.id.editText1);
        passwordField = (EditText) findViewById(R.id.editText2);
        myTextView = (TextView) findViewById(R.id.textView2);
//      eraseButton = (Button) findViewById(R.id.button1);
        sendButton = (Button) findViewById(R.id.button2);
        signUpButton = (Button) findViewById(R.id.button3);

        
//      eraseButton.setOnClickListener(new OnClickListener() {
//
//          @Override
//          public void onClick(View v) {
//              usernameField.setText("");
//              passwordField.setText("");
//          }
//      });    

        sendButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
                SignInTask task = new SignInTask();
                task.execute();
                progressBar.setVisibility(View.VISIBLE);
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
        protected void onPreExecute() {
            //todo : add spinning
        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean canConnect = ConnectionService.connect(username, password);
            if(canConnect){
                AppUser.getInstance().name(username).password(password);
                
                //AppUser.save(preferences);
                MessageService.getList();
                //Log.i("[Main Activity]", String.valueOf(MessageService.displayableList.size()));
            }
            // TODO Auto-generated method stub
            return canConnect;
        }
        
        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                intent = new Intent (context, MessagesActivity.class);
                startActivity(intent);
                progressBar.setVisibility(View.GONE);
                overridePendingTransition(R.anim.swipe_right_in, R.anim.swipe_right_out);
                //overridePendingTransition(R.anim.activity_switch, R.anim.activity_out);
            }
            progressBar.setVisibility(View.GONE);
        }
    }  
    
}