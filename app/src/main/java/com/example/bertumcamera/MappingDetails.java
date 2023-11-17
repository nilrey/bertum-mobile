package com.example.bertumcamera;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MappingDetails {

    public static  String getSideViewTitleRus(String engSideView) {
        String rus = "Неопределено";
        if(engSideView.equals("front_right")){ rus = "Спереди справа";}
        else if (engSideView.equals("front_front")) { rus = "Спереди";}
        else if (engSideView.equals("front_left")) { rus = "Спереди слева";}
        else if (engSideView.equals("left_left")) { rus = "Слева";}
        else if (engSideView.equals("right_right")) { rus = "Справа";}
        else if (engSideView.equals("back_right")) { rus = "Сзади справа";}
        else if (engSideView.equals("back_back")) { rus = "Сзади";}
        else if (engSideView.equals("back_left")) { rus = "Сзади слева";}
        return rus;
    }

    public static  String getSideViewImage(String engSideView) {
        // заменить картинку на нужный ракурс
        String rus = "m3d_vw6_sed_x033_"+engSideView;
//        if(engSideView.equals("front_right")){ rus = "m3d_vw6_sed_x033_front_right";}
//        else if (engSideView.equals("front_front")) { rus = "m3d_vw6_sed_x033_front_front";}
//        else if (engSideView.equals("front_left")) { rus = "m3d_vw6_sed_x033_front_left";}
//        else if (engSideView.equals("left_left")) { rus = "m3d_vw6_sed_x033_left_left";}
//        else if (engSideView.equals("right_right")) { rus = "m3d_vw6_sed_x033_right_right";}
//        else if (engSideView.equals("back_right")) { rus = "m3d_vw6_sed_x033_back_right";}
//        else if (engSideView.equals("back_back")) { rus = "m3d_vw6_sed_x033_back_back";}
//        else if (engSideView.equals("back_left")) { rus = "m3d_vw6_sed_x033_back_left";}
        return rus;
    }
    public static Boolean checkSideViewDetails( String sideView, String detailCode){
        if(sideView.equals("front_right")){
            if(detailCode.equals("front_bumper")) { return true;}
            if(detailCode.equals("rear_bumper")) { return true;}
            if(detailCode.equals("front_right_door")) { return true;}
            if(detailCode.equals("rear_right_door")) { return true;}
            if(detailCode.equals("hood")) { return true;}
            if(detailCode.equals("wheel")) { return true;}
            if(detailCode.equals("front_right_wing")) { return true;}
            if(detailCode.equals("rear_right_wing")) { return true;}
            if(detailCode.equals("trunk_lid")) { return true;}
            if(detailCode.equals("windshield")) { return true;}
            if(detailCode.equals("roof")) { return true;}
        }else if(sideView.equals("front_left")){
            if(detailCode.equals("front_bumper")) { return true;}
            if(detailCode.equals("rear_bumper")) { return true;}
            if(detailCode.equals("front_left_door")) { return true;}
            if(detailCode.equals("rear_left_door")) { return true;}
            if(detailCode.equals("hood")) { return true;}
            if(detailCode.equals("wheel")) { return true;}
            if(detailCode.equals("front_left_wing")) { return true;}
            if(detailCode.equals("rear_left_wing")) { return true;}
            if(detailCode.equals("trunk_lid")) { return true;}
            if(detailCode.equals("windshield")) { return true;}
            if(detailCode.equals("roof")) { return true;}
        }else if(sideView.equals("left_left")){
            if(detailCode.equals("front_bumper")) { return true;}
            if(detailCode.equals("rear_bumper")) { return true;}
            if(detailCode.equals("front_left_door")) { return true;}
            if(detailCode.equals("rear_left_door")) { return true;}
            if(detailCode.equals("hood")) { return true;}
            if(detailCode.equals("wheel")) { return true;}
            if(detailCode.equals("front_left_wing")) { return true;}
            if(detailCode.equals("rear_left_wing")) { return true;}
            if(detailCode.equals("trunk_lid")) { return true;}
            if(detailCode.equals("windshield")) { return true;}
            if(detailCode.equals("roof")) { return true;}
        }else if(sideView.equals("front_right")){
            if(detailCode.equals("front_bumper")) { return true;}
            if(detailCode.equals("rear_bumper")) { return true;}
            if(detailCode.equals("front_right_door")) { return true;}
            if(detailCode.equals("rear_right_door")) { return true;}
            if(detailCode.equals("hood")) { return true;}
            if(detailCode.equals("wheel")) { return true;}
            if(detailCode.equals("front_right_wing")) { return true;}
            if(detailCode.equals("rear_right_wing")) { return true;}
            if(detailCode.equals("trunk_lid")) { return true;}
            if(detailCode.equals("windshield")) { return true;}
            if(detailCode.equals("roof")) { return true;}
        }
        return false;
    }
}
