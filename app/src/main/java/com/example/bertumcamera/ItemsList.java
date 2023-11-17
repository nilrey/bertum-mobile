package com.example.bertumcamera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ItemsList extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private ConstraintLayout layoutButtons, areaInformer;
    private ImageView origDetailImage, nonorigDetailImage, usedDetailImage, discontDetailImage, ico_cart;
    private TextView detailTitle, detailArticle, cntSearchRresult, linkToBack,
            origDetailTitle, nonorigDetailTitle, usedDetailTitle, discontDetailTitle,
            origDetailArticle, nonorigDetailArticle, usedDetailArticle, discontDetailArticle,
            origDetailPrice, nonorigDetailPrice, usedDetailPrice, discontDetailPrice,

            cntTotalItems, sumRepairs, sumRepairsWork, linkToSmetaRepairs , linkToSmetaDetails;
    private int cnt, low, high, cartSumRepairs, cartSumRepairsWork, cartCntRepairs, cntAllVariants = 0;
    private Button filterOrig;
    private Button filterNonOrig;
    private Button filterUsed;
    private Button filterDiscont;
    private Button origCartAdd;
    private Button nonorigCartAdd;
    private Button discontCartAdd;
    private String countTotal = "0", countOrig = "0", countNonorig = "0", countDiscont = "0", countUsed = "0",
            minPriceOrig = "0", minPriceNonorig = "0", minPriceDiscont = "0", minPriceUsed = "0",
            minPriceOrigArticle = "0", minPriceNonorigArticle = "0", minPriceDiscontArticle = "0", minPriceUsedArticle = "0",
            minPriceOrigTitle = "0", minPriceNonorigTitle = "0", minPriceDiscontTitle = "0", minPriceUsedTitle = "0",
            responseArticle = "";

    private ConstraintLayout origLayout, nonorigLayout, discontLayout, usedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setSharedValueInt("cntPhoto", 0);
        // результаты поиска
        detailTitle = findViewById(R.id.detail_title);
        detailArticle = findViewById(R.id.detail_article);
        cntSearchRresult = findViewById(R.id.count_search_result);
        // кнопки фильтров
        areaInformer = findViewById(R.id.areaInformer);
        linkToBack = findViewById(R.id.linkToBack);
        layoutButtons = findViewById(R.id.layoutButtons);
        filterOrig = findViewById(R.id.filter_orig);
        filterNonOrig = findViewById(R.id.filter_nonorig);
        filterUsed = findViewById(R.id.filter_used);
        filterDiscont = findViewById(R.id.filter_discont);
        // блоки цен
        origLayout = findViewById(R.id.orig_cl_details);
        nonorigLayout = findViewById(R.id.nonorig_cl_details);
        discontLayout = findViewById(R.id.discont_cl_details);
        usedLayout = findViewById(R.id.used_cl_details);
        // нижняя часть
        cntTotalItems = findViewById(R.id.cntTotalItems);
        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);
        linkToSmetaDetails = findViewById(R.id.linkToSmetaDetails);
        linkToSmetaRepairs = findViewById(R.id.linkToSmetaRepairs);
        // плашки разных типов цен
        origDetailImage = findViewById(R.id.orig_detail_image);
        origDetailTitle = findViewById(R.id.orig_detail_title);
        origDetailArticle = findViewById(R.id.orig_detail_article);
        origDetailPrice = findViewById(R.id.orig_detail_price);
        origCartAdd = findViewById(R.id.orig_cart_add);

        nonorigDetailImage = findViewById(R.id.nonorig_detail_image);
        nonorigDetailTitle = findViewById(R.id.nonorig_detail_title);
        nonorigDetailArticle = findViewById(R.id.nonorig_detail_article);
        nonorigDetailPrice = findViewById(R.id.nonorig_detail_price);
        nonorigCartAdd = findViewById(R.id.nonorig_cart_add);

        usedDetailImage = findViewById(R.id.used_detail_image);
        usedDetailTitle = findViewById(R.id.used_detail_title);
        usedDetailArticle = findViewById(R.id.used_detail_article);
        usedDetailPrice = findViewById(R.id.used_detail_price);
        Button usedCartAdd = findViewById(R.id.used_cart_add);

        discontDetailImage = findViewById(R.id.discont_detail_image);
        discontDetailTitle = findViewById(R.id.discont_detail_title);
        discontDetailArticle = findViewById(R.id.discont_detail_article);
        discontDetailPrice = findViewById(R.id.discont_detail_price);
        discontCartAdd = findViewById(R.id.discont_cart_add);

        // Наполнение данными
        detailTitle.setText( getSharedValueStr("detail_title_rus"));
        detailArticle.setText( getSharedValueStr("detail_article"));

        origDetailTitle.setText( getSharedValueStr("detail_title_rus"));
        origDetailArticle.setText( getSharedValueStr("detail_article"));

        nonorigDetailTitle.setText( getSharedValueStr("detail_title_rus"));
        nonorigDetailArticle.setText( getSharedValueStr("detail_article"));

        usedDetailTitle.setText( getSharedValueStr("detail_title_rus"));
        usedDetailArticle.setText( getSharedValueStr("detail_article"));

        discontDetailTitle.setText( getSharedValueStr("detail_title_rus"));
        discontDetailArticle.setText( getSharedValueStr("detail_article"));


        linkToSmetaDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSharedValueInt("smetaTab", 0);
                startActivity(new Intent(ItemsList.this, SmetaActivity.class));
            }
        });
        linkToSmetaRepairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSharedValueInt("smetaTab", 1);
                startActivity(new Intent(ItemsList.this, SmetaActivity.class));
            }
        });
        ico_cart = findViewById(R.id.ico_cart);
        ico_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemsList.this, CartActivity.class));
            }
        });

        // get data from API
        String jsonAiApi = getSharedValueStr("jsonDetailPrices");
