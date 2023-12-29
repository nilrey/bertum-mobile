package com.example.bertumcamera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private ImageView ico_menu, link_step_1,  link_step_2, link_step_3, link_step_4, link_step_5, link_step_6;
    private EditText input_phone;
    private View regstep1, regstep2, regstep3, regstep4, regstep5, regstep6;
    private String lastChar = " ";
    float x, y, dx, dy;
    long cntTouchImpulse = 0, cntSlide = 1, cntSlideLimit = 7;
    private ImageView slide_place;
    private LinearLayout slider_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        slider_holder = findViewById(R.id.slider_holder);
        link_step_1 = findViewById(R.id.link_step_1);
        link_step_2 = findViewById(R.id.link_step_2);
        link_step_3 = findViewById(R.id.link_step_3);
        link_step_4 = findViewById(R.id.link_step_4);
        link_step_5 = findViewById(R.id.link_step_5);
        link_step_6 = findViewById(R.id.link_step_6);
        regstep1 = findViewById(R.id.regstep1);
        regstep2 = findViewById(R.id.regstep2);
        regstep3 = findViewById(R.id.regstep3);
        regstep4 = findViewById(R.id.regstep4);
        regstep5 = findViewById(R.id.regstep5);
        regstep6 = findViewById(R.id.regstep6);

        slide_place = findViewById(R.id.slide_place);


        slide_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        slide_place.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                doMoveAction(event);
                return false;
            }
        });
//        genImageViewOnTouch(slide_place);

//        try {
//            Thread.sleep(2000); // delay for 2 second
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        setStepsOnclick();

        link_step_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSharedValueInt("isFirstRun" , 1);
                startActivity(new Intent(RegActivity.this, MainActivity.class));
            }
        });
        input_phone = findViewById(R.id.input_phone);
        input_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int digits = input_phone.getText().toString().length();
                if (digits > 1){
                    lastChar = input_phone.getText().toString().substring(digits-1);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int digits = input_phone.getText().toString().length();
                if (!lastChar.equals("-")) {
                    if (digits == 3 || digits == 7) {
                        input_phone.append("-");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

    }

    public void setStepsOnclick(){

        link_step_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStep(regstep2);
            }
        });
        link_step_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStep(regstep3);
            }
        });
//        link_step_3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setStep(regstep4);
//            }
//        });
//        link_step_4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setStep(regstep5);
//            }
//        });
//        link_step_5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setStep(regstep6);
//            }
//        });

    }

    public void setStep(View elem){
        hideAllSteps();
        elem.setVisibility(View.VISIBLE);
    }
    public void hideAllSteps(){
        regstep1.setVisibility(View.GONE);
        regstep2.setVisibility(View.GONE);
        regstep3.setVisibility(View.GONE);
//        regstep4.setVisibility(View.GONE);
//        regstep5.setVisibility(View.GONE);
//        regstep6.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RegActivity.this, RegActivity.class));
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

    private void doMoveAction(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            cntTouchImpulse = 0;
            x = event.getX();
            y = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            ++cntTouchImpulse;
            if(cntTouchImpulse < 2 ) {
                if( ( x - event.getX() ) > 0) { // next slide
                    ++cntSlide;
                    if(cntSlide > cntSlideLimit) cntSlide = cntSlideLimit;
                }else{ // prev slide
                    --cntSlide;
                    if(cntSlide < 1) cntSlide = 1;
                }
                int detailImageId = getResources().getIdentifier("slide_"+String.valueOf(cntSlide), "drawable", getPackageName());
                if(detailImageId > 0 ){
                    slide_place.setImageResource(detailImageId);
                }

                int slidePointActiveId = getResources().getIdentifier("reg_step_circle_red", "drawable", getPackageName());
                int slidePointInactiveId = getResources().getIdentifier("reg_step_circle_grey", "drawable", getPackageName());

                for (int i = 1 ; i <= cntSlideLimit ; i++){
                    int ivSlidePointId = getResources().getIdentifier("slide_point_"+ String.valueOf(i),
                            "id", getPackageName());
                    ImageView ivSlidePoint = findViewById(ivSlidePointId);
                    if(i == cntSlide ){
                        ivSlidePoint.setImageResource(slidePointActiveId);
                    }else{
                        ivSlidePoint.setImageResource(slidePointInactiveId);
                    }
                }
//                Toast.makeText(RegActivity.this, String.valueOf(cntSlide), Toast.LENGTH_SHORT).show();
            }
        }
    }

}