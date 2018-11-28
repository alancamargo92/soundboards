package com.ukdev.carcadasalborghetti.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static final String FILE_NAME = "preferences";
    private static final String KEY_HAS_DISMISSED_TIP = "has_dismissed_tip";

    private SharedPreferences preferences;

    public PreferenceHelper(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public boolean hasDismissedTip() {
        return preferences.getBoolean(KEY_HAS_DISMISSED_TIP, false);
    }

    public void setHasDismissedTip() {
        preferences.edit().putBoolean(KEY_HAS_DISMISSED_TIP, true).apply();
    }

}
