package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HasLoggedInActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_has_logged_in);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new AddLabelFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_add_label);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemid = item.getItemId();
        if(itemid == R.id.nav_add_label){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new AddLabelFragment()).commit();
        }
        else if(itemid == R.id.nav_add_photo){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new AddPhotoFragment()).commit();
        }
        else if(itemid == R.id.nav_gallery){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new GalleryFragment()).commit();
        }else if(itemid == R.id.nav_about){
            Intent intent = new Intent(HasLoggedInActivity.this, About.class);
            startActivity(intent);
        }else if(itemid == R.id.nav_logout){
            Toast.makeText(this, "Çıkış Yapıldı", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HasLoggedInActivity.this, MainActivity.class);
            startActivity(intent);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}