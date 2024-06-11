package com.example.foodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class Viewmenu extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    String[] category_name, food_name, image, price, quantity, statu, menu_id, amt, food, value, stata;
    public static String mid, aid, fid, stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewmenu);
        l1 = (ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewmenu.this;
        String q = "/Viewmenu?hid="+Viewhotels.hid;
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
                category_name = new String[ja1.length()];
                food_name = new String[ja1.length()];
                image = new String[ja1.length()];
                price = new String[ja1.length()];
                quantity = new String[ja1.length()];
                statu = new String[ja1.length()];
                menu_id = new String[ja1.length()];
                amt = new String[ja1.length()];
                food = new String[ja1.length()];
                value = new String[ja1.length()];


                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    category_name[i] = ja1.getJSONObject(i).getString("category_name");
                    food_name[i] = ja1.getJSONObject(i).getString("food_name");
                    image[i] = ja1.getJSONObject(i).getString("image");
                    price[i] = ja1.getJSONObject(i).getString("price");
                    quantity[i] = ja1.getJSONObject(i).getString("quantity");
                    statu[i] = ja1.getJSONObject(i).getString("status");
                    menu_id[i] = ja1.getJSONObject(i).getString("menu_id");
                    amt[i] = ja1.getJSONObject(i).getString("price");
                    food[i] = ja1.getJSONObject(i).getString("food_name");


                    value[i] = "category_name:" + category_name[i] + "\nfood_name: " + food_name[i] + "\n image: " + image[i] + "\nprice: " + price[i] + "\nquantity: " + quantity[i] + "\nstatus: " + statu[i];

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                l1.setAdapter(ar);
                Custimage a = new Custimage(this, category_name, food_name, image, price, quantity, statu);
                l1.setAdapter(a);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mid = menu_id[i];
        aid = amt[i];
        fid = food[i];

            final CharSequence[] items = {"Make Order", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewmenu.this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Make Order")) {

                        startActivity(new Intent(getApplicationContext(), Makeorder.class));


                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }

            });
            builder.show();


        }
    }
