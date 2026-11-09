pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // Used only when building this plugin standalone. Inside a Flutter app,
        // the host project's Android Gradle Plugin version is used instead.
        id("com.android.library") version "9.5.1"
    }
}

rootProject.name = "safe_device"
