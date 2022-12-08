package com.loginform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {

    Context context;
    String[] technologyArray;
    int[] imageArray;

    public CustomListAdapter(CustomListViewActivity customListViewActivity, String[] technologyArray, int[] imageArray) {
        context=customListViewActivity;
        this.technologyArray=technologyArray;
        this.imageArray=imageArray;
    }

    @Override
    public int getCount() {
        return technologyArray.length;
    }

    @Override
    public Object getItem(int i) {
        return technologyArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_list,null);

        ImageView imageView=view.findViewById(R.id.custom_list_image);
        TextView name=view.findViewById(R.id.custom_list_name);

        name.setText(technologyArray[i]);
        imageView.setImageResource(imageArray[i]);

        return view;
    }
}
