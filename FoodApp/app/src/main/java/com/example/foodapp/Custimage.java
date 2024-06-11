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

public class Custimage extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;

	private String[] category_name;
	private String[] food_name;
	private String[] img;
	private String[] price;
	private String[] quantity;
	private String[] statu;
	
	 public Custimage(Activity context, String[] cat, String[] food,String[] image ,String[] pri, String[] qua,String[] sta ) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.cust_images, image);
	        this.context = context;

		    this.category_name = cat;
		 	this.food_name = food;
		 this.img = image;
			 this.price = pri;
			 this.quantity = qua;
		 this.statu = sta;

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
			t1.setText("category_name : "+category_name[position]+"\nfood_name : "+food_name[position]+"\nprice:"+price[position]+"\nquantity:"+quantity[position]+"\nstatus:"+statu[position]);
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