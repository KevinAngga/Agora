plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.playsService)
}

android {
    namespace = "com.angga.agora"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.angga.agora"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //hilt
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.androidx.hilt.work)

    //lottie
    implementation(libs.lottie)

    //sharedelement
    implementation(libs.androidx.compose.animation)

    //startup
    implementation(libs.startup.android)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    //encryptedPref
    implementation(libs.androidx.security.crypto.ktx)

    //ktor
    implementation(libs.bundles.ktor)

    //coil
    implementation(libs.coil)

    //pallet
    implementation(libs.pallet)

    //room
    implementation(libs.room)
    annotationProcessor(libs.room.runtime)
    implementation(libs.room.runtime)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    //retrofit
    implementation(libs.retrofit.gson.converter)
    implementation(libs.retrofit)

    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.compose)

    //paging
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)

    //timber
    implementation(libs.timber)

    //firebase
    implementation(libs.firebase.bom)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}