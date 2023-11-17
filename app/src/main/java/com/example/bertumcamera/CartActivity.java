package com.example.bertumcamera;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class CartActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor ed;
    private Context context;
    private LinearLayout areaInformer, areaDetailsRepairs, ll_details, ll_repair;
    private RelativeLayout rl_detail_1, rl_detail_2, rl_detail_3, rl_detail_4, rl_detail_5,
            rl_detail_6, rl_detail_7, rl_detail_8, rl_detail_9, rl_detail_10 ;
    private TextView cart_warn_empty_order, detail_title_1, cart_total_details,
            detail_minprice_1, detail_minprice_2, detail_minprice_3, detail_minprice_4, detail_minprice_5,
            detail_minprice_6, detail_minprice_7, detail_minprice_8, detail_minprice_9, detail_minprice_10,
            detail_article_1, detail_article_2, detail_article_3, detail_article_4, detail_article_5,
            detail_article_6, detail_article_7, detail_article_8, detail_article_9, detail_article_10,
            totalPrice,
            linkToMainpage, repair_hours_1, repair_minprice_1;
    private TextView
            cntTotalItems, sumRepairs, sumRepairsWork;

    private String detail_article_orig ="", detail_article = "", detail_title = "", detail_title_rus = "", detail_type = "",
            detail_price = "";
    private ImageView cart_tab_details, cart_tab_repair, detail_close_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cart_total_details = findViewById(R.id.cart_total_details);
        cart_warn_empty_order = findViewById(R.id.cart_warn_empty_order);
        cart_warn_empty_order.setText(Html.fromHtml(Const.MSG_SMETA_EMPTY));

        // переменные для заполнения
        totalPrice = findViewById(R.id.totalPrice);
        // контроллы управления и видимости
        areaInformer = findViewById(R.id.areaInformer);
        areaDetailsRepairs = findViewById(R.id.areaDetailsRepairs);
        context = areaDetailsRepairs.getContext();
        linkToMainpage = findViewById(R.id.linkToMainpage);
        cart_tab_details = findViewById(R.id.cart_tab_details);
        cart_tab_repair = findViewById(R.id.cart_tab_repair);
        ll_details = findViewById(R.id.ll_details);
        ll_repair = findViewById(R.id.ll_repair);

        // нижняя часть стоимость
        cntTotalItems = findViewById(R.id.cntTotalItems);
        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);

        initDetailBlocks();


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
            detail_article_1 = findViewById(R.id.detail_article_1);
