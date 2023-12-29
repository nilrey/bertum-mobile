package com.example.bertumcamera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Method;

public class ImageDragActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private DrawerLayout drawerLayout;
    private RelativeLayout blockCarRF_x033, blockCarRF_x050,
            blockCarFF_x050, blockCarFF_x033, blockCarLF_x050, blockCarLF_x033, blockCarLL_x050,
            blockCarLL_x033, blockCarLB_x050, blockCarLB_x033, blockCarBB_x050, blockCarBB_x033,
            blockCarRB_x050, blockCarBR_x033, blockCarRR_x050, blockCarRR_x033;
    private ImageView layoutCarRF_x033,
            layoutCarRF_x050, front_right_front_right_door, front_right_front_right_wing, front_right_front_bumper, front_right_rear_right_door,
            front_right_roof, front_right_hood, front_right_rear_right_wing, front_right_front_right_headlight, front_right_windshield, front_right_front_right_wheel, front_right_rear_right_wheel, front_right_central_bumper_grille,
            front_front_windshield, front_front_front_bumper, front_front_front_left_headlight, front_front_front_right_headlight, front_front_roof, front_front_hood, front_front_central_bumper_grille,
            front_left_windshield, front_left_rear_left_door, front_left_front_left_wing, front_left_front_left_door, front_left_front_bumper, front_left_hood, front_left_front_left_headlight, front_left_roof, front_left_rear_left_wing, front_left_front_left_wheel, front_left_rear_left_wheel, front_left_central_bumper_grille,
            left_left_front_bumper, left_left_front_left_door, left_left_front_left_headlight, left_left_front_left_wing, left_left_front_wheel, left_left_hood, left_left_rear_bumper, left_left_rear_left_door, left_left_rear_left_light, left_left_rear_left_wing, left_left_roof, left_left_rear_wheel,
            back_left_front_left_wing, back_left_front_wheel, back_left_rear_body_glass, back_left_rear_bumper, back_left_rear_left_door, back_left_rear_left_light, back_left_rear_left_wing, back_left_rear_wheel, back_left_trunk_lid, back_left_roof, back_left_front_left_door,
            back_back_rear_body_glass, back_back_rear_bumper, back_back_rear_left_light, back_back_rear_left_wing, back_back_rear_right_light, back_back_rear_right_wing, back_back_roof, back_back_trunk_lid,
            back_right_front_right_door, back_right_front_right_wing, back_right_front_wheel, back_right_rear_body_glass, back_right_rear_bumper, back_right_rear_right_door, back_right_trunk_lid, back_right_rear_right_wing, back_right_rear_right_light, back_right_rear_wheel, back_right_roof,
            right_right_front_bumper, right_right_front_right_door, right_right_front_right_headlight, right_right_front_right_wing, right_right_front_wheel, right_right_hood, right_right_rear_bumper, right_right_rear_right_door, right_right_rear_right_light, right_right_rear_right_wing, right_right_rear_wheel, right_right_roof,
            layoutCarFF_x050, layoutCarLF_x050, layoutCarLL_x050, layoutCarLB_x050,
            layoutCarBB_x050, layoutCarRB_x050, layoutCarRR_x050,
            ico_cart, ico_menu
            ;
    private ImageButton arrowPlus, arrowMinus, arrowLeft, arrowRight;
    private TextView respView,
    cntTotalItems, sumRepairs, sumRepairsWork, linkToSmetaRepairs , linkToSmetaDetails, informer2;
    private String sideView=Const.VIEW_RIGHT_FRONT;
    float x, y, dx, dy;
    private int bias=0, scaleValue = 1, setImageMiddle = 0, isActivateSideViewDetails = 0;
    long durationTouch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_drag);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        calculateBias();
        initAllSideViews();
        hideAllSideViews();
        setItemsListBackPage();
        setSharedValueInt("countTry" , 0);
        informer2 = findViewById(R.id.informer2);
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
        // нижняя часть стоимость
        cntTotalItems = findViewById(R.id.cntTotalItems);
        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);
        linkToSmetaDetails = findViewById(R.id.linkToSmetaDetails);
        linkToSmetaRepairs = findViewById(R.id.linkToSmetaRepairs);

        // 3D model functions
        layoutCarRF_x033 = findViewById(R.id.layoutCarRF_x033);
        layoutCarRF_x050 = findViewById(R.id.layoutCarRF_x050);
        initAllDetails2x();


        arrowPlus = findViewById(R.id.arrow_plus);
        arrowMinus = findViewById(R.id.arrow_minus);
        arrowRight = findViewById(R.id.arrow_right);
        arrowLeft = findViewById(R.id.arrow_left);

        respView = findViewById(R.id.respView);

        linkToSmetaDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 0);
                startActivity(new Intent(ImageDragActivity.this, SmetaActivity.class));
            }
        });
        linkToSmetaRepairs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSharedValueInt("smetaTab", 1);
                startActivity(new Intent(ImageDragActivity.this, SmetaActivity.class));
            }
        });

        ico_cart = findViewById(R.id.ico_cart);
        ico_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageDragActivity.this, CartActivity.class));
            }
        });

        genScaleOnClick(arrowMinus, 1);
        genScaleOnClick(arrowPlus, 2);

        float density = getResources().getDisplayMetrics().density;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int marginWidth = (int) ( 0*density);
        int image050Width = (int) ( 600*density);
        setImageMiddle((int ) (image050Width + marginWidth - screenWidth ) / (-2));

        arrowRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                hideAllSideViews();
                setSideViewToMiddle();
                if(sideView == Const.VIEW_RIGHT_FRONT){ sideView = Const.VIEW_FRONT_FRONT; }
                else if (sideView == Const.VIEW_FRONT_FRONT) { sideView = Const.VIEW_LEFT_FRONT;}
                else if (sideView == Const.VIEW_LEFT_FRONT) { sideView = Const.VIEW_LEFT_LEFT;}
                else if (sideView == Const.VIEW_LEFT_LEFT) { sideView = Const.VIEW_LEFT_BACK;}
                else if (sideView == Const.VIEW_LEFT_BACK) { sideView = Const.VIEW_BACK_BACK;}
                else if (sideView == Const.VIEW_BACK_BACK) { sideView = Const.VIEW_RIGHT_BACK;}
                else if (sideView == Const.VIEW_RIGHT_BACK) { sideView = Const.VIEW_RIGHT_RIGHT;}
                else if (sideView == Const.VIEW_RIGHT_RIGHT) { sideView = Const.VIEW_RIGHT_FRONT;}
                activateSvBlock();

                if(scaleValue == 2){
                    setSideViewToMiddle();
                }
            }
        });

        arrowLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                hideAllSideViews();
                setSideViewToMiddle();
                if(sideView == Const.VIEW_RIGHT_FRONT){ sideView = Const.VIEW_RIGHT_RIGHT; }
                else if (sideView == Const.VIEW_RIGHT_RIGHT) { sideView = Const.VIEW_RIGHT_BACK;}
                else if (sideView == Const.VIEW_RIGHT_BACK) { sideView = Const.VIEW_BACK_BACK;}
                else if (sideView == Const.VIEW_BACK_BACK) { sideView = Const.VIEW_LEFT_BACK;}
                else if (sideView == Const.VIEW_LEFT_BACK) { sideView = Const.VIEW_LEFT_LEFT;}
                else if (sideView == Const.VIEW_LEFT_LEFT) { sideView = Const.VIEW_LEFT_FRONT;}
                else if (sideView == Const.VIEW_LEFT_FRONT) { sideView = Const.VIEW_FRONT_FRONT;}
                else if (sideView == Const.VIEW_FRONT_FRONT) { sideView = Const.VIEW_RIGHT_FRONT;}
                activateSvBlock();

                if(scaleValue == 2){
                    setSideViewToMiddle();
                }
            }
        });

        // OnClick
        genImageViewOnClick(front_right_front_right_door, "6RU831056J", "Дверь передняя правая");
        genImageViewOnClick(front_right_front_right_wing, "6RU821106C", "Переднее правое крыло");
        genImageViewOnClick(front_right_front_bumper, "6RU807221A", "Бампер передний");
        genImageViewOnClick(front_right_rear_right_door, "6RU833056D", "Дверь задняя правая");
        genImageViewOnClick(front_right_roof, "6RU817111B", "Крыша");
        genImageViewOnClick(front_right_hood, "6RU823031C", "Капот");
        genImageViewOnClick(front_right_rear_right_wing, "6RU809605A", "Крыло заднее правое");
        genImageViewOnClick(front_right_windshield, "6RU845011J", "Лобовое стекло");
        genImageViewOnClick(front_right_central_bumper_grille, "6RU853651D", "Решетка бампера центральная");
        genImageViewOnClick(front_right_front_right_headlight, "6RU941016", "Фара передняя правая");
        genImageViewOnClick(front_right_front_right_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(front_right_rear_right_wheel, "6Q0601027AC", "Колесо");

        genImageViewOnClick(front_front_windshield, "6RU845011J", "Лобовое стекло");
        genImageViewOnClick(front_front_front_bumper, "6RU807221A", "Бампер передний");
        genImageViewOnClick(front_front_central_bumper_grille, "6RU853651D", "Решетка бампера центральная");
        genImageViewOnClick(front_front_front_left_headlight, "6RU941015", "Фара передняя левая");
        genImageViewOnClick(front_front_front_right_headlight, "6RU941016", "Фара передняя правая");
        genImageViewOnClick(front_front_roof, "6RU817111B", "Крыша");
        genImageViewOnClick(front_front_hood, "6RU823031C", "Капот");

        genImageViewOnClick(front_left_windshield, "6RU845011J", "Лобовое стекло");
        genImageViewOnClick(front_left_rear_left_door, "6RU833055D", "Дверь задняя левая");
        genImageViewOnClick(front_left_front_left_wing, "6RU821105C", "Крыло переднее левое");
        genImageViewOnClick(front_left_front_left_door, "6RU831055J", "Дверь передняя левая");
        genImageViewOnClick(front_left_front_bumper, "6RU807221A", "Бампер передний");
        genImageViewOnClick(front_left_hood, "6RU823031C", "Капот");
        genImageViewOnClick(front_left_central_bumper_grille, "6RU853651D", "Решетка бампера центральная");
        genImageViewOnClick(front_left_front_left_headlight, "6RU941015", "Фара передняя левая");
        genImageViewOnClick(front_left_roof, "6RU817111B", "Крыша");
        genImageViewOnClick(front_left_rear_left_wing, "6RU809605A", "Крыло заднее левое");
        genImageViewOnClick(front_left_front_left_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(front_left_rear_left_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(left_left_front_bumper, "6RU807221A", "Бампер передний");
        genImageViewOnClick(left_left_front_left_door, "6RU831055J", "Дверь передняя левая");
        genImageViewOnClick(left_left_front_left_headlight, "6RU941015", "Фара передняя левая");
        genImageViewOnClick(left_left_front_left_wing, "6RU821105C", "Крыло переднее левое");
        genImageViewOnClick(left_left_front_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(left_left_hood, "6RU823031C", "Капот");
        genImageViewOnClick(left_left_rear_bumper, "6RU807421FGRU", "Бампер задний");
        genImageViewOnClick(left_left_rear_left_door, "6RU833055D", "Дверь задняя левая");
        genImageViewOnClick(left_left_rear_left_light, "6RU945095M", "Фонарь задний левый");
        genImageViewOnClick(left_left_rear_left_wing, "6RU809605A", "Крыло заднее левое");
        genImageViewOnClick(left_left_roof, "6RU817111B", "Крыша");
        genImageViewOnClick(left_left_rear_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(back_left_front_left_wing, "6RU821105C", "Крыло переднее левое");
        genImageViewOnClick(back_left_front_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(back_left_rear_body_glass, "6RU845051F", "Стекло кузова заднее");
        genImageViewOnClick(back_left_rear_bumper, "6RU807421FGRU", "Бампер задний");
        genImageViewOnClick(back_left_rear_left_door, "6RU833055D", "Дверь задняя левая");
        genImageViewOnClick(back_left_rear_left_light, "6RU945095M", "Фонарь задний левый");
        genImageViewOnClick(back_left_rear_left_wing, "6RU809605A", "Крыло заднее левое");
        genImageViewOnClick(back_left_rear_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(back_left_trunk_lid, "6RU827025F", "Крышка багажника");
        genImageViewOnClick(back_left_roof, "6RU817111B", "Крыша");
        genImageViewOnClick(back_left_front_left_door, "6RU831055J", "Дверь передняя левая");
        genImageViewOnClick(back_back_rear_body_glass, "6RU845051F", "Стекло кузова заднее");
        genImageViewOnClick(back_back_rear_bumper, "6RU807421FGRU", "Бампер задний");
        genImageViewOnClick(back_back_rear_left_light, "6RU945095M", "Фонарь задний левый");
        genImageViewOnClick(back_back_rear_left_wing, "6RU809605A", "Крыло заднее левое");
        genImageViewOnClick(back_back_rear_right_light, "6RU945096K", "Фонарь задний правый");
        genImageViewOnClick(back_back_rear_right_wing, "6RU821106C", "Крыло переднее правое");
        genImageViewOnClick(back_back_roof, "6RU817111B", "Крыша");
        genImageViewOnClick(back_back_trunk_lid, "6RU827025F", "Крышка багажника");
        genImageViewOnClick(back_right_front_right_door, "6RU831056J", "Дверь передняя правая");
        genImageViewOnClick(back_right_front_right_wing, "6RU821106C", "Крыло переднее правое");
        genImageViewOnClick(back_right_front_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(back_right_rear_body_glass, "6RU845051F", "Стекло кузова заднее");
        genImageViewOnClick(back_right_rear_bumper, "6RU807421FGRU", "Бампер задний");
        genImageViewOnClick(back_right_rear_right_door, "6RU833056D", "Дверь задняя правая");
        genImageViewOnClick(back_right_trunk_lid, "6RU827025F", "Крышка багажника");
        genImageViewOnClick(back_right_rear_right_wing, "6RU821106C", "Крыло переднее правое");
        genImageViewOnClick(back_right_rear_right_light, "6RU945096K", "Фонарь задний правый");
        genImageViewOnClick(back_right_rear_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(back_right_roof, "6RU817111B", "Крыша");
        genImageViewOnClick(right_right_front_bumper, "6RU807221A", "Бампер передний");
        genImageViewOnClick(right_right_front_right_door, "6RU831056J", "Дверь передняя правая");
        genImageViewOnClick(right_right_front_right_headlight, "6RU941016", "Фара передняя правая");
        genImageViewOnClick(right_right_front_right_wing, "6RU821106C", "Крыло переднее правое");
        genImageViewOnClick(right_right_front_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(right_right_hood, "6RU823031C", "Капот");
        genImageViewOnClick(right_right_rear_bumper, "6RU807421FGRU", "Бампер задний");
        genImageViewOnClick(right_right_rear_right_door, "6RU833056D", "Дверь задняя правая");
        genImageViewOnClick(right_right_rear_right_light, "6RU945096K", "Фонарь задний правый");
        genImageViewOnClick(right_right_rear_right_wing, "6RU821106C", "Крыло переднее правое");
        genImageViewOnClick(right_right_rear_wheel, "6Q0601027AC", "Колесо");
        genImageViewOnClick(right_right_roof, "6RU817111B", "Крыша");

        genImageViewOnClickEmpty(layoutCarRF_x050);
        genImageViewOnClickEmpty(layoutCarFF_x050);
        genImageViewOnClickEmpty(layoutCarLF_x050);
        genImageViewOnClickEmpty(layoutCarLL_x050);
        genImageViewOnClickEmpty(layoutCarLB_x050);
        genImageViewOnClickEmpty(layoutCarBB_x050);
        genImageViewOnClickEmpty(layoutCarRB_x050);
        genImageViewOnClickEmpty(layoutCarRR_x050);

        // OnTouch
        genImageViewOnTouch(front_right_front_right_door);
        genImageViewOnTouch(front_right_front_right_wing);
        genImageViewOnTouch(front_right_front_bumper);
        genImageViewOnTouch(front_right_rear_right_door);
        genImageViewOnTouch(front_right_roof);
        genImageViewOnTouch(front_right_hood);
        genImageViewOnTouch(front_right_rear_right_wing);
        genImageViewOnTouch(front_right_windshield);
        genImageViewOnTouch(front_right_central_bumper_grille);
        genImageViewOnTouch(front_right_front_right_headlight);
        genImageViewOnTouch(front_right_front_right_wheel);
        genImageViewOnTouch(front_right_rear_right_wheel);
        genImageViewOnTouch(front_front_windshield);
        genImageViewOnTouch(front_front_front_bumper);
        genImageViewOnTouch(front_front_central_bumper_grille);
        genImageViewOnTouch(front_front_front_left_headlight);
        genImageViewOnTouch(front_front_front_right_headlight);
        genImageViewOnTouch(front_front_roof);
        genImageViewOnTouch(front_front_hood);
        genImageViewOnTouch(front_left_rear_left_door);
        genImageViewOnTouch(front_left_windshield);
        genImageViewOnTouch(front_left_front_left_wing);
        genImageViewOnTouch(front_left_front_left_door);
        genImageViewOnTouch(front_left_front_bumper);
        genImageViewOnTouch(front_left_hood);
        genImageViewOnTouch(front_left_central_bumper_grille);
        genImageViewOnTouch(front_left_front_left_headlight);
        genImageViewOnTouch(front_left_roof);
        genImageViewOnTouch(front_left_rear_left_wing);
        genImageViewOnTouch(front_left_front_left_wheel);
        genImageViewOnTouch(front_left_rear_left_wheel);
        genImageViewOnTouch(left_left_front_bumper);
        genImageViewOnTouch(left_left_front_left_door);
        genImageViewOnTouch(left_left_front_left_headlight);
        genImageViewOnTouch(left_left_front_left_wing);
        genImageViewOnTouch(left_left_front_wheel);
        genImageViewOnTouch(left_left_hood);
        genImageViewOnTouch(left_left_rear_bumper);
        genImageViewOnTouch(left_left_rear_left_door);
        genImageViewOnTouch(left_left_rear_left_light);
        genImageViewOnTouch(left_left_rear_left_wing);
        genImageViewOnTouch(left_left_roof);
        genImageViewOnTouch(left_left_rear_wheel);
        genImageViewOnTouch(back_left_front_left_wing);
        genImageViewOnTouch(back_left_front_wheel);
        genImageViewOnTouch(back_left_rear_body_glass);
        genImageViewOnTouch(back_left_rear_bumper);
        genImageViewOnTouch(back_left_rear_left_door);
        genImageViewOnTouch(back_left_rear_left_light);
        genImageViewOnTouch(back_left_rear_left_wing);
        genImageViewOnTouch(back_left_rear_wheel);
        genImageViewOnTouch(back_left_trunk_lid);
        genImageViewOnTouch(back_left_roof);
        genImageViewOnTouch(back_left_front_left_door);
        genImageViewOnTouch(back_back_rear_body_glass);
        genImageViewOnTouch(back_back_rear_bumper);
        genImageViewOnTouch(back_back_rear_left_light);
        genImageViewOnTouch(back_back_rear_left_wing);
        genImageViewOnTouch(back_back_rear_right_light);
        genImageViewOnTouch(back_back_rear_right_wing);
        genImageViewOnTouch(back_back_roof);
        genImageViewOnTouch(back_back_trunk_lid);
        genImageViewOnTouch(back_right_front_right_door);
        genImageViewOnTouch(back_right_front_right_wing);
        genImageViewOnTouch(back_right_front_wheel);
        genImageViewOnTouch(back_right_rear_body_glass);
        genImageViewOnTouch(back_right_rear_bumper);
        genImageViewOnTouch(back_right_rear_right_door);
        genImageViewOnTouch(back_right_trunk_lid);
        genImageViewOnTouch(back_right_rear_right_wing);
        genImageViewOnTouch(back_right_rear_right_light);
        genImageViewOnTouch(back_right_rear_wheel);
        genImageViewOnTouch(back_right_roof);
        genImageViewOnTouch(right_right_front_bumper);
        genImageViewOnTouch(right_right_front_right_door);
        genImageViewOnTouch(right_right_front_right_headlight);
        genImageViewOnTouch(right_right_front_right_wing);
        genImageViewOnTouch(right_right_front_wheel);
        genImageViewOnTouch(right_right_hood);
        genImageViewOnTouch(right_right_rear_bumper);
        genImageViewOnTouch(right_right_rear_right_door);
        genImageViewOnTouch(right_right_rear_right_light);
        genImageViewOnTouch(right_right_rear_right_wing);
        genImageViewOnTouch(right_right_rear_wheel);
        genImageViewOnTouch(right_right_roof);

        genImageViewOnTouch(layoutCarRF_x050);
        genImageViewOnTouch(layoutCarFF_x050);
        genImageViewOnTouch(layoutCarLF_x050);
        genImageViewOnTouch(layoutCarLL_x050);
        genImageViewOnTouch(layoutCarLB_x050);
        genImageViewOnTouch(layoutCarBB_x050);
        genImageViewOnTouch(layoutCarRB_x050);
        genImageViewOnTouch(layoutCarRR_x050);

/*        layoutCarRF_x050.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // initiate to drag car image, but it shouldn't be clickable
            }
        });*/
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
        DataStore.setMenuItems(item, ImageDragActivity.this);
        return true;
    }

    private void calculateBias(){
        Resources r = this.getResources();
        int currentWidth = Math.round(r.getDisplayMetrics().widthPixels / r.getDisplayMetrics().density);
        setBias(Math.round( (Const.SETS_WIDTH_KOEF_DEFAULT - currentWidth) / 2 ));
    }

    private void setItemsListBackPage(){
        setSharedValueStr("ItemsListBackPage", "ImageDragActivity");
    }
    private void setBias(int val){
        this.bias = val;
    }

    private int getBias(){
        return this.bias;
    }

    private void setDetailPosition(ImageView elem, int left, int top){
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        int biasLocal = getBias();
        elemParams.setMargins(convertDpToPixels(ImageDragActivity.this, left - biasLocal ),
                convertDpToPixels(ImageDragActivity.this, top ), 0, 0);
        elem.setLayoutParams(elemParams);
    }
    private void setSideViewToMiddle(){
        if(sideView == Const.VIEW_RIGHT_FRONT){ blockCarRF_x050.setX(getImageMiddle());}
        else if (sideView == Const.VIEW_FRONT_FRONT) { blockCarFF_x050.setX(getImageMiddle()); }
        else if (sideView == Const.VIEW_LEFT_FRONT) { blockCarLF_x050.setX(getImageMiddle()); }
        else if (sideView == Const.VIEW_LEFT_LEFT) { blockCarLL_x050.setX(getImageMiddle()); }
        else if (sideView == Const.VIEW_LEFT_BACK) { blockCarLB_x050.setX(getImageMiddle()); }
        else if (sideView == Const.VIEW_BACK_BACK) { blockCarBB_x050.setX(getImageMiddle()); }
        else if (sideView == Const.VIEW_RIGHT_BACK) { blockCarRB_x050.setX(getImageMiddle()); }
        else if (sideView == Const.VIEW_RIGHT_RIGHT) { blockCarRR_x050.setX(getImageMiddle()); }
    }
    private void genScaleOnClick(ImageView elem, final int newScale){
        elem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setSideViewToMiddle();
                scaleValue = newScale;
                hideAllSideViews();
                activateSvBlock();
                if(scaleValue == 2){
                    setSideViewToMiddle();
                }
            }
        });
    }

    private void genImageViewOnClickEmpty(ImageView elem){
        elem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // do nothing
            }
        });
    }

    private void genImageViewOnClick(ImageView elem, final String da, final String dt){
        elem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(durationTouch < 200) {
                    setSharedValueStr("detail_article", da );
                    setSharedValueStr("detail_title", dt );
                    setSharedValueStr("detail_title_rus", dt );
                    sendRequestToProxy();
                }
            }
        });
    }

    private void genImageViewOnTouch(ImageView elem){
        elem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                doMoveAction(event);
                return false;
            }
        });
    }
    private void initAllDetails2x() {
        layoutCarRF_x050 = findViewById(R.id.layoutCarRF_x050);
        layoutCarFF_x050 = findViewById(R.id.layoutCarFF_x050);
        layoutCarLF_x050 = findViewById(R.id.layoutCarLF_x050);
        layoutCarLL_x050 = findViewById(R.id.layoutCarLL_x050);
        layoutCarLB_x050 = findViewById(R.id.layoutCarLB_x050);
        layoutCarBB_x050 = findViewById(R.id.layoutCarBB_x050);
        layoutCarRB_x050 = findViewById(R.id.layoutCarRB_x050);
        layoutCarRR_x050 = findViewById(R.id.layoutCarRR_x050);

        front_right_front_right_door = findViewById(R.id.front_right_front_right_door);
        front_right_front_right_wing = findViewById(R.id.front_right_front_right_wing);
        front_right_front_bumper = findViewById(R.id.front_right_front_bumper);
        front_right_rear_right_door = findViewById(R.id.front_right_rear_right_door);
        front_right_roof = findViewById(R.id.front_right_roof);
        front_right_hood = findViewById(R.id.front_right_hood);
        front_right_windshield = findViewById(R.id.front_right_windshield);
        front_right_rear_right_wing = findViewById(R.id.front_right_rear_right_wing);
        front_right_front_right_headlight = findViewById(R.id.front_right_front_right_headlight);
        front_right_front_right_wheel = findViewById(R.id.front_right_front_right_wheel);
        front_right_rear_right_wheel = findViewById(R.id.front_right_rear_right_wheel);
        front_right_central_bumper_grille = findViewById(R.id.front_right_central_bumper_grille);

        front_front_windshield  = findViewById(R.id.front_front_windshield);
        front_front_front_bumper  = findViewById(R.id.front_front_front_bumper);
        front_front_front_left_headlight  = findViewById(R.id.front_front_front_left_headlight);
        front_front_front_right_headlight  = findViewById(R.id.front_front_front_right_headlight);
        front_front_roof  = findViewById(R.id.front_front_roof);
        front_front_hood  = findViewById(R.id.front_front_hood);
        front_front_central_bumper_grille = findViewById(R.id.front_front_central_bumper_grille);
        front_left_rear_left_door  = findViewById(R.id.front_left_rear_left_door);
        front_left_windshield = findViewById(R.id.front_left_windshield);
        front_left_front_left_wing  = findViewById(R.id.front_left_front_left_wing);
        front_left_front_left_door  = findViewById(R.id.front_left_front_left_door);
        front_left_front_bumper  = findViewById(R.id.front_left_front_bumper);
        front_left_hood  = findViewById(R.id.front_left_hood);
        front_left_front_left_headlight  = findViewById(R.id.front_left_front_left_headlight);
        front_left_roof  = findViewById(R.id.front_left_roof);
        front_left_rear_left_wing  = findViewById(R.id.front_left_rear_left_wing);
        front_left_front_left_wheel  = findViewById(R.id.front_left_front_left_wheel);
        front_left_rear_left_wheel  = findViewById(R.id.front_left_rear_left_wheel);
        front_left_central_bumper_grille = findViewById(R.id.front_left_central_bumper_grille);
        left_left_front_bumper  = findViewById(R.id.left_left_front_bumper);
        left_left_front_left_door  = findViewById(R.id.left_left_front_left_door);
        left_left_front_left_headlight  = findViewById(R.id.left_left_front_left_headlight);
        left_left_front_left_wing  = findViewById(R.id.left_left_front_left_wing);
        left_left_front_wheel  = findViewById(R.id.left_left_front_wheel);
        left_left_hood  = findViewById(R.id.left_left_hood);
        left_left_rear_bumper  = findViewById(R.id.left_left_rear_bumper);
        left_left_rear_left_door  = findViewById(R.id.left_left_rear_left_door);
        left_left_rear_left_light  = findViewById(R.id.left_left_rear_left_light);
        left_left_rear_left_wing  = findViewById(R.id.left_left_rear_left_wing);
        left_left_roof  = findViewById(R.id.left_left_roof);
        left_left_rear_wheel  = findViewById(R.id.left_left_rear_wheel);
        back_left_front_left_wing  = findViewById(R.id.back_left_front_left_wing);
        back_left_front_wheel  = findViewById(R.id.back_left_front_wheel);
        back_left_rear_body_glass  = findViewById(R.id.back_left_rear_body_glass);
        back_left_rear_bumper  = findViewById(R.id.back_left_rear_bumper);
        back_left_rear_left_door  = findViewById(R.id.back_left_rear_left_door);
        back_left_rear_left_light  = findViewById(R.id.back_left_rear_left_light);
        back_left_rear_left_wing  = findViewById(R.id.back_left_rear_left_wing);
        back_left_rear_wheel  = findViewById(R.id.back_left_rear_wheel);
        back_left_trunk_lid  = findViewById(R.id.back_left_trunk_lid);
        back_left_roof  = findViewById(R.id.back_left_roof);
        back_left_front_left_door  = findViewById(R.id.back_left_front_left_door);
        back_back_rear_body_glass  = findViewById(R.id.back_back_rear_body_glass);
        back_back_rear_bumper  = findViewById(R.id.back_back_rear_bumper);
        back_back_rear_left_light  = findViewById(R.id.back_back_rear_left_light);
        back_back_rear_left_wing  = findViewById(R.id.back_back_rear_left_wing);
        back_back_rear_right_light  = findViewById(R.id.back_back_rear_right_light);
        back_back_rear_right_wing  = findViewById(R.id.back_back_rear_right_wing);
        back_back_roof  = findViewById(R.id.back_back_roof);
        back_back_trunk_lid  = findViewById(R.id.back_back_trunk_lid);
        back_right_front_right_door  = findViewById(R.id.back_right_front_right_door);
        back_right_front_right_wing  = findViewById(R.id.back_right_front_right_wing);
        back_right_front_wheel  = findViewById(R.id.back_right_front_wheel);
        back_right_rear_body_glass  = findViewById(R.id.back_right_rear_body_glass);
        back_right_rear_bumper  = findViewById(R.id.back_right_rear_bumper);
        back_right_rear_right_door  = findViewById(R.id.back_right_rear_right_door);
        back_right_trunk_lid  = findViewById(R.id.back_right_trunk_lid);
        back_right_rear_right_wing  = findViewById(R.id.back_right_rear_right_wing);
        back_right_rear_right_light  = findViewById(R.id.back_right_rear_right_light);
        back_right_rear_wheel  = findViewById(R.id.back_right_rear_wheel);
        back_right_roof  = findViewById(R.id.back_right_roof);
        right_right_front_bumper  = findViewById(R.id.right_right_front_bumper);
        right_right_front_right_door  = findViewById(R.id.right_right_front_right_door);
        right_right_front_right_headlight  = findViewById(R.id.right_right_front_right_headlight);
        right_right_front_right_wing  = findViewById(R.id.right_right_front_right_wing);
        right_right_front_wheel  = findViewById(R.id.right_right_front_wheel);
        right_right_hood  = findViewById(R.id.right_right_hood);
        right_right_rear_bumper  = findViewById(R.id.right_right_rear_bumper);
        right_right_rear_right_door  = findViewById(R.id.right_right_rear_right_door);
        right_right_rear_right_light  = findViewById(R.id.right_right_rear_right_light);
        right_right_rear_right_wing  = findViewById(R.id.right_right_rear_right_wing);
        right_right_rear_wheel  = findViewById(R.id.right_right_rear_wheel);
        right_right_roof  = findViewById(R.id.right_right_roof);
    }

    private void initAllSideViews(){
        blockCarRF_x050 = findViewById(R.id.blockCarRF_x050);
        blockCarRF_x033 = findViewById(R.id.blockCarRF_x033);
        blockCarFF_x033 = findViewById(R.id.blockCarFF_x033);
        blockCarLF_x033 = findViewById(R.id.blockCarLF_x033);
        blockCarFF_x050 = findViewById(R.id.blockCarFF_x050);
        blockCarFF_x033 = findViewById(R.id.blockCarFF_x033);
        blockCarLF_x050 = findViewById(R.id.blockCarLF_x050);
        blockCarLF_x033 = findViewById(R.id.blockCarLF_x033);
        blockCarLL_x050 = findViewById(R.id.blockCarLL_x050);
        blockCarLL_x033 = findViewById(R.id.blockCarLL_x033);
        blockCarLB_x050 = findViewById(R.id.blockCarLB_x050);
        blockCarLB_x033 = findViewById(R.id.blockCarLB_x033);
        blockCarBB_x050 = findViewById(R.id.blockCarBB_x050);
        blockCarBB_x033 = findViewById(R.id.blockCarBB_x033);
        blockCarRB_x050 = findViewById(R.id.blockCarRB_x050);
        blockCarBR_x033 = findViewById(R.id.blockCarBR_x033);
        blockCarRR_x050 = findViewById(R.id.blockCarRR_x050);
        blockCarRR_x033 = findViewById(R.id.blockCarRR_x033);
    }

    private void hideAllSideViews(){
        hideSideView(blockCarRF_x050);
        hideSideView(blockCarRF_x033);
        hideSideView(blockCarFF_x033);
        hideSideView(blockCarLF_x033);
        hideSideView(blockCarFF_x050);
        hideSideView(blockCarFF_x033);
        hideSideView(blockCarLF_x050);
        hideSideView(blockCarLF_x033);
        hideSideView(blockCarLL_x050);
        hideSideView(blockCarLL_x033);
        hideSideView(blockCarLB_x050);
        hideSideView(blockCarLB_x033);
        hideSideView(blockCarBB_x050);
        hideSideView(blockCarBB_x033);
        hideSideView(blockCarRB_x050);
        hideSideView(blockCarBR_x033);
        hideSideView(blockCarRR_x050);
        hideSideView(blockCarRR_x033);
    }
    
    private void hideSideView(RelativeLayout elem){
        elem.setVisibility(View.GONE);
    }
    
    private void activateSvBlock(){
        // при заданном ракурсе, определим приближение
        if(sideView == Const.VIEW_RIGHT_FRONT){
            setVisibilityOnScaleValue(blockCarRF_x050, blockCarRF_x033);
        } else if (sideView == Const.VIEW_FRONT_FRONT) {
            setVisibilityOnScaleValue(blockCarFF_x050, blockCarFF_x033);
        } else if (sideView == Const.VIEW_LEFT_FRONT) {
            setVisibilityOnScaleValue(blockCarLF_x050, blockCarLF_x033);
        } else if (sideView == Const.VIEW_LEFT_LEFT) {
            setVisibilityOnScaleValue(blockCarLL_x050, blockCarLL_x033);
        } else if (sideView == Const.VIEW_LEFT_BACK) {
            setVisibilityOnScaleValue(blockCarLB_x050, blockCarLB_x033);
        } else if (sideView == Const.VIEW_BACK_BACK) {
            setVisibilityOnScaleValue(blockCarBB_x050, blockCarBB_x033);
        } else if (sideView == Const.VIEW_RIGHT_BACK) {
            setVisibilityOnScaleValue(blockCarRB_x050, blockCarBR_x033);
        } else if (sideView == Const.VIEW_RIGHT_RIGHT) {
            setVisibilityOnScaleValue(blockCarRR_x050, blockCarRR_x033);
        }
    }

    private void setVisibilityOnScaleValue(RelativeLayout elem, RelativeLayout elem_default){
        if(scaleValue == 2){
            elem.setVisibility(View.VISIBLE);
        }else{
            elem_default.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideAllSideViews();
        blockCarRF_x033.setVisibility(View.VISIBLE);

        sumRepairs.setText(String.valueOf(getSharedValueInt("cartSum")));
        sumRepairsWork.setText(String.valueOf(getSharedValueInt("cartRepairWork")));
        cntTotalItems.setText(String.valueOf(getSharedValueInt("cartCnt")));
    }

    private void setDetailParams(ImageView elem, float scalingFactor, int marLeft, int marTop, int marRight ){
        elem.setX(0.0F);
        elem.setY(0.0F);
        elem.setScaleX(scalingFactor);
        elem.setScaleY(scalingFactor);
        RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        elemParams.setMargins(convertDpToPixels(ImageDragActivity.this, marLeft),
                convertDpToPixels(ImageDragActivity.this, marTop), marRight, 0);
        elem.setLayoutParams(elemParams);
    }

    private void sendRequestToProxy(){
        startActivity(new Intent(ImageDragActivity.this, ProxyActivity.class));
    }

    private void setNewCoordinates(ImageView elem, float dx, float dy){
        elem.setX(elem.getX() + dx);
        elem.setX(elem.getY() + dy);
    }


    private void doMoveAction(MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x = event.getX();
            y = event.getY();
        }else if (event.getAction() == MotionEvent.ACTION_UP) {
            durationTouch = event.getEventTime() - event.getDownTime();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            dx = event.getX() - x;
            dy = event.getY() - y;
            if(sideView == Const.VIEW_RIGHT_FRONT){
//                if( layoutCarRF_x050.getX() > getImageMiddle()){
//                    dx = (layoutCarRF_x050.getX() - getImageMiddle() + 1 )*(-1) ;
//                }else if(layoutCarRF_x050.getX() < Math.abs(getImageMiddle())*(-1)) {
//                    dx = Math.abs(layoutCarRF_x050.getX() + getImageMiddle() - 1 ) ;
//                }
                layoutCarRF_x050.setX(layoutCarRF_x050.getX() + dx);
                front_right_front_right_door.setX(front_right_front_right_door.getX() + dx);
                front_right_front_right_wing.setX(front_right_front_right_wing.getX() + dx);
                front_right_front_bumper.setX(front_right_front_bumper.getX() + dx);
                front_right_rear_right_door.setX(front_right_rear_right_door.getX() + dx);
                front_right_roof.setX(front_right_roof.getX() + dx);
                front_right_hood.setX(front_right_hood.getX() + dx);
                front_right_windshield.setX(front_right_windshield.getX() + dx);
                front_right_central_bumper_grille.setX(front_right_central_bumper_grille.getX() + dx);
                front_right_rear_right_wing.setX(front_right_rear_right_wing.getX() + dx);
                front_right_front_right_headlight.setX(front_right_front_right_headlight.getX() + dx);
                front_right_front_right_wheel.setX(front_right_front_right_wheel.getX() + dx);
                front_right_rear_right_wheel.setX(front_right_rear_right_wheel.getX() + dx);
            } else if (sideView == Const.VIEW_FRONT_FRONT) {
                layoutCarFF_x050.setX(layoutCarFF_x050.getX() + dx);
                front_front_windshield.setX(front_front_windshield.getX() + dx);
                front_front_front_bumper.setX(front_front_front_bumper.getX() + dx);
                front_front_central_bumper_grille.setX(front_front_central_bumper_grille.getX() + dx);
                front_front_front_left_headlight.setX(front_front_front_left_headlight.getX() + dx);
                front_front_front_right_headlight.setX(front_front_front_right_headlight.getX() + dx);
                front_front_roof.setX(front_front_roof.getX() + dx);
                front_front_hood.setX(front_front_hood.getX() + dx);
            } else if (sideView == Const.VIEW_LEFT_FRONT) {
                layoutCarLF_x050.setX(layoutCarLF_x050.getX() + dx);
                front_left_rear_left_door.setX( front_left_rear_left_door.getX() + dx);
                front_left_windshield.setX( front_left_windshield.getX() + dx);
                front_left_central_bumper_grille.setX(front_left_central_bumper_grille.getX() + dx);
                front_left_front_left_wing.setX( front_left_front_left_wing.getX() + dx);
                front_left_front_left_door.setX( front_left_front_left_door.getX() + dx);
                front_left_front_bumper.setX( front_left_front_bumper.getX() + dx);
                front_left_hood.setX( front_left_hood.getX() + dx);
                front_left_front_left_headlight.setX( front_left_front_left_headlight.getX() + dx);
                front_left_roof.setX( front_left_roof.getX() + dx);
                front_left_rear_left_wing.setX( front_left_rear_left_wing.getX() + dx);
                front_left_front_left_wheel.setX( front_left_front_left_wheel.getX() + dx);
                front_left_rear_left_wheel.setX( front_left_rear_left_wheel.getX() + dx);
            } else if (sideView == Const.VIEW_LEFT_LEFT) {
                layoutCarLL_x050.setX(layoutCarLL_x050.getX() + dx);
                left_left_front_bumper.setX( left_left_front_bumper.getX() + dx);
                left_left_front_left_door.setX( left_left_front_left_door.getX() + dx);
                left_left_front_left_headlight.setX( left_left_front_left_headlight.getX() + dx);
                left_left_front_left_wing.setX( left_left_front_left_wing.getX() + dx);
                left_left_front_wheel.setX( left_left_front_wheel.getX() + dx);
                left_left_hood.setX( left_left_hood.getX() + dx);
                left_left_rear_bumper.setX( left_left_rear_bumper.getX() + dx);
                left_left_rear_left_door.setX( left_left_rear_left_door.getX() + dx);
                left_left_rear_left_light.setX( left_left_rear_left_light.getX() + dx);
                left_left_rear_left_wing.setX( left_left_rear_left_wing.getX() + dx);
                left_left_roof.setX( left_left_roof.getX() + dx);
                left_left_rear_wheel.setX( left_left_rear_wheel.getX() + dx);
            } else if (sideView == Const.VIEW_LEFT_BACK) {
                layoutCarLB_x050.setX(layoutCarLB_x050.getX() + dx);
                back_left_front_left_wing.setX( back_left_front_left_wing.getX() + dx);
                back_left_front_wheel.setX( back_left_front_wheel.getX() + dx);
                back_left_rear_body_glass.setX( back_left_rear_body_glass.getX() + dx);
                back_left_rear_bumper.setX( back_left_rear_bumper.getX() + dx);
                back_left_rear_left_door.setX( back_left_rear_left_door.getX() + dx);
                back_left_rear_left_light.setX( back_left_rear_left_light.getX() + dx);
                back_left_rear_left_wing.setX( back_left_rear_left_wing.getX() + dx);
                back_left_rear_wheel.setX( back_left_rear_wheel.getX() + dx);
                back_left_trunk_lid.setX( back_left_trunk_lid.getX() + dx);
                back_left_roof.setX( back_left_roof.getX() + dx);
                back_left_front_left_door.setX( back_left_front_left_door.getX() + dx);
            } else if (sideView == Const.VIEW_BACK_BACK) {
                layoutCarBB_x050.setX(layoutCarBB_x050.getX() + dx);
                back_back_rear_body_glass.setX( back_back_rear_body_glass.getX() + dx);
                back_back_rear_bumper.setX( back_back_rear_bumper.getX() + dx);
                back_back_rear_left_light.setX( back_back_rear_left_light.getX() + dx);
                back_back_rear_left_wing.setX( back_back_rear_left_wing.getX() + dx);
                back_back_rear_right_light.setX( back_back_rear_right_light.getX() + dx);
                back_back_rear_right_wing.setX( back_back_rear_right_wing.getX() + dx);
                back_back_roof.setX( back_back_roof.getX() + dx);
                back_back_trunk_lid.setX( back_back_trunk_lid.getX() + dx);
            } else if (sideView == Const.VIEW_RIGHT_BACK) {
                layoutCarRB_x050.setX(layoutCarRB_x050.getX() + dx);
                back_right_front_right_door.setX( back_right_front_right_door.getX() + dx);
                back_right_front_right_wing.setX( back_right_front_right_wing.getX() + dx);
                back_right_front_wheel.setX( back_right_front_wheel.getX() + dx);
                back_right_rear_body_glass.setX( back_right_rear_body_glass.getX() + dx);
                back_right_rear_bumper.setX( back_right_rear_bumper.getX() + dx);
                back_right_rear_right_door.setX( back_right_rear_right_door.getX() + dx);
                back_right_trunk_lid.setX( back_right_trunk_lid.getX() + dx);
                back_right_rear_right_wing.setX( back_right_rear_right_wing.getX() + dx);
                back_right_rear_right_light.setX( back_right_rear_right_light.getX() + dx);
                back_right_rear_wheel.setX( back_right_rear_wheel.getX() + dx);
                back_right_roof.setX( back_right_roof.getX() + dx);
            } else if (sideView == Const.VIEW_RIGHT_RIGHT) {
                layoutCarRR_x050.setX(layoutCarRR_x050.getX() + dx);
                right_right_front_bumper.setX( right_right_front_bumper.getX() + dx);
                right_right_front_right_door.setX( right_right_front_right_door.getX() + dx);
                right_right_front_right_headlight.setX( right_right_front_right_headlight.getX() + dx);
                right_right_front_right_wing.setX( right_right_front_right_wing.getX() + dx);
                right_right_front_wheel.setX( right_right_front_wheel.getX() + dx);
                right_right_hood.setX( right_right_hood.getX() + dx);
                right_right_rear_bumper.setX( right_right_rear_bumper.getX() + dx);
                right_right_rear_right_door.setX( right_right_rear_right_door.getX() + dx);
                right_right_rear_right_light.setX( right_right_rear_right_light.getX() + dx);
                right_right_rear_right_wing.setX( right_right_rear_right_wing.getX() + dx);
                right_right_rear_wheel.setX( right_right_rear_wheel.getX() + dx);
                right_right_roof.setX( right_right_roof.getX() + dx);
            }
            x = event.getX();
            y = event.getY();
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
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        return sharedPreferences.getInt(name, 0);
    }
    private String getSharedValueStr(String name){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(scaleValue < 2) {
            if(isActivateSideViewDetails == 0){
                activateSideViewDetails();
                isActivateSideViewDetails = 1;
            }
        }
    }

    private String getScaleImagePrefix(String sideView, int scaleVal){
        String res = "";
        if(scaleVal == 1){
            res += Const.IMG_PREFIX_033;
        }else{
            res += Const.IMG_PREFIX_050;
        }
        return res += sideView+"_";
    }

    private void activateSideViewDetails(){
        /*
         * Сколько на отображение распознанно для этого sideView
         * Сгенерировать названия
         * Сгенерировать ImageView
         * Получить заранее определенные соответствующие координаты каждой детали
         * Присвоить к parent
         * */
        String prefixImg = "";
        RelativeLayout parent_layout;
//        String prefixImg = getScaleImagePrefix(sideView);
//        if(sideView.equals(Const.VIEW_RIGHT_FRONT)){
        prefixImg = getScaleImagePrefix(Const.VIEW_RIGHT_FRONT, 1);
        parent_layout = blockCarRF_x033;
        initDetail( parent_layout, prefixImg+"roof", 75,  65, "roof", "6RU817111B", "Крыша", 75000000 + 21 );
        initDetail( parent_layout, prefixImg+"hood", 174,  customBiasValue(110,  2), "hood", "6RU823031C", "Капот", 75000000 + 15 );
        initDetail( parent_layout, prefixImg+"front_bumper", 190,  160, "front_bumper", "6RU807221A", "Бампер передний", 75000000 + 12 );
        initDetail( parent_layout, prefixImg+"windshield", 144,  72, "windshield", "6RU845011J", "Лобовое стекло", 75000000 + 17 );
        initDetail( parent_layout, prefixImg+"central_bumper_grille", 273, 146, "central_bumper_grille", "6RU853651D", "Решетка бампера центральная", 75000000 + 225 );
        initDetail( parent_layout, prefixImg+"front_right_door", 97,  customBiasValue(68, -2 ), "front_right_door", "6RU831056J", "Дверь передняя правая" , 75000000 + 13 );
        initDetail( parent_layout, prefixImg+"front_right_wing", 150, 110, "front_right_wing", "6RU821106C", "Крыло переднее правое", 75000000 + 14 );
        initDetail( parent_layout, prefixImg+"front_right_headlight", 212,  147, "front_right_headlight", "6RU941016", "Фара передняя правая", 75000000 + 19 );
        initDetail( parent_layout, prefixImg+"rear_right_wing", 42, 79, "rear_right_wing", "6RU809605A", "Крыло заднее правое", 75000000 + 16 );
        initDetail( parent_layout, prefixImg+"rear_right_door", 64, 103, "rear_right_door", "6RU833056D", "Дверь задняя правая", 75000000 + 11 );
        initDetail( parent_layout, prefixImg+"front_wheel", 160,  163, "front_wheel", "6Q0601027AC", "Колесо", 75000000 + 22 );
        initDetail( parent_layout, prefixImg+"rear_wheel", 51,  138, "rear_wheel", "6Q0601027AC", "Колесо", 75000000 + 23 );
//        }else if(sideView.equals(Const.VIEW_FRONT_FRONT)){
        prefixImg = getScaleImagePrefix(Const.VIEW_FRONT_FRONT, 1);
        parent_layout = blockCarFF_x033;
        initDetail( parent_layout, prefixImg+"hood", 95,  100, "hood", "6RU823031C", "Капот", 75000000 + 102 );
        initDetail( parent_layout, prefixImg+"roof", 111,  48, "roof", "6RU817111B", "Крыша", 75000000 + 103 );
        initDetail( parent_layout, prefixImg+"front_bumper", 76, 160, "front_bumper", "6RU807221A", "Бампер передний", 75000000 + 101 );
        initDetail( parent_layout, prefixImg+"windshield", 111,  55, "windshield", "6RU845011J", "Лобовое стекло", 75000000 + 104 );
        initDetail( parent_layout, prefixImg+"central_bumper_grille", 118, 159, "central_bumper_grille", "6RU853651D", "Решетка бампера центральная", 75000000 + 225 );
        initDetail( parent_layout, prefixImg+"front_left_headlight", 260,  139, "front_left_headlight", "6RU941015", "Фара передняя левая", 75000000 + 105 );
        initDetail( parent_layout, prefixImg+"front_right_headlight", 83,  139, "front_right_headlight", "6RU941016", "Фара передняя правая", 75000000 + 106 );
//        }else if(sideView.equals(Const.VIEW_LEFT_FRONT)){
        prefixImg = getScaleImagePrefix(Const.VIEW_LEFT_FRONT, 1);
        parent_layout = blockCarLF_x033;
        initDetail( parent_layout, prefixImg+"hood", 49,  112, "hood", "6RU823031C", "Капот", 75000000 + 204 );
        initDetail( parent_layout, prefixImg+"roof", 119,  customBiasValue(65,2),"roof", "6RU817111B", "Крыша", 75000000 + 205 );
        initDetail( parent_layout, prefixImg+"windshield", 117,  75, "windshield", "6RU845011J", "Лобовое стекло", 75000000 + 209 );
        initDetail( parent_layout, prefixImg+"front_bumper", 34, 160, "front_bumper", "6RU807221A", "Бампер передний", 75000000 + 201 );
        initDetail( parent_layout, prefixImg+"central_bumper_grille", 43, 147, "central_bumper_grille", "6RU853651D", "Решетка бампера центральная", 75000000 + 225 );
        initDetail( parent_layout, prefixImg+"front_left_door", 240,  customBiasValue(74,-3),"front_left_door", "6RU831055J", "Дверь передняя левая", 75000000 + 202 );
        initDetail( parent_layout, prefixImg+"front_left_wing", 162,  customBiasValue(112,-2),"front_left_wing", "6RU821105C", "Крыло переднее левое", 75000000 + 203 );
        initDetail( parent_layout, prefixImg+"front_left_headlight", 114,  148, "front_left_headlight", "6RU941015", "Фара передняя левая", 75000000 + 208 );
        initDetail( parent_layout, prefixImg+"rear_left_wing", 318, customBiasValue(81,-2),"rear_left_wing", "6RU809605A", "Крыло заднее левое", 75000000 + 207 );
        initDetail( parent_layout, prefixImg+"rear_left_door", 295,  103, "rear_left_door", "6RU833055D", "Дверь задняя левая", 75000000 + 206 );
        initDetail( parent_layout, prefixImg+"front_wheel", 193,  168, "front_wheel", "6Q0601027AC", "Колесо", 75000000 + 222 );
        initDetail( parent_layout, prefixImg+"rear_wheel", 319,  138, "rear_wheel", "6Q0601027AC", "Колесо", 75000000 + 223 );
//        }else if(sideView.equals(Const.VIEW_LEFT_LEFT)){
        prefixImg = getScaleImagePrefix(Const.VIEW_LEFT_LEFT, 1);
        parent_layout = blockCarLL_x033;
        initDetail( parent_layout, prefixImg+"front_bumper", 16, 186, "front_bumper", "6RU807221A", "Бампер передний", 75000000 + 601 );
        initDetail( parent_layout, prefixImg+"front_left_door", 117,  117, "front_left_door", "6RU831055J", "Дверь передняя левая", 75000000 + 602 );
        initDetail( parent_layout, prefixImg+"front_left_wing", 36,  149, "front_left_wing", "6RU821105C", "Крыло переднее левое", 75000000 + 603 );
        initDetail( parent_layout, prefixImg+"front_left_headlight", 363,  153, "front_left_headlight", "6RU941015", "Фара передняя левая", 75000000 + 604 );
        initDetail( parent_layout, prefixImg+"rear_left_light", 21,  172, "rear_left_light", "6RU945095M", "Фонарь задний левый", 75000000 + 605 );
        initDetail( parent_layout, prefixImg+"hood", 26,  142, "hood", "6RU823031C", "Капот", 75000000 + 606 );
        initDetail( parent_layout, prefixImg+"roof", 113,  108, "roof", "6RU817111B", "Крыша", 75000000 + 607 );
        initDetail( parent_layout, prefixImg+"rear_bumper", 336,  173, "rear_bumper", "6RU807421FGRU", "Бампер задний", 75000000 + 608 );
        initDetail( parent_layout, prefixImg+"rear_left_door", 213,  115, "rear_left_door", "6RU833055D", "Дверь задняя левая", 75000000 + 609 );
        initDetail( parent_layout, prefixImg+"rear_left_wing", 263, customBiasValue(123, -2), "rear_left_wing", "6RU809605A", "Крыло заднее левое", 75000000 + 610 );
        initDetail( parent_layout, prefixImg+"front_wheel", 50,  190, "front_wheel", "6Q0601027AC", "Колесо", 75000000 + 611 );
        initDetail( parent_layout, prefixImg+"rear_wheel", customBiasValue(284, 2),  customBiasValue(186, -2), "rear_wheel", "6Q0601027AC", "Колесо", 75000000 + 612 );
//        }else if(sideView.equals(Const.VIEW_LEFT_BACK)){
        prefixImg = getScaleImagePrefix(Const.VIEW_LEFT_BACK, 1);
        parent_layout = blockCarLB_x033;
        initDetail( parent_layout, prefixImg+"front_left_wing", 24, customBiasValue(121, -2), "front_left_wing", "6RU821105C", "Крыло переднее левое", 75000000 + 701 );
        initDetail( parent_layout, prefixImg+"front_left_door", 47,  customBiasValue(80, -2), "front_left_door", "6RU831055J", "Дверь передняя левая", 75000000 + 702 );
        initDetail( parent_layout, prefixImg+"roof", 77,  customBiasValue(69, -1), "roof", "6RU817111B", "Крыша", 75000000 + 703 );
        initDetail( parent_layout, prefixImg+"rear_bumper", 166,  155, "rear_bumper", "6RU807421FGRU", "Бампер задний", 75000000 + 704 );
        initDetail( parent_layout, prefixImg+"rear_left_door", 85,  customBiasValue(77, -2), "rear_left_door", "6RU833055D", "Дверь задняя левая", 75000000 + 705 );
        initDetail( parent_layout, prefixImg+"rear_left_wing", 119, customBiasValue(82, -3), "rear_left_wing", "6RU809605A", "Крыло заднее левое", 75000000 + 706 );
        initDetail( parent_layout, prefixImg+"rear_body_glass", 166,  customBiasValue(77, -2), "rear_body_glass", "6RU845051F", "Стекло кузова заднее", 75000000 + 707 );
        initDetail( parent_layout, prefixImg+"trunk_lid", 205,  110, "trunk_lid", "6RU827025F", "Крышка багажника", 75000000 + 708 );
        initDetail( parent_layout, prefixImg+"rear_left_light", 202,  135, "rear_left_light", "6RU945095M", "Фонарь задний левый", 75000000 + 709 );
        initDetail( parent_layout, prefixImg+"front_wheel", 26,  149, "front_wheel", "6Q0601027AC", "Колесо", 75000000 + 710 );
        initDetail( parent_layout, prefixImg+"rear_wheel", 133,  177, "rear_wheel", "6Q0601027AC", "Колесо", 75000000 + 711 );
//        }else if(sideView.equals(Const.VIEW_BACK_BACK)){
        prefixImg = getScaleImagePrefix(Const.VIEW_BACK_BACK, 1);
        parent_layout = blockCarBB_x033;
        initDetail( parent_layout, prefixImg+"rear_body_glass", 106, 62, "rear_body_glass", "6RU845051F", "Стекло кузова заднее", 75000000 + 301 );
        initDetail( parent_layout, prefixImg+"rear_bumper", 74,  160, "rear_bumper", "6RU807421FGRU", "Бампер задний", 75000000 + 304 );
        initDetail( parent_layout, prefixImg+"rear_left_wing", 81,  82, "rear_left_wing", "6RU809605A", "Крыло заднее левое", 75000000 + 305 );
        initDetail( parent_layout, prefixImg+"rear_right_wing", 275, 82, "rear_right_wing", "6RU809605A", "Крыло заднее правое", 75000000 + 306 );
        initDetail( parent_layout, prefixImg+"roof", 111,  56, "roof", "6RU817111B", "Крыша", 75000000 + 307 );
        initDetail( parent_layout, prefixImg+"trunk_lid", 98,  98, "trunk_lid", "6RU827025F", "Крышка багажника", 75000000 + 308 );
        initDetail( parent_layout, prefixImg+"rear_left_light", 77,  123, "rear_left_light", "6RU945095M", "Фонарь задний левый", 75000000 + 309 );
        initDetail( parent_layout, prefixImg+"right_rear_light", 279,  123, "right_rear_light", "6RU945096K", "Фонарь задний правый", 75000000 + 310 );
//        }else if(sideView.equals(Const.VIEW_RIGHT_BACK)){
        prefixImg = getScaleImagePrefix(Const.VIEW_RIGHT_BACK, 1);
        parent_layout = blockCarBR_x033;
        initDetail( parent_layout, prefixImg+"front_right_wing", 342, customBiasValue(119, -1), "front_right_wing", "6RU821106C", "Крыло переднее правое", 75000000 + 401 );
        initDetail( parent_layout, prefixImg+"front_right_door", 285,  customBiasValue(79, -2), "front_right_door", "6RU831056J", "Дверь передняя правая", 75000000 + 402 );
        initDetail( parent_layout, prefixImg+"roof", 136,  70, "roof", "6RU817111B", "Крыша", 75000000 + 403 );
        initDetail( parent_layout, prefixImg+"rear_bumper", 27,  customBiasValue(157, -2), "rear_bumper", "6RU807421FGRU", "Бампер задний", 75000000 + 404 );
        initDetail( parent_layout, prefixImg+"rear_right_door", 244,  customBiasValue(78, -2), "rear_right_door", "6RU833056D", "Дверь задняя правая", 75000000 + 405 );
        initDetail( parent_layout, prefixImg+"rear_right_wing", 143, customBiasValue(82, -3), "rear_right_wing", "6RU809605A", "Крыло заднее правое", 75000000 + 406 );
        initDetail( parent_layout, prefixImg+"rear_body_glass", 82,  77, "rear_body_glass", "6RU845051F", "Стекло кузова заднее", 75000000 + 407 );
        initDetail( parent_layout, prefixImg+"trunk_lid", 33,  110, "trunk_lid", "6RU827025F", "Крышка багажника", 75000000 + 408 );
        initDetail( parent_layout, prefixImg+"rear_right_light", 133,  135, "rear_right_light", "6RU945096K", "Фонарь задний правый", 75000000 + 409 );
        initDetail( parent_layout, prefixImg+"front_wheel", 340,  149, "front_wheel", "6Q0601027AC", "Колесо", 75000000 + 410 );
        initDetail( parent_layout, prefixImg+"rear_wheel", 208,  175, "rear_wheel", "6Q0601027AC", "Колесо", 75000000 + 411 );
//        }else if(sideView.equals(Const.VIEW_RIGHT_RIGHT)){
        prefixImg = getScaleImagePrefix(Const.VIEW_RIGHT_RIGHT, 1);
        parent_layout = blockCarRR_x033;
        initDetail( parent_layout, prefixImg+"front_bumper", 352, 170, "front_bumper", "6RU807221A", "Бампер передний", 75000000 + 501 );
        initDetail( parent_layout, prefixImg+"front_right_door", 190,  103, "front_right_door", "6RU831056J", "Дверь передняя правая", 75000000 + 502 );
        initDetail( parent_layout, prefixImg+"front_right_wing", 289,  138, "front_right_wing", "6RU821106C", "Крыло переднее правое", 75000000 + 503 );
        initDetail( parent_layout, prefixImg+"front_right_headlight", 361,  158, "front_right_headlight", "6RU941016", "Фара передняя правая", 75000000 + 504 );
        initDetail( parent_layout, prefixImg+"rear_right_light", 15,  144, "rear_right_light", "6RU945096K", "Фонарь задний правый", 75000000 + 505 );
        initDetail( parent_layout, prefixImg+"hood", 286,  130, "hood", "6RU823031C", "Капот", 75000000 + 506 );
        initDetail( parent_layout, prefixImg+"roof", 96,  96, "roof", "6RU817111B", "Крыша", 75000000 + 507 );
        initDetail( parent_layout, prefixImg+"rear_bumper", 10,  166, "rear_bumper", "6RU807421FGRU", "Бампер задний", 75000000 + 508 );
        initDetail( parent_layout, prefixImg+"rear_right_door", 98,  103, "rear_right_door", "6RU833056D", "Дверь задняя правая", 75000000 + 509 );
        initDetail( parent_layout, prefixImg+"rear_right_wing", 15, customBiasValue(112, -2), "rear_right_wing", "6RU809605A", "Крыло заднее правое", 75000000 + 510 );
        initDetail( parent_layout, prefixImg+"front_wheel", 303,  178, "front_wheel", "6Q0601027AC", "Колесо", 75000000 + 511 );
        initDetail( parent_layout, prefixImg+"rear_wheel", 62,  182, "rear_wheel", "6Q0601027AC", "Колесо", 75000000 + 512 );

    }

    private int scaleValueTo050(int def){
        return (int)(def*1.5);
    }
    private void initDetail(RelativeLayout parent_layout, String fName, int leftDp, int topDp,  String part_name,  String article,  String part_name_rus, int idDetail){
        Resources r = this.getResources();
        float density = r.getDisplayMetrics().density;
        double wCar = layoutCarRF_x033.getWidth();
        double hCar = layoutCarRF_x033.getHeight();

        Context context = layoutCarRF_x033.getContext();
        String tmpDetailName = null;
        try {
            tmpDetailName = context.getResources().getResourceName(idDetail);
        }catch (Exception e){
            tmpDetailName = null;
        }
        int detailImageId = context.getResources().getIdentifier(fName, "drawable", context.getPackageName());
        if(detailImageId > 0 && tmpDetailName == null ){
            Drawable drawable = getResources().getDrawable(detailImageId );
            int widthImage = drawable.getIntrinsicWidth();
            int heightImage = drawable.getIntrinsicHeight();
            double wDetail = widthImage/density;
            double hDetail = heightImage/density;

            double detailPxWidth = wDetail; // 173
            double detailPxHeight =  hDetail; // 70
            double detailPxLeft = leftDp; // 190
            double detailPxTop = topDp; // 160

            double ratioWidth = 396/detailPxWidth ; //2.289017341040462;
            double ratioHeight = 297/detailPxHeight; //4.242857142857143;
            double ratioLeft = 396/detailPxLeft; //2.084210526315789;
            double ratioTop = 297/detailPxTop; // 1.84472049689441;

            int left = (int) (wCar/ ratioLeft );
    //        int top = (int) (hCar/ ratioTop );
            double calcHeight = wCar/1.333333;
            int biasHeight = (int) (((hCar - calcHeight)*0.3333)*0.3333);
    //        if (wCar == 1040) { biasHeight = 0 ; }
            int top = (int) (calcHeight/ ratioTop - biasHeight); //  - (((780-613)*0.3333) /2 ));
            // define Main Image height once
            blockCarRF_x033.getLayoutParams().height = (int) calcHeight;
            blockCarFF_x033.getLayoutParams().height = (int) calcHeight;
            blockCarLF_x033.getLayoutParams().height = (int) calcHeight;
            blockCarLL_x033.getLayoutParams().height = (int) calcHeight;
            blockCarLB_x033.getLayoutParams().height = (int) calcHeight;
            blockCarBB_x033.getLayoutParams().height = (int) calcHeight;
            blockCarBR_x033.getLayoutParams().height = (int) calcHeight;
            blockCarRR_x033.getLayoutParams().height = (int) calcHeight;
            // init new Detail
            ImageView elem = new ImageView(this);
            elem.setId(idDetail);
            RelativeLayout.LayoutParams elemParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            elemParams.width = (int) (wCar/ ratioWidth );
            elemParams.height = (int) (hCar/ ratioHeight );
            elemParams.setMargins(left, top, 0, 0);
            elem.setLayoutParams(elemParams);

            elem.setImageResource(detailImageId);

//            genImageViewOnClick(elem, article, part_name_rus);
            genImageViewOnTouch(elem);
            final String da = article;
            final String ta = part_name;
            final String tr = part_name_rus;
            elem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(durationTouch < 200) {
                        // 1.save detail id
                        setSharedValueStr("detail_article", da);
                        setSharedValueStr("detail_title", ta);
                        setSharedValueStr("detail_title_rus", tr);
                        // 2.redirect to page-Prices
                        //                startActivity(new Intent(DetailsListActivity.this, DetailPricesActivity.class));
                        startActivity(new Intent(ImageDragActivity.this, ItemsList.class));
                        //Toast.makeText(DetailsListActivity.this, da, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            parent_layout.addView(elem);
            }
    }

    private String getCodeFromName(String engDetailCode){
        return engDetailCode.toLowerCase().substring(12).replace(" ", "_");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ImageDragActivity.this, MainActivity.class));
    }
    private int customBiasValue(int defaultValue, int bias){
        Resources r = this.getResources();
        int a =  r.getDisplayMetrics().widthPixels;
        int b  = r.getDisplayMetrics().heightPixels;
        if( a == 1080 && b == 2264
                || a == 1080 && b == 2120
                || a == 1080 && b == 2291
        ){
            return defaultValue + bias;
        }
        return defaultValue;
    }

    private void setImageMiddle(int value){
        this.setImageMiddle = value;
    }
    private int getImageMiddle(){
        return this.setImageMiddle;
    }


}