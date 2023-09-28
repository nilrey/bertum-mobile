package com.example.bertumcamera;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ItemsList extends AppCompatActivity {
    private TextView cntOrig, cntNonOrig, cntBU, cntDiscont, cntVariants,
            cntTotalItems, sumRepairs, sumRepairsWork, tagPrice1;
    private int cnt, low, high, cartSumRepairs, cartSumRepairsWork, cartCntRepairs, cntAllVariants = 0;

    private Button cartPrice1,cartPrice2, cartPrice3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_APPEND);
        SharedPreferences.Editor myEdit;
        myEdit = sharedPreferences.edit();
        myEdit.putInt("cntPhoto", 0);
        myEdit.commit();

        cntOrig = findViewById(R.id.cntOrig);
        cntNonOrig = findViewById(R.id.cntNonOrig);
        cntBU = findViewById(R.id.cntBU);
        cntDiscont = findViewById(R.id.cntDiscont);
        cntVariants = findViewById(R.id.cntVariants);

        cntTotalItems = findViewById(R.id.cntTotalItems);
        sumRepairs = findViewById(R.id.sumRepairs);
        sumRepairsWork = findViewById(R.id.sumRepairsWork);


        tagPrice1 = findViewById(R.id.tagPrice1);
        cartPrice1 = findViewById(R.id.cartPrice1);

        Random r = new Random();
        low = 1;
        high = 6;

        cnt = r.nextInt(high-low) + low;
        cntAllVariants += cnt;
        cntOrig.setText("ОРИГИНАЛ : " + cnt);

        cnt = r.nextInt(high-low) + low;
        cntAllVariants += cnt;
        cntNonOrig.setText("НЕОРИГИНАЛ : " + cnt);

        cnt = r.nextInt(high-low) + low;
        cntAllVariants += cnt;
        cntBU.setText("Б/У : " + cnt);

        cnt = r.nextInt(high-low) + low;
        cntAllVariants += cnt;
        cntDiscont.setText("УЦЕНКА : " + cnt);

        cntVariants.setText(cntAllVariants + " вариантов");

        low = 2500;
        high = 5500;
        cnt = r.nextInt(high-low) + low;
        tagPrice1.setText(String.valueOf(cnt) );

        // по клику
        cartPrice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // достаем сохраненные данные
                Log.d("BERTUM---------------", "OnClick");
                SharedPreferences getSharedPrefs = getSharedPreferences("MySharedPref", MODE_APPEND);
                int cartSum = getSharedPrefs.getInt("cartSum", 0); // get saved summ
                int cartCnt = getSharedPrefs.getInt("cartCnt", 0); // get saved cnt

                // цена товара 1
                int price1 = Integer.parseInt(tagPrice1.getText().toString()); // get added price
                cartSumRepairs = cartSum + price1;
                cartCntRepairs = cartCnt + 1;

                sumRepairs.setText(String.valueOf(cartSumRepairs));
                cntTotalItems.setText(String.valueOf(cartCntRepairs));

                SharedPreferences sh = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor edit1;
                edit1 = sh.edit();

                Random r = new Random();
                low = 6000;
                high = 18500;
                cartSumRepairsWork = r.nextInt(high-low) + low;
                sumRepairsWork.setText(String.valueOf(cartSumRepairsWork) );
                edit1.putInt("cartRepairWork", cartSumRepairsWork);

                edit1.putInt("cartSum", cartSumRepairs);
                edit1.putInt("cartCnt", cartCntRepairs);
                edit1.commit();

                Toast.makeText(ItemsList.this, "Деталь добавлна в корзину", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        int b = sh.getInt("cartSum", 0);
        Log.d("BERTUM---------------", "onResume");
//        Log.d("BERTUM---------------b", String.valueOf(b));
        sumRepairs.setText(String.valueOf(b));
        int c = sh.getInt("cartRepairWork", 0);
//        Log.d("BERTUM---------------c", String.valueOf(c));
        sumRepairsWork.setText(String.valueOf(c));
        int d = sh.getInt("cartCnt", 0);
//        Log.d("BERTUM---------------d", String.valueOf(d));
        cntTotalItems.setText(String.valueOf(d));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ItemsList.this, MainActivity.class));
    }
}