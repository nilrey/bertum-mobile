package com.example.bertumcamera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsListActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private LinearLayout ll_parent;
    private RelativeLayout areaSideView;
    private TextView responseTV, cntDetailsFound, tvSideview, cntDetailsAvailable;
    // 3D Model vars
    private ImageView carSideView, doorRightFrontRF;
    private ImageButton arrowPlus, arrowMinus, arrowLeft, arrowRight;
    private String msgDetailSelected;
    private float x, y, dx, dy;
    private int cntAttemptVolley = 0, bias=0, biasGen = 8;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);
        // 3D Model
        calculateBias();
        setItemsListBackPage();

        areaSideView = findViewById(R.id.areaSideView);
        carSideView = findViewById(R.id.carSideView);
        doorRightFrontRF = findViewById(R.id.doorRightFrontRF);
        arrowPlus = findViewById(R.id.arrow_plus);
        arrowMinus = findViewById(R.id.arrow_minus);
        msgDetailSelected = getResources().getString(R.string.msg_detail_selected);
        cntDetailsFound = findViewById(R.id.cnt_detail_found);
        tvSideview = findViewById(R.id.tv_sideview);
        cntDetailsAvailable = findViewById(R.id.details_available);

//        responseTV = findViewById(R.id.idTVResponse);
        int isError = 0, file_size=0;
        String sideView = "front_right", cache_file_name = "";
        String jsonAiApi = getSharedValueStr("jsonAiApi");
        String part_name_rus, part_name = "", article, minPriceFinal;

        try {
            // get JSONObject from JSON file
            JSONObject objects = new JSONObject (jsonAiApi);

            JSONObject settings = objects.getJSONObject("settings");
            JSONArray keys = settings.names ();
            for (int i = 0; i < keys.length(); ++i) {
                String key = keys.getString (i);
                JSONObject objDetail = settings.getJSONObject(key);
                isError = Integer.parseInt(objDetail.getString ("error"));
                if(isError == 0) {
                    sideView = objDetail.getString("view");
                    cache_file_name = objDetail.getString("cache");
                    file_size = Integer.parseInt(objDetail.getString ("file_size"));
                    if( sideView.equals("unnrecognized")
                            || sideView.equals("unrecognized")
                            || sideView.equals("")
                            || file_size == 0
                            || cache_file_name.equals("")
                    ) {
                        isError = 1;
                    }
                }
            }
            if(isError < 1) {
                JSONArray mask = objects.getJSONArray("mask");
                int cntDA = 0;
                for (int i = 0; i < mask.length(); i++) {
                    JSONObject record = mask.getJSONObject(i);
                    part_name = record.getString("Part_Name");
                    part_name_rus = record.getString("Part_Name_rus");
                    article = record.getString("Article");
                    minPriceFinal = record.getString("minPriceFinal");
                    if (Integer.parseInt(minPriceFinal) > 0) {
                        generateDetailBlock(part_name, article, part_name_rus, minPriceFinal);
                        cntDA++;
                        //activateSideViewDetails(sideView , getCodeFromName(part_name));
                    }
                }

                tvSideview.setText(MappingDetails.getSideViewTitleRus(sideView));
//            Log.d("BERTUM----sideView", sideView );
                Context context = carSideView.getContext();
                int id = context.getResources().getIdentifier(MappingDetails.getSideViewImage(sideView),
                        "drawable", context.getPackageName());
                carSideView.setImageResource(id);

                cntDetailsFound.setText( String.valueOf(mask.length()) );
                cntDetailsAvailable.setText( String.valueOf(cntDA) );
            }else{
                Toast.makeText(DetailsListActivity.this, Const.MSG_ERROR_AI_SERVICE, Toast.LENGTH_SHORT).show();
            }

//            activateSideViewDetails(carSideView, sideView);


        } catch (JSONException e) {
            Log.d("BERTUM---JSONException", e.getMessage() );
//            e.printStackTrace();
        }

        // 3D model functions

        arrowMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if( carSideView.getScaleX() > 1 ) {
                    startActivity(new Intent(DetailsListActivity.this, DetailsListActivity.class));
                }
            }
        });

        arrowPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if( carSideView.getScaleX() < 2 ) {
                    float scalingFactor = 2f;
                    carSideView.setScaleType(ImageView.ScaleType.CENTER);
                    carSideView.setX(0.0F);
                    carSideView.setY(0.0F);
                    carSideView.setScaleX(scalingFactor);
                    carSideView.setScaleY(scalingFactor);

                    doorRightFrontRF.setX(0.0F);
                    doorRightFrontRF.setY(0.0F);
                    doorRightFrontRF.setScaleX(scalingFactor);
                    doorRightFrontRF.setScaleY(scalingFactor);


                    RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    elemParams.setMargins(convertDpToPixels(DetailsListActivity.this, 140),
                            convertDpToPixels(DetailsListActivity.this, 130), 0, 0);
                    doorRightFrontRF.setLayoutParams(elemParams);
                }
            }
        });

