package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    Button login;
    EditText email,password;

    String emailpattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        login = findViewById(R.id.main_login);
        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_button);

        login.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().trim().equals("") || password.getText().toString().trim().equals(""))
                {
                    login.setClickable(false);
                    login.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                else
                {
                    login.setClickable(true);
                    login.setBackground(getResources().getDrawable(R.drawable.custom_button));
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.toString().trim().equals("") || email.getText().toString().trim().equals(""))
                {
                    login.setClickable(false);
                    login.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                else
                {
                    login.setClickable(true);
                    login.setBackground(getResources().getDrawable(R.drawable.custom_button));
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals(""))
                {
                    email.setError("Enter a valid Email id");
                }
                else if(!email.getText().toString().matches(emailpattern))
                {
                    email.setError("valid email id required");
                }
                else if(password.getText().toString().trim().equals(""))
                {
                    password.setError("Enter a valid password");
                }
                else {
//                   if(email.getText().toString().equals("admin@gmail.com") && password.getText().toString().equals("admin@007") )
//                    {
                        //System.out.println("login successfully");
                        //Toast.makeText(MainActivity.this,"login successfully",Toast.LENGTH_SHORT).show();
                        //Snackbar.make(view,"login successfully", BaseTransientBottomBar.LENGTH_LONG).show();
                        new CommonMethod(MainActivity.this, "login successfully");
                        new CommonMethod(view, "login successfully");

                        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("EMAIL",email.getText().toString());
                        bundle.putString("PASSWORD",password.getText().toString());
                        intent.putExtras(bundle);

                        startActivity(intent);
                        //new CommonMethod(MainActivity.this,HomeActivity.class);

                    //}
//                    else
//                    {
//                        new CommonMethod(MainActivity.this, "login unsuccessfully");
//                        new CommonMethod(view, "login unsuccessfully");
//                    }
                }

            }
        });
    }
}
