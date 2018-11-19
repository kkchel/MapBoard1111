package com.example.kicheol.myapplication;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String id = "";
        String pw = "";

        Intent intent = getIntent();

        if (intent != null) {
            id = intent.getStringExtra("id");
            pw = intent.getStringExtra("pw");
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.getTabAt(0).setIcon(R.drawable.tap_img1);
        tabLayout.getTabAt(1).setIcon(R.drawable.tap_img2);
        tabLayout.getTabAt(2).setIcon(R.drawable.tap_img3);
        tabLayout.getTabAt(3).setIcon(R.drawable.tap_img4);
        tabLayout.getTabAt(4).setIcon(R.drawable.tap_img5);



         mPagerAdapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());



        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
