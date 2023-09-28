package com.example.bertumcamera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AiWorking extends AppCompatActivity {

    private ProgressBar loadingPB;
    private TextView responseTV;
    String photoBase64;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sh = getSharedPreferences("MySharedPref", MODE_APPEND);

        setContentView(R.layout.activity_ai_working);
        loadingPB = findViewById(R.id.idPBLoading);
        responseTV = findViewById(R.id.idTVResponse);


        photoBase64 = sh.getString("photoBase64", "no val");
        responseTV.setText(photoBase64);

        postDataUsingVolley("123456789", photoBase64);

    }

    private void postDataUsingVolley(final String photoId, final String photoBase64) {
        // on below line specifying the url at which we have to make a post request
        String url = "https://reqres.in/api/users";
         url = "http://h304809427.nichost.ru/api/get_photo.php";
        // setting progress bar visibility on below line.
//        loadingPB.setVisibility(View.VISIBLE);
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(AiWorking.this);
        // making a string request on below line.
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                try {
//                    Thread.sleep(2000); // delay for 2 second
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                // channing progress bar visibility on below line.
//                 loadingPB.setVisibility(View.GONE); // loading image
                // setting response to text view.
                responseTV.setVisibility(View.VISIBLE);
                responseTV.setText("Response from the API is :" + response); // comment for a while
                Toast.makeText(AiWorking.this, "Фотография успешно отправлена", Toast.LENGTH_SHORT).show();

                //startActivity(new Intent(AiWorking.this, ItemsList.class));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling error on below line.
                loadingPB.setVisibility(View.GONE);
                responseTV.setVisibility(View.VISIBLE);
                responseTV.setText(error.getMessage());
                Toast.makeText(AiWorking.this, "Fail to get response..", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(AiWorking.this, ItemsList.class));

            }
        }) {
            // DIDNT GET IT, MUST FIND OUT WHAT IS THIS BLOCK FOR
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("photoId", photoId);
                params.put("photoBase64", photoBase64);
                return params;
            }
        };
        // adding request to queue to post the data.
        queue.add(request);
    }

}