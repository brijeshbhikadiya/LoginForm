package com.loginform;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView email,password;

    //RadioButton male;  //aavi rite female amd transgender nu banvi shaky ...
    //radio group no object banvi devo jethi three badhu aek ma aavi jay.

    RadioGroup gender;

    CheckBox androidcheck,java,ios,flutter;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email=findViewById(R.id.home_email);
        password=findViewById(R.id.home_password);

        Bundle bundle=getIntent().getExtras();
        email.setText(bundle.getString("EMAIL"));
        password.setText(bundle.getString("PASSWORD"));

//        male=findViewById(R.id.home_male);
//
//        male.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new CommonMethod(HomeActivity.this,"male");
//            }
//        });
        gender=findViewById(R.id.home_gender);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=findViewById(i);
                new CommonMethod(HomeActivity.this,radioButton.getText().toString());
            }
        });

        androidcheck=findViewById(R.id.home_android);
        java=findViewById(R.id.home_java);
        ios=findViewById(R.id.home_ios);
        flutter=findViewById(R.id.home_flutter);
        show=findViewById(R.id.home_showTechnology);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb=new StringBuilder();
                if(androidcheck.isChecked())
                {
                    sb.append(androidcheck.getText().toString()+"\n");
                }
                if(java.isChecked())
                {
                    sb.append(java.getText().toString()+"\n");
                }
                if(ios.isChecked())
                {
                    sb.append(ios.getText().toString()+"\n");
                }
                if(flutter.isChecked())
                {
                    sb.append(flutter.getText().toString()+"\n");
                }
                new CommonMethod(HomeActivity.this,sb.toString());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            onBackPressed();
            //alertMethod();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        alertMethod();
    }


    private void alertMethod()
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(HomeActivity.this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Exit Alert");
        builder.setMessage("Are You Sure Want To Exit");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //three step you can finish this process..
                //finish();  //this is not supported update version
                finishAffinity(); //this is supported all version
                //System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
            }
        });

        builder.setNeutralButton("Rate us", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new CommonMethod(HomeActivity.this,"Rate us"); //atyre khali message print karavyo kem k playstore ma app nathi..
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}