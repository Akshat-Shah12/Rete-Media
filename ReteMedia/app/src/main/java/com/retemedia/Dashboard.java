package com.retemedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<String> list;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);
        type = getIntent().getStringExtra("type");
        list = new ArrayList<String>();
        list.add("Chat");
        list.add("Work");
        list.add("Stats");
        list.add("Payment");
        prepareViewPager(viewPager,list);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void Signout(View view) {
        new File(getApplicationContext().getFilesDir(),"user.txt").delete();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    public void createUser(View view) {
        startActivity(new Intent(this,CreateUserActivity.class));
        overridePendingTransition(R.anim.hover_in,R.anim.no_anim);
    }

    class MainAdapter extends FragmentPagerAdapter{

        List<String> arrayList = new ArrayList<String>();;
        List<Fragment> fragmentList = new ArrayList<Fragment>();;

        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment, String title)
        {
            fragmentList.add(fragment);
            arrayList.add(title);
        }
        public List<String> getArrayList()
        {
            return arrayList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return arrayList.get(position);
        }
    }
    private void prepareViewPager(ViewPager vp,List<String> stringList)
    {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());
        for(int i = 0; i < list.size(); i++)
        {
            Bundle bundle = new Bundle();
            if(list.get(i).equals("Chat")) adapter.addFragment(new ChatFragment(getApplicationContext(),this),list.get(i));
            else if(list.get(i).equals("Payment")) adapter.addFragment(new Payment(getApplicationContext(),this),list.get(i));
            else if(list.get(i).equals("Stats")) adapter.addFragment(new Stats(getApplicationContext()),list.get(i));
            else adapter.addFragment(new MainFragment(list.get(i)),(String) list.get(i));
        }
        vp.setAdapter(adapter);
    }

}