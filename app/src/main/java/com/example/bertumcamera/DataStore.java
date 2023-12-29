package com.example.bertumcamera;

import static android.support.v4.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DataStore {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor ed;
    //
    public static int getSumRepairsWork(String article){
        return (int) ( getHoursDetailRepair(article) * Const.REPAIR_WORK_PERHOUR);
    }

    public static int getMinPriceByType(String orig_art){ // article avtolans orig type
        int res = 0;
        if(orig_art.equals("6RU809957")) res = SelectMinPrice("6RU809957", "VWL055020700L", "VWL055020700L_D_10", "");
        else if(orig_art.equals("6RU821111")) res = SelectMinPrice("6RU821111", "", "", "6RU821111_BU");
        else if(orig_art.equals("6RU821112")) res = SelectMinPrice("6RU821112", "", "", "6RU821112_BU");
        else if(orig_art.equals("6RU809958")) res = SelectMinPrice("6RU809958", "VWL055020700R", "VWL055020700R_D_30", "");
        else if(orig_art.equals("6R0823301B")) res = SelectMinPrice("6R0823301B", "VWL0031044L", "", "6R0823301A_BU");
        else if(orig_art.equals("6R0823302B")) res = SelectMinPrice("6R0823302B", "VWL0031044R", "", "6R0823302A_BU");
        else if(orig_art.equals("6RU863831")) res = SelectMinPrice("6RU863831", "", "", "");
        else if(orig_art.equals("6RU805915A")) res = SelectMinPrice("6RU805915A", "L053015700", "", "");
        else if(orig_art.equals("6C0807723A")) res = SelectMinPrice("6C0807723A", "VWL0109004L", "", "");
        else if(orig_art.equals("6C0807724A")) res = SelectMinPrice("6C0807724A", "VWL0109004R", "", "");
        else if(orig_art.equals("6RU853671D")) res = SelectMinPrice("6RU853671D", "VWL05B011700", "VWL05B011700_D_30", "");
        else if(orig_art.equals("6RU853651D")) res = SelectMinPrice("6RU853651D", "VWL05B011100", "VWL05B011100_D_30", "");
        else if(orig_art.equals("6RU854661D")) res = SelectMinPrice("6RU854661D", "VWL05B013301L", "", "");
        else if(orig_art.equals("6RU854662D")) res = SelectMinPrice("6RU854662D", "VWL05B013301R", "VWL05B013300R_D_20", "");
        else if(orig_art.equals("6R0941061D")) res = SelectMinPrice("6R0941061D", "VWL05B010300L", "", "");
        else if(orig_art.equals("6R0941062D")) res = SelectMinPrice("6R0941062D", "VWL05B010300R", "", "");
        else if(orig_art.equals("6RU898393B")) res = SelectMinPrice("6RU898393B", "", "", "");
        else if(orig_art.equals("6RU898393B")) res = SelectMinPrice("6RU898393B", "", "", "");
        else if(orig_art.equals("6RF853841A")) res = SelectMinPrice("6RF853841A", "VWL0109006L", "", "");
        else if(orig_art.equals("6RF853842A")) res = SelectMinPrice("6RF853842A", "VWL0109006R", "", "");
        else if(orig_art.equals("6RF945105")) res = SelectMinPrice("6RF945105", "VWL05B010700L", "", "");
        else if(orig_art.equals("6RF945106")) res = SelectMinPrice("6RF945106", "VWL05B010700R", "", "");
        else if(orig_art.equals("6RF853835A")) res = SelectMinPrice("6RF853835A", "VWL00109005", "", "");
        else if(orig_art.equals("6RU945095M")) res = SelectMinPrice("6RU945095M", "ST44119DDL", "", "");
        else if(orig_art.equals("6RU810971A")) res = SelectMinPrice("6RU810971A", "VWL031048RL", "", "");
        else if(orig_art.equals("6RU945096K")) res = SelectMinPrice("6RU945096K", "ST44119DDR", "", "");
        else if(orig_art.equals("6RU809857")) res = SelectMinPrice("6RU809857", "STVWP6064D0", "", "");
        else if(orig_art.equals("6RU810972A")) res = SelectMinPrice("6RU810972A", "VWL031048RR", "", "");
        else if(orig_art.equals("6RU867211E")) res = SelectMinPrice("6RU867211E", "", "", "");
        else if(orig_art.equals("6RU839461E")) res = SelectMinPrice("6RU839461E", "", "", "6RU839461E_BU");
        else if(orig_art.equals("5N0837205M")) res = SelectMinPrice("5N0837205M", "", "", "5N0837885H_5BU");
        else if(orig_art.equals("6RU867212E")) res = SelectMinPrice("6RU867212E", "", "", "");
        else if(orig_art.equals("6RU839462E")) res = SelectMinPrice("6RU839462E", "", "", "6RU839462E_BU");
        else if(orig_art.equals("5N0837205M")) res = SelectMinPrice("5N0837205M", "", "", "5N0837205MGRU_BU");
        else if(orig_art.equals("6RU845201A")) res = SelectMinPrice("6RU845201A", "", "", "6RU845201A_BU");
        else if(orig_art.equals("6RU867011G")) res = SelectMinPrice("6RU867011G", "", "", "6RU867011G_1BU");
        else if(orig_art.equals("6RU837461M")) res = SelectMinPrice("6RU837461M", "PWR1042L", "", "");
        else if(orig_art.equals("5N0837205M")) res = SelectMinPrice("5N0837205M", "", "", "5N0837885H_5BU");
        else if(orig_art.equals("6RU845202A")) res = SelectMinPrice("6RU845202A", "", "", "6RU845202A_BU");
        else if(orig_art.equals("6RU867012G")) res = SelectMinPrice("6RU867012G", "", "", "6RU867012G_BU");
        else if(orig_art.equals("6RU837462M")) res = SelectMinPrice("6RU837462M", "", "", "6RU837462M_BU");
        else if(orig_art.equals("5N0837205M")) res = SelectMinPrice("5N0837205M", "", "", "5N0837205MGRU_BU");
        else if(orig_art.equals("6RF827301A")) res = SelectMinPrice("6RF827301A", "", "", "6RF827301A_BU");
        else if(orig_art.equals("6RF827302A")) res = SelectMinPrice("6RF827302A", "", "", "6RF827302A_BU");
        else if(orig_art.equals("6RU827705B")) res = SelectMinPrice("6RU827705B", "", "", "6RU827705B_BU");
        else if(orig_art.equals("6RU867605")) res = SelectMinPrice("6RU867605", "", "", "6RU86760582V_BU");
        else if(orig_art.equals("6RU831055J")) res = SelectMinPrice("6RU831055J", "STVWP60402", "", "");
        else if(orig_art.equals("6RU831056J")) res = SelectMinPrice("6RU831056J", "", "VWL0130101R_D_5", "");
        else if(orig_art.equals("6RU833055D")) res = SelectMinPrice("6RU833055D", "STVWP60412", "", "");
        else if(orig_art.equals("6RU833056D")) res = SelectMinPrice("6RU833056D", "AW6RU08330056D", "", "");
        else if(orig_art.equals("6RU809957")) res = SelectMinPrice("6RU809957", "VWL055020700L", "VWL055020700L_D_10", "");
        else if(orig_art.equals("6RU809958")) res = SelectMinPrice("6RU809958", "VWL055020700R", "VWL055020700R_D_30", "");
        return res;
    }
    public static String SubDetailMinPriceArticle(String orig_art){
        String res = "";
        if(orig_art.equals("6RU809957")) res = SelectMinPriceArticle("6RU809957", "VWL055020700L", "VWL055020700L_D_10", "");
        else if(orig_art.equals("6RU821111")) res = SelectMinPriceArticle("6RU821111", "", "", "6RU821111_BU");
        else if(orig_art.equals("6RU821112")) res = SelectMinPriceArticle("6RU821112", "", "", "6RU821112_BU");
        else if(orig_art.equals("6RU809958")) res = SelectMinPriceArticle("6RU809958", "VWL055020700R", "VWL055020700R_D_30", "");
        else if(orig_art.equals("6R0823301B")) res = SelectMinPriceArticle("6R0823301B", "VWL0031044L", "", "6R0823301A_BU");
        else if(orig_art.equals("6R0823302B")) res = SelectMinPriceArticle("6R0823302B", "VWL0031044R", "", "6R0823302A_BU");
        else if(orig_art.equals("6RU863831")) res = SelectMinPriceArticle("6RU863831", "", "", "");
        else if(orig_art.equals("6RU805915A")) res = SelectMinPriceArticle("6RU805915A", "L053015700", "", "");
        else if(orig_art.equals("6C0807723A")) res = SelectMinPriceArticle("6C0807723A", "VWL0109004L", "", "");
        else if(orig_art.equals("6C0807724A")) res = SelectMinPriceArticle("6C0807724A", "VWL0109004R", "", "");
        else if(orig_art.equals("6RU853671D")) res = SelectMinPriceArticle("6RU853671D", "VWL05B011700", "VWL05B011700_D_30", "");
        else if(orig_art.equals("6RU853651D")) res = SelectMinPriceArticle("6RU853651D", "VWL05B011100", "VWL05B011100_D_30", "");
        else if(orig_art.equals("6RU854661D")) res = SelectMinPriceArticle("6RU854661D", "VWL05B013301L", "", "");
        else if(orig_art.equals("6RU854662D")) res = SelectMinPriceArticle("6RU854662D", "VWL05B013301R", "VWL05B013300R_D_20", "");
        else if(orig_art.equals("6R0941061D")) res = SelectMinPriceArticle("6R0941061D", "VWL05B010300L", "", "");
        else if(orig_art.equals("6R0941062D")) res = SelectMinPriceArticle("6R0941062D", "VWL05B010300R", "", "");
        else if(orig_art.equals("6RU898393B")) res = SelectMinPriceArticle("6RU898393B", "", "", "");
        else if(orig_art.equals("6RU898393B")) res = SelectMinPriceArticle("6RU898393B", "", "", "");
        else if(orig_art.equals("6RF853841A")) res = SelectMinPriceArticle("6RF853841A", "VWL0109006L", "", "");
        else if(orig_art.equals("6RF853842A")) res = SelectMinPriceArticle("6RF853842A", "VWL0109006R", "", "");
        else if(orig_art.equals("6RF945105")) res = SelectMinPriceArticle("6RF945105", "VWL05B010700L", "", "");
        else if(orig_art.equals("6RF945106")) res = SelectMinPriceArticle("6RF945106", "VWL05B010700R", "", "");
        else if(orig_art.equals("6RF853835A")) res = SelectMinPriceArticle("6RF853835A", "VWL00109005", "", "");
        else if(orig_art.equals("6RU945095M")) res = SelectMinPriceArticle("6RU945095M", "ST44119DDL", "", "");
        else if(orig_art.equals("6RU810971A")) res = SelectMinPriceArticle("6RU810971A", "VWL031048RL", "", "");
        else if(orig_art.equals("6RU945096K")) res = SelectMinPriceArticle("6RU945096K", "ST44119DDR", "", "");
        else if(orig_art.equals("6RU809857")) res = SelectMinPriceArticle("6RU809857", "STVWP6064D0", "", "");
        else if(orig_art.equals("6RU810972A")) res = SelectMinPriceArticle("6RU810972A", "VWL031048RR", "", "");
        else if(orig_art.equals("6RU867211E")) res = SelectMinPriceArticle("6RU867211E", "", "", "");
        else if(orig_art.equals("6RU839461E")) res = SelectMinPriceArticle("6RU839461E", "", "", "6RU839461E_BU");
        else if(orig_art.equals("5N0837205M")) res = SelectMinPriceArticle("5N0837205M", "", "", "5N0837885H_5BU");
        else if(orig_art.equals("6RU867212E")) res = SelectMinPriceArticle("6RU867212E", "", "", "");
        else if(orig_art.equals("6RU839462E")) res = SelectMinPriceArticle("6RU839462E", "", "", "6RU839462E_BU");
        else if(orig_art.equals("5N0837205M")) res = SelectMinPriceArticle("5N0837205M", "", "", "5N0837205MGRU_BU");
        else if(orig_art.equals("6RU845201A")) res = SelectMinPriceArticle("6RU845201A", "", "", "6RU845201A_BU");
        else if(orig_art.equals("6RU867011G")) res = SelectMinPriceArticle("6RU867011G", "", "", "6RU867011G_1BU");
        else if(orig_art.equals("6RU837461M")) res = SelectMinPriceArticle("6RU837461M", "PWR1042L", "", "");
        else if(orig_art.equals("5N0837205M")) res = SelectMinPriceArticle("5N0837205M", "", "", "5N0837885H_5BU");
        else if(orig_art.equals("6RU845202A")) res = SelectMinPriceArticle("6RU845202A", "", "", "6RU845202A_BU");
        else if(orig_art.equals("6RU867012G")) res = SelectMinPriceArticle("6RU867012G", "", "", "6RU867012G_BU");
        else if(orig_art.equals("6RU837462M")) res = SelectMinPriceArticle("6RU837462M", "", "", "6RU837462M_BU");
        else if(orig_art.equals("5N0837205M")) res = SelectMinPriceArticle("5N0837205M", "", "", "5N0837205MGRU_BU");
        else if(orig_art.equals("6RF827301A")) res = SelectMinPriceArticle("6RF827301A", "", "", "6RF827301A_BU");
        else if(orig_art.equals("6RF827302A")) res = SelectMinPriceArticle("6RF827302A", "", "", "6RF827302A_BU");
        else if(orig_art.equals("6RU827705B")) res = SelectMinPriceArticle("6RU827705B", "", "", "6RU827705B_BU");
        else if(orig_art.equals("6RU867605")) res = SelectMinPriceArticle("6RU867605", "", "", "6RU86760582V_BU");
        else if(orig_art.equals("6RU831055J")) res = SelectMinPriceArticle("6RU831055J", "STVWP60402", "", "");
        else if(orig_art.equals("6RU831056J")) res = SelectMinPriceArticle("6RU831056J", "", "VWL0130101R_D_5", "");
        else if(orig_art.equals("6RU833055D")) res = SelectMinPriceArticle("6RU833055D", "STVWP60412", "", "");
        else if(orig_art.equals("6RU833056D")) res = SelectMinPriceArticle("6RU833056D", "AW6RU08330056D", "", "");
        else if(orig_art.equals("6RU809957")) res = SelectMinPriceArticle("6RU809957", "VWL055020700L", "VWL055020700L_D_10", "");
        else if(orig_art.equals("6RU809958")) res = SelectMinPriceArticle("6RU809958", "VWL055020700R", "VWL055020700R_D_30", "");
        return res;
    }
    public static int SelectMinPrice(String orig_art, String nonorig_art, String discont_art, String used_art){
        if(getPriceByArticleAvl(nonorig_art) > 0 ) return getPriceByArticleAvl(nonorig_art);
        else if(getPriceByArticleAvl(orig_art) > 0 ) return getPriceByArticleAvl(orig_art);
        else if(getPriceByArticleAvl(discont_art) > 0 ) return getPriceByArticleAvl(discont_art);
        else if(getPriceByArticleAvl(used_art) > 0 ) return getPriceByArticleAvl(used_art);
        return 0;
    }
    public static String SelectMinPriceArticle(String orig_art, String nonorig_art, String discont_art, String used_art){
        if(getPriceByArticleAvl(nonorig_art) > 0 ) return nonorig_art;
        else if(getPriceByArticleAvl(orig_art) > 0 ) return orig_art;
        else if(getPriceByArticleAvl(discont_art) > 0 ) return discont_art;
        else if(getPriceByArticleAvl(used_art) > 0 ) return used_art;
        return "";
    }

    public static ArrayList getSubDetailsArticles(String article){
        ArrayList<String> res = new ArrayList<>();
        if(article.equals("6RU821105C")){
            res.add(SubDetailMinPriceArticle("6RU809957")); // find minimum from SubDetails4Prices
            res.add(SubDetailMinPriceArticle("6RU821111"));
        }else if(article.equals("6RU821106C")){
            res.add(SubDetailMinPriceArticle("6RU821112"));
            res.add(SubDetailMinPriceArticle("6RU809958"));
        }else if(article.equals("6RU823031C")){
            res.add(SubDetailMinPriceArticle("6R0823301B"));
            res.add(SubDetailMinPriceArticle("6R0823302B"));
            res.add(SubDetailMinPriceArticle("6RU863831"));
        }else if(article.equals("6RU807221A")){
            res.add(SubDetailMinPriceArticle("6RU805915A"));
            res.add(SubDetailMinPriceArticle("6C0807723A"));
            res.add(SubDetailMinPriceArticle("6C0807724A"));
            res.add(SubDetailMinPriceArticle("6RU853671D"));
            res.add(SubDetailMinPriceArticle("6RU853651D"));
            res.add(SubDetailMinPriceArticle("6RU854661D"));
            res.add(SubDetailMinPriceArticle("6RU854662D"));
            res.add(SubDetailMinPriceArticle("6R0941061D"));
            res.add(SubDetailMinPriceArticle("6R0941062D"));
        }else if(article.equals("6RU807421D")){
            res.add(SubDetailMinPriceArticle("6RU898393B"));
            res.add(SubDetailMinPriceArticle("6RU898393B"));
            res.add(SubDetailMinPriceArticle("6RF853841A"));
            res.add(SubDetailMinPriceArticle("6RF853842A"));
            res.add(SubDetailMinPriceArticle("6RF945105"));
            res.add(SubDetailMinPriceArticle("6RF945106"));
            res.add(SubDetailMinPriceArticle("6RF853835A"));
        }else if(article.equals("6RU809605А")){
            res.add(SubDetailMinPriceArticle("6RU945095M"));
            res.add(SubDetailMinPriceArticle("6RU810971A"));
        }else if(article.equals("6RU809606A")){
            res.add(SubDetailMinPriceArticle("6RU945096K"));
            res.add(SubDetailMinPriceArticle("6RU809857"));
            res.add(SubDetailMinPriceArticle("6RU810972A"));
        }else if(article.equals("6RU833055D")){
            res.add(SubDetailMinPriceArticle("6RU867211E"));
            res.add(SubDetailMinPriceArticle("6RU839461E"));
            res.add(SubDetailMinPriceArticle("5N0837205M"));
        }else if(article.equals("6RU833056D")){
            res.add(SubDetailMinPriceArticle("6RU867212E"));
            res.add(SubDetailMinPriceArticle("6RU839462E"));
            res.add(SubDetailMinPriceArticle("5N0837205M"));
        }else if(article.equals("6RU831055J")){
            res.add(SubDetailMinPriceArticle("6RU845201A"));
            res.add(SubDetailMinPriceArticle("6RU867011G"));
            res.add(SubDetailMinPriceArticle("6RU837461M"));
            res.add(SubDetailMinPriceArticle("5N0837205M"));
        }else if(article.equals("6RU831056J")){
            res.add(SubDetailMinPriceArticle("6RU845202A"));
            res.add(SubDetailMinPriceArticle("6RU867012G"));
            res.add(SubDetailMinPriceArticle("6RU837462M"));
            res.add(SubDetailMinPriceArticle("5N0837205M"));
        }else if(article.equals("6RU827025F")){
            res.add(SubDetailMinPriceArticle("6RF827301A"));
            res.add(SubDetailMinPriceArticle("6RF827302A"));
            res.add(SubDetailMinPriceArticle("6RU827705B"));
            res.add(SubDetailMinPriceArticle("6RU867605"));
        }else if(article.equals("6RU809219")){
            res.add(SubDetailMinPriceArticle("6RU831055J"));
            res.add(SubDetailMinPriceArticle("6RU833055D"));
            res.add(SubDetailMinPriceArticle("6RU833056D"));
        }else if(article.equals("6R4810609A")){
            res.add(SubDetailMinPriceArticle("6RU831055J"));
            res.add(SubDetailMinPriceArticle("6RU833055D"));
            res.add(SubDetailMinPriceArticle("6RU833056D"));
        }else if(article.equals("6R4810609A")){
            res.add(SubDetailMinPriceArticle("6RU831055J"));
            res.add(SubDetailMinPriceArticle("6RU833055D"));
            res.add(SubDetailMinPriceArticle("6RU833056D"));
        }else if(article.equals("6RU809220")){
            res.add(SubDetailMinPriceArticle("6RU809957"));
            res.add(SubDetailMinPriceArticle("6RU809958"));
            res.add(SubDetailMinPriceArticle("6RU831056J"));
        }else if(article.equals("6R4810610A")){
            res.add(SubDetailMinPriceArticle("6RU809957"));
            res.add(SubDetailMinPriceArticle("6RU809958"));
            res.add(SubDetailMinPriceArticle("6RU831056J"));
        }else if(article.equals("6RU809696A")){
            res.add(SubDetailMinPriceArticle("6RU809957"));
            res.add(SubDetailMinPriceArticle("6RU809958"));
            res.add(SubDetailMinPriceArticle("6RU831056J"));
        }

        return res;
    }

    public static int getPriceByArticleAvl(String article){
        int res = 0;
        if(article.equals("6RU809957")) res = 2500;
        else if(article.equals("6RU821111")) res = 0;
        else if(article.equals("6RU821112")) res = 0;
        else if(article.equals("6RU809958")) res = 25000;
        else if(article.equals("6R0823301B")) res = 6055;
        else if(article.equals("6R0823302B")) res = 0;
        else if(article.equals("6RU863831")) res = 8643;
        else if(article.equals("6RU805915A")) res = 8500;
        else if(article.equals("6C0807723A")) res = 4800;
        else if(article.equals("6C0807724A")) res = 5200;
        else if(article.equals("6RU853671D")) res = 4800;
        else if(article.equals("6RU853651D")) res = 5950;
        else if(article.equals("6RU854661D")) res = 3100;
        else if(article.equals("6RU854662D")) res = 3100;
        else if(article.equals("6R0941061D")) res = 3950;
        else if(article.equals("6R0941062D")) res = 3950;
        else if(article.equals("6RU898393B")) res = 7600;
        else if(article.equals("6RU898393B")) res = 7600;
        else if(article.equals("6RF853841A")) res = 2100;
        else if(article.equals("6RF853842A")) res = 2100;
        else if(article.equals("6RF945105")) res = 1500;
        else if(article.equals("6RF945106")) res = 1500;
        else if(article.equals("6RF853835A")) res = 3800;
        else if(article.equals("6RU945095M")) res = 6800;
        else if(article.equals("6RU810971A")) res = 3200;
        else if(article.equals("6RU945096K")) res = 7200;
        else if(article.equals("6RU809857")) res = 3980;
        else if(article.equals("6RU810972A")) res = 3300;
        else if(article.equals("6RU867211E")) res = 8500;
        else if(article.equals("6RU839461E")) res = 4800;
        else if(article.equals("5N0837205M")) res = 6300;
        else if(article.equals("6RU867212E")) res = 8500;
        else if(article.equals("6RU839462E")) res = 5200;
        else if(article.equals("5N0837205M")) res = 5900;
        else if(article.equals("6RU845201A")) res = 6500;
        else if(article.equals("6RU867011G")) res = 8500;
        else if(article.equals("6RU837461M")) res = 5200;
        else if(article.equals("5N0837205M")) res = 6300;
        else if(article.equals("6RU845202A")) res = 11000;
        else if(article.equals("6RU867012G")) res = 9000;
        else if(article.equals("6RU837462M")) res = 4800;
        else if(article.equals("5N0837205M")) res = 5900;
        else if(article.equals("6RF827301A")) res = 10000;
        else if(article.equals("6RF827302A")) res = 10000;
        else if(article.equals("6RU827705B")) res = 13500;
        else if(article.equals("6RU867605")) res = 4900;
        else if(article.equals("6RU831055J")) res = 19500;
        else if(article.equals("6RU831056J")) res = 24000;
        else if(article.equals("6RU833055D")) res = 21000;
        else if(article.equals("6RU833056D")) res = 23000;
        else if(article.equals("6RU809957")) res = 2500;
        else if(article.equals("6RU809958")) res = 25000;
        else if(article.equals("VWL055020700L")) res = 650;
        else if(article.equals("VWL055020700R")) res = 800;
        else if(article.equals("VWL0031044L")) res = 450;
        else if(article.equals("VWL0031044R")) res = 450;
        else if(article.equals("L053015700")) res = 1500;
        else if(article.equals("VWL0109004L")) res = 1070;
        else if(article.equals("VWL0109004R")) res = 1070;
        else if(article.equals("VWL05B011700")) res = 1700;
        else if(article.equals("VWL05B011100")) res = 2700;
        else if(article.equals("VWL05B013301L")) res = 700;
        else if(article.equals("VWL05B013301R")) res = 600;
        else if(article.equals("VWL05B010300L")) res = 1400;
        else if(article.equals("VWL05B010300R")) res = 1400;
        else if(article.equals("VWL0109006L")) res = 800;
        else if(article.equals("VWL0109006R")) res = 800;
        else if(article.equals("VWL05B010700L")) res = 450;
        else if(article.equals("VWL05B010700R")) res = 500;
        else if(article.equals("VWL00109005")) res = 1700;
        else if(article.equals("ST44119DDL")) res = 2900;
        else if(article.equals("VWL031048RL")) res = 700;
        else if(article.equals("ST44119DDR")) res = 3300;
        else if(article.equals("STVWP6064D0")) res = 1500;
        else if(article.equals("VWL031048RR")) res = 700;
        else if(article.equals("PWR1042L")) res = 3900;
        else if(article.equals("STVWP60402")) res = 13500;
        else if(article.equals("STVWP60412")) res = 14000;
        else if(article.equals("AW6RU08330056D")) res = 18000;
        else if(article.equals("VWL055020700L")) res = 650;
        else if(article.equals("VWL055020700R")) res = 800;
        else if(article.equals("VWL055020700L_D_10")) res = 414;
        else if(article.equals("VWL055020700R_D_30")) res = 413;
        else if(article.equals("VWL05B011700_D_30")) res = 945;
        else if(article.equals("VWL05B011100_D_30")) res = 1500;
        else if(article.equals("VWL05B013300R_D_20")) res = 300;
        else if(article.equals("VWL055020700L_D_10")) res = 414;
        else if(article.equals("VWL055020700R_D_30")) res = 413;
        else if(article.equals("VWL0130101R_D_5")) res = 11500;
        else if(article.equals("6RU821111_BU")) res = 1200;
        else if(article.equals("6RU821112_BU")) res = 1200;
        else if(article.equals("6R0823301A_BU")) res = 1000;
        else if(article.equals("6R0823302A_BU")) res = 1000;
        else if(article.equals("6RU839461E_BU")) res = 1500;
        else if(article.equals("5N0837885H_5BU")) res = 2500;
        else if(article.equals("6RU839462E_BU")) res = 1800;
        else if(article.equals("5N0837205MGRU_BU")) res = 1700;
        else if(article.equals("6RU845201A_BU")) res = 6000;
        else if(article.equals("6RU867011G_1BU")) res = 5900;
        else if(article.equals("5N0837885H_5BU")) res = 2500;
        else if(article.equals("6RU845202A_BU")) res = 10000;
        else if(article.equals("6RU867012G_BU")) res = 7500;
        else if(article.equals("6RU837462M_BU")) res = 1500;
        else if(article.equals("5N0837205MGRU_BU")) res = 1700;
        else if(article.equals("6RF827301A_BU")) res = 2800;
        else if(article.equals("6RF827302A_BU")) res = 1500;
        else if(article.equals("6RU827705B_BU")) res = 5600;
        else if(article.equals("6RU86760582V_BU")) res = 800;
        return res;
    }

    public static String getSupplyDetailTitle(String article){
        String res = "";
        if(article.equals("6RU809957") || article.equals("VWL055020700L") || article.equals("VWL055020700L_D_10") ) res = "подкрылок пер лев";
        else if(article.equals("6RU821111") || article.equals("6RU821111_BU")) res = "пыльник крыла лев";
        else if(article.equals("6RU821112") || article.equals("6RU821112_BU")) res = "пыльник крыла прав";
        else if(article.equals("6RU809958") || article.equals("VWL055020700R") || article.equals("VWL055020700R_D_30") ) res = "подкрылок пер прав";
        else if(article.equals("6R0823301B") || article.equals("VWL0031044L") || article.equals("6R0823301A_BU")) res = "петля капота лев";
        else if(article.equals("6R0823302B") || article.equals("VWL0031044R") || article.equals("6R0823302A_BU")) res = "петля капота прав";
        else if(article.equals("6RU863831") ) res = "шумоизоляция капота";
        else if(article.equals("6RU805915A") || article.equals("L053015700") ) res = "спойлер пер бампера";
        else if(article.equals("6C0807723A") || article.equals("VWL0109004L") ) res = "Кронштейн переднего бампера левый под ПТФ";
        else if(article.equals("6C0807724A") || article.equals("VWL0109004R") ) res = "Кронштейн переднего бампера правый под ПТФ";
        else if(article.equals("6RU853671D") || article.equals("VWL05B011700") || article.equals("VWL05B011700_D_30") ) res = "решетка бампера";
        else if(article.equals("6RU853651D") || article.equals("VWL05B011100") || article.equals("VWL05B011100_D_30") ) res = "решетка радиатора";
        else if(article.equals("6RU854661D") || article.equals("VWL05B013301L") ) res = "решетка под ПТФ лев";
        else if(article.equals("6RU854662D") || article.equals("VWL05B013301R") || article.equals("VWL05B013300R_D_20") ) res = "решетка под ПТФ прав";
        else if(article.equals("6R0941061D") || article.equals("VWL05B010300L") ) res = "фара противотуманная лев";
        else if(article.equals("6R0941062D") || article.equals("VWL05B010300R") ) res = "фара противотуманная прав";
        else if(article.equals("6RU898393B") ) res = "Кронштейн зад бампера левый";
        else if(article.equals("6RU898393B") ) res = "Кронштейн зад бампера прав";
        else if(article.equals("6RF853841A") || article.equals("VWL0109006L") ) res = "молдинг зад бампера лев";
        else if(article.equals("6RF853842A") || article.equals("VWL0109006R") ) res = "молдинг зад бампера прав";
        else if(article.equals("6RF945105") || article.equals("VWL05B010700L") ) res = "катафот лев";
        else if(article.equals("6RF945106") || article.equals("VWL05B010700R") ) res = "катафот прав";
        else if(article.equals("6RF853835A") || article.equals("VWL00109005") ) res = "молдинг зад бампера центр";
        else if(article.equals("6RU945095M") || article.equals("ST44119DDL") ) res = "фонарь зад лев";
        else if(article.equals("6RU810971A") || article.equals("VWL031048RL") ) res = "подкрылок зад лев";
        else if(article.equals("6RU945096K") || article.equals("ST44119DDR") ) res = "фонарь зад прав";
        else if(article.equals("6RU809857") || article.equals("STVWP6064D0") ) res = "лючок бензобака";
        else if(article.equals("6RU810972A") || article.equals("VWL031048RR") ) res = "подкрылок зад прав";
        else if(article.equals("6RU867211E") ) res = "обшивка двери зад лев";
        else if(article.equals("6RU839461E") || article.equals("6RU839461E_BU")) res = "стеклоподъемник зад лев";
        else if(article.equals("5N0837205M") || article.equals("5N0837885H_5BU")) res = "ручка двери зад лев внешняя";
        else if(article.equals("6RU867212E") ) res = "обшивка двери зад прав";
        else if(article.equals("6RU839462E") || article.equals("6RU839462E_BU")) res = "стеклоподъемник зад прав";
        else if(article.equals("5N0837205M") || article.equals("5N0837205MGRU_BU")) res = "ручка двери зад прав внешняя";
        else if(article.equals("6RU845201A") || article.equals("6RU845201A_BU")) res = "стекло двери перед лев";
        else if(article.equals("6RU867011G") || article.equals("6RU867011G_1BU")) res = "обшивка двери перед лев";
        else if(article.equals("6RU837461M") || article.equals("PWR1042L") ) res = "стеклоподъемник перед лев";
        else if(article.equals("5N0837205M") || article.equals("5N0837885H_5BU")) res = "ручка двери перед лев внешняя";
        else if(article.equals("6RU845202A") || article.equals("6RU845202A_BU")) res = "стекло двери перед прав";
        else if(article.equals("6RU867012G") || article.equals("6RU867012G_BU")) res = "обшивка двери перед прав";
        else if(article.equals("6RU837462M") || article.equals("6RU837462M_BU")) res = "стеклоподъемник перед прав";
        else if(article.equals("5N0837205M") || article.equals("5N0837205MGRU_BU")) res = "ручка двери перед прав внешняя";
        else if(article.equals("6RF827301A") || article.equals("6RF827301A_BU")) res = "петля крышки багажника лев";
        else if(article.equals("6RF827302A") || article.equals("6RF827302A_BU")) res = "петля крышки багажника прав";
        else if(article.equals("6RU827705B") || article.equals("6RU827705B_BU")) res = "уплотнитель";
        else if(article.equals("6RU867605") || article.equals("6RU86760582V_BU")) res = "обшивка крышки баг ";
        else if(article.equals("6RU831055J") || article.equals("STVWP60402") ) res = "дверь перед лев";
        else if(article.equals("6RU831056J") || article.equals("VWL0130101R_D_5") ) res = "дверь перед прав";
        else if(article.equals("6RU833055D") || article.equals("STVWP60412") ) res = "дверь зад лев";
        else if(article.equals("6RU833056D") || article.equals("AW6RU08330056D") ) res = "дверь зад прав";
        else if(article.equals("6RU809957") || article.equals("VWL055020700L") || article.equals("VWL055020700L_D_10") ) res = "подкрылок пер лев";
        else if(article.equals("6RU809958") || article.equals("VWL055020700R") || article.equals("VWL055020700R_D_30") ) res = "подкрылок пер прав";
        return res;
    }

    public static double getHoursDetailRepair(String article){
        double res = 0;
        if(article.equals("6RU821105C")) res = 0.5;
        if(article.equals("6RU821106C")) res = 0.5;
        if(article.equals("6RU823031C")) res = 0.5;
        if(article.equals("6RU807221A")) res = 1;
        if(article.equals("6RU807421D")) res = 1;
        if(article.equals("6RU809605А")) res = 1.5;
        if(article.equals("6RU809606A")) res = 1.5;
        if(article.equals("6RU833055D")) res = 0.5;
        if(article.equals("6RU833056D")) res = 0.5;
        if(article.equals("6RU831055J")) res = 0.5;
        if(article.equals("6RU831056J")) res = 0.5;
        if(article.equals("6RU827025F")) res = 0.5;
        if(article.equals("6RU809219")) res = 1.5;
        if(article.equals("6R4810609A")) res = 1.5;
        if(article.equals("6R4810609A")) res = 1.5;
        if(article.equals("6RU809220")) res = 1.5;
        if(article.equals("6R4810610A")) res = 1.5;
        if(article.equals("6RU809696A")) res = 1.5;
        return res;
    }

    public static double getHoursSubDetailsRepair(String article){
        double res = 0;
        if(article.equals("6RU821105C")) res = 1 ;
        if(article.equals("6RU821106C")) res = 1 ;
        if(article.equals("6RU823031C")) res = 1 ;
        if(article.equals("6RU807221A")) res = 1.5 ;
        if(article.equals("6RU807421D")) res = 1 ;
        if(article.equals("6RU809605А")) res = 3 ;
        if(article.equals("6RU809606A")) res = 3 ;
        if(article.equals("6RU833055D")) res = 2 ;
        if(article.equals("6RU833056D")) res = 2 ;
        if(article.equals("6RU831055J")) res = 2 ;
        if(article.equals("6RU831056J")) res = 2 ;
        if(article.equals("6RU827025F")) res = 0.5 ;
        if(article.equals("6RU809219")) res = 2 ;
        if(article.equals("6R4810609A")) res = 2 ;
        if(article.equals("6R4810609A")) res = 2 ;
        if(article.equals("6RU809220")) res = 2 ;
        if(article.equals("6R4810610A")) res = 2 ;
        if(article.equals("6RU809696A")) res = 2 ;
        return res;
    }

    public static void setMenuItems(MenuItem item, Context context){
        switch (item.getItemId()) {
            case R.id.nav_home:
                context.startActivity(new Intent(context, MainActivity.class));
                break;
            case R.id.nav_cart:
                context.startActivity(new Intent(context, CartActivity.class));
                break;
            case R.id.nav_details:
                context.startActivity(new Intent(context, SmetaActivity.class));
                break;
            case R.id.nav_giude:
                context.startActivity(new Intent(context, RegActivity.class));
                break;
        }
    }

    public static void orderAddItem(JSONArray arItems, String title, int price, int quant, int amount){
        JSONObject det = new JSONObject();
        try {
            det.put("Name", title);
            det.put("Price", String.valueOf(price*100));
            det.put("Quantity", String.valueOf( quant));
            det.put("Amount", String.valueOf(price*100*quant));
            det.put("Tax", "vat20");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        arItems.put(det);
    }

/*
    DataStore(){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public static SharedPreferences.Editor getSharedPreferencesEditor(){
        return editor;
    }
    public static void setSharedValueInt(String name, int value){
        getSharedPreferencesEditor().putInt(name, value).commit();
    }

    public static void setSharedValueStr(String name, String value){
        getSharedPreferencesEditor().putString(name, value).commit();
    }

    public static int getSharedValueInt(String name){

        return sharedPreferences.getInt(name, 0);
    }

    public static String getSharedValueStr(String name){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        return sharedPreferences.getString(name, "");
    }
*/

    public static String getTestJsonAiApi(){
        String out = "{\"mask\":[{\"Part_Name\":\"Large parts-Wheel\",\"Part_Name_rus\":\"\\u041a\\u043e\\u043b\\u0435\\u0441\\u043e\",\"Article\":\"6Q0601027AC\",\"Points\":[]},{\"Part_Name\":\"Large parts-Front left door\",\"Part_Name_rus\":\"\\u0414\\u0432\\u0435\\u0440\\u044c \\u043f\\u0435\\u0440\\u0435\\u0434\\u043d\\u044f\\u044f \\u043b\\u0435\\u0432\\u0430\\u044f\",\"Article\":\"6RU831055J\",\"Points\":[]},{\"Part_Name\":\"Large parts-Front left wing\",\"Part_Name_rus\":\"\\u041a\\u0440\\u044b\\u043b\\u043e \\u043f\\u0435\\u0440\\u0435\\u0434\\u043d\\u0435\\u0435 \\u043b\\u0435\\u0432\\u043e\\u0435\",\"Article\":\"6RU821105C\",\"Points\":[]},{\"Part_Name\":\"Large parts-Front left door\",\"Part_Name_rus\":\"\\u0414\\u0432\\u0435\\u0440\\u044c \\u043f\\u0435\\u0440\\u0435\\u0434\\u043d\\u044f\\u044f \\u043b\\u0435\\u0432\\u0430\\u044f\",\"Article\":\"6RU831055J\",\"Points\":[]},{\"Part_Name\":\"Large parts-Rear left wing\",\"Part_Name_rus\":\"\\u041a\\u0440\\u044b\\u043b\\u043e \\u0437\\u0430\\u0434\\u043d\\u0435\\u0435 \\u043b\\u0435\\u0432\\u043e\\u0435\",\"Article\":\"6RU809605A\",\"Points\":[]},{\"Part_Name\":\"Large parts-Wheel\",\"Part_Name_rus\":\"\\u041a\\u043e\\u043b\\u0435\\u0441\\u043e\",\"Article\":\"6Q0601027AC\",\"Points\":[]}],\"settings\":{\"main\":{\"cache\":\"aiapi_23_10_24___23_41_59.json\",\"file_size\":6822,\"view\":\"front_left\"}}}";
        return out;
    }
}
