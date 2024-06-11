package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

public class Registration extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    RadioButton r1,r2;
    Button b1;
    String fname,lname,housename,place,pincode,age,phone,email,username,password,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        e1=(EditText) findViewById(R.id.fname);
        e2=(EditText) findViewById(R.id.lname);
        e3=(EditText) findViewById(R.id.housename);
        e4=(EditText) findViewById(R.id.place);
        e5=(EditText) findViewById(R.id.pincode);
        e6=(EditText) findViewById(R.id.age);
        e7=(EditText) findViewById(R.id.phone);
        e8=(EditText) findViewById(R.id.email);
        e9=(EditText) findViewById(R.id.username);
        e10=(EditText) findViewById(R.id.password);
        r1=(RadioButton) findViewById(R.id.male);
        r2=(RadioButton) findViewById(R.id.female);
        b1=(Button) findViewById(R.id.registration);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                housename=e3.getText().toString();
                place=e4.getText().toString();
                pincode=e5.getText().toString();
               if (r1.isChecked())
               {
                   gender="male";
               }else{
                   gender="female";
               }
                age=e6.getText().toString();
                phone=e7.getText().toString();
                email=e8.getText().toString();
                username=e9.getText().toString();
                password=e10.getText().toString();
                if(fname.equalsIgnoreCase("")|| !fname.matches("[a-zA-Z ]+"))
                {
                    e1.setError("Enter your firstname");
                    e1.setFocusable(true);
                }
                else if(lname.equalsIgnoreCase("")|| !lname.matches("[a-zA-Z ]+"))
                {
                    e2.setError("Enter your lastname");
                    e2.setFocusable(true);
                }
                else if(housename.equalsIgnoreCase("")|| !housename.matches("[a-zA-Z ]+"))
                {
                    e2.setError("Enter your Address");
                    e2.setFocusable(true);
                }
                else if(place.equalsIgnoreCase("")|| !place.matches("[a-zA-Z ]+"))
                {
                    e2.setError("Enter your place");
                    e2.setFocusable(true);
                }
                else if(pincode.equalsIgnoreCase("")|| pincode.length()!=6)
                {
                    e3.setError("Enter your pincode");
                    e3.setFocusable(true);
                }
                else if(age.equalsIgnoreCase(""))
                {
                    e7.setError("Enter your age");
                    e7.setFocusable(true);
                }
                else if(phone.equalsIgnoreCase("")|| phone.length()!=10)
                {
                    e3.setError("Enter your phone");
                    e3.setFocusable(true);
                }
                else if(email.equalsIgnoreCase("")|| !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"))
                {
                    e4.setError("Enter your email");
                    e4.setFocusable(true);
                }

                else if(username.equalsIgnoreCase(""))
                {
                    e7.setError("Enter your username");
                    e7.setFocusable(true);
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e8.setError("Enter your password");
                    e8.setFocusable(true);
                }else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Registration.this;
                    String q = "/Registration?fname=" + fname + "&lname=" + lname + "&housename=" + housename + "&place=" + place + "&pincode=" + pincode + "&gender=" + gender + "&age=" + age + "&phone=" + phone + "&email=" + email + "&username=" + username + "&password=" + password;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));

            } else if (status.equalsIgnoreCase("duplicate")) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
                Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

            }else if (status.equalsIgnoreCase("already")) {
                Toast.makeText(getApplicationContext(), "Username Or Password ALREADY EXIST", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Registration.class));

            }else {
                startActivity(new Intent(getApplicationContext(), Registration.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}