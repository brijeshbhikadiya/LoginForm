package com.loginform;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class TabDemoActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_demo);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.tab_viewpager);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        //Activity To Activity => Intent
        //Activity To Fragment => getSupportFragmentManager()

        viewPager.setAdapter(new TabDemoAdapter(getSupportFragmentManager()));
    }

    private class TabDemoAdapter extends FragmentPagerAdapter {
                public  TabDemoAdapter(FragmentManager supportFragmentManager){
                    super(supportFragmentManager);  //costom adpater banavu ateale aa lakhvu pade extends class karvo pade fragmentpageradpater valo..
                }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case 0:
                            return "chat";
                        case 1:
                            return "status";
                        case 2:
                            return "call";
//                        default:
//                            return "demo"; //default use karvi to return super valu no aave.
                    }
            return super.getPageTitle(position);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ChatFragment();
                case 1:
                    return new StatusFragment();
                case 2:
                    return new CallFragment();
//                default:
//                    return new DemoFragment(); //bov badha leva hoy to chat status call demo etc..to defalut lai levu only samjava lidhu tu
            }     //default use karvi to return null no aave.
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}