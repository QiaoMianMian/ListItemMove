package com.list.move;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {
    private static String PREF_META_DATA = "pref.meta.data";
    private static PrefUtils instance;
    private SharedPreferences.Editor ed;
    private SharedPreferences sp;

    private PrefUtils(Context context) {
        init(context);
    }

    public static PrefUtils getInstance(Context context) {
        if (instance == null) {
            instance = new PrefUtils(context);
        }
        return instance;
    }

    public void init(Context context) {
        if ((this.sp != null) && (this.ed != null)) {
            return;
        }
        try {
            this.sp = context.getSharedPreferences(PREF_META_DATA, Context.MODE_PRIVATE);
            this.ed = this.sp.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //String
    public String getString(String key, String value) {
        if (this.ed == null) {
            return "";
        }
        return this.sp.getString(key, value);
    }

    public boolean saveString(String key, String value) {
        if (this.ed == null) {
            return false;
        }
        this.ed.putString(key, value);
        return this.ed.commit();
    }

    /**
     * ItemBean
     */
    private static final String PRF_ITEM_BEAN = "prf.item.bean";

    public static void saveItemBean(Context context, String str) {
        PrefUtils.getInstance(context).saveString(PRF_ITEM_BEAN, str);
    }

    public static String getItemBean(Context context) {
        return PrefUtils.getInstance(context).getString(PRF_ITEM_BEAN, "");
    }
}