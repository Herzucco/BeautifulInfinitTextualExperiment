package com.stratos.beautifulinfinittextualexperiment;

import com.api.MessageService;
import com.api.SignUpService;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(getApplicationInfo().name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        AskConnectTask task = new AskConnectTask();
        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    
    private class AskConnectTask extends
        android.os.AsyncTask<String, Void, Boolean> {
    
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        
        @Override
        protected Boolean doInBackground(String... params) {
            MessageService.send("test");
            return false;
            
        }
        
        @Override    
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
        }
    
    }
}
