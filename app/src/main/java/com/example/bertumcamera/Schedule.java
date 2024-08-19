package com.example.bertumcamera;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Spinner;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.util.Calendar;


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
    private Spinner service_name, service_time;

    int DIALOG_DATE = 1;
    int myYear = 2024;
    int myMonth = 8;
    int myDay = 18;
    private TextView button_picker_date, holder_picker_date;

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

        button_picker_date = findViewById(R.id.button_picker_date);
        holder_picker_date = findViewById(R.id.holder_picker_date);

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

        String [] autoservise_list = {"Выберите автосервис", "Автосервис #1", "Автосервис #2"};
        service_name =  findViewById(R.id.select_autoservice);
        ArrayAdapter<String> adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, autoservise_list);
        service_name.setAdapter(adapter);
        service_name.setSelection(0);

        String [] servise_time_list = {"Выберите время визита", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00" };
        Spinner service_time = findViewById(R.id.select_time);
        adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, servise_time_list);
        service_time.setAdapter(adapter);
        service_time.setSelection(0);

        button_picker_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                // on below line we are getting our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Schedule.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
//                                check date values
                                LocalDate today = LocalDate.now();
                                String month_str = String.valueOf(monthOfYear+1);
                                if(monthOfYear+1 < 10) month_str = '0'+ month_str;
                                String day_str = String.valueOf(dayOfMonth);
                                if(dayOfMonth < 10) day_str = '0'+ day_str;
                                LocalDate date = LocalDate.parse(String.valueOf(year)+"-"+ month_str +"-"+ day_str);
                                System.out.println(date);

                                if (date.compareTo(today) <= 0) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Schedule.this);
                                    alert.setMessage("На эту дату записи нет. Выберите, пожалуйста, дату начиная с завтрашнего дня");
                                    alert.setPositiveButton(android.R.string.ok, null);
                                    alert.show();
                                } else{
                                    String month_name = getMonthName(monthOfYear);
                                    String selected_date = dayOfMonth + " " + month_name + " " + year;
                                    holder_picker_date.setText(selected_date);
                                    button_picker_date.setText("Вы выбрали дату: " + selected_date);
                                }

                                // on below line we are setting date to our text view.

                            }
                        },
                        // on below line we are passing year, month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to display our date picker dialog.
                datePickerDialog.show();
            }

        });

        linkToSmetaDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 0);
                startActivity(new Intent(Schedule.this, SmetaActivity.class));
            }
        });
        linkToSmetaRepairs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 1);
                startActivity(new Intent(Schedule.this, SmetaActivity.class));
            }
        });

        ico_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Schedule.this, CartActivity.class));
            }
        });
        button_signup_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                после нажатия на кнопку записаться выдать сообщение с выбранными параметрами записи на сервис: Сервис, Дата, Время
                int selected_service = ((Spinner)findViewById(R.id.select_autoservice)).getSelectedItemPosition();
                int selected_time = ((Spinner)findViewById(R.id.select_time)).getSelectedItemPosition();
                String selected_service_name = String.valueOf(((Spinner)findViewById(R.id.select_autoservice)).getSelectedItem());
                String selected_service_date =((TextView) findViewById(R.id.holder_picker_date)).getText().toString();
                String selected_service_time = String.valueOf(((Spinner)findViewById(R.id.select_time)).getSelectedItem());
                String alert_message = "Вы выбрали: \n\n"+ selected_service_name +" \n\nДата визита: "+ selected_service_date +"\n\nВремя: " + selected_service_time;
                String alert_btn_title = "Отправить заявку";
                if(selected_service == 0){
                    alert_message = "Пожалуйста, выберите автосервис для обращения";
                    alert_btn_title = "Ok";
                }else if(selected_service_date.equals("") ){
                    alert_message = "Пожалуйста, выберите дату записи";
                    alert_btn_title = "Ok";
                }else if(selected_time == 0){
                    alert_message = "Пожалуйста, выберите время записи";
                    alert_btn_title = "Ok";
                }

                AlertDialog.Builder alert = new AlertDialog.Builder(Schedule.this);
                alert.setMessage(alert_message);
                alert.setPositiveButton(alert_btn_title, null);
                alert.show();
            }
        });

    }

    public String getMonthName(int mn_nmb){
        String [] months = {"Января","Февраля","Марта","Апреля","Мая","Июня","Июля","Августа","Сентября","Октября","Ноября","Декабря"};
        if(mn_nmb < 0 || mn_nmb > 11) mn_nmb = 0;
        return months[mn_nmb];
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