package com.example.bertumcamera;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
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
import java.io.IOException;

import android.util.Base64;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView aiWorking, clIcoDtp, carOnMain,
            m3d_detail_1, m3d_detail_2;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor ed;

    private EditText nameEdt, jobEdt;
    private ImageButton button, postDataBtn, postDataBtn2, bottomPrices;
    private ProgressBar loadingPB;
    private TextView responseTV, cntPhoto, cntTotalItems, sumRepairs, sumRepairsWork, selectFileMedia;

    private static final String TAG = ""; // Bitmap to base64
    private final int GALLERY_REQ_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startActivity(new Intent(MainActivity.this, Model3d.class));
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);
        cntTotalItems = findViewById(R.id.cntTotalItems);
        button = findViewById(R.id.button);
        bottomPrices = findViewById(R.id.bottomPrices);
        clIcoDtp = findViewById(R.id.ico_dtp2);
        selectFileMedia = findViewById(R.id.selectFileMedia);
        carOnMain  = findViewById(R.id.m3d_car);
        m3d_detail_1 = findViewById(R.id.m3d_detail_1);
        m3d_detail_2 = findViewById(R.id.m3d_detail_2);

        aiWorking = findViewById(R.id.aiWorking);
        postDataBtn = findViewById(R.id.idBtnPostData);
        postDataBtn2 = findViewById(R.id.idBtnPostData2);
        loadingPB = findViewById(R.id.idPBLoading);
        responseTV = findViewById(R.id.idTVResponse);
        // Storing data into SharedPreferences
        sharedPreferences = getSharedPreferences("BertumTmpData",MODE_PRIVATE);

        //Request for camera runtime permission
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
                SharedPreferences sh = getSharedPreferences("BertumTmpData",MODE_PRIVATE);
                SharedPreferences.Editor ed;
                ed = sh.edit();
                sumRepairs.setText("0");
                sumRepairsWork.setText("0");
                cntTotalItems.setText("0");

                ed.putInt("cartSum", 0);
                ed.putInt("cartCnt", 0);
                ed.putInt("cartRepairWork", 0);
                ed.commit();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        selectFileMedia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });
        carOnMain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Model3d.class));
            }
        });

        postDataBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AiWorking.class));
            }
        });
        m3d_detail_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Выбрана деталь - Правая передняя дверь", Toast.LENGTH_SHORT).show();
            }
        });
        m3d_detail_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Выбрана деталь - Стекло лобовое", Toast.LENGTH_SHORT).show();
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
                ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baoStream);
                byte[] b = baoStream.toByteArray();
                String photoBase64 = Base64.encodeToString(b, Base64.DEFAULT);

            SharedPreferences sh = getSharedPreferences("BertumTmpData", MODE_APPEND);
            cntPhoto = findViewById(R.id.cntPhoto);
            int a = sh.getInt("cntPhoto", 0);
            a += 1;
            cntPhoto.setText(String.valueOf(a));
            ed = sharedPreferences.edit();
            ed.putInt("cntPhoto", a);
            ed.putString("photoBase64", photoBase64);
            ed.commit();
            Toast.makeText(MainActivity.this, "Фотография добавлена", Toast.LENGTH_SHORT).show();
        }
        if(resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                //imgGallery.setImageURI(data.getData());
                try {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baoStream);
                    byte[] b = baoStream.toByteArray();
                    String photoBase64 = Base64.encodeToString(b, Base64.DEFAULT);

                    SharedPreferences sh = getSharedPreferences("BertumTmpData", MODE_APPEND);
                    cntPhoto = findViewById(R.id.cntPhoto);
                    int a = sh.getInt("cntPhoto", 0);
                    a += 1;
                    cntPhoto.setText(String.valueOf(a));
                    ed = sharedPreferences.edit();
                    ed.putInt("cntPhoto", a);
                    ed.putString("photoBase64", photoBase64);
                    ed.commit();
                    Toast.makeText(MainActivity.this, "Фотография добавлена", Toast.LENGTH_SHORT).show();

                    //textBase64.setText(photoBase64);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sh = getSharedPreferences("BertumTmpData", MODE_APPEND);
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
