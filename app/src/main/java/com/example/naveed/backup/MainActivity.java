package com.example.naveed.backup;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements msgFragment.OnFragmentInteractionListener{

    private ViewPager myViewPager;
    private TabLayout myTabs;
    private TabPagerAdapter myTabPagerAdapter;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        myTabs = findViewById(R.id.tabs);
        myTabs.setupWithViewPager(myViewPager);
        myTabs.addTab(myTabs.newTab().setText("Tab 1"));
        myTabs.addTab(myTabs.newTab().setText("Tab 2"));
        myTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        myViewPager = findViewById(R.id.pager);

        final TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(),myTabs.getTabCount());
        myViewPager.setAdapter(adapter);
        myViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(myTabs));

        myTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser == null){
            sendUserToLogin();
        }
    }

    private void sendUserToLogin() {

        Intent loginIntent = new Intent(MainActivity.this, Login.class);
        startActivity(loginIntent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId()== R.id.Logout){
            mAuth.signOut();
            sendUserToLogin();
        }

        return true;

    }
}
