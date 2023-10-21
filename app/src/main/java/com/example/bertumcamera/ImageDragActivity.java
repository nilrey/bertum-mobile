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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ImageDragActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private RelativeLayout blockCarRF_x033, blockCarRF_x050, blockCarFF_x033, blockCarLF_x033;
    private ImageView layoutCarRF_x033, doorRightFrontRF_x033, wingRightFrontRF_x033,
            layoutCarRF_x050, doorRightFrontRF_x050, wingRightFrontRF_x050;
    private ImageButton arrowPlus, arrowMinus, arrowLeft, arrowRight;
    private TextView respView;
    private String msgDetailSelected, vewRacurs="right_front";
    float x, y;
    float dx, dy;
    private int scaleValue = 1;
    private int mTouchSlop;
    private boolean mIsScrolling;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_drag);

        ViewConfiguration vc = ViewConfiguration.get( ImageDragActivity.this );
        mTouchSlop = vc.getScaledTouchSlop();

        blockCarRF_x033 = findViewById(R.id.blockCarRF_x033);
        layoutCarRF_x033 = findViewById(R.id.layoutCarRF_x033);
        doorRightFrontRF_x033 = findViewById(R.id.doorRightFrontRF_x033);
        wingRightFrontRF_x033 = findViewById(R.id.wingRightFrontRF_x033);
        blockCarRF_x050 = findViewById(R.id.blockCarRF_x050);
        layoutCarRF_x050 = findViewById(R.id.layoutCarRF_x050);
        doorRightFrontRF_x050 = findViewById(R.id.doorRightFrontRF_x050);
        wingRightFrontRF_x050 = findViewById(R.id.wingRightFrontRF_x050);

        blockCarFF_x033 = findViewById(R.id.blockCarFF_x033);
        blockCarLF_x033 = findViewById(R.id.blockCarLF_x033);

        arrowPlus = findViewById(R.id.arrow_plus);
        arrowMinus = findViewById(R.id.arrow_minus);
        arrowRight = findViewById(R.id.arrow_right);
        arrowLeft = findViewById(R.id.arrow_left);
        msgDetailSelected  =  getResources().getString(R.string.msg_detail_selected);


        respView = findViewById(R.id.respView);

        arrowMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                scaleValue = 1;

                blockCarRF_x050.setVisibility(View.GONE);
                blockCarRF_x033.setVisibility(View.GONE);
                blockCarFF_x033.setVisibility(View.GONE);
                blockCarLF_x033.setVisibility(View.GONE);
                blockCarRF_x033.setVisibility(View.VISIBLE);
            }
        });


        arrowPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                scaleValue = 2;
                blockCarRF_x050.setVisibility(View.GONE);
                blockCarRF_x033.setVisibility(View.GONE);
                blockCarFF_x033.setVisibility(View.GONE);
                blockCarLF_x033.setVisibility(View.GONE);
                blockCarRF_x050.setVisibility(View.VISIBLE);
                /*
                if( layoutCarRF_x033.getScaleX() < 2 ) {
                    float scalingFactor = 2f;
                    layoutCarRF_x033.setScaleType(ImageView.ScaleType.CENTER);
                    layoutCarRF_x033.setX(0.0F);
                    layoutCarRF_x033.setY(0.0F);
                    layoutCarRF_x033.setScaleX(scalingFactor);
                    layoutCarRF_x033.setScaleY(scalingFactor);

                    doorRightFrontRF_x033.setX(0.0F);
                    doorRightFrontRF_x033.setY(0.0F);
                    doorRightFrontRF_x033.setScaleX(scalingFactor);
                    doorRightFrontRF_x033.setScaleY(scalingFactor);

                    RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    elemParams.setMargins(convertDpToPixels(ImageDragActivity.this, 140),
                            convertDpToPixels(ImageDragActivity.this, 130), 0, 0);
                    doorRightFrontRF_x033.setLayoutParams(elemParams);

                    wingRightFrontRF_x033.setX(0.0F);
                    wingRightFrontRF_x033.setY(0.0F);
                    wingRightFrontRF_x033.setScaleX(scalingFactor);
                    wingRightFrontRF_x033.setScaleY(scalingFactor);
                    RelativeLayout.LayoutParams elemParams2 = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    elemParams2.setMargins(convertDpToPixels(ImageDragActivity.this, 310),
                            convertDpToPixels(ImageDragActivity.this, 230), 0, 0);
                    wingRightFrontRF_x033.setLayoutParams(elemParams2);
//                    setDetailParams(wingRightFrontRF_x033, scalingFactor, 153, 113, 0);
                }*/
            }
        });
        arrowRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                blockCarRF_x050.setVisibility(View.GONE);
                blockCarRF_x033.setVisibility(View.GONE);
                blockCarFF_x033.setVisibility(View.GONE);
                blockCarLF_x033.setVisibility(View.GONE);
                if(vewRacurs=="right_front"){
                    blockCarFF_x033.setVisibility(View.VISIBLE);
                    vewRacurs = "front_front";
                } else if (vewRacurs=="front_front") {
                    blockCarLF_x033.setVisibility(View.VISIBLE);
                    vewRacurs = "left_front";
                } else if (vewRacurs=="left_front") {
                    blockCarLF_x033.setVisibility(View.VISIBLE); // future change to back left
                }
            }
        });

        arrowLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                blockCarRF_x050.setVisibility(View.GONE);
                blockCarRF_x033.setVisibility(View.GONE);
                blockCarFF_x033.setVisibility(View.GONE);
                blockCarLF_x033.setVisibility(View.GONE);
                if(vewRacurs=="left_front"){
                    blockCarFF_x033.setVisibility(View.VISIBLE);
                    vewRacurs = "front_front";
                } else if (vewRacurs=="front_front") {
                    blockCarRF_x033.setVisibility(View.VISIBLE);
                    vewRacurs = "right_front";
                } else if (vewRacurs=="right_front") {
                    blockCarRF_x033.setVisibility(View.VISIBLE);// future change to back right
                }
            }
        });

        doorRightFrontRF_x033.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clickDoorRightFrontRF();
            }
        });
        doorRightFrontRF_x050.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clickDoorRightFrontRF();
            }
        });
        doorRightFrontRF_x050.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                doMoveAction(event);
                return false;
            }
        });
        wingRightFrontRF_x033.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clickWingRightFrontRF();
            }
        });
        wingRightFrontRF_x050.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clickWingRightFrontRF();
            }
        });
        wingRightFrontRF_x050.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                doMoveAction(event);
                return false;
            }
        });

    } // \onCreate

    @Override
    protected void onResume() {
        super.onResume();
        blockCarRF_x050.setVisibility(View.GONE);
        blockCarRF_x033.setVisibility(View.VISIBLE);
    }

    private void clickDoorRightFrontRF(){
        setSharedValueStr("detail_article", "6RU831055J" );
        setSharedValueStr("detail_title", "Передняя левая дверь" );
        sendRequestToProxy();
    }
    private void clickWingRightFrontRF(){
        setSharedValueStr("detail_article", "6RU821106C" );
        setSharedValueStr("detail_title", "Переднее правое крыло" );
        sendRequestToProxy();
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

//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        // This method only determines whether you want to intercept the motion.
//        // If this method returns true, onTouchEvent is called and you can do
//        // the actual scrolling there.
//
//        final int action = MotionEventCompat.getActionMasked(ev);
//
//        // Always handle the case of the touch gesture being complete.
//        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
//            // Release the scroll.
//            mIsScrolling = false;
//            return false; // Don't intercept touch event. Let the child handle it.
//        }
//
//        switch (action) {
//            case MotionEvent.ACTION_MOVE: {
//                if (mIsScrolling) {
//                    // You're currently scrolling, so intercept the touch event.
//                    return true;
//                }
//
//                // If the user drags their finger horizontally more than the
//                // touch slop, start the scroll.
//
//                // Left as an exercise for the reader.
//                final int xDiff = calculateDistanceX(ev);
//
//                // Touch slop is calculated using ViewConfiguration constants.
//                if (xDiff > mTouchSlop) {
//                    // Start scrolling.
//                    mIsScrolling = true;
//                    return true;
//                }
//                break;
//            }
//            ...
//        }
//
//        // In general, don't intercept touch events. The child view handles them.
//        return false;
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) { // 3D model actions
        if(scaleValue == 2){
            doMoveAction(event);
        }
        return super.onTouchEvent(event);
    }

    private void doMoveAction(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
            Toast.makeText(ImageDragActivity.this, "Down", Toast.LENGTH_SHORT).show();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            dx = event.getX() - x;
            dy = event.getY() - y;

            layoutCarRF_x050.setX(layoutCarRF_x050.getX() + dx);
            layoutCarRF_x050.setY(layoutCarRF_x050.getY() + dy);

            doorRightFrontRF_x050.setX(doorRightFrontRF_x050.getX() + dx);
            doorRightFrontRF_x050.setY(doorRightFrontRF_x050.getY() + dy);

            wingRightFrontRF_x050.setX(wingRightFrontRF_x050.getX() + dx);
            wingRightFrontRF_x050.setY(wingRightFrontRF_x050.getY() + dy);

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