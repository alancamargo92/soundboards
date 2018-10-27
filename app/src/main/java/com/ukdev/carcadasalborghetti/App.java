package com.ukdev.carcadasalborghetti;

import android.app.Application;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //MobileAds.initialize(this, getString(R.string.adMobId)); TODO
        overrideFont();
    }

    private void overrideFont() {
        try {
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Regular.ttf");
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
