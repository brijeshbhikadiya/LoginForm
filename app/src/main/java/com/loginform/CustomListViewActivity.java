package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class CustomListViewActivity extends AppCompatActivity {

    ListView listView;

    String[] technologyArray={"Android","Java","IOS","php"};
    int[] imageArray={R.drawable.clock,R.drawable.calendar,R.drawable.backgroud_calandar,R.drawable.banti1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view);

        listView=findViewById(R.id.custom_list_view);

        CustomListAdapter adapter=new CustomListAdapter(CustomListViewActivity.this,technologyArray,imageArray);
        listView.setAdapter(adapter);
    }
}