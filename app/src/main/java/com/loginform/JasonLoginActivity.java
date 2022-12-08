package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loginform.Utils.ApiInterface;
import com.loginform.Utils.Apiclient;
import com.loginform.Utils.GetLoginData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JasonLoginActivity extends AppCompatActivity {


    EditText email,password;
    Button submit;
    TextView createaccount;

    SharedPreferences sp;

    ApiInterface apiInterface;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jason_login);

        email=findViewById(R.id.json_login_email);
        password=findViewById(R.id.json_login_password);
        submit=findViewById(R.id.json_login_submit);
        createaccount=findViewById(R.id.json_login_create);

        apiInterface= Apiclient.getClient().create(ApiInterface.class);


        sp = getSharedPreferences(ConsatntUrl.PREF,MODE_PRIVATE);

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(JasonLoginActivity.this,Jason_Form_Activity.class);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().trim().equalsIgnoreCase(""))
                {
                    email.setError("Enter a Valid EmialId/Contactno");
                }
                else if(password.getText().toString().equalsIgnoreCase(""))
                {
                    password.setError("password required");
                }
                else
                {
                    if(new ConnectionDetector(JasonLoginActivity.this).isConnectingToInternet())
                    {
                        //new doLogin().execute();
                        pd=new ProgressDialog(JasonLoginActivity.this);
                        pd.setMessage("Please Wait...");
                        pd.setCancelable(false);
                        pd.show();
                        retrofitLogin();
                    }
                    else
                    {
                        new ConnectionDetector(JasonLoginActivity.this).connectiondetect();
                    }
                }

            }
        });


    }

    private void retrofitLogin() {
        Call<GetLoginData> call = apiInterface.getLoginData(email.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<GetLoginData>() {
            @Override
            public void onResponse(Call<GetLoginData> call, Response<GetLoginData> response) {
                pd.dismiss();
                if(response.code()==200)
                {
                    GetLoginData Data=response.body();
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

                            sp.edit().putString(ConsatntUrl.ID,sID).commit();
                            sp.edit().putString(ConsatntUrl.Name,sNAME).commit();
                            sp.edit().putString(ConsatntUrl.EmailID,sEMAILID).commit();
                            sp.edit().putString(ConsatntUrl.Gender,sGENDER).commit();
                            sp.edit().putString(ConsatntUrl.Contact,sCONTACT).commit();
                            sp.edit().putString(ConsatntUrl.City,sCITY).commit();
                            sp.edit().putString(ConsatntUrl.Type,sType).commit();
                            sp.edit().putString(ConsatntUrl.Dob,sDOB).commit();

                        }

                        new CommonMethod(JasonLoginActivity.this,Data.message);
                        new CommonMethod(JasonLoginActivity.this,JsonProfileActivity.class);
                    }
                    else
                    {
                        new CommonMethod(JasonLoginActivity.this,Data.message);
                    }
                }else
                {
                    new CommonMethod(JasonLoginActivity.this,"SERVER ERROR CODE :"+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetLoginData> call, Throwable t) {
                pd.dismiss();
                new CommonMethod(JasonLoginActivity.this,t.getMessage());
            }
        });
    }

    private class doLogin extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(JasonLoginActivity.this);
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("emailid",email.getText().toString());
            hashMap.put("password",password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConsatntUrl.LOGIN_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(s);
                if(jsonObject.getBoolean("Status")==true)
                {
                    JSONArray jsonArray=jsonObject.getJSONArray("Userdata");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object=jsonArray.getJSONObject(i);

                        String sID=object.getString("id");
                        String sNAME=object.getString("name");
                        String sEMAILID=object.getString("emailid");
                        String sCONTACT=object.getString("contact");
                        String sGENDER=object.getString("gender");
                        String sCITY=object.getString("city");
                        String sDOB=object.getString("dob");
                        String sType=object.getString("type");

                        sp.edit().putString(ConsatntUrl.ID,sID).commit();
                        sp.edit().putString(ConsatntUrl.Name,sNAME).commit();
                        sp.edit().putString(ConsatntUrl.EmailID,sEMAILID).commit();
                        sp.edit().putString(ConsatntUrl.Gender,sGENDER).commit();
                        sp.edit().putString(ConsatntUrl.Contact,sCONTACT).commit();
                        sp.edit().putString(ConsatntUrl.City,sCITY).commit();
                        sp.edit().putString(ConsatntUrl.Type,sType).commit();
                        sp.edit().putString(ConsatntUrl.Dob,sDOB).commit();

                    }

                    new CommonMethod(JasonLoginActivity.this,jsonObject.getString("Message"));
                    new CommonMethod(JasonLoginActivity.this,JsonProfileActivity.class);
                }
                else
                {
                    new CommonMethod(JasonLoginActivity.this,jsonObject.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new CommonMethod(JasonLoginActivity.this,e.getMessage());
            }
        }
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}