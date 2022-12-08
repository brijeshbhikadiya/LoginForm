package com.loginform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SqliteDatabaseActivity extends AppCompatActivity {

    EditText name,contact,email;
    Button insert,show,customshow;

    String emailpattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    SQLiteDatabase db;

    String sType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_database);

        db = openOrCreateDatabase("LoginForm",MODE_PRIVATE,null);
        String CreateTable="create table if not exists record(STUDENTNAME VARCHAR(255),EMAILID VARCHAR(100),CONTACTNO INT(10))";
        db.execSQL(CreateTable);

        name = findViewById(R.id.Sqlite_database_name);
        contact = findViewById(R.id.Sqlite_database_contact_no);
        email = findViewById(R.id.Sqlite_database_Email);

        insert = findViewById(R.id.Sqlite_database_insert);

        Bundle bundle = getIntent().getExtras();
        sType = bundle.getString("type");
        if(sType.equalsIgnoreCase("ADD"))
        {
            getSupportActionBar().setTitle("ADD DATA");
            insert.setText("Insert");
            contact.setEnabled(true);
        }
        else
        {
            getSupportActionBar().setTitle("Update Data");
            insert.setText("Update");
            name.setText(bundle.getString("NAME"));
            email.setText(bundle.getString("EMAIL"));
            contact.setText(bundle.getString("CONTACT"));
            contact.setEnabled(false);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        show = findViewById(R.id.Sqlite_database_show);
        customshow = findViewById(R.id.Sqlite_database_Custom_show);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SqliteDatabaseActivity.this,SqliteListViewActivity.class);
            }
        });

        customshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SqliteDatabaseActivity.this,SqliteCustomListViewActivity.class);
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equalsIgnoreCase(""))
                {
                    name.setError("Name required");
                }
                else if(contact.getText().toString().equalsIgnoreCase(""))
                {
                    contact.setError("ContactNo required");
                }
                else if(contact.getText().toString().length()<10)
                {
                    contact.setError("Only 10 number required");
                }
                else if(email.getText().toString().trim().equalsIgnoreCase(""))
                {
                    email.setError("EmailId Required");
                }
                else if(!email.getText().toString().matches(emailpattern))
                {
                    email.setError("Enter a valid email pattern");
                }
                else
                {
                    if(sType.equalsIgnoreCase("ADD"))
                    {
                    String selectquery= "select * from record WHERE EMAILID='"+email.getText().toString()+"'OR CONTACTNO='"+contact.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(selectquery,null);

                    if(cursor.getCount()>0)
                    {
                        new CommonMethod(SqliteDatabaseActivity.this,"EMAIL ID/CONTACT NO already register");
                    }
                    else{
                    String Insertquery = "insert into record VALUES('" + name.getText().toString() + "','" + email.getText().toString() + "','" + contact.getText().toString() + "')";
                    db.execSQL(Insertquery);
                    new CommonMethod(SqliteDatabaseActivity.this, "Insert Successfully");
                    cleardata();
                     }
                }
                    else
                    {
                        String updateQuery="UPDATE record SET STUDENTNAME='"+name.getText().toString()+"',EMAILID='"+email.getText().toString()+"' WHERE CONTACTNO='"+contact.getText().toString()+"'";
                        db.execSQL(updateQuery);
                        new CommonMethod(SqliteDatabaseActivity.this,"Update Successfully");
                        cleardata();
                    }
                }
            }

            private void cleardata() {
                name.setText("");
                contact.setText("");
                email.setText("");
                name.requestFocus();
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }
            return super.onOptionsItemSelected(item);
    }
}