//        setSharedValueStr("jsonDetailPrices", "no records");
        try {
            // get JSONObject from JSON file
            String titleDetail, part_name, article;
            JSONObject objects = new JSONObject (jsonAiApi);
            JSONObject settings = objects.getJSONObject("prices");
            JSONArray keys = settings.names ();
            for (int i = 0; i < keys.length(); ++i) {
                String key = keys.getString (i);
                JSONObject record = settings.getJSONObject(key);

                countTotal = record.getString("cntTotal");
                countOrig = record.getString("cntOrig");
                countNonorig = record.getString("cntNonorig");
                countDiscont = record.getString("cntDiscont");
                countUsed = record.getString("cntUsed");
                minPriceOrig = record.getString("minPriceOrig");
                minPriceNonorig = record.getString("minPriceNonorig");
                minPriceDiscont = record.getString("minPriceDiscont");
                minPriceUsed= record.getString("minPriceUsed");

                minPriceOrigArticle = record.getString("minPriceOrigArticle");
                minPriceNonorigArticle = record.getString("minPriceNonorigArticle");
                minPriceDiscontArticle = record.getString("minPriceDiscontArticle");
                minPriceUsedArticle = record.getString("minPriceUsedArticle");

                minPriceOrigTitle = record.getString("minPriceOrigTitle");
                minPriceNonorigTitle = record.getString("minPriceNonorigTitle");
                minPriceDiscontTitle = record.getString("minPriceDiscontTitle");
                minPriceUsedTitle = record.getString("minPriceUsedTitle");

                responseArticle = record.getString("article");
            }
//            Log.d("BERTUM----view_angle", view_angle );

        } catch (JSONException e) {
            Log.d("BERTUM---JSONException", e.getMessage() );
//            e.printStackTrace();
        }
        int cntTry = getSharedValueInt("countTry" );

        if( (jsonAiApi.equals("") && cntTry < 2)
        || ( (!getSharedValueStr("detail_article").equals(responseArticle))  && cntTry < 3 )
        ){
            startActivity(new Intent(ItemsList.this, ProxyActivity.class));
        }

        cntSearchRresult.setText(countTotal+ " вариантов");
        filterOrig.setText("ОРИГИНАЛ : " + countOrig);
        filterNonOrig.setText("НЕОРИГИНАЛ : " + countNonorig);
        filterUsed.setText("Б/У : " + countUsed);
        filterDiscont.setText("УЦЕНКА : " + countDiscont);
        origDetailPrice.setText(minPriceOrig);
        nonorigDetailPrice.setText(minPriceNonorig);
        usedDetailPrice.setText(minPriceUsed);
        discontDetailPrice.setText(minPriceDiscont);

        if( countDiscont.equals("0")){ discontLayout.setVisibility(View.GONE); }
        if( countUsed.equals("0")){ usedLayout.setVisibility(View.GONE); }
        if( countNonorig.equals("0")){ nonorigLayout.setVisibility(View.GONE); }
        if( countOrig.equals("0")) { origLayout.setVisibility(View.GONE); }

        if (Integer.parseInt(countTotal) > 0 ) {

            layoutButtons.setVisibility(View.VISIBLE);
            areaInformer.setVisibility(View.GONE);

            origCartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1.save detail id
                    saveSelectedDetailProps(responseArticle, getSharedValueStr("detail_title"),
                            minPriceOrigTitle, minPriceOrigTitle, minPriceOrigArticle, minPriceOrig);
                    // 2.redirect to page-Prices
                    addToCart(Integer.parseInt(minPriceOrig));
                }
            });
            nonorigCartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveSelectedDetailProps(responseArticle, getSharedValueStr("detail_title"),
                            minPriceNonorigTitle, minPriceNonorigTitle, minPriceNonorigArticle, minPriceNonorig);
                    addToCart(Integer.parseInt(minPriceNonorig));
                }
            });
            usedCartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveSelectedDetailProps(responseArticle, getSharedValueStr("detail_title"),
                            minPriceUsedTitle, minPriceUsedTitle, minPriceUsedArticle, minPriceUsed);
                    addToCart(Integer.parseInt(minPriceUsed));
                }
            });
            discontCartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveSelectedDetailProps(responseArticle, getSharedValueStr("detail_title"),
                            minPriceDiscontTitle, minPriceDiscontTitle, minPriceDiscontArticle, minPriceDiscont);
                    addToCart(Integer.parseInt(minPriceDiscont));
                }
            });
        }else{
            layoutButtons.setVisibility(View.GONE);
            areaInformer.setVisibility(View.VISIBLE);
            linkToBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
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
    private void addToCart(int minPrice){
        // достаем сохраненные данные
//        int cartSum = getSharedValueInt("cartSum"); // get saved summ
//        int cartCnt = getSharedValueInt("cartCnt"); // get saved cnt
//        int cartSumRepWork = getSharedValueInt("cartRepairWork"); // get saved cnt

//        cartSumRepairs = cartSum + minPrice;
//        cartSumRepairsWork = cartSumRepWork + getSumRepairsWork( getSharedValueStr("detail_article") );
//        cartCntRepairs = cartCnt + 1;

        // цена товара 1
        cartSumRepairs = minPrice;
        cartSumRepairsWork = DataStore.getSumRepairsWork( getSharedValueStr("detail_article") );
        cartCntRepairs = 1;

        sumRepairs.setText(String.valueOf(cartSumRepairs));
        cntTotalItems.setText(String.valueOf(cartCntRepairs));
        sumRepairsWork.setText(String.valueOf(cartSumRepairsWork) );
        setSharedValueInt("cartRepairWork", cartSumRepairsWork);

        setSharedValueInt("cartSum", cartSumRepairs);
        setSharedValueInt("cartCnt", cartCntRepairs);

        Toast.makeText(ItemsList.this, "Деталь добавлена в корзину", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences(Const.SHARE_STORE, MODE_APPEND);
        int b = sh.getInt("cartSum", 0);
        sumRepairs.setText(String.valueOf(b));
        int c = sh.getInt("cartRepairWork", 0);
        sumRepairsWork.setText(String.valueOf(c));
        int d = sh.getInt("cartCnt", 0);
        cntTotalItems.setText(String.valueOf(d));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSharedValueStr("ItemsListBackPage").equals("ImageDragActivity") ){
            startActivity(new Intent(ItemsList.this, ImageDragActivity.class));
        }else if(getSharedValueStr("ItemsListBackPage").equals("DetailsListActivity") ){
            startActivity(new Intent(ItemsList.this, DetailsListActivity.class));
        }else{
            startActivity(new Intent(ItemsList.this, MainActivity.class));
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
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_APPEND);
        return sharedPreferences.getInt(name, 0);
    }
    private String getSharedValueStr(String name){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_APPEND);
        return sharedPreferences.getString(name, "");
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
}