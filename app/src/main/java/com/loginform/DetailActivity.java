package com.loginform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView name,price,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView=findViewById(R.id.detail_image);
        name=findViewById(R.id.detail_name);
        price=findViewById(R.id.detail_price);
        description=findViewById(R.id.detail_description);

        Bundle bundle=getIntent().getExtras();

        getSupportActionBar().setTitle(bundle.getString("NAME"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name.setText(bundle.getString("NAME"));
        price.setText(getResources().getString(R.string.price_symbol)+bundle.getString("PRICE"));
        description.setText(bundle.getString("DESC"));
        imageView.setImageResource(bundle.getInt("IMAGE"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}