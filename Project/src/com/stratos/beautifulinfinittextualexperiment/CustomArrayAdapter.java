package com.stratos.beautifulinfinittextualexperiment;

import java.math.BigInteger;
import java.util.ArrayList;

import com.api.AppUser;
import com.api.ConnectionService;
import com.api.Message;
import com.api.MessageService;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

class CustomArrayAdapter extends ArrayAdapter<Message>
{	
    private ArrayList<Message> list;
    
    public CustomArrayAdapter(Context context, int textViewResourceId, ArrayList<Message> messages) 
    {
        //populate the local list with data.
        super(context, textViewResourceId, messages);
        this.list = messages;
    }
		
    public View getView(final int position, View convertView, ViewGroup parent)
    {
    	Context ctx = BeautifulInfinitTextualExperiment.getAppContext();
    	
        //creating the ViewHolder we defined earlier.
        final ViewHolder holder = new ViewHolder();
        
        //creating LayoutInflator for inflating the row layout.
        LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflating the row layout we defined earlier.
        convertView = inflator.inflate(R.layout.row_item_layout, null);
        
        //setting the views into the ViewHolder.
        holder.title = (TextView) convertView.findViewById(R.id.tvItemTitle);
        holder.content = (TextView) convertView.findViewById(R.id.iStatus);
        holder.layout = (LinearLayout) convertView.findViewById(R.id.layoutListItem);
        holder.button = (Button) convertView.findViewById(R.id.iEdit);
        holder.switcher = (ViewSwitcher) convertView.findViewById(R.id.my_switcher);
        holder.edit = (EditText) convertView.findViewById(R.id.iEditText);
 
        holder.title.setText(list.get(position).getAuthor());
        holder.content.setText(list.get(position).getContent());
        
        
        //return the row view.
        String hex = toHex(list.get(position).getAuthor());
        hex = hex.substring(hex.length()-6);
        
        int color = stringToColor("#"+hex);
        
        Log.i("[samer]", hex);
        Log.i("[samer]", String.valueOf(color));
        
        holder.layout.setBackgroundColor(color-4000000);
        holder.message = list.get(position);
        holder.message.view = holder;
        
        if(!AppUser.getInstance().getName().equals(holder.message.getAuthor())){
        	holder.button.setVisibility(View.INVISIBLE);
        }
        
        holder.button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("[click on layout]", holder.message.getContent());
				holder.switcher.showNext();
				holder.edit.requestFocus();
				//EditTask task = new EditTask(holder.message);
				//task.execute();
			}
		});    
        
        return convertView;
    }
    
    public String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }
    
    public static int stringToColor(final String value) {
        if (value == null) {
          return Color.BLACK;
        }
        try {
          // get color by hex or octal value
          return Color.parseColor(value);
        } catch (NumberFormatException nfe) {
          // if we can't decode lets try to get it by name
          return Color.BLACK;
        }
      }
    
    public class EditTask extends android.os.AsyncTask<String, Void, Boolean> {
    	Message message;
    	
    	public EditTask(Message msg){
    		message = msg;
    	}
		@Override
		protected void onPreExecute() {
			//todo : add spinning
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			message.setContent("This is an edit");
			message.send();
			return null;
		}
    	
		@Override
		protected void onPostExecute(Boolean result){
			message.edition = "This is an edit";
			message.edit();
		}
    }

}