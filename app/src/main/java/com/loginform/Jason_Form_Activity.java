package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import com.loginform.Utils.ApiInterface;
import com.loginform.Utils.Apiclient;

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

public class Jason_Form_Activity extends AppCompatActivity {

    EditText name,email,contact,dob,password;
    RadioGroup gender;
    Spinner city;
    Button submit;
    Calendar calendar;
    ArrayList<String> cityarray;

    ApiInterface apiInterface;
    ProgressDialog pd;
    String emailpattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String sGender,sCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jason_form);

        name = findViewById(R.id.json_activity_name);
        email = findViewById(R.id.json_activity_email);
        password = findViewById(R.id.json_activity_password);
        contact = findViewById(R.id.json_activity_Contact);
        dob = findViewById(R.id.json_activity_dob);
        city = findViewById(R.id.json_activity_city);
        gender = findViewById(R.id.json_activity_gender);
        submit = findViewById(R.id.json_activity_submit);

        apiInterface= Apiclient.getClient().create(ApiInterface.class);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton=findViewById(i);
                sGender=radioButton.getText().toString();
                new CommonMethod(Jason_Form_Activity.this,sGender);
            }
        });

        cityarray =new ArrayList<>();
        cityarray.add("Ahmedabad");
        cityarray.add("Anand");
        cityarray.add("Nadiad");
        cityarray.add("Surat");
        cityarray.add("Gandhinagar");
        cityarray.add("Rajkot");

        ArrayAdapter adapter=new ArrayAdapter(Jason_Form_Activity.this, android.R.layout.simple_list_item_1,cityarray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        city.setAdapter(adapter);

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //new CommonMethod(Spinner_List_Activity.this,CityArray[i]);
                sCity=cityarray.get(i);
                new CommonMethod(Jason_Form_Activity.this,sCity);
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
                DatePickerDialog datedialog=new DatePickerDialog(Jason_Form_Activity.this,dateclick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
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
                    new CommonMethod(Jason_Form_Activity.this,"Gender Required");
                }
                else{
                        //AsynkTask thi :
//                    if(new ConnectionDetector(Jason_Form_Activity.this).isConnectingToInternet())
//                    {
//                       // new CommonMethod(Jason_Form_Activity.this,"Interner/WIFI are connected");
//                        new doSignup().execute();
//                    }
//                    else
//                    {
//                        new ConnectionDetector(Jason_Form_Activity.this).connectiondetect() ;
//                    }
//                }
                    //Retrofit thi:
                    if(new ConnectionDetector(Jason_Form_Activity.this).isConnectingToInternet())
                    {
                       pd=new ProgressDialog(Jason_Form_Activity.this);
                       pd.setMessage("Please Wait....");
                       pd.setCancelable(false);
                       pd.show();
                       retrofitsignup();
                    }
                    else
                    {
                        new ConnectionDetector(Jason_Form_Activity.this).connectiondetect() ;
                    }
                }
            }
        });
    }

    private void retrofitsignup() {
        Call<GetSignupData> call=apiInterface.getSignupData(
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
                        new CommonMethod(Jason_Form_Activity.this,response.body().message);
                    }
                    else
                    {
                        new CommonMethod(Jason_Form_Activity.this,response.body().message);
                    }
                }
                else
                {
                    new CommonMethod(Jason_Form_Activity.this,"SERVER CODE ERROR : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                Log.d("RESPONSE_ERROR",t.getMessage());
            }
        });
    }

    private class doSignup extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd=new ProgressDialog(Jason_Form_Activity.this);
            pd.setMessage("Please wait....");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {

           HashMap<String,String> hashMap=new HashMap<>();
             hashMap.put("name",name.getText().toString());
            hashMap.put("emailid",email.getText().toString());
            hashMap.put("contact",contact.getText().toString());
            hashMap.put("gender",sGender);
            hashMap.put("city",sCity);
            hashMap.put("password",password.getText().toString());
            hashMap.put("dob",dob.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConsatntUrl.SIGNUP_URL,MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object=new JSONObject(s);
                if(object.getBoolean("Status")==true)
                {
                    new CommonMethod(Jason_Form_Activity.this,object.getString("Message"));
                }
                else
                {
                    new CommonMethod(Jason_Form_Activity.this,object.getString("Message"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                new CommonMethod(Jason_Form_Activity.this,e.getMessage());
            }
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}