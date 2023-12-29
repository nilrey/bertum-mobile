package com.example.bertumcamera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.example.bertumcamera.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AiWorking extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private DrawerLayout drawerLayout;
    private ProgressBar loadingPB;
    private TextView responseTV;
    private ImageView plateAiWorking, ico_menu;
    String photoBase64;
    SharedPreferences sh;
    private int cntAttemptVolley = 0;
    private String msgAiService = "";

    int ifAiError = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ai_working);
        sh = getSharedPreferences(Const.SHARE_STORE, MODE_APPEND); //read
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE); // write

        plateAiWorking = findViewById(R.id.plateAiWorking);
        loadingPB = findViewById(R.id.idPBLoading);
        responseTV = findViewById(R.id.idTVResponse);
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

        photoBase64 = sh.getString("photoBase64", "no val");
        responseTV.setText(photoBase64);

        postDataUsingVolley("123456789", photoBase64);

    }
    private void postDataUsingVolley(final String photoId, final String photoBase64) {
        String url = Const.URL_AIAPI;
        RequestQueue queue = Volley.newRequestQueue(AiWorking.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String resp = "no val";
                if ( !(response instanceof String) ){ resp = String.valueOf(response); }
                else { resp = response; }
                setSharedValueStr("jsonAiApi", resp);
                setVolleyResponse();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling error on below line.
//                loadingPB.setVisibility(View.GONE);
//                responseTV.setVisibility(View.VISIBLE);
//                responseTV.setText(error.getMessage());
                Toast.makeText(AiWorking.this, "Fail to get response..", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rmPoints", "1");
                params.put("photoBase64", photoBase64 );
                cntAttemptVolley++ ;
                return params;
            }
        };
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);

        Toast.makeText(AiWorking.this, "Фотография успешно отправлена", Toast.LENGTH_SHORT).show();

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
        return sharedPreferences.getString(name, "no val");
    }
    private void setVolleyResponse(){

        String jsonAiApi = getSharedValueStr("jsonAiApi");

        try {
            if( jsonAiApi.substring(0, 1).equals("{")) {
                setSharedValueStr("jsonAiApi", jsonAiApi);
                JSONObject objects = new JSONObject(jsonAiApi);
                JSONObject settings = objects.getJSONObject("settings");
                JSONArray keys = settings.names ();
                ifAiError = 1;
                for (int i = 0; i < keys.length(); ++i) {
                    String key = keys.getString(i);
                    JSONObject objDetail = settings.getJSONObject(key);
                    ifAiError = Integer.parseInt(objDetail.getString("error"));
                    if (ifAiError == 0) {
                        startActivity(new Intent(AiWorking.this, DetailsListActivity.class));
                    } else {
                        msgAiService = objDetail.getString("text");
                    }
                }
                /*
                ifAiError =  Integer.parseInt(settings.getString("error"));
                if (ifAiError ==  0) {
                    cntAttemptVolley = 0;
                    startActivity(new Intent(AiWorking.this, DetailsListActivity.class));
                }else{
                    msgAiService =settings.getString ("text");
                }*/
            }else{
                ifAiError = 1;
                msgAiService = "Извините, сервис распознования сейчас не недоступен, повторите попытку.";
                // no api server connect
            }
        }catch (Exception e){
            ifAiError = 1;
            msgAiService = e.getMessage();
        }

        if(ifAiError > 0){
            plateAiWorking.setVisibility(View.GONE);
            responseTV.setText(msgAiService);
            responseTV.setVisibility(View.VISIBLE);
            Toast.makeText(AiWorking.this, msgAiService, Toast.LENGTH_SHORT).show();
        }
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
        DataStore.setMenuItems(item, AiWorking.this);
        return true;
    }

}