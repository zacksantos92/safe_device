package com.xamdesign.safe_device.Emulator;

import android.os.Build;
import com.xamdesign.safe_device.SystemProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.List;

public class EmulatorCheck {

    // #region Files String
    private static ArrayList<String> GENY_FILES = new ArrayList<>(
            Arrays.asList(
                    "/dev/socket/genyd",
                    "/dev/socket/baseband_genyd"));

    private static ArrayList<String> PIPES = new ArrayList<>(
            Arrays.asList(
                    "/dev/socket/qemud",
                    "/dev/qemu_pipe"));

    private static ArrayList<String> X86_FILES = new ArrayList<>(
            Arrays.asList(
                    "ueventd.android_x86.rc",
                    "x86.prop",
                    "ueventd.ttVM_x86.rc",
                    "init.ttVM_x86.rc",
                    "fstab.ttVM_x86",
                    "fstab.vbox86",
                    "init.vbox86.rc",
                    "ueventd.vbox86.rc"));

    private static ArrayList<String> ANDY_FILES = new ArrayList<>(
            Arrays.asList(
                    "fstab.andy",
                    "ueventd.andy.rc"));

    private static ArrayList<String> NOX_FILES = new ArrayList<>(
            Arrays.asList(
                    "fstab.nox",
                    "init.nox.rc",
                    "ueventd.nox.rc"));

    private static ArrayList<String> LDPLAYER_FILES = new ArrayList<>(
            Arrays.asList(
                    "/system/lib/libc_malloc_debug_qemu.so",
                    "/system/bin/microvirt-prop",
                    "/system/bin/microvirt-uiautomator",
                    "/system/bin/microvirtd",
                    "/system/xbin/microvirt-prop"));

    private static ArrayList<String> MEMU_FILES = new ArrayList<>(
            Arrays.asList(
                    "fstab.memu",
                    "init.memu.rc",
                    "ueventd.memu.rc"));
    // #endregion

