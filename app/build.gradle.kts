plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mobilehadist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mobilehadist"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE*"
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    
    // Retrofit & GSON
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    
    // Lifecycle - Gunakan string langsung untuk menghindari sinkronisasi TOML yang error
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    
    // Tambahkan ini untuk memperbaiki crash "NoClassDefFoundError"
    implementation("androidx.concurrent:concurrent-futures:1.1.0")
    implementation("com.google.guava:listenablefuture:1.0")
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

// SOLUSI UNTUK DUPLICATE CLASSES & AAR METADATA
configurations.all {
    resolutionStrategy {
        // Paksa semua library menggunakan versi yang sama agar tidak bentrok
        force("com.google.android.material:material:1.10.0")
        force("androidx.appcompat:appcompat:1.6.1")
        force("androidx.core:core:1.9.0")
        force("androidx.annotation:annotation:1.6.0")
        force("androidx.resourceinspection:resourceinspection-annotation:1.0.1")
        
        // Seragamkan versi Lifecycle
        force("androidx.lifecycle:lifecycle-runtime:2.6.2")
        force("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
        force("androidx.lifecycle:lifecycle-common:2.6.2")
    }
}
