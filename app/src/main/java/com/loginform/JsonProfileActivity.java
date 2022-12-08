package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.loginform.GetData.GetSignupData;
import com.loginform.Utils.ApiInterface;
import com.loginform.Utils.Apiclient;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JsonProfileActivity extends AppCompatActivity {

    EditText name,email,contact,dob,password;
    RadioGroup gender;
    RadioButton Male,Female,Transgender;
    Spinner city;
    Button editprofile,submit,logout;
    Calendar calendar;
    ArrayList<String> cityarray;

    String emailpattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String sGender,sCity;

    SharedPreferences sp;
    ApiInterface apiInterface;
    ProgressDialog pd;

    CircleImageView profileIv;
    ImageView cameraIv;

    int REQUEST_CODE_CHOOSE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_profile);

        sp=getSharedPreferences(ConsatntUrl.PREF,MODE_PRIVATE);

        profileIv=findViewById(R.id.Json_profile_image);
        cameraIv=findViewById(R.id.Json_profile_camera);

        name = findViewById(R.id.json_profile_name);
        email = findViewById(R.id.json_profile_email);
        password = findViewById(R.id.json_profile_password);
        contact = findViewById(R.id.json_profile_Contact);
        dob = findViewById(R.id.json_profile_dob);

        apiInterface= Apiclient.getClient().create(ApiInterface.class);

        Male=findViewById(R.id.json_profile_Male);
        Female=findViewById(R.id.json_profile_Female);
        Transgender=findViewById(R.id.json_profile_Transgender);

       name.setText(sp.getString(ConsatntUrl.Name,""));
       email.setText(sp.getString(ConsatntUrl.EmailID,""));
       contact.setText(sp.getString(ConsatntUrl.Contact,""));
       dob.setText(sp.getString(ConsatntUrl.Dob,""));

        gender = findViewById(R.id.json_profile_gender);
        city = findViewById(R.id.json_profile_city);
        editprofile = findViewById(R.id.json_profile_edit);
        submit=findViewById(R.id.json_profile_submit);
        logout = findViewById(R.id.json_profile_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().clear().commit();
                new CommonMethod(JsonProfileActivity.this,JasonLoginActivity.class);
            }
        });

        sGender=sp.getString(ConsatntUrl.Gender,"");
        if(sGender.equalsIgnoreCase("Male"))
        {
            Male.setChecked(true);
        }
        else if(sGender.equalsIgnoreCase("Female"))
        {
            Female.setChecked(true);
        }
        else if(sGender.equalsIgnoreCase("Transgender"))
        {
            Transgender.setChecked(true);
        }
        else
        {

        }

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=findViewById(i);
                sGender=radioButton.getText().toString();
                new CommonMethod(JsonProfileActivity.this,sGender);
            }
        });

        cityarray =new ArrayList<>();
        cityarray.add("Ahmedabad");
        cityarray.add("Anand");
        cityarray.add("Nadiad");
        cityarray.add("Surat");
        cityarray.add("Gandhinagar");
        cityarray.add("Rajkot");

        ArrayAdapter adapter=new ArrayAdapter(JsonProfileActivity.this, android.R.layout.simple_list_item_1,cityarray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        city.setAdapter(adapter);

        sCity=sp.getString(ConsatntUrl.City,"");

        int selectposition=0;

        for(int i=0;i<cityarray.size();i++)
        {
            if(cityarray.get(i).equalsIgnoreCase(sCity)) {
                selectposition = i;
                break;
            }
        }
        city.setSelection(selectposition);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //new CommonMethod(Spinner_List_Activity.this,CityArray[i]);
                sCity=cityarray.get(i);
                new CommonMethod(JsonProfileActivity.this,sCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calendar =Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateclick=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());
                dob.setText(dateFormat.format(calendar.getTime()));
            }
        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datedialog=new DatePickerDialog(JsonProfileActivity.this,dateclick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datedialog.getDatePicker().setMinDate(System.currentTimeMillis());
                //datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datedialog.show();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().trim().equalsIgnoreCase(""))
                {
                    name.setError("Name Required");
                }
                else if(email.getText().toString().trim().equals(""))
                {
                    email.setError("Email id Required");
                }
                else if(password.getText().toString().trim().equals(""))
                {
                    password.setError("password required");
                }
                else if(!email.getText().toString().matches(emailpattern))
                {
                    email.setError("Valid Email Id required");
                }
                else if(contact.getText().toString().trim().equals("")){
                    contact.setError("Contact Required");
                }
                else if(contact.getText().toString().length()<10)
                {
                    contact.setError("Valid Contact Number Required");
                }
                else if(dob.getText().toString().trim().equals(""))
                {
                    dob.setError("Dath of Birth Required");
                }
                else if(gender.getCheckedRadioButtonId()==-1)
                {
                    new CommonMethod(JsonProfileActivity.this,"Gender Required");
                }
                else{
//                    setEnableData(false);
//                    editprofile.setVisibility(View.VISIBLE);
//                    submit.setVisibility(View.GONE);
                    if(new ConnectionDetector(JsonProfileActivity.this).isConnectingToInternet())
                    {
                        //new updateData().execute();
                        pd=new ProgressDialog(JsonProfileActivity.this);
                        pd.setMessage("Please Wait....");
                        pd.show();
                        pd.setCancelable(false);
                        retrofitUpdate();
                    }
                    else
                    {
                        new ConnectionDetector(JsonProfileActivity.this).connectiondetect();
                    }
                }
            }
        });

       setEnableData(false);
       editprofile.setVisibility(View.VISIBLE);
       submit.setVisibility(View.GONE);

       editprofile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               setEnableData(true);
               editprofile.setVisibility(View.GONE);
               submit.setVisibility(View.VISIBLE);
           }
       });
        cameraIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(JsonProfileActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(5)
                        .gridExpectedSize(200)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .showPreview(false) // Default is `true`
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
    }

    private void retrofitUpdate() {
        Call<GetSignupData> call=apiInterface.getUpdateData(

                sp.getString(ConsatntUrl.ID,""),
                name.getText().toString(),
                email.getText().toString(),
                contact.getText().toString(),
                sGender,
                sCity,
                password.getText().toString(),
                dob.getText().toString()
        );
        call.enqueue(new Callback<GetSignupData>() {
            @Override
            public void onResponse(Call<GetSignupData> call, Response<GetSignupData> response) {
                pd.dismiss();
                if(response.code()==200)
                {
                    if(response.body().status==true)
                    {
                        new CommonMethod(JsonProfileActivity.this,response.body().message);
                        setEnableData(false);
                        editprofile.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.GONE);

                        sp.edit().putString(ConsatntUrl.Name,name.getText().toString()).commit();
                        sp.edit().putString(ConsatntUrl.EmailID,email.getText().toString()).commit();
                        sp.edit().putString(ConsatntUrl.Contact,contact.getText().toString()).commit();
                        sp.edit().putString(ConsatntUrl.Dob,dob.getText().toString()).commit();
                        sp.edit().putString(ConsatntUrl.City,sCity).commit();
                        sp.edit().putString(ConsatntUrl.Gender,sGender).commit();

                    }
                    else
                    {
                        new CommonMethod(JsonProfileActivity.this,response.body().message);
                    }
                }
                else
                {
                    new CommonMethod(JsonProfileActivity.this,"SERVER CODE ERROR : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                Log.d("RESPONSE_ERROR",t.getMessage());
            }
        });
    }

    private void setEnableData(boolean b) {
        name.setEnabled(b);
        email.setEnabled(b);
        contact.setEnabled(b);
        password.setEnabled(b);

        Male.setEnabled(b);
        Female.setEnabled(b);
        Transgender.setEnabled(b);

        city.setEnabled(b);
        dob.setEnabled(b);

    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private class updateData extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(JsonProfileActivity.this);
            pd.setMessage("Please Wait....");
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("id",sp.getString(ConsatntUrl.ID,""));
            hashMap.put("name",name.getText().toString());
            hashMap.put("emailid",email.getText().toString());
            hashMap.put("contact",contact.getText().toString());
            hashMap.put("gender",sGender);
            hashMap.put("city",sCity);
            hashMap.put("password",password.getText().toString());
            hashMap.put("dob",dob.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConsatntUrl.UPDATE_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject jsonObject=new JSONObject(s);
                if(jsonObject.getBoolean("Status")==true)
                {
                    new CommonMethod(JsonProfileActivity.this,jsonObject.getString("Message"));

                    setEnableData(false);
                    editprofile.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);
                    sp.edit().putString(ConsatntUrl.Name,name.getText().toString()).commit();
                    sp.edit().putString(ConsatntUrl.EmailID,email.getText().toString()).commit();
                    sp.edit().putString(ConsatntUrl.Contact,contact.getText().toString()).commit();
                    sp.edit().putString(ConsatntUrl.Dob,dob.getText().toString()).commit();
                    sp.edit().putString(ConsatntUrl.City,sCity).commit();
                    sp.edit().putString(ConsatntUrl.Gender,sGender).commit();

                }
                else
                {
                    new CommonMethod(JsonProfileActivity.this,jsonObject.getString("Message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
