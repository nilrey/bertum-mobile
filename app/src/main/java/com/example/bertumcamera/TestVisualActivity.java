package com.example.bertumcamera;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_visual);

        layoutCarRF = findViewById(R.id.carSideView);
        doorRightFrontRF = findViewById(R.id.doorRightFrontRF);
        arrowPlus = findViewById(R.id.arrow_plus);
        arrowMinus = findViewById(R.id.arrow_minus);
        msgDetailSelected = getResources().getString(R.string.msg_detail_selected);
        cntDetailsFound = findViewById(R.id.cnt_detail_found);
        tvSideview = findViewById(R.id.tv_sideview);

//        responseTV = findViewById(R.id.idTVResponse);
        // TEST get JSON AI API
//        postDataUsingVolley("123456789", "fasdfasdf");
        String jsonAiApi = DataStore.getTestJsonAiApi();
        String view_angle = "";
        String cache_file_name = "";
        String file_size = "";

        try {
            // get JSONObject from JSON file
            String part_name_rus, part_name, article;
            JSONObject objects = new JSONObject (jsonAiApi);
            JSONArray mask  = objects.getJSONArray("mask");
            for (int i = 0; i < mask.length(); i++)
            {
                JSONObject record = mask.getJSONObject(i);
                part_name = record.getString("Part_Name");
                part_name_rus = record.getString("Part_Name_rus");
//                try {
//                    byte bytes[] = part_name_rus.getBytes("ISO-8859-1");
//                } catch (UnsupportedEncodingException e) {
//                    throw new RuntimeException(e);
//                }
                article = record.getString("Article");
                generateDetailBlock(part_name, article, part_name_rus);
            }
            cntDetailsFound.setText( String.valueOf(mask.length()) );


            JSONObject settings = objects.getJSONObject("settings");
            JSONArray keys = settings.names ();
            for (int i = 0; i < keys.length(); ++i) {
                String key = keys.getString (i);
                JSONObject objDetail = settings.getJSONObject(key);
                view_angle = objDetail.getString ("view");
                cache_file_name = objDetail.getString ("cache");
                file_size = objDetail.getString ("file_size");
            }
            tvSideview.setText(getSideViewTitleRus(view_angle));
//            tvSideview.setText(view_angle);
            Log.d("BERTUM----view_angle", view_angle );
            // заменить картинку на нужный ракурс
            // layoutCarRF m3d_vw6_sed_x033_right_front
            String img = "m3d_vw6_sed_x033_right_front";
            if(view_angle.equals("front_front")){
                img = "m3d_vw6_sed_x033_front_front";
            }
            if(view_angle.equals("front_left")){
                img = "m3d_vw6_sed_x033_left_front";
            }
            Context context = layoutCarRF.getContext();
            int id = context.getResources().getIdentifier(img, "drawable", context.getPackageName());
            layoutCarRF.setImageResource(id);


        } catch (JSONException e) {
            Log.d("BERTUM---JSONException", e.getMessage() );
//            e.printStackTrace();
        }

        // 3D model functions

        arrowMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if( layoutCarRF.getScaleX() > 1 ) {
//                    startActivity(new Intent(TestVisualActivity.this, Test.class));
                }
            }
        });

        arrowPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if( layoutCarRF.getScaleX() < 2 ) {
                    float scalingFactor = 2f;
                    layoutCarRF.setScaleType(ImageView.ScaleType.CENTER);
                    layoutCarRF.setX(0.0F);
                    layoutCarRF.setY(0.0F);
                    layoutCarRF.setScaleX(scalingFactor);
                    layoutCarRF.setScaleY(scalingFactor);

                    doorRightFrontRF.setX(0.0F);
                    doorRightFrontRF.setY(0.0F);
                    doorRightFrontRF.setScaleX(scalingFactor);
                    doorRightFrontRF.setScaleY(scalingFactor);


                    RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    elemParams.setMargins(convertDpToPixels(TestVisualActivity.this, 140),
                            convertDpToPixels(TestVisualActivity.this, 130), 0, 0);
                    doorRightFrontRF.setLayoutParams(elemParams);
                }
            }
        });
    }

    private void generateDetailBlock(String part_name, String detail_article, String part_name_rus){
        // Dynamic ids for controls
        int layoutRelativeId = ViewCompat.generateViewId();
        int plateDetail = ViewCompat.generateViewId();
        int imageDetail = ViewCompat.generateViewId();
        int textTitleId = ViewCompat.generateViewId();
        int textArticleId = ViewCompat.generateViewId();
        int btnPricesId = ViewCompat.generateViewId();
        LinearLayout ll_parent = findViewById(R.id.ll_details);
        // Create detail Relative layout
        RelativeLayout ll_new_block = new RelativeLayout(this);
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        elemParams.setMargins( convertDpToPixels(this, 20),
                convertDpToPixels(this, 20),
                convertDpToPixels(this, 20),
                0);
        ll_new_block.setLayoutParams(elemParams);
        ll_new_block.setId(layoutRelativeId);
        ll_new_block.setGravity(Gravity.CENTER_HORIZONTAL);
        ll_new_block.addView(genImageView(plateDetail , 0, 0) );
        ll_new_block.addView(genImageView2(imageDetail, plateDetail , 0, 0, part_name));

        //set detail title
        ll_new_block.addView(setDetailTitle(textTitleId, part_name_rus, plateDetail));
        // set detail article
        ll_new_block.addView(setArticleTitle(textArticleId, detail_article, plateDetail));
        // Create Button
        ll_new_block.addView(genButton(btnPricesId, part_name, "Цены", plateDetail , detail_article));

        ll_parent.addView(ll_new_block);

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

}