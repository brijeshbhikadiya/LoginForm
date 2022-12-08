package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

public class Pratice_List_Activity extends AppCompatActivity {

    ListView listView;

    String[] ecommorcetechnology={"clothes","pen","t-shirt"};
    int[] imagearray={R.drawable.banti1,R.drawable.banti1,R.drawable.banti1,};
    String[] descriptionarray={"red","white","yellow"};
    String[] pricearray={"12","11","13"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pratice_list);

        listView=findViewById(R.id.pratice_list_view);

        CustomAdapter adpter=new CustomAdapter(Pratice_List_Activity.this,ecommorcetechnology,imagearray,descriptionarray,pricearray);
        listView.setAdapter(adpter);
    }
}