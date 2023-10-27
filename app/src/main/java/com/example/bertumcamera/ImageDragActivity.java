package com.example.bertumcamera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Method;

public class ImageDragActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private RelativeLayout blockCarRF_x033, blockCarRF_x050, blockCarFF_x033, blockCarLF_x033;
    private ImageView layoutCarRF_x033, doorRightFrontRF_x033, wingRightFrontRF_x033, bumperRightFrontRF_x033,
            layoutCarRF_x050, doorRightFrontRF_x050, wingRightFrontRF_x050, bumperRightFrontRF_x050;
    private ImageButton arrowPlus, arrowMinus, arrowLeft, arrowRight;
    private TextView respView;
    private String msgDetailSelected, sideView=Const.VIEW_RIGHT_FRONT;
    float x, y, dx, dy;
    private int bias=0, scaleValue = 1;
//    private int mTouchSlop;
//    private boolean mIsScrolling;

    long durationTouch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_drag);

        calculateBias();
        initAllSideViews();
        hideAllSideViews();
        setItemsListBackPage();

        layoutCarRF_x033 = findViewById(R.id.layoutCarRF_x033);
        doorRightFrontRF_x033 = findViewById(R.id.doorRightFrontRF_x033);
        wingRightFrontRF_x033 = findViewById(R.id.wingRightFrontRF_x033);
        bumperRightFrontRF_x033 = findViewById(R.id.bumperRightFrontRF_x033);

        layoutCarRF_x050 = findViewById(R.id.layoutCarRF_x050);
        doorRightFrontRF_x050 = findViewById(R.id.doorRightFrontRF_x050);
        wingRightFrontRF_x050 = findViewById(R.id.wingRightFrontRF_x050);
        bumperRightFrontRF_x050 = findViewById(R.id.bumperRightFrontRF_x050);

        arrowPlus = findViewById(R.id.arrow_plus);
        arrowMinus = findViewById(R.id.arrow_minus);
        arrowRight = findViewById(R.id.arrow_right);
        arrowLeft = findViewById(R.id.arrow_left);
        msgDetailSelected  =  getResources().getString(R.string.msg_detail_selected);

        respView = findViewById(R.id.respView);

        genScaleOnClick(arrowMinus, 1);
        genScaleOnClick(arrowPlus, 2);

        if(getBias() > 0){
            setDetailPosition(wingRightFrontRF_x033, 160, 111);
            setDetailPosition(doorRightFrontRF_x033, 106, 72);
            setDetailPosition(bumperRightFrontRF_x033, 200, 161);
        }

        arrowRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            hideAllSideViews();
            if(sideView == Const.VIEW_RIGHT_FRONT){
                sideView = Const.VIEW_FRONT_FRONT;
                activateSvBlock();
            } else if (sideView == Const.VIEW_FRONT_FRONT) {
                sideView = Const.VIEW_LEFT_FRONT;
                activateSvBlock();
            } else if (sideView == Const.VIEW_LEFT_FRONT) {
                activateSvBlock();
            }
            }
        });

        arrowLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                hideAllSideViews();
                if(sideView == Const.VIEW_RIGHT_FRONT){
                    activateSvBlock();
                } else if (sideView == Const.VIEW_FRONT_FRONT) {
                    sideView = Const.VIEW_RIGHT_FRONT;
                    activateSvBlock();
                } else if (sideView == Const.VIEW_LEFT_FRONT) {
                    sideView = Const.VIEW_FRONT_FRONT;
                    activateSvBlock();
                }
            }
        });

        genImageViewOnClick(doorRightFrontRF_x033, "6RU831055J", "Передняя правая дверь");
        genImageViewOnClick(doorRightFrontRF_x050, "6RU831055J", "Передняя правая дверь");
        genImageViewOnClick(wingRightFrontRF_x033, "6RU821106C", "Переднее правое крыло");
        genImageViewOnClick(wingRightFrontRF_x050, "6RU821106C", "Переднее правое крыло");
        genImageViewOnClick(bumperRightFrontRF_x033, "6RU807221A", "Бампер передний");
        genImageViewOnClick(bumperRightFrontRF_x050, "6RU807221A", "Бампер передний");

        genImageViewOnTouch(layoutCarRF_x050);
        genImageViewOnTouch(doorRightFrontRF_x033);
        genImageViewOnTouch(doorRightFrontRF_x050);
        genImageViewOnTouch(wingRightFrontRF_x033);
        genImageViewOnTouch(wingRightFrontRF_x050);
        genImageViewOnTouch(bumperRightFrontRF_x033);
        genImageViewOnTouch(bumperRightFrontRF_x050);

        layoutCarRF_x050.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            }
        });
    }

    private void calculateBias(){
        Resources r = this.getResources();
        int currentWidth = Math.round(r.getDisplayMetrics().widthPixels / r.getDisplayMetrics().density);
        setBias(Math.round( (Const.SETS_WIDTH_KOEF_DEFAULT - currentWidth) / 2 ));
    }

    private void setItemsListBackPage(){
        setSharedValueStr("ItemsListBackPage", "ImageDragActivity");
    }
    private void setBias(int val){
        this.bias = val;
    }

    private int getBias(){
        return this.bias;
    }

    private void setDetailPosition(ImageView elem, int left, int top){
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        int biasLocal = getBias();
        elemParams.setMargins(convertDpToPixels(ImageDragActivity.this, left - biasLocal ),
                convertDpToPixels(ImageDragActivity.this, top ), 0, 0);
        elem.setLayoutParams(elemParams);
    }
    private void setSideViewToMiddle(){
        int middleBias = -250;
        if(sideView == Const.VIEW_RIGHT_FRONT){
            blockCarRF_x050.setX(middleBias);
        } else if (sideView == Const.VIEW_FRONT_FRONT) {

        } else if (sideView == Const.VIEW_LEFT_FRONT) {

        }
    }
    private void genScaleOnClick(ImageView elem, final int newScale){
        elem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                scaleValue = newScale;
                hideAllSideViews();
                activateSvBlock();
                if(scaleValue == 2){
                    setSideViewToMiddle();
                }
            }
        });
    }

    private void genImageViewOnClick(ImageView elem, final String da, final String dt){
        elem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(durationTouch < 200) {
                    setSharedValueStr("detail_article", da );
                    setSharedValueStr("detail_title", dt );
                    setSharedValueStr("detail_title_rus", dt );
                    sendRequestToProxy();
                }
                durationTouch = 0;
            }
        });
    }

    private void genImageViewOnTouch(ImageView elem){
        elem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                doMoveAction(event);
                return false;
            }
        });
    }

    private void initAllSideViews(){
        blockCarRF_x050 = findViewById(R.id.blockCarRF_x050);
        blockCarRF_x033 = findViewById(R.id.blockCarRF_x033);
        blockCarFF_x033 = findViewById(R.id.blockCarFF_x033);
        blockCarLF_x033 = findViewById(R.id.blockCarLF_x033);
    }

    private void hideAllSideViews(){
        hideSideView(blockCarRF_x050);
        hideSideView(blockCarRF_x033);
        hideSideView(blockCarFF_x033);
        hideSideView(blockCarLF_x033);
    }
    
    private void hideSideView(RelativeLayout elem){
        elem.setVisibility(View.GONE);
    }
    
    private void activateSvBlock(){
        // при заданном ракурсе, определим приближение
        if(sideView == Const.VIEW_RIGHT_FRONT){
            setVisibilityOnScaleValue(blockCarRF_x050, blockCarRF_x033);
        } else if (sideView == Const.VIEW_FRONT_FRONT) {
            setVisibilityOnScaleValue(blockCarFF_x033, blockCarFF_x033);
        } else if (sideView == Const.VIEW_LEFT_FRONT) {
            setVisibilityOnScaleValue(blockCarLF_x033, blockCarLF_x033);
        }
    }

    private void setVisibilityOnScaleValue(RelativeLayout elem, RelativeLayout elem_default){
        if(scaleValue == 2){
            elem.setVisibility(View.VISIBLE);
        }else{
            elem_default.setVisibility(View.VISIBLE);
            setDetailPosition(wingRightFrontRF_x033, 160, 111);
            setDetailPosition(doorRightFrontRF_x033, 106, 72);
            setDetailPosition(bumperRightFrontRF_x033, 200, 161);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        blockCarRF_x050.setVisibility(View.GONE);
        blockCarRF_x033.setVisibility(View.VISIBLE);
    }

    private void setDetailParams(ImageView elem, float scalingFactor, int marLeft, int marTop, int marRight ){
        elem.setX(0.0F);
        elem.setY(0.0F);
        elem.setScaleX(scalingFactor);
        elem.setScaleY(scalingFactor);
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        elemParams.setMargins(convertDpToPixels(ImageDragActivity.this, marLeft),
                convertDpToPixels(ImageDragActivity.this, marTop), marRight, 0);
        elem.setLayoutParams(elemParams);
    }

    private void sendRequestToProxy(){
        startActivity(new Intent(ImageDragActivity.this, ProxyActivity.class));
    }

    private void doMoveAction(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
        }else if (event.getAction() == MotionEvent.ACTION_UP) {
            durationTouch = event.getEventTime() - event.getDownTime();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            dx = event.getX() - x;
            dy = event.getY() - y;
            if(sideView == Const.VIEW_RIGHT_FRONT){
                layoutCarRF_x050.setX(layoutCarRF_x050.getX() + dx);
                layoutCarRF_x050.setY(layoutCarRF_x050.getY() + dy);
                doorRightFrontRF_x050.setX(doorRightFrontRF_x050.getX() + dx);
                doorRightFrontRF_x050.setY(doorRightFrontRF_x050.getY() + dy);
                wingRightFrontRF_x050.setX(wingRightFrontRF_x050.getX() + dx);
                wingRightFrontRF_x050.setY(wingRightFrontRF_x050.getY() + dy);
                bumperRightFrontRF_x050.setX(bumperRightFrontRF_x050.getX() + dx);
                bumperRightFrontRF_x050.setY(bumperRightFrontRF_x050.getY() + dy);
            } else if (sideView == Const.VIEW_FRONT_FRONT) {
                setVisibilityOnScaleValue(blockCarFF_x033, blockCarFF_x033);
            } else if (sideView == Const.VIEW_LEFT_FRONT) {
                setVisibilityOnScaleValue(blockCarLF_x033, blockCarLF_x033);
            }
            x = event.getX();
            y = event.getY();
        }
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

    private int convertDpToPixels(Context mContext, int valueDp) {
        Resources r = mContext.getResources();
        int valuePx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                valueDp,
                r.getDisplayMetrics()
        );
        return valuePx;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ImageDragActivity.this, MainActivity.class));
    }
} // \ImageDragActivity