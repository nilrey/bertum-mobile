package com.example.bertumcamera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Schedule extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private Context context;
    private DrawerLayout drawerLayout;
    private ImageView ico_menu, map_service_place;
    private LinearLayout areaSignupRepairs ;
    private TextView
            cntTotalItems, sumRepairs, sumRepairsWork, linkToSmetaRepairs , linkToSmetaDetails;
    private ImageView ico_cart, button_signup_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout = findViewById(R.id.drawer_layout);
        ico_menu = findViewById(R.id.ico_menu);
        ico_menu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCloseNavigationDrawer(v);
                    }
                }
        );

        // контроллы управления и видимости
        areaSignupRepairs = findViewById(R.id.areaSignupRepairs);
        context = areaSignupRepairs.getContext();
        map_service_place = findViewById(R.id.map_service_place);
        ico_cart = findViewById(R.id.ico_cart);
        button_signup_service = findViewById(R.id.button_signup_service);

        // нижняя часть стоимость
        cntTotalItems = findViewById(R.id.cntTotalItems);
        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);
        linkToSmetaDetails = findViewById(R.id.linkToSmetaDetails);
        linkToSmetaRepairs = findViewById(R.id.linkToSmetaRepairs);

        ico_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Schedule.this, CartActivity.class));
            }
        });
        button_signup_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                после нажатия на кнопку записаться выдать сообщение с выбранными параметрами записи на сервис: Сервис, Время
                openDialog();

            }
        });

    }

    public void openDialog() {
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.show(getSupportFragmentManager(), "");
    }

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

    private int getSharedValueInt(String name){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        return sharedPreferences.getInt(name, 0);
    }
    private String getSharedValueStr(String name){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBottomPrices();
    }

    private void setBottomPrices(){
        sumRepairs.setText(String.valueOf(getSharedValueInt("cartSum")));
        sumRepairsWork.setText(String.valueOf(getSharedValueInt("cartRepairWork")));
        cntTotalItems.setText(String.valueOf(getSharedValueInt("cartCnt")));

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
        DataStore.setMenuItems(item, Schedule.this);
        return true;
    }

}