/*        doorRightFrontRF.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueStr("detail_article", "" );
                setSharedValueStr("detail_title", "Дверь передняя левая" );
                startActivity(new Intent(DetailsListActivity.this, ItemsList.class));
//                Toast.makeText(DetailsListActivity.this, String.format(msgDetailSelected, "Правая передняя дверь"), Toast.LENGTH_SHORT).show();
            }
        });*/

    } // \onCreate

    private void setItemsListBackPage(){
        setSharedValueStr("ItemsListBackPage", "DetailsListActivity");
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) { // 3D model actions
        if(carSideView.getScaleX() > 1.0 ) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x = event.getX();
                y = event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                dx = event.getX() - x;
                dy = event.getY() - y;

                carSideView.setX(carSideView.getX() + dx);
                carSideView.setY(carSideView.getY() + dy);

                doorRightFrontRF.setX(doorRightFrontRF.getX() + dx);
                doorRightFrontRF.setY(doorRightFrontRF.getY() + dy);

                x = event.getX();
                y = event.getY();
            }
        }
        return super.onTouchEvent(event);
    }

    private void activateSideViewDetails(String sideView, String detailCode){
        /*
        * Сколько на отображение распознанно для этого sideView
        * Сгенерировать названия
        * Сгенерировать ImageView
        * Получить заранее определенные соответствующие координаты каждой детали
        * Присвоить к parent
        * */
        String fName = "m3d_vw6_sed_x033_" + sideView + "_" + detailCode;
        if(sideView.equals("front_right")){
//            if(fName.equals("m3d_vw6_sed_x033_front_right_back_right_door")){ initDetailPhoto(fName, 70, 102);}
//            else if(fName.equals("m3d_vw6_sed_x033_front_right_front_bumper")){ initDetailPhoto(fName, 190,  160);}
//            if(fName.equals("m3d_vw6_sed_x033_front_right_front_right_door")){ initDetailPhoto(fName, 95,  72);}
//            else if(fName.equals("m3d_vw6_sed_x033_front_right_front_right_headlight")){ initDetailPhoto(fName, 218,  146);}
//            else if(fName.equals("m3d_vw6_sed_x033_front_right_front_right_wing")){ initDetailPhoto(fName, 158,  110);}
//            else if(fName.equals("m3d_vw6_sed_x033_front_right_hood")){ initDetailPhoto(fName, 184,  110);}
//            else if(fName.equals("m3d_vw6_sed_x033_front_right_rear_right_wing")){ initDetailPhoto(fName, 40, 80);}
//            else if(fName.equals("m3d_vw6_sed_x033_front_right_right_side_mirror")){ initDetailPhoto(fName, 129,  94);}
        }else if(sideView.equals("front_front")){
//            if(fName.equals("m3d_vw6_sed_x033_front_front_front_bumper")){ initDetailPhoto(fName, 83, 160);}
//            else if(fName.equals("m3d_vw6_sed_x033_front_front_hood")){ initDetailPhoto(fName, 100,  100);}
//            else if(fName.equals("m3d_vw6_sed_x033_front_front_roof")){ initDetailPhoto(fName, 117,  45);}
        }

    }
    private void initDetailPhoto(String fName, int left, int top){
        int detailImageId = this.getResources().getIdentifier(fName, "drawable",
                this.getPackageName());

        if(detailImageId > 0) {
            int biasLocal = getBias();
            ImageView elem = new ImageView(this);
            elem.setImageResource(detailImageId);
            elem.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            elemParams.setMargins(convertDpToPixels(this, left-biasLocal-25),
                    convertDpToPixels(this, top), 0, 0);
            elemParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, carSideView.getId() );
            elemParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, carSideView.getId() );

            //        elem.setId(ViewCompat.generateViewId());
            elem.setLayoutParams(elemParams);
            areaSideView.addView(elem);
        }else{
            Log.d("BERTUM---Detail", "Image "+fName+"does not exist" );
        }
    }

    private void generateDetailBlock(String part_name, String detail_article, String part_name_rus, String minPriceFinal){
        // Dynamic ids for controls
        int layoutRelativeId = ViewCompat.generateViewId();
        int plateDetail = ViewCompat.generateViewId();
        int imageDetail = ViewCompat.generateViewId();
        int textTitleId = ViewCompat.generateViewId();
        int textMinPiceId = ViewCompat.generateViewId();
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
        // set detail min Price
        ll_new_block.addView(setMinPriceTitle(textMinPiceId, minPriceFinal, plateDetail));
        // Create Button
        ll_new_block.addView(genButton(btnPricesId, part_name, part_name_rus, "Цены", plateDetail , detail_article));

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

    private TextView setMinPriceTitle(int elem_id, String title, int parent_id){

        TextView elem = genTextView( elem_id,  title,  parent_id);
        elem.setTextSize(20);
        elem.setWidth( convertDpToPixels(this, 80));
        elem.setTextColor(Color.parseColor("#CC0000"));
        elem.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        RelativeLayout.LayoutParams elemParams = (RelativeLayout.LayoutParams)elem.getLayoutParams();
        elemParams.setMargins( convertDpToPixels(this, 22),  convertDpToPixels(this, 220 - biasGen),  0,  0);
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
                convertDpToPixels(this, 20 - biasGen),
                0);
        elemParams.addRule(RelativeLayout.ALIGN_START, parent_id );
        elemParams.height = convertDpToPixels(this, 120);

        String nameImage =  getCodeFromName(engDetailCode);//.toLowerCase().substring(12).replace(" ", "_");
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

    private String getCodeFromName(String engDetailCode){
        return engDetailCode.toLowerCase().substring(12).replace(" ", "_");
    }

    private Button genButton(int elem_id, String title, String titleRus, String btn_title, int parent_id, String detail_article){
        int biasLocal = getBias();
        Button elem = new Button(this);
        elem.setLayoutParams(
            new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            )
        );
        elem.setId(elem_id);
        elem.setText(btn_title);
        elem.setHeight(80); // px
        elem.setTextColor(Color.parseColor("#FFFFFF"));
        elem.setBackgroundColor(Color.parseColor("#F43934"));
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)elem.getLayoutParams();
        elemParams.addRule(RelativeLayout.ALIGN_START, parent_id );
        elemParams.setMargins( convertDpToPixels(this, 243-biasLocal),  convertDpToPixels(this, 300 - biasGen),  0,  0);

        final String da = detail_article;
        final String ta = title;
        final String tr = titleRus;

        elem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.save detail id
                setSharedValueStr("detail_article", da );
                setSharedValueStr("detail_title", ta );
                setSharedValueStr("detail_title_rus", tr );
                // 2.redirect to page-Prices
