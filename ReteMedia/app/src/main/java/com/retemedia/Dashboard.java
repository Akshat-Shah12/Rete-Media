package com.retemedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);
        list = new ArrayList<String>();
        list.add("Chat");
        list.add("Work");
        list.add("Stats");
        list.add("Payment");
        prepareViewPager(viewPager,list);
        tabLayout.setupWithViewPager(viewPager);
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
            adapter.addFragment(new MainFragment(list.get(i)),(String) list.get(i));
        }
        vp.setAdapter(adapter);
    }
}