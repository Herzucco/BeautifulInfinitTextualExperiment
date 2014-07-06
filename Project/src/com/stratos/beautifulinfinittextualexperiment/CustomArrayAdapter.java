package com.stratos.beautifulinfinittextualexperiment;

import java.util.ArrayList;

import com.api.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        ViewHolder holder = new ViewHolder();
        
        //creating LayoutInflator for inflating the row layout.
        LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflating the row layout we defined earlier.
        convertView = inflator.inflate(R.layout.row_item_layout, null);
        
        //setting the views into the ViewHolder.
        holder.title = (TextView) convertView.findViewById(R.id.tvItemTitle);
        holder.content = (TextView) convertView.findViewById(R.id.iStatus);
 
        holder.title.setText(list.get(position).getAuthor());
        holder.content.setText(list.get(position).getContent());
        //return the row view.
        return convertView;
    }
}