//                startActivity(new Intent(DetailsListActivity.this, DetailPricesActivity.class));
                startActivity(new Intent(DetailsListActivity.this, ItemsList.class));
                //Toast.makeText(DetailsListActivity.this, da, Toast.LENGTH_SHORT).show();
            }
        });

        elem.setLayoutParams(elemParams);
        return elem;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailsListActivity.this, MainActivity.class));
    }

    private void postDataUsingVolley(final String photoId, final String photoBase64) {
        String url = "http://h304809427.nichost.ru/api/get_segments_test_base.php";
        RequestQueue queue = Volley.newRequestQueue(DetailsListActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                responseTV.setText("Response from the API is :" + response); // comment for a while
                setSharedValueStr("jsonAiApi", response);
//                startActivity(new Intent(DetailsListActivity.this, ItemsList.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling error on below line.
                Toast.makeText(DetailsListActivity.this, "Fail to get response..", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rmPoints", "1");
                params.put("photoBase64", photoBase64);
                return params;
            }
        };
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);

        Toast.makeText(DetailsListActivity.this, "Фотография успешно отправлена", Toast.LENGTH_SHORT).show();

        // post the data.
        queue.add(request);
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
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_APPEND);
        return sharedPreferences.getInt(name, 0);
    }
    private String getSharedValueStr(String name){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_APPEND);
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

    private void calculateBias(){
        Resources r = this.getResources();
        int currentWidth = Math.round(r.getDisplayMetrics().widthPixels / r.getDisplayMetrics().density);
        setBias(Math.round( (Const.SETS_WIDTH_KOEF_DEFAULT - currentWidth) / 2 ) );
    }

    private void setBias(int val){
        this.bias = val;
    }

    private int getBias(){
        return this.bias;
    }

}