package com.example.bertumcamera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class AiWorking extends AppCompatActivity {
    private ProgressBar loadingPB;
    private TextView responseTV;
    String photoBase64;
    SharedPreferences sh;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sh = getSharedPreferences(Const.SHARE_STORE, MODE_APPEND); //read
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE); // write


        setContentView(R.layout.activity_ai_working);
        loadingPB = findViewById(R.id.idPBLoading);
        responseTV = findViewById(R.id.idTVResponse);

        photoBase64 = sh.getString("photoBase64", "no val");
        responseTV.setText(photoBase64);

        postDataUsingVolley("123456789", photoBase64);

    }
    private void postDataUsingVolley(final String photoId, final String photoBase64) {
        String url = "http://h304809427.nichost.ru/api/get_segments.php";
        RequestQueue queue = Volley.newRequestQueue(AiWorking.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //responseTV.setVisibility(View.VISIBLE);
                responseTV.setText("Response from the API is :" + response); // comment for a while

                ed = sharedPreferences.edit();
                ed.putString("jsonAiApi", response);
                ed.commit();
//                try {
//                    Thread.sleep(5000); // delay for 5 seс
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                startActivity(new Intent(AiWorking.this, ItemsList.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling error on below line.
                loadingPB.setVisibility(View.GONE);
                responseTV.setVisibility(View.VISIBLE);
                responseTV.setText(error.getMessage());
                Toast.makeText(AiWorking.this, "Fail to get response..", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(AiWorking.this, ItemsList.class));

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

        Toast.makeText(AiWorking.this, "Фотография успешно отправлена", Toast.LENGTH_SHORT).show();

        // post the data.
        queue.add(request);
    }
}