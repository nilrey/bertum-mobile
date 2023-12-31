package com.example.bertumcamera;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import android.util.Base64;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final int GALLERY_REQ_CODE = 1000;
    private final int CAMERA_REQ_CODE = 100;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private ImageView clIcoDtp, m3dCarFrontRight, ico_menu;
    private ImageButton startBlockDisbld, startBlockEnbld, bottomPrices;
    private TextView cntPhoto, cntTotalItems, sumRepairs, sumRepairsWork, selectFileMedia, linkToSmetaRepairs , linkToSmetaDetails;
    private Button selectCamera;
    private String msgDetailSelected, currentPhotoPath;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        startActivity(new Intent(MainActivity.this, TestVisualActivity.class));
        setContentView(R.layout.activity_main);
        if( getSharedValueInt("isFirstRun" ) == 0 ){
            startActivity(new Intent(MainActivity.this, RegActivity.class));
        }
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // make app fullscreen
        setSharedValueInt("tokenMainPage" , 123456789);
        // Bottom block Prices - priceRepair, cntDetailsSelected, priceRepairWork
        bottomPrices = findViewById(R.id.bottomPrices);
        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);
        cntTotalItems = findViewById(R.id.cntTotalItems);
        linkToSmetaDetails = findViewById(R.id.linkToSmetaDetails);
        linkToSmetaRepairs = findViewById(R.id.linkToSmetaRepairs);
        // Top block actions - Media Gallery, Camera
        selectCamera = findViewById(R.id.selectCamera);
        clIcoDtp = findViewById(R.id.ico_dtp2);
        selectFileMedia = findViewById(R.id.selectFileMedia);
        // block 3d model
        m3dCarFrontRight  = findViewById(R.id.m3d_car_front_right);
        // Middle block - post photo to AI API
        startBlockDisbld = findViewById(R.id.btnStartDisbld);
        startBlockEnbld = findViewById(R.id.idBtnPostData2);
        cntPhoto = findViewById(R.id.cntPhoto);
        // messages text
        msgDetailSelected  =  getResources().getString(R.string.msg_detail_selected);

        ico_menu = findViewById(R.id.ico_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        ico_menu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCloseNavigationDrawer(v);
                    }
                }
        );

        //Request for camera runtime permission
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
                    }, 100);
        }

        String fileName = "photo";
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File fileImage = null;
        try {
            fileImage = File.createTempFile(fileName, ".jpg", storageDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentPhotoPath = fileImage.getAbsolutePath();

        final File finalFileImage = fileImage;
        selectCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_REQ_CODE);

                //Request for camera runtime permission
//                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{
//                            Manifest.permission.CAMERA
//                    }, 100);
//                }

//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 1);
                try {
                    Uri imageUri = FileProvider.getUriForFile(MainActivity.this,
                            "com.example.bertumcamera.fileprovider", finalFileImage);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, 100);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        clIcoDtp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt(Const.CART_SUM, 0);
                setSharedValueInt(Const.CART_CNT, 0);
                setSharedValueInt(Const.CART_REPAIR_WORK, 0);
                setSharedValueInt(Const.CNT_PHOTO, 0);
                sumRepairs.setText(String.valueOf( getSharedValueInt(Const.CART_SUM) ));
                sumRepairsWork.setText(String.valueOf( getSharedValueInt(Const.CART_REPAIR_WORK) ));
                cntTotalItems.setText(String.valueOf( getSharedValueInt(Const.CART_CNT) ));
                cntPhoto.setText(String.valueOf( getSharedValueInt(Const.CNT_PHOTO) ));
                checkAiApiPostBlock();
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
        m3dCarFrontRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                startActivity(new Intent(MainActivity.this, Model3d.class));
                startActivity(new Intent(MainActivity.this, ImageDragActivity.class));
            }
        });

        startBlockEnbld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AiWorking.class));
            }
        });
        linkToSmetaDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 0);
                startActivity(new Intent(MainActivity.this, SmetaActivity.class));
            }
        });
        linkToSmetaRepairs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 1);
                startActivity(new Intent(MainActivity.this, SmetaActivity.class));
            }
        });
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
        DataStore.setMenuItems(item, MainActivity.this);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data ){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == CAMERA_REQ_CODE || requestCode == GALLERY_REQ_CODE){
                Bitmap bitmap = null, rotatedBitmap;
                if(requestCode == CAMERA_REQ_CODE){
                    bitmap = BitmapFactory.decodeFile(currentPhotoPath);
                }else{
//                    saveFromUri(data);
//                    String photoBase64 = getBase64FromUri(data);
                    Uri imageUri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    } catch (IOException e) {
//                        throw new RuntimeException(e);
                    }
                    //setSharedValueStr(Const.PHOTO_BASE64, photoBase64);
                }

                try {
                    ExifInterface ei = new ExifInterface(currentPhotoPath);
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    switch(orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = rotateImage(bitmap, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = rotateImage(bitmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = rotateImage(bitmap, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = bitmap;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                int nh = (int) ( rotatedBitmap.getHeight() * (500.0 / rotatedBitmap.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(rotatedBitmap, 500, nh, true);

                saveFromBitmap(scaled);
                manageCntPhoto();
            }
        }
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void manageCntPhoto(){
        increaseCntPhoto();
        enableAiApiPostBlock();
        Toast.makeText(MainActivity.this, Const.MSG_FOTO_ADDED, Toast.LENGTH_SHORT).show();
    }

    private void saveFromBitmap(Bitmap data){
        String photoBase64 = getBase64FromBitmap(data);
        setSharedValueStr(Const.PHOTO_BASE64, photoBase64);
    }

    private void saveFromUri(Intent data){
        String photoBase64 = getBase64FromUri(data);
        setSharedValueStr(Const.PHOTO_BASE64, photoBase64);
    }

    @Override
    public void onResume() {
        super.onResume();
        cntPhoto.setText(String.valueOf( getSharedValueInt(Const.CNT_PHOTO) ));
        sumRepairs.setText(String.valueOf( getSharedValueInt(Const.CART_SUM) ));
        cntTotalItems.setText(String.valueOf( getSharedValueInt(Const.CART_CNT) ));
        sumRepairsWork.setText(String.valueOf( getSharedValueInt(Const.CART_REPAIR_WORK) ));
    }

    private String getBase64FromUri(Intent data){
        Uri imageUri = data.getData();
        String strBase64;
        Bitmap bitmap, scaled;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            int nh = (int) ( bitmap.getHeight() * (500.0 / bitmap.getWidth()) );
            scaled = Bitmap.createScaledBitmap(bitmap, 500, nh, true);
            strBase64 = convertBitmapToBase64(scaled);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return strBase64;
    }

    private String getBase64FromBitmap(Bitmap data){
        String strBase64;
//        Bitmap bitmap;
//        try {
//            bitmap = (Bitmap) data.getExtras().get("data");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        strBase64 = convertBitmapToBase64(data);
        return strBase64;
    }

    private String convertBitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baoStream);
        byte[] b = baoStream.toByteArray();
        String strBase64 = Base64.encodeToString(b, Base64.DEFAULT);
        return strBase64;
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
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        return sharedPreferences.getInt(name, 0);
    }
    private String getSharedValueStr(String name){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }

    private void increaseCntPhoto(){
        setSharedValueInt( Const.CNT_PHOTO,  1 );
//        cntPhoto = findViewById(R.id.cntPhoto);
        cntPhoto.setText(String.valueOf( getSharedValueInt(Const.CNT_PHOTO) ));
    }

    private void checkAiApiPostBlock(){
        if(getSharedValueInt(Const.CNT_PHOTO) > 0){
            enableAiApiPostBlock();
        }else{
            disableAiApiPostBlock();
        }
    }

    private void enableAiApiPostBlock(){
        startBlockEnbld.setVisibility(View.VISIBLE);
        cntPhoto.setVisibility(View.VISIBLE);
    }

    private void disableAiApiPostBlock(){
        startBlockEnbld.setVisibility(View.GONE);
        cntPhoto.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }
}
