package com.example.myself.findme.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by MySelf on 4/16/2016.
 */
public class DirectoryStructure {

    public static String main_directory = Environment.getExternalStorageDirectory() + "/gol";
    public static String profile_pic_path = Environment.getExternalStorageDirectory() + "/gol/Your_Profile_Pic.png";
    public static String share_profile_pic_path = Environment.getExternalStorageDirectory() + "/gol/Share_Profile_Pic.png";
    public static String invite_pic_path = Environment.getExternalStorageDirectory() + "/gol/invite_pic.png";
    public static String selfie_pic_path = Environment.getExternalStorageDirectory() + "/gol/selfue_pic.png";


    public static void checkForDirectory() {
        if (!new File(main_directory).exists()) {
            new File(main_directory).mkdir();
        }
    }
}
