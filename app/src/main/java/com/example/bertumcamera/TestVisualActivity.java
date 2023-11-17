package com.example.bertumcamera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestVisualActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private LinearLayout ll_parent;
    private TextView responseTV, cntDetailsFound, tvSideview;
    // 3D Model vars
    ImageView layoutCarRF, doorRightFrontRF;
    private ImageButton arrowPlus, arrowMinus, arrowLeft, arrowRight;
    private String msgDetailSelected;
    float x, y;
    float dx, dy;

    private TextView informer;
    private ConstraintLayout topCarLayout;
    private RelativeLayout areaSideView;
    private ImageView carSideView;
    private Resources r;

    private Button reload;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_visual);

        r = this.getResources();

        informer = findViewById(R.id.informer);
        topCarLayout = findViewById(R.id.topCarLayout);
        areaSideView = findViewById(R.id.areaSideView);
        carSideView = findViewById(R.id.carSideView);
        reload = findViewById(R.id.reload);


        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestVisualActivity.this, TestVisualActivity.class));
            }
        });

        int a =  r.getDisplayMetrics().widthPixels;
        int b  = r.getDisplayMetrics().heightPixels;
        float c = r.getDisplayMetrics().density;
        float d = r.getDisplayMetrics().densityDpi;
        float f = r.getDisplayMetrics().scaledDensity;
        informer.setText("Width="+a+"; "
                +"Height="+b+"; "
                +"density="+c+"; "
                +"densityDpi="+d+"; "
                +"scaledDensity="+f+"; "
        );
    }

    private TextView setDetailTitle(int elem_id, String title, int parent_id){
        TextView elem = genTextView( elem_id,  title,  parent_id);
        elem.setTextSize(20);
        RelativeLayout.LayoutParams elemParams = (RelativeLayout.LayoutParams)elem.getLayoutParams();
        elemParams.setMargins( convertDpToPixels(this, 5),  convertDpToPixels(this, 160),  0,  0);
        elem.setLayoutParams(elemParams);
        return elem;
    }

    private TextView setArticleTitle(int elem_id, String title, int parent_id){
        TextView elem = genTextView( elem_id,  title,  parent_id);
        elem.setTextSize(20);
        RelativeLayout.LayoutParams elemParams = (RelativeLayout.LayoutParams)elem.getLayoutParams();
        elemParams.setMargins( convertDpToPixels(this, 5),  convertDpToPixels(this, 185),  0,  0);
        elem.setLayoutParams(elemParams);
        return elem;
    }

    private TextView genTextView(int elem_id, String title, int parent_id) {
        TextView elem = new TextView(this);
        elem.setId(elem_id);
        elem.setText(title);
        elem.setTextSize(16);
//        elem.setBackgroundColor(0xff66ff66);
        elem.setPadding(convertDpToPixels(this, 10),
                0,
                convertDpToPixels(this, 10),
                0);// in pixels (left, top, right, bottom)
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        elemParams.addRule(RelativeLayout.ALIGN_TOP, parent_id );
        //elemParams.setMargins( convertDpToPixels(this, 0),  convertDpToPixels(this, 150),  0,  0);
        elem.setLayoutParams(elemParams);

        return elem;
    }

    private ImageView genImageView(int elem_id, int left, int top){
        ImageView elem = new ImageView(this);
        elem.setLayoutParams(
                new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        convertDpToPixels(this, 370)
                )
        );
        Context context = elem.getContext();
        int id = context.getResources().getIdentifier("details_list_plate_empty", "drawable", context.getPackageName());
        elem.setImageResource(id);
