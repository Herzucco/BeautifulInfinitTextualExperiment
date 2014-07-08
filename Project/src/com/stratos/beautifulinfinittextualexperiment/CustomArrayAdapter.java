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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    	final Context ctx = BeautifulInfinitTextualExperiment.getAppContext();
    	
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
        holder.llayout = (LinearLayout) convertView.findViewById(R.id.relativeLayoutViewList);
 
        holder.title.setText(list.get(position).getAuthor());
        holder.content.setText(list.get(position).getContent());
        
        
        String hex = toHex(list.get(position).getAuthor());
        hex = hex.substring(hex.length()-6);
        
        int color = stringToColor("#"+hex);
        
        Log.i("[painting]", hex);
        Log.i("[painting]", String.valueOf(color));
        
        holder.content.setBackgroundColor(color-7000000);
        holder.title.setBackgroundColor(color-7000500);
        holder.message = list.get(position);
        holder.message.view = holder;
        
        if(!AppUser.getInstance().getName().equals(holder.message.getAuthor())){
        	holder.button.setVisibility(View.GONE);
        	
//        	ViewGroup.MarginLayoutParams params = (MarginLayoutParams) holder.llayout.getLayoutParams();
//        	params.rightMargin = -100;
//        	holder.llayout.setLayoutParams(params);	
        	
        	LinearLayout.LayoutParams align = (LinearLayout.LayoutParams) holder.llayout.getLayoutParams();
        	align.rightMargin = -100;
        	holder.llayout.setLayoutParams(align);
        }else{
        	
        }
        
        holder.button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("[click on layout]", holder.message.getContent());
				holder.isEditing = !holder.isEditing;
				holder.switcher.showNext();
				
				if(holder.isEditing){
					holder.edit.setText(holder.content.getText().toString());
					holder.edit.requestFocus();
					InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(holder.edit, InputMethodManager.SHOW_IMPLICIT);
				}
				else{
					InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(holder.edit.getWindowToken(), 0);
					EditTask task = new EditTask(holder.message, holder.edit.getText().toString());
					task.execute();
				}
			}
		});    
        
        return convertView;
    }
    
    public static String toHex(String arg) {
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
    	String content;
    	
    	public EditTask(Message msg, String str){
    		message = msg;
    		content = str;
    	}
		@Override
		protected void onPreExecute() {
			//todo : add spinning
		}

		@Override
		protected Boolean doInBackground(String... arg0) {
			message.setContent(content);
			message.send();
			return null;
		}
    	
		@Override
		protected void onPostExecute(Boolean result){
			message.edition = content;
			message.edit();
		}
    }

}