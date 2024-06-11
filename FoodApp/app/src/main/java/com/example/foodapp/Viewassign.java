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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewassign extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String [] hotel_name, food_name,name,date_time,stat,assign_id ,user_id,book;
    public static String aid,sta,uid ,bid;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewassign2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewassign.this;
        String q = "/Viewassign?login_id="+sh.getString("log_id","" );
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
                hotel_name = new String[ja1.length()];
                food_name = new String[ja1.length()];
                name = new String[ja1.length()];
                date_time = new String[ja1.length()];
                stat = new String[ja1.length()];
                assign_id=new String[ja1.length()];
                user_id=new String[ja1.length()];
                book=new String[ja1.length()];


                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    hotel_name[i] = ja1.getJSONObject(i).getString("hotel_name");
                    food_name[i] = ja1.getJSONObject(i).getString("food_name");
                    name[i] = ja1.getJSONObject(i).getString("first_name");
                    date_time[i] = ja1.getJSONObject(i).getString("date_time");
                    stat[i] = ja1.getJSONObject(i).getString("stat");
                    assign_id[i]=ja1.getJSONObject(i).getString("assign_id");
                    user_id[i]=ja1.getJSONObject(i).getString("user_id");
                    book[i]=ja1.getJSONObject(i).getString("booking_id");


                    value[i] ="hotel name:" + hotel_name[i]+ "\nfood name: " + food_name[i] + "\n delivery boy name: " + name[i] + "\ndate time: " + date_time[i] + "\nstatus: " + stat[i]  ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);

                l1.setAdapter(ar);
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
        aid=assign_id[i];
        sta=stat[i];
        uid=user_id[i];
        bid=book[i];

        if (sta.equalsIgnoreCase("assign")) {

            final CharSequence[] items = {"delivered","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewassign.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("delivered")) {
                        JsonReq JR = new JsonReq();
                        JR.json_response = (JsonResponse) Viewassign.this;
                        String q = "/delivered?aid=" + aid +"&bid="+bid;
                        q = q.replace(" ", "%20");
                        JR.execute(q);



                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();

        }else if (sta.equalsIgnoreCase("delivered")) {

            final CharSequence[] items = {"View User","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewassign.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("View User")) {

                        startActivity(new Intent(getApplicationContext(),Viewuser.class));




                    }else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }

                }

            });
            builder.show();
        }

    }
}