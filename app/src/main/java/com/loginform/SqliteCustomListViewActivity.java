package com.loginform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SqliteCustomListViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<SqliteCustomList> arrayList;
    SQLiteDatabase db;

    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_custom_list_view);

        getSupportActionBar().setTitle("Database List");   //name change for activity..
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = openOrCreateDatabase("LoginForm",MODE_PRIVATE,null);
        String CreateTable="create table if not exists record(STUDENTNAME VARCHAR(255),EMAILID VARCHAR(100),CONTACTNO INT(10))";
        db.execSQL(CreateTable);

        add = findViewById(R.id.sqlite_custom_listview_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new CommonMethod(SqliteCustomListViewActivity.this,SqliteDatabaseActivity.class);

                Intent intent = new Intent(SqliteCustomListViewActivity.this,SqliteDatabaseActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("type","ADD");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.Sqllite_custom_RecyclerView);

        //Recycler view as a list
        recyclerView.setLayoutManager(new LinearLayoutManager(SqliteCustomListViewActivity.this));

       //Recyclerview as a grid 3 row
       // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.HORIZONTAL));

        //Recyclerview as a grid 3 column
       // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        //Recyclerview as a horizontal
       // recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //home activity ma java mateno code...
        int id =item.getItemId();
        if(id==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {  //home per aavshe to direct application finish thay jase jo back karshe to
        finishAffinity();
        //super.onBackPressed();
    }

    @Override
    protected void onResume() {  //it is used for if you can input dynamic data inputs for your users....
        super.onResume();
        setData();
    }

    private void setData() {
        String SelectQuery="select * from record";
        Cursor cursor=db.rawQuery(SelectQuery,null);
        arrayList = new ArrayList<>(); //excel jevu lavva mate change karelo code..
        if(cursor.getCount()>=0)
        {
            if(cursor.moveToFirst())
            {
                //arrayList = new ArrayList<>();
                while (cursor.moveToNext())
                {
                    SqliteCustomList list=new SqliteCustomList();
                    list.setName(cursor.getString(0));
                    list.setEmail(cursor.getString(1));
                    list.setContactno(cursor.getString(2));
                    arrayList.add(list);
                }
                SqliteCustomAdapter adapter = new  SqliteCustomAdapter(SqliteCustomListViewActivity.this,arrayList);
                recyclerView.setAdapter(adapter);
            }
        }
        else
        {
            new CommonMethod(SqliteCustomListViewActivity.this,"Data Not Available");
        }
    }
}
