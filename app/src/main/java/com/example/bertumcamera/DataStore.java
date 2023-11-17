package com.example.bertumcamera;

import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;


public class DataStore {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor ed;

    public static int getSumRepairsWork(String article){
        int res = 0;
        int pricePerHour = 1200;
        if(article.equals("6RU821105C")) res = (int) ((0.5 + 1)*pricePerHour
                + getSupplyDetailPrice("6RU809957")
                + getSupplyDetailPrice("6RU821111"));
        if(article.equals("6RU821106C")) res = (int) ((0.5 + 1)*pricePerHour
                + getSupplyDetailPrice("6RU821112")
                + getSupplyDetailPrice("6RU809958"));
        if(article.equals("6RU823031C")) res = (int) ((0.5 + 1)*pricePerHour
                + getSupplyDetailPrice("6R0823301B")
                + getSupplyDetailPrice("6R0823302B")
                + getSupplyDetailPrice("6RU863831"));
        if(article.equals("6RU807221A")) res = (int) ((1 + 1.5)*pricePerHour
                + getSupplyDetailPrice("6RU805915A")
                + getSupplyDetailPrice("6C0807723A")
                + getSupplyDetailPrice("6C0807724A")
                + getSupplyDetailPrice("6RU853671D")
                + getSupplyDetailPrice("6RU853651D")
                + getSupplyDetailPrice("6RU854661D")
                + getSupplyDetailPrice("6RU854662D")
                + getSupplyDetailPrice("6R0941061D")
                + getSupplyDetailPrice("6R0941062D"));
        if(article.equals("6RU807421D")) res = (int) ((1 + 1)*pricePerHour
                + getSupplyDetailPrice("6RU898393B")
                + getSupplyDetailPrice("6RU898393B")
                + getSupplyDetailPrice("6RF853841A")
                + getSupplyDetailPrice("6RF853842A")
                + getSupplyDetailPrice("6RF945105")
                + getSupplyDetailPrice("6RF945106")
                + getSupplyDetailPrice("6RF853835A"));
        if(article.equals("6RU809605А")) res = (int) ((1.5 + 3)*pricePerHour
                + getSupplyDetailPrice("6RU945095M")
                + getSupplyDetailPrice("6RU810971A"));
        if(article.equals("6RU809606A")) res = (int) ((1.5 + 3)*pricePerHour
                + getSupplyDetailPrice("6RU945096K")
                + getSupplyDetailPrice("6RU809857")
                + getSupplyDetailPrice("6RU810972A"));
        if(article.equals("6RU833055D")) res = (int) ((0.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU867211E")
                + getSupplyDetailPrice("6RU839461E")
                + getSupplyDetailPrice("5N0837205M"));
        if(article.equals("6RU833056D")) res = (int) ((0.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU867212E")
                + getSupplyDetailPrice("6RU839462E")
                + getSupplyDetailPrice("5N0837205M"));
        if(article.equals("6RU831055J")) res = (int) ((0.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU845201A")
                + getSupplyDetailPrice("6RU867011G")
                + getSupplyDetailPrice("6RU837461M")
                + getSupplyDetailPrice("5N0837205M"));
        if(article.equals("6RU831056J")) res = (int) ((0.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU845202A")
                + getSupplyDetailPrice("6RU867012G")
                + getSupplyDetailPrice("6RU837462M")
                + getSupplyDetailPrice("5N0837205M"));
        if(article.equals("6RU827025F")) res = (int) ((0.5 + 0.5)*pricePerHour
                + getSupplyDetailPrice("6RF827301A")
                + getSupplyDetailPrice("6RF827302A")
                + getSupplyDetailPrice("6RU827705B")
                + getSupplyDetailPrice("6RU867605"));
        if(article.equals("6RU809219")) res = (int) ((1.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU831055J")
                + getSupplyDetailPrice("6RU833055D")
                + getSupplyDetailPrice("6RU833056D"));
        if(article.equals("6R4810609A")) res = (int) ((1.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU831055J")
                + getSupplyDetailPrice("6RU833055D")
                + getSupplyDetailPrice("6RU833056D"));
        if(article.equals("6R4810609A")) res = (int) ((1.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU831055J")
                + getSupplyDetailPrice("6RU833055D")
                + getSupplyDetailPrice("6RU833056D"));
        if(article.equals("6RU809220")) res = (int) ((1.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU809957")
                + getSupplyDetailPrice("6RU809958")
                + getSupplyDetailPrice("6RU831056J"));
        if(article.equals("6R4810610A")) res = (int) ((1.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU809957")
                + getSupplyDetailPrice("6RU809958")
                + getSupplyDetailPrice("6RU831056J"));
        if(article.equals("6RU809696A")) res = (int) ((1.5 + 2)*pricePerHour
                + getSupplyDetailPrice("6RU809957")
                + getSupplyDetailPrice("6RU809958")
                + getSupplyDetailPrice("6RU831056J"));
        return res;
    }


    public static ArrayList getSubDetailsArticles(String article){
        ArrayList<String> res = new ArrayList<>();
        if(article.equals("6RU821105C")){
            res.add("6RU809957");
            res.add("6RU821111");
        }else if(article.equals("6RU821106C")){
            res.add("6RU821112");
            res.add("6RU809958");
        }else if(article.equals("6RU823031C")){
            res.add("6R0823301B");
            res.add("6R0823302B");
            res.add("6RU863831");
        }else if(article.equals("6RU807221A")){
            res.add("6RU805915A");
            res.add("6C0807723A");
            res.add("6C0807724A");
            res.add("6RU853671D");
            res.add("6RU853651D");
            res.add("6RU854661D");
            res.add("6RU854662D");
            res.add("6R0941061D");
            res.add("6R0941062D");
        }else if(article.equals("6RU807421D")){
            res.add("6RU898393B");
            res.add("6RU898393B");
            res.add("6RF853841A");
            res.add("6RF853842A");
            res.add("6RF945105");
            res.add("6RF945106");
            res.add("6RF853835A");
        }else if(article.equals("6RU809605А")){
            res.add("6RU945095M");
            res.add("6RU810971A");
        }else if(article.equals("6RU809606A")){
            res.add("6RU945096K");
            res.add("6RU809857");
            res.add("6RU810972A");
        }else if(article.equals("6RU833055D")){
            res.add("6RU867211E");
            res.add("6RU839461E");
            res.add("5N0837205M");
        }else if(article.equals("6RU833056D")){
            res.add("6RU867212E");
            res.add("6RU839462E");
            res.add("5N0837205M");
        }else if(article.equals("6RU831055J")){
            res.add("6RU845201A");
            res.add("6RU867011G");
            res.add("6RU837461M");
            res.add("5N0837205M");
        }else if(article.equals("6RU831056J")){
            res.add("6RU845202A");
            res.add("6RU867012G");
            res.add("6RU837462M");
            res.add("5N0837205M");
        }else if(article.equals("6RU827025F")){
            res.add("6RF827301A");
            res.add("6RF827302A");
            res.add("6RU827705B");
            res.add("6RU867605");
        }else if(article.equals("6RU809219")){
            res.add("6RU831055J");
            res.add("6RU833055D");
            res.add("6RU833056D");
        }else if(article.equals("6R4810609A")){
            res.add("6RU831055J");
            res.add("6RU833055D");
            res.add("6RU833056D");
        }else if(article.equals("6R4810609A")){
            res.add("6RU831055J");
            res.add("6RU833055D");
            res.add("6RU833056D");
        }else if(article.equals("6RU809220")){
            res.add("6RU809957");
            res.add("6RU809958");
            res.add("6RU831056J");
        }else if(article.equals("6R4810610A")){
            res.add("6RU809957");
            res.add("6RU809958");
            res.add("6RU831056J");
        }else if(article.equals("6RU809696A")){
            res.add("6RU809957");
            res.add("6RU809958");
            res.add("6RU831056J");
        }

        return res;
    }

    public static int getSupplyDetailPrice(String article){
        int res = 0;
        if(article.equals("6RU809957")) res = 419;
        else if(article.equals("6RU821111")) res = 0;
        else if(article.equals("6RU821112")) res = 0;
        else if(article.equals("6RU809958")) res = 466;
        else if(article.equals("6R0823301B")) res = 0;
        else if(article.equals("6R0823302B")) res = 0;
        else if(article.equals("6RU863831")) res = 0;
        else if(article.equals("6RU805915A")) res = 0;
        else if(article.equals("6C0807723A")) res = 0;
        else if(article.equals("6C0807724A")) res = 0;
        else if(article.equals("6RU853671D")) res = 0;
        else if(article.equals("6RU853651D")) res = 1200;
        else if(article.equals("6RU854661D")) res = 0;
        else if(article.equals("6RU854662D")) res = 0;
        else if(article.equals("6R0941061D")) res = 0;
        else if(article.equals("6R0941062D")) res = 0;
        else if(article.equals("6RU898393B")) res = 0;
        else if(article.equals("6RF853841A")) res = 0;
        else if(article.equals("6RF853842A")) res = 0;
        else if(article.equals("6RF945105")) res = 497;
        else if(article.equals("6RF945106")) res = 497;
        else if(article.equals("6RF853835A")) res = 0;
        else if(article.equals("6RU945095M")) res = 2095;
        else if(article.equals("6RU810971A")) res = 0;
        else if(article.equals("6RU945096K")) res = 2592;
        else if(article.equals("6RU809857")) res = 0;
        else if(article.equals("6RU810972A")) res = 0;
        else if(article.equals("6RU867211E")) res = 0;
        else if(article.equals("6RU839461E")) res = 0;
        else if(article.equals("5N0837205M")) res = 0;
        else if(article.equals("6RU867212E")) res = 0;
        else if(article.equals("6RU839462E")) res = 0;
        else if(article.equals("6RU845201A")) res = 0;
        else if(article.equals("6RU867011G")) res = 0;
        else if(article.equals("6RU837461M")) res = 0;
        else if(article.equals("6RU845202A")) res = 0;
        else if(article.equals("6RU867012G")) res = 0;
        else if(article.equals("6RU837462M")) res = 0;
        else if(article.equals("6RF827301A")) res = 0;
        else if(article.equals("6RF827302A")) res = 0;
        else if(article.equals("6RU827705B")) res = 0;
        else if(article.equals("6RU867605")) res = 0;
        else if(article.equals("6RU831055J")) res = 15000;
        else if(article.equals("6RU833055D")) res = 0;
        else if(article.equals("6RU833056D")) res = 0;
        else if(article.equals("6RU831056J")) res = 11050;
        return res;
    }

    public static String getSupplyDetailTitle(String article){
        String res = "";
        if(article.equals("6RU809957")) res = "подкрылок пер лев";
        else if(article.equals("6RU821111")) res = "пыльник крыла лев";
        else if(article.equals("6RU821112")) res = "пыльник крыла прав";
        else if(article.equals("6RU809958")) res = "подкрылок пер прав";
        else if(article.equals("6R0823301B")) res = "петля капота лев";
        else if(article.equals("6R0823302B")) res = "петля капота прав";
        else if(article.equals("6RU863831")) res = "шумоизоляция капота";
        else if(article.equals("6RU805915A")) res = "спойлер пер бампера";
        else if(article.equals("6C0807723A")) res = "Кронштейн переднего бампера левый под ПТФ";
        else if(article.equals("6C0807724A")) res = "Кронштейн переднего бампера правый под ПТФ";
        else if(article.equals("6RU853671D")) res = "решетка бампера";
        else if(article.equals("6RU853651D")) res = "решетка радиатора";
        else if(article.equals("6RU854661D")) res = "решетка под ПТФ лев";
        else if(article.equals("6RU854662D")) res = "решетка под ПТФ прав";
        else if(article.equals("6R0941061D")) res = "фара противотуманная лев";
        else if(article.equals("6R0941062D")) res = "фара противотуманная прав";
        else if(article.equals("6RU898393B")) res = "Кронштейн зад бампера левый";
        else if(article.equals("6RU898393B")) res = "Кронштейн зад бампера прав";
        else if(article.equals("6RF853841A")) res = "молдинг зад бампера лев";
        else if(article.equals("6RF853842A")) res = "молдинг зад бампера прав";
        else if(article.equals("6RF945105")) res = "катафот лев";
        else if(article.equals("6RF945106")) res = "катафот прав";
        else if(article.equals("6RF853835A")) res = "молдинг зад бампера центр";
        else if(article.equals("6RU945095M")) res = "фонарь зад лев";
        else if(article.equals("6RU810971A")) res = "подкрылок зад лев";
        else if(article.equals("6RU945096K")) res = "фонарь зад прав";
        else if(article.equals("6RU809857")) res = "лючок бензобака";
        else if(article.equals("6RU810972A")) res = "подкрылок зад прав";
        else if(article.equals("6RU867211E")) res = "обшивка двери зад лев";
        else if(article.equals("6RU839461E")) res = "стеклоподъемник зад лев";
        else if(article.equals("5N0837205M")) res = "ручка двери зад лев внешняя";
        else if(article.equals("6RU867212E")) res = "обшивка двери зад прав";
        else if(article.equals("6RU839462E")) res = "стеклоподъемник зад прав";
        else if(article.equals("5N0837205M")) res = "ручка двери зад прав внешняя";
        else if(article.equals("6RU845201A")) res = "стекло двери перед лев";
        else if(article.equals("6RU867011G")) res = "обшивка двери перед лев";
        else if(article.equals("6RU837461M")) res = "стеклоподъемник перед лев";
        else if(article.equals("5N0837205M")) res = "ручка двери перед лев внешняя";
        else if(article.equals("6RU845202A")) res = "стекло двери перед прав";
        else if(article.equals("6RU867012G")) res = "обшивка двери перед прав";
        else if(article.equals("6RU837462M")) res = "стеклоподъемник перед прав";
        else if(article.equals("5N0837205M")) res = "ручка двери перед прав внешняя";
        else if(article.equals("6RF827301A")) res = "петля крышки багажника лев";
        else if(article.equals("6RF827302A")) res = "петля крышки багажника прав";
        else if(article.equals("6RU827705B")) res = "уплотнитель";
        else if(article.equals("6RU867605")) res = "обшивка крышки баг";
        else if(article.equals("6RU831055J")) res = "дверь перед лев";
        else if(article.equals("6RU833055D")) res = "дверь зад лев";
        else if(article.equals("6RU833056D")) res = "дверь зад прав";
        else if(article.equals("6RU809957")) res = "подкрылок пер лев";
        else if(article.equals("6RU809958")) res = "подкрылок пер прав";
        else if(article.equals("6RU831056J")) res = "дверь перед прав";
        return res;
    }


    public static double getHoursPriceRepairWork(String article){
        double res = 0;
        if(article.equals("6RU821105C")) res = getTotalHoursRepair("6RU821105C");
        if(article.equals("6RU821106C")) res = getTotalHoursRepair("6RU821106C");
        if(article.equals("6RU823031C")) res = getTotalHoursRepair("6RU823031C");
        if(article.equals("6RU807221A")) res = getTotalHoursRepair("6RU807221A");
        if(article.equals("6RU807421D")) res = getTotalHoursRepair("6RU807421D");
        if(article.equals("6RU809605А")) res = getTotalHoursRepair("6RU809605А");
        if(article.equals("6RU809606A")) res = getTotalHoursRepair("6RU809606A");
        if(article.equals("6RU833055D")) res = getTotalHoursRepair("6RU833055D");
        if(article.equals("6RU833056D")) res = getTotalHoursRepair("6RU833056D");
        if(article.equals("6RU831055J")) res = getTotalHoursRepair("6RU831055J");
        if(article.equals("6RU831056J")) res = getTotalHoursRepair("6RU831056J");
        if(article.equals("6RU827025F")) res = getTotalHoursRepair("6RU827025F");
        if(article.equals("6RU809219")) res = getTotalHoursRepair("6RU809219");
        if(article.equals("6R4810609A")) res = getTotalHoursRepair("6R4810609A");
        if(article.equals("6R4810609A")) res = getTotalHoursRepair("6R4810609A");
        if(article.equals("6RU809220")) res = getTotalHoursRepair("6RU809220");
        if(article.equals("6R4810610A")) res = getTotalHoursRepair("6R4810610A");
        if(article.equals("6RU809696A")) res = getTotalHoursRepair("6RU809696A");
        return res;
    }

    public static double getTotalHoursRepair(String article){
        return getHoursDetailRepair( article) + getHoursSubDetailsRepair(article);
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
/*
    DataStore(){
        sharedPreferences = getSharedPreferences(Const.SHARE_STORE,MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    private static SharedPreferences.Editor getSharedPreferencesEditor(){
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
