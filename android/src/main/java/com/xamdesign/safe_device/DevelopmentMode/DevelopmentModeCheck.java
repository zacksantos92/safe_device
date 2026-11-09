package com.xamdesign.safe_device.DevelopmentMode;

import android.content.Context;
import android.provider.Settings;

public class DevelopmentModeCheck {

    public static boolean developmentModeCheck(Context context) {
        return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) != 0;
    }

    public static boolean usbDebuggingCheck(Context context) {
        return Settings.Global.getInt(context.getContentResolver(),
                Settings.Global.ADB_ENABLED, 0) != 0;
    }

}
