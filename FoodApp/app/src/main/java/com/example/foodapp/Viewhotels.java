package com.example.foodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewhotels extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    EditText e1;

    String [] hotelname,phone,email,lic,statu,hotel_id,value;
   String status,search;
   public static String hid;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewhotels);
        l1=(ListView)findViewById(R.id.list);


        e1=(EditText) findViewById(R.id.search);

        l1.setOnItemClickListener(this);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewhotels.this;
        String q = "/Viewhotels";
        q = q.replace(" ", "%20");
        JR.execute(q);


        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                search=e1.getText().toString();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Viewhotels.this;
                String q = "/search?&search=" + search ;
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });




    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("Viewhotels")) {
                status = jo.getString("status");
                Log.d("pearlssssss", status);


                if (status.equalsIgnoreCase("success")) {
                    l1.setVisibility(View.VISIBLE);
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    hotelname = new String[ja1.length()];
                    phone = new String[ja1.length()];
                    email = new String[ja1.length()];
                    lic = new String[ja1.length()];
                    statu = new String[ja1.length()];
                    hotel_id = new String[ja1.length()];


                    value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        hotelname[i] = ja1.getJSONObject(i).getString("hotel_name");
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        email[i] = ja1.getJSONObject(i).getString("email");
                        lic[i] = ja1.getJSONObject(i).getString("license_number");
                        statu[i] = ja1.getJSONObject(i).getString("status");
                        hotel_id[i] = ja1.getJSONObject(i).getString("hotel_id");


                        value[i] = "hotel name:" + hotelname[i] + "\nphone: " + phone[i] + "\n email: " + email[i] + "\nlicense number: " + lic[i] + "\nstatus: " + statu[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                    l1.setAdapter(ar);
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_LONG).show();
                    l1.setVisibility(View.GONE);
                }
            }
            if (method.equalsIgnoreCase("search")) {
                status = jo.getString("status");
                Log.d("pearlsssss", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    hotelname = new String[ja1.length()];
                    phone = new String[ja1.length()];
                    email = new String[ja1.length()];
                    lic = new String[ja1.length()];
                    statu = new String[ja1.length()];
                    hotel_id = new String[ja1.length()];

                    String[] value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        hotelname[i] = ja1.getJSONObject(i).getString("hotel_name");
                        Toast.makeText(getApplicationContext(), hotelname[i], Toast.LENGTH_LONG).show();
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        email[i] = ja1.getJSONObject(i).getString("email");
                        lic[i] = ja1.getJSONObject(i).getString("license_number");
                        statu[i] = ja1.getJSONObject(i).getString("status");
                        hotel_id[i] = ja1.getJSONObject(i).getString("hotel_id");


                        value[i] = "hotel name:" + hotelname[i] + "\nphone: " + phone[i] + "\n email: " + email[i] + "\nlicense number: " + lic[i] + "\nstatus: " + statu[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);

                    l1.setAdapter(ar);
                }
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
        hid=hotel_id[i];

            final CharSequence[] items = {"View Menu","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewhotels.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("View Menu")) {

                        startActivity(new Intent(getApplicationContext(),Viewmenu.class));


                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();


    }
}