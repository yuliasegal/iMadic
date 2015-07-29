package com.philips.iMadic;

import android.app.Application;
import android.content.Context;

import com.philips.iMadic.Models.Interaction;

import java.util.List;

/**
 * Created by 310210574 on 2015-07-27.
 */
public class iMadicApp extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        iMadicApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return iMadicApp.context;
    }

    public static List<Interaction> Interactions;
}
