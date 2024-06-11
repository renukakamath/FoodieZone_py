package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewuser extends AppCompatActivity implements JsonResponse {

    ListView l1;
    String[] fname,lname,hname,place,pin,gen,age,phone,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuser);

        l1=(ListView) findViewById(R.id.list);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewuser.this;
        String q = "/Viewuser?uid="+Viewassign.uid;
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
                fname = new String[ja1.length()];
                lname = new String[ja1.length()];
                hname = new String[ja1.length()];
                place = new String[ja1.length()];
                pin = new String[ja1.length()];
                gen = new String[ja1.length()];
                age = new String[ja1.length()];
                phone = new String[ja1.length()];
                email = new String[ja1.length()];




                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    fname[i] = ja1.getJSONObject(i).getString("first_name");
                    lname[i] = ja1.getJSONObject(i).getString("last_name");
                    hname[i] = ja1.getJSONObject(i).getString("house_name");
                    place[i] = ja1.getJSONObject(i).getString("place");
                    pin[i] = ja1.getJSONObject(i).getString("pincode");
                    gen[i] = ja1.getJSONObject(i).getString("gender");
                    age[i] = ja1.getJSONObject(i).getString("age");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");



                    value[i] ="First name:" + fname[i]+ "\nLast name: " + lname[i] + "\n house name: " + hname[i] + "\nPlace: " + place[i] + "\npincode: " + pin[i] + "\ngender: " + gen[i]+ "\nage: " + age[i]+ "\nphone: " + phone[i] +"\nemail" +email[i]  ;

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
}