package com.example.bertumcamera;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class Model3d extends AppCompatActivity {

    private ImageView imgGallery;

    private RelativeLayout RF_lo_033,
            RF_lo_050;
    private TextView textBase64;
    private Button btnGallery;

    private ImageButton arrowPlus, arrowMinus, arrowLeft, arrowRight;
    private final int GALLERY_REQ_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model3d);
        imgGallery = findViewById(R.id.imgGallery);
        btnGallery = findViewById(R.id.btnGallery);
        textBase64 = findViewById(R.id.textBase64);

        arrowPlus = findViewById(R.id.arrow_plus);
        arrowMinus = findViewById(R.id.arrow_minus);
        arrowLeft = findViewById(R.id.arrow_left);
        arrowRight = findViewById(R.id.arrow_right);

        RF_lo_033 = findViewById(R.id.layout_m3d_vw6_sed_x033_right_front);
        RF_lo_050 = findViewById(R.id.layout_m3d_vw6_sed_x050_right_front);
//        RF_wing_r_033 = findViewById(R.id.m3d_vw6_sed_x033_right_front_wing_r);
//        RF_door_r_033 = findViewById(R.id.m3d_vw6_sed_x033_front_right_front_right_door);

        btnGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        arrowMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RF_lo_050.setVisibility(View.GONE);
                RF_lo_033.setVisibility(View.VISIBLE);
            }
        });

        arrowPlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RF_lo_033.setVisibility(View.GONE);
                RF_lo_050.setVisibility(View.VISIBLE);
/*
                m3dCar.setImageResource(R.drawable.m3d_vw6_sed_x050_right_front);
                m3dDetail1.setImageResource(R.drawable.m3d_vw6_sed_x050_right_front_wing_r);
                RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                elemParams.setMargins( convertDpToPixels(Model3d.this, -20),
                        convertDpToPixels(Model3d.this, 165),  0,  0);
                m3dDetail1.setLayoutParams(elemParams);

                m3dDetail2.setImageResource(R.drawable.m3d_vw6_sed_x050_right_front_door_r);
                elemParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                elemParams.setMargins( convertDpToPixels(Model3d.this, -220),
                        convertDpToPixels(Model3d.this, 106),  0,  0);
                m3dDetail2.setLayoutParams(elemParams);
*/
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data ){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                imgGallery.setImageURI(data.getData());
                Uri imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baoStream);
                    byte[] b = baoStream.toByteArray();
                    String photoBase64 = Base64.encodeToString(b, Base64.DEFAULT);
                    textBase64.setText(photoBase64);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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