    // #region Methods
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.MODEL.startsWith("sdk_")
                || Build.DEVICE.startsWith("emulator")
                || (Build.HOST.startsWith("Build") && !isKnownRealDeviceManufacturer())
                // MSI App Player
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk".equals(Build.PRODUCT)
                // another Android SDK emulator check
                || "1".equals(SystemProperties.get("ro.kernel.qemu"))
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.toLowerCase(Locale.ROOT).contains("nox")
                || Build.BOARD.toLowerCase(Locale.ROOT).contains("nox")
                || Build.HARDWARE.toLowerCase(Locale.ROOT).contains("nox")
                || Build.MODEL.toLowerCase(Locale.ROOT).contains("droid4x")
                || "vbox86".equals(Build.HARDWARE)
                // LDPlayer specific checks
                || isLDPlayer()
                // MEmu specific checks
                || isMEmu()
                // BlueStacks specific checks
                || isBlueStacks()
                // MuMu Player specific checks
                || isMuMuPlayer()
                // Enhanced architecture check
                || isX86Architecture()
                || checkEmulatorFiles();
    }

    /**
     * Returns true if the device is from a known real-device manufacturer.
     * Used to avoid false positives in broad emulator checks like Build.HOST.
     */
    private static boolean isKnownRealDeviceManufacturer() {
        String manufacturer = Build.MANUFACTURER.toLowerCase(Locale.ROOT);
        return manufacturer.contains("samsung")
                || manufacturer.contains("sony")
                || manufacturer.contains("xiaomi")
                || manufacturer.contains("motorola")
                || manufacturer.contains("lge")
                || manufacturer.contains("vivo")
                || manufacturer.contains("oppo")
                || manufacturer.contains("realme")
                || manufacturer.contains("huawei")
                || manufacturer.contains("honor")
                || manufacturer.contains("oneplus")
                || manufacturer.contains("asus")
                || manufacturer.contains("nokia");
    }

    /**
     * Enhanced LDPlayer detection
     */
    private static boolean isLDPlayer() {
        return Build.MANUFACTURER.toLowerCase(Locale.ROOT).contains("changwan")
                || Build.BRAND.toLowerCase(Locale.ROOT).contains("changwan")
                || Build.MODEL.toLowerCase(Locale.ROOT).contains("changwan")
                || Build.DEVICE.toLowerCase(Locale.ROOT).contains("changwan")
                || Build.PRODUCT.toLowerCase(Locale.ROOT).contains("changwan")
                || Build.FINGERPRINT.toLowerCase(Locale.ROOT).contains("changwan")
                || Build.MANUFACTURER.toLowerCase(Locale.ROOT).contains("ldplayer")
                || Build.BRAND.toLowerCase(Locale.ROOT).contains("ldplayer")
                || Build.MODEL.toLowerCase(Locale.ROOT).contains("ldplayer")
                || Build.HARDWARE.toLowerCase(Locale.ROOT).contains("lkm")
                || Build.HARDWARE.toLowerCase(Locale.ROOT).contains("ttvm")
                || "LDPlayer".equals(Build.MODEL)
                || "Chang Wan".equals(Build.MANUFACTURER)
                || "ttVM_Hdragon".equals(Build.DEVICE)
                || Build.FINGERPRINT.contains("LDPlayer")
                || checkFiles(LDPLAYER_FILES);
    }

    /**
     * Enhanced MEmu detection
     */
    private static boolean isMEmu() {
        return Build.MANUFACTURER.toLowerCase(Locale.ROOT).contains("memu")
                || Build.BRAND.toLowerCase(Locale.ROOT).contains("memu")
                || Build.MODEL.toLowerCase(Locale.ROOT).contains("memu")
                || Build.DEVICE.toLowerCase(Locale.ROOT).contains("memu")
                || Build.PRODUCT.toLowerCase(Locale.ROOT).contains("memu")
                || "Microvirt".equals(Build.MANUFACTURER)
                || "MEmu".equals(Build.MODEL)
                || Build.HARDWARE.toLowerCase(Locale.ROOT).contains("memu")
                || checkFiles(MEMU_FILES);
    }

    /**
     * Enhanced BlueStacks detection
     */
    private static boolean isBlueStacks() {
        return Build.MANUFACTURER.toLowerCase(Locale.ROOT).contains("bluestacks")
                || Build.BRAND.toLowerCase(Locale.ROOT).contains("bluestacks")
                || Build.MODEL.toLowerCase(Locale.ROOT).contains("bluestacks")
                || Build.DEVICE.toLowerCase(Locale.ROOT).contains("bluestacks")
                || Build.PRODUCT.toLowerCase(Locale.ROOT).contains("bluestacks")
                || "BlueStacks".equals(Build.MANUFACTURER)
                || "QC_Reference_Phone".equals(Build.BOARD) && !isKnownRealDeviceManufacturer();
    }

    /**
     * MuMu Player (NetEase) detection
     */
    private static boolean isMuMuPlayer() {
        return Build.MANUFACTURER.toLowerCase(Locale.ROOT).contains("netease")
                || Build.BRAND.toLowerCase(Locale.ROOT).contains("netease")
                || Build.MODEL.toLowerCase(Locale.ROOT).contains("mumu")
                || Build.PRODUCT.toLowerCase(Locale.ROOT).contains("nemu")
                || Build.DEVICE.toLowerCase(Locale.ROOT).contains("mumu")
                || Build.FINGERPRINT.toLowerCase(Locale.ROOT).contains("netease");
    }

    /**
     * Enhanced x86 architecture detection
     */
    private static boolean isX86Architecture() {
        try {
            String arch = System.getProperty("os.arch");
            return arch != null && (arch.contains("x86") || arch.contains("i686"));
        } catch (Exception e) {
            return false;
        }
    }
    // #endregion

    // #region Utils
    private static boolean checkFiles(List<String> targets) {
        for (String pipe : targets) {
            File file = new File(pipe);
            if (file.exists())
                return true;
        }
        return false;
    }

    private static boolean checkEmulatorFiles() {
        return (checkFiles(GENY_FILES)
                || checkFiles(ANDY_FILES)
                || checkFiles(NOX_FILES)
                || checkFiles(X86_FILES)
                || checkFiles(PIPES)
                || checkFiles(LDPLAYER_FILES)
                || checkFiles(MEMU_FILES));
    }
    // #endregion
}
