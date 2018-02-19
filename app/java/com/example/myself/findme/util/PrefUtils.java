package com.example.myself.findme.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MySelf on 4/16/2016.
 */
public class PrefUtils {

    // app_pref - emailid , islogin

    public static void setValue(Context context,String prefname,String key,String value)
    {
        SharedPreferences sp=context.getSharedPreferences(prefname,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=sp.edit();
        ed.putString(key,value);
        ed.commit();
    }

    public static String getValue(Context context,String prefname,String key)
    {
        SharedPreferences sp=context.getSharedPreferences(prefname,Context.MODE_PRIVATE);

        return  sp.getString(key,null);

    }
}