//        elem.setImageResource(R.drawable.det_door_right);
        elem.setId(elem_id);
        return elem;
    }
    private ImageView genImageView2(int elem_id, int parent_id, int left, int top, String engDetailCode){
        ImageView elem = new ImageView(this);
        int detailImageId =0;
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        elemParams.setMargins( convertDpToPixels(this, 20),
                convertDpToPixels(this, 8),
                convertDpToPixels(this, 20),
                0);
        elemParams.addRule(RelativeLayout.ALIGN_START, parent_id );
        elemParams.height = convertDpToPixels(this, 120);

        String nameImage =  engDetailCode.toLowerCase().substring(12).replace(" ", "_");
        Context context = elem.getContext();
        String isError;
        try {
            detailImageId = context.getResources().getIdentifier(nameImage, "drawable", context.getPackageName());
        }catch (Exception e){
            isError = e.getMessage();
        }
        if(detailImageId == 0){
            detailImageId = context.getResources().getIdentifier("detail_noimage", "drawable", context.getPackageName());
        }
        elem.setImageResource(detailImageId);

        //elemParams.addRule(RelativeLayout.ALIGN_START, parent_id );

        elem.setId(elem_id);
        elem.setLayoutParams(elemParams);
        return elem;
    }

    private Button genButton(int elem_id, String title, String btn_title, int parent_id, String detail_article){
        Button elem = new Button(this);
        elem.setLayoutParams(
                new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                )
        );
        elem.setId(elem_id);
        elem.setText(btn_title);
        elem.setTextColor(Color.parseColor("#FFFFFF"));
        elem.setBackgroundColor(Color.parseColor("#F43934"));
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)elem.getLayoutParams();
        elemParams.addRule(RelativeLayout.ALIGN_START, parent_id );
        elemParams.setMargins( convertDpToPixels(this, 245),  convertDpToPixels(this, 300),  0,  0);

        final String da = detail_article;
        final String ta = title;


        elem.setLayoutParams(elemParams);
        return elem;
    }

    private String getSideViewTitleRus(String engSideView) {
        String rus = "Неопределено";
        if(engSideView.equals("front_right")){ rus = "Спереди справа";}
        else if (engSideView.equals("front_front")) { rus = "Спереди";}
        else if (engSideView.equals("front_left")) { rus = "Спереди слева";}
        else if (engSideView.equals("left_left")) { rus = "Слева";}
        else if (engSideView.equals("right_right")) { rus = "Справа";}
        else if (engSideView.equals("back_right")) { rus = "Сзади справа";}
        else if (engSideView.equals("back_back")) { rus = "Сзади";}
        else if (engSideView.equals("back_left")) { rus = "Сзади слева";}
        return rus;
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        activateSideView("front_right");

    }

    private void activateSideView(String sideView){
        String[] detailsList = getSideViewDetails(sideView);
        for (Object detailName : detailsList)
        {
            initDetail(detailName.toString());
        }
    }

    private String[] getSideViewDetails(String sideView){
        if(sideView.equals("front_right")){
            return new String[]{"front_bumper", "front_right_door",
                     "hood"};
//            return new String[]{"front_bumper", "rear_bumper", "front_right_door",
//                    "rear_right_door", "hood", "wheel", "front_right_wing", "rear_right_wing",
//                    "trunk_lid", "windshield", "roof" };
        }else if (sideView.equals("front_left")) {
            return new String[]{"front_bumper", "rear_bumper", "front_left_door",
                    "rear_left_door", "hood", "wheel", "front_left_wing", "rear_left_wing",
                    "trunk_lid", "windshield", "roof" };
        }
        return new String[]{};
    }

    private void initDetail(String fName){
        float density = r.getDisplayMetrics().density;
        double wCar = carSideView.getWidth();
        double hCar = carSideView.getHeight();

        fName = "m3d_vw6_sed_x033_front_right_front_bumper";
        Context context = carSideView.getContext();
        int detailImageId = context.getResources().getIdentifier(fName, "drawable", context.getPackageName());
        Drawable drawable = getResources().getDrawable(detailImageId );
        int widthImage = drawable.getIntrinsicWidth();
        int heightImage = drawable.getIntrinsicHeight();
        double wDetail = widthImage/density;
        double hDetail = heightImage/density;

        // 2,289017341040462 w 396/173
        // 4,242857142857143 h 297/70
        // 2,084210526315789 bx 396/190
        // 1,84472049689441 by 297/161
        double detailPxWidth = wDetail; // 173
        double detailPxHeight =  hDetail; // 70
        double detailPxLeft = 191; // 190
        double detailPxTop = 160; // 160

        double ratioWidth = 396/detailPxWidth ; //2.289017341040462;
        double ratioHeight = 297/detailPxHeight; //4.242857142857143;
        double ratioLeft = 396/detailPxLeft; //2.084210526315789;
        double ratioTop = 297/detailPxTop; // 1.84472049689441;

        int left = (int) (wCar/ ratioLeft );
//        int top = (int) (hCar/ ratioTop );
        double calcHeight = wCar/1.333333;
        int biasHeight = (int) (((hCar - calcHeight)*0.3333)*0.3333);
        int top = (int) (calcHeight/ ratioTop - biasHeight); //  - (((780-613)*0.3333) /2 ));
        // define Main Image height once
        areaSideView.getLayoutParams().height = (int) calcHeight;

        // init new Detail
        ImageView elem = new ImageView(this);
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        elemParams.width = (int) (wCar/ ratioWidth );
        elemParams.height = (int) (hCar/ ratioHeight );
        elemParams.setMargins(left, top, 0, 0);
        elem.setLayoutParams(elemParams);

        elem.setImageResource(detailImageId);
        elem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.save detail id
//                setSharedValueStr("detail_article", da );
//                setSharedValueStr("detail_title", ta );
//                setSharedValueStr("detail_title_rus", tr );
                // 2.redirect to page-Prices
//                startActivity(new Intent(DetailsListActivity.this, DetailPricesActivity.class));
                startActivity(new Intent(TestVisualActivity.this, TestVisualActivity.class));
                //Toast.makeText(DetailsListActivity.this, da, Toast.LENGTH_SHORT).show();
            }
        });
        areaSideView.addView(elem);
    }
}