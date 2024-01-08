import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.serialization)
}

android {
    namespace = "tech.dalapenko.core.network"
    compileSdk = 34

    buildFeatures.buildConfig = true

    defaultConfig {
        minSdk = 27

        buildConfigField("String", "KINO_AUTH_TOKEN", getPropertyValue("network.properties", "auth.token"))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(libs.bundles.core.network.implementation)
    kapt(libs.bundles.core.network.kapt)
}

fun getPropertyValue(filename: String, propName: String): String {
    val propsFile = project.file(filename)
    check(propsFile.exists())

    val props = Properties()
    props.load(FileInputStream(propsFile))
    check(props[propName] != null)

    return props.getProperty(propName)
}