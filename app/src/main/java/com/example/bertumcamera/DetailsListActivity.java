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
    private TextView responseTV;
    // 3D Model vars
    ImageView layoutCarRF, doorRightFrontRF;
    private ImageButton arrowPlus, arrowMinus, arrowLeft, arrowRight;
    private String msgDetailSelected;
    float x, y;
    float dx, dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);
        // 3D Model

        layoutCarRF = findViewById(R.id.layoutCarRF);
        doorRightFrontRF = findViewById(R.id.doorRightFrontRF);
        arrowPlus = findViewById(R.id.arrow_plus);
        arrowMinus = findViewById(R.id.arrow_minus);
        msgDetailSelected = getResources().getString(R.string.msg_detail_selected);

//        responseTV = findViewById(R.id.idTVResponse);
        // TEST get JSON AI API
//        postDataUsingVolley("123456789", "fasdfasdf");
        String jsonAiApi = getSharedValueStr("jsonAiApi");
        String view_angle = "";
        String cache_file_name = "";
        String file_size = "";

        try {
            // get JSONObject from JSON file
            String titleDetail, part_name, article;
            JSONObject objects = new JSONObject (jsonAiApi);
            JSONArray mask  = objects.getJSONArray("mask");
            for (int i = 0; i < mask.length(); i++)
            {
                JSONObject record = mask.getJSONObject(i);
                part_name = record.getString("Part_Name");
                article = record.getString("Article");
                generateDetailBlock(part_name, article);
            }

            JSONObject settings = objects.getJSONObject("settings");
            JSONArray keys = settings.names ();
            for (int i = 0; i < keys.length(); ++i) {
                String key = keys.getString (i);
                JSONObject objDetail = settings.getJSONObject(key);
                view_angle = objDetail.getString ("view");
                cache_file_name = objDetail.getString ("cache");
                file_size = objDetail.getString ("file_size");
            }
            Log.d("BERTUM----view_angle", view_angle );

        } catch (JSONException e) {
            Log.d("BERTUM---JSONException", e.getMessage() );
//            e.printStackTrace();
        }

        // 3D model functions

        arrowMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if( layoutCarRF.getScaleX() > 1 ) {
                    startActivity(new Intent(DetailsListActivity.this, DetailsListActivity.class));
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

    @Override
    public boolean onTouchEvent(MotionEvent event) { // 3D model actions
        if(layoutCarRF.getScaleX() > 1.0 ) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                x = event.getX();
                y = event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                dx = event.getX() - x;
                dy = event.getY() - y;

                layoutCarRF.setX(layoutCarRF.getX() + dx);
                layoutCarRF.setY(layoutCarRF.getY() + dy);

                doorRightFrontRF.setX(doorRightFrontRF.getX() + dx);
                doorRightFrontRF.setY(doorRightFrontRF.getY() + dy);

                x = event.getX();
                y = event.getY();
            }
        }
        return super.onTouchEvent(event);
    }

    private void generateDetailBlock(String title, String detail_article){
        // Dynamic ids for controls
        int layoutRelativeId = ViewCompat.generateViewId();
        int imageViewId = ViewCompat.generateViewId();
        int textTitleId = ViewCompat.generateViewId();
        int textArticleId = ViewCompat.generateViewId();
        int btnPricesId = ViewCompat.generateViewId();
        LinearLayout ll_parent = findViewById(R.id.ll_details);
        // Create detail Relative layout
        RelativeLayout ll_new_block = new RelativeLayout(this);
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        elemParams.setMargins( 0,  convertDpToPixels(this, 20),  0,  0);
        ll_new_block.setLayoutParams(elemParams);
        ll_new_block.setId(layoutRelativeId);
        ll_new_block.setGravity(Gravity.CENTER_HORIZONTAL);
        ll_new_block.addView(genImageView(imageViewId ));
        //set detail title
        ll_new_block.addView(setDetailTitle(textTitleId, title, imageViewId));
        // set detail article
        ll_new_block.addView(setArticleTitle(textArticleId, detail_article, imageViewId));
        // Create Button
        ll_new_block.addView(genButton(btnPricesId, title, "Цены", imageViewId , detail_article));

        ll_parent.addView(ll_new_block);

    }

    private TextView setDetailTitle(int elem_id, String title, int parent_id){
        TextView elem = genTextView( elem_id,  title,  parent_id);
        elem.setTextSize(20);
        RelativeLayout.LayoutParams elemParams = (RelativeLayout.LayoutParams)elem.getLayoutParams();
        elemParams.setMargins( convertDpToPixels(this, 0),  convertDpToPixels(this, 150),  0,  0);
        elem.setLayoutParams(elemParams);
        return elem;
    }

    private TextView setArticleTitle(int elem_id, String title, int parent_id){
        TextView elem = genTextView( elem_id,  title,  parent_id);
        elem.setTextSize(20);
        RelativeLayout.LayoutParams elemParams = (RelativeLayout.LayoutParams)elem.getLayoutParams();
        elemParams.setMargins( convertDpToPixels(this, 0),  convertDpToPixels(this, 176),  0,  0);
        elem.setLayoutParams(elemParams);
        return elem;
    }

    private TextView genTextView(int elem_id, String title, int parent_id) {
        TextView elem = new TextView(this);
        elem.setId(elem_id);
        elem.setText(title);
        elem.setTextSize(16);
//        elem.setBackgroundColor(0xff66ff66);
        elem.setPadding(convertDpToPixels(this, 10), 0, convertDpToPixels(this, 10), 0);// in pixels (left, top, right, bottom)
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        elemParams.addRule(RelativeLayout.ALIGN_TOP, parent_id );
        //elemParams.setMargins( convertDpToPixels(this, 0),  convertDpToPixels(this, 150),  0,  0);
        elem.setLayoutParams(elemParams);

        return elem;
    }

    private ImageView genImageView(int elem_id){
        ImageView elem = new ImageView(this);
        elem.setLayoutParams(
            new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        );
        Context context = elem.getContext();
        int id = context.getResources().getIdentifier("det_door_right", "drawable", context.getPackageName());
        elem.setImageResource(id);
//        elem.setImageResource(R.drawable.det_door_right);
        elem.setId(elem_id);
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
        elem.setBackgroundColor(Color.parseColor("#FF605C"));
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)elem.getLayoutParams();
        elemParams.addRule(RelativeLayout.ALIGN_START, parent_id );
        elemParams.setMargins( convertDpToPixels(this, 120),  convertDpToPixels(this, 200),  0,  0);

        final String da = detail_article;
        final String ta = title;

        elem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1.save detail id
                setSharedValueStr("detail_article", da );
                setSharedValueStr("detail_title", ta );
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

}