package com.example.foodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewOrder extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String [] food_name,quantity,date_time,statu,booking_id ,total,value ,amo ,tot,hotel_name,hotel,img,fff;
    public static String bid,aid,tid,hid ,food,qua,data,toto,hota,sta;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) ViewOrder.this;
        String q = "/ViewOrder?login_id="+sh.getString("log_id","" );
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                food_name = new String[ja1.length()];
                quantity = new String[ja1.length()];
                date_time = new String[ja1.length()];
                total = new String[ja1.length()];

                amo = new String[ja1.length()];
                booking_id = new String[ja1.length()];
                value=new String[ja1.length()];
                tot=new String[ja1.length()];
                hotel_name=new String[ja1.length()];
                hotel=new String[ja1.length()];
                img=new String[ja1.length()];
                fff=new String[ja1.length()];
                statu=new String[ja1.length()];




                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    food_name[i] = ja1.getJSONObject(i).getString("food_name");
                    quantity[i] = ja1.getJSONObject(i).getString("quantity");
                    date_time[i] = ja1.getJSONObject(i).getString("date_time");
                    total[i] = ja1.getJSONObject(i).getString("total");
                    amo[i] = ja1.getJSONObject(i).getString("total");

                    booking_id[i] = ja1.getJSONObject(i).getString("booking_id");
                    tot[i] = ja1.getJSONObject(i).getString("total");
                    hotel_name[i] = ja1.getJSONObject(i).getString("hotel_name");
                    hotel[i] = ja1.getJSONObject(i).getString("hotel_name");
                    img[i] = ja1.getJSONObject(i).getString("image");
                    fff[i] = ja1.getJSONObject(i).getString("food_name");
                    statu[i] = ja1.getJSONObject(i).getString("status");





                    value[i] ="food_name:" + food_name[i]+ "\nquantity: " + quantity[i] + "\n date_time: " + date_time[i]  +"\ntotal:" +total[i] +"\nhotel:" +hotel_name[i] +"\nimage:" +img[i] ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);


                l1.setAdapter(ar);
                Custimage1 a=new Custimage1(this,food_name,quantity,date_time,amo,hotel_name,img);
                l1.setAdapter(a);
            }
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        bid=booking_id[i];
        aid=amo[i];
        tid=tot[i];
        hid=hotel[i];
        food=fff[i];
        qua=quantity[i];
        data=date_time[i];
        toto=total[i];
        hota=hotel_name[i];



            final CharSequence[] items = {"Make Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(ViewOrder.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Make Payment")) {

                        startActivity(new Intent(getApplicationContext(), Makepayment.class));


                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();

        }
    }


