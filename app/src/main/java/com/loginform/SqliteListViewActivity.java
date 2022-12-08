package com.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SqliteListViewActivity extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase db;
    ArrayList<String> arrayList;
    ArrayList<String> contactarrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_list_view);

        db = openOrCreateDatabase("LoginForm",MODE_PRIVATE,null);
        String CreateTable="create table if not exists record(STUDENTNAME VARCHAR(255),EMAILID VARCHAR(100),CONTACTNO INT(10))";
        db.execSQL(CreateTable);

        listView = findViewById(R.id.Sqlite_listView);

        String SelectQuery="select * from record";
        Cursor cursor=db.rawQuery(SelectQuery,null);
        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                arrayList = new ArrayList<>();
                contactarrayList=new ArrayList<>();
                while (cursor.moveToNext())
                {
                    String sStudentName= "STUDENT NAME : "+cursor.getString(0);
                    String sEmailId= "Email ID : "+cursor.getString(1);
                    String sContactNo= "CONTACT NUMBER : "+cursor.getString(2);

                    arrayList.add(sStudentName+"\n"+sEmailId+"\n"+sContactNo);
                    contactarrayList.add(cursor.getString(2));
                }
                ArrayAdapter adapter = new ArrayAdapter(SqliteListViewActivity.this, android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String DeleteQuery = "delete from record WHERE CONTACTNO='"+contactarrayList.get(i)+"'";
                        db.execSQL(DeleteQuery);
                        new CommonMethod(SqliteListViewActivity.this,"Student Record"+contactarrayList.get(i)+" Deleted successfullly");
                        arrayList.remove(i);
                        contactarrayList.get(i);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });

            }
            else
            {
                new CommonMethod(SqliteListViewActivity.this,"Data Not Available");
            }
        }



    }
}