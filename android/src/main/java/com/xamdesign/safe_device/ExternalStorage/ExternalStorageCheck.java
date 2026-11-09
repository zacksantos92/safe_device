package com.xamdesign.safe_device.ExternalStorage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class ExternalStorageCheck {
    /**
     * Checks if the application is installed on the SD card.
     *
     * @return <code>true</code> if the application is installed on the sd card
     */
    // The hardcoded "/sdcard/" is intentional: it matches a path, it does not build one.
    @SuppressLint("SdCardPath")
    public static boolean isOnExternalStorage(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            ApplicationInfo ai = pi.applicationInfo;
            return (ai.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE;
        } catch (PackageManager.NameNotFoundException e) {
            // ignore
        }

        // Fallback: check the files dir path
        try {
            String filesDir = context.getFilesDir().getAbsolutePath();
            if (filesDir.startsWith("/data/")) {
                return false;
            } else if (filesDir.contains("/mnt/") || filesDir.contains("/sdcard/")) {
                return true;
            }
        } catch (Throwable e) {
            // ignore
        }

        return false;
    }

}
