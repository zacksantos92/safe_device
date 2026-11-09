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
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.scottyab:rootbeer-lib:0.1.1")
    implementation("com.google.android.gms:play-services-location:21.1.0")
    implementation("androidx.annotation:annotation:1.7.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
