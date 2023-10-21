package com.example.bertumcamera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
    private ImageView origDetailImage, nonorigDetailImage, usedDetailImage, discontDetailImage;
    private TextView detailTitle, detailArticle, cntSearchRresult,
            origDetailTitle, nonorigDetailTitle, usedDetailTitle, discontDetailTitle,
            origDetailArticle, nonorigDetailArticle, usedDetailArticle, discontDetailArticle,
            origDetailPrice, nonorigDetailPrice, usedDetailPrice, discontDetailPrice,

            cntTotalItems, sumRepairs, sumRepairsWork;
    private int cnt, low, high, cartSumRepairs, cartSumRepairsWork, cartCntRepairs, cntAllVariants = 0;
    private Button filterOrig, filterNonOrig, filterUsed, filterDiscont,
            origCartAdd, nonorigCartAdd, usedCartAdd, discontCartAdd;
    private String countTotal, countOrig, countNonorig, countDiscont, countUsed,
            minPriceOrig, minPriceNonorig, minPriceDiscont, minPriceUsed;

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
        filterOrig = findViewById(R.id.filter_orig);
        filterNonOrig = findViewById(R.id.filter_nonorig);
        filterUsed = findViewById(R.id.filter_used);
        filterDiscont = findViewById(R.id.filter_discont);
        // блоки цен
        origLayout = findViewById(R.id.orig_cl_details);
        nonorigLayout = findViewById(R.id.nonorig_cl_details);
        discontLayout = findViewById(R.id.discont_cl_details);
        usedLayout = findViewById(R.id.orig_cl_details);
        // нижняя часть
        cntTotalItems = findViewById(R.id.cntTotalItems);
        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);
        // плашки разных типов цен
        origDetailImage = findViewById(R.id.orig_detail_image);
//        origDetailTitle = findViewById(R.id.orig_detail_title);
//        origDetailArticle = findViewById(R.id.orig_detail_article);
        origDetailPrice = findViewById(R.id.orig_detail_price);
        origCartAdd = findViewById(R.id.orig_cart_add);

        nonorigDetailImage = findViewById(R.id.nonorig_detail_image);
//        nonorigDetailTitle = findViewById(R.id.nonorig_detail_title);
//        nonorigDetailArticle = findViewById(R.id.nonorig_detail_article);
        nonorigDetailPrice = findViewById(R.id.nonorig_detail_price);
        nonorigCartAdd = findViewById(R.id.nonorig_cart_add);

        usedDetailImage = findViewById(R.id.used_detail_image);
//        usedDetailTitle = findViewById(R.id.used_detail_title);
//        usedDetailArticle = findViewById(R.id.used_detail_article);
        usedDetailPrice = findViewById(R.id.used_detail_price);
        usedCartAdd = findViewById(R.id.used_cart_add);

        discontDetailImage = findViewById(R.id.discont_detail_image);
//        discontDetailTitle = findViewById(R.id.discont_detail_title);
//        discontDetailArticle = findViewById(R.id.discont_detail_article);
        discontDetailPrice = findViewById(R.id.discont_detail_price);
        discontCartAdd = findViewById(R.id.discont_cart_add);

        // Наполнение данными
        detailTitle.setText( getSharedValueStr("detail_title"));
        detailArticle.setText( getSharedValueStr("detail_article"));
        // get data from API

        String jsonAiApi = getSharedValueStr("jsonDetailPrices");
        setSharedValueStr("jsonDetailPrices", "no records");
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
            }
//            Log.d("BERTUM----view_angle", view_angle );

        } catch (JSONException e) {
            Log.d("BERTUM---JSONException", e.getMessage() );
//            e.printStackTrace();
        }

        if(countTotal == null){
            startActivity(new Intent(ItemsList.this, ProxyActivity.class));
        }

        cntSearchRresult.setText(countTotal+ " вариантов");
        filterOrig.setText("ОРИГИНАЛ : " + countOrig);
        filterNonOrig.setText("НЕОРИГИНАЛ : " + countNonorig);
        filterUsed.setText("Б/У : " + countUsed);
        filterDiscont.setText("УЦЕНКА : " + countDiscont);
        origDetailPrice.setText(minPriceOrig);
        nonorigDetailPrice.setText(minPriceNonorig);
        usedDetailPrice.setText(minPriceDiscont);
        discontDetailPrice.setText(minPriceUsed);

//        if( countDiscont.equals("0")){ discontLayout.setVisibility(View.GONE); }
//        if( countUsed.equals("0")){ usedLayout.setVisibility(View.GONE); }
//        if( countNonorig.equals("0")){ nonorigLayout.setVisibility(View.GONE); }
//        if( countOrig.equals("0")) { origLayout.setVisibility(View.GONE); }
/*
        origCartAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // достаем сохраненные данные
                Log.d("BERTUM---------------", "OnClick");
                int cartSum = getSharedValueInt("cartSum"); // get saved summ
                int cartCnt = getSharedValueInt("cartCnt"); // get saved cnt

                // цена товара 1
                int price1 = Integer.parseInt(origDetailPrice.getText().toString()); // get added price
                cartSumRepairs = cartSum + price1;
                cartCntRepairs = cartCnt + 1;

                sumRepairs.setText(String.valueOf(cartSumRepairs));
                cntTotalItems.setText(String.valueOf(cartCntRepairs));

                Random r = new Random();
                low = 6000;
                high = 18500;
                cartSumRepairsWork = r.nextInt(high-low) + low;
                sumRepairsWork.setText(String.valueOf(cartSumRepairsWork) );
                setSharedValueInt("cartRepairWork", cartSumRepairsWork);

                setSharedValueInt("cartSum", cartSumRepairs);
                setSharedValueInt("cartCnt", cartCntRepairs);

                Toast.makeText(ItemsList.this, "Деталь добавлна в корзину", Toast.LENGTH_SHORT).show();
            }
        });*/

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
        startActivity(new Intent(ItemsList.this, MainActivity.class));
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

}