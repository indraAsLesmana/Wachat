package com.example.indraaguslesmana.wachat.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.indraaguslesmana.wachat.R;
import com.example.indraaguslesmana.wachat.Utility.PreferenceUtils;
import com.example.indraaguslesmana.wachat.WaChat;
import com.example.indraaguslesmana.wachat.adapter.SimpleFragmentPagerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference dbRefrenceUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager mMain_Pagger = (ViewPager) findViewById(R.id.main_pagger);

        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(
                getSupportFragmentManager(), this);

        mMain_Pagger.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mMain_Pagger);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainacitivty_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
                WaChat.getsAuth().signOut();
                PreferenceUtils.destroyUserSession();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
