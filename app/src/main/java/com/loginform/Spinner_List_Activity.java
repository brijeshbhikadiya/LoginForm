package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Spinner_List_Activity extends AppCompatActivity {
    Spinner spinner;
    //String[] cityArray={"gandhinagar","rajkot","surat","Ahamadavad","junagath","gandhinagar","rajkot","surat","Ahamadavad","junagath"};

    ArrayList<String> cityarray;

    AutoCompleteTextView autoCompleteTextView;
    MultiAutoCompleteTextView multiAutoCompleteTextView;

    EditText editText;
    Button button;

    //ListView listView;  list and grid both are simmilar so you can chosse of any one of both of them. main use are manu main..
    GridView gridView;     //phone ni side ma manu mate apray chee..
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_list);
        spinner=findViewById(R.id.spinner);
        editText=findViewById(R.id.spinner_edittext);
        button=findViewById(R.id.spinner_button);

        cityarray =new ArrayList<>();
//        cityarray.add("Ahmedabad");
//        cityarray.add("Anand");
//        cityarray.add("Nadiad");
//        cityarray.add("Surat");
//        cityarray.add("Gandhinagar");
//        cityarray.add("Rajkot");
//
//        cityarray.add("test");
//        cityarray.add("mahesava");
//
//        cityarray.remove(6);
//        cityarray.set(6,"Mahesana");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=editText.getText().toString();

                cityarray.add(text);
                gridView.setAdapter(gridView.getAdapter());

            }
        });


        ArrayAdapter adapter=new ArrayAdapter(Spinner_List_Activity.this, android.R.layout.simple_list_item_1,cityarray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //new CommonMethod(Spinner_List_Activity.this,CityArray[i]);
                new CommonMethod(Spinner_List_Activity.this,cityarray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        listView=findViewById(R.id.Spinner_listview);
//        ArrayAdapter listAapter=new ArrayAdapter(Spinner_List_Activity.this, android.R.layout.simple_list_item_1,CityArray);
//        listView.setAdapter(listAapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                new CommonMethod(Spinner_List_Activity.this,CityArray[i]);
//            }
//        });

        gridView=findViewById(R.id.spinner_gridview);
        ArrayAdapter gridAapter=new ArrayAdapter(Spinner_List_Activity.this, android.R.layout.simple_list_item_1,cityarray);
        gridView.setAdapter(gridAapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //new CommonMethod(Spinner_List_Activity.this,CityArray[i]);
                String s=adapterView.getItemAtPosition(i).toString();
                new CommonMethod(Spinner_List_Activity.this,s);
            }
        });

        autoCompleteTextView=findViewById(R.id.spinner_autocomplete);
        ArrayAdapter autoadapter=new ArrayAdapter(Spinner_List_Activity.this, android.R.layout.simple_list_item_1,cityarray);
        autoCompleteTextView.setAdapter(autoadapter);
        autoCompleteTextView.setThreshold(1);

        multiAutoCompleteTextView=findViewById(R.id.spinner_multiautocomplete);
        ArrayAdapter multiautoadapter=new ArrayAdapter(Spinner_List_Activity.this, android.R.layout.simple_list_item_1,cityarray);
        multiAutoCompleteTextView.setAdapter(multiautoadapter);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());




    }
}