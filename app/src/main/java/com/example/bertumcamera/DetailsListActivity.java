package com.example.bertumcamera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private DrawerLayout drawerLayout;
    private ImageView ico_menu;
    private LinearLayout ll_parent;
    private RelativeLayout areaSideView;
    private TextView responseTV, cntDetailsFound, tvSideview, cntDetailsAvailable,
            cntTotalItems, sumRepairs, sumRepairsWork, linkToSmetaRepairs , linkToSmetaDetails;
    // 3D Model vars
    private ImageView carSideView, doorRightFrontRF, ico_cart;
    private ImageButton arrowPlus, arrowMinus, arrowLeft, arrowRight;
    private String msgDetailSelected;
    private float x, y, dx, dy;
    private int cntAttemptVolley = 0, bias=0, biasGen = 8;
    private int cnt, low, high, cartSumRepairs, cartSumRepairsWork, cartCntRepairs, cntAllVariants = 0, isActivateSideViewDetails = 0;
    private int unDuplicateWheel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // make app fullscreen

        // 3D Model
        calculateBias();
        setItemsListBackPage();
        setSharedValueInt("countTry" , 0);

        // нижняя часть
        cntTotalItems = findViewById(R.id.cntTotalItems);
        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);
        linkToSmetaDetails = findViewById(R.id.linkToSmetaDetails);
        linkToSmetaRepairs = findViewById(R.id.linkToSmetaRepairs);
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
        // 3D model functions
        areaSideView = findViewById(R.id.areaSideView);
        carSideView = findViewById(R.id.carSideView);
        arrowPlus = findViewById(R.id.arrow_plus);
        arrowMinus = findViewById(R.id.arrow_minus);
        msgDetailSelected = getResources().getString(R.string.msg_detail_selected);
        cntDetailsFound = findViewById(R.id.cnt_detail_found);
        tvSideview = findViewById(R.id.tv_sideview);
        cntDetailsAvailable = findViewById(R.id.details_available);
        ll_parent = findViewById(R.id.ll_details);

        linkToSmetaDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 0);
                startActivity(new Intent(DetailsListActivity.this, SmetaActivity.class));
            }
        });
        linkToSmetaRepairs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 1);
                startActivity(new Intent(DetailsListActivity.this, SmetaActivity.class));
            }
        });

        ico_cart = findViewById(R.id.ico_cart);
        ico_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailsListActivity.this, CartActivity.class));
            }
        });
    }

    private void setItemsListBackPage(){
        setSharedValueStr("ItemsListBackPage", "DetailsListActivity");
    }

    private void generateDetailBlock(String part_name, String detail_article, String part_name_rus, String minPriceFinal){
        // Dynamic ids for controls
        int layoutRelativeId = ViewCompat.generateViewId();
        int layoutConstraintId = ViewCompat.generateViewId();
        int plateDetail = ViewCompat.generateViewId();
        int imageDetail = ViewCompat.generateViewId();
        int textTitleId = ViewCompat.generateViewId();
        int textMinPiceId = ViewCompat.generateViewId();
        int textArticleId = ViewCompat.generateViewId();
        int btnPricesId = ViewCompat.generateViewId();
        // RelativeLayout
        /*
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="10dp"
                        android:layout_marginEnd="10dp"
        * */
        RelativeLayout rl_new_block = new RelativeLayout(this);
        RelativeLayout.LayoutParams rl_params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        rl_params.setMargins( convertDpToPixels(this, 10),0, convertDpToPixels(this, 10), 0);
        rl_new_block.setLayoutParams(rl_params);
        rl_new_block.setId(layoutRelativeId);


        // Create detail ConstraintLayout layout
        /*
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:gravity="center_horizontal"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                            * */
        ConstraintLayout ll_new_block = new ConstraintLayout(this);
        ConstraintLayout.LayoutParams elemParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
/*        elemParams.setMargins( convertDpToPixels(this, 20),convertDpToPixels(this, 20), 0, convertDpToPixels(this, 20));*/
        ll_new_block.setLayoutParams(elemParams);
        ll_new_block.setId(layoutConstraintId);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(ll_new_block);
        constraintSet.connect(layoutRelativeId, ConstraintSet.START, layoutConstraintId, ConstraintSet.START, 0);
        constraintSet.connect(layoutRelativeId, ConstraintSet.END, layoutConstraintId, ConstraintSet.END, 0);

//        ll_new_block.setGravity(Gravity.CENTER_HORIZONTAL);

// Start create ELEMENTS
        // imageView Plate
        ll_new_block.addView(genImageView(plateDetail , 0, 0) );
        // imageView Detail Photo
        ll_new_block.addView(genImageView2(imageDetail, plateDetail , 0, 0, part_name));
//        ll_new_block.getLayoutParams().height = (int) calcHeight;
        //set detail title
        ll_new_block.addView(setDetailTitle(textTitleId, part_name_rus, plateDetail));
        // set detail article
        ll_new_block.addView(setArticleTitle(textArticleId, detail_article, plateDetail));
        // set detail min Price
        ll_new_block.addView(setMinPriceTitle(textMinPiceId, minPriceFinal, plateDetail));
        // Create Button
        ll_new_block.addView(genButton(btnPricesId, part_name, part_name_rus, "Цены", plateDetail , detail_article));
// END create ELEMENTS
        // add ConstraintLayout to RelativeLayout
        rl_new_block.addView(ll_new_block);

        // add RelativeLayout to ll_details
        ll_parent.addView(rl_new_block);

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
        elemParams.setMargins( convertDpToPixels(this, 42),
                convertDpToPixels(this, 220),
                0,  0);
        elem.setWidth(convertDpToPixels(this, 63));
        elem.setHeight(convertDpToPixels(this, 26));
        elem.setLayoutParams(elemParams);
        elem.setPadding(convertDpToPixels(this, convertDpToPixels(this, 20)),
                0,
                convertDpToPixels(this, 10),
                0);
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



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

//        responseTV = findViewById(R.id.idTVResponse);
        int isError = 0, file_size=0;
        String sideView = "front_right", cache_file_name = "";
        String jsonAiApi = getSharedValueStr("jsonAiApi");
        String part_name_rus, part_name = "", article, minPriceFinal, minPriceType, minPriceArticle;

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
                if(isActivateSideViewDetails == 0) {
                    activateSideViewDetails(sideView, "", "", "", "");
                    isActivateSideViewDetails = 1;
                }

                JSONArray mask = objects.getJSONArray("mask");
                int cntDA = 0;
                for (int i = 0; i < mask.length(); i++) {
                    JSONObject record = mask.getJSONObject(i);
                    part_name = record.getString("Part_Name");
                    part_name_rus = record.getString("Part_Name_rus");
                    article = record.getString("Article");
                    minPriceFinal = record.getString("minPriceFinal");
                    minPriceType = record.getString("minPriceType");
                    minPriceArticle = record.getString("minPriceArticle");
                    if (Integer.parseInt(minPriceFinal) > 0) {
                        cntDA++;
                        generateDetailBlockHard(part_name, article, part_name_rus, minPriceFinal,
                                minPriceType, minPriceArticle, cntDA);
//                        if(getSharedValueInt("tokenMainPage") > 0) {
//                        }
                    }

//                    if(isActivateSideViewDetails == 0){
////                        activateSideViewDetails(sideView, getCodeFromName(part_name), part_name, article, part_name_rus);
////                        activateSideViewDetails();
//                        if(i == mask.length() -1 ) { isActivateSideViewDetails = 1; }
//                    }
                }
//                setSharedValueInt("tokenMainPage", 0);
                unDuplicateWheel = 0;

                tvSideview.setText(getSideViewTitleRus(sideView));
//            Log.d("BERTUM----sideView", sideView );
                Context context = carSideView.getContext();
                int id = context.getResources().getIdentifier(getSideViewImage(sideView),
                        "drawable", context.getPackageName());
                carSideView.setImageResource(id);

                cntDetailsFound.setText( String.valueOf(mask.length()) );
                cntDetailsAvailable.setText( String.valueOf(cntDA) );
            }else{
                Toast.makeText(DetailsListActivity.this, Const.MSG_ERROR_AI_SERVICE, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Log.d("BERTUM---JSONException", e.getMessage() );
//            e.printStackTrace();
        }

//        activateSideView("front_right");

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences(Const.SHARE_STORE, MODE_APPEND);

        sumRepairs.setText(String.valueOf(getSharedValueInt("cartSum")));
        sumRepairsWork.setText(String.valueOf(getSharedValueInt("cartRepairWork")));
        cntTotalItems.setText(String.valueOf(getSharedValueInt("cartCnt")));
    }

    private void activateSideViewDetails(String sideView, String detailCode,  String part_name,  String article,  String part_name_rus){
        /*
         * Сколько на отображение распознанно для этого sideView
         * Сгенерировать названия
         * Сгенерировать ImageView
         * Получить заранее определенные соответствующие координаты каждой детали
         * Присвоить к parent
         * */
        String prefixImg = Const.IMG_PREFIX_033 + sideView+"_";
        String fName = prefixImg + detailCode;
        if(sideView.equals(Const.VIEW_RIGHT_FRONT)){
            initDetail( prefixImg+"roof", 75,  65, "roof", "6RU817111B", "Крыша" );
            initDetail( prefixImg+"hood", 174,  customBiasValue(110,  2), "hood", "6RU823031C", "Капот" );
            initDetail( prefixImg+"front_bumper", 190,  160, "front_bumper", "6RU807221A", "Бампер передний" );
            initDetail( prefixImg+"windshield", 144,  72, "windshield", "6RU845011J", "Лобовое стекло" );
            initDetail( prefixImg+"central_bumper_grille", 273, 146, "central_bumper_grille", "6RU853651D", "Решетка бампера центральная" );
            initDetail( prefixImg+"front_right_door", 97,  customBiasValue(68, -2 ), "front_right_door", "6RU831056J", "Дверь передняя правая" );
            initDetail( prefixImg+"front_right_wing", 150, 110, "front_right_wing", "6RU821106C", "Крыло переднее правое" );
            initDetail( prefixImg+"front_right_headlight", 212,  147, "front_right_headlight", "6RU941016", "Фара передняя правая" );
            initDetail( prefixImg+"rear_right_wing", 42, 79, "rear_right_wing", "6RU809605A", "Крыло заднее правое" );
            initDetail( prefixImg+"rear_right_door", 64, 103, "rear_right_door", "6RU833056D", "Дверь задняя правая" );
            initDetail( prefixImg+"front_wheel", 160,  163, "front_wheel", "6Q0601027AC", "Колесо" );
            initDetail( prefixImg+"rear_wheel", 51,  138, "rear_wheel", "6Q0601027AC", "Колесо" );
        }else if(sideView.equals(Const.VIEW_FRONT_FRONT)){
            initDetail( prefixImg+"hood", 95,  100, "hood", "6RU823031C", "Капот" );
            initDetail( prefixImg+"roof", 111,  48, "roof", "6RU817111B", "Крыша" );
            initDetail( prefixImg+"front_bumper", 76, 160, "front_bumper", "6RU807221A", "Бампер передний" );
            initDetail( prefixImg+"windshield", 111,  55, "windshield", "6RU845011J", "Лобовое стекло" );
            initDetail( prefixImg+"central_bumper_grille", 118, 159, "central_bumper_grille", "6RU853651D", "Решетка бампера центральная" );
            initDetail( prefixImg+"front_left_headlight", 260,  139, "front_left_headlight", "6RU941015", "Фара передняя левая" );
            initDetail( prefixImg+"front_right_headlight", 83,  139, "front_right_headlight", "6RU941016", "Фара передняя правая" );
        }else if(sideView.equals(Const.VIEW_LEFT_FRONT)){
            initDetail( prefixImg+"hood", 49,  112, "hood", "6RU823031C", "Капот");
            initDetail( prefixImg+"roof", 119,  customBiasValue(65,2),"roof", "6RU817111B", "Крыша");
            initDetail( prefixImg+"windshield", 117,  75, "windshield", "6RU845011J", "Лобовое стекло");
            initDetail( prefixImg+"front_bumper", 34, 160, "front_bumper", "6RU807221A", "Бампер передний");
            initDetail( prefixImg+"central_bumper_grille", 43, 147, "central_bumper_grille", "6RU853651D", "Решетка бампера центральная");
            initDetail( prefixImg+"front_left_door", 240,  customBiasValue(74,-3),"front_left_door", "6RU831055J", "Дверь передняя левая");
            initDetail( prefixImg+"front_left_wing", 162,  customBiasValue(112,-2),"front_left_wing", "6RU821105C", "Крыло переднее левое");
            initDetail( prefixImg+"front_left_headlight", 114,  148, "front_left_headlight", "6RU941015", "Фара передняя левая");
            initDetail( prefixImg+"rear_left_wing", 318, customBiasValue(81,-2),"rear_left_wing", "6RU809605A", "Крыло заднее левое");
            initDetail( prefixImg+"rear_left_door", 295,  103, "rear_left_door", "6RU833055D", "Дверь задняя левая");
            initDetail( prefixImg+"front_wheel", 193,  168, "front_wheel", "6Q0601027AC", "Колесо");
            initDetail( prefixImg+"rear_wheel", 319,  138, "rear_wheel", "6Q0601027AC", "Колесо");
        }else if(sideView.equals(Const.VIEW_LEFT_LEFT)){
            initDetail( prefixImg+"front_bumper", 16, 186, "front_bumper", "6RU807221A", "Бампер передний");
            initDetail( prefixImg+"front_left_door", 117,  117, "front_left_door", "6RU831055J", "Дверь передняя левая");
            initDetail( prefixImg+"front_left_wing", 36,  149, "front_left_wing", "6RU821105C", "Крыло переднее левое");
            initDetail( prefixImg+"front_left_headlight", 363,  153, "front_left_headlight", "6RU941015", "Фара передняя левая");
            initDetail( prefixImg+"rear_left_light", 21,  172, "rear_left_light", "6RU945095M", "Фонарь задний левый");
            initDetail( prefixImg+"hood", 26,  142, "hood", "6RU823031C", "Капот");
            initDetail( prefixImg+"roof", 113,  108, "roof", "6RU817111B", "Крыша");
            initDetail( prefixImg+"rear_bumper", 336,  173, "rear_bumper", "6RU807421FGRU", "Бампер задний");
            initDetail( prefixImg+"rear_left_door", 213,  115, "rear_left_door", "6RU833055D", "Дверь задняя левая");
            initDetail( prefixImg+"rear_left_wing", 263, customBiasValue(123, -2), "rear_left_wing", "6RU809605A", "Крыло заднее левое");
            initDetail( prefixImg+"front_wheel", 50,  190, "front_wheel", "6Q0601027AC", "Колесо");
            initDetail( prefixImg+"rear_wheel", customBiasValue(284, 2),  customBiasValue(186, -2), "rear_wheel", "6Q0601027AC", "Колесо");
        }else if(sideView.equals(Const.VIEW_LEFT_BACK)){
            initDetail( prefixImg+"front_left_wing", 24, customBiasValue(121, -2), "front_left_wing", "6RU821105C", "Крыло переднее левое" );
            initDetail( prefixImg+"front_left_door", 47,  customBiasValue(80, -2), "front_left_door", "6RU831055J", "Дверь передняя левая" );
            initDetail( prefixImg+"roof", 77,  customBiasValue(69, -1), "roof", "6RU817111B", "Крыша" );
            initDetail( prefixImg+"rear_bumper", 166,  155, "rear_bumper", "6RU807421FGRU", "Бампер задний" );
            initDetail( prefixImg+"rear_left_door", 85,  customBiasValue(77, -2), "rear_left_door", "6RU833055D", "Дверь задняя левая" );
            initDetail( prefixImg+"rear_left_wing", 119, customBiasValue(82, -3), "rear_left_wing", "6RU809605A", "Крыло заднее левое" );
            initDetail( prefixImg+"rear_body_glass", 166,  customBiasValue(77, -2), "rear_body_glass", "6RU845051F", "Стекло кузова заднее" );
            initDetail( prefixImg+"trunk_lid", 205,  110, "trunk_lid", "6RU827025F", "Крышка багажника" );
            initDetail( prefixImg+"rear_left_light", 202,  135, "rear_left_light", "6RU945095M", "Фонарь задний левый" );
            initDetail( prefixImg+"front_wheel", 26,  149, "front_wheel", "6Q0601027AC", "Колесо" );
            initDetail( prefixImg+"rear_wheel", 133,  177, "rear_wheel", "6Q0601027AC", "Колесо" );
        }else if(sideView.equals(Const.VIEW_BACK_BACK)){
            initDetail( prefixImg+"rear_body_glass", 106, 62, "rear_body_glass", "6RU845051F", "Стекло кузова заднее" );
            initDetail( prefixImg+"rear_bumper", 74,  160, "rear_bumper", "6RU807421FGRU", "Бампер задний" );
            initDetail( prefixImg+"rear_left_wing", 81,  82, "rear_left_wing", "6RU809605A", "Крыло заднее левое" );
            initDetail( prefixImg+"rear_right_wing", 275, 82, "rear_right_wing", "6RU809605A", "Крыло заднее правое" );
            initDetail( prefixImg+"roof", 111,  56, "roof", "6RU817111B", "Крыша" );
            initDetail( prefixImg+"trunk_lid", 98,  98, "trunk_lid", "6RU827025F", "Крышка багажника" );
            initDetail( prefixImg+"rear_left_light", 77,  123, "rear_left_light", "6RU945095M", "Фонарь задний левый" );
            initDetail( prefixImg+"right_rear_light", 279,  123, "right_rear_light", "6RU945096K", "Фонарь задний правый" );
        }else if(sideView.equals(Const.VIEW_RIGHT_BACK)){
            initDetail( prefixImg+"front_right_wing", 342, customBiasValue(119, -1), "front_right_wing", "6RU821106C", "Крыло переднее правое");
            initDetail( prefixImg+"front_right_door", 285,  customBiasValue(79, -2), "front_right_door", "6RU831056J", "Дверь передняя правая");
            initDetail( prefixImg+"roof", 136,  70, "roof", "6RU817111B", "Крыша");
            initDetail( prefixImg+"rear_bumper", 27,  customBiasValue(157, -2), "rear_bumper", "6RU807421FGRU", "Бампер задний");
            initDetail( prefixImg+"rear_right_door", 244,  customBiasValue(78, -2), "rear_right_door", "6RU833056D", "Дверь задняя правая");
            initDetail( prefixImg+"rear_right_wing", 143, customBiasValue(82, -3), "rear_right_wing", "6RU809605A", "Крыло заднее правое");
            initDetail( prefixImg+"rear_body_glass", 82,  77, "rear_body_glass", "6RU845051F", "Стекло кузова заднее");
            initDetail( prefixImg+"trunk_lid", 33,  110, "trunk_lid", "6RU827025F", "Крышка багажника");
            initDetail( prefixImg+"rear_right_light", 133,  135, "rear_right_light", "6RU945096K", "Фонарь задний правый");
            initDetail( prefixImg+"front_wheel", 340,  149, "front_wheel", "6Q0601027AC", "Колесо");
            initDetail( prefixImg+"rear_wheel", 208,  175, "rear_wheel", "6Q0601027AC", "Колесо");
        }else if(sideView.equals(Const.VIEW_RIGHT_RIGHT)){
            initDetail( prefixImg+"front_bumper", 352, 170, "front_bumper", "6RU807221A", "Бампер передний");
            initDetail( prefixImg+"front_right_door", 190,  103, "front_right_door", "6RU831056J", "Дверь передняя правая");
            initDetail( prefixImg+"front_right_wing", 289,  138, "front_right_wing", "6RU821106C", "Крыло переднее правое");
            initDetail( prefixImg+"front_right_headlight", 361,  158, "front_right_headlight", "6RU941016", "Фара передняя правая");
            initDetail( prefixImg+"rear_right_light", 15,  144, "rear_right_light", "6RU945096K", "Фонарь задний правый");
            initDetail( prefixImg+"hood", 286,  130, "hood", "6RU823031C", "Капот");
            initDetail( prefixImg+"roof", 96,  96, "roof", "6RU817111B", "Крыша");
            initDetail( prefixImg+"rear_bumper", 10,  166, "rear_bumper", "6RU807421FGRU", "Бампер задний");
            initDetail( prefixImg+"rear_right_door", 98,  103, "rear_right_door", "6RU833056D", "Дверь задняя правая");
            initDetail( prefixImg+"rear_right_wing", 15, customBiasValue(112, -2), "rear_right_wing", "6RU809605A", "Крыло заднее правое");
            initDetail( prefixImg+"front_wheel", 303,  178, "front_wheel", "6Q0601027AC", "Колесо");
            initDetail( prefixImg+"rear_wheel", 62,  182, "rear_wheel", "6Q0601027AC", "Колесо");
        }
    }
    private int customBiasValue(int defaultValue, int bias){
        Resources r = this.getResources();
        int a =  r.getDisplayMetrics().widthPixels;
        int b  = r.getDisplayMetrics().heightPixels;
        if( a == 1080 && b == 2264
                || a == 1080 && b == 2120
                || a == 1080 && b == 2291
        ){
            return defaultValue + bias;
        }
        return defaultValue;
    }
    private void initDetail(String fName, int leftDp, int topDp,  String part_name,  String article,  String part_name_rus){
        Resources r = this.getResources();
        float density = r.getDisplayMetrics().density;
        double wCar = carSideView.getWidth();
        double hCar = carSideView.getHeight();

//        fName = "m3d_vw6_sed_x033_front_right_front_bumper";
        Context context = carSideView.getContext();
        int detailImageId = context.getResources().getIdentifier(fName, "drawable", context.getPackageName());
        if(detailImageId > 0) {
            Drawable drawable = getResources().getDrawable(detailImageId);
            int widthImage = drawable.getIntrinsicWidth();
            int heightImage = drawable.getIntrinsicHeight();
            double wDetail = widthImage / density;
            double hDetail = heightImage / density;

            double detailPxWidth = wDetail; // 173
            double detailPxHeight = hDetail; // 70
            double detailPxLeft = leftDp; // 190
            double detailPxTop = topDp; // 160

            double ratioWidth = 396 / detailPxWidth; //2.289017341040462;
            double ratioHeight = 297 / detailPxHeight; //4.242857142857143;
            double ratioLeft = 396 / detailPxLeft; //2.084210526315789;
            double ratioTop = 297 / detailPxTop; // 1.84472049689441;

            int left = (int) (wCar / ratioLeft);
            //        int top = (int) (hCar/ ratioTop );
            double calcHeight = wCar / 1.333333;
            int biasHeight = (int) (((hCar - calcHeight) * 0.3333) * 0.3333);
            int top = (int) (calcHeight / ratioTop - biasHeight); //  - (((780-613)*0.3333) /2 ));
            // define Main Image height once
            areaSideView.getLayoutParams().height = (int) calcHeight +10;

            // init new Detail
            ImageView elem = new ImageView(this);
            RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            elemParams.width = (int) (wCar / ratioWidth);
            elemParams.height = (int) (hCar / ratioHeight);
            elemParams.setMargins(left, top, 0, 0);
            elem.setLayoutParams(elemParams);

            elem.setImageResource(detailImageId);

            final String da = article;
            final String ta = part_name;
            final String tr = part_name_rus;
            elem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1.save detail id
                    setSharedValueStr("detail_article", da);
                    setSharedValueStr("detail_title", ta);
                    setSharedValueStr("detail_title_rus", tr);
                    // 2.redirect to page-Prices
                    startActivity(new Intent(DetailsListActivity.this, ProxyActivity.class));
                    //Toast.makeText(DetailsListActivity.this, da, Toast.LENGTH_SHORT).show();
                }
            });
            areaSideView.addView(elem);
        }
    }


    public static  String getSideViewImage(String engSideView) {
        return "m3d_vw6_sed_x033_"+engSideView;
    }

    public static  String getSideViewTitleRus(String engSideView) {
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

    private void calculateBias(){
        Resources r = this.getResources();
        int currentWidth = Math.round(r.getDisplayMetrics().widthPixels / r.getDisplayMetrics().density);
        setBias((int) Math.round(  (Const.SETS_WIDTH_KOEF_DEFAULT - currentWidth) *0.3333/ 0.3333 ));
    }

    private void setBias(int val){
        this.bias = val;
    }

    private int getBias(){
        return this.bias;
    }


    private void initDetailPhoto(String fName, int left, int top,  String part_name,  String article,  String part_name_rus){
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

    private void generateDetailBlockHard(String part_name, String detail_article, String part_name_rus,
                                         final String minPriceFinal, final String minPriceType, final String minPriceArticle, int cntDA){
        Context context = ll_parent.getContext();
        String name = "rl_detail_"+ String.valueOf(cntDA);
        int rlDetail = getResources().getIdentifier(name, "id", getPackageName());

        if(rlDetail > 0) {
            final RelativeLayout rl = findViewById(rlDetail);
            rl.setVisibility(View.VISIBLE);
            TextView detailTitle = findViewById(context.getResources().getIdentifier("detail_title_"+ String.valueOf(cntDA), "id", context.getPackageName()) );
            detailTitle.setText(part_name_rus);
            TextView detailArticle = findViewById(context.getResources().getIdentifier("detail_article_"+ String.valueOf(cntDA), "id", context.getPackageName()) );
            detailArticle.setText(detail_article);
            TextView detailMinPrice = findViewById(context.getResources().getIdentifier("detail_minprice_"+ String.valueOf(cntDA), "id", context.getPackageName()) );
            detailMinPrice.setText(minPriceFinal);
            final Button detailButton = findViewById(context.getResources().getIdentifier("detail_prices_"+ String.valueOf(cntDA), "id", context.getPackageName()) );
            detailButton.setTextColor(Color.parseColor("#FFFFFF"));
            detailButton.setBackgroundColor(Color.parseColor("#F43934"));

            // set detail photo
            ImageView detailPhoto = findViewById(context.getResources().getIdentifier("detail_photo_"+ String.valueOf(cntDA), "id", context.getPackageName()) );
            int detailPhotoId = context.getResources().getIdentifier("photo_det_"+detail_article.toLowerCase(), "drawable", context.getPackageName());
            if(detailPhotoId == 0) {
                detailPhotoId = context.getResources().getIdentifier("plate_" + getCodeFromName(part_name), "drawable", context.getPackageName());
                if (detailPhotoId == 0) {
                    detailPhotoId = context.getResources().getIdentifier("detail_noimage", "drawable", context.getPackageName());
                }
            }
            detailPhoto.setImageResource(detailPhotoId);

            final String da = detail_article;
            final String ta = part_name;
            final String tr = part_name_rus;
            final String mpt = minPriceType;
            final String mpa = minPriceArticle;
            final String mpf = minPriceFinal;
            detailPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1.save detail id
                    saveSelectedDetailProps( da,  ta,  tr,  mpt,  mpa, mpf );
                    // 2.redirect to page-Prices
                    startActivity(new Intent(DetailsListActivity.this, ProxyActivity.class));
                }
            });
            ImageView detailClose = findViewById(context.getResources().getIdentifier("detail_close_"+ String.valueOf(cntDA), "id", context.getPackageName()) );

            detailClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl.setVisibility(View.GONE);
                }
            });

            detailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1.save detail id
                    saveSelectedDetailProps( da,  ta,  tr,  mpt,  mpa ,  mpf );
                    // 2.addToCart
                    addToCart(Integer.parseInt(minPriceFinal));
