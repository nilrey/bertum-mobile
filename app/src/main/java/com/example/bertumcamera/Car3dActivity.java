package com.example.bertumcamera;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Car3dActivity extends AppCompatActivity {
    private RelativeLayout blockCarRF_x050;
    private ImageView doorRightFrontRF_x050, wingRightFrontRF_x050,
            layoutCarRF_x050;

    private String sideView = Const.VIEW_RIGHT_FRONT;
    private int bias=0, scaleValue = 2;
    float x, y, dx, dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car3d);

        calculateBias();
        activateSideView();

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
    private void calculateBias(){
        Resources r = this.getResources();
        int currentWidth = Math.round(r.getDisplayMetrics().widthPixels / r.getDisplayMetrics().density);
        setBias(Math.round( (Const.SETS_WIDTH_KOEF_DEFAULT - currentWidth) / 2 ));
    }

    private void activateSideView(){
        initAllSideViews();
        activateSvBlock();
        activateSvDetails();
    }
    private void initAllSideViews(){
        blockCarRF_x050 = findViewById(R.id.blockCarRF_x050);
//        blockCarRF_x033 = findViewById(R.id.blockCarRF_x033);
//        blockCarFF_x033 = findViewById(R.id.blockCarFF_x033);
//        blockCarLF_x033 = findViewById(R.id.blockCarLF_x033);
        hideSideView(blockCarRF_x050);
    }
    private void hideSideView(RelativeLayout elem){
        elem.setVisibility(View.GONE);
    }
    private void activateSvBlock(){
        if(sideView == Const.VIEW_RIGHT_FRONT){
            blockCarRF_x050.setVisibility(View.VISIBLE);
        } else if (sideView == Const.VIEW_FRONT_FRONT) {

        }
    }

    private void activateSvDetails(){
        if(sideView == Const.VIEW_RIGHT_FRONT){
            wingRightFrontRF_x050 = findViewById(R.id.wingRightFrontRF_x050);
            doorRightFrontRF_x050 = findViewById(R.id.doorRightFrontRF_x050);
            setDetailPosition(wingRightFrontRF_x050, 128, 168);
            setDetailPosition(doorRightFrontRF_x050, 49, 110);
        }
    }

    private void setDetailPosition(ImageView elem, int left, int top){
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        int biasTest = getBias();
        elemParams.setMargins(convertDpToPixels(Car3dActivity.this, left - biasTest ),
                convertDpToPixels(Car3dActivity.this, top ), 0, 0);
        elem.setLayoutParams(elemParams);
    }


    private void moveImageView(float k, ImageView elem){

        RelativeLayout.LayoutParams elemParams = (RelativeLayout.LayoutParams) doorRightFrontRF_x050.getLayoutParams();
//        elemParams.get(convertDpToPixels(this, 0),
//                convertDpToPixels(this, 150), 0, 0);
//        doorRightFrontRF_x050.setLayoutParams(elemParams);
    }

    private ImageView genImageView(int elem_id){
        ImageView elem = new ImageView(this);
        elem.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );
        Context context = elem.getContext();
        int id = context.getResources().getIdentifier("det_door_right", "drawable", context.getPackageName());
        elem.setImageResource(id);
//        elem.setImageResource(R.drawable.det_door_right);
        elem.setId(elem_id);
        return elem;
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

    private void setBias(int val){
        this.bias = val;
    }

    private int getBias(){
        return this.bias;
    }

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
            Toast.makeText(Car3dActivity.this, "Down", Toast.LENGTH_SHORT).show();
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

}