package com.example.bertumcamera;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final int GALLERY_REQ_CODE = 1000;
    private final int CAMERA_REQ_CODE = 100;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private ImageView ico_menu;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        if( getSharedValueInt("isFirstRun" ) == 0 ){
            startActivity(new Intent(BaseActivity.this, RegActivity.class));
        }
//        startActivity(new Intent(BaseActivity.this, CameraActivity.class));
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // make app fullscreen
        setSharedValueInt("tokenMainPage" , 123456789);

        ico_menu = findViewById(R.id.ico_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        ico_menu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCloseNavigationDrawer(v);
                    }
                }
        );

        //Request for camera runtime permission
        if(ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(BaseActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
/*
        linkToSmetaDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 0);
                startActivity(new Intent(BaseActivity.this, SmetaActivity.class));
            }
        });
        linkToSmetaRepairs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 1);
                startActivity(new Intent(BaseActivity.this, SmetaActivity.class));
            }
        });*/
    }

    public void openCloseNavigationDrawer(View view) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DataStore.setMenuItems(item, BaseActivity.this);
        return true;
    }
/*
    @Override
    public void onResume() {
        super.onResume();
        cntPhoto.setText(String.valueOf( getSharedValueInt(Const.CNT_PHOTO) ));
        sumRepairs.setText(String.valueOf( getSharedValueInt(Const.CART_SUM) ));
        cntTotalItems.setText(String.valueOf( getSharedValueInt(Const.CART_CNT) ));
        sumRepairsWork.setText(String.valueOf( getSharedValueInt(Const.CART_REPAIR_WORK) ));
    }*/

    private void setSharedValueInt(String name, int value){
        ed = getSharedPreferencesEditor();
        ed.putInt(name, value);
        ed.commit();
    }

    private void setSharedValueStr(String name, String value){
        ed = getSharedPreferencesEditor();
        ed.putString(name, value);
        ed.commit();
    }

    private SharedPreferences.Editor getSharedPreferencesEditor(){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor;
    }
    public int getSharedValueInt(String name){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        return sharedPreferences.getInt(name, 0);
    }
    public String getSharedValueStr(String name){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }
}