//            detail_minprice_1 = findViewById(R.id.detail_minprice_1);
            repair_hours_1 = findViewById(R.id.repair_hours_1);
            repair_minprice_1 = findViewById(R.id.repair_minprice_1);
            detail_close_1 = findViewById(R.id.detail_close_1);


            rl_detail_1.setVisibility(View.VISIBLE);
            detail_title_1.setText(detail_title_rus);
            detail_article_1.setText(detail_article);
            detail_minprice_1.setText(detail_price+" руб.");
            double hours = DataStore.getHoursPriceRepairWork(String.valueOf(detail_article_orig));
            repair_hours_1.setText(String.valueOf(hours));
            int repairPrice = (int) (hours*Const.REPAIR_WORK_PERHOUR);
            repair_minprice_1.setText(String.valueOf(repairPrice)+" руб.");

            totalPrice.setText(
                    String.valueOf(
                            calculateTotalPrice()
                    ) + " руб."
            );
            detail_close_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rl_detail_1.setVisibility(View.GONE);

                    ArrayList<String> subDetailsAricles = DataStore.getSubDetailsArticles(detail_article_orig);
                    int cnt = 1;
                    for (String subArticle : subDetailsAricles) {
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
                    cart_warn_empty_order.setText(Html.fromHtml("Вы отменили все позиции вашего заказа.<br>"+Const.MSG_SMETA_EMPTY));

                }
            });

            String statusTabDetails = "smeta_tab_details_active";
            String statusTabRepair = "smeta_tab_repair_disable";
            cart_tab_details.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ll_repair.setVisibility(View.GONE);
                    ll_details.setVisibility(View.VISIBLE);
                    cart_tab_details.setImageResource(getImageId("smeta_tab_details_active"));
                    cart_tab_repair.setImageResource(getImageId("smeta_tab_repair_disable"));
                }
            });
            cart_tab_repair.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ll_details.setVisibility(View.GONE);
                    ll_repair.setVisibility(View.VISIBLE);
                    cart_tab_details.setImageResource(getImageId("smeta_tab_details_disable"));
                    cart_tab_repair.setImageResource(getImageId("smeta_tab_repair_active"));
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
            cart_tab_details.setImageResource(getImageId(statusTabDetails));
            cart_tab_repair.setImageResource(getImageId(statusTabRepair));

            // SUB DETAILS
            // get subdetails list
            ArrayList<String> subDetailsAricles = DataStore.getSubDetailsArticles(detail_article_orig);
            int cnt = 1;
            for (final String subArticleAvl : subDetailsAricles){
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
                detPriceTV.setText( String.valueOf(DataStore.getSupplyDetailPrice(subArticleAvl)) + " руб.");

                ImageView detailClose = findViewById(context.getResources().getIdentifier(
                        "detail_close_"+ String.valueOf(cnt), "id",
                        context.getPackageName()) );

                detailClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // descend Repair price proportion
                        ArrayList<String> subDetailsAricles = DataStore.getSubDetailsArticles(detail_article_orig);
                        int cntSubDetTotal = subDetailsAricles.size();
                        int cntSubDetCurrent = getCountActiveSubDetails() -1; // минус одна деталь которая сейчас обрабатывается
                        double hoursDetail = DataStore.getHoursDetailRepair(detail_article_orig);
                        double hoursSubDetails = DataStore.getHoursSubDetailsRepair(detail_article_orig);
                        double minutesRepairFinal = ((hoursSubDetails*60)/cntSubDetTotal)*cntSubDetCurrent + hoursDetail*60;
                        int hoursSubDetailsFinal = (int) (minutesRepairFinal/60);
                        int minutesSubDetailsFinal = (int) (minutesRepairFinal - hoursSubDetailsFinal*60 );
                        String txtRepairHours = "";
                        if(hoursSubDetailsFinal > 0 ) txtRepairHours = txtRepairHours + String.valueOf(hoursSubDetailsFinal) + "ч. ";
                        if(minutesSubDetailsFinal > 0 ) txtRepairHours = txtRepairHours + String.valueOf(minutesSubDetailsFinal) + "мин.";
                        repair_hours_1.setText(txtRepairHours);
                        int repairPriceFinal = (int) ((minutesRepairFinal* Const.REPAIR_WORK_PERHOUR)/60);
                        repair_minprice_1.setText(String.valueOf(repairPriceFinal)+" руб.");

                        //int newPerHourPrice = descendPerHourPrice(DataStore.getSupplyDetailPrice(subArticleAvl), detail_article_orig);

                        // hide plate so we dont count its price at final SubDetails Price
                        rl.setVisibility(View.GONE);

                        int subDetPrice = getPriceActiveSubDetails();
                        setSharedValueInt( "cartRepairWork", repairPriceFinal + subDetPrice );
                        setSharedValueInt( "cartSum", Integer.parseInt(detail_price) );
                        setBottomPrices();
                        totalPrice.setText(
                                String.valueOf(
                                        calculateTotalPrice()
                                ) + " руб."
                        );
                        cart_total_details.setText( String.valueOf(getCountActiveSubDetails() + 1) + " позиций");
                    }
                });

            }
            cart_total_details.setText( String.valueOf(getCountActiveSubDetails() + 1) + " позиций");

        }else{
            activateAreaInformer();
        }
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
    }

    private int getCountActiveSubDetails(){
        int cnt = 0; // hack for previously gone element
        if(rl_detail_2.getVisibility() == View.VISIBLE) cnt = cnt+1;
        if(rl_detail_3.getVisibility() == View.VISIBLE) cnt = cnt+1;
        if(rl_detail_4.getVisibility() == View.VISIBLE) cnt = cnt+1;
        if(rl_detail_5.getVisibility() == View.VISIBLE) cnt = cnt+1;
        if(rl_detail_6.getVisibility() == View.VISIBLE) cnt = cnt+1;
        if(rl_detail_7.getVisibility() == View.VISIBLE) cnt = cnt+1;
        if(rl_detail_8.getVisibility() == View.VISIBLE) cnt = cnt+1;
        if(rl_detail_9.getVisibility() == View.VISIBLE) cnt = cnt+1;
        if(rl_detail_10.getVisibility() == View.VISIBLE) cnt = cnt+1;
        return cnt;
    }

    private int getPriceActiveSubDetails(){
        int cnt = 0;
        if(rl_detail_2.getVisibility() == View.VISIBLE) cnt = cnt + getSubDetailPrice(detail_article_2);
        if(rl_detail_3.getVisibility() == View.VISIBLE) cnt = cnt + getSubDetailPrice(detail_article_3);
        if(rl_detail_4.getVisibility() == View.VISIBLE) cnt = cnt + getSubDetailPrice(detail_article_4);
        if(rl_detail_5.getVisibility() == View.VISIBLE) cnt = cnt + getSubDetailPrice(detail_article_5);
        if(rl_detail_6.getVisibility() == View.VISIBLE) cnt = cnt + getSubDetailPrice(detail_article_6);
        if(rl_detail_7.getVisibility() == View.VISIBLE) cnt = cnt + getSubDetailPrice(detail_article_7);
        if(rl_detail_8.getVisibility() == View.VISIBLE) cnt = cnt + getSubDetailPrice(detail_article_8);
        if(rl_detail_9.getVisibility() == View.VISIBLE) cnt = cnt + getSubDetailPrice(detail_article_9);
        if(rl_detail_10.getVisibility() == View.VISIBLE) cnt = cnt + getSubDetailPrice(detail_article_10);
        return cnt;
    }
    private int getSubDetailPrice(TextView elem) {
        String txtArticle = (String) elem.getText();
        int detPrice = DataStore.getSupplyDetailPrice(txtArticle);
        return detPrice;
    }

    private void activateAreaInformer(){
        cart_warn_empty_order.setText(Html.fromHtml(Const.MSG_SMETA_EMPTY));
        areaInformer.setVisibility(View.VISIBLE);
        areaDetailsRepairs.setVisibility(View.GONE);
        linkToMainpage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(CartActivity.this, MainActivity.class));
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

}