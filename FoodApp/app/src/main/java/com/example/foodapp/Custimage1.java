package com.example.foodapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custimage1 extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;

	private String[] food_name;
	private String[] quantity;
	private String[] date_time;
	private String[] total;

	private String[] hotel_name;
	private String[] img;

	 public Custimage1(Activity context, String[] food, String[] qua, String[] date , String[] tot, String[] hname, String[] image ) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.cust_images,image);
	        this.context = context;

		    this.food_name = food;
		 	this.quantity = qua;
		 this.date_time = date;
			 this.total = tot;
			 this.hotel_name = hname;
		 this.img = image;

	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.cust_images, null, true);
			//cust_list_view is xml file of layout created in step no.2

	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
	        TextView t1=(TextView)listViewItem.findViewById(R.id.textView3);

			TextView t2=(TextView)listViewItem.findViewById(R.id.textView5);
			t1.setText("food_name : "+food_name[position]+"\nquantity : "+quantity[position]+"\ndate_time:"+date_time[position]+"\ntotal:"+total[position]+"\nhotel_name:"+hotel_name[position]);
//			t2.setText(caption[position]);
	        sh=PreferenceManager.getDefaultSharedPreferences(getContext());
	        
	       String pth = "http://"+sh.getString("ip", "")+"/"+img[position];
	       pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();
	        
	        Log.d("-------------", pth);
	        Picasso.with(context)
	                .load(pth)
	                .placeholder(R.drawable.ic_launcher_background)
	                .error(R.drawable.ic_launcher_background).into(im);
	        
	        return  listViewItem;
	    }

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}