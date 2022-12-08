package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.loginform.GetData.GetSignupData;
import com.loginform.GetData.GetUserListData;
import com.loginform.Utils.ApiInterface;
import com.loginform.Utils.Apiclient;
import com.loginform.Utils.GetLoginData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonGetUserListActivity extends AppCompatActivity {

    EditText type;
    Button submit;

    ProgressDialog pd;
    ArrayList<String> arrayList;

    ApiInterface apiInterface;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_get_user_list);

        apiInterface=Apiclient.getClient().create(ApiInterface.class);
        sp = getSharedPreferences(ConsatntUrl.PREF,MODE_PRIVATE);

        type = findViewById(R.id.Json_getUserList_type);
        submit = findViewById(R.id.Json_getUserList_Submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type.getText().toString().trim().equalsIgnoreCase(""))
                {
                    type.setError("Enter a Valid Type");
                }
                else
                {
                    if(new ConnectionDetector(JsonGetUserListActivity.this).isConnectingToInternet())
                    {
                        //new doLogin().execute();
                        pd=new ProgressDialog(JsonGetUserListActivity.this);
                        pd.setMessage("Please Wait...");
                        pd.setCancelable(false);
                        pd.show();
                        retrofitGetUserData();
                    }
                    else
                    {
                        new ConnectionDetector(JsonGetUserListActivity.this).connectiondetect();
                    }
                }

            }
        });


    }

    private void retrofitGetUserData() {
        Call<GetUserListData> call=apiInterface.getUserDataData(type.getText().toString());
        call.enqueue(new Callback<GetUserListData>() {
            @Override
            public void onResponse(Call<GetUserListData> call, Response<GetUserListData> response) {
                pd.dismiss();
                if(response.code()==200)
                {
                    GetUserListData Data=response.body();
                    if(Data.status==true)
                    {
                        for(int i=0;i<Data.userdata.size();i++)
                        {
                            String sID=Data.userdata.get(i).id;
                            String sNAME=Data.userdata.get(i).name;
                            String sEMAILID=Data.userdata.get(i).emailid;
                            String sCONTACT=Data.userdata.get(i).contact;
                            String sGENDER=Data.userdata.get(i).gender;
                            String sCITY=Data.userdata.get(i).city;
                            String sDOB=Data.userdata.get(i).dob;
                            String sType=Data.userdata.get(i).type;
                            
//                            sp.edit().putString(ConsatntUrl.ID,sID).commit();
//                            sp.edit().putString(ConsatntUrl.Name,sNAME).commit();
//                            sp.edit().putString(ConsatntUrl.EmailID,sEMAILID).commit();
//                            sp.edit().putString(ConsatntUrl.Gender,sGENDER).commit();
//                            sp.edit().putString(ConsatntUrl.Contact,sCONTACT).commit();
//                            sp.edit().putString(ConsatntUrl.City,sCITY).commit();
//                            sp.edit().putString(ConsatntUrl.Type,sType).commit();
//                            sp.edit().putString(ConsatntUrl.Dob,sDOB).commit();


                            Intent intent=new Intent(JsonGetUserListActivity.this,JsonProfileActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("id",sID);
                            bundle.putString("name",sNAME);
                            bundle.putString("emailid",sEMAILID);
                            bundle.putString("contact",sCONTACT);
                            bundle.putString("gender",sGENDER);
                            bundle.putString("city",sCITY);
                            bundle.putString("dob",sDOB);
                            bundle.putString("type",sType);
                            intent.putExtras(bundle);

                            startActivity(intent);


                        }

                        new CommonMethod(JsonGetUserListActivity.this,Data.message);
                        new CommonMethod(JsonGetUserListActivity.this,JsonProfileActivity.class);
                    }
                    else
                    {
                        new CommonMethod(JsonGetUserListActivity.this,Data.message);
                    }
                }else
                {
                    new CommonMethod(JsonGetUserListActivity.this,"SERVER ERROR CODE :"+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetUserListData> call, Throwable t) {
                pd.dismiss();
                new CommonMethod(JsonGetUserListActivity.this,t.getMessage());
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}