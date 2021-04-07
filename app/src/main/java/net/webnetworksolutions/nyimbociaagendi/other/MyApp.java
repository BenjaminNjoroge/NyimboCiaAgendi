package net.webnetworksolutions.nyimbociaagendi.other;

import android.app.Application;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * BottomNav
 * Created by Benja on 8/2/16.
 * Copyright (c) 2017.  All rights reserved.
 */

public class MyApp extends Application {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
