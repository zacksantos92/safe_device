// AGP 9 ships with built-in Kotlin support, so the Kotlin Gradle Plugin
// (org.jetbrains.kotlin.android) is not applied.
plugins {
    id("com.android.library")
}

group = "com.xamdesign.safe_device"
version = "1.0"

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

android {
    namespace = "com.xamdesign.safe_device"
    // Kept in sync with Flutter's default compileSdk so host apps are not forced to upgrade.
    //noinspection GradleDependency
    compileSdk = 36

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    // 1.19+ requires compileSdk 37; stay on the latest release that supports 36.
    //noinspection GradleDependency
    implementation("androidx.core:core:1.18.0")
    implementation("androidx.annotation:annotation:1.11.0")
    implementation("com.scottyab:rootbeer-lib:0.1.2")
    implementation("com.google.android.gms:play-services-location:21.4.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
}
