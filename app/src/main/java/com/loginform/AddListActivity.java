package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AddListActivity extends AppCompatActivity {

    EditText editText;
    Button send;

    ListView listView;

    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        editText=findViewById(R.id.addlist_edit);
        send=findViewById(R.id.addlist_send);
        listView=findViewById(R.id.addlist_listview);

        arrayList =new ArrayList<>();
        ArrayAdapter adapter=new ArrayAdapter(AddListActivity.this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().trim().equalsIgnoreCase(""))
                {
                    editText.setError("Name Required");
                }
                else
                {
                    arrayList.add(editText.getText().toString());
                    //notify();not supported
                    //notifyAll();again not supported
                    adapter.notifyDataSetChanged(); //je add kara hoy text aene dekhadva mate
                    editText.setText("");  //aekavr text add karvi pachi message ma je lakhelu hoy e vay jashe

                }
            }
        });
    }
}