//                    startActivity(new Intent(DetailsListActivity.this, ProxyActivity.class));
                }
            });
//            int id = context.getResources().getIdentifier(MappingDetails.getSideViewImage(sideView),
//                    "drawable", context.getPackageName());
//            carSideView.setImageResource(id);
        }
    }

    private void saveSelectedDetailProps(String da, String ta, String tr, String mpt, String mpa, String mpf ){
        setSharedValueStr("detail_article", da );
        setSharedValueStr("detail_title", ta );
        setSharedValueStr("detail_title_rus", tr );
        setSharedValueStr("detail_minpice_type", mpt );
        setSharedValueStr("detail_minpice_article", mpa );
        setSharedValueStr("detail_minpice_final", mpf );
    }
    private void addToCart(int detailPrice){
        // цена товара 1
        setSubDetailsVisible();
        setSharedValueInt("repairMainDetailVisible", 1);
        setSharedValueInt("repairSubDetailVisible", 0);
        cartSumRepairs = detailPrice;
        cartSumRepairsWork = DataStore.getSumRepairsWork( getSharedValueStr("detail_article") );
        cartCntRepairs = 1;

        sumRepairs.setText(String.valueOf(cartSumRepairs));
        cntTotalItems.setText(String.valueOf(cartCntRepairs));
        sumRepairsWork.setText(String.valueOf(cartSumRepairsWork) );

        setSharedValueInt("cartRepairWork", cartSumRepairsWork);
        setSharedValueInt("cartSum", cartSumRepairs);
        setSharedValueInt("cartCnt", cartCntRepairs);

        Toast.makeText(DetailsListActivity.this, "Деталь добавлена в корзину", Toast.LENGTH_SHORT).show();

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
        DataStore.setMenuItems(item, DetailsListActivity.this);
        return true;
    }

    private void setSubDetailsVisible(){
        ArrayList<String> subDetailsArticles = DataStore.getSubDetailsArticles(
                getSharedValueStr("detail_article"));
        for (int i = 1; i < 11; i++) {
            if(i <= subDetailsArticles.size()+1) setSharedValueInt("subDetailVisible_" + String.valueOf(i), 1);
            else  setSharedValueInt("subDetailVisible_" + String.valueOf(i), 0);

            setSharedValueInt("subDetailBtnBuy_" + String.valueOf(i), 1);
        }
    }

}