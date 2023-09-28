package com.example.bertumcamera;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import android.util.Base64;

import android.widget.Toast;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private ImageView aiWorking, clIcoDtp;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
//    private WebView web;
    // on below line we are creating variables
    private EditText nameEdt, jobEdt;
    private ImageButton button, postDataBtn, postDataBtn2, bottomPrices;
    private ProgressBar loadingPB;
    private TextView responseTV, cntPhoto, cntTotalItems, sumRepairs, sumRepairsWork;

    private static final String TAG = ""; // Bitmap to base64

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);
        cntTotalItems = findViewById(R.id.cntTotalItems);
//        imageView = findViewById(R.id.imageview);
        button = findViewById(R.id.button);
        bottomPrices = findViewById(R.id.bottomPrices);
        clIcoDtp = findViewById(R.id.ico_dtp2);
//        web = (WebView)findViewById(R.id.webView);
//        web.setWebViewClient(new WebViewClient());

//        WebSettings ws = web.getSettings();
//        ws.setJavaScriptEnabled(true);
//        web.loadUrl("https://m-1-2-ai-not-active.bertum.com");

// -----------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------
// Storing data into SharedPreferences
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
//        myEdit.putString("name", name.getText().toString()); // how to get text from textItem
        //myEdit.putString("name", "Nil");
        //myEdit.putInt("age", Integer.parseInt("45"));


// Once the changes have been made, we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit.commit();
// -----------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------
//        Request for camera runtime permission
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
                    }, 100);
        }
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        bottomPrices.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, ItemsList.class));
            }
        });

        clIcoDtp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SharedPreferences sh = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor edit1;
                edit1 = sh.edit();
                sumRepairs.setText("0");
                sumRepairsWork.setText("0");
                cntTotalItems.setText("0");

                edit1.putInt("cartSum", 0);
                edit1.putInt("cartCnt", 0);
                edit1.putInt("cartRepairWork", 0);
                edit1.commit();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        // on below line initializing variable with id.
//        nameEdt = findViewById(R.id.idEdtName);
//        bottomPrices = findViewById(R.id.bottom_prices);
        aiWorking = findViewById(R.id.aiWorking);
        postDataBtn = findViewById(R.id.idBtnPostData);
        postDataBtn2 = findViewById(R.id.idBtnPostData2);
        loadingPB = findViewById(R.id.idPBLoading);
        responseTV = findViewById(R.id.idTVResponse);
        // on below line adding click listner for our post data button.
        postDataBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line checking of the edit text is empty or not.
//                if (nameEdt.getText().toString().isEmpty() && jobEdt.getText().toString().isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Pplease enter name and job..", Toast.LENGTH_SHORT).show();
//                } else {
//                    // on below line calling method to post the data.
//                    postDataUsingVolley(nameEdt.getText().toString(), jobEdt.getText().toString());
//                }

                //postDataUsingVolley("empty data", " to get response");

                startActivity(new Intent(MainActivity.this, AiWorking.class));
            }
        });

//        bottomPrices.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, ItemsList.class));
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data ){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            postDataBtn2.setVisibility(View.VISIBLE); // show +1 photo image
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baoStream);
            byte[] b = baoStream.toByteArray();
            String photoBase64 = Base64.encodeToString(b, Base64.DEFAULT);

            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
            cntPhoto = findViewById(R.id.cntPhoto);
//            String s1 = sh.getString("name", "");
            int a = sh.getInt("cntPhoto", 0);
            a += 1;
            cntPhoto.setText(String.valueOf(a));
            myEdit = sharedPreferences.edit();
            myEdit.putInt("cntPhoto", a);
            myEdit.putString("photoBase64", photoBase64);
            myEdit.commit();
            Toast.makeText(MainActivity.this, "Photo saved", Toast.LENGTH_SHORT).show();


            // send request to AI server
//            postDataUsingVolley("Empty data", " to get test response");
        }
    }


// -----------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------
    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        cntPhoto = findViewById(R.id.cntPhoto);
//            String s1 = sh.getString("name", "");
        int a = sh.getInt("cntPhoto", 0);
        cntPhoto.setText(String.valueOf(a));
        int b = sh.getInt("cartSum", 0);
        sumRepairs.setText(String.valueOf(b));
        int c = sh.getInt("cartCnt", 0);
        cntTotalItems.setText(String.valueOf(c));
        int d = sh.getInt("cartRepairWork", 0);
        sumRepairsWork.setText(String.valueOf(d));
    }
}
