plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "tech.dalapenko.kinosearch"
    compileSdk = 34

    defaultConfig {
        applicationId = "tech.dalapenko.kinosearch"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation(project(":feature:premieres"))
    implementation(project(":feature:releases"))
    implementation(project(":feature:search"))
    implementation(project(":feature:filmdetails"))
    implementation(libs.bundles.app.implementation)
    kapt(libs.bundles.app.kapt)
    testImplementation(libs.bundles.app.testImplementation)
    androidTestImplementation(libs.bundles.app.androidTestImplementation)
}