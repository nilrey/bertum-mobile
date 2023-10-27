package com.example.bertumcamera;

import android.content.SharedPreferences;

public class DataStore {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
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

/*
    public static String getDetailImageByCode(String engDetailCode){
        String res = "detail_noimage";
        if(engDetailCode.equals("Front bumper")){ res = "front_bumper";}
        if(engDetailCode.equals("Rear bumper")){ res = "rear_bumper";}
        if(engDetailCode.equals("Front left door")){ res = "front_left_door";}
        if(engDetailCode.equals("Front right door")){ res = "front_right_door";}
        if(engDetailCode.equals("Rear left door")){ res = "rear_left_door";}
        if(engDetailCode.equals("Rear right door")){ res = "rear_right_door";}
        if(engDetailCode.equals("Hood")){ res = "hood";}
        if(engDetailCode.equals("Wheel")){ res = "wheel";}
        if(engDetailCode.equals("Front left wing")){ res = "front_left_wing";}
        if(engDetailCode.equals("Front right wing")){ res = "front_right_wing";}
        if(engDetailCode.equals("Rear left wing")){ res = "rear_left_wing";}
        if(engDetailCode.equals("Rear right wing")){ res = "rear_right_wing";}
        if(engDetailCode.equals("Roof")){ res = "roof";}
        if(engDetailCode.equals("Trunk lid")){ res = "trunk_lid";}
        if(engDetailCode.equals("Windshield")){ res = "windshield";}
        if(engDetailCode.equals("Rear body glass")){ res = "rear_body_glass";}
        res = "detail_noimage";
        return res;
    }*/

    public static String getTestJsonAiApi(){
        String out = "{\"mask\":[{\"Part_Name\":\"Large parts-Wheel\",\"Part_Name_rus\":\"\\u041a\\u043e\\u043b\\u0435\\u0441\\u043e\",\"Article\":\"6Q0601027AC\",\"Points\":[]},{\"Part_Name\":\"Large parts-Front left door\",\"Part_Name_rus\":\"\\u0414\\u0432\\u0435\\u0440\\u044c \\u043f\\u0435\\u0440\\u0435\\u0434\\u043d\\u044f\\u044f \\u043b\\u0435\\u0432\\u0430\\u044f\",\"Article\":\"6RU831055J\",\"Points\":[]},{\"Part_Name\":\"Large parts-Front left wing\",\"Part_Name_rus\":\"\\u041a\\u0440\\u044b\\u043b\\u043e \\u043f\\u0435\\u0440\\u0435\\u0434\\u043d\\u0435\\u0435 \\u043b\\u0435\\u0432\\u043e\\u0435\",\"Article\":\"6RU821105C\",\"Points\":[]},{\"Part_Name\":\"Large parts-Front left door\",\"Part_Name_rus\":\"\\u0414\\u0432\\u0435\\u0440\\u044c \\u043f\\u0435\\u0440\\u0435\\u0434\\u043d\\u044f\\u044f \\u043b\\u0435\\u0432\\u0430\\u044f\",\"Article\":\"6RU831055J\",\"Points\":[]},{\"Part_Name\":\"Large parts-Rear left wing\",\"Part_Name_rus\":\"\\u041a\\u0440\\u044b\\u043b\\u043e \\u0437\\u0430\\u0434\\u043d\\u0435\\u0435 \\u043b\\u0435\\u0432\\u043e\\u0435\",\"Article\":\"6RU809605A\",\"Points\":[]},{\"Part_Name\":\"Large parts-Wheel\",\"Part_Name_rus\":\"\\u041a\\u043e\\u043b\\u0435\\u0441\\u043e\",\"Article\":\"6Q0601027AC\",\"Points\":[]}],\"settings\":{\"main\":{\"cache\":\"aiapi_23_10_24___23_41_59.json\",\"file_size\":6822,\"view\":\"front_left\"}}}";
        return out;
    }
}
