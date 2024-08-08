package com.example.bertumcamera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SmetaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private Context context;
    private DrawerLayout drawerLayout;
    private ImageView ico_menu;
    private LinearLayout areaInformer, areaDetailsRepairs, ll_details, ll_repair;
    private RelativeLayout rl_warn_subdetails, rl_warn_repair, rl_detail_1, rl_detail_2, rl_detail_3, rl_detail_4, rl_detail_5,
    rl_detail_6, rl_detail_7, rl_detail_8, rl_detail_9, rl_detail_10,
            rl_repair_1, rl_repair_2;
    private TextView smeta_warn_text, smeta_warn_empty_order, detail_title_1,
            detail_minprice_1, detail_minprice_2, detail_minprice_3, detail_minprice_4, detail_minprice_5,
            detail_minprice_6, detail_minprice_7, detail_minprice_8, detail_minprice_9, detail_minprice_10,
            detail_article_1, detail_article_2, detail_article_3, detail_article_4, detail_article_5,
            detail_article_6, detail_article_7, detail_article_8, detail_article_9, detail_article_10,
            totalPrice,
            linkToMainpage, repair_hours_1, repair_minprice_1, repair_hours_2, repair_minprice_2;
    private TextView
            cntTotalItems, sumRepairs, sumRepairsWork, linkToSmetaRepairs , linkToSmetaDetails;

    private String detail_article_orig ="", detail_article = "", detail_title = "", detail_title_rus = "", detail_type = "",
            detail_price = "";
    private ImageView smeta_tab_details, smeta_tab_repair, map_service_place, move_detail_service,
            detail_close_1, repair_close_1, repair_close_2, ico_cart, button_cart,
            detail_photo_1, detail_photo_2, detail_photo_3, detail_photo_4, detail_photo_5,
            detail_photo_6, detail_photo_7, detail_photo_8, detail_photo_9, detail_photo_10;
    private Button detail_buy_2, detail_buy_3, detail_buy_4, detail_buy_5, detail_buy_6,
            detail_buy_7, detail_buy_8, detail_buy_9, detail_buy_10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        smeta_warn_text = findViewById(R.id.smeta_warn_text);
        smeta_warn_empty_order = findViewById(R.id.smeta_warn_empty_order);
        smeta_warn_text.setText(Html.fromHtml(Const.MSG_SMETA_WARN));
        smeta_warn_empty_order.setText(Html.fromHtml(Const.MSG_SMETA_EMPTY));
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
//        smeta_warn_text.setText(String.valueOf(getSharedValueInt("smetaTab")));

        // переменные для заполнения
        totalPrice = findViewById(R.id.totalPrice);
        // контроллы управления и видимости
        areaInformer = findViewById(R.id.areaInformer);
        areaDetailsRepairs = findViewById(R.id.areaDetailsRepairs);
        context = areaDetailsRepairs.getContext();
        linkToMainpage = findViewById(R.id.linkToMainpage);
        smeta_tab_details = findViewById(R.id.smeta_tab_details);
        smeta_tab_repair = findViewById(R.id.smeta_tab_repair);
        map_service_place = findViewById(R.id.map_service_place);
        move_detail_service = findViewById(R.id.move_detail_service);
        ll_details = findViewById(R.id.ll_details);
        ll_repair = findViewById(R.id.ll_repair);
        rl_warn_subdetails = findViewById(R.id.rl_warn_subdetails);
        rl_warn_repair = findViewById(R.id.rl_warn_repair);
        ico_cart = findViewById(R.id.ico_cart);
        button_cart = findViewById(R.id.button_cart);

        // нижняя часть стоимость
        cntTotalItems = findViewById(R.id.cntTotalItems);
        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);
        linkToSmetaDetails = findViewById(R.id.linkToSmetaDetails);
        linkToSmetaRepairs = findViewById(R.id.linkToSmetaRepairs);

        initDetailBlocks();
        initRepairBlocks();

        linkToSmetaDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_repair.setVisibility(View.GONE);
                ll_details.setVisibility(View.VISIBLE);
                smeta_tab_details.setImageResource(getImageId("smeta_tab_details_active"));
                smeta_tab_repair.setImageResource(getImageId("smeta_tab_repair_disable"));
            }
        });
        linkToSmetaRepairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_details.setVisibility(View.GONE);
                ll_repair.setVisibility(View.VISIBLE);
                smeta_tab_details.setImageResource(getImageId("smeta_tab_details_disable"));
                smeta_tab_repair.setImageResource(getImageId("smeta_tab_repair_active"));
            }
        });

        map_service_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        } );

        move_detail_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        } );

        if(getSharedValueInt("cartSum") > 0 ) {
            areaInformer.setVisibility(View.GONE);
            areaDetailsRepairs.setVisibility(View.VISIBLE);

            detail_article_orig = getSharedValueStr("detail_article");
            detail_title = getSharedValueStr("detail_title");
            detail_title_rus = getSharedValueStr("detail_title_rus");
            detail_type = getSharedValueStr("detail_minpice_type");
            detail_article = getSharedValueStr("detail_minpice_article");
            detail_price = getSharedValueStr("detail_minpice_final");
            detail_title_1 = findViewById(R.id.detail_title_1);
//            detail_article_1 = findViewById(R.id.detail_article_1);
//            detail_minprice_1 = findViewById(R.id.detail_minprice_1);
            detail_close_1 = findViewById(R.id.detail_close_1);
            repair_close_1 = findViewById(R.id.repair_close_1);
            repair_close_2 = findViewById(R.id.repair_close_2);

            rl_detail_1.setVisibility(View.VISIBLE);
            detail_title_1.setText(detail_title_rus);
            detail_article_1.setText(detail_article);
            detail_minprice_1.setText(detail_price+Const.TXT_TAG_RUB);

            try {
                int detailPhotoId = getResources().getIdentifier("detail_photo_1",
                        "id", getPackageName());
                final ImageView detailPhotoIV = findViewById(detailPhotoId);
                detailPhotoId = context.getResources().getIdentifier("photo_det_"+detail_article.toLowerCase(), "drawable", context.getPackageName());
                if(detailPhotoId == 0) {
                    detailPhotoId = context.getResources().getIdentifier("detail_noimage", "drawable", context.getPackageName());
                }
                detailPhotoIV.setImageResource(detailPhotoId);
            }catch (Exception e){
                String isError = e.getMessage();
            }

            repair_hours_1 = findViewById(R.id.repair_hours_1);
            repair_minprice_1 = findViewById(R.id.repair_minprice_1);
            repair_hours_2 = findViewById(R.id.repair_hours_2);
            repair_minprice_2 = findViewById(R.id.repair_minprice_2);
            double hours = DataStore.getHoursDetailRepair(String.valueOf(detail_article_orig));
            repair_hours_1.setText(String.valueOf(hours));
            int repairPrice = (int) (hours*Const.REPAIR_WORK_PERHOUR);
            repair_minprice_1.setText(String.valueOf(repairPrice)+Const.TXT_TAG_RUB);

            hours = 0;
            repair_hours_2.setText(String.valueOf(hours));
            repairPrice = (int) (hours*Const.REPAIR_WORK_PERHOUR);
            repair_minprice_2.setText(String.valueOf(repairPrice)+Const.TXT_TAG_RUB);

            totalPrice.setText(
                String.valueOf(
                        calculateTotalPrice()
                ) + Const.TXT_TAG_RUB
            );

            ico_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SmetaActivity.this, CartActivity.class));
                }
            });
            button_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postDataUsingVolley("123456789", "");
                }
            });

            detail_close_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl_detail_1.setVisibility(View.GONE);

                    ArrayList<String> subDetailsArticles = DataStore.getSubDetailsArticles(detail_article_orig);
                    int cnt = 1;
                    for (String subArticle : subDetailsArticles) {
                        cnt++;
                        int detLayoutId = getResources().getIdentifier("rl_detail_"+ String.valueOf(cnt),
                                "id", getPackageName());
                        final RelativeLayout rl = findViewById(detLayoutId);
                        rl.setVisibility(View.GONE);
                    }
                    setSharedValueStr("detail_minpice_type", "");
                    setSharedValueStr("detail_minpice_article", "");
                    setSharedValueStr("detail_minpice_final", "");
                    // PARENT DETAIL CLOSED = CLEAR ALL CART
                    // ALL TOTAL PRICES
                    setSharedValueInt("cartSum", 0);
                    setSharedValueInt("cartRepairWork", 0);
                    setSharedValueInt("cartCnt", 0);
                    sumRepairs.setText(String.valueOf(getSharedValueInt("cartSum")));
                    sumRepairsWork.setText(String.valueOf(getSharedValueInt("cartRepairWork")));
                    cntTotalItems.setText(String.valueOf(getSharedValueInt("cartCnt")));

                    // TAB REPAIR ITEMS CLEAR

                    // AWAKE EMPTY WARNING
                    activateAreaInformer();
                    smeta_warn_empty_order.setText(Html.fromHtml(Const.MSG_U_CANCEL_ALL + Const.MSG_SMETA_EMPTY));

                }
            });

            repair_close_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl_repair_1.setVisibility(View.GONE);
                    setSharedValueInt("repairMainDetailVisible", 0);
                    onRepairCloseRecalculate();
                }
            });

            repair_close_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl_repair_2.setVisibility(View.GONE);
                    setSharedValueInt("repairSubDetailVisible", 0);
                    rl_warn_repair.setVisibility(View.GONE);
                    onRepairCloseRecalculate();
                }
            });

            String statusTabDetails = "smeta_tab_details_active";
            String statusTabRepair = "smeta_tab_repair_disable";
            smeta_tab_details.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                ll_repair.setVisibility(View.GONE);
                ll_details.setVisibility(View.VISIBLE);
                smeta_tab_details.setImageResource(getImageId("smeta_tab_details_active"));
                smeta_tab_repair.setImageResource(getImageId("smeta_tab_repair_disable"));
                }
            });
            smeta_tab_repair.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ll_details.setVisibility(View.GONE);
                    ll_repair.setVisibility(View.VISIBLE);
                    smeta_tab_details.setImageResource(getImageId("smeta_tab_details_disable"));
                    smeta_tab_repair.setImageResource(getImageId("smeta_tab_repair_active"));
                }
            });

            if(getSharedValueInt("smetaTab") == 1){ // tab Repair selected
                ll_details.setVisibility(View.GONE);
                ll_repair.setVisibility(View.VISIBLE);
                statusTabDetails = "smeta_tab_details_disable";
                statusTabRepair = "smeta_tab_repair_active";
            }else{ // default tab Detail selected
                ll_repair.setVisibility(View.GONE);
                ll_details.setVisibility(View.VISIBLE);
            }
            smeta_tab_details.setImageResource(getImageId(statusTabDetails));
            smeta_tab_repair.setImageResource(getImageId(statusTabRepair));

            // SUB DETAILS
            // get subdetails list
            ArrayList<String> subDetailsArticles = DataStore.getSubDetailsArticles(detail_article_orig);
            int cnt = 1;
            if(subDetailsArticles.size() > 0 ) rl_warn_subdetails.setVisibility(View.VISIBLE);
            else rl_warn_subdetails.setVisibility(View.GONE);

            for (final String subArticleAvl : subDetailsArticles){
                cnt++;
                int detLayoutId = getResources().getIdentifier("rl_detail_"+ String.valueOf(cnt),
                        "id", getPackageName());
                final RelativeLayout rl = findViewById(detLayoutId);
                rl.setVisibility(View.VISIBLE);

                int detTitleId = getResources().getIdentifier("detail_title_"+ String.valueOf(cnt),
                        "id", getPackageName());
                final TextView detTitleTV = findViewById(detTitleId);
                detTitleTV.setText(DataStore.getSupplyDetailTitle(subArticleAvl));

                int detArticleId = getResources().getIdentifier("detail_article_"+ String.valueOf(cnt),
                        "id", getPackageName());
                final TextView detArticleTV = findViewById(detArticleId);
                detArticleTV.setText(subArticleAvl);

                int detPriceId = getResources().getIdentifier("detail_minprice_"+ String.valueOf(cnt),
                        "id", getPackageName());
                final TextView detPriceTV = findViewById(detPriceId);
                detPriceTV.setText( String.valueOf(DataStore.getPriceByArticleAvl(subArticleAvl)) + Const.TXT_TAG_RUB);

                Button btnBuy = findViewById(context.getResources().getIdentifier(
                        "detail_buy_"+ String.valueOf(cnt), "id",
                        context.getPackageName()) );

                ImageView detailClose = findViewById(context.getResources().getIdentifier(
                        "detail_close_"+ String.valueOf(cnt), "id",
                        context.getPackageName()) );

                // SET SUBDETAIL PHOTO
                try {
                    int detailPhotoId = getResources().getIdentifier("detail_photo_"+ String.valueOf(cnt),
                            "id", getPackageName());
                    final ImageView detailPhotoIV = findViewById(detailPhotoId);
                    detailPhotoId = context.getResources().getIdentifier("photo_det_"+subArticleAvl.toLowerCase(), "drawable", context.getPackageName());
                    if(detailPhotoId == 0) {
                        detailPhotoId = context.getResources().getIdentifier("detail_noimage", "drawable", context.getPackageName());
                    }
                    detailPhotoIV.setImageResource(detailPhotoId);
                }catch (Exception e){
                    String isError = e.getMessage();
                }
                final int cnt_final = cnt;
                detailClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    // descend Repair price proportion
                    setSharedValueInt("subDetailVisible_" + String.valueOf(cnt_final), 0);
                    ArrayList<String> subDetailsArticles = DataStore.getSubDetailsArticles(detail_article_orig);
                    int cntSubDetTotal = subDetailsArticles.size();
                    // hide plate so we dont count its price at final SubDetails Price
                    rl.setVisibility(View.GONE);
                    int cntSubDetCurrent = getCountActiveSubDetails();
                    // считаем количество видимых плашек, где нажаты кнопки Купить
                    int cntBuyPressed = getCountPressedBuyButtons();
//                        double hoursDetail = DataStore.getHoursDetailRepair(detail_article_orig);
                    // получим Базовое Время (БВ) на замену доп.деталей
                    double hoursSubDetails = DataStore.getHoursSubDetailsRepair(detail_article_orig);
                    /* БВ в минутах делим на общее количество деталей необходимое по регламенту, получим пропорциональное время на каждую деталь
                     далее это Время детали умножаем на количество купленных деталей, получим Актуальное Время на все доп. детали в списке*/
//                        double minutesRepairFinal = ((hoursSubDetails*60)/cntSubDetTotal)*cntSubDetCurrent; // + hoursDetail*60;
                    double minutesRepairFinal = ((hoursSubDetails*60)/cntSubDetTotal)*cntBuyPressed; // + hoursDetail*60;
                    // АВД выделим часы
                    int hoursSubDetailsFinal = (int) (minutesRepairFinal/60);
                    // АВД выделим оставшиеся минуты
                    int minutesSubDetailsFinal = (int) (minutesRepairFinal - hoursSubDetailsFinal*60 );
                    String txtRepairHours = "";
                    // сложение текста часы и минуты
                    if(hoursSubDetailsFinal > 0 ) txtRepairHours = txtRepairHours + String.valueOf(hoursSubDetailsFinal) + "ч. ";
                    if(minutesSubDetailsFinal > 0 ) txtRepairHours = txtRepairHours + String.valueOf(minutesSubDetailsFinal) + "мин.";
                    repair_hours_2.setText(txtRepairHours);
                    // стоимость работ
                    int repairPriceFinal = (int) ((minutesRepairFinal* Const.REPAIR_WORK_PERHOUR)/60);
                    repair_minprice_2.setText(String.valueOf(repairPriceFinal)+Const.TXT_TAG_RUB);

                    double hours = DataStore.getHoursDetailRepair(String.valueOf(detail_article_orig));
                    int repairPriceMainDetail = (int) (hours*Const.REPAIR_WORK_PERHOUR);
                    int subDetPrice = getPriceActiveSubDetails();

                    if(rl_repair_1.getVisibility() == View.GONE || getSharedValueInt("repairMainDetailVisible") == 0 ) repairPriceMainDetail = 0;
                    if(rl_repair_2.getVisibility() == View.GONE || getSharedValueInt("repairSubDetailVisible") == 0) repairPriceFinal = 0;

                    setSharedValueInt( "cartRepairWork", repairPriceFinal + repairPriceMainDetail );
                    setSharedValueInt( "cartSum", Integer.parseInt(detail_price) + subDetPrice );
                    setSharedValueInt("cartCnt", cntBuyPressed + 1);
                    setBottomPrices();
                    totalPrice.setText(
                            String.valueOf(
                                    calculateTotalPrice()
                            ) + Const.TXT_TAG_RUB
                    );

                    showWarns(cntBuyPressed);

                    }
                });

                final int finalCnt = cnt;
                btnBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btnBuy = findViewById(context.getResources().getIdentifier(
                                "detail_buy_"+ String.valueOf(finalCnt), "id",
                                context.getPackageName()) );
                        setSharedValueInt("subDetailBtnBuy_" + String.valueOf(finalCnt), 0);
                        setSharedValueInt("repairSubDetailVisible", 1);
                        rl_repair_2.setVisibility(View.VISIBLE);
                        // получим стоимость из Глоб. переменной - общая цена Осн дет. + Сопутств
                        int newTotalPrice = getSharedValueInt("cartSum") + DataStore.getPriceByArticleAvl(subArticleAvl);

                        ArrayList<String> subDetailsArticles = DataStore.getSubDetailsArticles(detail_article_orig);
                        int cntSubDetTotal = subDetailsArticles.size();
                        // получим Базовое Время (БВ) на замену доп.деталей
                        double hoursSubDetails = DataStore.getHoursSubDetailsRepair(detail_article_orig);

                        // disable button Buy so we count its price at summ of SubDetails Repair
                        if(getSharedValueInt("subDetailBtnBuy_" + String.valueOf(finalCnt)) == 0 ) {
                            btnBuy.setEnabled(false);
                            btnBuy.setBackgroundColor(Color.parseColor("#cdcdcd"));
                        }
                        // считаем количество видимых плашек, где нажаты кнопки Купить
                        int cntBuyPressed = getCountPressedBuyButtons();
                        /* БВ в минутах делим на общее количество деталей необходимое по регламенту, получим пропорциональное время на каждую деталь
                         далее это Время детали умножаем на количество купленных деталей, получим Актуальное Время на все доп. детали в списке*/
                        double minutesRepairFinal = ((hoursSubDetails*60)/cntSubDetTotal)*cntBuyPressed;
                        // АВД выделим часы
                        int hoursSubDetailsFinal = (int) (minutesRepairFinal/60);
                        // АВД выделим оставшиеся минуты
                        int minutesSubDetailsFinal = (int) (minutesRepairFinal - hoursSubDetailsFinal*60 );
                        String txtRepairHours = "";
                        // сложение текста часы и минуты
                        if(hoursSubDetailsFinal > 0 ) txtRepairHours = txtRepairHours + String.valueOf(hoursSubDetailsFinal) + "ч. ";
                        if(minutesSubDetailsFinal > 0 ) txtRepairHours = txtRepairHours + String.valueOf(minutesSubDetailsFinal) + "мин.";
                        repair_hours_2.setText(txtRepairHours);
                        // стоимость работ
                        int repairPriceFinal = (int) ((minutesRepairFinal* Const.REPAIR_WORK_PERHOUR)/60);
                        repair_minprice_2.setText(String.valueOf(repairPriceFinal)+Const.TXT_TAG_RUB);

                        double hours = DataStore.getHoursDetailRepair(String.valueOf(detail_article_orig));
                        int repairPriceMainDetail = (int) (hours*Const.REPAIR_WORK_PERHOUR);

                        if(rl_repair_1.getVisibility() == View.GONE || getSharedValueInt("repairMainDetailVisible") == 0 ) repairPriceMainDetail = 0;
                        if(rl_repair_2.getVisibility() == View.GONE || getSharedValueInt("repairSubDetailVisible") == 0) repairPriceFinal = 0;

                        setSharedValueInt( "cartRepairWork", repairPriceFinal + repairPriceMainDetail );
                        setSharedValueInt("cartSum", newTotalPrice );
                        setSharedValueInt("cartCnt", cntBuyPressed + 1);

                        Toast.makeText(SmetaActivity.this, "Деталь добавлена в корзину", Toast.LENGTH_SHORT).show();

                        setBottomPrices();
                        totalPrice.setText(
                                String.valueOf(
                                        calculateTotalPrice()
                                ) + Const.TXT_TAG_RUB
                        );
                        rl_warn_repair.setVisibility(View.VISIBLE);
                        rl_repair_2.setVisibility(View.VISIBLE);
                    }
                });
                if(getSharedValueInt("subDetailVisible_" + String.valueOf(cnt)) == 0 ){
                    rl.setVisibility(View.GONE);
                    if(getCountPressedBuyButtons() == 0 ) {
                        rl_warn_subdetails.setVisibility(View.GONE);
                        rl_warn_repair.setVisibility(View.GONE);
                        rl_repair_2.setVisibility(View.GONE);
                    }
                }
            }

        }else{
            activateAreaInformer();
        }

        linkToSmetaDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                doOnTabChange(0);
            }
        });
        linkToSmetaRepairs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                doOnTabChange(1);
            }
        });
    }

    public void openDialog() {
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.show(getSupportFragmentManager(), "");
    }

    private void initRepairBlocks(){
        rl_repair_1 = findViewById(R.id.rl_repair_1);
        rl_repair_2 = findViewById(R.id.rl_repair_2);
    }

    private void initDetailBlocks(){
        rl_detail_1 = findViewById(R.id.rl_detail_1);
        rl_detail_2 = findViewById(R.id.rl_detail_2);
        rl_detail_3 = findViewById(R.id.rl_detail_3);
        rl_detail_4 = findViewById(R.id.rl_detail_4);
        rl_detail_5 = findViewById(R.id.rl_detail_5);
        rl_detail_6 = findViewById(R.id.rl_detail_6);
        rl_detail_7 = findViewById(R.id.rl_detail_7);
        rl_detail_8 = findViewById(R.id.rl_detail_8);
        rl_detail_9 = findViewById(R.id.rl_detail_9);
        rl_detail_10 = findViewById(R.id.rl_detail_10);
        detail_minprice_1 = findViewById(R.id.detail_minprice_1);
        detail_minprice_2 = findViewById(R.id.detail_minprice_2);
        detail_minprice_3 = findViewById(R.id.detail_minprice_3);
        detail_minprice_4 = findViewById(R.id.detail_minprice_4);
        detail_minprice_5 = findViewById(R.id.detail_minprice_5);
        detail_minprice_6 = findViewById(R.id.detail_minprice_6);
        detail_minprice_7 = findViewById(R.id.detail_minprice_7);
        detail_minprice_8 = findViewById(R.id.detail_minprice_8);
        detail_minprice_9 = findViewById(R.id.detail_minprice_9);
        detail_minprice_10 = findViewById(R.id.detail_minprice_10);
        detail_article_1 = findViewById(R.id.detail_article_1);
        detail_article_2 = findViewById(R.id.detail_article_2);
        detail_article_3 = findViewById(R.id.detail_article_3);
        detail_article_4 = findViewById(R.id.detail_article_4);
        detail_article_5 = findViewById(R.id.detail_article_5);
        detail_article_6 = findViewById(R.id.detail_article_6);
        detail_article_7 = findViewById(R.id.detail_article_7);
        detail_article_8 = findViewById(R.id.detail_article_8);
        detail_article_9 = findViewById(R.id.detail_article_9);
        detail_article_10 = findViewById(R.id.detail_article_10);
        detail_photo_1 = findViewById(R.id.detail_photo_1);
        detail_photo_2 = findViewById(R.id.detail_photo_2);
        detail_photo_3 = findViewById(R.id.detail_photo_3);
        detail_photo_4 = findViewById(R.id.detail_photo_4);
        detail_photo_5 = findViewById(R.id.detail_photo_5);
        detail_photo_6 = findViewById(R.id.detail_photo_6);
        detail_photo_7 = findViewById(R.id.detail_photo_7);
        detail_photo_8 = findViewById(R.id.detail_photo_8);
        detail_photo_9 = findViewById(R.id.detail_photo_9);
        detail_photo_10 = findViewById(R.id.detail_photo_10);

        detail_buy_2 = findViewById(R.id.detail_buy_2);
        detail_buy_3 = findViewById(R.id.detail_buy_3);
        detail_buy_4 = findViewById(R.id.detail_buy_4);
        detail_buy_5 = findViewById(R.id.detail_buy_5);
        detail_buy_6 = findViewById(R.id.detail_buy_6);
        detail_buy_7 = findViewById(R.id.detail_buy_7);
        detail_buy_8 = findViewById(R.id.detail_buy_8);
        detail_buy_9 = findViewById(R.id.detail_buy_9);
        detail_buy_10 = findViewById(R.id.detail_buy_10);

    }

    private int getCountActiveSubDetails(){
        int cnt = 0;
        if(rl_detail_2.getVisibility() == View.VISIBLE && detail_buy_2.isEnabled() == false) cnt = cnt + 1;
        if(rl_detail_3.getVisibility() == View.VISIBLE && detail_buy_3.isEnabled() == false) cnt = cnt + 1;
        if(rl_detail_4.getVisibility() == View.VISIBLE && detail_buy_4.isEnabled() == false) cnt = cnt + 1;
        if(rl_detail_5.getVisibility() == View.VISIBLE && detail_buy_5.isEnabled() == false) cnt = cnt + 1;
        if(rl_detail_6.getVisibility() == View.VISIBLE && detail_buy_6.isEnabled() == false) cnt = cnt + 1;
        if(rl_detail_7.getVisibility() == View.VISIBLE && detail_buy_7.isEnabled() == false) cnt = cnt + 1;
        if(rl_detail_8.getVisibility() == View.VISIBLE && detail_buy_8.isEnabled() == false) cnt = cnt + 1;
        if(rl_detail_9.getVisibility() == View.VISIBLE && detail_buy_9.isEnabled() == false) cnt = cnt + 1;
        if(rl_detail_10.getVisibility() == View.VISIBLE && detail_buy_10.isEnabled() == false) cnt = cnt + 1;
        return cnt;
    }


    private int getCountPressedBuyButtons(){
        int cnt = 0; 
        if(rl_detail_2.getVisibility() == View.VISIBLE && detail_buy_2.isEnabled() == false) cnt = cnt+1;
        if(rl_detail_3.getVisibility() == View.VISIBLE && detail_buy_3.isEnabled() == false) cnt = cnt+1;
        if(rl_detail_4.getVisibility() == View.VISIBLE && detail_buy_4.isEnabled() == false) cnt = cnt+1;
        if(rl_detail_5.getVisibility() == View.VISIBLE && detail_buy_5.isEnabled() == false) cnt = cnt+1;
        if(rl_detail_6.getVisibility() == View.VISIBLE && detail_buy_6.isEnabled() == false) cnt = cnt+1;
        if(rl_detail_7.getVisibility() == View.VISIBLE && detail_buy_7.isEnabled() == false) cnt = cnt+1;
        if(rl_detail_8.getVisibility() == View.VISIBLE && detail_buy_8.isEnabled() == false) cnt = cnt+1;
        if(rl_detail_9.getVisibility() == View.VISIBLE && detail_buy_9.isEnabled() == false) cnt = cnt+1;
        if(rl_detail_10.getVisibility() == View.VISIBLE && detail_buy_10.isEnabled() == false) cnt = cnt+1;
        return cnt;
    }
    private int getPriceActiveSubDetails(){
        int cnt = 0;
        if(rl_detail_2.getVisibility() == View.VISIBLE && detail_buy_2.isEnabled() == false) cnt = cnt + getSubDetailPrice(detail_article_2);
        if(rl_detail_3.getVisibility() == View.VISIBLE && detail_buy_3.isEnabled() == false) cnt = cnt + getSubDetailPrice(detail_article_3);
        if(rl_detail_4.getVisibility() == View.VISIBLE && detail_buy_4.isEnabled() == false) cnt = cnt + getSubDetailPrice(detail_article_4);
        if(rl_detail_5.getVisibility() == View.VISIBLE && detail_buy_5.isEnabled() == false) cnt = cnt + getSubDetailPrice(detail_article_5);
        if(rl_detail_6.getVisibility() == View.VISIBLE && detail_buy_6.isEnabled() == false) cnt = cnt + getSubDetailPrice(detail_article_6);
        if(rl_detail_7.getVisibility() == View.VISIBLE && detail_buy_7.isEnabled() == false) cnt = cnt + getSubDetailPrice(detail_article_7);
        if(rl_detail_8.getVisibility() == View.VISIBLE && detail_buy_8.isEnabled() == false) cnt = cnt + getSubDetailPrice(detail_article_8);
        if(rl_detail_9.getVisibility() == View.VISIBLE && detail_buy_9.isEnabled() == false) cnt = cnt + getSubDetailPrice(detail_article_9);
        if(rl_detail_10.getVisibility() == View.VISIBLE && detail_buy_10.isEnabled() == false) cnt = cnt + getSubDetailPrice(detail_article_10);
        return cnt;
    }

    private int getSubDetailPrice(TextView elem) {
        String txtArticle = (String) elem.getText();
        int detPrice = DataStore.getPriceByArticleAvl(txtArticle);
        return detPrice;
    }

    private void activateAreaInformer(){
        smeta_warn_empty_order.setText(Html.fromHtml(Const.MSG_SMETA_EMPTY));
        areaInformer.setVisibility(View.VISIBLE);
        areaDetailsRepairs.setVisibility(View.GONE);
        linkToMainpage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(SmetaActivity.this, MainActivity.class));
            }
        });

    }

    private int calculateTotalPrice(){
        int res  = getSharedValueInt("cartSum") + getSharedValueInt("cartRepairWork");
        return res;
    }

    private int getImageId( String imageName){
        return context.getResources().getIdentifier(imageName,"drawable", context.getPackageName());
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
    protected void onResume() {
            super.onResume();
        setBottomPrices();
    }

    private void setBottomPrices(){
        sumRepairs.setText(String.valueOf(getSharedValueInt("cartSum")));
        sumRepairsWork.setText(String.valueOf(getSharedValueInt("cartRepairWork")));
        cntTotalItems.setText(String.valueOf(getSharedValueInt("cartCnt")));

    }

    private void doOnTabChange(int tabIndex){
        setSharedValueInt("smetaTab", tabIndex);
        String statusTabDetails = "smeta_tab_details_active";
        String statusTabRepair = "smeta_tab_repair_disable";
        if(getSharedValueInt("smetaTab") == 1){ // tab Repair selected
            ll_details.setVisibility(View.GONE);
            ll_repair.setVisibility(View.VISIBLE);
            statusTabDetails = "smeta_tab_details_disable";
            statusTabRepair = "smeta_tab_repair_active";
        }else{ // default tab Detail selected
            ll_repair.setVisibility(View.GONE);
            ll_details.setVisibility(View.VISIBLE);
        }
        smeta_tab_details.setImageResource(getImageId(statusTabDetails));
        smeta_tab_repair.setImageResource(getImageId(statusTabRepair));
    }

    private void onRepairCloseRecalculate(){
        ArrayList<String> subDetailsArticles = DataStore.getSubDetailsArticles(detail_article_orig);
        int cntSubDetTotal = subDetailsArticles.size();
        // получим Базовое Время (БВ) на замену доп.деталей
        double hoursSubDetails = DataStore.getHoursSubDetailsRepair(detail_article_orig);

        // считаем количество видимых плашек, где нажаты кнопки Купить
        int cntBuyPressed = getCountPressedBuyButtons();
                            /* БВ в минутах делим на общее количество деталей необходимое по регламенту, получим пропорциональное время на каждую деталь
                             далее это Время детали умножаем на количество купленных деталей, получим Актуальное Время на все доп. детали в списке*/
        double minutesRepairFinal = ((hoursSubDetails * 60) / cntSubDetTotal) * cntBuyPressed;
        // АВД выделим часы
        int hoursSubDetailsFinal = (int) (minutesRepairFinal / 60);
        // АВД выделим оставшиеся минуты
        int minutesSubDetailsFinal = (int) (minutesRepairFinal - hoursSubDetailsFinal * 60);
        String txtRepairHours = "";
        // сложение текста часы и минуты
        if (hoursSubDetailsFinal > 0)
            txtRepairHours = txtRepairHours + String.valueOf(hoursSubDetailsFinal) + "ч. ";
        if (minutesSubDetailsFinal > 0)
            txtRepairHours = txtRepairHours + String.valueOf(minutesSubDetailsFinal) + "мин.";
        repair_hours_2.setText(txtRepairHours);
        // стоимость работ
        int repairPriceFinal = (int) ((minutesRepairFinal * Const.REPAIR_WORK_PERHOUR) / 60);
        repair_minprice_2.setText(String.valueOf(repairPriceFinal) + Const.TXT_TAG_RUB);

        double hours = DataStore.getHoursDetailRepair(String.valueOf(detail_article_orig));
        int repairPriceMainDetail = (int) (hours * Const.REPAIR_WORK_PERHOUR);

        if(rl_repair_1.getVisibility() == View.GONE || getSharedValueInt("repairMainDetailVisible") == 0 ) repairPriceMainDetail = 0;
        if(rl_repair_2.getVisibility() == View.GONE || getSharedValueInt("repairSubDetailVisible") == 0) repairPriceFinal = 0;

        setSharedValueInt("cartRepairWork", repairPriceFinal + repairPriceMainDetail);
        setSharedValueInt("cartCnt", cntBuyPressed + 1);
        setBottomPrices();
        totalPrice.setText(
                String.valueOf(
                        calculateTotalPrice()
                ) + Const.TXT_TAG_RUB
        );
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
        DataStore.setMenuItems(item, SmetaActivity.this);
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(getSharedValueInt("cartSum") > 0 ) {
            for (int i = 2; i < 11; i++) {
                int subDetailVisible = getSharedValueInt("subDetailVisible_" + String.valueOf(i));
                // set plate visible?gone
                int detLayoutId = getResources().getIdentifier("rl_detail_" + String.valueOf(i),
                        "id", getPackageName());
                final RelativeLayout rl = findViewById(detLayoutId);
                if (subDetailVisible > 0) rl.setVisibility(View.VISIBLE);
                else rl.setVisibility(View.GONE);

                // set buy button pressed
                if (getSharedValueInt("subDetailBtnBuy_" + String.valueOf(i)) == 0) {
                    Button btnBuy = findViewById(context.getResources().getIdentifier(
                            "detail_buy_" + String.valueOf(i), "id",
                            context.getPackageName()));
                    btnBuy.setEnabled(false);
                    btnBuy.setBackgroundColor(Color.parseColor("#cdcdcd"));
                }
            }

            ArrayList<String> subDetailsArticles = DataStore.getSubDetailsArticles(detail_article_orig);
            int cntSubDetTotal = subDetailsArticles.size();
            // получим Базовое Время (БВ) на замену доп.деталей
            double hoursSubDetails = DataStore.getHoursSubDetailsRepair(detail_article_orig);

            // считаем количество видимых плашек, где нажаты кнопки Купить
            int cntBuyPressed = getCountPressedBuyButtons();
            /* БВ в минутах делим на общее количество деталей необходимое по регламенту, получим пропорциональное время на каждую деталь
            далее это Время детали умножаем на количество купленных деталей, получим Актуальное Время на все доп. детали в списке*/
            double minutesRepairFinal = ((hoursSubDetails * 60) / cntSubDetTotal) * cntBuyPressed;
            // АВД выделим часы
            int hoursSubDetailsFinal = (int) (minutesRepairFinal / 60);
            // АВД выделим оставшиеся минуты
            int minutesSubDetailsFinal = (int) (minutesRepairFinal - hoursSubDetailsFinal * 60);
            String txtRepairHours = "";
            // сложение текста часы и минуты
            if (hoursSubDetailsFinal > 0)
                txtRepairHours = txtRepairHours + String.valueOf(hoursSubDetailsFinal) + Const.TXT_TAG_HOUR;
            if (minutesSubDetailsFinal > 0)
                txtRepairHours = txtRepairHours + String.valueOf(minutesSubDetailsFinal) + Const.TXT_TAG_MIN;
            repair_hours_2.setText(txtRepairHours);
            // стоимость работ
            int repairPriceFinal = (int) ((minutesRepairFinal * Const.REPAIR_WORK_PERHOUR) / 60);
            repair_minprice_2.setText(String.valueOf(repairPriceFinal) + Const.TXT_TAG_RUB);

            double hours = DataStore.getHoursDetailRepair(String.valueOf(detail_article_orig));
            int repairPriceMainDetail = (int) (hours * Const.REPAIR_WORK_PERHOUR);

            if(getSharedValueInt("repairMainDetailVisible") == 0){ rl_repair_1.setVisibility(View.GONE); }
            if(getSharedValueInt("repairSubDetailVisible") == 0){ rl_repair_2.setVisibility(View.GONE); }

            if(rl_repair_1.getVisibility() == View.GONE  ) repairPriceMainDetail = 0;
            if(rl_repair_2.getVisibility() == View.GONE ) repairPriceFinal = 0;

            if( rl_repair_2.getVisibility() == View.GONE){
                rl_warn_repair.setVisibility(View.GONE);
            }

            setSharedValueInt("cartRepairWork", repairPriceFinal + repairPriceMainDetail);
            setSharedValueInt("cartCnt", cntBuyPressed + 1);
            setBottomPrices();
            totalPrice.setText(
                String.valueOf(
                        calculateTotalPrice()
                ) + Const.TXT_TAG_RUB
            );

            showWarns(cntBuyPressed);
        }
    }

    private void showWarns(int cnt){
        if (cnt > 0) {
            rl_warn_subdetails.setVisibility(View.VISIBLE);
            rl_warn_repair.setVisibility(View.VISIBLE);
            rl_repair_2.setVisibility(View.VISIBLE);
        } else {
            rl_warn_subdetails.setVisibility(View.GONE);
            rl_warn_repair.setVisibility(View.GONE);
            rl_repair_2.setVisibility(View.GONE);
        }
    }
    
    private void postDataUsingVolley(final String photoId, final String photoBase64) {
        String url = Const.URL_PAYMENT;
        RequestQueue queue = Volley.newRequestQueue(SmetaActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject objects = null;
                try {
                    objects = new JSONObject(response);
                    JSONObject confirm = objects.getJSONObject("confirm");
                    String link = (String) confirm.get("payment_url");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SmetaActivity.this, Const.MSG_ERROR_NO_RESPONSE, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                JSONObject amountObj = new JSONObject();
                try {
                    amountObj.put("value",
                            String.valueOf( getSharedValueInt("cartSum")
                                    + getSharedValueInt("cartRepairWork") )
                    );
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                JSONArray arrDetails = new JSONArray();
                try {
                    int quantiy = 1;
                    for (int i = 1; i < 11; i++) {
                        // if detail active
                        if (getSharedValueInt("subDetailVisible_" + String.valueOf(i)) > 0){
                            // if buy button pressed Or if it's first - Main Detail
                            if (i == 1
                                || getSharedValueInt("subDetailBtnBuy_" + String.valueOf(i)) == 0) {
                                DataStore.orderAddItem(arrDetails,
                                        getTextViewValue("detail_title_" + String.valueOf(i)),
                                        convertTextToInt("detail_minprice_" + String.valueOf(i)) ,
                                        quantiy,
                                        convertTextToInt("detail_minprice_" + String.valueOf(i))
                                );
                            }
                        }
                    }
                    if(rl_repair_1.getVisibility() == View.VISIBLE){
                        DataStore.orderAddItem(arrDetails,
                            "Ремонт основной детали",
                            convertTextToInt("repair_minprice_1") ,
                            quantiy,
                            convertTextToInt("repair_minprice_1")
                        );
                    }
                    if(rl_repair_2.getVisibility() == View.VISIBLE){
                        DataStore.orderAddItem(arrDetails,
                            "Работы по замене сопутствующих запчастей",
                            convertTextToInt("repair_minprice_2") ,
                            quantiy,
                            convertTextToInt("repair_minprice_2")
                        );
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JSONObject receiptObj = new JSONObject();
                try {
                    receiptObj.put("Email", "meff86@list.ru");
                    receiptObj.put("Taxation", "osn");
                    receiptObj.put("Items", arrDetails);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                JSONObject orderObj = new JSONObject();
                try {
                    orderObj.put("amount", amountObj);
                    orderObj.put("order_id", String.valueOf(System.currentTimeMillis()));
                    orderObj.put("receipt", receiptObj);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String jsonStr = orderObj.toString();

                params.put("_content", jsonStr);
                return params;
            }
        };
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);

        Toast.makeText(SmetaActivity.this, Const.MSG_DATA_SENT, Toast.LENGTH_SHORT).show();

        // post the data.
        queue.add(request);
    }

    private String getTextViewValue(String tvId){
        TextView ctl = findViewById(context.getResources().getIdentifier(
                tvId, "id",
                context.getPackageName()));
        return ctl.getText().toString();
    }

    private int convertTextToInt(String tvId){ // значение берется из контролла "Цена детали" , убираем значение " руб." в конце
        String val = getTextViewValue(tvId);
        return Integer.parseInt( val.substring(0, (val.length() - Const.TXT_TAG_RUB.length() )) );
    }


}