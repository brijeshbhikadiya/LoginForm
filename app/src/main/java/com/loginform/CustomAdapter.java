package com.loginform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String[] ecommorcetechnology;
    int[] imagearray;
    String[] descriptionarray;
    String[] pricearray;

    public CustomAdapter(Pratice_List_Activity pratice_list_activity, String[] ecommorcetechnology, int[] imagearray, String[] descriptionarray,String[] pricearray) {
        context=pratice_list_activity;
        this.ecommorcetechnology=ecommorcetechnology;
        this.imagearray=imagearray;
        this.descriptionarray=descriptionarray;
        this.pricearray=pricearray;
    }

    @Override
    public int getCount() {
        return ecommorcetechnology.length;
    }

    @Override
    public Object getItem(int i) {
        return ecommorcetechnology[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_pratice,null);

        ImageView imageView=view.findViewById(R.id.custom_image);
        TextView textView1=view.findViewById(R.id.custom_pratice_name);
        TextView textView2=view.findViewById(R.id.custom_pratice_desc);
        TextView textView3=view.findViewById(R.id.custom_pratice_price);

        textView1.setText(ecommorcetechnology[i]);
        textView2.setText(descriptionarray[i]);
        textView3.setText(pricearray[i]);
        imageView.setImageResource(imagearray[i]);

        return view;
    }
}
