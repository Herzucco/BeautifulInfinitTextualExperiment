package com.stratos.beautifulinfinittextualexperiment;

import android.app.Application;
import android.content.Context;

public class BeautifulInfinitTextualExperiment extends Application{
	private static Context context;

    public void onCreate(){
        super.onCreate();
        BeautifulInfinitTextualExperiment.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BeautifulInfinitTextualExperiment.context;
    }
}
